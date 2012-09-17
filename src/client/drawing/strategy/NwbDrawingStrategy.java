package client.drawing.strategy;

import client.drawing.NwbDrawingInfo;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class NwbDrawingStrategy {

    protected NwbDrawingInfo drawingInfo;

    public abstract void drawShape(Graphics2D g2D);

    public void setDrawingInfo(NwbDrawingInfo info) {
        this.drawingInfo = info;
    }
}
