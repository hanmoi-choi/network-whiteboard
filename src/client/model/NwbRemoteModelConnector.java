package client.model;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import server.NwbRemoteModelServer;

public class NwbRemoteModelConnector {
	public static NwbRemoteModelServer connectModel(String hostname)
	{
		System.setProperty("java.rmi.server.codebase", 
				NwbRemoteModelConnector.class.getProtectionDomain().getCodeSource().getLocation().toString());

		System.out.println(NwbRemoteModelConnector.class.getProtectionDomain().getCodeSource().getLocation().toString());
		
		NwbRemoteModelServer sv = null;
		try {
			sv = (NwbRemoteModelServer)Naming.lookup("rmi://"+hostname+"/"+server.NwbServer.NWB_SERVICE_MODEL_NAME);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sv;	    
	}
	
	
	
}
