package ui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TextBox extends Control
{
	protected Font font = new Font("TimesRoman", Font.PLAIN, 20);
	protected String text = "";
	protected BufferedImage textPlane;
	protected Color borderColor = new Color(0, 0, 0);
	protected Color backgroundColor = new Color(255, 255, 255);
	protected TextListener textListener = new TextListener(this);
	protected boolean textLine = false;
	protected Timer tim;
	public boolean acceptsNumbers = true;
	public boolean acceptsLetters = true;
	public boolean acceptsOnlyNumbers = false;
	public int acceptsNumbersBeneath = 0;
	public TextBox(int width, int height, JPanel panel, Point location, JFrame Screen) 
	{
		super(width, height, panel, location);
		Graphics g = panel.getGraphics();
		g.setFont(font);
		this.height = g.getFontMetrics().getHeight();
		textPlane = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		refreshText();
		clickBox = new Polygon();
		clickBox.addPoint(location.x, location.y);
		clickBox.addPoint(location.x + this.width, location.y);
		clickBox.addPoint(location.x + this.width, location.y + this.height);
		clickBox.addPoint(location.x, location.y + this.height);
		Screen.addKeyListener(textListener);
	}
	public void setBackgroundColor(Color color)
	{
		backgroundColor = color;
		refreshText();
	}
	public void setBorderColor(Color color)
	{
		borderColor = color;
		refreshText();
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
		refreshText();
	}
	public void setFont(Font font)
	{
		this.font = font;
		Graphics g = panel.getGraphics();
		g.setFont(font);
		this.height = g.getFontMetrics().getHeight();
		textPlane = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		refreshText();
		clickBox = new Polygon();
		clickBox.addPoint(location.x, location.y);
		clickBox.addPoint(location.x + this.width, location.y);
		clickBox.addPoint(location.x + this.width, location.y + this.height);
		clickBox.addPoint(location.x, location.y + this.height);
	}
	public void paint(Graphics g)
	{
		if (visible)
		{
			super.paint(g);
			g.drawImage(textPlane, location.x, location.y, null);
			((Graphics2D) g).setStroke(new BasicStroke(2));
			g.setColor(borderColor);
			g.drawRect(location.x, location.y, width, height);
			if (focus && textLine)
			{
				g.setColor(Color.black);
				g.setFont(font);
				if (g.getFontMetrics().stringWidth(text) + 5 < width - 5)
				{
					g.drawLine(location.x + g.getFontMetrics().stringWidth(text) + 5 + 1, location.y + 3, location.x + g.getFontMetrics().stringWidth(text) + 5 + 1, location.y + height - 3);
				}
				else
				{
					g.drawLine(location.x + width - 4, location.y + 3, location.x + width - 4, location.y + height - 3);
				}
			}
		}
		g = null;
	}
	public void refreshText()
	{
		Graphics g2 = textPlane.createGraphics();
		g2.setColor(backgroundColor);
		g2.fillRect(0, 0, width, height);
		g2.setColor(color);
		g2.setFont(font);
		if (g2.getFontMetrics().stringWidth(text) + 5 < width - 5)
		{
			g2.drawString(text, 5, height * 6 / 8);
		}
		else
		{
			g2.drawString(text, width - 5 - g2.getFontMetrics().stringWidth(text), height * 6 / 8);
		}
		panel.repaint();
		g2.dispose();
		g2 = null;
	}
	protected void clickEvent()
	{
		super.clickEvent();
		tim = new Timer();
		textLine = true;
		tim.scheduleAtFixedRate(new TimerTask()
		{
			public void run() 
			{
				if (textLine)
				{
					textLine = false;
				}
				else
				{
					textLine = true;
				}
				panel.repaint();
			}
			
		}, 600, 600);
	}
	protected void removeFocus()
	{
		super.removeFocus();
		tim.cancel();
		textLine = false;
	}
}
