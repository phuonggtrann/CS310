import java.util.ArrayList;  // Use ArrayList, if you must, ONLY LOCALLY

import javax.management.RuntimeErrorException;

/**
 * Strie class.
 */
public class Strie{
	/**
	 *  Do NOT remove any method even if you are not implementing it.
	 * Root.
	 */ 
	private StrieNode root;  // the root of a strie
	/**
	 * Size of strie.
	 */
	private int size;

	/**
	 * constructor.
	 * initialize anything that needs initialization.
	 */
	public Strie(){
		root = new StrieNode();
		size = 0;
	}

	/**
	 * check if strie have any children.
	 * @param myStrie to check
	 * @return true if strie is empty false otherwise
	 */
	public static boolean isEmptyStrie(Strie myStrie){
		// O(1)
		return myStrie.size==0;
	}

	/**
	 * inserts word into Strie. 
	 * @param word to insert
	 */
	public void insert(String word){
		// word must contain characters between 'a' and 'z'. If not, throw RuntimeException with message "Invalid character entered. Characters must be between 'a' and 'z' "
		// O(n), where n is the number of characters in word
		StrieNode temp = root;
		for (int i=0; i<word.length(); i++){
			if (word.charAt(i)-'a' < 0 || word.charAt(i)-'a' > 25 || word.length()<1){
				throw new RuntimeException("Invalid character entered. Characters must be between 'a' and 'z' ");
			}
		}
		for (int i=0; i<word.length(); i++){
			temp.putChild(word.charAt(i), new StrieNode());
			temp = temp.getChild(word.charAt(i));
			if (i==word.length()-1) {
				temp.setEnd();
				temp.unsetRemoved();
			}
		}
		size++;
	}

	/**
	 * word must contain characters between 'a' and 'z', otherwise, returns false.
	 * @param word to search
	 * @return true if strie contains word
	 */
	public boolean contains(String word){
		// O(n), where n is the number of characters in word
		StrieNode temp = root;
		for (int i=0; i<word.length(); i++){
			if (word.charAt(i)-'a' < 0 || word.charAt(i)-'a' > 25){
				throw new RuntimeException("Invalid character entered. Characters must be between 'a' and 'z' ");
			}
		}
		for (int i=0; i<word.length(); i++){
			if (temp.getChild(word.charAt(i))==null || temp.getChild(word.charAt(i)).isRemoved()){
				return false;				
			}
			if (i==word.length()-1 && !temp.getChild(word.charAt(i)).isEnd()) {
				return false;
			}
			else {
				temp = temp.getChild(word.charAt(i));
			}
		}
		return true;
	}
	/**
	 * removes word from Strie.
	 * word must contain characters between 'a' and 'z', otherwise, returns false.
	 * @param word to remove
	 * @return true if sucess false otherwise
	 */
	public boolean remove(String word){
		// Check for valid input 
		for (int i=0; i<word.length(); i++){
			if (word.charAt(i)-'a' < 0 || word.charAt(i)-'a' > 25){
				throw new RuntimeException("Invalid character entered. Characters must be between 'a' and 'z' ");
			}
		}
		// Unset endmark last character
		StrieNode[] children = root.getAllChildren();
		ArrayList<StrieNode> parents = new ArrayList<>();
		for(int i=0; i<word.length();i++){
			if(children[word.charAt(i)-'a']==null || children[word.charAt(i)-'a'].isRemoved()){
				return false;
			}
			parents.add(0,children[word.charAt(i)-'a']);
			children=children[word.charAt(i)-'a'].getAllChildren();
		}
		if (activeChild(parents.get(0))==0) {
			parents.get(0).setRemoved();
		}
		parents.get(0).unsetEnd();
		for(int i=1; i<parents.size();i++){
			if (activeChild(parents.get(i))==0) {
				parents.get(i).setRemoved();
			}
		}
		
		return true;
	}
	/**
	 * Helper method to find active child.
	 * @param sn is node to find child
	 * @return number of active child
	 */
	private int activeChild(StrieNode sn) {
		int count=0;
		for (StrieNode s: sn.getAllChildren()) {
			if (s!=null && !s.isRemoved()) {
				count++;
			}
		}
		return count;
	}
	/**
	 * Do a Breadth First Traversal of myStrie.
	 * @param myStrie to do bfs
	 * @return the string built during traversal
	 */
	public static String levelOrderTraversal(Strie myStrie){
		String levelOrder = "";
		ArrayList<StrieNode> stries = new ArrayList<>();
		ArrayList<Integer> index = new ArrayList<>();

		// Get all root children
		StrieNode[] rootChild = myStrie.root.getAllChildren();
		for (int i=0; i<rootChild.length; i++) {
			if (rootChild[i]!=null && !rootChild[i].isRemoved()) {
				stries.add(rootChild[i]);
				index.add(i);
			}
		}
		
 		while (stries.size()>0){// Start adding char 
			StrieNode[] currentStrieChildren = stries.remove(0).getAllChildren();
			levelOrder = levelOrder + ((char)(index.remove(0)+97)) + " ";
			for (int i=0; i<currentStrieChildren.length; i++) {
				if (currentStrieChildren[i]!=null && !currentStrieChildren[i].isRemoved()) {
					stries.add(currentStrieChildren[i]);
					index.add(i);
				}
			}
		}
		return levelOrder.trim();
	}

	/**
	 * returns all words currently stored in myStrie.
	 * @param myStrie for search
	 * @return array of strings
	 */
	public static String[] getStrieWords(Strie myStrie){
 		return null;
	}

	/**
	 * get all possible surfix.
	 * @param myStrie to search
	 * @param query to search
	 * @return array of string
	 */
	public static String[] getAllSuffixes(Strie myStrie, String query){
 		return null;
	}

	/**
	 * get longest prefix.
	 * @param myStrie for search
	 * @param query for search
	 * @return the longest prefix stored in myStrie 
	 */
	public static String getLongestPrefix(Strie myStrie, String query){
		for (int i=0; i<query.length(); i++){
			if (query.charAt(i)-'a' < 0 || query.charAt(i)-'a' > 25){
				throw new RuntimeException("Invalid character entered. Characters must be between 'a' and 'z' ");
			}
		}
		StrieNode current = myStrie.root;
		String prefix ="";
		for (int i=0; i<query.length(); i++) {
			if (current.getAllChildren()[query.charAt(i)-'a'] == null || current.getAllChildren()[query.charAt(i)-'a'].isRemoved()){
				break;
			}
			prefix = prefix + String.valueOf(query.charAt(i));
			current = current.getAllChildren()[query.charAt(i)-'a'];
		}
		if (prefix.length()==0) {
			throw new RuntimeException("No prefix found!");
		}
		return prefix;
	}
	/**
	 * Returns the closest match(es) found in myStrie for the given quer.
	 * @param myStrie to get
	 * @param query to search
	 * @return array of strings
	 */
	public static String[] getClosestMatch(Strie myStrie, String query){
		return null;
	}

	// --------------------------------------------------------
	// example testing code... edit this as much as you want!
	// --------------------------------------------------------
	/**
	 * main method.
	 * @param args forr every main method
	 */
	public static void main(String[] args){
		Strie myStrie = new Strie();
		
		System.out.println(myStrie.root.isEmptyNode());
		if(isEmptyStrie(myStrie) && myStrie.root.isEmptyNode())
			System.out.println("Yay 1");

		myStrie.insert("a");
		StrieNode[] children = myStrie.root.getAllChildren();
		
		if(!isEmptyStrie(myStrie) && children[0].isEmptyNode() && children[0].isEnd() && myStrie.root.containsChar('a'))
			System.out.println("Yay 2");

		myStrie.insert("bat");
		myStrie.insert("bar");
		myStrie.insert("barn");
		myStrie.insert("cat");

		if(myStrie.contains("cat"))
			System.out.println("Yay 3");

		String res = Strie.levelOrderTraversal(myStrie).trim();
		String actualOut = "a b c a a r t t n";
		if(res.equals(actualOut))
			System.out.println("Yay 4");
			
		// String[] yourWords = Strie.getStrieWords(myStrie);
		// String[] allWords = {"a", "bar", "barn", "bat", "cat"};
		// int allMatches = 1;
		// for(int i = 0; i < yourWords.length; i++){
		// 	if(!yourWords[i].equals(allWords[i]))
		// 		allMatches = 0;
		// }	
		// if(allMatches == 1)
		// 	System.out.println("Yay 5");

		if(myStrie.remove("cat") && !myStrie.contains("cat"))
			System.out.println("Yay 6");
	}

}
