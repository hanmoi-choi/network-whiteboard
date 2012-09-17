package client.model;

import client.drawing.NwbDrawingInfo;
import client.drawing.strategy.NwbDrawingStrategy;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NwbDrawingCommand {
    void execute(Graphics2D g);
    void setDrawingInfo(NwbDrawingInfo drawingInfo);
    void setDrawingStrategy(NwbDrawingStrategy strategy);
}
