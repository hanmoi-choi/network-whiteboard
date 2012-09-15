package client.model;

import client.view.drawing.NwbDrawingInfo;
import client.view.drawing.strategy.NwbDrawingStrategy;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbLineDrawingCommand implements NwbDrawingCommand{
    private NwbDrawingInfo drawingInfo;
    private NwbDrawingStrategy strategy;

    @Override
    public void execute(Graphics2D g) {
        this.strategy.setDrawingInfo(this.drawingInfo);
        this.strategy.drawShape(g);
    }

    @Override
    public void setDrawingInfo(NwbDrawingInfo drawingInfo) {
        this.drawingInfo = drawingInfo;
    }

    @Override
    public void setDrawingStrategy(NwbDrawingStrategy strategy) {
        this.strategy = strategy;
    }
}
