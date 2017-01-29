package model.graph;

public class Edge {

	public Vertex source, destination;
	public boolean status;
	public double cost;

	public Edge(Vertex source, Vertex destination, double cost, boolean status) {
		this.source = source;
		this.destination = destination;
		this.status = status;
		this.cost = cost;
	}

	public Edge() {
	}

	public String getDestination() {
		return destination.getName();
	}

	public Vertex getDestVertex() {
		return destination;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
}