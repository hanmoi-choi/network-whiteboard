import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import view.factory.MenuFactory;


public class NWBClientView {

	private JFrame frame;
	private JMenuItem jmiNew;
	private JMenu jmFile;
	private JMenu jmEdit;
	private JMenuBar menuBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NWBClientView window = new NWBClientView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NWBClientView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initJFrame();
		initMenubar();

		JPanel chattingPanel = new JPanel();
		frame.getContentPane().add(chattingPanel, BorderLayout.SOUTH);

		JPanel chattingDisplayPanel = new JPanel();
		frame.getContentPane().add(chattingDisplayPanel, BorderLayout.EAST);

		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);

		JPanel drawingPanel = new JPanel();
		frame.getContentPane().add(drawingPanel, BorderLayout.CENTER);

		Canvas drawingCanvas = new Canvas();
		drawingPanel.add(drawingCanvas);







	}

	private void initMenubar() {
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		jmFile = MenuFactory.createFileMenu();
		jmEdit = MenuFactory.createEditMenu();

		menuBar.add(jmFile);
		menuBar.add(jmEdit);
	}

	private void initJFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
