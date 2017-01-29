package model.graph;

import java.util.LinkedList;
import java.util.List;

public class Vertex {

	private String name; // Vertex name
	public boolean status;
	public List<Edge> adjacent;
	public double dist;
	public Vertex prev;
	public int handle;
	private String color;

	public Vertex(String nm, boolean status) {
		name = nm;
		this.status = status;
		adjacent = new LinkedList<Edge>();
	}

	public Vertex() {
	}

	public void reset() {
		dist = Graph.INFINITY;
		prev = null;
	}

	public String getName() {
		return name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getHandle() {
		return handle;
	}

	public void setHandle(int handle) {
		this.handle = handle;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
