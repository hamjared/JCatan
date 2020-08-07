package com.JCatan;

import java.util.ArrayList;
import java.util.List;

public abstract class Player
{
    List<ResourceCard> resources;
    List<DevelopmentCard> devCards;
    List<Building> buildings;
    int victoryPoints;
    int diceRoll;
    String name;

    Player(String name)
    {
        resources = new ArrayList<ResourceCard>();
        devCards = new ArrayList<DevelopmentCard>();
        victoryPoints = 0;
        buildings = new ArrayList<Building>();
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

        return victoryPoints;

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

    public void giveDevelopmentCard(DevelopmentCard devCard)
    {
        devCards.add(devCard);
    }

}
