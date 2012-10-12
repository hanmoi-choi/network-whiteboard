package client.model.factory;

import server.NwbUserDataSecure;
import server.room.NwbServerRemoteModel;
import server.room.NwbServerRoom;
import client.model.NwbClientModel;
import client.model.NwbClientModel;
import client.model.NwbRemoteModel;
import client.model.room.NwbClientRoom;

public class NwbClientModelFactory {
	
	private static NwbClientModel createModelFactory(boolean isRemote)
	{
		NwbClientModel model = null;
		if(isRemote)
		{
			model = new NwbRemoteModel();
		}
		else
		{
			model = new NwbClientModel();
		}
		return model;
	}
	public static NwbClientModel createLocalModel()
	{
		return createModelFactory(false);
	}
	
	public static NwbClientModel createRemoteModel(NwbUserDataSecure user, NwbServerRemoteModel modelServer)
	{
		NwbClientModel model=null;
		
		// Make model
		model = NwbClientModelFactory.createModelFactory(true);
		((NwbRemoteModel)model).startRemoteMode(user, modelServer);
		
		return model;
	}
}
