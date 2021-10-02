package data_structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class GraphList<T extends Comparable<T>> implements IGraph<T> {
	
	private Map<T,VertexADJ<T>> map;
	private ArrayList<T> all;
	private boolean bidirectional;
	private Map<T,Integer> position;
	//hacer que cuando se ingrese un nuevo nodo tambien se le entregue un numero que se guardara en un hashmap
	//hacer lo mismo para la implementacion con matriz de adyacencia
	
	public GraphList(boolean bidirectional) {
		map = new HashMap<>();
		all = new ArrayList<>();
		this.bidirectional = bidirectional;
	}
	
	@Override
	public boolean addVertex(T element) {
		if(!map.containsKey(element)) {
			map.put(element, new VertexADJ<T>(element));
			all.add(element);
			return true;
		}
		return false;
	}
	
	@Override
	public void addEdge(T source,T destination) {
		if(!map.containsKey(source)) {
			addVertex(source);
			all.add(source);
		}
		if(!map.containsKey(destination)) {
			addVertex(destination);
			all.add(destination);
		}
		
		map.get(source).getEdges().add(destination);
		if(bidirectional) {
			map.get(destination).getEdges().add(source);
		}
		
	}
	
	@Override
	public void addEdge(T source,T destination,int weight) {
		if(!map.containsKey(source)) {
			addVertex(source);
		}
		if(!map.containsKey(destination)) {
			addVertex(destination);
		}
		
		map.get(source).getEdges().add(destination);
		map.get(source).getEdgesWeight().put(destination, weight);
		if(bidirectional) {
			map.get(destination).getEdges().add(source);
			map.get(destination).getEdgesWeight().put(source, weight);
		}
		
	}
	
	@Override
	public ArrayList<T> bfs(T initialNode) {
		Map<T,Boolean> visited = new HashMap<>();
		ArrayList<T> array = new ArrayList<>();
		Queue<T> stack = new LinkedList<>();
		stack.add(initialNode);
		while(!stack.isEmpty()) {
			T current = stack.poll();
			if(!visited.containsKey(current)) {
				visited.put(current, true);
				array.add(current);
				VertexADJ<T> cuurentVertex = map.get(current);
				ArrayList<T> edges = cuurentVertex.getEdges();
				for (int i = 0; i < edges.size(); i++) {
					T aux = edges.get(i);
					if(!visited.containsKey(aux)) {
						stack.add(aux);
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
				VertexADJ<T> cuurentVertex = map.get(current);
				ArrayList<T> edges = cuurentVertex.getEdges();
				for (int i = 0; i < edges.size(); i++) {
					T aux = edges.get(i);
					if(!visited.containsKey(aux)) {
						stack.add(aux);
					}
				}
			}
		}
		return array;
	}
	
	@Override
	public Map<T,T> dijkstra(T initialNode) {
		Map<T,Integer> distances = new HashMap<>();
		Map<T,T> previous = new HashMap<>();
		Map<T,Pair<T>> pairs = new HashMap<>();
		PriorityQueue<Pair<T>> pQueue = new PriorityQueue<Pair<T>>(); 
		
		for(Map.Entry<T,VertexADJ<T>> mapElement : map.entrySet()) {
			distances.put((T)mapElement.getKey(),Integer.MAX_VALUE);
			T key = mapElement.getKey();
			Pair<T> pair = new Pair<>(key,distances.get(key));
			pQueue.add(pair);
			pairs.put(key, pair);
		}
		distances.put(initialNode, 0);
		while(!pQueue.isEmpty()) {
			T currentVertex = pQueue.poll().getElement();
			ArrayList<T> currentNeighbors = map.get(currentVertex).getEdges();
			HashMap<T,Integer> currentWeights = (HashMap<T,Integer>) map.get(currentVertex).getEdgesWeight();
			
			for(int i=0;i<currentNeighbors.size();i++) {
				T neighbor = currentNeighbors.get(i);
				int aux = distances.get(currentVertex) + currentWeights.get(neighbor);
				int currentDistance = distances.get(neighbor);
				
				if(aux < currentDistance) {
					Pair<T> currentPair = pairs.get(neighbor);
					distances.remove(neighbor);
					distances.put(neighbor, aux);
					
					previous.remove(neighbor);
					previous.put(neighbor,currentVertex);
					
					pQueue.remove(currentPair);
					currentPair.setWeight(aux);
					pQueue.add(currentPair);
				}
			}
		}
		return previous;
	}
	
	public Map<T,Integer> dijkstraDistance(T[] initialNode) {
		Map<T,Integer> distances = new HashMap<>();
		PriorityQueue<Pair<T>> pQueue = new PriorityQueue<Pair<T>>(); 
		
		for(Map.Entry<T,VertexADJ<T>> mapElement : map.entrySet()) {
			distances.put((T)mapElement.getKey(),Integer.MAX_VALUE);
		}
		for (int i = 0; i < initialNode.length; i++) {
			distances.remove(initialNode[i]);
			distances.put(initialNode[i],0);
			Pair<T> pair = new Pair<>(initialNode[i],0);
			pQueue.add(pair);
		}
		while(!pQueue.isEmpty()) {
			T currentVertex = pQueue.poll().getElement();
			ArrayList<T> currentNeighbors = map.get(currentVertex).getEdges();
			HashMap<T,Integer> currentWeights =  (HashMap<T, Integer>) map.get(currentVertex).getEdgesWeight();
			Map<T,Integer> edgesWeight = map.get(currentVertex).getEdgesWeight();
			for (Map.Entry<T,Integer> entry : edgesWeight.entrySet()) {
				T i = entry.getKey();
				int alt =distances.get(currentVertex)+entry.getValue();
				if(alt<distances.get(i)) {
					distances.remove(i);
					distances.put(i, alt);
					pQueue.add(new Pair<>(i,alt));
				}
			}
	
			for(int i=0;i<currentNeighbors.size();i++) {
				T neighbor = currentNeighbors.get(i);
				int aux = distances.get(currentVertex) + currentWeights.get(neighbor);
				int currentDistance = distances.get(neighbor);
				
				if(aux < currentDistance) {
					distances.remove(neighbor);
					distances.put(neighbor, aux);
					
					Pair<T> pair = new Pair<>(neighbor,aux);
					pQueue.add(pair);
				}
			}
		}
		return distances;
	}
//	public void dijkstra(int[] police) {
//		dist = new int[dist.length];
//		Arrays.fill(dist, Integer.MAX_VALUE);
//		Queue<Pair> queue = new PriorityQueue<>(Collections.reverseOrder());
//		for (int i = 0; i < police.length; i++) {
//			dist[police[i]] = 0;
//			Pair pair = new Pair(police[i],dist[police[i]]);
//			queue.add(pair);
//		}
//		
//		while(!queue.isEmpty()) {
//			Pair current = queue.poll();
//			int position = current.element;
//			Map<Integer,Integer> edgesWeight = map.get(position).edgesWeight;
//			for (Map.Entry<Integer,Integer> entry : edgesWeight.entrySet()) {
//				int i = entry.getKey();
//				int alt =dist[position]+entry.getValue();
//				if(alt<dist[i]) {
//					dist[i] = alt;
//					queue.add(new Pair(i,dist[i]));
//				}
//			}
//		}
//	}
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
		int[][ ] dist = new int[map.size()][map.size()];
		position = new HashMap<>();
		int aux = 0;
		for(Map.Entry<T,VertexADJ<T>> entry:map.entrySet()) {
			T currentElement = entry.getKey();
			position.put(currentElement, aux);
			aux++;
		}
		for(Map.Entry<T,VertexADJ<T>> entry:map.entrySet()) {
			T currentElement = entry.getKey();
			int i = position.get(currentElement);
			VertexADJ<T> vertex = entry.getValue();
			for(Map.Entry<T,Integer> edge : vertex.getEdgesWeight().entrySet()) {
				int j = position.get(edge.getKey());
				int value = edge.getValue();
				dist[i][j] = value;
			}
		}
		for (int i = 0; i < dist.length; i++) {
			for (int j = 0; j < dist[i].length; j++) {
				if(i!=j) {
					if(dist[i][j] ==0) {
						dist[i][j] = Integer.MAX_VALUE;
					}
				}
			}
		}
		int length = position.size();
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
	
	
	public GraphList<T> prim( ){
		int NNodes = map.size();
		
		Map<T, Boolean>  reached = new HashMap<>();
		Map<T,T> predNode = new HashMap<>();
	
		int aux = 0;

		T first = null;
		for(Entry<T, VertexADJ<T>> entry:map.entrySet()) {
			if(aux==0) {
				first = entry.getKey();
				reached.put(entry.getKey(), true);
				predNode.put(entry.getKey(), entry.getKey());
			}else {
				reached.put(entry.getKey(), false);
			}
			aux++;
		}
		T x,y =null;
		for (int k = 1; k < NNodes; k++){
			x = y = first;
			for(Entry<T, VertexADJ<T>> entry:map.entrySet()) {
				Map<T,Integer> values = entry.getValue().getEdgesWeight();
				for(Entry<T,Integer> edge:values.entrySet()) {
					if(reached.get(entry.getKey()) && !reached.get(edge.getKey())
							&& (map.get(x).getEdgesWeight().get(y)==null || 
							(edge.getValue()< map.get(x).getEdgesWeight().get(y)))){
						x = entry.getKey();
						y = edge.getKey();
					}
				}
			}
			predNode.put(y, x);
    	    reached.put(y, true);
		}
		Map<T,T> a = predNode;
		GraphList<T> result = new GraphList<>(bidirectional);
		for(Entry<T, VertexADJ<T>> entry:map.entrySet()){
			result.addEdge(a.get(entry.getKey()),entry.getKey());
		}
		return result;
	}
	
	public GraphList<T> kruskal() {
		//Crear el arbol
		GraphList<T> result = new GraphList<>(bidirectional);
		DisjoinSet<T> disjoinset = new DisjoinSet<>(map.size());
		for(Entry<T, VertexADJ<T>> entry:map.entrySet()) {
			disjoinset.make(entry.getKey());
		}
		Queue<SuperPair<T>> edges = new PriorityQueue<>();
		for(Entry<T, VertexADJ<T>> entry:map.entrySet()) {
			Map<T,Integer> values = entry.getValue().getEdgesWeight();
			for(Entry<T,Integer> edge:values.entrySet()) {
				SuperPair<T> pair = new SuperPair<>(entry.getKey(),edge.getKey(),edge.getValue());
				edges.add(pair);
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
	public Map<T, VertexADJ<T>> getMap() {
		return map;
	}

	public ArrayList<T> getAll() {
		return all;
	}

	public boolean isBidirectional() {
		return bidirectional;
	}

	@Override
	public boolean containsVertex(T element) {
		return map.containsKey(element);
	}

	@Override
	public boolean delateVertex(T element) {
		if(map.containsKey(element)) {
			map.remove(element);
			for(Entry<T,VertexADJ<T>> entry: map.entrySet()) {
				ArrayList<T> array = entry.getValue().getEdges();
				for (int i = 0; i < array.size(); i++) {
					if(array.get(i).equals(element)) {
						array.remove(i);
					}
				}
			}
			return true;
		}
		return false;
	}

	public Map<T,Integer> getPosition() {
		return position;
	}
	

}
