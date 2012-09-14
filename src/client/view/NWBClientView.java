package client.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class NwbClientView implements NwbPaintable{

	private JFrame frame;
	private JMenuItem jmiNew;
	private NwbJMenu jmFile;
	private NwbJMenu jmEdit;
	private JMenuBar menuBar;
	private JToolBar toolBar;
    private Canvas drawingCanvas;

    /**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NwbClientView window = new NwbClientView();
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
	public NwbClientView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initJFrame();
		initMenubar();
		initToolbar();

		JPanel chattingPanel = new JPanel();
		frame.getContentPane().add(chattingPanel, BorderLayout.SOUTH);

		JPanel chattingDisplayPanel = new JPanel();
		frame.getContentPane().add(chattingDisplayPanel, BorderLayout.EAST);

        drawingCanvas = new Canvas();
        drawingCanvas.setBackground(Color.WHITE);

        frame.getContentPane().add(drawingCanvas, BorderLayout.CENTER);
	}

	private void initToolbar() {
		toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	private void initMenubar() {
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		jmFile = NwbMenuFactory.createFileMenu();
		jmEdit = NwbMenuFactory.createEditMenu();

		menuBar.add(jmFile);
		menuBar.add(jmEdit);
	}

	private void initJFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

    @Override
    public List<JMenuItem> getFileMenuItems() {
        return jmFile.getMenuItems();
    }

    @Override
    public List<JMenuItem> getEditMenuItems() {
        return jmEdit.getMenuItems();
    }

    @Override
    public Canvas getDrawingCanvs() {
        return drawingCanvas;
    }
}
