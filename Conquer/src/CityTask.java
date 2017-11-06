
public class CityTask extends Task 
{
	public CityTask(ColorPanel colorPanel, City city)
	{
		super(colorPanel);
		tilePositionOfTask = city.position;
	}
	public void setTurnRotationButton()
	{
		colorPanel.turnRotationButton.setText("           Choose Production");
	}
}
