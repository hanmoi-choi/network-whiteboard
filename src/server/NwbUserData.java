package server;

public class NwbUserData implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3645297812455244730L;
	private String username;
	private int sessionid;
	
	public NwbUserData(NwbUserData user)
	{
		this.username = user.username;
		this.sessionid = user.sessionid;
	}
	
	public NwbUserData(String username)
	{
		this.username = username;
	}
	
	public NwbUserData(String username, int sessionid)
	{
		this.username = username;
		this.sessionid = sessionid;
	}
	
	public boolean equals(Object o)
	{
		if(o instanceof NwbUserData)
		{
			NwbUserData user = (NwbUserData)o;
			if(this.sessionid == user.sessionid
					&& this.username.equals(user.username))
				return true;
		}
		return false;			
	}
	public int hashCode() 
	{
		  return ((Integer)sessionid).hashCode();
	}	
	public String getUsername() {
		return username;
	}
	public int getSessionid() {
		return sessionid;
	}
	
	public String toString()
	{
		return "NwbUserData: username = "+this.username+ ", sessionid = " + this.sessionid;
	}
}
