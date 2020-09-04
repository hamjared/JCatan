package com.JCatan.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.JCatan.BoardFactory;
import com.JCatan.Consumer;
import com.JCatan.Dice;
import com.JCatan.GameController;
import com.JCatan.GamePhase;
import com.JCatan.Player;
import com.JCatan.ResourceCard;
import com.JCatan.ResourceType;
import com.JCatan.Timer;
import com.JCatan.client.GameClient;
import com.JCatan.server.Message;
import com.JCatan.server.MessageBuilder;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
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
	JLayeredPane master;
	public static Player myPlayer;
	public static GameClient gameClient;
	public static JButton endButton;
	public static JPanel ResourcePanel;
	public static JPanel Player1Panel;
	public static JPanel Player2Panel;
	public static JPanel Player3Panel;
	public static JPanel Player4Panel;
	JPanel devCardPanel;
	JPanel ChatPanel;
	private JButton tradeButton;

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
	List<Rectangle> recs = new ArrayList<>();
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

	public GameGUI() {

	}

	public void setMyPlayer(Player player) {
		myPlayer = player;
	}

	public void setGameClient(GameClient gc) {
		gameClient = gc;
	}

	public void updateGameController(GameController gc) {
		controller = gc;
		if (gc.getCurPlayer().equals(myPlayer)) {
			enableAll();
		} else {
			disableAll();
		}
	}

	public void notifyPlayerBankAcceptedTrade(String s) {
		JOptionPane.showMessageDialog(this, s, "Trade Accepted", JOptionPane.PLAIN_MESSAGE);
		tradePanel.close();
	}
	
	public void notifyPlayerBadTrade(String s) {
		JOptionPane.showMessageDialog(this, s, "Trading Error", JOptionPane.WARNING_MESSAGE);
		tradePanel.resetFromBadTrade();
	}
	
	public void notifyPlayerDeclinedTrade(String s) {
		JOptionPane.showMessageDialog(this, s, "Trading Error", JOptionPane.WARNING_MESSAGE);
		tradePanel.resetFromBadTrade();
		if(!myPlayer.equals(controller.getCurPlayer())) {
			tradePanel.close();
		}
	}

	public void initialize(GameController gc) {

		controller = gc;
		controller.setRefreshScreenDelegate(x -> {
			ResourcePanel.repaint();
			Player1Panel.repaint();
			Player2Panel.repaint();
			Player3Panel.repaint();
			Player4Panel.repaint();
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1920, 1040);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tradePanel = new TradePanel(865, 0, 578, 402);
		tradePanel.setVisible(false);
		tradePanel.setEnabled(false);
		contentPane.add(tradePanel);

		master = new JLayeredPane();
		master.setLocation(0, 0);
		master.setSize(1441, 867);
		contentPane.add(master);

		Border border = new LineBorder(Color.BLACK, 2, true);

		ChatPanel = new ChatPanel(controller.getChat());
		contentPane.add(ChatPanel);
		ChatPanel.setBackground(Color.decode("#D3D3D3"));
		ChatPanel.setBorder(border);

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
		BankPanel.setBackground(Color.decode("#87ceeb"));
		BankPanel.setBorder(border);
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

		Player1Panel = new PlayerPanel(1441, 490, 463, 126, controller.getPlayer(0));
		Player1Panel.setBackground(Color.decode("#87ceeb"));
		Player1Panel.setBorder(border);
		contentPane.add(Player1Panel);

		Player2Panel = new PlayerPanel(1441, 615, 463, 126, controller.getPlayer(1));
		Player2Panel.setBackground(Color.decode("#87ceeb"));
		Player2Panel.setBorder(border);
		contentPane.add(Player2Panel);

		// controller.getPlayer(2).setColor(Color.decode("#FFA500"));

		Player3Panel = new PlayerPanel(1441, 741, 463, 126, controller.getPlayer(2));
		Player3Panel.setBackground(Color.decode("#87ceeb"));
		Player3Panel.setBorder(border);
		contentPane.add(Player3Panel);

		Player4Panel = new PlayerPanel(1441, 867, 463, 134, controller.getPlayer(3));
		Player4Panel.setBackground(Color.decode("#87ceeb"));
		Player4Panel.setBorder(border);
		contentPane.add(Player4Panel);

		devCardPanel = new DevCardPanel(1125, 675, 300, 100, this.BoardPanel);
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
		endButton.setBorder(border);

		Consumer<JButton> turnOnEndButton = b -> {
			if(myPlayer.equals(controller.getCurPlayer())) {
				endButton.setEnabled(true);
				tradeButton.setEnabled(true);
			}
		};
		tradePanel.setDelegate(turnOnEndButton);
		controller.setAction(t -> tradePanel.close());

		tradeButton = new JButton("Trade");
		controller.setAction(t -> tradePanel.close());

		tradeButton.setEnabled(false);
		tradeButton.setVisible(true);
		tradeButton.setBounds(750, 867, 122, 134);
		tradeButton.addActionListener(e -> {
			tradePanel.setEnabled(true);
			tradePanel.setVisible(true);
			endButton.setEnabled(false);
			tradeButton.setEnabled(false);
		});
		contentPane.add(tradeButton);
		tradeButton.setBorder(border);

		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((DevCardPanel) devCardPanel).hidePanel();

				switch (controller.getGamePhase()) {
				case SETUP:
					if (endButton.getText().equals("Build Settlement") || endButton.getText().equals("Start Setup")) {
						setTurnColor();
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
						Message msg = new MessageBuilder().action(Message.Action.EndSetupTurn).build();
						gameClient.sendMessage(msg);
						if (setupNum == 1) {
							endButton.setText("Roll Dice");
							tradeButton.setEnabled(false);
							controller.setGamePhase(GamePhase.GAMEROLL);
						} else {
							controller.setCurPlayer(controller.getPlayers().get(setupNum + 1));
							setupNum++;
							endButton.setText("Build Settlement");
						}
					}
					break;
				case GAMEROLL:
					Message msg = new MessageBuilder().action(Message.Action.RollDice).build();
					gameClient.sendMessage(msg);
					break;
				case GAMEMAIN:
					Message m = new MessageBuilder().action(Message.Action.EndTurn).build();
					gameClient.sendMessage(m);
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
		BuildingPanel.setBackground(Color.decode("#CC9966"));
		BuildingPanel.setBorder(border);
		BuildingPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (controller.getCurPlayer().equals(myPlayer)) {
					if (controller.getGamePhase().equals(GamePhase.GAMEMAIN)) {
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
					}
				}
			}
		});

		ResourcePanel = new ResourcesPanel();
		ResourcePanel.setBorder(border);
		contentPane.add(ResourcePanel);
		repaint();

		JPanel diceOnePanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(dieOne, 0, 0, getWidth(), getHeight(), null);
			}
		};
		diceOnePanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Message msg = new MessageBuilder().action(Message.Action.DiceData).build();
				gameClient.sendMessage(msg);
				
			}
			
		});
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

		disableAll();

	}

	protected void clickOnCity() {
		BoardPanel.buildCity();
		if (controller.getGamePhase().equals(GamePhase.SETUP)) {
			endButton.setEnabled(false);
		}

	}

	protected void clickOnSettlement() {
		BoardPanel.buildSettlement();
		if (controller.getGamePhase().equals(GamePhase.SETUP)) {
			endButton.setEnabled(false);
		}

	}

	protected void clickOnRoad() {
		BoardPanel.buildRoad();
		if (controller.getGamePhase().equals(GamePhase.SETUP)) {
			endButton.setEnabled(false);
		}

	}

	private void drawBankLabels() {
		brickLabel.setText("" + controller.getBank().getNumberOfResourceCardsRemaining(ResourceType.BRICK));
		woodLabel.setText("" + controller.getBank().getNumberOfResourceCardsRemaining(ResourceType.WOOD));
		wheatLabel.setText("" + controller.getBank().getNumberOfResourceCardsRemaining(ResourceType.WHEAT));
		sheepLabel.setText("" + controller.getBank().getNumberOfResourceCardsRemaining(ResourceType.SHEEP));
		oreLabel.setText("" + controller.getBank().getNumberOfResourceCardsRemaining(ResourceType.ORE));
	}

	public void disableAll() {

		for (Component c : contentPane.getComponents()) {
			c.setEnabled(false);
			if (c instanceof JPanel) {
				JPanel p = (JPanel) c;
				for (Component c2 : p.getComponents()) {
					c2.setEnabled(false);
				}
			}
		}

		for (Component c : master.getComponents()) {
			c.setEnabled(false);
		}
		
		ChatPanel.setEnabled(true);
		for (Component c : ChatPanel.getComponents()) {
			c.setEnabled(true);
		}

	}

	public void enableAll() {
		boolean isTradeButtonEnabled = tradeButton.isEnabled();
		setTurnColor();
		
		for (Component c : contentPane.getComponents()) {
			c.setEnabled(true);
			if (c instanceof JPanel) {
				JPanel p = (JPanel) c;
				for (Component c2 : p.getComponents()) {
					c2.setEnabled(true);
				}
			}
		}

		for (Component c : master.getComponents()) {
			c.setEnabled(true);
		}
		
		tradeButton.setEnabled(isTradeButtonEnabled);
	}

	public void updateTurn() {
		System.out.println("Cur Player Color: " + controller.getCurPlayer().getColor());
		System.out.println(myPlayer.getName() + " Color: " + myPlayer.getColor());
		if (controller.getCurPlayer().equals(myPlayer)) {
			enableAll();
		} else {
			System.out.println("Called disable all for " + myPlayer);
			setTurnColor();
			disableAll();
		}

	}

	public void updatePlayer() {
		for (Player p : controller.getPlayers()) {
			if (p.equals(myPlayer)) {
				myPlayer = p;
			}
			if (p.equals(((PlayerPanel) Player1Panel).getPlayer())) {
				((PlayerPanel) Player1Panel).setPlayer(p);
			}
			if (p.equals(((PlayerPanel) Player2Panel).getPlayer())) {
				((PlayerPanel) Player2Panel).setPlayer(p);
			}
			if (p.equals(((PlayerPanel) Player3Panel).getPlayer())) {
				((PlayerPanel) Player3Panel).setPlayer(p);
			}
			if (p.equals(((PlayerPanel) Player4Panel).getPlayer())) {
				((PlayerPanel) Player4Panel).setPlayer(p);
			}
		}

	}

	public void diceRolled() {

		tradeButton.setEnabled(false);
		die1 = controller.getCurPlayer().getDice().getDie1();
		die2 = controller.getCurPlayer().getDice().getDie2();

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
			dieTwo = six.getImage();
			break;
		}
		endButton.setText("End Turn");
		
		int sevenCheck = 0;
		for (Player p: controller.getPlayers()) {
			if (p.getCardsToDrop() == 0) {
				sevenCheck++;
			}
		}
		if (sevenCheck == 4) {
			checkForSevenRolled();
		}

		repaint();
	}
	
	public void checkForSevenRolled() {
		int val = die1 + die2;

		if (val != 7) {
			endButton.setText("End Turn");
			controller.setGamePhase(GamePhase.GAMEMAIN);
			controller.gamePhaseTrade();
		} else {
			if(myPlayer.equals(controller.getCurPlayer()))
					endButton.setText("End Robber Move");
			
			controller.setGamePhase(GamePhase.ROBBERMOVE);
		}
		if(myPlayer.equals(controller.getCurPlayer()))
			tradeButton.setEnabled(true);
		
		repaint();
	}

	public BoardPanel getBoardPanel() {
		return BoardPanel;
	}

	public TradePanel getTradePanel() {
		return tradePanel;
	}

	public void updateDevCardPanel() {
		((DevCardPanel) devCardPanel).updateComboBox();
	}

	public Player getMyPlayer() {
		return myPlayer;
	}

	public void gamePhaseEnd() {
		Timer.startTimer();
		tradeButton.setEnabled(false);
		endButton.setText("Roll Dice");
		if (controller.isGameEnded()) {
			JPanel gameOver = new EndGamePanel();
			gameOver.setBounds(getWidth() / 2 - 225, getHeight() / 2 - 150, 450, 300);
			gameOver.setEnabled(true);
			gameOver.setVisible(true);
			master.add(gameOver, new Integer(1), 0);

			return;

		}
		BoardPanel.drawRoads = false;
		BoardPanel.drawSettlements = false;
		BoardPanel.drawCities = false;
		//BoardPanel.repaint();
		updateTurn();
		repaint();
		Timer.endTimer();
	}

	public void robberMoved(Point p) {
		((BoardPanel) BoardPanel).getRobber().setPoint(p);

	}

	private void setTurnColor() {
		if (controller.getCurPlayer().equals(controller.getPlayers().get(0))) {
			if (controller.getCurPlayer().getColor().equals(Color.BLUE)) {
				Player1Panel.setBorder(new LineBorder(Color.BLUE, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
			} else if (controller.getCurPlayer().getColor().equals(Color.RED)) {
				Player1Panel.setBorder(new LineBorder(Color.RED, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
			} else if (controller.getCurPlayer().getColor().equals(Color.WHITE)) {
				Player1Panel.setBorder(new LineBorder(Color.WHITE, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
			} else {
				Player1Panel.setBorder(new LineBorder(Color.decode("#FFA500"), 3, true));
				Player2Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
			}
		} else if (controller.getCurPlayer().equals(controller.getPlayers().get(1))) {
			if (controller.getCurPlayer().getColor().equals(Color.BLUE)) {
				Player1Panel.setBorder(new LineBorder(Color.BLACK, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.BLUE, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
			} else if (controller.getCurPlayer().getColor().equals(Color.RED)) {
				Player1Panel.setBorder(new LineBorder(Color.BLACK, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.RED, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
			} else if (controller.getCurPlayer().getColor().equals(Color.WHITE)) {
				Player1Panel.setBorder(new LineBorder(Color.BLACK, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.WHITE, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
			} else {
				Player1Panel.setBorder(new LineBorder(Color.BLACK, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.decode("#FFA500"), 2, true));
				Player3Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
			}
		} else if (controller.getCurPlayer().equals(controller.getPlayers().get(2))) {
			if (controller.getCurPlayer().getColor().equals(Color.BLUE)) {
				Player1Panel.setBorder(new LineBorder(Color.BLACK, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.BLUE, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
			} else if (controller.getCurPlayer().getColor().equals(Color.RED)) {
				Player1Panel.setBorder(new LineBorder(Color.BLACK, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.RED, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
			} else if (controller.getCurPlayer().getColor().equals(Color.WHITE)) {
				Player1Panel.setBorder(new LineBorder(Color.BLACK, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.WHITE, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
			} else {
				Player1Panel.setBorder(new LineBorder(Color.BLACK, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.decode("#FFA500"), 2, true));
				Player4Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
			}
		} else {
			if (controller.getCurPlayer().getColor().equals(Color.BLUE)) {
				Player1Panel.setBorder(new LineBorder(Color.BLACK, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.BLUE, 2, true));
			} else if (controller.getCurPlayer().getColor().equals(Color.RED)) {
				Player1Panel.setBorder(new LineBorder(Color.BLACK, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.RED, 2, true));
			} else if (controller.getCurPlayer().getColor().equals(Color.WHITE)) {
				Player1Panel.setBorder(new LineBorder(Color.BLACK, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.WHITE, 2, true));
			} else {
				Player1Panel.setBorder(new LineBorder(Color.BLACK, 3, true));
				Player2Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player3Panel.setBorder(new LineBorder(Color.BLACK, 2, true));
				Player4Panel.setBorder(new LineBorder(Color.decode("#FFA500"), 2, true));
			}
		}

	}

}
