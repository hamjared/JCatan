package com.JCatan;

import java.util.List;

public class DomesticTrade extends Trade {
	Player receivingPlayer;
	
	public DomesticTrade(Player offeringPlayer, Player receivingPlayer, List<ResourceCard> offeringCards, List<ResourceCard> requestingCards) {
		super(offeringPlayer, offeringCards, requestingCards);
		this.receivingPlayer = receivingPlayer;
	}
	
	public Player getReceivingPlayer() {
		return receivingPlayer;
	}

    @Override
    public void accept()
    {
        //offeringPlayer remove offering cards
    	
    	//player remove requesting cards
    	
    	//offeringPlayer insert requestingCards
    	
    	//player insert offering cards
        
    }

    @Override
    public void counterOffer()
    {
        // TODO Trade: counterOffer
        
    }

    @Override
    public void decline()
    {
        // TODO Trade: decline
        
    }

    @Override
    public void offer(Player player)
    {
        // TODO Trade: offer
        
    }

    @Override
    protected void validateTrade() throws InvalidTradeException
    {
        // TODO Trade: validateTrade
        
    }



}
