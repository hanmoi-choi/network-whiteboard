package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import server.NwbServerGateObserver;
import server.room.NwbRoomData;
import server.room.NwbServerRoom;



public interface NwbServerGate extends Remote {
	public NwbUserDataSecure signIn(String username, NwbServerGateObserver observer) throws RemoteException;
	public void signOut(NwbUserDataSecure user) throws RemoteException;
	
	public NwbServerRoom createRoom(NwbUserDataSecure user, String roomname, int maxusers) throws RemoteException;
	public void joinRoomRequest(NwbUserDataSecure user, NwbRoomData room) throws RemoteException;
	public List<NwbRoomData> getRoomList(NwbUserDataSecure user) throws RemoteException;
}
