package cs.graph;

import java.util.Arrays;

public class DisjointSet {

    int[] parent;
    int[] rank;
    int[] size;

    //initialize all
    public DisjointSet(int k) {
        //initialise rank array
        this.rank = new int[k];
        //initialize size array
        this.size = new int[k];
        Arrays.fill(size, 1);
        //initialise parent array
        this.parent = new int[k];
        for (int i = 0; i < k; i++) {
            parent[i] = i;
        }

    }

    public void groupByRank(int u, int v) {
        int up = getUltimateParent(u);
        int vp = getUltimateParent(v);
        //add smaller rank to larger rank
        if (rank[vp] == rank[up]) {
            parent[vp] = up;
            rank[up]++;
        }
        //attach smaller to larger rank & do not increase rank
        else if (rank[vp] > rank[up]) {
            parent[up] = vp;
        } else {
            parent[vp] = up;
        }
    }

    //u->child , v->parent
    public void groupBySize(int u, int v) {
        int up = getUltimateParent(u);
        int vp = getUltimateParent(v);
        //attach smaller to larger
        if (size[vp] > size[up]) {
            parent[up] = vp;
            size[vp] += size[up];
        } else {
            parent[vp] = up;
            size[up] += size[vp];
        }
    }

    //do path compression & return ultimateParent
    public int getUltimateParent(int v) {
        if (v == parent[v]) return v;
        parent[v] = getUltimateParent(parent[v]);
        return parent[v];
    }

    public boolean isInSameComponent(int u, int v) {
        return getUltimateParent(u) == getUltimateParent(v);
    }
}
