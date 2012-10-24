package server;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NwbServer {
	public static final String NWB_SERVICE_NAME = "NwbService";
	public static final String NWB_SECURITY_POLICY_FILE = "security.policy";
	
	public void startService(int port)
	{
		System.setProperty("java.rmi.server.codebase", 
				this.getClass().getProtectionDomain().getCodeSource().getLocation().toString());
		if (System.getProperty("java.security.policy") == null)
		{
			System.out.println("Set policy...");
			System.setProperty("java.security.policy", NWB_SECURITY_POLICY_FILE);
		}
		if (System.getSecurityManager() == null)
		{ 
			System.out.println("Set SecurityManager...");
			System.setSecurityManager(new RMISecurityManager());
		}
		
		try {
			NwbServerGate server = new NwbServerGateImpl();
			Registry registry = LocateRegistry.createRegistry(port);
			registry.rebind(NWB_SERVICE_NAME, server);
		} catch(java.rmi.server.ExportException e) {
			System.err.println("Cannot bind a server to RMI service. Port may be in use:"+port);
			System.exit(0);
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("NwbServer has started with the port:"+port);
		
	}
	public static void main(String[] args) {
		
		// Default setting is localhost:30010
		int port = 30010;
		
		if(args.length == 1)
		{
			port = Integer.parseInt(args[0]);
		}
		else if(args.length != 0)
		{
			usage();
			System.exit(0);
		}
		
		NwbServer server = new NwbServer();
		server.startService(port);
		
	}
	private static void usage()
	{
		System.out.println("usage: java -jar NwbServer.jar [portNum]");
		System.out.println("Please make sure that you have proper security.policy file in the same directory.");
		System.out.println("   you can simply make policy file with that:");
		System.out.println("   grant { permission java.security.AllPermission; };");
	}
}
