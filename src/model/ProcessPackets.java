package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class ProcessPackets implements Runnable {
	String source, destination, status, path, currentCity;
	Date startDate, endDate;
	Connection DbInstance;
	Packet packet;
	Date timeStamp;
	Date defaultDate = new Date(0);
	ArrayList<String> transitCities = new ArrayList<>();
	ArrayList<Double> transitDistance = new ArrayList<>();
	private final String status1 = "In Transit";
	private final String status2 = "Delivered";
	private int  cityPosition = 0, id;
	String [] activity = {"Picked up", "In FedEx  possession", "Arrived at FedEx location", "Departed at FedEx location", "At local FedEx location"};
	public ProcessPackets(Connection DbInstance, Packet packet) {
		super();
		this.DbInstance = DbInstance;
		this.packet = packet;
	}

	@Override
	public void run() {
		movePacket();
	}

	public void movePacket() {
		id = packet.getId();
		source = packet.getSource();
		destination = packet.getDestination();
		currentCity = packet.getCurrentCity();
		path = packet.getPath();
		startDate = packet.getStart();
		endDate = packet.getEnd();
		status = packet.getStatus();
		splitPath(path);
		try {
			cityPosition=transitCities.indexOf(currentCity);
			while (!status.equals("Delivered")) {
				switch (status) {
				case "Package at Warehouse":
					timeStamp = getDate();
					updatePacket(id, timeStamp, defaultDate, status1, "");
					addTravelHistory(id, timeStamp, activity[1], source);
					Thread.sleep(timeToSleep(50));
					timeStamp = getDate();
					addTravelHistory(id, timeStamp, activity[0], source);
					cityPosition++;
					status = status1;
					Thread.sleep(timeToSleep(transitDistance.get(cityPosition)));
					break;
				case "In Transit":
					if (cityPosition != (transitCities.size()-1)) {
						timeStamp = getDate();
						updatePacket(id, defaultDate, defaultDate, "", transitCities.get(cityPosition));
						addTravelHistory(id, timeStamp, activity[2], transitCities.get(cityPosition));
						Thread.sleep(timeToSleep(100));
						addTravelHistory(id, timeStamp, activity[3], transitCities.get(cityPosition));
						cityPosition++;
					} else {
						status = "BeforeDelivered";
					}
					
					Thread.sleep(timeToSleep(transitDistance.get(cityPosition)));					
					break;
				case "BeforeDelivered":
					timeStamp = getDate();
					addTravelHistory(id, timeStamp, activity[4], destination);
					Thread.sleep(timeToSleep(50));
					timeStamp = getDate();
					updatePacket(id, defaultDate, timeStamp, status2, destination);
					addTravelHistory(id, timeStamp, status2, destination);
					status = status2;
					break;
				default:
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Date getDate() {
		java.util.Date today = new java.util.Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		return new java.sql.Timestamp(cal.getTimeInMillis());
	}

	public void splitPath(String path) {
		StringTokenizer pathList = new StringTokenizer(path, "$");

		while (pathList.hasMoreTokens()) {
			String city = pathList.nextToken();
			String distance = pathList.nextToken();
			if (!city.equals("")) {
				transitCities.add(city);
				transitDistance.add(Double.parseDouble(distance));
			}
		}

		for (int i = 0; i < (transitDistance.size()-1); i++) {
			double diff = transitDistance.get(i + 1) - transitDistance.get(i);
			transitDistance.set(i, diff);
		}
	}

	public int timeToSleep(double distance) {
		return (int) (distance * 20);
	}

	public void updatePacket(int id, Date startDate, Date endDate, String status, String city) {
		Statement stmt = null;
		String query = null;
		Date defaultDate1 = new Date(0);

		if (startDate.compareTo(defaultDate1) != 0)
			query = "UPDATE FEDEXPACKAGES SET StartDate = '" + startDate + "', Status = '" + status + "' WHERE ID = "
					+ id + ";";
		else if (endDate.compareTo(defaultDate1) != 0)
			query = "UPDATE FEDEXPACKAGES SET EndDate = '" + endDate + "', Status = '" + status + "', CurrentCity = '"
					+ city + "' WHERE ID = " + id + ";";
		else if (status.equals(""))
			query = "UPDATE FEDEXPACKAGES SET CurrentCity = '" + city + "' WHERE ID = " + id + ";";

		try {
			stmt = DbInstance.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addTravelHistory(int id, Date timeStamp, String activity, String location) {
		Statement stmt = null;

		String query = "INSERT INTO TRAVELHISTORY (ID, TimeStamp, Activity, Location) VALUES " + "(" + id + ", '"
				+ timeStamp + "', '" + activity + "', '" + location + "')";

		try {
			stmt = DbInstance.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
