package com.JCatan;

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.List;


public class LongestRoadCalculatorTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void test() throws InsufficientResourceCardException {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");
		List<Player> playerList = new ArrayList<>();
		playerList.add(player);
		GameController controller = new GameController(playerList, new TraditionalBoardFactory());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(10), board.board.getNodeList().get(14), controller);
		assertEquals(1, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(14), board.board.getNodeList().get(19), controller);

		assertEquals(2, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(14), board.board.getNodeList().get(9), controller);

		assertEquals(2, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(24), board.board.getNodeList().get(19), controller);

		assertEquals(3, player.calcLongestRoad());

	}

	public void test2() throws InsufficientResourceCardException {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(10), board.board.getNodeList().get(14), null);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(14), board.board.getNodeList().get(19), null);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(14), board.board.getNodeList().get(9), null);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(24), board.board.getNodeList().get(19), null);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(24), board.board.getNodeList().get(30), null);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(24), board.board.getNodeList().get(18), null);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(19), board.board.getNodeList().get(25), null);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(22), board.board.getNodeList().get(17), null);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(23), board.board.getNodeList().get(17), null);

		assertEquals(4, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(23), board.board.getNodeList().get(18), null);

		int lr = player.calcLongestRoad();
		assertEquals(7, lr);
	}

	public void test3() throws InsufficientResourceCardException {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(35), board.board.getNodeList().get(29), null);

		assertEquals(1, player.calcLongestRoad());

	}

	public void test5() throws InsufficientResourceCardException {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(10), board.board.getNodeList().get(14), null);

		assertEquals(1, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(14), board.board.getNodeList().get(19), null);

		assertEquals(2, player.calcLongestRoad());

	}

}
