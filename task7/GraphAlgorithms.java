package task7;

import java.util.*;

public class GraphAlgorithms {
    /**
     * Поиск длины кратчайшего цикла в неориентированном графе
     * @param graph граф
     * @return длина кратчайшего цикла или -1, если циклов нет
     */
    public static int shortestCycleLength(Graph graph) {
        int n = graph.vertexCount();
        int minCycle = Integer.MAX_VALUE;
        for (int start = 0; start < n; start++) {
            int[] dist = new int[n];
            Arrays.fill(dist, -1);
            Queue<Integer> queue = new LinkedList<>();
            queue.add(start);
            dist[start] = 0;
            int[] parent = new int[n];
            Arrays.fill(parent, -1);
            while (!queue.isEmpty()) {
                int u = queue.poll();
                for (int v : graph.adj(u)) {
                    if (dist[v] == -1) {
                        dist[v] = dist[u] + 1;
                        parent[v] = u;
                        queue.add(v);
                    } else if (parent[u] != v) {
                        // найден цикл
                        minCycle = Math.min(minCycle, dist[u] + dist[v] + 1);
                    }
                }
            }
        }
        return minCycle == Integer.MAX_VALUE ? -1 : minCycle;
    }
} 