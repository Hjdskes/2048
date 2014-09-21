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

import nl.tudelft.ti2206.handlers.RemoteInputHandler;
import nl.tudelft.ti2206.net.Networking.Mode;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.net.Socket;

public class NetWorkingTest {

	private static ArrayList<String> spyList;
	private static Socket socket;
	private static RemoteInputHandler handler;

	@BeforeClass
	public static void setUpBeforeClass() {
		ArrayList<String> addresses = new ArrayList<String>();
		spyList = spy(addresses);
		
		socket = mock(Socket.class);
		handler = mock(RemoteInputHandler.class);

		Networking.setAddresses(spyList);
	}

	@Test
	public void testInitLocalAddresses() {
		// clear the spyList to start cleanly.
		spyList.clear();

		assertEquals(0, spyList.size());
		when(spyList.isEmpty()).thenReturn(true);
		Networking.initLocalAddresses();
		verify(spyList, atLeast(0)).add(anyString());
	}

	@Test
	public void testLocalAddresses() {
		spyList.add("10.0.0.1");
		spyList.add("192.168.2.1");
		assertEquals("10.0.0.1\r\n192.168.2.1\r\n", Networking.localAddresses());
	}

	@Test
	public void testIsValidHost() {
		assertTrue(Networking.isValidHost("192.168.2.1"));
		assertFalse(Networking.isValidHost("192."));
		assertTrue(Networking.isValidHost("10.0.0.2"));
		// host name taken from javadoc
		assertTrue(Networking.isValidHost("java.sun.com"));
	}

	@Test
	public void testIsValidAddr() {
		assertFalse(Networking.isValidAddr(""));
		assertFalse(Networking.isValidAddr("add.res.s"));
		assertFalse(Networking.isValidAddr("280.100.0.2"));
		assertFalse(Networking.isValidAddr("192.uhh.122.???"));
		assertTrue(Networking.isValidAddr("10.0.0.2"));
	}


	@Test
	public void testGetRemoteAddress() {
		when(socket.getRemoteAddress()).thenReturn("/192.168.1.1:2526");
		Networking.setSocket(socket);
		String res = Networking.getRemoteAddress();
		assertEquals("192.168.1.1:2526", res);
	}

	@Test
	public void testIsConnected() {
		Networking.setSocket(socket);
		when(socket.isConnected()).thenReturn(true);
		
		Networking.setConnectionLost(true);
		assertFalse(Networking.isConnected());
		Networking.setConnectionLost(false);
		
		Networking.setInitialized(false);
		assertFalse(Networking.isConnected());
		
		Networking.setInitialized(true);
		assertTrue(Networking.isConnected());
	}

	@Test
	public void testIsInitialized() {
		Networking.setInitialized(true);
		assertTrue(Networking.isInitialized());
		Networking.setInitialized(false);
		assertFalse(Networking.isInitialized());
	}

	@Test
	public void testSetServerSocketInitialized() {
		Networking.setServerSocketInitialized(true);
		assertTrue(Networking.isServerSocketInitialized());
		Networking.setServerSocketInitialized(false);
		assertFalse(Networking.isServerSocketInitialized());
	}

	@Test
	public void testSetMode() {
		Networking.setMode(Mode.CLIENT);
		assertEquals(Mode.CLIENT, Networking.getMode());
	}

	@Test
	public void testSetLastError() {
		Networking.setLastError("server socket ");
		assertEquals("server\r\n", Networking.getLastError());
		Networking.setLastError("socket connection ");
		assertEquals("connection\r\n", Networking.getLastError());
	}

	@Test
	public void testGetRemoteInput() {
		Networking.setRemoteInput(handler);
		assertEquals(handler, Networking.getRemoteInput());
	}

	@Test
	public void testSetRemoteInput() {
		Networking.setRemoteInput(handler);
		assertEquals(handler, Networking.getRemoteInput());
	}

	@Test
	public void testDisconnect() {
		// Disconnect, and make sure no exceptions are thrown.
		Networking.disconnect();
	}

}
