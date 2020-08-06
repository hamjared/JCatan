package com.JCatan;

import java.util.List;

public class MaritimeTrade extends Trade {

	public MaritimeTrade(Player offeringPlayer, List<ResourceCard> offeringCards, List<ResourceCard> requestingCards) {
		super(offeringPlayer, offeringCards, requestingCards);

	}

    @Override
    public void accept()
    {
        // TODO Trade: accept
        
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
