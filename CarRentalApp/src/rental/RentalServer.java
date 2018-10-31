package rental;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import nameserver.CRCNamingService;
import nameserver.ICRCNamingService;

public class RentalServer {
	
	public static void main(String[] args) throws ReservationException,
			NumberFormatException, IOException, AlreadyBoundException, NotBoundException {
		System.setSecurityManager(null);
		
		ICRCNamingService incrc = (ICRCNamingService) new CRCNamingService();
		
		ICRCNamingService namingstub = (ICRCNamingService) UnicastRemoteObject.exportObject(incrc,0);
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("ns", namingstub);
		
		ICRCNamingService ns = (ICRCNamingService) registry.lookup("ns");
		//give databasename
		CrcData data1 = loadData("Hertz.csv");
		CarRentalCompany crc1 = new CarRentalCompany(data1.name, data1.regions, data1.cars);
		
		ICarRentalCompany stub1 = (ICarRentalCompany) UnicastRemoteObject.exportObject(crc1,0);
		ns.addCRC(stub1, data1.name);
		
		CrcData data2 = loadData("Dockx.csv");
		CarRentalCompany crc2 = new CarRentalCompany(data2.name, data2.regions, data2.cars);
		
		ICarRentalCompany stub2 = (ICarRentalCompany) UnicastRemoteObject.exportObject(crc2,0);
		ns.addCRC(stub2, data2.name);
	}

	public static CrcData loadData(String datafile)
			throws ReservationException, NumberFormatException, IOException {

		CrcData out = new CrcData();
		int nextuid = 0;

		// open file
		BufferedReader in = new BufferedReader(new FileReader(datafile));
		StringTokenizer csvReader;
		
		try {
			// while next line exists
			while (in.ready()) {
				String line = in.readLine();
				
				if (line.startsWith("#")) {
					// comment -> skip					
				} else if (line.startsWith("-")) {
					csvReader = new StringTokenizer(line.substring(1), ",");
					out.name = csvReader.nextToken();
					out.regions = Arrays.asList(csvReader.nextToken().split(":"));
				} else {
					// tokenize on ,
					csvReader = new StringTokenizer(line, ",");
					// create new car type from first 5 fields
					CarType type = new CarType(csvReader.nextToken(),
							Integer.parseInt(csvReader.nextToken()),
							Float.parseFloat(csvReader.nextToken()),
							Double.parseDouble(csvReader.nextToken()),
							Boolean.parseBoolean(csvReader.nextToken()));
					System.out.println(type);
					// create N new cars with given type, where N is the 5th field
					for (int i = Integer.parseInt(csvReader.nextToken()); i > 0; i--) {
						out.cars.add(new Car(nextuid++, type));
					}
				}
			}
		} finally {
			in.close();
		}

		return out;
	}
	
	static class CrcData {
		public List<Car> cars = new LinkedList<Car>();
		public String name;
		public List<String> regions =  new LinkedList<String>();
	}

}
