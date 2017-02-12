package model;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.graph.Graph;
import model.Packet;

public class DBUtil {

	Connection DbInstance;
	private String source, destination, dimension, service, packaging;
	private double weight;
	private int index, pieces;
	private String initialStatus = "Package at Warehouse";
	BufferedReader reader;

	public DBUtil(Connection DbInstance, BufferedReader reader) {
		super();
		this.DbInstance = DbInstance;
		this.reader = reader;
	}

	public void initialize() {
		FedEx fedEx = new FedEx();

		for (int i = 0; i < 10; i++) {
			source = "";
			destination = "";
			dimension = "";
			service = "";
			packaging = "";

			index = getIndex(fedEx.getCities().length);
			source = fedEx.getCities()[index];
			index = getIndex(fedEx.getCities().length);
			destination = fedEx.getCities()[index];

			while (source.equals(destination)) {
				destination = "";
				index = getIndex(fedEx.getCities().length);
				destination = fedEx.getCities()[index];
			}

			dimension = getDimention();
			pieces = getPieces();
			index = getIndex(fedEx.getServices().length);
			service = fedEx.getServices()[index];
			index = getIndex(fedEx.getPacking().length);
			packaging = fedEx.getPacking()[index];
			weight = fedEx.getPackaging().get(packaging);

			// long offset = Timestamp.valueOf("2017-01-23 00:00:00").getTime();
			// long end = Timestamp.valueOf("2017-02-01 00:00:00").getTime();
			// long diff = end - offset + 1;
			// Timestamp startDate = new Timestamp(offset + (long)(Math.random()
			// * diff));

			Graph graph = new Graph();
			graph.createGraph(reader);
			graph.dijkstrasAlgo(source, destination);
			StringBuilder path = graph.printPath(destination);
			Statement stmt = null;

			try {
				stmt = DbInstance.createStatement();
				String query = "INSERT INTO FEDEXPACKAGES (Weight, Dimensions, TotalPieces, Service, Packaging, Source, Destination, CurrentCity, Status, Path) VALUES "
						+ "(" + weight + ", '" + dimension + "', " + pieces + ", '" + service + "', '" + packaging
						+ "', '" + source + "', '" + destination + "', '" + source + "', '" + initialStatus + "', '"
						+ path + "')";
				stmt.executeUpdate(query);
			} catch (SQLException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int getIndex(int maximum) {
		maximum = maximum - 1;
		int randomNum = (int) (Math.random() * maximum);
		return randomNum;
	}

	public String getDimention() {
		int length = (int) (10 * Math.random());
		int breadth = (int) (5 * Math.random());
		int height = (int) (10 * Math.random());
		String dimensions = length + "x" + breadth + "x" + height;

		return dimensions;
	}

	public int getPieces() {
		return (1 + (int) (4 * Math.random()));
	}

	public ArrayList<Packet> getRecords() {
		ArrayList<Packet> records = new ArrayList<>();
		try {
			Statement stmt = DbInstance.createStatement();

			String sql = "SELECT * FROM FEDEXPACKAGES WHERE 1";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String status = rs.getString("Status");
				if (!status.equals("Delivered")) {
					Packet packet = new Packet();
					packet.setId(rs.getInt("ID"));
					packet.setSource(rs.getString("Source"));
					packet.setDestination(rs.getString("Destination"));
					packet.setCurrentCity(rs.getString("CurrentCity"));
					packet.setPath(rs.getString("Path"));
					packet.setStart(rs.getDate("StartDate"));
					packet.setEnd(rs.getDate("EndDate"));
					packet.setStatus(rs.getString("Status"));
					packet.setDimensions(rs.getString("Dimensions"));
					packet.setPieces(rs.getInt("TotalPieces"));
					packet.setWeight(rs.getDouble("Weight"));
					packet.setPackaging(rs.getString("Packaging"));
					packet.setService(rs.getString("Service"));
					records.add(packet);
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return records;
	}
	
	public Packet getHistory(int id) {
		ArrayList<History> travelHistory = new ArrayList<>();
		Packet packet = new Packet();
		
		try {
			Statement stmt = DbInstance.createStatement();
			String sql = "SELECT * FROM FEDEXPACKAGES WHERE ID = " + id;
			String query = "SELECT * FROM TRAVELHISTORY WHERE ID = " + id;
			ResultSet rs2 = stmt.executeQuery(sql);
			while(rs2.next()) {
				packet.setId(rs2.getInt("ID"));
				packet.setSource(rs2.getString("Source"));
				packet.setDestination(rs2.getString("Destination"));
				packet.setCurrentCity(rs2.getString("CurrentCity"));
				packet.setStatus(rs2.getString("Status"));
				packet.setDimensions(rs2.getString("Dimensions"));
				packet.setPieces(rs2.getInt("TotalPieces"));
				packet.setWeight(rs2.getDouble("Weight"));
				packet.setPackaging(rs2.getString("Packaging"));
				packet.setService(rs2.getString("Service"));
			}
			ResultSet rs1 = stmt.executeQuery(query);
			while(rs1.next()) {
				History history = new History();
				history.setActivity(rs1.getString("Activity"));
				history.setLocation(rs1.getString("Location"));
				history.setTimeStamp(rs1.getTimestamp("TimeStamp"));
				travelHistory.add(history);
			}
			packet.setHistory(travelHistory);
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packet;
	}
}