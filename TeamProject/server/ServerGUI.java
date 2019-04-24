package server;

import account.*;
import client.*;
import game.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ServerGUI extends JFrame { 
	private WoFServer server;
	private boolean serverStarted = false;
	
	private JLabel status;
	private String[] labels = {"Port #", "Timeout"};
	private JTextField[] textFields = new JTextField[labels.length];
	private JTextArea log;
	private JButton listen;
	private JButton close;
	private JButton stop;
	private JButton quit;

	public ServerGUI(String title) {	
	    try {
			server = new WoFServer();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
	    this.setTitle(title);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    this.setPreferredSize(new Dimension(800,600));
	    
	    JPanel north = new JPanel(new FlowLayout());
	    north.add(new JLabel("Status:"));
	    status = new JLabel("Not Connected");
	    status.setForeground(new Color(255,0,0));
	    north.add(status);
	    
	    JPanel center = new JPanel(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    
	    c.fill = GridBagConstraints.NONE;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.anchor = GridBagConstraints.LINE_END;
	    c.insets = new Insets(5,5,5,5);
	    c.weightx = 0.5;
	    c.weighty = 0.05;
	    JLabel temp = new JLabel(labels[0]);
	    temp.setHorizontalAlignment(JLabel.RIGHT);
	    center.add(temp,c);
	    
	    c.gridx = 1;
	    c.anchor = GridBagConstraints.LINE_START;
	    textFields[0] = new JTextField();
	    textFields[0].setColumns(5);
	    center.add(textFields[0],c);
	    
	    c.gridx = 0;
	    c.gridy = 1;
	    c.anchor = GridBagConstraints.LINE_END;
	    temp = new JLabel(labels[1]);
	    temp.setHorizontalAlignment(JLabel.RIGHT);
	    center.add(temp,c);
	    
	    c.gridx = 1;
	    c.anchor = GridBagConstraints.LINE_START;
	    textFields[1] = new JTextField();
	    textFields[1].setColumns(5);
	    center.add(textFields[1],c);
	    
	    c.gridx = 0;
	    c.gridy = 5;
	    c.anchor = GridBagConstraints.CENTER;
	    c.weighty = .2;
	    c.gridwidth = 2;
	    center.add(new JLabel("Server Log Below"),c);
	    
	    c.gridx = 0;
	    c.gridy = 6;
	    c.weighty = .3;
	    c.fill = GridBagConstraints.BOTH;
	    c.insets = new Insets(0,50,0,50);
	    log = new JTextArea(8,20);
	    JScrollPane lPane = new JScrollPane(log);
	    center.add(lPane,c);
	    
	    JPanel south = new JPanel(new FlowLayout());
	    listen = new JButton("Listen");
	    listen.addActionListener(new EventHandler("listen"));
	    close = new JButton("Close");
	    close.addActionListener(new EventHandler("close"));
	    stop = new JButton("Stop");
	    stop.addActionListener(new EventHandler("stop"));
	    quit = new JButton("Quit");
	    quit.addActionListener(new EventHandler("quit"));
	    south.add(listen);
	    south.add(close);
	    south.add(stop);
	    south.add(quit);
	    
	    this.setLayout(new BorderLayout());
	    this.add(north,BorderLayout.NORTH);
	    this.add(center,BorderLayout.CENTER);
	    this.add(south,BorderLayout.SOUTH);
	    
	    this.pack();
	    this.setVisible(true);
	    	
	}
	
	class EventHandler implements ActionListener
	  {
		
		private String input;
		
		public EventHandler(String input)
		{
			this.input = input;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(input == "listen") 
			{
				try
				{
					listen();
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(input == "close")
			{
				try
				{
					close();
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(input == "stop") {stop();}
			else if(input == "quit") {quit();}
		}
		
	  }
	
	public void listen() throws IOException
	{		
		if(textFields[0].getText().equals("") || textFields[0].getText().equals(""))
		{
			log.append("Port Number/timeout not entered before pressing Listen\n");
		}
		else
		{
			int port = Integer.parseInt(textFields[0].getText());
			int timeout = Integer.parseInt(textFields[1].getText());
			
			if((port >= 1024 && port <= 65535) && timeout > 0)
			{
				server.setPort(port);
				server.setTimeout(timeout);
				server.setStatus(status);
				server.setLog(log);
				server.listen();
				serverStarted = true;
			}
		}
	}
	
	public void close() throws IOException
	{
		if(!serverStarted)
		{
			log.append("Server not currently started\n");
		}
		else
		{
			server.stopListening();
			server.close();
			serverStarted = false;
		}
	}
	
	public void stop()
	{
		if(!serverStarted)
		{
			log.append("Server not currently started\n");
		}
		else
		{
			server.stopListening();
		}
	}
	
	public void quit()
	{
		System.exit(0);
	}
	
	
	
	/************************************
	 * Input: The answer to the question*
	 * In String form.					*
	 * 									*
	 * This takes the given answer and	*
	 * Adds shows it on the log.		*
	 ************************************/
	public void showAnswer(String answer)
	{
		log.append("ANSWER:" + answer + "\n");
	}
	
	/************************************
	 * Input: An arraylist that contains*
	 * strings formatted like so:		*
	 * "Player Name,Score"				*
	 * 									*
	 * This will split the arraylist at *
	 * the comma to get player name and *
	 * score, then display them on the  *
	 * log.								*
	 ************************************/
	public void showScores(ArrayList<String> namedScores)
	{
		log.append("Scores:\n");
		for(int i = 0; i < namedScores.size(); i++)
		{
			String player = namedScores.get(i).split(",")[0];
			String score = namedScores.get(i).split(",")[1];
			log.append("Player: " + player + "\tScore: " + score + "\n");
		}
	}
	
	public static void main(String args[])
	{
		new ServerGUI("Server");
	}
}