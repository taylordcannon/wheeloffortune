package account;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccount extends JPanel{
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField passwordField2;
	private JLabel errorLabel;
	
	public void setError(String error) {
		errorLabel.setText(error);
	}
	
	public String getUsername() {
		return usernameField.getText();
	}
	
	public String getPassword() {
		return new String(passwordField.getPassword());
	}
	
	public String getPassword2() {
		return new String(passwordField2.getPassword());
	}
	
	public CreateAccount(CreateAccountControl lc) {
		JPanel labelPanel = new JPanel(new GridLayout(3, 3, 5, 5));
		errorLabel = new JLabel("", JLabel.CENTER);
		errorLabel.setForeground(Color.RED);
		JLabel instructionLabel = new JLabel("Enter your username and password to creat an account.", JLabel.CENTER);
		JLabel instructionLabel2 = new JLabel("Password must be at least 6 characters", JLabel.CENTER);
		labelPanel.add(errorLabel);
		labelPanel.add(instructionLabel);
		labelPanel.add(instructionLabel2);
		
		JPanel createAccountPanel = new JPanel(new GridLayout(3, 3, 5, 5));
		JLabel usernameLabel = new JLabel("Username:", JLabel.RIGHT);
		usernameField = new JTextField(10);
		JLabel passwordLabel = new JLabel("Password:", JLabel.RIGHT);
		passwordField = new JPasswordField(10);
		JLabel passwordLabel2 = new JLabel("Verify Password:", JLabel.RIGHT);
		passwordField2 = new JPasswordField(10);
		
		createAccountPanel.add(usernameLabel);
		createAccountPanel.add(usernameField);
		createAccountPanel.add(passwordLabel);
		createAccountPanel.add(passwordField);
		createAccountPanel.add(passwordLabel2);
		createAccountPanel.add(passwordField2);
		
		JPanel buttonPanel = new JPanel();
		JButton submit = new JButton("Submit");
		submit.addActionListener(lc);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(lc);
		buttonPanel.add(submit);
		buttonPanel.add(cancel);
		
		JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
		grid.add(labelPanel);
		grid.add(createAccountPanel);
		grid.add(buttonPanel);
		this.add(grid);
	}

}
