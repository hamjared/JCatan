package com.JCatan;

import junit.framework.TestCase;

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

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(10), board.board.getNodeList().get(14), null);
		assertEquals(1, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(14), board.board.getNodeList().get(19), null);

		assertEquals(2, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(14), board.board.getNodeList().get(9), null);

		assertEquals(2, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(24), board.board.getNodeList().get(19), null);

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

	public void test6() throws InsufficientResourceCardException {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");
		GameController controller = new GameController(null, new TraditionalBoardFactory());

		player.buildSettlement(GamePhase.SETUP, board.board.getNodeList().get(3), controller);
		player.buildSettlement(GamePhase.SETUP, board.board.getNodeList().get(17), controller);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(3), board.board.getNodeList().get(0), null);

		assertEquals(1, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(3), board.board.getNodeList().get(7), null);

		assertEquals(2, player.calcLongestRoad());
		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(7), board.board.getNodeList().get(12), null);

		assertEquals(3, player.calcLongestRoad());
		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(17), board.board.getNodeList().get(12), null);

		assertEquals(4, player.calcLongestRoad());
		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(17), board.board.getNodeList().get(22), null);

		assertEquals(5, player.calcLongestRoad());
		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(22), board.board.getNodeList().get(16), null);

		assertEquals(6, player.calcLongestRoad());
		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(4), board.board.getNodeList().get(0), null);

		assertEquals(7, player.calcLongestRoad());
	}

	public void test7() throws InsufficientResourceCardException {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");
		GameController controller = new GameController(null, new TraditionalBoardFactory());

		player.buildSettlement(GamePhase.SETUP, board.board.getNodeList().get(18), controller);
		player.buildSettlement(GamePhase.SETUP, board.board.getNodeList().get(9), controller);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(17), board.board.getNodeList().get(12), null);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(9), board.board.getNodeList().get(13), null);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(23), board.board.getNodeList().get(18), null);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(13), board.board.getNodeList().get(18), null);

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(23), board.board.getNodeList().get(17), null);
		assertEquals(5, player.calcLongestRoad());

	}

}
