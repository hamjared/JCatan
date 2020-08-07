package com.JCatan.gui;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BuildingPanel extends JPanel
{
    public BuildingPanel() {
        setBackground(Color.PINK);
        setBounds(872, 867, 442, 134);
        setLayout(null);
        
        JPanel panel = new JPanel();
       
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buyDevCard();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        panel.setBounds(339, 11, 93, 112);
        add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Buy Dev Card");
        lblNewLabel.setBounds(10, 40, 73, 14);
        panel.add(lblNewLabel);
    }

    protected void buyDevCard()
    {
        // TODO BuildingPanel: buyDevCard
        
    }


}
