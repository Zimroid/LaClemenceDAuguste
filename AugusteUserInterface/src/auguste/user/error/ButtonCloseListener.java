package auguste.user.error;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class ButtonCloseListener implements ActionListener
{
	private JFrame frame;
	
	public ButtonCloseListener(JFrame frameToClose)
	{
		this.frame = frameToClose;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		this.frame.dispose();
	}
}