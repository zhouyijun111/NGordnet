package ngordnet.main;

public class Dfs {
    private boolean[] marked;  // marked[v] = true if v can be reached from sources

    public Dfs (Graph G, Iterable<Integer> sources) {
        this.marked = new boolean[G.V()];
        for (int v : sources) {
            if (!this.marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Graph G, int v) {
        this.marked[v] = true;
        for (int w : G.adj(v)) {
            if (!this.marked[w]) {
                dfs(G, w);
            }
        }
    }

    public boolean marked(int v) {
        return this.marked[v];
    }
}
