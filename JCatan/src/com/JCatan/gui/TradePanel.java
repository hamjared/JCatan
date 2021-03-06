package com.JCatan.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.border.TitledBorder;

import com.JCatan.Consumer;
import com.JCatan.DomesticTrade;
import com.JCatan.MaritimeTrade;
import com.JCatan.Player;
import com.JCatan.ResourceCard;
import com.JCatan.ResourceType;
import com.JCatan.SpecialTrade;
import com.JCatan.Trade;
import com.JCatan.server.Message;
import com.JCatan.server.MessageBuilder;

import javax.swing.border.BevelBorder;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class TradePanel extends JPanel {

	private static final long serialVersionUID = 208059651951407846L;
	private List<Player> participants;
	private Player currentPlayer;
	private Player selectedPlayer;
	private JPanel traders;
	private JPanel theirOffer;
	private JPanel playerOffer;
	private JButton confirmButton;
	private JButton offerButton;
	private JButton counterOfferButton;
	private JButton cancelButton;
	private ButtonGroup buttonToggleGroup = new ButtonGroup();
	private Consumer<JButton> action;
	private Map<String, Integer> offeringResourceTypeAndAmount;
	private Map<String, Integer> requestingResourceTypeAndAmount;
	private boolean recievingOffer = false;
	private JPanel bottomButtonPanel;
	JButton declineButton;

	private class CustomCheckBox extends JCheckBox {
		private static final long serialVersionUID = -6191571460091396369L;
		private Player player;

		public CustomCheckBox(Player player, String label) {
			super(label);
			this.player = player;
		}

		public Player getPlayer() {
			return player;
		}
	}

	public void setTradeInfo(Trade trade) {

		recievingOffer = true;
		traders.setEnabled(false);
		clearAllPlayerResources();
		this.setVisible(true);
		this.setEnabled(true);

		currentPlayer = trade.getReceivingPlayer();
		selectedPlayer = trade.getOfferingPlayer();

		List<ResourceCard> offering = trade.getOfferingCards();
		List<ResourceCard> requesting = trade.getRequestingCards();
		offerButton.setEnabled(false);

		setOfferResources(offering, true, theirOffer);
		setOfferResources(requesting, false, playerOffer);
		JCheckBox temp = new JCheckBox(selectedPlayer.getName());
		temp.setSelected(true);
		temp.setEnabled(false);
		buttonToggleGroup.add(temp);
		traders.add(temp);

		confirmButton.setEnabled(true);
		counterOfferButton.setEnabled(true);
		declineButton.setEnabled(true);
		cancelButton.setEnabled(false);
		
		this.revalidate();
		this.repaint();
	}

	private void counterOfferEvent() {
		// Set the players whole resources to counter offer...
		clearAllPlayerResources();

		JCheckBox temp = new JCheckBox(selectedPlayer.getName());
		temp.setSelected(true);
		temp.setEnabled(false);
		buttonToggleGroup.add(temp);
		traders.add(temp);

		confirmButton.setEnabled(false);
		counterOfferButton.setEnabled(false);
		declineButton.setEnabled(true);
		offerButton.setEnabled(true);
		cancelButton.setEnabled(false);
		setPlayersResources(currentPlayer, playerOffer);

		// Now grab the original offering player's resources to counter offer...
		setPlayersResources(selectedPlayer, theirOffer);

		this.revalidate();
		this.repaint();
	}

	private void confirmEvent() {
		List<ResourceCard> offering = setTradeCards(offeringResourceTypeAndAmount);
		List<ResourceCard> requesting = setTradeCards(requestingResourceTypeAndAmount);

		DomesticTrade trade = new DomesticTrade(selectedPlayer, currentPlayer, offering, requesting);
		Message msg = new MessageBuilder().action(Message.Action.FinalizeTrade).trade(trade).build();
		GameGUI.gameClient.sendMessage(msg);
	}

	public void setDelegate(Consumer<JButton> action) {
		this.action = action;
	}

	public void close() {
		this.setVisible(false);
		this.setEnabled(false);

		recievingOffer = false;
		clearAllPlayerResources();
		counterOfferButton.setEnabled(false);
		confirmButton.setEnabled(false);
		declineButton.setEnabled(false);
		cancelButton.setEnabled(true);

		//Reenable the end turn button
		if (action != null) {
			action.accept(cancelButton);
		}
	}

	private List<ResourceCard> setTradeCards(Map<String, Integer> resources) {
		List<ResourceCard> cards = new ArrayList<ResourceCard>();
		for (Map.Entry<String, Integer> entry : resources.entrySet()) {
			ResourceCard card = null;
			switch (entry.getKey()) {
			case "WOOD":
				card = new ResourceCard(ResourceType.WOOD);
				break;
			case "WHEAT":
				card = new ResourceCard(ResourceType.WHEAT);
				break;
			case "BRICK":
				card = new ResourceCard(ResourceType.BRICK);
				break;
			case "SHEEP":
				card = new ResourceCard(ResourceType.SHEEP);
				break;
			case "ORE":
				card = new ResourceCard(ResourceType.ORE);
				break;
			default:
				// Throw Exception...
				break;
			}
			for (int i = 0; i < entry.getValue(); i++) {
				cards.add(card);
			}
		}
		return cards;
	}

	public void resetFromBadTrade() {
		if (!recievingOffer) {
			offerButton.setEnabled(true);
			cancelButton.setEnabled(true);
			AddandDecButtonsEnabled(true);
		} else {
			recievingOffer = false;
			declineButton.setEnabled(false);
			cancelButton.setEnabled(true);
			clearAllPlayerResources();
			this.repaint();
		}
	}

	private void setTraders() {
		JCheckBox maritimeTrade = new JCheckBox("Maritime");
		maritimeTrade.addActionListener(e -> {
			clearSelectedPlayerResources();
			setPlayersResources(null, theirOffer);
			offerButton.setEnabled(true);
		});
		maritimeTrade.setSelected(false);
		buttonToggleGroup.add(maritimeTrade);
		traders.add(maritimeTrade);

		JCheckBox specialTrade = new JCheckBox("Special Trade");
		specialTrade.addActionListener(e -> {
			clearSelectedPlayerResources();
			setPlayersResources(null, theirOffer);
			offerButton.setEnabled(true);
		});
		maritimeTrade.setSelected(false);
		buttonToggleGroup.add(specialTrade);
		traders.add(specialTrade);

		this.currentPlayer = GameGUI.controller.getCurPlayer();
		this.participants = GameGUI.controller.getPlayers().stream().filter(t -> !Objects.equals(t, currentPlayer))
				.collect(Collectors.toList());

		for (Player player : participants) {
			CustomCheckBox temp = new CustomCheckBox(player, player.getName());
			temp.setSelected(false);
			temp.addActionListener(e -> {
				CustomCheckBox box = (CustomCheckBox) e.getSource();
				selectedPlayer = box.getPlayer();
				clearSelectedPlayerResources();
				setPlayersResources(selectedPlayer, theirOffer);
				offerButton.setEnabled(true);
			});
			traders.add(temp);
			buttonToggleGroup.add(temp);
		}
		buttonToggleGroup.clearSelection();
	}

	@Override
	protected void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			if (isVisible() && isEnabled() && !recievingOffer) {
				clearAllPlayerResources();
				setTraders();
				boolean turnOn = !(buttonToggleGroup.getSelection() == null);
				offerButton.setEnabled(turnOn);
				setPlayersResources(currentPlayer, playerOffer);
			}
		} finally {
		}
	}

	private void clearSelectedPlayerResources() {
		requestingResourceTypeAndAmount.clear();
		theirOffer.removeAll();
		theirOffer.revalidate();
		theirOffer.repaint();
	}

	private void clearAllPlayerResources() {
		offeringResourceTypeAndAmount.clear();
		requestingResourceTypeAndAmount.clear();
		Arrays.stream(traders.getComponents()).forEach(b -> buttonToggleGroup.remove((AbstractButton) b));
		traders.removeAll();
		playerOffer.removeAll();
		theirOffer.removeAll();
		traders.revalidate();
		playerOffer.revalidate();
		theirOffer.revalidate();
	}

	private void updateMap(Map<String, Integer> map, String resource, Integer amount) {
		map.computeIfPresent(resource, (k, v) -> amount);
	}

	private JLabel createLabel(JPanel panel, String text, int x, int y) {
		JLabel playerResource = new JLabel(text);
		playerResource.setSize(playerResource.getPreferredSize());
		GridBagConstraints gbc_playerResource = new GridBagConstraints();
		gbc_playerResource.anchor = GridBagConstraints.LINE_START;
		gbc_playerResource.insets = new Insets(0, 0, 0, 5);
		gbc_playerResource.gridx = x;
		gbc_playerResource.gridy = y;
		panel.add(playerResource, gbc_playerResource);
		return playerResource;
	}

	private void createButtonPanel(JPanel panel, JLabel playerResourceTotal, JLabel playerResourceName, int x, int y) {
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.CENTER;
		gbc_panel_1.gridx = x;
		gbc_panel_1.gridy = y;
		panel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_panel_1.rowWeights = new double[] { 1.0, 1.0 };
		panel_1.setLayout(gbl_panel_1);
		x++;

		JButton playerDecResource = new JButton("-");
		playerDecResource.addActionListener(e -> {
			String val = playerResourceTotal.getText();
			String resourceName = playerResourceName.getText();
			Integer temp = Integer.valueOf(val);
			if (temp <= 0)
				return;
			--temp;
			boolean isRequestMapping = ((JButton) e.getSource()).getParent().getParent().equals(theirOffer);
			if (isRequestMapping) {
				updateMap(requestingResourceTypeAndAmount, resourceName, temp);
			} else {
				updateMap(offeringResourceTypeAndAmount, resourceName, temp);
			}
			playerResourceTotal.setText(temp.toString());
		});
		GridBagConstraints gbc_playerDecResource = new GridBagConstraints();
		gbc_playerDecResource.insets = new Insets(0, 0, 0, 5);
		gbc_playerDecResource.gridx = x;
		gbc_playerDecResource.gridy = y;
		panel_1.add(playerDecResource, gbc_playerDecResource);
		x++;

		JButton playerIncResource_1 = new JButton("+");
		playerIncResource_1.addActionListener(e -> {
			String val = playerResourceTotal.getText();
			Integer temp = Integer.valueOf(val);
			String resourceName = playerResourceName.getText();
			++temp;
			boolean isRequestMapping = ((JButton) e.getSource()).getParent().getParent().equals(theirOffer);
			if (isRequestMapping) {
				updateMap(requestingResourceTypeAndAmount, resourceName, temp);
			} else {
				updateMap(offeringResourceTypeAndAmount, resourceName, temp);
			}
			playerResourceTotal.setText(temp.toString());
		});

		GridBagConstraints gbc_playerIncResource_1 = new GridBagConstraints();
		gbc_playerIncResource_1.gridx = x;
		gbc_playerIncResource_1.gridy = y;
		panel_1.add(playerIncResource_1, gbc_playerIncResource_1);
	}

	private void createTradeIcons(JPanel panel, String resourceName, String value, int i, int j, boolean needsButtons) {
		JLabel playerResourceName = createLabel(panel, resourceName, i, j);
		JLabel playerResourceTotal = createLabel(panel, value, ++i, j);
		if (needsButtons)
			createButtonPanel(panel, playerResourceTotal, playerResourceName, ++i, j);
	}

	private void setOfferResources(List<ResourceCard> cards, boolean isOfferingSet, JPanel panel) {
		int j = 0;
		Map<ResourceType, Integer> resources = cards.stream().collect(Collectors
				.toMap(t -> ((ResourceCard) t).getResourceType(), v -> 1, (existing, addition) -> existing + addition));

		for (Map.Entry<ResourceType, Integer> entry : resources.entrySet()) {
			int i = 0;
			String cardName = entry.getKey().name();
			String total = entry.getValue().toString();
			if (isOfferingSet) {
				offeringResourceTypeAndAmount.put(cardName, Integer.parseInt(total));
			} else {
				requestingResourceTypeAndAmount.put(cardName, Integer.parseInt(total));
			}
			createTradeIcons(panel, cardName, total, i, j, false);
			j++;
		}
	}

	private void setPlayersResources(Player player, JPanel panel) {
		int j = 0;
		int i = 0;
		Map<ResourceType, Integer> resources = null;
		if (player != null) {
			resources = player.getUniqueResourcesCount();
		} else {
			resources = GameGUI.controller.getBank().getResourceMap().entrySet().stream()
					.collect(Collectors.toMap(Map.Entry::getKey, e -> 0));
		}

		for (Map.Entry<ResourceType, Integer> entry : resources.entrySet()) {
			String cardName = entry.getKey().name();
			String total = entry.getValue().toString();
			if (player == currentPlayer) {
				offeringResourceTypeAndAmount.put(cardName, Integer.parseInt(total));
			} else {
				requestingResourceTypeAndAmount.put(cardName, Integer.parseInt(total));
			}

			createTradeIcons(panel, cardName, total, i, j, true);
			j++;
		}
	}

	private void AddandDecButtonsEnabled(boolean isOn) {

		Arrays.stream(playerOffer.getComponents()).filter(c->c instanceof JPanel).forEach(b -> {
			Arrays.stream(((JPanel) b).getComponents()).forEach(c -> c.setEnabled(isOn));
		});

		Arrays.stream(theirOffer.getComponents()).filter(c->c instanceof JPanel).forEach(b -> {
			Arrays.stream(((JPanel) b).getComponents()).forEach(c -> c.setEnabled(isOn));
		});
	}

	public TradePanel(int x, int y, int width, int height) {
		this.setDoubleBuffered(false);
		this.setBounds(x, y, width, height);

		selectedPlayer = null;
		requestingResourceTypeAndAmount = new HashMap<String, Integer>();
		offeringResourceTypeAndAmount = new HashMap<String, Integer>();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 429, 403, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		traders = new JPanel();
		traders.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Participants",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_traders = new GridBagConstraints();
		gbc_traders.gridwidth = 2;
		gbc_traders.weightx = 1.0;
		gbc_traders.insets = new Insets(0, 0, 5, 0);
		gbc_traders.fill = GridBagConstraints.BOTH;
		gbc_traders.gridx = 0;
		gbc_traders.gridy = 0;

		// setTraders();
		add(traders, gbc_traders);

		playerOffer = new JPanel();
		playerOffer.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "My Offer",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_playerOffer = new GridBagConstraints();
		gbc_playerOffer.weighty = 1.0;
		gbc_playerOffer.weightx = 1.0;
		gbc_playerOffer.insets = new Insets(0, 0, 5, 5);
		gbc_playerOffer.fill = GridBagConstraints.BOTH;
		gbc_playerOffer.gridx = 0;
		gbc_playerOffer.gridy = 1;
		GridBagLayout gbl_playerOffer = new GridBagLayout();
		gbl_playerOffer.columnWidths = new int[] { 0, 0, 0, 10, 0, 0 };
		gbl_playerOffer.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_playerOffer.columnWeights = new double[] { 1.0, 1.0, 1.0, 0.0, 1.0, 1.0 };
		gbl_playerOffer.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		playerOffer.setLayout(gbl_playerOffer);
		add(playerOffer, gbc_playerOffer);

		theirOffer = new JPanel();
		theirOffer.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Their Offer", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_theirOffer = new GridBagConstraints();
		gbc_theirOffer.weighty = 1.0;
		gbc_theirOffer.weightx = 1.0;
		gbc_theirOffer.insets = new Insets(0, 0, 5, 5);
		gbc_theirOffer.fill = GridBagConstraints.BOTH;
		gbc_theirOffer.gridx = 1;
		gbc_theirOffer.gridy = 1;
		GridBagLayout gbl_theirOffer = new GridBagLayout();
		gbl_theirOffer.columnWidths = new int[] { 0, 0, 0, 10, 0, 0 };
		gbl_theirOffer.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_theirOffer.columnWeights = new double[] { 1.0, 1.0, 1.0, 0.0, 1.0, 1.0 };
		gbl_theirOffer.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		theirOffer.setLayout(gbl_theirOffer);
		add(theirOffer, gbc_theirOffer);

		bottomButtonPanel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.weightx = 1.0;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		add(bottomButtonPanel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 114, 106, 143, 109, 100 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		bottomButtonPanel.setLayout(gbl_panel);

		offerButton = new JButton("Offer");
		offerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Get the player selected and offer the potential goods to selected player...
				JCheckBox trader = (JCheckBox) Arrays.stream(traders.getComponents())
						.filter(c -> ((JCheckBox) c).isSelected() == true).findFirst().orElse(null);

				if (trader != null && trader.isSelected()) {
					recievingOffer = false;
					// Turn off cancelButton and the + - buttons on labels
					AddandDecButtonsEnabled(false);
					Arrays.stream(bottomButtonPanel.getComponents()).forEach(b -> b.setEnabled(false));

					String name = trader.getText();

					List<ResourceCard> offering = setTradeCards(offeringResourceTypeAndAmount);
					List<ResourceCard> requesting = setTradeCards(requestingResourceTypeAndAmount);

					// Player Trade
					if (name != "Maritime" && name != "Special Trade") {
						DomesticTrade trade = new DomesticTrade(currentPlayer, selectedPlayer, offering, requesting);
						Message msg = new MessageBuilder().action(Message.Action.Trade).trade(trade).build();
						GameGUI.gameClient.sendMessage(msg);
					}// Maritime Trade 
					else if (name == "Maritime") {
						MaritimeTrade trade = new MaritimeTrade(currentPlayer, GameGUI.controller.getBank(), offering,
								requesting);
						Message msg = new MessageBuilder().action(Message.Action.Trade).trade(trade).build();
						GameGUI.gameClient.sendMessage(msg);
					}
					// Special Trade
					else if (name == "Special Trade") {
						SpecialTrade trade = new SpecialTrade(currentPlayer, GameGUI.controller.getBank(), offering,
								requesting);
						Message msg = new MessageBuilder().action(Message.Action.Trade).trade(trade).build();
						GameGUI.gameClient.sendMessage(msg);
					}
				}
			}
		});

		offerButton.setName("offer");
		GridBagConstraints gbc_offerButton = new GridBagConstraints();
		gbc_offerButton.fill = GridBagConstraints.BOTH;
		gbc_offerButton.anchor = GridBagConstraints.LINE_START;
		gbc_offerButton.weightx = 1.0;
		gbc_offerButton.insets = new Insets(0, 0, 0, 5);
		gbc_offerButton.gridx = 0;
		gbc_offerButton.gridy = 0;
		bottomButtonPanel.add(offerButton, gbc_offerButton);

		confirmButton = new JButton("Confirm");
		confirmButton.setEnabled(false);
		confirmButton.addActionListener(e -> confirmEvent());
		GridBagConstraints gbc_confirmButton = new GridBagConstraints();
		gbc_confirmButton.weightx = 1.0;
		gbc_confirmButton.fill = GridBagConstraints.BOTH;
		gbc_confirmButton.insets = new Insets(0, 0, 0, 5);
		gbc_confirmButton.gridx = 1;
		gbc_confirmButton.gridy = 0;
		bottomButtonPanel.add(confirmButton, gbc_confirmButton);

		counterOfferButton = new JButton("Counter Offer");
		counterOfferButton.setEnabled(false);
		GridBagConstraints gbc_counterOfferButton = new GridBagConstraints();
		gbc_counterOfferButton.weightx = 1.0;
		gbc_counterOfferButton.fill = GridBagConstraints.BOTH;
		gbc_counterOfferButton.insets = new Insets(0, 0, 0, 5);
		gbc_counterOfferButton.gridx = 2;
		gbc_counterOfferButton.gridy = 0;
		counterOfferButton.addActionListener(e -> {
			counterOfferEvent();
		});
		bottomButtonPanel.add(counterOfferButton, gbc_counterOfferButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> {
			close();
		});

		declineButton = new JButton("Decline");
		declineButton.addActionListener(e -> {
			// Send a message to server that player declined the trade...
			String message = "Player declined your trade";
			Message msg = new MessageBuilder().action(Message.Action.DeclineTrade).setCustomMessage(message)
					.player(selectedPlayer).build();
			GameGUI.gameClient.sendMessage(msg);

			// Now close this window and reset it properly.
			close();
		});

		GridBagConstraints gbc_declineButton = new GridBagConstraints();
		gbc_declineButton.weightx = 1.0;
		gbc_declineButton.fill = GridBagConstraints.BOTH;
		gbc_declineButton.gridx = 3;
		gbc_declineButton.gridy = 0;
		declineButton.setEnabled(false);
		bottomButtonPanel.add(declineButton, gbc_declineButton);

		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.weightx = 1.0;
		gbc_cancelButton.fill = GridBagConstraints.BOTH;
		gbc_cancelButton.gridx = 4;
		gbc_cancelButton.gridy = 0;
		cancelButton.setEnabled(true);
		bottomButtonPanel.add(cancelButton, gbc_cancelButton);
		bottomButtonPanel.revalidate();
		this.setVisible(false);
		this.setEnabled(false);
	}
}