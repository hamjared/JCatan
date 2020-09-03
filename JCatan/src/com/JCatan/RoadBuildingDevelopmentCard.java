package com.JCatan;

public class RoadBuildingDevelopmentCard extends DevelopmentCard {


    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
