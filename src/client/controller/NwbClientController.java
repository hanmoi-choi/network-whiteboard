package client.controller;

import client.model.NwbClientModel;
import client.model.NwbDrawingCommand;
import client.view.NwbClientView;
import org.jdesktop.application.Action;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbClientController {

    private NwbClientView view;
    private NwbClientModel model;
    private Properties fileActionProperty;

    public NwbClientController(){

    }

    public void setView(NwbClientView view){
        this.view = view;
    }

    public void setModel(NwbClientModel model){
        this.model = model;
        model.register(this);
    }

    @Action
    public void doNew(ActionEvent evt){
        System.out.println("Quit");
    }

    @Action
    public void doOpen(ActionEvent evt){
        System.out.println("Quit");
    }

    @Action
    public void doOpenRecent(ActionEvent evt){
        System.out.println("Quit");
    }

    @Action
    public void doSave(ActionEvent evt){
        System.out.println("Quit");
    }

    @Action
    public void doSaveAs(ActionEvent evt){
        System.out.println("Quit");
    }
    @Action
    public void doQuit(ActionEvent evt){
        System.out.println("Quit");
        System.exit(0);
    }

    @Action
    public void doRedo(ActionEvent evt){
        model.redo();
    }

    @Action
    public void doUndo(ActionEvent evt){
        model.undo();
    }

    public void newDrawingCommand(NwbDrawingCommand command ) {
        model.pushDrawingCommand(command);
    }

    public void update(List<NwbDrawingCommand> list){
        view.updateAllShape(list);
    }
}
