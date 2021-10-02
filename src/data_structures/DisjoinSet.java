package data_structures;

import java.util.ArrayList;

public class DisjoinSet<T> {
	private ArrayList<Set> sets;
	public DisjoinSet(int n) {
		sets = new ArrayList<>(n);
	}
	public void make(T value) {
		Set set = new Set(value);
		sets.add(set);
	}
	
	public void union(T x,T y) {
		Set first = null;
		Set second = null;
		int index = -1;
		for (int i = 0; i < sets.size() && (first==null || second==null); i++) {
			if(first==null) {
				first = sets.get(i).find(x)!=null?sets.get(i):null;
			}
			if(second==null) {
				second = sets.get(i).find(y)!=null?sets.get(i):null;
				index = i;
			}
		}
		if(index!=-1 && first!=second) {
			first.union(second);
			sets.remove(index);
		}
		
	}
	
	public T find(T x) {
		T result = null;
		for (int i = 0; i < sets.size(); i++) {
			result = sets.get(i).find(x);
			if(result!=null) {
				break;
			}
		} 
		return result;
	}
	
	class Set{
		T represent;
		ArrayList<T> values;
		public Set(T first) {
			represent = first;
			values = new ArrayList<>();
			values.add(first);
		}
		
		public void union(Set set) {
			for (int i = 0; i < set.values.size(); i++) {
				values.add(set.values.get(i));
			}
		}
		
		public T find(T x) {
			if(values.contains(x)) {
				return represent;
			}
			return null;
		}
	}
}
