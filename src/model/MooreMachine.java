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
		return null;
	}
}
