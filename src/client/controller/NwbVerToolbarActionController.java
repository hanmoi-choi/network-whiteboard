package client.controller;

import client.model.NwbClientModel;
import client.view.CanvasDrawble;
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
public class NwbVerToolbarActionController {

    private CanvasDrawble drawble;
    private NwbClientModel model;
    private Properties fileActionProperty;

    public NwbVerToolbarActionController(){

    }

    public void setCanvasDrawble(CanvasDrawble drawble){
        this.drawble = drawble;
    }

    public void setModel(NwbClientModel model) {
        this.model = model;
    }

    @Action
    public void doAdjustStroke(ActionEvent evt){
        System.out.println(evt);
    }


    @Action
    public void doChaneBgColor(ActionEvent evt){
        System.out.println(evt);
    }

    @Action
    public void doChaneFgColor(ActionEvent evt){
        System.out.println(evt);
    }

    @Action
    public void doSelectStrokeOnly(ActionEvent evt){
        System.out.println(evt);
    }

    @Action
    public void doSelectFillOnly(ActionEvent evt){
        System.out.println(evt);
    }

    @Action
    public void doSelectStrokeFill(ActionEvent evt){
        System.out.println(evt);
    }
    @Action
    public void doSwitchColor(ActionEvent evt){
        System.out.println(evt);
    }

}
