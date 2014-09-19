package nl.tudelft.ti2206.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import nl.tudelft.ti2206.game.GameWorld.GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

public class Networking {

	private static final int PORT = 2526;

	private static List<String> addresses = new ArrayList<String>();

	private static DataInputStream is;
	private static DataOutputStream os;

	private static ObjectOutputStream outputStream;
	private static ObjectInputStream inputStream;

	private static GameState oState;
	private static int oTile;

	public static void initalize() {
		initIPv4Address();
		System.out.println(listIPAdresses());
	}

	private static void initIPv4Address() {

		try {
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

	private static String listIPAdresses() {
		String res = "";
		for (String address : addresses) {
			res += address + "\n";
		}
		return res;
	}

	public static void startServer() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				System.out.println("Starting server");

				ServerSocketHints serverSocketHint = new ServerSocketHints();
				// 0 means no timeout. Probably not the greatest idea in
				// production!
				serverSocketHint.acceptTimeout = 0;

				// Only one app can listen to a port at a time, keep in mind
				// many ports are reserved
				// especially in the lower numbers ( like 21, 80, etc )
				ServerSocket serverSocket = Gdx.net.newServerSocket(
						Protocol.TCP, PORT, serverSocketHint);

				// Loop forever
				while (true) {
					// Create a socket

					Socket socket = serverSocket.accept(null);

					System.out.println("I'm connected to "
							+ socket.getRemoteAddress());

					is = new DataInputStream(socket.getInputStream());
					os = new DataOutputStream(socket.getOutputStream());

					try {
						outputStream = new ObjectOutputStream(socket
								.getOutputStream());
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					try {
						inputStream = new ObjectInputStream(socket.getInputStream());
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					try {
						os.writeBytes("I'm the server");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					String responseLine;
					try {
						while ((responseLine = is.readLine()) != null) {
							processResponse(responseLine);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}).start(); // And, start the thread running
	}

	public static void startClient(final String address, final int port) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Starting client");

				SocketHints socketHints = new SocketHints();
				// Socket will time our in 4 seconds
				socketHints.connectTimeout = 4000;
				// create the socket and connect to the server entered in the
				// text box ( x.x.x.x format ) on port 9021
				Socket socket = Gdx.net.newClientSocket(Protocol.TCP, address,
						port, socketHints);

				System.out.println("I'm connected to "
						+ socket.getRemoteAddress());

				is = new DataInputStream(socket.getInputStream());
				os = new DataOutputStream(socket.getOutputStream());

				try {
					outputStream = new ObjectOutputStream(socket.getOutputStream());
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					inputStream = new ObjectInputStream(socket.getInputStream());
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				try {
					os.writeBytes("I'm a client");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				String responseLine;
				try {
					while ((responseLine = is.readLine()) != null) {
						processResponse(responseLine);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start(); // And, start the thread running
	}

	public static void sendString(String msg) {
		try {
			os.writeBytes(msg + "\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void send(Sendable obj) {
		try {
			outputStream.writeObject(obj);
		} catch (IOException e) {
			System.err.println("Unable to send message");
		}
	}

	private static void processResponse(String msg) {
		System.out.println("response = " + msg);

		String[] parts = msg.split(":");

		if (parts[0].startsWith("LOST")) {
			setOpponentState(GameState.LOST);
		}

		if (parts[0].startsWith("TILE")) {
			setOpponentHighestTile(parts[1]);
		}
	}

	private static void setOpponentHighestTile(String string) {
		oTile = Integer.parseInt(string);
	}

	public static int getOpponentHighestTile() {
		return oTile;
	}

	private static void setOpponentState(GameState state) {
		GameState oState = state;
	}

	public static GameState getOpponentState() {
		return oState;
	}

}
