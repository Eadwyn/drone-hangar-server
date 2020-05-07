package com.utrons.dronehangarserver.comm;

import com.utrons.dronehangarserver.comm.client.TCPClient;

import java.net.InetSocketAddress;

public class TCPClientHelper {
	private static final String IP = "112.74.216.37";
	private static final int PORT = 10500;

	private static TCPClient tcpClient;

	public static TCPClient getTcpClient () {
		if (null == tcpClient) {
			synchronized (TCPClientHelper.class) {
				if (null == tcpClient) {
					tcpClient = new TCPClient();
				}
			}
		}

		return tcpClient;
	}

	public static void connect() {
		InetSocketAddress address = new InetSocketAddress(IP, PORT);
		getTcpClient().connect(address);
	}
}
