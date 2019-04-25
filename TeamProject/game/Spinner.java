package game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.mysql.fabric.xmlrpc.base.Array;

import client.WoFClient;

public class Spinner extends JPanel{
	
	private JLabel score;
	
	
	public Spinner(JPanel container, WoFClient client) {
		String[] scores = {"50", "100", "150", "200", "250", "300"};
		Random rand = new Random();
		
		
		JPanel inner = new JPanel();
		inner.setBounds(250, 15, 1, 1);
		inner.setLayout(null);
		setLayout(null);
		
		
		JLabel spinLabel = new JLabel("Spin!");		
		spinLabel.setHorizontalAlignment(SwingConstants.CENTER);		
		spinLabel.setBounds(80, 15, 300, 20);
		add(spinLabel);
		
		score = new JLabel("0");
		//score.setHorizontalAlignment(SwingConstants.CENTER);
		score.setBounds(230, 80, 300, 30);		
		add(score);
		
		
		JButton spinButton = new JButton("Spin");
		spinButton.setBounds(160, 160, 150, 35);
		add(spinButton);
		spinButton.setPreferredSize(new Dimension(30, 30));
		spinButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				int n = rand.nextInt(10);
				for(int z = 0; z < n; z++) {
					String str = (scores[z % scores.length]);
					score.setText((str));
					}				
				}			
		});
		
		this.add(inner);
	
		
		
	}
}

	
	


