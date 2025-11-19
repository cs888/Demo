package cs.graph;

import java.util.Arrays;

public class DS {
    int[] parent;
    int[] rank;
    int[] size;

    public DS(int totalSize) {
        this.parent = new int[totalSize+1];
        this.rank = new int[totalSize+1];
        this.size = new int[totalSize+1];
        //initially parent is itself
        for (int i = 0; i < totalSize+1; i++) {
            parent[i]=i;
        }
        Arrays.fill(size,1);
    }

    public int getSize(int node) {
        return size[node];
    }

    public void unionByRank(int u, int v) {
        int up = getUltimateParent(u);
        int vp = getUltimateParent(v);
        if (up == vp) return;
        if (rank[up] > rank[vp]) {
            parent[vp] = up;
        } else if (rank[up] < rank[vp]) {
            parent[up] = vp;
        } else {
            parent[vp] = up;
            rank[up]++;
        }
    }

    public void unionBySize(int u, int v) {
        int up = getUltimateParent(u);
        int vp = getUltimateParent(v);
        if (up == vp) return;
        if (size[up] < size[vp]) {
            parent[up] = vp;
            size[vp]+=size[up];
        } else {
            parent[vp] = up;
            size[up]+=size[vp];
        }
    }

    public int getUltimateParent(int node) {
        if (node == parent[node]) return node;
        return parent[node] = getUltimateParent(parent[node]);
    }

    public boolean isInSameComponent(int u, int v) {
        return getUltimateParent(v) == getUltimateParent(u);
    }
}
