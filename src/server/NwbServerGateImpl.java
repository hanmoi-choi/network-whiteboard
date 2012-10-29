package server;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.room.NwbRoomData;
import server.room.NwbRoomDataInternal;
import server.room.NwbServerRoom;
import server.room.NwbServerRoomImpl;

public class NwbServerGateImpl
				extends UnicastRemoteObject 
				implements NwbServerGate
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7901561451479560109L;
	
	private HashMap<NwbRoomDataInternal, NwbServerRoom> roomServers;
	
	private HashMap<String, NwbUserDataSecure> clients;
	private HashMap<NwbUserDataSecure, NwbServerGateObserver> clientObservers;
	private int ASSIGN_SESSION_ID_NEXT = 0;
	private int ASSIGN_ROOM_ID_NEXT = 0;
	
	private final ExecutorService keepAlivePool;
	private static final int KEEP_ALIVE_POOL_SIZE = 1;
	private final ExecutorService pool;
	
	public NwbServerGateImpl() throws RemoteException {
		super();
		
		roomServers = new HashMap<NwbRoomDataInternal, NwbServerRoom>();
		
		clients = new HashMap<String, NwbUserDataSecure>();
		clientObservers = new HashMap<NwbUserDataSecure, NwbServerGateObserver>();

		keepAlivePool = Executors.newFixedThreadPool(KEEP_ALIVE_POOL_SIZE);
		keepAlivePool.execute(new KeepAliveChecker(this));

		pool = Executors.newFixedThreadPool(NwbServerRoomImpl.POOL_SIZE);
	}
	
	private void checkAlive()
	{
		ArrayList<NwbUserDataSecure> cs = 
				new ArrayList<NwbUserDataSecure>(clients.values());
		
		for(NwbUserDataSecure u:cs)
		{
			try {
				NwbServerGateObserver o = clientObservers.get(u);
				if(o.alive() == true)
				{
					continue;
				}
			} catch (ConnectException ce){
				System.err.println("checkAlive: user is not online: " + u);
			} catch (RemoteException e) {
				System.err.println("checkAlive: cannot connect to the user: " + u);
			}
			deleteUser(u);
		}
	}
	public NwbUserDataSecure getUserDataSecure(NwbUserData user)
	{
		NwbUserDataSecure ret = clients.get(user.getUsername());
		
		if(ret != null && ret.getSessionid() == user.getSessionid())
			return ret;
		
		return null;
	}
	
	private int generateSessionId()
	{
		this.ASSIGN_SESSION_ID_NEXT++;
		return this.ASSIGN_SESSION_ID_NEXT;
	}
	
	private int generateRoomId()
	{
		this.ASSIGN_ROOM_ID_NEXT++;
		return this.ASSIGN_ROOM_ID_NEXT;
	}
	
	private int generateUserKey(NwbUserData user)
	{
		int key = user.getSessionid()*88 - 861; 
		return key;
	}

    @Override
	public NwbUserDataSecure signIn(String username, NwbServerGateObserver observer) throws RemoteException 
	{
    	if(clients.containsKey(username))
    	{
			System.err.println("signIn: username is duplicated- "+username);
    		return null;
    	}
    	
    	NwbUserData user = new NwbUserData(username, generateSessionId());
    	NwbUserDataSecure userSecure = new NwbUserDataSecure(user, generateUserKey(user));
    	
    	clients.put(username, userSecure);
		clientObservers.put(userSecure, observer);
		
		return userSecure;
	}
    
    private void deleteUser(NwbUserDataSecure user)
    {
    	System.out.println("deleteUser: "+user);
		clientObservers.remove(user);
		clients.remove(user.getUsername());
		
		for(NwbRoomDataInternal r: roomServers.keySet())
		{
			NwbServerRoom rs = roomServers.get(r);
			try {
				rs.exitRoom(user);
			} catch (RemoteException e) {
				// Room is not existing. delete room.
				deleteRoom(r);
			}
		}   	
    }
    
    @Override
    public void signOut(NwbUserDataSecure user) throws RemoteException {
    	deleteUser(user);
	}

    private final boolean isRoomOnClient = true;
    
    @Override
	public NwbServerRoom createRoom(NwbUserDataSecure user, String roomname, int maxusers)
	{
    	if(isRoomOnClient)
    		return createRoomOnClient(user, roomname, maxusers);
    	
		NwbRoomDataInternal roomData = new NwbRoomDataInternal(generateRoomId(), roomname, user, maxusers);
		NwbServerRoomImpl room = null;
		try {
			room = new NwbServerRoomImpl(roomData, user, this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		room.addClient(user);
		
		roomServers.put(roomData, room);
		
		return room;
	}

	private NwbServerRoom createRoomOnClient(NwbUserDataSecure user, String roomname, int maxusers)
	{
		NwbRoomDataInternal roomData = new NwbRoomDataInternal(generateRoomId(), roomname, user, maxusers);
		NwbServerRoom room = null;
		try {
			NwbServerGateObserver observer = clientObservers.get(user);
			room = observer.createServerRoom(roomData, user, this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		roomServers.put(roomData, room);
		
		return room;
	}

	public void deleteRoom(NwbRoomDataInternal room)
	{
	   	System.out.println("deleteRoom: "+room);
		roomServers.remove(room);
	}

	public List<NwbRoomData> getRoomList(NwbUserDataSecure user) throws RemoteException
	{
		List<NwbRoomData> ret = new ArrayList<NwbRoomData>();
		
		for(NwbServerRoom room:roomServers.values())
		{
			ret.add(room.getRoomData());
		}
		
		return ret;
	}
	private NwbRoomDataInternal getRoom(int roomId)
	{
		for(NwbRoomDataInternal rm:roomServers.keySet())
		{
			if(rm.getRoomid() == roomId)
				return rm;
		}
		return null;
	}
	public boolean joinRoomRequest(NwbUserDataSecure user, int roomId) throws RemoteException
	{
		NwbRoomDataInternal room = getRoom(roomId);
		System.out.println("joinRoomRequest : user="+user+", room=" + room);		
		NwbServerRoom roomserver = roomServers.get(room);
		
		if(roomserver == null)
		{
			System.err.println("joinRoomRequest: no room server!");
			return false;
		}
		if(roomserver.isJoinable() == false)
		{
			System.err.println("joinRoomRequest: user cannot join the room. maybe exceeds num users!");
			return false;
		}
		
		roomserver.requestJoin(user);
		
		return true;
	}

	public void notifyJoinResponse(NwbServerRoom server,
			NwbUserData joinUser, Boolean isAccepted) throws RemoteException
	{
		NwbUserDataSecure joinUserSecure = clients.get(joinUser.getUsername());
		
		if(joinUserSecure.getSessionid() != joinUser.getSessionid()) {
			//the user is not valid anymore
			System.err.println("manageJoinResponse: user is not valid. Maybe exit the program!");
			return;
		}

		NwbServerGateObserver observer = clientObservers.get(joinUserSecure);
		NwbServerRoom argServer = isAccepted ? server : null;

		try {
			pool.execute(new NotifyJoinResponseHandler(observer, server.getRoomData(), isAccepted, argServer));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    class NotifyJoinResponseHandler implements Runnable
    {
    	// Send to the manager to ask for joining 
    	NwbServerGateObserver o;
    	NwbRoomData roomData;
    	boolean isAccepted;
    	NwbServerRoom argServer;
    	NotifyJoinResponseHandler(NwbServerGateObserver o, NwbRoomData roomData,
    			boolean isAccepted, NwbServerRoom argServer) {
    		this.o=o;
    		this.roomData = roomData;
    		this.isAccepted = isAccepted;
    		this.argServer = argServer;    		
		}
		@Override
		public void run() {
			try {
				o.notifyJoin(roomData, isAccepted, argServer);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
	class KeepAliveChecker implements Runnable
	{
		NwbServerGateImpl gate;
		static final int SLEEP_TIME = 1000;
		KeepAliveChecker(NwbServerGateImpl gate)
		{
			this.gate = gate;
		}
		@Override
		public void run() {
			for(;;)
			{
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				gate.checkAlive();
			}
		}
	}
	

}
