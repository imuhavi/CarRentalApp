package session;

import java.util.List;

import rental.ICarRentalCompany;

public class ManagerSession implements IManagerSession {

	public ManagerSession() {
		
	}

	@Override
	public void registerCRC(ICarRentalCompany crc, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterCRC(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ICarRentalCompany getCRC(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCarTypes(String crc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNbReservationCarType(String crc) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getBestCustomer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMostPopularCarType(int year) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
