package data_structures;

import java.util.*;

import data_structures.*;

public class GraphListSimple<E> {
	private HashMap<E, VertexNew<E>> vertices;
	private HashMap<E, ArrayList<Edge<E>>> adjacencyLists;
	private boolean isDirected;
	private VertexNew<E> lastSrc;
	
	public AdjacencyListGraph(boolean isDirected) {
		this.isDirected = isDirected;
		vertices = new HashMap<>();
		adjacencyLists = new HashMap<>();
	}
	
	public boolean insertVertex(E e) {
		if(!containsVertex(e)) {
			vertices.put(e, new VertexNew<E>(e));
			adjacencyLists.put(e, new ArrayList<>());
			return true;
		}
		return false;
	}

	public boolean deleteVertex(E sk) {
		if(containsVertex(sk)) {
			vertices.remove(sk); //remove the vertex itself
			vertices.forEach((E t, VertexNew<E> u) -> {
				ArrayList<Edge<E>> toremove = new ArrayList<>();
				for(Edge<E> ale : adjacencyLists.get(t)) {
					if(ale.getDst().equals(sk)) {
						toremove.add(ale);
					}
				}
				adjacencyLists.get(t).removeAll(toremove);
			});

			return true;
		}
		return false;
	}

	public void link(E src, E dst, int weight) {
		insertVertex(src); //Inserts src if not currently in the graph
		insertVertex(dst); //Inserts dst if not currently in the graph
		Edge<E> newedge1 = new Edge<>(src, dst, weight);

		ArrayList<Edge<E>> sEdges = adjacencyLists.get(src);
		sEdges.remove(newedge1); //if the edge already exists remove it
		sEdges.add(newedge1);
		if(!isDirected) { //Add the additional edge if this graph is undirected
			Edge<E> newedge2 = new Edge<>(dst, src, weight);

			ArrayList<Edge<E>> dEdges = adjacencyLists.get(dst);
			dEdges.remove(newedge2); //if the edge already exists remove it
			dEdges.add(newedge2); 
		}
	}

	public boolean unlink(E src, E dst) {
		VertexNew<E> s = vertices.get(src);
		VertexNew<E> d = vertices.get(dst);
		if(s != null && d != null) {
			adjacencyLists.get(src).remove(new Edge<E>(s.getElement(), d.getElement(), 1)); //remove edge (s,d)
			if(!isDirected) { //Remove the other edge if this graph is undirected
				adjacencyLists.get(dst).remove(new Edge<E>(d.getElement(), s.getElement(), 1));
			}
			return true;
		}
		return false;
	}

	public boolean containsVertex(E key) {
		return vertices.containsKey(key);
	}

	public int getVerticesSize() {
		return vertices.size();
	}

	public boolean isEmpty() {
		return vertices.isEmpty();
	}

	public boolean BFS(E src) {
		if(containsVertex(src)) {
			VertexNew<E> s = vertices.get(src);
			lastSrc = s;
			vertices.forEach((E e, VertexNew<E> u) -> {
				u.setColor(Color.WHITE);
				u.setDistance(Integer.MAX_VALUE);
				u.setPredecessor(null);
			});
			s.setColor(Color.GRAY);
			s.setDistance(0);
			//s.predecessor is already null so skip that step
			Queue<VertexNew<E>> queue = new Queue<>();
			queue.enqueue(s);
			try {
				while(!queue.isEmpty()) {
					VertexNew<E> u = queue.dequeue();
					ArrayList<Edge<E>> adj = adjacencyLists.get(u.getElement());
					for(Edge<E> ale: adj) {
						VertexNew<E> v = vertices.get(ale.getDst());
						if(v.getColor() == Color.WHITE) {
							v.setColor(Color.GRAY);
							v.setDistance(u.getDistance()+1);
							v.setPredecessor(u);
							queue.enqueue(v);
						}
					}
					u.setColor(Color.BLACK);
				}
			} catch (Exception emptyQueueException) {
				//-_-
			}
			return true;
		}
		return false;
	}

	
}