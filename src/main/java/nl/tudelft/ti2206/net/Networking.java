package nl.tudelft.ti2206.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Observable;

import nl.tudelft.ti2206.log.Logger;
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
public class Networking extends Observable {
	/** A singleton reference to this class. */
	private static Networking instance = new Networking();

	/** The singleton reference to the Logger instance. */
	private static Logger logger = Logger.getInstance();

	/** Get current class name, used for logging output. */
	private final String className = this.getClass().getSimpleName();

	/** Enumeration indicating the mode of operation for the current game. */
	public enum Mode {
		NONE, CLIENT, SERVER
	}

	/** The default port to connect to. */
	private final int PORT = 2526;

	/** The list containing all local IP addresses. */
	private List<String> addresses = new ArrayList<String>();

	/** Current socket. */
	private Socket clientSocket;

	/** Server socket. */
	private ServerSocket serverSocket;

	/** Socket is initialized or not. */
	private boolean initialized = false;

	/** Server socket is initialized. */
	private boolean sSocketInitialized = false;

	/** DataStream for input. */
	private DataInputStream dInputStream;

	/** DataStream for output. */
	private DataOutputStream dOutputStream;

	/** String indicating the last occurred error. */
	private String lastError = "";

	private boolean error = false;

	/** True if the connection has been lost. */
	private boolean connectionLost = false;

	/** Current running thread. */
	private Thread thread;

	/** The current mode of operation. */
	private Mode mode = Mode.NONE;

	/** Overrides the default constructor. */
	private Networking() {
	}

	public static Networking getInstance() {
		return instance;
	}

	/**
	 * @return A list of local IP addresses.
	 */
	public List<String> initLocalAddresses() {
		if (addresses.isEmpty()) {
			try {
				logger.info(className + "/" + getMode(),
						"Enumerating network interfaces...");
				Enumeration<NetworkInterface> interfaces = NetworkInterface
						.getNetworkInterfaces();
				for (NetworkInterface ni : Collections.list(interfaces)) {
					for (InetAddress address : Collections.list(ni
							.getInetAddresses())) {
						// ignore localhost
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
	public String localAddresses() {
		initLocalAddresses();
		String res = "";
		for (String address : addresses) {
			res += address + "\r\n";
		}
		return res;
	}

	/**
	 * Checks if a host is valid by address or by name. This method can be very
	 * slow as it uses the system's DNS.
	 * 
	 * @param host
	 *            The hostname or IP address.
	 * @return True if host is valid, false otherwise.
	 */
	public boolean isValidHost(String host) {
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
	public boolean isValidAddr(String address) {
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
	public void startServer() {
		setMode(Mode.SERVER);
		clearErrors();
		setConnectionLost(false);

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info(className + "/" + getMode(),
						"Starting server, listening on port " + PORT);
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
					clientSocket = serverSocket.accept(null);

					if (clientSocket != null) {
						setStreams(clientSocket);
						setInitialized(true);

						logger.info(className + "/" + getMode(),
								"Incoming connection accepted from "
										+ getRemoteAddress());

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
	public void startClient(final String address) {
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
	public void startClient(final String address, final int port) {
		setMode(Mode.CLIENT);
		clearErrors();
		setConnectionLost(false);

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				SocketHints socketHints = new SocketHints();
				socketHints.connectTimeout = 4000;
				socketHints.keepAlive = true;

				logger.info(className + "/" + getMode(),
						"Starting client, trying to connect to " + address
								+ ":" + port + "...");
				logger.debug(className + "/" + getMode(),
						"Connection timeout is " + socketHints.connectTimeout
								+ "ms");
				try {
					clientSocket = Gdx.net.newClientSocket(Protocol.TCP,
							address, port, socketHints);
				} catch (Exception e) {
					String msg = e.getMessage();
					if (msg != null) {
						setLastError(msg);
					}
				}

				if (clientSocket != null) {
					setStreams(clientSocket);
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
	private void receiveLoop() {
		String response;
		try {
			while (isConnected()
					&& (response = dInputStream.readLine()) != null) {
				processResponse(response);
			}
		} catch (Exception e) {
			setConnectionLost(true);
			// e.printStackTrace();
		}
	}

	private void setStreams(Socket socket) {

		if (socket == null)
			return;

		setInputStream(socket.getInputStream());
		setOutputStream(socket.getOutputStream());
	}

	/**
	 * Sets input streams.
	 * 
	 * @param inputStream
	 *            The socket.
	 */
	private void setInputStream(InputStream inputStream) {
		dInputStream = new DataInputStream(inputStream);
	}

	/**
	 * Sets output streams.
	 * 
	 * @param outputStream
	 *            The socket.
	 */
	private void setOutputStream(OutputStream outputStream) {
		dOutputStream = new DataOutputStream(outputStream);
	}

	/**
	 * Sends a string to output stream. Appends newline if not yet present.
	 */
	public void sendString(String str) {
		sendString(str, true);
	}

	/**
	 * Sends a string to output stream.
	 * 
	 * @param str
	 *            the string to send
	 * @param newLine
	 *            append newline
	 */
	public void sendString(String str, boolean newLine) {

		logger.debug(className + "/" + getMode(), "Sending string: " + str);

		if (newLine && !str.endsWith("\r\n")) {
			str += "\r\n";
		}

		if (isConnected()) {
			try {
				dOutputStream.writeBytes(str);

			} catch (IOException e) {
				setConnectionLost(true);
				disconnect();
				logger.error(className + "/" + getMode(),
						"Unable to send string:" + str);
			}
		}
	}

	/**
	 * Handles an incoming response.
	 * 
	 * @param response
	 *            The response message.
	 */
	private void processResponse(String response) {

		logger.debug(className + "/" + getMode(), "processResponse(" + response
				+ ") sending to " + countObservers() + " registered observers");

		setChanged();
		notifyObservers(response);
	}

	/**
	 * @return The remote address string.
	 */
	public String getRemoteAddress() {
		return clientSocket.getRemoteAddress().replaceFirst("/", "");
	}

	/**
	 * @return True if the socket is currently connected, false otherwise.
	 */
	public boolean isConnected() {

		if (isConnectionLost())
			return false;

		if (!isInitialized())
			return false;

		return clientSocket.isConnected();
	}

	/**
	 * @return True if client socket initialized is initialized, false
	 *         otherwise.
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * Sets the connection state of the client socket (connected/disconnected).
	 * 
	 * @param initialized
	 *            The state to set: true for connected, false for disconnected.
	 */
	protected void setInitialized(boolean initialized) {

		if (initialized) {
			logger.info(className + "/" + getMode(),
					"Client socket initialized");
			setChanged();
			notifyObservers();
		}

		this.initialized = initialized;
	}

	/**
	 * @return True if the server socket is initialized.
	 */
	public boolean isServerSocketInitialized() {
		return sSocketInitialized;
	}

	/**
	 * Sets the connection state of the server socket (connected/disconnected).
	 * 
	 * @param initialized
	 *            The state to set: true for connected, false for disconnected.
	 */
	public void setServerSocketInitialized(boolean sSocketInitialized) {
		this.sSocketInitialized = sSocketInitialized;
	}

	/**
	 * Sets the mode of operation (server or client).
	 * 
	 * @param mode
	 *            Client or Server.
	 */
	public void setMode(Mode mode) {
		
		logger.debug(className, "Changing mode to " + mode);
		this.mode = mode;
	}

	/**
	 * @return The current mode of operation: Server or Client. Get mode of
	 *         operation.
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * @return The error message for the last error that has occurred.
	 */
	public String getLastError() {
		return lastError;
	}

	/**
	 * Sets the error message of the last error that has occurred.
	 * 
	 * @param lastError
	 *            The error message to set.
	 */
	public void setLastError(String lastError) {

		if (!lastError.isEmpty()) {
			this.error = true;

			logger.error(className + "/" + getMode(), lastError);

			if (lastError.contains("server socket ")) {
				lastError = lastError.replace("server socket ", "server\r\n");
			} else if (lastError.contains("socket connection ")) {
				lastError = lastError.replace("socket connection ",
						"connection\r\n");
			}
		}
		this.lastError = lastError;
	}

	public boolean errorOccured() {
		return error;
	}

	public void clearErrors() {
		this.error = false;
		this.lastError = "";
	}

	/**
	 * @return True if the connection has been lost, false otherwise.
	 */
	public boolean isConnectionLost() {
		return connectionLost;
	}

	/**
	 * Sets if the connection has been lost.
	 * 
	 * @param connectionLost
	 *            True if the connection has been lost.
	 */
	public void setConnectionLost(boolean connectionLost) {
		this.connectionLost = connectionLost;

		if (connectionLost) {
			logger.info(className + "/" + getMode(), "Connection lost.");
		}
	}

	/**
	 * Disconnects all sockets and cleans up.
	 */
	@SuppressWarnings("deprecation")
	public void disconnect() {

		logger.info(className + "/" + getMode(), "Disconnecting...");

		if (thread != null) {
			thread.stop();
		}

		if (getMode() == Mode.SERVER) {
			if (isServerSocketInitialized()) {
				serverSocket.dispose();
				setServerSocketInitialized(false);
			}
		}

		if (isInitialized()) {
			clientSocket.dispose();
			setInitialized(false);
			try {
				dInputStream.close();
				dOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			logger.debug(className + "/" + getMode(),
					"disconnect(): client socket not initialized (no connection)");
		}

		clearErrors();
		setMode(Mode.NONE);
	}

	/** For testing purposes only! */
	public void setSocket(Socket mockSocket) {
		clientSocket = mockSocket;
	}

	/** For testing purposes only! */
	public void setAddresses(ArrayList<String> spyAddresses) {
		addresses = spyAddresses;
	}
}
