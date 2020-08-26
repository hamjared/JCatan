package com.JCatan.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.JCatan.GameController;
import com.JCatan.GamePhase;
import com.JCatan.InsufficientResourceCardException;
import com.JCatan.Node;
import com.JCatan.Player;


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
				case EndTurn:
					endTurn(msg);
					break;
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

	private void endTurn(Message msg) {
		GameController controller = server.getController();
		controller.endSetupTurn();
		
	}

	private void buildRoad(Message msg) {
		System.out.println("Building a road for : " + msg.getMyPlayer() + " from " + msg.getRoad().getNode1().getNodeIndex() + "to " + msg.getRoad().getNode2().getNodeIndex());
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
