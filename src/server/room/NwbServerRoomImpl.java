package server.room;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

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
	private static final int POOL_SIZE = 10;
	
	private NwbRoomDataInternal roomdata = null;
	private NwbUserDataSecure manager = null;
	private NwbServerGateImpl gate = null;
	private HashMap<NwbUserDataSecure, NwbServerRoomObserver> clientObservers;
	
	private NwbServerRemoteModelImpl modelServer=null;
	
	public NwbServerRoomImpl(NwbRoomDataInternal roomdata, NwbUserDataSecure manager, NwbServerGateImpl gate) throws RemoteException {
		super();
		
		this.roomdata = roomdata;
		this.manager = manager;
		this.clientObservers = new HashMap<NwbUserDataSecure, NwbServerRoomObserver>();
		
		this.gate = gate;
		
		this.modelServer = new NwbServerRemoteModelImpl(POOL_SIZE);
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
	public int getNumUsers()
	{
		return clientObservers.size();
	}
	public boolean isJoinable()
	{
		boolean ret = true;
		if(getNumUsers() >= roomdata.getMaxusers())
			ret = false;
		
		return ret;
	}
	
    @Override
    public void manageJoinResponse(NwbUserDataSecure manager, NwbUserData joinUser, boolean isAccepted) throws RemoteException
    {
    	if(!this.manager.equals(manager))
    	{
    		// Attack?
    		System.err.println("manageJoinResponse: Manager is invalid. " +manager);
    		return;
    	}
    	
    	gate.manageJoinResponse(this, joinUser, isAccepted );
    }
    
	public void addClient(NwbUserDataSecure user)
	{
		System.out.println("Room: addClient : user="+user);
		
		clientObservers.put(user, null);		
	}
	
	
	private Set<NwbUserDataSecure> users()
	{
		return clientObservers.keySet();
	}
	private ArrayList<NwbUserData> getUserList()
	{
		
		ArrayList<NwbUserData> ret = new ArrayList<NwbUserData>();
		for(NwbUserDataSecure u: users())
			ret.add(u.getUserData());
		return ret;
	}

	private void notifyRefresh()
	{
		for(NwbServerRoomObserver o: clientObservers.values())
		{
			try {
				o.refresh();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void notifyTerminateRoom()
	{
		for(NwbServerRoomObserver o: clientObservers.values())
		{
			try {
				o.notifyTerminateRoom();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
    @Override
    public boolean bindObserver(NwbUserDataSecure user, NwbServerRoomObserver observer) throws RemoteException 
	{
		boolean ret = false;
		if(clientObservers.containsKey(user))
		{
			clientObservers.put(user, observer);
			ret = true;
		}
		System.out.println("Room.bindObserver:user="+user+", ret="+ret);
		
		// Joining process is DONE. Notify to all clients about this.
		notifyRefresh();
		
		return ret;
	}

    @Override
    public void exitRoom(NwbUserDataSecure user) throws RemoteException {
    	clientObservers.remove(user);
    	modelServer.removeClient(user);
    	
    	if(clientObservers.size() <= 0)
    		gate.deleteRoom(this.roomdata);
    	else if(user.equals(this.manager))
    	{
    		//manager has exited from the room. notify to clients and delete room
    		notifyTerminateRoom();
    		gate.deleteRoom(this.roomdata);
    	}
	}

	@Override
	public NwbServerRemoteModel getServerRemoteModel(NwbUserDataSecure user)
			throws RemoteException {
		return this.modelServer;
	}
	
	@Override
	public NwbRoomData getRoomData() throws RemoteException {
		NwbRoomData ret = new NwbRoomData(roomdata, getUserList() );
		return ret;
	}

	@Override
	public void manageKick(NwbUserDataSecure manager, NwbUserData kickUser) throws RemoteException
	{
    	if(!this.manager.equals(manager))
    	{
    		// Attack?
    		System.err.println("manageKick: Manager is invalid. " +manager);
    		return;
    	}
    	
    	NwbUserDataSecure kickUserSecure = gate.getUserDataSecure(kickUser);
    	NwbServerRoomObserver observer = clientObservers.get(kickUser);
    	
    	// force exit the user from the room
    	exitRoom(kickUserSecure);
    	
    	// notify to the user that he is kicked out
    	observer.notifyKicked();
		
	}
}
