package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class ScrollBar extends Control 
{
	private boolean mousePressed = false;
	public Color borderColor = new Color(255, 255, 255);
	public Color backgroundColor = new Color(0, 0, 0);
	public Color barColor = new Color(133, 131, 230);
	public Color barBorderColor = new Color(133, 131, 230);
	protected Timer tim;
	int barLocation = 0;
	public int barSize = 20;
	boolean roundedEdges = true;
	public int roundedAmount = 10;
	public int scrollWheelSpeed = 10;
	protected Control parentControl;
	public ScrollBar(int width, int height, JPanel panel, Point location) 
	{
		super(width, height, panel, location);
		panel.addMouseListener(new scrollListener());
		panel.addMouseWheelListener(new MouseWheelEventListener());
	}
	public boolean getMousePressed()
	{
		return mousePressed;
	}
	public void clickEvent()
	{
		super.clickEvent();
		mousePressed = true;
		tim = new Timer();
		tim.scheduleAtFixedRate(new TimerTask()
		{
			public void run() 
			{
				if (mousePressed)
				{
					try
					{
						int mY = MouseInfo.getPointerInfo().getLocation().y - panel.getLocationOnScreen().y - location.y;
						if (mY < 0)
						{
							barLocation = 0;
						}
						else if (mY > height - barSize)
						{
							barLocation = height - barSize;
						}
						else
						{
							barLocation = mY;
						}
						if (parentControl != null)
						{
							if (parentControl.getClass() == ListBox.class)
							{
								((ListBox) parentControl).refreshListBox();
							}
						}
						panel.repaint();
					}
					catch (Exception e)
					{
						
					}
				}
				else
				{
					tim.cancel();
				}
			}
		}, 50, 50);
	}
	public void paint(Graphics g)
	{
		g.setColor(backgroundColor);
		super.paint(g);
		if (roundedEdges)
		{
			g.fillRoundRect(location.x, location.y, width, height, roundedAmount, roundedAmount);
			g.setColor(borderColor);
			g.fillRoundRect(location.x, location.y, width, height, roundedAmount, roundedAmount);
			g.setColor(barColor);
			g.fillRoundRect(location.x, location.y + barLocation, width, barSize, roundedAmount, roundedAmount);
			g.setColor(barBorderColor);
			g.drawRoundRect(location.x, location.y + barLocation, width, barSize, roundedAmount, roundedAmount);
		}
		else
		{
			g.fillRect(location.x, location.y, width, height);
			g.setColor(borderColor);
			g.fillRect(location.x, location.y, width, height);
			g.setColor(barColor);
			g.fillRect(location.x, location.y + barLocation, width, barSize);
			g.setColor(barBorderColor);
			g.drawRect(location.x, location.y + barLocation, width, barSize);
		}
		g = null;
		g = null;
	}
	public class scrollListener extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e)
		{
			mousePressed = false;
		}
	}
	public class MouseWheelEventListener implements MouseWheelListener
	{
		public void mouseWheelMoved(MouseWheelEvent arg0) 
		{
			if (visible)
			{
				int notches = arg0.getWheelRotation();
				if (barLocation + (notches * scrollWheelSpeed) > height - barSize)
				{
					barLocation = height - barSize;
				}
				else if (barLocation + (notches * scrollWheelSpeed) < 0)
				{
					barLocation = 0;
				}
				else
				{
					barLocation = barLocation + (notches * scrollWheelSpeed);
				}
				if (parentControl != null)
				{
					if (parentControl.getClass() == ListBox.class)
					{
						((ListBox) parentControl).refreshListBox();
					}
				}
			}
			panel.repaint();
		}
	}
}
