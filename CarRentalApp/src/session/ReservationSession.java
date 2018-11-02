package session;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nameserver.ICRCNamingService;
import rental.CarType;
import rental.ICarRentalCompany;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

public class ReservationSession implements IReservationSession {

	protected String name;
	protected ICRCNamingService ns;
	protected List<Quote> quotes;
	
	public ReservationSession(String name) throws Exception{
		this.name = name;
		this.quotes = new ArrayList<Quote>();
		
		Registry registry = LocateRegistry.getRegistry();
		ns = (ICRCNamingService) registry.lookup("ns");
	}
	
	@Override
	public Set<CarType> getAvailableCarTypes(Date start, Date end) throws Exception {
		Set<CarType> available = new HashSet<CarType>();
		for(ICarRentalCompany icrc : ns.getAllCRCs()){
			available.addAll(icrc.getAvailableCarTypes(start, end));
		}
		return available;
	}

	@Override
	public Quote createQuote(String name, Date start, Date end, String carType, String region, String guest) throws Exception {
		ReservationConstraints constraints = new ReservationConstraints(start, end, carType, region);
		Quote q;
		for(ICarRentalCompany icrc : ns.getAllCRCs()){
			try{
				q = icrc.createQuote(constraints, guest);
				quotes.add(q);
				return q;
			} catch (Exception e){
				continue;
			}
		}
		throw new ReservationException("No match found.");
	}

	@Override
	public List<Reservation> confirmQuotes() throws Exception {
		List<Reservation> res = new ArrayList<Reservation>();
        List<Quote> remove = new ArrayList<Quote>();
        try{
            for(Quote q: quotes){
                if (q.getCarRenter().equals(name)){
                    remove.add(q);
                    ICarRentalCompany crc = ns.getCRC(q.getRentalCompany());
                    res.add(crc.confirmQuote(q));
                }
            }
        }catch (ReservationException e){
            for (Reservation rem: res){
            	ICarRentalCompany crc = ns.getCRC(rem.getRentalCompany());
                crc.cancelReservation(rem);
            }
            for (Quote q: remove){
                quotes.remove(q);
            }
            throw e;
        }
        for (Quote q: remove){
            quotes.remove(q);
        }
        return res;
	}

	@Override
	public List<Quote> getCurrentQuotes() throws RemoteException {
		return quotes;
	}

	@Override
	public String getCheapestCarType(String region, Date start, Date end) throws Exception {
		String ct = null;
		double min = Double.MAX_VALUE;
		for(ICarRentalCompany icrc : ns.getAllCRCs()){
			HashMap<String,Double> pair = icrc.getCheapestCarTypeAvailable(region, start, end);
			for(String key : pair.keySet()){
				if(min > pair.get(key) || key == null) {
					min = pair.get(key);
					ct = key;
				}
			}
		}
		return ct;
	}

}
