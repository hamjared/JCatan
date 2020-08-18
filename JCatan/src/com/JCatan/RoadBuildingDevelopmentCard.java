package com.JCatan;

public class RoadBuildingDevelopmentCard extends DevelopmentCard {


    
    public String toString() {
    	return "Road Builder";
    }

	@Override
	public void performAction(DevCardAction devCardAction) throws InvalidDevCardUseException {
		Player curPlayer = devCardAction.getCurPlayer();
		Bank bank = devCardAction.getBank();
		
		
		try {
			curPlayer.getResources().add(bank.takeResourceCard(ResourceType.BRICK));
			curPlayer.getResources().add(bank.takeResourceCard(ResourceType.BRICK));
			curPlayer.getResources().add(bank.takeResourceCard(ResourceType.WOOD));
			curPlayer.getResources().add(bank.takeResourceCard(ResourceType.WOOD));
		} catch (InsufficientResourceCardException e) {
			e.printStackTrace();
		}
		
		
	}



}
