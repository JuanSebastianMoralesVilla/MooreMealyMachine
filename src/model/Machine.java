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

	public HashMap<Integer, ArrayList<Q>> redefineState(ArrayList<ArrayList<Q>> parts, int targetSubset, S s) {
		ArrayList<Q> reviewedState = parts.get(targetSubset);
		HashMap<Integer, ArrayList<Q>> refinedPartition = new HashMap<>();

		for (int i = 0; i < reviewedState.size();i++) {
			Q dst = nextStates(reviewedState.get(i), s);
			boolean found = false;
			int j;
			for (j = 0; j < parts.size() && !found; j++) {
				ArrayList<Q> currentPartition = parts.get(j);
				found = currentPartition.contains(dst);
			}
			if (!refinedPartition.containsKey(j)) {
				refinedPartition.put(j, new ArrayList<>());
			}
			refinedPartition.get(j).add(reviewedState.get(i));
		}
		return refinedPartition;
	}

	public ArrayList<ArrayList<Q>> partitioningStepTwoAndStepThree(ArrayList<ArrayList<Q>> parts) {
		ArrayList<ArrayList<Q>> prevParts;
		boolean previousEqualsCurrent = false;
		boolean centinela = false;
		while (!previousEqualsCurrent && !centinela) {
			prevParts = parts;
			for (S s : getS()) {
				HashMap<Integer, HashMap<Integer, ArrayList<Q>>> originPartitioned = new HashMap<>();
				int newNumberOfPartitions = 0;
				for (int i = 0; i < parts.size(); i++) {
					HashMap<Integer, ArrayList<Q>> refinement = redefineState(parts, i, s);
					originPartitioned.put(i, refinement);
					newNumberOfPartitions += refinement.size();
				}
				if (newNumberOfPartitions > parts.size()) {

					parts = new ArrayList<>();
					for (int i = 0; i < originPartitioned.size(); i++) {
						parts.addAll(originPartitioned.get(i).values());
					}

					centinela = true;
				}
			}
			previousEqualsCurrent = prevParts.size() == parts.size();
		}
		return parts;
	}
}
