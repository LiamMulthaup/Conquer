import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
public class Conquer {
	public static int gameScreenSizeX = 800, gameScreenSizeY = 200, dragVelocityTim = 10, savedDragX, savedDragY, redrawTim = 0, continents = 6, islands = 60, landMassPercentage = 40;
	public static double dragEndAngle, dragEndVelocity;
	public static void main(String[] args) 
	{
		Timer tim = new Timer();
		JFrame Screen = new JFrame();
		Screen.setTitle("Conquer");
		Screen.setSize(1000, 700);
		Screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ColorPanel background = new ColorPanel();
		background.generateGeographicalMap();
		Screen.add(background);
		background.setBackground(new Color(100, 100, 100));
		Screen.setVisible(true);
		background.oceanimg = background.clipImages(background.oceanimg); background.desertimg = background.clipImages(background.desertimg);
		background.forestimg = background.clipImages(background.forestimg); background.plainsimg = background.clipImages(background.plainsimg);
		background.desertHillimg = background.clipImages(background.desertHillimg); background.plainsHillimg = background.clipImages(background.plainsHillimg);
		background.shallowsimg = background.clipImages(background.shallowsimg); background.mountainimg = background.clipImages(background.mountainimg);
		background.jungleimg = background.clipImages(background.jungleimg); background.marshimg = background.clipImages(background.marshimg);
		background.tundraHillimg = background.clipImages(background.tundraHillimg); background.tundraimg = background.clipImages(background.tundraimg);
		background.snowimg = background.clipImages(background.snowimg); background.snowHillimg = background.clipImages(background.snowHillimg);
		background.oasisimg = background.clipImages(background.oasisimg); background.glacierimg = background.clipImages(background.glacierimg);
		Graphics g = background.getGraphics();
		background.paintComponent(g);
		tim.scheduleAtFixedRate(new TimerTask()
				{
					public void run() 
					{
						if (ColorPanel.clickOccurring == true)
						{
							background.mouseTimEvent();
							ColorPanel.clickOccurring = false;
						}
						if (ColorPanel.mousePressed == true)
						{
							background.setDragOffset();
							Screen.repaint();
							dragVelocityTim--;
							if (dragVelocityTim == 0)
							{
								dragVelocityTim = 10;
								int newDragX =  MouseInfo.getPointerInfo().getLocation().x - background.getLocationOnScreen().x;
								int newDragY =  MouseInfo.getPointerInfo().getLocation().y - background.getLocationOnScreen().y;
								dragEndVelocity = Math.sqrt(((newDragX - savedDragX) * (newDragX - savedDragX)) + ((newDragY - savedDragY) * (newDragY - savedDragY))) / 10;
								dragEndAngle = Math.atan2((((double) (newDragY) - savedDragY)), (newDragX - savedDragX));
								savedDragX = newDragX;
								savedDragY = newDragY;
							}
						}
						if (ColorPanel.sliding == true)
						{
							background.xOffset += (Math.cos(dragEndAngle) * dragEndVelocity);
							if (2 * background.sideLength > background.yOffset + (Math.sin(dragEndAngle) * dragEndVelocity) && - 3 * background.sideLength / 2 * Conquer.gameScreenSizeY + background.getHeight() < background.yOffset + (Math.sin(dragEndAngle) * dragEndVelocity))
							{
								background.yOffset += (Math.sin(dragEndAngle) * dragEndVelocity);
								
							}
							background.yOrigin = background.yOffset;
							background.xOrigin = background.xOffset;
							Screen.repaint();
							redrawTim--;
							if (dragEndVelocity <= 0) { ColorPanel.sliding = false;}
							else
							{
								dragEndVelocity -= (dragEndVelocity + 2) * (dragEndVelocity + 2) * 0.004;
							}
						}
					}	
				}, 10, 10);
		
	}
}
