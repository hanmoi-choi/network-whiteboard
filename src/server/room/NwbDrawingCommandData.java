package server.room;

import server.NwbUserData;
import client.model.NwbDrawingCommand;

public class NwbDrawingCommandData implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5030116859308099485L;
	private int id;
	private NwbUserData createdUser;
	private NwbDrawingCommand command;
	
	public NwbDrawingCommandData(int id, NwbUserData user, NwbDrawingCommand cmd)
	{
		this.id=id; 
		this.createdUser = new NwbUserData(user); 
		this.command = cmd; 
	}

	public NwbUserData getCreatedUser() {
		return createdUser;
	}
	public NwbDrawingCommand getCommand() {
		return command;
	}
	public int getId() {
		return id;
	}
}

