package com.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client {

	public static final int MSG = 0;
	public static final int DIS = 1;

	private String name;
	private String address;
	private int port;
	private DatagramSocket socket;
	private InetAddress ip;
	private Thread send, receive;

	boolean running;
	private int ID = -1;
	private ClientWindow clientWindow;

	public Client(ClientWindow clientWindow, String name, String address,
			int port) {

		this.name = name;
		this.address = address;
		this.port = port;
		this.clientWindow = clientWindow;

		clientWindow.console("connecting..... ");
		boolean connection = openConnection(address);
		// System.out.println(connection);
		if (connection) {

			running = true;
			receive();
			String msg = "/c/" + name;
			send(msg.getBytes());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (ID == -1)
				errorConnectoin();
			else {
				try {
					socket.setSoTimeout(5000);
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				clientWindow
						.console("Connected Succefully to the server with ID : "
								+ ID);
			}
		} else {
			errorConnectoin();
		}

	}

	public  void errorConnectoin() {
		running = false;
		clientWindow.console("Connection faild....");
		System.err.println("error in connection");
		JOptionPane
				.showMessageDialog(null, "error in connection to the server");
		clientWindow.dispose();
		new Login().setVisible(true);
	}

	private boolean openConnection(String address) {
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			return false;
		} catch (SocketException e) {
			return false;
		}
		return true;
	}

	public void receive() {
		receive = new Thread("receive") {
			public  void run() {
				while (running) {
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data,
							data.length);
					try {
						socket.receive(packet);
					} catch (SocketTimeoutException e) {
						// TODO Auto-generated catch block
						running = false;
						socket.close();
						JOptionPane.showMessageDialog(null,
								"the srever has down");
						clientWindow.dispose();
						new Login().setVisible(true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String msg = new String(packet.getData());
					process(msg);
				}
			}
		};
		receive.start();
	}

	public void process(String msg) {
		int length = msg.indexOf('\0');

		if (msg.startsWith("/c/")) {
			this.ID = Integer.parseInt(msg.substring(3, length));
			System.out.println(ID);
		} else if (msg.startsWith("/m/")) {
			clientWindow.console(msg.substring(3, length));
		} else if (msg.startsWith("/tot/")) {
			System.out.println("hi");
			msg = "/tot/" + ID;
			send(msg.getBytes());
		}
	}

	public void send(final byte[] data) {
		send = new Thread("send") {
			public  void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length,
						ip, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		};
		send.start();
	}

	public String get_Name() {
		return name;
	}

	public String getIp() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public int getID() {
		return ID;
	}

}
