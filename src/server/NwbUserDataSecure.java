package server;

public class NwbUserDataSecure extends NwbUserData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1635719721729559114L;
	private int key;
	
	public NwbUserDataSecure(NwbUserDataSecure user)
	{
		super(user);
		this.key = user.key;
	}

	public NwbUserDataSecure(NwbUserData user, int key)
	{
		super(user);
		this.key = key;
	}

	public boolean equals(Object o)
	{
		if(o instanceof NwbUserDataSecure) {
			NwbUserDataSecure user = (NwbUserDataSecure)o;
			
			if(this.getSessionid() == user.getSessionid()
					&& this.getUsername().equals(user.getUsername())
					&& this.key == user.key)
				return true;
		}
		
		return false;			
	}

	public String toString()
	{
		return "NwbUserDataSecure: ("+super.toString()+ "), Key=" + this.key;
	}
}
