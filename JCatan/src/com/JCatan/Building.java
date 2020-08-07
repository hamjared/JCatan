package com.JCatan;

public abstract class Building {

	private int victoryPoints;
	Player player;
	
	public Building(int victoryPoints, Player player) {
		this.victoryPoints = victoryPoints;
		this.player = player;
	}

	/**
	 * @param resource
	 */
	public abstract void gatherResources(ResourceType resource);
}
