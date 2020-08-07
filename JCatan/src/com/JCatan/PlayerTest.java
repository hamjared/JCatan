package com.JCatan;

import junit.framework.TestCase;

public class PlayerTest extends TestCase
{

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }
    
    public void testCalcVictoryPoints() {
        Player player = new HumanPlayer("Test");
        Building city = new City(player);
        Building settlement = new Settlement(player); 
        city.setHasBeenPlayed(true);
        player.giveDevelopmentCard(new VictoryPointDevelopmentCard());
        player.giveDevelopmentCard(new KnightDevelopmentCard());
        
        assertEquals(3, player.calcVictoryPoints());
        settlement.setHasBeenPlayed(true);
        assertEquals(4, player.calcVictoryPoints());
    }

}
