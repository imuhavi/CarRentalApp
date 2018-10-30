package nameserver;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Remote;

import rental.CarRentalCompany;
import rental.ICarRentalCompany;
import rental.ReservationException;

public class CRCNamingService implements Remote{

	public static void main(String[] args) {
		System.setSecurityManager(null);
	}
	
	public void addCRC(ICarRentalCompany crc, String name) throws MalformedURLException, RemoteException, AlreadyBoundException{
		ICarRentalCompany stub = (ICarRentalCompany) UnicastRemoteObject.exportObject(crc,0);
		Naming.rebind(name, stub);
		
	}
	
	public void removeCRC(String name) throws RemoteException, MalformedURLException, NotBoundException {
		Naming.unbind(name);
	}
	
	public ICarRentalCompany getCRC(String name) throws MalformedURLException, RemoteException, NotBoundException{
		return (ICarRentalCompany) Naming.lookup(name);
	}
	
}
