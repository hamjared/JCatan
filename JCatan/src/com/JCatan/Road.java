package com.JCatan;

public class Road {

	Node node1;
	Node node2;
	Player player;
	
	/**
	 * @param node1
	 * @param node2
	 * @param player
	 */
	public Road(Node node1, Node node2, Player player) {
		this.node1 = node1;
		this.node2 = node2;
		this.player = player;
	}
}
