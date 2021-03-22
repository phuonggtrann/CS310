import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.graph.util.EdgeType;
import org.apache.commons.collections15.Factory;
import java.util.*;
/**
 * network class.
 */
public class Network implements Graph<Host,Connection>, DirectedGraph<Host,Connection> {
	/**
	 * max size of storage.
	 */
	private static final int MAX_NETWORK_SIZE = 255;
	/**
	 * can only be used.
	 */
	private ArrayOfListsOfPairs<Host,Connection> storage;
	/**
	 * array to keep track hosts.
	 */
	private ArrayList<Host> hosts;

	/**
	 * Class constructor for any initialization.
	 */
	public Network() {
		this.storage = new ArrayOfListsOfPairs<>(MAX_NETWORK_SIZE);
		this.hosts = new ArrayList<>();
	}
	
	/**
	 * Returns a view of all edges in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all edges in this graph
	 */
	public Collection<Connection> getEdges() {
		return this.storage.getAllValues();
	}
	
	/**
	 * Returns a view of all vertices in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all vertices in this graph
	 */
	public Collection<Host> getVertices() {
		return this.hosts;
	}
	
	/**
	 * Returns true if this graph's vertex collection contains vertex.
	 * Equivalent to getVertices().contains(vertex).
	 * @param vertex the vertex whose presence is being queried
	 * @return true iff this graph contains a vertex vertex
	 */
	public boolean containsVertex(Host vertex) {
		for (int i=0; i < this.hosts.size(); i++){
			if (this.hosts.get(i).equals(vertex)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if this graph's edge collection contains edge.
	 * Equivalent to getEdges().contains(edge).
	 * @param edge the edge whose presence is being queried
	 * @return true iff this graph contains an edge edge
	 */
	public boolean containsEdge(Connection edge) {
		ArrayList<Connection> edges = new ArrayList<>(getEdges());
		for (int i=0; i < edges.size(); i++){
			if (edges.get(i).equals(edge)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the number of edges in this graph.
	 * @return the number of edges in this graph
	 */
	public int getEdgeCount() {
		return getEdges().size();
	}
	
	/**
	 * Returns the number of vertices in this graph.
	 * @return the number of vertices in this graph
	 */
	public int getVertexCount() {
		return getVertices().size();
	}
	
	/**
	 * Returns a Collection view of the outgoing edges incident to vertex
	 * in this graph.
	 * @param vertex	the vertex whose outgoing edges are to be returned
	 * @return a Collection view of the outgoing edges incident 
	 * 				to vertex in this graph
	 */
	public Collection<Connection> getOutEdges(Host vertex) {
		ArrayList<Connection> outEdges =  new ArrayList<>();
		for (int i=0; i<this.storage.getAllPairs(vertex.getId()).size(); i++){
			outEdges.add(this.storage.getAllPairs(vertex.getId()).get(i).getValue());
		}
		return outEdges;
	}
	
	/**
	 * Returns a Collection view of the incoming edges incident to vertex
	 * in this graph.
	 * @param vertex	the vertex whose incoming edges are to be returned
	 * @return  a Collection view of the incoming edges incident 
	 * 				to vertex in this graph
	 */
	public Collection<Connection> getInEdges(Host vertex) {
		ArrayList<Connection> inEdges =  new ArrayList<>();
		for (int a=0; a<this.storage.getNumLists(); a++){
			for (int b=0; b<this.storage.getAllPairs(a).size(); b++) {
				if (this.storage.getAllPairs(a).get(b).getKey().equals(vertex)){
					inEdges.add(this.storage.getAllPairs(a).get(b).getValue());
				}
			}
		}
		return inEdges;
	}

	/**
	 * Returns a Collection view of the predecessors of vertex 
	 * in this graph.  A predecessor of vertex is defined as a vertex v 
	 * which is connected to 
	 * vertex by an edge e, where e is an outgoing edge of 
	 * v and an incoming edge of vertex.
	 * @param vertex	the vertex whose predecessors are to be returned
	 * @return  a Collection view of the predecessors of 
	 * 				vertex in this graph
	 */
	public Collection<Host> getPredecessors(Host vertex) {
		ArrayList<Host> predecessors =  new ArrayList<>();
		for (int a=0; a<this.storage.getNumLists(); a++){
			for (int b=0; b<this.storage.getAllPairs(a).size(); b++) {
				if (this.storage.getAllPairs(a).get(b).getKey().equals(vertex)){
					for (Host h : this.hosts) {
						if (h.getId() == a) {
							predecessors.add(h);
						}
					}
				}
			}
		}
		return predecessors;
	}
	
	/**
	 * Returns a Collection view of the successors of vertex 
	 * in this graph.  A successor of vertex is defined as a vertex v 
	 * which is connected to 
	 * vertex by an edge e, where e is an incoming edge of 
	 * v and an outgoing edge of vertex.
	 * @param vertex	the vertex whose predecessors are to be returned
	 * @return  a Collection view of the successors of 
	 * 				vertex in this graph
	 */
	public Collection<Host> getSuccessors(Host vertex) {
		ArrayList<Host> successors =  new ArrayList<>();
		for (int i=0; i<this.storage.getAllPairs(vertex.getId()).size(); i++){
			successors.add(this.storage.getAllPairs(vertex.getId()).get(i).getKey());
		}
		return successors;
	}
	
	/**
	 * If directedEdge is a directed edge in this graph, returns the source; 
	 * otherwise returns null. 
	 * The source of a directed edge d is defined to be the vertex for which  
	 * d is an outgoing edge.
	 * directedEdge is guaranteed to be a directed edge if 
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge the edge to get the source of
	 * @return  the source of directedEdge if it is a directed edge in this graph, or null otherwise
	 */
	public Host getSource(Connection directedEdge) {
		if (!getEdgeType(directedEdge).equals(EdgeType.DIRECTED)) {
			return null;
		}
		ArrayList<Connection> edges;
		for (Host h : this.hosts) {
			edges = new ArrayList<>(getOutEdges(h));
			for (Connection c : edges) {
				if (c.equals(directedEdge)) {
					return h;
				}
			}			
		}
		return null;
	}

	/**
	 * If directedEdge is a directed edge in this graph, returns the destination; 
	 * otherwise returns null. 
	 * The destination of a directed edge d is defined to be the vertex 
	 * incident to d for which  
	 * d is an incoming edge.
	 * directedEdge is guaranteed to be a directed edge if 
	 * its EdgeType is DIRECTED. 
	 * @param directedEdge the edge to get the destination of
	 * @return  the destination of directedEdge if it is a directed edge in this graph, or null otherwise
	 */
	public Host getDest(Connection directedEdge) {
		if (!getEdgeType(directedEdge).equals(EdgeType.DIRECTED)) {
			return null;
		}
		ArrayList<Connection> edges;
		for (Host h : this.hosts) {
			edges = new ArrayList<>(getInEdges(h));
			for (Connection c : edges) {
				if (c.equals(directedEdge)) {
					return h;
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns an edge that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting 
	 * v1 to v2), any of these edges 
	 * may be returned.  findEdgeSet(v1, v2) may be 
	 * used to return all such edges.
	 * Returns null if either of the following is true:
	 * <ul>
	 * <li/>v1 is not connected to v2
	 * <li/>either v1 or v2 are not present in this graph
	 * </ul> 
	 * For purposes of this method, v1 is only considered to be connected to
	 * v2 via a given directed edge e if
	 * v1 == e.getSource() && v2 == e.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if 
	 * u is incident to both v1 and v2.)
	 * @param v1 vertex 1
	 * @param v2 vertex 2
	 * @return  an edge that connects v1 to v2, 
	 * 				or null if no such edge exists (or either vertex is not present)
	 * @see Hypergraph#findEdgeSet(Object, Object) 
	 */
	public Connection findEdge(Host v1, Host v2) {
		int count = 0;
		if (!this.hosts.contains(v1) || !this.hosts.contains(v2)) {
			return null;
		}
		ArrayList<KeyValuePair<Host,Connection>> neighbor = this.storage.getAllPairs(v1.getId());
		for (KeyValuePair<Host,Connection> kvp : neighbor) {
			if (kvp.getKey().equals(v2)) {
				count++;
				return kvp.getValue();
			}
		}
		if (count==0) {
			return null;
		}
 		return null;
	}

	/**
	 * Adds edge e to this graph such that it connects 
	 * vertex v1 to v2.
	 * If this graph does not contain v1, v2, 
	 * or both, implementations may choose to either silently add 
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If this graph assigns edge types to its edges, the edge type of
	 * e will be the default for this graph.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure.
	 * @param e the edge to be added
	 * @param v1 the first vertex to be connected
	 * @param v2 the second vertex to be connected
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object, EdgeType)
	 */
	public boolean addEdge(Connection e, Host v1, Host v2) {
		boolean isContainHost1 = false;
		boolean isContainHost2 = false;
		for (Host h : this.hosts){
			if (h.equals(v1)) {
				isContainHost1 = true;
			}
			else if (h.equals(v2)) {
				isContainHost2 = true;
			}
		}
		// Silently add vertex
		if (!isContainHost1) {
			addVertex(v1);
		}
		if (!isContainHost2) {
			addVertex(v2);
		}
		return this.storage.add(v1.getId(), v2, e);
	}
	
	/**
	 * Adds vertex to this graph.
	 * Fails if vertex is null or already in the graph.
	 * 
	 * @param vertex	the vertex to add
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if vertex is null
	 */
	public boolean addVertex(Host vertex) {
		if (vertex == null){
			throw new IllegalArgumentException("Vertex is null");
		}
		if (this.hosts.contains(vertex)) {
			return false;
		}
		this.hosts.add(vertex);
		return true;
	}

	/**
	 * Removes edge from this graph.
	 * Fails if edge is null, or is otherwise not an element of this graph.
	 * 
	 * @param edge the edge to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeEdge(Connection edge) {
		boolean isEdge = false;
		if (edge == null) {
			return false;
		}
		return this.storage.removeValue(edge);
	}
	
	/**
	 * Removes vertex from this graph.
	 * @param vertex the vertex to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeVertex(Host vertex) {
		boolean isContain = false;
		if (vertex==null) {
			return false;
		}
		if (!this.hosts.contains(vertex)) {
			return false;
		}
		// Deleting vertex
		this.hosts.remove(vertex);
		// Deleting outedges
		this.storage.getStorage()[vertex.getId()] = null;
		// deleting inedges
		return this.storage.removeKey(vertex);
	}
	
	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------
	/**
	 * tostring method.
	 * @return tostring of host
	 */
	public String toString() {
		return super.toString();
	}
	
	/**
	 * typical java main method.	
	 * @param args for every main method
	 */
	public static void main(String[] args) {
		//create a set of 10 nodes and 10 edges to test with
		Host[] hosts = new Host[10];
		Factory<Host> hostFactory = Host.getFactory();
		for(int i = 0; i < hosts.length; i++) {
			hosts[i] = hostFactory.create();
		}
		
		Connection[] connections = new Connection[10];
		Factory<Connection> connFactory = Connection.getFactory();
		for(int i = 0; i < connections.length; i++) {
			connections[i] = connFactory.create();
		}
		
		//constructs a graph
		Network graph = new Network();
		graph.addVertex(hosts[0]);
		graph.addVertex(hosts[1]);
		graph.addEdge(connections[0],hosts[0],hosts[1]);
		
		//get the internal structure
		ArrayOfListsOfPairs<Host,Connection> intTable = graph.getInternalTable();
		
		//get the entries for host0
		ArrayList<KeyValuePair<Host,Connection>> pairs = intTable.getAllPairs(0);
		
		//there should be only one pair
		KeyValuePair<Host,Connection> pair = pairs.get(0);
		
		//make sure it's an entry connecting to host[1] using connection[0]
		if(pair.getKey().equals(hosts[1]) && pair.getValue().equals(connections[0])) {
			System.out.println("Yay");
		}
		// phuong testing start here
		// Test edge type
		System.out.println(graph.getEdgeType(connections[1]));
		// Test vertex count: 3
		System.out.println("Test vertex count: ");
		graph.addVertex(hosts[2]);
		System.out.println(graph.getVertexCount()); 
		// Test edge count: 
		System.out.println("Test edge count: ");
		graph.addEdge(connections[1], hosts[0], hosts[2]);
		graph.addEdge(connections[2], hosts[1], hosts[0]);
		graph.addEdge(connections[3], hosts[1], hosts[2]); 
		System.out.println(graph.getEdgeCount()); // edge count = 4
		// Test remove edge: 3
		System.out.println("Test remove edge: ");
		graph.removeEdge(connections[3]);
		System.out.println(graph.getEdgeCount());
		// Test remove vertex
		System.out.println("Test remove vertex: ");
		graph.addEdge(connections[3], hosts[1], hosts[2]); // readd for testing
		graph.removeVertex(hosts[0]);
		System.out.println(graph.getVertexCount());
		System.out.println(graph.getEdgeCount());
		graph.addVertex(hosts[0]);
		graph.addEdge(connections[0],hosts[0],hosts[1]);
		graph.addEdge(connections[1], hosts[0], hosts[2]);
		graph.addEdge(connections[2], hosts[1], hosts[0]);
		// Test getPred
		System.out.print("Test get pred: ");
		System.out.println(graph.getPredecessors(hosts[1]).toString());
		System.out.println(graph.isPredecessor(hosts[0],hosts[1]));
		// Test getSuc 
		System.out.println("Test get suc: ");
		System.out.println(graph.getSuccessors(hosts[0]).toString());
		System.out.println(graph.isSuccessor(hosts[1],hosts[0]));
		// Test find edge:
		System.out.println("Test find edge:");
		System.out.println(graph.getSource(connections[0]));
		System.out.println(graph.getDest(connections[0]));
		boolean test = (hosts[0].equals(graph.getSource(connections[0])) && hosts[1].equals(graph.getDest(connections[0])));
		System.out.println(test);
		System.out.println(connections[0].equals(graph.findEdge(hosts[0], hosts[1])));
	}

	
	//********************************************************************************
	// YOU MAY, BUT DON'T NEED TO, EDIT THINGS IN THIS SECTION, BUT DON'T BREAK IT...
	// THERE ARE MUCH MORE OPTIMAL WAYS TO DO MANY OF THESE METHODS, SO IT MIGHT BE
	// GOOD TO LOOK HERE IF YOUR CODE IS SLOWER THAN IT NEEDS TO BE.
	//********************************************************************************
	
	/**
	 * Returns true if v1 is a predecessor of v2 in this graph.
	 * Equivalent to v1.getPredecessors().contains(v2).
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a predecessor of v2, and false otherwise.
	 */
	public boolean isPredecessor(Host v1, Host v2) {
		return getPredecessors(v2).contains(v1);
	}
	
	/**
	 * Returns true if v1 is a successor of v2 in this graph.
	 * Equivalent to v1.getSuccessors().contains(v2).
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a successor of v2, and false otherwise.
	 */
	public boolean isSuccessor(Host v1, Host v2) {
		return getSuccessors(v2).contains(v1);
	}

	/**
	 * Returns the endpoints of edge.
	 * @param edge the edge whose endpoints are to be returned
	 * @return the endpoints (incident vertices) of edge
	 */
	public Pair<Host> getEndpoints(Connection edge) {
		//System.out.println(getSource(edge).getId() + "---" + edge + "---" + getDest(edge).getId());
		return new Pair<Host>(getSource(edge), getDest(edge));
	}

	/**
	 * Returns true if vertex and edge 
	 * are incident to each other.
	 * Equivalent to getIncidentEdges(vertex).contains(edge) and to
	 * getIncidentVertices(edge).contains(vertex).
	 * @param vertex aka host
	 * @param edge aka connection
	 * @return true if vertex and edge are incident to each other
	 */
	public boolean isIncident(Host vertex, Connection edge) {
		return getIncidentEdges(vertex).contains(edge);
	}

	/**
	 * Returns true if v1 and v2 share an incident edge.
	 * Equivalent to getNeighbors(v1).contains(v2).
	 * 
	 * @param v1 the first vertex to test
	 * @param v2 the second vertex to test
	 * @return true if v1 and v2 share an incident edge
	 */
	public boolean isNeighbor(Host v1, Host v2) {
		return getNeighbors(v1).contains(v2);
	}

	/**
	 * Returns the collection of vertices which are connected to vertex
	 * via any edges in this graph.
	 * If vertex is connected to itself with a self-loop, then 
	 * it will be included in the collection returned.
	 * 
	 * @param vertex the vertex whose neighbors are to be returned
	 * @return  the collection of vertices which are connected to vertex, 
	 * 				or null if vertex is not present
	 */
	public Collection<Host> getNeighbors(Host vertex) {
		if(!containsVertex(vertex)) return null;
		ArrayList<Host> neighbors = new ArrayList<>();
		neighbors.addAll(getSuccessors(vertex));
		neighbors.addAll(getPredecessors(vertex));
		
		Connection c = findEdge(vertex, vertex);
		if(c != null) neighbors.remove(vertex);
		
		return neighbors;
	}
	
	/**
	 * Returns the collection of edges in this graph which are connected to vertex.
	 * 
	 * @param vertex the vertex whose incident edges are to be returned
	 * @return  the collection of edges which are connected to vertex, 
	 * 				or null if vertex is not present
	 */
	public Collection<Connection> getIncidentEdges(Host vertex) {
		if(!containsVertex(vertex)) return null;
		ArrayList<Connection> edges = new ArrayList<>();
		edges.addAll(getOutEdges(vertex));
		edges.addAll(getInEdges(vertex));
		
		Connection c = findEdge(vertex, vertex);
		if(c != null) edges.remove(c);
		
		return edges;
	}
	
	/**
	 * Returns the number of incoming edges incident to vertex.
	 * Equivalent to getInEdges(vertex).size().
	 * @param vertex	the vertex whose indegree is to be calculated
	 * @return  the number of incoming edges incident to vertex
	 */
	public int inDegree(Host vertex) {
		return getInEdges(vertex).size();
	}

	/**
	 * Returns the number of vertices that are adjacent to vertex
	 * (that is, the number of vertices that are incident to edges in vertex's
	 * incident edge set).
	 * 
	 * <p>Equivalent to getNeighbors(vertex).size().
	 * @param vertex the vertex whose neighbor count is to be returned
	 * @return the number of neighboring vertices
	 */
	public int getNeighborCount(Host vertex) {
		return getNeighbors(vertex).size();
	}
	
	/**
	 * Returns the number of edges incident to vertex.  
	 * Special cases of interest:
	 * <ul>
	 * <li/> Incident self-loops are counted once.
	 * <li> If there is only one edge that connects this vertex to
	 * each of its neighbors (and vice versa), then the value returned 
	 * will also be equal to the number of neighbors that this vertex has
	 * (that is, the output of getNeighborCount).
	 * <li> If the graph is directed, then the value returned will be 
	 * the sum of this vertex's indegree (the number of edges whose 
	 * destination is this vertex) and its outdegree (the number
	 * of edges whose source is this vertex), minus the number of
	 * incident self-loops (to avoid double-counting).
	 * </ul>
	 * Equivalent to getIncidentEdges(vertex).size().
	 * 
	 * @param vertex the vertex whose degree is to be returned
	 * @return the degree of this node
	 * @see Hypergraph#getNeighborCount(Object)
	 */
	public int degree(Host vertex) {
		return getIncidentEdges(vertex).size();
	}
	
	/**
	 * Returns the number of outgoing edges incident to vertex.
	 * Equivalent to getOutEdges(vertex).size().
	 * @param vertex	the vertex whose outdegree is to be calculated
	 * @return  the number of outgoing edges incident to vertex
	 */
	public int outDegree(Host vertex) {
		return getOutEdges(vertex).size();
	}

	/**
	 * Returns the number of predecessors that vertex has in this graph.
	 * Equivalent to vertex.getPredecessors().size().
	 * @param vertex the vertex whose predecessor count is to be returned
	 * @return  the number of predecessors that vertex has in this graph
	 */
	public int getPredecessorCount(Host vertex) {
		return getPredecessors(vertex).size();
	}
	
	/**
	 * Returns the number of successors that vertex has in this graph.
	 * Equivalent to vertex.getSuccessors().size().
	 * @param vertex the vertex whose successor count is to be returned
	 * @return  the number of successors that vertex has in this graph
	 */
	public int getSuccessorCount(Host vertex) {
		return getSuccessors(vertex).size();
	}
	
	/**
	 * Returns the vertex at the other end of edge from vertex.
	 * (That is, returns the vertex incident to edge which is not vertex.)
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return the vertex at the other end of edge from vertex
	 */
	public Host getOpposite(Host vertex, Connection edge) {
		Pair<Host> p = getEndpoints(edge);
		if(p.getFirst().equals(vertex)) {
			return p.getSecond();
		}
		else {
			return p.getFirst();
		}
	}
	
	/**
	 * Returns all edges that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting 
	 * v1 to v2), any of these edges 
	 * may be returned.  findEdgeSet(v1, v2) may be 
	 * used to return all such edges.
	 * Returns null if v1 is not connected to v2.
	 * <br/>Returns an empty collection if either v1 or v2 are not present in this graph.
	 *  
	 * <p><b>Note</b>: for purposes of this method, v1 is only considered to be connected to
	 * v2 via a given <i>directed</i> edge d if
	 * v1 == d.getSource() && v2 == d.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if 
	 * u is incident to both v1 and v2.)
	 * @param v1 first vertex
	 * @param v2 second vertex
	 * @return  a collection containing all edges that connect v1 to v2, or null if either vertex is not present
	 * @see Hypergraph#findEdge(Object, Object) 
	 */
	public Collection<Connection> findEdgeSet(Host v1, Host v2) {
		Connection edge = findEdge(v1, v2);
		if(edge == null) {
			return null;
		}
		
		ArrayList<Connection> ret = new ArrayList<>();
		ret.add(edge);
		return ret;
		
	}
	
	/**
	 * Returns true if vertex is the source of edge.
	 * Equivalent to getSource(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the source of edge
	 */
	public boolean isSource(Host vertex, Connection edge) {
		return getSource(edge).equals(vertex);
	}
	
	/**
	 * Returns true if vertex is the destination of edge.
	 * Equivalent to getDest(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the destination of edge
	 */
	public boolean isDest(Host vertex, Connection edge) {
		return getDest(edge).equals(vertex);
	}
	
	/**
	 * Returns the collection of vertices in this graph which are connected to edge.
	 * Note that for some graph types there are guarantees about the size of this collection
	 * (i.e., some graphs contain edges that have exactly two endpoints, which may or may 
	 * not be distinct).  Implementations for those graph types may provide alternate methods 
	 * that provide more convenient access to the vertices.
	 * 
	 * @param edge the edge whose incident vertices are to be returned
	 * @return  the collection of vertices which are connected to edge, 
	 * 				or null if edge is not present
	 */
	public Collection<Host> getIncidentVertices(Connection edge) {
		if(!containsEdge(edge)) return null;
		
		ArrayList<Host> vert = new ArrayList<>();
		
		Host source = getSource(edge);
		Host dest = getDest(edge);
		
		vert.add(source);
		if(!source.equals(dest)) vert.add(dest);
		
		return vert;
	}
	
	/**
	 * Returns the number of edges of type edgeType in this graph.
	 * @param edgeType the type of edge for which the count is to be returned
	 * @return the number of edges of type edgeType in this graph
	 */
	public int getEdgeCount(EdgeType edgeType) {
		if(edgeType == EdgeType.DIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}
	
	/**
	 * Returns the collection of edges in this graph which are of type edgeType.
	 * @param edgeType the type of edges to be returned
	 * @return the collection of edges which are of type edgeType, or
	 * 				null if the graph does not accept edges of this type
	 * @see EdgeType
	 */
	public Collection<Connection> getEdges(EdgeType edgeType) {
		if(edgeType == EdgeType.DIRECTED) {
			return getEdges();
		}
		return null;
	}
	
	/**
	 * Adds edge e to this graph such that it connects vertex v1 to v2.
	 * @param e the edge to be added
	 * @param v1 the first vertex to be connected
	 * @param v2 the second vertex to be connected
	 * @param edgeType the type to be assigned to the edge
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object)
	 */
	public boolean addEdge(Connection e, Host v1, Host v2, EdgeType edgeType) {
		//NOTE: Only directed edges allowed
		
		if(edgeType == EdgeType.UNDIRECTED) {
			throw new IllegalArgumentException();
		}
		
		return addEdge(e, v1, v2);
	}
	
	/**
	 * Adds edge to this graph.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph 
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * </ul>
	 * 
	 * @param edge connection bt 2 vertices
	 * @param vertices aka hosts
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null, 
	 * 				or if a different vertex set in this graph is already connected by edge, 
	 * 				or if vertices are not a legal vertex set for edge 
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(Connection edge, Collection<? extends Host> vertices) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		Host[] vs = (Host[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}

	/**
	 * Adds edge to this graph with type edgeType.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph 
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * <li/>edgeType is not legal for this graph
	 * </ul>
	 * 
	 * @param edge connection bt 2 vertices
	 * @param vertices aka hosts
	 * @param edgeType directed/undirected
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null, 
	 * 				or if a different vertex set in this graph is already connected by edge, 
	 * 				or if vertices are not a legal vertex set for edge 
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(Connection edge, Collection<? extends Host> vertices, EdgeType edgeType) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		Host[] vs = (Host[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgeType);
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE EXCEPT TO ADD/CORRECT JAVADOCS
	//********************************************************************************
	
	/**
	 * This will be used to check that you are setting the storage up correctly.
	 * @return storage of the hash table
	 */
	public ArrayOfListsOfPairs<Host,Connection> getInternalTable() {
		return storage;
	}
	
	/**
	 * Returns a {@code Factory} that creates an instance of this graph type.
	 * @return a {@code Factory} that creates an instance of this graph type
	 */
	@SuppressWarnings("unchecked")
	public static Factory<Graph<Host,Connection>> getFactory() { 
		return new Factory<Graph<Host,Connection>> () {
			public Graph<Host,Connection> create() {
				return (Graph<Host,Connection>) new Network();
			}
		};
	}
	
	/**
	 * Returns the edge type of edge in this graph.
	 * @param edge connection bt 2 vertices
	 * @return the EdgeType of edge, or null if edge has no defined type
	 */
	public EdgeType getEdgeType(Connection edge) {
		return EdgeType.DIRECTED;
	}
	
	/**
	 * Returns the default edge type for this graph.
	 * 
	 * @return the default edge type for this graph
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.DIRECTED;
	}
	
	/**
	 * Returns the number of vertices that are incident to edge.
	 * For hyperedges, this can be any nonnegative integer; for edges this
	 * must be 2 (or 1 if self-loops are permitted). 
	 * 
	 * <p>Equivalent to getIncidentVertices(edge).size().
	 * @param edge the edge whose incident vertex count is to be returned
	 * @return the number of vertices that are incident to edge.
	 */
	public int getIncidentCount(Connection edge) {
		return 2;
	}
}
