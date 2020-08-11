package com.JCatan;
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

	public List<Player> getPlayers(){
		return players;
	}
	
	public Player getCurrentPlayer() {
		if(playerTurnIndex >= players.size())
			playerTurnIndex=0;
		return players.get(playerTurnIndex);
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
	        for(Player player: players) {
	            player.setup();
	        }
	        
	        for(int i = players.size() - 1; i >=0; i--) {
	            players.get(i).setup();
	        }
	        
	    }
	
	private void gamePhase() {
	    playerTurnIndex = 0;
		boolean gameEnded = false;
        while(!gameEnded) {
		    
        	System.out.print("In game phase");
            HumanPlayer curPlayer = (HumanPlayer) getCurrentPlayer();
            
            int diceRoll = curPlayer.getDiceRoll();
            
            if(diceRoll == 7) {
                players.forEach(p -> p.sevenRolled(curPlayer));
            }
            
            board.dishOutResources(diceRoll);
            
            System.out.print("Beginning Trade");
            
            curPlayer.tradePhase();
            
            curPlayer.buildPhase();
            
            if(curPlayer.calcVictoryPoints() > POINTS_TO_WIN) {
                gameWinnerIndex = playerTurnIndex;
                gameEnded = true;
            }
            
            playerTurnIndex++;
            
            if(playerTurnIndex >= players.size()) {
                playerTurnIndex = 0;
            }
		}
		
	}
	
	public void turnOnPlayersTradePanel(DomesticTrade trade) {
		//Turn the player's tradePanel on and insert the trade...
		System.out.print(trade.getReceivingPlayer().getName() + " MADE DOMESTIC TRADE OFFER!");
		
		
	}
	
	public void startGame() {
		
		diceRollPhase();
		
		setupPhase();
		
		gamePhase();
	}
}