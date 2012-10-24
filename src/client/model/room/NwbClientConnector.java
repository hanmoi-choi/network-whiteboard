package client.model.room;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import server.NwbServer;
import server.NwbServerGate;

public class NwbClientConnector {
	public static NwbServerGate connectServer(String hostname)
	{
		System.setProperty("java.rmi.server.codebase", 
				NwbClientConnector.class.getProtectionDomain().getCodeSource().getLocation().toString());
		
		if (System.getProperty("java.security.policy") == null)
		{
			System.out.println("Set policy...");
			System.setProperty("java.security.policy", NwbServer.NWB_SECURITY_POLICY_FILE);
		}
		if (System.getSecurityManager() == null)
		{ 
			System.out.println("Set SecurityManager...");
			System.setSecurityManager(new RMISecurityManager());
		}
		
		System.out.println(NwbClientConnector.class.getProtectionDomain().getCodeSource().getLocation().toString());
		
		NwbServerGate sv = null;
		try {
			sv = (NwbServerGate)Naming.lookup("rmi://"+hostname+"/"+NwbServer.NWB_SERVICE_NAME);
		} catch (java.rmi.ConnectException e) {
			//Do nothing. just return null
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
		    e.printStackTrace();
		}
		
		return sv;	    
	}
}
