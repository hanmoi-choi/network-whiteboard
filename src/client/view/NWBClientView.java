package client.view;

import client.controller.NwbDrawingCanvasController;
import client.model.NwbDrawingCommand;
import client.model.factory.NwbDrawingCommandFactory;
import client.drawing.NwbDrawingCanvas;
import client.drawing.NwbDrawingCanvas.ShapeType;
import client.drawing.NwbDrawingInfo;
import client.view.factory.NwbMenuFactory;
import client.view.factory.NwbToolBarFactory;
import org.jdesktop.application.ApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class NwbClientView {

    private JFrame frame;
    private JMenuItem jmiNew;
    private JMenu jmFile;
    private JMenu jmEdit;
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private NwbDrawingCanvas drawingCanvas;
    private ApplicationContext ctx;

    private NwbDrawingInfo drawingInfo;
    private ShapeType shapeType;
    private NwbDrawingCanvasController controller;

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

    public void setDrawingCanvasController(NwbDrawingCanvasController controller) {
        this.controller = controller;
    }

    public void updateAllShape(List<NwbDrawingCommand> list) {
        drawingCanvas.drawAllShape(list);
    }

    public void setShapeType(ShapeType type) {
        this.shapeType = type;
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

        shapeType = ShapeType.Sketch;
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
        drawingCanvas = new NwbDrawingCanvas();
        drawingInfo = new NwbDrawingInfo();
        drawingCanvas.setBackground(Color.WHITE);
        drawingCanvas.addMouseListener(new NwbCanvasMouseAdapter());
        drawingCanvas.addMouseMotionListener(new NwbCanvasMouseAdapter());
    }

    public void ShowTextDialog() {

    }

    private class NwbCanvasMouseAdapter extends MouseAdapter {
        private boolean isPressed = false;

        @Override
        public void mousePressed(MouseEvent e) {
            isPressed = true;
            addDrawingInfo(e);
            drawingCanvas.setShapeType(shapeType);

            drawingCanvas.setDrawingInfo(drawingInfo);
            drawingCanvas.setMode(NwbDrawingCanvas.CanvasMode.Draw);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            isPressed = false;
            addDrawingInfo(e);
            drawingCanvas.setDrawingInfo(drawingInfo);
            drawingCanvas.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            isPressed = false;
            addDrawingInfo(e);

            drawingCanvas.setDrawingInfo(drawingInfo);

            haltDrawingAndUpdate();
        }

        private void haltDrawingAndUpdate() {
            NwbDrawingCommand drawingCommand = NwbDrawingCommandFactory.createDrawingFactory(shapeType, drawingInfo.getClone());

            controller.newDrawingCommand(drawingCommand);
            drawingCanvas.repaint();
            drawingInfo.clearInfo();

            drawingCanvas.setMode(NwbDrawingCanvas.CanvasMode.Halt);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
        }

        private void addDrawingInfo(MouseEvent e) {
            if (shapeType == ShapeType.Erase || shapeType == ShapeType.Sketch) {
                addDrawingInfoForEraseAndSketch(e);
            } else if (shapeType == ShapeType.Text) {
                addDrawingInfoForText(e);
            } else {
                addDrawingInfoForOthers(e);
            }
        }

        private void addDrawingInfoForEraseAndSketch(MouseEvent e) {
            drawingInfo.addPointToPointList(e.getPoint());
        }

        private void addDrawingInfoForText(MouseEvent e)
        {
            if (isPressed) {
                drawingInfo.setText("Test");
                drawingInfo.setStartPoint(e.getPoint());
            } else {
                drawingInfo.setEndPoint(e.getPoint());
            }
        }

        private void addDrawingInfoForOthers(MouseEvent e) {
            if (isPressed) {
                drawingInfo.setStartPoint(e.getPoint());
            } else {
                drawingInfo.setEndPoint(e.getPoint());
            }
        }
    }


}
