import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import javafx.animation.KeyValue;
/**
 * ALOP class.
 * @param <K> is key
 * @param <V> is value
 */
public class ArrayOfListsOfPairs<K,V> {
	/**
	 * internal structure.
	 */
	private Node<K,V>[] storage;
	
	/**
	 * getter for array .
	 * @return storage aka arrays of linked list
	 */
	public Node<K,V>[] getStorage(){
		return storage;
	}

	/**
	 *  add new node in.
	 * @param index to add
	 * @param k is key
	 * @param v is value
	 * @return true if added
	 */
	public boolean add(int index, K k, V v){
		KeyValuePair<K,V> kvp = new KeyValuePair<>(k,v);
		if (storage[index] == null) {
			storage[index] = new Node<>(kvp);
			return true;
		}
		Node<K,V> current = storage[index];
		while(current != null) {
			if (current.next == null) {
				current.next = new Node<>(kvp);
				return true;
			}				
			current = current.next;	

		}
		return false;
	}

	/**
	 * removed the kvp.
	 * @param index to remove
	 * @param key is key to remove
	 * @return true if kvp is removed
	 */
	public boolean remove(int index, K key) {
		Node<K,V> current = storage[index];
		Node<K,V> previous = null;
		if (current == null) {
			return false;
		}
		while (!current.pair.getKey().equals(key)) {
			previous = current;
			current = current.next;
		}
		previous.next = current.next;
		return true;
	}

	/**
	 * replace kvp with new value.
	 * @param index to replace
	 * @param key is key
	 * @param value is new value
	 * @return true if replaced, false otherwise
	 */
	public boolean replace(int index, K key, V value){
		Node<K,V> current = storage[index];
		KeyValuePair<K,V> kvp = new KeyValuePair<>(key, value);
		Node<K,V> newNode = new Node<>(kvp);
		if (current == null) {
			return false;
		}
		Node<K,V> previous = current;
		current = current.next;
		// current is null
		if (previous.pair.getKey().equals(key)) {
			storage[index] = newNode;
			return true;
		}
		while (current!=null) {
			if (current.pair.getKey().equals(key)) {
				previous.next = newNode;
				newNode.next = current.next;
				return true;
			}
		}
		return true;
	}
	/**
	 * check if key is in storage.
	 * @param index to check
	 * @param key is key
	 * @return true if replaced, false otherwise
	 */
	public boolean containKey(int index, K key) {
		if (storage[index] == null) {
			return false;
		}
		Node<K,V> current = storage[index];
		while (current!=null) {
			if (current.pair.getKey().equals(key)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}
	/**
	 * get key value.
	 * @param index to check
	 * @param key to find
	 * @return value of key
	 */
	public V getKey(int index, K key) {
		if (storage[index] == null) {
			return null;
		}
		Node<K,V> current = storage[index];
		while (current!=null) {
			if (current.pair.getKey().equals(key)) {
				return current.pair.getValue();
			}
			current = current.next;
		}
		return null;
	}
	/**
	 * remove value.
	 * @param v to remove
	 * @return true if removed, false otherwise
	 */
	public boolean removeValue(V v) {
		Node<K,V> head;
		for(int i = 0; i < storage.length; i++) {
			head = storage[i];
			if (storage[i]==null) {
				continue;
			}
			else if(head.pair.getValue().equals(v)){
				if(head.next!=null){
					storage[i]=head.next;
				}
				else{
					storage[i]=null;
				}
			}
			else{
				Node<K,V> previous=head;
				Node<K,V> current=head.next;
				while (current!=null) {
					if(current.pair.getValue().equals(v)){
						previous.next=current.next;
					}
					previous=current;
					current=current.next;
				}
			}
			
		}
		return true;
	}
	/**
	 * remove key.
	 * @param k to remove
	 * @return true if removed, false otherwise
	 */
	public boolean removeKey(K k) {
		Node<K,V> head;
		for(int i = 0; i < storage.length; i++) {
			head = storage[i];
			if (storage[i]==null) {
				continue;
			}
			else if(head.pair.getKey().equals(k)){
				if(head.next!=null){
					storage[i]=head.next;
				}
				else{
					storage[i]=null;
				}
			}
			else{
				Node<K,V> previous=head;
				Node<K,V> current=head.next;
				while (current!=null) {
					if(current.pair.getKey().equals(k)){
						previous.next=current.next;
					}
					previous=current;
					current=current.next;
				}
			}
			
		}
		return true;
	}
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	
	/**
	 * tosting method.
	 * @return string of obj
	 */
	public String toString() {
		//you may edit this to make string representations of your
		//lists for testing
		return super.toString();
	}
	/**
	 * typical main method.
	 * @param args for any main method
	 */	
	public static void main(String[] args) {
		
	}
	
	
	//--------------------------------------------------------
	// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
	//--------------------------------------------------------
	
	/**
	 * This is what one node in one linked list looks like.
	 * @param <K> generic key
	 * @param <V> generic value.
	 */
	public static class Node<K,V> {
		/**
		 * it contains one key-value pair.
		 */
		public KeyValuePair<K,V> pair;
		
		/**
		 * and one pointer to the next node.
		 */
		public Node<K,V> next;
		
		/**
		 * convenience constructor.
		 * @param pair kvp
		 */
		public Node(KeyValuePair<K,V> pair) {
			this.pair = pair;
		}
		
		/**
		 * convenience constructor.
		 * @param pair kvp
		 * @param next node
		 */
		public Node(KeyValuePair<K,V> pair, Node<K,V> next) {
			this.pair = pair;
			this.next = next;
		}
	}
	
	/**
	 * constructor.
	 * @param numLists to initialize
	 */
	@SuppressWarnings("unchecked")
	public ArrayOfListsOfPairs(int numLists) {
		storage = (Node<K,V>[]) new Node[numLists];
	}
	
	/**
	 * Returns the number of lists in this collection.
	 * @return number of lists in this collection
	 */
	public int getNumLists() {
		return storage.length;
	}
	
	/**
	 * Returns all key-value pairs in the specified sublist of this collection.
	 * @param listId index to find
	 * @return all key-value pairs in the specified sublist of this collection
	 */
	public java.util.ArrayList<KeyValuePair<K,V>> getAllPairs(int listId) {
		java.util.ArrayList<KeyValuePair<K,V>> lst = new java.util.ArrayList<>();
		Node<K,V> current = storage[listId];
		while(current != null) {
			lst.add(current.pair);
			current = current.next;
		}
		
		return lst;
	}
	
	/**
	 * Returns all key-value pairs in this collection.
	 * @return all key-value pairs in this collection
	 */
	public java.util.ArrayList<KeyValuePair<K,V>> getAllPairs() {
		java.util.ArrayList<KeyValuePair<K,V>> lst = new java.util.ArrayList<>();
		
		for(int i = 0; i < storage.length; i++) {
			lst.addAll(getAllPairs(i));
		}
		return lst;
	}

	/**
	 * Return all values in this collection.
	 * @return all values in this collection
	 */
	public java.util.ArrayList<V> getAllValues(){
		java.util.ArrayList<KeyValuePair<K,V>> allPairs = getAllPairs();
		java.util.ArrayList<V> values = new java.util.ArrayList<>();
		for(int i = 0; i < allPairs.size(); i++){
			values.add(allPairs.get(i).getValue());
		}
		return values;
	}
}
