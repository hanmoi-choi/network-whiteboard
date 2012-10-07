package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NwbServer {
	public static final String NWB_SERVICE_NAME = "NwbService";
	
	public void startService(String host, int port)
	{
		System.setProperty("java.rmi.server.codebase", 
				this.getClass().getProtectionDomain().getCodeSource().getLocation().toString());
		System.out.println(this.getClass().getProtectionDomain().getCodeSource().getLocation().toString());
		
		try {
			NwbServerGate server = new NwbServerGateImpl();
			Registry registry = LocateRegistry.createRegistry(port);
			registry.rebind(NWB_SERVICE_NAME, server);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("RMI Server is running...");
		
	}
	public static void main(String[] args) {
		
		// Test
		String host = "localhost";
		int port = 30010;
		
		if(args.length == 2)
		{
			host = args[0];
			port = Integer.parseInt(args[1]);
		}
		
		NwbServer server = new NwbServer();
		server.startService(host, port);
		
	}
}
