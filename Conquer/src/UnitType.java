
public class UnitType extends ProductionType 
{
	Unit unit;
	public UnitType(Unit unit)
	{
		this.unit = unit;
	}
	public void produce(City city, ColorPanel colorPanel)
	{
		if ((unit.warrior && colorPanel.warriorMap[unit.location.x][unit.location.y] == null) || (!unit.warrior && colorPanel.workerMap[unit.location.x][unit.location.y] == null))
		{
			if (unit.warrior)
			{
				colorPanel.warriorMap[unit.location.x][unit.location.y] = unit;
			}
			else
			{
				colorPanel.workerMap[unit.location.x][unit.location.y] = unit;
			}
			unit.player.units.add(unit);
			super.produce(city, colorPanel);
		}
	}
}
