package nameserver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class NamingServer {

	public static void main() throws Exception{
		ICRCNamingService incrc = (ICRCNamingService) new CRCNamingService();
		
		ICRCNamingService namingstub = (ICRCNamingService) UnicastRemoteObject.exportObject(incrc,0);
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("ns", namingstub);
		
//		ICRCNamin>gService ns = (ICRCNamingService) registry.lookup("ns");
	}
}
