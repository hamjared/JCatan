package com.JCatan.server;

import java.awt.Point;

import com.JCatan.GameController;
import com.JCatan.Node;
import com.JCatan.Player;
import com.JCatan.Road;
import com.JCatan.Tile;
import com.JCatan.Trade;

public class MessageBuilder {
	
	Message message;
	
	public MessageBuilder() {
		message = new Message();	
	}
	
	public MessageBuilder action(Message.Action action) {
		message.setAction(action);
		return this;
	}
	
	public MessageBuilder buildOnNode(Node node) {
		message.setNode(node);
		return this;
	}
	
	public MessageBuilder player(Player player) {
		message.setMyPlayer(player);
		return this;
	}
	
	public MessageBuilder gameController(GameController gc) {
		message.setGc(gc);
		return this;
	}
	
	public MessageBuilder road(Road road) {
		message.setRoad(road);
		return this;
	}
	
	public Message build() {
		return message;
	}
	
	public MessageBuilder trade(Trade trade) {
		message.setTrade(trade);
		return this;
	}
	
	public MessageBuilder robberPoint(Point p) {
		message.setRobberPoint(p);
		return this;
	}

	public MessageBuilder robberTile(Tile tile) {
		message.robberTile = tile;
		return this;
	}
	


}
