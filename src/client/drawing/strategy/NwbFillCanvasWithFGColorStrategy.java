package client.drawing.strategy;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 30/09/12
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbFillCanvasWithFGColorStrategy extends NwbDrawingStrategy {
    @Override
    public void drawShape(Graphics2D g2D) {
        beforeDrawing(g2D);

        g2D.fillRect(0, 0, drawingInfo.getCanvasSize().x, drawingInfo.getCanvasSize().y);

        afterDrawing(g2D);
    }
}
