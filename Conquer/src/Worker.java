import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import ui.Button;
import ui.ControlHandler;
import ui.ImageBox;
import ui.ListBox;
import ui.Page;

public class Worker extends Unit
{
	Page buildingImprovementsContainer;
	ListBox improvementsToBuildListBox;
	Button buildButton;
	BuildingControlHandler controlHandler = new BuildingControlHandler();
	public Worker(ColorPanel colorPanel, int x, int y, Player player) 
	{
		super(colorPanel, x, y, player);
		warrior = false;
		attackOrUtilityimg = buildimg;
		unitName = "Worker";
	}
	public void paint(Graphics g, int x, int y)
	{
		super.paint(g, x, y);
		g.drawImage(workerimg, (int) (x + colorPanel.xDistanceIncrementer / 2 - 24), 
				(int) (y + colorPanel.sideLength * 2 / 3 - 25), 50, 50, null);
	}
	public void refreshButtons()
	{
		super.refreshButtons();
		unitImage = new ImageBox(150, 150, colorPanel, new Point(0, 150), workerimg);
		unitImage.bottomOriented = true;
		unitImage.setVisible(true);
	}
	public void utilityAction()
	{
		colorPanel.dimBackground = new Page(colorPanel.getWidth() + 5, colorPanel.getWidth() + 5, colorPanel, new Point(- 2, -2));
		colorPanel.dimBackground.backgroundVisibility = 0.6;
		colorPanel.dimBackground.id = "Dim Background";
		colorPanel.dimBackground.controlHandler = controlHandler;
		colorPanel.dimBackground.setVisible(true);
		buildingImprovementsContainer = new Page(200, 260, colorPanel, new Point(colorPanel.getWidth() / 2 - 100, colorPanel.getHeight() / 2 - 110));
		buildingImprovementsContainer.color = new Color(129, 131, 203);
		buildingImprovementsContainer.backgroundVisibility = 0.85;
		buildingImprovementsContainer.setVisible(true);
		improvementsToBuildListBox = new ListBox(160, 180, colorPanel, new Point(colorPanel.getWidth() / 2 - 80, colorPanel.getHeight() / 2 - 90));
		improvementsToBuildListBox.backgroundVisibility = 0.5;
		improvementsToBuildListBox.setVisible(true);
		if (colorPanel.cityMap[location.x][location.y] == null)
		{
			if (colorPanel.improvementMap[location.x][location.y] == null)
			{
				ColorPanel.baseGeography baseGeographyTile = colorPanel.baseGeographicMap[location.x][location.y];
				ColorPanel.upperGeography upperGeographyTile = colorPanel.upperGeographicMap[location.x][location.y];
				ColorPanel.resourceGeography resourceGeographyTile = colorPanel.resourceGeographicMap[location.x][location.y];
				if (((baseGeographyTile == ColorPanel.baseGeography.plains || baseGeographyTile == ColorPanel.baseGeography.desert) && upperGeographyTile != ColorPanel.upperGeography.hills) || (colorPanel.riverTracker[location.x][location.y].isAdjacentToRiver() && (baseGeographyTile == ColorPanel.baseGeography.tundra || baseGeographyTile == ColorPanel.baseGeography.plains || baseGeographyTile == ColorPanel.baseGeography.desert)))
				{
					if (upperGeographyTile != ColorPanel.upperGeography.forest && upperGeographyTile != ColorPanel.upperGeography.jungle)
					{
						improvementsToBuildListBox.add("Farm");
					}
				}
				if (upperGeographyTile == ColorPanel.upperGeography.hills || resourceGeographyTile == ColorPanel.resourceGeography.aluminium || resourceGeographyTile == ColorPanel.resourceGeography.coal || resourceGeographyTile == ColorPanel.resourceGeography.gems || resourceGeographyTile == ColorPanel.resourceGeography.gold || resourceGeographyTile == ColorPanel.resourceGeography.iron || resourceGeographyTile == ColorPanel.resourceGeography.silver  || resourceGeographyTile == ColorPanel.resourceGeography.uranium)
				{
					improvementsToBuildListBox.add("Mine");
				}
				if (resourceGeographyTile == ColorPanel.resourceGeography.stone || resourceGeographyTile == ColorPanel.resourceGeography.marble)
				{
					improvementsToBuildListBox.add("Quarry");
				}
				if (upperGeographyTile == ColorPanel.upperGeography.forest || upperGeographyTile == ColorPanel.upperGeography.jungle)
				{
					if (upperGeographyTile == ColorPanel.upperGeography.forest)
					{
						improvementsToBuildListBox.add("Lumber Mill");
					}
					improvementsToBuildListBox.add("Chop Down");
				}
				if (resourceGeographyTile == ColorPanel.resourceGeography.bananas || resourceGeographyTile == ColorPanel.resourceGeography.incense || resourceGeographyTile == ColorPanel.resourceGeography.wine || resourceGeographyTile == ColorPanel.resourceGeography.cotton || resourceGeographyTile == ColorPanel.resourceGeography.dyes || resourceGeographyTile == ColorPanel.resourceGeography.sugar || resourceGeographyTile == ColorPanel.resourceGeography.spices || resourceGeographyTile == ColorPanel.resourceGeography.silk)
				{
					improvementsToBuildListBox.add("Plantation");
				}
				if (resourceGeographyTile == ColorPanel.resourceGeography.cattle || resourceGeographyTile == ColorPanel.resourceGeography.sheep || resourceGeographyTile == ColorPanel.resourceGeography.horses)
				{
					improvementsToBuildListBox.add("Pasture");
				}
				if (resourceGeographyTile == ColorPanel.resourceGeography.furs || resourceGeographyTile == ColorPanel.resourceGeography.deer || resourceGeographyTile == ColorPanel.resourceGeography.ivory)
				{
					improvementsToBuildListBox.add("Camp");
				}
				if (resourceGeographyTile == ColorPanel.resourceGeography.oil)
				{
					improvementsToBuildListBox.add("Oil Well");
				}
				if (baseGeographyTile != ColorPanel.baseGeography.ocean && baseGeographyTile != ColorPanel.baseGeography.shallowOcean && resourceGeographyTile != ColorPanel.resourceGeography.bananas && upperGeographyTile != ColorPanel.upperGeography.snow && upperGeographyTile != ColorPanel.upperGeography.snowHills)
				{
					improvementsToBuildListBox.add("Trading Post");
				}
			}
			if (!colorPanel.roadMap[location.x][location.y])
			{
				improvementsToBuildListBox.add("Road");
			}
		}
		if (improvementsToBuildListBox.getItemsSize() > 0)
		{
			buildButton = new Button(" Build", 80, 30, colorPanel, new Point(60, 215));
			buildButton.setFont(new Font("Rockwell", Font.BOLD, 25));
			buildButton.controlHandler = controlHandler;
			buildButton.id = "Build";
			buildingImprovementsContainer.add(buildButton);
			buildButton.setVisible(true);
		}
	}
	private void clearBuildPanel()
	{
		if (colorPanel.dimBackground != null)
		{
			colorPanel.dimBackground.dispose();
			colorPanel.dimBackground = null;
		}
		if (buildingImprovementsContainer != null)
		{
			buildingImprovementsContainer.dispose();
			buildingImprovementsContainer = null;
		}
		if (improvementsToBuildListBox != null)
		{
			improvementsToBuildListBox.dispose();
			improvementsToBuildListBox = null;
		}
		if (buildButton != null)
		{
			buildButton.dispose();
			buildButton = null;
		}
	}
	private class BuildingControlHandler extends ControlHandler
	{
		public void clickEvent(String id)
		{
			if (id.equals("Build"))
			{
				
			}
			if (id.equals("Dim Background"))
			{
				clearBuildPanel();
				colorPanel.repaint();
			}
		}
	}
}
