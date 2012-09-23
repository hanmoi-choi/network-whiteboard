package client.drawing.strategy;

import java.awt.*;

import static client.view.ui.comp.NwbCanvas.StrokeNFillMode.FillOnly;
import static client.view.ui.comp.NwbCanvas.StrokeNFillMode.StrokeOnly;
import static client.view.ui.comp.NwbCanvas.StrokeNFillMode.FillNStroke;

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
        beforeDrawing(g2D);

        if(drawingInfo != null
           && drawingInfo.getStartPoint() != null
           && drawingInfo.getEndPoint() != null){
            int x = (drawingInfo.getStartPoint().x > drawingInfo.getEndPoint().x)
                    ?drawingInfo.getEndPoint().x
                    :drawingInfo.getStartPoint().x;
            int y = (drawingInfo.getStartPoint().y > drawingInfo.getEndPoint().y)
                    ?drawingInfo.getEndPoint().y
                    :drawingInfo.getStartPoint().y;
            int width = Math.abs((drawingInfo.getStartPoint().x - drawingInfo.getEndPoint().x));
            int height = Math.abs((drawingInfo.getStartPoint().y - drawingInfo.getEndPoint().y));

            if(drawingInfo.getFillNStrokeMode() == StrokeOnly){
                g2D.drawRect(x,
                        y,
                        width,
                        height);
            }
            else if(drawingInfo.getFillNStrokeMode() == FillOnly){
                g2D.fillRect(x,
                        y,
                        width,
                        height);
            }
            else if(drawingInfo.getFillNStrokeMode() == FillNStroke){
                switchColorBtwBgNFg(g2D);

                g2D.fillRect(x,
                        y,
                        width,
                        height);
                switchColorBtwBgNFg(g2D);

                g2D.drawRect(x,
                        y,
                        width,
                        height);
            }

        }

        afterDrawing(g2D);
    }
}
