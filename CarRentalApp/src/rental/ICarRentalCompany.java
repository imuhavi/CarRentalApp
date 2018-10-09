package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public interface ICarRentalCompany extends Remote {

	public Collection<CarType> getAllCarTypes() throws RemoteException;
	
	public CarType getCarType(String carTypeName) throws RemoteException;
	
	public boolean isAvailable(String carTypeName, Date start, Date end) throws RemoteException;
	
	public Set<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException;
	
	public Quote createQuote(ReservationConstraints constraints, String guest) throws RemoteException, ReservationException;
	
	public void cancelReservation(Reservation res) throws RemoteException;
	
}
