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
        if(drawingInfo != null){
            int x = drawingInfo.getStartPoint().x - (ERASER_SIZE/2);
            int y = drawingInfo.getStartPoint().y - (ERASER_SIZE/2);
            g2D.fillRect(x,
                         y,
                        ERASER_SIZE,
                        ERASER_SIZE);

        }
    }
}
