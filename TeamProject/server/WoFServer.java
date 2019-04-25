package server;



import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import javax.swing.*;

import account.CreateAccountData;
import account.Database;
import account.LoginData;
import game.PhraseData;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WoFServer extends AbstractServer
{
  private JTextArea log;
  private JLabel status;
  private Database database;
  private ArrayList<ArrayList<String>> ClientList;			//Tracks client names and statuses
  private ArrayList<Integer> ClientScores;					//Tracks clients scores
  private ArrayList<PhraseData> Questions;				//Storage for questions
  private int QuestionNumber;								//Value to track question number
  Random rand  = new Random();
  
  public WoFServer() throws FileNotFoundException {
    super(8300);
    database = new Database();
    ClientList = new ArrayList<ArrayList<String>>();
    new ArrayList<ConnectionToClient>();
    ClientScores = new ArrayList<Integer>();
    Questions = new ArrayList<PhraseData>();
    QuestionNumber = 0;
   
    FileReader in = new FileReader("WoF.txt");
    BufferedReader br = new BufferedReader(in);
    
    String string;
    PhraseData pd = new PhraseData(); 
    ArrayList<String> answers = new ArrayList<String>();
    int Tracker = 0;
        
    try {
		while ((string = br.readLine()) != null) {	
			if (Tracker % 4 == 0) {
				if(!(Tracker == 0)) {
					Questions.add(pd);
					pd = new PhraseData(); 
				}
				pd.setQuestion(string);
				Tracker+=1;
			}
			else if(Tracker % 4 == 1 || Tracker % 4 == 2 || Tracker % 4 == 3) {
				String str = "";
				if (string.contains("$")) {
					str = string.replace("$", "");
					System.out.print(str);
					answers.add(str);
					pd.setRightAnswer(str);
				} else 
					answers.add(string);
				if(Tracker % 4 == 3) {
					pd.setAnswers(answers);
					answers = new ArrayList<String>();
				}
				Tracker+=1; 
				}}
		} catch (IOException e) {e.printStackTrace();}
	
    try {
		in.close();
	} catch (IOException e) {e.printStackTrace();}
    
  }
  
  public void setDataBase(Database database) {
	  this.database = database;
  }
  
  public void setLog(JTextArea log) {
    this.log = log;
  }
  
  public void setStatus(JLabel status) {
    this.status = status;
  }
  
  public JTextArea getLog() {
    return log;
  }
  
  public JLabel getStatus() {
    return status;
  }
  
  public Database getDatabase() {
	  return database;
  }
  
  @Override
  protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {   
    if (arg0 instanceof LoginData) {
       LoginData ld = (LoginData)arg0;
       String un = ld.getUserName(); 
       String pass = ld.getPassword();
       ArrayList<String> validUserInfo = database.query("select username, password from player where username = '" + un + "';");
       if(un.equals(validUserInfo.get(0)) && pass.equals(validUserInfo.get(1))) {
    	   
    	   try {
    		   ClientList.add(new ArrayList<String>());						//Pushes blank arraylist onto 2d arraylist (new client)
    		   ClientList.get(ClientList.size()-1).add(ld.getUserName());	//Sets the user's name to their name in the database
    		   ClientList.get(ClientList.size()-1).add("Not Ready");		//Sets this user's status to not ready  
    		   ClientScores.add(0); 										//Sets this users score to 0 by default
    		   arg1.sendToClient("Success");  
    	   }
    	   catch(IOException e) {
    		   e.printStackTrace();
    	   }}
       else {
    	   try {
    		   arg1.sendToClient("Incorrect Username or Password");   
    	   }
    	   catch(IOException e) {
    		   e.printStackTrace();
    	   }}
    	
    }
    
    else if (arg0 instanceof CreateAccountData) {
       CreateAccountData cad = (CreateAccountData)arg0;
       String holder = "insert into player values('" + cad.getUserName().toString() + "','" + cad.getPassword().toString() + "');";
       boolean validUserInfo = database.executeDML(holder);
       
       if(validUserInfo) {
    	   try {
    		   ClientList.add(new ArrayList<String>());						//Pushes blank arraylist onto 2d arraylist (new client)
    		   ClientList.get(ClientList.size()-1).add(cad.getUserName());	//Sets the user's name to their name in the database
    		   ClientList.get(ClientList.size()-1).add("Not Ready");		//Sets this user's status to not ready
    		   ClientScores.add(0); 										//Sets this users score to 0 by default
    		   arg1.sendToClient("Success");  
    	   }
    	   catch(IOException e) {
    		   e.printStackTrace();
    	  }}
       else {
    	   try {
    		arg1.sendToClient("Invalid data");   
    	   } catch(IOException e) {
    		   e.printStackTrace();
    	  }}
    }
    
   else if(arg0 instanceof String) {
    	if (arg0.equals("Ready")) {
    		Thread[] clientThreadList = getClientConnections();
    		boolean isDistinct = true;
    		boolean toSend = true;
    		
    		for (ArrayList<String> Client : ClientList) 
    	        if (!(Client.get(1).equals(("Ready")))) 
    	            isDistinct = false;
    		if(!isDistinct) {
        		for(int i=0;i<clientThreadList.length;i++)
        			if(arg1 == (ConnectionToClient)clientThreadList[i])
        				ClientList.get(i).set(1, "Ready");
        		for (ArrayList<String> Client : ClientList) 
        	        if (!(Client.get(1).equals(("Ready")))) 
        	        	toSend = false;
        		}
    		if(toSend && ClientList.size() >= 2) {
    			log.append("\nGame Has Started");
    			System.out.println(ClientList.size());
    		 	this.sendToAllClients("Move Questions");
    			}
    		}
    	
    	else if (arg0.equals("Need Question")) {
    		Thread[] clientThreadList = getClientConnections();
    		boolean isDistinct = true;
    		boolean toSend = true;
    		
    		for (ArrayList<String> Client : ClientList) 
    	        if (!Client.get(1).equals("Need Question")) 
    	            isDistinct = false;
    		if(!isDistinct) {
        		for(int i=0;i<clientThreadList.length;i++) 
        			if(arg1 == (ConnectionToClient)clientThreadList[i]) 
        				ClientList.get(i).set(1, "Need Question");
        		for (ArrayList<String> Client : ClientList) 
        	        if (!(Client.get(1).equals(("Need Question")))) 
        	        	toSend = false;
        		}
    		if(toSend) {
    			this.sendToAllClients(Questions.get(QuestionNumber));
    			QuestionNumber++;
    		 	}
    		}
    	
    	else if (arg0.equals("Need Result")) {
    		Thread[] clientThreadList = getClientConnections();
    		boolean isDistinct = true;
    		boolean toSendDecider = true;
    		
    		for (ArrayList<String> Client : ClientList) 
    	        if (!Client.get(1).equals("Need Result")) 
    	            isDistinct = false;
    		if(!isDistinct) {
        		for(int i=0;i<clientThreadList.length;i++) 
        			if(arg1 == (ConnectionToClient)clientThreadList[i]) 
        				ClientList.get(i).set(1, "Need Result");  
        		for (ArrayList<String> Client : ClientList) 
        	        if (!(Client.get(1).equals(("Need Result")))) 
        	        	toSendDecider = false;
    			}
        	if(toSendDecider) {
	    		ArrayList<String> toSend = new ArrayList<String>();
	    		for(int i=0;i<ClientList.size();i++)
	    			toSend.add(ClientList.get(i).get(0));
	    		for(int j=0;j<ClientScores.size();j++) {
	    			toSend.add(Integer.toString(ClientScores.get(j)));
	    		}
				this.sendToAllClients(toSend);
        	}}
    	
   		}
    
    else if (arg0 instanceof PhraseData) {
    	boolean isDistinct = true; boolean toSend = true;
        PhraseData pd = (PhraseData)arg0;
        Thread[] clientThreadList = getClientConnections();
        
        for (ArrayList<String> Client : ClientList) 
	        if (!Client.get(1).equals("Submitted")) 
	            isDistinct = false;
        if(!isDistinct) {
        	for(int i=0;i<clientThreadList.length;i++) 
	    		if(arg1 == (ConnectionToClient)clientThreadList[i]) 
	    			ClientList.get(i).set(1, "Submitted");
        	for(int j=0;j<clientThreadList.length;j++)
        		if (pd.getUserAnswer().equals(pd.getRightAnswer())) 	        	 
		    		if(arg1.equals((ConnectionToClient)clientThreadList[j])) {
		    			int n = rand.nextInt(500);
		    			int toPass = ClientScores.get(j);
		    			toPass += n;
		    			ClientScores.set(j, toPass);
		    	}
	        for (ArrayList<String> Client : ClientList) 
    	        if (!(Client.get(1).equals(("Submitted")))) 
    	        	toSend = false;
        	}
    	if(toSend) {
    		if(QuestionNumber < Questions.size()) 
    			this.sendToAllClients("Move Question");
    		else if(QuestionNumber >= Questions.size()) 
    			this.sendToAllClients("Move Answer");
    	}}
    }
  
  protected void listeningException(Throwable exception) 
  {
    //Display info about the exception
    log.append("Listening Exception:" + exception);
    exception.printStackTrace();
    log.append(exception.getMessage());
    
    if (this.isListening())
    {
      log.append("Server not Listening\n");
      status.setText("Not Connected");
      status.setForeground(Color.RED);
    }
  }
  
  protected void serverStarted() 
  {
    log.append("Server Started");
    status.setText("Listening...");
    status.setForeground(Color.GREEN);
  }
  
  protected void serverStopped() 
  {
	  log.append("\nServer Stopped Accepting New Clients - Press Listen to Start Accepting New Clients");
	  status.setText("Stopped");
	  status.setForeground(Color.RED);
   
  }
  
  protected void serverClosed() 
  {
	  log.append("\nServer and all current clients are closed - Press Listen to Restart");
	  status.setText("Closed");
	  status.setForeground(Color.RED);
  }
 
  protected void clientConnected(ConnectionToClient client) 
  {
	  log.append("\nClient Connected");
	  status.setText("Connected");
	  status.setForeground(Color.GREEN);
      
  }
  
  protected void clientDisconnected(ConnectionToClient client) {
	  log.append("\nClient Disconnected!");
  }
}

