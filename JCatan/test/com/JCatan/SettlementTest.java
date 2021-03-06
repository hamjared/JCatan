package com.JCatan;

import junit.framework.TestCase;

public class SettlementTest extends TestCase
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
        Building building = new Settlement(new HumanPlayer("Test"), null);
        
        assertEquals(Integer.valueOf(1), building.getCost().get(ResourceType.WHEAT));
        assertEquals(Integer.valueOf(1), building.getCost().get(ResourceType.SHEEP));
        assertEquals(null, building.getCost().get(ResourceType.ORE));
        assertEquals(Integer.valueOf(1), building.getCost().get(ResourceType.BRICK));
        assertEquals(Integer.valueOf(1), building.getCost().get(ResourceType.WOOD));
    }

}
