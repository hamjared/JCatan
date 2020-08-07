package com.JCatan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GameController {
	private static final int POINTS_TO_WIN = 10;


	List<Player> players;
	int playerTurnIndex;
	int gameWinnerIndex;
	Board board;
	
	public Board getBoard()
    {
        return board;
    }


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
	
	   private void setupPhase() {
		   
		   int playerNum = 0;
		   Node node1 = null;
		   Node node2 = null;
	        for(Player player: players) {
	        	switch(playerNum) {
	        		case 0:
	        			node1 = board.getTiles().get(0).getNodes().get(0);
	            		node2 = board.getTiles().get(0).getNodes().get(1);
	            		break;
	        		case 1:
	        			node1 = board.getTiles().get(1).getNodes().get(0);
	            		node2 = board.getTiles().get(1).getNodes().get(1);
	            		break;
	        		case 2:
	        			node1 = board.getTiles().get(2).getNodes().get(0);
	            		node2 = board.getTiles().get(2).getNodes().get(1);
	            		break;
	        		case 3:
	        			node1 = board.getTiles().get(3).getNodes().get(0);
	            		node2 = board.getTiles().get(3).getNodes().get(1);
	            		break;
	        	}
	        	playerNum++;
	            player.setup(node1, node2);  
	        }
	        playerNum = 3;		
	        for(int i = players.size() - 1; i >=0; i--) {
	        	switch(playerNum) {
        		case 0:
        			node1 = board.getTiles().get(7).getNodes().get(0);
            		node2 = board.getTiles().get(7).getNodes().get(1);
            		break;
        		case 1:
        			node1 = board.getTiles().get(8).getNodes().get(0);
            		node2 = board.getTiles().get(8).getNodes().get(1);
            		break;
        		case 2:
        			node1 = board.getTiles().get(9).getNodes().get(0);
            		node2 = board.getTiles().get(9).getNodes().get(1);
            		break;
        		case 3:
        			node1 = board.getTiles().get(10).getNodes().get(0);
            		node2 = board.getTiles().get(10).getNodes().get(1);
            		break;
        	}
        	playerNum--;
	        players.get(i).setup(node1, node2);
	        }
	        
	    }
	
	private void gamePhase() {
	    playerTurnIndex = 0;
		boolean gameEnded = false;
        while(!gameEnded) {
		    
            Player curPlayer = players.get(playerTurnIndex);
            
            int diceRoll = curPlayer.getDiceRoll();
            
            if(diceRoll == 7) {
                players.forEach(p -> p.sevenRolled(curPlayer));
            }
            
            board.dishOutResources(diceRoll);
            
            //curPlayer.tradePhase();
            
            List<ResourceCard> resources = new ArrayList<>();
            ResourceCard card = new ResourceCard(ResourceType.WOOD);
            ResourceCard card2 = new ResourceCard(ResourceType.BRICK);
            ResourceCard card3 = new ResourceCard(ResourceType.SHEEP);
            ResourceCard card4 = new ResourceCard(ResourceType.WHEAT);
            ResourceCard card5 = new ResourceCard(ResourceType.WOOD);
            ResourceCard card6 = new ResourceCard(ResourceType.BRICK);
            ResourceCard card7 = new ResourceCard(ResourceType.ORE);
            ResourceCard card8 = new ResourceCard(ResourceType.ORE);
            ResourceCard card9 = new ResourceCard(ResourceType.ORE);
            ResourceCard card10 = new ResourceCard(ResourceType.WHEAT);
            ResourceCard card11 = new ResourceCard(ResourceType.WHEAT);
            resources.add(card);
            resources.add(card2);
            resources.add(card3);
            resources.add(card4);
            resources.add(card5);
            resources.add(card6);
            resources.add(card7);
            resources.add(card8);
            resources.add(card9);
            resources.add(card10);
            resources.add(card11);
            
            curPlayer.setResources(resources);
            
            curPlayer.buildPhase(board.getTiles().get(12).getNodes().get(0), board.getTiles().get(12).getNodes().get(1));
            if (board.getTiles().get(12).getNodes().get(0).getRoads().isEmpty()) {
            	System.out.println("No road here");
            } else {
            	System.out.println(board.getTiles().get(12).getNodes().get(0).getRoads().get(0));
            }
            if (board.getTiles().get(12).getNodes().get(0).getBuilding() == null) {
            	System.out.println("No building here");
            } else {
            	System.out.println(board.getTiles().get(12).getNodes().get(0).getBuilding());
            }
            if(curPlayer.getResources().isEmpty()) {
            	System.out.println("The resources have been removed correctly");
            } else {
            	System.out.println("I still have resources");
            }
            
            gameEnded = true;
            
            if(curPlayer.calcVictoryPoints() > POINTS_TO_WIN) {
                gameWinnerIndex = playerTurnIndex;
                gameEnded = true;
            }
            
            playerTurnIndex ++;
            
            if(playerTurnIndex >= players.size()) {
                playerTurnIndex = 0;
            }
		}
		
	}
	

	
	
	/**
	 * 
	 */
	public void startGame() {
		
		diceRollPhase();	
		
		setupPhase();
		
		for(Tile tile: board.getTiles()) {
				System.out.print(tile.getNumber() + ": ");
				if (tile.getNodes().get(0).getBuilding() == null) {
					System.out.println("No settlement");
				} else {
					System.out.println("Settlement");
				}
		}
		
		gamePhase();
		
	}
}
