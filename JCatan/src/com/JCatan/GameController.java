package com.JCatan;

import java.util.Comparator;
import java.util.List;

import com.JCatan.gui.GameGUI;

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
	Consumer<String> notify;
	Consumer refresh;
	Robber robber;
	boolean setUpChatCheck = false;

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
		robber = new Robber();
	}
	
	public void setNotifyPlayer(Consumer<String> c) {
		notify = c;
	}
	
	public void notifyPlayer(String message) {
		notify.accept(message);
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

	public void setCurPlayer(Player curPlayer) {
		this.curPlayer = curPlayer;
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
		
		curPlayer = players.get(playerTurnIndex);
		
		this.setGamePhase(GamePhase.SETUP);

	}
	
	public void setRefreshScreenDelegate(Consumer c) {
		this.refresh = c;
	}
	
	public void refreshScreen() {
		refresh.accept(this);
	}

	public void setAction(Consumer c) {
		action = c;
	}
	
	public void endTrade() {
		//Could display message player accepted the trade...
		action.accept(this);
	}
	
	public Robber getRobber() {
		return robber;
	}
	
	public void robberMovePhase() {
		curPlayer = players.get(playerTurnIndex);
		robber.rob(curPlayer);
	}
	
	public void gamePhaseRoll() {
			curPlayer = players.get(playerTurnIndex);
			if (setUpChatCheck == false) {
				chat.addToChat(curPlayer.getName() + "'s turn");
				setUpChatCheck = true;
			}
			
			

			int diceRoll = curPlayer.rollDice();

			if (diceRoll == 7) {
				for(Player p: players) {
					p.sevenRolled(p, this.getBank());
				}
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
		if(trade instanceof MaritimeTrade) {
			MaritimeTrade mt = (MaritimeTrade)trade;
			mt.accept();
		}
		if(trade instanceof SpecialTrade) {
			SpecialTrade st = (SpecialTrade)trade;
			st.accept();
		}
	}
	
	public void gamePhaseTrade() {
		curPlayer = players.get(playerTurnIndex);
	}
	
	public void gamePhaseBuild() {

		curPlayer = players.get(playerTurnIndex);
	}
	
	public boolean isGameEnded() {
		return gameEnded;
	}

	public void gamePhaseEnd() {
		for(DevelopmentCard card: curPlayer.getDevCards()) {
			card.setCanBePlayed(true);
		}
		
		setLargestArmy();
		setLongestRoad();
		if (curPlayer.calcVictoryPoints() >= POINTS_TO_WIN) {
			gameWinnerIndex = playerTurnIndex;
			gameEnded = true;
			chat.addToChat("Game over: " + curPlayer.getName() + " won!");
		}

		playerTurnIndex++;

		if (playerTurnIndex >= players.size()) {
			playerTurnIndex = 0;
		}
		curPlayer = players.get(playerTurnIndex);
		chat.addToChat(curPlayer.getName() + "'s turn");
	}
  
	private void setLargestArmy() {
		int largestArmy = 0;
		for(Player player: players) {
			player.setHasLargestArmy(false);
			int playersArmy = player.getNumberOfKnightsPlayed();
			if (playersArmy > largestArmy && playersArmy >= 3) {
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
	}
}