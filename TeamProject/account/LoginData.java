package account;

import java.io.Serializable;

public class LoginData implements Serializable{
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
