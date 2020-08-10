package com.JCatan;

import junit.framework.TestCase;

public class LongestRoadCalculatorTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void test() {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");

		Road road10_14 = new Road(board.board.getNodeList().get(10), board.board.getNodeList().get(14), player);
		board.board.getNodeList().get(14).addRoad(road10_14);
		board.board.getNodeList().get(10).addRoad(road10_14);
		player.addRoadToRoadPlayedList(road10_14);

		assertEquals(1, player.calcLongestRoad());

		Road road14_19 = new Road(board.board.getNodeList().get(14), board.board.getNodeList().get(19), player);
		board.board.getNodeList().get(14).addRoad(road14_19);
		board.board.getNodeList().get(19).addRoad(road14_19);
		player.addRoadToRoadPlayedList(road14_19);

		assertEquals(2, player.calcLongestRoad());

		Road road14_9 = new Road(board.board.getNodeList().get(14), board.board.getNodeList().get(9), player);
		board.board.getNodeList().get(14).addRoad(road14_9);
		board.board.getNodeList().get(19).addRoad(road14_9);
		player.addRoadToRoadPlayedList(road14_9);

		assertEquals(2, player.calcLongestRoad());

		Road road19_24 = new Road(board.board.getNodeList().get(24), board.board.getNodeList().get(19), player);
		board.board.getNodeList().get(19).addRoad(road19_24);
		board.board.getNodeList().get(24).addRoad(road19_24);
		player.addRoadToRoadPlayedList(road19_24);

		assertEquals(3, player.calcLongestRoad());



	}
	
	public void test2() {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");

		Road road10_14 = new Road(board.board.getNodeList().get(10), board.board.getNodeList().get(14), player);
		board.board.getNodeList().get(14).addRoad(road10_14);
		board.board.getNodeList().get(10).addRoad(road10_14);
		player.addRoadToRoadPlayedList(road10_14);

		Road road14_19 = new Road(board.board.getNodeList().get(14), board.board.getNodeList().get(19), player);
		board.board.getNodeList().get(14).addRoad(road14_19);
		board.board.getNodeList().get(19).addRoad(road14_19);
		player.addRoadToRoadPlayedList(road14_19);

		Road road14_9 = new Road(board.board.getNodeList().get(14), board.board.getNodeList().get(9), player);
		board.board.getNodeList().get(14).addRoad(road14_9);
		board.board.getNodeList().get(19).addRoad(road14_9);
		player.addRoadToRoadPlayedList(road14_9);

		Road road19_24 = new Road(board.board.getNodeList().get(24), board.board.getNodeList().get(19), player);
		board.board.getNodeList().get(19).addRoad(road19_24);
		board.board.getNodeList().get(24).addRoad(road19_24);
		player.addRoadToRoadPlayedList(road19_24);

		Road road24_30 = new Road(board.board.getNodeList().get(24), board.board.getNodeList().get(30), player);
		board.board.getNodeList().get(30).addRoad(road24_30);
		board.board.getNodeList().get(24).addRoad(road24_30);
		player.addRoadToRoadPlayedList(road24_30);

		Road road18_24 = new Road(board.board.getNodeList().get(24), board.board.getNodeList().get(18), player);
		board.board.getNodeList().get(18).addRoad(road18_24);
		board.board.getNodeList().get(24).addRoad(road18_24);
		player.addRoadToRoadPlayedList(road18_24);
		
		Road road19_25 = new Road(board.board.getNodeList().get(19), board.board.getNodeList().get(25), player);
		board.board.getNodeList().get(19).addRoad(road19_25);
		board.board.getNodeList().get(25).addRoad(road19_25);
		player.addRoadToRoadPlayedList(road19_25);


		Road road17_22 = new Road(board.board.getNodeList().get(22), board.board.getNodeList().get(17), player);
		board.board.getNodeList().get(22).addRoad(road17_22);
		board.board.getNodeList().get(17).addRoad(road17_22);
		player.addRoadToRoadPlayedList(road17_22);
		
		Road road17_23 = new Road(board.board.getNodeList().get(23), board.board.getNodeList().get(17), player);
		board.board.getNodeList().get(23).addRoad(road17_23);
		board.board.getNodeList().get(17).addRoad(road17_23);
		player.addRoadToRoadPlayedList(road17_23);
		
		assertEquals(4, player.calcLongestRoad());
		Road road18_23 = new Road(board.board.getNodeList().get(23), board.board.getNodeList().get(18), player);
		board.board.getNodeList().get(23).addRoad(road18_23);
		board.board.getNodeList().get(18).addRoad(road18_23);
		player.addRoadToRoadPlayedList(road18_23);

		int lr = player.calcLongestRoad();
		assertEquals(7, lr);
	}
	
	public void test3() {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");

		Road road29_35 = new Road(board.board.getNodeList().get(35), board.board.getNodeList().get(29), player);
		board.board.getNodeList().get(35).addRoad(road29_35);
		board.board.getNodeList().get(29).addRoad(road29_35);
		player.addRoadToRoadPlayedList(road29_35);

		assertEquals(1, player.calcLongestRoad());





	}
	
	public void test4() {
		Board board = new Board(new TraditionalBoardFactory());
		Player player = new HumanPlayer("Joe");

		Road road10_14 = new Road(board.board.getNodeList().get(10), board.board.getNodeList().get(14), player);
		board.board.getNodeList().get(14).addRoad(road10_14);
		board.board.getNodeList().get(10).addRoad(road10_14);
		player.addRoadToRoadPlayedList(road10_14);

		assertEquals(1, player.calcLongestRoad());

		Road road14_19 = new Road(board.board.getNodeList().get(14), board.board.getNodeList().get(19), player);
		board.board.getNodeList().get(14).addRoad(road14_19);
		board.board.getNodeList().get(19).addRoad(road14_19);
		player.addRoadToRoadPlayedList(road14_19);

		assertEquals(2, player.calcLongestRoad());




	}

}
