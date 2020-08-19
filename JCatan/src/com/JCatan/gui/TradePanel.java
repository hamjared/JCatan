package com.JCatan.gui;

import java.awt.Color;
import java.awt.Component;
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

import javax.swing.border.BevelBorder;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
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
	private String selectedPlayerlabel = "Selected Player";
	private Consumer<JButton> action;
	private Map<String, Integer> offeringResourceTypeAndAmount;
	private Map<String, Integer> requestingResourceTypeAndAmount;

	public void setTradeInfo(Trade trade) {

		// Just set up Labels in counter offer panel...
		List<ResourceCard> offering = trade.getOfferingCards();
		List<ResourceCard> requesting = trade.getRequestingCards();
	}

	public void setDelegate(Consumer<JButton> action) {
		this.action = action;
	}

	public void close() {
		cancelButton.doClick();
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

	private void setTraders() {
		JCheckBox maritimeTrade = new JCheckBox("Maritime");
		maritimeTrade.addActionListener(e -> {
			clearSelectedPlayerResources();
			setPlayersResources(null);
			offerButton.setEnabled(true);
		});
		maritimeTrade.setSelected(false);
		buttonToggleGroup.add(maritimeTrade);
		traders.add(maritimeTrade);

		JCheckBox specialTrade = new JCheckBox("Special Trade");
		specialTrade.addActionListener(e -> {
			clearSelectedPlayerResources();
			setPlayersResources(null);
			offerButton.setEnabled(true);
		});
		maritimeTrade.setSelected(false);
		buttonToggleGroup.add(specialTrade);
		traders.add(specialTrade);

		this.currentPlayer = GameGUI.controller.getCurPlayer();
		this.participants = GameGUI.controller.getPlayers().stream().filter(t -> !Objects.equals(t, currentPlayer))
				.collect(Collectors.toList());

		for (Player player : participants) {
			JCheckBox temp = new JCheckBox(player.getName());
			temp.setSelected(false);
			temp.addActionListener(e -> {
				JCheckBox box = (JCheckBox) e.getSource();

				selectedPlayer = GameGUI.controller.getPlayers().stream().filter(t -> t.getName() == box.getText())
						.findFirst().orElse(null);

				clearSelectedPlayerResources();
				setPlayersResources(selectedPlayer);
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
			if (isVisible() && isEnabled()) {
				clearAllPlayerResources();
				setTraders();
				boolean turnOn = !(buttonToggleGroup.getSelection() == null);
				offerButton.setEnabled(turnOn);
				setPlayersResources(currentPlayer);
			}
		} finally {
		}
	}

	private void clearSelectedPlayerResources() {
		Component[] components = playerOffer.getComponents();
		for (Component comp : components) {
			if (comp.getName() == selectedPlayerlabel)
				playerOffer.remove(comp);
		}
		playerOffer.revalidate();
		playerOffer.repaint();
	}

	private void clearAllPlayerResources() {
		offeringResourceTypeAndAmount.clear();
		requestingResourceTypeAndAmount.clear();
		traders.removeAll();
		playerOffer.removeAll();
		traders.revalidate();
		playerOffer.revalidate();
	}

	private void updateOfferingMap(String resource, Integer amount) {
		offeringResourceTypeAndAmount.computeIfPresent(resource, (k, v) -> amount);
	}

	private void updateRequestingMap(String resource, Integer amount) {
		requestingResourceTypeAndAmount.computeIfPresent(resource, (k, v) -> amount);
	}

	private JLabel createLabel(String text, String name, int x, int y) {
		JLabel playerResource = new JLabel(text);
		playerResource.setName(name);
		playerResource.setSize(playerResource.getPreferredSize());
		GridBagConstraints gbc_playerResource = new GridBagConstraints();
		gbc_playerResource.anchor = GridBagConstraints.LINE_START;
		gbc_playerResource.insets = new Insets(0, 0, 0, 5);
		gbc_playerResource.gridx = x;
		gbc_playerResource.gridy = y;
		playerOffer.add(playerResource, gbc_playerResource);
		return playerResource;
	}

	private void createButtonPanel(JLabel playerResourceTotal, JLabel playerResourceName, String name, int x, int y) {
		JPanel panel_1 = new JPanel();
		panel_1.setName(name);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.CENTER;
		gbc_panel_1.gridx = x;
		gbc_panel_1.gridy = y;
		playerOffer.add(panel_1, gbc_panel_1);
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
			if (panel_1.getName() == selectedPlayerlabel) {
				updateRequestingMap(resourceName, temp);
			} else {
				updateOfferingMap(resourceName, temp);
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
			if (panel_1.getName() == selectedPlayerlabel) {
				updateRequestingMap(resourceName, temp);
			} else {
				updateOfferingMap(resourceName, temp);
			}
			playerResourceTotal.setText(temp.toString());
		});

		GridBagConstraints gbc_playerIncResource_1 = new GridBagConstraints();
		gbc_playerIncResource_1.gridx = x;
		gbc_playerIncResource_1.gridy = y;
		panel_1.add(playerIncResource_1, gbc_playerIncResource_1);
	}

	private void createTradeIcons(String resourceName, String name, String value, int i, int j) {
		JLabel playerResourceName = createLabel(resourceName, name, i, j);
		JLabel playerResourceTotal = createLabel(value, name, ++i, j);
		createButtonPanel(playerResourceTotal, playerResourceName, name, ++i, j);
	}

	private void setPlayersResources(Player player) {
		int j = 0;
		Map<ResourceType, Integer> resources = null;
		if (player != null) {
			resources = player.getUniqueResourcesCount();
		} else {
			resources = GameGUI.controller.getBank().getResourceMap().entrySet().stream()
					.collect(Collectors.toMap(Map.Entry::getKey, e -> 0));
		}

		boolean isCurrentPlayer = player != null && player == currentPlayer;

		for (Map.Entry<ResourceType, Integer> entry : resources.entrySet()) {

			GridBagLayout temp = (GridBagLayout) playerOffer.getLayout();
			int i = (isCurrentPlayer) ? 0 : temp.columnWidths.length / 2 + 1;
			String cardName = entry.getKey().name();
			String total = entry.getValue().toString();
			if (isCurrentPlayer) {
				offeringResourceTypeAndAmount.put(cardName, Integer.parseInt(total));
			} else {
				requestingResourceTypeAndAmount.put(cardName, Integer.parseInt(total));
			}
			String labelName = (player != null && player == currentPlayer) ? "" : selectedPlayerlabel;
			createTradeIcons(cardName, labelName, total, i, j);
			j++;
		}
	}

	/**
	 * Create the panel.
	 */
	public TradePanel(int x, int y, int width, int height) {
		this.setBounds(x, y, width, height);

		selectedPlayer = null;
		requestingResourceTypeAndAmount = new HashMap<String, Integer>();
		offeringResourceTypeAndAmount = new HashMap<String, Integer>();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 569, 403, 0 };
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
		playerOffer.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Player's Offer", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_playerOffer = new GridBagConstraints();
		gbc_playerOffer.weightx = 1.0;
		gbc_playerOffer.insets = new Insets(0, 0, 5, 5);
		gbc_playerOffer.fill = GridBagConstraints.BOTH;
		gbc_playerOffer.gridx = 0;
		gbc_playerOffer.gridy = 1;
		GridBagLayout gbl_playerOffer = new GridBagLayout();
		gbl_playerOffer.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_playerOffer.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_playerOffer.columnWeights = new double[] { 1.0, 1.0, 1.0, 0.0, 1.0, 1.0 };
		gbl_playerOffer.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		playerOffer.setLayout(gbl_playerOffer);
		add(playerOffer, gbc_playerOffer);

		theirOffer = new JPanel();
		theirOffer.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Participant's Offer", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_theirOffer = new GridBagConstraints();
		gbc_theirOffer.weightx = 1.0;
		gbc_theirOffer.insets = new Insets(0, 0, 5, 5);
		gbc_theirOffer.fill = GridBagConstraints.BOTH;
		gbc_theirOffer.gridx = 1;
		gbc_theirOffer.gridy = 1;
		add(theirOffer, gbc_theirOffer);
		GridBagLayout gbl_theirOffer = new GridBagLayout();
		gbl_theirOffer.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_theirOffer.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_theirOffer.columnWeights = new double[] { 1.0, 1.0, 1.0, 0.0, 1.0, 1.0 };
		gbl_theirOffer.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		theirOffer.setLayout(gbl_theirOffer);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.weightx = 1.0;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 114, 106, 143, 109, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		offerButton = new JButton("Offer");
		offerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Get the player selected and offer the potential goods to selected player...
				JCheckBox trader = (JCheckBox) Arrays.stream(traders.getComponents())
						.filter(c -> ((JCheckBox) c).isSelected() == true).findFirst().orElse(null);

				if (trader != null && trader.isSelected()) {
					String name = trader.getText();

					List<ResourceCard> offering = setTradeCards(offeringResourceTypeAndAmount);
					List<ResourceCard> requesting = setTradeCards(requestingResourceTypeAndAmount);
					

					// Player Trade
					if (name != "Maritime" && name != "Special Trade") {
						DomesticTrade trade = new DomesticTrade(currentPlayer, selectedPlayer, offering, requesting);
						GameGUI.controller.initiateTrade(trade);
						GameGUI.ResourcePanel.repaint();
					}

					// Maritime Trade
					if (name == "Maritime") {
						MaritimeTrade trade = new MaritimeTrade(currentPlayer, GameGUI.controller.getBank(), offering,
								requesting);
						GameGUI.controller.initiateTrade(trade);
						GameGUI.ResourcePanel.repaint();
					}

					// Special Trade
					if (name == "Special Trade") {
						SpecialTrade trade = new SpecialTrade(currentPlayer, GameGUI.controller.getBank(), offering, requesting);
						GameGUI.controller.initiateTrade(trade);
						GameGUI.ResourcePanel.repaint();
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
		panel.add(offerButton, gbc_offerButton);

		confirmButton = new JButton("Confirm");
		confirmButton.setEnabled(false);
		GridBagConstraints gbc_confirmButton = new GridBagConstraints();
		gbc_confirmButton.weightx = 1.0;
		gbc_confirmButton.fill = GridBagConstraints.BOTH;
		gbc_confirmButton.insets = new Insets(0, 0, 0, 5);
		gbc_confirmButton.gridx = 1;
		gbc_confirmButton.gridy = 0;
		panel.add(confirmButton, gbc_confirmButton);

		counterOfferButton = new JButton("Counter Offer");
		counterOfferButton.setEnabled(false);
		GridBagConstraints gbc_counterOfferButton = new GridBagConstraints();
		gbc_counterOfferButton.weightx = 1.0;
		gbc_counterOfferButton.fill = GridBagConstraints.BOTH;
		gbc_counterOfferButton.insets = new Insets(0, 0, 0, 5);
		gbc_counterOfferButton.gridx = 2;
		gbc_counterOfferButton.gridy = 0;
		panel.add(counterOfferButton, gbc_counterOfferButton);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> {
			this.setVisible(false);
			this.setEnabled(false);
			clearAllPlayerResources();
			if (action != null) {
				action.accept(cancelButton);
			}
		});

		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.weightx = 1.0;
		gbc_cancelButton.fill = GridBagConstraints.BOTH;
		gbc_cancelButton.gridx = 3;
		gbc_cancelButton.gridy = 0;
		panel.add(cancelButton, gbc_cancelButton);
		this.setVisible(false);
		this.setEnabled(false);
	}
}