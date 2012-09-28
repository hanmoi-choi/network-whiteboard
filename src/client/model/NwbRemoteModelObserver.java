package client.model;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NwbRemoteModelObserver
			extends Serializable, Remote
{
	public void addCommand(int commandId, String user, NwbDrawingCommand command) throws RemoteException;
	public void removeCommand(int commandId) throws RemoteException;
}
