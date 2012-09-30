package client.view.ui.comp;

import client.drawing.NwbDrawingInfo;
import client.model.NwbDrawingCommand;
import client.drawing.strategy.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class NwbCanvas extends Canvas {

    private Color bgColor = Color.WHITE;
    private Color fgColor = Color.BLACK;



    public enum CanvasMode {Halt, Draw}
    public enum ShapeType {Rectangle, Line, Text, Erase, Oval,
                            RoundedRectangle, Sketch, OpenImage,
                            FillWithFGColor, FillWithBGColor}
    public enum StrokeNFillMode {FillOnly, StrokeOnly, FillNStroke}

    private NwbDrawingInfo drawingInfo;
    private CanvasMode mode;
    private ShapeType shapeType;
    private Map<ShapeType, NwbDrawingStrategy> strategyMap;
    private java.util.List<NwbDrawingCommand> commandList;

    public NwbCanvas() {
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
        strategyMap.put(ShapeType.OpenImage, new NwbOpenImageStrategy());
        strategyMap.put(ShapeType.FillWithFGColor, new NwbFillCanvasWithFGColorStrategy());
        strategyMap.put(ShapeType.FillWithBGColor, new NwbFillCanvasWithBGColorStrategy());

    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public void setFgColor(Color fgColor) {
        this.fgColor = fgColor;
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
        Graphics2D g2D = (Graphics2D) g;

        g2D.setBackground(this.bgColor);
        g2D.setColor(this.fgColor);

        if (commandList != null) {
            for (NwbDrawingCommand command : commandList) {
                command.execute(g2D);
            }
        }

        if (mode == CanvasMode.Draw) {
            strategyMap.get(shapeType).setDrawingInfo(drawingInfo);
            strategyMap.get(shapeType).drawShape(g2D);
        }
    }
}
