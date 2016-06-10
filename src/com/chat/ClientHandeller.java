package com.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ClientHandeller implements ActionListener, KeyListener,
		WindowListener {

	private ClientWindow client;

	public ClientHandeller(ClientWindow client) {
		this.client = client;
	}

	public void send(String msg, int status) {
		if (msg.equals(""))
			return;
		if (status == Client.MSG) {
			msg = client.getClient().get_Name() + ": " + msg;
			msg = "/m/" + msg;
			client.getClient().send(msg.getBytes());
			client.getTxtMessage().setText("");
		}
		else if(status == Client.DIS){
			msg = "/d/" + msg;
			client.getClient().send(msg.getBytes());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		send(client.getTxtMessage().getText(), Client.MSG);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			send(client.getTxtMessage().getText(), Client.MSG);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		send("" + client.getClient().getID(), Client.DIS);
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
