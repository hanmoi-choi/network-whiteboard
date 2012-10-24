package server;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import server.room.NwbRoomData;
import server.room.NwbRoomDataInternal;
import server.room.NwbServerRoom;

public interface NwbServerGateObserver
			extends Serializable, Remote
{
	void notifyJoin(NwbRoomData room, boolean isAccepted, NwbServerRoom roomServer) throws RemoteException;
	
	boolean alive() throws RemoteException;

	NwbServerRoom createServerRoom(NwbRoomDataInternal roomData, NwbUserDataSecure user,
			NwbServerGate nwbServerGateImpl) throws RemoteException;
	
	
}
