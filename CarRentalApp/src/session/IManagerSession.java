package session;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import rental.ICarRentalCompany;

public interface IManagerSession extends Remote {

	public void registerCRC(ICarRentalCompany crc, String name) throws Exception ;
	
	public void unregisterCRC(String name) throws Exception ;
	
	public ICarRentalCompany getCRC(String name) throws Exception ;
	
	public Collection<String> getCarTypes(String crc) throws Exception;
	
	public int getNbReservationCarType(String crc, String ct) throws Exception ;
	
	public String getBestCustomer() throws Exception ;
	
	public String getMostPopularCarType(String name, int year) throws Exception;
}
