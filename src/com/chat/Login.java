package com.chat;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtIp;
	private JTextField txtPort;
	
	private LoginHandeller handeller;

	public Login() {
		handeller = new LoginHandeller(this);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(301, 390);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name : ");
		lblName.setBounds(122, 53, 44, 24);
		contentPane.add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(62, 75, 164, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		txtIp = new JTextField();
		txtIp.setBounds(62, 160, 164, 20);
		contentPane.add(txtIp);
		txtIp.setColumns(10);
		
		JLabel lblIp = new JLabel("IP : ");
		lblIp.setBounds(131, 133, 26, 24);
		contentPane.add(lblIp);
		
		JLabel lblPort = new JLabel("port : ");
		lblPort.setBounds(126, 213, 37, 24);
		contentPane.add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setColumns(10);
		txtPort.setBounds(62, 240, 164, 20);
		contentPane.add(txtPort);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(96, 318, 89, 23);
		contentPane.add(btnLogin);
		btnLogin.addActionListener(handeller);
	}
	
	public JTextField getTxtName() {
		return txtName;
	}

	public JTextField getTxtIp() {
		return txtIp;
	}

	public JTextField getTxtPort() {
		return txtPort;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
