package ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Doodle extends Control
{
	BufferedImage doodle;
	public Doodle(int width, int height, JPanel panel, Point location, BufferedImage doodle)
	{
		super(width, height, panel, location);
		this.doodle = doodle;
	}
	public void paint(Graphics g)
	{
		g.drawImage(doodle, location.x, location.y, width, height, null);
	}
	public Graphics createGraphics()
	{
		return doodle.createGraphics();
	}
}
