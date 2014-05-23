package auguste.user.chat;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import auguste.client.entity.Client;
import auguste.user.error.ErrorWindow;

@SuppressWarnings("serial")
public class ChatWindow extends JFrame
{
	private JPanel panelSendMessage;
	private JPanel panel;
	private JButton buttonSend;
	private JTextField textMessageToSend;
	private JTextArea areaMessages;
	private JScrollPane scrollPane;
	
	private ActionListener sendMessageListener;
	private KeyListener sendMessageOnEnter;
	private int roomId;
	
	private static String BUTTON_SEND_TEXT = "Envoyer";
	private static int WINDOW_WIDTH = 200;
	private static int WINDOW_HEIGHT = 400;
	
	public ChatWindow(String title, int roomId)
	{
		super();
		
		this.panelSendMessage = new JPanel(new BorderLayout());
		this.panel = new JPanel(new BorderLayout());
		this.buttonSend = new JButton(BUTTON_SEND_TEXT);
		this.textMessageToSend = new JTextField();
			this.textMessageToSend.requestFocus();
		this.areaMessages = new JTextArea();
			this.areaMessages.setEditable(false);
			this.areaMessages.setLineWrap(true);
			this.areaMessages.setWrapStyleWord(true);
			
		this.scrollPane = new JScrollPane(this.areaMessages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.roomId = roomId;
		try
		{
			this.sendMessageListener = new SendMessageListener(Client.getInstance(), this);
			this.sendMessageOnEnter = new SendMessageOnEnterListener(Client.getInstance(), this);
			this.buttonSend.addActionListener(this.sendMessageListener);
			this.textMessageToSend.addKeyListener(this.sendMessageOnEnter);
		}
		catch (Exception e)
		{
			new ErrorWindow(e);
			e.printStackTrace();
		}
		
		this.panelSendMessage.add(this.textMessageToSend, BorderLayout.CENTER);
		this.panelSendMessage.add(this.buttonSend, BorderLayout.EAST);
		
		this.panel.add(this.scrollPane, BorderLayout.CENTER);
		this.panel.add(this.panelSendMessage, BorderLayout.SOUTH);
		
		this.add(this.panel);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//this.setState(JFrame.ICONIFIED);
		
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setTitle(title);
		this.setVisible(true);
	}
	
	public void putMessage(String author, String message, Date date)
	{
		String datePattern = "HH:mm:ss";
		DateFormat df = new SimpleDateFormat(datePattern);
		String formatedDate = df.format(date);
		
		String newMessage = "["+formatedDate+"] "+ author +" : " + message;
		this.areaMessages.append(newMessage+'\n');
		JScrollBar jsb = this.scrollPane.getVerticalScrollBar();
		jsb.setValue(jsb.getMaximum());
	}
	
	public void clearChatText()
	{
		this.textMessageToSend.setText("");
	}
	
	public String getMessage()
	{
		return this.textMessageToSend.getText();
	}
	
	public int getRoomId()
	{
		return this.roomId;
	}
}