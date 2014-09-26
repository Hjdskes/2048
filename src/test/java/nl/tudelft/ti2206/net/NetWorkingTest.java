package nl.tudelft.ti2206.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import nl.tudelft.ti2206.game.HeadlessLauncher;
import nl.tudelft.ti2206.net.Networking.Mode;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.net.Socket;

public class NetWorkingTest {

	private static ArrayList<String> spyList;
	private static Socket socket;

	/** The singleton Networking instance. */ 
	private static Networking networking = Networking.getInstance();
	
	@BeforeClass
	public static void setUpBeforeClass() {
		new HeadlessLauncher().launch();

		ArrayList<String> addresses = new ArrayList<String>();
		spyList = spy(addresses);
		
		socket = mock(Socket.class);

		networking.setAddresses(spyList);
	}

	@Test
	public void testInitLocalAddresses() {
		// clear the spyList to start cleanly.
		spyList.clear();

		assertEquals(0, spyList.size());
		when(spyList.isEmpty()).thenReturn(true);
		networking.initLocalAddresses();
		verify(spyList, atLeast(0)).add(anyString());
	}

	@Test
	public void testLocalAddresses() {
		spyList.add("10.0.0.1");
		spyList.add("192.168.2.1");
		assertEquals("10.0.0.1\r\n192.168.2.1\r\n", networking.localAddresses());
	}

	@Test
	public void testIsValidHost() {
		assertFalse(networking.isValidHost("192,"));
		assertTrue(networking.isValidHost("192.168.2.1"));
		assertTrue(networking.isValidHost("10.0.0.2"));
		// host name taken from javadoc
		assertTrue(networking.isValidHost("java.sun.com"));
	}

	@Test
	public void testIsValidAddr() {
		assertFalse(networking.isValidAddr(""));
		assertFalse(networking.isValidAddr("add.res.s"));
		assertFalse(networking.isValidAddr("280.100.0.2"));
		assertFalse(networking.isValidAddr("192.uhh.122.???"));
		assertTrue(networking.isValidAddr("10.0.0.2"));
	}


	@Test
	public void testGetRemoteAddress() {
		when(socket.getRemoteAddress()).thenReturn("/192.168.1.1:2526");
		networking.setSocket(socket);
		String res = networking.getRemoteAddress();
		assertEquals("192.168.1.1:2526", res);
	}

	@Test
	public void testIsConnected() {
		networking.setSocket(socket);
		when(socket.isConnected()).thenReturn(true);
		
		networking.setConnectionLost(true);
		assertFalse(networking.isConnected());
		networking.setConnectionLost(false);
		
		networking.setInitialized(false);
		assertFalse(networking.isConnected());
		
		networking.setInitialized(true);
		assertTrue(networking.isConnected());
	}

	@Test
	public void testIsInitialized() {
		networking.setInitialized(true);
		assertTrue(networking.isInitialized());
		networking.setInitialized(false);
		assertFalse(networking.isInitialized());
	}

	@Test
	public void testSetServerSocketInitialized() {
		networking.setServerSocketInitialized(true);
		assertTrue(networking.isServerSocketInitialized());
		networking.setServerSocketInitialized(false);
		assertFalse(networking.isServerSocketInitialized());
	}

	@Test
	public void testSetMode() {
		networking.setMode(Mode.CLIENT);
		assertEquals(Mode.CLIENT, networking.getMode());
	}

	@Test
	public void testSetLastError() {
		networking.setLastError("server socket ");
		assertEquals("server\r\n", networking.getLastError());
		networking.setLastError("socket connection ");
		assertEquals("connection\r\n   ", networking.getLastError());
	}

	@Test
	public void testDisconnect() {
		// Disconnect, and make sure no exceptions are thrown.
		networking.disconnect();
	}

}
