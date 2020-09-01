package com.JCatan.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.JCatan.BoardFactory;
import com.JCatan.HumanPlayer;
import com.JCatan.Player;
import com.JCatan.RandomBoardFactory;
import com.JCatan.TraditionalBoardFactory;
import com.JCatan.client.GameClient;
import com.JCatan.client.InitiatePlayer;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class StartMenuFrame extends JFrame
{
	private static final long serialVersionUID = 7807675421410822657L;
	private JPanel contentPane;
    private JTextField redPlayerName;
    private JComboBox<String> boardType;
    private JLabel gameStartLabel;

    public StartMenuFrame()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 765, 533);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("JCatan");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setBounds(302, 11, 147, 45);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Player Name");
        lblNewLabel_1.setBounds(213, 173, 80, 14);
        contentPane.add(lblNewLabel_1);
        
        redPlayerName = new JTextField();
        redPlayerName.setText("Joe");
        redPlayerName.setBounds(336, 170, 113, 20);
        contentPane.add(redPlayerName);
        redPlayerName.setColumns(10);
        
        boardType = new JComboBox<String>();
        boardType.setModel(new DefaultComboBoxModel<String>(new String[] {"Traditional Board", "Random Board"}));
        boardType.setBounds(336, 241, 113, 22);
        contentPane.add(boardType);
        
        JLabel lblNewLabel_5 = new JLabel("BoardLayout");
        lblNewLabel_5.setBounds(213, 245, 80, 14);
        contentPane.add(lblNewLabel_5);
        
        JButton btnNewButton = new JButton("Start Game");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	btnNewButton.setEnabled(false);
            	redPlayerName.setEnabled(false);
                startGame();
            }
        });
        btnNewButton.setBounds(302, 398, 89, 23);
        contentPane.add(btnNewButton);
        
        gameStartLabel = new JLabel("Waiting for Game to Start");
        gameStartLabel.setBounds(284, 297, 221, 14);
        contentPane.add(gameStartLabel);
        gameStartLabel.setVisible(false);
    }

    protected void startGame()
    {
        List<Player> players = getPlayers();
        BoardFactory bf ;
        if(boardType.getSelectedIndex() == 0) {
        	bf = new TraditionalBoardFactory();
        } 
        else {
        	bf = new RandomBoardFactory();
        }
        gameStartLabel.setVisible(true);
        SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			
    			GameGUI game = new GameGUI();
    			//GameClient gameClient = new GameClient("137.117.105.3", 5679);
    			GameClient gameClient = new GameClient("127.0.0.1", 5679);
    			game.setGameClient(gameClient);
    			gameClient.setGameGUI(game);
    			Player player = new HumanPlayer(redPlayerName.getText());
    			gameClient.sendPlayer(new InitiatePlayer(player, bf));
    			gameClient.waitForGame();
    			new Thread(gameClient).start();
    			
    			
    	        game.setVisible(true);
    	        setVisible(false);
    	        dispose();
    		}
    	});
        
        
    }
    
    protected List<Player> getPlayers(){
        List<Player> players = new ArrayList<>();
        if(!redPlayerName.getText().isEmpty()) {
            players.add(new HumanPlayer(redPlayerName.getText()));
        }
        return players;
    }
}