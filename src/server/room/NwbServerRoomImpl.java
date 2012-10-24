package server.room;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.NwbServerGate;
import server.NwbUserData;
import server.NwbUserDataSecure;

public class NwbServerRoomImpl extends UnicastRemoteObject implements
		NwbServerRoom {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7375101647398791502L;
	public static final int POOL_SIZE = 10;

	private NwbRoomDataInternal roomdata = null;
	private NwbUserDataSecure manager = null;
	private NwbServerGate gate = null;
	private HashMap<NwbUserDataSecure, NwbServerRoomObserver> clientObservers;

	private NwbServerRemoteModelImpl modelServer = null;

	private final ExecutorService pool;

	public NwbServerRoomImpl(NwbRoomDataInternal roomdata,
			NwbUserDataSecure manager, NwbServerGate gate)
			throws RemoteException {
		super();

		this.roomdata = roomdata;
		this.manager = manager;
		this.clientObservers = new HashMap<NwbUserDataSecure, NwbServerRoomObserver>();

		this.gate = gate;

		this.modelServer = new NwbServerRemoteModelImpl(POOL_SIZE);

		pool = Executors.newFixedThreadPool(POOL_SIZE);
	}

	public void requestJoin(NwbUserDataSecure user) {
		pool.execute(new ManageJoinRequestHandler(user));
	}

	ArrayList<NwbUserDataSecure> candidateMembers = new ArrayList<NwbUserDataSecure>();

	private int findCandidateMember(NwbUserData user) {
		return candidateMembers.indexOf(user);
	}

	private void manageJoinRequest(NwbUserDataSecure user) {
		NwbUserData requestedUser = new NwbUserData(user.getUsername(),
				user.getSessionid());
		candidateMembers.add(user);

		NwbServerRoomObserver managerObserver = this.clientObservers
				.get(manager);
		try {
			managerObserver.manageJoinRequest(requestedUser);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getNumUsers() {
		return clientObservers.size();
	}

	public boolean isJoinable() {
		boolean ret = true;
		if (getNumUsers() >= roomdata.getMaxusers())
			ret = false;

		return ret;
	}

	@Override
	public void manageJoinResponse(NwbUserDataSecure manager,
			NwbUserData joinUser, boolean isAccepted) throws RemoteException {
		if (!this.manager.equalsSecure(manager)) {
			// Attack?
			System.err.println("manageJoinResponse: Manager is invalid. "
					+ manager);
			return;
		}

		int index = this.findCandidateMember(joinUser);
		NwbUserDataSecure joinUserSecure = candidateMembers.get(index);
		candidateMembers.remove(index);

		if (joinUserSecure.getSessionid() != joinUser.getSessionid()) {
			// the user is not valid anymore
			System.err
					.println("manageJoinResponse: user is not valid. Maybe exit the program!");
			return;
		}

		if (isAccepted) {
			addClient(joinUserSecure);
		}

		gate.notifyJoinResponse(this, joinUser, isAccepted);
	}

	public void addClient(NwbUserDataSecure user) {
		System.out.println("Room: addClient : user=" + user);

		clientObservers.put(user, null);
	}

	private Set<NwbUserDataSecure> users() {
		return clientObservers.keySet();
	}

	private NwbUserDataSecure findUser(NwbUserData user) {
		for (NwbUserDataSecure u : users())
			if (u.equals(user))
				return u;
		return null;

	}

	private ArrayList<NwbUserData> getUserList() {
		ArrayList<NwbUserData> ret = new ArrayList<NwbUserData>();
		for (NwbUserDataSecure u : users())
			ret.add(u.getUserData());
		return ret;
	}

	private void notifyRefresh() {
		for (NwbServerRoomObserver o : clientObservers.values()) {
			try {
				o.refresh();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void notifyTerminateRoomInvoke()
	{
		pool.execute(new NotifyTerminateHandler(clientObservers));
	}
	
	private void notifyTerminateRoom(
			HashMap<NwbUserDataSecure, NwbServerRoomObserver> clientObservers) 
	{
		for (NwbServerRoomObserver o : clientObservers.values()) {
			try {
				o.notifyTerminateRoom();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean bindObserver(NwbUserDataSecure user,
			NwbServerRoomObserver observer) throws RemoteException {
		boolean ret = false;
		if (clientObservers.containsKey(user)) {
			clientObservers.put(user, observer);
			ret = true;
		}
		System.out.println("Room.bindObserver:user=" + user + ", ret=" + ret);

		// Joining process is DONE. Notify to all clients about this.
		// notifyRefresh();
		pool.execute(new NotifyRefreshHandler(this));

		return ret;
	}

	private void stop() {
		// manager has exited from the room. notify to clients and delete room
		notifyTerminateRoomInvoke();

		clientObservers.clear();
		modelServer.stop();
		try {
			gate.deleteRoom(this.roomdata);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			roomdata = null;
			manager = null;
		}
	}

	@Override
	public void exitRoom(NwbUserDataSecure user) throws RemoteException {
		clientObservers.remove(user);
		modelServer.removeClient(user);

		if (clientObservers.size() <= 0 || user.equalsSecure(this.manager)) {
			stop();
		} else {
			pool.execute(new NotifyRefreshHandler(this));
		}

	}

	@Override
	public NwbServerRemoteModel getServerRemoteModel(NwbUserDataSecure user)
			throws RemoteException {
		return this.modelServer;
	}

	@Override
	public NwbRoomData getRoomData() throws RemoteException {
		NwbRoomData ret = new NwbRoomData(roomdata, getUserList());
		return ret;
	}

	@Override
	public void manageKick(NwbUserDataSecure manager, NwbUserData kickUser)
			throws RemoteException {
		if (!this.manager.equalsSecure(manager)) {
			// Attack?
			System.err.println("manageKick: Manager is invalid. " + manager);
			return;
		}

		NwbUserDataSecure kickUserSecure = findUser(kickUser);
		NwbServerRoomObserver observer = clientObservers.get(kickUser);

		// force exit the user from the room
		exitRoom(kickUserSecure);

		// notify to the user that he is kicked out
		pool.execute(new NotifyKickedHandler(observer));
	}

	class NotifyRefreshHandler implements Runnable {
		NwbServerRoomImpl o;

		NotifyRefreshHandler(NwbServerRoomImpl o) {
			this.o = o;
		}

		@Override
		public void run() {
			o.notifyRefresh();
		}
	}

	class NotifyKickedHandler implements Runnable {
		NwbServerRoomObserver observer;

		NotifyKickedHandler(NwbServerRoomObserver observer) {
			this.observer = observer;
		}

		@Override
		public void run() {
			try {
				observer.notifyKicked();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class NotifyTerminateHandler implements Runnable {
		HashMap<NwbUserDataSecure, NwbServerRoomObserver> clientObservers;

		NotifyTerminateHandler(HashMap<NwbUserDataSecure, NwbServerRoomObserver> clientObservers) {
			this.clientObservers = 
					new HashMap<NwbUserDataSecure, NwbServerRoomObserver>(clientObservers);
		}

		@Override
		public void run() {
			notifyTerminateRoom(clientObservers);
		}
	}

	class ManageJoinRequestHandler implements Runnable {
		// Send to the manager to ask for joining
		NwbUserDataSecure user;

		ManageJoinRequestHandler(NwbUserDataSecure user) {
			this.user = user;
		}

		@Override
		public void run() {
			manageJoinRequest(user);
		}
	}

}
