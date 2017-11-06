package ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

public class Page extends Control
{
	protected class PageContainer extends Container
	{
		Page page;
		Boolean pageVisible;
		protected PageContainer(Page page)
		{
			this.page = page;
		}
		protected void setVisible(boolean v)
		{
			pageVisible = v;
		}
		public void add(Control control)
		{
			super.add(control);
			control.setLocation(control.location);
		}
		protected void setLocation(Point oldLocation)
		{
			for (int i = 0; i < controlGroup.size(); i++)
			{
				controlGroup.get(i).setLocation(new Point(controlGroup.get(i).location.x - oldLocation.x, controlGroup.get(i).location.y - oldLocation.y));
			}
		}
	}
	PageContainer pageContainer = new PageContainer(this);
	public Color borderColor = new Color(255, 255, 255);
	public boolean roundedEdges = true;
	public int roundedAmount = 10;
	public double backgroundVisibility = 1;
	public Page(int width, int height, JPanel panel, Point location) 
	{
		super(width, height, panel, location);
	}
	public void add(Control control)
	{
		pageContainer.add(control);
	}
	public void setVisible(boolean v)
	{
		visible = v;
		pageContainer.setVisible(v);
	}
	public void setLocation(Point location)
	{
		Point oldLocation = new Point(this.location.x, this.location.y);
		super.setLocation(location);
		pageContainer.setLocation(oldLocation);
	}
	public void dispose()
	{
		super.dispose();
		for (Control control : pageContainer.controlGroup)
		{
			control.dispose();
			control = null;
		}
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor(color);
		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				(float) (backgroundVisibility)));
		if (roundedEdges)
		{
			g.fillRoundRect(location.x, location.y, width, height, roundedAmount, roundedAmount);
			((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					(float) (1)));
			g.setColor(borderColor);
			g.drawRoundRect(location.x, location.y, width, height, roundedAmount, roundedAmount);
		}
		else
		{
			g.fillRect(location.x, location.y, width, height);
			((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					(float) (1)));
			g.setColor(borderColor);
			g.drawRect(location.x, location.y, width, height);
		}
		g = null;
	}
}
