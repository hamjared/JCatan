package com.JCatan;

public abstract class Building implements ICost {

	protected int victoryPoints;
	protected boolean hasBeenPlayed;
	Player player;
	
	public Building(Player player, int victoryPoints) {
	    this.player = player;
	    this.victoryPoints = victoryPoints;
	    this.hasBeenPlayed = false;
	}

	/**
	 * @param resource
	 */
	public abstract void gatherResources(GameController controller, ResourceType resource);
	
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

	public void gatherResources(ResourceType resource) {
		// TODO Auto-generated method stub
		
	}

	public Player getPlayer() {
		return player;
	}
	
	
}
