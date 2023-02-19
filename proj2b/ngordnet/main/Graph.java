package ngordnet.main;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    //adjacency list to represent directed graph
    private final int V;         //number of vertices in this directed graph
    private int E;               //number of edges in this directed graph
    private List<Integer>[] adj; // adj[V] represent the adjacency list for vertex V
    public Graph(int V) {
        this.V = V;
        this.E = 0;
        this.adj = (List<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v = v + 1){
            adj[v] = new ArrayList<Integer>();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        this.E = this.E + 1;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }
}
