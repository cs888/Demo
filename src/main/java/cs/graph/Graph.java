package cs.graph;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    public static void main(String[] args) {
        int v = 4;

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            adj.add(i, new ArrayList<>());
        }

     /*   adj.get(0).addAll(Arrays.asList(new Edge(1, 2), new Edge(2, 1)));
        adj.get(1).addAll(Arrays.asList(new Edge(0, 2), new Edge(2, 1)));
        adj.get(2).addAll(Arrays.asList(new Edge(0, 1), new Edge(1, 1), new Edge(4, 2), new Edge(3, 2)));
        adj.get(3).addAll(Arrays.asList(new Edge(4, 1), new Edge(2, 2)));
        adj.get(4).addAll(Arrays.asList(new Edge(2, 2), new Edge(3, 1)));
*/
        int g[][] = {
                {1, 0, 1},
                {0, 1, 0},
                {1, 0, 1}
        };

        adj.get(0).addAll(Arrays.asList(1, 2, 3));
        adj.get(1).addAll(Arrays.asList(0, 2, 3));
        adj.get(2).addAll(Arrays.asList(0, 1));
        adj.get(3).addAll(Arrays.asList(0, 1));

        //gColor(0, adj, 3);
        int nColor = 2;
        int[] colorArray = new int[adj.size()];
        Arrays.fill(colorArray, -1);
        colorArray[0] = 1;
        boolean color1 = color(1, adj, colorArray, nColor);
        System.out.println(color1);

    }

    //TODO:correct this result currently not correct
    private static boolean color(int node, List<List<Integer>> adj, int[] colorArray, int nColor) {
        if (node >= adj.size()) return true;

        for (int withColor = 1; withColor <= colorArray.length; withColor++) {
            if (canColor(node, withColor, colorArray, adj)) {
                colorArray[node] = withColor;
                if (color(node + 1, adj, colorArray, nColor)) return true;
                colorArray[node] = -1;
            }
        }
        return false;
    }

    private static boolean canColor(int node, int withColor, int[] colorArray, List<List<Integer>> adj) {
        for (Integer ithNode : adj.get(node)) {
            if (colorArray[ithNode] == withColor) return false;
        }
        return true;
    }

    private static void gColor(int start, List<List<Integer>> adj, int nColor) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        int[] color = new int[adj.size()];
        boolean[] vis = new boolean[adj.size()];
        while (!queue.isEmpty()) {
            Integer n = queue.poll();
            vis[n] = true;
            System.out.print(n + " ");

            for (Integer temp : adj.get(n)) {
                if (!vis[temp]) {
                    queue.add(temp);
                    vis[temp] = true;
                }
            }
        }
    }


    private static void doMColorGraphDfs(Integer temp, int[] color, Stack<Integer> stack, List<List<Integer>> adj, int nColor, int[] vis) {


    }

    private static int cityWithSmallerNumberOfNeighbourThreshold(List<List<Edge>> adj, int distanceThreshold) {

        int[][] dist = new int[adj.size()][adj.size()];
        for (int i = 0; i < adj.size(); i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        for (int i = 0; i < adj.size(); i++) {
            for (Edge edge : adj.get(i)) {
                dist[i][edge.getNode()] = edge.getDistance();
                dist[edge.getNode()][i] = edge.getDistance();
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
        int count = 0;
        for (int i = 0; i < dist.length; i++) {
            count = 0;
            for (int j = 0; j < dist[0].length; j++) {
                if (dist[i][j] <= distanceThreshold) count++;
            }
            min = Math.min(count, min);
        }
        return min;
    }

    private static int numberOfWaysToArriveAtDestination(int s, int e, List<List<Edge>> adj) {
        //dist,node
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        queue.add(new int[]{0, 0});
        int[] dist = new int[adj.size()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        int[] ways = new int[adj.size()];
        dist[0] = 0;
        ways[0] = 1;
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int len = temp[0];
            int u = temp[1];

            for (Edge v : adj.get(u)) {
                if (dist[u] + v.getDistance() == dist[v.getNode()])
                    ways[v.getNode()] = ways[u] + ways[v.getNode()];
                else if (dist[u] + v.getDistance() < dist[v.getNode()]) {
                    dist[v.getNode()] = dist[u] + v.getDistance();
                    queue.add(new int[]{v.getDistance() + len, v.getNode()});
                    ways[v.getNode()] = ways[u];
                }
            }
        }
        return ways[e];

    }

    private static int minMultipliatioins(int[] arr, int start, int end) {
        int mod = 100000;
        int[] node = new int[100000];
        //steps,node
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0, start});
        int[] dist = new int[mod];
        Arrays.fill(dist, Integer.MAX_VALUE);
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int step = temp[0];
            int ithnode = temp[1];
            if (ithnode == end) return step;
            for (int n : arr) {
                int tnode = (n * ithnode) % mod;
                if (step + 1 < dist[tnode])
                    queue.add(new int[]{step + 1, tnode});
            }
        }
        return -1;
    }

    private static void cheapestFlightKstop(int s, int t, List<List<Edge>> adj) {
        Queue<int[]> queue = new ArrayDeque<>();
        //totalStop,node
        queue.add(new int[]{0, 0});
        int[] dist = new int[adj.size()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[s] = 0;
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int totalStop = temp[0];
            int u = temp[1];
            if (totalStop > 2) continue;
            for (Edge v : adj.get(u)) {
                if (dist[u] + v.getDistance() < dist[v.getNode()]) {
                    dist[v.getNode()] = dist[u] + v.getDistance();
                    queue.add(new int[]{totalStop + 1, v.getNode()});
                }
            }
        }
        System.out.println(dist[t]);
    }

    private static int pathWithMinEffort(int sr, int sc, int dr, int dc, int[][] g) {
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        queue.add(new int[]{0, sr, sc});
        int[][] dist = new int[g.length][g[0].length];
        for (int i = 0; i < dist.length; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[sr][sc] = 0;
        while (!queue.isEmpty()) {
            //dist,i,j
            int[] temp = queue.poll();
            int distance = temp[0];
            int i = temp[1];
            int j = temp[2];
            //if(i==dr && j==dc) return distance;
            cornersIterateMinEffort(i - 1, j, g, i, j, distance, queue, dist);
            cornersIterateMinEffort(i + 1, j, g, i, j, distance, queue, dist);
            cornersIterateMinEffort(i, j - 1, g, i, j, distance, queue, dist);
            cornersIterateMinEffort(i, j + 1, g, i, j, distance, queue, dist);

        }
        return dist[dr][dc];
    }

    private static void cornersIterateMinEffort(int i, int j, int[][] g, int prevRow, int prevCol, int prevdiff, Queue<int[]> queue, int[][] dist) {
        if (isSafe(i, j, g)) {
            int diff = Math.max(prevdiff, Math.abs(g[i][j] - g[prevRow][prevCol]));
            if (diff < dist[i][j]) {
                queue.add(new int[]{diff, i, j});
                dist[i][j] = diff;
            }

        }
    }

    private static int binaryMazeMinDistance(int sr, int sc, int dr, int dc, int[][] g) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0, sr, sc});
        int[][] dist = new int[g.length][g[0].length];
        for (int i = 0; i < dist.length; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[sr][sc] = 0;
        while (!queue.isEmpty()) {
            //dist,i,j
            int[] temp = queue.poll();
            int distance = temp[0];
            int i = temp[1];
            int j = temp[2];

            //use distance+1 in order to accomdate pathWithMinEffort method
            cornersIterate(i - 1, j, g, i, j, distance, queue, dist);
            cornersIterate(i + 1, j, g, i, j, distance, queue, dist);
            cornersIterate(i, j - 1, g, i, j, distance, queue, dist);
            cornersIterate(i, j + 1, g, i, j, distance, queue, dist);

        }
        return dist[dr][dc];
    }

    private static void cornersIterate(int i, int j, int[][] g, int prevRow, int prevCol, int distance, Queue<int[]> queue, int[][] dist) {
        if (isSafe(i, j, g) && g[i][j] == 1 && dist[prevRow][prevCol] + distance < dist[i][j]) {
            queue.add(new int[]{distance + 1, i, j});
            dist[i][j] = dist[prevRow][prevCol] + distance;
        }
    }

    private static boolean isSafe(int i, int j, int[][] g) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length) return false;
        return true;
    }

    private static int binaryMazePathCount(int sr, int sc, int dr, int dc, int[][] g, boolean[][] vis) {
        if (isSafe(sr, sc, g, vis)) return 0;
        if (g[sr][sc] == 0) return 0;
        if (g[dr][dr] == 2) return 1;
        if (dr > 2 || dc > 2) return 0;

        vis[sr][sc] = true;

        return 1 + binaryMazePathCount(sr - 1, sc, dr, dc, g, vis) + binaryMazePathCount(sr + 1, sc, dr, dc, g, vis) +
                binaryMazePathCount(sr - 1, sc, dr, dc, g, vis) + binaryMazePathCount(sr, sc + 1, dr, dc, g, vis);
    }

    private static boolean isSafe(int sr, int sc, int[][] g, boolean[][] vis) {
        return sr < 0 || sc < 0 || sr >= g.length || sc >= g[0].length || vis[sr][sc];
    }

    //  String startWord = "bat";String targetWord = "coz";
    //        String[] wordList = {"pat", "bot", "pot", "poz", "coz"};
    private static List<List<String>> wordLadder(char[] startWord, char[] targetWord, String[] wordList) {
        Set<String> set = Arrays.stream(wordList).collect(Collectors.toSet());
        Queue<List<String>> queue = new ArrayDeque<>();
        queue.add(Arrays.asList(String.valueOf(startWord)));
        List<List<String>> ans = new ArrayList<>();
        Set<String> tobeDeleted = new HashSet<>();
        int prev_size = 1;

        //1 because last elemenet will be deleted after checking this if 1
        while (set.size() > 0) {
            List<String> temp = queue.poll();
            String endElement = temp.get(temp.size() - 1);
            char[] polledString = endElement.toCharArray();

            if (temp.size() > prev_size) {
                set.removeAll(tobeDeleted);
                tobeDeleted = new HashSet<>();
                prev_size = temp.size();
            }

            for (int i = 0; i < polledString.length; i++) {
                char[] ithName = Arrays.copyOf(polledString, polledString.length);
                for (char j = 'a'; j <= 'z'; j++) {
                    ithName[i] = j;
                    String value = String.valueOf(ithName);
                    if (set.size() > 0 && set.contains(value)) {
                        ArrayList<String> newList = new ArrayList<>(temp);
                        newList.add(value);
                        queue.add(newList);
                        tobeDeleted.add(value);
                    }
                }
            }
        }

        //collect result in ans

        while (!queue.isEmpty()) {
            ans.add(queue.poll());
        }

        return ans;
    }


    private static void wordLadderBruteForce(char[] startWord, char[] targetWord, String[] wordList) {
        Set<String> set = Arrays.stream(wordList).collect(Collectors.toSet());
        Queue<List<String>> queue = new ArrayDeque<>();
        queue.add(Arrays.asList(String.valueOf(startWord)));
        List<List<String>> ans = new ArrayList<>();
        Set<String> tobeDeleted = new HashSet<>();
        int prev_size = 1;

        //1 because last elemenet will be deleted after checking this if 1
        while (set.size() > 0) {
            List<String> temp = queue.poll();
            String endElement = temp.get(temp.size() - 1);
            char[] polledString = endElement.toCharArray();

            if (temp.size() > prev_size) {
                set.removeAll(tobeDeleted);
                tobeDeleted = new HashSet<>();
                prev_size = temp.size();
            }

            for (int i = 0; i < polledString.length; i++) {
                char[] ithName = Arrays.copyOf(polledString, polledString.length);
                for (char j = 'a'; j <= 'z'; j++) {
                    ithName[i] = j;
                    String value = String.valueOf(ithName);
                    if (set.size() > 0 && set.contains(value)) {
                        ArrayList<String> newList = new ArrayList<>(temp);
                        newList.add(value);
                        queue.add(newList);
                        tobeDeleted.add(value);
                    }
                }
            }
        }
    }

    private static int[] shortPathUGwithoutWt(int V, List<List<Integer>> adj) {
        Stack<Integer> stack = new Stack<>();
        int[] ds = new int[V];
        Arrays.fill(ds, Integer.MAX_VALUE);
        stack.add(0);
        ds[0] = 0;
        while (!stack.isEmpty()) {
            Integer u = stack.pop();
            for (Integer v : adj.get(u)) {
                if (ds[u] + 1 < ds[v]) {
                    ds[v] = ds[u] + 1;
                    stack.add(v);
                }
            }
        }
        return ds;
    }

    private static int[] shortPathDGwithWt(int V, List<List<Edge>> adj) {
        Stack<Integer> stack = getTopoSort(V, adj);
        int[] ds = new int[V];
        Arrays.fill(ds, Integer.MAX_VALUE);
        ds[stack.peek()] = 0;
        while (!stack.isEmpty()) {
            Integer u = stack.pop();
            for (Edge v : adj.get(u)) {
                if (ds[u] + v.getDistance() < ds[v.getNode()]) {
                    ds[v.getNode()] = ds[u] + v.getDistance();
                }
            }
        }
        return ds;
    }

    private static Stack<Integer> getTopoSort(int v, List<List<Edge>> adj) {
        return topoSortWithEdges(adj, v, 0);
    }

    private static Stack<Integer> topoSortWithEdges(List<List<Edge>> adj, int v, int start) {
        Stack<Integer> ans = new Stack<>();
        boolean[] vis = new boolean[v];
        for (int i = start; i < v; i++) {
            if (!vis[i]) {
                doTopoSortWithEdges(i, adj, vis, ans);
            }
        }
        return ans;
    }

    private static void doTopoSortWithEdges(int start, List<List<Edge>> adj, boolean[] vis, Stack<Integer> ans) {
        vis[start] = true;
        for (Edge temp : adj.get(start)) {
            if (!vis[temp.getNode()]) {
                doTopoSortWithEdges(temp.getNode(), adj, vis, ans);
            }
        }
        //note
        ans.add(start);
    }

    private static String alienDicionary(int v, String[] dict) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            adj.add(0, new ArrayList<>());
        }

        for (int i = 0; i < dict.length - 1; i++) {
            char[] uv = getUV(dict[i].toCharArray(), dict[i + 1].toCharArray());
            adj.get(getu(uv)).add(getv(uv));
        }

        List<Integer> kahn = kahn(v, adj);
        StringBuffer buffer = new StringBuffer();
        for (Integer i : kahn) {
            buffer.append(Character.toChars(i + 97));
        }
        return buffer.toString();

    }

    private static char[] getUV(char[] u, char[] v) {
        int i = 0;
        while (i < u.length && i < v.length && u[i] == v[i]) {
            i++;
        }
        if (i + 1 == u.length && i + 1 < v.length) return new char[]{(char) -1, (char) -1};
        return new char[]{u[i], v[i]};
    }

    private static int getu(char[] uv) {
        return uv[0] - 97;
    }

    private static Integer getv(char[] uv) {
        return uv[1] - 97;
    }


    public static int[] findOrder(int numCourses, int[][] prerequisites) {

        if (numCourses < 2) return new int[1];

        int[] inDegree = new int[numCourses];
        Stack<Integer> stack = new Stack<>();

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
                stack.add(i);
            }
        }
        List<Integer> ans = new ArrayList<>();
        while (!stack.isEmpty()) {
            int course = stack.pop();
            ans.add(course);
            for (Integer temp : adj.get(course)) {
                inDegree[temp]--;
                if (inDegree[temp] == 0) {
                    stack.add(temp);
                }
            }
        }
        if (ans.size() == numCourses) {
            Collections.reverse(ans);
            return ans.stream().mapToInt(x -> x).toArray();
        } else
            return new int[1];
    }

    private static List<Integer> kahn(int totalVertices, List<List<Integer>> adj) {

        Stack<Integer> stack = new Stack<>();

        int[] in = new int[totalVertices];
        for (int u = 0; u < totalVertices; u++) {
            for (int v : adj.get(u)) {
                in[v]++;
            }
        }

        //count indegree
        for (int u = 0; u < totalVertices; u++) {
            if (in[u] == 0) stack.add(u);
        }
        List<Integer> ans = new ArrayList<>();
        while (!stack.isEmpty()) {
            Integer u = stack.pop();
            //  System.out.println(u+" ");
            ans.add(u);
            //decrease indegree of all v for u->v
            for (int v : adj.get(u)) {
                in[v]--;
                if (in[v] == 0) stack.add(v);
            }
        }
        return ans;
    }

    private static void doDfscTemplate1(List<List<Integer>> adj, Stack<Integer> stack, boolean[] vis, Integer n, Stack<Integer> ans) {
        vis[n] = true;
        for (Integer temp : adj.get(n)) {
            if (!vis[temp]) {
                doDfscTemplate1(adj, stack, vis, temp, ans);
            }
        }
        ans.add(n);
    }

    private static void dfsTemplate(int startNode, int v, List<List<Integer>> adj) {

        Stack<Integer> stack = new Stack<>();
        boolean[] vis = new boolean[v];
        for (int i = startNode; i < v; i++) {
            if (!vis[i]) {
                stack.add(i);
                while (!stack.isEmpty()) {
                    Integer n = stack.pop();
                    if (!vis[n]) {
                        doDfscTemplate(adj, stack, vis, n);
                    }
                }
            }
        }
    }

    private static void doDfscTemplate(List<List<Integer>> adj, Stack<Integer> stack, boolean[] vis, Integer n) {
        vis[n] = true;
        System.out.print(n + " ");
        for (Integer temp : adj.get(n)) {
            if (!vis[temp]) {
                stack.add(temp);
            }
        }
    }

    private static List<Integer> safeStates(int V, List<List<Integer>> adj) {
        Stack<Integer> queue = new Stack<>();
        boolean vis[] = new boolean[V];
        boolean pathvis[] = new boolean[V];
        boolean safe[] = new boolean[V];
        boolean[] isSafe = new boolean[1];
        for (int i = 0; i < V; i++) {
            //will run once for each disconnected componensts
            if (!vis[i]) {
                queue.add(i);
                while (!queue.isEmpty()) {
                    isSafe[0] = true;
                    Integer n = queue.pop();
                    if (!vis[n]) {
                        doCheckForCycle(adj, queue, vis, pathvis, n, safe, isSafe);
                    }
                }
            }
        }
        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < safe.length; i++) {
            if (safe[i]) ans.add(i);
        }
        return ans;
    }

    private static void doCheckForCycle(List<List<Integer>> adj, Stack<Integer> stack, boolean[] vis, boolean[] pathvis, Integer n, boolean[] safe, boolean[] isSafe) {
        vis[n] = true;
        pathvis[n] = true;
        System.out.print(n + " ");
        for (Integer temp : adj.get(n)) {
            if (vis[temp] && pathvis[temp]) {
                isSafe[0] = false;
            } else if (!vis[temp]) {
                stack.add(temp);
                doCheckForCycle(adj, stack, vis, pathvis, temp, safe, isSafe);
            }
        }
        pathvis[n] = false;
        safe[n] = isSafe[0];
    }

    private static boolean biPart(List<List<Integer>> adj, int v) {
        boolean vis[] = new boolean[v];
        int col[] = new int[v];
        Arrays.fill(col, -1);
        int counter = 0;
        Queue<Integer> stack = new ArrayDeque<>();
        stack.add(1);
        while (!stack.isEmpty()) {
            Integer n = stack.poll();
            if (!vis[n]) {
                vis[n] = true;
                for (Integer temp : adj.get(n)) {
                    if (col[temp] != -1 && col[temp] == counter) return true;
                    if (!vis[temp]) stack.add(temp);
                    col[temp] = counter;
                    counter = counter == 0 ? 1 : 0;
                }
            }
        }
        return false;
    }

    private static int numberOfIsland(int[][] m) {
        Set<List<Integer>> ans = new HashSet<>();
        boolean vis[][] = new boolean[m.length][m[0].length];
        List<int[]> temp = new ArrayList<>();
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (m[i][j] == 1 && !vis[i][j]) {
                    dfsNumberOfIsland(i, j, m, vis, temp);
                    reduceToStart(temp.get(temp.size() - 1), temp);
                    List<Integer> collect = temp.stream().map(x -> Arrays.stream(x)).flatMapToInt(x -> x).boxed().collect(Collectors.toList());
                    ans.add(collect);
                    temp = new ArrayList<>();
                }
            }
        }
        return ans.size();
    }

    private static void reduceToStart(int[] start, List<int[]> temp) {
        for (int[] t : temp) {
            t[0] = start[0] - t[0];
            t[1] = start[1] - t[1];
        }
    }

    private static void dfsNumberOfIsland(int is, int js, int[][] m, boolean[][] vis, List<int[]> temp) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{is, js});
        temp.add(new int[]{is, js});
        while (!queue.isEmpty()) {
            int[] n = queue.poll();
            int i1 = n[0];
            int j1 = n[1];
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i == 0 && j == 0) continue;
                    doDfsIsland(m, vis, queue, i1 + i, j1 + j, temp);
                }
            }

        }
    }

    private static void doDfsIsland(int[][] m, boolean[][] vis, Queue<int[]> queue, int i, int j, List<int[]> temp) {
        if (isValid(i, j, m) && m[i][j] == 1 && !vis[i][j]) {
            queue.add(new int[]{i, j});
            temp.add(new int[]{i, j});
            vis[i][j] = true;
        }
    }

    private static int numberOfEnclaves(int[][] m) {
        //do the dfs from outer boundry & set which cannot be converted
        //then iterate array & set 0 to x if not visited

        boolean vis[][] = new boolean[m.length][m[0].length];
        //first column , last column
        for (int i = 0; i < m.length; i++) {
            if (m[i][0] == 1)
                surroundedRegionDFS(i, 0, m, vis);
            if (m[i][m[0].length - 1] == 1)
                surroundedRegionDFS(i, m[0].length - 1, m, vis);
        }
        //first row , last row column
        for (int j = 1; j < m[0].length - 1; j++) {
            if (m[0][j] == 1)
                surroundedRegionDFS(0, j, m, vis);
            if (m[m.length - 1][j] == 1)
                surroundedRegionDFS(m.length - 1, j, m, vis);
        }
        int ans = 0;
        //set all 0 to x
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (m[i][j] == 1 && !vis[i][j])
                    ans++;
            }
        }
        return ans;
    }

    private static void surroundedRegionDFS(int i, int j, int[][] m, boolean[][] vis) {

        Stack<int[]> stack = new Stack<>();
        stack.add(new int[]{i, j});
        while (!stack.isEmpty()) {
            int[] n = stack.pop();
            int i1 = n[0];
            int j2 = n[1];
            vis[i1][j2] = true;
            //for four direction & add to queue if any element is 0
            exploreAll4Corners(m, vis, stack, i1 - 1, j2);
            exploreAll4Corners(m, vis, stack, i1 + 1, j2);
            exploreAll4Corners(m, vis, stack, i1, j2 - 1);
            exploreAll4Corners(m, vis, stack, i1, j2 + 1);
        }
    }

    private static void exploreAll4Corners(int[][] m, boolean[][] vis, Stack<int[]> stack, int i, int j) {
        if (isValid(i, j, m) && m[i][j] == 1 && !vis[i][j]) {
            stack.add(new int[]{i, j});
            vis[i][j] = true;
        }
    }

    private static int[][] distanceOfNearestCell(int[][] m) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean vis[][] = new boolean[m.length][m[0].length];
        int dis[][] = new int[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (m[i][j] == 1) {
                    vis[i][j] = true;
                    queue.add(new int[]{i, j, 0});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            int i = poll[0];
            int j = poll[1];
            int k = poll[2];

            checkForUpDownleftRigt(m, queue, vis, dis, i - 1, j, k);
            checkForUpDownleftRigt(m, queue, vis, dis, i + 1, j, k);
            checkForUpDownleftRigt(m, queue, vis, dis, i, j - 1, k);
            checkForUpDownleftRigt(m, queue, vis, dis, i, j + 1, k);

        }
        return dis;
    }

    private static void checkForUpDownleftRigt(int[][] m, Queue<int[]> queue, boolean[][] vis, int[][] dis, int i, int j, int k) {
        if (isValid(i, j, m) && m[i][j] == 0 && !vis[i][j]) {
            queue.add(new int[]{i, j});
            vis[i][j] = true;
        }
    }

    private static boolean isValid(int i, int j, int[][] g) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length) return false;
        return true;
    }

    private static boolean isValid(int i, int j, char[][] g) {
        if (i < 0 || j < 0 || i >= g.length || j >= g[0].length) return false;
        return true;
    }

    private static boolean cycleBFS(List<List<Integer>> adj, int v) {

        boolean[] vis = new boolean[v];
        Stack<Node> stack = new Stack<>();
        for (int i = 1; i < v; i++) {
            if (!vis[i]) {
                stack.add(new Node(1, -1, 0));
                if (checkForCycle(adj, vis, stack)) return true;
            }
        }

        return false;
    }

    private static boolean checkForCycle(List<List<Integer>> adj, boolean[] vis, Stack<Node> stack) {
        while (!stack.isEmpty()) {
            Node n = stack.pop();
            if (!vis[n.getNode()]) {
                vis[n.getNode()] = true;
                //System.out.print(n.getu() + " ");
                for (Integer temp : adj.get(n.getNode())) {
                    if (vis[temp] && temp != n.getParent()) {
                        System.out.println("temp:" + temp);
                        return true;
                    }
                    if (!vis[temp]) stack.add(new Node(temp, n.getNode(), 0));
                }
            }
        }
        return false;
    }

    public static int orangesRotting(int[][] g) {
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                if (g[i][j] == 2)
                    queue.add(new int[]{i, j, 0});
            }
        }

        boolean[][] vis = new boolean[g.length][g[0].length];
        int ans = 0;
        while (!queue.isEmpty()) {
            int[] n = queue.poll();
            //up
            int row = n[0];
            int col = n[1];
            int count = n[2];

            doRottenOrganes(g, queue, vis, row - 1, col, count);
            doRottenOrganes(g, queue, vis, row + 1, col, count);
            doRottenOrganes(g, queue, vis, row, col - 1, count);
            doRottenOrganes(g, queue, vis, row, col + 1, count);

            ans = Math.max(ans, count);
        }
        return ans;
    }

    private static void doRottenOrganes(int[][] g, Queue<int[]> queue, boolean[][] vis, int i, int j, int k) {
        if (isValid(i, j, g) && g[i][j] == 1 && !vis[i][j]) {
            queue.add(new int[]{i, j, k + 1});
            vis[i][j] = true;
        }
    }

    public static int[][] mColorFill(int[][] image, int sr, int sc, int newColor) {
        doColorFill(image, sr, sc, newColor, image[sr][sc]);
        return image;
    }

    private static void doColorFill(int[][] image, int sr, int sc, int newColor, int initial) {
        if (sr < 0 || sc < 0 || sr >= image.length || sc >= image[0].length || image[sr][sc] == newColor) return;

        if (image[sr][sc] == initial) image[sr][sc] = newColor;

        doColorFill(image, sr - 1, sc, newColor, initial);
        doColorFill(image, sr + 1, sc, newColor, initial);
        doColorFill(image, sr, sc - 1, newColor, initial);
        doColorFill(image, sr, sc + 1, newColor, initial);

    }

    private static int numberOfIslands(int[][] g) {
        int ans = 0;
        boolean[][] vis = new boolean[g.length][g[0].length];
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                if (!vis[i][j] && g[i][j] != 0) {
                    ans++;
                    markAllReachableToZero(g, vis, i, j);
                }
            }
        }
        return ans;
    }

    private static void markAllReachableToZero(int[][] g, boolean[][] vis, int row, int col) {

        //mark visited
        vis[row][col] = true;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{row, col});
        while (!queue.isEmpty()) {
            //visit all neighbour nodes
            int[] n = queue.poll();

            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    int delr = n[0] + i;
                    int delc = n[1] + j;
                    if (i == 0 && j == 0) continue;
                    if (delr < 0 || delc < 0 || delr >= g.length || delc >= g[0].length) continue;
                    if (!vis[delr][delc] && g[delr][delc] == 1) {
                        queue.add(new int[]{delr, delc});
                        vis[delr][delc] = true;
                    }
                }
            }
        }
    }

    private static int numberOfProvinces(int start, int v, List<List<Integer>> adj) {
        boolean vis[] = new boolean[v];
        int ans = 0;
        for (int node = start; node < v; node++) {
            if (!vis[node]) {
                ans++;
                dfsNumberOfProvinces(node, vis, adj);
            }
        }
        return ans;
    }

    private static void dfsNumberOfProvinces(int node, boolean[] vis, List<List<Integer>> adj) {

        Stack<Integer> stack = new Stack<>();
        stack.add(node);
        while (!stack.isEmpty()) {
            Integer n = stack.pop();
            if (!vis[n]) {
                vis[n] = true;
                for (Integer temp : adj.get(n)) {
                    if (!vis[temp]) stack.add(temp);
                }
            }
        }
    }

    private static void dfs(int startNode, int v, List<List<Integer>> adj) {

        Stack<Integer> stack = new Stack<>();
        stack.add(startNode);
        boolean[] vis = new boolean[v];
        while (!stack.isEmpty()) {
            Integer n = stack.pop();
            if (!vis[n]) {
                vis[n] = true;
                System.out.print(n + " ");
                for (Integer temp : adj.get(n)) {
                    if (!vis[temp]) {
                        stack.add(temp);
                    }
                }
            }
        }
    }

    private static void dfsTraversal(Integer n, Stack<Integer> stack, boolean[] vis, List<List<Integer>> adj) {
        vis[n] = true;
        System.out.print(n + " ");
        for (Integer temp : adj.get(n)) {
            if (!vis[temp]) {
                stack.add(temp);
            }
        }
    }



    /* earlier implemented methods
     * */

    private static boolean biPartite(List<List<Integer>> adj, int v, int start) {
        Stack<Integer> stack = new Stack<>();
        stack.add(start);
        boolean[] vis = new boolean[v];
        ;
        Boolean[] col = new Boolean[v];
        boolean var = false;
        while (!stack.isEmpty()) {
            Integer n = stack.pop();
            if (col[n] != null && vis[n] && var == col[n]) return false;
            else if (!vis[n]) {
                vis[n] = true;
                col[n] = var;
                var = !var;
                //   System.out.println(n);
                for (Integer t : adj.get(n)) {
                    if (!vis[t]) {
                        stack.add(t);
                    }
                }
            }
        }
        return true;
    }

    //for strongly connected components(SCC)
    //valid only for directedGraph
    private static void kosaRaju(List<List<Integer>> adj, int v, int start) {
        //1.do toposort i.e get elements from last dfs vitited to first
        //2. transpose adj
        //3. do dfs & print element
        Stack<Integer> topoStack = topoSort(adj, v, start);

        List<List<Integer>> transposeAdj = new ArrayList<>(v);
        //initialize
        for (int i = 0; i < v; i++) {
            transposeAdj.add(i, new ArrayList<>());
        }

        //transpose
        for (int i = 0; i < v; i++) {
            for (int n : adj.get(i)) {
                List<Integer> temp = transposeAdj.get(n);
                temp.add(i);
            }
        }
        kosaRajuDfs(transposeAdj, topoStack, v);
    }

    private static void kosaRajuDfs(List<List<Integer>> tadj, Stack<Integer> stack, int v) {
        boolean[] vis = new boolean[v];
        while (!stack.isEmpty()) {
            Integer temp = stack.pop();
            if (!vis[temp]) {
                ArrayList<Integer> ans = kosaRajuDFS(temp, vis, tadj, stack, new ArrayList<>());
                System.out.println(ans);
            }
        }
    }

    private static ArrayList<Integer> kosaRajuDFS(Integer n, boolean[] vis, List<List<Integer>> tadj, Stack<Integer> stack, ArrayList<Integer> temp) {
        vis[n] = true;
        temp.add(n);
        for (Integer t : tadj.get(n)) {
            if (!vis[t]) {
                stack.add(t);
                kosaRajuDFS(t, vis, tadj, stack, temp);
            }
        }
        return temp;
    }

    //work only for Directed Graph
    //for negative cycles or negative edges , if UDG then add both edges
    //for shortest path , SSST
    static int[] bellman_ford(int V, ArrayList<ArrayList<Integer>> adj, int start) {

        int dist[] = new int[V];
        //update to max distance
        for (int i = 0; i < V; i++) {
            dist[i] = (int) 1e8;
        }
        dist[start] = 0;
        //relaxation of edges n-1 times
        for (int i = 0; i < V; i++) {
            for (ArrayList<Integer> edge : adj) {
                int u = edge.get(0);
                int v = edge.get(1);
                int w = edge.get(2);
                if (dist[u] != (int) 1e8 && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }
        //check for negative cycle , if yes return {-1}
        for (ArrayList<Integer> l : adj) {
            int u = l.get(0);
            int w = l.get(1);
            int v = l.get(2);
            if (dist[u] != (int) 1e8 && dist[u] + w < dist[v]) {
                return new int[]{-1};
            }
        }
        return dist;
    }

    private static Stack<Integer> topoSort(List<List<Integer>> adj, int v, int start) {
        Stack<Integer> ans = new Stack<>();
        boolean[] vis = new boolean[v];
        for (int i = start; i < v; i++) {
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

    private static void topoSortIncomingEdgeKahnAlgo(List<List<Integer>> adj, int v) {
        List<Integer> ans = new ArrayList<>();
        int[] arrIncoming = new int[v];

        Queue<Integer> queue = new ArrayDeque<>();
        //update incoming edges
        for (int i = 0; i < v; i++) {
            for (Integer in : adj.get(i)) {
                arrIncoming[in]++;
            }
        }
        //add first set of incoming edges to queue whose incoming is 0
        for (int i = 0; i < v; i++) {
            if (arrIncoming[i] == 0) queue.add(i);
        }

        while (!queue.isEmpty()) {
            Integer n = queue.poll();
            //add polled to ans
            ans.add(n);
            //for all element decremetn incoming edges
            for (Integer in : adj.get(n)) {
                arrIncoming[in]--;
                if (arrIncoming[in] == 0) queue.add(in);
            }
        }

        System.out.println(ans);

    }

    //TODO
    private static boolean isCycleDirected(int start, List<List<Integer>> adj, int v) {
        boolean vis[] = new boolean[v];
        boolean[] dfsvis = new boolean[v];
        for (int n = start; n < v; n++) {
            if (!vis[n]) {
                if (checkCycleDirected(n, adj, vis, dfsvis)) return true;
            }
        }
        return false;
    }

    private static boolean checkCycleDirected(int node, List<List<Integer>> adj, boolean[] vis, boolean[] pathvis) {
        vis[node] = true;
        pathvis[node] = true;
        for (Integer temp : adj.get(node)) {
            if (vis[temp] && pathvis[temp]) return true;
            else if (!vis[temp]) {
                if (checkCycleDirected(temp, adj, vis, pathvis)) return true;
            }
        }
        pathvis[node] = false;
        return false;
    }

    //sort edges
    //keep adding if not in same component
    private static List<EdgeUVW> krushalMST(ArrayList<EdgeUVW> adj, int k) {
        Collections.sort(adj, Comparator.comparingInt(EdgeUVW::getDist).thenComparing(EdgeUVW::getU).thenComparing(EdgeUVW::getV));
        List<EdgeUVW> mst = new ArrayList<>();
        DisjointSet disjointset = new DisjointSet(k);
        for (EdgeUVW edge : adj) {
            //keep adding to result if u & v is not in same component
            if (!disjointset.isInSameComponent(edge.getU(), edge.getV())) {
                mst.add(edge);
            }
            //update distjointSet with latest edge
            disjointset.groupBySize(edge.getU(), edge.getV());
        }
        return mst;
    }

    private static List<int[]> primMST(int start, List<List<Edge>> adj, int k) {

        //1.take out elment from min heap , mark as visited & add to result
        //2. for add all adjacent unvisited nodes add to heap
        //3.keep repeating till heap is not empty
        Queue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance));
        List<int[]> listOfEges_ans = new ArrayList<>();
        boolean vis[] = new boolean[k];
        queue.add(new Node(start, -1, 0));
        while (!queue.isEmpty()) {
            Node u = queue.poll();
            if (vis[u.getNode()]) continue;
            if (u.getParent() != -1)
                listOfEges_ans.add(new int[]{u.getParent(), u.getNode()});

            vis[u.getNode()] = true;

            for (Edge v : adj.get(u.getNode())) {
                if (!vis[v.getNode()]) {
                    queue.add(new Node(v.getNode(), u.getNode(), v.getDistance()));
                }
            }
        }
        listOfEges_ans.forEach(x -> System.out.println(Arrays.toString(x)));

        return listOfEges_ans;
    }

    private static List<Integer> dijkstraShortestPath(int start, List<List<Edge>> adj, int V) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingInt(Edge::getDistance).thenComparing(Edge::getNode));
        queue.add(new Edge(start, 0));
        int dist[] = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        int parent[] = new int[V];
        //1. take out element from queue
        // 2. if sum of parent distance and currentNode distance is less than already stored at distance arry then update it
        //otherwise
        //keep repeating till queue is not empty
        while (!queue.isEmpty()) {
            Edge u = queue.poll();
            for (Edge v : adj.get(u.getNode()))
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

    //min distance from start to end
    //does not work for negative/weight cycle
    //SSST
    private static int dijkstraShortestPathFromStart(int start, ArrayList<List<Edge>> adj, int k) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingInt(Edge::getDistance).thenComparing(Edge::getNode));
        queue.add(new Edge(0, start));
        int dist[] = new int[k];
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
                if (dist[u.getNode()] + v.getDistance() < dist[v.getNode()]) {
                    dist[v.getNode()] = dist[u.getNode()] + v.getDistance();
                    queue.add(v);
                }
        }
        //return distance storead at last index
        return dist[k - 1];
    }

}

class Node {

    private int node;
    private int parent;
    private int distance;

    public Node(int node, int parent, int distance) {
        this.node = node;
        this.parent = parent;
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public int getNode() {
        return node;
    }

    public int getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "Node{" +
                "dist=" + distance +
                ", node=" + node +
                ", parent=" + parent +
                '}';
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

class Edge {

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

    public Edge(int node, int dist) {
        this.node = node;
        this.dist = dist;
    }

}


class Wpair {
    char[] name;
    int count;

    public Wpair(char[] name, int count) {
        this.name = name;
        this.count = count;
    }

    public char[] getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}