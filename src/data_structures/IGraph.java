package data_structures;

import java.util.ArrayList;
import java.util.Map;

public interface IGraph <T extends Comparable<T>> {
	
	public boolean addVertex(T element);
	public void addEdge(T source, T destination);
	public void addEdge(T source, T destination,int weight);
	public boolean delateVertex(T element);
	public boolean containsVertex(T element);
	public ArrayList<T> bfs(T initialNode);
	public ArrayList<T> dfs(T initialNode);
	public Map<T,T> dijkstra(T initialNode);
	public ArrayList<T> dijkstra(T initialNode, T finalNode);
	public int[][] floydWarshall(); 
}
