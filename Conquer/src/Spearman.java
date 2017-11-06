import java.awt.Graphics;

public class Spearman extends Unit
{
	public Spearman(ColorPanel colorPanel, int x, int y, Player player) {
		super(colorPanel, x, y, player);
		warrior = true;
		attackOrUtilityimg = attackimg;
		unitName = "Spearman";
	}
	public void paint(Graphics g, int x, int y)
	{
		super.paint(g, x, y);
		g.drawImage(spearmanimg, (int) (x + colorPanel.xDistanceIncrementer / 2 - 24), 
				(int) (y + colorPanel.sideLength * 2 / 3 - 24), 55, 53, null);
	}
}
