package client.view.ui.factory;

import client.controller.NwbHorToolbarActionController;
import client.view.ui.comp.NwbJToggleButton;
import client.view.ui.controller.NwbUIComponentMediator;
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
    private static NwbUIComponentMediator nwbUIComponentMediator;

    public static void setActionMap(NwbHorToolbarActionController controller){
        actionMap = Application.getInstance()
                               .getContext()
                               .getActionMap(NwbHorToolbarActionController.class, controller);
    }

    public static void setUIMediator(NwbUIComponentMediator mediator){
        nwbUIComponentMediator = mediator;
    }

    public static JToolBar getToolBar(){

        JToolBar toolBar = new JToolBar();

        NwbJToggleButton btnSketch = createToggleButton("doSketch");
        btnSketch.setSelected(true);    //default
        toolBar.add(btnSketch);

        NwbJToggleButton btnLine = createToggleButton("doLine");
        toolBar.add(btnLine);

        NwbJToggleButton btnRect =  createToggleButton("doRect");
        toolBar.add(btnRect);

        NwbJToggleButton btnRoundedRect = createToggleButton("doRoundedRect");
        toolBar.add(btnRoundedRect);

        NwbJToggleButton btnErase = createToggleButton("doErase");
        toolBar.add(btnErase);

        NwbJToggleButton btnOval =  createToggleButton("doOval");
        toolBar.add(btnOval);

        NwbJToggleButton btnText =  createToggleButton("doText");
        toolBar.add(btnText);

        toolBar.setFloatable(false);
        return toolBar;
    }

    private static NwbJToggleButton createToggleButton(String actionCommand) {
        NwbJToggleButton toggleButton = new NwbJToggleButton();
        toggleButton.setAction(actionMap.get(actionCommand));
        toggleButton.setActionCommand(actionCommand);
        toggleButton.setMediator(nwbUIComponentMediator);

        return toggleButton;
    }
}
