package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import server.room.NwbRoomData;
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
	
	private ArrayList<NwbRoomData> rooms;
	private HashMap<Integer, NwbServerRoomImpl> roomServers;
	
	private HashMap<String, NwbUserDataSecure> clients;
	private HashMap<NwbUserDataSecure, NwbServerGateObserver> clientObservers;
	private int ASSIGN_SESSION_ID_NEXT = 0;
	private int ASSIGN_ROOM_ID_NEXT = 0;
	
	public NwbServerGateImpl() throws RemoteException {
		super();
		
		rooms = new ArrayList<NwbRoomData>();
		roomServers = new HashMap<Integer, NwbServerRoomImpl>();
		
		clients = new HashMap<String, NwbUserDataSecure>();
		clientObservers = new HashMap<NwbUserDataSecure, NwbServerGateObserver>();
		
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
    
    @Override
    public void signOut(NwbUserDataSecure user) throws RemoteException {
		clientObservers.remove(user);
		clients.remove(user);
		
		for(NwbServerRoomImpl rs: roomServers.values())
		{
			rs.exitRoom(user);
		}
	}

    @Override
	public NwbServerRoom createRoom(NwbUserDataSecure user, String roomname, int maxusers)
	{
		NwbRoomData roomData = new NwbRoomData(generateRoomId(), roomname, user, maxusers);
		NwbServerRoomImpl room = null;
		try {
			room = new NwbServerRoomImpl(roomData, user, this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		room.addClient(user);
		
		rooms.add(roomData);
		roomServers.put(roomData.getRoomid(), room);
		
		return room;
	}
    
	public void deleteRoom(NwbRoomData room)
	{
		if(rooms.contains(room))
		{
			roomServers.remove(room.getRoomid());
			rooms.remove(room);
		}
	}
		
	public List<NwbRoomData> getRoomList(NwbUserDataSecure user)
	{
		List<NwbRoomData> ret = rooms;
		
		return ret;
	}
	
	public void joinRoomRequest(NwbUserDataSecure user, NwbRoomData room)
	{
		System.out.println("joinRoomRequest : user="+user+", room=" + room);
		NwbServerRoomImpl roomserver = roomServers.get(room.getRoomid());
		if(roomserver == null)
		{
			System.err.println("joinRoomRequest: no room server!");
			return;
		}
		
		roomserver.requestJoin(user);
	}

	public void manageJoinResponse(NwbServerRoomImpl nwbServerRoomImpl,
			NwbUserData joinUser, boolean isAccepted) 
	{
		NwbUserDataSecure joinUserSecure = clients.get(joinUser.getUsername());
		if(joinUserSecure.getSessionid() != joinUser.getSessionid()) {
			//the user is not valid anymore
			System.err.println("manageJoinResponse: user is not valid!");
			return;
		}

		NwbServerGateObserver observer = clientObservers.get(joinUserSecure);
		
		nwbServerRoomImpl.addClient(joinUserSecure);
		
		NwbServerRoom argServer = isAccepted ? nwbServerRoomImpl : null;
		
		try {
			observer.notifyJoin(nwbServerRoomImpl.getRoomData(), isAccepted, argServer);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
