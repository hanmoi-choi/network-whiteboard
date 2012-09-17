package client.drawing.strategy;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbEraseStrategy extends NwbDrawingStrategy {

    private static final int ERASER_SIZE = 8;

    @Override
    public void drawShape(Graphics2D g2D) {
        switchColor(g2D);
        if (drawingInfo != null) {
            for (Point point : drawingInfo.getPointList()) {

                int x = (int) point.getX() - (ERASER_SIZE / 2);
                int y = (int) point.getY() - (ERASER_SIZE / 2);
                g2D.fillRect(x,
                        y,
                        ERASER_SIZE,
                        ERASER_SIZE);
            }
        }
        switchColor(g2D);
    }

    private void switchColor(Graphics2D g2D) {
            Color bgColor = g2D.getBackground();
            Color fgColor = g2D.getColor();
            g2D.setBackground(fgColor);
            g2D.setColor(bgColor);
    }
}
