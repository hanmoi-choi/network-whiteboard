package server.room;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import server.NwbUserData;

public interface NwbServerRoomObserver
			extends Serializable, Remote
{
	public void refresh() throws RemoteException;
	public void notifyTerminateRoom() throws RemoteException;
	
	public void notifyKicked() throws RemoteException;
	

	// Only for managers
	public void manageJoinRequest(NwbUserData user) throws RemoteException;

}
