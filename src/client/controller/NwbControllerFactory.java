package client.controller;

import java.util.ArrayList;

import client.model.NwbClientModel;
import client.view.ui.controller.NwbUIComponentMediator;

public class NwbControllerFactory {
	
	private static ArrayList<NwbController> controllers = new ArrayList<NwbController>();
	
	public enum ControllerType
	{
		DrawingConvas,
		DrawingOptionAction,
		DrawingTypeToolbar,
		MenuAction
	}

	public static NwbController createController(ControllerType type,NwbUIComponentMediator mediator)
	{
		NwbController controller = null;
		switch(type)
		{
		case DrawingConvas:
			controller = new NwbDrawingCanvasController();
			break;
		case DrawingOptionAction:
			controller = new NwbDrawingOptionActionController();
			break;
		case DrawingTypeToolbar:
			controller = new NwbDrawingTypeToolbarController();
			break;
		case MenuAction:
			controller = new NwbMenuActionController(mediator);
			break;
		default:
			break;
		}
		controllers.add(controller);
		return controller;
	}
	
	public static void setModel(NwbClientModel model)
	{
		for(NwbController con:controllers)
			con.setModel(model);
	}
}

