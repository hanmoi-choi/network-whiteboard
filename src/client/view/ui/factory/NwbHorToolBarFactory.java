package client.view.ui.factory;

import client.controller.NwbHorToolbarActionController;
import client.view.ui.comp.NwbJToggleButton;
import client.view.ui.controller.NwbJToggleButtonMediator;
import client.view.ui.controller.NwbUIMediator;
import org.jdesktop.application.Application;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 16/09/12
 * Time: 2:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class NwbHorToolBarFactory {
    private static ActionMap actionMap;

    public static void setActionMap(NwbHorToolbarActionController controller){
        actionMap = Application.getInstance()
                               .getContext()
                               .getActionMap(NwbHorToolbarActionController.class, controller);
    }

    public static JToolBar getToolBar(){
        NwbUIMediator mediator = new NwbJToggleButtonMediator();
        JToolBar toolBar = new JToolBar();

        NwbJToggleButton btnSketch = new NwbJToggleButton("");
        btnSketch.setAction(actionMap.get("doSketch"));
        btnSketch.setMediator(mediator);
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

        toolBar.setFloatable(false);
        return toolBar;
    }
}
