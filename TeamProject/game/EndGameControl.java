//This manages the information sent to and from the server after a session has ended
package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JPanel;

import client.WoFClient;

public class EndGameControl implements ActionListener {
	private JPanel container;
	private WoFClient client;
	
	public EndGameControl(JPanel container, WoFClient client) {
	    this.container = container;
	    this.client = client;
	   }
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String command = ae.getActionCommand();
		if (command.equals("Exit")) {
			try {
				client.closeConnection();
				System.exit(0);
			} catch(IOException e) {
				e.printStackTrace();
		}}}
	}
