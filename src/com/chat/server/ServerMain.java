package com.chat.server;

public class ServerMain {
	
	/*
	 * private int port;
	
	public ServerMain(int port){
		this.port = port;
	}
	*/
	public static void main(String[] args){
		if(args.length != 1){
			System.out.println("Usage : java -jar chat.jat [port]");
			return;
		}
		
		int port = Integer.parseInt(args[0]);
		new Server(port);
	}
}
