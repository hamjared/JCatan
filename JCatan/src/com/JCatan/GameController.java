package com.JCatan;

import java.util.Comparator;
import java.util.List;

public class GameController {
	public static void main(String [] args) {
		
		
		
		
	}
	List<Player> players;
	int playerTurnIndex;


	
	Board board;
	
	/**
	 * @param players
	 * @param bf
	 */
	public GameController(List<Player> players, BoardFactory bf) {
		this.players = players;
		this.board = new Board(bf);
		playerTurnIndex = 0;
		
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void diceRollPhase() {
		for(Player player : players) {
			player.rollDice();
		}
		
		players.sort(new Comparator() {

			@SuppressWarnings("null")
			@Override
			public int compare(Object o1, Object o2) {
				Player p1 = null;
				Player p2 = null;
				if (o1 instanceof Player && o2 instanceof Player) {
					p1 = (Player) o1;
					p2 = (Player) o2;
					
					
				}
				if(p1 == null) {
					return -1;
				}
				return p2.getDiceRoll() - p1.getDiceRoll();
			}
			
		});
		
	}
	
	private void gamePhase() {
		// TODO Auto-generated method stub
		
	}
	
	private void setupPhase() {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * 
	 */
	public void startGame() {
		
		diceRollPhase();
		
		setupPhase();
		
		gamePhase();
		
	}
}
