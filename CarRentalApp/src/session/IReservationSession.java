package session;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import rental.*;

public interface IReservationSession extends Remote {
	public Set<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException;
	
	public Quote createQuote(ReservationConstraints constraints, String guest) throws RemoteException, ReservationException;
	
	public Reservation confirmQuote(Quote quote) throws RemoteException, ReservationException;
	
	public List<Quote> getCurrentQuotes() throws RemoteException;
	
	public String getCheapestCarType(String region, Date start, Date end) throws RemoteException;
}
