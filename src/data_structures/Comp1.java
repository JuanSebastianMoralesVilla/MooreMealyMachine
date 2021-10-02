package data_structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Comp1<T extends Comparable<T>> {
	
	// Atributos ---------------------------------------------------------
	
	/**
	 * Representa la lista de vertices que tiene el grafo.
	 */
	private HashMap<T, Comp2<T>> vertices;
	
	/**
	 * Valor lógico que determina si el grafo es dirigido o no.
	 */
	private boolean esDirigido;
	
	// Constructor -------------------------------------------------------
	
	/**
	 * Construye el grafo dependiendo si es dirigido o no. Si el grafo es no dirigido,
	 * al crear una arista entre dos vértices se tendrá en cuenta la referencia tanto
	 * de ida como de vuelta. Si el grafo es dirigido la anterior anotación se ignora
	 * @param esDirigido - Valor que determina si el grafo es dirigido o no.
	 */
	public Comp1(boolean esDirigido) {
		vertices = new HashMap<>();
		this.esDirigido = esDirigido;
	}
	
	// Métodos fundamentales ---------------------------------------------
	
	/**
	 * Se encarga de dar los vértices del grafo.
	 * @return
	 */
	public HashMap<T, Comp2<T>> darVertices(){
		return vertices;
	}
	
	// Métodos y servicios -----------------------------------------------

	public void agregarVertice(T contenido) {
		if(!vertices.containsKey(contenido)) {
			Comp2<T> nuevoVertice = new Comp2<T>(contenido);
			vertices.put(contenido, nuevoVertice);
		}
	}
	
	public void agregarArista(T verticeA, T verticeB, int peso) {
		Comp2<T> vertA = vertices.get(verticeA);
		Comp2<T> vertB = vertices.get(verticeB);
		if (vertA == null || vertB == null) {
			System.err.println("Alguno de los vértices no existe");
		}else {
			HashMap<T, Comp3<T>> aristasA = vertA.darAristas();
			HashMap<T, Comp3<T>> aristasB = vertB.darAristas();
			if (!esDirigido) {
				if (!aristasB.containsKey(verticeA)) {
					aristasB.put(verticeA, new Comp3<>(peso, vertA));
					vertB.aumentarGrado();
				}else {
					System.err.println("No se pudo agregar la arista");
				}
			}
			if(!aristasA.containsKey(verticeB)) {
				aristasA.put(verticeB, new Comp3<T>(peso, vertB));
				vertA.aumentarGrado();
			}else {
				System.err.println("No se pudo agregar la arista");
			}
		}
	}
	
	public void eliminarArista(Comp2<T> verticeA, Comp2<T> verticeB) {
		
	}
	
	public HashMap<T, Integer> dijkstra(T vertice) {
		System.out.println("Caminos más cortos de "+vertice+" hasta:");
		Comp2<T> verticeElegido = vertices.get(vertice);
		HashMap<T, Integer> L = new HashMap<>();
		if(verticeElegido == null) {
			System.err.println("El vértice no existe");
		}else {
			L.put(verticeElegido.darContenido(), 0);
			Set<Comp2<T>> S = new HashSet<>();
			while(S.size() != vertices.size() - 1) {
				T u = buscarElementoMinimoLNoPertenecienteS(L, S);
				if (u != null) {
					S.add(vertices.get(u));
					HashMap<T, Comp3<T>> aristasU = vertices.get(u).darAristas();
					Iterator<T> v = vertices.keySet().iterator();
					while(v.hasNext()) {
						T elementoActual = v.next();
						if(!S.contains(vertices.get(elementoActual)) && aristasU.get(elementoActual) != null) {
							Integer pesoUyV = aristasU.get(elementoActual).darPeso();
							Integer Lv = L.get(elementoActual);
							if(Lv == null) {
								Lv = Integer.MAX_VALUE;
							}
							if(L.get(u)+pesoUyV < Lv) {
								if(L.containsKey(elementoActual)) {
									L.replace(elementoActual, L.get(u)+pesoUyV);
								}else {
									L.put(elementoActual, L.get(u)+pesoUyV);
								}
							}
						}
					}
				}
			}
		}
		return L;
	}
	
	private T buscarElementoMinimoLNoPertenecienteS(HashMap<T, Integer> L, Set<Comp2<T>> S) {
		T elemento = null;
		Iterator<T> iterador = L.keySet().iterator();
		while(iterador.hasNext()) {
			T elementoActual = iterador.next();
			if(!S.contains(vertices.get(elementoActual))) {
				if(elemento == null) {
					elemento = elementoActual;
				}else {
					if(L.get(elementoActual) < L.get(elemento)) {
						elemento = elementoActual;
					}
				}
			}
		}
		return elemento;
	}
	
	/**
	 * Método que se encarga de hacer el recorrido en amplitud del el grafo, iniciando
	 * en el vértice indicado.
	 * @param vertice - Es el vértice donde se inicia el recorrido.
	 * @return Una lista con los vértices que se visitaron en el grafo.
	 */
	public List<T> BFS(T vertice){
		Comp2<T> v = vertices.get(vertice);
		List<T> respuesta = new ArrayList<>();
		if(vertice == null) {
			System.err.println("El vértice no existe");
		}else {
			Queue<T> cola = new LinkedList<>();
			cola.add(v.darContenido());
			while(!cola.isEmpty()) {
				v = vertices.get(cola.peek());
				PriorityQueue<T> adyacencias = v.darAdyacenciasOrdenAscendente();
				while(!adyacencias.isEmpty()) {
					T elementoActual = adyacencias.poll();
					Comp2<T> verticeAdyacente = vertices.get(elementoActual);
					if(!verticeAdyacente.fueVisitado()) {
						if(!cola.contains(verticeAdyacente.darContenido())) {
							cola.add(verticeAdyacente.darContenido());
						}
					}
				}
				cola.poll();
				v.cambiarEstatus(true);
				respuesta.add(v.darContenido());
			}
		}
		return respuesta;
	}
	
	public List<T> DFS(T vertice){
		List<T> respuesta = new ArrayList<>();
		Comp2<T> v = vertices.get(vertice);
		if(v == null) {
			System.err.println("El vértice no existe");
		}else {
			Stack<T> pila = new Stack<>();
			pila.push(vertice);
			while(!pila.isEmpty()) {
				v = vertices.get(pila.pop());
				respuesta.add(v.darContenido());
				Stack<T> adyacencias = v.darAdyacenciasDescendente();
				while(!adyacencias.isEmpty()) {
					T elemento = adyacencias.pop();
					if(!respuesta.contains(elemento)) {
						if(!pila.contains(elemento)) {
							pila.push(elemento);							
						}
					}
				}
			}
		}
		return respuesta;
	}
}
