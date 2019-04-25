package account;

import client.*;
import server.*;
import game.*;

import java.io.Serializable;

public class LoginData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	
	public String getUserName() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public LoginData(String username, String password) {
		this.username = username;
		this.password = password;
	}

}
