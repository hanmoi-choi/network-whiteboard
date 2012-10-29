package client.model.room;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import client.view.ui.factory.NwbMenuFactory;
import client.view.ui.factory.NwbRemoteOptionFactory;

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
	
	private final ExecutorService keepAlivePool;
	private static final int KEEP_ALIVE_POOL_SIZE = 1;

	public NwbClientRoom()
	{
		keepAlivePool = Executors.newFixedThreadPool(KEEP_ALIVE_POOL_SIZE);	
	}
	
    public void enterRoom(NwbUserDataSecure user, NwbServerRoom server) 
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
        
		keepAlivePool.execute(new KeepAliveChecker());
        
        isExited = false;
    }
    
	class KeepAliveChecker implements Runnable
	{
		static final int SLEEP_TIME = 1000;

		@Override
		public void run() {
			for(;;)
			{
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				long time = System.currentTimeMillis();
				
				if(isExited)
					break;
				
				if(checkServerAlive() == false)
					break;
				System.out.println("Response time from the Server(ms):" 
					+ (System.currentTimeMillis()-time));
			}
		}
	}
	
	private boolean checkServerAlive()
	{
		try {
			if(server != null)
				return server.alive();
		} catch (ConnectException ce){
			System.err.println("checkServerAlive: Server is not online");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		handleServerException();
		return false;
	}	
    private boolean isExited = false;
    
	public void exitRoom()
	{
		if(isExited)
			return;
		
		this.keepAlivePool.shutdown();

		try {
			if(server != null)
				server.exitRoom(this.user);
			server = null;
		} catch (java.rmi.ConnectException e) {
			server = null;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if(observer != null)
				java.rmi.server.UnicastRemoteObject.unexportObject(observer, true);
			observer = null;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		isExited = true;
		
	}
	   
    private void updateRoomdata() throws RemoteException
    {
        this.room = server.getRoomData();
    }
    
	public void refresh() {
		try {
			updateRoomdata();
			NwbRemoteOptionFactory.updateMemberList(server);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
    private void handleServerException()
    {
		JOptionPane.showMessageDialog(null,
				"Server is not online. The room would be banged!",
				"Server Error", JOptionPane.ERROR_MESSAGE);	
		NwbMenuFactory.forceChangeLocalMode();
    }

	public void manageJoinRequest(NwbUserData joinUser) {
		// Pop-up and ask accepting for the user.
		int option = JOptionPane.showConfirmDialog(null,
				joinUser.getUsername()+" wants to join. Do you want to accept join?",
				"Join request", JOptionPane.YES_NO_OPTION);
		
		System.out.println("manageJoinRequested: Do you want to accept join?" + joinUser);
		
		boolean opt = (option == JOptionPane.YES_OPTION) ? true : false;
		System.out.println("option"+opt);
		
		manageJoinResponse(joinUser, opt);
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
	
	public List<NwbUserData> getUserList()
	{
		return this.room.getUserList();
	}
	
	public NwbRoomData getRoomData()
	{
		return this.room;
	}
	public NwbUserData getManager()
	{
		return this.room.getManager();
	}
	

	public void notifyKicked()
	{
		JOptionPane.showMessageDialog(null,
				"Oops, I'm kicked out by manager..",
				"being kicked out", JOptionPane.ERROR_MESSAGE);
		
		exitRoom();
		NwbMenuFactory.forceChangeLocalMode();
	}
	
	public void notifyTerminate()
	{
		JOptionPane.showMessageDialog(null,
				"OMG, this room is terminated!",
				"terminate", JOptionPane.ERROR_MESSAGE);

		exitRoom();
		NwbMenuFactory.forceChangeLocalMode();
	}
}
