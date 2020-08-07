package com.JCatan;

import junit.framework.TestCase;

public class DevelopmentCardTest extends TestCase
{

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    
    public void testGetCost() {
        DevelopmentCard devCard = new RoadBuildingDevelopmentCard();
        
        assertEquals(Integer.valueOf(1), devCard.getCost().get(ResourceType.WHEAT));
        assertEquals(Integer.valueOf(1), devCard.getCost().get(ResourceType.SHEEP));
        assertEquals(Integer.valueOf(1), devCard.getCost().get(ResourceType.ORE));
        assertEquals(null, devCard.getCost().get(ResourceType.BRICK));
        assertEquals(null, devCard.getCost().get(ResourceType.WOOD));
    }
}
