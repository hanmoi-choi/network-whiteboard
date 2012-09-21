package client.model.factory;

import client.model.NwbDrawingCommand;
import client.model.NwbDrawingCommandImpl;
import client.drawing.NwbDrawingInfo;
import client.drawing.strategy.*;

import static client.view.ui.comp.NwbCanvas.ShapeType;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbDrawingCommandFactory {

    public static NwbDrawingCommand createDrawingFactory(ShapeType type, NwbDrawingInfo info){
        NwbDrawingCommand command = null;
        command = new NwbDrawingCommandImpl();
        command.setDrawingInfo(info);

        if(type == ShapeType.Line){
            command.setDrawingStrategy(new NwbLineStrategy());
        }
        else if(type == ShapeType.Oval){

            command.setDrawingStrategy(new NwbOvalStrategy());
        }
        else if(type == ShapeType.Rectangle){

            command.setDrawingStrategy(new NwbRectangleStrategy());
        }
        else if(type == ShapeType.RoundedRectangle){

            command.setDrawingStrategy(new NwbRoundedRectangleStrategy());
        }
        else if(type == ShapeType.Sketch){

            command.setDrawingStrategy(new NwbSketchStrategy());
        }
        else if(type == ShapeType.Erase){

            command.setDrawingStrategy(new NwbEraseStrategy());
        }
        else if(type == ShapeType.Text){

            command.setDrawingStrategy(new NwbTextStrategy());
        }
        else if(type == ShapeType.OpenImage){

            command.setDrawingStrategy(new NwbOpenImageStrategy());
        }
        return command;
    }
}
