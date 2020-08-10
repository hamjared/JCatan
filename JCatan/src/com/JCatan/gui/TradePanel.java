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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.border.TitledBorder;

import com.JCatan.DomesticTrade;
import com.JCatan.MaritimeTrade;
import com.JCatan.Player;
import com.JCatan.ResourceType;

import javax.swing.border.BevelBorder;
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
	private String selectedPlayerlabel = "Selected Player";
	
	public void setEventForButtons(ActionListener list) {
		confirmButton.addActionListener(list);
		offerButton.addActionListener(list);
		counterOfferButton.addActionListener(list);
	}
	
	private void setTraders() {
		JCheckBox maritimeTrade = new JCheckBox("Maritime");
		maritimeTrade.setSelected(false);
		buttonToggleGroup.add(maritimeTrade);
		traders.add(maritimeTrade);
		
		JCheckBox specialTrade = new JCheckBox("Special Trade");
		maritimeTrade.setSelected(false);
		buttonToggleGroup.add(specialTrade);
		traders.add(specialTrade);
		
		this.currentPlayer = GameGUI.controller.getCurrentPlayer();
		this.participants = GameGUI.controller.getPlayers().stream()
				  .filter(t -> !Objects.equals(t, currentPlayer))
				  .collect(Collectors.toList());
		
		for(Player player : participants) {
			JCheckBox temp = new JCheckBox(player.getName());
			temp.setSelected(false);
			temp.addActionListener(e ->{
				JCheckBox box = (JCheckBox) e.getSource();
				
				selectedPlayer = GameGUI.controller.getPlayers().stream()
				  .filter(t -> t.getName() == box.getText()).findFirst().orElse(null);

				clearSelectedPlayerResources();
				setPlayersResources(selectedPlayer);
			});
			traders.add(temp);
			buttonToggleGroup.add(temp);
		}
	}
	
	@Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setPlayersResources(currentPlayer);
		playerOffer.revalidate();
	}
		
	private void clearSelectedPlayerResources() {
		Component[] components = playerOffer.getComponents();
		for(Component comp : components) {
			if(comp.getName() == selectedPlayerlabel)
				playerOffer.remove(comp);
		}
		playerOffer.revalidate();
		playerOffer.repaint();
	}
	
	private void clearAllPlayerResources() {
		playerOffer.removeAll();
		playerOffer.revalidate();
		playerOffer.repaint();
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
	
	private void createButtonPanel(JLabel playerResourceTotal, String name, int x, int y) {
		JPanel panel_1 = new JPanel();
		panel_1.setName(name);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.CENTER;
		gbc_panel_1.gridx = x;
		gbc_panel_1.gridy = y;
		playerOffer.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, 1.0, 1.0};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0};
		panel_1.setLayout(gbl_panel_1);
		x++;
		
		JButton playerDecResource = new JButton("-");
		playerDecResource.addActionListener(e -> {
			String val = playerResourceTotal.getText();
			Integer temp = Integer.valueOf(val);
			if(temp <= 0)
				return;
			playerResourceTotal.setText((--temp).toString());
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
			playerResourceTotal.setText((++temp).toString());
		});
		
		GridBagConstraints gbc_playerIncResource_1 = new GridBagConstraints();
		gbc_playerIncResource_1.gridx = x;
		gbc_playerIncResource_1.gridy = y;
		panel_1.add(playerIncResource_1, gbc_playerIncResource_1);
	}
	
	private void createTradeIcons(String resourceName, String name, String value, int i, int j) {
		createLabel(resourceName, name, i, j);
		JLabel playerResourceTotal = createLabel(value, name, ++i, j);
		createButtonPanel(playerResourceTotal, name, ++i, j);
	}
	
	private void setPlayersResources(Player player) {
		int j=0;
		Map<ResourceType, Integer> resources = player.getUniqueResourcesCount();
		
		for(Map.Entry<ResourceType, Integer> entry: resources.entrySet()) {
			
			GridBagLayout temp = (GridBagLayout)playerOffer.getLayout();
			int i= (player == currentPlayer)? 0 : temp.columnWidths.length/2+1;
			String cardName = entry.getKey().name();
			String total = entry.getValue().toString();
			String labelName = (player == currentPlayer) ? "" : selectedPlayerlabel;
			createTradeIcons(cardName, labelName, total, i, j);
			j++;
		}
	}

	/**
	 * Create the panel.
	 */
	public TradePanel(int x, int y, int width, int height) {
		this.setBounds(x, y, width, 379);
		selectedPlayer=null;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{569, 403, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		traders = new JPanel();
		traders.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Participants", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_traders = new GridBagConstraints();
		gbc_traders.gridwidth = 2;
		gbc_traders.weightx = 1.0;
		gbc_traders.insets = new Insets(0, 0, 5, 0);
		gbc_traders.fill = GridBagConstraints.BOTH;
		gbc_traders.gridx = 0;
		gbc_traders.gridy = 0;
		
		setTraders();
		add(traders, gbc_traders);
		
		playerOffer = new JPanel();		
		playerOffer.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Player's Offer", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_playerOffer = new GridBagConstraints();
		gbc_playerOffer.weightx = 1.0;
		gbc_playerOffer.insets = new Insets(0, 0, 5, 5);
		gbc_playerOffer.fill = GridBagConstraints.BOTH;
		gbc_playerOffer.gridx = 0;
		gbc_playerOffer.gridy = 1;
		GridBagLayout gbl_playerOffer = new GridBagLayout();
		gbl_playerOffer.columnWidths = new int[] {0, 0, 0, 0, 0, 0};
		gbl_playerOffer.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_playerOffer.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0};
		gbl_playerOffer.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		playerOffer.setLayout(gbl_playerOffer);
		add(playerOffer, gbc_playerOffer);
				
		theirOffer = new JPanel();
		theirOffer.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Participant's Offer", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_theirOffer = new GridBagConstraints();
		gbc_theirOffer.weightx = 1.0;
		gbc_theirOffer.insets = new Insets(0, 0, 5, 0);
		gbc_theirOffer.fill = GridBagConstraints.BOTH;
		gbc_theirOffer.gridx = 1;
		gbc_theirOffer.gridy = 1;
		add(theirOffer, gbc_theirOffer);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.weightx = 1.0;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{114, 106, 143, 109, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		offerButton = new JButton("Offer");
		offerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Get the player selected and offer the potential goods to selected player...
				Component[] people = traders.getComponents();
				
				for(Component comp : people) {
					JCheckBox trader = (JCheckBox)comp;
					
					
					if(trader != null && trader.isSelected()) {
						String name = trader.getText();
						
						//Player Trade
						if(name != "Maritime" && name != "Special Trade") {
							Player player = participants.stream().filter(p -> p.getName() == name).findFirst().orElse(null);
							
							if(player == null)
								//Throw and error here...
								return;
							
							DomesticTrade trade = new DomesticTrade(currentPlayer, null, null);
							GameGUI.controller.turnOnPlayersTradePanel(trade, player);
							break;
						}
						
						//Maritime Trade
						if(name == "Maritime") {
							MaritimeTrade trade = new MaritimeTrade(currentPlayer, null, null);
							//Let banker trade with player...
						}
						
						//Special Trade
						if(name == "Special Trade") {
							
						}
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