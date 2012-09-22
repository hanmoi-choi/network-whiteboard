package client.view;

import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class a {

	private JFrame frmCanvasSize;
	private JTextField textField;
	private JTextField textField_1;
	private JComboBox comboBox;
	private JLabel lblPreset;

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
		frmCanvasSize.getContentPane().setLayout(null);

		comboBox = new JComboBox();
		comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"640 x 480", "800 x 600", "1024 x 768", "1280 x 1024", "Custom"}));
		comboBox.setBounds(115, 18, 136, 27);
		frmCanvasSize.getContentPane().add(comboBox);

		JLabel lblX = new JLabel("Width:");
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setBounds(44, 57, 61, 28);
		frmCanvasSize.getContentPane().add(lblX);

		textField = new JTextField();
		textField.setBounds(115, 57, 84, 28);
		frmCanvasSize.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeight.setBounds(44, 97, 61, 28);
		frmCanvasSize.getContentPane().add(lblHeight);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(115, 97, 84, 28);
		frmCanvasSize.getContentPane().add(textField_1);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(54, 143, 84, 29);
		frmCanvasSize.getContentPane().add(btnCancel);

		JButton btnOk = new JButton("OK");
		btnOk.setBounds(175, 143, 90, 29);
		frmCanvasSize.getContentPane().add(btnOk);

		lblPreset = new JLabel("Preset:");
		lblPreset.setHorizontalAlignment(SwingConstants.CENTER);
		lblPreset.setBounds(19, 22, 61, 16);
		frmCanvasSize.getContentPane().add(lblPreset);

		JLabel lblPixels = new JLabel("Pixels");
		lblPixels.setBounds(224, 63, 61, 16);
		frmCanvasSize.getContentPane().add(lblPixels);

		JLabel label = new JLabel("Pixels");
		label.setBounds(224, 103, 61, 16);
		frmCanvasSize.getContentPane().add(label);
	}
}
