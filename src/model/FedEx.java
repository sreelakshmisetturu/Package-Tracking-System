package model;

import java.util.HashMap;
import java.util.Map;

public class FedEx {
	private String [] cities = {"Northborough,MA", "Edison,NJ", "Pittsburgh,PA", "Allentown,PA", "Martinsburg,WV", "Charlotte,NC", "Atlanta,GA", "Orlando,FL", "Memphis,TN", "Grove City,OH", "Indianapolis,IN", "Detroit,MI", "New Berlin,WI", "Minneapolis,MN", "St. Louis,MO", "Kansas,KS", "Dallas,TX", "Houston,TX", "Denver,CO", "Salt Lake City,UT", "Phoenix,AZ", "Los Angeles,CA", "Chino,CA", "Sacramento,CA", "Seattle,WA"};
	private String [] services = {"Contiguous U.S.", "Alaska and Hawaii", "FedEx Home Delivery", "FedEx Smart Post", "FedEx Ground", "FedEx 2Day", "FedEx Express Saver", "FedEx First Overnight", "FedEx Priority Overnight", "FedEx Standard Overnight", "FedEx Same Day"};
	private String [] packing = {"FedEx Envelope", "FedEx Pak", "FedEx Padded Pak", "FedEx Box Small", "FedEx Box Medium", "FedEx Box Large", "FedEx Box Tube", "FedEx 10Kg Box", "FedEx 25Kg Box"};
	
	@SuppressWarnings("serial")
	private Map<String, Double> packaging = new HashMap<String, Double>()
	{
		{
			put("FedEx Envelope", 1.0);
		    put("FedEx Pak", 2.0);
		    put("FedEx Padded Pak", 5.0);
		    put("FedEx Box Small", 4.0);
		    put("FedEx Box Medium", 8.0);
		    put("FedEx Box Large", 10.0);
		    put("FedEx Box Tube", 18.0);
		    put("FedEx 10Kg Box", 22.0);
		    put("FedEx 25kg Box", 55.0);
		}
	};
	
	public String [] getCities() {
		return cities;
	}

	public String[] getServices() {
		return services;
	}

	public Map<String, Double> getPackaging() {
		return packaging;
	}

	public String[] getPacking() {
		return packing;
	}
}
