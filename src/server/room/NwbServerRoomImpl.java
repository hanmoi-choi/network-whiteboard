package server.room;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import server.NwbServerGateImpl;
import server.NwbUserData;
import server.NwbUserDataSecure;

public class NwbServerRoomImpl
				extends UnicastRemoteObject 
				implements NwbServerRoom
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7375101647398791502L;
	private NwbRoomData roomdata = null;
	private NwbUserDataSecure manager = null;
	private NwbServerGateImpl gate = null;
	private HashMap<NwbUserDataSecure, NwbServerRoomObserver> clientObservers;
	
	private NwbServerRemoteModelImpl modelServer=null;
	
	public NwbServerRoomImpl(NwbRoomData roomdata, NwbUserDataSecure manager, NwbServerGateImpl gate) throws RemoteException {
		super();
		
		this.roomdata = roomdata;
		this.manager = manager;
		this.clientObservers = new HashMap<NwbUserDataSecure, NwbServerRoomObserver>();
		
		this.gate = gate;
		
		this.modelServer = new NwbServerRemoteModelImpl();
	}
	
	public void requestJoin(NwbUserDataSecure user)
	{
		NwbUserData requestedUser = new NwbUserData(user.getUsername(), user.getSessionid());
		
		NwbServerRoomObserver managerObserver = this.clientObservers.get(manager);
		try {
			managerObserver.manageJoinRequest(requestedUser);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
    @Override
    public void manageJoinResponse(NwbUserDataSecure manager, NwbUserData joinUser, boolean isAccepted) throws RemoteException
    {
    	if(!this.manager.equals(manager))
    	{
    		// Attack?
    		return;
    	}
    	
    	gate.manageJoinResponse(this, joinUser, isAccepted );
    }
    
	public void addClient(NwbUserDataSecure user)
	{
		System.out.println("Room: addClient : user="+user);
		
		clientObservers.put(user, null);		
	}

    @Override
    public boolean bindObserver(NwbUserDataSecure user, NwbServerRoomObserver observer) throws RemoteException 
	{
		System.out.println("Room: bindObserver : user="+user);
		
		System.out.println("Room: bindObserver : users = "+ clientObservers);
		
		boolean ret = false;
		if(clientObservers.containsKey(user))
		{
			clientObservers.put(user, observer);
			ret = true;
		}
		System.out.println("Room: bindObserver : ret="+ret);
		return ret;
			
	}

    @Override
    public void exitRoom(NwbUserDataSecure user) throws RemoteException {
    	clientObservers.remove(user);
    	
    	if(clientObservers.size() < 0 || user.equals(this.manager))
    		gate.deleteRoom(this.roomdata);
	}

	@Override
	public NwbServerRemoteModel getServerRemoteModel(NwbUserDataSecure user)
			throws RemoteException {
		return this.modelServer;
	}
	
	@Override
	public NwbRoomData getRoomData() throws RemoteException {
		return this.roomdata;
	}
}
