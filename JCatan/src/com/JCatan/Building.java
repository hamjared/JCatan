package com.JCatan;

public abstract class Building implements ICost {

	protected int victoryPoints;
	protected boolean hasBeenPlayed;
	Player player;
	
	public Building(Player player, int victoryPoints) {
	    this.player = player;
	    this.victoryPoints = victoryPoints;
	    this.hasBeenPlayed = false;
	    player.giveBuilding(this);
	}

	/**
	 * @param resource
	 */
	public abstract void gatherResources(ResourceType resource);
	
	public  int getVictoryPoints() {
	    return victoryPoints;
	}

    public boolean hasBeenPlayed()
    {
        return hasBeenPlayed;
    }

    public void setHasBeenPlayed(boolean hasBeenPlayed)
    {
        this.hasBeenPlayed = hasBeenPlayed;
    }
	
	
}
