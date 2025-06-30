package task7;

public interface Graph {
    int vertexCount();
    int edgeCount();
    void addEdge(int v1, int v2);
    void removeEdge(int v1, int v2);
    Iterable<Integer> adj(int v);
    default boolean isAdj(int v1, int v2) {
        for (Integer adj : adj(v1)) {
            if (adj == v2) {
                return true;
            }
        }
        return false;
    }
} 