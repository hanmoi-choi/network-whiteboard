package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class NwbServer {
	public static final String NWB_SERVICE_MODEL_NAME = "NwbServiceModel";
	
	public void startModelService(String hostname)
	{
		System.setProperty("java.rmi.server.codebase", 
				this.getClass().getProtectionDomain().getCodeSource().getLocation().toString());
		System.out.println(this.getClass().getProtectionDomain().getCodeSource().getLocation().toString());
		
		try {
			NwbRemoteModelServer serverModel = new NwbRemoteModelServerImpl();
			Naming.rebind("rmi://"+hostname+"/"+NWB_SERVICE_MODEL_NAME, serverModel);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("RMI Server is running...");
		
	}
	public static void main(String[] args) {
		
		// Test hostname
		String hostname = "localhost:30010";
		NwbServer server = new NwbServer();
		
		server.startModelService(hostname);
		
	}
}
