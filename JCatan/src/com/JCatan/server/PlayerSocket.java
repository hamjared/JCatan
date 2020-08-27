package com.JCatan.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.JCatan.GameController;
import com.JCatan.GamePhase;
import com.JCatan.HumanPlayer;
import com.JCatan.InsufficientResourceCardException;
import com.JCatan.Node;
import com.JCatan.Player;
import com.JCatan.gui.GameGUI;

public class PlayerSocket implements Runnable {
	Player player;
	Socket connection;
	GameServer server;

	public Socket getConnection() {
		return connection;
	}

	public PlayerSocket(Player player, Socket socket, GameServer server) {
		this.player = player;
		this.connection = socket;
		this.server = server;
	}

	@Override
	public void run() {
		while (true) {
			try {
				ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
				Object obj = ois.readObject();
				if (obj == null || !(obj instanceof Message)) {
					continue;
				}
				Message msg = (Message) obj;
				switch (msg.getAction()) {
				case BuildSettlement:
					buildSettlement(msg);
					break;
				case BuildRoad:
					buildRoad(msg);
					break;
				case EndSetupTurn:
					endSetupTurn(msg);
					break;
				case EndTurn:
					endTurn(msg);
					Message m = new MessageBuilder().action(Message.Action.EndTurn)
							.gameController(server.getController()).build();
					server.broadcastMessage(m);
					continue;
				case MoveRobber:
					moveRobber(msg);
					Message msg2 = new MessageBuilder().action(Message.Action.MoveRobber)
							.gameController(server.getController()).robberPoint(msg.getRobberPoint()).build();
					server.broadcastMessage(msg2);
					continue;
				case RollDice:
					rollDice();
					Message message = new MessageBuilder().action(Message.Action.DiceRolled)
							.gameController(server.getController()).build();
					server.broadcastMessage(message);
					continue;
					
				case Trade:
					if(msg.getTrade() instanceof com.JCatan.DomesticTrade)
						server.broadcastMessage(msg);
					else
						trade(msg);
					continue;
				case FinalizeTrade:
					finalizeTrade(msg);
					continue;
				default:
					break;
				}

				Message returnMessage = new MessageBuilder().action(Message.Action.UpdateBoard)
						.gameController(server.getController()).build();

				System.out.println("Game board: ");
				System.out.println(server.getController().getBoard());

				server.broadcastMessage(returnMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void trade(Message msg) {
		System.out.println("Player is initiating Maritime Trade");
		server.getController().initiateTrade(msg.getTrade());
	}
	
	private void finalizeTrade(Message msg) {
		com.JCatan.Trade trade = msg.getTrade();
		if(trade != null) {
			server.getController().initiateTrade(trade);
			Player offerer = server.getController().getPlayers().stream().filter(p->p.getName().equals(trade.getOfferingPlayer().getName())).findFirst().get();
			Player receiver = server.getController().getPlayers().stream().filter(p->p.getName().equals(trade.getReceivingPlayer().getName())).findFirst().get();
			offerer.receiveTrade(trade);
			receiver.receiveTrade(trade);
			msg.setGc(server.getController());
			server.broadcastMessage(msg);
		}
	}
	
	private void moveRobber(Message msg) {
		System.out.println("Moving robber to tile " + msg.getRobberTile().getNumber());
		server.getController().setGamePhase(GamePhase.GAMEMAIN);
		server.getController().getRobber().move(msg.getRobberTile());
		server.getController().getRobber().rob(server.getController().getCurPlayer());

	}

	private void endTurn(Message msg) {
		server.getController().setGamePhase(GamePhase.GAMEROLL);
		server.getController().gamePhaseEnd();
		System.out.println("Turn ended, " + server.getController().getCurPlayer().getName() + "'s Turn now");

	}

	private void rollDice() {
		server.getController().getCurPlayer().rollDice();
		server.getController().gamePhaseRoll();
		for(Player p : server.getController().getPlayers()) {
			System.out.println(p.getName() + " Hand: " + p.getResources());
		}

	}

	private void endSetupTurn(Message msg) {
		GameController controller = server.getController();
		controller.endSetupTurn();

	}

	private void buildRoad(Message msg) {
		System.out.println("Building a road for : " + msg.getMyPlayer() + " from "
				+ msg.getRoad().getNode1().getNodeIndex() + "to " + msg.getRoad().getNode2().getNodeIndex());
		GameController controller = server.getController();
		try {
			Node node1 = controller.getBoard().getBoard().getNodeList().get(msg.getRoad().getNode1().getNodeIndex());
			Node node2 = controller.getBoard().getBoard().getNodeList().get(msg.getRoad().getNode2().getNodeIndex());
			controller.getCurPlayer().buildRoad(controller.getGamePhase(), node1, node2, controller);
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void buildSettlement(Message msg) {
		System.out.println("Building a setllement for : " + msg.getMyPlayer() + "on node " + msg.getNode());
		GameController controller = server.getController();
		try {
			Node node = controller.getBoard().getBoard().getNodeList().get(msg.getNode().getNodeIndex());
			controller.getCurPlayer().buildSettlement(controller.getGamePhase(), node, controller);
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Player getPlayer() {
		return player;
	}

}
