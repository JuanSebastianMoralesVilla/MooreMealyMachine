package data_structures;


public class Comp3<T extends Comparable<T>> {

	// Atributos ----------------------------------------------------------
	
	/**
	 * Representa el vértice que conecta esta arista.
	 */
	private Comp2<T> vertice;
	
	/**
	 * Representa el peso de esta arista.
	 */
	private int peso;
	
	// Constructor ---------------------------------------------------------
	
	/**
	 * Construye una arista con el peso y el vértice de destino indicados.
	 * @param peso - Es el peso que tendrá la arista.
	 * @param vertice - Es el otro vértice que conectará la arista.
	 */
	public Comp3(int peso, Comp2<T> vertice) {
		this.vertice = vertice;
		this.peso = peso;
	}
	
	// Métodos fundamentales ------------------------------------------------
	
	/**
	 * Se encarga de dar el peso de la arista.
	 * @return Un entero que representa el peso.
	 */
	public int darPeso() {
		return peso;
	}
	
	/**
	 * Se encarga de dar el otro vértice que conecta esta arista.
	 * @return Un vértice que representa la conexión de esta arista.
	 */
	public Comp2<T> darVertice(){
		return vertice;
	}
	
	// Métodos y servicios --------------------------------------------------
	
	@Override
	public String toString() {
		String respuesta = "";
		respuesta = ""+vertice+"; "+peso;
		return respuesta;
	}
}
