package client.view;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class a {

	private JFrame frmCanvasSize;
	private JTextField textField;
	private JTextField textField_1;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					a window = new a();
					window.frmCanvasSize.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public a() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCanvasSize = new JFrame();
		frmCanvasSize.setFont(null);
		frmCanvasSize.setTitle("Canvas Size");
		frmCanvasSize.setBounds(100, 100, 400, 300);
		frmCanvasSize.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmCanvasSize.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmStart = new JMenuItem("Start");
		mnFile.add(mntmStart);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnFile.add(mntmQuit);
		frmCanvasSize.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("IP:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblNewLabel.setBounds(16, 6, 36, 32);
		frmCanvasSize.getContentPane().add(lblNewLabel);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblPort.setBounds(16, 50, 36, 32);
		frmCanvasSize.getContentPane().add(lblPort);

		textField = new JTextField();
		textField.setBounds(49, 8, 134, 28);
		frmCanvasSize.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(49, 52, 134, 28);
		frmCanvasSize.getContentPane().add(textField_1);

		JRadioButton rdbtnReamMachine = new JRadioButton("Ream Machine");
		buttonGroup.add(rdbtnReamMachine);
		rdbtnReamMachine.setBounds(16, 131, 141, 23);
		frmCanvasSize.getContentPane().add(rdbtnReamMachine);

		JRadioButton rdbtnDummyMachine = new JRadioButton("Dummy Machine");
		buttonGroup.add(rdbtnDummyMachine);
		rdbtnDummyMachine.setBounds(16, 151, 141, 23);
		frmCanvasSize.getContentPane().add(rdbtnDummyMachine);

		JLabel lblEyeseeingMachine = new JLabel("Eye-Seeing Machine");
		lblEyeseeingMachine.setBounds(16, 103, 134, 28);
		frmCanvasSize.getContentPane().add(lblEyeseeingMachine);

		JLabel lblSmoothingStrategy = new JLabel("Smoothing Strategy");
		lblSmoothingStrategy.setBounds(207, 103, 134, 28);
		frmCanvasSize.getContentPane().add(lblSmoothingStrategy);

		JRadioButton rdbtnNone = new JRadioButton("None");
		buttonGroup_1.add(rdbtnNone);
		rdbtnNone.setBounds(207, 131, 141, 23);
		frmCanvasSize.getContentPane().add(rdbtnNone);

		JRadioButton rdbtnAverageValue = new JRadioButton("Average Value");
		buttonGroup_1.add(rdbtnAverageValue);
		rdbtnAverageValue.setBounds(207, 151, 141, 23);
		frmCanvasSize.getContentPane().add(rdbtnAverageValue);
	}
}
