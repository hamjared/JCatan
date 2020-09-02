package com.JCatan;

public class MonopolyDevelopmentCard extends DevelopmentCard {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void performAction(DevCardAction devCardAction) throws InvalidDevCardUseException
    {
        ResourceType rt = devCardAction.getStealResourceType();
        Player stealFromPlayer = devCardAction.getStealFromPlayer();
        Player curPlayer = devCardAction.getCurPlayer();
        
        
        int numCards = (int) stealFromPlayer.getResources().stream().filter(rc -> rc.getResourceType() == rt).count();
        for(int i = 0; i < numCards; i++) {
        	curPlayer.getResources().add(new ResourceCard(rt));
        }
        
        stealFromPlayer.getResources().removeIf(rc -> rc.getResourceType() == rt);
        
    }
    
    public String toString() {
    	return "Monopoly";
    }



}
