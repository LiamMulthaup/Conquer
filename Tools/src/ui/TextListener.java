package ui;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TextListener implements KeyListener
{
	TextBox textBox;
	public TextListener(TextBox textBox)
	{
		super();
		this.textBox = textBox;
	}
	@Override
	public void keyPressed(KeyEvent arg0)
	{
		if (!textBox.acceptsOnlyNumbers || (arg0.isShiftDown() != true && ((arg0.getKeyCode() > 47 && arg0.getKeyCode() < 58) || (arg0.getKeyCode() > 95 && arg0.getKeyCode() < 106))))
		{
			if (textBox.acceptsNumbers || (!(arg0.getKeyCode() < 58 && arg0.getKeyCode() > 47 && arg0.isShiftDown() != true) || !(arg0.getKeyCode() > 95 && arg0.getKeyCode() < 106 && arg0.isShiftDown() != true)))
			{
				if (textBox.acceptsLetters || !(arg0.getKeyCode() > 64 && arg0.getKeyCode() < 91))
				{
					if (textBox.focus == true && ((arg0.getKeyCode() > 31 && arg0.getKeyLocation() < 112)))
					{
						textBox.text += arg0.getKeyChar();
						textBox.refreshText();
					}
				}
			}
		}
		if (textBox.focus && arg0.getKeyCode() == 8 && textBox.text.length() > 0)
		{
			textBox.text = textBox.text.substring(0, textBox.text.length() - 1);
			textBox.refreshText();
		}
		try
		{
		if (textBox.focus && textBox.acceptsOnlyNumbers && textBox.acceptsNumbersBeneath > 0 && Integer.parseInt(textBox.getText()) >= textBox.acceptsNumbersBeneath)
		{
			textBox.text = textBox.text.substring(0, textBox.text.length() - 1);
			textBox.refreshText();
		}
		}
		catch (Exception e)
		{
			textBox.setText("");
		}
		if (this.textBox.focus && textBox.controlHandler != null)
		{
			textBox.controlHandler.keyPressed(arg0, textBox.id);
		}
	}

	@Override	
	public void keyReleased(KeyEvent arg0) 
	{
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}

