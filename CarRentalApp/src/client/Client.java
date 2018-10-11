package client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rental.Car;
import rental.CarType;
import rental.ICarRentalCompany;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;

public class Client extends AbstractTestBooking {
	
	/********
	 * MAIN *
	 ********/
	
	static ICarRentalCompany crc;
	
	public static void main(String[] args) throws Exception {
		
		String carRentalCompanyName = "Hertz";
		
		System.setSecurityManager(null);
		
		// An example reservation scenario on car rental company 'Hertz' would be...
		Client client = new Client("simpleTrips", carRentalCompanyName);
		client.run();
	}
	
	/***************
	 * CONSTRUCTOR 
	 * @throws Exception *
	 ***************/
	
	public Client(String scriptFile, String carRentalCompanyName) throws Exception {
		super(scriptFile);
		
		Registry registry = LocateRegistry.getRegistry();
		crc = (ICarRentalCompany) registry.lookup(carRentalCompanyName);
		
//		throw new UnsupportedOperationException("TODO");
	}
	
	/**
	 * Check which car types are available in the given period
	 * and print this list of car types.
	 *
	 * @param 	start
	 * 			start time of the period
	 * @param 	end
	 * 			end time of the period
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected void checkForAvailableCarTypes(Date start, Date end) throws Exception {
		for(CarType carType : crc.getAvailableCarTypes(start, end)){
			System.out.println(carType.getName());
		}
//		throw new UnsupportedOperationException("TODO");
	}

	/**
	 * Retrieve a quote for a given car type (tentative reservation).
	 * 
	 * @param	clientName 
	 * 			name of the client 
	 * @param 	start 
	 * 			start time for the quote
	 * @param 	end 
	 * 			end time for the quote
	 * @param 	carType 
	 * 			type of car to be reserved
	 * @param 	region
	 * 			region in which car must be available
	 * @return	the newly created quote
	 *  
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected Quote createQuote(String clientName, Date start, Date end,
			String carType, String region) throws Exception {

		ReservationConstraints constraints = new ReservationConstraints(start, end, carType, region);
		return crc.createQuote(constraints, clientName);
//		throw new UnsupportedOperationException("TODO");
	}

	/**
	 * Confirm the given quote to receive a final reservation of a car.
	 * 
	 * @param 	quote 
	 * 			the quote to be confirmed
	 * @return	the final reservation of a car
	 * 
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected Reservation confirmQuote(Quote quote) throws Exception {
		return crc.confirmQuote(quote);
//		throw new UnsupportedOperationException("TODO");
	}
	
	/**
	 * Get all reservations made by the given client.
	 *
	 * @param 	clientName
	 * 			name of the client
	 * @return	the list of reservations of the given client
	 * 
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected List<Reservation> getReservationsByRenter(String clientName) throws Exception {
		List<Reservation> reservations = new ArrayList<Reservation>();
		for (Car car: crc.getAllCars()){
			for (Reservation reservation: car.getAllReservations()){
				if (reservation.getCarRenter().equals(clientName)){
					System.out.println("Cartype: " + reservation.getCarType() + " Car-ID: " + reservation.getCarId() + " Period: " + reservation.getStartDate() + " - " + reservation.getEndDate() + " Rentalprice: " + reservation.getRentalPrice() + ".");
					reservations.add(reservation);
				}
			}
		}
		return reservations;
//		throw new UnsupportedOperationException("TODO");
	}

	/**
	 * Get the number of reservations for a particular car type.
	 * 
	 * @param 	carType 
	 * 			name of the car type
	 * @return 	number of reservations for the given car type
	 * 
	 * @throws 	Exception
	 * 			if things go wrong, throw exception
	 */
	@Override
	protected int getNumberOfReservationsForCarType(String carType) throws Exception {
		int i = 0;
		for (Car car: crc.getAllCars()){
			if (car.getType().equals(carType)){
				i += car.getAllReservations().size();
			}
		}
		return i;
//		throw new UnsupportedOperationException("TODO");
	}
}