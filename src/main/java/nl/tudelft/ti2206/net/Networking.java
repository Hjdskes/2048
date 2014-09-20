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

	private static Thread thread;

	private static Mode mode;
	
//	private static boolean startReceived;

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

	public static String strAddresses() {
		String res = "";
		for (String address : getLocalAddresses()) {
			res += address + "\r\n";
		}
		return res;
	}

	public static List<String> getLocalAddresses() {

		return initLocalAddresses();
	}

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

	public static void startServer() {
		setMode(Mode.SERVER);
		setLastError("");
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
					// e.printStackTrace();

					String msg = e.getMessage();

					if (msg != null) {

						if (msg.contains("server socket "))
							msg = msg.replace("server socket ", "server\r\n");

						setLastError(msg);
					}
				}

				if (serverSocket != null) {

					setServerSocketInitialized(true);

					// while (true) {

						socket = serverSocket.accept(null);

						if (socket != null) {

							setInitialized(true);
							System.out
									.println("Accepted incoming connection from "
											+ socket.getRemoteAddress());

							setInput(socket);
							setOutput(socket);

							while (isConnected()) {
								receiveLoop();
							}
						}
					// }
				}
			}
		});
		thread.start();

	}

	public static void startClient(final String address) {
		startClient(address, PORT);
	}

	public static void startClient(final String address, final int port) {

		setMode(Mode.CLIENT);
		setLastError("");
		//setStartReceived(false);

		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Starting client, connecting to " + address
						+ ":" + port);

				SocketHints socketHints = new SocketHints();
				socketHints.connectTimeout = 4000;
				socketHints.keepAlive = true;

				try {
					socket = Gdx.net.newClientSocket(Protocol.TCP, address,
							port, socketHints);
				} catch (Exception e) {
					// e.printStackTrace();

					String msg = e.getMessage();

					if (msg != null) {

						if (msg.contains("socket connection "))
							msg = msg.replace("socket connection ",
									"connection\r\n");

						setLastError(msg);

					}
				}

				if (socket != null) {

					System.out.println("I'm connected to "
							+ socket.getRemoteAddress());

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

	@SuppressWarnings("deprecation")
	private static void receiveLoop() {
		String response;
		try {

			while (isConnected()
					&& (response = dInputStream.readLine()) != null) {
				processResponse(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void setInput(Socket socket) {
		dInputStream = new DataInputStream(socket.getInputStream());

		// try {
		// oInputStream = new ObjectInputStream(socket.getInputStream());
		// } catch (IOException e) {
		// System.err.println("Error setting object input stream:");
		// e.printStackTrace();
		// }
	}

	private static void setOutput(Socket socket) {
		dOutputStream = new DataOutputStream(socket.getOutputStream());

		// try {
		// oOutputStream = new ObjectOutputStream(socket.getOutputStream());
		// } catch (IOException e) {
		// System.err.println("Error setting object output stream:");
		// e.printStackTrace();
		// }

	}

	public static void sendString(String str) {
		if (getMode() == Mode.SERVER)
			System.out.println("[SERVER]: sending str = " + str);
		else
			System.out.println("[CLIENT]: sending str = " + str);

		if (isConnected()) {
			try {
				dOutputStream.writeBytes(str);
				// dOutputStream.writeChars(str);
			} catch (IOException e) {
				System.err.println("Unable to send string:");
				e.printStackTrace();
			}
		}
	}

	public static void sendObject(Object object) {
		System.out.println("sending obj = " + object);
		if (isConnected()) {
			// try {
			// oOutputStream.writeObject(object + "\r\n");
			// } catch (IOException e) {
			// System.err.println("Unable to send object:");
			// e.printStackTrace();
			// }
		}
	}

	private static void processResponse(String response) {
		System.out.println("str = " + response);
//		if (response.startsWith("[START]"))
//		{
//			System.out.println("Start received");
//			Networking.setStartReceived(true);
//		}
//		else
		if (response.startsWith("GRID[")) {
			int closing = response.indexOf(']');

			String strGrid = response.substring(5, closing);
			
			if (remoteInput != null)
				remoteInput.fillGrid(strGrid);
			
			if (getMode() == Mode.SERVER)
				System.out.println("[SERVER]: recv grid str = " + strGrid);
			else
				System.out.println("[CLIENT]: recv grid str = " + strGrid);

		} else if (response.startsWith("MOVE[")) {

			// int closing = response.indexOf(']');
			char direction = response.charAt(5);

			System.out.println("received dir = " + direction);

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
				System.out.println("Unknown direction: " + direction);
				break;

			}
		}

	}

	public static String getRemoteAddress() {
		return socket.getRemoteAddress().replaceFirst("/", "");
	}

	public static Socket getSocket() {
		return socket;
	}

	public static boolean isConnected() {

		if (!isInitialized())
			return false;

		return socket.isConnected();
	}

	public static boolean isInitialized() {
		return initialized;
	}

	private static void setInitialized(boolean initialized) {
		System.out.println("setting socket initialized to " + initialized);
		Networking.initialized = initialized;
	}

	public static void disconnect() {

		//setStartReceived(false);
		
		if (thread != null)
			thread.stop();

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

	public static boolean isServerSocketInitialized() {
		return sSocketInitialized;
	}

	public static void setServerSocketInitialized(boolean sSocketInitialized) {
		System.out.println("setting serverSocket initialized to "
				+ sSocketInitialized);
		Networking.sSocketInitialized = sSocketInitialized;
	}

	public static void setMode(Mode mode) {
		Networking.mode = mode;
	}

	public static Mode getMode() {
		return Networking.mode;
	}

	public static String getLastError() {
		return lastError;
	}

	public static void setLastError(String lastError) {
		Networking.lastError = lastError;
	}

	public static RemoteInputHandler getRemoteInput() {
		return remoteInput;
	}

	public static void setRemoteInput(RemoteInputHandler remoteInput) {
		Networking.remoteInput = remoteInput;
	}
//
//	public static boolean isStartReceived() {
//		return startReceived;
//	}
//
//	public static void setStartReceived(boolean startReceived) {
//		Networking.startReceived = startReceived;
//	}
}
