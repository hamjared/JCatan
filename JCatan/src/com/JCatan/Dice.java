package com.JCatan;

public class Dice {
	
	private boolean wasDoubles;
	private static int die1;
	private static int die2;

	/**
	 * @return
	 */
	public int rollDice() {
		die1 = (int) Math.round(Math.random()*5 + 1);
		die2 = (int) Math.round(Math.random()*5 + 1);
		
		if(die1 == die2) {
			wasDoubles = true;
		}
		else {
			wasDoubles = false;
		}
		
		return die1 + die2;
	}

	public static int getDie1() {
		return die1;
	}

	public static void setDie1(int die1) {
		Dice.die1 = die1;
	}

	public static int getDie2() {
		return die2;
	}

	public static void setDie2(int die2) {
		Dice.die2 = die2;
	}
	
	

	
}
