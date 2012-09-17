package client.controller;

import client.model.NwbClientModel;
import client.model.NwbDrawingCommand;
import client.view.NwbClientView;

import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbDrawingCanvasController {

    private NwbClientView view;
    private NwbClientModel model;
    private Properties fileActionProperty;

    public NwbDrawingCanvasController(){

    }

    public void setView(NwbClientView view){
        this.view = view;
    }

    public void setModel(NwbClientModel model){
        this.model = model;
        model.register(this);
    }

    public void newDrawingCommand(NwbDrawingCommand command ) {
        model.pushDrawingCommand(command);
    }

    public void update(List<NwbDrawingCommand> list){
        view.updateAllShape(list);
    }
}
