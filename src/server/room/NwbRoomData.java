package server.room;

import server.NwbUserData;

public class NwbRoomData implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8942151395426907988L;
	private int roomid;
	private String roomname;
	private NwbUserData manager;
	private int maxusers;		// maximum users joinable
	
	public NwbRoomData(int roomid, String roomname, NwbUserData manager, int maxusers)
	{
		this.roomid = roomid;
		this.roomname = roomname;
		this.manager = manager;
		this.maxusers =maxusers;
	}
	public NwbRoomData(NwbRoomData room)
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
		return "NwbRoomData: id " + roomid +
				", name " + roomname +
				", manager " + manager +
				", max " + maxusers;
	}
}
