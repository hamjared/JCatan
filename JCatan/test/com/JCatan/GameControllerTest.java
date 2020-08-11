package com.JCatan;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class GameControllerTest extends TestCase {
	
	public void testDiceRollPhase() {
		Player player = new HumanPlayer("Jim");
		Player player2 = new HumanPlayer("Bob");
		Player player3 = new HumanPlayer("Tom");
		
		List<Player> players = new ArrayList<>();
		players.add(player);
		players.add(player2);
		players.add(player3);
		
		GameController controller = new GameController(players, new TraditionalBoardFactory());
		

		controller.startGame();
		

		
		
		
	}

}
