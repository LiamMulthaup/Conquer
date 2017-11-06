import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ui.Button;
import ui.CircleButton;
import ui.Control;
import ui.ControlHandler;
import ui.DoodleImage;
import ui.Label;
import ui.ListBox;
import ui.Page;
import ui.TextBox;
public class ColorPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	enum baseGeography
	{
		plains(1), ocean(2), desert(3), tundra(4), oasis(5), mountain(6), ice(7), glacier(8), shallowOcean(9);
		public static baseGeography getGeography(int val)
		{
			baseGeography geography = null;
			baseGeography[] geographies = baseGeography.values();
			for (int i = 0; i < geographies.length; i++)
			{
				if (geographies[i].numval == val)
				{
					geography = geographies[i];
				}
			}
			return geography;
		}
		private int numval;
		public int getValue()
		{
			return this.numval;
		}
		private baseGeography(int val)
		{
			numval = val;
		}
	}
	enum upperGeography
	{
		hills(1), forest(2), snow(3), jungle(4), marsh(5), flat(6), snowHills(7);
		public static upperGeography getGeography(int val)
		{
			upperGeography geography = null;
			upperGeography[] geographies = upperGeography.values();
			for (int i = 0; i < geographies.length; i++)
			{
				if (geographies[i].numval == val)
				{
					geography = geographies[i];
				}
			}
			return geography;
		}
		private int numval;
		public int getValue()
		{
			return this.numval;
		}
		private upperGeography(int val)
		{
			numval = val;
		}
	}
	enum resourceGeography
	{
		oil(1), iron(2), uranium(3), aluminium(4), horses(5), coal(6),//strategic
		bananas(7),  fish(8), stone(9), cattle(10), sheep(11), deer(12), wheat(13), //bonus 
		gold(14), silver(15), cotton(16), spices(17), pearls(18), gems(19), whales(20), marble(21), wine(22), incense(23), //luxury
		dyes(24), silk(25), ivory(26), furs(27), sugar(28);
		
		public static resourceGeography getGeography(int val)
		{
			resourceGeography geography = null;
			resourceGeography[] geographies = resourceGeography.values();
			for (int i = 0; i < geographies.length; i++)
			{
				if (geographies[i].numval == val)
				{
					geography = geographies[i];
				}
			}
			return geography;
		}
		private int numval;
		public int getValue()
		{
			return this.numval;
		}
		private resourceGeography(int val)
		{
			numval = val;
		}
	}
	enum improvements
	{
		farm(1), fort(2), lumberMill(3), mine(4), tradingPost(5), camp(6), fishingBoats(7), offshorePlatform(8), oilWell(9),
		pasture(10), plantation(11), quarry(12);
		private int val;
		private improvements(int val)
		{
			this.val = val;
		}
		public int getValue()
		{
			return val;
		}
	}
	enum worldCivilizations
	{
		Greece("Greece", new Color(103, 109, 215)), Songhai("Songhai", new Color(228, 124, 19)), 
		Rome("Rome", new Color(130, 0, 77)), Germany("Germany", new Color(137, 141, 143)), Russia("Russia", new Color(236, 190, 18)), 
		Persia("Persia", new Color(240, 44, 0)), England("England", new Color(213, 0, 0)), India("India", new Color(0, 132, 0)), Mongolia("Mongolia", new Color(119, 0, 0)), 
		Denmark("Denmark", new Color(137, 38, 0)), Arabia("Arabia", new Color(0, 124, 90)), Iroquois("Iroquois", new Color(0, 70, 0)), 
		Aztec("Aztec", new Color(209, 0, 0)), France("France", new Color(168, 67, 218)), Japan("Japan", new Color(201, 198, 212)), Inca("The Incans", new Color(164, 231, 23)), 
		Egypt("Egypt", new Color(205, 188, 49)), Siam("Siam", new Color(255, 255, 85)), Korea("Korea", new Color(101, 5, 65)), 
		Ottoman("The Ottoman Empire", new Color(121, 156, 88)), America("America", new Color(5, 6, 248)), China("China", new Color(125, 196, 128)); 
		private String civilizationName;
		private Color civilizationColor;
		private Color civilizationBorder;
		private worldCivilizations(String s, Color civC)
		{
			civilizationColor = civC;
			civilizationName = s;
		}
		public static worldCivilizations getCivilization(String s)
		{
			for (int i = 0; i < values().length; i++)
			{
				if (s == values()[i].civilizationName)
				{
					return values()[i];
				}
			}
			return null;
		}
		public Color getColor()
		{
			return civilizationColor;
		}
		public String getCivilizationName()
		{
			return civilizationName;
		}
	}
	enum cityStates
	{
		Cape_Town, Ormus, Vancouver, Manila, Mombasa, Quebec_City, Ragusa, Panama_City, Sydney, Riga, Ur, Byblos, Mogadishu, Wellingtom, //Maritime
		Brussels, Bucharest, Florence, Buenos_Aires, Kuala_Lumpur, Milan, Monaco, Prague, Bratislava, Yerevan, Kabul, Kiev, Kyzyl, Bogota, //Cultured
		Almaty, Belgrade, Budapest, Hanoi, Sidon, Valetta, Sofia,                                                                           //Militaristic
		Antwerp, Cahokia, Colombo, Genoa, Hong_Kong, Antananarivo, Singapore, Tyre, Zanzibar, Zurich, Samarkand, Malacca, Melbourne, Vilnius, //Mercantile
		Geneva, Jerusalem, La_Venta, Lhasa, Vatican_City, Wittenburg, Kathmandu, Ife; 															//Religious
	}
	baseGeography[][] baseGeographicMap = new baseGeography[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	upperGeography[][] upperGeographicMap = new upperGeography[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	resourceGeography[][] resourceGeographicMap = new resourceGeography[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	improvements[][] improvementMap = new improvements[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	City[][] cityMap = new City[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	vertices[][] riverTracker = new vertices[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	boolean[][] discoveredTiles = new boolean[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	Unit[][] workerMap = new Unit[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	Unit[][] warriorMap = new Unit[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	int[][] movementPlan = new int[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	int[][] fogOfWar = new int[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	worldCivilizations[][] territoryMap = new worldCivilizations[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	boolean[][] roadMap = new boolean[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	double[][] movementPlanAngles = new double[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	int farthestDiscoveredNorth = 0, farthestDiscoveredWest = 0, farthestDiscoveredSouth = Conquer.gameScreenSizeY, farthestDiscoveredEast = Conquer.gameScreenSizeX;
	Player[] players;
	int turn = -1;
	int mX, mY, rX, rY;
	public static boolean mousePressed = false, clickOccurring = false, sliding = false, clickReleaseOccurring = false, clickReleaseOveride = false;
	double xOffset = 0, yOffset = 0, xOrigin = 0, yOrigin = 0;
	int techOriginX;
	final double sideLength = 65, xDistanceIncrementer = sideLength * Math.sqrt(3);
	int testRows, testColumns;
	boolean checkingifadjacent = false;
	int minimizeMiniMap = 1;
	Rectangle miniMapContainer;
	Rectangle miniMapBox;
	int miniMapMinimizeButtonCenterX, miniMapMinimizeButtonCenterY, miniMapMinimizeButtonRadius = 19;
	int miniMapMaximizeButtonCenterX, miniMapMaximizeButtonCenterY, miniMapMaximizeButtonRadius = 19;
	double mainMapImageOffsetX, mainMapImageOffsetY;
	ArrayList<Integer> tilesNeededToRefreshX = new ArrayList<Integer>();
	ArrayList<Integer> tilesNeededToRefreshY = new ArrayList<Integer>();
	Point[] movementTilesNeededToRefresh;
	ArrayList<Unit> unitsNeededToRefresh = new ArrayList<Unit>();
	ArrayList<Point> unitLocationsNeededToClear = new ArrayList<Point>();
	ArrayList<CityTag> cityTags = new ArrayList<CityTag>();
	int lastWidth = 0, lastHeight = 0;
	boolean menuOpen = false;
	boolean productionOpen = false;
	boolean techTreeOpen = false;
	CircleButton menuButton;
	CircleButton techButton;
	CircleButton socialButton;
	CircleButton closeButton;
	Page menu, dimBackground;
	ListBox savedGamesDisplay;
	Label resumeGameLabel, saveGameLabel, quitGameLabel, saveGameTextBoxLabel;
	Button saveGameButton, turnRotationButton;
	TextBox savedGameTextBox;
	BufferedImage mainMap = new BufferedImage(2000, 1000, BufferedImage.TYPE_INT_RGB);
	BufferedImage mainMapDrawing;
	BufferedImage unitMap = new BufferedImage( (int) (300 * Math.sqrt(3)), 150 * 3 / 2, BufferedImage.TRANSLUCENT);
	BufferedImage unitMapDrawing;
	BufferedImage movementMap;
	BufferedImage movementMapDrawing;
	BufferedImage miniMap = new BufferedImage( (int) (300 * Math.sqrt(3)), 150 * 3 / 2, BufferedImage.TYPE_INT_RGB);
	BufferedImage movingUnitImage;
	BufferedImage cityTagsImage = new BufferedImage( (int) (300 * Math.sqrt(3)), 150 * 3 / 2, BufferedImage.TRANSLUCENT);
	BufferedImage cityTagsImageDrawing;
	BufferedImage fogOfWarImage = new BufferedImage( (int) (300 * Math.sqrt(3)), 150 * 3 / 2, BufferedImage.TRANSLUCENT);
	DoodleImage techTree;
	colorPanelControlHandler backgroundControlListener = new colorPanelControlHandler(this);
	String fileName = "";
	Image oceanimg = new ImageIcon(this.getClass().getResource("DarkOcean_(Civ5).png")).getImage(), 
			desertimg = new ImageIcon(this.getClass().getResource("Desert_(Civ5).png")).getImage(),
		 	plainsimg = new ImageIcon(this.getClass().getResource("Grassland_(Civ5).png")).getImage(),
		 	forestimg = new ImageIcon(this.getClass().getResource("Forest_(Civ5).png")).getImage(),
		 	plainsHillimg = new ImageIcon(this.getClass().getResource("grassland_hills1.jpg")).getImage(),
			desertHillimg = new ImageIcon(this.getClass().getResource("deserthill.jpg")).getImage(),
			shallowsimg = new ImageIcon(this.getClass().getResource("Ocean_(Civ5).png")).getImage(),
			mountainimg = new ImageIcon(this.getClass().getResource("civ_mountain.jpg")).getImage(),
			jungleimg = new ImageIcon(this.getClass().getResource("Jungle_(Civ5).png")).getImage(),
			marshimg = new ImageIcon(this.getClass().getResource("Marsh_(Civ5).png")).getImage(),
			tundraimg = new ImageIcon(this.getClass().getResource("tundraTile.jpg")).getImage(),
			oasisimg = new ImageIcon(this.getClass().getResource("oasis5.jpg")).getImage(),
			snowimg = new ImageIcon(this.getClass().getResource("snow.jpg")).getImage(),
			glacierimg = new ImageIcon(this.getClass().getResource("glacier.jpg")).getImage(),
			tundraHillimg = new ImageIcon(this.getClass().getResource("tundraHill.jpg")).getImage(),
			snowHillimg = new ImageIcon(this.getClass().getResource("snowHill.jpg")).getImage(),
			furTreeimg = new ImageIcon(this.getClass().getResource("foxCamp.png")).getImage(),
			cloudimg = new ImageIcon(this.getClass().getResource("clouds.jpg")).getImage(),
			cityimg = new ImageIcon(this.getClass().getResource("CityTile.png")).getImage(),
			tradingPostimg = new ImageIcon(this.getClass().getResource("TradingPostInDesert2 Transparent.png")).getImage(),
			aluminumMineimg = new ImageIcon(this.getClass().getResource("aluminumMine.PNG")).getImage(),
			aluminumimg = new ImageIcon(this.getClass().getResource("Aluminum_(Civ5).png")).getImage(),
			bananasimg = new ImageIcon(this.getClass().getResource("Bananas.png")).getImage(),
			cattleimg = new ImageIcon(this.getClass().getResource("cattle.png")).getImage(),
			sheepimg = new ImageIcon(this.getClass().getResource("SheepOnAHill.PNG")).getImage(),
			sheepPastureimg = new ImageIcon(this.getClass().getResource("PastureHill.PNG")).getImage(),
			cattlePastureimg = new ImageIcon(this.getClass().getResource("CattleInPen.PNG")).getImage(),														coalimg = new ImageIcon(this.getClass().getResource("Coal_(Civ5).png")).getImage(),
			coalMineimg = new ImageIcon(this.getClass().getResource("CoalMine.PNG")).getImage(),
			farmimg = new ImageIcon(this.getClass().getResource("farm1.jpg")).getImage(),
			horseimg = new ImageIcon(this.getClass().getResource("Horses.png")).getImage(),
			horsePastureimg = new ImageIcon(this.getClass().getResource("HorsePasture.PNG")).getImage()
		 	;
	public void rotateTurn()
	{
		fogOfWar = new int[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
		if (Unit.selected != null)
		{
			Unit.selected.clearButtons();
			Unit.selected = null;
		}
		if (turn != -1)
		{
			players[turn].xOffset = xOffset;
			players[turn].yOffset = yOffset;
			
			players[turn].farthestDiscoveredWest = farthestDiscoveredWest;
			players[turn].farthestDiscoveredNorth = farthestDiscoveredNorth;
			players[turn].farthestDiscoveredSouth = farthestDiscoveredSouth;
			players[turn].farthestDiscoveredEast = farthestDiscoveredEast;
		}
		turn++;
		System.out.println("Turn: " + turn);
		if (turn >= players.length)
		{
			turn = 0;
		}
		if (players[turn].getClass() != AI.class)
		{
			discoveredTiles = players[turn].discoveredTiles;
			xOffset = players[turn].xOffset;
			yOffset = players[turn].yOffset;
			xOrigin = players[turn].xOffset;
			yOrigin = players[turn].yOffset;
			farthestDiscoveredWest = players[turn].farthestDiscoveredWest;
			farthestDiscoveredNorth = players[turn].farthestDiscoveredNorth;
			farthestDiscoveredSouth = players[turn].farthestDiscoveredSouth;
			farthestDiscoveredEast = players[turn].farthestDiscoveredEast;
			for (City city : players[turn].cities)
			{
				if (city.currentProduce != null)
				{
					if (city.currentProduce.turnsLeft != 0)
					{
						city.currentProduce.turnsLeft--;
					}
					if (city.currentProduce.turnsLeft == 0)
					{
						city.currentProduce.produce(city, this);
					}	
				}
				for (Point point : city.tilesOwned)
				{
					int[][] tiles = getAllTilesWithin(point.x, point.y, 1);
					for (int i = 0; i < tiles[0].length; i++)
					{
						fogOfWar[tiles[0][i]][tiles[1][i]]++;
						System.out.println(i);
					}
				}
			}
			players[turn].toDoList = new ArrayList<Task>();
			for (int i = 0; i < players[turn].cities.size(); i++)
			{
				if (players[turn].cities.get(i).currentProduce == null)
				{
					players[turn].toDoList.add(new CityTask(this, players[turn].cities.get(i)));
					System.out.println("City Added");
				}
			}
			for (Unit unit : players[turn].units)
			{
				int[][] tiles = getAllTilesWithin(unit.location.x, unit.location.y, unit.sight);
				for (int i = 0; i < tiles[0].length; i++)
				{
					fogOfWar[tiles[0][i]][tiles[1][i]]++;
				}
				fogOfWar[unit.location.x][unit.location.y]++;
				unit.mobility = unit.totalMobility;
				if (!unit.sleeping && !unit.scheduledToMove)
				{
					players[turn].toDoList.add(new UnitTask(this, unit));
				}
				if (unit.healing)
				{
					if (cityMap[unit.location.x][unit.location.y] != null)
					{
						if (cityMap[unit.location.x][unit.location.y].resistance)
						{
							unit.health+= 15;
						}
						else
						{
							unit.health+= 20;
						}
					}
					else if (territoryMap[unit.location.x][unit.location.y] == unit.owner)
					{
						unit.health+= 15;
					}
					else if (territoryMap[unit.location.x][unit.location.y] == null)
					{
						unit.health+= 10;
					}
					else
					{
						unit.health+= 5;
					}
					if (unit.health >= unit.totalHealth)
					{
						unit.health = unit.totalHealth;
						unit.healing = false;
						unit.sleeping = false;
						players[turn].toDoList.add(new UnitTask(this, unit));
					}
				}
			}
			if (players[turn].toDoList.size() > 0)
			{
				players[turn].toDoList.get(0).setTurnRotationButton();	
			}
			else
			{
				turnRotationButton.setText("                    Next turn");
			}
		}
		else
		{
			
		}
		developeMiniMap();
	}
	public void rotateTasks()
	{
		if (players[turn].toDoList.size() > 0)
		{
			players[turn].toDoList.get(0).setTurnRotationButton();
		}
		else
		{
			turnRotationButton.setText("                    Next turn");
		}
	}
	public void setUpPlayers(int[][] startingPoints)
	{
		for (int i = 0; i < startingPoints.length; i++)
		{
			int[][] tiles = getAllTilesWithin(startingPoints[i][0], startingPoints[i][1], 3);
			players[i].discoveredTiles[startingPoints[i][0]][startingPoints[i][1]] = true;
			for (int z = 0; z < tiles[0].length; z++)
			{
				players[i].discoveredTiles[tiles[0][z]][tiles[1][z]] = true;
			}
			players[i].farthestDiscoveredEast = startingPoints[i][0] + 6;
			players[i].farthestDiscoveredWest = startingPoints[i][0] - 6;
			players[i].farthestDiscoveredNorth = startingPoints[i][1] - 3;
			players[i].farthestDiscoveredSouth = startingPoints[i][1] + 3;
			if (players[i].farthestDiscoveredEast >= Conquer.gameScreenSizeX)
			{
				players[i].farthestDiscoveredEast = players[i].farthestDiscoveredEast - Conquer.gameScreenSizeX;
			}
			if (players[i].farthestDiscoveredSouth >= Conquer.gameScreenSizeY)
			{
				players[i].farthestDiscoveredSouth = Conquer.gameScreenSizeY - 1;
			}
			if (players[i].farthestDiscoveredNorth < 0)
			{
				players[i].farthestDiscoveredNorth = 0;
			}
			if (players[i].farthestDiscoveredWest < 0)
			{
				players[i].farthestDiscoveredWest = Conquer.gameScreenSizeX + players[i].farthestDiscoveredWest;
			}
			double newXOffset;
			double newYOffset;
			if (startingPoints[i][0] % 2 == 1)
			{
				newXOffset = (startingPoints[i][0] - 1) / 2 * xDistanceIncrementer;
				newYOffset = startingPoints[i][1] * sideLength * 3 / 2 - sideLength * 3 / 2;
			}
			else
			{
				newXOffset = (startingPoints[i][0]) / 2 * xDistanceIncrementer - xDistanceIncrementer / 2;
				newYOffset = startingPoints[i][1] * sideLength * 3 / 2 - sideLength * 3 / 2;
			}
			players[i].xOffset = - newXOffset + getWidth() / 2 - 70;
			players[i].yOffset = - newYOffset + getHeight() / 2 - 70;
			Settler firstSettler = new Settler(this, startingPoints[i][0], startingPoints[i][1], players[i]);
			players[i].units.add(firstSettler);
			int[][] tilesWithin1 = getAllTilesWithin(startingPoints[i][0], startingPoints[i][1], 1);
			for (int x = 0; x < tilesWithin1[0].length; x++)
			{
				if (baseGeographicMap[tilesWithin1[0][x]][tilesWithin1[1][x]] != baseGeography.mountain && baseGeographicMap[tilesWithin1[0][x]][tilesWithin1[1][x]] != baseGeography.glacier && baseGeographicMap[tilesWithin1[0][x]][tilesWithin1[1][x]] != baseGeography.shallowOcean)
				{
					warriorMap[tilesWithin1[0][x]][tilesWithin1[1][x]] = new Warrior(this, tilesWithin1[0][x], tilesWithin1[1][x], players[i]);
					players[i].units.add(warriorMap[tilesWithin1[0][x]][tilesWithin1[1][x]]);
					break;
				}
			}
			workerMap[startingPoints[i][0]][startingPoints[i][1]] = firstSettler;
		}
	}
	public boolean controlCityTagClickEvents(int rX, int rY, boolean clickOccurring)
	{
		for (int i = cityTags.size() - 1; i > -1; i--)
		{
			if (cityTags.get(i).contains(rX - xOffset, rY - yOffset))
			{
				clickOccurring = false;
				cityTags.get(i).clickEvent();
				break;
			}
		}
		return clickOccurring;
	}
	public static String getWorldLeader(worldCivilizations civilization)
	{
		String leader = "";
		if (civilization == worldCivilizations.Greece)
		{
			leader = "Alexander";
		}
		if (civilization == worldCivilizations.Songhai)
		{
			leader = "Askia";
		}
		if (civilization == worldCivilizations.Rome)
		{
			leader = "Augustus Caesar";
		}
		if (civilization == worldCivilizations.Germany)
		{
			leader = "Bismark";
		}
		if (civilization == worldCivilizations.Russia)
		{
			leader = "Catherine";
		}
		if (civilization == worldCivilizations.Persia)
		{
			leader = "Darius I";
		}
		if (civilization == worldCivilizations.England)
		{
			leader = "Elizabeth";
		}
		if (civilization == worldCivilizations.India)
		{
			leader = "Gandhi";
		}
		if (civilization == worldCivilizations.Mongolia)
		{
			leader = "Genghis Khan";
		}
		if (civilization == worldCivilizations.Denmark)
		{
			leader = "Harald Bluetooth";
		}
		if (civilization == worldCivilizations.Arabia)
		{
			leader = "Harun al-Rashid";
		}
		if (civilization == worldCivilizations.Iroquois)
		{
			leader = "Hiwatha";
		}
		if (civilization == worldCivilizations.Aztec)
		{
			leader = "Montezuma";
		}
		if (civilization == worldCivilizations.France)
		{
			leader = "Napoleon";
		}
		if (civilization == worldCivilizations.Japan)
		{
			leader = "Oda Nobunaga";
		}
		if (civilization == worldCivilizations.Inca)
		{
			leader = "Pachacuti";
		}
		if (civilization == worldCivilizations.Egypt)
		{
			leader = "Ramesses";
		}
		if (civilization == worldCivilizations.Siam)
		{
			leader = "Ramkhamhaeng";
		}
		if (civilization == worldCivilizations.Korea)
		{
			leader = "Sejong";
		}
		if (civilization == worldCivilizations.Ottoman)
		{
			leader = "Suleiman";
		}
		if (civilization == worldCivilizations.America)
		{
			leader = "Washington";
		}
		if (civilization == worldCivilizations.China)
		{
			leader = "Wu Zetian";
		}
		return leader;
	}
	public int[][] generateGeographicalMap() 
	{
		for (int x = 0; x < baseGeographicMap.length; x++)
		{
			for (int y = 0; y < baseGeographicMap[x].length; y++)
			{
				riverTracker[x][y] = new vertices();
				baseGeographicMap[x][y] = baseGeography.ocean;
				if (y == 0 || y == Conquer.gameScreenSizeY - 1)
				{
					baseGeographicMap[x][y] = baseGeography.glacier;
				}
			}
		}
		baseGeographicMap[1][1] = baseGeography.tundra;
		upperGeographicMap[1][1] = upperGeography.forest;
		resourceGeographicMap[1][1] = resourceGeography.furs;
		Random geographyTypePicker = new Random();
		List<Integer> riverStartPointsX = new ArrayList<Integer>();
		List<Integer> riverStartPointsY = new ArrayList<Integer>();
		for (int i = 0; i < Conquer.continents; i++)
		{
			int originPointY = geographyTypePicker.nextInt(Conquer.gameScreenSizeY - 2) + 1;
			int originPointX = 2 * geographyTypePicker.nextInt(Conquer.gameScreenSizeX / 2) + (originPointY % 2);
			while (baseGeographicMap[originPointX][originPointY] != baseGeography.ocean && baseGeographicMap[originPointX][originPointY] != baseGeography.shallowOcean)                           
			{
				originPointY = geographyTypePicker.nextInt(Conquer.gameScreenSizeY - 2) + 1;
				originPointX = 2 * geographyTypePicker.nextInt(Conquer.gameScreenSizeX / 2) + (originPointY % 2);
			}
			int landPerContinent = (int) ( (Conquer.landMassPercentage / 100.0) * Conquer.gameScreenSizeY * Conquer.gameScreenSizeX / 2 / Conquer.continents ) - (Conquer.gameScreenSizeY * Conquer.gameScreenSizeX / 4 / 2000 / Conquer.continents) + geographyTypePicker.nextInt( (int) (Conquer.gameScreenSizeY * Conquer.gameScreenSizeX / 2 / 2000 / Conquer.continents) + 1);
			List<Integer> continentAdjacentRows = new ArrayList<Integer>();
			List<Integer> continentAdjacentColumns = new ArrayList<Integer>();
			int sheepTimer = (int) (6 * Math.pow(geographyTypePicker.nextInt(26), 0.4));
			int cattleTimer = (int) (6 * Math.pow(geographyTypePicker.nextInt(26), 0.4));
			int horseTimer = (int) (6 * Math.pow(geographyTypePicker.nextInt(50), 0.4));
			while (landPerContinent > 0)
			{
				int biomeSize = 0;
				baseGeography biomeTypeBase = baseGeography.ocean;
				upperGeography biomeTypeUpper = upperGeography.flat;
				resourceGeography biomeTypeResource = null;
				int chanceOfHills = 0;
				if (originPointY < Conquer.gameScreenSizeY / 10 || originPointY > 9 * Conquer.gameScreenSizeY / 10)
				{
					biomeTypeBase = baseGeography.tundra;
					biomeSize = geographyTypePicker.nextInt(6) + 3;
					int tundraChance = geographyTypePicker.nextInt(12);
					chanceOfHills = geographyTypePicker.nextInt(40);
					if (originPointY > Conquer.gameScreenSizeY / 20 && originPointY < 19 * Conquer.gameScreenSizeY / 20)
					{
						if (tundraChance < 5)
						{
							biomeTypeUpper = upperGeography.forest;
						}
					}
					else if (tundraChance > 5 && tundraChance < 7)
					{
						biomeTypeBase = baseGeography.mountain;
						biomeSize = geographyTypePicker.nextInt(3) + 3;
					}
					else if (originPointY < Conquer.gameScreenSizeY / 25 || originPointY > 24 * Conquer.gameScreenSizeY / 25)
					{
						biomeTypeUpper = upperGeography.snow;
					}
				}
				else
				{
					double chanceOfMountains = 0.07;
					double chanceOfForest = Math.abs(originPointY - (Conquer.gameScreenSizeY / 2.0)) / (Conquer.gameScreenSizeY / 2.0);
					double chanceOfDesert = 0;
					if ((originPointY > Conquer.gameScreenSizeY / 4 + Conquer.gameScreenSizeY / 20 * Math.sin(originPointX / (Conquer.gameScreenSizeX / 4 / Math.PI)) && originPointY < Conquer.gameScreenSizeY * 3 / 8 + Conquer.gameScreenSizeY / 20 * Math.sin(originPointX / (Conquer.gameScreenSizeX / 4 / Math.PI))) || (originPointY > 5 * Conquer.gameScreenSizeY / 8 + Conquer.gameScreenSizeY / 20 * Math.sin(originPointX / (Conquer.gameScreenSizeX / 4 / Math.PI)) && originPointY < Conquer.gameScreenSizeY / 4 * 3 + Conquer.gameScreenSizeY / 20 * Math.sin(originPointX / (Conquer.gameScreenSizeX / 4 / Math.PI))))
					{
						chanceOfDesert = 1;
						chanceOfMountains = 0.07;
					}
					else
					{
						chanceOfDesert = 0.03;
					}
					double chanceOfPlains = 0;
					if ((originPointY > Conquer.gameScreenSizeY / 10 && originPointY < Conquer.gameScreenSizeY / 4) || (originPointY > 3 * Conquer.gameScreenSizeY / 4 && originPointY < 9 * Conquer.gameScreenSizeY / 10))
					{
						chanceOfPlains = 1;
						chanceOfMountains = 0.07;
					}
					else
					{
						chanceOfPlains = 0.3;
					}
					double chanceOfJungle = 0;
					if (originPointY > 3 * Conquer.gameScreenSizeY / 8 && originPointY < 5 * Conquer.gameScreenSizeY / 8)
					{
						chanceOfJungle = 0.1;
						chanceOfMountains = 0.03;
					}
					int biomeTypeRandom = geographyTypePicker.nextInt( (int) ( (100 * chanceOfForest) + (100 * chanceOfDesert) + (100 * chanceOfPlains) + (100 * chanceOfJungle) + (100 * chanceOfMountains) ));
					if (biomeTypeRandom >= 0 && biomeTypeRandom < (int) (100 * chanceOfForest))
					{
						biomeTypeBase = baseGeography.plains;
						biomeTypeUpper = upperGeography.forest;
						biomeSize = geographyTypePicker.nextInt(6) + 3;
					}
					if (biomeTypeRandom >= (int) ( (100 * chanceOfForest) ) && biomeTypeRandom < (int) ( (100 * chanceOfForest) + (100 * chanceOfDesert)))
					{
						biomeTypeBase = baseGeography.desert;
						biomeSize = geographyTypePicker.nextInt(8) + 7;
						chanceOfHills = geographyTypePicker.nextInt(40);
					}
					if (biomeTypeRandom >= (int) ( (100 * chanceOfForest) + (100 * chanceOfDesert) ) && biomeTypeRandom < (int) ( (100 * chanceOfForest) + (100 * chanceOfDesert) + (100 * chanceOfPlains)))
					{
						biomeTypeBase = baseGeography.plains;
						biomeSize = geographyTypePicker.nextInt(8) + 7;
						chanceOfHills = geographyTypePicker.nextInt(40);
					}
					if (biomeTypeRandom >= (int) ( (100 * chanceOfForest) + (100 * chanceOfDesert) + (100 * chanceOfPlains) ) && biomeTypeRandom < (int) ( (100 * chanceOfForest) + (100 * chanceOfDesert) + (100 * chanceOfPlains) + (100 * chanceOfJungle)))
					{
						biomeTypeBase = baseGeography.plains;
						biomeTypeUpper = upperGeography.jungle;
						biomeSize = geographyTypePicker.nextInt(3) + 1;
					}
					if (biomeTypeRandom >= (int) ( (100 * chanceOfForest) + (100 * chanceOfDesert) + (100 * chanceOfPlains) + (100 * chanceOfJungle) ) && biomeTypeRandom < (int) ( (100 * chanceOfForest) + (100 * chanceOfDesert) + (100 * chanceOfPlains) + (100 * chanceOfJungle) + (100 * chanceOfMountains)))
					{
						biomeTypeBase = baseGeography.mountain;
						biomeSize = geographyTypePicker.nextInt(3) + 3;
					}
					if (biomeTypeBase == baseGeography.ocean)
					{
						System.out.println("BiomeTypeRandom: " + biomeTypeRandom + "\nBiomeTypeRandom: " + " " + chanceOfForest + " " + chanceOfDesert + " " + chanceOfPlains + " " + chanceOfJungle + " " + chanceOfMountains);
					}
				}
				if (geographyTypePicker.nextInt(100) < chanceOfHills)
				{
					if (biomeTypeUpper == upperGeography.snow)
					{
						biomeTypeUpper = upperGeography.snowHills;
					}
					else
					{
						biomeTypeUpper = upperGeography.hills;
					}
				}
				int selectedX = originPointX;
				int selectedY = originPointY;
				baseGeographicMap[selectedX][selectedY] = biomeTypeBase;
				upperGeographicMap[selectedX][selectedY] = biomeTypeUpper;
				changeAllTilesWithinToShallowOcean(selectedX, selectedY, 2);
				landPerContinent-= 1;
				List<Integer> adjacentRows = new ArrayList<Integer>();
				List<Integer> adjacentColumns = new ArrayList<Integer>();
				if (biomeTypeUpper == upperGeography.snowHills)
				{
					biomeTypeUpper = upperGeography.snow;
				}
				if (biomeTypeUpper == upperGeography.hills)
				{
					biomeTypeUpper = upperGeography.flat;
				}
				while (biomeSize > 0)
				{
					if (testForValues(adjacentRows, adjacentColumns, getTopRightTile(selectedX, selectedY)[0], getTopRightTile(selectedX, selectedY)[1]) == false)
					{ 
						adjacentRows.add(getTopRightTile(selectedX, selectedY)[0]);
						adjacentColumns.add(getTopRightTile(selectedX, selectedY)[1]);
						continentAdjacentRows.add(getTopRightTile(selectedX, selectedY)[0]);
						continentAdjacentColumns.add(getTopRightTile(selectedX, selectedY)[1]);
					}
					if (testForValues(adjacentRows, adjacentColumns, getTopLeftTile(selectedX, selectedY)[0], getTopLeftTile(selectedX, selectedY)[1]) == false) 
					{
						adjacentRows.add(getTopLeftTile(selectedX, selectedY)[0]);
						adjacentColumns.add(getTopLeftTile(selectedX, selectedY)[1]);
						continentAdjacentRows.add(getTopLeftTile(selectedX, selectedY)[0]);
						continentAdjacentColumns.add(getTopLeftTile(selectedX, selectedY)[1]);
					}
					if (testForValues(adjacentRows, adjacentColumns, getRightTile(selectedX, selectedY)[0], getRightTile(selectedX, selectedY)[1]) == false) 
					{ 
						adjacentRows.add(getRightTile(selectedX, selectedY)[0]); 
						adjacentColumns.add(getRightTile(selectedX, selectedY)[1]);
						continentAdjacentRows.add(getRightTile(selectedX, selectedY)[0]); 
						continentAdjacentColumns.add(getRightTile(selectedX, selectedY)[1]);
					}
					if (testForValues(adjacentRows, adjacentColumns, getLeftTile(selectedX, selectedY)[0], getLeftTile(selectedX, selectedY)[1]) == false) 
					{
						adjacentRows.add(getLeftTile(selectedX, selectedY)[0]); 
						adjacentColumns.add(getLeftTile(selectedX, selectedY)[1]);	
						continentAdjacentRows.add(getLeftTile(selectedX, selectedY)[0]); 
						continentAdjacentColumns.add(getLeftTile(selectedX, selectedY)[1]);	
					}
					if (testForValues(adjacentRows, adjacentColumns, getBottomRightTile(selectedX, selectedY)[0], getBottomRightTile(selectedX, selectedY)[1]) == false) 
					{
						adjacentRows.add(getBottomRightTile(selectedX, selectedY)[0]);
						adjacentColumns.add(getBottomRightTile(selectedX, selectedY)[1]);
						continentAdjacentRows.add(getBottomRightTile(selectedX, selectedY)[0]);
						continentAdjacentColumns.add(getBottomRightTile(selectedX, selectedY)[1]);
					}
					if (testForValues(adjacentRows, adjacentColumns, getBottomLeftTile(selectedX, selectedY)[0], getBottomLeftTile(selectedX, selectedY)[1]) == false) 
					{ 
						adjacentRows.add(getBottomLeftTile(selectedX, selectedY)[0]); 
						adjacentColumns.add(getBottomLeftTile(selectedX, selectedY)[1]);
						continentAdjacentRows.add(getBottomLeftTile(selectedX, selectedY)[0]); 
						continentAdjacentColumns.add(getBottomLeftTile(selectedX, selectedY)[1]);
					}
					if (adjacentColumns.size() != 0)
					{
						int arrayCordinates = geographyTypePicker.nextInt(adjacentColumns.size());
						selectedX = adjacentColumns.get(arrayCordinates);
						selectedY = adjacentRows.get(arrayCordinates);
						removeAllOccurrences(selectedX, selectedY, adjacentColumns, adjacentRows);
						removeAllOccurrences(selectedX, selectedY, continentAdjacentColumns, continentAdjacentRows);
						if (geographyTypePicker.nextInt(100) < chanceOfHills)
						{
							if (biomeTypeUpper == upperGeography.snow)
							{
								biomeTypeUpper = upperGeography.snowHills;
							}
							else
							{
								if (biomeTypeBase == baseGeography.plains)
								{
									if (sheepTimer == 0)
									{
										biomeTypeResource = resourceGeography.sheep;
										sheepTimer = (int) (6 * Math.pow(geographyTypePicker.nextInt(26), 0.4));
									}
									else
									{
										sheepTimer--;
									}
								}
								biomeTypeUpper = upperGeography.hills;
							}
						}
						else
						{
							if (geographyTypePicker.nextInt(80) == 12 && biomeTypeBase == baseGeography.desert)
							{
								biomeTypeBase = baseGeography.oasis;
							}
						}
						if (biomeTypeBase == baseGeography.plains && biomeTypeUpper == upperGeography.flat)
						{
							if (cattleTimer == 0)
							{
								biomeTypeResource = resourceGeography.cattle;
								cattleTimer = (int) (6 * Math.pow(geographyTypePicker.nextInt(26), 0.4));
							}
							else
							{
								cattleTimer--;
								if (horseTimer == 0)
								{
									biomeTypeResource = resourceGeography.horses;
									horseTimer = (int) (6 * Math.pow(geographyTypePicker.nextInt(50), 0.4));
								}
								else
								{
									horseTimer--;
								}
							}
						}
						baseGeographicMap[selectedX][selectedY] = biomeTypeBase;
						upperGeographicMap[selectedX][selectedY] = biomeTypeUpper;
						resourceGeographicMap[selectedX][selectedY] = biomeTypeResource;
						changeAllTilesWithinToShallowOcean(selectedX, selectedY, 2);
						if (biomeTypeUpper == upperGeography.snowHills)
						{
							biomeTypeUpper = upperGeography.snow;
						}
						if (biomeTypeUpper == upperGeography.hills)
						{
							biomeTypeUpper = upperGeography.flat;
						}
						if (biomeTypeBase == baseGeography.oasis)
						{
							biomeTypeBase = baseGeography.desert;
						}
						landPerContinent-= 1;
						biomeSize--;
					}
					else
					{
						biomeSize = 0;
					}
					biomeTypeResource = null;
				}
				if (continentAdjacentColumns.size() != 0)
				{
					int arrayCordinates = geographyTypePicker.nextInt(continentAdjacentColumns.size());
					originPointX = continentAdjacentColumns.get(arrayCordinates);
					originPointY = continentAdjacentRows.get(arrayCordinates);
					removeAllOccurrences(originPointX, originPointY, continentAdjacentColumns, continentAdjacentRows);
				}
			}
			for (int x = 0; x < continentAdjacentColumns.size(); x++)
			{
				if (geographyTypePicker.nextInt(10) < 1 && baseGeographicMap[continentAdjacentColumns.get(x)][continentAdjacentRows.get(x)] == baseGeography.shallowOcean)
				{
					riverStartPointsX.add(continentAdjacentColumns.get(x));
					riverStartPointsY.add(continentAdjacentRows.get(x));
				}
			}
		}
		// Ends The Base / UpperGeography generator
		// ----------------------------------------
		
		
		
		// ----------------------------------------
		// Begins The River Generator
		for (int x = 0; x < riverStartPointsX.size(); x++)
		{
			if (baseGeographicMap[riverStartPointsX.get(x)][riverStartPointsY.get(x)] == baseGeography.shallowOcean) 
			{
			Boolean[] ifLand = new Boolean[6];
			baseGeography topRightTile = baseGeographicMap[ getTopRightTile(riverStartPointsX.get(x), riverStartPointsY.get(x))[1] ][ getTopRightTile(riverStartPointsX.get(x), riverStartPointsY.get(x))[0] ];
			baseGeography topLeftTile = baseGeographicMap[getTopLeftTile(riverStartPointsX.get(x), riverStartPointsY.get(x))[1]][ getTopLeftTile(riverStartPointsX.get(x), riverStartPointsY.get(x))[0]];
			baseGeography rightTile = baseGeographicMap[getRightTile(riverStartPointsX.get(x), riverStartPointsY.get(x))[1]][ getRightTile(riverStartPointsX.get(x), riverStartPointsY.get(x))[0]];
			baseGeography leftTile = baseGeographicMap[getLeftTile(riverStartPointsX.get(x), riverStartPointsY.get(x))[1]][ getLeftTile(riverStartPointsX.get(x), riverStartPointsY.get(x))[0]];
			baseGeography bottomRightTile = baseGeographicMap[getBottomRightTile(riverStartPointsX.get(x), riverStartPointsY.get(x))[1]][ getBottomRightTile(riverStartPointsX.get(x), riverStartPointsY.get(x))[0]];
			baseGeography bottomLeftTile = baseGeographicMap[getBottomLeftTile(riverStartPointsX.get(x), riverStartPointsY.get(x))[1]][ getBottomLeftTile(riverStartPointsX.get(x), riverStartPointsY.get(x))[0]];
			ifLand[5] = (topRightTile != baseGeography.ocean && topRightTile != baseGeography.shallowOcean  && topRightTile != baseGeography.glacier);
			ifLand[0] = (topLeftTile != baseGeography.ocean && topLeftTile != baseGeography.shallowOcean && topLeftTile != baseGeography.glacier);
			ifLand[4] = (rightTile != baseGeography.ocean && rightTile != baseGeography.shallowOcean && rightTile != baseGeography.glacier);
			ifLand[1] = (leftTile != baseGeography.ocean && leftTile != baseGeography.shallowOcean && leftTile != baseGeography.glacier);
			ifLand[3] = (bottomRightTile != baseGeography.ocean && bottomRightTile != baseGeography.shallowOcean && bottomRightTile != baseGeography.glacier);
			ifLand[2] = (bottomLeftTile != baseGeography.ocean && bottomLeftTile != baseGeography.shallowOcean  && bottomLeftTile != baseGeography.glacier);
			List<Integer> ifVertices = new ArrayList<Integer>();
			for (int i = 0; i < 6; i++)
			{
				if (ifLand[i] == true && ifLand[(i + 1) % 6] == true)
				{
					ifVertices.add(i);
				}
			}
			if (ifVertices.size() != 0)
			{
				int startingVertice = ifVertices.get(geographyTypePicker.nextInt(ifVertices.size()));
				int riverDirection = startingVertice;
				riverTracker[riverStartPointsX.get(x)][riverStartPointsY.get(x)].verticesRiverActive[startingVertice] = true;
				int[] selectedRowAndColumn = { riverStartPointsY.get(x), riverStartPointsX.get(x) };
				int newRiverLength = 0;
				while (true)
				{
					selectedRowAndColumn = parseHexagonId(selectedRowAndColumn[1],selectedRowAndColumn[0], startingVertice);
					if (baseGeographicMap[selectedRowAndColumn[1]][selectedRowAndColumn[0]] != baseGeography.ocean && baseGeographicMap[selectedRowAndColumn[1]][selectedRowAndColumn[0]] != baseGeography.shallowOcean && baseGeographicMap[selectedRowAndColumn[1]][selectedRowAndColumn[0]] != baseGeography.glacier)
					{
						newRiverLength++;
						selectedRowAndColumn = parseHexagonId(selectedRowAndColumn[1],selectedRowAndColumn[0], (startingVertice + 1) % 6);
						if (baseGeographicMap[selectedRowAndColumn[1]][selectedRowAndColumn[0]] != baseGeography.ocean && baseGeographicMap[selectedRowAndColumn[1]][selectedRowAndColumn[0]] != baseGeography.shallowOcean && baseGeographicMap[selectedRowAndColumn[1]][selectedRowAndColumn[0]] != baseGeography.glacier)
						{
							newRiverLength++;
						}
						else
						{
							break;
						}
					}
					else
					{
						break;
					}
				}
				if (newRiverLength > 5)
				{
					if (newRiverLength > 8 && geographyTypePicker.nextBoolean())
					{
						int amountToMultiply = 2;
						if (newRiverLength > 10 && geographyTypePicker.nextBoolean()){ amountToMultiply += 1; }
						if (newRiverLength > 12 && geographyTypePicker.nextBoolean()){ amountToMultiply += 1; }
						if (newRiverLength > 14 && geographyTypePicker.nextBoolean()){ amountToMultiply += 2; }
						if (newRiverLength > 16 && geographyTypePicker.nextBoolean()){ amountToMultiply += 2; }
						if (newRiverLength > 18 && geographyTypePicker.nextBoolean()){ amountToMultiply += 2; }
						if (newRiverLength > 20 && geographyTypePicker.nextBoolean()){ amountToMultiply += 2; }
						if (newRiverLength > 22 && geographyTypePicker.nextBoolean()){ amountToMultiply += 2; }
						if (newRiverLength > 24 && geographyTypePicker.nextBoolean()){ amountToMultiply += 2; }
						newRiverLength *= amountToMultiply;
					}
					newRiverLength--;
					Boolean firstTime = true;
					int riverThreads = 1;
					List<Integer> offShootStartX = new ArrayList<Integer>();
					List<Integer> offShootStartY = new ArrayList<Integer>();
					List<Integer> offShootStartVert = new ArrayList<Integer>();
					List<Integer> offShootStartDirection = new ArrayList<Integer>();
					int idOffset = 0;
					
					while (riverThreads > 0)
					{
					int selectedVert = startingVertice;
					int selectedColumn = riverStartPointsX.get(x), selectedRow = riverStartPointsY.get(x);
					int currentRiverDirection = riverDirection;
					int distanceUntilSplit = -1;
					if (geographyTypePicker.nextBoolean() && firstTime)
					{
						distanceUntilSplit = 7 + geographyTypePicker.nextInt(6);
					}
					if (firstTime == false)
					{
						selectedVert = offShootStartVert.get(0);
						selectedColumn = offShootStartX.get(0);
						selectedRow = offShootStartY.get(0);
						currentRiverDirection = offShootStartDirection.get(0);
						riverDirection = currentRiverDirection;
						offShootStartVert.remove(0);
						offShootStartX.remove(0);
						offShootStartY.remove(0);
						offShootStartDirection.remove(0);
						distanceUntilSplit = -1;
						newRiverLength = 0;
						selectedRowAndColumn[1] = selectedColumn;
						selectedRowAndColumn[0] = selectedRow;
						/*
						while (true)
						{
							System.out.println(riverDirection);
							
							selectedRowAndColumn = parseHexagonId(selectedRowAndColumn[1], selectedRowAndColumn[0], riverDirection);
							System.out.println(selectedRowAndColumn[0]);
							System.out.println(selectedRowAndColumn[1]);
							if (selectedRowAndColumn[0] != -1 && selectedRowAndColumn[0] != 200 && baseGeographicMap[selectedRowAndColumn[1]][selectedRowAndColumn[0]] != baseGeography.ocean && baseGeographicMap[selectedRowAndColumn[1]][selectedRowAndColumn[0]] != baseGeography.shallowOcean && baseGeographicMap[selectedRowAndColumn[1]][selectedRowAndColumn[0]] != baseGeography.glacier)
							{
								newRiverLength++;
								selectedRowAndColumn = parseHexagonId(selectedRowAndColumn[1],selectedRowAndColumn[0], (riverDirection + 1) % 6);
								if (selectedRowAndColumn[0] != -1 && selectedRowAndColumn[0] != 200 && baseGeographicMap[selectedRowAndColumn[1]][selectedRowAndColumn[0]] != baseGeography.ocean && baseGeographicMap[selectedRowAndColumn[1]][selectedRowAndColumn[0]] != baseGeography.shallowOcean && baseGeographicMap[selectedRowAndColumn[1]][selectedRowAndColumn[0]] != baseGeography.glacier)
								{
									newRiverLength++;
								}
								else
								{
									break;
								}
							}
							else
							{
								break;
							}
						}
						if (newRiverLength > baseRiverLength / 3)
						{
							newRiverLength = baseRiverLength / 3;
						}
						*/
						newRiverLength = 4 + geographyTypePicker.nextInt(5);
					}
					firstTime = false;
					adjustSimilarRiverVertices(selectedColumn, selectedRow, selectedVert, x + (riverStartPointsX.size() * idOffset));
					int sidesUntilDirectionChange = 5 + geographyTypePicker.nextInt(4);
					int previousDirection = 0;
					for (int riverIncrementer = 0; riverIncrementer < newRiverLength; riverIncrementer++)
					{
						int direction;
						if (selectedVert % 2 == 1)
						{
							if (currentRiverDirection % 2 == 1)
							{
								direction = currentRiverDirection;
							}
							else
							{
								Boolean rightOrLeft = geographyTypePicker.nextBoolean();
								if (rightOrLeft) 
								{ 
									direction = ((currentRiverDirection + 6) - 1) % 6;
									if (direction == previousDirection)
									{
										direction = (currentRiverDirection + 1) % 6;
									}
								}
								else 
								{ 
									direction = (currentRiverDirection + 1) % 6; 
									if (direction == previousDirection)
									{
										direction = (currentRiverDirection + 5) % 6;
									}
								}
							}
						}
						else
						{
							if (currentRiverDirection % 2 == 0)
							{
								direction = currentRiverDirection;
							}
							else
							{
								Boolean rightOrLeft = geographyTypePicker.nextBoolean();
								if (rightOrLeft) 
								{ 
									direction = ((currentRiverDirection + 6) - 1) % 6; 
									if (direction == previousDirection)
									{
										direction = (currentRiverDirection + 1) % 6;
									}
								}
								else 
								{ 
									direction = (currentRiverDirection + 1) % 6;
									if (direction == previousDirection)
									{
										direction = (currentRiverDirection + 5) % 6;
									}
								}
							}
						}
						distanceUntilSplit--;
						
						if (distanceUntilSplit == 0)
						{
							riverThreads++;
							distanceUntilSplit = 6 + geographyTypePicker.nextInt(20);
							if ((direction + 2) % 6 == previousDirection)
							{
								offShootStartDirection.add((previousDirection + 2) % 6);
							}
							else if ((previousDirection + 2) % 6 == direction)
							{
								offShootStartDirection.add((direction + 2) % 6);
							}
							offShootStartX.add(selectedColumn);
							offShootStartY.add(selectedRow);
							offShootStartVert.add(selectedVert);
							sidesUntilDirectionChange += 4;
						}
						previousDirection = (direction + 3) % 6;
						if (direction == selectedVert)
						{ 
							selectedRow = parseHexagonId(selectedColumn, selectedRow, selectedVert)[0];
							selectedColumn = parseHexagonId(selectedColumn, selectedRow, selectedVert)[1];
							selectedVert = (selectedVert + 1) % 6;
						}
						else 
						{
							selectedVert = (direction - (selectedVert - (direction - 3)) + 12) % 6;
						}
						if (adjustSimilarRiverVertices(selectedColumn, selectedRow, selectedVert, x + (riverStartPointsX.size() * idOffset)))
						{
							break;
						}
						sidesUntilDirectionChange--;
						
						if (sidesUntilDirectionChange == 0)
						{
							if (currentRiverDirection == (riverDirection + 2) % 6)
							{
								if (geographyTypePicker.nextBoolean())
								{
									currentRiverDirection = (currentRiverDirection + 5) % 6;
								}
							}
							else if (currentRiverDirection == (riverDirection - 2 + 6) % 6)
							{
								if (geographyTypePicker.nextBoolean())
								{
									currentRiverDirection++;
									currentRiverDirection %= 6;
								}
							}
							else
							{
								int directionChange = geographyTypePicker.nextInt(3) - 1;
								currentRiverDirection = (currentRiverDirection + 6 + directionChange) % 6;
							}
							sidesUntilDirectionChange = 3 + geographyTypePicker.nextInt(5);
						}
					}
					riverThreads--;
					idOffset++;
					}
				}
			}
		}
		}
		//Ends the River Generator
		//-----------------------------------------
		int[][] startingPoints = new int[players.length][2];
		for (int i = 0; i < startingPoints.length; i++)
		{
			startingPoints[i][0] = -100;
			startingPoints[i][1] = -100;
		}
		//-----------------------------------------
		//Begins the player starting point search
		{
			ArrayList<ArrayList<Integer>> scoreSpreadSheetX = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> scoreSpreadSheetY = new ArrayList<ArrayList<Integer>>();
            for (int x = 0; x < Conquer.gameScreenSizeX; x++)
            {
                for (int y = 0; y < Conquer.gameScreenSizeY; y++)
                {
                    int score = 0;
                    if (riverTracker[x][y].isAdjacentToRiver())
                    {
                        score+= 45;
                    }
                    int[][] tilesWithin4 = getAllTilesWithin(x, y, 4);
                    for (int i = 0; i < tilesWithin4[0].length; i++)
                    {
                    	int tileX = tilesWithin4[0][i];
                    	int tileY = tilesWithin4[1][i];
                    	if (tileY > -1 && tileY < Conquer.gameScreenSizeY)
                    	{
                    		baseGeography tileBaseGeography = baseGeographicMap[tileX][tileY];
                    		upperGeography tileUpperGeography = upperGeographicMap[tileX][tileY];
                    		resourceGeography tileResourceGeography = resourceGeographicMap[tileX][tileY];
                    		if (tileBaseGeography == baseGeography.plains && (tileUpperGeography != upperGeography.forest || tileUpperGeography != upperGeography.jungle || tileUpperGeography != upperGeography.marsh))
                    		{
                    			score+= 3;
                    		}
                    		if ((tileBaseGeography == baseGeography.desert || tileBaseGeography == baseGeography.tundra) && riverTracker[tileX][tileY].isAdjacentToRiver())
                    		{
                    			score+= 3;
                    		}
                    		if (tileBaseGeography == baseGeography.plains && riverTracker[tileX][tileY].isAdjacentToRiver())
                    		{
                    			score+= 1;
                    		}
                    		if (tileUpperGeography == upperGeography.forest)
                    		{
                    			score+= 1;
                    		}
                    	}
                    }
                    int[][] tilesWithin1 = getAllTilesWithin(x, y, 1);
                    for (int i = 0; i < tilesWithin1[0].length; i++)
                    {
                    	if (tilesWithin1[1][i] != -1)
                    	{
                    		baseGeography tileBaseGeography = baseGeographicMap[tilesWithin1[0][i]][tilesWithin1[1][i]];
                    		upperGeography tileUpperGeography = upperGeographicMap[tilesWithin1[0][i]][tilesWithin1[1][i]];
                    		resourceGeography tileResourceGeography = resourceGeographicMap[tilesWithin1[0][i]][tilesWithin1[1][i]];
                        	if (tileBaseGeography == baseGeography.plains && tileUpperGeography != upperGeography.jungle && tileUpperGeography != upperGeography.forest)
                        	{
                        		score+= 6;
                        	}
                    	}
                    }
                    while (score > scoreSpreadSheetX.size() - 1)
                    {
                        scoreSpreadSheetX.add(new ArrayList<Integer>());
                        scoreSpreadSheetY.add(new ArrayList<Integer>());
                    }
                    if (baseGeographicMap[x][y] == baseGeography.mountain || baseGeographicMap[x][y] == baseGeography.ocean || baseGeographicMap[x][y] == baseGeography.shallowOcean || baseGeographicMap[x][y] == baseGeography.glacier)
                    {
                    	score = 0; 
                    }
                    scoreSpreadSheetX.get(score).add(x);
                    scoreSpreadSheetY.get(score).add(y);
                }
            }
            int[] scores = new int[startingPoints.length];
            int a = 0;
            for (int i = 0; i < players.length; i++)
            {
            	boolean canSettle = true;
            	for (int x = scoreSpreadSheetX.size() - 1; x >= 0; x--)
            	{
            		for (int y = 0; y < scoreSpreadSheetX.get(x).size(); y++)
            		{
            			a++;
            			canSettle = true;
            			int row = scoreSpreadSheetY.get(x).get(y);
            			int column = scoreSpreadSheetX.get(x).get(y);
            			for (int z = 0; z < startingPoints.length; z++)
            			{
            				if (tileIsWithinRadius(column, row, Conquer.distanceBetweenPlayers, startingPoints[z][0], startingPoints[z][1]) || 
            						(startingPoints[z][0] == column && startingPoints[z][1] == row)
            						)
            				{
            					canSettle = false;
            				}
            			}
            			boolean desert = (row > Conquer.gameScreenSizeY / 4 && row < Conquer.gameScreenSizeX / 8 * 3) || (row > Conquer.gameScreenSizeX / 8 * 5 && row < Conquer.gameScreenSizeX * 3 / 4);
            			boolean tundra = (row < Conquer.gameScreenSizeY / 10) || (row > Conquer.gameScreenSizeY * 9 / 10);
            			boolean jungle = (row > 3 * Conquer.gameScreenSizeY / 8 && row < 5 * Conquer.gameScreenSizeY / 8);
            			boolean plains = ((row > Conquer.gameScreenSizeY / 10 && row < Conquer.gameScreenSizeY / 4) || (row > 3 * Conquer.gameScreenSizeY / 4 && row < 9 * Conquer.gameScreenSizeY / 10));
            			if (players[i].civilization == worldCivilizations.Egypt && (baseGeographicMap[column][row] != baseGeography.desert || !desert))
            			{
            				canSettle = false;
            			}
            			else if (players[i].civilization == worldCivilizations.Russia && (!tundra))
            			{
            				canSettle = false;
            			}
            			else if (players[i].civilization == worldCivilizations.England)
            			{
            				if (!isOceanCoastal(column, row) || !plains)
            				{
            					canSettle = false;
            				}
            			}
            			else if (players[i].civilization == worldCivilizations.Denmark && (!tundra))
            			{
            				canSettle = false;
            			}
            			else if (players[i].civilization == worldCivilizations.Mongolia && (!plains))
            			{
            				canSettle = false;
            			}
            			else if (players[i].civilization == worldCivilizations.Japan)
            			{
            				if (!isOceanCoastal(column, row) || !jungle)
            				{
            					canSettle = false;
            				}
            			}
            			else if (players[i].civilization == worldCivilizations.Greece)
            			{
            				if (!isOceanCoastal(column, row))
            				{
            					canSettle = false;
            				}
            			}
            			else if (players[i].civilization == worldCivilizations.Ottoman)
            			{
            				if (!isOceanCoastal(column, row))
            				{
            					canSettle = false;
            				}
            			}
            			else if (players[i].civilization == worldCivilizations.Arabia && !desert)
            			{
            				canSettle = false;
            			}
            			else if (players[i].civilization == worldCivilizations.Korea)
            			{
            				if (!isOceanCoastal(column, row))
            				{
            					canSettle = false;
            				}
            			}
            			else if (players[i].civilization == worldCivilizations.Aztec && !desert)
            			{
            				canSettle = false;
            			}
            			else if (players[i].civilization == worldCivilizations.Iroquois)
            			{
            				int[][] isForestTiles = getAllTilesWithin(column, row, 2);
            				boolean forest = false;
            				for (int f = 0; f < isForestTiles[0].length; f++)
            				{
            					if (isForestTiles[0][f] != -1)
            					{
            						if (upperGeographicMap[isForestTiles[0][f]][isForestTiles[1][f]] == upperGeography.forest)
            						{
            							forest = true;
            						}
            					}
            				}
            				if (!forest) { canSettle = false; }
            			}
            			else if (players[i].civilization == worldCivilizations.Inca && !jungle)
            			{
            				canSettle = false;
            			}
            			else if (players[i].civilization == worldCivilizations.Songhai && tundra)
            			{
            				canSettle = false;
            			}
            			else if (players[i].civilization == worldCivilizations.Siam && !jungle)
            			{
            				canSettle = false;
            			}
            			else if (players[i].civilization == worldCivilizations.India && !plains)
            			{
            				canSettle = false;
            			}
            			else if (players[i].civilization == worldCivilizations.Persia && !desert)
            			{
            				canSettle = false;
            			}
            			else if (players[i].civilization == worldCivilizations.Rome && !plains)
            			{
            				canSettle = false;
            			}
            			else if (players[i].civilization == worldCivilizations.Denmark && !tundra)
            			{
            				canSettle = false;
            			}
            			if (canSettle)
            			{
            				scores[i] = x;
            				startingPoints[i][0] = scoreSpreadSheetX.get(x).get(y);
            				startingPoints[i][1] = scoreSpreadSheetY.get(x).get(y);
            				upperGeographicMap[column][row] = upperGeography.hills;
            				break;
            			}
            		}
            		if (canSettle)
            		{
            			break;
            		}
            	}
            }
            System.out.println(a);
            for (int i = 0; i < startingPoints.length; i++)
            {
            	System.out.println("The Points: Score: " + scores[i] + " x: " + startingPoints[i][0] + " y: " + startingPoints[i][1]);
            }
            System.out.println(tileIsWithinRadius(1 ,1, 10, 1, 20));
		}
		//Ends the player starting point search
		//-----------------------------------------
		
		//-----------------------------------------
		// Begins the Island Generator
		
				for (int i = 0; i < Conquer.islands; i++)
				{
					while (true)
					{
						int startingRow = geographyTypePicker.nextInt(Conquer.gameScreenSizeY);
						int startingColumn = 2 * geographyTypePicker.nextInt(Conquer.gameScreenSizeX / 2) + (startingRow % 2);
						if (baseGeographicMap[startingColumn][startingRow] == baseGeography.ocean)
						{
							baseGeography biomeTypeBase = baseGeography.ocean;
							upperGeography biomeTypeUpper = upperGeography.flat;
							if (startingColumn < Conquer.gameScreenSizeY / 10 || startingRow > 9 * Conquer.gameScreenSizeY / 10)
							{
								biomeTypeBase = baseGeography.tundra;
							}
							else
							{
								int randomBiome = geographyTypePicker.nextInt(10);
								if (randomBiome == 0 || randomBiome == 1)
								{
									biomeTypeBase = baseGeography.plains;
									biomeTypeUpper = upperGeography.jungle;
								}
								if (randomBiome > 1)
								{
									biomeTypeBase = baseGeography.plains;
								}
							}
							int islandSize = 1 + geographyTypePicker.nextInt(8);
							if (geographyTypePicker.nextBoolean())
							{
								islandSize += geographyTypePicker.nextInt(3);
								if (geographyTypePicker.nextBoolean())
								{
									islandSize += geographyTypePicker.nextInt(3);
									if (geographyTypePicker.nextBoolean())
									{
										islandSize += geographyTypePicker.nextInt(6);
									}
								}
							}
							int selectedX = startingColumn;
							int selectedY = startingRow;
							List<Integer> adjacentColumns = new ArrayList<Integer>();
							List<Integer> adjacentRows = new ArrayList<Integer>();
							baseGeographicMap[selectedX][selectedY] = biomeTypeBase;
							upperGeographicMap[selectedX][selectedY] = biomeTypeUpper;
							changeAllTilesWithinToShallowOcean(selectedX, selectedY, 2);

							for (int a = 0; a < islandSize; a++)
							{
								if (testForValues(adjacentRows, adjacentColumns, getTopRightTile(selectedX, selectedY)[0], getTopRightTile(selectedX, selectedY)[1]) == false)
								{ 
									adjacentRows.add(getTopRightTile(selectedX, selectedY)[0]);
									adjacentColumns.add(getTopRightTile(selectedX, selectedY)[1]);
								}
								if (testForValues(adjacentRows, adjacentColumns, getTopLeftTile(selectedX, selectedY)[0], getTopLeftTile(selectedX, selectedY)[1]) == false) 
								{
									adjacentRows.add(getTopLeftTile(selectedX, selectedY)[0]);
									adjacentColumns.add(getTopLeftTile(selectedX, selectedY)[1]);
								}
								if (testForValues(adjacentRows, adjacentColumns, getRightTile(selectedX, selectedY)[0], getRightTile(selectedX, selectedY)[1]) == false) 
								{ 
									adjacentRows.add(getRightTile(selectedX, selectedY)[0]); 
									adjacentColumns.add(getRightTile(selectedX, selectedY)[1]);
								}
								if (testForValues(adjacentRows, adjacentColumns, getLeftTile(selectedX, selectedY)[0], getLeftTile(selectedX, selectedY)[1]) == false) 
								{
									adjacentRows.add(getLeftTile(selectedX, selectedY)[0]); 
									adjacentColumns.add(getLeftTile(selectedX, selectedY)[1]);
								}
								if (testForValues(adjacentRows, adjacentColumns, getBottomRightTile(selectedX, selectedY)[0], getBottomRightTile(selectedX, selectedY)[1]) == false) 
								{
									adjacentRows.add(getBottomRightTile(selectedX, selectedY)[0]);
									adjacentColumns.add(getBottomRightTile(selectedX, selectedY)[1]);
								}
								if (testForValues(adjacentRows, adjacentColumns, getBottomLeftTile(selectedX, selectedY)[0], getBottomLeftTile(selectedX, selectedY)[1]) == false) 
								{ 
									adjacentRows.add(getBottomLeftTile(selectedX, selectedY)[0]); 
									adjacentColumns.add(getBottomLeftTile(selectedX, selectedY)[1]);
								}
								if (adjacentColumns.size() != 0)
								{
									int arrayCordinates = geographyTypePicker.nextInt(adjacentColumns.size());
									selectedX = adjacentColumns.get(arrayCordinates);
									selectedY = adjacentRows.get(arrayCordinates);
									removeAllOccurrences(selectedX, selectedY, adjacentColumns, adjacentRows);
									baseGeographicMap[selectedX][selectedY] = biomeTypeBase;
									upperGeographicMap[selectedX][selectedY] = biomeTypeUpper;
									changeAllTilesWithinToShallowOcean(selectedX, selectedY, 2);
								}
							}
							break;
						}
					}
				}
				// Ends the Island Generator
				//-----------------------------------------
		return startingPoints;
	}
	public boolean isCoastal(int column, int row)
	{
		int[][] tiles = getAllTilesWithin(column, row, 1);
		for (int i = 0; i < tiles[0].length; i++)
		{
			if (tiles[0][i] != -1)
			{
				if (baseGeographicMap[tiles[0][i]][tiles[1][i]] == baseGeography.shallowOcean)
				{
					return true;
				}
			}
		}
		return false;
	}
	public boolean isOceanCoastal(int column, int row)
	{
		if (isCoastal(column, row))
		{
			int[][] tiles = getAllTilesWithin(column, row, 1);
			for (int i = 0; i < tiles[0].length; i++)
			{
				if (tiles[1][i] != -1)
				{
					if (baseGeographicMap[tiles[0][i]][tiles[1][i]] == baseGeography.shallowOcean)
					{
						ArrayList<Integer> usedXPoints = new ArrayList<Integer>();
						ArrayList<Integer> usedYPoints = new ArrayList<Integer>();
						ArrayList<Integer> activeXPoints = new ArrayList<Integer>();
						ArrayList<Integer> activeYPoints = new ArrayList<Integer>();
						activeXPoints.add(tiles[0][i]);
						activeYPoints.add(tiles[1][i]);
						while (activeXPoints.size() > 0)
						{
							ArrayList<Integer> subActiveXPoints = new ArrayList<Integer>();
							ArrayList<Integer> subActiveYPoints = new ArrayList<Integer>();
							for (int z = 0; z < activeXPoints.size(); z++)
							{
								usedXPoints.add(activeXPoints.get(z));
								usedYPoints.add(activeYPoints.get(z));
								int[][] surroundingTiles = getAllTilesWithin(activeXPoints.get(z), activeYPoints.get(z), 1);
								for (int a = 0; a < surroundingTiles[0].length; a++)
								{
									if (surroundingTiles[0][a] != -1)
									{
										if (baseGeographicMap[surroundingTiles[0][a]][surroundingTiles[1][a]] == baseGeography.shallowOcean)
										{
											boolean passes = true;
											for (int l = 0; l < usedXPoints.size(); l++)
											{
												if (usedXPoints.get(l) == surroundingTiles[0][a] && usedYPoints.get(l) == surroundingTiles[1][a])
												{
													passes = false;
												}
											}
											if (passes)
											{
												subActiveXPoints.add(surroundingTiles[0][a]);
												subActiveYPoints.add(surroundingTiles[1][a]);
											}
										}
										if (baseGeographicMap[surroundingTiles[0][a]][surroundingTiles[1][a]] == baseGeography.ocean)
										{
											return true;
										}
									}
								}
							}
							activeXPoints.clear();
							activeYPoints.clear();
							activeXPoints.addAll(subActiveXPoints);
							activeYPoints.addAll(subActiveYPoints);
						}
					}
				}
			}
		}
		return false;
	}
	private boolean tileIsWithinRadius(int column, int row, int radius, int newColumn, int newRow)
	{
		int[][] tiles = getAllTilesWithin(column, row, radius);
		for (int i = 0; i < tiles[0].length; i++)
		{
			if (tiles[0][i] == newColumn && tiles[1][i] == newRow)
			{
				return true;
			}
		}
		return false;
	}
	private boolean adjustSimilarRiverVertices(int column, int row, int vertice, int riverId)
	{
		Boolean endRiver = false;
		int similarColumn1 = parseHexagonId(column, row, vertice)[1];
		int similarRow1 = parseHexagonId(column, row, vertice)[0];
		int similarColumn2 = parseHexagonId(column, row, (vertice + 1) % 6)[1];
		int similarRow2 = parseHexagonId(column, row, (vertice + 1) % 6)[0];
		riverTracker[similarColumn1][similarRow1].verticesRiverActive[(vertice + 2) % 6] = true;
		riverTracker[similarColumn2][similarRow2].verticesRiverActive[(vertice + 4) % 6] = true;
		riverTracker[column][row].verticesRiverActive[vertice] = true;
		if (riverTracker[column][row].riverID[vertice][0] == -1)
		{
			riverTracker[column][row].riverID[vertice][0] = riverId;
		}
		else
		{
			riverTracker[column][row].riverID[vertice][1] = riverId;
			endRiver = true;
		}
		if (riverTracker[similarColumn1][similarRow1].riverID[(vertice + 2) % 6][0] == -1)
		{
			riverTracker[similarColumn1][similarRow1].riverID[(vertice + 2) % 6][0] = riverId;
		}
		else
		{
			riverTracker[similarColumn1][similarRow1].riverID[(vertice + 2) % 6][1] = riverId;
			endRiver = true;
		}
		if (riverTracker[similarColumn2][similarRow2].riverID[(vertice + 4) % 6][0] == -1)
		{
			riverTracker[similarColumn2][similarRow2].riverID[(vertice + 4) % 6][0] = riverId;
		}
		else
		{
			riverTracker[similarColumn2][similarRow2].riverID[(vertice + 4) % 6][1] = riverId;
			endRiver = true;
		}
		if (baseGeographicMap[column][row] == baseGeography.shallowOcean || baseGeographicMap[similarColumn1][similarRow1] == baseGeography.shallowOcean || baseGeographicMap[similarColumn2][similarRow2] == baseGeography.shallowOcean || baseGeographicMap[column][row] == baseGeography.glacier || baseGeographicMap[similarColumn1][similarRow1] == baseGeography.glacier || baseGeographicMap[similarColumn2][similarRow2] == baseGeography.glacier)
		{ endRiver = true; }
		Random rand = new Random();
		if ((baseGeographicMap[column][row] == baseGeography.mountain || baseGeographicMap[similarColumn1][similarRow1] == baseGeography.mountain || baseGeographicMap[similarColumn2][similarRow2] == baseGeography.mountain) && rand.nextBoolean())
		{
			endRiver = true;
		}
		return endRiver;
	}
	private int[] parseHexagonId(int column, int row, int iD)
	{
		if (iD == 0)
		{
			return getTopLeftTile(column, row);
		}
		else if (iD == 1)
		{
			return getLeftTile(column, row);
		}
		else if (iD == 2)
		{
			return getBottomLeftTile(column, row);
		}
		else if (iD == 3)
		{
			return getBottomRightTile(column, row);		
		}
		else if (iD == 4)
		{
			return getRightTile(column, row);
		}
		else if (iD == 5)
		{
			return getTopRightTile(column, row);
		}
		else
		{
			return null;
		}
		
	}
	private void changeAllTilesWithinToShallowOcean(int column, int row, int radius)
	{
		int[][] tiles = getAllTilesWithin(column, row, radius);
		for (int i = 0; i < tiles[0].length; i++)
		{
			if (tiles[1][i] >= 0 && tiles[1][i] < Conquer.gameScreenSizeY)
			{
				if (baseGeographicMap[tiles[0][i]][tiles[1][i]] == baseGeography.ocean)
				{
					baseGeographicMap[tiles[0][i]][tiles[1][i]] = baseGeography.shallowOcean;
				}
			}
		}
	}
	public static int[][] getAllTilesWithin(int column, int row, int radius)// X Values are held in tiles[0] while Y values are held in tiles[1].
	{
		int[][] tiles = new int[2][radius * (3 * radius + 3)];
		for (int i = 0; i < tiles[0].length; i++)
		{
			tiles[0][i] = -1;
			tiles[1][i] = -1;
		}
		for (int i = 1; i <= radius; i++)
		{
			int selectedRow = row - i;
			int selectedColumn = column - i;
			int x = - 1;
			while (selectedColumn != column + i || selectedRow != row - i)
			{
				x++;
				selectedColumn += 2;
				int assignedX = selectedColumn;
				int assignedY = selectedRow;
				if (selectedColumn > Conquer.gameScreenSizeX - 1)
				{
					assignedX -= Conquer.gameScreenSizeX;
				}
				if (selectedColumn < 0)
				{
					assignedX = Conquer.gameScreenSizeX + selectedColumn;
				}
				if (assignedY > -1 && assignedY < Conquer.gameScreenSizeY)
				{
					tiles[0][(i - 1) * ((i - 1) * 3 + 3) + x] = assignedX;
					tiles[1][(i - 1) * ((i - 1) * 3 + 3) + x] = assignedY;
				}
			}
			while (selectedColumn != column + (2 * i) || selectedRow != row)
			{
				x++;
				selectedColumn += 1;
				selectedRow += 1;
				int assignedX = selectedColumn;
				int assignedY = selectedRow;
				if (selectedColumn > Conquer.gameScreenSizeX - 1)
				{
					assignedX -= Conquer.gameScreenSizeX;
				}
				if (selectedColumn < 0)
				{
					assignedX = Conquer.gameScreenSizeX + selectedColumn;
				}
				if (assignedY > -1 && assignedY < Conquer.gameScreenSizeY)
				{
					tiles[0][(i - 1) * ((i - 1) * 3 + 3) + x] = assignedX;
					tiles[1][(i - 1) * ((i - 1) * 3 + 3) + x] = assignedY;
				}
			}
			while (selectedColumn != column + i || selectedRow != row + i)
			{
				x++;
				selectedColumn -= 1;
				selectedRow += 1;
				int assignedX = selectedColumn;
				int assignedY = selectedRow;
				if (selectedColumn > Conquer.gameScreenSizeX - 1)
				{
					assignedX -= Conquer.gameScreenSizeX;
				}
				if (selectedColumn < 0)
				{
					assignedX = Conquer.gameScreenSizeX + selectedColumn;
				}
				if (assignedY > -1 && assignedY < Conquer.gameScreenSizeY)
				{
					tiles[0][(i - 1) * ((i - 1) * 3 + 3) + x] = assignedX;
					tiles[1][(i - 1) * ((i - 1) * 3 + 3) + x] = assignedY;
				}
			}
			while (selectedColumn != column - i || selectedRow != row + i)
			{
				x++;
				selectedColumn -= 2;
				int assignedX = selectedColumn;
				int assignedY = selectedRow;
				if (selectedColumn > Conquer.gameScreenSizeX - 1)
				{
					assignedX -= Conquer.gameScreenSizeX;
				}
				if (selectedColumn < 0)
				{
					assignedX = Conquer.gameScreenSizeX + selectedColumn;
				}
				if (assignedY > -1 && assignedY < Conquer.gameScreenSizeY)
				{
					tiles[0][(i - 1) * ((i - 1) * 3 + 3) + x] = assignedX;
					tiles[1][(i - 1) * ((i - 1) * 3 + 3) + x] = assignedY;
				}
			}
			while (selectedColumn != column - (2 * i) || selectedRow != row)
			{
				x++;
				selectedRow -= 1;
				selectedColumn -= 1;
				int assignedX = selectedColumn;
				int assignedY = selectedRow;
				if (selectedColumn > Conquer.gameScreenSizeX - 1)
				{
					assignedX -= Conquer.gameScreenSizeX;
				}
				if (selectedColumn < 0)
				{
					assignedX = Conquer.gameScreenSizeX + selectedColumn;
				}
				if (assignedY > -1 && assignedY < Conquer.gameScreenSizeY)
				{
					tiles[0][(i - 1) * ((i - 1) * 3 + 3) + x] = assignedX;
					tiles[1][(i - 1) * ((i - 1) * 3 + 3) + x] = assignedY;
				}
			}
			while (selectedColumn != column - i || selectedRow != row - i)
			{
				x++;
				selectedRow -= 1;
				selectedColumn += 1;
				int assignedX = selectedColumn;
				int assignedY = selectedRow;
				if (selectedColumn > Conquer.gameScreenSizeX - 1)
				{
					assignedX -= Conquer.gameScreenSizeX;
				}
				if (selectedColumn < 0)
				{
					assignedX = Conquer.gameScreenSizeX + selectedColumn;
				}
				if (assignedY > -1 && assignedY < Conquer.gameScreenSizeY)
				{
					tiles[0][(i - 1) * ((i - 1) * 3 + 3) + x] = assignedX;
					tiles[1][(i - 1) * ((i - 1) * 3 + 3) + x] = assignedY;
				}
			}
		}
		return tiles;
	}
	private void removeAllOccurrences(int columns, int rows, List<Integer> adjacentColumns, List<Integer> adjacentRows)
	{
		for (int i = adjacentColumns.size() - 1; i >= 0; i--)
		{
			if (adjacentColumns.get(i) == columns && adjacentRows.get(i) == rows)
			{
				adjacentColumns.remove(i);
				adjacentRows.remove(i);
			}
		}
	}
	private boolean testForValues(List<Integer> array1, List<Integer> array2, int value1, int value2)
	{
		boolean returnValue = false;
		if (baseGeographicMap[value2][value1] != baseGeography.ocean && baseGeographicMap[value2][value1] != baseGeography.shallowOcean)
		{
			returnValue = true;
		}
		return returnValue;
	}
	public int[] getTopRightTile(int columns, int rows)
	{
		int[] rowsAndColumns = new int[2];
		rowsAndColumns[0] = rows - 1;
		rowsAndColumns[1] = columns + 1;
		if (rowsAndColumns[1] < 0)
		{
			rowsAndColumns[1] = Conquer.gameScreenSizeX - rowsAndColumns[1];
		}
		if (rowsAndColumns[1] > Conquer.gameScreenSizeX - 1)
		{
			rowsAndColumns[1] = rowsAndColumns[1] - Conquer.gameScreenSizeX;
		}
		return rowsAndColumns;
	}
	public int[] getTopLeftTile(int columns, int rows)
	{
		int[] rowsAndColumns = new int[2];
		rowsAndColumns[0] = rows - 1;
		rowsAndColumns[1] = columns - 1;
		if (rowsAndColumns[1] < 0)
		{
			rowsAndColumns[1] = Conquer.gameScreenSizeX + rowsAndColumns[1];
		}
		if (rowsAndColumns[1] > Conquer.gameScreenSizeX - 1)
		{
			rowsAndColumns[1] = rowsAndColumns[1] - Conquer.gameScreenSizeX;
		}
		return rowsAndColumns;
	}
	public int[] getRightTile(int columns, int rows)
	{
		int[] rowsAndColumns = new int[2];
		rowsAndColumns[0] = rows;
		rowsAndColumns[1] = columns + 2;
		if (rowsAndColumns[1] < 0)
		{
			rowsAndColumns[1] = Conquer.gameScreenSizeX + rowsAndColumns[1];
		}
		if (rowsAndColumns[1] > Conquer.gameScreenSizeX - 1)
		{
			rowsAndColumns[1] = rowsAndColumns[1] - Conquer.gameScreenSizeX;
		}
		return rowsAndColumns;
	}
	public int[] getLeftTile(int columns, int rows)
	{
		int[] rowsAndColumns = new int[2];
		rowsAndColumns[0] = rows;
		rowsAndColumns[1] = columns - 2;
		if (rowsAndColumns[1] < 0)
		{
			rowsAndColumns[1] = Conquer.gameScreenSizeX + rowsAndColumns[1];
		}
		if (rowsAndColumns[1] > Conquer.gameScreenSizeX - 1)
		{
			rowsAndColumns[1] = rowsAndColumns[1] - Conquer.gameScreenSizeX;
		}
		return rowsAndColumns;
	}
	public int[] getBottomLeftTile(int columns, int rows)
	{
		int[] rowsAndColumns = new int[2];
		rowsAndColumns[0] = rows + 1;
		rowsAndColumns[1] = columns - 1;
		if (rowsAndColumns[1] < 0)
		{
			rowsAndColumns[1] = Conquer.gameScreenSizeX + rowsAndColumns[1];
		}
		if (rowsAndColumns[1] > Conquer.gameScreenSizeX - 1)
		{
			rowsAndColumns[1] = rowsAndColumns[1] - Conquer.gameScreenSizeX;
		}
		return rowsAndColumns;
	}
	public int[] getBottomRightTile(int columns, int rows)
	{
		int[] rowsAndColumns = new int[2];
		rowsAndColumns[0] = rows + 1;
		rowsAndColumns[1] = columns + 1;
		if (rowsAndColumns[1] < 0)
		{
			rowsAndColumns[1] = Conquer.gameScreenSizeX + rowsAndColumns[1];
		}
		if (rowsAndColumns[1] > Conquer.gameScreenSizeX - 1)
		{
			rowsAndColumns[1] = rowsAndColumns[1] - Conquer.gameScreenSizeX;
		}
		return rowsAndColumns;
	}
	public Image clipImages(Image img)
	{
		double penStartingPointX = (xDistanceIncrementer / 2);
		double penStartingPointY = 0;
		int[] hexPointsX = new int[6];
		double drawAngle = -60 * Math.PI / 180;
		int[] hexPointsY = new int[6];
		for (int i = 0; i < 6; i++)
		{
			double newY = penStartingPointY + (Math.cos(drawAngle) * sideLength);
			double newX = penStartingPointX + (Math.sin(drawAngle) * sideLength);
			hexPointsX[i] = (int) newX;
			hexPointsY[i] = (int) newY;
			penStartingPointX = newX;
			penStartingPointY = newY;
			drawAngle += (60 * Math.PI / 180);
		}
		BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);

		Graphics g = image.getGraphics();

		Path2D path = new Path2D.Double();
		path.moveTo(hexPointsX[5], hexPointsY[5]);
		path.lineTo(hexPointsX[0], hexPointsY[0]);
		path.lineTo(hexPointsX[1], hexPointsY[1]);
		path.lineTo(hexPointsX[2], hexPointsY[2]);
		path.lineTo(hexPointsX[3], hexPointsY[3]);
		path.lineTo(hexPointsX[4], hexPointsY[4]);
		path.closePath();
		g.setClip(path);
		g.drawImage(img, 0, 0, null);
		img.flush();
		img = null;
		img = image;
		g.dispose();
		image.flush();
		image = null;
		return img;
		
	}
	public Image magnifyImage(Image img, double magnificationValue)
	{
		return img.getScaledInstance((int) (img.getWidth(null) * magnificationValue), (int) (img.getHeight(null) * magnificationValue), Image.SCALE_SMOOTH);
	}
	public void developeMiniMap()
	{
		Graphics2D g = miniMap.createGraphics();
		double width = 0;
		double height = 0;
		int startingColumn = farthestDiscoveredWest - 8;
		int baseRow = farthestDiscoveredNorth - 4;
		if ((farthestDiscoveredEast - farthestDiscoveredWest + 1) / (double)(farthestDiscoveredSouth - farthestDiscoveredNorth + 1) > 4)
		{
			width = (farthestDiscoveredEast - farthestDiscoveredWest + 1);
			height = (farthestDiscoveredEast - farthestDiscoveredWest + 1) / 4.0;
			if (startingColumn % 2 != baseRow % 2)
			{
				baseRow-= 1;
			}
		}
		else
		{
			height = (farthestDiscoveredSouth - farthestDiscoveredNorth + 1);
			width = (farthestDiscoveredSouth - farthestDiscoveredNorth + 1) * 4.0;
			if (startingColumn % 2 != baseRow % 2)
			{
				startingColumn-= 1;
			}
		}
		Boolean plusorminus = true; // Determines whether or not to add or subtract the pen starting point.
		double xStarter = 0; // Starting Point for the pen along the x-axis.
		
		double miniMapSideLength = (miniMap.getHeight() * 2) / (3 * height + 1);
		double newXDistanceIncrementer = miniMapSideLength * Math.sqrt(3);
		g.setStroke(new BasicStroke((float) 1));
		int baseColumn = startingColumn;
		if (baseRow < 0)
		{
			baseRow = Conquer.gameScreenSizeY + baseRow;
		}
		if (baseRow >= Conquer.gameScreenSizeY)
		{
			baseRow = baseRow - Conquer.gameScreenSizeY;
		}
		
		for (double y = - (6 * miniMapSideLength); y <= miniMap.getHeight(); y += (3 * miniMapSideLength / 2))//Conquer.gameScreenSizeY * 3 * miniMapSideLength / 2
		{
			if (plusorminus == true)
			{
				baseColumn = startingColumn;
				if (baseColumn < 0)
				{
					baseColumn = Conquer.gameScreenSizeX + baseColumn;
				}
				if (baseColumn >= Conquer.gameScreenSizeX)
				{
					baseColumn = baseColumn - Conquer.gameScreenSizeX;
				}
				xStarter += newXDistanceIncrementer / 2;
				plusorminus = false;
			}
			else
			{
				baseColumn = startingColumn - 1;
				if (baseColumn < 0)
				{
					baseColumn = Conquer.gameScreenSizeX + baseColumn;
				}
				if (baseColumn >= Conquer.gameScreenSizeX)
				{
					baseColumn = baseColumn - Conquer.gameScreenSizeX;
				}
				xStarter -= newXDistanceIncrementer / 2;
				plusorminus = true;
			}
			// The pen starting point fluctuates in order to create a hexagon pattern.
			
			 // Establishes base values for the what row and column each tile is in for later.
 		for (double x = xStarter - (4 * newXDistanceIncrementer);x <= miniMap.getWidth() + (newXDistanceIncrementer * 2); x += newXDistanceIncrementer) //x <= Conquer.gameScreenSizeX * newXDistanceIncrementer / 2
		{
			double penStartingPointX = x;
			double penStartingPointY = y;
			int[] hexPointsX = new int[6]; // Holds the x points of the hexagon.
			int[] hexPointsY = new int[6]; // Holds the y points of the hexagon.
			double drawAngle = -60 * Math.PI / 180; // begins the angle at -60 degrees and converts it to radians.
			for (int i = 0; i < 6; i++)
			{
				double newY = penStartingPointY + (Math.cos(drawAngle) * miniMapSideLength);
				double newX = penStartingPointX + (Math.sin(drawAngle) * miniMapSideLength); 
				// Gets the new x and y coordinates for a point at a given angle and side length away from a given x and y coordinate.
				hexPointsX[i] = (int) newX;
				hexPointsY[i] = (int) newY;
				penStartingPointX = newX;
				penStartingPointY = newY;
				drawAngle += (60 * Math.PI / 180);
				// Adds points to a hexagon.
			}
			baseGeography selectedTileBase = baseGeographicMap[baseColumn][baseRow];
			upperGeography selectedTileUpper = upperGeographicMap[baseColumn][baseRow];
			resourceGeography selectedTileResource = resourceGeographicMap[baseColumn][baseRow]; // Gets the tile types at the given coordinates.
			if (selectedTileBase == baseGeography.plains)
			{
				g.setColor(new Color(67, 209, 35));
				if (selectedTileUpper == upperGeography.forest)
				{
					g.setColor(new Color(0, 170, 4));
				}
				if (selectedTileUpper == upperGeography.jungle)
				{
					g.setColor(new Color(0, 98, 0));
				}
			}
			else if (selectedTileBase == baseGeography.desert)
			{
				g.setColor(new Color(255, 255, 75));
			}
			else if (selectedTileBase == baseGeography.ocean)
			{
				g.setColor(new Color(0, 0, 170));
			}
			else if (selectedTileBase == baseGeography.shallowOcean)
			{
				g.setColor(new Color(30, 97, 177));
			}
			else if (selectedTileBase == baseGeography.tundra)
			{
				g.setColor(new Color(151, 154, 160));
				if (selectedTileUpper == upperGeography.forest)
				{
					g.setColor(new Color(0, 170, 4));
				}
				if (selectedTileUpper == upperGeography.snow || selectedTileUpper == upperGeography.snowHills)
				{
					g.setColor(new Color(218, 225, 209));
				}
			}
			else if (selectedTileBase == baseGeography.oasis)
			{
				g.setColor(new Color(255, 255, 75));
			}
			else if (selectedTileBase == baseGeography.glacier)
			{
				g.setColor(new Color(119, 203, 255));
			}
			else if (selectedTileBase == baseGeography.mountain)
			{
				g.setColor(new Color(109, 114, 110));
			}
			if (discoveredTiles[baseColumn][baseRow] == false)
			{
				g.setColor(new Color(200, 200, 200));
			}
			Polygon hex = new Polygon();
			hex.addPoint(hexPointsX[0], hexPointsY[0]);
			hex.addPoint(hexPointsX[1], hexPointsY[1]);
			hex.addPoint(hexPointsX[2], hexPointsY[2]);
			hex.addPoint(hexPointsX[3], hexPointsY[3]);
			hex.addPoint(hexPointsX[4], hexPointsY[4]);
			hex.addPoint(hexPointsX[5], hexPointsY[5]);
			g.fillPolygon(hex);
			g.drawPolygon(hex);
			g.setColor(new Color(150, 150, 150));
			//g.drawPolygon(hex);
			// Draws Hexagon.
			
			if (discoveredTiles[baseColumn][baseRow] == true)
			{
				for (int i = 0; i < 6; i++)
				{
					int adjustedColumn = baseColumn;
					if (adjustedColumn < 0) { adjustedColumn += Conquer.gameScreenSizeX; }
					if (riverTracker[adjustedColumn][baseRow].verticesRiverActive[i] == true && 
							riverTracker[adjustedColumn][baseRow].verticesRiverActive[(i + 1) % 6] == true && 
							(riverTracker[adjustedColumn][baseRow].riverID[i][0] == riverTracker[adjustedColumn][baseRow].riverID[(i + 1) % 6][0] || 
							riverTracker[adjustedColumn][baseRow].riverID[i][1] == riverTracker[adjustedColumn][baseRow].riverID[(i + 1) % 6][1] || 
							riverTracker[adjustedColumn][baseRow].riverID[i][0] == riverTracker[adjustedColumn][baseRow].riverID[(i + 1) % 6][1] || 
							riverTracker[adjustedColumn][baseRow].riverID[i][1] == riverTracker[adjustedColumn][baseRow].riverID[(i + 1) % 6][0]))                              
					{
						g.setColor(new Color(102, 144, 153));
						if (miniMapSideLength > 40)
						{
							((Graphics2D) g).setStroke(new BasicStroke(5));
						}
						else if (miniMapSideLength > 30)
						{
							((Graphics2D) g).setStroke(new BasicStroke(4));
						}
						else if (miniMapSideLength > 15)
						{
							((Graphics2D) g).setStroke(new BasicStroke(3));
						}
						else if (miniMapSideLength > 10)
						{
							((Graphics2D) g).setStroke(new BasicStroke(2));
						}
						
						g.drawLine(hexPointsX[i], hexPointsY[i], hexPointsX[(i + 1) % 6], hexPointsY[(i + 1) % 6]); 
						((Graphics2D) g).setStroke(new BasicStroke(1));
					}
				}
			}
			baseColumn += 2;
			baseColumn %= Conquer.gameScreenSizeX;
			}
 		baseRow++;
 		if (baseRow < 0)
		{
			baseRow = Conquer.gameScreenSizeY + baseRow;
		}
 		if (baseRow >= Conquer.gameScreenSizeY)
 		{
 			baseRow = baseRow - Conquer.gameScreenSizeY;
 		}
		}
		g.dispose();
		g = null;
		System.out.println("Mini Map Side Length:" + miniMapSideLength);
	}
	public void developeMiniMapOffThread()
	{
		new Thread()
		{
			public void run()
			{
				developeMiniMap();
				repaint();
			}
		}.start();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); // Runs the super jPanel painComponent method.
		xOffset %= (xDistanceIncrementer / 2 * Conquer.gameScreenSizeX); // Clips xOffset and yOffset to reasonable amount (garbage disposal). 
		yOffset %= (sideLength * 3 / 2 * Conquer.gameScreenSizeY);
		if (minimizeMiniMap == 2 && (lastWidth != this.getWidth() || lastHeight != this.getHeight()))
		{
			if ((this.getWidth() / Math.sqrt(3)) / (this.getHeight() * 2 / 3) > 2)
			{
				miniMap = new BufferedImage( (int) ((int) this.getHeight() * 4 * Math.sqrt(3) / 3), this.getHeight(), BufferedImage.TYPE_INT_RGB);
			}
			else
			{
				miniMap = new BufferedImage( this.getWidth(), (int) (this.getWidth() * 3 / (4 * Math.sqrt(3))), BufferedImage.TYPE_INT_RGB);
			}
			developeMiniMap();
		}
		
		g.drawImage(mainMap, (int)( - mainMapImageOffsetX + xOffset), (int)( - mainMapImageOffsetY + yOffset), null);
		g.drawImage(fogOfWarImage, (int)( - mainMapImageOffsetX + xOffset), (int)( - mainMapImageOffsetY + yOffset), null);
		if (movementMap != null)
		{
			g.drawImage(movementMap, (int)( - mainMapImageOffsetX + xOffset), (int)( - mainMapImageOffsetY + yOffset), null);
		}
		g.drawImage(unitMap, (int)( - mainMapImageOffsetX + xOffset), (int)( - mainMapImageOffsetY + yOffset), null);
		g.drawImage(cityTagsImage, (int)( - mainMapImageOffsetX + xOffset), (int)( - mainMapImageOffsetY + yOffset), null);
		if (movingUnitImage != null)
		{
			g.drawImage(movingUnitImage, (int)( - mainMapImageOffsetX + xOffset), (int)( - mainMapImageOffsetY + yOffset), null);
		}
		
		if (menuButton != null)
		{
			menuButton.setLocation(new Point(this.getWidth() - 40, 0));
		}
		if (menuOpen)
		{
			menu.setLocation(new Point(this.getWidth() / 2 - 300, 20));
		}
		final int miniMapSizeIncreaserX = 15;
    	final int miniMapSizeIncreaserY = 20;
		
    	if (menuOpen == false)
    	{
    		if (minimizeMiniMap == 1)
    		{
    			miniMapMinimizeButtonCenterX = this.getWidth() - miniMap.getWidth() - miniMapSizeIncreaserX + 20;
    			miniMapMinimizeButtonCenterY = this.getHeight() - miniMap.getHeight() - miniMapSizeIncreaserY;
    			miniMapMaximizeButtonCenterX = this.getWidth() - miniMap.getWidth() - miniMapSizeIncreaserX + 40;
    			miniMapMaximizeButtonCenterY = this.getHeight() - miniMap.getHeight() - miniMapSizeIncreaserY;
    			miniMapContainer = new Rectangle(this.getWidth() - miniMap.getWidth() - miniMapSizeIncreaserX, this.getHeight() - miniMap.getHeight() - miniMapSizeIncreaserY, miniMap.getWidth() + miniMapSizeIncreaserX, miniMap.getHeight() + miniMapSizeIncreaserY);
    			g.setColor(new Color(220, 205, 164));
    			g.fillRect(this.getWidth() - miniMap.getWidth() - miniMapSizeIncreaserX, this.getHeight() - miniMap.getHeight() - miniMapSizeIncreaserY, miniMap.getWidth() + miniMapSizeIncreaserX, miniMap.getHeight() + miniMapSizeIncreaserY);
    			((Graphics2D) g).setStroke(new BasicStroke(3));
    			g.setColor(new Color(181, 184, 180));
    			g.drawRect(this.getWidth() - miniMap.getWidth() - miniMapSizeIncreaserX, this.getHeight() - miniMap.getHeight() - miniMapSizeIncreaserY, miniMap.getWidth() + miniMapSizeIncreaserX, miniMap.getHeight() + miniMapSizeIncreaserY);
    			((Graphics2D) g).setStroke(new BasicStroke(1));
    			g.setColor(new Color(228, 231, 223));
    			g.fillOval(miniMapMinimizeButtonCenterX, miniMapMinimizeButtonCenterY, miniMapMinimizeButtonRadius, miniMapMinimizeButtonRadius);
    			g.setColor(new Color(181, 184, 180));
    			g.drawOval(miniMapMinimizeButtonCenterX, miniMapMinimizeButtonCenterY, miniMapMinimizeButtonRadius, miniMapMinimizeButtonRadius);
    			g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
    			g.drawString("-", miniMapMinimizeButtonCenterX + (miniMapMinimizeButtonRadius / 2) - 4, miniMapMinimizeButtonCenterY + (miniMapMinimizeButtonRadius / 2) + 9);
    			
    			g.setColor(new Color(228, 231, 223));
    			g.fillOval(miniMapMaximizeButtonCenterX, miniMapMaximizeButtonCenterY, miniMapMaximizeButtonRadius, miniMapMaximizeButtonRadius);
    			g.setColor(new Color(181, 184, 180));
    			g.drawOval(miniMapMaximizeButtonCenterX, miniMapMaximizeButtonCenterY, miniMapMaximizeButtonRadius, miniMapMaximizeButtonRadius);
    			g.setFont(new Font("TimesRoman", Font.BOLD, 23)); 
    			g.drawString("+", miniMapMaximizeButtonCenterX + (miniMapMaximizeButtonRadius / 2) - 5, miniMapMaximizeButtonCenterY + (miniMapMaximizeButtonRadius / 2) + 8);
    			if (turnRotationButton != null)
    			{
    				turnRotationButton.setLocation(new Point(this.getWidth() - miniMap.getWidth() - miniMapSizeIncreaserX, this.getHeight() - miniMap.getHeight() - miniMapSizeIncreaserY - turnRotationButton.getHeight()));
    			}
    			g.drawImage(miniMap, this.getWidth() - miniMap.getWidth(), this.getHeight() - miniMap.getHeight(), null);
    			miniMapBox = new Rectangle(this.getWidth() - miniMap.getWidth(), this.getHeight() - miniMap.getHeight(), miniMap.getWidth(), miniMap.getHeight());
    			drawLocationDot(g);
    		}	
    		else if (minimizeMiniMap == 0)
    		{
    			g.setColor(new Color(220, 205, 164));
    			miniMapContainer.x = this.getWidth() - miniMap.getWidth() - miniMapSizeIncreaserX;
    			miniMapContainer.y = this.getHeight() - miniMapSizeIncreaserY;
    			g.fillRect(miniMapContainer.x, miniMapContainer.y, (int) miniMapContainer.getWidth(), (int) miniMapContainer.getHeight());
    			((Graphics2D) g).setStroke(new BasicStroke(3));
    			g.setColor(new Color(181, 184, 180));
    			g.drawRect(miniMapContainer.x, miniMapContainer.y, (int) miniMapContainer.getWidth(), (int) miniMapContainer.getHeight());
    			if (turnRotationButton != null)
    			{
    				turnRotationButton.setLocation(new Point(miniMapContainer.x, miniMapContainer.y - turnRotationButton.getHeight()));
    			}
    			((Graphics2D) g).setStroke(new BasicStroke(1));
    			miniMapMaximizeButtonCenterX = this.getWidth() - miniMap.getWidth() - miniMapSizeIncreaserX + 20;
    			miniMapMaximizeButtonCenterY = this.getHeight() - miniMapSizeIncreaserY;
    			g.setColor(new Color(228, 231, 223));
    			g.fillOval(miniMapMaximizeButtonCenterX, miniMapMaximizeButtonCenterY, miniMapMaximizeButtonRadius, miniMapMaximizeButtonRadius);
    			g.setColor(new Color(181, 184, 180));
    			g.drawOval(miniMapMaximizeButtonCenterX, miniMapMaximizeButtonCenterY, miniMapMaximizeButtonRadius, miniMapMaximizeButtonRadius);
    			g.setFont(new Font("TimesRoman", Font.BOLD, 23)); 
    			g.drawString("+", miniMapMaximizeButtonCenterX + (miniMapMaximizeButtonRadius / 2) - 5, miniMapMaximizeButtonCenterY + (miniMapMaximizeButtonRadius / 2) + 8);
    			miniMapBox = null;
    		}	
    		else
    		{
    			g.setColor(new Color(212, 198, 172));
    			g.fillRect(0, 0, this.getWidth(), this.getHeight());
    			g.drawImage(miniMap, (this.getWidth() - miniMap.getWidth()) / 2, (this.getHeight() - miniMap.getHeight()) / 2, null);
    			miniMapMinimizeButtonCenterX = 10;
    			miniMapMinimizeButtonCenterY = 10;
    			((Graphics2D) g).setStroke(new BasicStroke(1));
    			g.setColor(new Color(228, 231, 223));
    			g.fillOval(miniMapMinimizeButtonCenterX, miniMapMinimizeButtonCenterY, miniMapMinimizeButtonRadius, miniMapMinimizeButtonRadius);
    			g.setColor(new Color(181, 184, 180));
    			g.drawOval(miniMapMinimizeButtonCenterX, miniMapMinimizeButtonCenterY, miniMapMinimizeButtonRadius, miniMapMinimizeButtonRadius);
    			g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
    			g.drawString("-", miniMapMinimizeButtonCenterX + (miniMapMinimizeButtonRadius / 2) - 4, miniMapMinimizeButtonCenterY + (miniMapMinimizeButtonRadius / 2) + 9);
    			miniMapBox = new Rectangle((this.getWidth() - miniMap.getWidth()) / 2, (this.getHeight() - miniMap.getHeight()) / 2, miniMap.getWidth(), miniMap.getHeight());
    			drawLocationDot(g);
			}
    	}
    	if (CityTag.selectedCity != null)
    	{
    		if (CityTag.selectedCity.cityProductionBackground != null)
    		{
    			CityTag.selectedCity.cityProductionBackground.setSize(this.getWidth() + 5, this.getHeight() + 5);
    		}
    	}
    	if (dimBackground != null)
    	{
    		dimBackground.setSize(getWidth() + 5, getHeight() + 5);
    		if (techTree != null)
    		{
    			techTree.setSize(techTree.getWidth() * getHeight() / techTree.getHeight(), getHeight());
    		}
    		if (closeButton != null)
    		{
    			closeButton.setLocation(new Point(getWidth() - 50, 5));
    		}
    	}
    	lastWidth = this.getWidth();
		lastHeight = this.getHeight();
    	Control.paintControls(this, g);
		g.dispose();
		g = null;
	}
	public void refreshTile(int column, int row)
	{
		Graphics g = mainMap.createGraphics();
		Graphics g2 = cityTagsImage.createGraphics();
		Graphics g3 = unitMap.createGraphics();
		Graphics g4 = fogOfWarImage.createGraphics();
		refreshTile(column, row, g, g2, g3, g4, mainMapImageOffsetX, mainMapImageOffsetY, cityTags);
		g.dispose();
		g = null;
		g2.dispose();
		g2 = null;
		g3.dispose();
		g3 = null;
		if (mainMap != mainMapDrawing && mainMapDrawing != null)
		{
			tilesNeededToRefreshX.add(column);
			tilesNeededToRefreshY.add(row);
		}
	}
	private void refreshTile(int column, int row, Graphics g, Graphics g2, Graphics g3, Graphics g4, double mainMapImageOffsetX, double mainMapImageOffsetY, ArrayList<CityTag> newCityTags)
	{
		double newXOffset;
		double newYOffset;
		if (column % 2 == 1)
		{
			newXOffset = (column - 1) / 2 * xDistanceIncrementer;
			newYOffset = row * sideLength * 3 / 2 - sideLength * 3 / 2;
		}
		else
		{
			newXOffset = (column) / 2 * xDistanceIncrementer - xDistanceIncrementer / 2;
			newYOffset = row * sideLength * 3 / 2 - sideLength * 3 / 2;
		}
		double x = newXOffset + mainMapImageOffsetX;
		double y = newYOffset + mainMapImageOffsetY;
		if (x > mainMap.getWidth() + 100)
		{
			if (column % 2 == 1)
			{
				newXOffset = (column - 1 - Conquer.gameScreenSizeX) / 2 * xDistanceIncrementer;
			}
			else
			{
				newXOffset = (column - Conquer.gameScreenSizeX) / 2 * xDistanceIncrementer - xDistanceIncrementer / 2;
			}
			x = newXOffset + mainMapImageOffsetX;
		}
		if (x < -100)
		{
			if (column % 2 == 1)
			{
				newXOffset = (column - 1 + Conquer.gameScreenSizeX) / 2 * xDistanceIncrementer;
			}
			else
			{
				newXOffset = (column + Conquer.gameScreenSizeX) / 2 * xDistanceIncrementer - xDistanceIncrementer / 2;
			}
			x = newXOffset + mainMapImageOffsetX;
		}
		double penStartingPointX = x + xDistanceIncrementer / 2;
		double penStartingPointY = y;
		int[] hexPointsX = new int[6]; // Holds the x points of the hexagon.
		int[] hexPointsY = new int[6]; // Holds the y points of the hexagon.
		double drawAngle = -60 * Math.PI / 180; // begins the angle at -60 degrees and converts it to radians.
		for (int i = 0; i < 6; i++)
		{
			double newY = penStartingPointY + (Math.cos(drawAngle) * sideLength);
			double newX = penStartingPointX + (Math.sin(drawAngle) * sideLength); 
			// Gets the new x and y coordinates for a point at a given angle and side length away from a given x and y coordinate.
			hexPointsX[i] = (int) newX;
			hexPointsY[i] = (int) newY;
			penStartingPointX = newX;
			penStartingPointY = newY;
			drawAngle += (60 * Math.PI / 180);
			// Adds points to a hexagon.
		}
		baseGeography selectedTileBase = baseGeographicMap[column][row];
		upperGeography selectedTileUpper = upperGeographicMap[column][row];
		resourceGeography selectedTileResource = resourceGeographicMap[column][row]; // Gets the tile types at the given coordinates.
		Image img = oceanimg;
		boolean drawCity = false;
		boolean isTerritory = false;
		if (selectedTileBase == baseGeography.ocean)
		{
			img = oceanimg;
		}
		else if (selectedTileBase == baseGeography.shallowOcean)
		{
			img = shallowsimg;
		}
		else if (selectedTileBase == baseGeography.plains)
		{
			img = plainsimg;
			if (selectedTileResource == resourceGeography.cattle)
			{
				img = cattleimg;
			}
			if (selectedTileUpper == upperGeography.hills)
			{
				img = plainsHillimg;
			}
			else if (selectedTileUpper == upperGeography.marsh)
			{
				img = marshimg;
			}
			else if (selectedTileUpper == upperGeography.jungle)
			{
				img = jungleimg;
			}
			else if (selectedTileUpper == upperGeography.forest)
			{
				img = forestimg;
			}
		}
		else if (selectedTileBase == baseGeography.desert)
		{
			img = desertimg;
			if (selectedTileUpper == upperGeography.hills)
			{
				img = desertHillimg;
			}
		}
		else if (selectedTileBase == baseGeography.mountain)
		{
			img = mountainimg;
		}
		else if (selectedTileBase == baseGeography.glacier)
		{
			img = glacierimg;
		}
		else if (selectedTileBase == baseGeography.tundra)
		{
			img = tundraimg;
			if (selectedTileUpper == upperGeography.hills)
			{
				img = tundraHillimg;
			}
			else if (selectedTileUpper == upperGeography.snow)
			{
				img = snowimg;
			}
			else if (selectedTileUpper == upperGeography.snowHills)
			{
				img = snowHillimg;
			}
			else if (selectedTileUpper == upperGeography.forest)
			{
				img = forestimg;
				if (selectedTileResource == resourceGeography.furs)
				{
					img = furTreeimg;
				}
			}
		}
		else if (selectedTileBase == baseGeography.oasis)
		{
			img = oasisimg;
		}
		if (cityMap[column][row] != null)
		{
			drawCity = true;
		}
		if (discoveredTiles[column][row] == false)
		{
			drawCity = false;
			img = cloudimg;
		}
		else
		{
			if (territoryMap[column][row] != null)
			{
				isTerritory = true;
			}
		}
		// Determines what image to use based on tile type.
		
		Polygon hex = new Polygon();
		hex.addPoint(hexPointsX[0], hexPointsY[0]);
		hex.addPoint(hexPointsX[1], hexPointsY[1]);
		hex.addPoint(hexPointsX[2], hexPointsY[2]);
		hex.addPoint(hexPointsX[3], hexPointsY[3]);
		hex.addPoint(hexPointsX[4], hexPointsY[4]);
		hex.addPoint(hexPointsX[5], hexPointsY[5]);
		g.drawImage(img, hexPointsX[0], hexPointsY[5], null); // Draws Tile image.
		if (drawCity)
		{
			g2.setFont(new Font("Rockwell", Font.BOLD, 20));
			int stringWidth = g2.getFontMetrics().stringWidth(cityMap[column][row].name);
			int citizenWidth = g2.getFontMetrics().stringWidth("" + cityMap[column][row].citizens);
			Composite c = ((Graphics2D)g2).getComposite();
			((Graphics2D)g2).setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
			g2.fillRoundRect((int) (hexPointsX[0] + xDistanceIncrementer / 2 - stringWidth / 2 - citizenWidth / 2 - 15), hexPointsY[5] - 10, (int) stringWidth + citizenWidth + 30, 30, 30, 30);
			((Graphics2D)g2).setComposite(c);
			g2.setColor(cityMap[column][row].backgroundColor);
			((Graphics2D)g2).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					(float) ((60 / 100.0))));
			g2.fillRoundRect((int) (hexPointsX[0] + xDistanceIncrementer / 2 - stringWidth / 2 - citizenWidth / 2 - 15), hexPointsY[5] - 10, (int) stringWidth + citizenWidth + 30, 30, 30, 30);
			g2.setColor(cityMap[column][row].textColor);
			g2.drawRoundRect((int) (hexPointsX[0] + xDistanceIncrementer / 2 - stringWidth / 2 - citizenWidth / 2 - 15), hexPointsY[5] - 10, (int) stringWidth + citizenWidth + 30, 30, 30, 30);
			CityTag cityTag = new CityTag(this);
			cityTag.setRoundRect((int) (hexPointsX[0] + xDistanceIncrementer / 2 - stringWidth / 2 - citizenWidth / 2 - 15 - mainMapImageOffsetX), hexPointsY[5] - 10 - mainMapImageOffsetY, (int) stringWidth + citizenWidth + 30, 30, 30, 30);
			cityTag.city = cityMap[column][row];
			newCityTags.add(cityTag);
			g2.drawString("" + cityMap[column][row].citizens, (int) (hexPointsX[0] + xDistanceIncrementer / 2 - stringWidth / 2 - citizenWidth / 2 - 7), hexPointsY[5] + 12);
			((Graphics2D)g2).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					(float) (1)));
			g2.drawString(cityMap[column][row].name, (int) (hexPointsX[0] + xDistanceIncrementer / 2 - stringWidth / 2 + citizenWidth / 2  + 5),  hexPointsY[5] + 12);
			g.drawImage(cityimg, hexPointsX[0] - 10, hexPointsY[5] + 15, null);
		}
		img.flush();
		img = null;
		if (isTerritory)
		{
			int[] center = {(int) (hexPointsX[5]), (hexPointsY[0] + hexPointsY[1]) / 2};
			int baseIndex = 5;
			int previousIndex = 4;
			for (int i = 0; i < 6; i++)
			{
				int[] tile = parseHexagonId(column, row, i);
				if (territoryMap[tile[1]][tile[0]] != territoryMap[column][row])
				{
					int point1X = (int) (((hexPointsX[i] - center[0]) * 8.6 / 10) + center[0]);
					int point1Y = (int) (((hexPointsY[i] - center[1]) * 8.6 / 10) + center[1]);
					int point2X = (int) (((hexPointsX[baseIndex] - center[0]) * 8.6 / 10) + center[0]);
					int point2Y = (int) (((hexPointsY[baseIndex] - center[1]) * 8.6 / 10) + center[1]);
					int[] nextTile = parseHexagonId(column, row, (i + 1) % 6);
					int[] lastTile = parseHexagonId(column, row, baseIndex);
					if (territoryMap[nextTile[1]][nextTile[0]] == territoryMap[column][row])
					{
						double slopeOfTerritoryBorder = (point1Y - point2Y) / (double)(point1X - point2X);
						double slopeOfHexBorder = (hexPointsY[i] - hexPointsY[(i + 1) % 6]) / (double)(hexPointsX[i] - hexPointsX[(i + 1) % 6]);
						double hexPointX = hexPointsX[i];
						double hexPointY = hexPointsY[i];
						if (("" + slopeOfTerritoryBorder).equals("Infinity") || ("" + slopeOfTerritoryBorder).equals("-Infinity"))
						{
							double newX = point1X;
							double newY = slopeOfHexBorder * (newX - hexPointX) + hexPointY;
							point1X = (int) newX;
							point1Y = (int) newY;
						}
						else if (("" + slopeOfHexBorder).equals("Infinity") || ("" + slopeOfHexBorder).equals("-Infinity"))
						{
							double newX = hexPointX;
							double newY = slopeOfTerritoryBorder * (newX - point1X) + point1Y;
							point1X = (int) newX;
							point1Y = (int) newY;
						}
						else
						{
							// y - hexPointY = slopeOfHexBorder * (x - hexPointX)
							//(slopeOfHexBorder * x) - y = - hexPointY + (slpeOfHexBorder * hexPointX)
							double hexSum = - hexPointY + (slopeOfHexBorder * hexPointX);
							double borderSum = - point1Y + (slopeOfTerritoryBorder * point1X);
							double totalSum = borderSum - hexSum;
							double newX = totalSum / (slopeOfTerritoryBorder - slopeOfHexBorder);
							double newY = slopeOfTerritoryBorder * (newX - point1X) + point1Y;
							point1X = (int) newX;
							point1Y = (int) newY;
						}
					}
					if (territoryMap[lastTile[1]][lastTile[0]] == territoryMap[column][row])
					{
						double slopeOfTerritoryBorder = (point1Y - point2Y) / (double)(point1X - point2X);
						double slopeOfHexBorder = (hexPointsY[previousIndex] - hexPointsY[baseIndex]) / (double)(hexPointsX[previousIndex] - hexPointsX[baseIndex]);
						double hexPointX = hexPointsX[previousIndex];
						double hexPointY = hexPointsY[previousIndex];
						if (("" + slopeOfTerritoryBorder).equals("Infinity") || ("" + slopeOfTerritoryBorder).equals("-Infinity"))
						{
							double newX = point2X;
							double newY = slopeOfHexBorder * (newX - hexPointX) + hexPointY;
							point2X = (int) newX;
							point2Y = (int) newY;
						}
						else if (("" + slopeOfHexBorder).equals("Infinity") || ("" + slopeOfHexBorder).equals("-Infinity"))
						{
							double newX = hexPointX;
							double newY = slopeOfTerritoryBorder * (newX - point2X) + point2Y;
							point2X = (int) newX;
							point2Y = (int) newY;
						}
						else
						{
							// y - hexPointY = slopeOfHexBorder * (x - hexPointX)
							//(slopeOfHexBorder * x) - y = - hexPointY + (slpeOfHexBorder * hexPointX)
							double hexSum = - hexPointY + (slopeOfHexBorder * hexPointX);
							double borderSum = - point2Y + (slopeOfTerritoryBorder * point2X);
							double totalSum = borderSum - hexSum;
							double newX = totalSum / (slopeOfTerritoryBorder - slopeOfHexBorder);
							double newY = slopeOfTerritoryBorder * (newX - point2X) + point2Y;
							System.out.println("point1: " + point2X + " point2: " + point2Y);
							point2X = (int) newX;
							point2Y = (int) newY;
							System.out.println("Slope1: " + point2X + " Slope2: " + point2Y);
						}
					}
					g.setColor(territoryMap[column][row].civilizationColor);
					((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
							(float) (1)));
					((Graphics2D) g).setStroke(new BasicStroke(8));
					g.drawLine(point1X, point1Y, point2X, point2Y);
					((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
							(float) (1)));
					((Graphics2D) g).setStroke(new BasicStroke(1));
				}
				baseIndex++;
				previousIndex++;
				previousIndex%= 6;
				baseIndex%= 6;
			}
		}
		g3.setColor(new Color(0,0,0, (float)0));
		Composite c2 = ((Graphics2D)g3).getComposite();
		((Graphics2D)g3).setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		g3.fillRect(hexPointsX[0], hexPointsY[5], (int) xDistanceIncrementer, (int) sideLength * 3 / 2);
		((Graphics2D)g3).setComposite(c2);
		if (discoveredTiles[column][row] && fogOfWar[column][row] > 0)
		{
			if (workerMap[column][row] != null)
			{
				if (warriorMap[column][row] != null)
				{
					workerMap[column][row].paint(g3, hexPointsX[0], hexPointsY[5] + 10);
					if (!Unit.units.contains(workerMap[column][row]))
					{
						Unit.units.add(workerMap[column][row]);
					}
				}
				else
				{
					workerMap[column][row].paint(g3, hexPointsX[0], hexPointsY[5]);
					if (!Unit.units.contains(workerMap[column][row]))
					{
						Unit.units.add(workerMap[column][row]);
					}
				}
			}
			if (warriorMap[column][row] != null)
			{
				warriorMap[column][row].paint(g3, hexPointsX[0], hexPointsY[5]);
				if (!Unit.units.contains(warriorMap[column][row]))
				{
					Unit.units.add(warriorMap[column][row]);
				}
			}
		}
		((Graphics2D) g).setStroke(new BasicStroke(3));
		g.setColor(new Color(150, 150, 150));
		g.drawPolygon(hex);
		((Graphics2D) g).setStroke(new BasicStroke(1));
			for (int i = 0; i < 6; i++)
			{
				int adjustedColumn = column;
				if (adjustedColumn < 0) { adjustedColumn += Conquer.gameScreenSizeX; }
				if (discoveredTiles[adjustedColumn][row] == false)
				{
					break;
				}
				if (riverTracker[adjustedColumn][row].verticesRiverActive[i] == true && 
						riverTracker[adjustedColumn][row].verticesRiverActive[(i + 1) % 6] == true && 
						(riverTracker[adjustedColumn][row].riverID[i][0] == riverTracker[adjustedColumn][row].riverID[(i + 1) % 6][0] || 
						riverTracker[adjustedColumn][row].riverID[i][1] == riverTracker[adjustedColumn][row].riverID[(i + 1) % 6][1] || 
						riverTracker[adjustedColumn][row].riverID[i][0] == riverTracker[adjustedColumn][row].riverID[(i + 1) % 6][1] || 
						riverTracker[adjustedColumn][row].riverID[i][1] == riverTracker[adjustedColumn][row].riverID[(i + 1) % 6][0]))                              
				{
					g.setColor(new Color(102, 144, 153));
					//g.setColor(new Color(75, 75, 160));
					drawThickLine(hexPointsX[i], hexPointsY[i], hexPointsX[(i + 1) % 6], hexPointsY[(i + 1) % 6], 2.5, g); 
				}
			}
			Composite c = ((Graphics2D)g).getComposite();
			((Graphics2D)g4).setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
			g4.fillPolygon(hex);
			((Graphics2D)g4).setComposite(c);
			if (fogOfWar[column][row] == 0 && discoveredTiles[column][row])//aa2
			{
				((Graphics2D) g4).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) (0.3)));
				g4.setColor(new Color(0, 0, 0));
				g4.fillPolygon(hex);
				((Graphics2D) g4).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) (1)));
			}
			g = null;
	}
	public void refreshMovementTile(Point[] tiles)
	{
		BufferedImage movementMapPlaceHolder =  new BufferedImage(mainMap.getWidth(), mainMap.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics g = movementMapPlaceHolder.createGraphics();
		BufferedImage circles =  new BufferedImage(mainMap.getWidth(), mainMap.getHeight(), BufferedImage.TRANSLUCENT);
		Graphics g2 = circles.createGraphics();
		refreshMovementTile(tiles, g, g2, circles, mainMapImageOffsetX, mainMapImageOffsetY);
		g.dispose();
		g2.dispose();
		movementMap = movementMapPlaceHolder;
		if (mainMapDrawing != null)
		{
			movementTilesNeededToRefresh = tiles;
		}
	}
	public void refreshMovementTile(Point[] tiles, Graphics g, Graphics g2, BufferedImage circles, double mainMapImageOffsetX, double mainMapImageOffsetY)
	{
		for (int i = 0; i < tiles.length; i++)
		{
			double newXOffset;
			double newYOffset;
			if (tiles[i].x % 2 == 1)
			{
				newXOffset = (tiles[i].x - 1) / 2 * xDistanceIncrementer;
				newYOffset = tiles[i].y * sideLength * 3 / 2 - sideLength * 3 / 2;
			}
			else
			{
				newXOffset = (tiles[i].x) / 2 * xDistanceIncrementer - xDistanceIncrementer / 2;
				newYOffset = tiles[i].y * sideLength * 3 / 2 - sideLength * 3 / 2;
			}
			double x = newXOffset + mainMapImageOffsetX;
			double y = newYOffset + mainMapImageOffsetY;
			if (x > mainMap.getWidth() + 100)
			{
				if (tiles[i].x % 2 == 1)
				{
					newXOffset = (tiles[i].x - 1 - Conquer.gameScreenSizeX) / 2 * xDistanceIncrementer;
				}
				else
				{
					newXOffset = (tiles[i].x - Conquer.gameScreenSizeX) / 2 * xDistanceIncrementer - xDistanceIncrementer / 2;
				}
				x = newXOffset + mainMapImageOffsetX;
			}
			if (x < -100)
			{
				if (tiles[i].x % 2 == 1)
				{
					newXOffset = (tiles[i].x - 1 + Conquer.gameScreenSizeX) / 2 * xDistanceIncrementer;
				}
				else
				{
					newXOffset = (tiles[i].x + Conquer.gameScreenSizeX) / 2 * xDistanceIncrementer - xDistanceIncrementer / 2;
				}
				x = newXOffset + mainMapImageOffsetX;
			}
			double penStartingPointX = x + xDistanceIncrementer / 2;
			double penStartingPointY = y;
			int[] hexPointsX = new int[6]; // Holds the x points of the hexagon.
			int[] hexPointsY = new int[6]; // Holds the y points of the hexagon.
			double drawAngle = -60 * Math.PI / 180; // begins the angle at -60 degrees and converts it to radians.
			for (int t = 0; t < 6; t++)
			{
				double newY = penStartingPointY + (Math.cos(drawAngle) * sideLength);
				double newX = penStartingPointX + (Math.sin(drawAngle) * sideLength); 
				// Gets the new x and y coordinates for a point at a given angle and side length away from a given x and y coordinate.
				hexPointsX[t] = (int) newX;
				hexPointsY[t] = (int) newY;
				penStartingPointX = newX;
				penStartingPointY = newY;
				drawAngle += (60 * Math.PI / 180);
				// Adds points to a hexagon.
			}
			Polygon hex = new Polygon();
			hex.addPoint(hexPointsX[0], hexPointsY[0]);
			hex.addPoint(hexPointsX[1], hexPointsY[1]);
			hex.addPoint(hexPointsX[2], hexPointsY[2]);
			hex.addPoint(hexPointsX[3], hexPointsY[3]);
			hex.addPoint(hexPointsX[4], hexPointsY[4]);
			hex.addPoint(hexPointsX[5], hexPointsY[5]);
			((Graphics2D) g).setStroke(new BasicStroke(1));
			g.setColor(new Color(0,0,0, (float)0));
			Composite c = ((Graphics2D)g).getComposite();
			((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
			g.fillPolygon(hex);
			((Graphics2D)g).setComposite(c);
		}
		for (int i = 0; i < tiles.length; i++)
		{
			refreshMovementTile(tiles[i].x, tiles[i].y, g, g2, mainMapImageOffsetX, mainMapImageOffsetY);
		}
		g.drawImage(circles, 0, 0, null);
	}
	public void refreshMovementTile(int column, int row, Graphics g, Graphics g2, double mainMapImageOffsetX, double mainMapImageOffsetY)
	{
		double newXOffset;
		double newYOffset;
		if (column % 2 == 1)
		{
			newXOffset = (column - 1) / 2 * xDistanceIncrementer;
			newYOffset = row * sideLength * 3 / 2 - sideLength * 3 / 2;
		}
		else
		{
			newXOffset = (column) / 2 * xDistanceIncrementer - xDistanceIncrementer / 2;
			newYOffset = row * sideLength * 3 / 2 - sideLength * 3 / 2;
		}
		double x = newXOffset + mainMapImageOffsetX;
		double y = newYOffset + mainMapImageOffsetY;
		if (x > mainMap.getWidth() + 100)
		{
			if (column % 2 == 1)
			{
				newXOffset = (column - 1 - Conquer.gameScreenSizeX) / 2 * xDistanceIncrementer;
			}
			else
			{
				newXOffset = (column - Conquer.gameScreenSizeX) / 2 * xDistanceIncrementer - xDistanceIncrementer / 2;
			}
			x = newXOffset + mainMapImageOffsetX;
		}
		if (x < -100)
		{
			if (column % 2 == 1)
			{
				newXOffset = (column - 1 + Conquer.gameScreenSizeX) / 2 * xDistanceIncrementer;
			}
			else
			{
				newXOffset = (column + Conquer.gameScreenSizeX) / 2 * xDistanceIncrementer - xDistanceIncrementer / 2;
			}
			x = newXOffset + mainMapImageOffsetX;
		}
		double penStartingPointX = x + xDistanceIncrementer / 2;
		double penStartingPointY = y;
		int[] hexPointsX = new int[6]; // Holds the x points of the hexagon.
		int[] hexPointsY = new int[6]; // Holds the y points of the hexagon.
		double drawAngle = -60 * Math.PI / 180; // begins the angle at -60 degrees and converts it to radians.
		for (int i = 0; i < 6; i++)
		{
			double newY = penStartingPointY + (Math.cos(drawAngle) * sideLength);
			double newX = penStartingPointX + (Math.sin(drawAngle) * sideLength); 
			// Gets the new x and y coordinates for a point at a given angle and side length away from a given x and y coordinate.
			hexPointsX[i] = (int) newX;
			hexPointsY[i] = (int) newY;
			penStartingPointX = newX;
			penStartingPointY = newY;
			drawAngle += (60 * Math.PI / 180);
			// Adds points to a hexagon.
		}
		Polygon hex = new Polygon();
		hex.addPoint(hexPointsX[0], hexPointsY[0]);
		hex.addPoint(hexPointsX[1], hexPointsY[1]);
		hex.addPoint(hexPointsX[2], hexPointsY[2]);
		hex.addPoint(hexPointsX[3], hexPointsY[3]);
		hex.addPoint(hexPointsX[4], hexPointsY[4]);
		hex.addPoint(hexPointsX[5], hexPointsY[5]);
		((Graphics2D) g).setStroke(new BasicStroke(1));
		if (movementPlan[column][row] != 0)
		{
			if (movementPlan[column][row] == -1 || movementPlan[column][row] == -2 || movementPlan[column][row] == -3 || movementPlan[column][row] == -5)
			{
				((Graphics2D) g).setStroke(new BasicStroke(5));
				if (movementPlan[column][row] == -1)
				{
					g.setColor(new Color(32, 33, 255));
				}
				else if (movementPlan[column][row] == - 2)
				{
					g.setColor(new Color(219, 170, 36));
				}
				else if (movementPlan[column][row] == - 3)
				{
					g.setColor(new Color(155, 163, 151));
				}
				else
				{
					g.setColor(new Color(255, 0, 0));
				}
				double iX = hexPointsX[5];
				double iY = (hexPointsY[5] + hexPointsY[2]) / 2.0;
				double fX = iX + Math.cos(movementPlanAngles[column][row]) * xDistanceIncrementer;
				double fY = iY - Math.sin(movementPlanAngles[column][row]) * xDistanceIncrementer;
				g.drawLine((int)iX, (int)iY, (int)fX, (int)fY);
				((Graphics2D) g2).setStroke(new BasicStroke(1));
				if (movementPlan[column][row] == -1)
				{
					g2.setColor(new Color(32, 33, 255));
				}
				else if (movementPlan[column][row] == -2)
				{
					g2.setColor(new Color(219, 170, 36));
				}
				else if (movementPlan[column][row] == -3)
				{
					g2.setColor(new Color(155, 163, 151));
				}
				else
				{
					g2.setColor(new Color(255, 0, 0));
				}
				g2.fillOval((int)fX - 10, (int)fY - 10, 20, 20);
				g2.setColor(new Color(255, 255, 255));
				g2.drawOval((int)fX - 10, (int)fY - 10, 20, 20);
			}
			if (movementPlan[column][row] == -4)
			{
				((Graphics2D) g).setStroke(new BasicStroke(6));
				g.setColor(new Color(253, 27, 27));
				((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
			              (float) ((60) / 100.0)));
				g.drawOval(hexPointsX[0], hexPointsY[5] + 10, (int)xDistanceIncrementer, (int)xDistanceIncrementer);
				((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
			              (float) (1)));
			}
			if (movementPlan[column][row] > 0)
			{
				((Graphics2D) g).setStroke(new BasicStroke(5));
				if (movementPlan[column][row] == 1)
				{
					g.setColor(new Color(32, 33, 255));
				}
				else if (movementPlan[column][row] == 2)
				{
					g.setColor(new Color(219, 170, 36));
				}
				else
				{
					g.setColor(new Color(155, 163, 151));
				}
				double iX = hexPointsX[5];
				double iY = (hexPointsY[5] + hexPointsY[2]) / 2.0;
				double fX = iX + Math.cos(movementPlanAngles[column][row]) * xDistanceIncrementer;
				double fY = iY - Math.sin(movementPlanAngles[column][row]) * xDistanceIncrementer;
				g.drawLine((int)iX, (int)iY, (int)fX, (int)fY);
				((Graphics2D) g2).setStroke(new BasicStroke(1));
				if (movementPlan[column][row] == 1)
				{
					g2.setColor(new Color(32, 33, 255));
				}
				else if (movementPlan[column][row] == 2)
				{
					g2.setColor(new Color(219, 170, 36));
				}
				else
				{
					g2.setColor(new Color(155, 163, 151));
				}
				g2.fillOval((int)fX - 15, (int)fY - 15, 30, 30);
				g2.setColor(new Color(255, 255, 255));
				g2.drawOval((int)fX - 15, (int)fY - 15, 30, 30);
				g2.setFont(new Font("TimesRoman", Font.BOLD, 20));
				g2.drawString("" + movementPlan[column][row], (int)fX - g2.getFontMetrics().stringWidth("" + movementPlan[column][row]) / 2, (int)fY + 7);
			}
		}
		g = null;
	}
	public void paintMainMap()
	{
		ArrayList<CityTag> newCityTags = new ArrayList<CityTag>();
		tilesNeededToRefreshX = new ArrayList<Integer>();
		tilesNeededToRefreshY = new ArrayList<Integer>();
		mainMapDrawing = new BufferedImage(this.getWidth() + 2000, this.getHeight() + 2000, BufferedImage.TYPE_INT_RGB);
		unitMapDrawing = new BufferedImage(this.getWidth() + 2000, this.getHeight() + 2000, BufferedImage.TRANSLUCENT);
		movementMapDrawing = new BufferedImage(this.getWidth() + 2000, this.getHeight() + 2000, BufferedImage.TRANSLUCENT);
		cityTagsImageDrawing = new BufferedImage(this.getWidth() + 2000, this.getHeight() + 2000, BufferedImage.TRANSLUCENT);
		BufferedImage movementMapDrawingCircles = new BufferedImage(this.getWidth() + 2000, this.getHeight() + 2000, BufferedImage.TRANSLUCENT);
		BufferedImage fogOfWarDrawing = new BufferedImage(this.getWidth() + 2000, this.getHeight() + 2000, BufferedImage.TRANSLUCENT);
		Graphics g2 = unitMapDrawing.createGraphics();
		Graphics g3 = movementMapDrawing.createGraphics();
		Graphics g4 = movementMapDrawingCircles.createGraphics();
		Graphics g5 = cityTagsImageDrawing.createGraphics();
		Graphics g6 = fogOfWarDrawing.createGraphics();
		double xOffset = this.xOffset + (mainMap.getWidth() - this.getWidth()) / 2;
		double yOffset = this.yOffset + (mainMap.getHeight() - this.getHeight()) / 2;
		Graphics g = mainMapDrawing.createGraphics();
		g.setColor(new Color(150, 150, 150));
		g.drawRect(0, 0, mainMap.getWidth(), mainMap.getHeight());
		ArrayList<Unit> newUnits = new ArrayList<Unit>();
		Boolean plusorminus = true; // Determines whether or not to add or subtract the pen starting point.
		double xStarter = 0; // Starting Point for the pen along the x-axis.
		for (double y = - (6 * sideLength) + (yOffset % (3 * sideLength)); y < mainMap.getHeight(); y += (3 * sideLength / 2))
		{
			if (plusorminus == true)
			{
				xStarter += xDistanceIncrementer / 2;
				plusorminus = false;
			}
			else
			{
				xStarter -= xDistanceIncrementer / 2;
				plusorminus = true;
			}
			// The pen starting point fluctuates in order to create a hexagon pattern.
			
			int baseColumn = getColumns((int)(xStarter - xDistanceIncrementer + (xOffset % xDistanceIncrementer)), (int) (y + sideLength), xOffset, yOffset);
			int baseRow = getRows((int)(xStarter - xDistanceIncrementer + (xOffset % xDistanceIncrementer)), (int) (y + sideLength), xOffset, yOffset); // Establishes base values for the what row and column each tile is in for later.
 		for (double x = xStarter - (2 * xDistanceIncrementer) + (xOffset % xDistanceIncrementer); x < mainMap.getWidth() + xDistanceIncrementer; x += xDistanceIncrementer)
		{
			double penStartingPointX = x;
			double penStartingPointY = y;
			int[] hexPointsX = new int[6]; // Holds the x points of the hexagon.
			int[] hexPointsY = new int[6]; // Holds the y points of the hexagon.
			double drawAngle = -60 * Math.PI / 180; // begins the angle at -60 degrees and converts it to radians.
			for (int i = 0; i < 6; i++)
			{
				double newY = penStartingPointY + (Math.cos(drawAngle) * sideLength);
				double newX = penStartingPointX + (Math.sin(drawAngle) * sideLength); 
				// Gets the new x and y coordinates for a point at a given angle and side length away from a given x and y coordinate.
				hexPointsX[i] = (int) newX;
				hexPointsY[i] = (int) newY;
				penStartingPointX = newX;
				penStartingPointY = newY;
				drawAngle += (60 * Math.PI / 180);
				// Adds points to a hexagon.
			}
			baseGeography selectedTileBase = baseGeographicMap[baseColumn][baseRow];
			upperGeography selectedTileUpper = upperGeographicMap[baseColumn][baseRow];
			resourceGeography selectedTileResource = resourceGeographicMap[baseColumn][baseRow]; // Gets the tile types at the given coordinates.
			improvements selectedTileImprovement = improvementMap[baseColumn][baseRow];
			Image img = oceanimg;
			boolean drawCity = false;
			if (selectedTileBase == baseGeography.ocean)
			{
				img = oceanimg;
			}
			else if (selectedTileBase == baseGeography.shallowOcean)
			{
				img = shallowsimg;
			}
			else if (selectedTileBase == baseGeography.plains)
			{
				img = plainsimg;
				if (selectedTileResource == resourceGeography.cattle)
				{
					img = cattleimg;
					if (selectedTileImprovement == improvements.pasture)
					{
						img = cattlePastureimg;
					}
				}
				if (selectedTileResource == resourceGeography.horses)
				{
					img = horseimg;
					if (selectedTileImprovement == improvements.pasture)
					{
						img = horsePastureimg;
					}
				}
				if (selectedTileUpper == upperGeography.hills)
				{
					img = plainsHillimg;
					if (selectedTileResource == resourceGeography.sheep)
					{
						img = sheepimg;
						if (selectedTileImprovement == improvements.pasture)
						{
							img = sheepPastureimg;
						}
					}
				}
				else if (selectedTileUpper == upperGeography.marsh)
				{
					img = marshimg;
				}
				else if (selectedTileUpper == upperGeography.jungle)
				{
					img = jungleimg;
				}
				else if (selectedTileUpper == upperGeography.forest)
				{
					img = forestimg;
				}
			}
			else if (selectedTileBase == baseGeography.desert)
			{
				img = desertimg;
				if (selectedTileUpper == upperGeography.hills)
				{
					img = desertHillimg;
				}
			}
			else if (selectedTileBase == baseGeography.mountain)
			{
				img = mountainimg;
			}
			else if (selectedTileBase == baseGeography.glacier)
			{
				img = glacierimg;
			}
			else if (selectedTileBase == baseGeography.tundra)
			{
				img = tundraimg;
				if (selectedTileUpper == upperGeography.hills)
				{
					img = tundraHillimg;
				}
				else if (selectedTileUpper == upperGeography.snow)
				{
					img = snowimg;
				}
				else if (selectedTileUpper == upperGeography.snowHills)
				{
					img = snowHillimg;
				}
				else if (selectedTileUpper == upperGeography.forest)
				{
					img = forestimg;
					if (selectedTileResource == resourceGeography.furs)
					{
						img = furTreeimg;
					}
				}
			}
			else if (selectedTileBase == baseGeography.oasis)
			{
				img = oasisimg;
			}
			if (cityMap[baseColumn][baseRow] != null)
			{
				drawCity = true;
			}
			boolean isTerritory = false;
			if (discoveredTiles[baseColumn][baseRow] == false)
			{
				drawCity = false;
				img = cloudimg;
			}
			else
			{
				if (territoryMap[baseColumn][baseRow] != null)
				{
					isTerritory = true;
				}
			}
			// Determines what image to use based on tile type.
			
			if (selectedTileImprovement == improvements.farm)
			{
				img = farmimg;
			}
			else if (selectedTileImprovement == improvements.mine)
			{
				if (selectedTileResource == resourceGeography.aluminium)
				{
					
				}
				else if (selectedTileResource == resourceGeography.coal)
				{
					
				}
				else if (selectedTileResource == resourceGeography.iron)
				{
					
				}
				else if (selectedTileResource == resourceGeography.uranium)
				{
					
				}
				else if (selectedTileResource == resourceGeography.gold)
				{
					
				}
				else if (selectedTileResource == resourceGeography.silver)
				{
					
				}
				else if (selectedTileResource == resourceGeography.silver)
				{
					
				}
				else
				{
					
				}
			}
			Polygon hex = new Polygon();
			hex.addPoint(hexPointsX[0], hexPointsY[0]);
			hex.addPoint(hexPointsX[1], hexPointsY[1]);
			hex.addPoint(hexPointsX[2], hexPointsY[2]);
			hex.addPoint(hexPointsX[3], hexPointsY[3]);
			hex.addPoint(hexPointsX[4], hexPointsY[4]);
			hex.addPoint(hexPointsX[5], hexPointsY[5]);
			g.setColor(new Color(150, 150, 150));
			g.drawImage(img, hexPointsX[4], hexPointsY[5], null); // Draws Tile image.
			if (improvementMap[baseColumn][baseRow] == improvements.tradingPost)
			{
				g.drawImage(tradingPostimg, hexPointsX[4] - 10, hexPointsY[5] + 10, (int) (tradingPostimg.getWidth(null) / 2.1), (int) (tradingPostimg.getHeight(null) / 2.1), null);
			}
			if (drawCity)
			{
				g5.setFont(new Font("Rockwell", Font.BOLD, 20));
				int stringWidth = g5.getFontMetrics().stringWidth(cityMap[baseColumn][baseRow].name);
				int citizenWidth = g5.getFontMetrics().stringWidth("" + cityMap[baseColumn][baseRow].citizens);
				g5.setColor(cityMap[baseColumn][baseRow].backgroundColor);
				((Graphics2D)g5).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) ((60 / 100.0))));
				g5.fillRoundRect((int) (hexPointsX[4] + xDistanceIncrementer / 2 - stringWidth / 2 - citizenWidth / 2 - 15), hexPointsY[5] - 10, (int) stringWidth + citizenWidth + 30, 30, 30, 30);
				g5.setColor(cityMap[baseColumn][baseRow].textColor);
				g5.drawRoundRect((int) (hexPointsX[4] + xDistanceIncrementer / 2 - stringWidth / 2 - citizenWidth / 2 - 15), hexPointsY[5] - 10, (int) stringWidth + citizenWidth + 30, 30, 30, 30);
				CityTag cityTag = new CityTag(this);
				cityTag.setRoundRect((int) (hexPointsX[4] + xDistanceIncrementer / 2 - stringWidth / 2 - citizenWidth / 2 - 15) - xOffset, hexPointsY[5] - 10 - yOffset, (int) stringWidth + citizenWidth + 30, 30, 30, 30);
				cityTag.city = cityMap[baseColumn][baseRow];
				newCityTags.add(cityTag);
				g5.drawString("" + cityMap[baseColumn][baseRow].citizens, (int) (hexPointsX[4] + xDistanceIncrementer / 2 - stringWidth / 2 - citizenWidth / 2 - 7), hexPointsY[5] + 12);
				((Graphics2D)g5).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) (1)));
				g5.drawString(cityMap[baseColumn][baseRow].name, (int) (hexPointsX[4] + xDistanceIncrementer / 2 - stringWidth / 2 + citizenWidth / 2 + 5),  hexPointsY[5] + 12);
				g.drawImage(cityimg, hexPointsX[4] - 10, hexPointsY[5] + 15, null);
			}
			img.flush();
			img = null;
			((Graphics2D) g).setStroke(new BasicStroke(3));
			g.drawPolygon(hex);
			((Graphics2D) g).setStroke(new BasicStroke(1));
			// Draws Hexagon.
				for (int i = 0; i < 6; i++)
				{
					int adjustedColumn = baseColumn - 2;
					if (adjustedColumn < 0) { adjustedColumn += Conquer.gameScreenSizeX; }
					if (discoveredTiles[adjustedColumn][baseRow] == false)
					{
						break;
					}
					if (riverTracker[adjustedColumn][baseRow].verticesRiverActive[i] == true && 
							riverTracker[adjustedColumn][baseRow].verticesRiverActive[(i + 1) % 6] == true && 
							(riverTracker[adjustedColumn][baseRow].riverID[i][0] == riverTracker[adjustedColumn][baseRow].riverID[(i + 1) % 6][0] || 
							riverTracker[adjustedColumn][baseRow].riverID[i][1] == riverTracker[adjustedColumn][baseRow].riverID[(i + 1) % 6][1] || 
							riverTracker[adjustedColumn][baseRow].riverID[i][0] == riverTracker[adjustedColumn][baseRow].riverID[(i + 1) % 6][1] || 
							riverTracker[adjustedColumn][baseRow].riverID[i][1] == riverTracker[adjustedColumn][baseRow].riverID[(i + 1) % 6][0]))                              
					{
						g.setColor(new Color(102, 144, 153));
						drawThickLine(hexPointsX[i], hexPointsY[i], hexPointsX[(i + 1) % 6], hexPointsY[(i + 1) % 6], 2.5, g); 
					}
				}
			if (isTerritory)
			{
				int[] center = {(int) (hexPointsX[5] + xDistanceIncrementer), (hexPointsY[0] + hexPointsY[1]) / 2};
				int baseIndex = 5;
				int previousIndex = 4;
				for (int i = 0; i < 6; i++)
				{
					int[] tile = parseHexagonId(baseColumn, baseRow, i);
					if (territoryMap[tile[1]][tile[0]] != territoryMap[baseColumn][baseRow])
					{
						int point1X = (int) (((hexPointsX[i] + xDistanceIncrementer - center[0]) * 8.6 / 10) + center[0]);
						int point1Y = (int) (((hexPointsY[i] - center[1]) * 8.6 / 10) + center[1]);
						int point2X = (int) (((hexPointsX[baseIndex] + xDistanceIncrementer - center[0]) * 8.6 / 10) + center[0]);
						int point2Y = (int) (((hexPointsY[baseIndex] - center[1]) * 8.6 / 10) + center[1]);
						int[] nextTile = parseHexagonId(baseColumn, baseRow, (i + 1) % 6);
						int[] lastTile = parseHexagonId(baseColumn, baseRow, baseIndex);
						if (territoryMap[nextTile[1]][nextTile[0]] == territoryMap[baseColumn][baseRow])
						{
							double slopeOfTerritoryBorder = (point1Y - point2Y) / (double)(point1X - point2X);
							double slopeOfHexBorder = (hexPointsY[i] - hexPointsY[(i + 1) % 6]) / (double)(hexPointsX[i] - hexPointsX[(i + 1) % 6]);
							double hexPointX = hexPointsX[i] + xDistanceIncrementer;
							double hexPointY = hexPointsY[i];
							if (("" + slopeOfTerritoryBorder).equals("Infinity") || ("" + slopeOfTerritoryBorder).equals("-Infinity"))
							{
								double newX = point1X;
								double newY = slopeOfHexBorder * (newX - hexPointX) + hexPointY;
								point1X = (int) newX;
								point1Y = (int) newY;
							}
							else if (("" + slopeOfHexBorder).equals("Infinity") || ("" + slopeOfHexBorder).equals("-Infinity"))
							{
								double newX = hexPointX;
								double newY = slopeOfTerritoryBorder * (newX - point1X) + point1Y;
								point1X = (int) newX;
								point1Y = (int) newY;
							}
							else
							{
								// y - hexPointY = slopeOfHexBorder * (x - hexPointX)
								//(slopeOfHexBorder * x) - y = - hexPointY + (slpeOfHexBorder * hexPointX)
								double hexSum = - hexPointY + (slopeOfHexBorder * hexPointX);
								double borderSum = - point1Y + (slopeOfTerritoryBorder * point1X);
								double totalSum = borderSum - hexSum;
								double newX = totalSum / (slopeOfTerritoryBorder - slopeOfHexBorder);
								double newY = slopeOfTerritoryBorder * (newX - point1X) + point1Y;
								point1X = (int) newX;
								point1Y = (int) newY;
							}
						}
						if (territoryMap[lastTile[1]][lastTile[0]] == territoryMap[baseColumn][baseRow])
						{
							double slopeOfTerritoryBorder = (point1Y - point2Y) / (double)(point1X - point2X);
							double slopeOfHexBorder = (hexPointsY[previousIndex] - hexPointsY[baseIndex]) / (double)(hexPointsX[previousIndex] - hexPointsX[baseIndex]);
							double hexPointX = hexPointsX[previousIndex] + xDistanceIncrementer;
							double hexPointY = hexPointsY[previousIndex];
							if (("" + slopeOfTerritoryBorder).equals("Infinity") || ("" + slopeOfTerritoryBorder).equals("-Infinity"))
							{
								double newX = point2X;
								double newY = slopeOfHexBorder * (newX - hexPointX) + hexPointY;
								point2X = (int) newX;
								point2Y = (int) newY;
							}
							else if (("" + slopeOfHexBorder).equals("Infinity") || ("" + slopeOfHexBorder).equals("-Infinity"))
							{
								double newX = hexPointX;
								double newY = slopeOfTerritoryBorder * (newX - point2X) + point2Y;
								point2X = (int) newX;
								point2Y = (int) newY;
							}
							else
							{
								// y - hexPointY = slopeOfHexBorder * (x - hexPointX)
								//(slopeOfHexBorder * x) - y = - hexPointY + (slpeOfHexBorder * hexPointX)
								double hexSum = - hexPointY + (slopeOfHexBorder * hexPointX);
								double borderSum = - point2Y + (slopeOfTerritoryBorder * point2X);
								double totalSum = borderSum - hexSum;
								double newX = totalSum / (slopeOfTerritoryBorder - slopeOfHexBorder);
								double newY = slopeOfTerritoryBorder * (newX - point2X) + point2Y;
								System.out.println("point1: " + point2X + " point2: " + point2Y);
								point2X = (int) newX;
								point2Y = (int) newY;
								System.out.println("Slope1: " + point2X + " Slope2: " + point2Y);
							}
						}
						g.setColor(territoryMap[baseColumn][baseRow].civilizationColor);
						((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
								(float) (1)));
						((Graphics2D) g).setStroke(new BasicStroke(8));
						g.drawLine(point1X, point1Y, point2X, point2Y);
						((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
								(float) (1)));
						((Graphics2D) g).setStroke(new BasicStroke(1));
					}
					baseIndex++;
					previousIndex++;
					previousIndex%= 6;
					baseIndex%= 6;
				}
			}
			int adjustedColumn = baseColumn - 2;
			if (adjustedColumn < 0) { adjustedColumn += Conquer.gameScreenSizeX; }
			if (fogOfWar[adjustedColumn][baseRow] == 0 && discoveredTiles[adjustedColumn][baseRow])//aa1
			{
				((Graphics2D)g6).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) (0.3)));
				g6.setColor(new Color(0, 0, 0));
				g6.fillPolygon(hex);
				((Graphics2D)g6).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) (1)));
			}
			if (discoveredTiles[baseColumn][baseRow] && fogOfWar[baseColumn][baseRow] > 0)
			{
				if (workerMap[baseColumn][baseRow] != null)
				{
					if (warriorMap[baseColumn][baseRow] != null)
					{
						workerMap[baseColumn][baseRow].paint(g2, hexPointsX[4], hexPointsY[5] + 10);
						newUnits.add(workerMap[baseColumn][baseRow]);
					}
					else
					{
						workerMap[baseColumn][baseRow].paint(g2, hexPointsX[4], hexPointsY[5]);
						newUnits.add(workerMap[baseColumn][baseRow]);
					}
				}
				if (warriorMap[baseColumn][baseRow] != null)
				{
					warriorMap[baseColumn][baseRow].paint(g2, hexPointsX[4], hexPointsY[5]);
					newUnits.add(warriorMap[baseColumn][baseRow]);
				}
			}
			int newColumn = baseColumn - 2;
			if (newColumn < 0)
			{
				newColumn = Conquer.gameScreenSizeX + newColumn;
			}
			if (movementPlan[newColumn][baseRow] != 0)
			{
				if (movementPlan[newColumn][baseRow] == -1 || movementPlan[newColumn][baseRow] == -2 || movementPlan[newColumn][baseRow] == -3 || movementPlan[newColumn][baseRow] == -5)
				{
					((Graphics2D) g3).setStroke(new BasicStroke(5));
					if (movementPlan[newColumn][baseRow] == -1)
					{
						g3.setColor(new Color(32, 33, 255));
					}
					else if (movementPlan[newColumn][baseRow] == - 2)
					{
						g3.setColor(new Color(219, 170, 36));
					}
					else if (movementPlan[newColumn][baseRow] == -3)
					{
						g3.setColor(new Color(155, 163, 151));
					}
					else
					{
						g3.setColor(new Color(255, 0, 0));
					}
					double iX = hexPointsX[5];
					double iY = (hexPointsY[5] + hexPointsY[2]) / 2.0;
					double fX = iX + Math.cos(movementPlanAngles[newColumn][baseRow]) * xDistanceIncrementer;
					double fY = iY - Math.sin(movementPlanAngles[newColumn][baseRow]) * xDistanceIncrementer;
					g3.drawLine((int)iX, (int)iY, (int)fX, (int)fY);
					((Graphics2D) g2).setStroke(new BasicStroke(1));
					if (movementPlan[newColumn][baseRow] == -1)
					{
						g4.setColor(new Color(32, 33, 255));
					}
					else if (movementPlan[newColumn][baseRow] == -2)
					{
						g4.setColor(new Color(219, 170, 36));
					}
					else if (movementPlan[newColumn][baseRow] == -3)
					{
						g4.setColor(new Color(155, 163, 151));
					}
					else
					{
						g4.setColor(new Color(255, 0, 0));
					}
					g4.fillOval((int)fX - 10, (int)fY - 10, 20, 20);
					g4.setColor(new Color(255, 255, 255));
					g4.drawOval((int)fX - 10, (int)fY - 10, 20, 20);
				}
				if (movementPlan[newColumn][baseRow] == -4)
				{
					((Graphics2D) g3).setStroke(new BasicStroke(6));
					g3.setColor(new Color(253, 27, 27));
					((Graphics2D) g3).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				              (float) ((60) / 100.0)));
					g3.drawOval(hexPointsX[0], hexPointsY[5] + 10, (int)xDistanceIncrementer, (int)xDistanceIncrementer);
					((Graphics2D) g3).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				              (float) (1)));
				}
				if (movementPlan[newColumn][baseRow] > 0)
				{
					((Graphics2D) g3).setStroke(new BasicStroke(5));
					if (movementPlan[newColumn][baseRow] == 1)
					{
						g3.setColor(new Color(32, 33, 255));
					}
					else if (movementPlan[newColumn][baseRow] == 2)
					{
						g3.setColor(new Color(219, 170, 36));
					}
					else
					{
						g3.setColor(new Color(155, 163, 151));
					}
					double iX = hexPointsX[5];
					double iY = (hexPointsY[5] + hexPointsY[2]) / 2.0;
					double fX = iX + Math.cos(movementPlanAngles[newColumn][baseRow]) * xDistanceIncrementer;
					double fY = iY - Math.sin(movementPlanAngles[newColumn][baseRow]) * xDistanceIncrementer;
					g3.drawLine((int)iX, (int)iY, (int)fX, (int)fY);
					((Graphics2D) g4).setStroke(new BasicStroke(1));
					if (movementPlan[newColumn][baseRow] == 1)
					{
						g4.setColor(new Color(32, 33, 255));
					}
					else if (movementPlan[newColumn][baseRow] == 2)
					{
						g4.setColor(new Color(219, 170, 36));
					}
					else
					{
						g4.setColor(new Color(155, 163, 151));
					}
					g4.fillOval((int)fX - 15, (int)fY - 15, 30, 30);
					g4.setColor(new Color(255, 255, 255));
					g4.drawOval((int)fX - 15, (int)fY - 15, 30, 30);
					g4.setFont(new Font("TimesRoman", Font.BOLD, 20));
					g4.drawString("" + movementPlan[newColumn][baseRow], (int)fX - g2.getFontMetrics().stringWidth("" + movementPlan[newColumn][baseRow]) / 2, (int)fY + 7);
				}
			}
			baseColumn += 2;
			baseColumn %= Conquer.gameScreenSizeX;
			}
		}
		BufferedImage movingUnitDrawing = new BufferedImage(mainMapDrawing.getWidth(), mainMapDrawing.getHeight(), BufferedImage.TRANSLUCENT);
		System.out.println("Tiles Needed To Refresh: " + tilesNeededToRefreshX.size());
		for (int i = 0; i < tilesNeededToRefreshX.size(); i++)
		{
			if (tilesNeededToRefreshX.size() != tilesNeededToRefreshY.size())
			{
				System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHAHA");
			}
			refreshTile(tilesNeededToRefreshX.get(i), tilesNeededToRefreshY.get(i), g, g5, g2, g6, xOffset, yOffset, newCityTags);
		}
		tilesNeededToRefreshX = new ArrayList<Integer>();
		tilesNeededToRefreshY = new ArrayList<Integer>();
		if (movementTilesNeededToRefresh != null)
		{
			movementMapDrawingCircles = new BufferedImage(mainMapDrawing.getWidth(), mainMapDrawing.getHeight(), BufferedImage.TRANSLUCENT);
			g4.dispose();
			g4 = null;
			g4 = movementMapDrawingCircles.createGraphics();
			movementMapDrawing = new BufferedImage(mainMapDrawing.getWidth(), mainMapDrawing.getHeight(), BufferedImage.TRANSLUCENT);
			g3.dispose();
			g3 = null;
			g3 = movementMapDrawing.createGraphics();
			refreshMovementTile(movementTilesNeededToRefresh, g3, g4, movementMapDrawingCircles, xOffset, yOffset);
		}
		movementTilesNeededToRefresh = null;
		g3.drawImage(movementMapDrawingCircles, 0, 0, null);
		for (int i = 0; i < tilesNeededToRefreshX.size(); i++)
		{
			if (tilesNeededToRefreshX.size() != tilesNeededToRefreshY.size())
			{
				System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHAHA");
			}
			refreshTile(tilesNeededToRefreshX.get(i), tilesNeededToRefreshY.get(i), g, g5, g2, g6, xOffset, yOffset, newCityTags);
		}
		tilesNeededToRefreshX = new ArrayList<Integer>();
		tilesNeededToRefreshY = new ArrayList<Integer>();
		movementMapDrawingCircles = null;
		mainMapImageOffsetX = xOffset;
		mainMapImageOffsetY = yOffset;
		mainMap = null;
		mainMap = mainMapDrawing;
		fogOfWarImage = fogOfWarDrawing;
		mainMapDrawing = null;
		unitMap = unitMapDrawing;
		unitMapDrawing = null;
		if (movementMap != null)
		{
			movementMap = movementMapDrawing;
		}
		movementMapDrawing = null;
		for (int i = 0; i < unitLocationsNeededToClear.size(); i++)
		{
			Point p = Unit.getUnitLocationOnMainMap(new Unit(this, 0, 0, new Player()), unitLocationsNeededToClear.get(i));
			g2.setColor(new Color(0,0,0, (float)0));
			Composite c = ((Graphics2D)g2).getComposite();
			((Graphics2D)g2).setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
			g2.fillRect(p.x, p.y, (int)xDistanceIncrementer, (int)sideLength * 3 /2);
			((Graphics2D)g2).setComposite(c);
			if (workerMap[unitLocationsNeededToClear.get(i).x][unitLocationsNeededToClear.get(i).y] != null)
			{
				if (warriorMap[unitLocationsNeededToClear.get(i).x][unitLocationsNeededToClear.get(i).y] != null)
				{
					workerMap[unitLocationsNeededToClear.get(i).x][unitLocationsNeededToClear.get(i).y].paint(g2, p.x, p.y + 10);
					newUnits.add(workerMap[unitLocationsNeededToClear.get(i).x][unitLocationsNeededToClear.get(i).y]);
				}
				else
				{
					workerMap[unitLocationsNeededToClear.get(i).x][unitLocationsNeededToClear.get(i).y].paint(g2, p.x, p.y);
					newUnits.add(workerMap[unitLocationsNeededToClear.get(i).x][unitLocationsNeededToClear.get(i).y]);
				}
			}
			if (warriorMap[unitLocationsNeededToClear.get(i).x][unitLocationsNeededToClear.get(i).y] != null)
			{
				warriorMap[unitLocationsNeededToClear.get(i).x][unitLocationsNeededToClear.get(i).y].paint(g2, p.x, p.y);
				newUnits.add(warriorMap[unitLocationsNeededToClear.get(i).x][unitLocationsNeededToClear.get(i).y]);
			}
			System.out.println("Clearing");
		}
		for (int i = 0; i < unitsNeededToRefresh.size(); i++)
		{
			Point p = Unit.getUnitLocationOnMainMap(unitsNeededToRefresh.get(i), unitsNeededToRefresh.get(i).location);
			if (workerMap[unitsNeededToRefresh.get(i).location.x][unitsNeededToRefresh.get(i).location.y] != null)
			{
				if (warriorMap[unitsNeededToRefresh.get(i).location.x][unitsNeededToRefresh.get(i).location.y] != null)
				{
					workerMap[unitsNeededToRefresh.get(i).location.x][unitsNeededToRefresh.get(i).location.y].paint(g2, p.x, p.y + 10);
					newUnits.add(workerMap[unitsNeededToRefresh.get(i).location.x][unitsNeededToRefresh.get(i).location.y]);
				}
				else
				{
					workerMap[unitsNeededToRefresh.get(i).location.x][unitsNeededToRefresh.get(i).location.y].paint(g2, p.x, p.y);
					newUnits.add(workerMap[unitsNeededToRefresh.get(i).location.x][unitsNeededToRefresh.get(i).location.y]);
				}
			}
			if (warriorMap[unitsNeededToRefresh.get(i).location.x][unitsNeededToRefresh.get(i).location.y] != null)
			{
				warriorMap[unitsNeededToRefresh.get(i).location.x][unitsNeededToRefresh.get(i).location.y].paint(g2, p.x, p.y);
				newUnits.add(warriorMap[unitsNeededToRefresh.get(i).location.x][unitsNeededToRefresh.get(i).location.y]);
			}
			System.out.println("Refreshing");
		}
		Unit.units = newUnits;
		cityTags = newCityTags;
		unitLocationsNeededToClear = new ArrayList<Point>();
		unitsNeededToRefresh = new ArrayList<Unit>();
		movingUnitImage = movingUnitDrawing;
		cityTagsImage = cityTagsImageDrawing;
		cityTagsImageDrawing = null;
		g6.dispose();
		g6 = null;
		g5.dispose();
		g5 = null;
		g4.dispose();
		g4 = null;
		g3.dispose();
		g3 = null;
		g.dispose();
		g = null;
		g2.dispose();
		g = null;
		repaint();
	}
	public void drawThickLine(int iX, int iY, int fX, int fY, double thickness, Graphics g)
	{
		double lineAngle = Math.atan2(fY - iY, fX - iX) * 180 / Math.PI;
		int x1 = (int) (Math.cos((lineAngle + 90) * Math.PI / 180) * thickness) + iX;
		int y1 = (int) (Math.sin((lineAngle + 90) * Math.PI / 180) * thickness) + iY;
		int x2 = (int) (Math.cos((lineAngle + 90) * Math.PI / 180) * thickness) + fX;
		int y2 = (int) (Math.sin((lineAngle + 90) * Math.PI / 180) * thickness) + fY;
		int x3 = (int) (Math.cos((lineAngle - 90) * Math.PI / 180) * thickness) + iX;
		int y3 = (int) (Math.sin((lineAngle - 90) * Math.PI / 180) * thickness) + iY;
		int x4 = (int) (Math.cos((lineAngle - 90) * Math.PI / 180) * thickness) + fX;
		int y4 = (int) (Math.sin((lineAngle - 90) * Math.PI / 180) * thickness) + fY;
		Polygon p = new Polygon();
		p.addPoint(x3, y3);
		p.addPoint(x4, y4);
		p.addPoint(x2, y2);
		p.addPoint(x1, y1);
		g.drawPolygon(p);
		g.fillPolygon(p);
		
	}
	public boolean isAdjacent(int row1, int column1, int row2, int column2)
	{
		boolean adjacent = false;
		if ((Math.abs(row1 - row2) + Math.abs(column1 - column2) == 2) && Math.abs(row1 - row2) != 2)
		{
			adjacent = true;
		}
		else if ((Math.abs(row1 - row2) == Conquer.gameScreenSizeY - 1 && Math.abs(column1 - column2) == 1) || (Math.abs(column1 - column2) >= Conquer.gameScreenSizeX - 2 && (Math.abs(row1 - row2) <= 1)) || (Math.abs(column1 - column2) == Conquer.gameScreenSizeX - 1 && Math.abs(row1 - row2) == Conquer.gameScreenSizeY - 1))
		{
			adjacent = true;
		}
		return adjacent;
	}
	public void setDragOffset()
	{
		xOffset = xOrigin + MouseInfo.getPointerInfo().getLocation().x - mX - this.getLocationOnScreen().x;
		if (2 * sideLength > yOrigin + MouseInfo.getPointerInfo().getLocation().y - mY - this.getLocationOnScreen().y && - 3 * sideLength / 2 * Conquer.gameScreenSizeY + this.getHeight() < yOrigin + MouseInfo.getPointerInfo().getLocation().y - mY - this.getLocationOnScreen().y)
		{
			yOffset = yOrigin + MouseInfo.getPointerInfo().getLocation().y - mY - this.getLocationOnScreen().y;
		}
		else if (2 * sideLength < yOrigin + MouseInfo.getPointerInfo().getLocation().y - mY - this.getLocationOnScreen().y)
		{
			yOffset = 2 * sideLength;
		}
		else if (- 3 * sideLength / 2 * Conquer.gameScreenSizeY + this.getHeight() > yOrigin + MouseInfo.getPointerInfo().getLocation().y - mY - this.getLocationOnScreen().y)
		{
			yOffset = - 3 * sideLength / 2 * Conquer.gameScreenSizeY + this.getHeight();
		}
	}
	boolean shiftDown = false;
	public class PanelListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			if (e.isShiftDown())
			{
				shiftDown = true;
				System.out.println("SHIFT DOWN");
			}
			if (mousePressed == false)
			{
				clickOccurring = true;
			}
			sliding = false;
			mX = e.getX();
			mY = e.getY();
			Conquer.dragVelocityTim = 10;
			Conquer.savedDragX = mX;
			Conquer.savedDragY = mY;
			mousePressed = true;			
		}
		public void mouseReleased(MouseEvent e)
		{
			if (mousePressed == true)
			{
				rX = e.getX();
				rY = e.getY();
				if (Math.abs(mX - rX) < 10 && Math.abs(mY - rY) < 10)
				{
					clickReleaseOccurring = true;
				}
				xOrigin = xOffset;
				yOrigin = yOffset;
				sliding = true;
			}
			mousePressed = false;
		}
	}
	public void addToControlListener(Control control)
	{
		control.controlHandler = backgroundControlListener;
	}
	public class colorPanelControlHandler extends ControlHandler
	{
		ColorPanel colorPanel;
		public colorPanelControlHandler(ColorPanel colorPanel)
		{
			this.colorPanel = colorPanel;
		}
		public void clickEvent(String id)
		{
			if (id.equals("Menu Button"))
			{
				menuButton.setVisible(false);
				techButton.setVisible(false);
				socialButton.setVisible(false);
				turnRotationButton.setVisible(false);
				menuOpen = true;
				if (menu == null)
				{
					menu = new Page(600, 670, colorPanel, new Point(colorPanel.getWidth() / 2 - 300, 20));
					menu.backgroundVisibility = 0.8;
					menu.color = new Color(86, 91, 223);
					Label menuLabel = new Label("Menu", colorPanel, new Point(menu.getWidth() / 2 - 220, 40));
					menuLabel.setFont(new Font("Monospaced", Font.BOLD, 90));
					menuLabel.setVisible(true);
					menuLabel.color = new Color(255, 255, 255);
					resumeGameLabel = new Label("Resume", colorPanel, new Point(menu.getWidth() / 2 - 220, 140));
					resumeGameLabel.setFont(new Font("Rockwell", Font.BOLD, 40));
					resumeGameLabel.setVisible(true);
					resumeGameLabel.id = "Resume Game";
					saveGameLabel = new Label("Save", colorPanel, new Point(menu.getWidth() / 2 - 220, 200));
					saveGameLabel.setFont(new Font("Rockwell", Font.BOLD, 40));
					saveGameLabel.setVisible(true);
					saveGameLabel.id = "Save Game";
					quitGameLabel = new Label("Quit", colorPanel, new Point(menu.getWidth() / 2 - 220, 260));
					quitGameLabel.setFont(new Font("Rockwell", Font.BOLD, 40));
					quitGameLabel.setVisible(true);
					quitGameLabel.id = "Quit Game";
					addToControlListener(resumeGameLabel);
					addToControlListener(saveGameLabel);
					addToControlListener(quitGameLabel);
					savedGameTextBox.setLocation(new Point(menu.getWidth() / 2 - 70, 150));
					savedGameTextBox.setFont(new Font("TimesRoman", Font.BOLD, 25));
					saveGameTextBoxLabel = new Label("Enter File Name: ", colorPanel, new Point(menu.getWidth() / 2 - 220, 155));
					saveGameTextBoxLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
					savedGamesDisplay = new ListBox(300, 400, colorPanel, new Point(menu.getWidth() / 2 - 180, 200));
					savedGamesDisplay.id = "Saved Games ListBox";
					savedGamesDisplay.controlHandler = new colorPanelControlHandler(colorPanel);
					saveGameButton = new Button(" Save", 60, 30, colorPanel, new Point(menu.getWidth() / 2 + 100, 150));
					saveGameButton.setFont(new Font("TimesRoman", Font.BOLD, 24));
					saveGameButton.backgroundColor = new Color(209, 207, 215);
					saveGameButton.id = "Save";
					addToControlListener(saveGameButton);
					menu.add(saveGameButton);
					menu.add(savedGamesDisplay);
					menu.add(menuLabel);
					menu.add(saveGameTextBoxLabel);
					menu.add(resumeGameLabel);
					menu.add(saveGameLabel);
					menu.add(quitGameLabel);
					menu.add(savedGameTextBox);
				}
				menu.setVisible(true);
				repaint();
			}
			else if (id.equals("Tech Button"))
			{
				techOriginX = 0 - mX;
				menuButton.setVisible(false);
				techButton.setVisible(false);
				socialButton.setVisible(false);
				techTreeOpen = true;
				dimBackground = new Page(colorPanel.getWidth() + 5, colorPanel.getWidth() + 5, colorPanel, new Point(- 2, -2));
				dimBackground.backgroundVisibility = 0.6;
				dimBackground.setVisible(true);
				Image techTreeimg = new ImageIcon(this.getClass().getResource("techTree.jpg")).getImage();
				techTree = new DoodleImage(techTreeimg.getWidth(null) * colorPanel.getHeight() / techTreeimg.getHeight(null), colorPanel.getHeight(), colorPanel, new Point(0, 0), techTreeimg);
				techTree.antialiasing = true;
				dimBackground.add(techTree);
				techTree.setVisible(true);
				closeButton = new CircleButton("x", 30, colorPanel, new Point(colorPanel.getWidth() - 50, 8));
				closeButton.color = new Color(255, 0, 0);
				closeButton.backgroundColor = Color.black;
				closeButton.backgroundVisibility = 0;
				closeButton.antialiasing = true;
				closeButton.borderColor = new Color(255, 0 , 0);
				closeButton.setFont(new Font("Verdana", Font.BOLD, 27));
				closeButton.stringOffsetX = 2;
				closeButton.stringOffsetY = 2;
				closeButton.id = "Close Tech Tree";
				addToControlListener(closeButton);
				dimBackground.add(closeButton);
				closeButton.setVisible(true);
			}
			else if (id.equals("Close Tech Tree"))
			{
				techTreeOpen = false;
				techTree.dispose();
				techTree = null;
				dimBackground.dispose();
				dimBackground = null;
				closeButton.dispose();
				closeButton = null;
				menuButton.setVisible(true);
				techButton.setVisible(true);
				socialButton.setVisible(true);
			}
			else if (id.equals("Resume Game"))
			{
				menu.setVisible(false);
				menuOpen = false;
				menuButton.setVisible(true);
				techButton.setVisible(true);
				socialButton.setVisible(true);
				turnRotationButton.setVisible(true);
				repaint();
			}
			else if (id.equals("Rotate Turn"))
			{
				if (players[turn].toDoList.size() > 0)
				{
					players[turn].toDoList.get(0).turnRotationClickEvent();	
				}
				else
				{
					rotateTurn();
				}
			}
			else if (id.equals("Save Game"))
			{
				resumeGameLabel.setVisible(false);
				saveGameLabel.setVisible(false);
				quitGameLabel.setVisible(false);
				savedGameTextBox.setVisible(true);
				savedGameTextBox.setText(fileName);
				saveGameTextBoxLabel.setVisible(true);
				savedGameTextBox.bringToFront();
				savedGamesDisplay.setVisible(true);
				saveGameButton.setVisible(true);
				savedGamesDisplay.clearItems();
				File conquerGames = new File("Conquer Games");
				File[] conquerFiles = conquerGames.listFiles();
				for (int i = 0; i < conquerFiles.length; i++)
				{
					savedGamesDisplay.add(conquerFiles[i].getName());
				}
			}
			else if (id.equals("Quit Game"))
			{
				colorPanel.setVisible(false);
			}
			else if (id.equals("Save"))
			{
				try {
					if (savedGameTextBox.getText().equals(""))
					{
						System.out.println("Error");
					}
					else
					{
						saveGame(savedGameTextBox.getText());
						if (savedGamesDisplay.getIndexOf(savedGameTextBox.getText()) == -1)
						{
							savedGamesDisplay.add(savedGameTextBox.getText());
						}
						repaint();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (id.equals("Saved Games ListBox"))
			{
				if (savedGamesDisplay.getItem(savedGamesDisplay.getSelectedIndex()) != null)
				{
					savedGameTextBox.setText(savedGamesDisplay.getItem(savedGamesDisplay.getSelectedIndex()));
				}
				repaint();
			}
		}
	}
	public void saveGame(String gameName) throws IOException
	{
		String fileName = gameName;
		File gameFile = new File("Conquer Games/" + fileName);
		if (!gameFile.exists())
		{
			if (!gameFile.exists())
			{
				gameFile.mkdir();
			}
			File mapFile = new File("Conquer Games/" + gameFile.getName() + "/map");
			if (mapFile.exists() == false)
			{
				mapFile.mkdir();
			}
			File playersFile = new File("Conquer Games/" + gameFile.getName() + "/players");
			if (playersFile.exists() == false)
			{
				playersFile.mkdir();
			}
			File savesFile = new File("Conquer Games/" + gameFile.getName() + "/saves");
			if (savesFile.exists() == false)
			{
				savesFile.mkdir();
			}
			File baseGeographyFile = new File("Conquer Games/" + gameFile.getName() + "/map/baseGeography.txt");
			File resourceGeographyFile = new File("Conquer Games/" + gameFile.getName() + "/map/resourceGeography.txt");
			File upperGeographyFile = new File("Conquer Games/" + gameFile.getName() + "/map/upperGeography.txt");
			File riverGeographyFile = new File("Conquer Games/" + gameFile.getName() + "/map/riverGeography.txt");
			File discoveredTilesFile = new File("Conquer Games/" + gameFile.getName() + "/map/discoveredTiles.txt");
			File gameInfo = new File("Conquer Games/" + gameFile.getName() + "/Game Info.txt");
			if (!resourceGeographyFile.exists())
			{
				resourceGeographyFile.createNewFile();
			}
			if (!upperGeographyFile.exists())
			{
				upperGeographyFile.createNewFile();
			}
			if (!baseGeographyFile.exists())
			{
				baseGeographyFile.createNewFile();
			}
			if (!riverGeographyFile.exists())
			{
				riverGeographyFile.createNewFile();
			}
			if (!gameInfo.exists())
			{
				gameInfo.createNewFile();
			}
			if (!discoveredTilesFile.exists())
			{
				discoveredTilesFile.createNewFile();
			}
		}
		File gameInfo = new File("Conquer Games/" + fileName + "/Game Info.txt");
		try {
			FileWriter wr = new FileWriter(gameInfo);
			PrintWriter writer = new PrintWriter(wr);
			String timeStamp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
			writer.println(timeStamp);
			writer.println();
			writer.println(xOffset + " " + yOffset);
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File baseGeographyFile = new File("Conquer Games/" + fileName + "/map/baseGeography.txt");
		try {
			FileWriter wr = new FileWriter(baseGeographyFile);
			PrintWriter writer = new PrintWriter(wr);
			writer.println(baseGeographicMap.length + " " + baseGeographicMap[0].length);
			for (int y = 0; y < baseGeographicMap[0].length; y++)
			{
				for (int x = 0; x < baseGeographicMap.length; x++)
				{
					writer.print(baseGeographicMap[x][y].getValue() + " ");
				}
				writer.println();
			}
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File upperGeographyFile = new File("Conquer Games/" + fileName + "/map/upperGeography.txt");
		try {
			FileWriter wr = new FileWriter(upperGeographyFile);
			PrintWriter writer = new PrintWriter(wr);
			writer.println(baseGeographicMap.length + " " + baseGeographicMap[0].length);
			for (int y = 0; y < baseGeographicMap[0].length; y++)
			{
				for (int x = 0; x < baseGeographicMap.length; x++)
				{
					if (upperGeographicMap[x][y] != null)
					{
						writer.print(upperGeographicMap[x][y].getValue() + " ");
					}
					else
					{
						writer.print(-1 + " ");
					}
				}
				writer.println();
			}
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File resourceGeography = new File("Conquer Games/" + fileName + "/map/resourceGeography.txt");
		try {
			FileWriter wr = new FileWriter(resourceGeography);
			PrintWriter writer = new PrintWriter(wr);
			writer.println(baseGeographicMap.length + " " + baseGeographicMap[0].length);
			for (int y = 0; y < baseGeographicMap[0].length; y++)
			{
				for (int x = 0; x < baseGeographicMap.length; x++)
				{
					if (resourceGeographicMap[x][y] != null)
					{
						writer.print(resourceGeographicMap[x][y].getValue() + " ");
					}
					else
					{
						writer.print(-1 + " ");
					}
				}
				writer.println();
			}
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File riverGeography = new File("Conquer Games/" + fileName + "/map/riverGeography.txt");
		try {
			FileWriter wr = new FileWriter(riverGeography);
			PrintWriter writer = new PrintWriter(wr);
			writer.println(baseGeographicMap.length + " " + baseGeographicMap[0].length);
			for (int y = 0; y < baseGeographicMap[0].length; y++)
			{
				for (int x = 0; x < baseGeographicMap.length; x++)
				{
					if (riverTracker[x][y].isAdjacentToRiver())
					{
						writer.print("1" + " ");
						if (riverTracker[x][y].verticesRiverActive[0])
						{
							writer.print("t" + " ");
						}
						else
						{
							writer.print("f" + " ");
						}
						if (riverTracker[x][y].verticesRiverActive[1])
						{
							writer.print("t" + " ");
						}
						else
						{
							writer.print("f" + " ");
						}
						if (riverTracker[x][y].verticesRiverActive[2])
						{
							writer.print("t" + " ");
						}
						else
						{
							writer.print("f" + " ");
						}
						if (riverTracker[x][y].verticesRiverActive[3])
						{
							writer.print("t" + " ");
						}
						else
						{
							writer.print("f" + " ");
						}
						if (riverTracker[x][y].verticesRiverActive[4])
						{
							writer.print("t" + " ");
						}
						else
						{
							writer.print("f" + " ");
						}
						if (riverTracker[x][y].verticesRiverActive[5])
						{
							writer.print("t" + " ");
						}
						else
						{
							writer.print("f" + " ");
						}
						writer.print(riverTracker[x][y].riverID[0][0] + " ");
						writer.print(riverTracker[x][y].riverID[1][0] + " ");
						writer.print(riverTracker[x][y].riverID[2][0] + " ");
						writer.print(riverTracker[x][y].riverID[3][0] + " ");
						writer.print(riverTracker[x][y].riverID[4][0] + " ");
						writer.print(riverTracker[x][y].riverID[5][0] + " ");
						
						writer.print(riverTracker[x][y].riverID[0][1] + " ");
						writer.print(riverTracker[x][y].riverID[1][1] + " ");
						writer.print(riverTracker[x][y].riverID[2][1] + " ");
						writer.print(riverTracker[x][y].riverID[3][1] + " ");
						writer.print(riverTracker[x][y].riverID[4][1] + " ");
						writer.print(riverTracker[x][y].riverID[5][1] + " ");
					}
					else
					{
						writer.print(-99 + " ");
					}
				}
				writer.println();
			}
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File discoveredTilesFile = new File("Conquer Games/" + gameFile.getName() + "/map/discoveredTiles.txt");
		try {
			FileWriter wr = new FileWriter(discoveredTilesFile);
			PrintWriter writer = new PrintWriter(wr);
			writer.println(discoveredTiles.length + " " + discoveredTiles[0].length);
			for (int y = 0; y < discoveredTiles[0].length; y++)
			{
				for (int x = 0; x < discoveredTiles.length; x++)
				{
					if (discoveredTiles[x][y] == true)
					{
						writer.print("t ");
					}
					else
					{
						writer.print("f ");
					}
				}
				writer.println();
			}
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadGame()
	{
		File baseGeographyFile = new File("Conquer Games/" + fileName + "/map/baseGeography.txt");
		try {
			Scanner scan = new Scanner(baseGeographyFile);
			Conquer.gameScreenSizeX = scan.nextInt();
			Conquer.gameScreenSizeY = scan.nextInt();
			farthestDiscoveredEast = Conquer.gameScreenSizeX;
			farthestDiscoveredWest = 0;
			farthestDiscoveredNorth = 0;
			farthestDiscoveredSouth = Conquer.gameScreenSizeY;
			
			//IMPORTANT READ DON'T CREATE LOAD STATEMENTS BEFORE THIS ONE BECAUSE Conquer.gameScreenSizeY
			
			
			baseGeographicMap = new baseGeography[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
			for (int y = 0; y < Conquer.gameScreenSizeY; y++)
			{
				for (int x = 0; x < Conquer.gameScreenSizeX; x++)
				{
					baseGeographicMap[x][y] = baseGeography.getGeography(scan.nextInt());
				}
			}
			scan.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File upperGeographyFile = new File("Conquer Games/" + fileName + "/map/upperGeography.txt");
		try {
			Scanner scan = new Scanner(upperGeographyFile);
			upperGeographicMap = new upperGeography[scan.nextInt()][scan.nextInt()];
			for (int y = 0; y < Conquer.gameScreenSizeY; y++)
			{
				for (int x = 0; x < Conquer.gameScreenSizeX; x++)
				{
					upperGeographicMap[x][y] = upperGeography.getGeography(scan.nextInt());
				}
			}
			scan.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File resourceGeographyFile = new File("Conquer Games/" + fileName + "/map/resourceGeography.txt");
		try {
			Scanner scan = new Scanner(resourceGeographyFile);
			resourceGeographicMap = new resourceGeography[scan.nextInt()][scan.nextInt()];
			for (int y = 0; y < Conquer.gameScreenSizeY; y++)
			{
				for (int x = 0; x < Conquer.gameScreenSizeX; x++)
				{
					resourceGeographicMap[x][y] = resourceGeography.getGeography(scan.nextInt());
				}
			}
			scan.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File riverTrackerFile = new File("Conquer Games/" + fileName + "/map/riverGeography.txt");
		try {
			Scanner scan = new Scanner(riverTrackerFile);
			riverTracker = new vertices[scan.nextInt()][scan.nextInt()];
			for (int y = 0; y < Conquer.gameScreenSizeY; y++)
			{
				for (int x = 0; x < Conquer.gameScreenSizeX; x++)
				{
					if (scan.next().equals("1"))
					{
						riverTracker[x][y] = new vertices();
						for (int i = 0; i < 6; i++)
						{
							if (scan.next().equals("t"))
							{
								riverTracker[x][y].verticesRiverActive[i] = true;
							}
							else
							{
								riverTracker[x][y].verticesRiverActive[i] = false;
							}
						}
						for (int i = 0; i < 6; i++)
						{
							riverTracker[x][y].riverID[i][0] = scan.nextInt();
						}
						for (int i = 0; i < 6; i++)
						{
							riverTracker[x][y].riverID[i][1] = scan.nextInt();
						}
					}
					else
					{
						riverTracker[x][y] = new vertices();
					}
				}
			}
			scan.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File discoveredTilesFile = new File("Conquer Games/" + fileName + "/map/discoveredTiles.txt");
		try {
			Scanner scan = new Scanner(discoveredTilesFile);
			discoveredTiles = new boolean[scan.nextInt()][scan.nextInt()];
			for (int y = 0; y < Conquer.gameScreenSizeY; y++)
			{
				for (int x = 0; x < Conquer.gameScreenSizeX; x++)
				{
					if (scan.next().equals("t"))
					{
						discoveredTiles[x][y] = true;
					}
					else
					{
						discoveredTiles[x][y] = false;
					}
				}
			}
			scan.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getRows(int x, int y, double xOffset, double yOffset)
	{
		int rows;
		for(rows = - 5; y - yOffset > (Math.abs((Math.abs(x - xOffset + (xDistanceIncrementer / 2 * rows)) % xDistanceIncrementer) - (xDistanceIncrementer / 2)) / Math.sqrt(3)) + (3 * sideLength / 2 * rows); rows++);
		rows = ((rows % Conquer.gameScreenSizeY) + Conquer.gameScreenSizeY) % Conquer.gameScreenSizeY;
		return rows;
	}
	public int getColumns(int x, int y, double xOffset, double yOffset)
	{
		int columns;
		for(columns = - (Conquer.gameScreenSizeX + 5) ; x - xOffset > (Math.sqrt(3) * Math.abs( ((Math.abs(y - yOffset + (sideLength / 2) + (3 * sideLength / 2 * columns)) % (3 * sideLength)) - (3 * sideLength / 2) ) ) / 2) - (Math.sqrt(3) * Math.abs( ((Math.abs(y - yOffset + (sideLength / 2) + (3 * sideLength / 2 * columns)) % (sideLength)) - (sideLength / 2) ) ) / 2) + (xDistanceIncrementer / 2 * columns); columns++);
		columns = ((columns % Conquer.gameScreenSizeX) + Conquer.gameScreenSizeX) % Conquer.gameScreenSizeX;
		return columns;
	}
	public void addMouseListener()
	{
		addMouseListener(new PanelListener());
	}
	public void mouseTimEvent()
	{
		int rows = getRows(mX, mY, xOffset, yOffset);
		int columns = getColumns(mX, mY, xOffset, yOffset);
		if (checkingifadjacent == false)
		{
			testRows = rows;
			testColumns = columns;
			checkingifadjacent = true;
		}
		else
		{
			if(isAdjacent(testRows, testColumns, rows, columns))
			{ System.out.println("Is adjacent"); }
			else { System.out.println("Is not adjacent"); }
			checkingifadjacent = false;
		}
		System.out.println("Rows: " + rows + "\nColumns: " + columns);
		if (shiftDown)
		{
			shiftDown = false;
			/*
			Point[] path = getViablePathBetween(new Point(testColumns, testRows), new Point(columns, rows), 2);
			for (int i = 0; i < path.length; i++)
			{
				System.out.println("Path " + i + ": " + path[i].x + " " + path[i].y);
				baseGeographicMap[path[i].x][path[i].y] = baseGeography.glacier;
				refreshTile(path[i].x, path[i].y);
			}
			*/
			///*
			int[][] tiles = getAllTilesWithin(columns, rows, 100);
			for (int i = 0; i < tiles[0].length; i++)
			{
				if (tiles[0][i] != -1)
				{
					discoveredTiles[tiles[0][i]][tiles[1][i]] = true;
					refreshTile(tiles[0][i], tiles[1][i]);
					setFarthestOut(tiles[0][i], tiles[1][i]);
				}
			}
			discoveredTiles[columns][rows] = true;
			refreshTile(columns, rows);
			//*/
			developeMiniMapOffThread();
		}
		if (techTreeOpen)
		{
			System.out.println("X ratio: " + ((mX - techTree.getLocation().x) / (double) techTree.getWidth()) + "Y ratio: " + (mY / (double) techTree.getHeight()));
		}
		repaint();
	}
	public boolean setFarthestOut(int x, int y)
	{
		boolean pastBounds = false;
		if (x > farthestDiscoveredEast)
		{
			farthestDiscoveredEast = x;
			pastBounds = true;
		}
		if (y > farthestDiscoveredSouth)
		{
			farthestDiscoveredSouth = y;
			pastBounds = true;
		}
		if (x < farthestDiscoveredWest)
		{
			farthestDiscoveredWest = x;
			pastBounds = true;
		}
		if (y < farthestDiscoveredNorth)
		{
			farthestDiscoveredNorth = y;
			pastBounds = true;
		}
		return pastBounds;
	}
	public boolean anyObjectContains(int x, int y)
	{
		boolean overlapsObject = false;
		if (miniMapContainer.contains(x, y))
		{
			overlapsObject = true;
		}
		if (circleContains(miniMapMinimizeButtonCenterX, miniMapMinimizeButtonCenterY, x, y, miniMapMinimizeButtonRadius) && clickOccurring)
		{
			clickOccurring = false;
			overlapsObject = true;
			if (minimizeMiniMap > 0)
			{
				minimizeMiniMap--;
			}
			if (minimizeMiniMap == 1)
			{
				miniMap.flush();
				miniMap = null;
				miniMap = new BufferedImage( (int) (300 * Math.sqrt(3)), 150 * 3 / 2, BufferedImage.TYPE_INT_RGB);
				developeMiniMap();
			}
			menuButton.setVisible(true);
			socialButton.setVisible(true);
			techButton.setVisible(true);
			turnRotationButton.setVisible(true);
		}
		if (circleContains(miniMapMaximizeButtonCenterX, miniMapMaximizeButtonCenterY, x, y, miniMapMaximizeButtonRadius) && minimizeMiniMap < 2 && clickOccurring)
		{
			clickOccurring = false;
			overlapsObject = true;
			minimizeMiniMap++;
			if (minimizeMiniMap == 2)
			{
				menuButton.setVisible(false);
				socialButton.setVisible(false);
				techButton.setVisible(false);
				turnRotationButton.setVisible(false);
				if ((this.getWidth() / Math.sqrt(3)) / (this.getHeight() * 2 / 3) > 2)
				{
					miniMap.flush();
					miniMap = null;
					miniMap = new BufferedImage( (int) ((int) this.getHeight() * 4 * Math.sqrt(3) / 3), this.getHeight(), BufferedImage.TYPE_INT_RGB);
				}
				else
				{
					miniMap.flush();
					miniMap = null;
					miniMap = new BufferedImage( this.getWidth(), (int) (this.getWidth() * 3 / (4 * Math.sqrt(3))), BufferedImage.TYPE_INT_RGB);
				}
				developeMiniMap();
			}
		}
		if (miniMapBox != null)
		{
			if (miniMapBox.contains(mX, mY) && clickOccurring)
			{
				clickOccurring = false;
				overlapsObject = true;
				minimizeMiniMap = 1;
				double width, height;
				if ((farthestDiscoveredEast - farthestDiscoveredWest + 1) / (double)(farthestDiscoveredSouth - farthestDiscoveredNorth + 1) > 4)
				{
					width = (farthestDiscoveredEast - farthestDiscoveredWest + 1);
					height = (farthestDiscoveredEast - farthestDiscoveredWest + 1) / 4.0;
				}
				else
				{
					height = (farthestDiscoveredSouth - farthestDiscoveredNorth + 1);
					width = (farthestDiscoveredSouth - farthestDiscoveredNorth + 1) * 4.0;
				}
				double miniMapSideLength = (miniMap.getHeight() * 2) / (3 * height);
				double newXDistanceIncrementer = miniMapSideLength * Math.sqrt(3);
				double miniMapX = mX - miniMapBox.x;
				double miniMapY = mY - miniMapBox.y;
				double newXOffset;
				double newYOffset;
				if (farthestDiscoveredWest % 2 == 1)
				{
					newXOffset = (farthestDiscoveredWest - 1) / 2 * xDistanceIncrementer;
					newYOffset = farthestDiscoveredNorth * sideLength * 3 / 2 - sideLength * 3 / 2;
				}
				else
				{
					newXOffset = (farthestDiscoveredWest) / 2 * xDistanceIncrementer - xDistanceIncrementer / 2;
					newYOffset = farthestDiscoveredNorth * sideLength * 3 / 2 - sideLength * 3 / 2;
				}
				xOffset = - miniMapX / newXDistanceIncrementer * xDistanceIncrementer - newXOffset + getWidth() / 2;
				yOffset = - miniMapY / (2 * miniMapSideLength) * 2 * sideLength - newYOffset + getHeight() / 2;
				xOrigin = xOffset;
				yOrigin = yOffset;
				menuButton.setVisible(true);
				socialButton.setVisible(true);
				techButton.setVisible(true);
				turnRotationButton.setVisible(true);
				miniMap.flush();
				miniMap = null;
				miniMap = new BufferedImage( (int) (300 * Math.sqrt(3)), 150 * 3 / 2, BufferedImage.TYPE_INT_RGB);
				developeMiniMap();
				repaint();
			}
		}
		if (minimizeMiniMap == 2)
		{
			overlapsObject = true;
		}
		if (overlapsObject)
		{
			clickOccurring = false;
		}
		return overlapsObject;
	}
	public boolean controlTechTreeClickEvents(boolean clickOccurring)
	{
		String name = "";
		if (rX - techTree.getLocation().x > 0.0121708342 * techTree.getWidth() && rY > 0.129151291 * techTree.getHeight() && rX - techTree.getLocation().x < 0.0559858375 * techTree.getWidth() && rY < 0.210332103 * techTree.getHeight())
		{
			name = "Pottery";
			clickOccurring = false;
		}
		else if (rX - techTree.getLocation().x > 0.012157382 * techTree.getWidth() && rY > 0.4048059149722736 * techTree.getHeight() && rX - techTree.getLocation().x < 0.05570291777188329 * techTree.getWidth() && rY < 0.4879852125693161 * techTree.getHeight())
		{
			name = "Animal Husbandry";
			clickOccurring = false;
		}
		else if (rX - techTree.getLocation().x > 0.01237842617152962 * techTree.getWidth() && rY > 0.5878003696857671 * techTree.getHeight() && rX - techTree.getLocation().x < 0.05570291777188329 * techTree.getWidth() && rY < 0.6691312384473198 * techTree.getHeight())
		{
			name = "Archery";
			clickOccurring = false;
		}
		else if (rX - techTree.getLocation().x > 0.01215738284703802 * techTree.getWidth() && rY > 0.7726432532347505 * techTree.getHeight() && rX - techTree.getLocation().x < 0.05570291777188329 * techTree.getWidth() && rY < 0.8539741219963032 * techTree.getHeight())
		{
			name = "Mining";
			clickOccurring = false;
		}
		System.out.println(name);
		return clickOccurring;
	}
	private void drawLocationDot(Graphics g)
	{
		double width, height;
		if ((farthestDiscoveredEast - farthestDiscoveredWest + 1) / (double)(farthestDiscoveredSouth - farthestDiscoveredNorth + 1) > 4)
		{
			width = (farthestDiscoveredEast - farthestDiscoveredWest + 1);
			height = (farthestDiscoveredEast - farthestDiscoveredWest + 1) / 4.0;
		}
		else
		{
			height = (farthestDiscoveredSouth - farthestDiscoveredNorth + 1);
			width = (farthestDiscoveredSouth - farthestDiscoveredNorth + 1) * 4.0;
		}
		double miniMapSideLength = (miniMap.getHeight() * 2) / (3 * height);
		double newXDistanceIncrementer = miniMapSideLength * Math.sqrt(3);
		double addedXOffset;
		double addedYOffset;
		if (farthestDiscoveredWest % 2 == 1)
		{
			addedXOffset = (farthestDiscoveredWest - 1) / 2 * xDistanceIncrementer;
			addedYOffset = farthestDiscoveredNorth * sideLength * 3 / 2 - sideLength * 3 / 2;
		}
		else
		{
			addedXOffset = (farthestDiscoveredWest) / 2 * xDistanceIncrementer - xDistanceIncrementer / 2;
			addedYOffset = farthestDiscoveredNorth * sideLength * 3 / 2 - sideLength * 3 / 2;
		}
		double newXOffset = xOffset - getWidth() / 2 + addedXOffset;
		if (newXOffset > 0)
		{
			newXOffset = - (Conquer.gameScreenSizeX / 2 * xDistanceIncrementer) + newXOffset;
		}
		double dotLocationX = (- newXOffset) / xDistanceIncrementer * newXDistanceIncrementer + miniMapBox.x;
		double dotLocationY = (- yOffset - addedYOffset + getHeight() / 2) / (2 * sideLength) * 2 * miniMapSideLength + miniMapBox.y;
		g.setColor(new Color(250, 6, 11));
		if (miniMapBox.contains(dotLocationX, dotLocationY))
		{
			g.fillOval((int) dotLocationX - 5, (int) dotLocationY - 5, 10, 10);
		}
		g = null;
	}
	private boolean circleContains(int cX, int cY, int x, int y, int radius)
	{
		return (radius * radius  / 4 > (Math.pow(cX + (radius / 2) - x, 2) + Math.pow(cY + (radius / 2) - y, 2)));
	}
	public Point[] getViablePathBetween(Point startingPoint, Point endingPoint, int viability, int currentMobility, int totalMobility, Unit unit, boolean attacking, int range)
	{
		boolean[][] usedTiles = new boolean[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
		ArrayList<Point> activePoints = new ArrayList<Point>();
		ArrayList<Point> activePointsHolder = new ArrayList<Point>();
		ArrayList<Point> newPathItems = new ArrayList<Point>();
		ArrayList<Integer> activePointsWaitTimers = new ArrayList<Integer>();
		ArrayList<Integer> activePointsWaitTimersHolder = new ArrayList<Integer>();
		Point[][] paths = new Point[1][1];
		paths[0][0] = startingPoint;
		ArrayList<Integer> pathRoots = new ArrayList<Integer>();
		activePoints.add(new Point(startingPoint.x, startingPoint.y));
		activePointsWaitTimers.add(0);
		while (activePoints.size() > 0)
		{
			if (currentMobility == 0)
			{
				currentMobility = totalMobility;
			}
			if (attacking && range == 0)
			{
				System.out.println("Broken");
				break;
			}
			for (int i = 0; i < activePoints.size(); i++)
			{
				if (activePointsWaitTimers.get(i) > 0)
				{
					activePointsHolder.add(activePoints.get(i));
					newPathItems.add(activePoints.get(i));
					pathRoots.add(i);
					activePointsWaitTimersHolder.add(activePointsWaitTimers.get(i) - 1);
				}
				else
				{
					int[][] tiles = getAllTilesWithin(activePoints.get(i).x, activePoints.get(i).y, 1);
					for (int x = 0; x < tiles[0].length; x++)
					{
						if (tiles[0][x] != -1)
						{
							boolean passed = !usedTiles[tiles[0][x]][tiles[1][x]];
							usedTiles[tiles[0][x]][tiles[1][x]] = true;
							if (
									(
											(viability == 0) || 
											(viability == 1 && baseGeographicMap[tiles[0][x]][tiles[1][x]] != baseGeography.mountain) || 
											(viability == 2 && baseGeographicMap[tiles[0][x]][tiles[1][x]] != baseGeography.ocean && baseGeographicMap[tiles[0][x]][tiles[1][x]] != baseGeography.shallowOcean && baseGeographicMap[tiles[0][x]][tiles[1][x]] != baseGeography.mountain) || 
											(viability == 3 && baseGeographicMap[tiles[0][x]][tiles[1][x]] != baseGeography.ocean && baseGeographicMap[tiles[0][x]][tiles[1][x]] != baseGeography.shallowOcean && baseGeographicMap[tiles[0][x]][tiles[1][x]] != baseGeography.mountain && 
												(
													(unit.warrior && warriorMap[tiles[0][x]][tiles[1][x]] == null) || (!unit.warrior && workerMap[tiles[0][x]][tiles[1][x]] == null)
												) && 
												!(warriorMap[tiles[0][x]][tiles[1][x]] != null && warriorMap[tiles[0][x]][tiles[1][x]].player != unit.player) && !(workerMap[tiles[0][x]][tiles[1][x]] != null && workerMap[tiles[0][x]][tiles[1][x]].player != unit.player)
											) ||
											(viability == 4 && baseGeographicMap[tiles[0][x]][tiles[1][x]] != baseGeography.ocean && baseGeographicMap[tiles[0][x]][tiles[1][x]] != baseGeography.shallowOcean && baseGeographicMap[tiles[0][x]][tiles[1][x]] != baseGeography.mountain && 
												(
													(unit.warrior && warriorMap[tiles[0][x]][tiles[1][x]] == null) || (!unit.warrior && workerMap[tiles[0][x]][tiles[1][x]] == null) || (endingPoint.x == tiles[0][x] && endingPoint.y == tiles[1][x])
												) && 
												(!(warriorMap[tiles[0][x]][tiles[1][x]] != null && warriorMap[tiles[0][x]][tiles[1][x]].player != unit.player) || (endingPoint.x == tiles[0][x] && endingPoint.y == tiles[1][x])) && (!(workerMap[tiles[0][x]][tiles[1][x]] != null && workerMap[tiles[0][x]][tiles[1][x]].player != unit.player) || (endingPoint.x == tiles[0][x] && endingPoint.y == tiles[1][x])) 
											)
									)
									&& passed)
							{
								if (tiles[0][x] == endingPoint.x && tiles[1][x] == endingPoint.y)
								{
									Point[] returnPath = new Point[paths[i].length + 1];
									for (int l = 0; l < paths[i].length; l++)
									{
										returnPath[l] = paths[i][l];
									}
									returnPath[returnPath.length - 1] = new Point(endingPoint);
									return returnPath;
								}
								else
								{
									activePointsHolder.add(new Point(tiles[0][x], tiles[1][x]));
									int timer = 0;
									if (currentMobility > 1 && 
											(
													(viability == 3 || viability == 4) && (upperGeographicMap[tiles[0][x]][tiles[1][x]] == upperGeography.hills || upperGeographicMap[tiles[0][x]][tiles[1][x]] == upperGeography.snowHills || upperGeographicMap[tiles[0][x]][tiles[1][x]] == upperGeography.marsh || upperGeographicMap[tiles[0][x]][tiles[1][x]] == upperGeography.forest || upperGeographicMap[tiles[0][x]][tiles[1][x]] == upperGeography.jungle)
											)
										)
									{
										timer = 1;
										newPathItems.add(activePoints.get(i));
									}
									else
									{
										newPathItems.add(new Point(tiles[0][x], tiles[1][x]));
									}
									activePointsWaitTimersHolder.add(timer);
									pathRoots.add(i);
								}
							}
						}
					}
				}
			}
			Point[][] pathHolder = new Point[activePointsHolder.size()][paths[0].length + 1];
			for (int x = 0; x < pathHolder.length; x++)
			{
				for (int y = 0; y < paths[0].length; y++)
				{
					pathHolder[x][y] = paths[pathRoots.get(x)][y];
				}
				pathHolder[x][pathHolder[0].length - 1] = newPathItems.get(x);
			}
			newPathItems.clear();
			paths = pathHolder;
			pathRoots.clear();
			activePoints.clear();
			activePoints.addAll(activePointsHolder);
			activePointsHolder.clear();
			activePointsWaitTimers.clear();
			activePointsWaitTimers.addAll(activePointsWaitTimersHolder);
			activePointsWaitTimersHolder.clear();
			currentMobility--;
			range--;
		}
		return null;
	}
}