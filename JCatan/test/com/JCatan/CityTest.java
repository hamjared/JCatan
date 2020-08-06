package com.JCatan;

import junit.framework.TestCase;

public class CityTest extends TestCase
{

    protected void setUp() throws Exception
    {
        super.setUp();
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    public void testGetCost()
    {
        Building building = new City();

        assertEquals(Integer.valueOf(2),
                building.getCost().get(ResourceType.WHEAT));
        assertEquals(null,
                building.getCost().get(ResourceType.SHEEP));
        assertEquals(Integer.valueOf(3), building.getCost().get(ResourceType.ORE));
        assertEquals(null,
                building.getCost().get(ResourceType.BRICK));
        assertEquals(null,
                building.getCost().get(ResourceType.WOOD));
    }

}
