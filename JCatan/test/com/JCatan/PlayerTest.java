package com.JCatan;

import java.util.ArrayList;
import java.util.List;

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
    

    
    public void testBuyDevelopmentCard() throws InsufficientResourceCardException {
    	Player player = new HumanPlayer("Test");
    	GameController controller = new GameController(List.of(player), new TraditionalBoardFactory());
    	
    	List<ResourceCard> res = new ArrayList<>();
    	res.add(new ResourceCard(ResourceType.SHEEP));
    	res.add(new ResourceCard(ResourceType.ORE));
    	res.add(new ResourceCard(ResourceType.WHEAT));
    	player.setResources(res);
    	
    	player.buyDevelopmentCard(controller);
    	
    	assertEquals(1,player.getDevCards().size());
    	assertEquals(0, player.getResources().size());
    }

}
