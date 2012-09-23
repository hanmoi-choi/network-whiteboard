package client.drawing.strategy;

import java.awt.*;
import static client.view.ui.comp.NwbCanvas.StrokeNFillMode.FillOnly;
import static client.view.ui.comp.NwbCanvas.StrokeNFillMode.StrokeOnly;
import static client.view.ui.comp.NwbCanvas.StrokeNFillMode.FillNStroke;

public class NwbOvalStrategy extends NwbDrawingStrategy {

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
                g2D.drawOval(x,
                        y,
                        width,
                        height);
            }
            else if(drawingInfo.getFillNStrokeMode() == FillOnly){
                g2D.fillOval(x,
                        y,
                        width,
                        height);
            }
            else if(drawingInfo.getFillNStrokeMode() == FillNStroke){
                g2D.fillOval(x,
                        y,
                        width,
                        height);

                g2D.drawOval(x,
                        y,
                        width,
                        height);
            }
        }

        afterDrawing(g2D);
    }
}
