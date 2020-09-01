package com.JCatan;

import java.util.List;

public class GenericTrade extends Trade {

	public GenericTrade(Player offeringPlayer, List<ResourceCard> offeringCards, List<ResourceCard> requestingCards) {
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
    public void validateTrade() throws InvalidTradeException
    {
        // TODO Trade: validateTrade
        
    }



}
