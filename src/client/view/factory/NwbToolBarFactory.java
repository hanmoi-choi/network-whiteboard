package client.view.factory;

import client.controller.NwbToolbarActionController;
import client.view.NwbJToggleButton;
import client.view.NwbJToggleButtonMediator;
import org.jdesktop.application.Application;

import javax.swing.*;

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
        btnSketch.setAction(actionMap.get("doSketch"));
        btnSketch.setMediator(mediator);
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

        return toolBar;
    }
}
