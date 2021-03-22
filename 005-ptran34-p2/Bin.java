import java.util.NoSuchElementException;
/**
 * Bin class.
 *  @author Phuong Tran
 */
public class Bin implements Stack<Plate> {
	/**
	 * internal list.
	 */
	public AttachedList<Plate> internalList;
	
	/**
	 * Constructor.
	 */
	public Bin() {
		internalList = new AttachedList<>();
	}
	
	/**
	 * push value to head of node.
	 * @param value needed to add
	 * @return true
	 */
	@Override
	public boolean push(Plate value){
		internalList.add(0,value);
		return true;
		
	}

	/**
	 * remove the tail of list.
	 * @return pop node's value
	 * @throws NoSuchElementException if there's nothing to remove
	 */
	@Override
	public Plate pop(){
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
	public int size(){
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
	@Override
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
		boolean first = true;
		for(Plate p : internalList) {
			if(first) { returnString = returnString+p; first = false; }
			else {returnString = returnString+"|"+p; }
		}
		return returnString;
	}
}
