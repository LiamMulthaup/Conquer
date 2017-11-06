import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.RoundRectangle2D;

import ui.Button;
import ui.Control;
import ui.ControlHandler;
import ui.ImageBox;
import ui.Label;
import ui.ListBox;
import ui.Page;

public class CityTag extends RoundRectangle2D.Double 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5236914160744850300L;
	static City selectedCity;
	City city;
	ColorPanel colorPanel;
	CityTagControlHandler controlHandler = new CityTagControlHandler();
	ProductionType chosenProduction;
	public CityTag(ColorPanel colorPanel)
	{
		this.colorPanel = colorPanel;
	}
	public static void disposeProductionElements()
	{
		if (selectedCity != null)
		{
			if (selectedCity.cityProductionBackground != null)
			{
				selectedCity.cityProductionBackground.dispose();
			}
			if (selectedCity.cityProductionContainer != null)
			{
				selectedCity.cityProductionContainer = null;
			}
			if (selectedCity.cityProductionList != null)
			{
				selectedCity.cityProductionList = null;
			}
			if (selectedCity.productionLabel != null)
			{
				selectedCity.productionLabel = null;
			}
			if (selectedCity.chosenProductionIcon != null)
			{
				selectedCity.chosenProductionIcon = null;
			}
			if (selectedCity.costsLabel != null)
			{
				selectedCity.costsLabel = null;
			}
			if (selectedCity.resourceCost != null)
			{
				selectedCity.resourceCost = null;
			}
			if (selectedCity.productionCost != null)
			{
				selectedCity.productionCost = null;
			}
			if (selectedCity.turnCostLabel != null)
			{
				selectedCity.turnCostLabel = null;
			}
			if (selectedCity.productionIncomeLabel != null)
			{
				selectedCity.productionIncomeLabel = null;
			}
			if (selectedCity.goldIncomeLabel != null)
			{
				selectedCity.goldIncomeLabel = null;
			}
			if (selectedCity.foodIncomeLabel != null)
			{
				selectedCity.foodIncomeLabel = null;
			}
			if (selectedCity.scienceIncomeLabel != null)
			{
				selectedCity.scienceIncomeLabel = null;
			}
			if (selectedCity.cultureIncomeLabel != null)
			{
				selectedCity.cultureIncomeLabel = null;
			}
			if (selectedCity.defenseLabel != null)
			{
				selectedCity.defenseLabel = null;
			}
			if (selectedCity.unitOrBuildingDescription != null)
			{
				selectedCity.unitOrBuildingDescription = null;
			}
			if (selectedCity.produceButton != null)
			{
				selectedCity.produceButton = null;
			}
			if (selectedCity.cityCurrentProductionPage != null)
			{
				selectedCity.cityCurrentProductionPage = null;
			}
		}
	}
	public void clickEvent()
	{
		System.out.println("Working...");
		System.out.println(selectedCity == null);
		System.out.println(city == null);
		System.out.println(city == selectedCity);
		if (selectedCity != city)
		{
			System.out.println("Passed Test");
			disposeProductionElements();
			selectedCity = city;
			city.cityProductionBackground = new Page(colorPanel.getWidth() + 6, colorPanel.getHeight() + 6, colorPanel, new Point(-3, -3));
			city.cityProductionBackground.roundedEdges = false;
			city.cityProductionBackground.color = new Color(0, 0, 0);
			city.cityProductionBackground.id = "Background";
			city.cityProductionBackground.controlHandler = controlHandler;
			city.cityProductionBackground.backgroundVisibility = 0.6;
			city.cityProductionBackground.setVisible(true);
			city.cityProductionContainer = new Page(400, 560, colorPanel, new Point(colorPanel.getWidth() / 2 - 400 / 2, colorPanel.getHeight() / 2 - 560 / 2));
			city.cityProductionBackground.add(city.cityProductionContainer);
			city.cityProductionList = new ListBox(215, 400, colorPanel, new Point(20, 100));
			city.cityProductionList.scrollBar.setSize(10, city.cityProductionList.scrollBar.getHeight());
			city.cityProductionList.id = "Build Option";
			city.cityProductionList.controlHandler = controlHandler;
			city.cityProductionContainer.add(city.cityProductionList);
			city.cityProductionContainer.setVisible(true);
			city.cityProductionContainer.color = new Color(129, 131, 203);
			city.cityProductionContainer.backgroundVisibility = 0.85;
			city.cityProductionList.backgroundColor = new Color(0, 0, 0);
			city.cityProductionList.backgroundVisibility = 0.5;
			city.cityProductionList.setFont(new Font("Rockwell", Font.PLAIN, 15));
			city.cityProductionList.setVisible(true);
			city.cityProductionList.bringUp();
			city.productionLabel = new Label(city.name, colorPanel, new Point(30, 15));
			city.cityProductionContainer.add(city.productionLabel);
			city.productionLabel.color = new Color(255, 255, 255);
			city.productionLabel.setVisible(true);
			city.productionLabel.setFont(new Font("Rockwell", Font.BOLD, 40));
			city.productionIncomeLabel = new Label("Production: " + city.production, colorPanel, new Point(30, 55));
			city.productionIncomeLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
			city.cityProductionContainer.add(city.productionIncomeLabel);
			city.productionIncomeLabel.color = new Color(255, 255, 255);
			city.productionIncomeLabel.setVisible(true);
			city.goldIncomeLabel = new Label("Gold: " + city.income, colorPanel, new Point(30, 75));
			city.goldIncomeLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
			city.cityProductionContainer.add(city.goldIncomeLabel);
			city.goldIncomeLabel.color = new Color(255, 255, 255);
			city.goldIncomeLabel.setVisible(true);
			city.foodIncomeLabel = new Label("Food: " + city.food, colorPanel, new Point(175, 55));
			city.foodIncomeLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
			city.cityProductionContainer.add(city.foodIncomeLabel);
			city.foodIncomeLabel.color = new Color(255, 255, 255);
			city.foodIncomeLabel.setVisible(true);
			city.defenseLabel = new Label("Defense: " + city.defense, colorPanel, new Point(175, 75));
			city.defenseLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
			city.cityProductionContainer.add(city.defenseLabel);
			city.defenseLabel.color = new Color(255, 255, 255);
			city.defenseLabel.setVisible(true);
			city.scienceIncomeLabel = new Label("Science: " + city.science, colorPanel, new Point(290, 55));
			city.scienceIncomeLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
			city.cityProductionContainer.add(city.scienceIncomeLabel);
			city.scienceIncomeLabel.color = new Color(255, 255, 255);
			city.scienceIncomeLabel.setVisible(true);
			city.cultureIncomeLabel = new Label("Culture: " + city.culture, colorPanel, new Point(290, 75));
			city.cultureIncomeLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
			city.cityProductionContainer.add(city.cultureIncomeLabel);
			city.cultureIncomeLabel.color = new Color(255, 255, 255);
			city.cultureIncomeLabel.setVisible(true);
			if (city.currentProduce != null)
			{
				city.cityCurrentProductionPage = new Page(300, 100, colorPanel, new Point(colorPanel.getWidth() / 2 - 400 / 2 + city.cityProductionContainer.getWidth() + 30, colorPanel.getHeight() / 2 - 560 / 2));
				city.cityProductionBackground.add(city.cityCurrentProductionPage);
				city.cityCurrentProductionPage.color = new Color(40, 40, 193);
				city.cityCurrentProductionPage.backgroundVisibility = 0.70;
				Label currentProduceLabel = new Label(city.currentProduce.productionName, colorPanel, new Point(130, 30));
				Label currentProduceTurnsLabel = new Label("Turns: " + city.currentProduce.turnsLeft, colorPanel, new Point(130, 60));
				if (city.currentProduce.turnsLeft == 0)
				{
					currentProduceTurnsLabel.setText("Unit is Blocked");
					currentProduceTurnsLabel.setLocation(new Point(currentProduceTurnsLabel.getLocation().x - 40, currentProduceTurnsLabel.getLocation().y));
				}
				ImageBox currentProduceImage = new ImageBox(80, 80, colorPanel, new Point(10, 10), city.currentProduce.productionImage);
				currentProduceLabel.color = Color.white;
				currentProduceTurnsLabel.color = Color.white;
				currentProduceLabel.setFont(new Font("Rockwell", Font.PLAIN, 25));
				currentProduceTurnsLabel.setFont(new Font("Rockwell", Font.PLAIN, 25));
				city.cityCurrentProductionPage.add(currentProduceLabel);
				city.cityCurrentProductionPage.add(currentProduceTurnsLabel);
				city.cityCurrentProductionPage.add(currentProduceImage);
				currentProduceLabel.setVisible(true);
				currentProduceTurnsLabel.setVisible(true);
				currentProduceImage.setVisible(true);
				city.cityCurrentProductionPage.setVisible(true);
			}
			colorPanel.productionOpen = true;
			if (true)
			{
				city.cityProductionList.add("Settler");
				city.cityProductionList.add("Worker");
				city.cityProductionList.add("Warrior");
				city.cityProductionList.add("Scout");
				city.cityProductionList.add("Archer");
				city.cityProductionList.add("Trireme");
				city.cityProductionList.add("Chariot Archer");
				city.cityProductionList.add("Spearman");
				city.cityProductionList.add("Horseman");
				city.cityProductionList.add("Catapult");
				city.cityProductionList.add("Swordsman");
				city.cityProductionList.add("Pikeman");
				city.cityProductionList.add("Knight");
				city.cityProductionList.add("Crossbowman");
				city.cityProductionList.add("Trebuchet");
				city.cityProductionList.add("Longswordsman");
				city.cityProductionList.add("Caravel");
				city.cityProductionList.add("Musketman");
				city.cityProductionList.add("Frigate");
				city.cityProductionList.add("Cannon");
				city.cityProductionList.add("Lancer");
				city.cityProductionList.add("Cavalry");
				city.cityProductionList.add("Rifleman");
				city.cityProductionList.add("Ironclad");
				city.cityProductionList.add("Anti-Tank Gun");
				city.cityProductionList.add("Infantry");
				city.cityProductionList.add("Artillery");
				city.cityProductionList.add("Submarine");
				city.cityProductionList.add("Battleship");
				city.cityProductionList.add("Anti-Aircraft Gun");
				city.cityProductionList.add("Carrier");
				city.cityProductionList.add("Fighter");
				city.cityProductionList.add("Destroyer");
				city.cityProductionList.add("Tank");
				city.cityProductionList.add("Mechanized Infantry");
				city.cityProductionList.add("Bomber");
				city.cityProductionList.add("Paratrooper");
				city.cityProductionList.add("Mobile SAM");
				city.cityProductionList.add("Nuclear Submarine");
				city.cityProductionList.add("Helicopter Gunship");
				city.cityProductionList.add("Rocket Artillery");
				city.cityProductionList.add("Jet Fighter");
				city.cityProductionList.add("Modern Armor");
				city.cityProductionList.add("Atomic Bomb");
				city.cityProductionList.add("Missile Cruiser");
				city.cityProductionList.add("Guided Missile");
				city.cityProductionList.add("Stealth Bomber");
				city.cityProductionList.add("Nuclear Missile");
				city.cityProductionList.add("Giant Death Robot");
				city.cityProductionList.add("Monument");
				city.cityProductionList.add("Granary");
				city.cityProductionList.add("Stone Works");
				city.cityProductionList.add("Library");
				city.cityProductionList.add("Circus");
				city.cityProductionList.add("Water Mill");
				city.cityProductionList.add("Walls");
				city.cityProductionList.add("Barracks");
				city.cityProductionList.add("Lighthouse");
				city.cityProductionList.add("Temple");
				city.cityProductionList.add("Stable");
				city.cityProductionList.add("Courthouse");
				city.cityProductionList.add("Colloseum");
				city.cityProductionList.add("Monastery");
				city.cityProductionList.add("Garden");
				city.cityProductionList.add("Market");
				city.cityProductionList.add("Mint");
				city.cityProductionList.add("Aqueduct");
				city.cityProductionList.add("Forge");
				city.cityProductionList.add("Workshop");
				city.cityProductionList.add("Harbor");
				city.cityProductionList.add("University");
				city.cityProductionList.add("Castle");
				city.cityProductionList.add("Armory");
				city.cityProductionList.add("Observatory");
				city.cityProductionList.add("Opera House");
				city.cityProductionList.add("Bank");
				city.cityProductionList.add("Theatre");
				city.cityProductionList.add("Seaport");
				city.cityProductionList.add("Windmill");
				city.cityProductionList.add("Museum");
				city.cityProductionList.add("Public School");
				city.cityProductionList.add("Military Academy");
				city.cityProductionList.add("Arsenal");
				city.cityProductionList.add("Hospital");
				city.cityProductionList.add("Factory");
				city.cityProductionList.add("Stock Exchange");
				city.cityProductionList.add("Military Base");
				city.cityProductionList.add("Broadcast Tower");
				city.cityProductionList.add("Hydro Plant");
				city.cityProductionList.add("Research Lab");
				city.cityProductionList.add("Medical Lab");
				city.cityProductionList.add("Stadium");
				city.cityProductionList.add("Solar Plant");
				city.cityProductionList.add("Nuclear Plant");
				city.cityProductionList.add("Spaceship Factory");
				city.cityProductionList.add("Temple of Artemis");
				city.cityProductionList.add("Stonehenge");
				city.cityProductionList.add("Great Library");
				city.cityProductionList.add("Pyramids");
				city.cityProductionList.add("Mausoleum of Halicarnassus");
				city.cityProductionList.add("Statue of Zeus");
				city.cityProductionList.add("Great Lighthouse");
				city.cityProductionList.add("Hanging Gardens");
				city.cityProductionList.add("Teracotta Army");
				city.cityProductionList.add("Oracle");
				city.cityProductionList.add("Petra");
				city.cityProductionList.add("Great Wall");
				city.cityProductionList.add("Colossus");
				city.cityProductionList.add("Hagia Sophia");
				city.cityProductionList.add("Great Mosque of Djenne");
				city.cityProductionList.add("Borobudur");
				city.cityProductionList.add("Chichen Itza");
				city.cityProductionList.add("Machu Picchu");
				city.cityProductionList.add("Angkor Wat");
				city.cityProductionList.add("Notre Dame");
				city.cityProductionList.add("Sistine Chapel");
				city.cityProductionList.add("Forbidden Palace");
				city.cityProductionList.add("Leaning Tower of Pisa");
				city.cityProductionList.add("Globe Theatre");
				city.cityProductionList.add("Himeji Castle");
				city.cityProductionList.add("Porcelain Tower");
				city.cityProductionList.add("Taj Mahal");
				city.cityProductionList.add("Uffizi");
				city.cityProductionList.add("Red Fort");
				city.cityProductionList.add("Louvre");
				city.cityProductionList.add("Big Ben");
				city.cityProductionList.add("Brandenburg Gate");
				city.cityProductionList.add("Eiffel Tower");
				city.cityProductionList.add("Statue of Liberty");
				city.cityProductionList.add("Prora");
				city.cityProductionList.add("Kremlin");
				city.cityProductionList.add("Neuschwanstein");
				city.cityProductionList.add("Cristo Redentor");
				city.cityProductionList.add("Pentagon");
				city.cityProductionList.add("Sydney Opera House");
			}
			colorPanel.repaint();
		}
	}
	public class CityTagControlHandler extends ControlHandler
	{
		public void clickEvent(String id)
		{
			if (id.equals("Background"))
			{
				colorPanel.productionOpen = false;
				disposeProductionElements();
				selectedCity = null;
				chosenProduction = null;
			}
			if (id.equals("Produce"))
			{
				if (city.currentProduce == null)
				{
					for (int i = 0; i < colorPanel.players[colorPanel.turn].toDoList.size(); i++)
					{
						if (colorPanel.players[colorPanel.turn].toDoList.get(i).getClass() == CityTask.class)
						{
							if (((CityTask) colorPanel.players[colorPanel.turn].toDoList.get(i)).tilePositionOfTask.x == selectedCity.position.x &&
									((CityTask) colorPanel.players[colorPanel.turn].toDoList.get(i)).tilePositionOfTask.y == selectedCity.position.y)
							{
								colorPanel.players[colorPanel.turn].toDoList.remove(i);
							}
						}
					}
					colorPanel.rotateTasks();
				}
				city.currentProduce = chosenProduction;
				chosenProduction = null;
				colorPanel.productionOpen = false;
				disposeProductionElements();
				selectedCity = null;
			}
			if (id.equals("Build Option"))
			{
				if (selectedCity.chosenProductionIcon != null)
				{
					selectedCity.chosenProductionIcon.dispose();
					selectedCity.chosenProductionIcon = null;
				}
				if (selectedCity.costsLabel != null)
				{
					selectedCity.costsLabel.dispose();
					selectedCity.costsLabel = null;
				}
				if (selectedCity.resourceCost != null)
				{
					selectedCity.resourceCost.dispose();
					selectedCity.resourceCost = null;
				}
				if (selectedCity.productionCost != null)
				{
					selectedCity.productionCost.dispose();
					selectedCity.productionCost = null;
				}
				if (selectedCity.turnCostLabel != null)
				{
					selectedCity.turnCostLabel.dispose();
					selectedCity.turnCostLabel = null;
				}
				if (selectedCity.unitOrBuildingDescription != null)
				{
					selectedCity.unitOrBuildingDescription.dispose();
					selectedCity.unitOrBuildingDescription = null;
				}
				if (selectedCity.produceButton != null)
				{
					selectedCity.produceButton.dispose();
					selectedCity.produceButton = null;
				}
				if (selectedCity.cityProductionList.getSelectedIndex() != -1)
				{
					String selectedItem = selectedCity.cityProductionList.getItem(selectedCity.cityProductionList.getSelectedIndex());
					int productionCost = 0;
					String resourceCost = "none";
					String description = "";
					int oilCost, ironCost, uraniumCost, aluminiumCost, horsesCost, coalCost;
					if (selectedItem.equals("Warrior"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(100, 120, colorPanel, new Point(250, 90), Unit.warriorimg);
						productionCost = 40;
						description = "Combat Strength: \nMobility: \nExtra: ";
						chosenProduction = new UnitType(new Warrior(colorPanel, selectedCity.position.x, selectedCity.position.y, city.owner));
					}
					else if (selectedItem.equals("Settler"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.settlerimg);
						productionCost = 106;
						description = "Combat Strength: \nMobility: \nExtra: ";
						chosenProduction = new UnitType(new Settler(colorPanel, selectedCity.position.x, selectedCity.position.y, city.owner));
					}
					else if (selectedItem.equals("Worker"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.workerimg);
						productionCost = 70;
						description = "Combat Strength: \nMobility: \nExtra: ";
						chosenProduction = new UnitType(new Worker(colorPanel, selectedCity.position.x, selectedCity.position.y, city.owner));
					}
					else if (selectedItem.equals("Scout"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.scoutimg);
						productionCost = 25;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Archer"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.archerimg);
						productionCost = 40;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Trireme"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.triremeimg);
						productionCost = 56;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Chariot Archer"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.chariotArcherimg);
						resourceCost = "1 Horse";
						productionCost = 56;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Spearman"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.spearmanimg);
						productionCost = 56;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Horseman"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.horsemanimg);
						productionCost = 75;
						resourceCost = "1 Horse";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Catapult"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.catapultimg);
						productionCost = 75;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Swordsman"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.swordsmanimg);
						productionCost = 75;
						resourceCost = "1 Iron";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Pikeman"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.pikemanimg);
						productionCost = 90;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Knight"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.knightimg);
						productionCost = 120;
						resourceCost = "1 Horse";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Crossbowman"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.crossbowmanimg);
						productionCost = 120;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Trebuchet"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.trebuchetimg);
						productionCost = 120;
						resourceCost = "1 Iron";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Longswordsman"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.longswordsmanimg);
						productionCost = 120;
						resourceCost = "1 Iron";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Caravel"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.caravelimg);
						productionCost = 120;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Musketman"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.musketmanimg);
						productionCost = 150;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Frigate"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.frigateimg);
						productionCost = 185;
						resourceCost = "1 Iron";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Cannon"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.cannonimg);
						productionCost = 185;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Lancer"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.lancerimg);
						productionCost = 185;
						resourceCost = "1 Horse";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Cavalry"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.cavalryimg);
						productionCost = 225;
						resourceCost = "1 Horse";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Rifleman"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.catapultimg);
						productionCost = 225;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Ironclad"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.ironcladimg);
						productionCost = 250;
						resourceCost = "Coal";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Anti-Tank Gun"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.antiTankGunimg);
						productionCost = 300;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Infantry"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 100, colorPanel, new Point(250, 90), Unit.infantryimg);
						productionCost = 375;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Artillery"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.artilleryimg);
						productionCost = 320;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Submarine"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.submarineimg);
						productionCost = 325;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Battleship"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.battleshipimg);
						productionCost = 375;
						resourceCost = "1 Oil";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Anti-Aircraft Gun"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.antiAircraftGunimg);
						productionCost = 375;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Carrier"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.carrierimg);
						productionCost = 375;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Fighter"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(140, 120, colorPanel, new Point(250, 90), Unit.fighterimg);
						productionCost = 375;
						resourceCost = "1 Oil";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Destroyer"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.destroyerimg);
						productionCost = 375;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Tank"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.tankimg);
						productionCost = 375;
						resourceCost = "1 Oil";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Mechanized Infantry"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.mechanizedInfantryimg);
						productionCost = 375;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Bomber"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.bomberimg);
						productionCost = 375;
						resourceCost = "1 Oil";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Paratrooper"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.paratrooperimg);
						productionCost = 375;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Mobile SAM"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.mobileSAMimg);
						productionCost = 425;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Nuclear Submarine"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.nuclearSubmarineimg);
						productionCost = 425;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Helicopter Gunship"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.helicopterGunshipimg);
						productionCost = 425;
						resourceCost = "1 Aluminum";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Rocket Artillery"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.rocketArtilleryimg);
						productionCost = 425;
						resourceCost = "1 Aluminum";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Jet Fighter"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.jetFighterimg);
						productionCost = 425;
						resourceCost = "1 Aluminum";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Modern Armor"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.modernArmorimg);
						productionCost = 425;
						resourceCost = "1 Aluminum";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Atomic Bomb"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.atomicBombimg);
						productionCost = 600;
						resourceCost = "1 Uranium";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Missile Cruiser"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.missileCruiserimg);
						productionCost = 425;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Guided Missile"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.guidedMissileimg);
						productionCost = 150;
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Stealth Bomber"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.stealthBomberimg);
						productionCost = 425;
						resourceCost = "1 Aluminum";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Nuclear Missile"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.nuclearMissileimg);
						productionCost = 1000;
						resourceCost = "2 Uranium";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Giant Death Robot"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Unit.giantDeathRobotimg);
						productionCost = 425;
						resourceCost = "1 Uranium";
						description = "Combat Strength: \nMobility: \nExtra: ";
					}
					else if (selectedItem.equals("Monument"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.monumentimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Granary"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.granaryimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Stone Works"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.stoneworksimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Library"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.libraryimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Circus"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.circusimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Water Mill"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.waterMillimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Walls"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.wallsimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Barracks"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.barracksimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Lighthouse"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.lighthouseimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Temple"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.templeimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Stable"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.stableimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Courthouse"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.courthouseimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Colloseum"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.colloseumimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Monastery"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.monasteryimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Garden"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.gardenimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Market"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.marketimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Mint"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.mintimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Aqueduct"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.aqueductimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Forge"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.forgeimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Workshop"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.workshopimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Harbor"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.harborimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("University"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.universityimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Castle"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.castleimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Armory"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.armoryimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Observatory"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.observatoryimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Opera House"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.operaHouseimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Bank"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.bankimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Theatre"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.theatreimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Seaport"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.seaportimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Windmill"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.windmillimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Museum"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.museumimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Public School"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.publicSchoolimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Military Academy"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.militaryAcademyimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Arsenal"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.arsenalimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Hospital"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.hospitalimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Factory"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.factoryimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Stock Exchange"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.stockExchangeimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Military Base"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.militaryBaseimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Broadcast Tower"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.broadcastTowerimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Hydro Plant"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.hydroPlantimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Research Lab"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.researchLabimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Medical Lab"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.medicalLabimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Stadium"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.stadiumimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Solar Plant"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.solarPlantimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Nuclear Plant"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.nuclearPlantimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Spaceship Factory"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.spaceshipFactoryimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Temple of Artemis"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.templeOfArtemisimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Stonehenge"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.stonehengeimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Great Library"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.greatLibraryimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Pyramids"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.pyramidsimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Mausoleum of Halicarnassus"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.mausoleumOfHalicarnassusimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Statue of Zeus"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.statueOfZeusimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Great Lighthouse"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.greatLighthouseimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Hanging Gardens"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.hangingGardensimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Teracotta Army"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.teracottaArmyimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Oracle"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.oracleimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Petra"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.petraimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Great Wall"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.greatWallimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Colossus"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.colossusimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Hagia Sophia"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.hagiaSophiaimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Great Mosque of Djenne"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.greatMosqueOfDjenneimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Borobudur"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.borobudurimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Chichen Itza"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.chichenItzaimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Machu Picchu"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.machuPicchuimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Angkor Wat"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.angkorWatimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Notre Dame"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.notreDameimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Sistine Chapel"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.sistineChapelimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Forbidden Palace"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.forbiddenPalaceimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Leaning Tower of Pisa"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.leaningTowerOfPisaimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Globe Theatre"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.globeTheatreimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Himeji Castle"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.himejiCastleimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Porcelain Tower"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.porcelainTowerimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Taj Mahal"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.tajMahalimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Uffizi"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.uffiziimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Red Fort"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.redFortimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Louvre"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.louvreimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Big Ben"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.bigBenimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Brandenburg Gate"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.brandenburgGateimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Eiffel Tower"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.eiffelTowerimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Statue of Liberty"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.statueOfLibertyimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Prora"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.proraimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Kremlin"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.kremlinimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Neuschwanstein"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.neuschwansteinimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Cristo Redentor"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.cristoRedentorimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Pentagon"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.pentagonimg);
						productionCost = 425;
						description = "";
					}
					else if (selectedItem.equals("Sydney Opera House"))
					{
						selectedCity.chosenProductionIcon = new ImageBox(120, 120, colorPanel, new Point(250, 90), Building.sydneyOperaHouseimg);
						productionCost = 425;
						description = "";
					}
					if (selectedCity.chosenProductionIcon != null)
					{
						selectedCity.cityProductionContainer.add(selectedCity.chosenProductionIcon);
						selectedCity.chosenProductionIcon.setVisible(true);
					}
					selectedCity.costsLabel = new Label("Costs: ", colorPanel, new Point(250, 300));
					selectedCity.costsLabel.setFont(new Font("Rockwell", Font.BOLD, 25));
					selectedCity.cityProductionContainer.add(selectedCity.costsLabel);
					selectedCity.costsLabel.color = new Color(255, 255, 255);
					selectedCity.costsLabel.setVisible(true);
					selectedCity.productionCost = new Label("Production: " + productionCost, colorPanel, new Point(250, 330));
					selectedCity.productionCost.setFont(new Font("Rockwell", Font.PLAIN, 15));
					selectedCity.productionCost.color = new Color(255, 255, 255);
					selectedCity.productionCost.setVisible(true);
					selectedCity.cityProductionContainer.add(selectedCity.productionCost);
					selectedCity.resourceCost = new Label("Resource: " + resourceCost, colorPanel, new Point(250, 350));
					selectedCity.resourceCost.setFont(new Font("Rockwell", Font.PLAIN, 15));
					selectedCity.resourceCost.color = new Color(255, 255, 255);
					selectedCity.resourceCost.setVisible(true);
					selectedCity.cityProductionContainer.add(selectedCity.resourceCost);
					int turns = productionCost / selectedCity.production;
					if (productionCost % selectedCity.production != 0) { turns++; }
					selectedCity.turnCostLabel = new Label("Turns: " + turns, colorPanel, new Point(250, 370));
					selectedCity.turnCostLabel.setFont(new Font("Rockwell", Font.PLAIN, 15));
					selectedCity.turnCostLabel.color = new Color(255, 255, 255);
					selectedCity.turnCostLabel.setVisible(true);						
					selectedCity.cityProductionContainer.add(selectedCity.turnCostLabel);
					selectedCity.produceButton = new Button(" Produce", 70, 20, colorPanel, new Point(250, 420));
					selectedCity.produceButton.setFont(new Font("Rockwell", Font.PLAIN, 15));
					selectedCity.produceButton.color = new Color(255, 255, 255);
					selectedCity.produceButton.backgroundColor = new Color(129, 131, 203);
					selectedCity.produceButton.borderColor = new Color(255, 255, 255);
					selectedCity.produceButton.setVisible(true);					
					selectedCity.produceButton.controlHandler = controlHandler;
					selectedCity.produceButton.id = "Produce";
					selectedCity.cityProductionContainer.add(selectedCity.produceButton);
					if (chosenProduction != null)
					{
						chosenProduction.turnsLeft = turns;
						chosenProduction.productionImage = selectedCity.chosenProductionIcon.getImage();
						chosenProduction.productionName = selectedItem;
					}
					colorPanel.repaint();
				}
				System.out.println(Control.getControlsLength());
			}
		}
	}
}
