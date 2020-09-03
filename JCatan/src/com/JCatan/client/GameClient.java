package com.JCatan.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.JCatan.GameController;
import com.JCatan.HumanPlayer;
import com.JCatan.Player;
import com.JCatan.TraditionalBoardFactory;
import com.JCatan.gui.GameGUI;
import com.JCatan.server.Message;

public class GameClient implements Runnable {

	Socket serverConnection;
	GameGUI gameGUI;
	GameController controller;

	public void setGameGUI(GameGUI gg) {
		gameGUI = gg;
	}

	public Socket getServerConnection() {
		return serverConnection;
	}

	public void setServerConnection(Socket serverConnection) {
		this.serverConnection = serverConnection;
	}

	public GameController getController() {
		return controller;
	}

	public GameClient(String ip, int port) {
		try {
			serverConnection = new Socket(ip, port);
			System.out.println("Connected to server: " + serverConnection);
		} catch (IOException e) {
			System.out.println("Unable to connect to server on " + ip + " Port: " + port);
		}
	}

	public void sendPlayer(InitiatePlayer player) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(serverConnection.getOutputStream());
			oos.writeObject(player);
		} catch (IOException e) {
			System.out.println("Could not send player");
			e.printStackTrace();
		}
	}

	public void waitForGame() {
		System.out.println("Waiting for game to start ... ");
		while (true) {
			try {
				ObjectInputStream oos = new ObjectInputStream(serverConnection.getInputStream());
				Object obj = oos.readObject();
				if (obj == null) {
					continue;
				}
				Message msg = (Message) obj;
				controller = msg.getGc();
				gameGUI.initialize(controller);
				gameGUI.setMyPlayer(msg.getMyPlayer());
				gameGUI.updateGameController(controller);
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void sendMessage(Message msg) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(serverConnection.getOutputStream());
			oos.writeObject(msg);
		} catch (IOException e) {
			System.out.println("Could not send message");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("Waiting for messages from server");
		while (true) {
			try {
				ObjectInputStream ois = new ObjectInputStream(serverConnection.getInputStream());
				Object obj = ois.readObject();
				if (obj == null || !(obj instanceof Message)) {
					continue;
				}

				Message msg = (Message) obj;
				System.out.println("Message from server received: " + msg.getAction());
				switch (msg.getAction()) {
				case UpdateBoard:
					updateBoard(msg);
					break;
				case DiceRolled:
					diceRolled(msg);
					break;
				case EndTurn:
					turnedEnded(msg);
					break;
				case MoveRobber:
					robberMoved(msg);
					break;
				case Trade:
					trade(msg);
					break;
				case BadTrade:
					badTradeMessage(msg);
					break;
				case BankAcceptedTrade:
					bankAcceptedTrade(msg);
					break;
				case FinalizeTrade:
					finalizeTrade(msg);
					break;
				case DeclineTrade:
					playerDeclinedTrade(msg);
					break;
				case DiceData:
					showDiceData(msg);
					break;
				case RoadBuilder:
					redrawBuildableRoads(msg);
					break;
				default:
					break;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(GameGUI.myPlayer.getName() + " Lost connection to server");
				e.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private void redrawBuildableRoads(Message msg) {
		GameGUI.controller = msg.getGc();
		this.controller = msg.getGc();
		gameGUI.updatePlayer();
		gameGUI.getBoardPanel().buildRoad();
		gameGUI.repaint();
		
	}

	private void playerDeclinedTrade(Message msg) {
		boolean isPlayerOfferer = gameGUI.getMyPlayer().equals(msg.getMyPlayer());
		if(isPlayerOfferer) {
			gameGUI.notifyPlayerDeclinedTrade(msg.getCustomMessage());
		}
	}
	
	private void bankAcceptedTrade(Message msg) {
		boolean isPlayerOfferer = gameGUI.getMyPlayer().getName().equals(msg.getMyPlayer().getName());
		GameGUI.controller = msg.getGc();
		gameGUI.updatePlayer();
		if(isPlayerOfferer) {
			gameGUI.notifyPlayerBankAcceptedTrade(msg.getCustomMessage());
		}
		gameGUI.repaint();
	}
	
	private void badTradeMessage(Message msg) {
		boolean isPlayerOfferer = gameGUI.getMyPlayer().getName().equals(msg.getMyPlayer().getName());
		if(isPlayerOfferer)
			gameGUI.notifyPlayerBadTrade(msg.getCustomMessage());		
	}

	private void showDiceData(Message msg) {
		GameGUI.controller = msg.getGc();
		this.controller = msg.getGc();
		System.out.println("Dice roll history:");
		System.out.println(msg.getDice().getDiceRollHistory());
		gameGUI.repaint();
		
	}

	private void robberMoved(Message msg) {
		GameGUI.controller = msg.getGc();
		this.controller = msg.getGc();
		System.out.println("Robber Moved");
		System.out.println("Is Robber moving: " + GameGUI.controller.getBoard().isRobberMoving());
		System.out.println("Game Phase: " + GameGUI.controller.getGamePhase());
		gameGUI.robberMoved(msg.getRobberPoint());
		gameGUI.updatePlayer();
		gameGUI.repaint();

	}

	private void turnedEnded(Message msg) {
		GameGUI.controller = msg.getGc();
		this.controller = msg.getGc();
		System.out.println(GameGUI.controller.getCurPlayer().getName() + "'s turn");
		gameGUI.updatePlayer();
		gameGUI.updateDevCardPanel();
		gameGUI.gamePhaseEnd();

	}

	private void diceRolled(Message msg) {
		GameGUI.controller = msg.getGc();
		this.controller = msg.getGc();
		gameGUI.updatePlayer();
		System.out.println(gameGUI.getMyPlayer().getName() + " Hand: " + gameGUI.getMyPlayer().getResources());
		gameGUI.diceRolled();
	}

	private void updateBoard(Message msg) {
		System.out.println("Updating Board ...");
		GameGUI.controller = msg.getGc();
		this.controller = msg.getGc();
		System.out.println("Board from Server: ");
		System.out.println(GameGUI.controller.getBoard());
		System.out.println("------------------");
		System.out.println("Game Phase in GameCLient: " + this.getController().getGamePhase());
		System.out.println("Game Phase in GameGUI: " + GameGUI.controller.getGamePhase());
		gameGUI.updateDevCardPanel();
		gameGUI.updateTurn();
		gameGUI.updatePlayer();
		gameGUI.repaint();

	}

	private void trade(Message msg) {
		System.out.println("Player is wanting to trade with another player...");
		System.out.println("Receiving Player is: " + msg.getTrade().getReceivingPlayer().getName());
		System.out.println("My player is: " + gameGUI.getMyPlayer().getName());
		if (msg.getTrade().getReceivingPlayer().equals(gameGUI.getMyPlayer())) {
			System.out.println("Trying to enable and start trade panel");
			gameGUI.getTradePanel().setTradeInfo(msg.getTrade());
		}
	}
	
	private void finalizeTrade(Message msg) {
		System.out.println("Finalizing Trade");
		GameGUI.controller = msg.getGc();
		gameGUI.updatePlayer();
		Player myPlayer = gameGUI.getMyPlayer();
		Player offeringPlayer = msg.getTrade().getOfferingPlayer();
		Player receivingPlayer = msg.getTrade().getReceivingPlayer();
		boolean isPlayerTrading = myPlayer.equals(offeringPlayer) || myPlayer.equals(receivingPlayer);
		if(isPlayerTrading) {
			gameGUI.getTradePanel().close();
		}
		gameGUI.repaint();
	}

	public static void main(String[] args) throws InterruptedException {
		GameClient gc = new GameClient("127.0.0.1", 5678);
		gc.sendPlayer(new InitiatePlayer(new HumanPlayer("Tim"), new TraditionalBoardFactory()));

		Thread thread = new Thread(gc);
		thread.start();
		thread.join();
	}

}
