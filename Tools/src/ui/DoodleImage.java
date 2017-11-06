package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class DoodleImage extends ImageBox
{
	protected BufferedImage doodleimg;
	public DoodleImage(int width, int height, JPanel panel, Point location, Image image) 
	{
		super(width, height, panel, location, image);
		doodleimg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		// TODO Auto-generated constructor stub
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(doodleimg, location.x, location.y, width, height, null);
	}
	public Graphics createGraphics()
	{
		return doodleimg.createGraphics();
	}
}
