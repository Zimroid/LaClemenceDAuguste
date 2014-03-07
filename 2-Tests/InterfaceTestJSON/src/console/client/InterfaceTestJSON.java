package console.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * @author Vr4el
 */

public class InterfaceTestJSON extends JFrame /*implements ActionListener*/ {
    private JFrame mainFrame;
    private JPanel sendingPannel;
    private JTextField sendingBox;
    private JTextArea showMessage;

    private SocketHandler sh;
    
    public InterfaceTestJSON(){
        prepareGUI();
        sh = new SocketHandler(this);
        
        JTextArea showMessage = this.getShowMessage();
        showMessage.append("Bonjour et bienvenue sur l'interface de test JSON\r\n");
        showMessage.append("Merci de bien vouloir saisir votre JSON et de l'envoyer pour voir ce que le serveur en fera ...\r\n\r\n");
    }
    
    public static void main(String[] args) {
        InterfaceTestJSON swingControl = new InterfaceTestJSON();      
        swingControl.sendingCreator();
    }
    
    private void prepareGUI(){
        mainFrame = new JFrame("Interface de test des JSON");
        mainFrame.setSize(600,700);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }        
        });    

        // Création zone d'affichage
        showMessage = new JTextArea();
        showMessage.setColumns(50);
        showMessage.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(showMessage);    
        scrollPane.setPreferredSize(new Dimension(550, 620));
        
        // Zone d'envoi
        sendingPannel = new JPanel();
        sendingPannel.setLayout(new FlowLayout());

        // Fenetre
        mainFrame.add(scrollPane, BorderLayout.PAGE_START);
        mainFrame.add(sendingPannel, BorderLayout.PAGE_END);
        mainFrame.setVisible(true);
    }

    private void sendingCreator(){
        // Création textBox
        sendingBox = new JTextField();
        sendingBox.setColumns(44);

        // Création bouton
        JButton sendingButton = new JButton("Envoyer");
        sendingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {     
                System.out.println("text :" + sendingBox.getText());
                sh.sendMessage(sendingBox.getText());
            }
        }); 

        // Ajout à la fenetre
        sendingPannel.add(sendingBox);
        sendingPannel.add(sendingButton); 
    }
    
    public JTextArea getShowMessage() {
        return showMessage;
    }
}