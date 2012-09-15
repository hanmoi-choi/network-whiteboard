package client.controller;

import client.model.NwbClientModel;
import client.view.NwbPaintable;
import org.jdesktop.application.Action;

import java.awt.event.ActionEvent;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbClientController {

    private NwbPaintable view;
    private NwbClientModel model;
    private Properties fileActionProperty;

    public NwbClientController(){
    }

    public void setView(NwbPaintable view){
        this.view = view;
    }

    public void setModel(NwbClientModel model){
        this.model = model;
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

}
