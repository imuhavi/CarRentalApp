package session;

import java.rmi.Remote;
import java.util.List;

import rental.ICarRentalCompany;

public interface IManagerSession extends Remote {

	public void registerCRC(ICarRentalCompany crc);
	
	public void unregisterCRC(String name);
	
	public ICarRentalCompany getCRC(String name);
	
	public List<String> getCarTypes(ICarRentalCompany crc);
	
	public int getNbReservationCarType(ICarRentalCompany crc);
	
	public String getBestCustomer();
	
	public String getMostPopularCarType(int year);
}
