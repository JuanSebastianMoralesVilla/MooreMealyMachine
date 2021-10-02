package data_structures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;

public class Comp2<T extends Comparable<T>> {
	
	// Atributos -------------------------------------------------------------------
	
	/**
	 * Representa la lista de aristas asociadas a este vértice.
	 */
	private HashMap<T, Comp3<T>> aristas;
	
	/**
	 * Representa el contenido del vértice.
	 */
	private T contenido;
	
	/**
	 * Valor que indica si el vértice ha sido visitado o no.
	 */
	private boolean fueVisitado;
	
	/**
	 * Representa el grado del vértice.
	 */
	private int grado;
	
	// Constructor ------------------------------------------------------------------
	
	public Comp2(T contenido) {
		this.contenido = contenido;
		aristas = new HashMap<>();
		grado = 0;
		fueVisitado = false;
	}
	
	// Métodos fundamentales ---------------------------------------------------------
	
	/**
	 * Se encarga de dar las aristas de este vértice.
	 * @return Un hash table con las aristas asociadas a este vértice.
	 */
	public HashMap<T, Comp3<T>> darAristas(){
		return aristas;
	}
	
	/**
	 * Método que se encarga de dar la lista de adyacencias del vértice en
	 * orden ascendente.
	 * @return Una cola de prioridad con las adyacencias de los vértices.
	 */
	public PriorityQueue<T> darAdyacenciasOrdenAscendente(){
		PriorityQueue<T> adyacencias = new PriorityQueue<>();
		Iterator<T> iterador = this.aristas.keySet().iterator();
		while(iterador.hasNext()) {
			T elementoActual = iterador.next();
			adyacencias.add(elementoActual);
		}
		return adyacencias;
	}
	
	/**
	 * Método que se encarga de dar la lista de adyacencias del vértice en orden
	 * descendente.
	 * @return Una pila con las adyacencias.
	 */
	public Stack<T> darAdyacenciasDescendente(){
		Stack<T> respuesta = new Stack<>();
		PriorityQueue<T> colaPrioridad = darAdyacenciasOrdenAscendente();
		while(!colaPrioridad.isEmpty()) {
			respuesta.push(colaPrioridad.poll());
		}
		return respuesta;
	}
	
	/**
	 * Se encarga de dar el contenido de este vértice.
	 * @return Un objeto genérico representando el contenido del vértice.
	 */
	public T darContenido() {
		return contenido;
	}
	
	/**
	 * Se encarga de dar el grado de este vértice.
	 * @return Un entero que representa el grado.
	 */
	public int darGrado() {
		return grado;
	}
	
	public void aumentarGrado() {
		grado++;
	}
	
	public boolean fueVisitado() {
		return fueVisitado;
	}
	
	public void cambiarEstatus(boolean fueVisitado) {
		this.fueVisitado = fueVisitado;
	}
	
	@Override
	public String toString() {
		
		return contenido+"";
	}
	
	// Métodos y servicios ------------------------------------------------------------
	
	
}
