package data_structures;

public class SuperPair<T extends Comparable<T>> implements Comparable<SuperPair<T>>{
	private T element;
	private T element2;
	private int weight;
	public SuperPair(T element, T element2,int weight) {
		this.element = element;
		this.element2 = element2;
		this.weight = weight;
	}
	
	@Override
	public int compareTo(SuperPair<T> pair) {
		return weight-pair.weight;
	}
	public T getElement() {
		return element;
	}
	public T getElement2() {
		return element2;
	}
	public int getWeight() {
		return weight;
	}
}

