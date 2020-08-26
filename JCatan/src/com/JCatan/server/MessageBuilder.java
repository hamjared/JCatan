package com.JCatan.server;

import com.JCatan.GameController;
import com.JCatan.Node;
import com.JCatan.Player;
import com.JCatan.Road;

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
	


}
