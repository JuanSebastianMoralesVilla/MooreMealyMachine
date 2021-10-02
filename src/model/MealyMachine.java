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
		return null;
	}
}
