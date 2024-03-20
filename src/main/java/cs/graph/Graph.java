package cs.graph;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    public static void main(String[] args) {
        List<List<Edge>> wadj = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            wadj.add(i, new ArrayList<>());
        }

        wadj.get(0).addAll(List.of(new Edge(1, 2), new Edge(2, 1)));
        wadj.get(1).addAll(List.of(new Edge(0, 2), new Edge(2, 1)));
        wadj.get(2).addAll(List.of(new Edge(0, 1), new Edge(1, 1), new Edge(3, 2), new Edge(4, 2)));
        wadj.get(3).addAll(List.of(new Edge(2, 2), new Edge(4, 1)));
        wadj.get(4).addAll(List.of(new Edge(2, 2), new Edge(3, 1)));


        List<List<Integer>> edges =
                List.of(
                        List.of(1, 1, 4),
                        List.of(2, 1, 2),
                        List.of(3, 2, 3),
                        List.of(3, 2, 4),
                        List.of(4, 1, 5),
                        List.of(5, 3, 4),
                        List.of(7, 2, 6),
                        List.of(8, 3, 6),
                        List.of(9, 4, 5)
                );

        List<List<String>> accounts = List.of(
                List.of("John", "johnsmith@mail.com", "john_newyork@mail.com"),
                List.of("John", "johnsmith@mail.com", "john00@mail.com"),
                List.of("Mary", "mary@mail.com"),
                List.of("Johny", "johnnybravo@mail.com")
        );

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            adj.add(i, new ArrayList<>());
        }

        adj.get(0).addAll(Arrays.asList(1, 2, 3));
        adj.get(1).addAll(Arrays.asList(0));
        adj.get(2).addAll(Arrays.asList(0, 3, 4, 5));
        adj.get(3).addAll(Arrays.asList(0, 2));
        adj.get(4).addAll(Arrays.asList(2, 6));
        adj.get(5).addAll(Arrays.asList(2, 6));
        adj.get(6).addAll(Arrays.asList(4, 5));


        int g[][] = {{1, 1}, {0, 1}};

        int i = maxConnection(g);
        System.out.println(i);

    }

    //video - 56
    private static int numberOfArticulationPoint(int V, List<List<Integer>> adj) {
        boolean vis[] = new boolean[V];
        int tin[] = new int[V];
        int low[] = new int[V];
        List<List<Integer>> ans = new ArrayList<>();
        for (int node = 0; node < V; node++) {
            if (!vis[node]) {
                doDfsArticulationPoint(node, vis, adj, low, ans, 1, -1, tin);
            }
        }
        //for start node 0
        return ans.size() + adj.get(0).size() > 1 ? 1 : 0;
    }

    private static void doDfsArticulationPoint(int node, boolean[] vis, List<List<Integer>> adj, int[] low, List<List<Integer>> ans, int insertionTime, int parent, int[] tin) {
        vis[node] = true;
        tin[node] = low[node] = insertionTime;
        for (int it : adj.get(node)) {
            if (it == parent) continue;
            if (!vis[it]) {
                doDfsArticulationPoint(it, vis, adj, low, ans, insertionTime + 1, node, tin);
                low[node] = Math.min(low[node],low[it]);
                //do not apply to root
                // or low[it]>=in[node]
                //& do not apply to first node
                if (tin[node] <= low[it] && parent != -1) ans.add(List.of(node));
            } else {
                low[node] = Math.min(low[node],tin[it]);
            }
        }
    }

    //video - 55
    //Targen algo
    //logic : if (tin[node] < low[it]) then it is bridge i.e cannot reach back
    // trace all node except parent
    public static int numberOfBridges(int V, List<List<Integer>> edges) {

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(i, new ArrayList<>());
        }
        for (List<Integer> edge : edges) {
            adj.get(edge.get(0)).add(edge.get(1));
        }

        boolean vis[] = new boolean[V];
        int tin[] = new int[V];
        int low[] = new int[V];
        List<List<Integer>> ans = new ArrayList<>();
        for (int node = 0; node < V; node++) {
            if (!vis[node]) {
                doDFSforbridge(node, vis, adj, low, ans, 1, -1, tin);
            }
        }
        return ans.size();
    }

    private static void doDFSforbridge(int node, boolean[] vis, List<List<Integer>> adj, int[] low, List<List<Integer>> ans, int insertionTime, int parent, int[] tin) {
        vis[node] = true;
        //min insertion of all element apart from parent
        tin[node] = low[node] = insertionTime;
        for (int child : adj.get(node)) {
            //skip for parent
            if (child == parent) continue;
            if (!vis[child]) {
                doDFSforbridge(child, vis, adj, low, ans, insertionTime + 1, node, tin);
                low[node] = Math.min(low[node], low[child]);
                //condition for bridge , means child cannot be reached
                if (tin[node] < low[child]) ans.add(List.of(node, child));
            } else {
                low[node] = Math.min(low[node],low[child]);
            }
        }
    }

    //TC -> 3*TC(of dfs/bs)
    //KosaRaju Algorithm
    //for strongly connected components(SCC)
    //valid only for directedGraph
    //video - 54
    public int kosaraju(int V, List<List<Integer>> adj) {
        //1.do toposort i.e get element(from where to start) from last dfs vitited to first
        //2. reverse adj
        //3. do dfs & print element

        Stack<Integer> stack = topoSort(adj);
        //transpose edges
        List<List<Integer>> Tadj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            Tadj.add(i, new ArrayList<>());
        }

        //reverse edges
        for (int u = 0; u < V; u++) {
            for (int v : adj.get(u)) {
                Tadj.get(v).add(u);
            }
        }

        boolean vis[] = new boolean[V];
        int count = 0;
        while (!stack.empty()) {
            Integer u = stack.pop();
            if (!vis[u]) {
                doDFSKosa(u, vis, Tadj);
                count++;
            }

        }
        return count;
    }

    private static Stack<Integer> topoSort(List<List<Integer>> adj) {
        Stack<Integer> ans = new Stack<>();
        boolean[] vis = new boolean[adj.size()];
        for (int i = 0; i < adj.size(); i++) {
            if (!vis[i]) {
                doTopoSortDfs(i, adj, vis, ans);
            }
        }

        return ans;
    }

    private static void doTopoSortDfs(int start, List<List<Integer>> adj, boolean[] vis, Stack<Integer> ans) {
        vis[start] = true;
        for (Integer temp : adj.get(start)) {
            if (!vis[temp]) {
                doTopoSortDfs(temp, adj, vis, ans);
            }
        }
        ans.add(start);
    }

    private void doDFSKosa(Integer u, boolean[] vis, List<List<Integer>> tadj) {
        vis[u] = true;
        for (int v : tadj.get(u)) {
            if (!vis[v]) {
                doDFSKosa(v, vis, tadj);
            }
        }
    }

    private Stack<Integer> dsfNodeInLastFinisTime(ArrayList<ArrayList<Integer>> adj) {
        return null;
    }

    //video - 53
    //logic : treat row & column as separate node
    //edges are given
    //TODO:not working correctly
    int maxRemove(int[][] stones, int n) {
        //return number_of_stones-number of connected Componenets

        int maxCol = 0;
        int maxRow = 0;

        for (int i = 0; i < stones.length; i++) {
            maxRow = Math.max(maxRow, stones[i][0]);
            maxCol = Math.max(maxCol, stones[i][1]);
        }

        DisjointSet DisjointSet = new DisjointSet(maxRow + maxCol + 2);
        Set<Integer> stoneNodes = new HashSet<>();
        for (int i = 0; i < stones.length; i++) {
            int rowNode = stones[i][0];
            int colNode = stones[i][1];
            DisjointSet.groupBySize(rowNode, colNode + 1 + maxRow);
            stoneNodes.add(rowNode);
            stoneNodes.add(colNode);
        }
        //get count of numberOfComponents
        int numberOfComponents = (int) stoneNodes.stream().filter(key -> DisjointSet.getUltimateParent(key) == key).count();
        //have to do extra stuff
        return n - numberOfComponents;
    }

    //video - 52
    //TODO: wrong answer
    public static int maxConnection(int grid[][]) {
        int row = grid.length;
        int col = grid[0].length;
        DisjointSet DisjointSet = new DisjointSet(row * col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 0) continue;
                checkCornerAndAddToDisjointSet(i, j, i - 1, j, grid, DisjointSet, col);
                checkCornerAndAddToDisjointSet(i, j, i + 1, j, grid, DisjointSet, col);
                checkCornerAndAddToDisjointSet(i, j, i, j - 1, grid, DisjointSet, col);
                checkCornerAndAddToDisjointSet(i, j, i, j + 1, grid, DisjointSet, col);
            }
        }
        int ans = 0;
        Set<Integer> visitedParent = new HashSet<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int temp = 0;
                if (grid[i][j] == 0) {
                    temp += checkCornerElement(i - 1, j, grid, DisjointSet, col, visitedParent);
                    temp += checkCornerElement(i + 1, j, grid, DisjointSet, col, visitedParent);
                    temp += checkCornerElement(i, j - 1, grid, DisjointSet, col, visitedParent);
                    temp += checkCornerElement(i, j + 1, grid, DisjointSet, col, visitedParent);
                }
                ans = Math.max(ans, temp + 1);
            }
        }
        return ans;
    }

    private static int checkCornerElement(int i, int j, int[][] g, DisjointSet DisjointSet, int col, Set<Integer> visitedUltimateParentSet) {
        int nodeNumber = i * col + j;
        int ultimateParent = DisjointSet.getUltimateParent(nodeNumber);
        if (visitedUltimateParentSet.contains(ultimateParent)) return 0;
        if (isSafe(i, j, g) && g[i][i] == 1) {
            visitedUltimateParentSet.add(ultimateParent);
            int size = DisjointSet.size[ultimateParent];
            return size;
        }
        return 0;
    }

    private static void checkCornerAndAddToDisjointSet(int previ, int prevj, int i, int j, int[][] g, DisjointSet DisjointSet, int col) {
        //if prevNode & curNode is 1 , then go ahead and join prevnode & curNode
        if (isSafe(i, j, g) && g[i][j] == 1 && g[previ][prevj] == 1) {
            int prevNode = previ * col + prevj;
            int curNode = i * col + j;
            DisjointSet.groupBySize(prevNode, curNode);
        }
    }

    //video - 50
    //link: https://leetcode.com/problems/accounts-merge/
    public static List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<Integer, String> nameToNodeMapping = new LinkedHashMap<>();
        Map<String, Integer> mailToNodeMapping = new LinkedHashMap<>();
        DisjointSet DisjointSet = new DisjointSet(accounts.size());
        int nodeNumber = 0;
        for (List<String> account : accounts) {
            nameToNodeMapping.put(nodeNumber, account.get(0));
            for (int i = 1; i < account.size(); i++) {
                String mail = account.get(i);
                if (mailToNodeMapping.containsKey(mail)) {
                    DisjointSet.groupBySize(mailToNodeMapping.get(mail), nodeNumber);
                } else mailToNodeMapping.put(mail, nodeNumber);
            }
            nodeNumber++;
        }

        //initialize arrayList
        List<List<String>> ans = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            ans.add(i, new ArrayList<>());
        }

        mailToNodeMapping.forEach((key, value) -> {
            int ultimateParent = DisjointSet.getUltimateParent(value);
            List<String> temp = ans.get(ultimateParent);
            //add map name at 0 index
            if (temp.size() == 0) temp.add(nameToNodeMapping.get(ultimateParent));
            temp.add(key);
            Collections.sort(temp.subList(1, temp.size()));
        });

        //filterNonZerList
        List<List<String>> collect = ans.stream().filter(x -> x.size() > 0).collect(Collectors.toList());

        return collect;
    }

    //video - 49
    private static int minNoOfOperationsToMakeGraphConnected(int V, int[][] edges) {

        int numberOfExtraEdges = 0;
        DisjointSet DisjointSet = new DisjointSet(V);
        for (int[] edge : edges) {
            Integer u = edge[0];
            Integer v = edge[1];
            //if ultimateparent is same , it means already connected & we have extraEdges
            if (DisjointSet.getUltimateParent(u) == DisjointSet.getUltimateParent(v)) {
                numberOfExtraEdges++;
            } else DisjointSet.groupBySize(u, v);
        }
        int numberOfGraphComponents = 0;
        //count number of bosses which will give number of components of graph
        for (int node = 0; node < V; node++) {
            //if node and ultimate parent are same
            if (node == DisjointSet.getUltimateParent(node)) numberOfGraphComponents++;
        }

        int atLeastRequireEdges = numberOfGraphComponents - 1;
        if (atLeastRequireEdges >= numberOfExtraEdges) return atLeastRequireEdges;

        return -1;

    }


    //video - 48
    //i.e is total number of ulitmate parent
    // or number bosses or DisjointSet.getUltimateParent(node) == node
    private static int noDisjointSetOrNumberOfProvinces(int[][] g) {

        int row = g.length;
        int col = g[0].length;
        DisjointSet DisjointSet = new DisjointSet(g.length);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (g[i][j] == 1) DisjointSet.groupBySize(i, j);
            }
        }
        int noOfUltimateParent = 0;
        // count number of bosses , boss is node whose node & its parent is same
        for (int node = 0; node < row; node++) {
            if (DisjointSet.getUltimateParent(node) == node) noOfUltimateParent++;
        }

        return noOfUltimateParent;

    }

    //video - 47
    //sort edges
    //keep adding if not in same component
    private static void krushkalMST(List<List<Integer>> edges) {

        //sort by weight
        Collections.sort(edges, Comparator.comparingInt(a -> a.get(0)));
        DisjointSet DisjointSet = new DisjointSet(edges.size());
        List<List<Integer>> mst = new ArrayList<>();
        int cost = 0;
        for (List<Integer> edge : edges) {
            Integer u = edge.get(1);
            Integer v = edge.get(2);
            if (!DisjointSet.isInSameComponent(u, v)) {
                mst.add(List.of(u, v));
                cost += edge.get(0);
            }
            DisjointSet.groupBySize(u, v);
        }

        System.out.println(mst);
        System.out.println(cost);

    }

    // video - 45
    //1.take out elment from min heap , mark as visited & add to result
    //2. for add all adjacent unvisited nodes add to heap
    //3.keep repeating till heap is not empty
    // if edges_list not required then 3rd element in queue array is not required
    //ElogE complexity
    private static int primMST(int start, List<List<Edge>> wadj) {

        boolean[] vis = new boolean[wadj.size()];
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        //wt,node,parent
        queue.add(new int[]{0, start, -1});
        List<List<Integer>> edges = new ArrayList<>();
        int ans = 0;
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int dist = temp[0];
            int u = temp[1];
            int v = temp[2];
            if (!vis[u]) {
                vis[u] = true;
                ans += dist;
                if (v != -1) edges.add(List.of(u, v));
                for (Edge child : wadj.get(u)) {
                    queue.add(new int[]{child.Wt, child.node, u});
                }
            }
        }
        System.out.println(edges);
        return ans;
    }

    //video - 43
    int findCity(int n, int m, int[][] edges, int distanceThreshold) {
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(matrix[i], Integer.MAX_VALUE);
        }

        //for UD graph add
        for (int i = 0; i < m; i++) {
            //edges[i][2] is weight
            matrix[edges[i][0]][edges[i][1]] = edges[i][2];
            matrix[edges[i][1]][edges[i][0]] = edges[i][2];
        }

        //self as 0
        for (int i = 0; i < n; i++) {
            matrix[i][i] = 0;
        }

        for (int via = 0; via < n; via++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][via] == Integer.MAX_VALUE || matrix[via][j] == Integer.MAX_VALUE) continue;
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i][via] + matrix[via][j]);
                }
            }
        }

        int node = n;
        int count = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int icount = 0;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] <= distanceThreshold) icount++;
            }
            if (icount <= count) {
                node = i;
                count = icount;
            }
        }
        return node;
    }

    //used for every path to every path i.e mutlisource
    //used to find negative cycle
    // if g[i][i]<0 return negative path
    //multiSource shortest path
    private static void floyd_warshal(int[][] g, int V) {
        int row = g.length;
        int col = g[0].length;

        //go via every edge and store distance
        //for all Nodes
        for (int via = 0; via < V; via++) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    g[i][j] = Math.min(g[i][j], g[i][via] + g[via][j]);
                }
            }
        }

        //check for negative cycle
        // i.e if costing of same to same node is less than 0 then it has cycle
        for (int i = 0; i < row; i++) {
            if (g[i][i] < 0) return;
        }

    }

    //video - 41
    //work for Directed Graph as well as un-directed , if UDG then add both edges
    //for negative cycles or negative edges
    //for shortest path , SSST
    private static int[] bellManFord(int N, List<List<Integer>> adj) {
        int[] dist = new int[adj.size() - 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;

        //relaxation of edges for N-1 times , where N total number of nodes startig at index 1
        for (int relax = 1; relax < adj.size(); relax++) {
            for (List<Integer> temp : adj) {
                int u = temp.get(0);
                int v = temp.get(1);
                int wt = temp.get(2);
                if (dist[u] != Integer.MAX_VALUE && dist[u] + wt < dist[v])
                    dist[v] = dist[u] + wt;
            }
        }

        //if still negative cycle on Nth iteration then returns -1
        // i.e on Nth iteration(i.e do one more iteration) if dist array still updated then return -1
        for (List<Integer> temp : adj) {
            int u = temp.get(0);
            int v = temp.get(1);
            int wt = temp.get(2);
            //if still negative cycle returns -1
            if (dist[u] != Integer.MAX_VALUE && dist[u] + wt < dist[v])
                return new int[]{-1};
        }

        return dist;
    }

    //video - 40
    //do djikstra
    // ways of start=1
    // keep track of ways if dist[u] + v.Wt == dist[v.node] then updated ways sum of both
    //otherwise , take ways of u only,which is till uth node is reached
    private static int numberOfWaysToArriveAtDestination(int start, int end, List<List<Edge>> wadj) {
        int size = wadj.size();
        int[] ways = new int[size];
        ways[start] = 1;
        int[] dist = new int[size];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        //dist,node
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        queue.add(new int[]{0, start});
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int distance = temp[0];
            int u = temp[1];
            for (Edge v : wadj.get(u)) {
                if (dist[u] + v.Wt < dist[v.node]) {
                    dist[v.node] = dist[u] + v.Wt;
                    ways[v.node] = ways[u];
                    queue.add(new int[]{dist[v.node], v.node});
                } else if (dist[u] + v.Wt == dist[v.node]) {
                    ways[v.node] = ways[u] + ways[v.node];
                }
            }
        }
        System.out.println(Arrays.toString(ways));
        System.out.println(Arrays.toString(dist));

        return ways[end];
    }


    //logic : keep on multiplying start with array element and keep track of steps in queue
    // on popping end from queue return steps
    //Note: array used to handle infinite loop in queue
    private static int minMultiplicationToReachEnd(int[] a, int start, int end) {
        int mod = 100000;
        int[] dis = new int[mod];
        Arrays.fill(dis, Integer.MAX_VALUE);
        //steps,queue
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{start, 0});
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int steps = temp[0];
            int node = temp[1];
            if (node == end) return steps;
            for (int i = 0; i < a.length; i++) {
                int curentNode = (a[i] * node) % mod;
                if (steps + 1 < dis[curentNode]) {
                    dis[curentNode] = steps + 1;
                    queue.add(new int[]{steps + 1, curentNode});
                }
            }
        }
        return -1;
    }

    //use djikstra alog with check if stop>k then continue
    //djikstra's will not only give us the shortest path with valid number of stops to node 2 (dst)
    // but it will also generate shortest path with valid number of stops to all the nodes in graph
    // stop in terms of priority queue first element
    private static void cheapestFlightWithKStop(int source, int destination, int maxStop, List<List<Edge>> adj) {

        int dist[] = new int[adj.size()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        Queue<int[]> queue = new ArrayDeque<>();
        //stop,node,dist
        queue.add(new int[]{0, 0, 0});
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int stop = temp[0];
            int u = temp[1];
            int distance = temp[2];
            if (stop > maxStop) continue;
            for (Edge v : adj.get(u)) {
                if (dist[u] + v.Wt < dist[v.node]) {
                    dist[v.node] = dist[u] + v.Wt;
                    queue.add(new int[]{stop + 1, v.node, distance + v.Wt});
                }
            }
        }
        System.out.println(Arrays.toString(dist));
    }

    //video - 37
    //logic is : keep diff as Math.max(diff,abs(u cell value- v cell value)
    // add to queue only when not visted
    // once end is reached that will be min path
    // stop when poll give endRow & endColumn
    private static int minPathEffort(int[][] g) {
        int[][] dist = new int[g.length][g[0].length];
        for (int i = 0; i < g.length; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[0][0] = 0;
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        queue.add(new int[]{0, 0, 0});
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int i = temp[1];
            int j = temp[2];
            int diff = temp[0];
            if (i == g.length - 1 && j == g[0].length - 1) return diff;
            visitAll4Corners(i - 1, j, i, j, g, dist, diff, queue);
            visitAll4Corners(i + 1, j, i, j, g, dist, diff, queue);
            visitAll4Corners(i, j - 1, i, j, g, dist, diff, queue);
            visitAll4Corners(i, j + 1, i, j, g, dist, diff, queue);

        }
        return -1;
    }

    private static void visitAll4Corners(int vi, int vj, int ui, int uj, int[][] g, int[][] dist, int diff, Queue<int[]> queue) {
        if (isSafe(vi, vj, g)) {
            int currentMax = Math.max(diff, Math.abs(g[vi][vj] - g[ui][uj]));
            //update with min b/w currentMax & dist[vi][vj]
            if (currentMax < dist[vi][vj]) {
                dist[vi][vj] = currentMax;
                queue.add(new int[]{vi, vj, currentMax});
            }
        }
    }

    //video - 36
    // can be done using simple BFS also
    private static int shortestDistanceBinaryMaze(int sr, int sc, int dr, int dc, int[][] g) {

        int[][] dist = new int[g.length][g[0].length];
        for (int i = 0; i < g.length; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[sr][sc] = 0;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{sr, sc, 0});
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int i = temp[0];
            int j = temp[1];
            int wt = temp[2];
            if (i == dr && j == dc) return dist[dr][dc];
            iterate4Sides(dist, i - 1, j, i, j, wt, g, queue);
            iterate4Sides(dist, i + 1, j, i, j, wt, g, queue);
            iterate4Sides(dist, i, j - 1, i, j, wt, g, queue);
            iterate4Sides(dist, i, j + 1, i, j, wt, g, queue);
        }
        return -1;
    }

    private static void iterate4Sides(int[][] dist, int vi, int vj, int ui, int uj, int wt, int[][] g, Queue<int[]> queue) {
        if (isSafe(vi, vj, g) && dist[ui][uj] + 1 < dist[vi][vj] && g[vi][vj] == 1) {
            dist[vi][vj] = dist[ui][uj] + 1;
            queue.add(new int[]{vi, vj, 1 + wt});
        }
    }

    // video - 35
    //use parent array to keep track from / keep track of u->v, v.parent=u
    private static void dijkstraShortestPath(int start, List<List<Edge>> adj) {
        Queue<Edge> queue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWt).thenComparing(Edge::getNode));
        queue.add(new Edge(start, 0));
        int dist[] = new int[adj.size() - 1];
        int parent[] = new int[adj.size() - 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        //1. take out element from queue
        // 2. if sum of parent distance and currentNode distance is less than already stored at distance arry then update it
        //otherwise
        //keep repeating till queue is not empty
        while (!queue.isEmpty()) {
            Edge u = queue.poll();
            for (Edge v : adj.get(u.getNode()))
                //for u-w->v if dist[u]+w <dist[v] then update
                if (dist[u.getNode()] + v.getWt() < dist[v.getNode()]) {
                    dist[v.getNode()] = dist[u.getNode()] + v.getWt();
                    parent[v.getNode()] = u.node;
                    queue.add(v);
                }
        }
        List<Integer> path = new ArrayList<>();
        int node = adj.size() - 2;
        while (node != parent[node]) {
            path.add(node);
            node = parent[node];
        }
        path.add(node);
        //return distance stored at last index
        System.out.println(Arrays.toString(dist));
        Collections.reverse(path);
        System.out.println(path);
    }

    //video - 32
    //  Time Complexity: O( E log(V) ), Where E = Number of edges and V = Number of Nodes.
    //min distance from start to end
    //does not work for negative weight Or negative cycle
    //SSST
    //for directed & undirectrd graph
    private static void dijkstraShortestPathLength(int start, List<List<Edge>> adj) {
        Queue<Edge> queue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWt).thenComparing(Edge::getNode));
        queue.add(new Edge(start, 0));
        int dist[] = new int[adj.size() - 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        //1. take out element from queue
        // 2. if sum of parent distance and currentNode distance is less than already stored at distance array then update it
        //otherwise
        //keep repeating till queue is not empty
        while (!queue.isEmpty()) {
            Edge u = queue.poll();
            for (Edge v : adj.get(u.getNode()))
                //for u-w->v if dist[u]+v.w <dist[v] then update
                if (dist[u.getNode()] + v.getWt() < dist[v.getNode()]) {
                    dist[v.getNode()] = dist[u.getNode()] + v.getWt();
                    queue.add(v);
                }
        }
        //return distance stored at last index
        System.out.println(Arrays.toString(dist));
    }


    //video - 31
    //  String[] wordList = {"hot", "dot", "dog", "lot", "log", "cog"};
    //        String startWord = "hit", targetWord = "cog";
    private static int wordLadder3Optimized(char[] startWord, String targetWord, String[] wordList) {

        Set<String> stringSet = new HashSet<>(List.of(wordList));
        Queue<Ladder> queue = new ArrayDeque<>();
        queue.add(new Ladder(startWord, 0));
        stringSet.remove(startWord);
        int ans = 0;
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put(String.valueOf(startWord), 0);
        while (!queue.isEmpty()) {
            Ladder temp = queue.poll();
            if (new String(temp.word).equals(targetWord)) ans = temp.len;
            for (int i = 0; i < startWord.length; i++) {
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    char[] arr = temp.word.clone();
                    arr[i] = ch;
                    String str = String.valueOf(arr);
                    if (stringSet.contains(str)) {
                        queue.add(new Ladder(arr, temp.len + 1));
                        map.put(str, temp.len + 1);
                        stringSet.remove(str);
                    }
                }
            }
        }
        printMapUsingDFS(map, targetWord, new ArrayList<>(), String.valueOf(startWord));
        return ans;
    }


    private static void printMapUsingDFS(Map<String, Integer> map, String targetWord, List<String> temp, String startWord) {
        if (targetWord.equals(startWord)) {
            ArrayList<String> ans = new ArrayList<>(temp);
            ans.add(startWord);
            Collections.reverse(ans);
            System.out.println(ans);
            return;
        }

        for (int i = 0; i < targetWord.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                char[] ar = targetWord.toCharArray().clone();
                ar[i] = ch;
                String currentStr = String.valueOf(ar);
                if (map.containsKey(currentStr) && map.get(currentStr) == map.get(targetWord) - 1) {
                    temp.add(targetWord);
                    printMapUsingDFS(map, currentStr, temp, startWord);
                    temp.remove(temp.size() - 1);
                }
            }
        }
    }

    // TC -> next to impossible
    //print ladder words shortest sequence
    private static List<List<String>> wordLadder2(char[] startWord, String targetWord, String[] wordList) {

        Set<String> inputSet = new HashSet<>(List.of(wordList));
        Queue<LadderList> queue = new ArrayDeque<>();
        List<String> firstList = List.of(String.valueOf(startWord));
        queue.add(new LadderList(firstList, 1));
        List<List<String>> ans = new ArrayList<>();
        Set<String> to_be_deleted = new HashSet<>();
        inputSet.remove(String.valueOf(startWord));
        int currentLevel = 0;
        while (!queue.isEmpty()) {

            LadderList polled = queue.poll();
            //change in level
            if (currentLevel < polled.level) inputSet.removeAll(to_be_deleted);
            currentLevel = polled.level;
            //get last list word
            String temp = polled.list.get(polled.list.size() - 1);
            if (temp.equals(targetWord)) {
                ans.add(new ArrayList<>(polled.list));
                //poll all queue elements
                while (!queue.isEmpty() && queue.peek().level <= currentLevel)
                    ans.add(new ArrayList<>(queue.poll().list));
                break;
            }

            char[] ar = temp.toCharArray().clone();
            for (int i = 0; i < targetWord.length(); i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    ar[i] = c;
                    String currentStr = String.valueOf(ar);
                    if (!currentStr.equals(temp) && inputSet.contains(currentStr)) {
                        List<String> appendedList = new ArrayList<>(polled.list);
                        appendedList.add(currentStr);
                        queue.add(new LadderList(appendedList, polled.level + 1));
                        to_be_deleted.add(currentStr);
                    }
                }
            }
        }
        return ans;
    }

    //video - 29
    //Time Complexity: O(Number of input words * M(lenght of each work) * 26)
    private static int wordLadder1(char[] startWord, String targetWord, String[] wordList) {

        Set<String> set = new HashSet<>(List.of(wordList));
        Queue<Ladder> queue = new ArrayDeque<>();
        queue.add(new Ladder(startWord, 1));
        set.remove(startWord);
        while (!queue.isEmpty()) {
            Ladder temp = queue.poll();
            int len = temp.len;
            if (new String(temp.word).equals(targetWord)) return len;
            for (int i = 0; i < temp.word.length; i++) {
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    temp.word[i] = ch;
                    String str = String.valueOf(temp.word);
                    if (set.contains(str)) {
                        queue.add(new Ladder(temp.word, len + 1));
                        set.remove(str);
                    }
                }
            }
        }
        return 0;
    }

    static class LadderList {
        List<String> list;
        int level;

        public LadderList(List<String> list1, int level) {
            list = new ArrayList<>(list1);
            this.level = level;
        }
    }

    static class Ladder {
        char[] word;
        int len;

        public Ladder(char[] s, int len) {
            this.word = s.clone();
            this.len = len;
        }

        @Override
        public String toString() {
            return "Ladder{" +
                    "a=" + Arrays.toString(word) +
                    ", len=" + len +
                    '}';
        }
    }


    //TC - >(V+E)
    //[0, 1, 2, 1, 2, 3, 3, 4, 4]
    //video - 28
    //same as prev , only change is distance is 1
    private static int[] shortestPathWithoutAlgoUG(int startNode, List<List<Integer>> adj) {
        int size = adj.size();
        int dist[] = new int[size];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[startNode] = 0;
        Queue<Edge> queue = new ArrayDeque<>();
        queue.add(new Edge(startNode, 0));

        while (!queue.isEmpty()) {
            Edge u = queue.poll();
            for (Integer v : adj.get(u.node)) {
                if (dist[u.node] + 1 < dist[v]) {
                    //update v with lower weight
                    dist[v] = 1 + dist[u.node];
                    queue.add(new Edge(v, u.Wt + 1));
                }
            }
        }
        System.out.println(Arrays.toString(dist));
        return dist;
    }

    //dist[u]+v.Wt
    //[5, 7, 3, 6, 2, 3, 0]
    //video-27
    private static int[] shortestPathWeightedWithoutAlgo(int startNode, List<List<Edge>> adj) {
        List<Integer> list = kahnTopoWithEdges(adj);
        Stack<Integer> stack = new Stack<>();
        //reverse since we need terminal at top of stack of node
        Collections.reverse(list);
        stack.addAll(list);
        int dist[] = new int[adj.size()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[startNode] = 0;
        while (!stack.isEmpty()) {
            Integer u = stack.pop();
            for (Edge v : adj.get(u)) {
                if (dist[u] + v.Wt < dist[v.node])
                    dist[v.node] = dist[u] + v.Wt;
            }
        }
        System.out.println(Arrays.toString(dist));
        return dist;
    }

    private static List<Integer> kahnTopoWithEdges(List<List<Edge>> adj) {
        int V = adj.size();
        int[] inDegree = new int[V];

        //update income edges for all nodes
        Queue<Integer> queue = new ArrayDeque<>();
        for (List<Edge> temp : adj) {
            for (Edge n : temp) {
                inDegree[n.node]++;
            }
        }
        // add all nodes whose inDegree edge is 0
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) queue.add(i);
        }
        List<Integer> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            Integer temp = queue.poll();
            //decrement inDegree edges for popped node
            for (Edge n : adj.get(temp)) {
                inDegree[n.node]--;
                if (inDegree[n.node] == 0) queue.add(n.node);
            }
            ans.add(temp);
        }
        return ans;
    }


    static class Edge {
        int node;
        int Wt;

        public Edge(int node, int wt) {
            this.node = node;
            Wt = wt;
        }

        public int getNode() {
            return node;
        }

        public int getWt() {
            return Wt;
        }
    }

    //video - 26
    //not dict possible if
    // 1. cyclic dependency
    //2 if smaller common string is after larger string length
    // e.g abcd is before abc
    private static List<Character> alienDict(int K, String[] dict) {
        List<List<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < K; i++) {
            adj.add(i, new ArrayList<>());
        }

        for (int i = 0; i < dict.length - 1; i++) {
            addGraphNodes(dict[i].toCharArray(), dict[i + 1].toCharArray(), adj);
        }

        List<Integer> list = kahnTopo(adj);
        List<Character> collect = list.stream().map(x -> (char) (x + 'a')).collect(Collectors.toList());
        System.out.println(collect);
        return collect;

    }

    private static void addGraphNodes(char[] s1, char[] s2, List<List<Integer>> adj) {
        int u = 0;
        int v = 0;
        while (u < s1.length && v < s2.length) {
            if (s1[u] != s2[v]) break;
            u++;
            v++;
        }
        adj.get(s1[u] - 'a').add(s2[v] - 'a');
    }

    //video - 25
    private static void safeStatesAnotherWay(List<List<Integer>> adj) {
        //reverse edges
        // take terminal nodes
        // & keep on doing kahn algo
        // unsafe nodes is never visited

        int size = adj.size();
        List<List<Integer>> revAdj = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            revAdj.add(i, new ArrayList<>());
        }

        //reverse Edges
        for (int u = 0; u < size; u++) {
            for (Integer v : adj.get(u)) {
                revAdj.get(v).add(u);
            }
        }
        int[] inDegree = new int[size];
        for (int u = 0; u < size; u++) {
            for (Integer v : revAdj.get(u)) {
                inDegree[v]++;
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < size; i++) {
            if (inDegree[i] == 0) queue.add(i);
        }

        List<Integer> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            Integer temp = queue.poll();
            for (Integer v : revAdj.get(temp)) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue.add(v);
                }
            }
            ans.add(temp);
        }
        System.out.println(ans);

    }

    //dp-24
    public static int[] courseSchedule(int numCourses, int[][] prerequisites) {

        if (numCourses < 2) return new int[1];

        int[] inDegree = new int[numCourses];
        Queue<Integer> queue = new ArrayDeque<>();

        //declare adjancency list
        List<List<Integer>> adj = new ArrayList<>();

        //initialize graph
        for (int i = 0; i < numCourses; i++)
            adj.add(i, new ArrayList());

        //make graph
        for (int i = 0; i < prerequisites.length; i++) {
            // u->v
            adj.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }
        //count inDegree
        for (int i = 0; i < adj.size(); i++) {
            for (int temp : adj.get(i)) {
                inDegree[temp]++;
            }
        }

        //add inDegree 0 to stack
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        List<Integer> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            int course = queue.poll();
            ans.add(course);
            for (Integer temp : adj.get(course)) {
                inDegree[temp]--;
                if (inDegree[temp] == 0) {
                    queue.add(temp);
                }
            }
        }
        if (ans.size() == numCourses) {
            Collections.reverse(ans);
            return ans.stream().mapToInt(x -> x).toArray();
        } else
            return new int[1];
    }


    //cycle detection using kahn algo
    //video - 23
    private static boolean cycleDetectionKahn(List<List<Integer>> adj) {
        List<Integer> list = kahnTopo(adj);
        if (list.size() == adj.size()) return true;
        return false;
    }

    //video - 22
    //5, 4, 3, 2, 1, 0
//    Time Complexity: O(V+E), where V = no. of nodes and E = no. of edges. This is a simple BFS algorithm.
    private static List<Integer> kahnTopo(List<List<Integer>> adj) {
        int V = adj.size();
        int[] inDegree = new int[V];

        //update income edges for all nodes
        Queue<Integer> queue = new ArrayDeque<>();
        for (List<Integer> temp : adj) {
            for (int n : temp) {
                inDegree[n]++;
            }
        }
        // add all nodes whose inDegree edge is 0
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) queue.add(i);
        }
        List<Integer> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            Integer temp = queue.poll();
            //decrement inDegree edges for popped node
            for (int n : adj.get(temp)) {
                inDegree[n]--;
                if (inDegree[n] == 0) queue.add(n);
            }
            ans.add(temp);
        }
        return ans;
    }

    //video - 21
    //O(V+2E)
    private static void dsfTopo(List<List<Integer>> adj) {
        int size = adj.size();
        boolean[] vis = new boolean[size];
        Stack<Integer> ans = new Stack<>();
        for (int i = 0; i < size; i++) {
            if (!vis[i]) doDFSTopo(i, vis, adj, ans);
        }
        List<Integer> collect = ans.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        System.out.println(collect);

    }

    private static void doDFSTopo(int node, boolean[] vis, List<List<Integer>> adj, Stack<Integer> ans) {
        vis[node] = true;
        for (Integer temp : adj.get(node)) {
            if (!vis[temp]) doDFSTopo(temp, vis, adj, ans);
        }
        ans.add(node);
    }

    //video - 20
    //any1 part of cycle cannot be safe node
    //any1 leads to cycle cannot be safe node
    private static List<Integer> safeStates(List<List<Integer>> adj) {
        int size = adj.size();
        boolean vis[] = new boolean[size];
        boolean pathvis[] = new boolean[size];
        boolean safeNodes[] = new boolean[size];
        List<Integer> safeStates = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            if (!vis[i]) {
                doDFSSafeStates(i, vis, pathvis, adj, safeNodes);
            }
        }
        for (int i = 0; i < vis.length; i++) {
            if (safeNodes[i]) safeStates.add(i);
        }
        return safeStates;
    }

    private static boolean doDFSSafeStates(int node, boolean[] vis, boolean[] pathvis, List<List<Integer>> adj, boolean[] safe) {
        vis[node] = true;
        pathvis[node] = true;
        safe[node] = false;
        for (Integer temp : adj.get(node)) {
            if (vis[temp] && pathvis[temp]) return true;
            else if (!vis[temp]) {
                if (doDFSSafeStates(temp, vis, pathvis, adj, safe)) return true;
            }
        }
        //backtrack
        pathvis[node] = false;
        //if not cycle then mark as safe node as true
        safe[node] = true;
        return false;
    }

    //TC -> V+E
    //video - 19
    // is Cycle if & only if visited & pathvisted
    // have to visit same node on same path that is why it is pathVisited
    private static boolean cycleDirected(List<List<Integer>> adj) {
        int size = adj.size();
        boolean vis[] = new boolean[size];
        boolean pathvis[] = new boolean[size];
        for (int i = 1; i < size; i++) {
            if (!vis[i]) {
                if (dfsCycleDirected(i, vis, pathvis, adj)) return true;
            }
        }
        return false;
    }

    private static boolean dfsCycleDirected(int parent, boolean[] vis, boolean[] pathvis, List<List<Integer>> adj) {
        vis[parent] = true;
        pathvis[parent] = true;
        for (Integer child : adj.get(parent)) {
            //condition for cycle
            if (vis[child] && pathvis[child]) return true;
            else if (!vis[child]) {
                if (dfsCycleDirected(child, vis, pathvis, adj)) return true;
            }
        }
        //backtrack
        pathvis[parent] = false;
        return false;
    }

    //video - 18
    private static boolean bipartiteDFS(int startNode, List<List<Integer>> adj) {
        int[] col = new int[adj.size()];
        Arrays.fill(col, -1);
        for (int node = 1; node < adj.size(); node++) {
            if (col[node] == -1) {
                if (isBiparTiteUsingDFS(node, col, adj, 0) == false) return false;
            }
        }
        return true;
    }

    private static boolean isBiparTiteUsingDFS(int parent, int[] col, List<List<Integer>> adj, int val) {
        col[parent] = val;
        for (Integer child : adj.get(parent)) {
            //if not colored
            if (col[child] == -1) {
                if (val == 1) isBiparTiteUsingDFS(child, col, adj, 0);
                else isBiparTiteUsingDFS(child, col, adj, 0);
            } else if (col[child] == col[parent]) return false;
        }
        return true;
    }


    //TC - > Simiar to BFS/DFS
    //video=17
    //linear graph with no cycle is always biPartite
    // any graph with even cycle length is always biPartite
    // any graph with odd cycle length is never biPartite
    private static boolean isBiPartiteBFS(int startNode, List<List<Integer>> adj) {
        int[] col = new int[adj.size()];
        Arrays.fill(col, -1);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(startNode);
        col[startNode] = 0;
        while (!queue.isEmpty()) {
            Integer parent = queue.poll();
            for (Integer child : adj.get(parent)) {
                //if not visited/colored
                if (col[child] == -1) {
                    if (col[parent] == 0) col[child] = 1;
                    else col[child] = 0;
                    queue.add(child);
                } else if (col[child] == col[parent]) return false;
            }
        }
        return true;
    }

    //video - 16
    private static void numberOfDistinctIsland(int[][] g) {
        int rows = g.length;
        int cols = g[0].length;
        boolean vis[][] = new boolean[rows][cols];
        Set<List<String>> ans = new HashSet<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (g[i][j] == 1 && !vis[i][j]) {
                    List<String> temp = new ArrayList<>();
                    numberOfDistinctIsland(i, j, g, vis, temp, i, j);
                    ans.add(new ArrayList<>(temp));
                }
            }
        }
        System.out.println(ans.size());
    }

    private static void numberOfDistinctIsland(int i, int j, int[][] g, boolean[][] vis, List<String> temp, int baseI, int baseJ) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || vis[i][j] || g[i][j] == 0) return;
        vis[i][j] = true;
        temp.add("{" + (i - baseI) + "," + (j - baseJ) + "}");

        //this will be in order
        numberOfDistinctIsland(i - 1, j, g, vis, temp, baseI, baseJ);
        numberOfDistinctIsland(i + 1, j, g, vis, temp, baseI, baseJ);
        numberOfDistinctIsland(i, j - 1, g, vis, temp, baseI, baseJ);
        numberOfDistinctIsland(i, j + 1, g, vis, temp, baseI, baseJ);

    }

    //video - 15 using BFS
    //if not visited & is 1 , count such 1's
    private static void countOfInner1BFS(int[][] g) {
        int cols = g[0].length;
        int rows = g.length;
        Queue<int[]> queue = new ArrayDeque<>();
        boolean vis[][] = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || j == 0 || i == rows - 1 || j == cols - 1) {
                    if (g[i][j] == 1) {
                        queue.add(new int[]{i, j});
                        vis[i][j] = true;
                    }
                }
            }
        }
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int i = temp[0];
            int j = temp[1];
            markVisitedFrom(i + 1, j, g, vis, queue);
            markVisitedFrom(i - 1, j, g, vis, queue);
            markVisitedFrom(i, j - 1, g, vis, queue);
            markVisitedFrom(i, j + 1, g, vis, queue);
        }

        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (g[i][j] == 1 && !vis[i][j]) count++;
            }
        }
        System.out.println(count);
    }

    private static void markVisitedFrom(int i, int j, int[][] g, boolean[][] vis, Queue<int[]> queue) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || vis[i][j] || g[i][j] == 0) return;
        vis[i][j] = true;
        queue.add(new int[]{i, j});

    }


    //video - 15 using recursion
    //if not visited & is 1 , count such 1's
    private static void numberOfEnclaves(int[][] g) {
        int cols = g[0].length;
        int rows = g.length;
        boolean vis[][] = new boolean[rows][cols];
        //0th & last row
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || j == 0 || i == rows - 1 || j == cols - 1) {
                    if (g[i][j] == 1) {
                        dfsMark1Visit(i, j, g, vis);
                    }
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (g[i][j] == 1 && !vis[i][j]) ans++;
            }
        }
        System.out.println(ans);
    }

    private static void dfsMark1Visit(int i, int j, int[][] g, boolean[][] vis) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || vis[i][j] || g[i][j] == 0) return;
        vis[i][j] = true;
        dfsMark1Visit(i - 1, j, g, vis);
        dfsMark1Visit(i + 1, j, g, vis);
        dfsMark1Visit(i, j - 1, g, vis);
        dfsMark1Visit(i, j + 1, g, vis);
    }


    //video - 14
    //mark from boundry which cannot be 4 sides sorrouned by 'X'
    // then change all other unMarked with 'O'
    private static void replace0withX(char[][] g) {
        int cols = g[0].length;
        int rows = g.length;
        boolean vis[][] = new boolean[rows][cols];

        //for 0th & last rows
        for (int j = 0; j < cols; j++) {
            if (g[0][j] == 'O') markVisited(0, j, g, vis);
            if (g[rows - 1][j] == 'O') markVisited(rows - 1, j, g, vis);
        }

        //first & last col
        for (int i = 0; i < rows; i++) {
            if (g[i][0] == 'O') markVisited(rows, i, g, vis);
            if (g[i][cols - 1] == 'O') markVisited(cols - 1, i, g, vis);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                //if not visitd and is 0 then mark as 'X'
                if (g[i][j] == 'O' && !vis[i][j]) g[i][j] = 'X';
            }
        }
        System.out.println(Arrays.deepToString(g));
    }

    private static void markVisited(int i, int j, char[][] g, boolean[][] vis) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || vis[i][j] || g[i][j] != 'O') return;
        vis[i][j] = true;
        markVisited(i - 1, j, g, vis);
        markVisited(i + 1, j, g, vis);
        markVisited(i, j - 1, g, vis);
        markVisited(i, j + 1, g, vis);
    }

    //n*m*4
    //video - 13
    //keep track using visited so that element is not visited twice
    private static int[][] distanceNearestCell_1(int[][] g) {
        boolean vis[][] = new boolean[g.length][g[0].length];
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                if (g[i][j] == 1) queue.add(new int[]{i, j, 0});
            }
        }
        int ans[][] = new int[g.length][g[0].length];
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int i = temp[0];
            int j = temp[1];
            int len = temp[2];
            ans[i][j] = len;

            check4Sides(i - 1, j, g, vis, queue, len);
            check4Sides(i + 1, j, g, vis, queue, len);
            check4Sides(i, j - 1, g, vis, queue, len);
            check4Sides(i, j + 1, g, vis, queue, len);
        }
        return ans;
    }

    private static void check4Sides(int i, int j, int[][] g, boolean[][] vis, Queue<int[]> queue, int len) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || g[i][j] == 1 || vis[i][j]) return;
        vis[i][j] = true;
        queue.add(new int[]{i, j, len + 1});
    }

    //TC -> O(V+2E)+O(n) for iteration
    //video - 12 if any neighbour which is not from parent path but is already visited
    //return cycle is true
    private static boolean cycleDFS(int startNode, int parent, List<List<Integer>> adj) {
        boolean[] vis = new boolean[adj.size()];
        for (int i = startNode; i < adj.size(); i++) {
            if (!vis[i]) {
                vis[i] = true;
                if (doCheckCycle(i, parent, adj, vis)) return true;
            }
        }
        return false;
    }

    private static boolean doCheckCycle(int node, int parent, List<List<Integer>> adj, boolean[] vis) {
        for (Integer child : adj.get(node)) {
            if (!vis[child]) {
                vis[child] = true;
                if (doCheckCycle(child, node, adj, vis)) return true;
            } else if (child != parent && parent!=-1) return true;
        }
        return false;
    }

    //TC -> O(n+2E)
    //video - 11
    //if already visited & is not parent then there is cycle
    private static boolean isCycleBFS(int startNode, List<List<Integer>> adj) {

        Queue<int[]> queue = new ArrayDeque<>();
        boolean[] vis = new boolean[adj.size()];
        //queue of startNode and parent
        queue.add(new int[]{startNode, -1});
        vis[startNode] = true;
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int node = temp[0];
            int parent = temp[1];
            for (Integer child : adj.get(node)) {
                if (!vis[child]) {
                    vis[child] = true;
                    queue.add(new int[]{child, node});
                } else if (child != parent) return true;
            }
        }
        return false;
    }

    //TC -> O(N*M)
    //video - 10
    private static int timeToRottenOranges(int[][] g) {
        Queue<int[]> queue = new ArrayDeque<>();

        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                if (g[i][j] == 2) queue.add(new int[]{i, j, 0});
            }
        }
        boolean[][] vis = new boolean[g.length][g[0].length];
        int ans = 0;
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int i = temp[0];
            int j = temp[1];
            int time = temp[2];

            doVisit(i - 1, j, time, queue, g, vis);
            doVisit(i + 1, j, time, queue, g, vis);
            doVisit(i, j - 1, time, queue, g, vis);
            doVisit(i, j + 1, time, queue, g, vis);

            ans = Math.max(ans, time);
        }
        return ans;
    }

    private static void doVisit(int i, int j, int time, Queue<int[]> stack, int[][] g, boolean[][] vis) {
        //g[i][j]=1 is fresh orange
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || g[i][j] != 1 || vis[i][j]) return;
        vis[i][j] = true;
        stack.add(new int[]{i, j, time + 1});
    }

    //video - 9
    public int[][] floodFill(int[][] a, int sr, int sc, int color) {
        f(sr, sc, a, a[sr][sc], color);
        return a;
    }

    private static void f(int i, int j, int[][] a, int oldColor, int newColor) {
        if (i < 0 || j < 0 || i >= a.length || j >= a[0].length || a[i][j] != oldColor || a[i][j] == newColor)
            return;

        a[i][j] = newColor;
        f(i + 1, j, a, oldColor, newColor);
        f(i - 1, j, a, oldColor, newColor);
        f(i, j + 1, a, oldColor, newColor);
        f(i, j - 1, a, oldColor, newColor);
    }

    //using recursion
    //dp - 7
    private static int numberOfProvinces(int[][] g) {
        int number_of_provinces = 0;
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                if (g[i][i] == 1) {
                    number_of_provinces++;
                    visitAll4Corners(i, j, g);
                }
            }
        }
        return number_of_provinces;
    }

    private static void visitAll4Corners(int i, int j, int[][] g) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || g[i][j] == 0) return;

        //make it 0
        g[i][j] = 0;

        visitAll4Corners(i - 1, j, g);
        visitAll4Corners(i + 1, j, g);
        visitAll4Corners(i, j - 1, g);
        visitAll4Corners(i, j + 1, g);

    }

    //TC - >O(n)+O(V+2E) where 2E is total degree of graph
    //video - 7
    private static int numberOfProvinces(List<List<Integer>> adj, int V) {

        boolean vis[] = new boolean[adj.size()];
        int number_of_provinces = 0;
        for (int i = 0; i < V; i++) {
            if (!vis[i]) {
                dfs(i, vis, adj);
                number_of_provinces++;
            }
        }
        return number_of_provinces;
    }

    //TC - >O(n)+O(2E) where 2E is total degree of graph
    //video - 6
    private static void dfs(int startNode, boolean[] vis, List<List<Integer>> adj) {
        Stack<Integer> stack = new Stack<>();
        stack.add(startNode);
        while (!stack.isEmpty()) {
            Integer temp = stack.pop();
            if (!vis[temp]) {
                vis[temp] = true;
                System.out.print(temp + " ");
                for (int neighbour : adj.get(temp)) {
                    if (!vis[neighbour]) {
                        stack.add(neighbour);
                    }
                }
            }
        }
    }

    //TC - >O(n)+O(2E) where 2E is total degree of graph
    //video - 5
    private static void bfs(int startNode, List<List<Integer>> adj) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] vis = new boolean[adj.size()];
        queue.add(startNode);
        vis[startNode]=true;
        while (!queue.isEmpty()) {
            Integer node = queue.poll();
            if (!vis[node]) {
                vis[node] = true;
                System.out.print(node + " ");
                for (int neighbour : adj.get(node)) {
                    if (!vis[neighbour]) {
                        queue.add(neighbour);
                    }
                }
            }
        }
    }

    private static boolean isSafe(int i, int j, int[][] g) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length) return false;
        return true;
    }
}