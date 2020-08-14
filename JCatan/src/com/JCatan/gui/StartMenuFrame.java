package com.JCatan.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.JCatan.BoardFactory;
import com.JCatan.HumanPlayer;
import com.JCatan.Player;
import com.JCatan.RandomBoardFactory;
import com.JCatan.TraditionalBoardFactory;

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
    private JTextField whitePlayerName;
    private JTextField bluePlayerName;
    private JTextField orangePlayerName;
    private JComboBox<String> boardType;

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
        
        JLabel lblNewLabel_1 = new JLabel("Red Player");
        lblNewLabel_1.setBounds(213, 114, 80, 14);
        contentPane.add(lblNewLabel_1);
        
        redPlayerName = new JTextField();
        redPlayerName.setText("Joe");
        redPlayerName.setBounds(336, 111, 113, 20);
        contentPane.add(redPlayerName);
        redPlayerName.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("White Player");
        lblNewLabel_2.setBounds(213, 162, 80, 14);
        contentPane.add(lblNewLabel_2);
        
        whitePlayerName = new JTextField();
        whitePlayerName.setText("Bob");
        whitePlayerName.setBounds(336, 159, 113, 20);
        contentPane.add(whitePlayerName);
        whitePlayerName.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("Blue Player");
        lblNewLabel_3.setBounds(213, 211, 67, 14);
        contentPane.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("Orange Player");
        lblNewLabel_4.setBounds(213, 265, 80, 14);
        contentPane.add(lblNewLabel_4);
        
        bluePlayerName = new JTextField();
        bluePlayerName.setText("Tim");
        bluePlayerName.setBounds(336, 208, 113, 20);
        contentPane.add(bluePlayerName);
        bluePlayerName.setColumns(10);
        
        orangePlayerName = new JTextField();
        orangePlayerName.setText("Will");
        orangePlayerName.setBounds(336, 262, 113, 20);
        contentPane.add(orangePlayerName);
        orangePlayerName.setColumns(10);
        
        boardType = new JComboBox<String>();
        boardType.setModel(new DefaultComboBoxModel<String>(new String[] {"Traditional Board", "Random Board"}));
        boardType.setBounds(336, 317, 113, 22);
        contentPane.add(boardType);
        
        JLabel lblNewLabel_5 = new JLabel("BoardLayout");
        lblNewLabel_5.setBounds(213, 321, 80, 14);
        contentPane.add(lblNewLabel_5);
        
        JButton btnNewButton = new JButton("Start Game");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        btnNewButton.setBounds(302, 398, 89, 23);
        contentPane.add(btnNewButton);
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
        	System.out.println("Random Board");
        }
        
        SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			GameGUI game = new GameGUI(players, bf);
    	        game.setVisible(true);
    		}
    	});
        setVisible(false);
        dispose();
    }
    
    protected List<Player> getPlayers(){
        List<Player> players = new ArrayList<>();
        if(!redPlayerName.getText().isEmpty()) {
            players.add(new HumanPlayer(redPlayerName.getText()));
        }
        if(!bluePlayerName.getText().isEmpty()) {
            players.add(new HumanPlayer(bluePlayerName.getText()));
        }
        if(!whitePlayerName.getText().isEmpty()) {
            players.add(new HumanPlayer(whitePlayerName.getText()));
        }
        if(!orangePlayerName.getText().isEmpty()) {
            players.add(new HumanPlayer(orangePlayerName.getText()));
        }
        return players;
    }
}