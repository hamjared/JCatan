package com.JCatan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean wasDoubles;
	private int die1;
	private int die2;
	private Map<Integer, Integer> diceRollHistory;
	private static Dice dice = new Dice();
	
	public static Dice getInstance() {
		return dice;
	}
	
	private Dice() {
		diceRollHistory = new HashMap<>();
		for(int i = 2; i < 13; i++) {
			diceRollHistory.put(i, 0); //initialize all possible diceRolls to 0
		}
	}

	/**
	 * @return
	 */
	public int rollDice() {
		die1 = (int) Math.round(Math.random() * 5 + 1);
		die2 = (int) Math.round(Math.random() * 5 + 1);

		if (die1 == die2) {
			wasDoubles = true;
		} else {
			wasDoubles = false;
		}

		diceRollHistory.put(die1+die2, diceRollHistory.get(die1+die2) + 1);
		return die1 + die2;
	}

	public int getDie1() {
		return die1;
	}

	public int getDie2() {
		return die2;
	}
	
	public Map <Integer, Integer> getDiceRollHistory() {
		return diceRollHistory;
	}

}
