//Create Account Control
package account;

import client.*;
import server.*;
import game.*;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import client.WoFClient;

public class CreateAccountControl implements ActionListener{
	
	private JPanel container;
	private WoFClient client;
	
	public CreateAccountControl(JPanel container, WoFClient client) {
		this.container = container;
		this.client = client;
	}
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		CreateAccount ca = (CreateAccount)container.getComponent(1);
		String username = ca.getUsername();
		String password = ca.getPassword();
		String password2 = ca.getPassword2();
		CreateAccountData cad = new CreateAccountData(username, password, password2);
		
		if(command == "Cancel") {
			CardLayout cl = (CardLayout)container.getLayout();
			cl.show(container, "1");
		}else if(command == "Submit") {
			if(username.isEmpty()||password.isEmpty()) {
				displayError("Fill in all blanks");
				return;
			}
			if(password.length()<6 && password.length() != 0) {
				displayError("Password not correct length");
				return;
			}
			if(!password.equals(password2)) {
				displayError("Your password do not match");
				return;
			}else {
				try {
					client.openConnection();
					client.sendToServer(cad);
					System.out.println("Hello");
					try {
						Thread.sleep(1000);
					}catch(InterruptedException e1) {
						e1.printStackTrace();
					}
					System.out.println((String)client.getServerMsg());
					if((String)client.getServerMsg() == "Success") {
						CardLayout cl = (CardLayout)container.getLayout();
						cl.show(container, "1");
					}else if((String)client.getServerMsg() == "Exists") {
						displayError("That account exists");
					}else {
						displayError("Server Error");
						CardLayout cl = (CardLayout)container.getLayout();
						cl.show(container, "1");
					}
				}catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void displayError(String error) {
		CreateAccount createAccountPanel = (CreateAccount)container.getComponent(1);
		createAccountPanel.setError(error);
	}

}
