package account;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class DatabaseTest1 {
	
	Database db;
	
	@Before
	public void setUp() {
		db = new Database();
	}

	@Test
	public void testSetConnection() {
		try {
			db.setConnection("./db.properties");
		} catch (Exception e) {
			fail("Connection not set.");
		}
	}

	@Test
	public void testGetConnection() {
		try {
			db.setConnection("./db.properties");
			if(db.getConnection() == null) {
				fail("Connection was null");
			}
		} catch (Exception e) {
			fail("Connection not set.");
		}
	}
	
	@Test
	public void testQuery() {
		try {
			db.setConnection("./db.properties");
			if(db.getConnection() == null) {
				fail("Connection was null");
			}
			else {
				ArrayList<String> r = db.query("select * from player;");
				if(r == null) {
					fail("Query returned no results");
				}
			}
		} catch (Exception e) {
			fail("Connection not set.");
		}
	}

	@Test
	public void testExecuteDML() {
		try {
			db.setConnection("./db.properties");
			if(db.getConnection() == null) {
				fail("Connection was null");
			}
			else {
				if(!db.executeDML("insert into player values('testuser','testpass');")) {
					fail("DML not executed");
				}
			}
		} catch (Exception e) {
			fail("Connection not set.");
		}
	}

}
