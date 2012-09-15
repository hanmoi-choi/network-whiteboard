package client.view.drawing;

import client.model.NwbDrawingCommand;
import client.view.drawing.strategy.CircleStrategy;
import client.view.drawing.strategy.DrawingStrategy;
import client.view.drawing.strategy.LineStrategy;
import client.view.drawing.strategy.RectangleStrategy;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class DrawingCanvas extends Canvas {

    private DrawingInfo drawingInfo;

    public enum CanvasMode {Halt, Draw, Update}

    public enum ShapeType {Rectangle, Line, Circle}

    private CanvasMode mode;
    private ShapeType shapeType;
    private Map<ShapeType, DrawingStrategy> strategyMap;
    private java.util.List<NwbDrawingCommand> commandList;

    public DrawingCanvas() {
        super();

        mode = CanvasMode.Draw;
        shapeType = ShapeType.Line;

        strategyMap = new HashMap<ShapeType, DrawingStrategy>();
        strategyMap.put(ShapeType.Line, new LineStrategy());
        strategyMap.put(ShapeType.Rectangle, new RectangleStrategy());
        strategyMap.put(ShapeType.Circle, new CircleStrategy());
    }

    public void update(java.util.List<NwbDrawingCommand> commandList) {
        commandList = commandList;
        repaint();
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public void setMode(CanvasMode mode) {
        this.mode = mode;
    }

    public void setDrawingInfo(DrawingInfo drawingInfo) {
        this.drawingInfo = drawingInfo;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        strategyMap.get(shapeType).setDrawingInfo(drawingInfo);
        strategyMap.get(shapeType).drawShape(g2D);

        if (commandList != null) {
            for (NwbDrawingCommand command : commandList) {
                command.execute(g2D);
            }
        }
    }
}
