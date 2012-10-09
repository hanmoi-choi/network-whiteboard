package client.signin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import server.NwbServerGate;
import server.NwbServerGateObserver;
import server.NwbUserDataSecure;
import server.room.NwbRoomData;
import server.room.NwbServerRoom;

import client.controller.NwbControllerFactory;
import client.model.NwbClientModel;
import client.model.factory.NwbClientModelFactory;
import client.model.room.NwbClientRoom;
import client.signin.setting.NwbClientCanvasSize;
import client.view.ui.factory.NwbRemoteOptionFactory;

public class NwbClientConnect extends JFrame {

	private final JTabbedPane tabbedPane;
	private final JLabel tabJoin = new JLabel();
	private final JLabel tabCreate = new JLabel();
	private final JTable table;
	private JTextField nameField = null;
	private JTextField maxField = null;
	private JButton createButton = null;
	private JButton joinButton = null;
	private JComboBox canvasSize = new JComboBox(new NwbClientCanvasSize());

	
	private NwbServerGate server;
	private NwbUserDataSecure user;
	
	public NwbClientConnect(NwbServerGate server, NwbUserDataSecure user) {
		super();
		
		this.server = server;
		this.user = user;
		
		getContentPane().setFocusCycleRoot(true);
		setTitle("Network");
		setBounds(100, 100, 500, 375);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFont(new java.awt.Font("Calibri", 0, 15));
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		float[] hsbColor = Color.RGBtoHSB(217, 217, 217, null);
		table.setBackground(Color.getHSBColor(hsbColor[0], hsbColor[1],
				hsbColor[2]));

		table.setRowHeight(20);
		initTable();

		setupTabJoin();
		setupTabCreate();
	}

	private void setupTabJoin() {

		JLabel title = new JLabel("Join an exited white board");
		title.setBounds(new Rectangle(86, 16, 300, 18));
		title.setFont(new java.awt.Font("Calibri", Font.ITALIC, 22));
		JScrollPane scrollPanel = new JScrollPane(table);
		scrollPanel.setBounds(50, 50, 310, 200);

		tabJoin.add(title);
		tabJoin.add(scrollPanel);
		tabJoin.add(getJoinButton());

		tabbedPane.addTab("Join", tabJoin);
	}

	private void setupTabCreate() {

		JLabel title = new JLabel("Create a new white board");
		title.setBounds(new Rectangle(86, 40, 300, 18));
		title.setFont(new java.awt.Font("Calibri", Font.ITALIC, 22));
		JLabel name = new JLabel("Room Name");
		name.setBounds(new Rectangle(86, 100, 80, 18));
		JLabel roomSize = new JLabel("Room Size");
		roomSize.setBounds(new Rectangle(86, 140, 80, 18));
		JLabel size = new JLabel("Canvas Size");
		size.setBounds(new Rectangle(86, 180, 80, 18));
		canvasSize.setBounds(168, 177, 120, 25);
		canvasSize.setSelectedIndex(1);

		tabCreate.add(title);
		tabCreate.add(name);
		tabCreate.add(roomSize);
		tabCreate.add(getCreateButton());
		tabCreate.add(getNameField());
		tabCreate.add(getMaxField());
		tabCreate.add(size);
		tabCreate.add(canvasSize);

		tabbedPane.addTab("Create", tabCreate);
	}

	private JTextField getNameField() {
		if (nameField == null) {
			nameField = new JTextField();
			nameField.setBounds(new Rectangle(168, 97, 120, 25));
		}
		return nameField;
	}
	private JTextField getMaxField() {
		if (maxField == null) {
			maxField = new JTextField();
			maxField.setBounds(new Rectangle(168, 137, 120, 25));
		}
		return maxField;
	}
	private JButton getCreateButton() {
		if (createButton == null) {
			createButton = new JButton("Create");
			createButton.setBounds(new Rectangle(250, 260, 100, 25));
			createButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (nameField.getText().equals("")) {
						JOptionPane.showMessageDialog(NwbClientConnect.this,
								"Room name cannot be null.",
								"No room name", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						try {
							if (maxField.getText().equals("")||Integer.parseInt(maxField.getText())<1||Integer.parseInt(maxField.getText())>10) {
								JOptionPane.showMessageDialog(NwbClientConnect.this,
										"Room size shoule be an integer between 1 and 10.",
										"invalid room size", JOptionPane.ERROR_MESSAGE);
								return;
							} 
							
							try {
								NwbServerRoom newRoom = server.createRoom(user, nameField.getText(), Integer.parseInt(maxField.getText()));
								enterRoom(newRoom);
								NwbRemoteOptionFactory.showKickButton(user,newRoom);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(
									NwbClientConnect.this,
									"Room size shoule be an integer between 1 and 10.",
									"invalid room size", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					setVisible(false);
				}
			});
		}
		return createButton;
	}

	private void initTable() {

		String[] columnNames = { "Room ID", "Room Name","Creator", "Peers" };
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setColumnIdentifiers(columnNames);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);

		final JTextField pdField = new JTextField(0);
		pdField.setEditable(false);
		JTextField readOnlyField = new JTextField(0);
		readOnlyField.setEditable(false);

		DefaultCellEditor readOnlyEditor = new DefaultCellEditor(readOnlyField);

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setCellEditor(readOnlyEditor);
		}
		
		List<NwbRoomData> rooms = null;
		try {
			rooms = server.getRoomList(user);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(NwbRoomData rm : rooms)
		{
			Object[] row = new Object[columnNames.length];
			row[0] = rm.getRoomid();
			row[1] = rm.getRoomname();
			row[2] = rm.getManager().getUsername();
			row[3] = rm.getNumusers()+"/"+rm.getMaxusers();
			tableModel.addRow(row);
		}
	}

	private JButton getJoinButton() {
		if (joinButton == null) {
			joinButton = new JButton("Join");
			joinButton.setBounds(new Rectangle(300, 280, 100, 25));
			joinButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (table.getSelectedRow() == -1) {
						JOptionPane.showMessageDialog(NwbClientConnect.this,
								"Please select a white board.",
								"No selected board", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						int boardID = Integer.parseInt(table.getValueAt(
								table.getSelectedRow(), 0).toString());
						try {
							server.joinRoomRequest(user, boardID);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					setVisible(false);
				}
			});
		}
		return joinButton;
	}

	public void enterRoom(NwbServerRoom roomServer) {
		NwbClientRoom room = NwbClientModelFactory.createRoomFactory(user, roomServer);
		NwbClientModel newModel = NwbClientModelFactory.createRemoteModel(user, room.getServerRemoteModel());
		NwbControllerFactory.setModel(newModel);
		//roomServer.getRoomData().getManager();
		// Create a list of users and show it on the window here.
		
	}
	

	public void drawPopup() {
		// TODO Auto-generated method stub
		System.out.println("Join is not allowed!");
//		JOptionPane.showMessageDialog(NwbClientConnect.this,
//				"Join is not allowed!",
//				"Request rejected", JOptionPane.ERROR_MESSAGE);
	}
}
