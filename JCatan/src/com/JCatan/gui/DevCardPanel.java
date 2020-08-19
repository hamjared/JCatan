package com.JCatan.gui;

import javax.swing.JPanel;

import com.JCatan.Bank;
import com.JCatan.DevCardAction;
import com.JCatan.DevCardActionBuilder;
import com.JCatan.DevelopmentCard;
import com.JCatan.InvalidDevCardUseException;
import com.JCatan.KnightDevelopmentCard;
import com.JCatan.MonopolyDevelopmentCard;
import com.JCatan.Player;
import com.JCatan.ResourceType;
import com.JCatan.RoadBuildingDevelopmentCard;
import com.JCatan.YearOfPlentyDevelopmentCard;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DevCardPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8147720823464828336L;
	private JComboBox resourceComboBox1;
	private JComboBox playerComboBox;
	private JComboBox resourceComboBox2;
	private JLabel resources1Label;
	private JLabel playerLabel;
	
	private JComboBox comboBox;
	private JLabel resource2Label;
	
	public DevCardPanel(int x, int y, int width, int height) {
		
		setBounds(1125, 650, 300, 125);
		
		JButton hideButton = new JButton("Hide");
		hideButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				setVisible(false);
				setEnabled(false);
			}
		}); 
		hideButton.setBounds(221, 91, 69, 23);
		add(hideButton);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDevCardOptions();
			}
		});
		comboBox.setBounds(10, 11, 181, 22);
		add(comboBox);
		
		JButton btnNewButton = new JButton("Play");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DevelopmentCard card = (DevelopmentCard) comboBox.getSelectedItem();
				try {
					DevCardAction devCardAction = makeDevCardAction(card);
					if(devCardAction == null) {
						GameGUI.controller.getChat().addToChat("Error playing dev card, please select correct options");
					}
					GameGUI.controller.getCurPlayer().playDevelopmentCard(card, devCardAction);
					GameGUI.controller.getChat().addToChat(GameGUI.controller.getCurPlayer().getName() + " played " + card);
					getParent().repaint();
					updateComboBox();
				} catch (InvalidDevCardUseException e1) {
					// TODO Auto-generated catch block 
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(221, 11, 69, 23);
		add(btnNewButton);
		setLayout(null);
		
		playerComboBox = new JComboBox();
		playerComboBox.setBounds(63, 44, 128, 22);
		add(playerComboBox);
		
		playerLabel = new JLabel("Player");
		playerLabel.setBounds(10, 48, 46, 14);
		add(playerLabel);
		
		resource2Label = new JLabel("Resource");
		resource2Label.setBounds(10, 48, 46, 14);
		add(resource2Label);
		
		resourceComboBox1 = new JComboBox();
		resourceComboBox1.setBounds(63, 78, 128, 22);
		add(resourceComboBox1);
		
		resourceComboBox2 = new JComboBox();
		resourceComboBox2.setBounds(63, 44, 128, 22);
		add(resourceComboBox2);
		
		resources1Label = new JLabel("Resource");
		resources1Label.setBounds(7, 82, 46, 14);
		add(resources1Label);
		
		showDevCardOptions();
		
		
	}
	

	
	protected void showDevCardOptions() {
		DevelopmentCard card = (DevelopmentCard) comboBox.getSelectedItem();
		
		if(card instanceof KnightDevelopmentCard) {
			renderComboBoxes(true, false, false);
			
		}
		else if(card instanceof YearOfPlentyDevelopmentCard) {
			renderComboBoxes(false, true, true);
		}
		
		else if(card instanceof MonopolyDevelopmentCard) {
			renderComboBoxes(true, true, false);
		}
		else if(card instanceof RoadBuildingDevelopmentCard) {
			renderComboBoxes(false, false, false);
		}
		else {
			renderComboBoxes(false, false, false);
		}
		
		
		
	}



	protected DevCardAction makeDevCardAction(DevelopmentCard card) {
		if(card instanceof KnightDevelopmentCard) {
			renderComboBoxes(true, false, false);
			Player stealPlayer = (Player) playerComboBox.getSelectedItem();
			if(stealPlayer == null) {
				return null;
			}
			return new DevCardActionBuilder().curPlayer(GameGUI.controller.getCurPlayer())
					.stealPlayer(stealPlayer)
					.build();
		}
		if(card instanceof YearOfPlentyDevelopmentCard) {
			Bank bank = GameGUI.controller.getBank();
			ResourceType rt = (ResourceType) resourceComboBox1.getSelectedItem();
			ResourceType rt2 = (ResourceType) resourceComboBox2.getSelectedItem();
			return new DevCardActionBuilder().curPlayer(GameGUI.controller.getCurPlayer())
					.stealResourceType1(rt)
					.stealResourceType2(rt2)
					.bank(bank)
					.build();
		}
		
		if(card instanceof MonopolyDevelopmentCard) {
			Player stealPlayer = (Player) playerComboBox.getSelectedItem();
			ResourceType rt = (ResourceType) resourceComboBox1.getSelectedItem();
			if(stealPlayer == null || rt == null) {
				return null;
			}
			return new DevCardActionBuilder().curPlayer(GameGUI.controller.getCurPlayer())
					.stealResourceType1(rt)
					.stealPlayer(stealPlayer)
					.build();
		}
		if(card instanceof RoadBuildingDevelopmentCard) {
			Bank bank = GameGUI.controller.getBank();
			return new DevCardActionBuilder().curPlayer(GameGUI.controller.getCurPlayer())
					.bank(bank)
					.build();
		}
		
		
		
		
		return null;
	}



	private void renderComboBoxes(boolean showPlayers, boolean showResources1, boolean showResources2) {
		playerComboBox.setEnabled(false);
		playerComboBox.setVisible(false);
		playerLabel.setVisible(false);
		resourceComboBox1.setEnabled(false);
		resourceComboBox1.setVisible(false);
		resources1Label.setVisible(false);
		resourceComboBox2.setEnabled(false);
		resourceComboBox2.setVisible(false);
		resource2Label.setVisible(false);
		if(showPlayers) {
			playerComboBox.setEnabled(true);
			playerComboBox.setVisible(true);
			playerLabel.setVisible(true);
		}
		if(showResources1) {
			resourceComboBox1.setEnabled(true);
			resourceComboBox1.setVisible(true);
			resourceComboBox1.setVisible(true);
			
		}
		
		if(showResources2) {
			resourceComboBox2.setEnabled(true);
			resourceComboBox2.setVisible(true);
			resource2Label.setVisible(true);
		}
		
	}



	public void showPanel() {
		setEnabled(true);
		setVisible(true);
		updateComboBox();
		updateResourceComboBox();
		updatePlayerComboBox();
		showDevCardOptions();
	}
	
	public void hidePanel() {
		setEnabled(false);
		setVisible(false);
	}
	
	@SuppressWarnings("unchecked")
	private void updateComboBox() {
		comboBox.removeAllItems();
		for(DevelopmentCard card: GameGUI.controller.getCurPlayer().getDevCards()) {
			if(! card.isHasBeenPlayed()) {
				comboBox.addItem(card);
			}
			
		}
	}
	
	@SuppressWarnings("unchecked")
	private void updatePlayerComboBox() {
		playerComboBox.removeAllItems();
		for(Player player: GameGUI.controller.getPlayers()) {
			if(! player.equals(GameGUI.controller.getCurPlayer())) {
				playerComboBox.addItem(player);
			}
			
		}
	}
	
	@SuppressWarnings("unchecked")
	private void updateResourceComboBox() {
		resourceComboBox1.removeAllItems();
		resourceComboBox1.addItem(ResourceType.BRICK);
		resourceComboBox1.addItem(ResourceType.WHEAT);
		resourceComboBox1.addItem(ResourceType.WOOD);
		resourceComboBox1.addItem(ResourceType.SHEEP);
		resourceComboBox1.addItem(ResourceType.ORE);
		
		resourceComboBox2.removeAllItems();
		resourceComboBox2.addItem(ResourceType.BRICK);
		resourceComboBox2.addItem(ResourceType.WHEAT);
		resourceComboBox2.addItem(ResourceType.WOOD);
		resourceComboBox2.addItem(ResourceType.SHEEP);
		resourceComboBox2.addItem(ResourceType.ORE);
			
			
		
	}


}
