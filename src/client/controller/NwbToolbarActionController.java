package client.controller;

import client.model.NwbClientModel;
import client.view.NwbClientView;
import client.view.NwbJToggleButton;
import client.view.drawing.NwbDrawingCanvas;
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
public class NwbToolbarActionController {

    private NwbClientView view;
    private NwbClientModel model;
    private Properties fileActionProperty;

    public NwbToolbarActionController(){

    }

    public void setView(NwbClientView view){
        this.view = view;
    }

    public void setModel(NwbClientModel model) {
        this.model = model;
    }

    @Action
    public void doSketch(ActionEvent evt){
        notifyToToggleMediator(evt);
        view.setShapeType(NwbDrawingCanvas.ShapeType.Sketch);
    }

    @Action
    public void doLine(ActionEvent evt){
        notifyToToggleMediator(evt);
        view.setShapeType(NwbDrawingCanvas.ShapeType.Line);
    }

    @Action
    public void doRect(ActionEvent evt){
        notifyToToggleMediator(evt);
        view.setShapeType(NwbDrawingCanvas.ShapeType.Rectangle);
    }
    @Action
    public void doRoundedRect(ActionEvent evt){
        notifyToToggleMediator(evt);
        view.setShapeType(NwbDrawingCanvas.ShapeType.RoundedRectangle);
    }
    @Action
    public void doOval(ActionEvent evt){
        notifyToToggleMediator(evt);
        view.setShapeType(NwbDrawingCanvas.ShapeType.Oval);
    }

    @Action
    public void doErase(ActionEvent evt){
        notifyToToggleMediator(evt);
        view.setShapeType(NwbDrawingCanvas.ShapeType.Erase);
    }

    @Action
    public void doText(ActionEvent evt){
        notifyToToggleMediator(evt);
        view.setShapeType(NwbDrawingCanvas.ShapeType.Text);
    }

    private void notifyToToggleMediator(ActionEvent evt) {
        NwbJToggleButton button = (NwbJToggleButton)evt.getSource();
        button.notifyToToggleMediator();
    }
}
