package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Display implements Runnable {
	Connection con;
	int id;
	
	public Display(Connection con, int id){
		this.con = con;
		this.id = id;
	}
	
	@Override
	public void run() {
		try {
			Statement stmt = con.createStatement();
			System.out.println("here");
			String sql = "SELECT * FROM FEDEXPACKAGES WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				System.out.println("here2");
				System.out.println("Your packet details are below:");
				System.out.println("\tTracking Id:\t"+rs.getInt("ID"));
				System.out.println("\tSource:\t"+rs.getString("Source"));
				System.out.println("\tDestination:\t"+rs.getString("Destination"));
				System.out.println("\tCurrent City:\t"+rs.getString("CurrentCity"));
				System.out.println("\tStatus:\t"+rs.getString("Status"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
