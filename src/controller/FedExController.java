package controller;

import java.sql.Connection;
import java.sql.SQLException;

import model.DBUtil;
import model.Display;
import model.Packet;
import model.ProcessPackets;
import model.dbConnection.DBConnection;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FedExController {
	private static Connection connection = null;
	private static int threadPoolSize;
	private static ArrayList<Packet> packets = new ArrayList<>();

	@SuppressWarnings("resource")
	public static void main(String[] args) throws SQLException {
		//Connecting to database
		DBConnection connect = new DBConnection();
		connection = connect.ConnectDB();
		
		//Initializing the database
		DBUtil util = new DBUtil(connection);
		util.initialize();
		
		packets = util.getRecords();
		threadPoolSize = packets.size();
		
		//Creating threadPool to process all the packets at the same time 
		ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
		for(int i = 0; i< threadPoolSize; i++){
			Runnable process = new ProcessPackets(connection, packets.get(i));
			executor.execute(process);
		}
		//executor.shutdown();
		
		while(true){
			Scanner in = new Scanner(System.in);
			System.out.print("Enter tracking ID:");
			int id = Integer.parseInt(in.nextLine());
			Display show = new Display(connection, id);
			Thread t = new Thread(show);
			t.start();
		}
	}
}
