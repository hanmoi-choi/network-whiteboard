package client.view.factory;

import client.controller.NwbClientController;
import org.jdesktop.application.Application;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 1:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbActionFactory {

    protected static ActionMap actionMap;

    public static void setActionMap(NwbClientController controller){
        actionMap = Application.getInstance().getContext().getActionMap(NwbClientController.class, controller);
    }
}
