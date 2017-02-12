package model;

import java.sql.Date;
import java.util.ArrayList;

public class Packet {
	private int id, pieces;
	private double weight;
	private Date start, end;
	private String source, destination, currentCity, dimensions, service, packaging, status, path;
	private ArrayList<History> history;

	public int getId() {
		return id;
	}

	public int getPieces() {
		return pieces;
	}

	public double getWeight() {
		return weight;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public String getDimensions() {
		return dimensions;
	}

	public String getService() {
		return service;
	}

	public String getPackaging() {
		return packaging;
	}

	public String getStatus() {
		return status;
	}

	public String getPath() {
		return path;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public void setService(String service) {
		this.service = service;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ArrayList<History> getHistory() {
		return history;
	}

	public void setHistory(ArrayList<History> history) {
		this.history = history;
	}
}
