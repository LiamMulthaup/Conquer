import java.util.ArrayList;

public class Player 
{
	ColorPanel.worldCivilizations civilization;
	ArrayList<Action> actionQueue = new ArrayList<Action>();
	ArrayList<Task> toDoList = new ArrayList<Task>();
	ArrayList<City> cities = new ArrayList<City>();
	ArrayList<Unit> units = new ArrayList<Unit>();
	boolean[][] discoveredTiles = new boolean[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	int farthestDiscoveredNorth = -1, farthestDiscoveredWest = -1, farthestDiscoveredSouth = -1, farthestDiscoveredEast = -1;
	double xOffset = 0, yOffset = 0;
	int nameIndex = 1;
	int money;
	int science;
	int culture;
	
	int oil, iron, uranium, aluminium, horses, coal, gold, slilver, cotton, spices, pearls, gems, whales, marble, wine, incence, dyes, silk, ivory, furs, sugar; // resource amount
	public Player()
	{
		
	}
	public Player(ColorPanel.worldCivilizations civilization)
	{
		this.civilization = civilization;
	}
}
