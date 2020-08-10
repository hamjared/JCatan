package com.JCatan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Player {
	List<ResourceCard> resources;
	List<DevelopmentCard> devCards;
	int victoryPoints;
	int diceRoll;
	String name;
	
	Player(String name){
		resources = new ArrayList<ResourceCard>();
		devCards = new ArrayList<DevelopmentCard>();
		victoryPoints = 0;
		this.name = name;
	}
	
	public List<ResourceCard> getResources(){
		return resources;
	}
	
    public Map<ResourceType, Integer> getUniqueResourcesCount(){
   	 Map<ResourceType, Integer> temp = resources.stream().collect(
   			 										Collectors.toMap(ResourceCard::getResourceType,
   			 										r->1, (existing, addition)-> existing + addition));
   	 return temp;
   }
	
	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buildCity() throws InsufficientResourceCardException;
	
	/**
	 * 
	 */
	public abstract void buildPhase();
	
	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buildRoad() throws InsufficientResourceCardException;
	
	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buildSettlement() throws InsufficientResourceCardException;
	
	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buyDevelopmentCard() throws InsufficientResourceCardException;
	
	/**
	 * 
	 */
	public abstract int calcVictoryPoints();
	
	/**
	 * 
	 */
	public abstract void endTurn();
	
	public String getName() {
		return name;
	}
	
	/**
	 * @return
	 */
	public int getDiceRoll() {
		return diceRoll;
	}
	
	/**
	 * @param card
	 * @throws InvalidDevCardUseException
	 */
	public abstract void playDevelopmentCard(DevelopmentCard card) throws InvalidDevCardUseException;
	
	/**
	 * @param trade
	 */
	public abstract void proposeTrade(Trade trade);
	
	/**
	 * @param trade
	 */
	public abstract void receiveTrade(Trade trade);
	
	/**
	 * @return
	 */
	public abstract int rollDice();
	
	/**
	 * 
	 */
	public abstract void setup();
	
	/**
	 * @param activePlayer
	 */
	public abstract void sevenRolled(Player activePlayer);
	
	/**
	 *
	 */
	@Override
    public String toString() {
		return name + ": " + diceRoll;
	}
	
	/**
	 * 
	 */
	public abstract void tradePhase();
	
	
	
	

}