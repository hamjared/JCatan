package com.JCatan.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.JCatan.ResourceType;
import com.JCatan.Tile;

public class BoardPanel extends JPanel
{
    public BoardPanel()
    {
        super();
        setBackground(Color.BLUE);
        setBounds(0, 0, 1441, 867);
    }

    private static final long serialVersionUID = 1L;
    private final int WIDTH = 1441;
    private final int HEIGHT = 867;

    private final int W2 = WIDTH / 2;
    private final int H2 = HEIGHT / 2;

    private Font font = new Font("Arial", Font.BOLD, 24);
    FontMetrics metrics;

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_MITER));
        g2d.setFont(font);
        metrics = g.getFontMetrics();

        drawCircle(g2d, W2, H2, 10000, true, true, 0x4488FF, 0);

        drawHexGridAdvanced(g2d, 5, 85);
    }

    private void drawHexGridAdvanced(Graphics g, int n, int r)
    {
        double ang30 = Math.toRadians(30);
        double xOff = Math.cos(ang30) * r;
        double yOff = Math.sin(ang30) * r;
        int h = n / 2;
        int tileIndex = 0;
        List<Tile> tiles = GameGUI.controller.getBoard().getTiles();

        for (int row = 0; row < 5; row++)
        {
            int cols = 0;
            switch (row)
            {
            case (0):
            case (4):
                cols = 3;
                break;
            case (1):
            case (3):
                cols = 4;
                break;
            case (2):
                cols = 5;
                break;

            }
            for (int col = 0; col < cols; col++)
            {

                if (row < 2)
                {
                    drawHex(g, col - row, -h + row,
                            (int) (W2 + xOff * (-cols + (col * 2 + 1))),
                            (int) (H2 - yOff * (n - cols) * 3), r,
                            tiles.get(tileIndex),
                            "" + tiles.get(tileIndex).getNumber());
                } else if (row == 2)
                {
                    drawHex(g, col - row, -h + row,
                            (int) (W2 + xOff * (-cols + (col * 2 + 1))),
                            (int) (H2 + yOff * (n - cols) * 3), r,
                            tiles.get(tileIndex),
                            "" + tiles.get(tileIndex).getNumber());
                } else if (row == 3)
                {
                    drawHex(g, col - row + 1, -h + row,
                            (int) (W2 + xOff * (-cols + (col * 2 + 1))),
                            (int) (H2 + yOff * (n - cols) * 3), r,
                            tiles.get(tileIndex),
                            "" + tiles.get(tileIndex).getNumber());
                } else if (row == 4)
                {
                    drawHex(g, col - row + 2, -h + row,
                            (int) (W2 + xOff * (-cols + (col * 2 + 1))),
                            (int) (H2 + yOff * (n - cols) * 3), r,
                            tiles.get(tileIndex),
                            "" + tiles.get(tileIndex).getNumber());
                }

                tileIndex++;
            }

        }

    }

    private void drawHex(Graphics g, int posX, int posY, int x, int y, int r,
            Tile tile, String text)
    {
        Hexagon hex = new Hexagon(x, y, r, tile);

        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();

        g.setColor(tile.getColor());
        g.fillPolygon(hex);
        g.setColor(new Color(0x000000));
        g.drawPolygon(hex);
        g.setColor(new Color(0xFFFFFF));
        g.drawString(text, x - w / 2, y + h / 2);
    }

    private String coord(int value)
    {
        return (value > 0 ? "+" : "") + Integer.toString(value);
    }

    public void drawCircle(Graphics2D g, int x, int y, int diameter,
            boolean centered, boolean filled, int colorValue, int lineThickness)
    {
        drawOval(g, x, y, diameter, diameter, centered, filled, colorValue,
                lineThickness);
    }

    public void drawOval(Graphics2D g, int x, int y, int width, int height,
            boolean centered, boolean filled, int colorValue, int lineThickness)
    {
        // Store before changing.
        Stroke tmpS = g.getStroke();
        Color tmpC = g.getColor();

        g.setColor(new Color(colorValue));
        g.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));

        int x2 = centered ? x - (width / 2) : x;
        int y2 = centered ? y - (height / 2) : y;

        if (filled)
            g.fillOval(x2, y2, width, height);
        else
            g.drawOval(x2, y2, width, height);

        // Set values to previous when done.
        g.setColor(tmpC);
        g.setStroke(tmpS);
    }

}
