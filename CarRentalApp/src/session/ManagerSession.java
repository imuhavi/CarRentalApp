package session;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nameserver.ICRCNamingService;
import rental.ICarRentalCompany;

public class ManagerSession implements IManagerSession {

	ICRCNamingService ns;
	
	public ManagerSession() throws Exception {
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
	public String getBestCustomer() throws Exception {
		HashMap<String,Integer> rentres = new HashMap<String,Integer>();
		for(ICarRentalCompany icrc : ns.getAllCRCs()){
			for(String renter : icrc.getAllnBReservations().keySet()){
				int val = icrc.getAllnBReservations().get(renter);
				if(rentres.containsKey(renter)) rentres.put(renter, rentres.get(renter) + val);
				else rentres.put(renter, val);
			}
		}
		
		HashMap.Entry max = null;
		for(Map.Entry entry : rentres.entrySet()){
			if(max == null || (Integer) entry.getValue() > (Integer) max.getValue()) max = entry;
		}
		return (String) max.getKey();
	}

	@Override
	public String getMostPopularCarType(String name, int year) throws Exception {
		ICarRentalCompany crc = getCRC(name);
		return crc.getMostPopularCarTypeInYear(year);
	}
	
	
}
