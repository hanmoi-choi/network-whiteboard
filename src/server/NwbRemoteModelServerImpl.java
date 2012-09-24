package server;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import client.model.NwbRemoteModelObserver;
import client.model.NwbDrawingCommand;

public class NwbRemoteModelServerImpl
				extends UnicastRemoteObject 
				implements NwbRemoteModelServer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8352118101116000485L;

	class ServerModelData {
		public int id;
		public String user;
		public NwbDrawingCommand command;
		public ServerModelData(int id, String user, NwbDrawingCommand cmd)
		{ this.id=id; this.user=user; this.command = cmd; }
	}

	class ClientData {
		public String user;
		public NwbRemoteModelObserver observer;
		public ClientData(String user, NwbRemoteModelObserver observer)
		{ this.user=user; this.observer = observer; }
	}

	private ArrayList<ServerModelData> modelData;
	private ArrayList<ClientData> clients;
	
	private Integer commandId = 0;
	
	public NwbRemoteModelServerImpl() throws RemoteException {
		super();
		
		clients = new ArrayList<ClientData>();
		modelData = new ArrayList<ServerModelData>();
	}
	
    @Override
	public void connect(String user, NwbRemoteModelObserver client) throws RemoteException 
	{
		clients.add(new ClientData(user, client));		
	}
    @Override
    public void disconnect(String user) throws RemoteException {
		int index = -1;
		for(int i = 0; i< clients.size(); i++)
			if(user.equals(clients.get(i).user))
				index = i;
		if(index != -1)
			clients.remove(index);
	}
    
    @Override
    public int addCommand(String user, NwbDrawingCommand command) {
    	int id = 0;
    	
    	synchronized(commandId)
    	{
	    	id = commandId;
	    	commandId++;
    	}
    	
    	synchronized(modelData)
    	{
	    	modelData.add(new ServerModelData (id, user, command));
    	}
    	
    	notifyAddCommand(id, user, command);
    	return id;
	}
    
    @Override
    public void removeCommand(String requestUser, int commandId)
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
    
    private void notifyAddCommand(int id, String user, NwbDrawingCommand command)
    {
    	for(ClientData cli: clients)
    	{
    		if(!cli.user.equals(user))
    		{
	    		try {
					cli.observer.addCommand(id, user, command);
				} catch (ConnectException ce) {
					clients.remove(cli);
					ce.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
    		}
    	}
    }
    
    private void notifyRemoveCommand(String user, int id)
    {
    	for(ClientData cli: clients)
    	{
    		if(!cli.user.equals(user))
    		{
	    		try {
					cli.observer.removeCommand(id);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
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
    
}
