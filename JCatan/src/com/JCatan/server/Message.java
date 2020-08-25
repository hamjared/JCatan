package com.JCatan.server;

import java.io.Serializable;
import java.util.List;

import com.JCatan.Board;
import com.JCatan.GameController;
import com.JCatan.Player;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public enum Action {
		RollDice
	}
	GameController gc;
	Action action;
	
	
	public Message(Action action, GameController controller) {
		this.action = action;
		this.gc = controller;
	}
	
}
