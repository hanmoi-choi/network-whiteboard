package client;

import client.controller.NwbDrawingCanvasController;
import client.controller.NwbMenuActionController;
import client.controller.NwbToolbarActionController;
import client.model.NwbClientModel;
import client.view.NwbClientView;
import client.view.factory.NwbMenuFactory;
import client.view.factory.NwbToolBarFactory;
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
        NwbClientView view = new NwbClientView();
        view.setDrawingCanvasController(drawingCanvasController);
        drawingCanvasController.setView(view);
        menuActionController.setView(view);
        toolbarActionController.setView(view);

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
