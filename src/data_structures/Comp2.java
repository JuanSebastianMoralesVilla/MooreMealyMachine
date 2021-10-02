package data_structures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;

public class Comp2<T extends Comparable<T>> {
	
	// Atributos -------------------------------------------------------------------
	
	/**
	 * Representa la lista de aristas asociadas a este v�rtice.
	 */
	private HashMap<T, Comp3<T>> aristas;
	
	/**
	 * Representa el contenido del v�rtice.
	 */
	private T contenido;
	
	/**
	 * Valor que indica si el v�rtice ha sido visitado o no.
	 */
	private boolean fueVisitado;
	
	/**
	 * Representa el grado del v�rtice.
	 */
	private int grado;
	
	// Constructor ------------------------------------------------------------------
	
	public Comp2(T contenido) {
		this.contenido = contenido;
		aristas = new HashMap<>();
		grado = 0;
		fueVisitado = false;
	}
	
	// M�todos fundamentales ---------------------------------------------------------
	
	/**
	 * Se encarga de dar las aristas de este v�rtice.
	 * @return Un hash table con las aristas asociadas a este v�rtice.
	 */
	public HashMap<T, Comp3<T>> darAristas(){
		return aristas;
	}
	
	/**
	 * M�todo que se encarga de dar la lista de adyacencias del v�rtice en
	 * orden ascendente.
	 * @return Una cola de prioridad con las adyacencias de los v�rtices.
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
	 * M�todo que se encarga de dar la lista de adyacencias del v�rtice en orden
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
	 * Se encarga de dar el contenido de este v�rtice.
	 * @return Un objeto gen�rico representando el contenido del v�rtice.
	 */
	public T darContenido() {
		return contenido;
	}
	
	/**
	 * Se encarga de dar el grado de este v�rtice.
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
	
	// M�todos y servicios ------------------------------------------------------------
	
	
}
