package com.JCatan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Player
{
    List<ResourceCard> resources;
    List<DevelopmentCard> devCards;
    List<Building> buildings;
    List<Road> playedRoads;
    int victoryPoints;
    int diceRoll;
    boolean hasLongestRoad;
    boolean hasLargestArmy;
    String name;

    Player(String name)
    {
        resources = new ArrayList<ResourceCard>();
        devCards = new ArrayList<DevelopmentCard>();
        victoryPoints = 0;
        buildings = new ArrayList<Building>();
        playedRoads = new ArrayList<>();
        hasLongestRoad = false;
        hasLargestArmy = false;
        this.name = name;
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
    public abstract void buildSettlement()
            throws InsufficientResourceCardException;

    /**
     * @throws InsufficientResourceCardException
     */
    public abstract void buyDevelopmentCard()
            throws InsufficientResourceCardException;

    /**
     * 
     */
    public int calcVictoryPoints()
    {
        victoryPoints = 0;
        victoryPoints += buildings.stream()
                .filter(b -> b.hasBeenPlayed())
                .reduce(0, (subtotal, b) -> subtotal + b.getVictoryPoints(),
                        Integer::sum);

        victoryPoints += devCards.stream().reduce(0,
                (subtotal, dc) -> subtotal + dc.getVictoryPoints(),
                Integer::sum);
        
        if(hasLongestRoad) {
            victoryPoints += 2;
        }
        
        if(hasLargestArmy) {
            victoryPoints += 2;
        }

        return victoryPoints;

    }
    
    public int getNumberOfKnightsPlayed() {
    	int numKnights = 0;
    	for (DevelopmentCard devCard : devCards) {
    		if(devCard instanceof KnightDevelopmentCard && devCard.isHasBeenPlayed()) {
    			numKnights ++;
    		}
    	}
    	
    	return numKnights;
    }

    public void giveBuilding(Building building)
    {
        this.buildings.add(building);
    }

    /**
     * 
     */
    public abstract void endTurn();

    /**
     * @return
     */
    public int getDiceRoll()
    {
        return diceRoll;
    }

    /**
     * @param card
     * @throws InvalidDevCardUseException
     */
    public abstract void playDevelopmentCard(DevelopmentCard card)
            throws InvalidDevCardUseException;

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

    public List<DevelopmentCard> getDevCards()
    {
        return devCards;
    }

    /**
     *
     */
    @Override
    public String toString()
    {
        return name + ": " + diceRoll;
    }

    /**
     * 
     */
    public abstract void tradePhase();

    public boolean isHasLongestRoad() {
		return hasLongestRoad;
	}

	public boolean isHasLargestArmy() {
		return hasLargestArmy;
	}

	public void giveDevelopmentCard(DevelopmentCard devCard)
    {
        devCards.add(devCard);
    }
    
    public int calcLongestRoad() {
    	return new LongestRoadCalculator(playedRoads).calcLongestRoad();
    }
    
    public void addRoadToRoadPlayedList(Road road) {
    	playedRoads.add(road);
    }

	public void setHasLongestRoad(boolean hasLongestRoad) {
		this.hasLongestRoad = hasLongestRoad;
	}

	public void setHasLargestArmy(boolean hasLargestArmy) {
		this.hasLargestArmy = hasLargestArmy;
	}

    

}
