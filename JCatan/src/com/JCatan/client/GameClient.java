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
import com.JCatan.server.MessageBuilder;

public class GameClient implements Runnable{
	
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
		} catch(IOException e) {
			System.out.println("Unable to connect to server on " + ip + " Port: " + port);
		}
	}
	
	public void sendPlayer(InitiatePlayer player)  {
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
		while(true) {
			try {
				ObjectInputStream oos = new ObjectInputStream(serverConnection.getInputStream());
				Object obj = oos.readObject();
				if(obj == null) {
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
		while(true) {
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
				default:
					break;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	private void updateBoard(Message msg) {
		System.out.println("Updating Board ...");
		GameGUI.controller = msg.getGc();
		System.out.println("Board from Server: ");
		System.out.println(GameGUI.controller.getBoard());
		System.out.println("------------------");
		gameGUI.updateTurn();
		gameGUI.updatePlayer();
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
