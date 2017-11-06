import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.Button;
import ui.Control;
import ui.Label;
import ui.ListBox;
import ui.RadioButton;
import ui.TextBox;
import ui.ControlHandler;


public class TitlePage extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public boolean mousePressed = false, clickOccurring;
		public static int mY, mX, timmy = 0;
		Image planetimg = new ImageIcon(this.getClass().getResource("planet.png")).getImage();
		Image spaceimg = new ImageIcon(this.getClass().getResource("space.jpg")).getImage();
		Image logo = new ImageIcon(this.getClass().getResource("logo.png")).getImage();
		boolean userHasBegunGame = false;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		boolean menuOpen = true;
		Rectangle newGame;
		boolean startNewGame = false;
		Rectangle loadGame;
		boolean startLoadGame = false;
		Rectangle settings;
		boolean settingsOpen = false;
		boolean firstPage = true;
		RadioButton hugeSizeButton, largeSizeButton, normalSizeButton, smallSizeButton, tinySizeButton, quickBattleSizeButton, 
		radioGreece, radioSonghai, radioRome, radioGermany, radioRussia, radioPersia, radioEngland, 
		radioIndia, radioMongolia, radioDenmark, radioArabia, radioIroquois, radioAztec, radioFrance, radioJapan, 
		radioInca, radioEgypt, radioSiam, radioKorea, radioOttoman, radioAmerica, radioChina, radioRandom;
		TextBox textBox, playersTextBox, AIsTextBox;
		Label civilizationListLabel, menuButton, loadGameLabel, newGamePlayerLabel, playersPromptLabel, AIsPromptLabel, worldLeaderLabel;
		Button loadGameButton, nextButton, backButton, finishButton;
		ListBox loadGameListBox;
		titlePageControlHandler controlHandler = new titlePageControlHandler();
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			if (timmy < 200)
			{
				final int pixelSize = 10;
				for (int y = 0; y < this.getHeight(); y += pixelSize)
				{
					for (int x = 0; x < this.getWidth(); x += pixelSize)
					{
						int R = 0;
						int G = 0;
						int B = 0;
						final int speed = 30;
						final int baseBrightness = 255, baseOffset = -1000;
						final double rippleLength = 0.3;
						if (baseBrightness - rippleLength * Math.abs((x - baseOffset) - (timmy * speed)) > -1 && baseBrightness - rippleLength * Math.abs((x - baseOffset) - (timmy * speed)) < baseBrightness + 1)
						{
							R = (int) (0.3 * (baseBrightness - rippleLength * Math.abs((x - baseOffset) - (timmy * speed))));
							G = (int) (0.3 * (baseBrightness - rippleLength * Math.abs((x - baseOffset) - (timmy * speed))));
							B = (int) ((baseBrightness - rippleLength * Math.abs((x - baseOffset) - (timmy * speed))));
						}
						g.setColor(new Color(R, G, B));
						g.fillRect(x, y, pixelSize, pixelSize);
					}	
				}
				g.setFont(new Font("Rockwell", Font.BOLD, 150));
				g.setColor(new Color(0, 0, 0));
				g.drawString("AIM", this.getWidth() / 2 - 150, this.getHeight() / 2 + 50);
			}
			else if (timmy > 200 && timmy < 600)
			{
				g.setFont(new Font("Book Antiqua Bold", Font.BOLD, 100));
				g.setColor(new Color(245, 245, 0));
				if (timmy < 300)
				{
					((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
							(float) ((timmy - 200) / 100.0)));
				}
				if (timmy > 350 && timmy <= 450)
				{
					((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
			              (float) ((timmy - 350) / 100.0)));
					g.drawImage(spaceimg, 0, 0, (int)(spaceimg.getWidth(null) * 1.5 * screenSize.getWidth() / 1366), (int) (spaceimg.getHeight(null) * 1.5 * screenSize.getWidth() / 1366), null);
					((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				              (float) (1)));
				}
				if (timmy > 450)
				{
					g.drawImage(spaceimg, 0, 0, (int)(spaceimg.getWidth(null) * 1.5 * screenSize.getWidth() / 1366), (int) (spaceimg.getHeight(null) * 1.5 * screenSize.getWidth() / 1366), null);
				}
				if (timmy > 500)
				{
					((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				              (float) (1)));
					g.drawImage(spaceimg, 0, 0, (int)(spaceimg.getWidth(null) * 1.5 * screenSize.getWidth() / 1366), (int) (spaceimg.getHeight(null) * 1.5 * screenSize.getWidth() / 1366), null);
					((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				              (float) ((600 - timmy) / 100.0)));
				}
				g.drawString("Conquer", this.getWidth() / 2 - 200, this.getHeight() / 2);
			}
			else if (timmy >= 600 && timmy <= 700)
			{
				g.drawImage(spaceimg, 0, 0, (int)(spaceimg.getWidth(null) * 1.5 * screenSize.getWidth() / 1366), (int) (spaceimg.getHeight(null) * 1.5 * screenSize.getWidth() / 1366), null);

			}
			if (timmy > 700)
			{
				if (800 - (Math.sqrt(timmy - 700) * 45) > -80)
				{
					g.drawImage(spaceimg, 0, 50 - (int) (Math.sqrt(timmy - 700) * 45), (int) (spaceimg.getWidth(null) * 1.5 * screenSize.getWidth() / 1366), (int) (spaceimg.getHeight(null) * 1.5 * screenSize.getWidth() / 1366), null);
					g.drawImage(planetimg, 0, 800 - (int) (Math.sqrt(timmy - 700) * 45), (int) (planetimg.getWidth(null) * screenSize.getWidth() / 1366), (int) (planetimg.getHeight(null) * screenSize.getWidth() / 1366), null);
				}
				else
				{
					if (userHasBegunGame)
					{
						if (timmy > 1300)
						{
							g.drawImage(spaceimg, 0,  - 830, (int) (spaceimg.getWidth(null) * 1.5 * screenSize.getWidth() / 1366), (int) (spaceimg.getHeight(null) * 1.5 * screenSize.getWidth() / 1366), null);
							g.drawImage(planetimg, 0, -80, (int) (planetimg.getWidth(null) * screenSize.getWidth() / 1366), (int) (planetimg.getHeight(null) * screenSize.getWidth() / 1366), null);
							if (timmy < 1393)
							{
								((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
							              (float) ((timmy - 1300) / 100.0)));
							}
							else
							{
								((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
										(float) ((93) / 100.0)));
							}
							g.setColor(new Color(40, 10, 40));
							g.fillRoundRect(this.getWidth() / 2 - (int) (300 * (screenSize.getWidth() / 1366)), 30, (int) (600 * screenSize.getWidth() / 1366), (int) (650 * screenSize.getHeight() / 768), 20, 20);
							g.setColor(new Color(0, 0, 0));
							g.drawRoundRect(this.getWidth() / 2 -  (int) (300 * (screenSize.getWidth() / 1366)), 30, (int) (600 * screenSize.getWidth() / 1366), (int) (650 * screenSize.getHeight() / 768), 20, 20);
							if (menuOpen)
							{
								g.setFont(new Font("TimesRoman", Font.BOLD, 100));
								g.drawString("Menu", this.getWidth() / 2 - 120, 110);
								g.setColor(new Color(192, 187, 192));
								g.setFont(new Font("Rockwell", Font.BOLD, 30));
								g.drawString("New Game", this.getWidth() / 2 - 80, 170);
								newGame = new Rectangle(this.getWidth() / 2 - 80, 148, 160, 24);
								g.drawString("Load Game", this.getWidth() / 2 - 80, 220);
								loadGame = new Rectangle(this.getWidth() / 2 - 80, 198, 170, 24);
								g.drawString("Settings", this.getWidth() / 2 - 80, 270);
								settings = new Rectangle(this.getWidth() / 2 - 80, 248, 120, 24);
							}
							if (settingsOpen)
							{
								
							}
							if (startLoadGame)
							{
								
							}
							if (startNewGame)
							{
								if (firstPage)
								{
									g.setFont(new Font("TimesRoman", Font.BOLD, 100));
									g.drawString("New Game", this.getWidth() / 2 - 220, 110);
									g.setColor(new Color(192, 187, 192));
									g.setFont(new Font("Rockwell", Font.BOLD, 30));
									g.drawString("Game Title:", this.getWidth() / 2 - 180, 180);
									g.drawString("Choose Map Size: ", this.getWidth() / 2 - 270, 220);
								}
							}
							((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
									(float) (1)));
						}
					}
					else
					{
						g.drawImage(spaceimg, 0,  - 830, (int) (spaceimg.getWidth(null) * 1.5 * screenSize.getWidth() / 1366), (int) (spaceimg.getHeight(null) * 1.5 * screenSize.getWidth() / 1366), null);
						g.drawImage(planetimg, 0, -80, (int) (planetimg.getWidth(null) * screenSize.getWidth() / 1366), (int) (planetimg.getHeight(null) * screenSize.getWidth() / 1366), null);
					}
				}
			}
			Control.paintControls(this, g);
		}
		public void addMouseListener()
		{
			addMouseListener(new PanelListener());
		}
		public class PanelListener extends MouseAdapter
		{
			public void mousePressed(MouseEvent e)
			{
				mX = e.getX();
				mY = e.getY();
				mousePressed = true;
				clickOccurring = true;
			}
			public void mouseReleased(MouseEvent e)
			{
				mousePressed = false;
			}
		}
		public void addControlToHandler(Control control, JFrame frame)
		{
			control.controlHandler = controlHandler;
			controlHandler.frame = frame;
		}
		public void nextPlayerPage(int player, int AIs, int players)
		{
			if (civilizationListLabel != null)
			{
				civilizationListLabel.dispose();
				newGamePlayerLabel.dispose();
			}
			RadioButton[] civilizationButtons2 = { radioGreece, radioSonghai, radioRome, radioGermany, radioRussia, radioPersia, radioEngland, 
					radioIndia, radioMongolia, radioDenmark, radioArabia, radioIroquois, radioAztec, radioFrance, radioJapan, 
					radioInca, radioEgypt, radioSiam, radioKorea, radioOttoman, radioAmerica, radioChina, radioRandom };
			if (radioGreece != null)
			{
				for (RadioButton radioButton : civilizationButtons2)
				{
					radioButton.dispose();
				}
			}
			if (player <= players - AIs)
			{
				newGamePlayerLabel = new Label("Player " + player + " Settings", this, new Point(getWidth() / 2 - 200, 50));
			}
			else
			{
				newGamePlayerLabel = new Label("AI " + (player - (players - AIs)) + " Settings", this, new Point(getWidth() / 2 - 200, 50));
			}
			newGamePlayerLabel.setFont(new Font("Rockwell", Font.BOLD, 50));
			newGamePlayerLabel.setVisible(true);
			if (worldLeaderLabel != null)
			{
				worldLeaderLabel.dispose();
			}
			worldLeaderLabel = new Label("Leader: ?", this, new Point(getWidth() / 2 - 250, 150));
			worldLeaderLabel.setFont(new Font("Rockwell", Font.PLAIN, 35));
			worldLeaderLabel.setVisible(true);
			radioRandom = new RadioButton("Random", this, new Point(getWidth() / 2 - 80, 500));
			radioGreece = new RadioButton("Greece", this, new Point(getWidth() / 2 - 150, 280));
			radioSonghai = new RadioButton("Songhai", this, new Point(getWidth() / 2 - 150, 300)); 
			radioRome = new RadioButton("Rome", this, new Point(getWidth() / 2 - 150, 320));
			radioGermany = new RadioButton("Germany", this, new Point(getWidth() / 2 - 150, 340));
			radioRussia = new RadioButton("Russia", this, new Point(getWidth() / 2 - 150, 360));
			radioPersia = new RadioButton("Persia", this, new Point(getWidth() / 2 - 150, 380));
			radioEngland = new RadioButton("England", this, new Point(getWidth() / 2 - 150, 400));
			radioIndia = new RadioButton("India", this, new Point(getWidth() / 2 - 150, 420));
			radioMongolia = new RadioButton("Mongolia", this, new Point(getWidth() / 2 - 150, 440)); 
			radioDenmark = new RadioButton("Denmark", this, new Point(getWidth() / 2 - 150, 460)); 
			radioArabia = new RadioButton("Arabia", this, new Point(getWidth() / 2 - 150, 480));
			radioIroquois = new RadioButton("Iroquois", this, new Point(getWidth() / 2 + 10, 280));
			radioAztec = new RadioButton("Aztec", this, new Point(getWidth() / 2 + 10, 300));
			radioFrance = new RadioButton("France", this, new Point(getWidth() / 2 + 10, 320));
			radioJapan = new RadioButton("Japan", this, new Point(getWidth() / 2 + 10, 340));
			radioInca = new RadioButton("The Incans", this, new Point(getWidth() / 2 + 10, 360));
			radioEgypt = new RadioButton("Egypt", this, new Point(getWidth() / 2 + 10, 380));
			radioSiam = new RadioButton("Siam", this, new Point(getWidth() / 2 + 10, 400));
			radioKorea = new RadioButton("Korea", this, new Point(getWidth() / 2 + 10, 420));
			radioOttoman = new RadioButton("The Ottoman Empire", this, new Point(getWidth() / 2 + 10, 440));
			radioAmerica = new RadioButton("America", this, new Point(getWidth() / 2 + 10, 460));
			radioChina = new RadioButton("China", this, new Point(getWidth() / 2 + 10, 480));
			civilizationListLabel = new Label("Pick a Civilization:", this, new Point(getWidth() / 2 - 270, 240));
			civilizationListLabel.color = new Color(170, 170, 170);
			civilizationListLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
			civilizationListLabel.setVisible(true);
			Conquer.civContainer.clear();
			RadioButton[] civilizationButtons = { radioGreece, radioSonghai, radioRome, radioGermany, radioRussia, radioPersia, radioEngland, 
					radioIndia, radioMongolia, radioDenmark, radioArabia, radioIroquois, radioAztec, radioFrance, radioJapan, 
					radioInca, radioEgypt, radioSiam, radioKorea, radioOttoman, radioAmerica, radioChina, radioRandom };
			for (int i = 0; i < civilizationButtons.length; i++)
			{
				civilizationButtons[i].setFont(new Font("Rockwell", Font.BOLD, 15));
				civilizationButtons[i].setVisible(true);
				civilizationButtons[i].opaqueBubble = false;
				civilizationButtons[i].choiceLocked = true;
				civilizationButtons[i].id = "civilizationButtons";
				civilizationButtons[i].controlHandler = new titlePageControlHandler();
				Conquer.civContainer.add(civilizationButtons[i]);
			}
			radioRandom.selected = true;
		}
		public class titlePageControlHandler extends ControlHandler
		{
			JFrame frame;
			int page = 0, players, AIs;
			Player[] playerArray;
			int[] pickedRadioButtons;
			public titlePageControlHandler()
			{
				
			}
			public titlePageControlHandler(JFrame frame)
			{
				this.frame = frame;
			}
			public void keyPressed(KeyEvent e, String id)
			{
				if (id.equals("playersTextBox"))
				{
					try
					{
						int playersAmount = Integer.parseInt(playersTextBox.getText());
						if (e.getKeyCode() == 8)
						{
							if (!AIsTextBox.getText().equals(""))
							{
								if (Integer.parseInt(AIsTextBox.getText()) > playersAmount)
								{
									AIsTextBox.setText("" + playersAmount);
								}
							}
						}
						AIsTextBox.acceptsNumbersBeneath = playersAmount + 1;
					}
					catch (Exception e2)
					{
						AIsTextBox.setText("");
						AIsTextBox.acceptsNumbersBeneath = 1;
					}
				}
			}
			public void clickEvent(String id, Control control)
			{
				if (id.equals("civilizationButtons"))
				{
					RadioButton r = (RadioButton) control;
					ColorPanel.worldCivilizations wC = ColorPanel.worldCivilizations.getCivilization(r.getText());
					String worldLeader = ColorPanel.getWorldLeader(wC);
					if (wC == null)
					{
						worldLeader = "?";
					}
					worldLeaderLabel.setText("Leader: " + worldLeader);
				}
			}
			public void setUpLoadedPage()
			{
				if (playerArray[page - 1] != null)
				{
					RadioButton[] civilizationButtons = { radioGreece, radioSonghai, radioRome, radioGermany, radioRussia, radioPersia, radioEngland, 
							radioIndia, radioMongolia, radioDenmark, radioArabia, radioIroquois, radioAztec, radioFrance, radioJapan, 
							radioInca, radioEgypt, radioSiam, radioKorea, radioOttoman, radioAmerica, radioChina, radioRandom };
					if (playerArray[page - 1].civilization != null)
					{
						ColorPanel.worldCivilizations civ = playerArray[page - 1].civilization;
						for (RadioButton r : civilizationButtons)
						{
							if (ColorPanel.worldCivilizations.getCivilization(r.getText()) == civ)
							{
								r.setSelected();
								if (r == radioRandom)
								{
									worldLeaderLabel.setText("Leader: ?");
								}
								else
								{
									String worldLeader = ColorPanel.getWorldLeader(ColorPanel.worldCivilizations.getCivilization(r.getText()));
									worldLeaderLabel.setText("Leader: " + worldLeader);
								}
								break;
							}
						}
					}
				}
			}
			public boolean savePage()
			{
				boolean passed = false;
				if (page == 0)
				{
					File gameFile = new File("Conquer Games/" + textBox.getText());
					if (!textBox.getText().equals("") && !AIsTextBox.getText().equals("") && !playersTextBox.getText().equals("") && !gameFile.exists() && Integer.parseInt(playersTextBox.getText()) > 1)
					{
						passed = true;
						firstPage = false;
						players = Integer.parseInt(playersTextBox.getText());
						AIs = Integer.parseInt(AIsTextBox.getText());
						textBox.setVisible(false);
						if (Conquer.mapContainer.getSelected() == hugeSizeButton)
						{
							Conquer.gameScreenSizeX = 1600;
							Conquer.gameScreenSizeY = 400;
							Conquer.islands = 120;
							Conquer.landMassPercentage = 40;
							Conquer.continents = 6;
						}
						else if(Conquer.mapContainer.getSelected() == largeSizeButton)
						{
							Conquer.gameScreenSizeX = 1200;
							Conquer.gameScreenSizeY = 300;
							Conquer.islands = 80;
							Conquer.landMassPercentage = 40;
							Conquer.continents = 6;
						}
						else if(Conquer.mapContainer.getSelected() == normalSizeButton)
						{
							Conquer.gameScreenSizeX = 800;
							Conquer.gameScreenSizeY = 200;
							Conquer.islands = 60;
							Conquer.landMassPercentage = 40;
							Conquer.continents = 6;
						}
						else if(Conquer.mapContainer.getSelected() == smallSizeButton)
						{
							Conquer.gameScreenSizeX = 600;
							Conquer.gameScreenSizeY = 150;
							Conquer.islands = 30;
							Conquer.landMassPercentage = 40;
							Conquer.continents = 6;
						}
						else if(Conquer.mapContainer.getSelected() == tinySizeButton)
						{
							Conquer.gameScreenSizeX = 400;
							Conquer.gameScreenSizeY = 100;
							Conquer.islands = 20;
							Conquer.landMassPercentage = 40;
							Conquer.continents = 6;
						}
						else if(Conquer.mapContainer.getSelected() == quickBattleSizeButton)
						{
							Conquer.gameScreenSizeX = 200;
							Conquer.gameScreenSizeY = 50;
							Conquer.landMassPercentage = 30;
							Conquer.continents = 2;
							Conquer.islands = 10;
						}
						hugeSizeButton.setVisible(false);
						largeSizeButton.setVisible(false);
						normalSizeButton.setVisible(false);
						smallSizeButton.setVisible(false);
						tinySizeButton.setVisible(false);
						quickBattleSizeButton.setVisible(false);
						AIsPromptLabel.setVisible(false);
						AIsTextBox.setVisible(false);
						playersPromptLabel.setVisible(false);
						playersTextBox.setVisible(false);
						playerArray = new Player[players];
						pickedRadioButtons = new int[players];
						for (int i = 0; i < players; i++)
						{
							pickedRadioButtons[i] = -1;
						}
					}
					else
					{
						if (textBox.getText().equals("") || gameFile.exists())
						{
							textBox.setBackgroundColor(new Color(255, 50, 50));
						}
						if (playersTextBox.getText().equals("") || Integer.parseInt(playersTextBox.getText()) < 2)
						{
							playersTextBox.setBackgroundColor(new Color(255, 50, 50));
						}
						if (AIsTextBox.getText().equals(""))
						{
							AIsTextBox.setBackgroundColor(new Color(255, 50, 50));
						}
					}
				}
				if (page > 0)
				{
					passed = true;
					RadioButton selectedRadio = Conquer.civContainer.getSelected();
					RadioButton[] civilizationButtons = { radioGreece, radioSonghai, radioRome, radioGermany, radioRussia, radioPersia, radioEngland, 
							radioIndia, radioMongolia, radioDenmark, radioArabia, radioIroquois, radioAztec, radioFrance, radioJapan, 
							radioInca, radioEgypt, radioSiam, radioKorea, radioOttoman, radioAmerica, radioChina, radioRandom };
					for (int i = 0; i < civilizationButtons.length; i++)
					{
						if (selectedRadio == civilizationButtons[i])
						{
							pickedRadioButtons[page - 1] = i;
							break;
						}
					}
					if (selectedRadio == radioRandom)
					{
						if (page <= players - AIs)
						{
							playerArray[page - 1] = new Player();
						}
						else
						{
							playerArray[page - 1] = new AI();
						}
					}
					else
					{
						ColorPanel.worldCivilizations wC = ColorPanel.worldCivilizations.getCivilization(selectedRadio.getText());
						if (page <= players - AIs)
						{
							playerArray[page - 1] = new Player(wC);
						}
						else
						{
							playerArray[page - 1] = new AI(wC);
						}
					}
				}
				return passed;
			}
			public void clickEvent(String id)
			{
				if (id.equals("Huge Size"))
				{
					playersPromptLabel.setText("Enter # of Players:               (Max 22)");
					playersTextBox.acceptsNumbersBeneath = 23;
					Conquer.distanceBetweenPlayers = 50;
				}
				if (id.equals("Large Size"))
				{
					playersPromptLabel.setText("Enter # of Players:               (Max 22)");
					playersTextBox.acceptsNumbersBeneath = 23;
					Conquer.distanceBetweenPlayers = 40;
				}
				if (id.equals("Normal Size"))
				{
					playersPromptLabel.setText("Enter # of Players:               (Max 22)");
					playersTextBox.acceptsNumbersBeneath = 23;
					Conquer.distanceBetweenPlayers = 30;
				}
				if (id.equals("Small Size"))
				{
					playersPromptLabel.setText("Enter # of Players:               (Max 22)");
					playersTextBox.acceptsNumbersBeneath = 23;
					Conquer.distanceBetweenPlayers = 20;
				}
				if (id.equals("Tiny Size"))
				{
					playersPromptLabel.setText("Enter # of Players:               (Max 10)");
					playersTextBox.acceptsNumbersBeneath = 11;
					Conquer.distanceBetweenPlayers = 8;
					if (AIsTextBox.acceptsNumbersBeneath > 11)
					{
						AIsTextBox.acceptsNumbersBeneath = 11;
					}
					try 
					{
						if (Integer.parseInt(playersTextBox.getText()) > 10)
						{
							playersTextBox.setText("10");
						}
						if (Integer.parseInt(AIsTextBox.getText()) > 10)
						{
							AIsTextBox.setText("10");
						}
					}
					catch (Exception e)
					{
						
					}
				}
				if (id.equals("Quick Battle Size"))
				{
					playersPromptLabel.setText("Enter # of Players:               (Max 8)");
					playersTextBox.acceptsNumbersBeneath = 9;
					Conquer.distanceBetweenPlayers = 5;
					if (AIsTextBox.acceptsNumbersBeneath > 9)
					{
						AIsTextBox.acceptsNumbersBeneath = 9;
					}
					try 
					{
						if (Integer.parseInt(playersTextBox.getText()) > 8)
						{
							playersTextBox.setText("8");
						}
						if (Integer.parseInt(AIsTextBox.getText()) > 8)
						{
							AIsTextBox.setText("8");
						}
					}
					catch (Exception e)
					{
						
					}
				}
				if (id.equals("Finish Button"))
				{
					savePage();
					RadioButton[] civilizationButtons = { radioGreece, radioSonghai, radioRome, radioGermany, radioRussia, radioPersia, radioEngland, 
							radioIndia, radioMongolia, radioDenmark, radioArabia, radioIroquois, radioAztec, radioFrance, radioJapan, 
							radioInca, radioEgypt, radioSiam, radioKorea, radioOttoman, radioAmerica, radioChina };
					ArrayList<RadioButton> remainingRadioButtons = new ArrayList<RadioButton>();
					for (int i = 0; i < playerArray.length; i++)
					{
						if (playerArray[i] == null)
						{
							if (i < players - AIs)
							{
								playerArray[i] = new Player();
							}
							else
							{
								playerArray[i] = new AI();
							}
						}
					}
					for (int i = 0; i < civilizationButtons.length; i++)
					{
						remainingRadioButtons.add(civilizationButtons[i]);
					}
					for (int i = 0; i < pickedRadioButtons.length; i++)
					{
						if (pickedRadioButtons[i] != -1 && pickedRadioButtons[i] != 22)
						{
							remainingRadioButtons.remove(civilizationButtons[pickedRadioButtons[i]]);
						}
					}
					for (int i = 0; i < playerArray.length; i++)
					{
						if (playerArray[i].civilization == null)
						{
							Random rand = new Random();
							RadioButton randomSelected = remainingRadioButtons.get(rand.nextInt(remainingRadioButtons.size()));
							remainingRadioButtons.remove(randomSelected);
							playerArray[i].civilization = ColorPanel.worldCivilizations.getCivilization(randomSelected.getText());
						}
					}
					for (int i = 0; i < playerArray.length; i++)
					{
						System.out.println("Player " + (i + 1) + " civilization is: " + playerArray[i].civilization.getCivilizationName());
					}
					try {
						frame.setVisible(false);
						Conquer.startNewGame(frame, textBox.getText(), playerArray);
					} catch (IOException e) {
						e.printStackTrace();
					}
					id = "Menu Button";
				}
				if (id.equals("Back Button"))
				{
					boolean passed = false;
					passed = savePage();
					if (passed)
					{
						page--;
						nextPlayerPage(page, AIs, players);
						setUpLoadedPage();
						if (page == 1)
						{
							backButton.setVisible(false);
						}
						RadioButton[] civilizationButtons = { radioGreece, radioSonghai, radioRome, radioGermany, radioRussia, radioPersia, radioEngland, 
								radioIndia, radioMongolia, radioDenmark, radioArabia, radioIroquois, radioAztec, radioFrance, radioJapan, 
								radioInca, radioEgypt, radioSiam, radioKorea, radioOttoman, radioAmerica, radioChina, radioRandom };
						for (int i = 0; i < pickedRadioButtons.length; i++)
						{
							if (pickedRadioButtons[i] != civilizationButtons.length - 1 && i != page - 1 && pickedRadioButtons[i] != -1)
							{
								civilizationButtons[pickedRadioButtons[i]].setVisible(false);
							}
						}
					}
					nextButton.setVisible(true);
				}
				if (id.equals("playersTextBox"))
				{
					playersTextBox.setBackgroundColor(new Color(255, 255, 255));
				}
				if (id.equals("Text Box"))
				{
					textBox.setBackgroundColor(new Color(255, 255, 255));
				}
				if (id.equals("AIsTextBox"))
				{
					AIsTextBox.setBackgroundColor(new Color(255, 255, 255));
				}
				if (id.equals("Next Button"))
				{
					boolean passed = false;
					passed = savePage();
					if (passed)
					{
						finishButton.setVisible(true);
						page++;
						nextPlayerPage(page, AIs, players);
						setUpLoadedPage();
						if (page > 1)
						{
							backButton.setVisible(true);
						}
						if (page == players)
						{
							nextButton.setVisible(false);
						}
						RadioButton[] civilizationButtons = { radioGreece, radioSonghai, radioRome, radioGermany, radioRussia, radioPersia, radioEngland, 
								radioIndia, radioMongolia, radioDenmark, radioArabia, radioIroquois, radioAztec, radioFrance, radioJapan, 
								radioInca, radioEgypt, radioSiam, radioKorea, radioOttoman, radioAmerica, radioChina, radioRandom };
						for (int i = 0; i < pickedRadioButtons.length; i++)
						{
							if (pickedRadioButtons[i] != civilizationButtons.length - 1 && i != page - 1 && pickedRadioButtons[i] != -1)
							{
								civilizationButtons[pickedRadioButtons[i]].setVisible(false);
							}
						}
					}
				}
				if (id.equals("Load Game"))
				{
					try {
						frame.setVisible(false);
						Conquer.startNewGame(frame, loadGameListBox.getItem(loadGameListBox.getSelectedIndex()), null);
					} catch (IOException e) {
						e.printStackTrace();
						frame.setVisible(true);
						System.out.println("Error");
					}
				}
				if (id.equals("Menu Button"))
				{
					if (startLoadGame)
					{
						loadGameListBox.setVisible(false);
						loadGameButton.setVisible(false);
						menuOpen = true;
						loadGameLabel.setVisible(false);
						startLoadGame = false;
					}
					if (settingsOpen)
					{
						
					}
					if (startNewGame)
					{
						menuOpen = true;
						textBox.setVisible(false);
						hugeSizeButton.setVisible(false);
						largeSizeButton.setVisible(false);
						normalSizeButton.setVisible(false);
						smallSizeButton.setVisible(false);
						tinySizeButton.setVisible(false);
						quickBattleSizeButton.setVisible(false);
						startNewGame = false;
						nextButton.setVisible(false);
						AIsPromptLabel.setVisible(false);
						AIsTextBox.setVisible(false);
						playersPromptLabel.setVisible(false);
						playersTextBox.setVisible(false);
						playersTextBox.setText("");
						textBox.setText("");
						AIsTextBox.setText("");
						AIsTextBox.acceptsNumbersBeneath = 1;
						if (firstPage == false)
						{
							firstPage = true;
							RadioButton[] civilizationButtons = { radioGreece, radioSonghai, radioRome, radioGermany, radioRussia, radioPersia, radioEngland, 
									radioIndia, radioMongolia, radioDenmark, radioArabia, radioIroquois, radioAztec, radioFrance, radioJapan, 
									radioInca, radioEgypt, radioSiam, radioKorea, radioOttoman, radioAmerica, radioChina, radioRandom };
							for (int i = 0; i < civilizationButtons.length; i++)
							{
								civilizationButtons[i].dispose();
							}
							civilizationListLabel.dispose();
							worldLeaderLabel.dispose();
							newGamePlayerLabel.dispose();
							finishButton.setVisible(false);
							page = 0;
							backButton.setVisible(false);
						}
					}
					menuButton.setVisible(false);
				}
			}
		}
	}