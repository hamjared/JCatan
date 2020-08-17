package com.JCatan;

import java.util.HashMap;
import java.util.Map;

public abstract class DevelopmentCard implements ICost {
	private boolean canBePlayed;
	public boolean isCanBePlayed() {
		return canBePlayed;
	}

	public void setCanBePlayed(boolean canBePlayed) {
		this.canBePlayed = canBePlayed;
	}

	private boolean hasBeenPlayed;
	
	
	/**
	 * 
	 */
	public DevelopmentCard() {
		canBePlayed = false;
		hasBeenPlayed = false;
	}
	
	public boolean isHasBeenPlayed() {
		return hasBeenPlayed;
	}

	public void setHasBeenPlayed(boolean hasBeenPlayed) {
		this.hasBeenPlayed = hasBeenPlayed;
	}

	/**
	 * @throws InvalidDevCardUseException
	 */
	public abstract void performAction() throws InvalidDevCardUseException;
	
	public int getVictoryPoints() {
	    return 0;
	}
	
	public Map<ResourceType, Integer> getCost(){
	    Map<ResourceType, Integer> cost = new HashMap<>();
	    
	    cost.put(ResourceType.WHEAT , 1);
	    cost.put(ResourceType.SHEEP, 1);
	    cost.put(ResourceType.ORE, 1);
	    
	    
        return cost;
	    
	}
}
