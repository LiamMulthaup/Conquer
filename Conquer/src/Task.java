import java.awt.Point;

public class Task 
{
	ColorPanel colorPanel;
	Point tilePositionOfTask;
	public Task(ColorPanel colorPanel)
	{
		this.colorPanel = colorPanel;
	}
	public void setTurnRotationButton()
	{
		
	}
	public void turnRotationClickEvent()
	{
		double newXOffset;
		double newYOffset;
		int column = tilePositionOfTask.x;
		int row = tilePositionOfTask.y;
		if (column % 2 == 1)
		{
			newXOffset = (column - 1) / 2 * colorPanel.xDistanceIncrementer;
			newYOffset = row * colorPanel.sideLength * 3 / 2 - colorPanel.sideLength * 3 / 2;
		}
		else
		{
			newXOffset = (column) / 2 * colorPanel.xDistanceIncrementer - colorPanel.xDistanceIncrementer / 2;
			newYOffset = row * colorPanel.sideLength * 3 / 2 - colorPanel.sideLength * 3 / 2;
		}
		colorPanel.xOffset = - newXOffset + colorPanel.getWidth() / 2 - colorPanel.xDistanceIncrementer / 2;
		colorPanel.yOffset = - newYOffset + colorPanel.getHeight() / 2 - colorPanel.sideLength;
		colorPanel.xOrigin = colorPanel.xOffset;
		colorPanel.yOrigin = colorPanel.yOffset;
	}
}
