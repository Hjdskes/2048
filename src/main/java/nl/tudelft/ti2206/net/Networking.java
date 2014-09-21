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

import nl.tudelft.ti2206.handlers.RemoteInputHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

/**
 * The Networking class is used to set up a connection when playing multiplayer.
 * It is suitable for being both a client and the server.
 */
public class Networking {
	/** Enumeration indicating the mode of operation for the current game. */
	public enum Mode {
		CLIENT, SERVER
	}

	/** The default port to connect to. */
	private static final int PORT = 2526;

	/** The list containing all local IP addresses. */
	private static List<String> addresses = new ArrayList<String>();

	/** . */
	private static Socket socket;

	/** . */
	private static ServerSocket serverSocket;

	/** . */
	private static boolean initialized = false;

	/** . */
	private static boolean sSocketInitialized = false;

	/** . */
	private static DataInputStream dInputStream;

	/** . */
	private static DataOutputStream dOutputStream;

	/** The RemoteInputHandler controls the opponent's Grid. */
	private static RemoteInputHandler remoteInput;

	/** String indicating the last occured error. */
	private static String lastError = "";

	/** True if the connection has been lost. */
	private static boolean connectionLost = false;

	/** . */
	private static Thread thread;

	/** The current mode of operation. */
	private static Mode mode;

	/**
	 * @return A list of local IP addresses.
	 */
	public static List<String> initLocalAddresses() {
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
	 * @return A list (string) all local addresses.
	 */
	public static String localAddresses() {
		String res = "";
		for (String address : initLocalAddresses()) {
			res += address + "\r\n";
		}
		return res;
	}

	/**
	 * Checks if a host is valid by address or by name.
	 * 
	 * @param host
	 *            The hostname or IP address.
	 * @return True if host is valid, false otherwise.
	 */
	public static boolean isValidHost(String host) {
		if (isValidAddr(host)) {
			return true;
		}

		InetAddress address = null;
		try {
			address = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			return false;
		}
		return isValidAddr(address.getHostAddress());
	}

	/**
	 * Check if the supplied string contains a valid IPv4 address.
	 * 
	 * @param address
	 *            The string to check.
	 * @return True if the address is a valid IPv4 address, false otherwise.
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
	 * Starts a server thread (listening on socket).
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
	 * Starts a client and connects to the specified address on a built-in port.
	 * 
	 * @param address
	 *            The address to connect to.
	 */
	public static void startClient(final String address) {
		startClient(address, PORT);
	}

	/**
	 * Starts a client and connects to the specified address and port.
	 * 
	 * @param address
	 *            The address to connect to.
	 * @param port
	 *            The port number.
	 */
	public static void startClient(final String address, final int port) {
		setMode(Mode.CLIENT);
		setLastError("");
		setConnectionLost(false);

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
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
	 * Handles all incoming messages.
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
	 * Sets input streams by socket.
	 * 
	 * @param socket
	 *            The socket.
	 */
	private static void setInput(Socket socket) {
		dInputStream = new DataInputStream(socket.getInputStream());
	}

	/**
	 * Sets output streams by socket.
	 * 
	 * @param socket
	 *            The socket.
	 */
	private static void setOutput(Socket socket) {
		dOutputStream = new DataOutputStream(socket.getOutputStream());
	}

	/**
	 * Sends a string to the output stream.
	 * 
	 * @param str
	 *            The string to send.
	 */
	public static void sendString(String str) {
		if (isConnected()) {
			try {
				dOutputStream.writeBytes(str);
			} catch (IOException e) {
				setConnectionLost(true);
				System.err.println("Unable to send string:");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Handles an incoming response.
	 * 
	 * @param response
	 *            The response message.
	 */
	private static void processResponse(String response) {

		// ignore responses if remoteInput is not set
		if (remoteInput == null)
			return;

		int closing = response.indexOf(']');
		if (response.startsWith("GRID[")) {
			String strGrid = response.substring(5, closing);
			if (remoteInput != null) {
				remoteInput.fillGrid(strGrid);
			}
		} else if (response.startsWith("MOVE[")) {
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
	 * @return The remote address string.
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
	 * @return True if the socket is currently connected, false otherwise.
	 */
	public static boolean isConnected() {

		if (isConnectionLost())
			return false;

		if (!isInitialized())
			return false;

		return socket.isConnected();
	}

	/**
	 * @return True if client socket initialized is initialized, false
	 *         otherwise.
	 */
	public static boolean isInitialized() {
		return initialized;
	}

	/**
	 * Sets the connection state of the client socket (connected/disconnected).
	 * 
	 * @param initialized
	 *            The state to set: true for connected, false for disconnected.
	 */
	private static void setInitialized(boolean initialized) {
		Networking.initialized = initialized;
	}

	/**
	 * @return True if the server socket is initialized.
	 */
	public static boolean isServerSocketInitialized() {
		return sSocketInitialized;
	}

	/**
	 * Sets the connection state of the server socket (connected/disconnected).
	 * 
	 * @param initialized
	 *            The state to set: true for connected, false for disconnected.
	 */
	public static void setServerSocketInitialized(boolean sSocketInitialized) {
		Networking.sSocketInitialized = sSocketInitialized;
	}

	/**
	 * Sets the mode of operation (server or client).
	 * 
	 * @param mode Client or Server.
	 */
	public static void setMode(Mode mode) {
		Networking.mode = mode;
	}

	/**
	 * @return The current mode of operation: Server or Client.
	 * Get mode of operation.
	 */
	public static Mode getMode() {
		return Networking.mode;
	}

	/**
	 * @return The error message for the last error that has occurred.
	 */
	public static String getLastError() {
		return lastError;
	}

	/**
	 * Sets the error message of the last error that has occurred.
	 * 
	 * @param lastError The error message to set.
	 */
	public static void setLastError(String lastError) {
		if (lastError.contains("server socket ")) {
			lastError = lastError.replace("server socket ", "server\r\n");
		} else if (lastError.contains("socket connection ")) {
			lastError = lastError.replace("socket connection ",
					"connection\r\n");
		}
		Networking.lastError = lastError;
	}

	/**
	 * @return The RemoteInputHandler.
	 */
	public static RemoteInputHandler getRemoteInput() {
		return remoteInput;
	}

	/**
	 * Set the RemoteInputHandler.
	 * 
	 * @param remoteInput The RemoteInputHandler to set.
	 */
	public static void setRemoteInput(RemoteInputHandler remoteInput) {
		Networking.remoteInput = remoteInput;
	}

	/**
	 * @return True if the connection has been lost, false otherwise.
	 */
	public static boolean isConnectionLost() {
		return connectionLost;
	}

	/**
	 * Sets if the connection has been lost.
	 * 
	 * @param connectionLost
	 *            True if the connection has been lost.
	 */
	public static void setConnectionLost(boolean connectionLost) {
		Networking.connectionLost = connectionLost;
	}

	/**
	 * Disconnects all sockets and cleans up.
	 */
	@SuppressWarnings("deprecation")
	public static void disconnect() {
		if (thread != null) {
			thread.stop();
		}

		setRemoteInput(null);

		if (getMode() == Mode.SERVER) {
			if (isServerSocketInitialized()) {
				serverSocket.dispose();
				setServerSocketInitialized(false);
			}
		}

		if (isInitialized()) {
			socket.dispose();
			setInitialized(false);
			try {
				dInputStream.close();
				dOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("disconnect(): client socket not initialized");
		}
	}
}
