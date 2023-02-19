package ngordnet.main;
import java.util.*;

public class GraphTraversal {
    /**Graph helper function findhymNodes which do DFS in order find
     * keys of all hyponyms of the input words */
    public static Set<Integer> findhymNodes(Graph g, Set<Integer> id) {
        Set<Integer> hymNodes = new HashSet<Integer>();
        Dfs dfs = new Dfs(g,id);
        int n = 0;
        while (n <= g.V()-1) {
            if (dfs.marked(n)) {
                hymNodes.add(n);
            }
            n = n + 1;
        }
        return hymNodes;
    }
}
