
// --------------------------------------------------------
// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
// --------------------------------------------------------

/**
 * Stack interface.
 * @param <T> generic type
 */
interface Stack<T> {
	/**
	 * append value to the list.
	 * @param value of the node
	 * @return true for every time node get pushed
	 */
	public boolean push(T value);

	/**
	 * remove last element.
	 * @return pop node's value
	 * @throws NoSuchElementException if nothing to pop
	 */
	public T pop();

	/**
	 * check size of list.
	 * @return Size of list
	 */
	public int size();

	/**
	 * check if the list is empty.
	 * @return true if list is empty, false otherwise
	 */
	public boolean isEmpty();

	/**
	 * Clear the list.
	 */
	public void clear();
}