package com.JCatan.server;

public class Main {

	public static void main(String[] args) {
		GameServer gs = new GameServer(5679);

		gs.acceptConnections();
		
		gs.startGame();
		
		gs.acceptMessages();

	}

}
