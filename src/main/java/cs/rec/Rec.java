package cs.rec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static cs.ArrayDemo.swap;

public class Rec {
    public static void main(String[] args) {
        int g[][] = new int[4][4];
        int a[][] = {
                {1, 0, 0, 0},
                {1, 1, 0, 1},
                {1, 1, 0, 0},
                {0, 1, 1, 1}
        };
        int p[] = new int[0];

        List<List<Integer>> collect = List.of(Arrays.stream(p).boxed().toList());

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            graph.add(i, new ArrayList<>());
        }

        graph.get(0).addAll(List.of(1, 2, 3));
        graph.get(1).addAll(List.of(0, 2));
        graph.get(2).addAll(List.of(0, 1, 3));
        graph.get(3).addAll(List.of(0, 2));
        int col[] = new int[4];
        List<Integer> ans = new ArrayList<>();
        ans.add(1);
        ans.add(2);
        ans.add(3);
        int[] ar = {5, 3, 2, 4, 1};
        int ms = countInversion(0, ar.length - 1, ar);
        System.out.println(Arrays.toString(ar));
        System.out.println(ms);
    }

    // Time Complexity: O(N*logN), where N = size of the given array.
    //Reason: We are not changing the merge sort algorithm except by adding a variable to it.
    // So, the time complexity is as same as the merge sort.
    //video - 20
    private static int countInversion(int start, int end, int[] ar) {
        if (start >= end) return 0;
        int count = 0;
        int mid = start + (end - start) / 2;
        count += countInversion(start, mid, ar);
        count += countInversion(mid + 1, end, ar);
        count += mergeAndCount(start, mid, end, ar);
        return count;
    }

    private static int mergeAndCount(int lo, int mid, int hi, int[] a) {
        int count = 0;
        int temp[] = new int[hi - lo + 1];
        int start1 = lo;
        int start2 = mid + 1;
        int itemp = 0;
        while (start1 <= mid && start2 <= hi) {
            if (a[start1] < a[start2]) {
                temp[itemp++] = a[start1++];
            } else {
                count += mid - start1 + 1;
                temp[itemp++] = a[start2++];
            }
        }
        while (start1 <= mid) {
            temp[itemp++] = a[start1++];
        }
        while (start2 <= hi) {
            temp[itemp++] = a[start2++];
        }

        for (int i = 0; i < temp.length; i++) {
            a[lo++] = temp[i];
        }
        return count;
    }


    //video - 18
    //Time Complexity: O(N) * O(N) = O(N^2)
    //Reason: We are placing N numbers in N positions.
    // This will take O(N) time. For every number, we are reducing the search space by removing the element
    // already placed in the previous step. This takes another O(N) time.
    private static void kThPermutation(List<Integer> list, int k) {
        List<Integer> ans = new ArrayList<>();
        while (list.size() > 1) {
            int each_block_Size = fact(list.size()) / list.size();
            ans.add(list.get(k / each_block_Size));
            list.remove(k / each_block_Size);
            k = k % each_block_Size;
        }
        ans.add(list.get(0));
        System.out.println(ans);
    }

    //Time Complexity: O(4^(m*n)), because on every cell we need to try 4 different directions.
    //video - 19
    private static void ratInMaze(int i, int j, int[][] a, boolean[][] vis, ArrayList<String> temp, List<List<String>> ans) {
        if (a[i][j] == 0) return;
        if (i == a.length - 1 && j == a[0].length - 1) {
            ans.add(new ArrayList<>(temp));
            return;
        }

        if (i < 0 || j < 0 || i >= a.length || j >= a[0].length) {
            return;
        }

        //D- down
        if (i + 1 < a.length && !vis[i + 1][j]) {
            temp.add("D");
            vis[i + 1][j] = true;
            ratInMaze(i + 1, j, a, vis, temp, ans);
            vis[i + 1][j] = false;
            temp.remove(temp.size() - 1);
        }

        if (j - 1 >= 0 && !vis[i][j - 1]) {
            temp.add("L");
            vis[i][j - 1] = true;
            ratInMaze(i, j - 1, a, vis, temp, ans);
            temp.remove(temp.size() - 1);
            vis[i][j - 1] = false;
        }

        if (j + 1 < a[0].length && !vis[i][j + 1]) {
            vis[i][j + 1] = true;
            temp.add("R");
            ratInMaze(i, j + 1, a, vis, temp, ans);
            temp.remove(temp.size() - 1);
            vis[i][j + 1] = false;

        }

        if (i - 1 >= 0 && !vis[i - 1][j]) {
            vis[i - 1][j] = true;
            temp.add("U");
            ratInMaze(i - 1, j, a, vis, temp, ans);
            temp.remove(temp.size() - 1);
            vis[i - 1][j] = false;

        }
    }


    //Time Complexity: O( (2^n) *k*(n/2) )
    //Reason: O(2^n) to generate every substring and O(n/2)  to check if the substring generated is a palindrome.
    // O(k) is for inserting the palindromes in another data structure, where k  is the average length of the palindrome list.
    //video - 17
    //return all partition string
    private static void palindromePartitioning(int ind, String s, ArrayList<String> temp, List<List<String>> ans) {

        if (ind >= s.length()) {
            ans.add(new ArrayList<>(temp));
            return;
        }
        for (int i = ind; i < s.length(); i++) {
            String substring = s.substring(ind, i + 1);
            if (isPallindrome(substring.toCharArray())) {
                temp.add(substring);
                palindromePartitioning(i + 1, s, temp, ans);
                temp.remove(temp.size() - 1);
            }
        }
    }

    private static boolean isPallindrome(char[] c) {
        int start = 0;
        int end = c.length - 1;
        while (start < end) {
            if (c[start++] != c[end--]) return false;
        }
        return true;
    }


    //TC - >  O( N^M)
    //video - 16
    // boolean b = mColoringGraph(graph, 0, 3, 4, col);
    private static boolean mColoringGraph(List<List<Integer>> graph, int node, int M, int N, int[] colArray) {

        if (node == N) return true;
        for (int col = 1; col <= M; col++) {
            if (canColorNodeWithiColor(node, col, graph, colArray)) {
                colArray[node] = col;
                if (mColoringGraph(graph, node + 1, M, N, colArray)) return true;
                colArray[node] = 0;
            }
        }
        return false;
    }

    private static boolean canColorNodeWithiColor(int node, int color, List<List<Integer>> graph, int[] col) {
        for (int child : graph.get(node)) {
            if (col[child] == color) return false;
        }
        return true;
    }

    // O(9(n ^ 2)),
    //video - 15
    //  char[][] board = {
    //                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
    //                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
    //                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
    //                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
    //                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
    //                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
    //                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
    //                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
    //                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
    //        };
    private static boolean sudkoSolver(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    for (char val = '1'; val <= '9'; val++) {
                        if (canPlaceValAt(i, j, board, val)) {
                            board[i][j] = val;
                            if (sudkoSolver(board)) return true;
                            board[i][j] = '.';
                        }
                    }
                    //return false so that continue with other val
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean canPlaceValAt(int row, int col, char[][] board, char val) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == val) return false;
            if (board[row][i] == val) return false;
            if (board[3 * (row / 3) + (i / 3)][3 * (col / 3) + (i % 3)] == val) return false;
        }
        return true;
    }

    //RP-14
    // Another way
    public List<List<Integer>> permute(int[] a) {
        List<List<Integer>> ans = new ArrayList<>();
        f(0, ans, a);
        return ans;
    }

    private static void f(int i, List<List<Integer>> ans, int[] a) {
        if (i >= a.length) {
            ans.add(Arrays.stream(a).boxed().toList());
            return;
        }
        for (int j = i; j < a.length; j++) {
            swap(i, j, a);
            f(i + 1, ans, a);
            swap(i, j, a);
        }
    }

    // RP-13
    //time complexity : n!* n
    // n! to generate all permutation & n to iterate array elements
    public List<List<Integer>> permute2(int[] nums) {
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        pr(0, n, new boolean[n], nums, ans, new ArrayList<>());
        return ans;
    }

    private void pr(int ind, int n, boolean[] vis, int[] a, List<List<Integer>> ans, ArrayList<Integer> temp) {
        if (temp.size() == n) {
            ans.add(new ArrayList<>(temp));
            return;
        }
        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                vis[i] = true;
                temp.add(a[i]);
                pr(i + 1, n, vis, a, ans, temp);
                temp.remove(temp.size() - 1);
                vis[i] = false;
            }
        }
    }

    //RP-13 , without target
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        f(0, nums, new ArrayList<>(), ans);
        return ans;
    }

    // using try & pick subsequences
    private static void f(int ind, int[] s, List<Integer> temp, List<List<Integer>> ans) {
        ans.add(new ArrayList<>(temp));
        for (int i = ind; i < s.length; i++) {
            // will act as base case
            if (i > ind && s[i] == s[i - 1])
                continue;
            temp.add(s[i]);
            f(i + 1, s, temp, ans);
            temp.remove(temp.size() - 1);
        }
    }

    //RP-12 , without target
    ArrayList<Integer> subsetSums(ArrayList<Integer> arr, int N) {

        ArrayList<Integer> ans = new ArrayList<>();
        f(0, N, 0, ans, arr);
        return ans;
    }

    static void f(int i, int N, int tempSum, ArrayList<Integer> ans, ArrayList<Integer> a) {
        if (i == N) {
            ans.add(tempSum);
            return;
        }
        //take
        f(i + 1, N, tempSum + a.get(i), ans, a);

        //not_take
        f(i + 1, N, tempSum, ans, a);

    }

    //RP-11 ,sum with target
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> ans = new ArrayList<>();
        f1(0, target, new ArrayList<>(), candidates, ans);
        return ans;
    }

    static void f1(int ind, int target, List<Integer> temp, int[] a, List<List<Integer>> ans) {
        if (target == 0) {
            ans.add(new ArrayList<>(temp));
            return;
        }
        // try and pick subsequences
        for (int i = ind; i < a.length; i++) {
            // take first element always & test for duplicates after wards
            if (i > ind && a[i] == a[i - 1])
                continue;

            if (a[i] > target)
                break;

            temp.add(a[i]);
            f1(i + 1, target - a[i], temp, a, ans);
            temp.remove(temp.size() - 1);

        }
    }

    //RP-10 ,sum with target
    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        List<List<Integer>> result = new ArrayList<>();
        f(0, target, new ArrayList<>(), candidates, result);
        return result;
    }

    static void f(int ind, int sum, List<Integer> temp, int[] a, List<List<Integer>> result) {
        if (ind == a.length) {
            if (sum == 0) {
                result.add(new ArrayList<>(temp));
            }
            return;
        }

        // take index ,can take same index multiple times
        if (sum - a[ind] >= 0) {
            temp.add(a[ind]);
            f(ind, sum - a[ind], temp, a, result);
            temp.remove(Integer.valueOf(a[ind]));
        }

        // not take index
        f(ind + 1, sum, temp, a, result);

    }


    //RP - 9
    public static List<Integer> quickSort(List<Integer> arr) {
        int a[] = arr.stream().mapToInt(x -> x).toArray();
        qs(0, a.length - 1, a);
        return Arrays.stream(a).boxed().collect(Collectors.toList());
    }

    static void qs(int lo, int hi, int a[]) {
        if (lo >= hi)
            return;

        int pIndex = placeAndGetPIndex(lo, hi, a);
        qs(lo, pIndex - 1, a);
        qs(pIndex + 1, hi, a);

    }

    static int placeAndGetPIndex(int lo, int hi, int[] a) {
        int pivot = a[lo];
        int i = lo, j = hi;

        while (i < j) {
            i = lo;
            j = hi;
            // note i<=hi
            while (i <= hi && a[i] <= pivot) {
                i++;
            }
            // note j>=lo
            while (j >= lo && a[j] > pivot) {
                j--;
            }
            if (i < j)
                swap(i, j, a);

        }
        swap(lo, j, a);
        return j;
    }

    static void swap(int p1, int p2, int[] a) {
        int temp = a[p2];
        a[p2] = a[p1];
        a[p1] = temp;
    }

    //RP-8
    public static void mergeSort(int[] arr, int n) {
        mergeSort(0, n - 1, arr);
    }

    static void mergeSort(int l, int r, int[] arr) {
        if (l >= r)
            return;
        int mid = l + r >> 1;
        mergeSort(l, mid, arr);
        mergeSort(mid + 1, r, arr);
        merge(l, mid, r, arr);
    }

    static void merge(int l, int mid, int r, int[] arr) {
        int[] temp = new int[r - l + 1];
        int ind = 0, lStart = l, rStart = mid + 1;
        while (lStart <= mid && rStart <= r) {
            if (arr[lStart] < arr[rStart]) {
                temp[ind++] = arr[lStart++];
            } else {
                temp[ind++] = arr[rStart++];
            }
        }
        while (lStart <= mid)
            temp[ind++] = arr[lStart++];

        while (rStart <= r)
            temp[ind++] = arr[rStart++];

        for (int i = l, j = 0; i <= r; i++) {
            arr[i] = temp[j++];
        }
    }

    private static int fact(int n) {
        if (n == 1) return n;
        return n * fact(n - 1);
    }

    private static void nqueen(int row, int col, int[][] g) {
        //if last columnn+1  then return
        if (col == g[0].length) {
            System.out.println(Arrays.deepToString(g));
            return;
        }

        for (int i = row; i < g.length; i++) {
            if (canPlaceAt(i, col, g)) {
                g[i][col] = 1;
                nqueen(0, col + 1, g);
                g[i][col] = 0;
            }
        }
    }

    private static boolean canPlaceAt(int row, int col, int[][] g) {
        //check for row
        for (int j = 0; j < g[0].length; j++) {
            if (g[row][j] == 1) return false;
        }

        //check for col
        for (int i = 0; i < g[0].length; i++) {
            if (g[i][col] == 1) return false;
        }

        //check for diagonals
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 1 && j == 1) continue;
                int er = row + i;
                int ec = col + j;
                if (er < 0 || ec < 0 || er >= g.length || ec >= g[0].length) continue;
                if (g[er][ec] == 1) return false;
            }
        }

        return true;
    }

    //TC -> O(n!)*n -- n for loop
    //video - 13
    private static void per(int ind, int[] a) {
        if (ind == a.length) {
            System.out.println(Arrays.toString(a));
            return;
        }
        for (int i = ind; i < a.length; i++) {
            swap(ind, i, a);
            per(ind + 1, a);
            swap(ind, i, a);
        }
    }

    //TC -> O(n!)*n -- n for loop
    //video-12
    private static void permutationUsingMark(int[] a, ArrayList<Integer> ds, boolean[] mark) {
        if (ds.size() == a.length) {
            System.out.println(ds);
            return;
        }

        //backtracking
        for (int i = 0; i < a.length; i++) {
            if (!mark[i]) {
                ds.add(a[i]);
                mark[i] = true;
                permutationUsingMark(a, ds, mark);
                mark[i] = false;
                ds.remove(ds.size() - 1);
            }
        }
    }

    //video 11
    private static void uniqueSubSet(int ind, int[] a, ArrayList<Integer> temp, List<List<Integer>> ans) {
        if (ind == a.length) return;
        ans.add(new ArrayList<>(temp));

        for (int j = ind; j < a.length; j++) {
            if (j > ind && a[j] == a[j - 1]) continue;
            temp.add(a[j]);
            uniqueSubSet(j + 1, a, temp, ans);
            temp.remove(temp.size() - 1);
        }
    }

    //video 10
    private static void combinationSum3(int i, int[] a, List<Integer> ans, int sum) {
        if (i == a.length) {
            ans.add(sum);
            return;
        }

        //take
        combinationSum3(i + 1, a, ans, sum + a[i]);

        //not take
        combinationSum3(i + 1, a, ans, sum);
    }

    //video-9
    private static void combinationSum2(int ind, int[] a, ArrayList<Integer> temp, List<List<Integer>> ans, int target) {
        if (target == 0) {
            ans.add(new ArrayList<>(temp));
            return;
        }

        for (int i = ind; i < a.length; i++) {
            //check for duplicates
            if (i > ind && a[i] == a[i - 1]) continue;
            if (target - a[i] >= 0) {
                temp.add(a[i]);
                combinationSum2(i + 1, a, temp, ans, target - a[i]);
                temp.remove(temp.size() - 1);
            }
        }
    }

    //video-8
    private static void combinationSum(int i, int[] a, ArrayList<Integer> temp, List<List<Integer>> ans, int target) {
        if (i == a.length) {
            if (target == 0) {
                ans.add(new ArrayList<>(temp));
            }
            return;
        }

        //take case
        if (target - a[i] >= 0) {
            temp.add(a[i]);
            combinationSum(i, a, temp, ans, target - a[i]);
            temp.remove(temp.size() - 1);
        }

        //not take
        combinationSum(i + 1, a, temp, ans, target);

    }

    //sum problem
    private static int summ(int i, int[] a, ArrayList<Integer> temp, int sum) {
        if (i == a.length) {
            if (sum == 2) return 1;
            return 0;
        }

        temp.add(a[i]);
        int l = summ(i + 1, a, temp, sum + a[i]);
        temp.remove(temp.size() - 1);

        int r = summ(i + 1, a, temp, sum);
        return l + r;
    }
}