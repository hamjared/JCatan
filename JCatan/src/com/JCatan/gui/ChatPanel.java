package com.JCatan.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.JCatan.Chat;

public class ChatPanel extends JPanel
{
	private Chat chat;
	private JTextArea chatArea;
    public ChatPanel(Chat chat) {
        super();
        setBackground(Color.GREEN);
        setBounds(1441, 0, 463, 364);
        setLayout(null);
        
        chatArea = new JTextArea();
        chatArea.setBounds(10, 11, 443, 342);
        add(chatArea);
        this.chat = chat;
    }
    
    @Override
    public void paint(Graphics g) {
    	super.paint(g);
    	chatArea.setText(chat.toString());
    }
}