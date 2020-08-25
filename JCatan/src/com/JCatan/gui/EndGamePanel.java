package com.JCatan.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EndGamePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public EndGamePanel() {
		setLayout(null);
		
		JLabel lblGameOver = new JLabel("Game Over");
		lblGameOver.setBounds(170, 99, 131, 15);
		add(lblGameOver);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(151, 150, 117, 25);
		add(btnExit);

	}
}
