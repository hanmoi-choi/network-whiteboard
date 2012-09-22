package client.view;

import client.model.NwbDrawingCommand;
import client.view.ui.comp.NwbCanvas;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 20/09/12
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CanvasDrawble {
    void textDraw(String text, Font font);
    void setShapeType(NwbCanvas.ShapeType type);
    void updateAllShape(java.util.List<NwbDrawingCommand> list);

    void openImage(File imageFile);

    BufferedImage getBufferedImageOfCanvas();

    void newCanvas();

    Color getBgColor();

    Color getFgColor();

    void swithBgNFgColor();

    void setStrokeNFillMode(NwbCanvas.StrokeNFillMode fillNStroke);

    void setStroke(int strokeSize);

    void setBgColor(Color newColor);

    void setFgColor(Color newColor);
}
