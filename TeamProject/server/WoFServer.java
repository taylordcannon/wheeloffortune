package server;

//Ray: Look at comments for TODOs and information regarding changes

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

import javax.swing.*;

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
  
  public WoFServer() throws FileNotFoundException {
    super(8300);
    database = new Database();
    ClientList = new ArrayList<ArrayList<String>>();
    new ArrayList<ConnectionToClient>();
    ClientScores = new ArrayList<Integer>();
    Questions = new ArrayList<PhraseData>();
    QuestionNumber = 0;
    
    //TODO: This portion needs to include data from the text file
    //Phrases needs to include every PhraseData object 
    //Uncommenting the bit below this will run 2 generic phrases
    //This can be run to test the model without the actual data
    
    FileReader in = new FileReader("Questions.txt");
    BufferedReader br = new BufferedReader(in);
    
    String string;
    PhraseData qd = new PhraseData();
    ArrayList<String> answers = new ArrayList<String>();
    ArrayList<String> questions=new ArrayList<String>();
    ArrayList<String> rightAnswers = new ArrayList<String>();
        
    try {
		while ((string = br.readLine()) != null) 
		{	
			if (string.contains("?"))
			{
				questions.add(string);
				//System.out.println(string);
				
			}
			else
			{
				if(string.contains("$"))
				{
					rightAnswers.add(string.replace("$", ""));
					//System.out.println(qd.getRightAnswer());
					answers.add(string.replace("$", ""));
					qd.setRightAnswers(rightAnswers);
				}
				else
				{
					answers.add(string);
					qd.setAnswers(answers);
				}
			}
			
				//qd.setQuestions(questions);
				Questions.add(qd);
			
			
		}
		System.out.println(answers);
		System.out.println(qd.getRightAnswers());
		
		qd.setQuestions(questions);
	
		    
		    
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    try {
		in.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
   /* PhraseData tester = new PhraseData();
    tester.setQuestion("This is a test question!");
    
    ArrayList<String> tosetA = new ArrayList<String>();
    tosetA.add("A)This is a test answer A");
    tosetA.add("B)This is a test answer B");
    tosetA.add("C)This is a test answer C");
    tester.setAnswers(tosetA);
    tester.setRightAnswer("B)This is a test answer B");
    Questions.add(tester);
    
    PhraseData tester2 = new PhraseData();
    tester2.setQuestion("This is a test question2!");
    
    ArrayList<String> tosetA2 = new ArrayList<String>();
    tosetA2.add("A)This is a test answer A");
    tosetA2.add("B)This is a test answer B");
    tosetA2.add("C)This is a test answer C");
    tester2.setAnswers(tosetA2);
    tester2.setRightAnswer("B)This is a test answer B");
    Questions.add(tester2);*/
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
       String un = ld.getUserName(); String pass = ld.getPassword();
       ArrayList<String> validUserInfo = database.query("select username, password from users where username = '" + un + "';");
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
       
       //This portion commented out below is tester data in the case the databse isn't working
       //Commenting out the database code above and uncommenting this allows for testing without the database
       //This is assuming you click the login button instead of the create account button
       //Attempting to submit an account to the database will result in a crash without the database available
    	/*try {
 		   ClientList.add(new ArrayList<String>());						
 		   ClientList.get(ClientList.size()-1).add("Tester Username");			
 		   ClientList.get(ClientList.size()-1).add("Not Ready");		
 		   
 		   ClientScores.add(0); 										
 		   arg1.sendToClient("Success");  
 	   }
 	   catch(IOException e) {
 		   e.printStackTrace();
       }
    }
    
    else if (arg0 instanceof CreateAccountData) {
       CreateAccountData cad = (CreateAccountData)arg0;
       String holder = "insert into users values('" + cad.getUserName().toString() + "','" + cad.getPassword().toString() + "');";
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
    	  }}*/
    }
    
   else if(arg0 instanceof String) {
    	//Set this up to determine whether not all clients are ready to play
        //If they are, send notification value to break infinite waiting loop for both clients in second thread of LoginControl (switches panels)
        //If not, updates the client who sent in "Ready" to proper ready status 
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
    		 	this.sendToAllClients("Move Questions");
    			}
    		}
    	
    	//A player that answers a question will send in the "Need Question" status, which will need to be updated on this side.
    	//Once both players have the "Need Question" status (Which would replace the "Ready" status), then the server sends a new question to both clients.
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
    	
    	else if(arg0.equals("Play Again")) {
    		//Sets all the the data back to default values
    		//This includes both client's score and the QuestionNumber value
    		//Client List player names should stay the same, but the ready status should be set to "Ready"
    		//On the EndGameSSControl, another thread should be instantiated that checks for notification from the server that 
    		//both players are ready to play again
    		}
   		}
    
    //This is the portion that handles data from PhraseControl
    //Checks the clients answer against the correct answer. If right, increments that user's score. If wrong does nothing
    //Sets their status to submitted regardless
    //If all clients have submitted data, server sends all clients a notification to break infinite loop in second thread (switch panels)
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
        		if (pd.getUserAnswer().equals(pd.getRightAnswers().get(QuestionNumber))) 	        	 
		    		if(arg1.equals((ConnectionToClient)clientThreadList[j])) {
		    			int toPass = ClientScores.get(j); toPass += 1;
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

