package com.JCatan;

import java.io.Serializable;

public class Dice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean wasDoubles;
	private int die1;
	private int die2;

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

		return die1 + die2;
	}

	public int getDie1() {
		return die1;
	}

	public int getDie2() {
		return die2;
	}

}
