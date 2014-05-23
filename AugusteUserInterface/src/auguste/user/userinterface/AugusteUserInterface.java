package auguste.user.userinterface;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import auguste.client.entity.ChatMessageReceived;
import auguste.client.entity.Client;
import auguste.client.interfaces.UpdateListener;
import auguste.user.chat.ChatWindow;
import auguste.user.error.ErrorWindow;

public class AugusteUserInterface implements UpdateListener
{
	private AugusteMainFrame mainFrame;
	
	private Client engine;
	private Map<Integer, ChatWindow> chatWindows;
	
	private Map<Integer, Queue<ChatMessageReceived>> chatMessages;
	
	private static int GLOBAL_ROOM_ID = 0;
	
	public AugusteUserInterface()
	{
		this.chatWindows = new HashMap<>();
		this.chatMessages = new HashMap<>();
		
		try
		{
			this.engine = Client.getInstance();
			this.engine.addInterface(this);

			this.mainFrame = AugusteMainFrame.getInstance();
			this.mainFrame.setVisible(true);
		}
		catch (URISyntaxException e)
		{
			System.out.println("Erreur lors de la recuperation de l'instance de moteur client.");
			new ErrorWindow(e);
		}
	}

	@Override
	public void chatUpdate(int id)
	{
		ChatMessageReceived cmr = this.chatMessages.get(id).remove();
		String author = cmr.getAuthor();
		String message = cmr.getMessage();
		Date date = cmr.getDate();
		
		this.chatWindows.get(id).putMessage(author, message, date);
		//this.chatWindows.get(id).setFocusableWindowState(false);
		this.chatWindows.get(id).setAutoRequestFocus(false);
		this.chatWindows.get(id).setVisible(true);
		//this.chatWindows.get(id).toFront();
		//this.chatWindows.get(id).setState(JFrame.ICONIFIED);
		//this.chatWindows.get(id).setFocusableWindowState(true);
	}

	@Override
	public void userUpdate()
	{
		System.out.println("*********************************");
		System.out.println("USER UPDATE");
		System.out.println(this.engine.getUser().getName());
		System.out.println("*********************************");
		
		this.mainFrame.connect();

		//Chargement de la fenetre de chat principale
		Queue<ChatMessageReceived> queue = this.engine.getChatMessage(GLOBAL_ROOM_ID);
		this.chatMessages.put(GLOBAL_ROOM_ID, queue);
		ChatWindow mainChatWindow = new ChatWindow("General", GLOBAL_ROOM_ID);
		this.chatWindows.put(0, mainChatWindow);
		this.mainFrame.setChatWindow(mainChatWindow);
		
		mainChatWindow.setVisible(true);
	}

	@Override
	public void createGameUpdate(int id)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listGameUpdate()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void confirmMessageUpdate()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameTurnUpdate(int id)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String errorType)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logOut()
	{
		// TODO Auto-generated method stub
		
	}
}