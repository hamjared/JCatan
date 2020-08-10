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

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(10), board.board.getNodeList().get(14));
		assertEquals(1, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(14), board.board.getNodeList().get(19));

		assertEquals(2, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(14), board.board.getNodeList().get(9));

		assertEquals(2, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(24), board.board.getNodeList().get(19));

		assertEquals(3, player.calcLongestRoad());

	}

	public void test2() throws InsufficientResourceCardException {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(10), board.board.getNodeList().get(14));

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(14), board.board.getNodeList().get(19));

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(14), board.board.getNodeList().get(9));

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(24), board.board.getNodeList().get(19));

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(24), board.board.getNodeList().get(30));

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(24), board.board.getNodeList().get(18));

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(19), board.board.getNodeList().get(25));

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(22), board.board.getNodeList().get(17));

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(23), board.board.getNodeList().get(17));

		assertEquals(4, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(23), board.board.getNodeList().get(18));

		int lr = player.calcLongestRoad();
		assertEquals(7, lr);
	}

	public void test3() throws InsufficientResourceCardException {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(35), board.board.getNodeList().get(29));

		assertEquals(1, player.calcLongestRoad());

	}

	public void test5() throws InsufficientResourceCardException {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(10), board.board.getNodeList().get(14));

		assertEquals(1, player.calcLongestRoad());

		player.buildRoad(GamePhase.SETUP, board.board.getNodeList().get(14), board.board.getNodeList().get(19));

		assertEquals(2, player.calcLongestRoad());

	}

}
