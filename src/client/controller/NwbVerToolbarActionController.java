package client.controller;

import client.model.NwbClientModel;
import client.view.CanvasDrawble;
import client.view.ui.comp.NwbCanvas;
import org.jdesktop.application.Action;

import javax.swing.*;
import java.awt.*;
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
        JComboBox<Integer> comboBox = (JComboBox<Integer>)evt.getSource();
        int strokeSize = comboBox.getItemAt(comboBox.getSelectedIndex()).intValue();
        drawble.setStroke(strokeSize);
    }


    @Action
    public void doChaneBgColor(ActionEvent evt){
        Color newColor = JColorChooser.showDialog(null, "Choose BG Color", drawble.getBgColor());
        drawble.setBgColor(newColor);
    }

    @Action
    public void doChaneFgColor(ActionEvent evt){
        Color newColor = JColorChooser.showDialog(null, "Choose FG Color", drawble.getFgColor());
        drawble.setFgColor(newColor);
    }

    @Action
    public void doSelectStrokeOnly(ActionEvent evt){
        drawble.setStrokeNFillMode(NwbCanvas.StrokeNFillMode.StrokeOnly);
    }

    @Action
    public void doSelectFillOnly(ActionEvent evt){
        drawble.setStrokeNFillMode(NwbCanvas.StrokeNFillMode.FillOnly);
    }

    @Action
    public void doSelectStrokeFill(ActionEvent evt){
        drawble.setStrokeNFillMode(NwbCanvas.StrokeNFillMode.FillNStroke);
    }
    @Action
    public void doSwitchColor(ActionEvent evt){
        drawble.swithBgNFgColor();
    }

}
