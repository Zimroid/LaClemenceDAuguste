package auguste.user.userinterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayChatListener implements ActionListener
{
	private AugusteMainFrame amf;

	public DisplayChatListener(AugusteMainFrame amf)
	{
		this.amf = amf;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		amf.showGeneralChatWindow();
	}

}
