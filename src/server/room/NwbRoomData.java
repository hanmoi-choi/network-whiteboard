package server.room;

import java.util.List;

import server.NwbUserData;

public class NwbRoomData
		extends NwbRoomDataInternal {
	private List<NwbUserData> users;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6141243065118394156L;

	public NwbRoomData(NwbRoomDataInternal room, List<NwbUserData> users) {
		super(room);
		this.users = users;
	}
	
	public List<NwbUserData> getUserList()
	{
		return users;
	}
	
	public int getNumusers()
	{
		return users.size();
	}
	
	public String toString()
	{
		return "NwbRoomData: (Room=" +super.toString()
				+"), (users=" +this.users + ")";
	}
}
