package rental;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CarRentalCompany implements ICarRentalCompany{

	private static Logger logger = Logger.getLogger(CarRentalCompany.class.getName());
	
	private List<String> regions;
	private String name;
	private List<Car> cars;
	private Map<String,CarType> carTypes = new HashMap<String, CarType>();

	/***************
	 * CONSTRUCTOR *
	 ***************/

	public CarRentalCompany(String name, List<String> regions, List<Car> cars) {
		logger.log(Level.INFO, "<{0}> Car Rental Company {0} starting up...", name);
		setName(name);
		this.cars = cars;
		setRegions(regions);
		for(Car car:cars)
			carTypes.put(car.getType().getName(), car.getType());
		logger.log(Level.INFO, this.toString());
	}

	/********
	 * NAME *
	 ********/

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

    /***********
     * Regions *
     **********/
    private void setRegions(List<String> regions) {
        this.regions = regions;
    }
    
    public List<String> getRegions() {
        return this.regions;
    }
    
    public boolean hasRegion(String region) {
        return this.regions.contains(region);
    }
	
	/*************
	 * CAR TYPES *
	 *************/

	public Collection<String> getAllCarTypes() {
		return carTypes.keySet();
	}
	
	public CarType getCarType(String carTypeName) {
		if(carTypes.containsKey(carTypeName))
			return carTypes.get(carTypeName);
		throw new IllegalArgumentException("<" + carTypeName + "> No car type of name " + carTypeName);
	}
	
	// mark
	public boolean isAvailable(String carTypeName, Date start, Date end) {
		logger.log(Level.INFO, "<{0}> Checking availability for car type {1}", new Object[]{name, carTypeName});
		if(carTypes.containsKey(carTypeName)) {
			return getAvailableCarTypes(start, end).contains(carTypes.get(carTypeName));
		} else {
			throw new IllegalArgumentException("<" + carTypeName + "> No car type of name " + carTypeName);
		}
	}
	
	public Set<CarType> getAvailableCarTypes(Date start, Date end) {
		Set<CarType> availableCarTypes = new HashSet<CarType>();
		for (Car car : cars) {
			if (car.isAvailable(start, end)) {
				availableCarTypes.add(car.getType());
			}
		}
		return availableCarTypes;
	}
	
	public int getNumberOfReservationsForCarType(String carType){
		int i = 0;
		for (Car car: getAllCars()){
			if (car.getType().getName().equals(carType)){
				i += car.getAllReservations().size();
			}
		}
		return i;
	}
	
	@SuppressWarnings("deprecation")
	public CarType getMostPopularCarTypeInYear(int year) throws Exception {
		HashMap<CarType,Integer> cartypesres = new HashMap<CarType,Integer>();
		for (Car car : getAllCars()){
			CarType ct = car.getType();
			for(Reservation res : car.getAllReservations()){
				Date start = res.getStartDate();
				if(start.after(new Date(year-1900, 0, 1)) && start.before(new Date(year-1900, 12, 31))){
					if(cartypesres.containsKey(ct)) cartypesres.put(ct, cartypesres.get(ct) + 1);
					else cartypesres.put(ct , 1);
				}
			}
		}
		
//		HashMap<CarType,Integer> max = new HashMap<CarType,Integer>();
//		int maxValue = 0;
		Map.Entry<CarType, Integer> max = null;
		for(Map.Entry<CarType,Integer> entry : cartypesres.entrySet()){
//			is leeg...
			if(max == null || entry.getValue() > max.getValue()) {
//				max.clear();
				max = entry;
//				max.put(entry.getKey(), entry.getValue());
			}
//			else if (entry.getValue() == maxValue){
//				max.put(entry.getKey(), entry.getValue());
//			}
		}
		/*
		if(max.isEmpty()){
			throw new Exception("No cars reserved in that year.");
		}
		*/
		return (CarType) max.getKey();
	}
	
	public HashMap<String,Double> getCheapestCarTypeAvailable(String region, Date start, Date end){
		HashMap<String,Double> cheapest = new HashMap<String,Double>();
		double min = Double.MAX_VALUE;
		if(getRegions().contains(region)){
			for(Car car : getAllCars()){
				if(car.isAvailable(start, end)) {
					if(min > car.getType().getRentalPricePerDay() || cheapest.isEmpty()) {
						min = car.getType().getRentalPricePerDay();
						cheapest.clear();
						cheapest.put(car.getType().getName(), car.getType().getRentalPricePerDay());
					}
				}
			}
		}
		return cheapest;
	}
	
	/*********
	 * CARS *
	 *********/
	
	private Car getCar(int uid) {
		for (Car car : cars) {
			if (car.getId() == uid)
				return car;
		}
		throw new IllegalArgumentException("<" + name + "> No car with uid " + uid);
	}
	
	private List<Car> getAvailableCars(String carType, Date start, Date end) {
		List<Car> availableCars = new LinkedList<Car>();
		for (Car car : cars) {
			if (car.getType().getName().equals(carType) && car.isAvailable(start, end)) {
				availableCars.add(car);
			}
		}
		return availableCars;
	}

	/****************
	 * RESERVATIONS *
	 ****************/

	public Quote createQuote(ReservationConstraints constraints, String client)
			throws ReservationException {
		logger.log(Level.INFO, "<{0}> Creating tentative reservation for {1} with constraints {2}", 
                        new Object[]{name, client, constraints.toString()});
		
				
		if(!regions.contains(constraints.getRegion()) || !isAvailable(constraints.getCarType(), constraints.getStartDate(), constraints.getEndDate()))
			throw new ReservationException("<" + name
				+ "> No cars available to satisfy the given constraints.");

		CarType type = getCarType(constraints.getCarType());
		
		double price = calculateRentalPrice(type.getRentalPricePerDay(),constraints.getStartDate(), constraints.getEndDate());
		
		return new Quote(client, constraints.getStartDate(), constraints.getEndDate(), getName(), constraints.getCarType(), price);
	}

	// Implementation can be subject to different pricing strategies
	private double calculateRentalPrice(double rentalPricePerDay, Date start, Date end) {
		return rentalPricePerDay * Math.ceil((end.getTime() - start.getTime())
						/ (1000 * 60 * 60 * 24D));
	}

	public Reservation confirmQuote(Quote quote) throws ReservationException {
		logger.log(Level.INFO, "<{0}> Reservation of {1}", new Object[]{name, quote.toString()});
		List<Car> availableCars = getAvailableCars(quote.getCarType(), quote.getStartDate(), quote.getEndDate());
		if(availableCars.isEmpty())
			throw new ReservationException("Reservation failed, all cars of type " + quote.getCarType()
	                + " are unavailable from " + quote.getStartDate() + " to " + quote.getEndDate());
		Car car = availableCars.get((int)(Math.random()*availableCars.size()));
		
		Reservation res = new Reservation(quote, car.getId());
		car.addReservation(res);
		return res;
	}

	public void cancelReservation(Reservation res) {
		logger.log(Level.INFO, "<{0}> Cancelling reservation {1}", new Object[]{name, res.toString()});
		getCar(res.getCarId()).removeReservation(res);
	}
	
	public List<Reservation> getReservationsByRenter(String clientName){
		List<Reservation> reservations = new ArrayList<Reservation>();
		for (Car car: getAllCars()){
			for (Reservation reservation: car.getAllReservations()){
				if (reservation.getCarRenter().equals(clientName)){
					reservations.add(reservation);
				}
			}
		}
		return reservations;
	}
	
	public HashMap<String,Integer> getAllNbReservations() {
		HashMap<String,Integer> rentres = new HashMap<String,Integer>();
		for(Car car : getAllCars()){
			for(Reservation res : car.getAllReservations()){
				String renter = res.getCarRenter();
				int val = 0;
				if(rentres.get(renter) != null) val = rentres.get(renter);
				rentres.put(renter, val+1);
			}
		}
		return rentres;
	}
	
	@Override
	public String toString() {
		return String.format("<%s> CRC is active in regions %s and serving with %d car types", name, listToString(regions), carTypes.size());
	}
	
	private static String listToString(List<? extends Object> input) {
		StringBuilder out = new StringBuilder();
		for (int i=0; i < input.size(); i++) {
			if (i == input.size()-1) {
				out.append(input.get(i).toString());
			} else {
				out.append(input.get(i).toString()+", ");
			}
		}
		return out.toString();
	}
	
	/*************
	 * CARS *
	 *************/

	public Collection<Car> getAllCars() {
		return cars;
	}
	
}