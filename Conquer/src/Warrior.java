import java.awt.Graphics;
import java.awt.Point;

import ui.ImageBox;

public class Warrior extends Unit 
{
	public Warrior(ColorPanel colorPanel, int x, int y, Player player) 
	{
		super(colorPanel, x, y, player);
		warrior = true;
		attackOrUtilityimg = attackimg;
		unitName = "Warrior";
	}
	public void paint(Graphics g, int x, int y)
	{
		super.paint(g, x, y);
		g.drawImage(warriorimg, (int) (x + colorPanel.xDistanceIncrementer / 2 - 26), 
				(int) (y + colorPanel.sideLength * 2 / 3 - 30), 51, 60, null);
	}
	public void refreshButtons()
	{
		super.refreshButtons();
		unitImage = new ImageBox(150, 170, colorPanel, new Point(0, 155), warriorimg);
		unitImage.bottomOriented = true;
		unitImage.setVisible(true);
	}
	public void utilityAction()
	{
		meleeAttack();
	}
}
