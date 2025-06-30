package task7;

import java.util.Arrays;
import java.util.Iterator;

public class AdjMatrixGraph implements Graph {
    private boolean[][] adjMatrix = null;
    private int vCount = 0;
    private int eCount = 0;

    public AdjMatrixGraph(int vertexCount) {
        adjMatrix = new boolean[vertexCount][vertexCount];
        vCount = vertexCount;
    }

    public AdjMatrixGraph() {
        this(0);
    }

    @Override
    public int vertexCount() {
        return vCount;
    }

    @Override
    public int edgeCount() {
        return eCount;
    }

    @Override
    public void addEdge(int v1, int v2) {
        int maxV = Math.max(v1, v2);
        if (maxV >= vertexCount()) {
            adjMatrix = Arrays.copyOf(adjMatrix, maxV + 1);
            for (int i = 0; i <= maxV; i++) {
                adjMatrix[i] = i < vCount ? Arrays.copyOf(adjMatrix[i], maxV + 1) : new boolean[maxV + 1];
            }
            vCount = maxV + 1;
        }
        if (!adjMatrix[v1][v2]) {
            adjMatrix[v1][v2] = true;
            eCount++;
            adjMatrix[v2][v1] = true;
        }
    }

    @Override
    public void removeEdge(int v1, int v2) {
        if (adjMatrix[v1][v2]) {
            adjMatrix[v1][v2] = false;
            eCount--;
            adjMatrix[v2][v1] = false;
        }
    }

    @Override
    public Iterable<Integer> adj(int v) {
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    int next = findNext(0);
                    private int findNext(int start) {
                        for (int i = start; i < vCount; i++) {
                            if (adjMatrix[v][i]) return i;
                        }
                        return -1;
                    }
                    @Override
                    public boolean hasNext() {
                        return next != -1;
                    }
                    @Override
                    public Integer next() {
                        int res = next;
                        next = findNext(next + 1);
                        return res;
                    }
                };
            }
        };
    }
} 