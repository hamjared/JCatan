package com.JCatan;

import java.util.List;

public abstract class Trade {
	
	Player offeringPlayer;
	Player receivingPlayer;
	List<ResourceCard> offeringCards;
	List<ResourceCard> requestingCards;
	
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
	
	/**
	 * 
	 */
	public abstract void accept();

	/**
	 * 
	 */
	public abstract void counterOffer();
	
	/**
	 * 
	 */
	public abstract void decline();
	
	/**
	 * @param player
	 */
	public abstract void offer(Player player);
	
	/**
	 * @throws InvalidTradeException
	 */
	protected abstract void validateTrade() throws InvalidTradeException;
	
}
