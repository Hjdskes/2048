package nl.tudelft.ti2206.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class Networking {

	private static List<String> addresses = new ArrayList<String>();
	
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
	
}
