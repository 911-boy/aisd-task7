package task7;

import java.util.Scanner;

public class GraphUtils {
    public static Graph fromStr(String str, Class<? extends Graph> graphClass) throws Exception {
        Graph graph = graphClass.getDeclaredConstructor().newInstance();
        Scanner scanner = new Scanner(str);
        int vertexCount = scanner.nextInt();
        if (vertexCount > 0) {
            graph.addEdge(vertexCount - 1, vertexCount - 1);
            graph.removeEdge(vertexCount - 1, vertexCount - 1);
        }
        int edgeCount = scanner.nextInt();
        for (int i = 0; i < edgeCount; i++) {
            graph.addEdge(scanner.nextInt(), scanner.nextInt());
        }
        scanner.close();
        return graph;
    }
} 