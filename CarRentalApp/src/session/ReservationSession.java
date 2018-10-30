package session;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

public class ReservationSession implements IReservationSession {

	@Override
	public Set<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quote createQuote(ReservationConstraints constraints, String guest)
			throws RemoteException, ReservationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation confirmQuote(Quote quote) throws RemoteException, ReservationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Quote> getCurrentQuotes() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCheapestCarType(String region, Date start, Date end) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
