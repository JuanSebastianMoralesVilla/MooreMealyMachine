/**
 * 
 */
package model;
import java.util.*;

/**
 * @author Sebastian Morales (JuanSebastianMoralesVilla)
 * @author Alex Sanchez (ALEXJR2002)
 * @author Miguel Sarasti (MSarasti)
 *
 */
@SuppressWarnings("unused")
public class MooreMachine<Q extends Comparable<Q>,S,R> extends Machine<Q, S, R> {
	private HashMap<Q, R> rMoore;
	
	public MooreMachine(Q inState, R outInState) {
		super(inState);
		rMoore = new HashMap<>();
		insertState(inState, outInState);
	}
	
	public boolean insertState(Q state, R response) {
		boolean in = false;
		if(state!=null && !rMoore.containsKey(state)) {
			rMoore.put(state, response);
			addVertex(state);
			getR().add(response);
			in = true;
		}
		return in;
	}
	
	public R getRM(Q state) {
		return rMoore.get(state);
	}
	
	@Override
	public MooreMachine<Q, S, R> minimize(){
		bfs(getStart());
		ArrayList<ArrayList<Q>> parts = partitioningAlgorithm();

		Q newState = parts.get(0).get(0);
		MooreMachine<Q, S, R> minimizedMachine = new MooreMachine<>(newState, getResponses(newState));
		for (int i = 1; i < parts.size(); i++) {
			newState = parts.get(i).get(0);
			minimizedMachine.insertState(newState, getResponses(newState));
		}
		ArrayList<Q> origen = minimizedMachine.getAll();
		for (int i = 0; i < origen.size();i++) {
			Q src = origen.get(i);
			for (S s : getS()) {
				Q dst = nextStates(src, s);
				boolean t = false;
				for (int j = 0; j < parts.size() && !t; j++) {
					ArrayList<Q> p = parts.get(j);
					if (p.contains(dst)) {
						minimizedMachine.addConnection(src, p.get(0), s);
						t = true;
					}
				}
			}
		}
		return minimizedMachine;
	}

	private ArrayList<ArrayList<Q>> partitioningAlgorithm() {
		ArrayList<ArrayList<Q>> parts = partitioningStepOne();
		return super.partitioningStepTwoAndStepThree(parts);
	}

	private ArrayList<ArrayList<Q>> partitioningStepOne() {
		ArrayList<ArrayList<Q>> partitioningParts = new ArrayList<>();
		HashMap<R, Integer> responseToIndex = new HashMap<>();
		ArrayList<Q> statesSet = getAll();
		int index = 0;
		for (int i = 0; i < statesSet.size();i++) {
			Q q = statesSet.get(i);
			R r = getResponses(q);
			if (!responseToIndex.containsKey(r)) {
				responseToIndex.put(r, index);
				partitioningParts.add(new ArrayList<>());
				index++;
			}
			partitioningParts.get(responseToIndex.get(r)).add(q);
		}
		return partitioningParts;

	}

	public R getResponses(Q q) {
		return rMoore.get(q);
	}
}
