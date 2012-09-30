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
import static client.view.ui.comp.NwbCanvas.StrokeNFillMode;
import static client.view.ui.comp.NwbCanvas.StrokeNFillMode.*;

public class NwbCanvasUIHandler extends MouseAdapter implements CanvasDrawble {
    private boolean isMousePressed = false;
    private boolean isTextSelected = false;

    private NwbDrawingInfo drawingInfo;
    private ShapeType shapeType = ShapeType.Sketch;
    private NwbCanvas canvas;
    private NwbDrawingCanvasController controller;
    private BufferedImage canvasScreenShot;

    private int strokeSize = 1;
    private Color bgColor = Color.WHITE;
    private Color fgColor = Color.BLACK;
    private StrokeNFillMode fillNStrokeMode = StrokeOnly;

    public NwbCanvasUIHandler(NwbDrawingCanvasController controller) {
        this.controller = controller;

        drawingInfo = new NwbDrawingInfo();
        drawingInfo.setBgColor(bgColor);
        drawingInfo.setFgColor(fgColor);
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
        drawingInfo.clearInfo();

        canvas.setDrawingInfo(drawingInfo);
    }

    @Override
    public Color getBgColor() {
        return this.bgColor;
    }

    @Override
    public Color getFgColor() {
        return this.fgColor;
    }

    @Override
    public void swithBgNFgColor() {
        Color tmp = null;
        tmp = fgColor;
        fgColor = bgColor;
        bgColor = tmp;
    }

    @Override
    public void setStrokeNFillMode(StrokeNFillMode fillNStroke) {
        this.fillNStrokeMode = fillNStroke;
    }

    @Override
    public void setStroke(int strokeSize) {
        this.strokeSize = strokeSize;
    }

    @Override
    public void setBgColor(Color newColor) {
        this.bgColor = newColor;
        canvas.setBgColor(newColor);
    }

    @Override
    public void setFgColor(Color newColor) {
        this.fgColor = newColor;
        canvas.setFgColor(newColor);
    }

    public void setCanvas(NwbCanvas canvas){
        this.canvas = canvas;
        drawingInfo.setCanvasSize(new Point(canvas.getWidth(), canvas.getHeight()));
        canvas.setDrawingInfo(drawingInfo);
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
            drawingInfo.setFgColor(fgColor);
            canvas.setMode(CanvasMode.Draw);
            canvas.setDrawingInfo(drawingInfo);
            canvas.repaint();
        }
    }

    private void addDrawingInfo(MouseEvent e) {
        drawingInfo.setStrokeSize(strokeSize);
        drawingInfo.setBgColor(bgColor);
        drawingInfo.setFgColor(fgColor);
        drawingInfo.setStrokeNFillMode(fillNStrokeMode);
        drawingInfo.setCanvasSize(new Point(canvas.getWidth(), canvas.getHeight()));

        if (shapeType == ShapeType.Erase){
            addDrawingInfoForErase(e);
        }
        else if(shapeType == ShapeType.Sketch) {
            addDrawingInfoForSketch(e);
        }
        else {
            addDrawingInfoForOthers(e);
        }
    }

    private void addDrawingInfoForSketch(MouseEvent e) {
        Point startPoint = drawingInfo.getEndPoint();
        Point endPoint = e.getPoint();

        drawingInfo.addSketchPoints(startPoint, endPoint);
        drawingInfo.setStartPoint(startPoint);
        drawingInfo.setEndPoint(endPoint);
    }

    private void addDrawingInfoForErase(MouseEvent e) {
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

    private void mousePressedAtTextShape(MouseEvent e) {
        if (!isTextSelected) {
            NwbTextInputDialog dialog = new NwbTextInputDialog(this);
            dialog.setVisible(true);
        } else {
            isTextSelected = false;
        }
    }
}
