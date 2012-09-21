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
    private Container contentPane;
    private JSplitPane verticlaSplitPane;
    private JSplitPane upperRightSplitPane;
    private JSplitPane upperLeftSplitPane;


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
        contentPane = frame.getContentPane();

        verticlaSplitPane = new JSplitPane();
        upperRightSplitPane = new JSplitPane();
        upperLeftSplitPane = new JSplitPane();

        verticlaSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

        contentPane.add(verticlaSplitPane, BorderLayout.CENTER);

        JPanel messageInputPanel = new JPanel();
        JPanel drawingOptionPanel = new JPanel();
        JPanel chattingDisplayPanel = new JPanel();

        verticlaSplitPane.setResizeWeight(1.0);
        verticlaSplitPane.setLeftComponent(upperLeftSplitPane);
        verticlaSplitPane.setRightComponent(messageInputPanel);

        upperLeftSplitPane.setResizeWeight(0.01);
        upperLeftSplitPane.setLeftComponent(drawingOptionPanel);
        upperLeftSplitPane.setRightComponent(upperRightSplitPane);

        upperRightSplitPane.setResizeWeight(0.99);
        upperRightSplitPane.setRightComponent(chattingDisplayPanel);

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
        canvas.setPreferredSize(new Dimension(640, 480));
        canvas.setBackground(Color.WHITE);
        canvas.addMouseListener(uiHandler);
        canvas.addMouseMotionListener(uiHandler);
        uiHandler.setCanvas(canvas);
        upperRightSplitPane.setLeftComponent(canvas);
    }

    public JFrame getFrame() {
        return this.frame;
    }
}
