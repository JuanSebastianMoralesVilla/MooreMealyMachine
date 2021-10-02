package data_structures;

public class Pair <T extends Comparable<T>> implements Comparable<Pair<T>>{
	private T element;
	private Integer weight;
	public Pair(T element, Integer weight){
		this.element = element;
		this.weight = weight;
	}
	
	@Override
	public int compareTo(Pair<T> arg0) {
		return arg0.weight-weight;
	}

	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}