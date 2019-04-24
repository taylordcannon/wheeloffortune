package game;

import account.*;
import client.*;
import server.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import client.WoFClient;

public class PhraseControl implements ActionListener {
	private JPanel container;
	private WoFClient client;
	private PhraseData UA;
	private boolean isPressed;
	
	class WaitingThread implements Runnable {
		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			while(true) {
				try {Thread.sleep(500);
				} catch (InterruptedException e1) {e1.printStackTrace();}
				if(client.getServerMsg() instanceof String) {
					String toCheck = (String)client.getServerMsg();
					if(toCheck.equals("Move Answer")) {
						try {
							client.sendToServer("Need Result");
							Thread.sleep(500);
						} catch (IOException | InterruptedException e) {e.printStackTrace();}
						
						UpdateResults((ArrayList<String>)client.getServerMsg());
						CardLayout cl = (CardLayout)container.getLayout();
			    		cl.show(container, "4");
			    		break;
					}
					else if(toCheck.equals("Move Question")) {
						try {
							client.sendToServer("Need Question");
							Thread.sleep(500);
						} catch (IOException | InterruptedException e) {e.printStackTrace();}
						
						isPressed = false;
						displayLabel("Choose the Best Answer!");
						UpdateGUI((PhraseData)client.getServerMsg());
						CardLayout cl = (CardLayout)container.getLayout();
			    		cl.show(container, "3");
			    		break;
					}}
			}}
	 }
	 
	public PhraseControl(JPanel container, WoFClient client) {
	    this.container = container;
	    this.client = client;
	    this.isPressed = false;
	   }
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		UA = (PhraseData)client.getServerMsg();
		String command = ae.getActionCommand();
	    if (command.charAt(0) == 'A' || command.charAt(0) == 'B' || command.charAt(0) == 'C') {
	    	 UA.setUserAnswer(command);
	    } else if(command.equals("Submit Answer") && !(UA.getUserAnswer().equals("")) && !(isPressed)) {
	    	try {
	    		isPressed = true;
	    		client.openConnection();
	    		client.sendToServer(UA);
	    		displayLabel("Waiting...");
	    		Thread TH = new Thread(new WaitingThread()); 
	    		TH.start();
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    }}}
	
	public void displayLabel(String Label) {
		PhrasePanel pp = (PhrasePanel)container.getComponent(2);
		pp.setPhraseLabel(Label);
	   	}
	
	public void UpdateGUI(PhraseData UA) {
		PhrasePanel pp = (PhrasePanel)container.getComponent(2);
		pp.setPhraseData(UA);
		}
	
	public void UpdateResults(ArrayList<String> toUpdate) {
		EndGamePanel egp = (EndGamePanel)container.getComponent(3);
		egp.setPlayerUsername(toUpdate.get(0), toUpdate.get(1));
		egp.setScores(toUpdate.get(2), toUpdate.get(3));
		}
	}
