package client.model.factory;

import server.NwbUserDataSecure;
import server.room.NwbServerRemoteModel;
import server.room.NwbServerRoom;
import client.model.NwbClientModel;
import client.model.NwbClientModel;
import client.model.NwbRemoteModel;
import client.model.room.NwbClientRoom;

public class NwbClientModelFactory {
	public static NwbClientModel createModelFactory(boolean isRemote)
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
	
	public static NwbClientRoom createRoomFactory(NwbUserDataSecure user, NwbServerRoom server)
	{
		NwbClientRoom roomClient = new NwbClientRoom(user, server);
		
		return roomClient;
 
	}
}
