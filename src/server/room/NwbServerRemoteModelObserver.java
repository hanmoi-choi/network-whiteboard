package server.room;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NwbServerRemoteModelObserver
			extends Serializable, Remote
{
	public void addCommand(NwbDrawingCommandData command) throws RemoteException;
	public void removeCommand(int commandId) throws RemoteException;
	public void removeCommandAll() throws RemoteException;
}
