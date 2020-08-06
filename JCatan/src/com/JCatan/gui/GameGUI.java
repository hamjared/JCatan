package com.JCatan.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.JCatan.BoardFactory;
import com.JCatan.GameController;
import com.JCatan.Player;

import java.awt.Color;

public class GameGUI extends JFrame
{

    private JPanel contentPane;
    public static GameController controller;

    public GameGUI(List<Player> players, BoardFactory bf)
    {
        controller = new GameController(players, bf);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1920, 1040);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JPanel ChatPanel = new ChatPanel();
        contentPane.add(ChatPanel);
        
        JPanel BoardPanel = new BoardPanel();
        contentPane.add(BoardPanel);
        
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
        
        JPanel TradePanel = new TradePanel();
        contentPane.add(TradePanel);
        
        JPanel ResourcePanel = new ResourcesPanel();
        contentPane.add(ResourcePanel);
    }
}
