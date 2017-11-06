import java.awt.Graphics;
import java.awt.Point;

import ui.ImageBox;

public class Settler extends Unit 
{
	public Settler(ColorPanel colorPanel, int x, int y, Player player) 
	{
		super(colorPanel, x, y, player);
		warrior = false;
		attackOrUtilityimg = settleimg;
		unitName = "Settler";
	}
	public void paint(Graphics g, int x, int y)
	{
		super.paint(g, x, y);
		g.drawImage(settlerimg, (int) (x + colorPanel.xDistanceIncrementer / 2 - 24), 
				(int) (y + colorPanel.sideLength * 2 / 3 - 24), 51, 50, null);
	}
	public void refreshButtons()
	{
		super.refreshButtons();
		unitImage = new ImageBox(150, 170, colorPanel, new Point(0, 155), settlerimg);
		unitImage.bottomOriented = true;
		unitImage.setVisible(true);
	}
	protected void utilityAction()
	{
		if (colorPanel.territoryMap[location.x][location.y] == null)
		{
			colorPanel.cityMap[location.x][location.y] = new City(player, new Point(location));
			int[][] tiles = ColorPanel.getAllTilesWithin(location.x, location.y, 1);
			for (int i = 0; i < tiles[0].length; i++)
			{
				if (colorPanel.territoryMap[tiles[0][i]][tiles[1][i]] == null && !(colorPanel.warriorMap[tiles[0][i]][tiles[1][i]] != null && colorPanel.warriorMap[tiles[0][i]][tiles[1][i]].player != player) && !(colorPanel.workerMap[tiles[0][i]][tiles[1][i]] != null && colorPanel.workerMap[tiles[0][i]][tiles[1][i]].player != player))
				{
					colorPanel.territoryMap[tiles[0][i]][tiles[1][i]] = player.civilization;
					colorPanel.cityMap[location.x][location.y].tilesOwned.add(new Point(tiles[0][i], tiles[1][i]));
				}
			}
			kill();
			clearButtons();
			selected = null;
			colorPanel.players[colorPanel.turn].toDoList.add(new CityTask(colorPanel, colorPanel.cityMap[location.x][location.y]));
			colorPanel.rotateTasks();
			tiles = ColorPanel.getAllTilesWithin(location.x, location.y, 2);
			colorPanel.territoryMap[location.x][location.y] = player.civilization;
			for (int i = 0; i < tiles[0].length; i++)
			{
				colorPanel.fogOfWar[tiles[0][i]][tiles[1][i]]++;
				colorPanel.refreshTile(tiles[0][i], tiles[1][i]);
			}
			colorPanel.fogOfWar[location.x][location.y]++;
			colorPanel.refreshTile(location.x, location.y);
		}
	}
}