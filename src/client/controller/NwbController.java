package client.controller;

import client.model.NwbClientModel;
import client.view.CanvasDrawble;

public interface NwbController {
	public void setModel(NwbClientModel model);
	public void setCanvasDrawble(CanvasDrawble drawble);
}
