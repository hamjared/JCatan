package com.JCatan.gui;

import javax.swing.JPanel;

import com.JCatan.DevelopmentCard;
import com.JCatan.InvalidDevCardUseException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

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
		hideButton.setBounds(201, 66, 89, 23);
		add(hideButton);
		
		comboBox = new JComboBox();
		comboBox.setBounds(10, 11, 181, 22);
		add(comboBox);
		
		JButton btnNewButton = new JButton("Play");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DevelopmentCard card = (DevelopmentCard) comboBox.getSelectedItem();
				try {
					GameGUI.controller.getCurPlayer().playDevelopmentCard(card);
					GameGUI.controller.getChat().addToChat(GameGUI.controller.getCurPlayer().getName() + " played " + card);
					getParent().repaint();
					updateComboBox();
				} catch (InvalidDevCardUseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(201, 11, 89, 23);
		add(btnNewButton);
		setLayout(null);
	}
	

	
	public void showPanel() {
		setEnabled(true);
		setVisible(true);
		updateComboBox();
	}
	
	public void hidePanel() {
		setEnabled(false);
		setVisible(false);
	}
	
	private void updateComboBox() {
		comboBox.removeAllItems();
		for(DevelopmentCard card: GameGUI.controller.getCurPlayer().getDevCards()) {
			if(! card.isHasBeenPlayed()) {
				comboBox.addItem(card);
			}
			
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8147720823464828336L;
}
