package model;

import java.util.*;

import data_structures.*;

/**
 * @author Sebastian Morales (JuanSebastianMoralesVilla)
 * @author Alex Sanchez (ALEXJR2002)
 * @author Miguel Sarasti (MSarasti)
 *
 */
public abstract class Machine<Q extends Comparable<Q>,S,R> extends GraphList<Q> {
	private Q start; //Estado inicial
	private HashSet<S> S; //Estados de estimulo
	private HashSet<R> R; //Estados de respuesta
	private HashMap<Q, HashMap<S, Q>> stateFun;
	
	public Machine (Q inState) {
		super(false);
		start = inState;
		S = new HashSet<>();
		R = new HashSet<>();
		stateFun = new HashMap<>();
		addVertex(inState);
	}

	/**
	 * Adds the connection between two states in the graph.
	 * @param inState the current state.
	 * @param nxtState the state you went to with the input.
	 * @param input symbol of the input alphabet.
	 * @return true if the connection was successful or false if it couldn't connect.
	 */
	public boolean addConnection(Q inState, Q nxtState, S input) {
		boolean connected = false;
		if(!stateFun.containsKey(inState)) {
			stateFun.put(inState, new HashMap<>());
		}
		if(input!=null && containsVertex(inState) && !stateFun.get(inState).containsKey(input) && containsVertex(nxtState)) {
			addEdge(inState, nxtState, 1);
			stateFun.get(inState).put(input, nxtState);
			S.add(input);
			connected = true;
		}
		return connected;
	}
	
	/**
	 * Returns the next state for a given input.
	 * @param current the current state.
	 * @param input symbol of the input alphabet
	 * @return The state you went with the given input or null.
	 */
	public Q nextStates(Q current, S input) {
		if(stateFun.containsKey(current)) {
			return stateFun.get(current).get(input);
		}
		return null;
	}
	
	public HashSet<R> getR(){
		return R;
	}
	
	public HashSet<S> getS(){
		return S;
	}
	
	public Q getStart() {
		return start;
	}
	
	public abstract Machine<Q, S, R> minimize();
	
	
}
