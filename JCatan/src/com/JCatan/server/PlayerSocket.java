package com.JCatan.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.JCatan.DevCardAction;
import com.JCatan.DevelopmentCard;
import com.JCatan.Dice;
import com.JCatan.GameController;
import com.JCatan.GamePhase;
import com.JCatan.InsufficientResourceCardException;
import com.JCatan.InvalidDevCardUseException;
import com.JCatan.InvalidTradeException;
import com.JCatan.KnightDevelopmentCard;
import com.JCatan.Node;
import com.JCatan.Player;
import com.JCatan.ResourceCard;
import com.JCatan.RoadBuildingDevelopmentCard;
import com.JCatan.gui.GameGUI;

public class PlayerSocket implements Runnable {
	Player player;
	Socket connection;
	GameServer server;

	public Socket getConnection() {
		return connection;
	}

	public PlayerSocket(Player player, Socket socket, GameServer server) {
		this.player = player;
		this.connection = socket;
		this.server = server;
	}

	@Override
	public void run() {
		while (true) {
			try {
				ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
				Object obj = ois.readObject();
				if (obj == null || !(obj instanceof Message)) {
					continue;
				}
				Message msg = (Message) obj;
				switch (msg.getAction()) {
				case BuildSettlement:
					buildSettlement(msg);
					break;
				case BuildRoad:
					buildRoad(msg);
					break;
				case BuildCity:
					buildCity(msg);
					break;
				case EndSetupTurn:
					endSetupTurn(msg);
					break;
				case EndTurn:
					endTurn(msg);
					Message m = new MessageBuilder().action(Message.Action.EndTurn)
							.gameController(server.getController()).build();
					server.broadcastMessage(m);
					continue;
				case MoveRobber:
					moveRobber(msg);
					Message msg2 = new MessageBuilder().action(Message.Action.MoveRobber)
							.gameController(server.getController()).robberPoint(msg.getRobberPoint()).build();
					server.broadcastMessage(msg2);
					continue;
				case RollDice:
					rollDice();
					Message message = new MessageBuilder().action(Message.Action.DiceRolled)
							.gameController(server.getController()).build();
					server.broadcastMessage(message);
					continue;
				case Trade:
					trade(msg);
					continue;
				case BadTrade:
					continue;
				case FinalizeTrade:
					finalizeTrade(msg);
					continue;
				case PlayDevelopmentCard:
					if (server.getController().getCurPlayer().isPlayedDevCardOnTurn()) {
						// Can't play dev card
					} else {
						playDevelopmentCard(msg);
						if (msg.getDevCard() instanceof RoadBuildingDevelopmentCard) {
							Message rbMessage = new MessageBuilder().action(Message.Action.RoadBuilder)
									.gameController(server.getController()).build();
							server.broadcastMessage(rbMessage);
							continue;
						}
					}

					break;
				case BuyDevelopmentCard:
					buyDevCard(msg);
					break;
				case DeclineTrade:
					playerDeclineTrade(msg);
					continue;
				case DiceData:
					sendDiceData(msg);
					continue;
				case Chat:
					updateChat(msg);
					break;
				case DropCardsOnSeven:
					playerDropsCards(msg);
					continue;
				default:
					break;
				}

				Message returnMessage = new MessageBuilder().action(Message.Action.UpdateBoard)
						.gameController(server.getController()).build();

				server.broadcastMessage(returnMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void playerDropsCards(Message msg) {
		GameController controller = server.getController();
		Player dropCardPlayer = null;
		
		ResourceCard card = msg.getCard();
		for(Player p: controller.getPlayers()) {
			if(p.equals(msg.getMyPlayer())) {
				dropCardPlayer = p;
				break;
			}
		}
		
		dropCardPlayer.dropCardOnSeven(card, dropCardPlayer, controller.getBank());
		
		for (Player p: controller.getPlayers()) {
			server.getController().getChat().addToChat(p.getName() + " needs to drop " + p.getCardsToDrop() + " cards");
		}
				
		Message msg2 = new MessageBuilder().action(Message.Action.DropCardsOnSeven).gameController(server.getController()).build();
		server.broadcastMessage(msg2);
	}

	private void playerDeclineTrade(Message msg) {
		server.broadcastMessage(msg);
	}

	private void updateChat(Message msg) {
		server.getController().getChat().addToChat(msg.getChatMessage());

	}

	private void sendDiceData(Message msg) {

		server.getController().getChat().addToChat("Dice Roll History:\n" + Dice.getInstance().getDiceRollHistory());
		Message message = new MessageBuilder().action(Message.Action.DiceData).gameController(server.getController())
				.dice(Dice.getInstance()).build();
		server.broadcastMessage(message);

	}

	private void trade(Message msg) {
		com.JCatan.Trade trade = msg.getTrade();
		try {
			if (trade instanceof com.JCatan.DomesticTrade) {
				server.getController().initiateTrade(trade);
				server.broadcastMessage(msg);
			} else {
				server.getController().initiateTrade(trade);
				Player player = server.getController().getPlayers().stream()
						.filter(p -> p.equals(trade.getOfferingPlayer())).findFirst().get();
				server.getController().acceptTrade(trade, player);
				Message newMsg = new MessageBuilder().action(Message.Action.BankAcceptedTrade).player(player)
						.gameController(server.getController()).setCustomMessage("Bank Accepted Trade").build();
				server.broadcastMessage(newMsg);
			}
		} catch (InvalidTradeException e) {
			System.out.println("BAD TRADE DETECTED!!!");
			Message newMsg = new MessageBuilder().action(Message.Action.BadTrade).player(trade.getOfferingPlayer())
					.setCustomMessage(e.getMessage()).build();
			server.broadcastMessage(newMsg);
		}
	}

	private void finalizeTrade(Message msg) {
		com.JCatan.Trade trade = msg.getTrade();
		if (trade != null) {
			try {
				server.getController().initiateTrade(trade);

				Player offerer = server.getController().getPlayers().stream()
						.filter(p -> p.equals(trade.getOfferingPlayer())).findFirst().get();
				Player receiver = server.getController().getPlayers().stream()
						.filter(p -> p.equals(trade.getReceivingPlayer())).findFirst().get();
				offerer.receiveTrade(trade);
				receiver.receiveTrade(trade);
				msg.setGc(server.getController());
				server.broadcastMessage(msg);

			} catch (InvalidTradeException e) {
				System.out.println("BAD TRADE DETECTED!!!");
				Message newMsg = new MessageBuilder().action(Message.Action.BadTrade).player(trade.getOfferingPlayer())
						.setCustomMessage(e.getMessage()).build();
				server.broadcastMessage(newMsg);
			}
		}
	}

	private void buildCity(Message msg) {

		GameController controller = server.getController();
		try {
			Node node = controller.getBoard().getBoard().getNodeList().get(msg.getNode().getNodeIndex());
			controller.getCurPlayer().buildCity(node, controller);
			if (controller.getGamePhase().equals(GamePhase.GAMEROLL)) {
				controller.setGamePhase(GamePhase.GAMEMAIN);
			}
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void buyDevCard(Message msg) {

		try {
			server.getController().getCurPlayer().buyDevelopmentCard(server.getController());
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void playDevelopmentCard(Message msg) {

		convertDevCardActionToServerObjects(msg.getDevCardAction());
		DevelopmentCard card = convertDevCardToServerObject(msg.getDevCard());
		if (msg.getDevCard() instanceof KnightDevelopmentCard) {
			server.getController().setGamePhase(GamePhase.ROBBERMOVE);
		}
		try {
			server.getController().getCurPlayer().playDevelopmentCard(card, msg.getDevCardAction());

		} catch (InvalidDevCardUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private DevelopmentCard convertDevCardToServerObject(DevelopmentCard devCard) {
		for (DevelopmentCard dc : server.getController().getCurPlayer().getDevCards()) {
			if (dc.equals(devCard)) {
				return dc;
			}
		}

		return null;

	}

	private void convertDevCardActionToServerObjects(DevCardAction devCardAction) {
		if (devCardAction.getBank() != null) {
			devCardAction.setBank(server.getController().getBank());
		}
		if (devCardAction.getCurPlayer() != null) {
			devCardAction.setCurPlayer(server.getController().getCurPlayer());
		}
		if (devCardAction.getStealFromPlayer() != null) {
			int stealPlayerIndex = server.getController().getPlayers().indexOf(devCardAction.getStealFromPlayer());
			devCardAction.setStealFromPlayer(server.getController().getPlayers().get(stealPlayerIndex));
		}
		if (devCardAction.getGamePlayers() != null) {
			devCardAction.setGamePlayers(server.getController().getPlayers());
		}

	}

	private void moveRobber(Message msg) {

		server.getController().setGamePhase(GamePhase.GAMEMAIN);
		server.getController().getRobber().move(server.getController().getBoard().getTiles().get(msg.getRobberTile()));
		server.getController().getRobber().rob(server.getController().getCurPlayer());
	}

	private void endTurn(Message msg) {
		server.getController().setGamePhase(GamePhase.GAMEROLL);
		server.getController().gamePhaseEnd();

	}

	private void rollDice() {
		server.getController().getCurPlayer().setDice(Dice.getInstance());
		server.getController().getCurPlayer().rollDice();
		server.getController().gamePhaseRoll();
		server.getController().setGamePhase(GamePhase.GAMEMAIN);

	}

	private void endSetupTurn(Message msg) {
		GameController controller = server.getController();
		controller.endSetupTurn();

	}

	private void buildRoad(Message msg) {
		GameController controller = server.getController();
		try {
			Node node1 = controller.getBoard().getBoard().getNodeList().get(msg.getRoad().getNode1().getNodeIndex());
			Node node2 = controller.getBoard().getBoard().getNodeList().get(msg.getRoad().getNode2().getNodeIndex());
			controller.getCurPlayer().buildRoad(controller.getGamePhase(), node1, node2, controller);
			if (controller.getGamePhase().equals(GamePhase.GAMEROLL)) {
				controller.setGamePhase(GamePhase.GAMEMAIN);
			}
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void buildSettlement(Message msg) {
		GameController controller = server.getController();
		try {
			Node node = controller.getBoard().getBoard().getNodeList().get(msg.getNode().getNodeIndex());
			controller.getCurPlayer().buildSettlement(controller.getGamePhase(), node, controller);
			if (controller.getGamePhase().equals(GamePhase.GAMEROLL)) {
				controller.setGamePhase(GamePhase.GAMEMAIN);
			}
		} catch (InsufficientResourceCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Player getPlayer() {
		return player;
	}

}
