package com.JCatan.server;

public class Main {

	public static void main(String[] args) {
		GameServer gs = new GameServer(5678);

		gs.acceptConnections();
		
		gs.startGame();
		
		gs.acceptMessages();

	}

}
