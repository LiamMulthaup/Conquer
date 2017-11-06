package ui;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class RadioButton extends Label
{
	public boolean selected = false;
	public boolean opaqueBubble = true;
	public Color bubbleColor = new Color(255, 255, 255);
	public boolean choiceLocked = false;
	public RadioButton(String text, JPanel panel, Point location) 
	{
		super(text, panel, location);
	}
	protected void resize()
	{
		super.resize();
		this.width += this.height;
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
			g.setColor(color);
			g.setFont(font);
			g.drawString(text, location.x + height, location.y + (height * 3 / 4));
			((Graphics2D) g).setStroke(new BasicStroke(3));
			if (selected)
			{
				g.fillOval(location.x, location.y + (height / 8), height * 5 / 8, height * 5 / 8);
				g.drawOval(location.x, location.y + (height / 8) , height * 5 / 8, height * 5 / 8);
			}
			else
			{
				if (opaqueBubble == false)
				{
					((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				              0));
				}
				g.setColor(bubbleColor);
				g.fillOval(location.x, location.y + (height / 8), height * 5 / 8, height * 5 / 8);
				((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
			              1));
				g.setColor(color);
				g.drawOval(location.x, location.y + (height / 8), height * 5 / 8, height * 5 / 8);
				((Graphics2D) g).setStroke(new BasicStroke(1));
				//g.drawRect(location.x, location.y, width, height);
			}
		}
		g = null;
	}
	protected void clickEvent()
	{
		super.clickEvent();
		setSelected();
	}
	public void setSelected()
	{
		if (selected && choiceLocked == false)
		{
			selected = false;
		}
		else
		{
			if (container != null)
			{
				for (int i = 0; i < container.controlGroup.size(); i++)
				{
					Control control = container.controlGroup.get(i);
					if (control.getClass() == RadioButton.class)
					{
						RadioButton radioButton = (RadioButton) control;
						radioButton.selected = false;
					}
				}
			}
			selected = true;
		}
	}
}
