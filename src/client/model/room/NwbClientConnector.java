package client.model.room;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import server.NwbServerGate;

public class NwbClientConnector {
	public static NwbServerGate connectServer(String hostname)
	{
		System.setProperty("java.rmi.server.codebase", 
				NwbClientConnector.class.getProtectionDomain().getCodeSource().getLocation().toString());

		System.out.println(NwbClientConnector.class.getProtectionDomain().getCodeSource().getLocation().toString());
		
		NwbServerGate sv = null;
		try {
			sv = (NwbServerGate)Naming.lookup("rmi://"+hostname+"/"+server.NwbServer.NWB_SERVICE_NAME);
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
