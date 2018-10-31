package session;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nameserver.ICRCNamingService;
import rental.CarType;
import rental.ICarRentalCompany;

public class ManagerSession implements IManagerSession {

	protected ICRCNamingService ns;
	protected String name;
	protected String carRentalName;
	
	public ManagerSession(String name, String carRentalName) throws Exception {
		this.name = name;
		this.carRentalName = carRentalName;
		
		Registry registry = LocateRegistry.getRegistry();
		ns = (ICRCNamingService) registry.lookup("ns");
	}

	@Override
	public void registerCRC(ICarRentalCompany crc, String name) throws Exception {
		ns.addCRC(crc, name);
	}

	@Override
	public void unregisterCRC(String name) throws Exception {
		ns.removeCRC(name);
	}

	@Override
	public ICarRentalCompany getCRC(String name) throws Exception {
		return ns.getCRC(name);
	}

	@Override
	public Collection<String> getCarTypes(String name) throws Exception {
		ICarRentalCompany crc = getCRC(name);
		return crc.getAllCarTypes();
	}

	@Override
	public int getNbReservationCarType(String name, String ct) throws Exception {
		ICarRentalCompany crc = getCRC(name);
		return crc.getNumberOfReservationsForCarType(ct);
	}

	@Override
	public Set<String> getBestCustomers() throws Exception {
//		ICarRentalCompany icrc = getCRC(name);
		HashMap<String,Integer> rentres = new HashMap<String,Integer>();
		for(ICarRentalCompany icrc : ns.getAllCRCs()){
			for(String renter : icrc.getAllNbReservations().keySet()){
				int val = icrc.getAllNbReservations().get(renter);
				if(rentres.containsKey(renter)) rentres.put(renter, rentres.get(renter) + val);
				else rentres.put(renter, val);
			}
		}
		
		HashMap.Entry<String,Integer> max = null;
		Set<String> best = new HashSet<String>();
		
		for(Map.Entry<String,Integer> entry : rentres.entrySet()){
			if(max == null || (Integer) entry.getValue() > (Integer) max.getValue()) {
				max = entry;
				best.clear();
				best.add((String) entry.getKey());
			}
			else if ((Integer) entry.getValue() == (Integer) max.getValue()){
				best.add((String) entry.getKey()); 
			}
		}
		return best;
	}

	@Override
	public CarType getMostPopularCarType(String name, int year) throws Exception {
		ICarRentalCompany crc = getCRC(name);
		return crc.getMostPopularCarTypeInYear(year);
	}
	
	@Override
	public int getNumberOfReservationsBy(String renter) throws Exception{
		int val = 0;
		for(ICarRentalCompany icrc : ns.getAllCRCs()){
			val += icrc.getReservationsByRenter(renter).size();
		}
		return val;
	}
	
	
}
