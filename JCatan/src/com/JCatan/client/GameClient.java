package com.JCatan.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.JCatan.HumanPlayer;
import com.JCatan.Player;
import com.JCatan.TraditionalBoardFactory;

public class GameClient implements Runnable{
	
	Socket serverConnection;

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

	@Override
	public void run() {
		while(true) {
			System.out.println("Accepting Messages from server...");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		GameClient gc = new GameClient("127.0.0.1", 5678);
		gc.sendPlayer(new InitiatePlayer(new HumanPlayer("Tim"), new TraditionalBoardFactory()));
		
		Thread thread = new Thread(gc);
		thread.start();
		thread.join();
	}
	
}
