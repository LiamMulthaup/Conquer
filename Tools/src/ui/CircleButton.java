package ui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class CircleButton extends Label
{
	public Color borderColor = new Color(0, 0, 0);
	public Color backgroundColor = new Color(255, 255, 255);
	public double backgroundVisibility = 1;
	public int stringOffsetX = 0;
	public int stringOffsetY = 0;
	public CircleButton(String text, int diameter, JPanel panel, Point location) 
	{
		super(text, panel, location);
		this.width = diameter;
		this.height = diameter;
	}
	public void paint(Graphics g)
	{
		if (visible)
		{
			if (antialiasing)
			{
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}
			else
			{
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			}
			((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					(float) (backgroundVisibility)));
			g.setColor(backgroundColor);
			g.fillOval(location.x, location.y, width, height);
			((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					(float) 1));
			((Graphics2D) g).setStroke(new BasicStroke(3));
			g.setColor(borderColor);
			g.drawOval(location.x, location.y, width, height);
			((Graphics2D) g).setStroke(new BasicStroke(1));
			g.setColor(color);
			g.setFont(font);
			g.drawString(text, location.x + (height / 7) + stringOffsetX, location.y + (height * 7 / 10) + stringOffsetY);
		}
		g = null;
	}
	public void setFont(Font font)
	{
		this.font = font;
	}
}
