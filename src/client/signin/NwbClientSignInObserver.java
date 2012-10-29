package client.signin;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import server.NwbServerGate;
import server.NwbServerGateObserver;
import server.NwbUserDataSecure;
import server.room.NwbRoomData;
import server.room.NwbRoomDataInternal;
import server.room.NwbServerRoom;
import server.room.NwbServerRoomImpl;

public class NwbClientSignInObserver extends UnicastRemoteObject
implements NwbServerGateObserver
{
	protected NwbClientSignInObserver() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5223138927704289501L;
	private NwbClientConnect connectDialog;
	
	@Override
	public void notifyJoin(NwbRoomData room, boolean isAccepted,
			NwbServerRoom roomServer) throws RemoteException {
		connectDialog.joinResponse(isAccepted, roomServer);
	}

	@Override
	public boolean alive() throws RemoteException {
		return true;
	}

	public void setClientConnect(NwbClientConnect connectDialog) {
		this.connectDialog = connectDialog;
		
	}

	@Override
	public NwbServerRoomImpl createServerRoom(NwbRoomDataInternal roomData,
			NwbUserDataSecure user, NwbServerGate nwbServerGateImpl)
			throws RemoteException {
		NwbServerRoomImpl room = null;
		try {
			room = new NwbServerRoomImpl(roomData, user, nwbServerGateImpl);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		room.addClient(user);
		return room;
	}
	
}