package client.view;

import client.view.ui.comp.NwbCanvas;
import client.view.ui.controller.NwbCanvasUIHandler;
import client.view.ui.factory.NwbMenuFactory;
import client.view.ui.factory.NwbToolBarFactory;
import org.jdesktop.application.ApplicationContext;

import javax.swing.*;
import java.awt.*;


public class NwbClientViewFrame {

    private JFrame frame;
    private JMenuItem jmiNew;
    private JMenu jmFile;
    private JMenu jmEdit;
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private NwbCanvas canvas;
    private ApplicationContext ctx;
    private NwbCanvasUIHandler uiHandler;


    /**
     * Create the application.
     */
    public NwbClientViewFrame(NwbCanvasUIHandler uiHandler) {
        this.uiHandler = uiHandler;
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
        initDrawingCanvas();
        initMenubar();
        initToolbar();

        JPanel chattingPanel = new JPanel();
        frame.getContentPane().add(chattingPanel, BorderLayout.SOUTH);

        JPanel chattingDisplayPanel = new JPanel();
        frame.getContentPane().add(chattingDisplayPanel, BorderLayout.EAST);
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
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
        toolBar = NwbToolBarFactory.getToolBar();
        frame.getContentPane().add(toolBar, BorderLayout.NORTH);
    }

    private void initDrawingCanvas() {
        canvas = new NwbCanvas();
        canvas.setBackground(Color.WHITE);
        canvas.addMouseListener(uiHandler);
        canvas.addMouseMotionListener(uiHandler);
        uiHandler.setCanvas(canvas);
    }

    public JFrame getFrame() {
        return this.frame;
    }
}
