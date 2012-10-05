package client.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import server.NwbUserData;
import server.room.NwbServerRemoteModelObserver;

public class NwbRemoteModelObserverImpl
			extends UnicastRemoteObject
			implements NwbServerRemoteModelObserver
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7038512380968311976L;
	private NwbRemoteModel client;

	NwbRemoteModelObserverImpl(NwbRemoteModel client) throws RemoteException
	{
		super();
		this.client = client;
	}
	
	public void addCommand(int commandId, NwbUserData user, NwbDrawingCommand command) throws RemoteException
	{
		client.addCommandFromServer(commandId, user, command);
	}
	
	public void removeCommand(int commandId) throws RemoteException
	{
		client.removeCommandFromServer(commandId);
	}	
}
