package session;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import rental.*;

public interface IReservationSession extends Remote {
	public Set<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException, Exception;
	
	public Quote createQuote(ReservationConstraints constraints, String guest) throws RemoteException, ReservationException, Exception;
	
	public List<Reservation> confirmQuotes() throws RemoteException, ReservationException, Exception;
	
	public List<Quote> getCurrentQuotes() throws RemoteException;
	
	public String getCheapestCarType(String region, Date start, Date end) throws RemoteException, Exception;
}
