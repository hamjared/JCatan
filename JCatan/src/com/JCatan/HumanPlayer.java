package com.JCatan;

public class HumanPlayer extends Player
{
	private Object temp = new Object();
	
    public HumanPlayer(String name)
    {
        super(name);
        for(int i = 0; i < 10; i++) {
        	this.resources.add(new ResourceCard(ResourceType.BRICK));
        	this.resources.add(new ResourceCard(ResourceType.ORE));
        	this.resources.add(new ResourceCard(ResourceType.SHEEP));
        	this.resources.add(new ResourceCard(ResourceType.WHEAT));
        }
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
    synchronized(temp) {
    	boolean finishedTrade = false;
    		try {		
    			while(!finishedTrade) {
    				System.out.print("WAITING");	
    				temp.wait();
    			}
    		} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }

    @Override
    public int calcVictoryPoints()
    {
        
        //TODO update victory points
        return victoryPoints;

    }

}
