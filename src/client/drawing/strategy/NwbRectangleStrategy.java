package client.drawing.strategy;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbRectangleStrategy extends NwbDrawingStrategy {

    @Override
    public void drawShape(Graphics2D g2D) {
        if(drawingInfo != null){
            int x = (drawingInfo.getStartPoint().x > drawingInfo.getEndPoint().x)
                    ?drawingInfo.getEndPoint().x
                    :drawingInfo.getStartPoint().x;
            int y = (drawingInfo.getStartPoint().y > drawingInfo.getEndPoint().y)
                    ?drawingInfo.getEndPoint().y
                    :drawingInfo.getStartPoint().y;
            int width = Math.abs((drawingInfo.getStartPoint().x - drawingInfo.getEndPoint().x));
            int height = Math.abs((drawingInfo.getStartPoint().y - drawingInfo.getEndPoint().y));
            g2D.drawRect(x,
                        y,
                        width,
                        height);

        }
    }
}
