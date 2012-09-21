package client.view;

import client.view.ui.comp.NwbCanvas;
import client.view.ui.controller.NwbCanvasUIHandler;
import client.view.ui.factory.NwbHorToolBarFactory;
import client.view.ui.factory.NwbMenuFactory;
import client.view.ui.factory.NwbVerToolBarFactory;
import org.jdesktop.application.ApplicationContext;

import javax.swing.*;
import java.awt.*;


public class NwbClientViewFrame {

    private JFrame frame;
    private JMenuItem jmiNew;
    private JMenu jmFile;
    private JMenu jmEdit;
    private JMenuBar menuBar;
    private JToolBar horToolBar;
    private NwbCanvas canvas;
    private ApplicationContext ctx;
    private NwbCanvasUIHandler uiHandler;
    private Container contentPane;
    private JSplitPane verticlaSplitPane;
    private JSplitPane upperRightSplitPane;
    private JSplitPane upperSplitPane;
    private JToolBar verToolBar;


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
        verToolBar = NwbVerToolBarFactory.getToolBar();
        frame.getContentPane().add(horToolBar, BorderLayout.NORTH);
        frame.getContentPane().add(verToolBar, BorderLayout.WEST);
    }

    private void initDrawingCanvas() {

        canvas = new NwbCanvas();
        canvas.setPreferredSize(new Dimension(640, 480));
        canvas.setBounds(0, 0, 640, 480);
        canvas.setBackground(Color.WHITE);
        canvas.setSize(640,480);
        canvas.addMouseListener(uiHandler);
        canvas.addMouseMotionListener(uiHandler);
        upperSplitPane.setLeftComponent(canvas);
        uiHandler.setCanvas(canvas);
    }

    public JFrame getFrame() {
        return this.frame;
    }
}
