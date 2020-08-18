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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class DevCardPanel extends JPanel{
	
	private JComboBox comboBox;
	
	public DevCardPanel(int x, int y, int width, int height) {
		
		setBounds(1125, 675, 300, 100);
		
		JButton hideButton = new JButton("Hide");
		hideButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				setVisible(false);
				setEnabled(false);
			}
		}); 
		hideButton.setBounds(221, 66, 69, 23);
		add(hideButton);
		
		comboBox = new JComboBox();
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
		
		JLabel lblNewLabel = new JLabel("Player");
		lblNewLabel.setBounds(10, 48, 46, 14);
		add(lblNewLabel);
		
		resourceComboBox = new JComboBox();
		resourceComboBox.setBounds(63, 78, 128, 22);
		add(resourceComboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Resource");
		lblNewLabel_1.setBounds(7, 82, 46, 14);
		add(lblNewLabel_1);
	}
	

	
	protected DevCardAction makeDevCardAction(DevelopmentCard card) {
		if(card instanceof KnightDevelopmentCard) {
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
			ResourceType rt = (ResourceType) resourceComboBox.getSelectedItem();
			return new DevCardActionBuilder().curPlayer(GameGUI.controller.getCurPlayer())
					.stealResourceType(rt)
					.bank(bank)
					.build();
		}
		
		if(card instanceof MonopolyDevelopmentCard) {
			Player stealPlayer = (Player) playerComboBox.getSelectedItem();
			ResourceType rt = (ResourceType) resourceComboBox.getSelectedItem();
			if(stealPlayer == null || rt == null) {
				return null;
			}
			return new DevCardActionBuilder().curPlayer(GameGUI.controller.getCurPlayer())
					.stealResourceType(rt)
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



	public void showPanel() {
		setEnabled(true);
		setVisible(true);
		updateComboBox();
		updateResourceComboBox();
		updatePlayerComboBox();
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
		resourceComboBox.removeAllItems();
		resourceComboBox.addItem(ResourceType.BRICK);
		resourceComboBox.addItem(ResourceType.WHEAT);
		resourceComboBox.addItem(ResourceType.WOOD);
		resourceComboBox.addItem(ResourceType.SHEEP);
		resourceComboBox.addItem(ResourceType.ORE);
			
			
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8147720823464828336L;
	private JComboBox resourceComboBox;
	private JComboBox playerComboBox;
}
