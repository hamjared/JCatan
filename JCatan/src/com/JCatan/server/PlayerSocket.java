package com.JCatan.server;

import java.net.Socket;

import com.JCatan.Player;

public class PlayerSocket implements Runnable{
	Player player;
	Socket connection;
	
	
	public PlayerSocket(Player player, Socket socket) {
		this.player = player;
		this.connection = socket;
	}


	@Override
	public void run() {
		while(true) {
			System.out.println("Accepting messages from " + player);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}


	public Player getPlayer() {
		return player;
	}
	
}
