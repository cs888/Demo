package cs.graph;

import java.util.*;

public class Graph2 {

    private static int cityWithSmallerNumberOfNeighbourThreshold(List<List<EEdge>> adj, int distanceThreshold) {

        int[][] dist = new int[adj.size()][adj.size()];
        for (int i = 0; i < adj.size(); i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        for (int i = 0; i < adj.size(); i++) {
            for (EEdge EEdge : adj.get(i)) {
                dist[i][EEdge.getNode()] = EEdge.getDistance();
                dist[EEdge.getNode()][i] = EEdge.getDistance();
            }
        }
        for (int i = 0; i < adj.size(); i++) {
            dist[i][i] = 0;
        }
        for (int via = 0; via < dist.length; via++) {
            for (int i = 0; i < dist.length; i++) {
                for (int j = 0; j < dist.length; j++) {
                    if (dist[i][via] == Integer.MAX_VALUE || dist[via][j] == Integer.MAX_VALUE) continue;
                    dist[i][j] = Math.min(dist[i][j], dist[i][via] + dist[via][j]);
                }
            }
        }

        int min = Integer.MAX_VALUE;
        int count;
        for (int i = 0; i < dist.length; i++) {
            count = 0;
            for (int j = 0; j < dist[0].length; j++) {
                if (dist[i][j] <= distanceThreshold) count++;
            }
            min = Math.min(count, min);
        }
        return min;
    }

    //print path
    private static List<Integer> dijkstraShortestPath(int start, List<List<EEdge>> adj, int V) {
        PriorityQueue<EEdge> queue = new PriorityQueue<>(Comparator.comparingInt(EEdge::getDistance).thenComparing(EEdge::getNode));
        queue.add(new EEdge(start, 0));
        int dist[] = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        int parent[] = new int[V];
        //1. take out element from queue
        // 2. if sum of parent distance and currentNode distance is less than already stored at distance arry then update it
        //otherwise
        //keep repeating till queue is not empty
        while (!queue.isEmpty()) {
            EEdge u = queue.poll();
            for (EEdge v : adj.get(u.getNode()))
                //for u-w->v if dist[u]+w <dist[v] then update
                if (dist[u.getNode()] + v.getDistance() < dist[v.getNode()]) {
                    dist[v.getNode()] = dist[u.getNode()] + v.getDistance();
                    parent[v.getNode()] = u.getNode();
                    queue.add(v);
                }
        }
        List<Integer> ans = new ArrayList<>();
        //trace baack the parent
        ans.add(V - 1);
        int ind = V - 1;
        while (parent[ind] >= start) {
            ans.add(0, parent[ind]);
            ind = parent[ind];
        }
        return ans;

    }


}

class EdgeUVW {
    private int u;

    private int dist;
    private int v;

    public EdgeUVW(int dist, int u, int v) {
        this.dist = dist;
        this.u = u;
        this.v = v;
    }

    public int getDist() {
        return dist;
    }

    public int getU() {
        return u;
    }

    public int getV() {
        return v;
    }

    @Override
    public String toString() {
        return "Edges{" +
                "dist=" + dist +
                ", u=" + u +
                ", v=" + v +
                '}';
    }
}

class EEdge {

    private int dist;
    private int node;

    public int getNode() {
        return node;
    }

    public int getDistance() {
        return dist;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "node=" + node +
                ", dist=" + dist +
                '}';
    }

    public EEdge(int node, int dist) {
        this.node = node;
        this.dist = dist;
    }

}

