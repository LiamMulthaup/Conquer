
public class UnitTask extends Task
{
	Unit unit;
	public UnitTask(ColorPanel colorPanel, Unit unit) 
	{
		super(colorPanel);
		this.unit = unit;
		tilePositionOfTask = unit.location;
	}
	public void setTurnRotationButton()
	{
		colorPanel.turnRotationButton.setText("            Unit Needs Orders");
	}
	public void turnRotationClickEvent()
	{
		super.turnRotationClickEvent();
		unit.clickEvent();
	}
}
