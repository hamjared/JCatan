package com.JCatan.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.JCatan.Chat;
import com.JCatan.server.Message;
import com.JCatan.server.MessageBuilder;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatPanel extends JPanel
{
	private Chat chat;
	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	private JTextArea chatArea;
	private JTextField textField;
    public ChatPanel(Chat chat) {
        super();
        setBackground(Color.GREEN);
        setBounds(1441, 0, 463, 364);
        setLayout(null);
        
        chatArea = new JTextArea();
        chatArea.setBounds(10, 11, 443, 314);
        add(chatArea);
        this.chat = chat;
        
        textField = new JTextField();
        textField.setBounds(10, 336, 371, 20);
        add(textField);
        textField.setColumns(10);
        
        JButton btnNewButton = new JButton("Send");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Chat chat = GameGUI.controller.getChat();
        		
        		String chatMessage = GameGUI.myPlayer.getName() + ": " + textField.getText().trim();
        		if(chatMessage.isBlank()) {
        			return;
        		}
        		chat.addToChat(chatMessage);
        		Message msg = new MessageBuilder().action(Message.Action.Chat).chatMessage(chatMessage).build();
        		GameGUI.gameClient.sendMessage(msg);
        	}
        });
        btnNewButton.setBounds(390, 336, 63, 17);
        add(btnNewButton);
    }
    
    @Override
    public void paint(Graphics g) {
    	super.paint(g);
    	chatArea.setText(GameGUI.controller.getChat().toString());
    }
}