package com.JCatan;

public class KnightDevelopmentCard extends DevelopmentCard {

    @Override
    public void performAction(DevCardAction devCardAction) throws InvalidDevCardUseException
    {
//    	int stealCardIndex = (int) Math.round(Math.random() * devCardAction.getStealFromPlayer().getResources().size());
//        ResourceCard stolenCard = devCardAction.getStealFromPlayer().getResources().get(stealCardIndex);
//        devCardAction.getStealFromPlayer().getResources().remove(stealCardIndex);
//        
//        devCardAction.getCurPlayer().getResources().add(stolenCard);
        
    }
    
    public String toString() {
    	return "Knight";
    }



}
