package client.drawing.strategy;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 6:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbLineStrategy extends NwbDrawingStrategy {

    @Override
    public void drawShape(Graphics2D g2D) {
        beforeDrawing(g2D);

        if(drawingInfo != null
           && drawingInfo.getStartPoint() != null
           && drawingInfo.getEndPoint() != null){
            g2D.drawLine(drawingInfo.getStartPoint().x,
                    drawingInfo.getStartPoint().y,
                    drawingInfo.getEndPoint().x,
                    drawingInfo.getEndPoint().y);
        }

        afterDrawing(g2D);
    }

}
