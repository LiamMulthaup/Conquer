import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

import javax.swing.JFrame;

public class KeyEventListener implements KeyListener
{
	JFrame Screen;
	Timer tim;
	public KeyEventListener(JFrame Screen, Timer tim)
	{
		super();
		this.Screen = Screen;
		this.tim = tim;
	}
	@Override
	public void keyPressed(KeyEvent arg0) 
	{
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
			tim.cancel();
			Screen.dispose();
		}
	}

	@Override	public void keyReleased(KeyEvent arg0) 
	{
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
