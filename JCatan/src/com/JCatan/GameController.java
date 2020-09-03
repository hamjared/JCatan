package com.JCatan;

import java.awt.Color;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import com.JCatan.gui.GameGUI;

public class GameController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	int setupNum = 0;
	boolean setupReverse = false;

	public List<Player> getPlayers() {
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
		for (Tile t: this.getBoard().getTiles()) {
			if (t.getResourceType().equals(ResourceType.DESERT)) {
				robber.setTargetTile(t);
			}
		}

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
		if (refresh != null)
			refresh.accept(this);
	}

	public void setAction(Consumer c) {
		action = c;
	}

	public void endTrade() {
		// Could display message player accepted the trade...
		if (action != null)
			action.accept(this);
	}

	public Robber getRobber() {
		return robber;
	}

	public void gamePhaseRoll() {
		curPlayer = players.get(playerTurnIndex);
		if (setUpChatCheck == false) {
			chat.addToChat(curPlayer.getName() + "'s turn");
			setUpChatCheck = true;
		}

		int diceRoll = curPlayer.getDiceRoll();

		if (diceRoll == 7) {
			for (Player p : players) {
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

	public void initiateTrade(Trade trade) throws InvalidTradeException {
		System.out.print("Validating Trade");
		if (trade instanceof DomesticTrade) {
			DomesticTrade dt = (DomesticTrade) trade;
			dt.validateTrade();
		}
		if (trade instanceof MaritimeTrade) {
			MaritimeTrade mt = (MaritimeTrade) trade;
			mt.validateTrade();
		}
		if (trade instanceof SpecialTrade) {
			SpecialTrade st = (SpecialTrade) trade;
			st.validateTrade();
		}
	}

	public void acceptTrade(Trade trade, Player player) {
		trade.offer(player);
		if (trade instanceof MaritimeTrade)
			this.bank = ((MaritimeTrade) trade).getBank();
		if (trade instanceof SpecialTrade)
			this.bank = ((SpecialTrade) trade).getBank();
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
		for (DevelopmentCard card : curPlayer.getDevCards()) {
			card.setCanBePlayed(true);
		}

		setLargestArmy();
		setLongestRoad();
		if (curPlayer.calcVictoryPoints() >= POINTS_TO_WIN) {
			gameWinnerIndex = playerTurnIndex;
			gameEnded = true;
			chat.addToChat("Game over: " + curPlayer.getName() + " won!");
		}

		Dice dice = Dice.getInstance();
		if (dice.isWasDoubles()) {
			chat.addToChat(curPlayer.getName() + " rolled doubles. Player will go again.");
		} else {
			playerTurnIndex++;
		}

		curPlayer.setPlayedDevCardOnTurn(false);

		if (playerTurnIndex >= players.size()) {
			playerTurnIndex = 0;
		}
		curPlayer = players.get(playerTurnIndex);
		chat.addToChat(curPlayer.getName() + "'s turn");
	}

	private void setLargestArmy() {
		int largestArmy = 0;
		Player prevLargestArmyPlayer = null;
		for (Player player : players) {
			if (player.hasLargestArmy) {
				prevLargestArmyPlayer = player;
			}
		}

		if (prevLargestArmyPlayer != null) {
			largestArmy = prevLargestArmyPlayer.getNumberOfKnightsPlayed();
		}

		for (Player player : players) {
			int playersArmy = player.getNumberOfKnightsPlayed();

			if (player.equals(prevLargestArmyPlayer) && playersArmy > largestArmy
					|| playersArmy > largestArmy && playersArmy >= 3) {

				if (prevLargestArmyPlayer != null)
					prevLargestArmyPlayer.setHasLargestArmy(false);

				largestArmy = playersArmy;
				player.setHasLargestArmy(true);
				prevLargestArmyPlayer = player;
			}
		}
	}

	private void setLongestRoad() {
		int longestRoad = 0;
		Player prevLongestRoadPlayer = null;
		for (Player player : players) {
			if (player.hasLongestRoad) {
				prevLongestRoadPlayer = player;
			}
		}
		if (prevLongestRoadPlayer != null) {
			longestRoad = prevLongestRoadPlayer.calcLongestRoad();
		}
		for (Player player : players) {
			player.setHasLongestRoad(false);
			int playersLongestRoad = player.calcLongestRoad();
			if (playersLongestRoad > longestRoad && playersLongestRoad >= 5) {
				longestRoad = playersLongestRoad;
				player.setHasLongestRoad(true);
			}
			if (player.equals(prevLongestRoadPlayer) && playersLongestRoad >= longestRoad) {
				longestRoad = playersLongestRoad;
				player.setHasLongestRoad(true);
			}
		}

	}

	public void startGame() {
		chat.addToChat("Game started");
		Stack<Color> colors = new Stack<>();
		colors.push(Color.WHITE);
		colors.push(Color.BLUE);
		colors.push(Color.ORANGE);
		colors.push(Color.RED);
		for (Player player : players) {
			player.setColor(colors.pop());
		}
		diceRollPhase();
	}

	public void endSetupTurn() {
		if (setupNum == 3 && setupReverse == false) {
			setCurPlayer(getPlayers().get(setupNum));
			setupReverse = true;
		} else if (setupNum <= 3 && setupReverse == true) {
			if (setupNum == 0) {
				setGamePhase(GamePhase.GAMEROLL);
			} else {
				setCurPlayer(getPlayers().get(setupNum - 1));
				setupNum--;
			}
		} else {
			setCurPlayer(getPlayers().get(setupNum + 1));
			setupNum++;
		}
	}

	public String toString() {
		String s = "";
		s += "Cur Player: " + this.curPlayer.getName() + "\n";
		s += "Game Phase: " + this.gamePhase + "\n";
		return s;
	}
}