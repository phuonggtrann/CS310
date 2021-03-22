//This is mostly done for you. The remaining part is to
//do just a little setup work for Dijkstra's shortest
//path algorithm and the setting up of the routing tables.
//See the two "YOUR CODE HERE" spots below.

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

import java.awt.Color;
import java.security.Key;
import java.util.Collection;
import java.util.PriorityQueue;

/**
 *  Runs the actual networking code for the simulation.
 *  
 *  @author Katherine (Raven) Russell
 */
class ThreeTenNetwork {
	/**
	 * set up dijkstras.
	 * @param graph to extract vertices
	 * @param startNode for algorithm
	 * @param queue to add vertices
	 * @param hostToDijkstraNode hashtable
	 */
	public static void setupDijkstras(Network graph, Host startNode, PriorityQueue<DijkstraNode> queue, HashTable<Host,DijkstraNode> hostToDijkstraNode) {
		Collection<Host> allVertices = graph.getVertices();
		for (Host h : allVertices) {
			DijkstraNode node = new DijkstraNode(h);
			if (node.host.equals(startNode)) {
				node.distance = 0;
			}
			queue.add(node);
			hostToDijkstraNode.add(h, node);
		}
	}
	/**
	 * set up table for Dijkstra.
	 * @param graph graph to extract vertices
	 * @param startNode for algorithm
	 * @param hostToDijkstraNode hashtable
	 */
	public static void setupTables(Network graph, Host startNode, HashTable<Host,DijkstraNode> hostToDijkstraNode) {
		//Get the routing table for the start node.
		RoutingTable route = startNode.getRoutingTable();
		//1. Get the DijkstraNode representation
		//2. Discover the next hop from the start node towards the other node
		//3. Add the pair (other host, next hop) to the routing table for the start node
		Collection<Host> allVertices = graph.getVertices();
		for (Host h : allVertices) {
			if (h.equals(startNode)) {
				continue;
			}
			DijkstraNode node = hostToDijkstraNode.get(h);
			if (node.parent==null) {
				continue;
			}
			while (node.parent!=null && !node.host.equals(startNode)) {
				node = node.parent;
			}
			route.add(h,node.host);
		}
	}
	
	//--------------------------------------------------------
	// DO NOT EDIT ANYTHING BELOW THIS LINE
	//--------------------------------------------------------
	
	/**
	 *  A node in Dijkstra's shortest path algothim needs to be
	 *  able to be marked as done and have a parent and a current
	 *  distance. This wraps a Host with those properties for
	 *  running the algorithm.
	 *  
	 *  @author Katherine (Raven) Russell
	 */
	private static class DijkstraNode implements Comparable<DijkstraNode> {
		/**
		 *  The host being wrapped.
		 */
		Host host;
		
		/**
		 *  The "parent" which will be discovered by Dijkstra's
		 *  algorithm.
		 */
		DijkstraNode parent;
		
		/**
		 *  Whether or not the host is "done" (finalized) when
		 *  running the algorithm.
		 */
		boolean done = false;
		
		/**
		 *  The "distance" a node is from the starting node.
		 *  Discovered by running Dijkstra's algorithm.
		 */
		int distance = Integer.MAX_VALUE;
		
		/**
		 *  Convenience constructor.
		 *  @param h the host to wrap
		 */
		public DijkstraNode(Host h) { this.host = h; }
		
		/**
		 *  Compares two nodes in the algorithm so that
		 *  nodes with the smallest "distance" are taken
		 *  out of the priority queue first. Ties are
		 *  broken by node id.
		 *  
		 *  @param other the other node to compare this one to
		 *  @return 0 if two nodes are equal, < 0 if this < other, > 0  if this > other
		 */
		@Override
		public int compareTo(DijkstraNode other) {
			if(this.distance == other.distance)
				return this.host.getId() - other.host.getId();
			return this.distance - other.distance;
		}
	}
	
	/**
	 *  The network graph to use.
	 */
	private Network graph;
	
	/**
	 *  Whether or not the routes have been computed.
	 */
	private boolean started = false;
	
	/**
	 *  The current location of a message being sent
	 *  in the network.
	 */
	private Host currentLoc = null;
	
	/**
	 *  The default color of a node.
	 */
	public static final Color COLOR_DEFAULT_NODE = Color.LIGHT_GRAY;
	
	/**
	 *  The default color of a node when it has a route, but
	 *  isn't being used.
	 */
	public static final Color COLOR_NONE_NODE = Color.WHITE;
	
	/**
	 *  The default color of an edge when it isn't being used.
	 */
	public static final Color COLOR_NONE_EDGE = Color.BLACK;
	
	/**
	 *  The color of an intermediate node when routing.
	 */
	public static final Color COLOR_DONE_NODE = Color.YELLOW;
	
	/**
	 *  The default color of a when a message failed to route.
	 */
	public static final Color COLOR_FAILED_NODE = Color.RED;
	
	/**
	 *  The default color of a node sending a message.
	 */
	public static final Color COLOR_SOURCE_NODE = Color.CYAN;
	
	/**
	 *  The default color of a node receiving a message.
	 */
	public static final Color COLOR_DEST_NODE = Color.GREEN;
	
	/**
	 *  The color of an edge being used for routing.
	 */
	public static final Color COLOR_DONE_EDGE = Color.CYAN.darker();
	
	/**
	 *  Resets the network with a new graph.
	 *  @param g the new network graph to use
	 */
	public void reset(Network g) {
		this.graph = g;
		//System.out.println(graph.getInternalTable());
		clean();
	}
	
	/**
	 *  Puts things back the way they were initially.
	 */
	private void clean() {
		started = false;
		currentLoc = null;
		for(Host v : graph.getVertices()) {
			v.setColor(COLOR_DEFAULT_NODE);
		}
		for(Connection e : graph.getEdges()) {
			e.setColor(COLOR_NONE_EDGE);
		}
	}
	
	/**
	 *  Does the routing in the network.
	 */
	public void start() {
		clean();
		for(Host v : graph.getVertices()) {
			//System.out.println("Running Dijkstra's from host " + v.toString());
			runDijkstra(v);
		}
		started = true;
	}
	
	/**
	 *  Runs Dijkstra's shortest path algorithm from a given starting
	 *  node and constructs its routing table.
	 *  @param startNode starting node for the algothim
	 */
	private void runDijkstra(Host startNode) {
		//This is the queue which picks the "next" node
		//to pick for Dijkstra's shortest path algothim.
		PriorityQueue<DijkstraNode> queue = new PriorityQueue<>();
		
		//"Map" the host to it's representation in Dijkstra's algorithm
		HashTable<Host,DijkstraNode> hostToDijkstraNode = new HashTable<>(10);
		
		//setup Dijkstra's shortest path algrothim
		setupDijkstras(graph, startNode, queue, hostToDijkstraNode);
		
		//This is Dijkstra's algothim... it's done for you.
		
		//Get the minimum node cost node that's still in the queue
		DijkstraNode currMin = queue.poll();
		
		//If there are more nodes that aren't "done"
		//(they're still in the queue)...
		while(currMin != null && currMin.distance != Integer.MAX_VALUE) {
			//get the edges from that node to anothers in the network
			Collection<Connection> outEdges = graph.getOutEdges(currMin.host);
			
			//update each connection
			for(Connection e : outEdges) {
				//get the node on the other side of the connection
				Host n = graph.getOpposite(currMin.host, e);
				DijkstraNode algNode = hostToDijkstraNode.get(n);
				
				//work out what the new cost would be
				int newCost = currMin.distance + e.getWeight();
				
				//update the other node if that node has not been
				//finished and the new cost is less than the distance
				if(!algNode.done && newCost < algNode.distance) {
					//there's no update, so just remove from
					//the queue, update it, and add it back in
					queue.remove(algNode);
					algNode.distance = newCost;
					algNode.parent = currMin;
					queue.add(algNode);
				}
			}
			
			//this node is now done
			currMin.done = true;
			
			//get a new node to work on
			currMin = queue.poll();
		}
		
		//Setup routing table for the start node...
		setupTables(graph, startNode, hostToDijkstraNode);
		
		//color
		startNode.setColor(COLOR_NONE_NODE);
	}
	
	/**
	 *  Takes the first "step" when routing a new message
	 *  from a source to a destination host in the network.
	 *  @param source the source host of the message
	 *  @param dest the destination host of the message
	 *  @return whether or not another step is needed
	 */
	public boolean step(Host source, Host dest) {
		currentLoc = source;
		for(Host h : graph.getVertices()) {
			h.setColor(COLOR_NONE_NODE);
		}
		for(Connection c : graph.getEdges()) {
			c.setColor(COLOR_NONE_EDGE);
		}
		
		if(source.equals(dest) && graph.findEdge(source, dest) == null) {
			source.setColor(COLOR_FAILED_NODE);
			return false;
		}
		
		currentLoc.setColor(COLOR_SOURCE_NODE);
		return true;
	}
	
	/**
	 *  Takes another "step" when routing a message
	 *  to a destination host in the network.
	 *  @param dest the destination host of the message
	 *  @return whether or not another step is needed
	 */
	public boolean step(Host dest) {
		if(!route(dest)) {
			finish(dest);
			return false;
		}
		return true;
	}
	
	/**
	 *  Routes the message one step further in the network.
	 *  @param dest the destination host of the message
	 *  @return whether or not the routing is done
	 */
	public boolean route(Host dest) {
		Host nextHop = currentLoc.getRoutingTable().get(dest);
		
		if(nextHop == null) return false;
		
		Connection c = graph.findEdge(currentLoc, nextHop);
		c.setColor(COLOR_DONE_EDGE);
		
		currentLoc = nextHop;
		currentLoc.setColor(COLOR_DONE_NODE);
		
		return !currentLoc.equals(dest);
	}
	
	/**
	 *  Colors hosts after the routing is done.
	 *  @param dest the destination host of the message
	 */
	public void finish(Host dest) {
		if(currentLoc.equals(dest)) {
			dest.setColor(COLOR_DEST_NODE);
		}
		else {
			currentLoc.setColor(COLOR_FAILED_NODE);
		}
	}
}
