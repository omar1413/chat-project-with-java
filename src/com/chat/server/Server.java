package com.chat.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

	private DatagramSocket socket;
	private int port;
	private Thread run, manage, send, receive;

	private boolean running = false;

	private List<ClientServer> list;
	
	private List<Integer> online = new ArrayList<Integer>();

	public Server(int port) {
		this.port = port;

		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		run = new Thread(this, "run");
		run.start();

		list = new ArrayList<ClientServer>();
	}

	public void manageClients() {
		manage = new Thread("manage") {
			public void run() {
				while (running) {
					String msg = "/tot/";
					sendToAll(msg);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int i = 0; i < list.size(); i++){
						ClientServer client = list.get(i);
						if(!online.contains(list.get(i).getID())){
							client.attempt++;
						}
						else {
							client.attempt = 0;
						}
						if(client.attempt > 5){
							list.remove(i);
							System.out.println(client.getName() + " with ip : " + client.getIp() + ":" + client.getPort() + " timeout");
						}
					}
					online.clear();
				}
			}
		};
		manage.start();
	}

	public void receive() {
		receive = new Thread("receive") {
			public void run() {
				while (running) {
					System.out.println(list.size());
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data,
							data.length);
					try {
						socket.receive(packet);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					process(packet);
				}
			}
		};
		receive.start();
	}

	void process(DatagramPacket packet) {
		String str = new String(packet.getData());

		int length = str.indexOf('\0');

		if (str.startsWith("/c/")) {
			String name = str.substring(3, length);
			// UUID u = UUID.randomUUID();
			// String id = u.toString();
			
			int id = UniqueID.getID();
			ClientServer client = new ClientServer(name, packet.getAddress(),
					packet.getPort(), id);
			list.add(client);
			System.out.println(name + " connected with ip "
					+ packet.getAddress() + ":" + packet.getPort() + " ID : "
					+ id);
			String msg = "/c/" + id;
			send(msg.getBytes(), client.getIp(), client.getPort());
		} 
		else if (str.startsWith("/m/")) {
			String msg = str;
			sendToAll(msg);
		} 
		else if(str.startsWith("/d/")){
			int id = Integer.parseInt(str.substring(3, length));
			disconnect(id);
			System.out.println(id);
		}
		else if(str.startsWith("/tot/")){
			int id = Integer.parseInt(str.substring(5,length));
			online.add(id);
		}
	}

	public void disconnect(int ID){
		ClientServer client = null;
		for(int i = 0; i < list.size(); i++){
			client = list.get(i);
			if(ID == client.getID()){
				list.remove(i);
				break;
			}
		}
		System.out.println(client.getName() + " with ip : " + client.getIp() + ":" + client.getPort() + " disconnected");
	}
	
	public void sendToAll(String msg) {
		for (int i = 0; i < list.size(); i++) {
			ClientServer client = list.get(i);
			send(msg.getBytes(), client.getIp(), client.getPort());
		}

	}

	public void send(final byte[] data, final InetAddress ip, final int port) {
		send = new Thread("send") {
			public void run() {
				DatagramPacket packet = new DatagramPacket(data, data.length,
						ip, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		send.start();
	}

	public void run() {
		running = true;

		System.out.println("server start at port " + port);

		manageClients();
		receive();
	}

}
