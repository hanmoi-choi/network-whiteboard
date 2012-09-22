package client.drawing.strategy;

import java.awt.*;
import java.util.Map;

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
            for (Map<String, Point> map : drawingInfo.getSketchPointList()){
                if(map.get("start") != null){

                    int startx = map.get("start").x;
                    int starty = map.get("start").y;
                    int endx = map.get("end").x;
                    int endy = map.get("end").y;

                    g2D.drawLine(startx, starty, endx, endy);
                }
            }
        }

        afterDrawing(g2D);
    }
}
