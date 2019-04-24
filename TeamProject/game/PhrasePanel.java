package game;

import account.*;
import client.*;
import server.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PhrasePanel extends JPanel{
	JLabel phraseLabel;
	JTextArea phraseArea;
	JRadioButton choicea;
	JRadioButton choiceb;
	JRadioButton choicec;
	ButtonGroup grp;
	
	int i = 0;
	int n =0;
	
	public void setPhraseData(PhraseData pd) {
		phraseArea.setText((String) pd.getQuestions().get(i));
		choicea.setText(pd.getAnswer().get(n));
		choiceb.setText(pd.getAnswer().get(n+1));
		choicec.setText(pd.getAnswer().get(n+2));
		
		n+=3;
		i++;
		grp.clearSelection();
	}
	
	public void setPhraseLabel(String pl) {
		phraseLabel.setText(pl);
	}
	
	public PhrasePanel(PhraseControl pc) {
		this.setPreferredSize(new Dimension(800, 600));
		JPanel north = new JPanel(new FlowLayout());
		phraseLabel = new JLabel("Choose what the phrase is:");
		phraseArea = new JTextArea();
		north.add(phraseLabel);
		
		JPanel center = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.3;
		c.fill = GridBagConstraints.NORTH;
		c.insets = new Insets(0, 1, 0, 20);
		phraseArea = new JTextArea(3, 40);
		JScrollPane scroll = new JScrollPane(phraseArea);
		phraseArea.setText("Fill in the missing letters");
		phraseArea.setEditable(false);
		center.add(scroll, c);
		
		choicea = new JRadioButton("A)Answer");
		choiceb = new JRadioButton("B)Answer");
		choicec = new JRadioButton("C)Answer");
		
		grp = new ButtonGroup();
		grp.add(choicea);
		grp.add(choiceb);
		grp.add(choicec);
		
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0, 0, 5, 5);
		c.weightx = 0.5;
		c.weighty = 0.05;
		
		choicea.setHorizontalAlignment(JLabel.LEFT);
		center.add(choicea, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LINE_START;
		center.add(choiceb, c);
		
		c.gridx =  0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		center.add(choicec, c);
		
		JPanel south = new JPanel(new FlowLayout());
		JButton submit = new JButton("Submit Answer");
		south.add(submit, c);
		
		choicea.addActionListener(pc);
		choiceb.addActionListener(pc);
		choicec.addActionListener(pc);
		
		submit.addActionListener(pc);
		
		this.setLayout(new BorderLayout());
		this.add(north, BorderLayout.NORTH);
		this.add(center, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
	}

}
