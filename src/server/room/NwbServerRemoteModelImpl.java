package server.room;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import server.NwbUserData;
import server.NwbUserDataSecure;

import client.model.NwbDrawingCommand;

public class NwbServerRemoteModelImpl
				extends UnicastRemoteObject 
				implements NwbServerRemoteModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8352118101116000485L;
	
	private ArrayList<DrawingCommandData> modelData;
	private HashMap<NwbUserDataSecure, NwbServerRemoteModelObserver> clientObservers;
	private Integer commandId = 0;

	// Structure to store model data of each room in the server.
	class DrawingCommandData {
		private int id;
		private NwbUserDataSecure createdUser;
		private NwbDrawingCommand command;
		
		public DrawingCommandData(int id, NwbUserDataSecure user, NwbDrawingCommand cmd)
		{
			this.id=id; 
			this.createdUser = new NwbUserDataSecure(user); 
			this.command = cmd; 
		}

		public NwbUserDataSecure getCreatedUser() {
			return createdUser;
		}
		public NwbDrawingCommand getCommand() {
			return command;
		}
	}
	
	public NwbServerRemoteModelImpl() throws RemoteException {
		super();
		
		clientObservers = new HashMap<NwbUserDataSecure, NwbServerRemoteModelObserver>();
		modelData = new ArrayList<DrawingCommandData>();
	}
	
	public void addClient(NwbUserDataSecure user)  
	{
		clientObservers.put(user, null);		
	}
	
    public void removeClient(NwbUserDataSecure user) 
    {
		clientObservers.remove(user);
	}
	
    // Remote functions
    @Override
	public void bindObserver(NwbUserDataSecure user, NwbServerRemoteModelObserver observer) throws RemoteException 
	{
		clientObservers.put(user, observer);		
	}
    
    @Override
    public int addCommand(NwbUserDataSecure user, NwbDrawingCommand command) {
    	int id = 0;
    	synchronized(commandId)
    	{
	    	id = commandId;
	    	commandId++;
    	}
    	
    	synchronized(modelData)
    	{
	    	modelData.add(new DrawingCommandData (id, user, command));
    	}
    	
    	notifyAddCommand(id, user, command);
    	return id;
	}
    
    @Override
    public void removeCommand(NwbUserDataSecure requestUser, int commandId)
    {
    	synchronized(modelData)
    	{
    		int index = findCommand(commandId);
    		if(index == -1)
    			return;
    		
    		modelData.remove(index);
    	}
    	notifyRemoveCommand(requestUser, commandId);
    }

    private int findCommand(int commandId)
    {
    	for(int i=0; i<modelData.size(); i++)
    	{
    		if(modelData.get(i).id == commandId)
    			return i;
    	}
    	return -1;
    }
       
    private void notifyAddCommand(int id, NwbUserDataSecure reqUser, NwbDrawingCommand command)
    {
    	// Removes secure field from to send all clients
    	NwbUserData requestUser = new NwbUserData(reqUser.getUsername(), reqUser.getSessionid());

    	for(NwbUserDataSecure user: clientObservers.keySet())
    	{
    		if(user.getSessionid() != requestUser.getSessionid() )
    		{
	    		try {
	    			NwbServerRemoteModelObserver observer = clientObservers.get(user);
					observer.addCommand(id, requestUser, command);
				} catch (ConnectException ce) {
					removeClient(user);
					ce.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    		}
    	}
    }
    
    private void notifyRemoveCommand(NwbUserDataSecure reqUser, int id)
    {
    	// Removes secure field from to send all clients
    	NwbUserData requestUser = new NwbUserData(reqUser.getUsername(), reqUser.getSessionid());
    	
    	for(NwbUserDataSecure user: clientObservers.keySet())
    	{
    		if(user.getSessionid() != requestUser.getSessionid() )
    		{
	    		try {
	    			NwbServerRemoteModelObserver observer = clientObservers.get(user);
					observer.removeCommand(id);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    }

}
