package com.JCatan.server;

import java.awt.Point;
import java.io.Serializable;
import java.util.List;

import com.JCatan.Board;
import com.JCatan.Chat;
import com.JCatan.DevCardAction;
import com.JCatan.DevelopmentCard;
import com.JCatan.Dice;
import com.JCatan.GameController;
import com.JCatan.Node;
import com.JCatan.Player;
import com.JCatan.Road;
import com.JCatan.Tile;
import com.JCatan.Trade;

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
		BuyDevelopmentCard, 
		PlayDevelopmentCard,
		MoveRobber, 
		RollDice, UpdateBoard, EndTurn, DiceRolled, EndSetupTurn, Trade,BadTrade,
		FinalizeTrade, BankAcceptedTrade, DeclineTrade DiceData, Chat
	}
	
	GameController gc;
	Player myPlayer;
	Node node;
	Road road;
	Tile robberTile;
	Point RobberPoint;
	String customMessage;
	Dice dice;
	String chatMessage;



	public String getChatMessage() {
		return chatMessage;
	}


	public void setChatMessage(String chatMessage) {
		this.chatMessage = chatMessage;
	}


	public Dice getDice() {
		return dice;
	}


	public void setDice(Dice dice) {
		this.dice = dice;
	}


	Trade trade;
	DevCardAction devCardAction;
	DevelopmentCard devCard;
	
	public DevelopmentCard getDevCard() {
		return devCard;
	}

	public void setCustomMessage(String val) {
		customMessage = val;
	}
	public String getCustomMessage() {
		return customMessage;
	}

	public void setDevCard(DevelopmentCard devCard) {
		this.devCard = devCard;
	}

	public DevCardAction getDevCardAction() {
		return devCardAction;
	}

	public void setDevCardAction(DevCardAction devCardAction) {
		this.devCardAction = devCardAction;
	}

	public Point getRobberPoint() {
		return RobberPoint;
	}


	public void setRobberPoint(Point robberPoint) {
		RobberPoint = robberPoint;
	}


	public Tile getRobberTile() {
		return robberTile;
	}


	public void setRobberTile(Tile robberTile) {
		this.robberTile = robberTile;
	}


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
	
	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	
	public Trade getTrade() {
		return this.trade;
	}
	
	public Message() {
		// TODO Auto-generated constructor stub
	}
	
}
