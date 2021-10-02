package data_structures;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.Map.Entry;

public class GraphMatrix<T extends Comparable<T>> implements IGraph<T>{
	Map<T,Integer> positionsVertex;
	Map<Integer,T> positionsIndex;
	private boolean bidirectional;
	private int[][] weightMatrix;
	private int size;
	
	public GraphMatrix(boolean bidirectional,int lenght) {
		positionsVertex = new HashMap<>();
		positionsIndex = new HashMap<>();
		this.bidirectional = bidirectional;
		weightMatrix = new int[lenght][lenght];
		size = 0;
	}
	
	@Override
	public boolean addVertex(T element) {
		if(!positionsVertex.containsKey(element)) {
			int i = positionsVertex.size();
			positionsVertex.put(element, i);
			positionsIndex.put(i,element);
			size++;
			return true;
		}
		return false;
	}
	
	@Override
	public void addEdge(T source, T destination) {
		if(!positionsVertex.containsKey(source)) {
			addVertex(source);
		}
		if(!positionsVertex.containsKey(destination)) {
			addVertex(destination);
		}
		int i = positionsVertex.get(source);
		int j = positionsVertex.get(destination);
		weightMatrix[i][j] = 1;
		if(bidirectional) {
			weightMatrix[j][i]	= 1;
		}
		
	}
	
	@Override
	public void addEdge(T source, T destination,int weight) {
		if(!positionsVertex.containsKey(source)) {
			addVertex(source);
		}
		if(!positionsVertex.containsKey(destination)) {
			addVertex(destination);
		}
		int i = positionsVertex.get(source);
		int j = positionsVertex.get(destination);
		weightMatrix[i][j] = weight;
		if(bidirectional) {
			weightMatrix[j][i]	= weight;
		}
	}
	
	
	@Override
	public ArrayList<T> bfs(T initialNode) {
		Map<T,Boolean> visited = new HashMap<>();
		ArrayList<T> array = new ArrayList<>();
		Queue<T> queue = new LinkedList<>();
		queue.add(initialNode);
		while(!queue.isEmpty()) {
			T current = queue.poll();
			if(!visited.containsKey(current)) {
				visited.put(current, true);
				array.add(current);
				int position = positionsVertex.get(current);
				for (int i = 0; i < weightMatrix[position].length; i++) {
					if(weightMatrix[position][i]!=0	) {
						T aux = positionsIndex.get(i);
						if(!visited.containsKey(aux)) {
							queue.add(aux);
						}
					}
					
				}
			}
		}
		return array;
	}

	@Override
	public ArrayList<T> dfs(T initialNode) {
		Map<T,Boolean> visited = new HashMap<>();
		ArrayList<T> array = new ArrayList<>();
		Stack<T> stack = new Stack<>();
		stack.add(initialNode);
		while(!stack.isEmpty()) {
			T current = stack.pop();
			if(!visited.containsKey(current)) {
				visited.put(current, true);
				array.add(current);
				int position = positionsVertex.get(current);
				for (int i = 0; i < weightMatrix[position].length; i++) {
					if(weightMatrix[position][i]!=0	) {
						T aux = positionsIndex.get(i);
						if(!visited.containsKey(aux)) {
							stack.add(aux);
						}
					}
				}
			}
		}
		return array;
	}

	@Override
	public Map<T, T> dijkstra(T initialNode) {
		int position = positionsVertex.get(initialNode);
		int lenght = weightMatrix[position].length;
		Map<T,T> prev = new HashMap<>();
		Map<Integer,Pair<Integer>> pairs = new HashMap<>();
		int[] dist = new int[lenght];
		Queue<Pair<Integer>> queue = new PriorityQueue<>();
		for (int i = 0; i < dist.length; i++) {
			if(i!=position) {
				dist[i] = Integer.MAX_VALUE;
			}
			Pair<Integer> pair = new Pair<>(i,dist[i]);
			pairs.put(i, pair);
			queue.add(pair);
		}
		
		while(!queue.isEmpty()) {
			Pair<Integer> current = queue.poll();
			position = current.getElement();
			for (int i = 0; i < weightMatrix[0].length; i++) {
				if(weightMatrix[position][i]!=0) {
					int alt = dist[position] + weightMatrix[position][i];
					if(alt<dist[i]) {
						dist[i] = alt;
						prev.put(positionsIndex.get(i),positionsIndex.get(position));
						queue.remove(pairs.get(i));
						queue.add(pairs.get(i));
					}
				}
			}
		}
		return prev;
	}

	@Override
	public ArrayList<T> dijkstra(T initialNode, T finalNode) {
		Map<T,T> previous = dijkstra(initialNode);
		boolean pathFounded = false;
		ArrayList<T> path = new ArrayList<>();
		path.add(finalNode);
		
		while(!pathFounded) {
			T currentVertex = previous.get(path.get(path.size()-1));
			path.add(currentVertex);
			
			if(currentVertex.compareTo(initialNode)==0) {
				pathFounded = true;
			}
		}
		
		ArrayList<T> finalPath = new ArrayList<T>();
		
		for(int i=path.size()-1;i>=0;i--) {
			finalPath.add(path.get(i));
		}
		
		return finalPath;
	}

	@Override
	public int[][] floydWarshall() {
		int length = weightMatrix.length;
		int[][] dist = new int[length][length];
		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist[i].length; j++) {
				int value = weightMatrix[i][j];
				if(i==j) {
					dist[i][j] = 0;
				}else {
					dist[i][j] = value!=0?value:Integer.MAX_VALUE;
				}
			}
		}
		for (int k = 0; k <length; k++) {
			for (int i = 0; i < length; i++) {
				for (int j = 0; j < length; j++) {
					if(dist[i][j]> dist[i][k]+ dist[k][j] && (dist[i][k]!=Integer.MAX_VALUE && dist[k][j]!=Integer.MAX_VALUE)) {
						dist[i][j] = dist[i][k] + dist[k][j];
					}
				}
			}
		}
		return dist;
	}
	

	public GraphMatrix<T> kruskal() {

		DisjoinSet<T> disjoinset = new DisjoinSet<>(size);
		GraphMatrix<T> result = new GraphMatrix<>(bidirectional, size);
		PriorityQueue<SuperPair<T>> edges = new PriorityQueue<>();
		for(Entry<T, Integer> entry:positionsVertex.entrySet()) {
			disjoinset.make(entry.getKey());
		}
		for ( int i=0; i < size; i++){
			for ( int j=0; j < size; j++){
				if ( weightMatrix[i][j] != 0 ) {
					T u = positionsIndex.get(i);
					T v = positionsIndex.get(j);
					int weight = weightMatrix[i][j];
					SuperPair<T> pair = new SuperPair<>(u, v, weight);
					edges.add(pair);
				}
			}	
		}
		while(!edges.isEmpty()) {
			SuperPair<T> pair = edges.poll();
			
			T u = pair.getElement();
			T v = pair.getElement2();
			if(disjoinset.find(u)!=disjoinset.find(v)) {
				disjoinset.union(u, v);
				result.addEdge(u, v, pair.getWeight());
			}
			
		}
		return result;
	}
	
	public GraphMatrix<T> prim( ){
		int NNodes = size;
		int i, j, k, x, y;
		boolean[] Reached = new boolean[NNodes];
		int[] predNode = new int[NNodes];
		Reached[0] = true;
		int infinite = Integer.MAX_VALUE;
		int[][] LinkCost = new int[NNodes][NNodes];
		for ( i=0; i < NNodes; i++){
			for ( j=0; j < NNodes; j++){
        	 LinkCost[i][j] = weightMatrix[i][j];
        	 if ( LinkCost[i][j] == 0 )
        		 LinkCost[i][j] = infinite;
			}
		}
		for ( k = 1; k < NNodes; k++ )
		{
			Reached[k] = false;
		}
		predNode[0] = 0;
		for (k = 1; k < NNodes; k++){
			x = y = 0;
			for ( i = 0; i < NNodes; i++ ) {
				for ( j = 0; j < NNodes; j++ ){
					if ( Reached[i] && !Reached[j] && LinkCost[i][j] < LinkCost[x][y] ){
						x = i;
						y = j;
					}
				}
			}
			predNode[y] = x;
    	    Reached[y] = true;
		}
		int[] a= predNode;
		GraphMatrix<T> result = new GraphMatrix<>(bidirectional, NNodes);
		for ( i = 0; i < NNodes; i++ ) {
			if(a[i]!=i) {
				result.addEdge(positionsIndex.get(a[i]), positionsIndex.get(i));
			}
		}
		return result;
	}
	
	
	public Map<T, Integer> getPositionsVertex() {
		return positionsVertex;
	}

	public Map<Integer, T> getPositionsIndex() {
		return positionsIndex;
	}

	public boolean isBidirectional() {
		return bidirectional;
	}

	public int[][] getWeightMatrix() {
		return weightMatrix;
	}

	@Override
	public boolean containsVertex(T element) {
		return positionsVertex.containsKey(element);
	}

	@Override
	public boolean delateVertex(T element) {
		if(positionsVertex.containsKey(element)) {
			int position = positionsVertex.get(element);
			positionsVertex.remove(element);
			positionsIndex.remove(position);
			for (int i = 0; i < weightMatrix.length; i++) {
				weightMatrix[i][position] = 0;
				weightMatrix[position][i] = 0;
			}
			size--;
			return true;
		}
		return false;
	}


}
