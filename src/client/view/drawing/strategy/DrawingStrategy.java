package client.view.drawing.strategy;

import client.view.drawing.DrawingInfo;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DrawingStrategy {
    void drawShape(Graphics2D g2D);
    void setDrawingInfo(DrawingInfo info);
}
