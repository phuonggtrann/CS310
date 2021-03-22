/** 
 * StrieNode class that hold Strie's children.
 */
public class StrieNode{
	/**
	 * Number of children for each node. 
	 * Do not change this value.
	 */
	private static final int NUM_CHILDREN = 26;
	
	/**
	 * Use this array to hold children nodes.
	 */
	private StrieNode[] children = new StrieNode[NUM_CHILDREN];

	/**
	 * Marks the end of a word.
	 */
	private boolean endMarker;

	/**
	 * Marks a character/child-node as 'removed' if this node/char denoted the end of a word before and that word has been removed by a remove() operation.
	 */
	private boolean removed;

	/**
	 * self declare.
	 */
	private int numChild;

	/**
	 * Constructor.
	 */
	public StrieNode(){
		removed=false;
		endMarker=false;
		numChild=0;
	}

	/**
	 * set the "removed" status.
	 */
	public void setRemoved(){
		// O(1)
		removed = true;
	}

	/**
	 * unset the "removed" status.
	 */
	public void unsetRemoved(){
		// O(1)
		removed = false;
		
	}
	
	/**
	 * returns the 'removed' status of a node.
	 * @return removed status of a node.
	 */
	public boolean isRemoved(){
		// O(1)
		return removed;
	}

	/**
	 * returns true if node contains ch, false otherwise.
	 * @param ch given char
	 * @return true if ch at index is not nul, false otherwise
	 */
	public boolean containsChar(char ch){
		// O(1)
		int index = ch - 'a'; 
		return children[index]!=null;
	}

	/**
	 * returns the node that contains ch.
	 * @param ch given char
	 * @return node at index contains that ch
	 */
	public StrieNode getChild(char ch){
		// O(1)
		if (children[ch-'a'].isRemoved()){
			return null;
		}
		return children[ch-'a'];
	}

	/**
	 * put node in the appropriate index.
	 * @param ch to fine the index
	 * @param node to put in ch's index
	 */
	public void putChild(char ch, StrieNode node){
		// O(1)
		if (children[ch-'a']==null){
			children[ch-'a']=node;
			numChild++;
		}
		else if (children[ch-'a'].isRemoved()){
			children[ch-'a'].unsetRemoved();
			numChild++;
		}
	}
	
	/**
	 * get all 26 children (included null).
	 * @return children array
	 */
	public StrieNode[] getAllChildren(){
		// O(1)
		return children;
	}
	
	/**
	 * get the number of node's children.
	 * @return number children
	 */
	public int getNumChildren(){
		// O(1)
		return 26;
	}

	/**
	 * Sets the end marker to indicate the end of a string/word.
	 */
	public void setEnd(){
		// O(1)
		endMarker=true;
	}

	/**
	 * Unsets a previously set end marker.
	 */
	public void unsetEnd(){
		// O(1)
		endMarker=false;
	}

	/**
	 * Checks whether the current node is marked as the end of a string/word.
	 * @return boolean  true if markend is true, false otherwise
	 */	
	public boolean isEnd(){
		// O(1)
		return endMarker;
	}

	/**
	 * Check if node is empty.
	 * @return boolean  true if empty, false otherwise
	 */
	public boolean isEmptyNode(){
		// checks if a node is empty
		// O(1)
		return numChild==0;
	}
}
