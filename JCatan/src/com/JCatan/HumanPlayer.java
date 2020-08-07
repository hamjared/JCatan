package com.JCatan;

import java.util.ArrayList;
import java.util.List;

public class HumanPlayer extends Player
{

    public HumanPlayer(String name)
    {
        super(name);

    }

    @Override
    public void buildCity(Node node) throws InsufficientResourceCardException
    {
    	if(node.getBuilding() == null) {
    		//Can't build City
    	}

    }

    @Override
    public void buildPhase()
    {
        //Check for listener about player wanting to building road/building
    	
    	buildRoad(GamePhase.GAME, Node node1, Node node2);
    	
    	buildSettlement(GamePhase.GAME, Node node1);
    	
    	buildCity()

    }

    @Override
    public void buildRoad(GamePhase phase, Node node1, Node node2) throws InsufficientResourceCardException
    {
        if(phase == GamePhase.SETUP) {
        	//Setup Phase Build
        	Road road = new Road(node1, node2, this);
        	node1.addRoad(road);
        	road = new Road(node2, node1, this);
        	node2.addRoad(road);
        } else {
        	//Game Phase Build
        	
        }

    }

    @Override
    public void buildSettlement(GamePhase phase, Node node) throws InsufficientResourceCardException
    {
    	if(phase == GamePhase.SETUP) {
    		
    		System.out.println("Im in here");
    		//SetUp Phase Build
    		Settlement settlement = new Settlement(1, this);
    		node.setBuilding(settlement);
    	} else {
    		//Game Phase Build
    	}
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
    public void setup(Node node1, Node node2)
    {
        //Player needs to put down 1 Settlement and 1 Road
    	try {
			buildSettlement(GamePhase.SETUP, node1);
		} catch (InsufficientResourceCardException e) {
			e.printStackTrace();
		}
    	try {
			buildRoad(GamePhase.SETUP, node1, node2);
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
        
        //TODO update victory points
        return victoryPoints;

    }

}
