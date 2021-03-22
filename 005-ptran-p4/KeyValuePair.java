//This class represents a key-value pair. It is completed for you,
//but you need to add JavaDocs.

//--------------------------------------------------------
// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
//--------------------------------------------------------
/**
 * KVP class.
 * @param <K> key
 * @param <V> value
 */
public class KeyValuePair<K,V> {
	/**
	 * key.
	 */
	private K key;
	/**
	 * value.
	 */
	private V value;
	/**
	 * constructor.
	 * @param key of kvp
	 * @param value of kvp
	 */
	public KeyValuePair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	/**
	 * get key.
	 * @return key of kvp
	 */
	public K getKey() {
		return key;
	}
	/**
	 * get value.
	 * @return value of kvp
	 */
	public V getValue() {
		return value;
	}
	/**
	 * equals method.
	 * @param o obj to compare
	 * @return true if equal, false otherwise
	 */
	public boolean equals(Object o) {
		if(o instanceof KeyValuePair) {
			return key.equals(((KeyValuePair)o).key);
		}
		return false;
	}
	/**
	 * get hashcode.
	 * @return hashcode of key.
	 */
	public int hashCode() {
		return key.hashCode();
	}
	/**
	 * tostring method.
	 * @return string of kvp
	 */
	public String toString() {
		return "("+key+","+value+")";
	}
}