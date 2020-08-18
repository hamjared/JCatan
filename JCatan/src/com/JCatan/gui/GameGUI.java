package com.JCatan.gui;

import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.JCatan.BoardFactory;
import com.JCatan.Consumer;
import com.JCatan.Dice;
import com.JCatan.GameController;
import com.JCatan.GamePhase;
import com.JCatan.Player;
import com.JCatan.ResourceCard;
import com.JCatan.ResourceType;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;

public class GameGUI extends JFrame {

	private static final long serialVersionUID = -5012311744443108873L;
	private TradePanel tradePanel = null;
	private JPanel contentPane;
	private BoardPanel BoardPanel;
	public static GameController controller;
	private JLabel brickLabel;
	private JLabel woodLabel;
	private JLabel wheatLabel;
	private JLabel sheepLabel;
	private JLabel oreLabel;
	public static JButton endButton;
	public static JPanel ResourcePanel;
	public static JPanel Player1Panel;
	public static JPanel Player2Panel;
	public static JPanel Player3Panel;
	public static JPanel Player4Panel;
	

	ImageIcon one = new ImageIcon("images/one.png");
	ImageIcon two = new ImageIcon("images/two.png");
	ImageIcon three = new ImageIcon("images/three.png");
	ImageIcon four = new ImageIcon("images/four.png");
	ImageIcon five = new ImageIcon("images/five.png");
	ImageIcon six = new ImageIcon("images/six.png");

	ImageIcon brick = new ImageIcon("images/brick.jpg");
	ImageIcon ore = new ImageIcon("images/ore.jpg");
	ImageIcon sheep = new ImageIcon("images/sheep.jpg");
	ImageIcon wheat = new ImageIcon("images/wheat.jpg");
	ImageIcon wood = new ImageIcon("images/wood.jpg");

	ImageIcon bank = new ImageIcon("images/bank.png");

	Image dieOne;
	Image dieTwo;
	Image resourceCard;
	Image bankImg = bank.getImage();
	Image bankBrick = brick.getImage();
	Image bankOre = ore.getImage();
	Image bankSheep = sheep.getImage();
	Image bankWheat = wheat.getImage();
	Image bankWood = wood.getImage();

	int die1;
	int die2;
	int j = 0;
	int setupNum = 0;
	public static boolean setupReverse = false;

	@Override
	public void paint(Graphics g) {
		try {
			super.paint(g);
			revalidate();
		} finally {
			g.dispose();
		}
	}

	public GameGUI(List<Player> players, BoardFactory bf) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1920, 1040);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		controller = new GameController(players, bf);
		controller.setNotifyPlayer(
				s -> JOptionPane.showMessageDialog(this, s, "Trading Error", JOptionPane.WARNING_MESSAGE));

		tradePanel = new TradePanel(865, 0, 578, 402);
		tradePanel.setVisible(false);
		tradePanel.setEnabled(false);
		contentPane.add(tradePanel);

		JLayeredPane master = new JLayeredPane();
		master.setLocation(0, 0);
		master.setSize(1441, 867);
		contentPane.add(master);

		JPanel ChatPanel = new ChatPanel(controller.getChat());
		contentPane.add(ChatPanel);

		BoardPanel = new BoardPanel();
		BoardPanel.setBounds(0, 0, 1441, 867);
		master.add(BoardPanel, new Integer(0), 0);

		JPanel BankPanel = new BankPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(bankImg, 15, 15, 90, 90, null);
				g.drawImage(bankBrick, 150, 12, 38, 60, null);
				g.drawImage(bankWood, 210, 12, 38, 60, null);
				g.drawImage(bankWheat, 270, 12, 38, 60, null);
				g.drawImage(bankSheep, 330, 12, 38, 60, null);
				g.drawImage(bankOre, 390, 12, 38, 60, null);
				drawBankLabels();
			}
		};
		contentPane.add(BankPanel);
		BankPanel.setLayout(null);

		brickLabel = new JLabel("");

		brickLabel.setBounds(145, 92, 46, 14);
		brickLabel.setHorizontalAlignment(brickLabel.CENTER);
		BankPanel.add(brickLabel);

		woodLabel = new JLabel();
		woodLabel.setBounds(205, 92, 46, 14);
		woodLabel.setHorizontalAlignment(woodLabel.CENTER);
		BankPanel.add(woodLabel);

		wheatLabel = new JLabel("");
		wheatLabel.setBounds(265, 92, 46, 14);
		wheatLabel.setHorizontalAlignment(wheatLabel.CENTER);
		BankPanel.add(wheatLabel);

		sheepLabel = new JLabel("");
		sheepLabel.setBounds(325, 92, 46, 14);
		sheepLabel.setHorizontalAlignment(sheepLabel.CENTER);
		BankPanel.add(sheepLabel);

		oreLabel = new JLabel("");
		oreLabel.setBounds(385, 92, 46, 14);
		oreLabel.setHorizontalAlignment(oreLabel.CENTER);
		BankPanel.add(oreLabel);

		controller.getPlayer(0).setColor(Color.BLUE);
		Player1Panel = new PlayerPanel(1441, 490, 463, 126, controller.getPlayer(0));
		Player1Panel.setBackground(Color.ORANGE);
		contentPane.add(Player1Panel);

		controller.getPlayer(1).setColor(Color.RED);
		Player2Panel = new PlayerPanel(1441, 615, 463, 126, controller.getPlayer(1));
		Player2Panel.setBackground(Color.PINK);
		contentPane.add(Player2Panel);

		controller.getPlayer(2).setColor(Color.decode("#FFA500"));
		Player3Panel = new PlayerPanel(1441, 741, 463, 126, controller.getPlayer(2));
		Player3Panel.setBackground(Color.ORANGE);
		contentPane.add(Player3Panel);

		controller.getPlayer(3).setColor(Color.WHITE);
		Player4Panel = new PlayerPanel(1441, 867, 463, 134, controller.getPlayer(3));
		Player4Panel.setBackground(Color.PINK);
		contentPane.add(Player4Panel);

		JPanel devCardPanel = new DevCardPanel(1125, 675, 300, 100);
		devCardPanel.setVisible(false);
		devCardPanel.setEnabled(false);
		master.add(devCardPanel, new Integer(1), 0);

		JButton devCardPanelButton = new JButton("Play Dev Card");
		devCardPanelButton.setBounds(1150, 800, 150, 50);
		devCardPanelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				((DevCardPanel) devCardPanel).showPanel();

			}

		});
		master.add(devCardPanelButton, new Integer(1), 0);

		JPanel EndTurnPanel = new EndTurnPanel();
		contentPane.add(EndTurnPanel);
		EndTurnPanel.setLayout(new GridLayout(1, 0, 0, 0));
		endButton = new JButton("Start Setup");

		Consumer<JButton> turnOnEndButton = b -> endButton.setEnabled(true);
		tradePanel.setDelegate(turnOnEndButton);
		controller.setAction(t -> tradePanel.close());

		JButton tradeButton = new JButton("Trade");
		controller.setAction(t -> tradePanel.close());

		tradeButton.setEnabled(true);
		tradeButton.setVisible(true);
		tradeButton.setBounds(750, 867, 122, 134);
		tradeButton.addActionListener(e -> {
			tradePanel.setEnabled(true);
			tradePanel.setVisible(true);
			endButton.setEnabled(false);
		});
		contentPane.add(tradeButton);

		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((DevCardPanel) devCardPanel).hidePanel();
				switch (controller.getGamePhase()) {
				case SETUP:
					if (endButton.getText().equals("Build Settlement") || endButton.getText().equals("Start Setup")) {
						BoardPanel.buildSettlement();
						Player1Panel.repaint();
						Player2Panel.repaint();
						Player3Panel.repaint();
						Player4Panel.repaint();
						endButton.setEnabled(false);
						endButton.setText("Build Road");
					} else if (endButton.getText().equals("Build Road")) {
						BoardPanel.buildRoad();
						Player1Panel.repaint();
						Player2Panel.repaint();
						Player3Panel.repaint();
						Player4Panel.repaint();
						endButton.setEnabled(false);
						endButton.setText("End Turn");
					} else {
						if (setupNum == 3 && setupReverse == false) {
							controller.setCurPlayer(controller.getPlayers().get(setupNum));
							setupReverse = true;
							endButton.setText("Build Settlement");
						} else if (setupNum <= 3 && setupReverse == true) {
							if (setupNum == 0) {
								controller.setGamePhase(GamePhase.GAMEROLL);
								endButton.setText("Roll Dice");
							} else {
								controller.setCurPlayer(controller.getPlayers().get(setupNum - 1));
								setupNum--;
								endButton.setText("Build Settlement");
							}
						} else {
							controller.setCurPlayer(controller.getPlayers().get(setupNum + 1));
							setupNum++;
							endButton.setText("Build Settlement");
						}
					}
					break;
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

					if (die1 + die2 != 7) {
					  endButton.setText("End Turn");
					  controller.setGamePhase(GamePhase.GAMEMAIN);
					  controller.gamePhaseTrade();
					} else {
						endButton.setText("End Robber Move");
						controller.setGamePhase(GamePhase.ROBBERMOVE);
					}
					tradeButton.setEnabled(true);
					repaint();
					break;
				case GAMEMAIN:
					tradeButton.setEnabled(false);
					endButton.setText("Roll Dice");
					controller.setGamePhase(GamePhase.GAMEROLL);
					controller.gamePhaseEnd();
					repaint();
					break;
				case ROBBERMOVE:
					endButton.setText("End Turn");
					controller.setGamePhase(GamePhase.GAMEMAIN);
					controller.robberMovePhase();
					repaint();
					break;
				default:
					break;
				}
			}
		});
		endButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		EndTurnPanel.add(endButton);

		BuildingPanel BuildingPanel = new BuildingPanel(controller);
		contentPane.add(BuildingPanel);
		BuildingPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				int x = e.getX();
				int y = e.getY();

				if ((x >= 30 && x <= 60) && (y >= 20 && y <= 110)) {

					clickOnRoad();
				}
				if ((x >= 121 && x <= 167) && (y >= 43 && y <= 109)) {

					clickOnSettlement();
				} else if ((x >= 62 && x <= 188) && (y >= 53 && y <= 64)) {

					clickOnSettlement();
				} else if ((x >= 135 && x <= 155) && (y >= 20 && y <= 110)) {

					clickOnSettlement();
				}
				if ((x >= 243 && x <= 310) && (y >= 89 && y <= 110)) {

					clickOnCity();
				} else if ((x >= 243 && x <= 266) && (y >= 33 && y <= 110)) {

					clickOnCity();
				} else if ((x >= 220 && x <= 290) && (y >= 37 && y <= 65)) {

					clickOnCity();
				}

				buyDevCard();
			}
		});

		ResourcePanel = new ResourcesPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					for (int i = 0; i < controller.getCurPlayer().getResources().size(); i++) {
						ResourceCard resource = controller.getCurPlayer().getResources().get(i);
						switch (resource.getResourceType()) {
						case WOOD:
							resourceCard = wood.getImage();
							break;
						case SHEEP:
							resourceCard = sheep.getImage();
							break;
						case ORE:
							resourceCard = ore.getImage();
							break;
						case BRICK:
							resourceCard = brick.getImage();
							break;
						case WHEAT:
							resourceCard = wheat.getImage();
							break;
						default:
							break;
						}
						g.drawImage(resourceCard, j + 5, 18, 55, 95, null);
						j += 56;
					}
					j = 0;
				} catch (Exception e) {

				}
			}
		};
		ResourcePanel.setBounds(0, 867, 750, 134);
		contentPane.add(ResourcePanel);
		repaint();

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

	protected void buyDevCard() {
		// TODO GameGUI: buyDevCard

	}

	protected void clickOnCity() {
		BoardPanel.buildCity();
		endButton.setEnabled(false);


	}

	protected void clickOnSettlement() {
		BoardPanel.buildSettlement();
		endButton.setEnabled(false);

	}

	protected void clickOnRoad() {
		BoardPanel.buildRoad();
		endButton.setEnabled(false);

	}

	private void drawBankLabels() {
		brickLabel.setText("" + controller.getBank().getNumberOfResourceCardsRemaining(ResourceType.BRICK));
		woodLabel.setText("" + controller.getBank().getNumberOfResourceCardsRemaining(ResourceType.WOOD));
		wheatLabel.setText("" + controller.getBank().getNumberOfResourceCardsRemaining(ResourceType.WHEAT));
		sheepLabel.setText("" + controller.getBank().getNumberOfResourceCardsRemaining(ResourceType.SHEEP));
		oreLabel.setText("" + controller.getBank().getNumberOfResourceCardsRemaining(ResourceType.ORE));
	}
}
