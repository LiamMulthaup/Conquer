import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


import javax.swing.JFrame;

import ui.Button;
import ui.CircleButton;
import ui.Container;
import ui.Control;
import ui.Label;
import ui.ListBox;
import ui.RadioButton;
import ui.TextBox;

public class Conquer {
	public static int gameScreenSizeX = 800, gameScreenSizeY = 200, dragVelocityTim = 10, savedDragX, savedDragY, redrawTim = 0, continents = 6, islands = 60, landMassPercentage = 40, numberOfAIs = 40, distanceBetweenPlayers = 30;
	public static double dragEndAngle, dragEndVelocity;
	public static boolean unitMenuOpen, movementOpen, scheduleMovement;
	static Container mapContainer = new Container();
	static Container civContainer = new Container();
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static void main(String[] args) 
	{
		Timer tim = new Timer();
		JFrame Screen = new JFrame();
		Screen.setTitle("Conquer");
		Screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Screen.setUndecorated(true);
		Screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TitlePage titlePage = new TitlePage();
		Screen.add(titlePage);
		titlePage.setBackground(new Color(0, 0, 0));
		Screen.setVisible(true);
		Screen.setIconImage(titlePage.logo);
		Graphics g = titlePage.getGraphics();
		titlePage.paintComponent(g);
		titlePage.addMouseListener();
		Screen.addKeyListener(new KeyEventListener(Screen, tim));
		tim.scheduleAtFixedRate(new TimerTask()
		{
			public void run()
			{
				if (Screen.isVisible())
				{
					if (TitlePage.timmy < 700)
					{
						if (titlePage.clickOccurring)
						{
							Screen.setVisible(false);
							try {
								startNewGame(Screen, "Admin Game", null);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						titlePage.repaint();
						TitlePage.timmy++;
					}
					else
					{
						if (titlePage.menuButton == null)
						{
							titlePage.menuButton = new Label("< Menu", titlePage, new Point(titlePage.getWidth() / 2 + 200, (int) (650 * screenSize.getHeight() / 768)));
							titlePage.menuButton.setFont(new Font("Rockwell", Font.BOLD, 20));
							titlePage.menuButton.color = new Color(250, 250, 250);
							titlePage.menuButton.id = "Menu Button";
							titlePage.addControlToHandler(titlePage.menuButton, Screen);
						}
						if (titlePage.clickOccurring && titlePage.menuOpen == true)
						{
							if (titlePage.userHasBegunGame == true && titlePage.newGame.contains(TitlePage.mX, TitlePage.mY))
							{
								if (titlePage.textBox == null)
								{
									titlePage.textBox = new TextBox(100, 20, titlePage, new Point(titlePage.getWidth() / 2, 160), Screen);
									titlePage.hugeSizeButton = new RadioButton("Huge (800 x 400)", titlePage, new Point(titlePage.getWidth() / 2, 205));
									titlePage.hugeSizeButton.opaqueBubble = false;
									titlePage.hugeSizeButton.choiceLocked = true;
									titlePage.hugeSizeButton.setFont(new Font("Rockwell", Font.BOLD, 20));
									titlePage.largeSizeButton = new RadioButton("Large (600 x 300)", titlePage, new Point(titlePage.getWidth() / 2, 225));
									titlePage.largeSizeButton.opaqueBubble = false;
									titlePage.largeSizeButton.choiceLocked = true;
									titlePage.largeSizeButton.setFont(new Font("Rockwell", Font.BOLD, 20));
									titlePage.normalSizeButton = new RadioButton("Normal (400 x 200)", titlePage, new Point(titlePage.getWidth() / 2, 245));
									titlePage.normalSizeButton.opaqueBubble = false;
									titlePage.normalSizeButton.choiceLocked = true;
									titlePage.normalSizeButton.setFont(new Font("Rockwell", Font.BOLD, 20));
									titlePage.normalSizeButton.selected = true;
									titlePage.smallSizeButton = new RadioButton("Small (300 x 150)", titlePage, new Point(titlePage.getWidth() / 2, 265));
									titlePage.smallSizeButton.opaqueBubble = false;
									titlePage.smallSizeButton.choiceLocked = true;
									titlePage.smallSizeButton.setFont(new Font("Rockwell", Font.BOLD, 20));
									titlePage.tinySizeButton = new RadioButton("Tiny (200 x 100)", titlePage, new Point(titlePage.getWidth() / 2, 285));
									titlePage.tinySizeButton.opaqueBubble = false;
									titlePage.tinySizeButton.choiceLocked = true;
									titlePage.tinySizeButton.setFont(new Font("Rockwell", Font.BOLD, 20));
									titlePage.quickBattleSizeButton = new RadioButton("Quick Battle (100 x 50)", titlePage, new Point(titlePage.getWidth() / 2, 305));
									titlePage.quickBattleSizeButton.opaqueBubble = false;
									titlePage.quickBattleSizeButton.choiceLocked = true;
									titlePage.quickBattleSizeButton.setFont(new Font("Rockwell", Font.BOLD, 20));
									titlePage.addControlToHandler(titlePage.hugeSizeButton, Screen);
									titlePage.hugeSizeButton.id = "Huge Size";
									titlePage.addControlToHandler(titlePage.largeSizeButton, Screen);
									titlePage.largeSizeButton.id = "Large Size";
									titlePage.addControlToHandler(titlePage.normalSizeButton, Screen);
									titlePage.normalSizeButton.id = "Normal Size";
									titlePage.addControlToHandler(titlePage.smallSizeButton, Screen);
									titlePage.smallSizeButton.id = "Small Size";
									titlePage.addControlToHandler(titlePage.tinySizeButton, Screen);
									titlePage.tinySizeButton.id = "Tiny Size";
									titlePage.addControlToHandler(titlePage.quickBattleSizeButton, Screen);
									titlePage.quickBattleSizeButton.id = "Quick Battle Size";
									titlePage.playersPromptLabel = new Label("Enter # of Players:               (Max 22)", titlePage, new Point(titlePage.getWidth() / 2 - 280, 340));
									titlePage.playersPromptLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
									titlePage.playersPromptLabel.color = new Color(180, 180, 180);
									titlePage.playersTextBox = new TextBox(100, 20, titlePage, new Point(titlePage.getWidth() / 2, 340), Screen);
									titlePage.playersTextBox.acceptsOnlyNumbers = true;
									titlePage.playersTextBox.acceptsNumbersBeneath = 23;
									titlePage.AIsPromptLabel = new Label("Enter # of AI's:", titlePage, new Point(titlePage.getWidth() / 2 - 225, 380));
									titlePage.AIsPromptLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
									titlePage.AIsPromptLabel.color = new Color(180, 180, 180);
									titlePage.AIsTextBox = new TextBox(100, 20, titlePage, new Point(titlePage.getWidth() / 2, 380), Screen);
									titlePage.AIsTextBox.acceptsOnlyNumbers = true;
									titlePage.AIsTextBox.acceptsNumbersBeneath = 1;
									titlePage.playersTextBox.id = "playersTextBox";
									titlePage.addControlToHandler(titlePage.playersTextBox, Screen);
									titlePage.nextButton = new Button(" Next", 70, 30, titlePage, new Point(titlePage.getWidth() / 2 + 20, (int) (650 * screenSize.getHeight() / 768) - 60));
									titlePage.backButton = new Button(" Back", 70, 30, titlePage, new Point(titlePage.getWidth() / 2 - 70, (int) (650 * screenSize.getHeight() / 768) - 60));
									titlePage.nextButton.setFont(new Font("Rockwell", Font.ITALIC, 25));
									titlePage.backButton.setFont(new Font("Rockwell", Font.ITALIC, 25));
									titlePage.backButton.borderColor = new Color(180, 180, 180);
									titlePage.backButton.color = new Color(180, 180, 180);
									titlePage.backButton.backgroundColor = new Color(0, 0, 0);
									titlePage.nextButton.borderColor = new Color(180, 180, 180);
									titlePage.nextButton.color = new Color(180, 180, 180);
									titlePage.nextButton.backgroundColor = new Color(0, 0, 0);
									titlePage.nextButton.id = "Next Button";
									titlePage.addControlToHandler(titlePage.nextButton, Screen);
									titlePage.backButton.id = "Back Button";
									titlePage.addControlToHandler(titlePage.backButton, Screen);
									titlePage.textBox.id = "Text Box";
									titlePage.AIsTextBox.id = "AIsTextBox";
									titlePage.addControlToHandler(titlePage.textBox, Screen);
									titlePage.addControlToHandler(titlePage.AIsTextBox, Screen);
									titlePage.finishButton = new Button(" Finish", 75, 30, titlePage, new Point(titlePage.getWidth() / 2 + 110, (int) (650 * screenSize.getHeight() / 768) - 60));
									titlePage.finishButton.setFont(new Font("Rockwell", Font.ITALIC, 25));
									titlePage.finishButton.borderColor = new Color(180, 180, 180);
									titlePage.finishButton.color = new Color(180, 180, 180);
									titlePage.finishButton.backgroundColor = new Color(0, 0, 0);
									titlePage.finishButton.id = "Finish Button";
									titlePage.addControlToHandler(titlePage.finishButton, Screen);
								}
								titlePage.nextButton.setVisible(true);
								titlePage.AIsPromptLabel.setVisible(true);
								titlePage.AIsTextBox.setVisible(true);
								titlePage.playersPromptLabel.setVisible(true);
								titlePage.playersTextBox.setVisible(true);
								titlePage.menuOpen = false;
								titlePage.startNewGame = true;
								titlePage.clickOccurring = false;
								titlePage.textBox.setVisible(true);
								titlePage.hugeSizeButton.setVisible(true);
								titlePage.largeSizeButton.setVisible(true);
								titlePage.normalSizeButton.setVisible(true);
								titlePage.smallSizeButton.setVisible(true);
								titlePage.tinySizeButton.setVisible(true);
								titlePage.quickBattleSizeButton.setVisible(true);
								titlePage.textBox.setText("");
								mapContainer.add(titlePage.hugeSizeButton);
								mapContainer.add(titlePage.largeSizeButton);
								mapContainer.add(titlePage.normalSizeButton);
								mapContainer.add(titlePage.smallSizeButton);
								mapContainer.add(titlePage.tinySizeButton);
								mapContainer.add(titlePage.quickBattleSizeButton);
								titlePage.menuButton.setVisible(true);
							}
							else if (titlePage.userHasBegunGame == true && titlePage.loadGame.contains(TitlePage.mX, TitlePage.mY))
							{
								if (titlePage.loadGameListBox == null)
								{
									titlePage.loadGameListBox = new ListBox(300, 400, titlePage, new Point(titlePage.getWidth() / 2 - 150, 170));
									titlePage.loadGameLabel = new Label("Load Game", titlePage, new Point(titlePage.getWidth() / 2 - 200, 70));
									titlePage.loadGameLabel.setFont(new Font("TimesRoman", Font.BOLD, 80));
									titlePage.loadGameLabel.color = Color.white;
									titlePage.loadGameButton = new Button(" Load", 70, 30, titlePage, new Point(titlePage.getWidth() / 2 - 50, 590));
									titlePage.loadGameButton.backgroundColor = new Color(109, 115, 169);
									titlePage.loadGameButton.setFont(new Font("TimesRoman", Font.PLAIN, 26));
									titlePage.loadGameButton.id = "Load Game";
									titlePage.addControlToHandler(titlePage.loadGameButton, Screen);
								}
								titlePage.loadGameLabel.setVisible(true);
								titlePage.loadGameButton.setVisible(true);
								titlePage.menuButton.setVisible(true);
								titlePage.menuOpen = false;
								titlePage.startLoadGame = true;
								titlePage.clickOccurring = false;
								titlePage.loadGameListBox.setVisible(true);
								titlePage.loadGameListBox.clearItems();
								File conquerGames = new File("Conquer Games");
								File[] conquerFiles = conquerGames.listFiles();
								for (int i = 0; i < conquerFiles.length; i++)
								{
									titlePage.loadGameListBox.add(conquerFiles[i].getName());
								}
							}
							else if (titlePage.userHasBegunGame == true && titlePage.settings.contains(TitlePage.mX, TitlePage.mY))
							{
								titlePage.menuOpen = false;
								titlePage.settingsOpen = true;
								titlePage.clickOccurring = false;
							}
						}
						if (titlePage.clickOccurring && titlePage.startLoadGame)
						{
							
						}
						if (titlePage.clickOccurring && titlePage.settingsOpen)
						{
							
						}
						if (titlePage.clickOccurring && titlePage.startNewGame)
						{
							
						}
						if (titlePage.clickOccurring && titlePage.userHasBegunGame == false)
						{
							titlePage.userHasBegunGame = true;
							TitlePage.timmy = 1300;
							titlePage.clickOccurring = false;
						}
						titlePage.repaint();
						if (TitlePage.timmy < 100000)
						{
							TitlePage.timmy++;
						}
					}
				}
				Control.controlClickEvents(titlePage, TitlePage.mX, TitlePage.mY, titlePage.clickOccurring);
				titlePage.clickOccurring = false;
			}
		} , 10, 10);
	}
	public static void startNewGame(JFrame titlePage, String gameName, Player[] players) throws IOException
	{
		Timer tim = new Timer();
		JFrame Screen = new JFrame();
		Screen.setTitle("Conquer");
		Screen.setSize(1000, 700);
		Screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		Screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ColorPanel background = new ColorPanel();
		background.fileName = gameName;
		File conquerFile = new File("Conquer Games/" + gameName);
		boolean shouldRotateTurn = false;
		int[][] startingPoints = null;
		if (!conquerFile.exists())
		{
			if (players == null)
			{
				players = new Player[6];
				players[0] = new Player(ColorPanel.worldCivilizations.Russia);
				players[1] = new Player(ColorPanel.worldCivilizations.America);
				players[2] = new Player(ColorPanel.worldCivilizations.England);
				players[3] = new Player(ColorPanel.worldCivilizations.Egypt);
				players[4] = new Player(ColorPanel.worldCivilizations.Rome);
				players[5] = new Player(ColorPanel.worldCivilizations.Mongolia);
			}
			shouldRotateTurn = true;
			background.players = players;
			startingPoints = background.generateGeographicalMap();
			File gameFile = new File("Conquer Games/" + gameName);
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
			background.saveGame(gameName);
		}
		else
		{
			background.loadGame();
			if (gameName.equals("Admin Game"))
			{
				background.fogOfWar[0][0] = 1;
				background.players = new Player[6];
				background.players[0] = new Player(ColorPanel.worldCivilizations.Russia);
				background.players[1] = new Player(ColorPanel.worldCivilizations.America);
				background.players[2] = new Player(ColorPanel.worldCivilizations.England);
				background.players[3] = new Player(ColorPanel.worldCivilizations.Egypt);
				background.players[4] = new Player(ColorPanel.worldCivilizations.Rome);
				background.players[5] = new Player(ColorPanel.worldCivilizations.Mongolia);
				background.players[0].farthestDiscoveredEast = gameScreenSizeX;
				background.players[0].farthestDiscoveredWest = 0;
				background.players[0].farthestDiscoveredSouth = gameScreenSizeY;
				background.players[0].farthestDiscoveredNorth = 0;
				background.players[1].farthestDiscoveredEast = gameScreenSizeX;
				background.players[1].farthestDiscoveredWest = 0;
				background.players[1].farthestDiscoveredSouth = gameScreenSizeY;
				background.players[1].farthestDiscoveredNorth = 0;
				background.players[2].farthestDiscoveredEast = gameScreenSizeX;
				background.players[2].farthestDiscoveredWest = 0;
				background.players[2].farthestDiscoveredSouth = gameScreenSizeY;
				background.players[2].farthestDiscoveredNorth = 0;
				background.players[3].farthestDiscoveredEast = gameScreenSizeX;
				background.players[3].farthestDiscoveredWest = 0;
				background.players[3].farthestDiscoveredSouth = gameScreenSizeY;
				background.players[3].farthestDiscoveredNorth = 0;
				background.players[4].farthestDiscoveredEast = gameScreenSizeX;
				background.players[4].farthestDiscoveredWest = 0;
				background.players[4].farthestDiscoveredSouth = gameScreenSizeY;
				background.players[4].farthestDiscoveredNorth = 0;
				background.players[5].farthestDiscoveredEast = gameScreenSizeX;
				background.players[5].farthestDiscoveredWest = 0;
				background.players[5].farthestDiscoveredSouth = gameScreenSizeY;
				background.players[5].farthestDiscoveredNorth = 0;
				background.workerMap[175][91] = new Settler(background, 175, 91, background.players[1]);
				background.players[1].units.add(background.workerMap[175][91]);
				background.warriorMap[175][91] = new Warrior(background, 175, 91, background.players[1]);
				background.warriorMap[175][91].strength = 60;
				background.players[1].units.add(background.warriorMap[175][91]);
				background.warriorMap[176][90] = new Warrior(background, 176, 90, background.players[2]);
				background.warriorMap[176][90].strength = 30;
				background.players[2].units.add(background.warriorMap[176][90]);
				background.improvementMap[1][1] = ColorPanel.improvements.pasture;
				background.resourceGeographicMap[1][1] = ColorPanel.resourceGeography.horses;
				background.baseGeographicMap[1][1] = ColorPanel.baseGeography.plains;
				background.upperGeographicMap[1][1] = ColorPanel.upperGeography.flat;
			}
		}
		Screen.add(background);
		background.setBackground(new Color(100, 100, 100));
		Screen.setVisible(true);
		background.addMouseListener();
		background.horsePastureimg = background.clipImages(background.horsePastureimg);
		background.sheepPastureimg = background.clipImages(background.sheepPastureimg); background.horseimg = background.clipImages(background.horseimg);
		background.aluminumMineimg = background.clipImages(background.aluminumMineimg); background.sheepimg = background.clipImages(background.sheepimg);
		background.cattleimg = background.clipImages(background.cattleimg); background.cattlePastureimg = background.clipImages(background.cattlePastureimg);
		background.farmimg = background.clipImages(background.farmimg); background.coalMineimg = background.clipImages(background.coalMineimg);
		background.oceanimg = background.clipImages(background.oceanimg); background.desertimg = background.clipImages(background.desertimg);
		background.forestimg = background.clipImages(background.forestimg); background.plainsimg = background.clipImages(background.plainsimg);
		background.desertHillimg = background.clipImages(background.desertHillimg); background.plainsHillimg = background.clipImages(background.plainsHillimg);
		background.shallowsimg = background.clipImages(background.shallowsimg); background.mountainimg = background.clipImages(background.mountainimg);
		background.jungleimg = background.clipImages(background.jungleimg); background.marshimg = background.clipImages(background.marshimg);
		background.tundraHillimg = background.clipImages(background.tundraHillimg); background.tundraimg = background.clipImages(background.tundraimg);
		background.snowimg = background.clipImages(background.snowimg); background.snowHillimg = background.clipImages(background.snowHillimg);
		background.oasisimg = background.clipImages(background.oasisimg); background.glacierimg = background.clipImages(background.glacierimg);
		background.cattleimg = background.clipImages(background.cattleimg); background.furTreeimg = background.clipImages(background.furTreeimg);
		background.cloudimg = background.clipImages(background.cloudimg);
		Graphics g = background.getGraphics();
		background.paintMainMap();
		background.menuButton = new CircleButton("||", 40, background, new Point(background.getWidth() - 40, 0));
		background.menuButton.setFont(new Font("Verdana", Font.BOLD, 27));
		background.menuButton.setVisible(true);
		background.menuButton.id = "Menu Button";
		background.techButton = new CircleButton("T", 40, background, new Point(5, 5));
		background.techButton.stringOffsetX = 5;
		background.techButton.stringOffsetY = 5;
		background.techButton.setFont(new Font("Verdana", Font.BOLD, 30));
		background.techButton.backgroundColor = new Color(82, 103, 255);
		background.techButton.borderColor = new Color(82, 103, 255);
		background.techButton.antialiasing = true;
		background.techButton.setVisible(true);
		background.techButton.id = "Tech Button"; 
		background.socialButton = new CircleButton("S", 40, background, new Point(55, 5));
		background.socialButton.stringOffsetX = 3;
		background.socialButton.stringOffsetY = 3;
		background.socialButton.setFont(new Font("Verdana", Font.BOLD, 30));
		background.socialButton.backgroundColor = new Color(82, 103, 255);
		background.socialButton.borderColor = new Color(82, 103, 255);
		background.socialButton.antialiasing = true;
		background.socialButton.setVisible(true);
		background.socialButton.id = "Social Button"; 
		background.turnRotationButton = new Button("                    Next turn", background.miniMap.getWidth() + 15, 40, background, new Point(0, 0));
		background.turnRotationButton.backgroundColor = new Color(230, 230, 230);
		background.turnRotationButton.borderColor = new Color(181, 184, 180);
		background.turnRotationButton.color = new Color(181, 184, 180);
		background.turnRotationButton.setFont(new Font("Rockwell", Font.BOLD, 35));
		background.turnRotationButton.id = "Rotate Turn";
		background.turnRotationButton.setVisible(true);
		background.savedGameTextBox = new TextBox(160, 30, background, new Point(0, 80), Screen);
		background.addToControlListener(background.menuButton);
		background.addToControlListener(background.techButton);
		background.addToControlListener(background.socialButton);
		background.addToControlListener(background.turnRotationButton);
		Timer mapRefreshTim = new Timer();
		if (shouldRotateTurn)
		{
			background.setUpPlayers(startingPoints);
			background.rotateTurn();
		}
		if (gameName.equals("Admin Game"))
		{
			background.rotateTurn();
		}
		background.developeMiniMap();
		mapRefreshTim.scheduleAtFixedRate(new TimerTask()
		{
			double xOffset, yOffset;
			int it = 0;
			public void run() 
			{
				if (Math.abs(xOffset - background.xOffset) > 250 || (Math.abs(yOffset - background.yOffset) > 250))
				{
					it++;
					System.out.println(it);
					xOffset = background.xOffset;
					yOffset = background.yOffset;
					background.paintMainMap();
					it--;
				}
			}
		}, 10, 10);
		background.paintComponent(g);
		g.dispose();
		g = null;
		Screen.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) 
		    {
		    	Control.disposeAll(background);
		    	Unit.disposeAll();
		    	Screen.removeAll();
		    	Screen.dispose();
		        titlePage.setVisible(true);
		        tim.purge();
		        mapRefreshTim.cancel();
		        mapRefreshTim.purge();
				ColorPanel.clickOccurring = false;
				ColorPanel.mousePressed = false;
				tim.cancel();
		    }
		});
		tim.scheduleAtFixedRate(new TimerTask()
				{
					public void run() 
					{
						if (background.isVisible() == false)
						{
							Control.disposeAll(background);
					    	Unit.disposeAll();
					    	Screen.removeAll();
					    	Screen.dispose();
					        titlePage.setVisible(true);
					        tim.purge();
					        mapRefreshTim.cancel();
					        mapRefreshTim.purge();
							ColorPanel.clickOccurring = false;
							ColorPanel.mousePressed = false;
							tim.cancel();
						}
						if (background.techTreeOpen && ColorPanel.clickOccurring)
						{
							background.techOriginX = background.techTree.getLocation().x - background.mX + 2;
						}
						if (background.techTreeOpen && ColorPanel.mousePressed)
						{
							background.techTree.setLocation(new Point(background.techOriginX + MouseInfo.getPointerInfo().getLocation().x - background.getLocationOnScreen().x, 0));
							if (background.techTree.getLocation().x > 0)
							{
								background.techTree.setLocation(new Point(0, 0));
							}
							else if (background.techTree.getLocation().x < - background.techTree.getWidth() + background.getWidth())
							{
								background.techTree.setLocation(new Point(- background.techTree.getWidth() + background.getWidth(), 0));
							}
							background.repaint();
						}
						boolean ObjectOverlap = false;
						if (ColorPanel.mousePressed == true && background.menuOpen == false && background.techTreeOpen == false && background.productionOpen == false)
						{
							ObjectOverlap = background.anyObjectContains(background.mX, background.mY);
						}
						if (ObjectOverlap == false)
						{
							if (ColorPanel.clickReleaseOccurring)
							{
								if (background.techTreeOpen)
								{
									ColorPanel.clickReleaseOccurring = background.controlTechTreeClickEvents(ColorPanel.clickReleaseOccurring);
								}
								if (!ColorPanel.clickReleaseOveride)
								{
									ColorPanel.clickReleaseOccurring = background.controlCityTagClickEvents(background.rX, background.rY, ColorPanel.clickReleaseOccurring);
									ColorPanel.clickReleaseOccurring = Unit.controlClickEvents(background.rX, background.rY, ColorPanel.clickReleaseOccurring);
									if (unitMenuOpen && !movementOpen)
									{
										if (ColorPanel.clickReleaseOccurring && Unit.selected != null)
										{
											Unit.selected.clearButtons();
											Unit.selected = null;
											unitMenuOpen = false;
										}
									}
									else if (movementOpen)
									{
										scheduleMovement = true;
									}
								}
								ColorPanel.clickReleaseOveride = false;
								ColorPanel.clickReleaseOccurring = false;
							}
							if (ColorPanel.clickOccurring == true)
							{
								background.mouseTimEvent();
								ColorPanel.clickOccurring = Control.controlClickEvents(background, background.mX, background.mY, ColorPanel.clickOccurring);
								ColorPanel.clickReleaseOveride = !ColorPanel.clickOccurring;
								ColorPanel.clickOccurring = false;
							}
							if (ColorPanel.mousePressed == true && background.menuOpen == false && background.techTreeOpen == false && background.productionOpen == false)
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
						else
						{
							ColorPanel.clickReleaseOveride = ObjectOverlap;
							background.repaint();
						}
					}
				}, 10, 10);
	}
}
