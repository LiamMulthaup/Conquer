import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import ui.Button;
import ui.ImageBox;
import ui.Label;
import ui.ListBox;
import ui.Page;

public class City {
	int health;
	int population;
	int defense = 50;
	int attackPower;
	int citizens = 1;
	int production = 100;
	int income = 10;
	int food = 10;
	int science = 20;
	int culture = 20;
	boolean resistance = false;
	Point position;
	Color backgroundColor;
	Color textColor;
	String name;
	Player owner;
	ArrayList<Building> buildings = new ArrayList<Building>();
	ArrayList<Point> tilesOwned = new ArrayList<Point>();
	ArrayList<Building> availableBuildings = new ArrayList<Building>();
	ListBox cityProductionList;
	Page cityProductionContainer, cityCurrentProductionPage;
	Page cityProductionBackground;
	Button produceButton;
	Label productionLabel, costsLabel, resourceCost, productionCost, turnCostLabel, productionIncomeLabel, goldIncomeLabel,
	foodIncomeLabel, scienceIncomeLabel, cultureIncomeLabel, defenseLabel, unitOrBuildingDescription;
	ImageBox chosenProductionIcon;
	ProductionType currentProduce;
	public City(Player owner, Point position)
	{
		this.position = position;
		this.owner = owner;
		this.owner.cities.add(this);
		File nameFile = new File("Game Resources/Cities/" + owner.civilization.getCivilizationName() + ".txt");
		String name = null;
		backgroundColor = owner.civilization.getColor();
		textColor = new Color(255, 255, 255);
		Scanner scan;
		try {
			scan = new Scanner(nameFile);
			int numberOfCities = 1;
			String addOn = "";
			for (int i = 0; i < owner.nameIndex; i++)
			{
				if (!scan.hasNextLine())
				{
					scan = new Scanner(nameFile);
					numberOfCities++;
					addOn = " " + numberOfCities;
				}
				name = scan.nextLine() + addOn;
			}
			this.name = name;
			owner.nameIndex++;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

