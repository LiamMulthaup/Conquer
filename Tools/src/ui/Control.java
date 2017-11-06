package ui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import ui.Page.PageContainer;

public class Control 
{
	Container container;
	Polygon clickBox = new Polygon();
	public ControlHandler controlHandler;
	public String id = "";
	protected int width;
	protected int height;
	protected boolean visible = false;
	JPanel panel;
	protected Point location;
	public Color color = new Color(0, 0, 0);
	static protected ArrayList<Control> controls = new ArrayList<Control>();
	static protected Control focusControl;
	protected boolean focus = false;
	public boolean bottomOriented = false;
	public boolean rightOriented = false;
	public boolean antialiasing = false;
	public Control(int width, int height, JPanel panel, Point location)
	{
		this.location = location;
		this.panel = panel;
		this.width = width;
		this.height = height;
		clickBox.addPoint(location.x, location.y);
		clickBox.addPoint(location.x + width, location.y);
		clickBox.addPoint(location.x + width, location.y + height);
		clickBox.addPoint(location.x, location.y + height);
		controls.add(this);
	}
	static public int getControlsLength()
	{
		return controls.size();
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public void setSize(int width, int height)
	{
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
		if (antialiasing)
		{
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		else
		{
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		g = null;
	}
	static public void paintControls(JPanel selectedPanel, Graphics g)
	{
		for (int i = 0; i < controls.size(); i++)
		{
			Control control = controls.get(i);
			boolean containerVisible = true;
			if (control.container != null)
			{
				if (control.container.getClass() == PageContainer.class)
				{
					PageContainer pageContainer = (PageContainer) control.container;
					if (pageContainer.pageVisible == false)
					{
						containerVisible = false;
					}
				}
			}
			if (control.panel == selectedPanel && control.visible && containerVisible)
			{
				Point locationHolder = new Point(control.location);
				if (control.rightOriented)
				{
					control.setLocation(new Point(control.panel.getWidth() - control.location.x, control.location.y));
				}
				if (control.bottomOriented)
				{
					control.setLocation(new Point(control.location.x, control.panel.getHeight() - control.location.y));
				}
				control.paint(g);
				control.location = locationHolder;
			}
		}
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	protected void clickEvent()
	{
		if (focusControl != null)
		{
			focusControl.removeFocus();
		}
		this.focus = true;
		focusControl = this;
	}
	static public boolean controlClickEvents(JPanel selectedPanel, int mX, int mY, boolean clickOccurring)
	{
		for (int i = controls.size() - 1; i >= 0; i--)
		{
			if (clickOccurring == false)
			{
				break;
			}
			Control control = controls.get(i);
			boolean containerVisible = true;
			if (control.container != null)
			{
				if (control.container.getClass() == PageContainer.class)
				{
					PageContainer pageContainer = (PageContainer) control.container;
					if (pageContainer.pageVisible == false)
					{
						containerVisible = false;
					}
				}
			}
			if (control.getClass() != CircleButton.class)
			{
				if (control.panel == selectedPanel && control.visible == true && control.clickBox.contains(mX, mY) && containerVisible)
				{
					control.clickEvent();
					if (control.controlHandler != null)
					{
						control.controlHandler.clickEvent(control.id);
						control.controlHandler.clickEvent(control.id, control);
					}
					clickOccurring = false;
				}
			}
			else
			{
				if (containerVisible && control.panel == selectedPanel && control.visible == true && circleContains(mX, mY, control.location.x + control.width, control.location.y + control.width, control.width))
				{
					control.clickEvent();
					if (control.controlHandler != null)
					{
						control.controlHandler.clickEvent(control.id);
						control.controlHandler.clickEvent(control.id, control);
					}
					clickOccurring = false;
				}
			}
		}
		return clickOccurring;
	}
	public boolean isVisible()
	{
		return visible;
	}
	public boolean contains(int mX, int mY)
	{
		return clickBox.contains(mX, mY);
	}
	protected void removeFocus()
	{
		focus = false;
	}
	private static boolean circleContains(int cX, int cY, int x, int y, int radius)
	{
		return (radius * radius  / 4> (Math.pow(cX + (radius / 2) - x, 2) + Math.pow(cY + (radius / 2) - y, 2)));
	}
	public void setVisible(boolean v)
	{
		visible = v;
	}
	public Point getLocation()
	{
		return new Point(location);
	}
	public void setLocation(Point location)
	{
		this.location = location;
		if (container != null)
		{
			if (container.getClass() == PageContainer.class)
			{
				PageContainer pageContainer = (PageContainer) container;
				this.location.x = location.x + pageContainer.page.location.x;
				this.location.y = location.y + pageContainer.page.location.y;
			}
		}
		clickBox = new Polygon();
		clickBox.addPoint(this.location.x, this.location.y);
		clickBox.addPoint(this.location.x + width, this.location.y);
		clickBox.addPoint(this.location.x + width, this.location.y + height);
		clickBox.addPoint(this.location.x, this.location.y + height);
	}
	public void bringToFront()
	{
		for (int i = controls.indexOf(this); i < controls.size() - 1; i++)
		{
			controls.set(i, controls.get(i + 1));
		}
		controls.set(controls.size() - 1, this);
	}
	public void bringUp()
	{
		int i = controls.indexOf(this);
		controls.set(i, controls.get(i + 1));
		controls.set(i + 1, this);
	}
	public void pushDown()
	{
		int i = controls.indexOf(this);
		controls.set(i, controls.get(i - 1));
		controls.set(i - 1, this);
	}
	public void pushToBack()
	{
		for (int i = controls.indexOf(this); i > 0; i--)
		{
			controls.set(i, controls.get(i - 1));
		}
		controls.set(0, this);
	}
	public void dispose()
	{
		controls.remove(this);
	}
	public static void disposeAll()
	{
		controls = new ArrayList<Control>();
	}
	public static void disposeAll(JPanel panel)
	{
		for(int i = 0; i < controls.size(); i++)
		{
			if (controls.get(i).panel == panel)
			{
				controls.remove(i);
			}
		}
	}
}
