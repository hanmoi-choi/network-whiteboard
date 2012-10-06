package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import server.NwbServerGate;
import server.NwbServerGateObserver;
import server.NwbUserDataSecure;
import server.room.NwbRoomData;
import server.room.NwbServerRemoteModel;
import server.room.NwbServerRoom;
import client.controller.NwbControllerFactory;
import client.model.NwbClientModel;
import client.model.NwbRemoteModel;
import client.model.factory.NwbClientModelFactory;
import client.model.room.NwbClientConnector;
import client.model.room.NwbClientRoom;

public class NwbClientTest
			extends UnicastRemoteObject
			implements NwbServerGateObserver
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5905131711792637463L;
	String username = null;
	String hostname = null;
	
	NwbUserDataSecure user = null;
	
	public NwbClientTest(String username, String hostname) throws RemoteException
	{
		super();
		
		this.hostname = hostname;
		this.username = username;		
	}

	public NwbUserDataSecure testLogin()
	{
		NwbServerGate server = NwbClientConnector.connectServer(this.hostname);
		
        // Sign in to server
		NwbUserDataSecure user = null;
		try {
			this.user = user = server.signIn(username, this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	

	public NwbClientRoom testCreateRoom(NwbUserDataSecure user)
	{
		NwbServerGate server = NwbClientConnector.connectServer(this.hostname);
		
        // create room on server side
		NwbServerRoom roomServer = null;
		try {
			roomServer = server.createRoom(user, "Test Room", 10);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NwbClientRoom room = NwbClientModelFactory.createRoomFactory(user, roomServer);
		return room;
	}

	public NwbClientModel testCreateModel(NwbUserDataSecure user, NwbServerRemoteModel modelServer)
	{
		NwbClientModel model=null;
		
		// Make model
		model = NwbClientModelFactory.createModelFactory(true);
		((NwbRemoteModel)model).startRemoteMode(user, modelServer);
		
		return model;
		
	}
	
	public List<NwbRoomData> testGetRoomList(NwbUserDataSecure user)
	{
		NwbServerGate server = NwbClientConnector.connectServer(this.hostname);
		
		// get room list
		List<NwbRoomData> rooms = null;
		try {
			rooms = server.getRoomList(user);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(NwbRoomData room:rooms)
		{
			System.out.println("ID : "+room.getRoomid() + 
					", Name: " + room.getRoomname() + 
					", MaxUsers: "+room.getMaxusers());
		}
		return rooms;
	}
	
	public void testJoinRoomRequest(NwbUserDataSecure user, NwbRoomData r)
	{
		NwbServerGate server = NwbClientConnector.connectServer(this.hostname);
		
		try {
			server.joinRoomRequest(user, r);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void notifyJoin(NwbRoomData roomData, boolean isAccepted, NwbServerRoom roomServer)
	{
		if(isAccepted)
		{
			System.out.println("notifyJoin: accepted!");
			NwbClientRoom room = NwbClientModelFactory.createRoomFactory(user, roomServer);
			NwbClientModel newModel = this.testCreateModel(user, room.getServerRemoteModel());
	        this.setModel(newModel);
	    }
		else
		{
			// Not accepted to join the room
			// Maybe try another room?
			System.out.println("notifyJoin: denied - "+ roomData);
		}
		
	}

	public void setModel(NwbClientModel model)
	{
		NwbControllerFactory.setModel(model);
	}

	// Test Program. Follow this procedure!
	// It connects to server, sign in and do either
	// *create* a room (if username == 'user0') or *join* the 1st room (otherwise) 
	public static void startTest(String username)
	{
        // Initialise -----------------------------------------------------
        NwbClientTest test = null;
		try {
			test = new NwbClientTest(username, "localhost:30010");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        NwbUserDataSecure user = test.testLogin();
        
        if(username.equals("user0"))
        {
            //*/// Create Room --------------------------------------------------
	        NwbClientRoom room = test.testCreateRoom(user);
			NwbClientModel newModel = test.testCreateModel(user, room.getServerRemoteModel());
	        test.setModel(newModel);
        }
        else
        {
	        //*/// Join Room --------------------------------------------------
	        List<NwbRoomData> rooms = test.testGetRoomList(user);
	        test.testJoinRoomRequest(user, rooms.get(0));
        }
	}
	
	
}
