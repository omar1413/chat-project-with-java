package com.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginHandeller implements ActionListener{
	private String name;
	private String ip;
	private int port;
	private Login login;
	
	public LoginHandeller(Login login) {
		this.login = login;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		init();
		login(name, ip ,port);
	}
	
	private void init(){
		name = login.getTxtName().getText();
		ip = login.getTxtIp().getText();
		port = Integer.parseInt(login.getTxtPort().getText());
	}
	
	public void login(String name, String ip, int port){
		login.dispose();
		new ClientWindow(name, ip, port);
	}

}
