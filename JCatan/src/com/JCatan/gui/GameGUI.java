package com.JCatan.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.JCatan.BoardFactory;
import com.JCatan.Dice;
import com.JCatan.GameController;
import com.JCatan.GamePhase;
import com.JCatan.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

public class GameGUI extends JFrame {

	private JPanel contentPane;
	public static GameController controller;
	ImageIcon one = new ImageIcon("images/one.png");
	ImageIcon two = new ImageIcon("images/two.png");
	ImageIcon three = new ImageIcon("images/three.png");
	ImageIcon four = new ImageIcon("images/four.png");
	ImageIcon five = new ImageIcon("images/five.png");
	ImageIcon six = new ImageIcon("images/six.png");

	Image dieOne;
	Image dieTwo;
	
	int die1;
	int die2;

	public GameGUI(List<Player> players, BoardFactory bf) {
		controller = new GameController(players, bf);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1920, 1040);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane master = new JLayeredPane();
		master.setLocation(0, 0);
		master.setSize(1441, 867);
		contentPane.add(master);

		JPanel ChatPanel = new ChatPanel(controller.getChat());
		contentPane.add(ChatPanel);

		JPanel BoardPanel = new BoardPanel();
		BoardPanel.setBounds(0, 0, 1441, 867);
		master.add(BoardPanel, new Integer(0), 0);

		JPanel BankPanel = new BankPanel();
		contentPane.add(BankPanel);
		
		
		controller.getPlayer(0).setColor(Color.BLUE);
		JPanel Player1Panel = new PlayerPanel(1441, 490, 463, 126, controller.getPlayer(0));
		Player1Panel.setBackground(Color.ORANGE);
		contentPane.add(Player1Panel);

		controller.getPlayer(1).setColor(Color.RED);
		JPanel Player2Panel = new PlayerPanel(1441, 615, 463, 126, controller.getPlayer(1) );
		Player2Panel.setBackground(Color.PINK);
		contentPane.add(Player2Panel);

		controller.getPlayer(2).setColor(Color.ORANGE);
		JPanel Player3Panel = new PlayerPanel(1441, 741, 463, 126, controller.getPlayer(2));
		Player3Panel.setBackground(Color.ORANGE);
		contentPane.add(Player3Panel);

		controller.getPlayer(3).setColor(Color.WHITE);
		JPanel Player4Panel = new PlayerPanel(1441, 867, 463, 134, controller.getPlayer(3));
		Player4Panel.setBackground(Color.PINK);
		contentPane.add(Player4Panel);

		JPanel EndTurnPanel = new EndTurnPanel();
		contentPane.add(EndTurnPanel);
		EndTurnPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JButton endButton = new JButton("Roll Dice");
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch (controller.getGamePhase()) {
				case GAMEROLL:
					controller.gamePhaseRoll();
					die1 = Dice.getDie1();
					die2 = Dice.getDie2();
					switch (die1) {
					case 1:
						dieOne = one.getImage();
						break;
					case 2:
						dieOne = two.getImage();
						break;
					case 3:
						dieOne = three.getImage();
						break;
					case 4:
						dieOne = four.getImage();
						break;
					case 5:
						dieOne = five.getImage();
						break;
					case 6:
						dieOne = six.getImage();
						break;
					}
					switch (die2) {
					case 1:
						dieTwo = one.getImage();
						break;
					case 2:
						dieTwo = two.getImage();
						break;
					case 3:
						dieTwo = three.getImage();
						break;
					case 4:
						dieTwo = four.getImage();
						break;
					case 5:
						dieTwo = five.getImage();
						break;
					case 6:
						dieTwo = five.getImage();
						break;
					}
					endButton.setText("Start Build");
					controller.setGamePhase(GamePhase.GAMETRADE);
					controller.gamePhaseTrade();
					repaint();
					break;
				case GAMETRADE:
					endButton.setText("End Turn");
					controller.setGamePhase(GamePhase.GAMEBUILD);
					controller.gamePhaseBuild();
					break;
				case GAMEBUILD:
					endButton.setText("Roll Dice");
					controller.setGamePhase(GamePhase.GAMEROLL);
					controller.gamePhaseEnd();
					break;
				}
			}
		});
		endButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		EndTurnPanel.add(endButton);

		JPanel BuildingPanel = new BuildingPanel();
		contentPane.add(BuildingPanel);

		JPanel TradePanel = new TradePanel();
		contentPane.add(TradePanel);

		JPanel ResourcePanel = new ResourcesPanel();
		contentPane.add(ResourcePanel);

		JPanel diceOnePanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(dieOne, 0, 0, getWidth(), getHeight(), null);
			}
		};
		diceOnePanel.setBackground(Color.WHITE);
		diceOnePanel.setBounds(1314, 798, 56, 58);
		master.add(diceOnePanel, new Integer(1), 0);

		JPanel diceTwoPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(dieTwo, 0, 0, getWidth(), getHeight(), null);
			}
		};
		diceTwoPanel.setBackground(Color.WHITE);
		diceTwoPanel.setBounds(1379, 798, 56, 58);
		master.add(diceTwoPanel, new Integer(1), 0);
		
		controller.startGame();
	} 
}
