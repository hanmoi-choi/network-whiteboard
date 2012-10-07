package client.model.room;

import java.rmi.RemoteException;
import server.NwbUserData;
import server.NwbUserDataSecure;
import server.room.NwbRoomData;
import server.room.NwbServerRemoteModel;
import server.room.NwbServerRoom;
import server.room.NwbServerRoomObserver;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 9:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbClientRoom {
	private NwbRoomData room;
	private NwbUserDataSecure user;
	
	private NwbServerRoom server;
	private NwbServerRoomObserver observer;
	
    public NwbClientRoom(NwbUserDataSecure user, NwbServerRoom server) 
    {
        this.server = server;
        this.user = user;
        
		// bind observer to the ROOM
        try {
			this.observer = new NwbClientRoomObserverImpl(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			this.server.bindObserver(user, this.observer);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // update room
        try {
			updateRoomdata();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    private void updateRoomdata() throws RemoteException
    {
        this.room = server.getRoomData();
    }
    
	public void refresh() {
		try {
			updateRoomdata();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void manageJoinRequest(NwbUserData joinUser) {
		//TODO notify to user
		// Pop-up and ask accepting for the user.
		System.out.println("manageJoinRequested: Do you want to accept join?" + joinUser);
		
		// Only for test
		manageJoinResponse(joinUser, true);
	}
	
	private void manageJoinResponse(NwbUserData joinUser, boolean isAccepted)
	{
		try {
			this.server.manageJoinResponse(this.user, joinUser, isAccepted);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public NwbServerRemoteModel getServerRemoteModel()
	{
		try {
			return this.server.getServerRemoteModel(this.user);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    
}