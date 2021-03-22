// --------------------------------------------------------
// DO NOT EDIT ANYTHING BELOW THIS LINE (except to add JavaDocs)
// --------------------------------------------------------

/**
 * Plate class.
 */
public class Plate {
	/** 
	 * Number of plates.
	 */
	private int number;
	
	/**
	 * Constructor.
	 * @param number of plates
	 */
	public Plate(int number) {
		this.number = number;
	}
	
	/**
	 * toString method.
	 * @return value of plate
	 */
	public String toString() {
		return "("+((char)(this.number+96))+")";
	}
	
	/**
	 * number of plates getter.
	 * @return amount of number of plate
	 */
	public int getNumber() {
		return this.number;
	}
}
