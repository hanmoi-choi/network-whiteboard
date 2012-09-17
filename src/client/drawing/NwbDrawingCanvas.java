package client.drawing;

import client.model.NwbDrawingCommand;
import client.drawing.strategy.*;

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

    public enum ShapeType {Rectangle, Line, Text, Erase, Oval, RoundedRectangle, Sketch}

    private CanvasMode mode;
    private ShapeType shapeType;
    private Map<ShapeType, NwbDrawingStrategy> strategyMap;
    private java.util.List<NwbDrawingCommand> commandList;

    public NwbDrawingCanvas() {
        super();

        mode = CanvasMode.Draw;
        shapeType = ShapeType.Sketch;

        strategyMap = new HashMap<ShapeType, NwbDrawingStrategy>();
        strategyMap.put(ShapeType.Line, new NwbLineStrategy());
        strategyMap.put(ShapeType.Rectangle, new NwbRectangleStrategy());
        strategyMap.put(ShapeType.Oval, new NwbOvalStrategy());
        strategyMap.put(ShapeType.Text, new NwbTextStrategy());
        strategyMap.put(ShapeType.RoundedRectangle, new NwbRoundedRectangleStrategy());
        strategyMap.put(ShapeType.Erase, new NwbEraseStrategy());
        strategyMap.put(ShapeType.Sketch, new NwbSketchStrategy());
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

        if (commandList != null) {
            for (NwbDrawingCommand command : commandList) {
                System.out.println("update");
                command.execute(g2D);
            }
        }

        if (mode == CanvasMode.Draw) {
            strategyMap.get(shapeType).setDrawingInfo(drawingInfo);
            strategyMap.get(shapeType).drawShape(g2D);
        }



    }


}
