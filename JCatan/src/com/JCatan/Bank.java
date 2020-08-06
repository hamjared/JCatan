package com.JCatan;

import java.util.ArrayList;
import java.util.List;

public class Bank extends Player
{

    final List<ResourceCard> brick = new ArrayList<>(19);
    final List<ResourceCard> wood = new ArrayList<>(19);
    final List<ResourceCard> wheat = new ArrayList<>(19);
    final List<ResourceCard> ore = new ArrayList<>(19);
    final List<ResourceCard> sheep = new ArrayList<>(19);
    final List<DevelopmentCard> devCards;

    Bank()
    {
        super("Bank");
        devCards = DevCardFactory.makeDevCards();

    }

    @Override
    public void buildCity() throws InsufficientResourceCardException
    {
        // TODO Player: buildCity

    }

    @Override
    public void buildPhase()
    {
        // TODO Player: buildPhase

    }

    @Override
    public void buildRoad() throws InsufficientResourceCardException
    {
        // TODO Player: buildRoad

    }

    @Override
    public void buildSettlement() throws InsufficientResourceCardException
    {
        // TODO Player: buildSettlement

    }

    @Override
    public void buyDevelopmentCard() throws InsufficientResourceCardException
    {
        // TODO Player: buyDevelopmentCard

    }

    @Override
    public void endTurn()
    {
        // TODO Player: endTurn

    }

    public void getResourceCard(Player player, ResourceType resource,
            int amount) throws InsufficientResourceCardException
    {

    }

    @Override
    public void playDevelopmentCard(DevelopmentCard card)
            throws InvalidDevCardUseException
    {
        // TODO Player: playDevelopmentCard

    }

    @Override
    public void proposeTrade(Trade trade)
    {
        // TODO Player: proposeTrade

    }

    @Override
    public void receiveTrade(Trade trade)
    {
        // TODO Player: receiveTrade

    }

    @Override
    public int rollDice()
    {
        // TODO Player: rollDice
        return 0;
    }

    @Override
    public void setup()
    {
        // TODO Player: setup

    }

    @Override
    public void sevenRolled(Player activePlayer)
    {
        // TODO Player: sevenRolled

    }

    @Override
    public void tradePhase()
    {
        // TODO Player: tradePhase

    }

    @Override
    public int calcVictoryPoints()
    {
        // TODO Player: calcVictoryPoints
        return 0;
    }

}
