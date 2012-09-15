package client.view.drawing.strategy;

import client.view.drawing.DrawingInfo;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 6:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class LineStrategy implements DrawingStrategy {
    private DrawingInfo drawingInfo;

    @Override
    public void drawShape(Graphics2D g2D) {
        if(drawingInfo != null){
            g2D.drawLine(drawingInfo.getStartPoint().x,
                    drawingInfo.getStartPoint().y,
                    drawingInfo.getEndPoint().x,
                    drawingInfo.getEndPoint().y);
        }

    }

    @Override
    public void setDrawingInfo(DrawingInfo info) {
        this.drawingInfo = info;
    }
}
