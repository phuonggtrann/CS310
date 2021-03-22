import java.util.NoSuchElementException;

/**
 * Air class.
 * @author Phuong Tran.
 */
public class Air implements Queue<Plate> {
	/**
	 * Max capacity is 13.
	 */
	public static final int MAX_CAPACITY = 13;
	/**
	 * list we will be using.
	 */
	public AttachedList<Plate> internalList;

	/**
	 * constructor.
	 * initialiize internalList.
	 */
	public Air() {
		internalList = new AttachedList<>();		
	}
	/**
	 * append value to end of list.
	 * @param value needed to add
	 * @return true
	 */
	@Override
	public boolean enqueue(Plate value){
		return internalList.add(value);
	}

	/**
	 * remove the head of list.
	 * @return dequeue node's value
	 * @throws NoSuchElementException if there's nothing to remove
	 */
	@Override
	public Plate dequeue(){
		if (isEmpty()){
			throw new NoSuchElementException();
		}
		return internalList.remove(0);
	} 

	/**
	 * list's size getter.
	 * @return size of internalList 
	 */
	@Override
	public int size() {
		return internalList.size();
	}

	/**
	 * check if the list is empty.
	 * @return true if list is empty, false otherwise
	 */
	@Override
	public boolean isEmpty(){
		return internalList.isEmpty();
	}
	/**
	 * clear all list.
	 */
	public void clear(){
		internalList.clear();
	}
	
	// --------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	// --------------------------------------------------------
	/**
	 * main method.
	 * @param args for every main method
	 */
	public static void main(String[] args) {	
	}
	
	// --------------------------------------------------------
	// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
	// --------------------------------------------------------
	/**
	 * toString method.
	 * @return String of internal list
	 */
	public String toString() {
		String returnString = "";
		for(Plate p : internalList) {
			returnString = p+returnString;
		}
		return returnString;
	}
}
