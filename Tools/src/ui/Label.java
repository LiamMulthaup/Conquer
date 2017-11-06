package ui;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JPanel;

public class Label extends Control
{
	protected Font font = new Font("TimesRoman", Font.PLAIN, 40);
	protected String text;
	public Label(String text, JPanel panel, Point location) 
	{
		super(0, 0, panel, location);
		this.text = text;
		resize();
	}
	public String getText()
	{
		return text;
	}
	protected void resize()
	{
		Graphics g = panel.getGraphics();
		g.setFont(font);
		this.width = g.getFontMetrics().stringWidth(text);
		this.height = g.getFontMetrics().getHeight() * 3 / 4;
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
			super.paint(g);
			g.setColor(color);
			g.setFont(font);
			g.drawString(text, location.x, location.y + (height * 3 / 4));
		}
		g = null;
	}
	public void setFont(Font font)
	{
		this.font = font;
		resize();
	}
	public void setText(String text)
	{
		this.text = text;
		resize();
	}
}
