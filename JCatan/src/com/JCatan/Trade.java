package com.JCatan;

import java.io.Serializable;
import java.util.List;

public abstract class Trade implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player offeringPlayer;
	Player receivingPlayer;
	List<ResourceCard> offeringCards;
	List<ResourceCard> requestingCards;
	boolean acceptedOffer;
	
	/**
	 * @param offeringPlayer  Player proposing the trade
	 * @param offeringCards  Cards being offered in the trade
	 * @param requestingCards  Cards wanted by the player in the trade
	 */
	public Trade(Player offeringPlayer, List<ResourceCard> offeringCards, List<ResourceCard> requestingCards) {
		this.offeringPlayer = offeringPlayer;
		this.offeringCards = offeringCards;
		this.requestingCards = requestingCards;
	}
	
	public List<ResourceCard> getOfferingCards(){
		return offeringCards;
	}
	
	public List<ResourceCard> getRequestingCards(){
		return requestingCards;
	}
	
	public Player getReceivingPlayer() {
		return receivingPlayer;
	}
	
	public Player getOfferingPlayer() {
		return offeringPlayer;
	}
	
	/**
	 * @param player
	 */
	public abstract void offer(Player player);
	
	public abstract void validateTrade() throws InvalidTradeException;
}