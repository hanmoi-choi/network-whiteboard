package server;

import server.room.NwbRoomData;
import server.room.NwbRoomDataInternal;
import server.room.NwbServerRoom;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;



public interface NwbServerGate extends Remote {
	public NwbUserDataSecure signIn(String username, NwbServerGateObserver observer) throws RemoteException;
	public void signOut(NwbUserDataSecure user) throws RemoteException;

	public NwbServerRoom createRoom(NwbUserDataSecure user, String roomname, int maxusers) throws RemoteException;
	public boolean joinRoomRequest(NwbUserDataSecure user, int roomId) throws RemoteException;
	public List<NwbRoomData> getRoomList(NwbUserDataSecure user) throws RemoteException;

	public void notifyJoinResponse(NwbServerRoom server,
			NwbUserData joinUser, Boolean isAccepted) throws RemoteException;
	public void deleteRoom(NwbRoomDataInternal roomdata) throws RemoteException;

}
