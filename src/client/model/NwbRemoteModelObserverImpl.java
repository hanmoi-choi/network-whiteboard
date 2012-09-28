package client.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NwbRemoteModelObserverImpl
			extends UnicastRemoteObject
			implements NwbRemoteModelObserver
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
	
	public void addCommand(int commandId, String user, NwbDrawingCommand command) throws RemoteException
	{
		client.addCommandFromServer(commandId, user, command);
	}
	
	public void removeCommand(int commandId) throws RemoteException
	{
		client.removeCommandFromServer(commandId);
	}	
}
