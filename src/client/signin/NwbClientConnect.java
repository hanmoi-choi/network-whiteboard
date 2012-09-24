package client.signin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import client.signin.setting.NwbClientCanvasSize;

public class NwbClientConnect extends JFrame {

	private final JTabbedPane tabbedPane;
	private final JLabel tabJoin = new JLabel();
	private final JLabel tabCreate = new JLabel();
	private final JTable table;
	private JTextField portField = null;
	private JButton createButton = null;
	private JButton joinButton = null;
	private JComboBox canvasSize = new JComboBox(new NwbClientCanvasSize());

	public NwbClientConnect() {
		super();
		getContentPane().setFocusCycleRoot(true);
		setTitle("Network");
		setBounds(100, 100, 500, 375);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		// tabbedPane.addChangeListener(new ChangeListener() {
		// public void stateChanged(ChangeEvent e) {
		// int selectedIndex = tabbedPane.getSelectedIndex();
		// String title = tabbedPane.getTitleAt(selectedIndex);
		// System.out.println(title);
		// }
		// });
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
		title.setBounds(new Rectangle(86, 50, 300, 18));
		title.setFont(new java.awt.Font("Calibri", Font.ITALIC, 22));
		JLabel port = new JLabel("Port  Number");
		port.setBounds(new Rectangle(86, 120, 80, 18));
		JLabel size = new JLabel("Canvas Size");
		size.setBounds(new Rectangle(86, 160, 80, 18));
		canvasSize.setBounds(168, 157, 120, 25);
		canvasSize.setSelectedIndex(1);

		tabCreate.add(title);
		tabCreate.add(port);
		tabCreate.add(getCreateButton());
		tabCreate.add(getPortField());
		tabCreate.add(size);
		tabCreate.add(canvasSize);

		tabbedPane.addTab("Create", tabCreate);
	}

	private JTextField getPortField() {
		if (portField == null) {
			portField = new JTextField();
			portField.setBounds(new Rectangle(168, 117, 150, 25));
		}
		return portField;
	}

	private JButton getCreateButton() {
		if (createButton == null) {
			createButton = new JButton("Create");
			createButton.setBounds(new Rectangle(250, 260, 100, 25));
			createButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (portField.getText().equals("")) {
						JOptionPane.showMessageDialog(NwbClientConnect.this,
								"Port number cannot be null.",
								"No port number", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						try {
							int portNum = Integer.parseInt(portField.getText());
							//
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(
									NwbClientConnect.this,
									"Port number is invalid",
									"Invalid port number",
									JOptionPane.ERROR_MESSAGE);
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

		String[] columnNames = { "Room ID", "Creator", "Peers", "Canvas Size" };
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

		Object[] row = new Object[columnNames.length];
		row[0] = 1001;
		row[1] = "Jane";
		row[2] = 3;
		row[3] = "819*460";
		tableModel.addRow(row);

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
								"No selecte board", JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						int boardID = Integer.parseInt(table.getValueAt(
								table.getSelectedRow(), 0).toString());

					}
					setVisible(false);
				}
			});
		}
		return joinButton;
	}
}
