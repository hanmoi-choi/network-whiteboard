package client;

import client.controller.NwbDrawingCanvasController;
import client.controller.NwbMenuActionController;
import client.controller.NwbToolbarActionController;
import client.model.NwbClientModel;
import client.view.NwbClientViewFrame;
import client.view.ui.controller.NwbCanvasUIHandler;
import client.view.ui.factory.NwbMenuFactory;
import client.view.ui.factory.NwbToolBarFactory;
import org.jdesktop.application.Application;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 12:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class NwbClientApplication extends Application {
    @Override
    protected void startup() {
        //Controller
        NwbDrawingCanvasController drawingCanvasController = new NwbDrawingCanvasController();
        NwbMenuActionController menuActionController = new NwbMenuActionController();
        NwbToolbarActionController toolbarActionController = new NwbToolbarActionController();
        NwbMenuFactory.setActionMap(menuActionController);
        NwbToolBarFactory.setActionMap(toolbarActionController);

        //View
        NwbCanvasUIHandler mouseAdapter = new NwbCanvasUIHandler(drawingCanvasController);
        NwbClientViewFrame view = new NwbClientViewFrame(mouseAdapter);
        drawingCanvasController.setCanvasDrawble(mouseAdapter);
        menuActionController.setCanvasDrawble(mouseAdapter);
        toolbarActionController.setCanvasDrawble(mouseAdapter);

        //Model
        NwbClientModel model = new NwbClientModel();
        drawingCanvasController.setModel(model);
        menuActionController.setModel(model);
        toolbarActionController.setModel(model);

        view.showView();

    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        Application.launch(NwbClientApplication.class, args);
    }
}
