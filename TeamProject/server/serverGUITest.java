package server;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class serverGUITest {
	
	ServerGUI gui;

	@Before
	public void setUp() throws Exception {
		gui = new ServerGUI("Server");
	}

	@Test
	public void testListen() {
		try {
			gui.listen();
		} catch (IOException e) {
			fail("Server couldn't listen");
		}
	}

	@Test
	public void testClose() {
		try {
			gui.listen();
		} catch (IOException e) {
			fail("Server couldn't listen");
		}
		try {
			gui.close();
		} catch (IOException e) {
			fail("Server couldn't close");
		}
	}

}
