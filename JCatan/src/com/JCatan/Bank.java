package com.JCatan;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Bank 
{

    public static final int NUM_OF_EACH_RESOURCE_CARD = 19;
    final Stack<ResourceCard> brick = new Stack<ResourceCard>();
    final Stack<ResourceCard> wood = new Stack<>();
    final Stack<ResourceCard> wheat = new Stack<>();
    final Stack<ResourceCard> ore = new Stack<>();
    final Stack<ResourceCard> sheep = new Stack<>();
    private Map<ResourceType, Stack<ResourceCard>> resourceTypeToResourceCards;
    final Stack<DevelopmentCard> devCards;

    Bank() 
    {
        devCards = DevCardFactory.makeDevCards();
        resourceTypeToResourceCards = new HashMap<>();
        for (int i = 0 ; i < NUM_OF_EACH_RESOURCE_CARD; i++) {
            brick.push(new ResourceCard(ResourceType.BRICK));
            wood.push(new ResourceCard(ResourceType.WOOD));
            wheat.push(new ResourceCard(ResourceType.WHEAT));
            ore.push(new ResourceCard(ResourceType.ORE));
            sheep.push(new ResourceCard(ResourceType.SHEEP));
        }
        
        resourceTypeToResourceCards.put(ResourceType.BRICK, brick);
        resourceTypeToResourceCards.put(ResourceType.WOOD, wood);
        resourceTypeToResourceCards.put(ResourceType.WHEAT, wheat);
        resourceTypeToResourceCards.put(ResourceType.ORE, ore);
        resourceTypeToResourceCards.put(ResourceType.SHEEP, sheep);
    }
    
    public Map<ResourceType, Stack<ResourceCard>> getResourceMap(){
    	return resourceTypeToResourceCards;
    }
    
    public void receiveTrade(Trade trade) {
    	List<ResourceCard> requesting = trade.getRequestingCards();
		List<ResourceCard> playerOffering = trade.getOfferingCards();
		
		for(ResourceCard card : requesting) {
			resourceTypeToResourceCards.get(card.resourceType).removeIf(c -> c.resourceType == card.resourceType);
		}
		
		for(ResourceCard card : playerOffering) {
			giveResourceCard(card);
		}
    }
    
    public ResourceCard takeResourceCard(ResourceType resourceType) throws InsufficientResourceCardException{
        try {
            resourceTypeToResourceCards.get(resourceType).peek();
        } catch(EmptyStackException e) {
            throw new InsufficientResourceCardException();
        }
        return resourceTypeToResourceCards.get(resourceType).pop();
    }
    
    public void giveResourceCard(ResourceCard resourceCard) {
        resourceTypeToResourceCards.get(resourceCard.getResourceType()).push(resourceCard);
    }
    
    public int getNumberOfResourceCardsRemaining(ResourceType rt) {
        return resourceTypeToResourceCards.get(rt).size();
    }
    
    public int getNumberOfDevelopmentCards() {
        return devCards.size();
    }
    
    public DevelopmentCard takeDevelopmentCard() throws OutOfDevelopmentCardsException{
        try {
            devCards.peek();
        } catch(EmptyStackException e) {
            throw new OutOfDevelopmentCardsException();
        }
        return devCards.pop();
    }
    
    public Map<ResourceType, Integer> getDevCardCost(){
    	return devCards.peek().getCost();
    }
}