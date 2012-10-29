package client.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import server.room.NwbDrawingCommandData;
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
	
	public void addCommand(NwbDrawingCommandData com) throws RemoteException
	{
		client.addCommandFromServer(com.getId(), com.getCreatedUser(), com.getCommand());
	}
	
	public void removeCommand(int commandId) throws RemoteException
	{
		client.removeCommandFromServer(commandId);
	}

	@Override
	public void removeCommandAll() throws RemoteException {
		client.removeCommandAllFromServer();
		
	}	
}
