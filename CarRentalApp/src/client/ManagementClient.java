package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nameserver.ICRCNamingService;
import rental.CarType;
import rental.ICarRentalCompany;
import rental.Reservation;
import rental.ReservationConstraints;
import session.ManagerSession;
import session.ReservationSession;

public class ManagementClient extends AbstractTestManagement<ReservationSession, ManagerSession> {

	ICRCNamingService ns;
	
	public static void main(String args[]) throws Exception{
		System.setSecurityManager(null);
		
		ManagementClient manclient = new ManagementClient("trips");
		
		manclient.run();
	}
	
	public ManagementClient(String scriptFile) throws Exception {
		super(scriptFile);
		Registry registry = LocateRegistry.getRegistry();
		ns = (ICRCNamingService) registry.lookup("ns");
	}

	@Override
	protected Set<String> getBestClients(ManagerSession ms) throws Exception {
		return ms.getBestCustomers();
	}

	@Override
	protected String getCheapestCarType(ReservationSession session, Date start, Date end, String region)
			throws Exception {
		return session.getCheapestCarType(region, start, end);	
	}

	@Override
	protected CarType getMostPopularCarTypeIn(ManagerSession ms, String carRentalCompanyName, int year)
			throws Exception {
		return ms.getMostPopularCarType(carRentalCompanyName, year);
	}

	@Override
	protected ReservationSession getNewReservationSession(String name) throws Exception {
		return new ReservationSession(name);
	}

	@Override
	protected ManagerSession getNewManagerSession(String name, String carRentalName) throws Exception {
		return new ManagerSession(name, carRentalName);
	}

	@Override
	protected void checkForAvailableCarTypes(ReservationSession session, Date start, Date end) throws Exception {
		for(CarType ct : session.getAvailableCarTypes(start, end)){
			System.out.println(ct.getName());
		}
	}

	@Override
	protected void addQuoteToSession(ReservationSession session, String name, Date start, Date end, String carType,
			String region) throws Exception {
		session.createQuote(name, start, end, carType, region, name);
	}

	@Override
	protected List<Reservation> confirmQuotes(ReservationSession session, String name) throws Exception {
		return session.confirmQuotes();
	}

	@Override
	protected int getNumberOfReservationsBy(ManagerSession ms, String clientName) throws Exception {
		return ms.getNumberOfReservationsBy(clientName);
	}

	@Override
	protected int getNumberOfReservationsForCarType(ManagerSession ms, String carRentalName, String carType)
			throws Exception {
		return ms.getNbReservationCarType(carRentalName, carType);
	}

}
