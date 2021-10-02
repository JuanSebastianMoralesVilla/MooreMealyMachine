package data_structures;


public class Comp3<T extends Comparable<T>> {

	// Atributos ----------------------------------------------------------
	
	/**
	 * Representa el v�rtice que conecta esta arista.
	 */
	private Comp2<T> vertice;
	
	/**
	 * Representa el peso de esta arista.
	 */
	private int peso;
	
	// Constructor ---------------------------------------------------------
	
	/**
	 * Construye una arista con el peso y el v�rtice de destino indicados.
	 * @param peso - Es el peso que tendr� la arista.
	 * @param vertice - Es el otro v�rtice que conectar� la arista.
	 */
	public Comp3(int peso, Comp2<T> vertice) {
		this.vertice = vertice;
		this.peso = peso;
	}
	
	// M�todos fundamentales ------------------------------------------------
	
	/**
	 * Se encarga de dar el peso de la arista.
	 * @return Un entero que representa el peso.
	 */
	public int darPeso() {
		return peso;
	}
	
	/**
	 * Se encarga de dar el otro v�rtice que conecta esta arista.
	 * @return Un v�rtice que representa la conexi�n de esta arista.
	 */
	public Comp2<T> darVertice(){
		return vertice;
	}
	
	// M�todos y servicios --------------------------------------------------
	
	@Override
	public String toString() {
		String respuesta = "";
		respuesta = ""+vertice+"; "+peso;
		return respuesta;
	}
}
