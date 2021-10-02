package data_structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VertexADJ<T extends Comparable<T>> extends Vertex<T> {

	private ArrayList<T> edges;
	private Map<T,Integer> edgesWeight;
	
	public VertexADJ(T name) {
		super(name);
		edges= new ArrayList<T>();
		edgesWeight= new HashMap<>();
	}

	public ArrayList<T> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<T> edges) {
		this.edges = edges;
	}

	public Map<T,Integer> getEdgesWeight() {
		return edgesWeight;
	}


}
