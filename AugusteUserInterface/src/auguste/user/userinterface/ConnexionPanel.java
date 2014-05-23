package auguste.user.userinterface;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import auguste.client.entity.Client;

@SuppressWarnings("serial")
public class ConnexionPanel extends JPanel
{
	private Client engine;
	private JFrame mainFrame;
	private JPanel panelInfo;

	private JLabel labelUserName, labelPassword;
	private JTextField textUserName;
	private JPasswordField textPassword;
	private JButton buttonConnexion;
	
	private ActionListener connexionListener;
	private KeyListener connectOnEnter;
	
	private static String LABEL_USERNAME = "Utilisateur";
	private static String LABEL_PASSWORD = "Mot de passe";
	private static String BUTTON_CONNEXION = "Connexion";

	public ConnexionPanel(JFrame mainFrame, Client engine)
	{
		super(new GridLayout(2,1));
		this.mainFrame = mainFrame;
		this.engine = engine;
		
		this.panelInfo = new JPanel(new GridLayout(2,2));
		this.labelUserName = new JLabel(LABEL_USERNAME);
		this.labelPassword = new JLabel(LABEL_PASSWORD);
		this.textUserName = new JTextField();
		this.textPassword = new JPasswordField();
		this.buttonConnexion = new JButton(BUTTON_CONNEXION);
		
		this.panelInfo.add(this.labelUserName);
		this.panelInfo.add(this.textUserName);
		this.panelInfo.add(this.labelPassword);
		this.panelInfo.add(this.textPassword);
		
		this.add(this.panelInfo);
		this.add(this.buttonConnexion);

		this.connexionListener = new ConnexionListener(this.engine, this);
		this.connectOnEnter = new ConnectOnEnter(this.engine, this);
		this.buttonConnexion.addActionListener(this.connexionListener);
		this.textUserName.addKeyListener(this.connectOnEnter);
		this.textPassword.addKeyListener(this.connectOnEnter);
	}

	public ConnexionPanel(LayoutManager arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ConnexionPanel(boolean arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ConnexionPanel(LayoutManager arg0, boolean arg1)
	{
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public String getUsername()
	{
		return this.textUserName.getText();
	}
	
	public String getPassword()
	{
		return new String(this.textPassword.getPassword());
	}
}
