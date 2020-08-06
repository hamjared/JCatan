package com.JCatan;

import junit.framework.TestCase;

public class RoadTest extends TestCase
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
        Road road = new Road(null, null, null);
        
        assertEquals(Integer.valueOf(1), road.getCost().get(ResourceType.BRICK));
        assertEquals(Integer.valueOf(1), road.getCost().get(ResourceType.WOOD));
        assertEquals(null, road.getCost().get(ResourceType.ORE));
        assertEquals(null, road.getCost().get(ResourceType.WHEAT));
        assertEquals(null, road.getCost().get(ResourceType.SHEEP));
    }

}
