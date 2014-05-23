package auguste.user.error;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class ErrorWindow extends JFrame
{
	private JPanel panel;
	private Exception exception;
	private JButton buttonOk;
	private ActionListener ActionButtonClose;
	private JLabel labelErrorMessage;
	
	private JTextArea areaError;
	
	private static String BUTTON_TEXT = "OK";
	private static String ERROR_TITLE =	"Erreur";

	public ErrorWindow(Exception e)
	{
		super();
		this.panel = new JPanel();
		this.ActionButtonClose = new ButtonCloseListener(this);
		this.exception = e;
		this.buttonOk = new JButton(BUTTON_TEXT);
			this.buttonOk.addActionListener(this.ActionButtonClose);
		this.areaError = new JTextArea();
		
		String text = "";
		for(StackTraceElement st : this.exception.getStackTrace())
		{
			text += st.toString()+'\n';
		}
		this.areaError.setText(text);
		
		this.panel.add(this.areaError);
		this.panel.add(this.buttonOk);
		
		this.add(this.panel);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setTitle(ERROR_TITLE);
		this.setVisible(true);
	}
	
	public ErrorWindow(String errorMessage)
	{
		super();
		this.panel = new JPanel();
		this.ActionButtonClose = new ButtonCloseListener(this);
		this.buttonOk = new JButton(BUTTON_TEXT);
			this.buttonOk.addActionListener(this.ActionButtonClose);
		this.labelErrorMessage = new JLabel(errorMessage);
		
		this.panel.add(this.labelErrorMessage);
		this.panel.add(this.buttonOk);
		
		this.add(this.panel);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setTitle(ERROR_TITLE);
		this.setVisible(true);
	}
}