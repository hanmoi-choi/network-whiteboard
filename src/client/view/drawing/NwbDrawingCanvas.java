package client.view.drawing;

import client.model.NwbDrawingCommand;
import client.view.drawing.strategy.NwbCircleStrategy;
import client.view.drawing.strategy.NwbDrawingStrategy;
import client.view.drawing.strategy.NwbLineStrategy;
import client.view.drawing.strategy.NwbRectangleStrategy;

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
public class NwbDrawingCanvas extends Canvas {

    private NwbDrawingInfo drawingInfo;

    public enum CanvasMode {Halt, Draw}

    public enum ShapeType {Rectangle, Line, Circle}

    private CanvasMode mode;
    private ShapeType shapeType;
    private Map<ShapeType, NwbDrawingStrategy> strategyMap;
    private java.util.List<NwbDrawingCommand> commandList;

    public NwbDrawingCanvas() {
        super();

        mode = CanvasMode.Draw;
        shapeType = ShapeType.Line;

        strategyMap = new HashMap<ShapeType, NwbDrawingStrategy>();
        strategyMap.put(ShapeType.Line, new NwbLineStrategy());
        strategyMap.put(ShapeType.Rectangle, new NwbRectangleStrategy());
        strategyMap.put(ShapeType.Circle, new NwbCircleStrategy());
    }

    public void drawAllShape(java.util.List<NwbDrawingCommand> commandList) {
        this.commandList = commandList;
        repaint();
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public void setMode(CanvasMode mode) {
        this.mode = mode;
    }

    public void setDrawingInfo(NwbDrawingInfo drawingInfo) {
        this.drawingInfo = drawingInfo;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        if(mode == CanvasMode.Draw){
            strategyMap.get(shapeType).setDrawingInfo(drawingInfo);
            strategyMap.get(shapeType).drawShape(g2D);
        }

        if (commandList != null) {
            for (NwbDrawingCommand command : commandList) {
                System.out.println("update");
                command.execute(g2D);
            }
        }
    }
}
