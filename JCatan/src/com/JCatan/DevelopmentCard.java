package com.JCatan;

public abstract class DevelopmentCard {
	private boolean canBePlayed;
	
	/**
	 * 
	 */
	public DevelopmentCard() {
		canBePlayed = false;
	}
	
	/**
	 * @throws InvalidDevCardUseException
	 */
	public abstract void performAction() throws InvalidDevCardUseException;
}
