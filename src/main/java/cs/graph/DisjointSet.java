package cs.graph;

import java.util.Arrays;
import java.util.List;

public class DisjointSet {

    static int[] parent;
    int[] rank;
    int[] size;

    //to test disjoint set
    public static void main(String[] args) {

        List<int[]> ints = Arrays.asList(new int[]{1, 2}, new int[]{2, 3}, new int[]{4, 5}, new int[]{6, 7}, new int[]{5, 6}, new int[]{3, 7});
        DisjointSet ds = new DisjointSet(8);

        parent[7] = 6;
        parent[6] = 5;
        parent[5] = 4;

        ds.getUltimateParent(7);
    }

    //initialize all
    public DisjointSet(int k) {
        this.rank = new int[k];
        this.parent = new int[k];
        for (int i = 0; i < k; i++) {
            parent[i] = i;
        }
        this.size = new int[k];
        Arrays.fill(size, 1);
    }

    public void groupByRank(int u, int v) {
        int up = getUltimateParent(u);
        int vp = getUltimateParent(v);
        if (rank[vp] > rank[up]) {
            parent[up] = vp;
        } else if (rank[vp] == rank[up]) {
            parent[vp] = up;
            rank[up]++;
        } else {
            parent[vp] = up;
        }
    }

    public void groupBySize(int u, int v) {
        int up = getUltimateParent(u);
        int vp = getUltimateParent(v);
        if (size[vp] > size[up]) {
            parent[up] = vp;
            size[vp] += size[up];
        } else {
            parent[vp] = up;
            size[up] += size[vp];
        }
    }

    //do path compression & return ultimateParent
    private int getUltimateParent(int v) {
        if (v == parent[v]) return v;
        parent[v] = getUltimateParent(parent[v]);
        return parent[v];
    }

    public boolean isInSameComponent(int u, int v) {
        return getUltimateParent(u) == getUltimateParent(v);
    }
}
