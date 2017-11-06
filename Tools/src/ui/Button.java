package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Button extends Label
{
	public Color borderColor = new Color(0, 0, 0);
	public Color backgroundColor = new Color(255, 255, 255);
	protected BufferedImage textPlane;
	public Button(String text, int width, int height, JPanel panel, Point location) 
	{
		super(text, panel, location);
		this.width = width;
		this.height = height;
		clickBox = new Polygon();
		clickBox.addPoint(location.x, location.y);
		clickBox.addPoint(location.x + width, location.y);
		clickBox.addPoint(location.x + width, location.y + height);
		clickBox.addPoint(location.x, location.y + height);
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
			g.setColor(backgroundColor);
			g.fillRect(location.x, location.y, width, height);
			((Graphics2D) g).setStroke(new BasicStroke(3));
			g.setColor(borderColor);
			g.drawRect(location.x, location.y, width, height);
			((Graphics2D) g).setStroke(new BasicStroke(1));
			super.paint(g);
			g = null;
		}
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public void setFont(Font font)
	{
		this.font = font;
	}
}

