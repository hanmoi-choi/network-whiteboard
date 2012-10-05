package server.room;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import client.model.NwbDrawingCommand;

import server.NwbUserData;

public interface NwbServerRemoteModelObserver
			extends Serializable, Remote
{
	public void addCommand(int commandId, NwbUserData user, NwbDrawingCommand command) throws RemoteException;
	public void removeCommand(int commandId) throws RemoteException;
}
