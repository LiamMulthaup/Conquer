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
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import ui.Control;
import ui.ControlHandler;
import ui.Doodle;
import ui.ImageBox;
import ui.Label;
import ui.Page;

public class Unit
{
	double health = 100;
	double totalHealth = 100; // all units have 100 hitPoints
	double mobility = 2, totalMobility = 2;
	int sight = 2;
	double strength = 8;
	int rangedStrength;
	double strengthModifier;
	int experience = 0;
	int maxExperience;
	int level = 1;
	Point location;
	static int radius = 28;
	ColorPanel.worldCivilizations owner = ColorPanel.worldCivilizations.Denmark;
	ColorPanel colorPanel;
	boolean warrior;
	boolean ranged;
	Player player;
	static Image warriorimg = new ImageIcon(Unit.class.getResource("Warrior_(Civ5).png")).getImage(),
			settlerimg = new ImageIcon(Unit.class.getResource("settler.png")).getImage(),
			workerimg = new ImageIcon(Unit.class.getResource("Worker_(Civ5).png")).getImage(),
			spearmanimg = new ImageIcon(Unit.class.getResource("Spearman_(Civ5).png")).getImage(),
			movementimg = new ImageIcon(Unit.class.getResource("Movement.PNG")).getImage(),
			rangedimg = new ImageIcon(Unit.class.getResource("RangedAttack.PNG")).getImage(),
			attackimg = new ImageIcon(Unit.class.getResource("Attacking.PNG")).getImage(),
			buildimg = new ImageIcon(Unit.class.getResource("Build.PNG")).getImage(),
			doNothingimg = new ImageIcon(Unit.class.getResource("DoNothing.PNG")).getImage(),
			garrisonimg = new ImageIcon(Unit.class.getResource("Garrison.PNG")).getImage(),
			goldenAgeimg = new ImageIcon(Unit.class.getResource("GoldenAge.PNG")).getImage(),
			healingimg = new ImageIcon(Unit.class.getResource("Healing.PNG")).getImage(),
			pillageimg = new ImageIcon(Unit.class.getResource("Pillaging.PNG")).getImage(),
			sleepimg = new ImageIcon(Unit.class.getResource("Sleep.PNG")).getImage(),
			wakeimg = new ImageIcon(Unit.class.getResource("WakeUp.PNG")).getImage(),
			alertimg = new ImageIcon(Unit.class.getResource("Alert.PNG")).getImage(),
			settleimg = new ImageIcon(Unit.class.getResource("StartACity.PNG")).getImage(),
			scoutimg = new ImageIcon(Unit.class.getResource("Scout_(Civ5).png")).getImage(),
			archerimg = new ImageIcon(Unit.class.getResource("Bowman_(Civ5).png")).getImage(),
			triremeimg = new ImageIcon(Unit.class.getResource("Trireme_(Civ5).png")).getImage(),
			chariotArcherimg = new ImageIcon(Unit.class.getResource("War_chariot_(Civ5).png")).getImage(),
			horsemanimg = new ImageIcon(Unit.class.getResource("Horseman_(Civ5).png")).getImage(),
			catapultimg = new ImageIcon(Unit.class.getResource("Catapult_(Civ5).png")).getImage(),
			swordsmanimg = new ImageIcon(Unit.class.getResource("Swordsman_(Civ5).png")).getImage(),
			pikemanimg = new ImageIcon(Unit.class.getResource("Pikeman_(Civ5).png")).getImage(),
			knightimg = new ImageIcon(Unit.class.getResource("Knight_(Civ5).png")).getImage(),
			crossbowmanimg = new ImageIcon(Unit.class.getResource("Crossbowman_(Civ5).png")).getImage(),
			trebuchetimg = new ImageIcon(Unit.class.getResource("Trebuchet_(Civ5).png")).getImage(),
			longswordsmanimg= new ImageIcon(Unit.class.getResource("Longswordsman_(Civ5).png")).getImage(),
			caravelimg = new ImageIcon(Unit.class.getResource("Caravel_(Civ5).png")).getImage(),
			musketmanimg= new ImageIcon(Unit.class.getResource("Musketman_(Civ5).png")).getImage(),
			frigateimg = new ImageIcon(Unit.class.getResource("Frigate_(Civ5).png")).getImage(),
			cannonimg = new ImageIcon(Unit.class.getResource("Cannon_(Civ5).png")).getImage(),
			lancerimg = new ImageIcon(Unit.class.getResource("Lancer_(Civ5).png")).getImage(),
			cavalryimg = new ImageIcon(Unit.class.getResource("Cavalry_(Civ5).png")).getImage(),
			riflemanimg = new ImageIcon(Unit.class.getResource("Rifleman_(Civ5).png")).getImage(),
			ironcladimg = new ImageIcon(Unit.class.getResource("Ironclad_(Civ5).png")).getImage(),
			antiTankGunimg = new ImageIcon(Unit.class.getResource("Anti-tank_gun_(Civ5).png")).getImage(),
			infantryimg = new ImageIcon(Unit.class.getResource("Infantry_(Civ5).png")).getImage(),
			artilleryimg = new ImageIcon(Unit.class.getResource("Artillery_(Civ5).png")).getImage(),
			submarineimg = new ImageIcon(Unit.class.getResource("submarine_(Civ5).png")).getImage(),
			battleshipimg = new ImageIcon(Unit.class.getResource("Battleship_(Civ5).png")).getImage(),
			antiAircraftGunimg = new ImageIcon(Unit.class.getResource("Anti-aircraft_gun_(Civ5).png")).getImage(),
			carrierimg = new ImageIcon(Unit.class.getResource("Carrier_(Civ5).png")).getImage(),
			fighterimg = new ImageIcon(Unit.class.getResource("Fighter_(Civ5).png")).getImage(),
			destroyerimg = new ImageIcon(Unit.class.getResource("Destroyer_(Civ5).png")).getImage(),
			tankimg = new ImageIcon(Unit.class.getResource("Tank_(Civ5).png")).getImage(),
			mechanizedInfantryimg = new ImageIcon(Unit.class.getResource("Mechanized_infantry_(Civ5).png")).getImage(),
			bomberimg = new ImageIcon(Unit.class.getResource("Bomber_(Civ5).png")).getImage(),
			paratrooperimg = new ImageIcon(Unit.class.getResource("Paratrooper_(Civ5).png")).getImage(),
			mobileSAMimg= new ImageIcon(Unit.class.getResource("Mobile_SAM_(Civ5).png")).getImage(),
			nuclearSubmarineimg = new ImageIcon(Unit.class.getResource("Nuclear_submarine_(Civ5).png")).getImage(),
			helicopterGunshipimg = new ImageIcon(Unit.class.getResource("Helicopter_gunship_(Civ5).png")).getImage(),
			rocketArtilleryimg = new ImageIcon(Unit.class.getResource("Rocket_artillery_(Civ5).png")).getImage(),
			jetFighterimg = new ImageIcon(Unit.class.getResource("Jet_fighter_(Civ5).png")).getImage(),
			modernArmorimg = new ImageIcon(Unit.class.getResource("Modern_armor_(Civ5).png")).getImage(),
			atomicBombimg = new ImageIcon(Unit.class.getResource("Atomic_bomb_(Civ5).png")).getImage(),
			missileCruiserimg = new ImageIcon(Unit.class.getResource("Missile_cruiser_(Civ5).png")).getImage(),
			guidedMissileimg = new ImageIcon(Unit.class.getResource("Guided_missile_(Civ5).png")).getImage(),
			stealthBomberimg = new ImageIcon(Unit.class.getResource("Stealth_bomber_(Civ5).png")).getImage(),
			nuclearMissileimg = new ImageIcon(Unit.class.getResource("Nuclear_missile_(Civ5).png")).getImage(),
			giantDeathRobotimg = new ImageIcon(Unit.class.getResource("Giant_death_robot_(Civ5).png")).getImage()
			;
	Image attackOrUtilityimg;
	Page unitController;
	Page unitInfoPage, attackingInfoPage, defendingUnitInfoPage;
	Label unitNameLabel, healthStatLabel, combatStrengthLabel, mobilityLabel, rangedStrengthLabel, ownerLabel, defendingUnitNameLabel, defendingOwnerLabel, defendingHealthStatLabel, defendingMobilityLabel, defendingCombatStrengthLabel, defendingRangedStrengthLabel;
	Label attackingUnitStrengthLabel, attackingUnitStrengthModifierLabel, defendingUnitStrengthLabel, defendingUnitStrengthModifierLabel, attackingApproxDamageDealtLabel, defendingApproxDamageDealtLabel;
	Doodle attackingHealthBar, defendingHealthBar;
	static ArrayList<Unit> units = new ArrayList<Unit>();
	static Unit selected;
	UnitControlHandler controlHandler = new UnitControlHandler();
	ImageBox attackOrUtilityImage;
	ImageBox unitImage;
	ImageBox movementImage;
	ImageBox healImage;
	ImageBox sleepImage;
	ImageBox wakeUpImage;
	ImageBox alertImage;
	ImageBox pillageImage;
	ImageBox doNothingImage;
	boolean healing = false;
	boolean utilityShow = true;
	boolean sleeping = false;
	boolean scheduledToMove = false;
	boolean onEnemyTerritory = false;
	boolean alert = false;
	boolean alertShow = true;
	boolean turnDone = false;
	boolean onCity = false;
	static boolean moving = false;
	boolean cancelMovementTim = false;
    static Timer movementCheckerTim;
    String unitName;
    
	public Unit(ColorPanel colorPanel, int x, int y, Player player)
	{
		this.colorPanel = colorPanel;
		location = new Point(x, y);
		this.player = player;
		this.owner = player.civilization;
	}
	public void dispose()
	{
		units.remove(this);
	}
	public void paint(Graphics g, int x, int y)
	{
		g.setColor(owner.getColor());
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillOval((int) (x + colorPanel.xDistanceIncrementer / 2 - 28), (int) (y + colorPanel.sideLength * 2 / 3 - 28), 56, 56);
		g.setColor(Color.black);
		g.drawOval((int) (x + colorPanel.xDistanceIncrementer / 2 - 28), (int) (y + colorPanel.sideLength * 2 / 3 - 28), 56, 56);
		if (totalHealth != health)
		{
			((Graphics2D) g).setStroke(new BasicStroke(5));
			g.setColor(new Color(0, 0, 0));
			g.drawLine((int)(x + colorPanel.xDistanceIncrementer / 2 - 28),(int) (y + colorPanel.sideLength * 2 / 3 - 33), (int) (x + colorPanel.xDistanceIncrementer / 2 - 28 + (56)), (int) (y + colorPanel.sideLength * 2 / 3 - 33));
			g.setColor(new Color(0, 204, 0));
			g.drawLine((int)(x + colorPanel.xDistanceIncrementer / 2 - 28),(int) (y + colorPanel.sideLength * 2 / 3 - 33), (int) (x + colorPanel.xDistanceIncrementer / 2 - 28 + (56 * health / totalHealth)), (int) (y + colorPanel.sideLength * 2 / 3 - 33));
			((Graphics2D) g).setStroke(new BasicStroke(1));
		}
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	public static void disposeAll()
	{
		units.clear();
	}
	public void clickEvent()
	{
		Conquer.unitMenuOpen = true;
		if (selected != this)
		{
			if (warrior)
			{
				System.out.println("Warrior");
			}
			else
			{
				System.out.println("Settler");
			}
			refreshButtons();
			selected = this;
			System.out.println(Control.getControlsLength());
		}
	}
	public void clearButtons()
	{
		if (selected.unitController != null)
		{
			selected.unitController.dispose();
			selected.unitController = null;
		}
		if (selected.healImage != null)
		{
			selected.healImage.dispose();
			selected.healImage = null;
		}
		if (selected.sleepImage != null)
		{
			selected.sleepImage.dispose();
			selected.sleepImage = null;
		}
		if (selected.alertImage != null)
		{
			selected.alertImage.dispose();
			selected.alertImage = null;
		}
		if (selected.wakeUpImage != null)
		{
			selected.wakeUpImage.dispose();
			selected.wakeUpImage = null;
		}
		if (selected.doNothingImage != null)
		{
			selected.doNothingImage.dispose();
			selected.doNothingImage = null;
		}
		if (selected.pillageImage != null)
		{
			selected.pillageImage.dispose();
			selected.pillageImage = null;
		}
		if (selected.movementImage != null)
		{
			selected.movementImage.dispose();
			selected.movementImage = null;
		}
		if (selected.attackOrUtilityImage != null)
		{
			selected.attackOrUtilityImage.dispose();
			selected.attackOrUtilityImage = null;
		}
		if (selected.unitImage != null)
		{
			selected.unitImage.dispose();
			selected.unitImage = null;
		}
		if (selected.unitInfoPage != null)
		{
			selected.unitInfoPage.dispose();
			selected.unitInfoPage = null;
		}
		if (selected.combatStrengthLabel != null)
		{
			selected.combatStrengthLabel.dispose();
			selected.combatStrengthLabel = null;
		}
		if (selected.healthStatLabel != null)
		{
			selected.healthStatLabel.dispose();
			selected.healthStatLabel = null;
		}
		if (selected.mobilityLabel != null)
		{
			selected.mobilityLabel.dispose();
			selected.mobilityLabel = null;
		}
		if (selected.ownerLabel != null)
		{
			selected.ownerLabel.dispose();
			selected.ownerLabel = null;
		}
		if (selected.rangedStrengthLabel != null)
		{
			selected.rangedStrengthLabel.dispose();
			selected.rangedStrengthLabel = null;
		}
		if (selected.unitNameLabel != null)
		{
			selected.unitNameLabel.dispose();
			selected.unitNameLabel = null;
		}
		clearAttackingTable();
	}
	public void refreshButtons()
	{
		if (selected != null)
		{
			clearButtons();
		}
		int movementLoc = 0;
		int utilityLoc = 0;
		int sleepingLoc = 0;
		int pillagingLoc = 0;
		int alertLoc = 0;
		int doNothingLoc = 0;
		int garrisonLoc = 0;
		int healLoc = 0;
		int wakeUpLoc = 0;
		if (!turnDone && player == colorPanel.players[colorPanel.turn])
		{
			if (mobility != 0 && !sleeping)
			{
				movementLoc = 45;
			}
			if (utilityShow && mobility != 0 && !sleeping)
			{
				utilityLoc = 45;
			}
			if (sleeping && mobility != 0)
			{
				wakeUpLoc = 45;
			}
			if (!sleeping && mobility != 0)
			{
				sleepingLoc = 45;
			}
			if (!sleeping && alertShow && mobility !=0)
			{
				alertLoc = 45;
			}
			if (onCity && mobility != 0 && !sleeping)
			{
				garrisonLoc = 45;
			}
			if (totalHealth != health && !sleeping && mobility != 0)
			{
				healLoc = 45;
			}
			if (mobility != 0 && !sleeping)
			{
				doNothingLoc = 45;
			}
		}
		int locTotal = movementLoc + utilityLoc + sleepingLoc + pillagingLoc + alertLoc + doNothingLoc + garrisonLoc + healLoc + wakeUpLoc;
		unitInfoPage = new Page(400, 110, colorPanel, new Point(0, 110));
		unitInfoPage.backgroundVisibility = 0.96;
		unitInfoPage.bottomOriented = true;
		unitInfoPage.setVisible(true);
		unitNameLabel = new Label(unitName, colorPanel, new Point(200, 105));
		unitNameLabel.color = Color.white;
		unitNameLabel.setFont(new Font("Rockwell", Font.BOLD, 23));
		unitNameLabel.setLocation(new Point(260 - unitNameLabel.getWidth() / 2, 105));
		unitNameLabel.bottomOriented = true;
		unitNameLabel.setVisible(true);
		ownerLabel = new Label("Owner: " + owner.name(), colorPanel, new Point(155, 85));
		ownerLabel.color = Color.white;
		ownerLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		ownerLabel.setLocation(new Point(260 - ownerLabel.getWidth() / 2, 85));
		ownerLabel.bottomOriented = true;
		ownerLabel.setVisible(true);
		healthStatLabel = new Label("Health: " + (int)health + "/" + (int)totalHealth, colorPanel, new Point(155, 70));
		healthStatLabel.color = Color.white;
		healthStatLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		healthStatLabel.setLocation(new Point(260 - healthStatLabel.getWidth() / 2, 70));
		healthStatLabel.bottomOriented = true;
		healthStatLabel.setVisible(true);
		mobilityLabel = new Label("Mobility: " + (int)mobility + "/" + (int)totalMobility, colorPanel, new Point(155, 55));
		mobilityLabel.color = Color.white;
		mobilityLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
		mobilityLabel.setLocation(new Point(260 - mobilityLabel.getWidth() / 2, 55));
		mobilityLabel.bottomOriented = true;
		mobilityLabel.setVisible(true);
		if (warrior)
		{
			combatStrengthLabel = new Label("Combat Strength: " + (int)strength, colorPanel, new Point(155, 40));
			combatStrengthLabel.color = Color.white;
			combatStrengthLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
			combatStrengthLabel.setLocation(new Point(260 - combatStrengthLabel.getWidth() / 2, 40));
			combatStrengthLabel.bottomOriented = true;
			combatStrengthLabel.setVisible(true);
		}
		if (ranged)
		{
			rangedStrengthLabel = new Label("Ranged Strength: " + (int)rangedStrength, colorPanel, new Point(155, 25));
			rangedStrengthLabel.color = Color.white;
			rangedStrengthLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
			rangedStrengthLabel.setLocation(new Point(260 - rangedStrengthLabel.getWidth() / 2, 25));
			rangedStrengthLabel.bottomOriented = true;
			rangedStrengthLabel.setVisible(true);
		}
		if (locTotal != 0)
		{
			unitController = new Page(60, 160 + locTotal, colorPanel, new Point(-5, 160 + locTotal));
			unitController.color = new Color(0, 0, 0);
			unitController.bottomOriented = true;
			unitController.backgroundVisibility = 0.96;
			unitController.setVisible(true);
			int height = 150;
			if (doNothingLoc != 0)
			{
				height+= doNothingLoc;
				doNothingImage = new ImageBox(40, 40, colorPanel, new Point(7, height), doNothingimg);
				doNothingImage.setVisible(true);
				doNothingImage.bottomOriented = true;
				doNothingImage.controlHandler = controlHandler;
				doNothingImage.id = "Do Nothing";
			}
			if (pillagingLoc != 0)
			{
				height+= pillagingLoc;
				pillageImage = new ImageBox(40, 40, colorPanel, new Point(7, height), pillageimg);
				pillageImage.setVisible(true);
				pillageImage.bottomOriented = true;
				pillageImage.controlHandler = controlHandler;
				pillageImage.id = "Pillage";
			}
			if (alertLoc != 0)
			{
				height+= alertLoc;
				alertImage = new ImageBox(40, 40, colorPanel, new Point(7, height), alertimg);
				alertImage.setVisible(true);
				alertImage.bottomOriented = true;
				alertImage.controlHandler = controlHandler;
				alertImage.id = "Alert";
			}
			if (sleepingLoc != 0)
			{
				height+= sleepingLoc;
				sleepImage = new ImageBox(40, 40, colorPanel, new Point(7, height), sleepimg);
				sleepImage.setVisible(true);
				sleepImage.bottomOriented = true;
				sleepImage.controlHandler = controlHandler;
				sleepImage.id = "Sleep";
			}
			if (wakeUpLoc != 0)
			{
				height+= wakeUpLoc;
				wakeUpImage = new ImageBox(40, 40, colorPanel, new Point(7, height), wakeimg);
				wakeUpImage.setVisible(true);
				wakeUpImage.bottomOriented = true;
				wakeUpImage.controlHandler = controlHandler;
				wakeUpImage.id = "Wake Up";
			}
			if (utilityLoc != 0)
			{
				height+= utilityLoc;
				attackOrUtilityImage = new ImageBox(40, 40, colorPanel, new Point(7, height), attackOrUtilityimg);
				attackOrUtilityImage.controlHandler = controlHandler;
				attackOrUtilityImage.id = "Utility";
				attackOrUtilityImage.setVisible(true);
				attackOrUtilityImage.bottomOriented = true;
			}
			if (healLoc != 0)
			{
				height+= healLoc;
				healImage = new ImageBox(40, 40, colorPanel, new Point(7, height), healingimg);
				healImage.setVisible(true);
				healImage.bottomOriented = true;
				healImage.controlHandler = controlHandler;
				healImage.id = "Heal";
			}
			if (movementLoc != 0)
			{
				height+= movementLoc;
				movementImage = new ImageBox(40, 40, colorPanel, new Point(7, height), movementimg);
				movementImage.controlHandler = controlHandler;
				movementImage.id = "Movement";
				movementImage.setVisible(true);
				movementImage.bottomOriented = true;
			}
		}
	}
	static public boolean controlClickEvents(int mX, int mY, boolean clickOccurring)
	{
		if (moving || movementCheckerTim != null)
		{
			return false;
		}
		for (int i = units.size() - 1; i > -1; i--)
		{
			double newXOffset;
			double newYOffset;
			if (!clickOccurring)
			{
				break;
			}
			Point newOffset = getUnitLocation(units.get(i), units.get(i).location);
			newXOffset = newOffset.x;
			newYOffset = newOffset.y;
			if (units.get(i).warrior == false && units.get(i).colorPanel.warriorMap[units.get(i).location.x][units.get(i).location.y] != null)
			{
				newYOffset+= 10;
			}
			
			if (radius * radius > (mX - newXOffset) * (mX - newXOffset) + (mY - newYOffset) * (mY - newYOffset))
			{
				units.get(i).clickEvent();
				clickOccurring = false;
			}
		}
		return clickOccurring;
	}
	private class UnitControlHandler extends ControlHandler
	{
		public void clickEvent(String id)
		{
			if (!colorPanel.menuOpen)
			{
				if (id.equals("Movement"))
				{
					if (!moving)
					{
						if (!Conquer.movementOpen)
						{
							beginMovementChecker(false);
							Conquer.movementOpen = true;
						}
						else
						{
							clearAttackingTable();
							Conquer.movementOpen = false;
							cancelMovementTim = true;
						}
					}
				}
				if (id.equals("Utility"))
				{
					if (!moving)
					{
						if (!Conquer.movementOpen)
						{
							utilityAction();
						}
						else
						{
							clearAttackingTable();
							Conquer.movementOpen = false;
							cancelMovementTim = true;
						}
						
					}
				}
				if (id.equals("Do Nothing"))
				{
					if (!moving)
					{
						if (Conquer.movementOpen)
						{
							clearAttackingTable();
							Conquer.movementOpen = false;
							cancelMovementTim = true;
						}
						removeTask();
						colorPanel.rotateTasks();
						clearButtons();
						selected = null;
					}
				}
				if (id.equals("Pillage"))
				{
					if (!moving)
					{
						if (Conquer.movementOpen)
						{
							clearAttackingTable();
							Conquer.movementOpen = false;
							cancelMovementTim = true;
						}
					}
				}
				if (id.equals("Alert"))
				{
					if (!moving)
					{
						if (Conquer.movementOpen)
						{
							clearAttackingTable();
							Conquer.movementOpen = false;
							cancelMovementTim = true;
						}
						alert = true;
						sleeping = true;
						clearButtons();
						selected = null;
						removeTask();
						colorPanel.rotateTasks();
					}
				}
				if (id.equals("Sleep"))
				{
					if (!moving)
					{
						if (Conquer.movementOpen)
						{
							clearAttackingTable();
							Conquer.movementOpen = false;
							cancelMovementTim = true;
						}
						sleeping = true;
						removeTask();
						colorPanel.rotateTasks();
						clearButtons();
						selected = null;
					}
				}
				if (id.equals("Wake Up"))
				{
					if (!moving)
					{
						if (Conquer.movementOpen)
						{
							clearAttackingTable();
							Conquer.movementOpen = false;
							cancelMovementTim = true;
						}
						sleeping = false;
						alert = false;
						healing = false;
						addTask();
						colorPanel.rotateTasks();
						refreshButtons();
					}
				}
				if (id.equals("Heal"))
				{
					if (!moving)
					{
						if (Conquer.movementOpen)
						{
							clearAttackingTable();
							Conquer.movementOpen = false;
							cancelMovementTim = true;
						}
						healing = true;
						sleeping = true;
						removeTask();
						colorPanel.rotateTasks();
						clearButtons();
						selected = null;
					}
				}
			}
		}
	}
	private void beginMovementChecker(boolean attacking)
	{
		Point origin = new Point(selected.location);
		movementCheckerTim = new Timer();
		double mobility = this.mobility;
		double totalMobility = this.totalMobility;
		Unit unit = this;
		movementCheckerTim.scheduleAtFixedRate(new TimerTask(){
			Point currentPoint = new Point(origin);
			Point[] currentPath = new Point[0];
			public void run()
			{
				if (Conquer.scheduleMovement)
				{
					scheduleMovementAndMove(currentPath, attacking);
					cancelMovementTim = true;
					Conquer.scheduleMovement = false;
				}
				if (cancelMovementTim)
				{
					for (int i = 0; i < currentPath.length; i++)
					{
						colorPanel.movementPlan[currentPath[i].x][currentPath[i].y] = 0;
						colorPanel.movementPlanAngles[currentPath[i].x][currentPath[i].y] = 0;
					}
					colorPanel.refreshMovementTile(new Point[0]);
					cancelMovementTim = false;
					Conquer.movementOpen = false;
					movementCheckerTim = null;
					colorPanel.repaint();
					this.cancel();
					colorPanel.movementMap = null;
				}
				else
				{
					double positionX = 0;
					double positionY = 0;
					boolean passable = true;
					try
					{
						positionX = (MouseInfo.getPointerInfo().getLocation().x - colorPanel.getLocationOnScreen().x);
						positionY = (MouseInfo.getPointerInfo().getLocation().y - colorPanel.getLocationOnScreen().y);
					}
					catch(Exception e)
					{
						passable = false;
					}
					if (passable)
					{
						int newX = colorPanel.getColumns((int)positionX, (int)positionY, colorPanel.xOffset, colorPanel.yOffset);
						int newY = colorPanel.getRows((int)positionX, (int)positionY, colorPanel.xOffset, colorPanel.yOffset);
						if (!(newX == currentPoint.x && newY == currentPoint.y))
						{
							clearAttackingTable();
							currentPoint = new Point(newX, newY);
							if (currentPoint.x != origin.x || currentPoint.y != origin.y)
							{
								Point[] newPath = null;
								if ((!attacking || (colorPanel.warriorMap[currentPoint.x][currentPoint.y] != null && colorPanel.warriorMap[currentPoint.x][currentPoint.y].player != unit.player) || (colorPanel.workerMap[currentPoint.x][currentPoint.y] != null && colorPanel.workerMap[currentPoint.x][currentPoint.y].player != unit.player)))
								{
									if (attacking)
									{
										System.out.println("Attacking 1");
										newPath = colorPanel.getViablePathBetween(origin, currentPoint, 4,(int) mobility,(int) totalMobility, unit, attacking, (int) mobility);
									}
									else
									{
										newPath = colorPanel.getViablePathBetween(origin, currentPoint, 3,(int) mobility,(int) totalMobility, unit, attacking, 0);
									}
								}
								for (int i = 0; i < currentPath.length; i++)
								{
									colorPanel.movementPlan[currentPath[i].x][currentPath[i].y] = 0;
									colorPanel.movementPlanAngles[currentPath[i].x][currentPath[i].y] = 0;
								}
								if (newPath != null)
								{
									if (attacking)
									{
										Unit a = null;
										Unit b = null;
										Unit defendingUnit = null;
										double approxDamageDealtA = 0;
										double approxDamageDealtB = 0;
										if (colorPanel.warriorMap[currentPoint.x][currentPoint.y] != null)
										{
											defendingUnit = colorPanel.warriorMap[currentPoint.x][currentPoint.y];
											if (colorPanel.warriorMap[currentPoint.x][currentPoint.y].strength > unit.strength)
											{
												a = colorPanel.warriorMap[currentPoint.x][currentPoint.y];
												b = unit;
											}
											else
											{
												b = colorPanel.warriorMap[currentPoint.x][currentPoint.y];
												a = unit;
											}
											double r = a.strength / b.strength;
											double m = 0.5 + Math.pow(r + 3, 4) / 512;
											double minDamageB = 4 * m;
											double damageSpreadB = 4 * m;
											double minDamageA = 4 / m;
											double damageSpreadA = 4 / m;double damageDealtMaxA = (int) ((minDamageA + 0.9999999999 * damageSpreadA)) * (totalHealth / 10);
											double damageDealtMinA = minDamageA * 10;
											approxDamageDealtA = ((int)(0.5 * (damageDealtMaxA - damageDealtMinA + 1)) + damageDealtMinA) * (1 - (int)((totalHealth - b.health) / 2) / totalHealth);
											double damageDealtMaxB = (int) ((minDamageB + 0.9999999999 * damageSpreadB)) * (totalHealth / 10);
											double damageDealtMinB = minDamageB * 10;
											approxDamageDealtB = ((int)(0.5 * (damageDealtMaxB - damageDealtMinB + 1)) + damageDealtMinB) * (1 - (int)((totalHealth - a.health) / 2) / totalHealth);
										}
										else if (colorPanel.workerMap[currentPoint.x][currentPoint.y] != null)
										{
											a = unit;
											b = colorPanel.workerMap[currentPoint.x][currentPoint.y];
											defendingUnit = colorPanel.workerMap[currentPoint.x][currentPoint.y];
											approxDamageDealtA = 0;
											approxDamageDealtB = colorPanel.workerMap[currentPoint.x][currentPoint.y].health;
										}
										double attackingApproxDamage;
										double defendingApproxDamage;
										if (a == unit)
										{
											attackingApproxDamage = Math.round(approxDamageDealtA);
											defendingApproxDamage = Math.round(approxDamageDealtB);
										}
										else
										{
											attackingApproxDamage = Math.round(approxDamageDealtB);
											defendingApproxDamage = Math.round(approxDamageDealtA);
										}
										System.out.println("DefendingApproxDamage:" + defendingApproxDamage);
										double newAttackingHealth = health - attackingApproxDamage;
										double newDefendingHealth = defendingUnit.health - defendingApproxDamage;
										if (attackingApproxDamage > health)
										{
											attackingApproxDamage = health;
										}
										if (defendingApproxDamage > defendingUnit.health)
										{
											defendingApproxDamage = defendingUnit.health;
										}
										if (newAttackingHealth <= 0 && newDefendingHealth <= 0)
										{
											if (newAttackingHealth > newDefendingHealth)
											{
												newAttackingHealth = 1;
												attackingApproxDamage = health - 1;
											}
											else
											{
												newDefendingHealth = 1;
												defendingApproxDamage = defendingUnit.health - 1;
											}
										}
										attackingInfoPage = new Page(580, 110, colorPanel, new Point(110, 220));
										attackingInfoPage.backgroundVisibility = 0.70;
										attackingInfoPage.bottomOriented = true;
										attackingInfoPage.pushToBack();
										attackingInfoPage.setVisible(true);
										
										attackingApproxDamageDealtLabel = new Label("Approx. DMG Dealt:" + ((int) defendingApproxDamage), colorPanel, new Point(160, 200));
										attackingApproxDamageDealtLabel.bottomOriented = true;
										attackingApproxDamageDealtLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
										attackingApproxDamageDealtLabel.color = Color.white;
										attackingApproxDamageDealtLabel.setVisible(true);
										attackingUnitStrengthLabel = new Label("Your Strength:" + ((int) (strength * 10) / 10.0), colorPanel, new Point(175, 180));
										attackingUnitStrengthLabel.bottomOriented = true;
										attackingUnitStrengthLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
										attackingUnitStrengthLabel.color = Color.white;
										attackingUnitStrengthLabel.setVisible(true);
										
										defendingApproxDamageDealtLabel = new Label("Approx. DMG Dealt:" + ((int) attackingApproxDamage), colorPanel, new Point(465, 200));
										defendingApproxDamageDealtLabel.bottomOriented = true;
										defendingApproxDamageDealtLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
										defendingApproxDamageDealtLabel.color = Color.white;
										defendingApproxDamageDealtLabel.setVisible(true);
										defendingUnitStrengthLabel = new Label("Their Strength:" + ((int) (defendingUnit.strength * 10) / 10.0), colorPanel, new Point(478, 180));
										defendingUnitStrengthLabel.bottomOriented = true;
										defendingUnitStrengthLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
										defendingUnitStrengthLabel.color = Color.white;
										defendingUnitStrengthLabel.setVisible(true);
										
										attackingHealthBar = new Doodle(10, 80, colorPanel, new Point(385, 205), new BufferedImage(10, 80, BufferedImage.TYPE_INT_RGB));
										attackingHealthBar.bottomOriented = true;
										Graphics g = attackingHealthBar.createGraphics();
										g.setColor(new Color(100, 0, 0));
										g.fillRect(0, 80 - (int) (health / totalHealth * 80), 10, (int) (health / totalHealth * 80));
										g.setColor(new Color(0, 204, 0));
										g.fillRect(0, 80 - (int) (newAttackingHealth / totalHealth * 80), 10, (int) (newAttackingHealth / totalHealth * 80));
										g.dispose();
										attackingHealthBar.setVisible(true);
										defendingHealthBar = new Doodle(10, 80, colorPanel, new Point(405, 205), new BufferedImage(10, 80, BufferedImage.TYPE_INT_RGB));
										defendingHealthBar.bottomOriented = true;
										g = defendingHealthBar.createGraphics();
										g.setColor(new Color(100, 0, 0));
										g.fillRect(0, 80 - (int) (defendingUnit.health / totalHealth * 80), 10, (int) (defendingUnit.health / totalHealth * 80));
										g.setColor(new Color(0, 204, 0));
										g.fillRect(0, 80 - (int) (newDefendingHealth / totalHealth * 80), 10, (int) (newDefendingHealth / totalHealth * 80));
										g.dispose();
										defendingHealthBar.setVisible(true);
										
										defendingUnitInfoPage = new Page(310, 110, colorPanel, new Point(400, 110));
										defendingUnitInfoPage.backgroundVisibility = 0.96;
										defendingUnitInfoPage.bottomOriented = true;
										defendingUnitInfoPage.setVisible(true);
										defendingUnitNameLabel = new Label(defendingUnit.unitName, colorPanel, new Point(500, 105));
										defendingUnitNameLabel.color = Color.white;
										defendingUnitNameLabel.setFont(new Font("Rockwell", Font.BOLD, 23));
										defendingUnitNameLabel.setLocation(new Point(560 - unitNameLabel.getWidth() / 2, 105));
										defendingUnitNameLabel.bottomOriented = true;
										defendingUnitNameLabel.setVisible(true);
										defendingOwnerLabel = new Label("Owner: " + defendingUnit.owner.name(), colorPanel, new Point(455, 85));
										defendingOwnerLabel.color = Color.white;
										defendingOwnerLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
										defendingOwnerLabel.setLocation(new Point(560 - ownerLabel.getWidth() / 2, 85));
										defendingOwnerLabel.bottomOriented = true;
										defendingOwnerLabel.setVisible(true);
										defendingHealthStatLabel = new Label("Health: " + (int)defendingUnit.health + "/" + (int)totalHealth, colorPanel, new Point(455, 70));
										defendingHealthStatLabel.color = Color.white;
										defendingHealthStatLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
										defendingHealthStatLabel.setLocation(new Point(560 - healthStatLabel.getWidth() / 2, 70));
										defendingHealthStatLabel.bottomOriented = true;
										defendingHealthStatLabel.setVisible(true);
										defendingMobilityLabel = new Label("Mobility: " + (int)defendingUnit.mobility + "/" + (int)defendingUnit.totalMobility, colorPanel, new Point(455, 55));
										defendingMobilityLabel.color = Color.white;
										defendingMobilityLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
										defendingMobilityLabel.setLocation(new Point(560 - mobilityLabel.getWidth() / 2, 55));
										defendingMobilityLabel.bottomOriented = true;
										defendingMobilityLabel.setVisible(true);
										if (defendingUnit.warrior)
										{
											defendingCombatStrengthLabel = new Label("Combat Strength: " + (int)defendingUnit.strength, colorPanel, new Point(455, 40));
											defendingCombatStrengthLabel.color = Color.white;
											defendingCombatStrengthLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
											defendingCombatStrengthLabel.setLocation(new Point(560 - combatStrengthLabel.getWidth() / 2, 40));
											defendingCombatStrengthLabel.bottomOriented = true;
											defendingCombatStrengthLabel.setVisible(true);
										}
										if (defendingUnit.ranged)
										{
											defendingRangedStrengthLabel = new Label("Ranged Strength: " + (int)defendingUnit.rangedStrength, colorPanel, new Point(455, 25));
											defendingRangedStrengthLabel.color = Color.white;
											defendingRangedStrengthLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
											defendingRangedStrengthLabel.setLocation(new Point(560 - rangedStrengthLabel.getWidth() / 2, 25));
											defendingRangedStrengthLabel.bottomOriented = true;
											defendingRangedStrengthLabel.setVisible(true);
										}
									}
									System.out.println("Controls: " + Control.getControlsLength());
									int turn = 1;
									double currentMobility = mobility;
									if (currentMobility <= 0)
									{
										currentMobility = totalMobility;
										turn++;
									}
									for (int i = 0; i < newPath.length - 1; i++)
									{
										int direction = getAngle(newPath[i].x, newPath[i].y, newPath[i + 1].x, newPath[i + 1].y);
										double angle = ((direction + 2) % 6) / 6.0 * 2 * Math.PI;
										colorPanel.movementPlanAngles[newPath[i].x][newPath[i].y] = angle;
										currentMobility--;
										if (currentMobility > 0)
										{
											if (turn == 1)
											{
												colorPanel.movementPlan[newPath[i].x][newPath[i].y] = -1;
											}		
											else if (turn == 2)
											{
												colorPanel.movementPlan[newPath[i].x][newPath[i].y] = -2;
											}		
											else
											{
												colorPanel.movementPlan[newPath[i].x][newPath[i].y] = -3;
											}	
										}
										if (currentMobility <= 0)
										{
											colorPanel.movementPlan[newPath[i].x][newPath[i].y] = turn;
											if (attacking && i == newPath.length - 2)
											{
												colorPanel.movementPlan[newPath[i].x][newPath[i].y] = -5;
											}
											turn+= 1;
											currentMobility = totalMobility;
										}	
										else if (i == newPath.length - 2)
										{
											colorPanel.movementPlan[newPath[i].x][newPath[i].y] = turn;
											if (attacking)
											{
												colorPanel.movementPlan[newPath[i].x][newPath[i].y] = -5;
											}
										}
									}
									colorPanel.refreshMovementTile(newPath);
								}
								else
								{
									System.out.println("Creating red Circle");
									colorPanel.movementPlan[currentPoint.x][currentPoint.y] = -4;
									newPath = new Point[1];
									newPath[0] = new Point(currentPoint);
									colorPanel.refreshMovementTile(newPath);
								}
								currentPath = newPath;
								colorPanel.repaint();
							}
							else
							{
								currentPath = new Point[0];
								colorPanel.refreshMovementTile(currentPath);
								colorPanel.repaint();
							}
						}
					}
				}
			}
			
		}, 200, 100);
	}
	public void meleeAttack()
	{
		beginMovementChecker(true);
		Conquer.movementOpen = true;
	}
	private static Point getUnitLocation(Unit unit, Point location)
	{
		double newXOffset;
		double newYOffset;
		if (location.x % 2 == 1)
		{
			newXOffset = (location.x - 1) / 2 * unit.colorPanel.xDistanceIncrementer;
			newYOffset = location.y * unit.colorPanel.sideLength * 3 / 2 - unit.colorPanel.sideLength * 3 / 2;
		}
		else
		{
			newXOffset = (location.x) / 2 * unit.colorPanel.xDistanceIncrementer - unit.colorPanel.xDistanceIncrementer / 2;
			newYOffset = location.y * unit.colorPanel.sideLength * 3 / 2 - unit.colorPanel.sideLength * 3 / 2;
		}
		newXOffset+= unit.colorPanel.xOffset + unit.colorPanel.xDistanceIncrementer / 2;
		newYOffset+= unit.colorPanel.yOffset + unit.colorPanel.sideLength * 2 / 3;
		if (newXOffset > unit.colorPanel.getWidth())
		{
			if (location.x  % 2 == 1)
			{
				newXOffset = (location.x  - 1 - Conquer.gameScreenSizeX) / 2 * unit.colorPanel.xDistanceIncrementer;
			}
			else
			{
				newXOffset = (location.x  - Conquer.gameScreenSizeX) / 2 * unit.colorPanel.xDistanceIncrementer - unit.colorPanel.xDistanceIncrementer / 2;
			}
			newXOffset+= unit.colorPanel.xOffset + unit.colorPanel.xDistanceIncrementer / 2;
		}
		if (newXOffset < -100)
		{
			if (location.x % 2 == 1)
			{
				newXOffset = (location.x - 1 + Conquer.gameScreenSizeX) / 2 * unit.colorPanel.xDistanceIncrementer;
			}
			else
			{
				newXOffset = (location.x + Conquer.gameScreenSizeX) / 2 * unit.colorPanel.xDistanceIncrementer - unit.colorPanel.xDistanceIncrementer / 2;
			}
			newXOffset+= unit.colorPanel.xOffset + unit.colorPanel.xDistanceIncrementer / 2;
		}
		return new Point((int)newXOffset, (int)newYOffset);
	}
	public void scheduleMovementAndMove(Point[] path, boolean attacking)
	{
		moving = true;
		int finalIndex = -2;
		for (int i = 0; i < path.length; i++)
		{
			if (colorPanel.movementPlan[path[i].x][path[i].y] != 1 && colorPanel.movementPlan[path[i].x][path[i].y] != -1 && colorPanel.movementPlan[path[i].x][path[i].y] != -5)
			{
				break;
			}
			finalIndex = i;
		}
		Point[] movementPath = new Point[finalIndex + 2];
		if (finalIndex + 2 > path.length)
		{
			movementPath = new Point[path.length];
		}
		for (int i = 0; i < movementPath.length; i++)
		{
			movementPath[i] = path[i];
		}
		Unit unit = this;
		Timer tim = new Timer();
		for (int i = 0; i < movementPath.length; i++)
		{
			System.out.println(movementPath[i].x + " " + movementPath[i].y);
		}
		if (movementPath.length > 1)
		{
			this.dispose();
			if (unit.warrior)
			{
				colorPanel.warriorMap[this.location.x][this.location.y] = null;
			}
			else
			{
				colorPanel.workerMap[this.location.x][this.location.y] = null;
			}
			for (int i = 1; i < movementPath.length; i++)
			{
				if (colorPanel.upperGeographicMap[movementPath[i].x][movementPath[i].y] == ColorPanel.upperGeography.hills || colorPanel.upperGeographicMap[movementPath[i].x][movementPath[i].y] == ColorPanel.upperGeography.hills || colorPanel.upperGeographicMap[movementPath[i].x][movementPath[i].y] == ColorPanel.upperGeography.snowHills || colorPanel.upperGeographicMap[movementPath[i].x][movementPath[i].y] == ColorPanel.upperGeography.marsh || colorPanel.upperGeographicMap[movementPath[i].x][movementPath[i].y] == ColorPanel.upperGeography.forest || colorPanel.upperGeographicMap[movementPath[i].x][movementPath[i].y] == ColorPanel.upperGeography.jungle)
				{
					mobility-=2;
				}
				else
				{
					mobility--;
				}
				if (mobility < 1)
				{
					mobility = 0;
				}
			}
			if (attacking)
			{
				mobility = 0;
			}
			refreshButtons();
			selected = this;
			BufferedImage movingUnitDrawing = new BufferedImage(colorPanel.mainMap.getWidth(), colorPanel.mainMap.getHeight(), BufferedImage.TRANSLUCENT);
			Graphics g = movingUnitDrawing.createGraphics();
			Point p = getUnitLocationOnMainMap(this, location);
			this.paint(g, p.x, p.y);
			g.dispose();
			g = colorPanel.unitMap.createGraphics();
			g.setColor(new Color(0,0,0, (float)0));
			Composite c = ((Graphics2D)g).getComposite();
			((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
			g.fillRect(p.x, p.y, (int)colorPanel.xDistanceIncrementer, (int)colorPanel.sideLength * 3 / 2);
			((Graphics2D)g).setComposite(c);
			if (colorPanel.workerMap[location.x][location.y] != null)
			{
				if (colorPanel.warriorMap[location.x][location.y] != null)
				{
					colorPanel.workerMap[location.x][location.y].paint(g, p.x, p.y + 10);
					Unit.units.add(colorPanel.workerMap[location.x][location.y]);
				}
				else
				{
					colorPanel.workerMap[location.x][location.y].paint(g, p.x, p.y);
					Unit.units.add(colorPanel.workerMap[location.x][location.y]);
				}
			}
			if (colorPanel.warriorMap[location.x][location.y] != null)
			{
				colorPanel.warriorMap[location.x][location.y].paint(g, p.x, p.y);
				Unit.units.add(colorPanel.warriorMap[location.x][location.y]);
			}
			g.dispose();
			if (colorPanel.mainMapDrawing != null)
			{
				colorPanel.unitLocationsNeededToClear.add(location);
			}
			Timer paintTim = new Timer();
			MovingUnitTask movingUnitTask = new MovingUnitTask(movementPath, p, unit, tim, paintTim, attacking);
			tim.scheduleAtFixedRate(movingUnitTask, 30, 30);
			paintTim.scheduleAtFixedRate(new TimerTask()
			{
				public void run()
				{
					BufferedImage movingUnitDrawing = new BufferedImage(colorPanel.mainMap.getWidth(), colorPanel.mainMap.getHeight(), BufferedImage.TRANSLUCENT);
					Graphics g = movingUnitDrawing.createGraphics();
					unit.paint(g, movingUnitTask.position.x, movingUnitTask.position.y);
					g.dispose();
					colorPanel.movingUnitImage = movingUnitDrawing;
					if (movingUnitTask.canceled)
					{
						colorPanel.movingUnitImage = null;
					}
					colorPanel.repaint();
				}
			}, 32, 30);
		}
		else
		{
			moving = false;
		}
	}
	private class MovingUnitTask extends TimerTask
	{
		Point[] movementPath;
		Point position;
		Unit unit;
		Timer tim;
		Timer paintTim;
		boolean attacking = false;
		boolean canceled = false;
		public MovingUnitTask(Point[] movementPath, Point position, Unit unit, Timer tim, Timer paintTim, boolean attacking)
		{
			super();
			this.movementPath = movementPath;
			this.position = position;
			this.unit = unit;
			this.tim = tim;
			this.paintTim = paintTim;
			this.attacking = attacking;
		}
		final double totalDivisor = 30;
		double currentMultiplier = 0;
		int currentPathIndex = 0;
		public void run()
		{
			if (movementPath[currentPathIndex].x == movementPath[currentPathIndex + 1].x && movementPath[currentPathIndex].y == movementPath[currentPathIndex + 1].y)
			{
				currentPathIndex++;
			}
			if (attacking && currentPathIndex == movementPath.length - 2)
			{
				if (colorPanel.warriorMap[movementPath[currentPathIndex + 1].x][movementPath[currentPathIndex + 1].y] != null)
				{
					Unit a;
					Unit b;
					if (colorPanel.warriorMap[movementPath[currentPathIndex + 1].x][movementPath[currentPathIndex + 1].y].strength > unit.strength)
					{
						a = colorPanel.warriorMap[movementPath[currentPathIndex + 1].x][movementPath[currentPathIndex + 1].y];
						b = unit;
					}
					else
					{
						b = colorPanel.warriorMap[movementPath[currentPathIndex + 1].x][movementPath[currentPathIndex + 1].y];
						a = unit;
					}
					double r = a.strength / b.strength;
					double m = 0.5 + Math.pow(r + 3, 4) / 512;
					double minDamageB = 4 * m;
					double damageSpreadB = 4 * m;
					double minDamageA = 4 / m;
					double damageSpreadA = 4 / m;double damageDealtMaxA = (int) ((minDamageA + 0.9999999999 * damageSpreadA)) * (totalHealth / 10);
					double damageDealtMinA = minDamageA * 10;
					double damageDealtA = ((int)(Math.random() * (damageDealtMaxA - damageDealtMinA + 1)) + damageDealtMinA) * (1 - (int)((totalHealth - b.health) / 2) / totalHealth);
					double damageDealtMaxB = (int) ((minDamageB + 0.9999999999 * damageSpreadB)) * (totalHealth / 10);
					double damageDealtMinB = minDamageB * 10;
					double damageDealtB = ((int)(Math.random() * (damageDealtMaxB - damageDealtMinB + 1)) + damageDealtMinB) * (1 - (int)((totalHealth - a.health) / 2) / totalHealth);
					System.out.println("Damage DealtA: " + damageDealtA);
					System.out.println("Damage DealtB: " + damageDealtB);
					a.health-= damageDealtA;
					b.health-= damageDealtB;
					if (a.health <= 0 && b.health <= 0)
					{
						if (unit.health > colorPanel.warriorMap[movementPath[currentPathIndex + 1].x][movementPath[currentPathIndex + 1].y].health)
						{
							unit.health = 1;
						}
						else
						{
							colorPanel.warriorMap[movementPath[currentPathIndex + 1].x][movementPath[currentPathIndex + 1].y].health = 1;
						}
					}
					if (colorPanel.warriorMap[movementPath[currentPathIndex + 1].x][movementPath[currentPathIndex + 1].y].health <= 0)
					{
						colorPanel.warriorMap[movementPath[currentPathIndex + 1].x][movementPath[currentPathIndex + 1].y].kill();
					}
					else
					{
						movementPath[currentPathIndex + 1] = movementPath[currentPathIndex];
						currentMultiplier = totalDivisor - 1;
					}
					colorPanel.refreshTile(movementPath[currentPathIndex + 1].x, movementPath[currentPathIndex + 1].y);
				}
				else if (colorPanel.workerMap[movementPath[currentPathIndex + 1].x][movementPath[currentPathIndex + 1].y] != null)
				{
					colorPanel.workerMap[movementPath[currentPathIndex + 1].x][movementPath[currentPathIndex + 1].y].player = unit.player;
					colorPanel.workerMap[movementPath[currentPathIndex + 1].x][movementPath[currentPathIndex + 1].y].owner = unit.owner;
					colorPanel.refreshTile(movementPath[currentPathIndex + 1].x, movementPath[currentPathIndex + 1].y);
				}
			}
			currentMultiplier++;
			Point getCurrentLocation = getUnitLocationOnMainMap(unit, movementPath[currentPathIndex]);
			Point getNextLocation = getUnitLocationOnMainMap(unit, movementPath[currentPathIndex + 1]);	
			position = new Point((int) (getCurrentLocation.x + ((getNextLocation.x - getCurrentLocation.x) * currentMultiplier / totalDivisor)), (int) (getCurrentLocation.y + ((getNextLocation.y - getCurrentLocation.y) * currentMultiplier / totalDivisor)));
			if (unit.health <= 0)
			{
				System.out.println("HEYYYY");
				unit.kill();
				clearButtons();
				selected = null;
				colorPanel.movingUnitImage = null;
				currentMultiplier = totalDivisor + 1;
				canceled = true;
				colorPanel.repaint();
				moving = false;
				paintTim.cancel();
				this.cancel();
			}
			if (currentMultiplier == totalDivisor)
			{
				if (currentPathIndex < movementPath.length - 2)
				{
					currentMultiplier = 0;
					currentPathIndex++;
				}
				else
				{
					tim.cancel();
					paintTim.cancel();
					int[][] tiles = ColorPanel.getAllTilesWithin(location.x, location.y, sight);
					for (int i = 0; i < tiles[0].length; i++)
					{
						colorPanel.fogOfWar[tiles[0][i]][tiles[1][i]]--;
						colorPanel.refreshTile(tiles[0][i], tiles[1][i]);
					}
					colorPanel.fogOfWar[location.x][location.y]--;
					colorPanel.refreshTile(location.x, location.y);
					unit.location = movementPath[currentPathIndex + 1];
					if (unit.warrior)
					{
						colorPanel.warriorMap[unit.location.x][unit.location.y] = unit;
					}
					else
					{
						colorPanel.workerMap[unit.location.x][unit.location.y] = unit;
					}
					for (int i = 0; i < colorPanel.players[colorPanel.turn].toDoList.size(); i++)
					{
						if (UnitTask.class == colorPanel.players[colorPanel.turn].toDoList.get(i).getClass())
						{
							if (((UnitTask) colorPanel.players[colorPanel.turn].toDoList.get(i)).unit == unit)
							{
								if (unit.mobility <= 0)
								{
									colorPanel.players[colorPanel.turn].toDoList.remove(i);
									colorPanel.rotateTasks();
								}
								else
								{
									colorPanel.players[colorPanel.turn].toDoList.get(i).tilePositionOfTask = unit.location;
								}
							}
						}
					}
					units.add(unit);
					Graphics g = colorPanel.unitMap.createGraphics();
					position = getUnitLocationOnMainMap(unit, location);
					if (colorPanel.workerMap[location.x][location.y] != null)
					{
						if (colorPanel.warriorMap[location.x][location.y] != null)
						{
							colorPanel.workerMap[location.x][location.y].paint(g, position.x, position.y + 10);
							Unit.units.add(colorPanel.workerMap[location.x][location.y]);
						}
						else
						{
							colorPanel.workerMap[location.x][location.y].paint(g, position.x, position.y);
							Unit.units.add(colorPanel.workerMap[location.x][location.y]);
						}
					}
					if (colorPanel.warriorMap[location.x][location.y] != null)
					{
						colorPanel.warriorMap[location.x][location.y].paint(g, position.x, position.y);
						Unit.units.add(colorPanel.warriorMap[location.x][location.y]);
					}
					g.dispose();
					if (colorPanel.mainMapDrawing != null)
					{
						colorPanel.unitsNeededToRefresh.add(unit);
					}
					tiles = ColorPanel.getAllTilesWithin(location.x, location.y, sight);
					for (int i = 0; i < tiles[0].length; i++)
					{
						colorPanel.fogOfWar[tiles[0][i]][tiles[1][i]]++;
						colorPanel.discoveredTiles[tiles[0][i]][tiles[1][i]] = true;
						colorPanel.setFarthestOut(tiles[0][i], tiles[1][i]);
						colorPanel.refreshTile(tiles[0][i], tiles[1][i]);
					}
					colorPanel.fogOfWar[location.x][location.y]++;
					colorPanel.discoveredTiles[location.x][location.y] = true;
					colorPanel.refreshTile(location.x, location.y);
					colorPanel.repaint();
					moving = false;
					colorPanel.movingUnitImage = null;
					canceled = true;
					colorPanel.developeMiniMapOffThread();
				}
			}
		}
	}
	public static Point getUnitLocationOnMainMap(Unit unit, Point location)
	{
		double newXOffset;
		double newYOffset;
		if (location.x % 2 == 1)
		{
			newXOffset = (location.x - 1) / 2 * unit.colorPanel.xDistanceIncrementer;
			newYOffset = location.y * unit.colorPanel.sideLength * 3 / 2 - unit.colorPanel.sideLength * 3 / 2;
		}
		else
		{
			newXOffset = (location.x) / 2 * unit.colorPanel.xDistanceIncrementer - unit.colorPanel.xDistanceIncrementer / 2;
			newYOffset = location.y * unit.colorPanel.sideLength * 3 / 2 - unit.colorPanel.sideLength * 3 / 2;
		}
		newXOffset+= unit.colorPanel.mainMapImageOffsetX;
		newYOffset+= unit.colorPanel.mainMapImageOffsetY;
		if (newXOffset > unit.colorPanel.mainMap.getWidth() + 100)
		{
			if (location.x  % 2 == 1)
			{
				newXOffset = (location.x  - 1 - Conquer.gameScreenSizeX) / 2 * unit.colorPanel.xDistanceIncrementer;
			}
			else
			{
				newXOffset = (location.x  - Conquer.gameScreenSizeX) / 2 * unit.colorPanel.xDistanceIncrementer - unit.colorPanel.xDistanceIncrementer / 2;
			}
			newXOffset+= unit.colorPanel.mainMapImageOffsetX;
		}
		if (newXOffset < -100)
		{
			if (location.x % 2 == 1)
			{
				newXOffset = (location.x - 1 + Conquer.gameScreenSizeX) / 2 * unit.colorPanel.xDistanceIncrementer;
			}
			else
			{
				newXOffset = (location.x + Conquer.gameScreenSizeX) / 2 * unit.colorPanel.xDistanceIncrementer - unit.colorPanel.xDistanceIncrementer / 2;
			}
			newXOffset+= unit.colorPanel.mainMapImageOffsetX;
		}
		return new Point((int)newXOffset, (int)newYOffset);
	}
	private int getAngle(int iX, int iY, int fX, int fY)
	{
		if (colorPanel.getTopRightTile(iX, iY)[0] == fY && colorPanel.getTopRightTile(iX, iY)[1] == fX)
		{
			return 5;
		}
		if (colorPanel.getRightTile(iX, iY)[0] == fY && colorPanel.getRightTile(iX, iY)[1] == fX)
		{
			return 4;
		}
		if (colorPanel.getBottomRightTile(iX, iY)[0] == fY && colorPanel.getBottomRightTile(iX, iY)[1] == fX)
		{
			return 3;
		}
		if (colorPanel.getBottomLeftTile(iX, iY)[0] == fY && colorPanel.getBottomLeftTile(iX, iY)[1] == fX)
		{
			return 2;
		}
		if (colorPanel.getLeftTile(iX, iY)[0] == fY && colorPanel.getLeftTile(iX, iY)[1] == fX)
		{
			return 1;
		}
		if (colorPanel.getTopLeftTile(iX, iY)[0] == fY && colorPanel.getTopLeftTile(iX, iY)[1] == fX)
		{
			return 0;
		}
		return -1;
	}
	protected void utilityAction()
	{
		
	}
	public void kill()
	{
		Point p = getUnitLocationOnMainMap(this, location);
		if (warrior)
		{
			colorPanel.warriorMap[location.x][location.y] = null;
		}
		else
		{
			colorPanel.workerMap[location.x][location.y] = null;
		}
		removeTask();
		colorPanel.rotateTasks();
		player.units.remove(this);
		Unit.units.remove(this);
		if (colorPanel.players[colorPanel.turn] == player)
		{
			int[][] tiles = ColorPanel.getAllTilesWithin(location.x, location.y, sight);
			for (int i = 0; i < tiles[0].length; i++)
			{
				colorPanel.fogOfWar[tiles[0][i]][tiles[1][i]]--;
				colorPanel.refreshTile(tiles[0][i], tiles[1][i]);
			}
			colorPanel.fogOfWar[location.x][location.y]--;
			colorPanel.refreshTile(location.x, location.y);
		}
		Graphics g = colorPanel.unitMap.createGraphics();
		g.setColor(new Color(0,0,0, (float)0));
		Composite c = ((Graphics2D)g).getComposite();
		((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		g.fillRect(p.x, p.y, (int)colorPanel.xDistanceIncrementer, (int)colorPanel.sideLength * 3 / 2);
		((Graphics2D)g).setComposite(c);
		if (colorPanel.workerMap[location.x][location.y] != null)
		{
			if (colorPanel.warriorMap[location.x][location.y] != null)
			{
				colorPanel.workerMap[location.x][location.y].paint(g, p.x, p.y + 10);
				Unit.units.add(colorPanel.workerMap[location.x][location.y]);
			}
			else
			{
				colorPanel.workerMap[location.x][location.y].paint(g, p.x, p.y);
				Unit.units.add(colorPanel.workerMap[location.x][location.y]);
			}
		}
		if (colorPanel.warriorMap[location.x][location.y] != null)
		{
			colorPanel.warriorMap[location.x][location.y].paint(g, p.x, p.y);
			Unit.units.add(colorPanel.warriorMap[location.x][location.y]);
		}
		g.dispose();
		if (colorPanel.mainMapDrawing != null)
		{
			colorPanel.unitLocationsNeededToClear.add(location);
		}
	}
	private void addTask()
	{
		player.toDoList.add(new UnitTask(colorPanel, this));
	}
	public void removeTask()
	{
		for (int i = 0; i < colorPanel.players[colorPanel.turn].toDoList.size(); i++)
		{
			if (UnitTask.class == colorPanel.players[colorPanel.turn].toDoList.get(i).getClass())
			{
				if (((UnitTask) colorPanel.players[colorPanel.turn].toDoList.get(i)).unit == this)
				{
					colorPanel.players[colorPanel.turn].toDoList.remove(i);
				}
			}
		}
	}
	public void clearAttackingTable()
	{
		if (attackingInfoPage != null)
		{
			attackingInfoPage.dispose();
			attackingInfoPage = null;
		}
		if (attackingApproxDamageDealtLabel != null)
		{
			attackingApproxDamageDealtLabel.dispose();
			attackingApproxDamageDealtLabel = null;
		}
		if (attackingUnitStrengthLabel != null)
		{
			attackingUnitStrengthLabel.dispose();
			attackingUnitStrengthLabel = null;
		}
		if (defendingApproxDamageDealtLabel != null)
		{
			defendingApproxDamageDealtLabel.dispose();
			defendingApproxDamageDealtLabel = null;
		}
		if (defendingUnitStrengthLabel != null)
		{
			defendingUnitStrengthLabel.dispose();
			defendingUnitStrengthLabel = null;
		}
		if (attackingHealthBar != null)
		{
			attackingHealthBar.dispose();
			attackingHealthBar = null;
		}
		if (defendingHealthBar != null)
		{
			defendingHealthBar.dispose();
			defendingHealthBar = null;
		}
		if (defendingUnitInfoPage != null)
		{
			defendingUnitInfoPage.dispose();
			defendingUnitInfoPage = null;
		}
		if (defendingUnitNameLabel != null)
		{
			defendingUnitNameLabel.dispose();
			defendingUnitNameLabel = null;
		}
		if (defendingOwnerLabel != null)
		{
			defendingOwnerLabel.dispose();
			defendingOwnerLabel = null;
		}
		if (defendingHealthStatLabel != null)
		{
			defendingHealthStatLabel.dispose();
			defendingHealthStatLabel = null;
		}
		if (defendingMobilityLabel != null)
		{
			defendingMobilityLabel.dispose();
			defendingMobilityLabel = null;
		}
		if (defendingCombatStrengthLabel != null)
		{
			defendingCombatStrengthLabel.dispose();
			defendingCombatStrengthLabel = null;
		}
		if (defendingRangedStrengthLabel != null)
		{
			defendingRangedStrengthLabel.dispose();
			defendingRangedStrengthLabel = null;
		}
	}
}