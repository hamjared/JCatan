package com.JCatan;

import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buildCity(Node node) throws InsufficientResourceCardException;
	
	/**
	 * 
	 */
	public abstract void buildPhase();
	
	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buildRoad(GamePhase phase, Node node1, Node node2) throws InsufficientResourceCardException;
	
	/**
	 * @throws InsufficientResourceCardException
	 */
	public abstract void buildSettlement(GamePhase phase, Node node) throws InsufficientResourceCardException;
	
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
	
	/**
	 * @return
	 */
	public int getDiceRoll() {
		return diceRoll;
	}
	
	public List<ResourceCard> getResources() {
		return resources;
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
	public abstract void setup(Node node1, Node node2);
	
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
