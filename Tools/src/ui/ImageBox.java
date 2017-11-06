package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JPanel;

public class ImageBox extends Control
{
	Image image;
	public ImageBox(int width, int height, JPanel panel, Point location, Image image) 
	{
		super(width, height, panel, location);
		this.image = image;
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(image, location.x, location.y, width, height, null);
	}
	public Image getImage()
	{
		return image;
	}
}
