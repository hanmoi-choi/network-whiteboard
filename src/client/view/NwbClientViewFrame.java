package client.view;

import client.view.ui.comp.NwbCanvas;
import client.view.ui.controller.NwbCanvasMouseEventHandler;
import client.view.ui.factory.NwbDrawingOptionFactory;
import client.view.ui.factory.NwbHorToolBarFactory;
import client.view.ui.factory.NwbMenuFactory;
import org.jdesktop.application.ApplicationContext;

import javax.swing.*;
import java.awt.*;


public class NwbClientViewFrame {
    private static final int CANVAS_WIDTH = 640;
    private static final int CANVAS_HEIGHT = 480;
    private JFrame frame;
    private JMenuItem jmiNew;
    private JMenu jmFile;
    private JMenu jmEdit;
    private JMenuBar menuBar;
    private JToolBar horToolBar;
    private NwbCanvas canvas;
    private ApplicationContext ctx;
    private NwbCanvasMouseEventHandler mouseEventHandler;
    private Container contentPane;
    private JSplitPane verticlaSplitPane;
    private JSplitPane upperSplitPane;
    private JPanel drawingOptionPanel;

    public NwbClientViewFrame(NwbCanvasMouseEventHandler mouseEventHandler) {
        this.mouseEventHandler = mouseEventHandler;
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
        frame.pack();
    }
    /*
       ------------------------------------
       drawing |drawing        |chatting
       Option  |Canvas         |DisplayPanel
       Panel   |               |
               |               |
               |               |
       ------------------------------------
            messageInputPanel
       ------------------------------------
     */
    private void initJFrame() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);
        contentPane = frame.getContentPane();

        verticlaSplitPane = new JSplitPane();
        upperSplitPane = new JSplitPane();

        verticlaSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

        contentPane.add(verticlaSplitPane, BorderLayout.CENTER);

        JPanel messageInputPanel = new JPanel();
        JPanel chattingDisplayPanel = new JPanel();

        verticlaSplitPane.setResizeWeight(1.0);
        verticlaSplitPane.setLeftComponent(upperSplitPane);
        verticlaSplitPane.setRightComponent(messageInputPanel);

        upperSplitPane.setResizeWeight(0.01);

        upperSplitPane.setRightComponent(chattingDisplayPanel);
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
        horToolBar = NwbHorToolBarFactory.getToolBar();
        drawingOptionPanel = NwbDrawingOptionFactory.getToolBar();
        frame.getContentPane().add(horToolBar, BorderLayout.NORTH);
        frame.getContentPane().add(drawingOptionPanel, BorderLayout.WEST);
    }

    private void initDrawingCanvas() {

        canvas = new NwbCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvas.setBounds(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(Color.WHITE);
        canvas.setSize(CANVAS_WIDTH,CANVAS_HEIGHT);
        canvas.addMouseListener(mouseEventHandler);
        canvas.addMouseMotionListener(mouseEventHandler);
        upperSplitPane.setLeftComponent(canvas);
        mouseEventHandler.setCanvas(canvas);
    }

    public JFrame getFrame() {
        return this.frame;
    }
}
