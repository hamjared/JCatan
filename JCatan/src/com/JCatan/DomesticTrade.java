package com.JCatan;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.JCatan.gui.GameGUI;

public class DomesticTrade extends Trade {
	
	//This will need to notify gamecontroller to update the panel...
	public DomesticTrade(Player offeringPlayer, Player receivingPlayer, List<ResourceCard> offeringCards, List<ResourceCard> requestingCards) {
		super(offeringPlayer, offeringCards, requestingCards);
		this.receivingPlayer = receivingPlayer;
	}
	
    @Override
    public void accept()
    {
    	try {
	    	validateTrade();
	        offeringPlayer.receiveTrade(this);
	        receivingPlayer.receiveTrade(this);
	        GameGUI.controller.endTrade();
    	} catch(InvalidTradeException ex) {
    		ex.printStackTrace();
    	}
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

    private boolean checkResources(Map<ResourceType, Integer> tradingCards, Map<ResourceType, Integer> actualResource){
    	for(Map.Entry<ResourceType, Integer> e : actualResource.entrySet()) {
    		Integer val = tradingCards.getOrDefault(e.getKey(), 0);
    		if(val > e.getValue() || val < 0) {
    			return false;
    		}
    	}
    	return true;
    }
    
    @Override
    protected void validateTrade() throws InvalidTradeException
    {
    	if(offeringCards == null || requestingCards == null)
    		throw new InvalidTradeException("Null trading cards!");
    	
        //Check if the offering player has that amount of resources...
    	Map<ResourceType, Integer> offeringPlayerResources = offeringPlayer.getUniqueResourcesCount();
    	Map<ResourceType, Integer> offeringCardsMap = offeringCards.stream().collect(
														Collectors.toMap(r->r.getResourceType(),
																		 r->1, (existing, addition)-> existing + addition));
    	
    	Map<ResourceType, Integer> receivingPlayerResources = receivingPlayer.getUniqueResourcesCount();
    	Map<ResourceType, Integer> requestingCardsMap = requestingCards.stream().collect(
    													 Collectors.toMap(r->r.getResourceType(),
																		  r->1, (existing, addition)-> existing + addition));
    	
    	if(!checkResources(requestingCardsMap, receivingPlayerResources) || !checkResources(offeringCardsMap, offeringPlayerResources)) {
    		throw new InvalidTradeException("Not enough resources");
    	}
    }
}