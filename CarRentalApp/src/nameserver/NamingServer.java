package nameserver;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import rental.RentalServer;

public class NamingServer {

	public static void main(String[] args) throws Exception {
		System.setSecurityManager(null);
		
		ICRCNamingService incrc = (ICRCNamingService) new CRCNamingService();
		
		ICRCNamingService namingstub = (ICRCNamingService) UnicastRemoteObject.exportObject(incrc,0);
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("ns", namingstub);
		
	}
	
}
