package client.view.drawing.strategy;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbTextStrategy extends NwbDrawingStrategy {

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
            g2D.drawOval(x,
                         y,
                         width,
                         height);

        }
    }
}
