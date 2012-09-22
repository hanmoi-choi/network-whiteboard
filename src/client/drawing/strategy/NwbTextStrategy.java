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
        beforeDrawing(g2D);

        if(drawingInfo != null
           && drawingInfo.getText() != null
           && drawingInfo.getStartPoint() != null){
            int x = drawingInfo.getStartPoint().x + 3;
            int y = drawingInfo.getStartPoint().y - 3;
            g2D.setFont(drawingInfo.getFont());
            g2D.drawString(drawingInfo.getText(), x, y);
        }

        afterDrawing(g2D);
    }
}
