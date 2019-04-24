package account;

import client.*;
import server.*;
import game.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Database
{
  private Connection conn;

  public Database()
  {
	//Read properties file
	Properties prop = new Properties();
	FileInputStream fis = null;    
	try{
		fis = new FileInputStream("db.properties");
	} catch(FileNotFoundException e){
		e.printStackTrace();
	}
	
	try{
		prop.load(fis);
	} catch (IOException e){
		e.printStackTrace();
	}
	String url = prop.getProperty("url");
	String user = prop.getProperty("user");
	String pass = prop.getProperty("password");
	
	try{
		conn = DriverManager.getConnection(url, user, pass);
	} catch (SQLException e){
	      e.printStackTrace();
	}
  }
  
  public ArrayList<String> query(String query) {
	  ArrayList<String> toReturn = new ArrayList<String>();
	  Statement stmt = null;
	try {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		String Username;
		String Password;
		while(rs.next()) {
			Username = rs.getString("username");
			Password = rs.getString("password");
			toReturn.add(Username);
			toReturn.add(Password);
		}
		return toReturn;
	} catch(SQLException e) {
		e.printStackTrace();
		return null;
	}
  }
  
  public boolean executeDML(String dml)
  {
	try {
		Statement stmt = conn.createStatement();
	    stmt.execute(dml);
	   // conn.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		System.out.println("ERROR, in catch\n");
		e.printStackTrace();
	}
	System.out.println("SUCCESS");
	return true;
  }
  public void setConnection(String fn) throws Exception {
		//Read from the properties named db.properties
		Properties prop = new Properties();
		FileInputStream fis = null;

		//Create Connection
		fis = new FileInputStream(fn);
		prop.load(fis);
		String url = prop.getProperty("url");
		String user = prop.getProperty("user");
		String pass = prop.getProperty("password");
		conn = DriverManager.getConnection(url, user, pass);
	}

	public Connection getConnection() {
		return conn;
	}
  
}
