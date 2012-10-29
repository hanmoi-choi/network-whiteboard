package client.model;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Stack;

import javax.swing.JOptionPane;

import client.view.ui.factory.NwbMenuFactory;

import server.NwbUserData;
import server.NwbUserDataSecure;
import server.room.NwbDrawingCommandData;
import server.room.NwbServerRemoteModel;

/**
 * Created with IntelliJ IDEA.
 * User: hanmoi
 * Date: 14/09/12
 * Time: 9:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class NwbRemoteModel extends NwbClientModel {
	private Stack<Integer> commandIdStack;
	private Stack<NwbUserData> commandUserStack;	// who is in charge of the command.
	
	private NwbUserDataSecure user;
	
	private NwbServerRemoteModel server;
    NwbRemoteModelObserverImpl observer;

    public NwbRemoteModel() 
    {
        super();
        
        commandUserStack = new Stack<NwbUserData>();
        commandIdStack = new Stack<Integer>();
    }
    
    public void startRemoteMode(NwbUserDataSecure user, NwbServerRemoteModel server)
    {
        this.user = new NwbUserDataSecure(user);
        this.server = server;
        
		try {
			observer = new NwbRemoteModelObserverImpl(this);
	        server.bindObserver(user, observer);
	        
	        List<NwbDrawingCommandData> initData = server.getAllCommands(user);
	        
	        for(NwbDrawingCommandData data:initData)
	        {
	        	addCommand(data.getId(),data.getCreatedUser(),data.getCommand());
	        }
	        
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void stop()
    {
    	try {
			server.removeClient(this.user);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			UnicastRemoteObject.unexportObject(this.observer, true);
		} catch (NoSuchObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    public void pushDrawingCommand(NwbDrawingCommand command) {
    	addCommandFromClient(command);
        super.updateSubscribers();
    }

    @Override
    public void undo() {
        if (!commandStack.empty()) {
        	NwbDrawingCommand cmd = removeCommandFromClient(this.user);
        	if(cmd != null)
        		redoStack.push(cmd);            
            super.updateSubscribers();
        }
    }

    @Override
    public void redo() {
        if (!redoStack.empty()) {
        	addCommandFromClient(redoStack.pop());
        	super.updateSubscribers();
        }
    }

    @Override
    public void clearStack(){
    	
    	while(removeCommandFromClient(this.user) != null);    	
        redoStack.clear();
        super.updateSubscribers();
        
        try {
			server.removeCommandAll(user);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void handleServerException()
    {
		JOptionPane.showMessageDialog(null,
				"Cannot connect to the remote room! The room is banged!",
				"Server Error", JOptionPane.ERROR_MESSAGE);	
		NwbMenuFactory.forceChangeLocalMode();
    }
    
    // Remote model specific methods
    public void addCommandFromClient(NwbDrawingCommand command)
    {
    	int id = 0;
		try {
			id = server.addCommand(this.user, command);
		} catch (RemoteException e) {
			e.printStackTrace();
			handleServerException();
		}
    	addCommand(id, this.user, command);
    }
    public NwbDrawingCommand removeCommandFromClient(int commandId)
    {
    	NwbDrawingCommand ret = removeCommand(commandId);
    	try {
			server.removeCommand(this.user, commandId);
		} catch (RemoteException e) {
			e.printStackTrace();
			handleServerException();
		}
    	
    	return ret;
    }
    
    public NwbDrawingCommand removeCommandFromClient(NwbUserData user)
    {
    	int index = commandUserStack.lastIndexOf(user);
    	
    	if(index == -1)
    		return null;
    	
    	int id = commandIdStack.get(index);
    	
    	NwbDrawingCommand ret = removeCommand(id);
    	try {
			server.removeCommand(this.user, id);
		} catch (RemoteException e) {
			e.printStackTrace();
			handleServerException();
		}
    	
    	return ret;
    }
    
    public void addCommandFromServer(int commandId, NwbUserData user, NwbDrawingCommand command)
    {
    	addCommand(commandId, user, command);
    	super.updateSubscribers();
    }
    
    public void removeCommandFromServer(int commandId)
    {
    	removeCommand(commandId);
    	super.updateSubscribers();
    }
    
    public void removeCommandAllFromServer()
    {
    	synchronized(commandStack)
    	{
	    	commandIdStack.clear();
	    	commandUserStack.clear();
	        commandStack.clear();
    	}
    	redoStack.clear();
    	super.updateSubscribers();
    }

    // Internal methods
    private synchronized void addCommand(int commandId, NwbUserData user, NwbDrawingCommand command)
    {
    	synchronized(commandStack)
    	{
    		addCommandInternal(commandId, user, command);
    	}
    }
    
    // remove the first command found that matches the commandId
    private synchronized NwbDrawingCommand removeCommand(int commandId)
    {
    	NwbDrawingCommand ret = null;
    	
    	synchronized(commandStack)
    	{
	    	int index = commandIdStack.indexOf(commandId);
	    	if(index != -1)
	    	{
	    		ret = removeCommandInternal(index);
	    	}
    	}
        return ret;
    }
    
    private int findCommandLocation(int newCommandId)
    {
    	for(int i=commandIdStack.size(); i>0; i--)
    	{
    		if(commandIdStack.get(i-1) < newCommandId)
    			return i;
    	}
    	return 0;
    }
    
    private synchronized void addCommandInternal(int commandId, NwbUserData user, NwbDrawingCommand command)
    {
    	// To ensure adding command to the right place, find the place for it
    	int index = findCommandLocation(commandId); 
    	commandIdStack.add(index, commandId);
    	commandUserStack.add(index, user);
        commandStack.add(index, command);
    }
    
    private synchronized NwbDrawingCommand removeCommandInternal(int index)
    {
    	NwbDrawingCommand ret = null;
    	if(index == -1)
    		return ret;
    	
    	if(commandIdStack.empty() || commandUserStack.empty() || commandStack.empty())
    		return ret;
    		
   		commandIdStack.remove(index);
   		commandUserStack.remove(index);
   		ret = commandStack.remove(index);
   		
   		return ret;
    }
}
