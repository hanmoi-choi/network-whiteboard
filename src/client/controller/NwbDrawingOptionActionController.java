package client.controller;

import client.model.NwbClientModel;
import client.view.CanvasDrawble;
import client.view.ui.comp.NwbColorControllButton;
import client.view.ui.comp.NwbJToggleButton;
import org.jdesktop.application.Action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Properties;

import static client.view.ui.comp.NwbCanvas.StrokeNFillMode.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbDrawingOptionActionController implements NwbController {

    private CanvasDrawble drawble;
    private NwbClientModel model;
    private Properties fileActionProperty;

    public NwbDrawingOptionActionController(){

    }

    public void setCanvasDrawble(CanvasDrawble drawble){
        this.drawble = drawble;
    }

    public void setModel(NwbClientModel model) {
        this.model = model;
    }

    @Action
    public void doAdjustStroke(ActionEvent evt){
        JSlider slider = (JSlider) evt.getSource();
        int strokeSize =  slider.getValue();
        drawble.setStroke(strokeSize);
    }

    @Action
    public void doChaneBgColor(ActionEvent evt){
        Color newColor = JColorChooser.showDialog(null, "Choose BG Color", drawble.getBgColor());
        changeUIColor(evt, newColor);
        drawble.setBgColor(newColor);
    }

    @Action
    public void doChaneFgColor(ActionEvent evt){

        Color newColor = JColorChooser.showDialog(null, "Choose FG Color", drawble.getFgColor());
        changeUIColor(evt, newColor);
        drawble.setFgColor(newColor);

    }
    @Action
    public void doSwitchColor(ActionEvent evt){
        drawble.swithBgNFgColor();
        changeUIColor(evt, null);
    }

    private void changeUIColor(ActionEvent evt, Color newColor) {
        NwbColorControllButton button = (NwbColorControllButton)evt.getSource();
        button.notifyToMediatorWithNewColor(newColor);
    }

    @Action
    public void doSelectStrokeOnly(ActionEvent evt){
        drawble.setStrokeNFillMode(StrokeOnly);
        notifyToToggleMediator(evt);
    }

    @Action
    public void doSelectFillOnly(ActionEvent evt){
        drawble.setStrokeNFillMode(FillOnly);
        notifyToToggleMediator(evt);
    }

    @Action
    public void doSelectStrokeFill(ActionEvent evt){
        drawble.setStrokeNFillMode(FillNStroke);
        notifyToToggleMediator(evt);
    }

    private void notifyToToggleMediator(ActionEvent evt) {
        NwbJToggleButton button = (NwbJToggleButton)evt.getSource();
        button.notifyToMediator();
    }
}
