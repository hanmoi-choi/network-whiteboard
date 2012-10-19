package server.room;

import java.rmi.Remote;
import java.rmi.RemoteException;

import server.NwbUserData;
import server.NwbUserDataSecure;

public interface NwbServerRoom extends Remote {
	public boolean bindObserver(NwbUserDataSecure user, NwbServerRoomObserver observer) throws RemoteException;
	public void exitRoom(NwbUserDataSecure user) throws RemoteException;
	
	public NwbRoomData getRoomData() throws RemoteException;
	public NwbServerRemoteModel getServerRemoteModel(NwbUserDataSecure user) throws RemoteException;

	public void manageJoinResponse(NwbUserDataSecure manager, NwbUserData joinUser, boolean isAccepted) throws RemoteException;
	public void manageKick(NwbUserDataSecure manager, NwbUserData kickUser) throws RemoteException;
	public boolean isJoinable() throws RemoteException;
	public void requestJoin(NwbUserDataSecure user) throws RemoteException;
}
