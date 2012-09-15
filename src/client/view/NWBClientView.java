package client.view;

import client.view.drawing.DrawingCanvas;
import client.view.drawing.DrawingInfo;
import client.view.factory.NwbMenuFactory;
import org.jdesktop.application.ApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class NwbClientView {

	private JFrame frame;
	private JMenuItem jmiNew;
	private NwbJMenu jmFile;
	private NwbJMenu jmEdit;
	private JMenuBar menuBar;
	private JToolBar toolBar;
    private DrawingCanvas drawingCanvas;
    private ApplicationContext ctx;

    private DrawingInfo drawingInfo;

    /**
	 * Create the application.
	 */
	public NwbClientView() {
		initialize();
	}

    public void setApplicationContext(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public void showView() {
        frame.setVisible(true);
    }

	private void initialize() {
		initJFrame();
		initMenubar();
		initToolbar();
        initDrawingCanvas();

		JPanel chattingPanel = new JPanel();
		frame.getContentPane().add(chattingPanel, BorderLayout.SOUTH);

		JPanel chattingDisplayPanel = new JPanel();
		frame.getContentPane().add(chattingDisplayPanel, BorderLayout.EAST);
        frame.getContentPane().add(drawingCanvas, BorderLayout.CENTER);
	}

    private void initJFrame() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initMenubar() {
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        jmFile = NwbMenuFactory.createFileMenu();
        jmEdit = NwbMenuFactory.createEditMenu();

        menuBar.add(jmFile);
        menuBar.add(jmEdit);
    }

    private void initToolbar() {
        toolBar = new JToolBar();
        frame.getContentPane().add(toolBar, BorderLayout.NORTH);
    }

    private void initDrawingCanvas() {
        drawingCanvas = new DrawingCanvas();
        drawingInfo = new DrawingInfo();
        drawingCanvas.setBackground(Color.WHITE);
        drawingCanvas.addMouseListener(new NwbCanvasMouseAdapter());
        drawingCanvas.addMouseMotionListener(new NwbCanvasMouseAdapter());
    }

    private class NwbCanvasMouseAdapter extends MouseAdapter{


        @Override
        public void mousePressed(MouseEvent e) {
            drawingInfo.setStartPoint(e.getPoint());
            drawingCanvas.setDrawingInfo(drawingInfo);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            drawingInfo.setEndPoint(e.getPoint());
            drawingCanvas.setDrawingInfo(drawingInfo);
            drawingCanvas.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            drawingInfo.setEndPoint(e.getPoint());
            drawingCanvas.setDrawingInfo(drawingInfo);
            drawingCanvas.repaint();
            drawingInfo.clearInfo();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
        }
    }


}
