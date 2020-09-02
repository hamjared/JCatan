package com.JCatan.gui;

import javax.swing.JPanel;

import com.JCatan.Bank;
import com.JCatan.DevCardAction;
import com.JCatan.DevCardActionBuilder;
import com.JCatan.DevelopmentCard;
import com.JCatan.GamePhase;
import com.JCatan.InvalidDevCardUseException;
import com.JCatan.KnightDevelopmentCard;
import com.JCatan.MonopolyDevelopmentCard;
import com.JCatan.Player;
import com.JCatan.ResourceType;
import com.JCatan.RoadBuildingDevelopmentCard;
import com.JCatan.VictoryPointDevelopmentCard;
import com.JCatan.YearOfPlentyDevelopmentCard;
import com.JCatan.server.Message;
import com.JCatan.server.MessageBuilder;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DevCardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8147720823464828336L;
	private JComboBox resourceComboBox1;
	private JComboBox playerComboBox;
	private JComboBox resourceComboBox2;
	private JLabel resources1Label;
	private JLabel playerLabel;
	private BoardPanel boardPanel;
	JButton playButton;

	private JComboBox comboBox;
	private JLabel resource2Label;

	public DevCardPanel(int x, int y, int width, int height, BoardPanel boardPanel) {
		this.boardPanel = boardPanel;

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
				grayOutPlayButton();
			}
		});
		comboBox.setBounds(10, 11, 181, 22);
		add(comboBox);

		 playButton = new JButton("Play");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Play development card pressed, Game Phase: "
						+ GameGUI.gameClient.getController().getGamePhase());
				DevelopmentCard card = (DevelopmentCard) comboBox.getSelectedItem();
				if (GameGUI.gameClient.getController().getGamePhase().equals(GamePhase.GAMEMAIN)) {
					DevCardAction devCardAction = makeDevCardAction(card);
					if (devCardAction == null) {
						GameGUI.controller.getChat().addToChat("Error playing dev card, please select correct options");
					}

					Message msg = new MessageBuilder().action(Message.Action.PlayDevelopmentCard)
							.devCardAction(devCardAction).devCard(card).build();
					GameGUI.gameClient.sendMessage(msg);
//							GameGUI.controller.getCurPlayer().playDevelopmentCard(card, devCardAction);
//							GameGUI.controller.getChat()
//									.addToChat(GameGUI.controller.getCurPlayer().getName() + " played " + card);

					updateComboBox();

					getParent().getParent().repaint();
				}
			}
		});
		playButton.setBounds(221, 11, 69, 23);
		add(playButton);
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

	protected void grayOutPlayButton() {
		DevelopmentCard card = (DevelopmentCard) comboBox.getSelectedItem();
		if(card == null) {
			playButton.setEnabled(false);
			return;
		}
		if(card.isCanBePlayed()) {
			playButton.setEnabled(true);
		}
		else {
			playButton.setEnabled(false);
		}
		
	}

	protected void showDevCardOptions() {
		DevelopmentCard card = (DevelopmentCard) comboBox.getSelectedItem();

		if (card instanceof KnightDevelopmentCard) {
			renderComboBoxes(false, false, false);

		} else if (card instanceof YearOfPlentyDevelopmentCard) {
			renderComboBoxes(false, true, true);
		}

		else if (card instanceof MonopolyDevelopmentCard) {
			renderComboBoxes(true, true, false);
		} else if (card instanceof RoadBuildingDevelopmentCard) {
			renderComboBoxes(false, false, false);
		} else {
			renderComboBoxes(false, false, false);
		}

	}

	protected DevCardAction makeDevCardAction(DevelopmentCard card) {
		if (card instanceof KnightDevelopmentCard) {
			GameGUI.controller.setGamePhase(GamePhase.ROBBERMOVE);
			GameGUI.gameClient.getController().setGamePhase(GamePhase.ROBBERMOVE);
			System.out
					.println("Knight card played, is robber moving: " + GameGUI.controller.getBoard().isRobberMoving());
			return new DevCardActionBuilder().curPlayer(GameGUI.controller.getCurPlayer()).build();
		}
		if (card instanceof YearOfPlentyDevelopmentCard) {
			Bank bank = GameGUI.controller.getBank();
			ResourceType rt = (ResourceType) resourceComboBox1.getSelectedItem();
			ResourceType rt2 = (ResourceType) resourceComboBox2.getSelectedItem();
			return new DevCardActionBuilder().curPlayer(GameGUI.controller.getCurPlayer()).stealResourceType1(rt)
					.stealResourceType2(rt2).bank(bank).build();
		}

		if (card instanceof MonopolyDevelopmentCard) {
			Player stealPlayer = (Player) playerComboBox.getSelectedItem();
			ResourceType rt = (ResourceType) resourceComboBox1.getSelectedItem();
			if (stealPlayer == null || rt == null) {
				return null;
			}
			return new DevCardActionBuilder().curPlayer(GameGUI.controller.getCurPlayer()).stealResourceType1(rt)
					.stealPlayer(stealPlayer).build();
		}
		if (card instanceof RoadBuildingDevelopmentCard) {
			Bank bank = GameGUI.controller.getBank();
			this.boardPanel.buildRoad();

			return new DevCardActionBuilder().curPlayer(GameGUI.controller.getCurPlayer()).bank(bank).build();
		}

		if (card instanceof VictoryPointDevelopmentCard) {
			return new DevCardActionBuilder().build();
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
		if (showPlayers) {
			playerComboBox.setEnabled(true);
			playerComboBox.setVisible(true);
			playerLabel.setVisible(true);
		}
		if (showResources1) {
			resourceComboBox1.setEnabled(true);
			resourceComboBox1.setVisible(true);
			resourceComboBox1.setVisible(true);

		}

		if (showResources2) {
			resourceComboBox2.setEnabled(true);
			resourceComboBox2.setVisible(true);
			resource2Label.setVisible(true);
		}

	}

	public void showPanel() {
		
		updateComboBox();
		updateResourceComboBox();
		updatePlayerComboBox();
		showDevCardOptions();
		grayOutPlayButton();
		setEnabled(true);
		setVisible(true);
	}

	public void hidePanel() {
		setEnabled(false);
		setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void updateComboBox() {
		comboBox.removeAllItems();
		for (DevelopmentCard card : GameGUI.controller.getCurPlayer().getDevCards()) {
			if (!card.isHasBeenPlayed()) {
				comboBox.addItem(card);
			}

		}
	}

	@SuppressWarnings("unchecked")
	private void updatePlayerComboBox() {
		playerComboBox.removeAllItems();
		for (Player player : GameGUI.controller.getPlayers()) {
			if (!player.equals(GameGUI.controller.getCurPlayer())) {
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
