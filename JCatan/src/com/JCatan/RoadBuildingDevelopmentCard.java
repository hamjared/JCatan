package com.JCatan;

public class RoadBuildingDevelopmentCard extends DevelopmentCard {


    
    public String toString() {
    	return "Road Builder";
    }

	@Override
	public void performAction(DevCardAction devCardAction) throws InvalidDevCardUseException {
		Player curPlayer = devCardAction.getCurPlayer();
		Bank bank = devCardAction.getBank();
		curPlayer.setRoadBuilderRoads(2);
		
		
		
	}



}
