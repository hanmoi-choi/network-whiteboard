package client.view;

import client.controller.NwbClientController;
import client.model.NwbDrawingCommand;
import client.model.factory.NwbDrawingCommandFactory;
import client.view.drawing.NwbDrawingCanvas;
import client.view.drawing.NwbDrawingInfo;
import client.view.factory.NwbMenuFactory;
import org.jdesktop.application.ApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class NwbClientView {

    private JFrame frame;
    private JMenuItem jmiNew;
    private NwbJMenu jmFile;
    private NwbJMenu jmEdit;
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private NwbDrawingCanvas drawingCanvas;
    private ApplicationContext ctx;

    private NwbDrawingInfo drawingInfo;
    private NwbDrawingCanvas.ShapeType shapeType;
    private NwbClientController controller;

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

    public void setController(NwbClientController controller){
        this.controller = controller;
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

        shapeType = NwbDrawingCanvas.ShapeType.Line;
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
        drawingCanvas = new NwbDrawingCanvas();
        drawingInfo = new NwbDrawingInfo();
        drawingCanvas.setBackground(Color.WHITE);
        drawingCanvas.addMouseListener(new NwbCanvasMouseAdapter());
        drawingCanvas.addMouseMotionListener(new NwbCanvasMouseAdapter());
    }

    public void updateAllShape(List<NwbDrawingCommand> list) {
        drawingCanvas.drawAllShape(list);
    }

    private class NwbCanvasMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            drawingInfo.setStartPoint(e.getPoint());
            drawingCanvas.setDrawingInfo(drawingInfo);
            drawingCanvas.setMode(NwbDrawingCanvas.CanvasMode.Draw);
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

            controller.newDrawingCommand(NwbDrawingCommandFactory.createDrawingFactory(shapeType, drawingInfo.getClone()));
            drawingCanvas.repaint();
            drawingInfo.clearInfo();
            
            drawingCanvas.setMode(NwbDrawingCanvas.CanvasMode.Halt);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
        }
    }


}
