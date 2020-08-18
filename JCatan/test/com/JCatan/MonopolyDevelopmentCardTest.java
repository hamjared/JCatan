package com.JCatan;

import junit.framework.TestCase;

public class MonopolyDevelopmentCardTest extends TestCase {
	
	public void test() throws InvalidDevCardUseException {
		Player curPlayer = new HumanPlayer("Joe");
		Player stealPlayer = new HumanPlayer("Bob");
		
		for(int i = 0 ; i <4; i++) {
			stealPlayer.getResources().add(new ResourceCard(ResourceType.WHEAT));
		}
		stealPlayer.getResources().add(new ResourceCard(ResourceType.BRICK));
		
		DevelopmentCard mdc = new MonopolyDevelopmentCard();
		
		curPlayer.getDevCards().add(new MonopolyDevelopmentCard());
		
		DevCardAction dcAction = new DevCardActionBuilder().curPlayer(curPlayer)
				.stealPlayer(stealPlayer)
				.stealResourceType(ResourceType.WHEAT)
				.build();
		
		mdc.setCanBePlayed(true);
		
		curPlayer.playDevelopmentCard(mdc, dcAction);
		
		assertEquals(4, curPlayer.getResources().size());
		assertEquals(1, stealPlayer.getResources().size());
		
		
		
	}

}
