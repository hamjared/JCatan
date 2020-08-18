package com.JCatan;

public class YearOfPlentyDevelopmentCard extends DevelopmentCard {


    
    public String toString() {
    	return "Year of Plenty";
    }

	@Override
	public void performAction(DevCardAction devCardAction) throws InvalidDevCardUseException {
		Bank bank = devCardAction.getBank();
		ResourceType rt = devCardAction.getStealResourceType();
		Player curPlayer = devCardAction.getCurPlayer();
		
		try {
			curPlayer.getResources().add(bank.takeResourceCard(rt));
			curPlayer.getResources().add(bank.takeResourceCard(rt));
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



}
