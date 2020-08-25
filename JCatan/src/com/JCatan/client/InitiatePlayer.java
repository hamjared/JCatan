package com.JCatan.client;

import java.io.Serializable;

import com.JCatan.BoardFactory;
import com.JCatan.Player;

public class InitiatePlayer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player player;
	BoardFactory factory;
	
	public InitiatePlayer(Player player, BoardFactory bf) {
		this.player = player;
		this.factory = bf;
	}

	public Player getPlayer() {
		return player;
	}

	public BoardFactory getFactory() {
		return factory;
	}

}
