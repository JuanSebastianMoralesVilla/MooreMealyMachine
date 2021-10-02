package data_structures;

public class Vertex<T extends Comparable<T>> {
	
	private T vertexName;
	public Vertex(T name) {
		this.vertexName = name;
	}
	public T getVertexName() {
		return vertexName;
	}
	public void setVertexName(T vertexName) {
		this.vertexName = vertexName;
	}
	
}
