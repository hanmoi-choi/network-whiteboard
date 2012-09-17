package client.controller;

import client.model.NwbClientModel;
import client.view.NwbClientView;
import org.jdesktop.application.Action;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbMenuActionController {

    private NwbClientView view;
    private NwbClientModel model;

    public NwbMenuActionController(){
    }

    public void setView(NwbClientView view){
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

    @Action
    public void doRedo(ActionEvent evt){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                model.redo();
            }
        });

    }

    @Action
    public void doUndo(ActionEvent evt){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                model.undo();
            }
        });

    }
}
