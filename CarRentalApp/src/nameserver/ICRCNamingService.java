package nameserver;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import rental.ICarRentalCompany;

public interface ICRCNamingService extends Remote {

	public void addCRC(ICarRentalCompany crc, String name) throws MalformedURLException, RemoteException, AlreadyBoundException;
	
	public void removeCRC(String name) throws RemoteException, MalformedURLException, NotBoundException;
	
	public ICarRentalCompany getCRC(String name) throws MalformedURLException, RemoteException, NotBoundException;
	
	public List<ICarRentalCompany> getAllCRCs() throws Exception;
	
	public Set<String> getAllNames() throws Exception;
}
