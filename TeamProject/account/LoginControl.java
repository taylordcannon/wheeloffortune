//This manages the information related to clients login
//This looks mostly done

package account;

import java.awt.*;
import javax.swing.*;

import client.WoFClient;
import game.PhraseData;

import java.awt.event.*;
import java.io.IOException;

public class LoginControl implements ActionListener {
  private JPanel container;
  private WoFClient client;
  boolean isPressed;
  
  class WaitingThread implements Runnable {
	@Override
	public void run() {
		while(true) {
			try {Thread.sleep(500);
			} catch (InterruptedException e1) {e1.printStackTrace();}
			if(client.getServerMsg() instanceof String) {
				String toCheck = (String)client.getServerMsg();
				if(toCheck.equals("Move Questions")) {
					try {
						client.sendToServer("Need Question");
						Thread.sleep(500);
					} catch (IOException | InterruptedException e) {e.printStackTrace();}
					 
					PhraseData toSend = (PhraseData)client.getServerMsg();
					UpdateGUI(toSend);
					CardLayout cl = (CardLayout)container.getLayout();
		    		cl.show(container, "3");
		    		break;
				}}}}
	  		}
  
  public LoginControl(JPanel container, WoFClient client) {
    this.container = container;
    this.client = client;
    this.isPressed = false;
  }
  
  public void actionPerformed(ActionEvent ae) {    
    String command = ae.getActionCommand();
    
    if (command.equals("Create Account")) {
      CardLayout cl = (CardLayout)container.getLayout();
      cl.show(container, "2");
    }
    
    if(command.contentEquals("Submit") && !(isPressed)) {
    	LoginPanel lp = (LoginPanel)container.getComponent(0);
	    LoginData ld = new LoginData(lp.getUserName(), lp.getPassword());
	    try {
	    	client.openConnection();
	    	client.sendToServer(ld);
			Thread.sleep(500);
		} catch (IOException e1) {e1.printStackTrace();}  
	      catch (InterruptedException e) {e.printStackTrace();}
	    	String toCompare = (String)client.getServerMsg();
	    	//if(toCompare.equals("Success")) {
		    	try {
		    		client.openConnection();
		    		client.sendToServer("Ready");
		    		displayError("Success! Waiting...", true);
		    		Thread TH = new Thread(new WaitingThread()); 
		    		TH.start();
		    		isPressed = true;
		    	} catch(IOException e) {displayError("Server Connection Failure!", false);}
	    		
	    	/*} else if(toCompare.equals("Incorrect Username or Password!")) {
	    		displayError("Incorrect Username or Password!", false);
	    	}*/
	 }}
    
  public void displayError(String error, boolean Decider) {
   	 LoginPanel lp= (LoginPanel)container.getComponent(0);
   	 lp.setErrorMsg(error);
   	 if(Decider) lp.hideButtons();
   	}
  /*
  public void UpdateGUI(PhraseData UA) {
	PhrasePanel pp = (PhrasePanel)container.getComponent(2);
	pp.setQData(UA);
	}
	*/
  }