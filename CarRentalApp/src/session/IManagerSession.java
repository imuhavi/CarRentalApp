package session;

import java.rmi.Remote;
import java.util.Collection;
import java.util.Set;

import rental.CarType;
import rental.ICarRentalCompany;

public interface IManagerSession extends Remote {

	public void registerCRC(ICarRentalCompany crc, String name) throws Exception ;
	
	public void unregisterCRC(String name) throws Exception ;
	
	public ICarRentalCompany getCRC(String name) throws Exception ;
	
	public Collection<String> getCarTypes(String crc) throws Exception;
	
	public int getNbReservationCarType(String crc, String ct) throws Exception ;
	
	public Set<String> getBestCustomers() throws Exception ;
	
	public CarType getMostPopularCarType(String name, int year) throws Exception;
	
	public int getNumberOfReservationsBy(String renter) throws Exception;
}
