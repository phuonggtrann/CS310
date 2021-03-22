//Hash table with separate chaining. Each key and value gets
//placed together as a single entry in the table. The hash code
//of a key is used to place the pair in the table and to look
//for it again. Note that KeyValuePair is a structure for
//ArrayOfListsOfPairs, this part of the code needs to be able to
//deal with keys and values separately.

import java.util.Collection;

/**
 * hashtable class.
 * @param <K> is key
 * @param <V> is value
 */
public class HashTable<K,V> {
	/**
	 * This is the minimum number of slots in the hash table.
	 */
	private static final int MIN_SLOTS = 2;
	
	/**
	 * You must use this as your internal storage.
	 */
	protected ArrayOfListsOfPairs<K,V> storage;
	/**
	 * keep track of table size.
	 */
	private int tableSize;

	/**
	 * If the number of slots requested is less than the minimum number of slots, use the minimum instead.
	 * @param numSlots to initialize
	 */
	public HashTable(int numSlots) {
		if (numSlots < MIN_SLOTS){
			this.storage = new ArrayOfListsOfPairs<>(MIN_SLOTS);
		}
		else {
			this.storage = new ArrayOfListsOfPairs<>(numSlots);
		}
		this.tableSize = 0;
	}
	
	/**
	 * The number of key-value entries in the table.
	 * @return The number of key-value entries in the table.
	 */
	public int size() {
		return this.tableSize;
	}
	
	/**
	 * Returns the number of slots in the table.
	 * @return the number of slots in the table.
	 */
	public int getNumSlots() {
		return this.storage.getNumLists();
	}
	
	/**
	 * Returns the load on the table.
	 * load = number of entries / number of slots
	 * @return load on the table
	 */
	public double getLoad() {
		double entries = (double) size();
		double slots = (double) getNumSlots();
		return entries/slots;
	}
	
	/**
	 * add kvp to hash table.
	 * @param key to add
	 * @param value to add
	 * @return true if added, false otherwise
	 */
	public boolean add(K key, V value) {
		if (key==null || value==null) {
			return false;
		}
		if (contains(key)) {
			return false;
		}
		int index = key.hashCode() % this.storage.getNumLists();
		this.storage.add(index, key, value);
		this.tableSize++;
		if (this.getLoad()>3) {
			rehash(3*getNumSlots());
		}
		return true;
	}
	
	/**
	 * Rehashes the table to the given new size.
	 * @param newSize to rehash
	 */
	public void rehash(int newSize) {
		if (newSize<MIN_SLOTS) {
			newSize = MIN_SLOTS;
		}
		ArrayOfListsOfPairs<K,V> newStorage = new ArrayOfListsOfPairs<>(newSize);
		this.storage = newStorage;
		Collection<KeyValuePair<K,V>> newRehashMap = this.storage.getAllPairs();
		for (KeyValuePair<K,V> pair : newRehashMap) {
			int index = pair.getKey().hashCode() % this.storage.getNumLists();
			this.storage.add(index, pair.getKey(), pair.getValue());
		}
	}
	
	/**
	 * change the associated value to the provided value.
	 * @param key of hashtable
	 * @param value new value to change
	 * @return true if change succeed, false otherwise
	 */
	public boolean replace(K key, V value) {
		if (!contains(key)) {
			return false;
		}
		int index = key.hashCode() % this.storage.getNumLists();
		return this.storage.replace(index, key, value);
	}
	
	/**
	 * remove key and its connection.
	 * @param key of hashtable
	 * @return true if key is remove, false if key notfound/othewise
	 */
	public boolean remove(K key) {
		int index = key.hashCode() % this.storage.getNumLists();
		if (!contains(key)) {
			return false;
		}
		this.tableSize--;
		return this.storage.remove(index, key);
	}
	
	/**
	 * contain key method.
	 * @param key of hashtable
	 * @return true if found, false otherwise
	 */
	public boolean contains(K key) {
		int index = key.hashCode() % this.storage.getNumLists();
		return this.storage.containKey(index, key);
	}
	
	/**
	 * get method.
	 * @param key of hashtable
	 * @return its value
	 */
	public V get(K key) {
		int index = key.hashCode() % this.storage.getNumLists();
		if (!contains(key)) {
			return null;
		}
		return this.storage.getKey(index, key);
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	
	/**
	 * tostring method.
	 * @return string of object
	 */
	public String toString() {
		return super.toString();
	}
	
	/**
	 * typical main method.
	 * @param args for every main method
	 */
	public static void main(String[] args) {
		//Some example testing code...
		
		//make a hash table and add something to it
		HashTable<Integer,String> ht = new HashTable<>(2);
		ht.add(2,"Apple");
		
		//get all pairs at location 0
		Collection<KeyValuePair<Integer,String>> pairs = ht.getInternalTable().getAllPairs(0);
		
		//should be one pair there...
		if(pairs.size() == 1) {
			//get the first pair from the list
			KeyValuePair<Integer,String> pair = pairs.iterator().next();
			
			//make sure it's the pair expected
			if(pair.getKey().equals(2) && pair.getValue().equals("Apple")) {
				System.out.println("Yay");
			}	
		}
		ht.replace(2, "banana");
		System.out.println(ht.get(2));
		ht.add(3, "cherry");
		ht.replace(3, "apple");
		System.out.println(ht.get(3));
	}
	
	//--------------------------------------------------------
	// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
	//--------------------------------------------------------
	
	/**
	 * This will be used to check that you are setting the storage up correctly.
	 * @return storage of hashtable
	 */
	public ArrayOfListsOfPairs<K,V> getInternalTable() {
		return storage;
	}
}