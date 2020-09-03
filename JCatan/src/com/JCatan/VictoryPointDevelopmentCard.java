package com.JCatan;

public class VictoryPointDevelopmentCard extends DevelopmentCard {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;

	public VictoryPointDevelopmentCard() {
		super();
		this.setCanBePlayed(true);
	}
    
    @Override
    public int getVictoryPoints() {
        return 1;
    }
    
    public String toString() {
    	return "VictoryPoint";
    }

	@Override
	public void performAction(DevCardAction devCardAction) throws InvalidDevCardUseException {
		// TODO Auto-generated method stub
		
	}
	
	


}
