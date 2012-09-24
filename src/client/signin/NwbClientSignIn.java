package client.signin;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.*;

public class NwbClientSignIn extends JFrame {

	private JLabel jLabel = null;
	private JTextField userField = null;
	private JLabel jLabel1 = null;
	private JTextField IPField = null;
	private JButton connectButton = null;
	private static String userStr;
	private NwbClientSignInPanel signinPanel = null;
	private int width;
	private int height;

	public NwbClientSignIn() {
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private NwbClientSignInPanel getLoginPanel() {
		if (signinPanel == null) {
			jLabel = new JLabel("Username");
			jLabel.setBounds(new Rectangle(120, 50, 100, 18));
			jLabel1 = new JLabel("Server IP address");
			jLabel1.setBounds(new Rectangle(120, 100, 150, 18));

			signinPanel = new NwbClientSignInPanel();
			signinPanel.setLayout(null);
			signinPanel.setBackground(Color.white);
			signinPanel.add(jLabel, null);
			signinPanel.add(getUserField(), null);
			signinPanel.add(jLabel1, null);
			signinPanel.add(getIPField(), null);
			signinPanel.add(getConnectButton(), null);
		}
		return signinPanel;
	}

	private JTextField getUserField() {
		if (userField == null) {
			userField = new JTextField();
			userField.setBounds(new Rectangle(120, 70, 200, 28));
		}
		return userField;
	}

	private JTextField getIPField() {
		if (IPField == null) {
			IPField = new JTextField();
			IPField.setBounds(new Rectangle(120, 120, 200, 28));
			IPField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar() == '\n')
						connectButton.doClick();
				}
			});
		}
		return IPField;
	}

	private JButton getConnectButton() {
		if (connectButton == null) {
			connectButton = new JButton("Connect");
			connectButton.setBounds(new Rectangle(175, 166, 90, 25));
			connectButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						userStr = userField.getText();
						if (userStr.equals("")) {
							JOptionPane.showMessageDialog(NwbClientSignIn.this,
									"Error: Username cannot be null",
									"No username", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (IPField.getText().equals("")) {
							JOptionPane
									.showMessageDialog(
											NwbClientSignIn.this,
											"Error: Server IP address cannot cannot be null",
											"No IP address",
											JOptionPane.ERROR_MESSAGE);
							return;
						}
						InetAddress aHost = InetAddress.getByName(IPField
								.getText());

					} catch (IOException e1) {
						JOptionPane.showMessageDialog(NwbClientSignIn.this,
								"Error: Incorrect IP address format.",
								"IP address incorrect",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
						return;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					setVisible(false);
				}
			});
		}
		return connectButton;
	}

	private void initialize() {
		Dimension size = getToolkit().getScreenSize();
		this.width = 458;
		this.height = 396;
		setLocation((size.width - this.width) / 2,
				(size.height - this.height) / 2);
		setSize(this.width, this.height);
		this.setTitle("Sign in");
		this.setResizable(false);
		setContentPane(getLoginPanel());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}