package com.JCatan;

public class HumanPlayer extends Player
{

    HumanPlayer(String name)
    {
        super(name);

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
        diceRoll = new Dice().rollDice();
        return diceRoll;
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

}
