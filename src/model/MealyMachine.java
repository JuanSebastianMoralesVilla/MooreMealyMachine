/**
 * 
 */
package model;
import java.util.*;

import data_structures.*;

/**
 * @author Sebastian Morales (JuanSebastianMoralesVilla)
 * @author Alex Sanchez (ALEXJR2002)
 * @author Miguel Sarasti (MSarasti)
 *
 */
@SuppressWarnings("unused")
public class MealyMachine<Q extends Comparable<Q>,S,R> extends Machine<Q, S, R> {
	private HashMap<Q, HashMap<S, R>> rMealy;
	
	public MealyMachine(Q inState) {
		super(inState);
		rMealy = new HashMap<>();
	}

	public boolean addConnection(Q inState, S inSymbol, Q nxtState, R out) {
		boolean connected = super.addConnection(inState, nxtState, inSymbol);
		if(connected) {
			if(!rMealy.containsKey(inState)) {
				rMealy.put(inState, new HashMap<>());
			}
			rMealy.get(inState).put(inSymbol, out);
			getR().add(out);
		}
		return connected;
	}
	
	@Override
	public MealyMachine<Q, S, R> minimize(){
		bfs(getStart());
		ArrayList<ArrayList<Q>> parts = partitioningAlgorithm();

		Q newState = parts.get(0).get(0);
		MealyMachine<Q, S, R> minimizedMachine = new MealyMachine<>(newState);

		for (int i = 1; i < parts.size(); i++) {
			newState = parts.get(i).get(0);
			minimizedMachine.insertState(newState);
		}

		ArrayList<Q> origen = minimizedMachine.getAll();
		for (int j = 0; j < origen.size();j++) {
			Q src = origen.get(j);
			for (S s : getS()) {
				Q dst = nextStates(src, s);
				R rsp = getResponses(src, s);
				boolean relacionarEstadosd = false;
				for (int i = 0; i < parts.size() && !relacionarEstadosd; i++) {
					ArrayList<Q> p = parts.get(i);
					if (p.contains(dst)) {
						minimizedMachine.addConnection(src, s, p.get(0), rsp);
						relacionarEstadosd = true;
					}
				}
			}
		}

		return null;
	}

	private ArrayList<ArrayList<Q>> partitioningAlgorithm() {
		ArrayList<ArrayList<Q>> parts = partitioningStepOne();
		return super.partitioningStepTwoAndStepThree(parts);
	}

	private ArrayList<ArrayList<Q>> partitioningStepOne() {

		ArrayList<ArrayList<Q>> parts = new ArrayList<>();
		HashMap<ArrayList<R>, Integer> responseToIndex = new HashMap<>();
		ArrayList<Q> statesSet = getAll();
		int index = 0;
		//Pone el estado inicial de la particion P1
		for (int i = 0; i < statesSet.size();i++) {
			Q q = statesSet.get(i);
			ArrayList<R> responsesForStateQ = new ArrayList<>();
			for(S s : getS()) {
				responsesForStateQ.add(getResponses(q, s));
			}
			if (!responseToIndex.containsKey(responsesForStateQ)) {
				responseToIndex.put(responsesForStateQ, index);
				parts.add(new ArrayList<>());
				index++;
			}
			parts.get(responseToIndex.get(responsesForStateQ)).add(q);
		}
		return parts;
	}

	public boolean insertState(Q state) {
		boolean t = false;
		if(state != null) {
			t =  addVertex(state);
		}
		return t;
	}

	public R getResponses(Q q, S s) {
		if(rMealy.containsKey(q)) {
			return rMealy.get(q).get(s);
		}
		return null;
	}
}
