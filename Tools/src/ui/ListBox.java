package ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ListBox extends Control
{
	protected ArrayList<String> items = new ArrayList<String>();
	int selectedString = -1;
	protected Color borderColor = new Color(255, 255, 255);
	public boolean roundedEdges = true;
	public int roundedAmount = 10;
	public double backgroundVisibility = 1;
	public Color backgroundColor = new Color(0, 0, 0);
	public Color highLightingColor = new Color(156, 149, 147);
	protected Font font = new Font("TimesRoman", Font.PLAIN, 20);
	protected BufferedImage listBoxImage;
	public ScrollBar scrollBar;
	protected BufferedImage panelDrawing = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TRANSLUCENT);
	public ListBox(int width, int height, JPanel panel, Point location) 
	{
		super(width, height, panel, location);
		color = new Color(255, 255, 255);
		listBoxImage = new BufferedImage(this.width + 1, this.height + 1, BufferedImage.TYPE_INT_RGB);
		scrollBar = new ScrollBar(20, height, panel, new Point(location.x + width - 20, location.y));
		scrollBar.barSize = 40;
		scrollBar.parentControl = this;
		refreshListBox();
	}
	public String getItem(int index)
	{
		if (index < items.size() && index > -1)
		{
			return items.get(index);
		}
		return null;
	}
	public void add(String string)
	{
		items.add(string);
		refreshListBox();
	}
	public int getIndexOf(String s)
	{
		return items.indexOf(s);
	}
	public void setFont(Font font)
	{
		this.font = font;
		refreshListBox();
	}
	public void setVisible(boolean v)
	{
		super.setVisible(v);
		Graphics g = panel.getGraphics();
		if (items.size() * g.getFontMetrics(font).getHeight() > height)
		{
			scrollBar.setVisible(v);
		}
		else
		{
			scrollBar.setVisible(false);
		}
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage((Image)listBoxImage, location.x, location.y, null);
		g.setFont(font);
		if (items.size() * g.getFontMetrics().getHeight() > height)
		{
			scrollBar.setVisible(true);
			scrollBar.paint(g);
		}
		g = null;
	}
	public void setLocation(Point location)
	{
		super.setLocation(location);
		scrollBar.setLocation(new Point(location.x + width - scrollBar.width, location.y));
	}
	protected void refreshListBox()
	{
		BufferedImage newlistBoxImage = new BufferedImage(this.width + 1, this.height + 1, BufferedImage.TRANSLUCENT);
		Graphics g = newlistBoxImage.createGraphics();
		g.setColor(backgroundColor);
		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				(float) (backgroundVisibility)));
		if (roundedEdges)
		{
			g.fillRoundRect(0, 0, width, height, roundedAmount, roundedAmount);
			((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					(float) (1)));
		}
		else
		{
			g.fillRect(0, 0, width, height);
			((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					(float) (1)));
		}
		g.setFont(font);
		int scrolledHeight = (int) (scrollBar.barLocation / ((double) height - scrollBar.barSize) * (items.size() * g.getFontMetrics().getHeight() - height));
		for (int i = 0; i < items.size(); i++)
		{
			if (selectedString == i)
			{
				g.setColor(highLightingColor);
				g.fillRoundRect(0, i * g.getFontMetrics().getHeight() - scrolledHeight, width, g.getFontMetrics().getHeight(), roundedAmount, roundedAmount);
			}
		}
		g.setColor(color);
		for (int i = 0; i < items.size(); i++)
		{
			g.drawString(items.get(i), 0 + 5, 0 + (i + 1) * g.getFontMetrics().getHeight() - (g.getFontMetrics().getHeight() / 5) - scrolledHeight);
		}
		if (roundedEdges)
		{
			g.setColor(borderColor);
			g.drawRoundRect(0, 0, width, height, roundedAmount, roundedAmount);
		}
		else
		{
			g.setColor(borderColor);
			g.drawRect(0, 0, width, height);
		}
		if (!(items.size() * g.getFontMetrics().getHeight() > height))
		{
			scrollBar.setVisible(false);
		}
		listBoxImage.flush();
		listBoxImage = null;
		listBoxImage = newlistBoxImage;
		newlistBoxImage.flush();
		newlistBoxImage = null;
	}
	public int getSelectedIndex()
	{
		if (selectedString > -1 && selectedString < items.size())
		{
			return selectedString;
		}
		else
		{
			return -1;
		}
	}
	public int getItemsSize()
	{
		return items.size();
	}
	public void setSelected(int i)
	{
		selectedString = i;
		refreshListBox();
		panel.repaint();
	}
	public void clearItems()
	{
		items.clear();
	}
	public void remove(int i)
	{
		items.remove(i);
	}
	public void remove(String s)
	{
		items.remove(s);
	}
	public void clickEvent()
	{
		super.clickEvent();
		Graphics g = panel.getGraphics();
		g.setFont(font);
		int stringHeight = g.getFontMetrics().getHeight();
		int scrolledHeight = (int) (scrollBar.barLocation / ((double) height - scrollBar.barSize) * (items.size() * g.getFontMetrics().getHeight() - height));		
		int mY = MouseInfo.getPointerInfo().getLocation().y - panel.getLocationOnScreen().y - location.y;
		selectedString = (mY + scrolledHeight) / stringHeight;
		refreshListBox();
		panel.repaint();
	}
	public void bringUp()
	{
		super.bringUp();
		scrollBar.bringUp();
	}
	public void pushDown()
	{
		super.pushDown();
		scrollBar.pushDown();
	}
	public void bringToFront()
	{
		super.bringToFront();
		scrollBar.bringToFront();
	}
	public void pushToBack()
	{
		super.pushToBack();
		scrollBar.pushToBack();
	}
	public void dispose()
	{
		super.dispose();
		if (scrollBar != null)
		{
			scrollBar.dispose();
		}
	}
}
