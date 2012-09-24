package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.model.NwbRemoteModelObserver;
import client.model.NwbDrawingCommand;

public interface NwbRemoteModelServer extends Remote {
	public void connect(String user, NwbRemoteModelObserver client) throws RemoteException;
	public void disconnect(String user) throws RemoteException;
	
    public int addCommand(String requestedUser, NwbDrawingCommand command) throws RemoteException;
    public void removeCommand(String requestedUser, int commandId) throws RemoteException;
}
