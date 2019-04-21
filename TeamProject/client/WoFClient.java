package client;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ocsf.client.AbstractClient;

public class WoFClient extends AbstractClient{
	
	private JLabel status;
	private JPanel container;
	private JTextArea log;
	private CardLayout cl;
	private Object msg;
	
	public WoFClient() {
		super("localhost", 8300);
		status = new JLabel();
		container = new JPanel();
		log = new JTextArea();
		cl = new CardLayout();
	}
	
	public void setStatus(JLabel status) {
		this.status = status;
	}
	
	public void setContainer(JPanel container) {
		this.container = container;
	}
	
	public void setLog(JTextArea log) {
		this.log = log;
	}
	
	public void setCardLayout(CardLayout cl) {
		this.cl = cl;
	}
	
	protected void handleMessageFromServer(Object arg0) {
		msg = arg0;
	}
	
	public void connectionException(Throwable exception) {
		status.setText("Connection Exception Occurred");
		status.setForeground(Color.RED);
		log.append(exception.getMessage());
		exception.printStackTrace();
	}
	
	public void connectionEstablished() {
		status.setText("Client Connected");
		status.setForeground(Color.GREEN);
	}
	
	public Object getServerMsg() {
		return msg;
	}

}
