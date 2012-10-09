package client;

import client.controller.NwbControllerFactory;
import client.controller.NwbDrawingCanvasController;
import client.controller.NwbDrawingOptionActionController;
import client.controller.NwbDrawingTypeToolbarController;
import client.controller.NwbMenuActionController;
import client.model.NwbClientModel;
import client.model.factory.NwbClientModelFactory;
import client.view.NwbClientViewFrame;
import client.view.ui.controller.NwbCanvasMouseEventHandler;
import client.view.ui.controller.NwbUIComponentMediator;
import client.view.ui.factory.NwbDrawingOptionFactory;
import client.view.ui.factory.NwbHorToolBarFactory;
import client.view.ui.factory.NwbMenuFactory;
import client.view.ui.factory.NwbRemoteOptionFactory;

import org.jdesktop.application.Application;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 15/09/12
 * Time: 12:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class NwbClientApplication extends Application {
	
	// Test line for make a unique username
	String username=null;
	protected void initialize(String[] args)
	{
		if(args.length != 0)
			username=args[0];
	}
	
    @Override
    protected void startup() {
        //UI Component Mediator
        NwbUIComponentMediator mediator = new NwbUIComponentMediator();

        //Controller
        /*
        NwbDrawingCanvasController drawingCanvasController = new NwbDrawingCanvasController();
        NwbMenuActionController menuActionController = new NwbMenuActionController();
        NwbDrawingTypeToolbarController drawingTypeToolbarController = new NwbDrawingTypeToolbarController();
        NwbDrawingOptionActionController drawingOptionActionController = new NwbDrawingOptionActionController();
		*/
        
        NwbDrawingCanvasController drawingCanvasController =
        		(NwbDrawingCanvasController) NwbControllerFactory.createController(
        				NwbControllerFactory.ControllerType.DrawingConvas,mediator);
        NwbMenuActionController menuActionController = 
        		(NwbMenuActionController) NwbControllerFactory.createController(
        				NwbControllerFactory.ControllerType.MenuAction,mediator);
        NwbDrawingTypeToolbarController drawingTypeToolbarController = 
        		(NwbDrawingTypeToolbarController) NwbControllerFactory.createController(
        				NwbControllerFactory.ControllerType.DrawingTypeToolbar,mediator);
        NwbDrawingOptionActionController drawingOptionActionController = 
        		(NwbDrawingOptionActionController) NwbControllerFactory.createController(
        				NwbControllerFactory.ControllerType.DrawingOptionAction,mediator);

        NwbMenuFactory.setActionMap(menuActionController);
        NwbHorToolBarFactory.setActionMap(drawingTypeToolbarController);
        NwbHorToolBarFactory.setUIMediator(mediator);
        NwbDrawingOptionFactory.setActionMap(drawingOptionActionController);
        NwbDrawingOptionFactory.setUIMediator(mediator);
        NwbRemoteOptionFactory.setUIMediator(mediator);

        //View
        NwbCanvasMouseEventHandler mouseAdapter = new NwbCanvasMouseEventHandler(drawingCanvasController);
        NwbClientViewFrame view = new NwbClientViewFrame(mouseAdapter);
        drawingCanvasController.setCanvasDrawble(mouseAdapter);
        menuActionController.setCanvasDrawble(mouseAdapter);
        drawingTypeToolbarController.setCanvasDrawble(mouseAdapter);
        drawingOptionActionController.setCanvasDrawble(mouseAdapter);

        //Model
        NwbClientModel model = NwbClientModelFactory.createLocalModel();
        NwbControllerFactory.setModel(model);
        /*
        drawingCanvasController.setModel(model);
        menuActionController.setModel(model);
        drawingTypeToolbarController.setModel(model);
        drawingOptionActionController.setModel(model);
        */

        view.showView();

        // Remote mode test!
        if(this.username != null)
        	NwbClientTest.startTest(username);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        Application.launch(NwbClientApplication.class, args);
    }
}
