package client.drawing.strategy;

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
        if(drawingInfo != null && drawingInfo.getText() != null){
            int x = drawingInfo.getStartPoint().x+ 2;
            int y = drawingInfo.getStartPoint().y+ 7;

            g2D.drawString(drawingInfo.getText(), x, y);
        }
    }
}
