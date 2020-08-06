package com.JCatan;

public class Dice {
	
	private boolean wasDoubles;
	
	/**
	 * @return
	 */
	public int rollDice() {
		int die1 = (int) Math.round(Math.random()*5 + 1);
		int die2 = (int) Math.round(Math.random()*5 + 1);
		

		if(die1 == die2) {
			wasDoubles = true;
		}
		else {
			wasDoubles = false;
		}
		
		return die1 + die2;
	}

}
