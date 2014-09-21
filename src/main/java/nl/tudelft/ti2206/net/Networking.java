package nl.tudelft.ti2206.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import nl.tudelft.ti2206.game.TwentyFourtyGame.GameState;
import nl.tudelft.ti2206.handlers.RemoteInputHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class Networking {

	public enum Mode {
		CLIENT, SERVER
	}

	private static final int PORT = 2526;

	private static List<String> addresses = new ArrayList<String>();

	private static Socket socket;
	private static ServerSocket serverSocket;

	private static boolean initialized = false;
	private static boolean sSocketInitialized = false;

	private static DataInputStream dInputStream;
	private static DataOutputStream dOutputStream;
	// private static ObjectInputStream oInputStream;
	// private static ObjectOutputStream oOutputStream;

	private static RemoteInputHandler remoteInput;

	private static String lastError = "";

	private static boolean connectionLost = false;

	private static Thread thread;

	private static Mode mode;

	public static List<String> initalize() {
		return initLocalAddresses();
	}

	/**
	 * Create a list of local IP addresses.
	 * 
	 * @return list
	 */
	private static List<String> initLocalAddresses() {

		if (addresses.isEmpty()) {

			try {
				System.out.println("Enumerating network devices...");
				Enumeration<NetworkInterface> interfaces = NetworkInterface
						.getNetworkInterfaces();
				for (NetworkInterface ni : Collections.list(interfaces)) {
					for (InetAddress address : Collections.list(ni
							.getInetAddresses())) {
						if (address instanceof Inet4Address) {
							if (!address.getHostAddress().equals("127.0.0.1")) {
								addresses.add(address.getHostAddress());
							}
						}
					}
				}
			} catch (SocketException se) {
				se.printStackTrace();
			}
		}
		return addresses;
	}

	/**
	 * Get a list (string) of all local addresses.
	 * 
	 * @return string all addresses
	 */
	public static String strAddresses() {
		String res = "";
		for (String address : getLocalAddresses()) {
			res += address + "\r\n";
		}
		return res;
	}

	/**
	 * Get stored list of local addresses.
	 * 
	 * @return list of local addresses
	 */
	public static List<String> getLocalAddresses() {

		return initLocalAddresses();
	}

	/**
	 * Check if a host is valid (by address or by name).
	 * 
	 * @param host
	 *            the string (can be hostname or IP)
	 * @return true if host is valid
	 */
	public static boolean isValidHost(String host) {

		if (isValidAddr(host))
			return true;

		InetAddress address = null;
		try {
			address = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			return false;
		}
		return isValidAddr(address.getHostAddress());
	}

	/**
	 * Check if string contains a valid IPv4 address.
	 * 
	 * @param address
	 *            the address
	 * @return true if address is valid IPv4
	 */
	public static boolean isValidAddr(String address) {
		if (address
				.matches("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$")) {
			String[] groups = address.split("\\.");

			for (int i = 0; i <= 3; i++) {
				String segment = groups[i];
				if (segment == null || segment.length() <= 0) {
					return false;
				}

				int value = 0;
				try {
					value = Integer.parseInt(segment);
				} catch (NumberFormatException e) {
					return false;
				}
				if (value > 255) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Start a server thread (listening on socket).
	 */
	public static void startServer() {
		setMode(Mode.SERVER);
		setLastError("");
		setConnectionLost(false);

		thread = new Thread(new Runnable() {

			@Override
			public void run() {

				System.out.println("Starting server on port " + PORT);

				ServerSocketHints serverSocketHint = new ServerSocketHints();
				serverSocketHint.acceptTimeout = 0;
				serverSocketHint.reuseAddress = true;

				try {
					serverSocket = Gdx.net.newServerSocket(Protocol.TCP, PORT,
							serverSocketHint);

				} catch (Exception e) {

					String msg = e.getMessage();

					if (msg != null) {
						setLastError(msg);
					}
				}

				if (serverSocket != null) {

					setServerSocketInitialized(true);

					socket = serverSocket.accept(null);

					if (socket != null) {

						setInitialized(true);

						setInput(socket);
						setOutput(socket);

						while (isConnected()) {
							receiveLoop();
						}
					}
				}
			}
		});
		thread.start();

	}

	/**
	 * Start a client and connect to address on builtin port.
	 * 
	 * @param address
	 *            the address
	 */
	public static void startClient(final String address) {
		startClient(address, PORT);
	}

	/**
	 * Start a client and connect to address and port
	 * 
	 * @param address
	 *            the address
	 * @param port
	 *            the port number
	 */
	public static void startClient(final String address, final int port) {

		setMode(Mode.CLIENT);
		setLastError("");
		setConnectionLost(false);

		thread = new Thread(new Runnable() {

			@Override
			public void run() {

				System.out.println("Starting client, connecting to " + address + ":" + port);

				SocketHints socketHints = new SocketHints();
				socketHints.connectTimeout = 4000;
				socketHints.keepAlive = true;

				try {
					socket = Gdx.net.newClientSocket(Protocol.TCP, address,
							port, socketHints);
				} catch (Exception e) {

					String msg = e.getMessage();

					if (msg != null) {
						setLastError(msg);

					}
				}

				if (socket != null) {

					setInput(socket);
					setOutput(socket);

					setInitialized(true);

					while (isConnected()) {
						receiveLoop();
					}
				}
			}

		});
		thread.start();
	}

	/**
	 * Handle all incoming messages.
	 */
	@SuppressWarnings("deprecation")
	private static void receiveLoop() {
		String response;
		try {

			while (isConnected()
					&& (response = dInputStream.readLine()) != null) {
				processResponse(response);
			}
		} catch (Exception e) {
			setConnectionLost(true);
			e.printStackTrace();
		}

	}

	/**
	 * Set input streams by socket.
	 * 
	 * @param socket
	 *            the socket
	 */
	private static void setInput(Socket socket) {
		dInputStream = new DataInputStream(socket.getInputStream());

		// try {
		// oInputStream = new ObjectInputStream(socket.getInputStream());
		// } catch (IOException e) {
		// System.err.println("Error setting object input stream:");
		// e.printStackTrace();
		// }
	}

	/**
	 * Set ouput streams by socket.
	 * 
	 * @param socket
	 *            the socket
	 */
	private static void setOutput(Socket socket) {
		dOutputStream = new DataOutputStream(socket.getOutputStream());

		// try {
		// oOutputStream = new ObjectOutputStream(socket.getOutputStream());
		// } catch (IOException e) {
		// System.err.println("Error setting object output stream:");
		// e.printStackTrace();
		// }

	}

	/**
	 * Send string to output stream.
	 * 
	 * @param str
	 *            the string
	 */
	public static void sendString(String str) {
		if (isConnected()) {
			try {
				dOutputStream.writeBytes(str);
				// dOutputStream.writeChars(str);
			} catch (IOException e) {
				setConnectionLost(true);

				System.err.println("Unable to send string:");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Handle incoming response.
	 * 
	 * @param response
	 *            the response message.
	 */
	private static void processResponse(String response) {

		// ignore responses if remoteInput is not set
		if (remoteInput == null)
			return;

		int closing = response.indexOf(']');

		if (response.startsWith("STATE[")) {
			String strstate = response.substring(6, closing);

			switch (strstate) {
			case "WON":
				remoteInput.setState(GameState.WON);
				break;
			case "LOST":
				remoteInput.setState(GameState.LOST);
				break;
			default:
				// remoteInput.setState(GameState.RUNNING);
				break;
			}
		} else if (response.startsWith("GRID[")) {

			remoteInput.fillGrid(response.substring(5, closing));

		} else if (response.startsWith("MOVE[")) {

			// don't need the closing tag's position here as it's the first
			// character
			// after MOVE[ (position 5)
			char direction = response.charAt(5);

			switch (direction) {
			case 'U':
				remoteInput.moveUp();
				break;

			case 'D':
				remoteInput.moveDown();
				break;

			case 'R':
				remoteInput.moveRight();
				break;

			case 'L':
				remoteInput.moveLeft();
				break;

			default:
				System.err.println("Unknown direction: " + direction);
				break;

			}
		}
	}

	/**
	 * Get the remote address.
	 * 
	 * @return the address string.
	 */
	public static String getRemoteAddress() {
		return socket.getRemoteAddress().replaceFirst("/", "");
	}

	// /**
	// * Get the socket that's currently in use.
	// * @return
	// */
	// public static Socket getSocket() {
	// return socket;
	// }

	/**
	 * Check if socket is currently connected.
	 * 
	 * @return true if connected
	 */
	public static boolean isConnected() {

		if (isConnectionLost())
			return false;

		if (!isInitialized())
			return false;

		return socket.isConnected();
	}

	/**
	 * Check if client socket is initialized.
	 * 
	 * @return true if client socket initialized
	 */
	public static boolean isInitialized() {
		return initialized;
	}

	/**
	 * Set if client socket initialized.
	 * 
	 * @param initialized
	 */
	private static void setInitialized(boolean initialized) {
		// System.out.println("setting socket initialized to " + initialized);
		Networking.initialized = initialized;
	}

	/**
	 * Check if server socket is initialized.
	 * 
	 * @return true if server socket initialized
	 */
	public static boolean isServerSocketInitialized() {
		return sSocketInitialized;
	}

	/**
	 * Set if server socket is initialized
	 * 
	 * @param sSocketInitialized
	 */
	public static void setServerSocketInitialized(boolean sSocketInitialized) {
		System.out.println("setting serverSocket initialized to "
				+ sSocketInitialized);
		Networking.sSocketInitialized = sSocketInitialized;
	}

	/**
	 * Set mode of operation (server or client).
	 * 
	 * @param mode
	 */
	public static void setMode(Mode mode) {
		Networking.mode = mode;
	}

	/**
	 * Get mode of operation.
	 * 
	 * @return server or client
	 */
	public static Mode getMode() {
		return Networking.mode;
	}

	/**
	 * Get the error message for the last error that has occured.
	 * 
	 * @return error message
	 */
	public static String getLastError() {
		return lastError;
	}

	/**
	 * Set the error message of the last error that has occured.
	 * 
	 * @param lastError
	 */
	public static void setLastError(String lastError) {

		if (lastError.contains("server socket "))
			lastError = lastError.replace("server socket ", "server\r\n");

		if (lastError.contains("socket connection "))
			lastError = lastError.replace("socket connection ",
					"connection\r\n");

		Networking.lastError = lastError;
	}

	/**
	 * Get the remote input handler object.
	 * 
	 * @return remote input handler object
	 */
	public static RemoteInputHandler getRemoteInput() {
		return remoteInput;
	}

	/**
	 * Set the remote input handler object
	 * 
	 * @param remoteInput
	 */
	public static void setRemoteInput(RemoteInputHandler remoteInput) {
		Networking.remoteInput = remoteInput;
	}

	/**
	 * Check if connection has been lost.
	 * 
	 * @return true if connection is lost
	 */
	public static boolean isConnectionLost() {
		return connectionLost;
	}

	/**
	 * Set if connection has been lost.
	 * 
	 * @param connectionLost
	 *            true if connection has been lost
	 */
	public static void setConnectionLost(boolean connectionLost) {
		Networking.connectionLost = connectionLost;
	}

	/**
	 * Disconnect all sockets and clean up.
	 */
	public static void disconnect() {

		if (thread != null)
			thread.stop();

		setRemoteInput(null);

		if (getMode() == Mode.SERVER) {

			if (isServerSocketInitialized()) {
				serverSocket.dispose();
				setServerSocketInitialized(false);
			}
		}

		if (isInitialized()) {
			System.out.println("disconnect(): disconnecting...");
			socket.dispose();
			setInitialized(false);
			try {
				dInputStream.close();
				dOutputStream.close();
				// oInputStream.close();
				// oOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("disconnect(): client socket not initialized");
		}
	}
}
