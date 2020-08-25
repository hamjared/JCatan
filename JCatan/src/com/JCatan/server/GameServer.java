package com.JCatan.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.JCatan.BoardFactory;
import com.JCatan.GameController;
import com.JCatan.Player;
import com.JCatan.RandomBoardFactory;
import com.JCatan.TraditionalBoardFactory;
import com.JCatan.client.InitiatePlayer;

public class GameServer {
	List<PlayerSocket> players;
	List<BoardFactory> boardPreferences;
	GameController controller;
	ServerSocket connectionListener;
	
	public GameServer(int port) {
		
		try {
			this.connectionListener = new ServerSocket();
			connectionListener.setReuseAddress(true);
			connectionListener.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			System.out.println("Unable to create server Socket on port " + port);
		}
		
		players = new ArrayList<>();
		boardPreferences = new ArrayList<>();
		
	}
	
	
	public void acceptConnections() {
		System.out.println("Waiting for connections on: " + connectionListener);
		while(players.size() < 4) {
			Socket s = null;
			try {
				s = connectionListener.accept();
				if(s == null) {
					continue;
				}
				System.out.println("Connection accepted: " + s);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				handleNewPlayer(ois.readObject(), s);
			} catch(IOException e) {
				
			} catch (ClassNotFoundException e) {
				
			}
		}
	}

	
	public void acceptMessages() {
		List<Thread> threads = new ArrayList<>();
		for( PlayerSocket p: players) {
			Thread thread = new Thread(p);
			 threads.add(thread);
			 thread.start();
		}
		
		threads.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	private void handleNewPlayer(Object obj, Socket con) {
		System.out.println("Creating new player");
		InitiatePlayer player = null;
		if(obj != null && obj instanceof InitiatePlayer) {
			player = (InitiatePlayer) obj;
		}
		if(player == null) {
			System.out.println("Player was null");
			return;
		}
		PlayerSocket ps = new PlayerSocket(player.getPlayer(), con);
		this.players.add(ps);
		this.boardPreferences.add(player.getFactory());
		
		System.out.println("Player added: " + player.getPlayer());
	}


	public void startGame() {
		int countRandom = 0;
		int countTraditional = 0;
		
		for (BoardFactory f: this.boardPreferences) {
			if (f instanceof TraditionalBoardFactory) {
				countTraditional ++;
			}
			if(f instanceof RandomBoardFactory) {
				countRandom++;
			}
		}
		
		List<Player> p = new ArrayList<>();
		for(PlayerSocket ps: players) {
			p.add(ps.getPlayer());
		}
		
		if(countTraditional >= countRandom) {
			System.out.println("Started game with traditional board");
			controller = new GameController(p, new TraditionalBoardFactory());
		}
		else {
			System.out.println("Started game with random board");
			controller = new GameController(p, new RandomBoardFactory());
		}
		
	}
	

	
}
