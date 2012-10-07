package server.room;

import server.NwbUserData;

public class NwbRoomDataInternal 
		implements java.io.Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2880705359060155959L;
	private int roomid;
	private String roomname;
	private NwbUserData manager;
	private int maxusers;		// maximum users joinable
	
	public NwbRoomDataInternal(int roomid, String roomname, NwbUserData manager, int maxusers)
	{
		this.roomid = roomid;
		this.roomname = roomname;
		this.manager = manager;
		this.maxusers =maxusers;
	}
	public NwbRoomDataInternal(NwbRoomDataInternal room)
	{
		this(room.roomid, room.roomname, room.manager, room.maxusers);
	}

	public int getRoomid() {
		return roomid;
	}

	public String getRoomname() {
		return roomname;
	}

	public NwbUserData getManager() {
		return manager;
	}

	public int getMaxusers() {
		return maxusers;
	}
	
	public String toString()
	{
		return "NwbRoomDataInternal: id " + roomid +
				", name " + roomname +
				", manager " + manager +
				", max " + maxusers;
	}
	public boolean equals(Object o)
	{
		if(o instanceof NwbRoomDataInternal)
		{
			NwbRoomDataInternal room = (NwbRoomDataInternal)o;
			if(this.roomid == room.roomid)
				return true;
		}
		return false;			
	}
	public int hashCode() 
	{
		  return ((Integer)roomid).hashCode();
	}	
}
