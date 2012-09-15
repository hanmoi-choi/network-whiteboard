package client.model.factory;

import client.model.NwbDrawingCommand;
import client.model.NwbLineDrawingCommand;
import client.view.drawing.NwbDrawingCanvas;
import client.view.drawing.NwbDrawingInfo;
import client.view.drawing.strategy.LineStrategy;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbDrawingCommandFactory {

    public static NwbDrawingCommand createDrawingFactory(NwbDrawingCanvas.ShapeType type, NwbDrawingInfo info){
        NwbDrawingCommand command = null;

        if(type == NwbDrawingCanvas.ShapeType.Line){
            command = new NwbLineDrawingCommand();
            command.setDrawingInfo(info);
            command.setDrawingStrategy(new LineStrategy());
        }
        return command;
    }
}
