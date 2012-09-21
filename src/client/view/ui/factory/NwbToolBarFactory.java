package client.view.ui.factory;

import client.controller.NwbToolbarActionController;
import client.view.ui.comp.NwbJToggleButton;
import client.view.ui.controller.NwbJToggleButtonMediator;
import org.jdesktop.application.Application;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 16/09/12
 * Time: 2:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class NwbToolBarFactory{
    private static ActionMap actionMap;

    public static void setActionMap(NwbToolbarActionController controller){
        actionMap = Application.getInstance()
                               .getContext()
                               .getActionMap(NwbToolbarActionController.class, controller);
    }

    public static JToolBar getToolBar(){
        NwbJToggleButtonMediator mediator = new NwbJToggleButtonMediator();
        JToolBar toolBar = new JToolBar();

        NwbJToggleButton btnSketch = new NwbJToggleButton("");

        btnSketch.setIcon(new ImageIcon("/Users/hanmoi/git/network-whiteboard/src/client/controller/resources/sketch.png"));
        btnSketch.setMediator(mediator);
        btnSketch.setAction(actionMap.get("doSketch"));
        btnSketch.setSelected(true);    //default
        toolBar.add(btnSketch);

        NwbJToggleButton btnLine = new NwbJToggleButton("");
        btnLine.setAction(actionMap.get("doLine"));
        btnLine.setMediator(mediator);
        toolBar.add(btnLine);

        NwbJToggleButton btnRect = new NwbJToggleButton("");
        btnRect.setAction(actionMap.get("doRect"));
        btnRect.setMediator(mediator);
        toolBar.add(btnRect);

        NwbJToggleButton btnRoundedRect = new NwbJToggleButton("");
        btnRoundedRect.setAction(actionMap.get("doRoundedRect"));
        btnRoundedRect.setMediator(mediator);
        toolBar.add(btnRoundedRect);

        NwbJToggleButton btnErase = new NwbJToggleButton("");
        btnErase.setAction(actionMap.get("doErase"));
        btnErase.setMediator(mediator);
        toolBar.add(btnErase);

        NwbJToggleButton btnOval = new NwbJToggleButton("");
        btnOval.setAction(actionMap.get("doOval"));
        btnOval.setMediator(mediator);
        toolBar.add(btnOval);

        NwbJToggleButton btnText = new NwbJToggleButton("");
        btnText.setAction(actionMap.get("doText"));
        btnText.setMediator(mediator);
        toolBar.add(btnText);

        toolBar.add(new JToolBar.Separator());

        NwbJToggleButton btnFgColor = new NwbJToggleButton("A");
        btnFgColor.setBackground(Color.BLACK);
        btnFgColor.setPreferredSize(new Dimension(30,30));
        toolBar.add(btnFgColor);

        return toolBar;
    }
}