package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public interface ICarRentalCompany extends Remote {

	public Collection<String> getAllCarTypes() throws RemoteException;
	
	public CarType getCarType(String carTypeName) throws RemoteException;
	
	public boolean isAvailable(String carTypeName, Date start, Date end) throws RemoteException;
	
	public Set<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException;
	
	public Quote createQuote(ReservationConstraints constraints, String guest) throws RemoteException, ReservationException;
	
	public Reservation confirmQuote(Quote quote) throws RemoteException, ReservationException;

	public void cancelReservation(Reservation res) throws RemoteException;
	
	public Collection<Car> getAllCars() throws Exception;
	
	public List<Reservation> getReservationsByRenter(String clientName) throws RemoteException;
	
	public int getNumberOfReservationsForCarType(String carType) throws RemoteException;
	
	public HashMap<String,Integer> getAllNbReservations() throws RemoteException;
	
	public CarType getMostPopularCarTypeInYear(int year) throws RemoteException, Exception;
	
	public HashMap<String, Double> getCheapestCarTypeAvailable(String region, Date start, Date end) throws RemoteException;
	
}
