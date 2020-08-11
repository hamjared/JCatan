package com.JCatan.gui;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import com.JCatan.BoardFactory;
import com.JCatan.GameController;
import com.JCatan.Player;

import java.awt.Color;
import javax.swing.JButton;

public class GameGUI extends JFrame
{
	private static final long serialVersionUID = -5012311744443108873L;
	private JPanel contentPane = null;
	private TradePanel tradePanel = null;
    public static GameController controller;
    
    //USE THIS AS AN EVENT DELEGATE...
    public TradePanel getTradePanel() {
    	return tradePanel;
    }
    
    public GameGUI(List<Player> players, BoardFactory bf)
    {        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1920, 1040);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        controller = new GameController(players, bf);       		
        
        tradePanel = new TradePanel(1079, 0, 750, 800);
        JButton tradeBtn = new JButton("Trade");
        tradeBtn.setBounds(1352, 844, 89, 23);
        tradeBtn.addActionListener(e -> {
        	tradePanel.setEnabled(true);
        	tradePanel.setVisible(true);
        });
        contentPane.add(tradePanel);
        contentPane.add(tradeBtn);
        
        ChatPanel ChatPanel = new ChatPanel();
        contentPane.add(ChatPanel);
        
        JPanel BankPanel = new BankPanel();
        contentPane.add(BankPanel);
        
        JPanel Player1Panel = new PlayerPanel(1441, 490, 463, 126);
        Player1Panel.setBackground(Color.ORANGE);
        contentPane.add(Player1Panel);
        
        JPanel Player2Panel = new PlayerPanel(1441, 615, 463, 126);
        Player2Panel.setBackground(Color.PINK);
        contentPane.add(Player2Panel);
        
        JPanel Player3Panel = new PlayerPanel(1441, 741, 463, 126);
        Player3Panel.setBackground(Color.ORANGE);
        contentPane.add(Player3Panel);
        
        JPanel Player4Panel = new PlayerPanel(1441, 867, 463, 134);
        Player4Panel.setBackground(Color.PINK);
        contentPane.add(Player4Panel);
        
        JPanel EndTurnPanel = new EndTurnPanel();
        contentPane.add(EndTurnPanel);
        
        JPanel BuildingPanel = new BuildingPanel();
        contentPane.add(BuildingPanel);
                
        JPanel ResourcePanel = new ResourcesPanel();
        ResourcePanel.setBounds(0, 867, 873, 134);
        contentPane.add(ResourcePanel);
        
        BoardPanel boardPanel = new BoardPanel(0, 0, 1441, 867);
        contentPane.add(boardPanel);
        
        
                
//        SwingWorker<Void, Void> gameControllerThread = new SwingWorker<Void, Void>() {
//        	
//        	@Override
//        	protected Void doInBackground() throws Exception{
//        		System.out.print("Running Game");
//        		controller = new GameController(players, bf);
//        		controller.startGame();        		
//        		return null;
//        	}
//        	
//        	@Override
//        	protected void process(List chunks) {
//        		//Can update the GUI safely from here...
//        	}
//        	
//        	@Override
//        	protected void done() {
//        		
//        	}
//        };
//        Executors.newSingleThreadExecutor().execute(gameControllerThread);
        //gameControllerThread.execute();
    }
}
