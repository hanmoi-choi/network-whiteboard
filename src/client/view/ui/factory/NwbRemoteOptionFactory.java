package client.view.ui.factory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import server.NwbUserData;
import server.NwbUserDataSecure;
import server.room.NwbRoomData;
import server.room.NwbServerRoom;

import client.signin.NwbClientConnect;
import client.view.ui.controller.NwbUIComponentMediator;

public class NwbRemoteOptionFactory {

	private static NwbUIComponentMediator nwbUIComponentMediator;
	private static JPanel displayPanel;
	private static JTable memberListTable;
	private static DefaultTableModel tableModel;
	private static JButton kickButton;

	public static void setUIMediator(NwbUIComponentMediator mediator) {
		nwbUIComponentMediator = mediator;
	}

	public static JPanel getDisplayPanel() {
		if (displayPanel == null) {
			displayPanel = new JPanel();
			displayPanel.setLayout(new BorderLayout());

			initTable();
			JScrollPane scrollPanel = new JScrollPane(memberListTable);
			nwbUIComponentMediator.addMemberScrollPanel(scrollPanel);
			scrollPanel.setBounds(0, 0, 120, 300);
			scrollPanel.setPreferredSize(new Dimension(120, 300));
			displayPanel.add(scrollPanel,BorderLayout.SOUTH);
			displayPanel.add(getKickButton(),BorderLayout.SOUTH);
			scrollPanel.setVisible(false);
		}
		return displayPanel;
	}

	private static void initTable() {

		memberListTable = new JTable();
		memberListTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// memberListTable.setFont(new java.awt.Font("Calibri", 0, 15));
		memberListTable.setShowHorizontalLines(false);
		memberListTable.setShowVerticalLines(false);

		memberListTable.setRowHeight(20);

		String[] columnNames = { "Room Members" };
		tableModel = (DefaultTableModel) memberListTable.getModel();
		tableModel.setColumnIdentifiers(columnNames);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		memberListTable.setDefaultRenderer(Object.class, r);

		JTextField readOnlyField = new JTextField(0);
		readOnlyField.setEditable(false);

		DefaultCellEditor readOnlyEditor = new DefaultCellEditor(readOnlyField);

		for (int i = 0; i < columnNames.length; i++) {
			TableColumn column = memberListTable.getColumnModel().getColumn(i);
			column.setCellEditor(readOnlyEditor);
		}
	}

	public static void updateMemberList(NwbServerRoom roomServer) {

		List<NwbUserData> users = null;

		try {
			NwbRoomData rm = roomServer.getRoomData();
			users = rm.getUserList();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int rowCount = memberListTable.getRowCount();
		System.out.println("columnCount:"+rowCount);
		for (int i = 0; i < rowCount; i++) {
			tableModel.removeRow(0);
		}

		for (NwbUserData u : users) {
			Object[] row = new Object[1];
			row[0] = u.getUsername();
			tableModel.addRow(row);
		}
	}

	public static void addMembertoList(String username) {
		Object[] row = new Object[1];
		row[0] = username;
		tableModel.addRow(row);
	}
	
	private static JButton getKickButton()
	{
		if (kickButton == null) {
			kickButton = new JButton();
			kickButton.setText("Kick out");
			kickButton.setBounds(10, 400, 50, 25);
			kickButton.setVisible(false);
		}
		return kickButton;
	}
	public static void showKickButton(final NwbUserDataSecure manager, final NwbServerRoom server)
	{
		if (kickButton != null) {
			kickButton.setVisible(true);
			kickButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(memberListTable.getSelectedRow()==-1)
					{
						JOptionPane.showMessageDialog(null,
								"Please select a user.",
								"No selected user", JOptionPane.ERROR_MESSAGE);
						return;
					}
					else
					{
						NwbUserData kickUser = null;
						List<NwbUserData> users = null;
						try {
							users = server.getRoomData().getUserList();
						} catch (RemoteException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						for (NwbUserData u : users) {
							if(u.getUsername().equals(memberListTable.getValueAt(memberListTable.getSelectedRow(), 0)))
							{
								kickUser = u;
							}
						}
						try {
							server.getRoomData().getUserList();
							server.manageKick(manager, kickUser);
							int row = memberListTable.getSelectedRow();
							tableModel.removeRow(row);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
		}
	}
}
