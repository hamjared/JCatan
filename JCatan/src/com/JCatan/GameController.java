package com.JCatan;

import java.util.Comparator;
import java.util.List;

public class GameController {
	private static final int POINTS_TO_WIN = 10;

	List<Player> players;
	int playerTurnIndex = 0;
	int gameWinnerIndex;
	Board board;
	Player curPlayer;
	boolean gameEnded = false;
	GamePhase gamePhase;
	Consumer action;

	public List<Player> getPlayers(){
		return players;
	}
	Bank bank;
	Chat chat;

	public Board getBoard() {
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
		bank = new Bank();
		this.chat = new Chat();

	}
	
	public Bank getBank() {
		return bank;
	}
    
	public Chat getChat() {
		return chat;
	}

	public Player getCurPlayer() {
		return curPlayer;
	}

	public GamePhase getGamePhase() {
		return gamePhase;
	}
	
	public Player getPlayer(int playerNumber) {
		return players.get(playerNumber);
	}

	public void setGamePhase(GamePhase gamePhase) {
		this.gamePhase = gamePhase;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void diceRollPhase() {
		for (Player player : players) {
			player.rollDice();
		}

		players.sort(new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				Player p1 = null; 
				Player p2 = null;
				if (o1 instanceof Player && o2 instanceof Player) {
					p1 = (Player) o1;
					p2 = (Player) o2;

				}
				if (p1 == null) {
					return -1;
				}
				return p2.getDiceRoll() - p1.getDiceRoll();
			}

		});

	}

	public void setAction(Consumer c) {
		action = c;
	}
	
	public void endTrade() {
		//Could display message player accepted the trade...
		action.accept(this);
	}
	
	private void setupPhase() {
		
		chat.addToChat("Beginning setup phase");

		int playerNum = 0;
		Node node1 = null;
		Node node2 = null;
		for (Player player : players) {

			switch (playerNum) {
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
			player.setup(node1, node2, this);
		}
		playerNum = 3;
		for (int i = players.size() - 1; i >= 0; i--) {
			switch (playerNum) {
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
			players.get(i).setup(node1, node2, this);
		}
		
		gamePhase = GamePhase.GAMEROLL;

	}
	
	public void gamePhaseRoll() {
			Player curPlayer = players.get(playerTurnIndex);
			chat.addToChat(curPlayer.getName() + "'s turn");
			

			int diceRoll = curPlayer.rollDice();

			if (diceRoll == 7) {
				players.forEach(p -> p.sevenRolled(curPlayer));
				boolean cardStolen = false;
//				while (cardStolen == false) {
//					cardStolen = curPlayer.sevenRolledSteal(players.get(playerTurnIndex + 1)); // Pass in a player to
//																								// steal from here right
//																								// now it is set to
//																								// curPlayer + 1
//				}
			}

			board.dishOutResources(this, diceRoll);
	}
    
	
	public void initiateTrade(Trade trade) {
		if(trade instanceof DomesticTrade) {
			DomesticTrade dt = (DomesticTrade)trade;
			dt.accept();
		}
	}
	
	public void gamePhaseTrade() {
		curPlayer = players.get(playerTurnIndex);
	}
	
	public void gamePhaseBuild() {
		curPlayer = players.get(playerTurnIndex);
		//board.getBoard().getNodes().get()
		curPlayer.buildPhase(board.getTiles().get(12).getNodes().get(0),
				board.getTiles().get(12).getNodes().get(1), this);
	}
	
	public void gamePhaseEnd() {
		if (curPlayer.calcVictoryPoints() > POINTS_TO_WIN) {
			gameWinnerIndex = playerTurnIndex;
			gameEnded = true;
		}

		playerTurnIndex++;

		if (playerTurnIndex >= players.size()) {
			playerTurnIndex = 0;
		}
		
		
		
		
	}
  
	private void setLargestArmy() {
		int largestArmy = 0;
		for(Player player: players) {
			player.setHasLargestArmy(false);
			int playersArmy = player.getNumberOfKnightsPlayed();
			if (playersArmy > largestArmy && playersArmy >= 5) {
				largestArmy = playersArmy;
				player.setHasLargestArmy(true);
			}
		}
	}
	
	private void setLongestRoad() {
		int longestRoad = 0;
		for(Player player: players) {
			player.setHasLongestRoad(false);
			int playersLongestRoad = player.calcLongestRoad();
			if (playersLongestRoad > longestRoad && playersLongestRoad >= 5) {
				longestRoad = playersLongestRoad;
				player.setHasLongestRoad(true);
			}
		}
		
	}

	public void startGame() {
		chat.addToChat("Game started");
		diceRollPhase();

		setupPhase();

		

	}
}