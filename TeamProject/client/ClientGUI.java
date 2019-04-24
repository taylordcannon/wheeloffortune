package client;

import account.*;
import server.*;
import game.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import account.CreateAccount;
import account.CreateAccountControl;
import account.LoginControl;
import account.LoginPanel;
import game.EndGameControl;
import game.EndGamePanel;
import game.PhraseControl;
import game.PhrasePanel;

public class ClientGUI extends JFrame {
	
	private WoFClient client;
	private CardLayout cl = new CardLayout();
	
	public ClientGUI() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		client = new WoFClient();
		client.setHost("localhost");
		client.setPort(8300);
		
		new JPanel(cl);
		
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);
		
		
		
		LoginControl lc = new LoginControl(container, client);
		CreateAccountControl ca = new CreateAccountControl(container, client);
		PhraseControl pc = new PhraseControl(container, client);
		EndGameControl egp = new EndGameControl(container, client);
		
		JPanel view1 = new LoginPanel(lc);
		JPanel view2 = new CreateAccount(ca); 
		JPanel view3 = new PhrasePanel(pc); 
		JPanel view4 = new EndGamePanel(egp); 
		
		container.add(view1, "1");
		container.add(view2, "2");
		container.add(view3, "3");
		container.add(view4, "4");
		
		CardLayout cl = (CardLayout)container.getLayout();
		cl.show(container, "1");
		
		this.add(container, BorderLayout.CENTER);
		
		this.setSize(500, 300);
		this.setVisible(true);
				
	}
	
	public static void main(String[] args) {
		new ClientGUI();
	}
	

}
