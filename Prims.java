import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Prims {

    public static void main(String[] args) throws IOException {
        int[][] graph = constructGraph();
        int[] parent = new int[graph.length];
        computeMinimumSpanningTree(graph, parent);
        printOutput(graph, parent);
    }

    public static void computeMinimumSpanningTree(int[][] graph, int[] parent) {
        boolean[] minimumSpanningTreeVertices = new boolean[graph.length];
        int[] minimumWeights = new int[graph.length];
        Arrays.fill(minimumWeights, Integer.MAX_VALUE);
        minimumWeights[1] = 1;

        for (int times = 0; times < graph.length - 1; times++) {
            int nextMinVertex = getNextMinVertex(minimumWeights, minimumSpanningTreeVertices);
            minimumSpanningTreeVertices[nextMinVertex] = true;
            for (int i = 1; i < graph.length; i++) {
                if (graph[nextMinVertex][i] != 0 && !minimumSpanningTreeVertices[i] && graph[nextMinVertex][i] < minimumWeights[i]) {
                    minimumWeights[i] = graph[nextMinVertex][i];
                    parent[i] = nextMinVertex;
                }
            }
        }
    }

    private static void printOutput(int[][] graph, int[] parent) {
        int minSpanningTreeWeight = 0;
        for (int i = 2; i < graph.length; i++) {
            minSpanningTreeWeight += graph[i][parent[i]];
        }
        System.out.print(minSpanningTreeWeight);
        for (int i = 2; i < graph.length; i++) {
            System.out.print("\n" + parent[i] + " " + i);
        }
    }

    private static int getNextMinVertex(int[] minimumWeights, boolean[] minimumSpanningTreeVertices) {
        int minWeight = Integer.MAX_VALUE;
        int minVertex = 0;

        for (int i = 0; i < minimumSpanningTreeVertices.length; i++) {
            if (!minimumSpanningTreeVertices[i] && minimumWeights[i] < minWeight) {
                minWeight = minimumWeights[i];
                minVertex = i;
            }
        }
        return minVertex;
    }

    private static int[][] constructGraph() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String vertexAndEdgeCountLine = reader.readLine();
        String[] vertexAndEdgeCountLineSplit = vertexAndEdgeCountLine.split("\\s+");
        int vertexCount = Integer.parseInt(vertexAndEdgeCountLineSplit[0]);
        int edgeCount = Integer.parseInt(vertexAndEdgeCountLineSplit[1]);
        int[][] graph = new int[vertexCount + 1][vertexCount + 1];

        for(int i = 0; i < edgeCount; i++) {
            String edgesAndWeightLine = reader.readLine();
            String[] edgesAndWeightLineSplit = edgesAndWeightLine.split("\\s+");

            int vertex1 = Integer.parseInt(edgesAndWeightLineSplit[0]);
            int vertex2 = Integer.parseInt(edgesAndWeightLineSplit[1]);
            int weight = Integer.parseInt(edgesAndWeightLineSplit[2]);

            graph[vertex1][vertex2] = weight;
            graph[vertex2][vertex1] = weight;
        }
        return graph;
    }
}