package client.drawing.strategy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbOpenImageStrategy extends NwbDrawingStrategy {

    private static final int ERASER_SIZE = 8;
    private BufferedImage bufferedImage = null;
    @Override
    public void drawShape(Graphics2D g2D) {
        beforeDrawing(g2D);

        if(drawingInfo.isImageStale()){
            reloadBufferedImageFile();
        }

        g2D.drawImage(bufferedImage, 0, 0, 640, 480, null);

        afterDrawing(g2D);
    }

    private void reloadBufferedImageFile() {
        try {
            bufferedImage = ImageIO.read(drawingInfo.getBGImage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
