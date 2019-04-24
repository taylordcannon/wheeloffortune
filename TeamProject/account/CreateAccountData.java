//Create Account Data
package account;

import client.*;
import server.*;
import game.*;

import java.io.Serializable;

public class CreateAccountData implements Serializable{
	
	private String username;
	private String password;
	private String password2;
	
	public String getUserName() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPassword2() {
		return password2;
	}
	
	public CreateAccountData(String username, String password, String password2) {
		this.username = username;
		this.password = password;
		this.password2 = password2;
	}

}
