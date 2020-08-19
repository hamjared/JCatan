package com.JCatan;

public class YearOfPlentyDevelopmentCard extends DevelopmentCard {


    
    public String toString() {
    	return "Year of Plenty";
    }

	@Override
	public void performAction(DevCardAction devCardAction) throws InvalidDevCardUseException {
		Bank bank = devCardAction.getBank();
		ResourceType rt = devCardAction.getStealResourceType1();
		ResourceType rt2 = devCardAction.getStealResourceType2();
		Player curPlayer = devCardAction.getCurPlayer();
		
		try {
			curPlayer.getResources().add(bank.takeResourceCard(rt));
			curPlayer.getResources().add(bank.takeResourceCard(rt2));
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



}
