package client.model.factory;

import client.model.NwbDrawingCommand;
import client.model.NwbLineDrawingCommand;
import client.view.drawing.NwbDrawingCanvas;
import client.view.drawing.NwbDrawingInfo;
import client.view.drawing.strategy.NwbCircleStrategy;
import client.view.drawing.strategy.NwbLineStrategy;
import client.view.drawing.strategy.NwbRectangleStrategy;

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
        command = new NwbLineDrawingCommand();
        command.setDrawingInfo(info);

        if(type == NwbDrawingCanvas.ShapeType.Line){
            command.setDrawingStrategy(new NwbLineStrategy());
        }
        else if(type == NwbDrawingCanvas.ShapeType.Circle){

            command.setDrawingStrategy(new NwbCircleStrategy());
        }
        else if(type == NwbDrawingCanvas.ShapeType.Rectangle){

            command.setDrawingStrategy(new NwbRectangleStrategy());
        }
        return command;
    }
}
