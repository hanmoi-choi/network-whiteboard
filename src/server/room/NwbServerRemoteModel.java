package server.room;

import java.rmi.Remote;
import java.rmi.RemoteException;

import server.NwbUserDataSecure;

import client.model.NwbDrawingCommand;

public interface NwbServerRemoteModel extends Remote {
	public void bindObserver(NwbUserDataSecure user, NwbServerRemoteModelObserver client) throws RemoteException;
	public void removeClient(NwbUserDataSecure user) throws RemoteException;
	
    public int addCommand(NwbUserDataSecure requestedUser, NwbDrawingCommand command) throws RemoteException;
    public void removeCommand(NwbUserDataSecure requestedUser, int commandId) throws RemoteException;
}
