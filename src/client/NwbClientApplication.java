package client;

import client.controller.NwbDrawingCanvasController;
import client.controller.NwbDrawingTypeToolbarController;
import client.controller.NwbMenuActionController;
import client.controller.NwbVerToolbarActionController;
import client.model.NwbClientModel;
import client.view.NwbClientViewFrame;
import client.view.ui.controller.NwbCanvasUIHandler;
import client.view.ui.controller.NwbUIComponentMediator;
import client.view.ui.factory.NwbHorToolBarFactory;
import client.view.ui.factory.NwbMenuFactory;
import client.view.ui.factory.NwbVerToolBarFactory;
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
	String user="test";
	protected void initialize(String[] args)
	{
		if(args.length != 0)
			user=args[0];
	}
	
    @Override
    protected void startup() {
        //UI Component Mediator
        NwbUIComponentMediator mediator = new NwbUIComponentMediator();

        //Controller
        NwbDrawingCanvasController drawingCanvasController = new NwbDrawingCanvasController();
        NwbMenuActionController menuActionController = new NwbMenuActionController();
        NwbDrawingTypeToolbarController drawingTypeToolbarController = new NwbDrawingTypeToolbarController();
        NwbVerToolbarActionController verToolbarActionController = new NwbVerToolbarActionController();

        NwbMenuFactory.setActionMap(menuActionController);
        NwbHorToolBarFactory.setActionMap(drawingTypeToolbarController);
        NwbHorToolBarFactory.setUIMediator(mediator);
        NwbVerToolBarFactory.setActionMap(verToolbarActionController);
        NwbVerToolBarFactory.setUIMediator(mediator);

        //View
        NwbCanvasUIHandler mouseAdapter = new NwbCanvasUIHandler(drawingCanvasController);
        NwbClientViewFrame view = new NwbClientViewFrame(mouseAdapter);
        drawingCanvasController.setCanvasDrawble(mouseAdapter);
        menuActionController.setCanvasDrawble(mouseAdapter);
        drawingTypeToolbarController.setCanvasDrawble(mouseAdapter);
        verToolbarActionController.setCanvasDrawble(mouseAdapter);

        //Model
        NwbClientModel model = new NwbClientModel();
		
		/* RemoteModel test.. Don't need it later		
        NwbClientModel model = new NwbRemoteModel(user, 
        		NwbRemoteModelConnector.connectModel(
        				"localhost:30010"
        				//"holly.csse.unimelb.edu.au:30010"
        				));
		*/
        drawingCanvasController.setModel(model);
        menuActionController.setModel(model);
        drawingTypeToolbarController.setModel(model);
        verToolbarActionController.setModel(model);

        view.showView();

    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        Application.launch(NwbClientApplication.class, args);
    }
}
