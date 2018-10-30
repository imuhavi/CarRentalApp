package nameserver;

import java.rmi.Remote;

import rental.ICarRentalCompany;

public interface ICRCNamingService extends Remote {

	public void addCRC(ICarRentalCompany crc, String name);
	
	public void removeCRC(String name);
	
	public ICarRentalCompany getCRC(String name);
	
	
}
