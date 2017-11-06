package ui;
import java.util.ArrayList;

public class Container 
	{
		ArrayList<Control> controlGroup = new ArrayList<Control>();
		public void add(Control control)
		{
			controlGroup.add(control);
			control.container = this;
		}
		public void clear()
		{
			controlGroup.clear();
		}
		public RadioButton getSelected()
		{
			for (int i = 0; i < controlGroup.size(); i++)
			{
				if (controlGroup.get(i).getClass() == RadioButton.class)
				{
					RadioButton r = (RadioButton) controlGroup.get(i);
					if (r.selected)
					{
						return r;
					}
				}
			}
			return null;
		}
	}
