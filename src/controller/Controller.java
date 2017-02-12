package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DBUtil;
import model.ProcessPackets;
import model.dbConnection.DBConnection;
import model.Packet;

import java.sql.Connection;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection connection = null;
	private static int threadPoolSize;
	private static ArrayList<Packet> packets = new ArrayList<>();

    public Controller() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ArrayIndexOutOfBoundsException {
		doGet(request, response);
		HttpSession session = request.getSession();
		
		//FileReader reader = new FileReader(isr);
		String filename = "/WEB-INF/network.txt";
        ServletContext context = getServletContext();
        InputStream is = context.getResourceAsStream(filename);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		String id=request.getParameter("action");
		if(id.equals("initiate")){
			//Connecting to database
			
			DBConnection connect = new DBConnection();
			connection = connect.ConnectDB();
			session.setAttribute("connection", connection);
			
			//Initializing the database
			DBUtil util = new DBUtil(connection, reader);
			util.initialize();
			
			packets = util.getRecords();
			threadPoolSize = packets.size();
			
			//Creating threadPool to process all the packets at the same time 
			ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
			for(int i = 0; i< threadPoolSize; i++){
				Runnable process = new ProcessPackets(connection, packets.get(i));
				executor.execute(process);
			}
			RequestDispatcher rd=request.getRequestDispatcher("/home.jsp");
			rd.forward(request, response);
		}
		try{
			if(id.equals("track")){
				//System.out.println("reached");
				String trid= request.getParameter("ID");
				request.setAttribute("ID", trid);
				int trackid=Integer.parseInt(trid);
				Connection conn=(Connection) session.getAttribute("connection");
				DBUtil util = new DBUtil(conn, reader);
				Packet packet = util.getHistory(trackid);
				Date Start= packet.getHistory().get(0).getTimeStamp();
				Date end=packet.getHistory().get(packet.getHistory().size()-1).getTimeStamp();
				request.setAttribute("start", Start);
				request.setAttribute("subscribed",false);
				request.setAttribute("end", end);
				System.out.println("from servlet "+packet.getCurrentCity());
				request.setAttribute("packet", packet);
				request.setAttribute("packetHistory", packet.getHistory());
				RequestDispatcher rd=request.getRequestDispatcher("/history.jsp");
				rd.forward(request, response);
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("here");
			request.setAttribute("subscribed",true);
			RequestDispatcher rd=request.getRequestDispatcher("/home.jsp");
			rd.forward(request, response);
		}
	}
}