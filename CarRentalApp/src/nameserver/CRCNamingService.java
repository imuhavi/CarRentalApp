package nameserver;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rental.ICarRentalCompany;

public class CRCNamingService implements ICRCNamingService{

	HashMap<String, ICarRentalCompany> crcs;
	
	public CRCNamingService(){
		crcs = new HashMap<String, ICarRentalCompany>();
	}
	
	public void addCRC(ICarRentalCompany crc, String name) throws MalformedURLException, RemoteException, AlreadyBoundException{
		ICarRentalCompany stub = (ICarRentalCompany) UnicastRemoteObject.exportObject(crc,0);
		crcs.put(name, stub);
	}
	
	public void removeCRC(String name) throws RemoteException, MalformedURLException, NotBoundException {
		crcs.remove(name);
	}
	
	public ICarRentalCompany getCRC(String name) throws MalformedURLException, RemoteException, NotBoundException{
		return (ICarRentalCompany) crcs.get(name);
	}
	
	public List<ICarRentalCompany> getAllCRCs() throws Exception {
		List<ICarRentalCompany> allcrcs = new ArrayList<ICarRentalCompany>();
		for(String name : crcs.keySet()){
			System.out.println(name);
			allcrcs.add(getCRC(name));
		}
		return allcrcs;
	}
	
}
