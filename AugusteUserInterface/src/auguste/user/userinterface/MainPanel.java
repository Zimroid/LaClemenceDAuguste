package auguste.user.userinterface;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import auguste.client.entity.Client;

@SuppressWarnings("serial")
public class MainPanel extends JPanel
{
	private AugusteMainFrame mainFrame;
	private Client engine;
	
	private JButton buttonCreate, buttonSearch, buttonGeneralChat;
	
	private ActionListener displayChatListener;
	
	private static String BUTTON_CREATE = "Creer une partie";
	private static String BUTTON_SEARCH = "Chercher une partie";
	private static String BUTTON_GENERAL_CHAT = "Chat Général";

	public MainPanel(AugusteMainFrame mainFrame, Client engine)
	{
		super(new GridLayout(3,1));
		this.mainFrame = mainFrame;
		this.engine = engine;
		
		this.buttonCreate = new JButton(BUTTON_CREATE);
		this.buttonSearch = new JButton(BUTTON_SEARCH);
		this.buttonGeneralChat = new JButton(BUTTON_GENERAL_CHAT);
		
		this.displayChatListener = new DisplayChatListener(this.mainFrame);
		
		this.buttonGeneralChat.addActionListener(this.displayChatListener);
		
		this.add(this.buttonCreate);
		this.add(this.buttonSearch);
		this.add(this.buttonGeneralChat);
	}

	public MainPanel(LayoutManager layout)
	{
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public MainPanel(boolean isDoubleBuffered)
	{
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public MainPanel(LayoutManager layout, boolean isDoubleBuffered)
	{
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

}
