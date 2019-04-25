package game;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class EndGamePanel extends JPanel {
	private JLabel p1Label;
	private JLabel p2Label;
	private JLabel p1Score;
	private JLabel p2Score;

	public void setPlayerUsername(String p1,String p2) {
	    p1Label.setText(p1+" Score: ");
	    p2Label.setText(p2+" Score: ");
	  }
	public void setScores(String p1, String p2) {
		p1Score.setText(p1);
		p2Score.setText(p2);
	  }

	public EndGamePanel(EndGameControl egs) {
		JPanel inner = new JPanel( );
		inner.setBounds(250,15,1,1);
		inner.setLayout(null);
		setLayout(null);
		
		JLabel welcomeLabel = new JLabel("Game Over");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setBounds(80,15, 300, 20);
		add(welcomeLabel);
		
		 p1Label=new JLabel();
		 p1Label.setBounds(150,70,300,30);
		 add(p1Label);
		 p1Label.setText("");
		 
		 p2Label=new JLabel();
		 p2Label.setBounds(150,100,300,30);
		 add(p2Label);
		 p2Label.setText("");

		 p1Score = new JLabel();
		 p1Score.setBounds(310, 70, 300,30);
		 add(p1Score);
		 p1Score.setText("");	 
		 
		 p2Score = new JLabel();
		 p2Score.setBounds(310, 100, 300,30);
		 add(p2Score);
		 p2Score.setText("");
		 
		JButton playButton = new JButton("Exit");
		playButton.setBounds(160, 160, 150, 35);
		add(playButton);
		playButton.setPreferredSize(new Dimension(30,30));
		playButton.addActionListener(egs);

		
		this.add(inner);
		}
	}
