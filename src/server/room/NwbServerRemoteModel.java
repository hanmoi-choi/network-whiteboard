package server.room;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import server.NwbUserDataSecure;

import client.model.NwbDrawingCommand;

public interface NwbServerRemoteModel extends Remote {
	public void bindObserver(NwbUserDataSecure user, NwbServerRemoteModelObserver client) throws RemoteException;
	public void removeClient(NwbUserDataSecure user) throws RemoteException;
	
    public int addCommand(NwbUserDataSecure requestedUser, NwbDrawingCommand command) throws RemoteException;
    public void removeCommand(NwbUserDataSecure requestedUser, int commandId) throws RemoteException;
    public void removeCommandAll(NwbUserDataSecure requestedUser) throws RemoteException;

    public List<NwbDrawingCommandData> getAllCommands(NwbUserDataSecure requestedUser) throws RemoteException;
}
