package com.JCatan.server;

import java.io.Serializable;
import java.util.List;

import com.JCatan.Board;
import com.JCatan.GameController;
import com.JCatan.Node;
import com.JCatan.Player;
import com.JCatan.Road;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Road getRoad() {
		return road;
	}


	public void setRoad(Road road) {
		this.road = road;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public enum Action {
		StartGame,
		BuildSettlement, 
		BuildRoad,
		BuildCity, 
		BuyDevelopment, 
		PlayDevelopmentCard,
		MoveRobber, 
		RollDice, UpdateBoard, EndTurn
	}
	GameController gc;
	Player myPlayer;
	Node node;
	Road road;
	
	
	public Node getNode() {
		return node;
	}


	public void setNode(Node node) {
		this.node = node;
	}


	public GameController getGc() {
		return gc;
	}


	public void setGc(GameController gc) {
		this.gc = gc;
	}


	public Player getMyPlayer() {
		return myPlayer;
	}


	public void setMyPlayer(Player myPlayer) {
		this.myPlayer = myPlayer;
	}


	public Action getAction() {
		return action;
	}


	public void setAction(Action action) {
		this.action = action;
	}


	Action action;
	
	
	public Message(Action action, GameController controller, Player player) {
		this.action = action;
		this.gc = controller;
		this.myPlayer = player;
	}


	public Message() {
		// TODO Auto-generated constructor stub
	}
	
}
