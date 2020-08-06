package com.JCatan;

public abstract class Building implements ICost {

	private int victoryPoints;
	Player player;
	
	/**
	 * @param resource
	 */
	public abstract void gatherResources(ResourceType resource);
}
