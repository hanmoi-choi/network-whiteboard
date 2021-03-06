package client.model.room;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import server.NwbUserData;
import server.room.NwbServerRoomObserver;

public class NwbClientRoomObserverImpl
			extends UnicastRemoteObject
			implements NwbServerRoomObserver
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7038512380968311976L;
	private NwbClientRoom client;

	public NwbClientRoomObserverImpl(NwbClientRoom client) throws RemoteException
	{
		super();
		this.client = client;
	}
	
	public void refresh() throws RemoteException
	{
		this.client.refresh();
	}
	
	@Override
	public void manageJoinRequest(NwbUserData joinUser)
	{
		this.client.manageJoinRequest(joinUser);
	}

	@Override
	public void notifyKicked() throws RemoteException {
		this.client.notifyKicked();
	}

	@Override
	public void notifyTerminateRoom() throws RemoteException {
		this.client.notifyTerminate();
	}
}
