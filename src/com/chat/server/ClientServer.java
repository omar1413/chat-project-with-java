package com.chat.server;

import java.net.InetAddress;

public class ClientServer {
	private InetAddress ip;
	private String name;
	private int port;
	private int ID;
	public int attempt = 0;
	
	public ClientServer(String name, InetAddress ip, int port, int ID){
		this.name = name;
		this.ip = ip;
		this.port = port;
		this.ID = ID;
	}

	public InetAddress getIp() {
		return ip;
	}

	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}

	public int getID() {
		return ID;
	}
	
	
}
