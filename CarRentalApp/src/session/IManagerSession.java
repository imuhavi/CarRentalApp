package session;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import rental.ICarRentalCompany;

public interface IManagerSession extends Remote {

	public void registerCRC(ICarRentalCompany crc, String name) throws RemoteException;
	
	public void unregisterCRC(String name) throws RemoteException;
	
	public ICarRentalCompany getCRC(String name) throws RemoteException;
	
	public List<String> getCarTypes(String crc) throws RemoteException;
	
	public int getNbReservationCarType(String crc) throws RemoteException;
	
	public String getBestCustomer() throws RemoteException;
	
	public String getMostPopularCarType(int year) throws RemoteException;
}
