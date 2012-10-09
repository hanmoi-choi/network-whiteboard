package client.signin;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

import server.NwbServerGateObserver;
import server.room.NwbRoomData;
import server.room.NwbServerRoom;

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
		if(isAccepted)
		{
			//make a room and open new window
			connectDialog.enterRoom(roomServer);
		}
		else
		{
			// popup to say denied the joining
			connectDialog.drawPopup();
		}
		
	}

	@Override
	public boolean alive() throws RemoteException {
		return true;
	}

	public void setClientConnect(NwbClientConnect connectDialog) {
		this.connectDialog = connectDialog;
		
	}
	
}