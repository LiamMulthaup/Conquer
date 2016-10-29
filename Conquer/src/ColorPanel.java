import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Polygon;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ColorPanel extends JPanel {
	enum baseGeography
	{
		plains, ocean, desert, tundra, oasis, mountain, ice, glacier, shallowOcean;
	}
	enum upperGeography
	{
		hills, forest, snow, jungle, marsh, flat, snowHills;
	}
	enum resourceGeography
	{
		oil, iron, uranium, aluminum, horses, coal,//strategic
		bananas,  fish, stone, cattle, sheep, deer, wheat, //bonus 
		gold, slilver, cotton, spices, pearls, gems, whales, marble, wine, incence, //luxury
		dyes, silk, ivory, furs, sugar;
	}
	baseGeography[][] baseGeographicMap = new baseGeography[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	upperGeography[][] upperGeographicMap = new upperGeography[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	resourceGeography[][] resourceGeographicMap = new resourceGeography[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY];
	vertices[][] riverTracker = new vertices[Conquer.gameScreenSizeX][Conquer.gameScreenSizeY]; 
	int mX, mY;
	double xOffset = 0, yOffset = 0, xOrigin = 0, yOrigin = 0;
	final double sideLength = 65;
	double xDistanceIncrementer = sideLength * Math.sqrt(3);
	public static boolean mousePressed = false, clickOccurring = false, sliding = false;
	int testRows, testColumns;
	boolean checkingifadjacent = false;
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
			snowHillimg = new ImageIcon(this.getClass().getResource("snowHill.jpg")).getImage()
		 	;
	public void generateGeographicalMap()
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
		baseGeographicMap[1][1] = baseGeography.desert;
		riverTracker[1][1].verticesRiverActive[0] = true;
		adjustSimilarRiverVertices(1, 1, 0, -1);
		riverTracker[1][1].verticesRiverActive[1] = true;
		adjustSimilarRiverVertices(1, 1, 1, -1);
		riverTracker[1][1].verticesRiverActive[2] = true;
		adjustSimilarRiverVertices(1, 1, 2, -1);
		riverTracker[1][1].verticesRiverActive[3] = true;
		adjustSimilarRiverVertices(1, 1, 3, -1);
		riverTracker[1][1].verticesRiverActive[4] = true;
		adjustSimilarRiverVertices(1, 1, 4, -1);
		riverTracker[1][1].verticesRiverActive[5] = true;
		adjustSimilarRiverVertices(1, 1, 5, -1);
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
			System.out.println(landPerContinent);
			List<Integer> continentAdjacentRows = new ArrayList<Integer>();
			List<Integer> continentAdjacentColumns = new ArrayList<Integer>();
			while (landPerContinent > 0)
			{
				int biomeSize = 0;
				baseGeography biomeTypeBase = baseGeography.ocean;
				upperGeography biomeTypeUpper = upperGeography.flat;
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
					double chanceOfForest = Math.abs(originPointY - (Conquer.gameScreenSizeY / 2.0)) / (Conquer.gameScreenSizeY / 2.0);
					double chanceOfDesert = 0;
					if ((originPointY > Conquer.gameScreenSizeY / 4 && originPointY < Conquer.gameScreenSizeY * 3 / 8) || (originPointY > 5 * Conquer.gameScreenSizeY / 8 && originPointY < Conquer.gameScreenSizeY / 4 * 3))
					{
						chanceOfDesert = 1;
					}
					else
					{
						chanceOfDesert = 0.05;
					}
					double chanceOfPlains = 0;
					if ((originPointY > Conquer.gameScreenSizeY / 10 && originPointY < Conquer.gameScreenSizeY / 4) || (originPointY > 3 * Conquer.gameScreenSizeY / 4 && originPointY < 9 * Conquer.gameScreenSizeY / 10))
					{
						chanceOfPlains = 1;
					}
					else
					{
						chanceOfPlains = 0.3;
					}
					double chanceOfJungle = 0;
					if (originPointY > 3 * Conquer.gameScreenSizeY / 8 && originPointY < 5 * Conquer.gameScreenSizeY / 8)
					{
						chanceOfJungle = 0.1;
					}
					double chanceOfMountains = 0.07;
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
								biomeTypeUpper = upperGeography.hills;
							}
						}
						if (geographyTypePicker.nextInt(40) == 1 && biomeTypeBase == baseGeography.desert)
						{
							biomeTypeBase = baseGeography.oasis;
						}
						baseGeographicMap[selectedX][selectedY] = biomeTypeBase;
						upperGeographicMap[selectedX][selectedY] = biomeTypeUpper;
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
						/*
						this.repaint();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
					}
					else
					{
						biomeSize = 0;
					}
					
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
					System.out.println("StartingColumn: " + startingColumn);
					System.out.println("StartingRow: " + startingRow);
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
					int islandSize = 1 + geographyTypePicker.nextInt(2);
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
					for (int a = 0; a < islandSize; a++)
					{
						changeAllTilesWithinToShallowOcean(selectedX, selectedY, 2);
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
						}
					}
					break;
				}
			}
		}
		// Ends the Island Generator
		//-----------------------------------------
		
		// ----------------------------------------
		// Begins The River Generator
		System.out.println("River#:" + riverStartPointsX.size());
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
					int baseRiverLength = newRiverLength;
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
	private int[][] getAllTilesWithin(int column, int row, int radius)
	{
		int[][] tiles = new int[2][12 * radius - 6];
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
				tiles[0][6 * (i - 1) + x] = assignedX;
				tiles[1][6 * (i - 1) + x] = assignedY;
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
				tiles[0][6 * (i - 1) + x] = assignedX;
				tiles[1][6 * (i - 1) + x] = assignedY;
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
				tiles[0][6 * (i - 1) + x] = assignedX;
				tiles[1][6 * (i - 1) + x] = assignedY;
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
				tiles[0][6 * (i - 1) + x] = assignedX;
				tiles[1][6 * (i - 1) + x] = assignedY;
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
				tiles[0][6 * (i - 1) + x] = assignedX;
				tiles[1][6 * (i - 1) + x] = assignedY;
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
				tiles[0][6 * (i - 1) + x] = assignedX;
				tiles[1][6 * (i - 1) + x] = assignedY;
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
	private int[] getTopRightTile(int columns, int rows)
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
	private int[] getTopLeftTile(int columns, int rows)
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
	private int[] getRightTile(int columns, int rows)
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
	private int[] getLeftTile(int columns, int rows)
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
	private int[] getBottomLeftTile(int columns, int rows)
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
	private int[] getBottomRightTile(int columns, int rows)
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
		img = image;
		return img;
		
	}
	public void paintComponent(Graphics g)
	{
		xOffset %= (xDistanceIncrementer / 2 * Conquer.gameScreenSizeX); // Clips xOffset and yOffset to reasonable amount (garbage disposal). 
		yOffset %= (sideLength * 3 / 2 * Conquer.gameScreenSizeY);
		super.paintComponent(g); // Runs the super jPanel painComponent method.
		Boolean plusorminus = true; // Determines whether or not to add or subtract the pen starting point.
		double xStarter = 0; // Starting Point for the pen along the x-axis.
		for (double y = - (6 * sideLength) + (yOffset % (3 * sideLength)); y < this.getHeight(); y += (3 * sideLength / 2))
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
			
			int baseColumn = getColumns((int)(xStarter - xDistanceIncrementer + (xOffset % xDistanceIncrementer)), (int) (y + sideLength));
			int baseRow = getRows((int)(xStarter - xDistanceIncrementer + (xOffset % xDistanceIncrementer)), (int) (y + sideLength)); // Establishes base values for the what row and column each tile is in for later.
 		for (double x = xStarter - (2 * xDistanceIncrementer) + (xOffset % xDistanceIncrementer); x < this.getWidth() + xDistanceIncrementer; x += xDistanceIncrementer)
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
			Image img = oceanimg;
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
				}
			}
			else if (selectedTileBase == baseGeography.oasis)
			{
				img = oasisimg;
			}
			// Determines what image to use based on tile type.
			
			Polygon hex = new Polygon();
			hex.addPoint(hexPointsX[0], hexPointsY[0]);
			hex.addPoint(hexPointsX[1], hexPointsY[1]);
			hex.addPoint(hexPointsX[2], hexPointsY[2]);
			hex.addPoint(hexPointsX[3], hexPointsY[3]);
			hex.addPoint(hexPointsX[4], hexPointsY[4]);
			hex.addPoint(hexPointsX[5], hexPointsY[5]);
			g.setColor(new Color(150, 150, 150));
			g.drawPolygon(hex);
			// Draws Hexagon.
			
			for (int i = 0; i < 6; i++)
			{
				int adjustedColumn = baseColumn - 2;
				if (adjustedColumn < 0) { adjustedColumn += Conquer.gameScreenSizeX; }
				if (riverTracker[adjustedColumn][baseRow].verticesRiverActive[i] == true && 
						riverTracker[adjustedColumn][baseRow].verticesRiverActive[(i + 1) % 6] == true && 
						(riverTracker[adjustedColumn][baseRow].riverID[i][0] == riverTracker[adjustedColumn][baseRow].riverID[(i + 1) % 6][0] || 
						riverTracker[adjustedColumn][baseRow].riverID[i][1] == riverTracker[adjustedColumn][baseRow].riverID[(i + 1) % 6][1] || 
						riverTracker[adjustedColumn][baseRow].riverID[i][0] == riverTracker[adjustedColumn][baseRow].riverID[(i + 1) % 6][1] || 
						riverTracker[adjustedColumn][baseRow].riverID[i][1] == riverTracker[adjustedColumn][baseRow].riverID[(i + 1) % 6][0]))                              
				{
					g.setColor(new Color(102, 144, 153));
					//g.setColor(new Color(75, 75, 160));
					drawThickLine(hexPointsX[i], hexPointsY[i], hexPointsX[(i + 1) % 6], hexPointsY[(i + 1) % 6], 2.5, g); 
				}
			}
			g.drawImage(img, hexPointsX[4], hexPointsY[5], null); // Draws Tile image.
			baseColumn += 2;
			baseColumn %= Conquer.gameScreenSizeX;
			
			}
		}
		addMouseListener(new PanelListener());
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
	public class PanelListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			sliding = false;
			mX = e.getX();
			mY = e.getY();
			Conquer.dragVelocityTim = 10;
			Conquer.savedDragX = mX;
			Conquer.savedDragY = mY;
			mousePressed = true;
			clickOccurring = true;
		}
		public void mouseReleased(MouseEvent e)
		{
			if (mousePressed == true)
			{
				xOrigin = xOffset;
				yOrigin = yOffset;
				sliding = true;
			}
			mousePressed = false;
		}
	}
	public int getRows(int x, int y)
	{
		int rows;
		for(rows = - 5; y - yOffset > (Math.abs((Math.abs(x - xOffset + (xDistanceIncrementer / 2 * rows)) % xDistanceIncrementer) - (xDistanceIncrementer / 2)) / Math.sqrt(3)) + (3 * sideLength / 2 * rows); rows++);
		rows = ((rows % Conquer.gameScreenSizeY) + Conquer.gameScreenSizeY) % Conquer.gameScreenSizeY;
		return rows;
	}
	public int getColumns(int x, int y)
	{
		int columns;
		for(columns = - (Conquer.gameScreenSizeX + 5) ; x - xOffset > (Math.sqrt(3) * Math.abs( ((Math.abs(y - yOffset + (sideLength / 2) + (3 * sideLength / 2 * columns)) % (3 * sideLength)) - (3 * sideLength / 2) ) ) / 2) - (Math.sqrt(3) * Math.abs( ((Math.abs(y - yOffset + (sideLength / 2) + (3 * sideLength / 2 * columns)) % (sideLength)) - (sideLength / 2) ) ) / 2) + (xDistanceIncrementer / 2 * columns); columns++);
		columns = ((columns % Conquer.gameScreenSizeX) + Conquer.gameScreenSizeX) % Conquer.gameScreenSizeX;
		return columns;
	}
	public void mouseTimEvent()
	{
		int rows = getRows(mX, mY);
		int columns = getColumns(mX, mY);
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
	}
}
