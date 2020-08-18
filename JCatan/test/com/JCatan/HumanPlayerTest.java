package com.JCatan;

import junit.framework.TestCase;

public class HumanPlayerTest extends TestCase
{

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }
    
    public void testPlayDevelopmentCard() throws InvalidDevCardUseException {
        Player hp = new HumanPlayer("Test");
        DevelopmentCard devCard = new RoadBuildingDevelopmentCard();
        DevelopmentCard doesntHave = new YearOfPlentyDevelopmentCard();
        hp.giveDevelopmentCard(devCard);
        assertEquals(1, hp.getDevCards().size());
        boolean threwException = false;
        try {
            hp.playDevelopmentCard(doesntHave, null);
            
        }catch(InvalidDevCardUseException e) {
            threwException = true;
        }
        assertTrue("Should have thrown exception since player does not have the dev card", threwException);
        hp.playDevelopmentCard(devCard, null);
        assertEquals(0, hp.getDevCards().size());
        
        
    }
    

}
