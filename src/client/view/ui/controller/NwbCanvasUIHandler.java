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
import java.util.List;

import static client.view.ui.comp.NwbCanvas.CanvasMode;
import static client.view.ui.comp.NwbCanvas.ShapeType;

public class NwbCanvasUIHandler extends MouseAdapter implements CanvasDrawble {
    private boolean isPressed = false;
    private boolean isTextSelected = false;

    private NwbDrawingInfo drawingInfo;
    private ShapeType shapeType;
    private NwbCanvas canvas;
    private NwbDrawingCanvasController controller;

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
        }
    }

    @Override
    public void setShapeType(ShapeType type) {
        this.shapeType = type;
    }

    public void updateAllShape(List<NwbDrawingCommand> list) {
        canvas.drawAllShape(list);
    }

    public void setCanvas(NwbCanvas canvas){
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (shapeType == ShapeType.Text) {
            if (!isTextSelected) {
                drawingInfo.setStartPoint(e.getPoint());
                NwbTextInputDialog dialog = new NwbTextInputDialog(this);
                dialog.setVisible(true);
                isTextSelected = true;
            } else {
                isTextSelected = false;
            }
        } else {
            canvas.setMode(CanvasMode.Draw);
            isPressed = true;
            addDrawingInfo(e);
        }

        canvas.setShapeType(shapeType);
        canvas.setDrawingInfo(drawingInfo);
        canvas.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        isPressed = false;
        addDrawingInfo(e);
        canvas.setDrawingInfo(drawingInfo);
        canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isPressed = false;
        isTextSelected = false;

        addDrawingInfo(e);

        canvas.setDrawingInfo(drawingInfo);

        haltDrawingAndUpdate();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);

        if (shapeType == ShapeType.Text) {
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

    private void addDrawingInfoForText(MouseEvent e) {
        drawingInfo.setStartPoint(e.getPoint());
    }

    private void addDrawingInfoForOthers(MouseEvent e) {
        if (isPressed) {
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
