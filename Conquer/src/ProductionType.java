import java.awt.Image;

public class ProductionType 
{
	int turnsLeft;
	Image productionImage;
	String productionName;
	public void produce(City city, ColorPanel colorPanel)
	{
		city.currentProduce = null;
	}
}
