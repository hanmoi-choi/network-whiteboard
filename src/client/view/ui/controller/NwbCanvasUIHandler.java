package client.view.ui.controller;

import client.controller.NwbDrawingCanvasController;
import client.drawing.NwbDrawingInfo;
import client.model.NwbDrawingCommand;
import client.model.factory.NwbDrawingCommandFactory;
import client.view.CanvasDrawble;
import client.view.ui.comp.NwbCanvas;
import client.view.ui.comp.NwbTextInputDialog;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import static client.view.ui.comp.NwbCanvas.CanvasMode;
import static client.view.ui.comp.NwbCanvas.ShapeType;

public class NwbCanvasUIHandler extends MouseAdapter implements CanvasDrawble {
    private boolean isMousePressed = false;
    private boolean isTextSelected = false;

    private NwbDrawingInfo drawingInfo;
    private ShapeType shapeType;
    private NwbCanvas canvas;
    private NwbDrawingCanvasController controller;
    private BufferedImage canvasScreenShot;

    public NwbCanvasUIHandler(NwbDrawingCanvasController controller) {
        this.controller = controller;
        drawingInfo = new NwbDrawingInfo();
        this.shapeType = ShapeType.Sketch;
    }

    @Override
    public void textDraw(String text, Font font) {
        if (shapeType == ShapeType.Text) {
            drawingInfo.setText(text);
            drawingInfo.setFont(font);
            isTextSelected = true;
        }
    }

    @Override
    public void setShapeType(ShapeType type) {
        this.shapeType = type;
    }

    public void updateAllShape(List<NwbDrawingCommand> list) {
        canvas.drawAllShape(list);
    }

    @Override
    public void openImage(File imageFile) {
        drawingInfo.setBGImage(imageFile);
        canvas.setDrawingInfo(drawingInfo);
        canvas.setShapeType(ShapeType.OpenImage);
        canvas.setMode(CanvasMode.Draw);
        canvas.repaint();
        NwbDrawingCommand drawingCommand = NwbDrawingCommandFactory
                                            .createDrawingFactory(ShapeType.OpenImage,
                                                    drawingInfo.getClone());
        controller.newDrawingCommand(drawingCommand);
    }

    @Override
    public BufferedImage getBufferedImageOfCanvas() {
        canvasScreenShot = new BufferedImage(
                canvas.getWidth(),
                canvas.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = canvasScreenShot.getGraphics();
        g.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        canvas.paint(g);

        return canvasScreenShot;
    }

    @Override
    public void newCanvas() {
    }

    public void setCanvas(NwbCanvas canvas){
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (shapeType == ShapeType.Text) {
            mousePressedAtTextShape(e);
        } else {
            canvas.setMode(CanvasMode.Draw);
            isMousePressed = true;
            addDrawingInfo(e);
        }

        canvas.setShapeType(shapeType);
        canvas.setDrawingInfo(drawingInfo);
        canvas.repaint();
    }

    private void mousePressedAtTextShape(MouseEvent e) {
        if (!isTextSelected) {
            NwbTextInputDialog dialog = new NwbTextInputDialog(this);
            dialog.setVisible(true);
        } else {
            isTextSelected = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        isMousePressed = false;
        addDrawingInfo(e);
        canvas.setDrawingInfo(drawingInfo);
        canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isMousePressed = false;
        isTextSelected = false;

        addDrawingInfo(e);

        canvas.setDrawingInfo(drawingInfo);

        haltDrawingAndUpdate();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (shapeType == ShapeType.Text){
            drawingInfo.setStartPoint(e.getPoint());
            canvas.repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (shapeType == ShapeType.Text){
            drawingInfo.setStartPoint(null);
            canvas.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (shapeType == ShapeType.Text && isTextSelected) {
            drawingInfo.setStartPoint(e.getPoint());
            canvas.setMode(CanvasMode.Draw);
            canvas.setDrawingInfo(drawingInfo);
            canvas.repaint();
        }
    }

    private void addDrawingInfo(MouseEvent e) {
        if (shapeType == ShapeType.Erase || shapeType == ShapeType.Sketch) {
            addDrawingInfoForEraseAndSketch(e);
        } else {
            addDrawingInfoForOthers(e);
        }
    }

    private void addDrawingInfoForEraseAndSketch(MouseEvent e) {
        drawingInfo.addPointToPointList(e.getPoint());
    }

    private void addDrawingInfoForOthers(MouseEvent e) {
        if (isMousePressed) {
            drawingInfo.setStartPoint(e.getPoint());
        } else {
            drawingInfo.setEndPoint(e.getPoint());
        }
    }

    private void haltDrawingAndUpdate() {
        NwbDrawingCommand drawingCommand = NwbDrawingCommandFactory.createDrawingFactory(shapeType, drawingInfo.getClone());

        controller.newDrawingCommand(drawingCommand);
        canvas.repaint();
        drawingInfo.clearInfo();

        canvas.setMode(CanvasMode.Halt);
    }
}
