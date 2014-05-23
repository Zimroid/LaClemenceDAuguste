package auguste.user.userinterface;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import auguste.client.entity.Client;
import auguste.user.chat.ChatWindow;
import auguste.user.error.ErrorWindow;

@SuppressWarnings("serial")
public class AugusteMainFrame extends JFrame
{
	private Client engine;
	private static String TITLE = "Auguste";
	private JPanel panel;
	private ChatWindow generalChat;
	
	private static AugusteMainFrame INSTANCE;
	
	private AugusteMainFrame(Client engine) throws HeadlessException
	{
		super(TITLE);
		
		this.engine = engine;
		
		this.panel = new ConnexionPanel(this, engine);
		
		this.add(this.panel);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
	}
	
	public static AugusteMainFrame getInstance()
	{
		if(INSTANCE == null)
		{
			try
			{
				INSTANCE = new AugusteMainFrame(Client.getInstance());
			}
			catch (Exception e)
			{
				new ErrorWindow(e);
				e.printStackTrace();
			}
		}
		return INSTANCE;
	}

	public AugusteMainFrame(GraphicsConfiguration gc)
	{
		super(gc);
		// TODO Auto-generated constructor stub
	}

	public AugusteMainFrame(String title) throws HeadlessException
	{
		super(title);
		// TODO Auto-generated constructor stub
	}

	public AugusteMainFrame(String title, GraphicsConfiguration gc)
	{
		super(title, gc);
		// TODO Auto-generated constructor stub
	}
	
	public void setChatWindow(ChatWindow cw)
	{
		this.generalChat = cw;
	}

	public void connect()
	{
		this.remove(this.panel);
		this.panel = new MainPanel(this, this.engine);
		this.add(this.panel);
		this.pack();
	}

	public void showGeneralChatWindow()
	{
		this.generalChat.setVisible(true);
	}
}