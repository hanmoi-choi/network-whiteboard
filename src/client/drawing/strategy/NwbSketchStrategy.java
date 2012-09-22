package client.drawing.strategy;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbSketchStrategy extends NwbDrawingStrategy {

    private static final int BRUSH_SIZE = 8;

    @Override
    public void drawShape(Graphics2D g2D) {
        beforeDrawing(g2D);

        if (drawingInfo != null) {
            for (Point point : drawingInfo.getPointList()) {
                int x = (int) point.getX() - (BRUSH_SIZE / 2);
                int y = (int) point.getY() - (BRUSH_SIZE / 2);
                g2D.fillOval(x,
                        y,
                        BRUSH_SIZE,
                        BRUSH_SIZE);
            }
        }

        afterDrawing(g2D);
    }
}
