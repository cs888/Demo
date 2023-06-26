package cs;

import java.util.*;

public class ArrayDemo {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};

        List<List<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        int n = 4;
        //1st optimization
        boolean[] rowFlag = new boolean[n + 1];

        //2nd optimization
        boolean[] cornerFlag = new boolean[2 * n];
        //3rd optimization
        boolean[] colFlag = new boolean[n * 2];
        int g[][] = new int[n][n];
        //  nQueen(0, g, rowFlag, cornerFlag, colFlag);
        System.out.println("abcd".substring(0,3));



        char[][] board = {{'5', '3', ' ', ' ', '7', ' ', ' ', ' ', ' '}, {'6', ' ', ' ', '1', '9', '5', ' ', ' ', ' '}, {' ', '9', '8', ' ', ' ', ' ', ' ', '6', ' '}, {'8', ' ', ' ', ' ', '6', ' ', ' ', ' ', '3'}, {'4', ' ', ' ', '8', ' ', '3', ' ', ' ', '1'}, {'7', ' ', ' ', ' ', '2', ' ', ' ', ' ', '6'}, {' ', '6', ' ', ' ', ' ', ' ', '2', '8', ' '}, {' ', ' ', ' ', '4', '1', '9', ' ', ' ', '5'}, {' ', ' ', ' ', ' ', '8', ' ', ' ', '7', '9'}};
        sudkoSolver(board);

        System.out.println(
                Arrays.deepToString(board)
        );

    }

    static int[] nextGreaterElement(int[] a, int n) {

        int ans[] = new int[n];
        Arrays.fill(ans, -1);
        ans[n - 1] = -1;

        // iterating over the array
        for (int i = n - 2; i >= 0; i--) {
            if (a[i + 1] > a[i]) {
                ans[i] = a[i + 1];
            } else if (ans[i + 1] > a[i]) {
                ans[i] = ans[i + 1];
            }
        }
        return ans;
    }


    private static boolean sudkoSolver(char[][] s) {

        for (int row = 0; row < s.length; row++) {
            for (int col = 0; col < s[0].length; col++) {
                if (s[row][col] == ' ') {
                    for (char number = '1'; number <= '9'; number++) {
                        if (canPlaceNAt(row, col, number, s)) {
                            s[row][col] = number;
                            if (sudkoSolver(s)) return true;
                            s[row][col] = ' ';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean canPlaceNAt(int row, int col, char c, char[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == c)
                return false;

            if (board[row][i] == c)
                return false;
        }
        int di = row / board.length;
        int dj = col / board[0].length;

        //TODO :need to work and understand this
       /* for (int i = 0; i <3 ; i++) {
            for (int j = 0; j <3 ; j++) {
                if(board[di+i][dj+i]==c) return false;
            }
        }*/

        //not intuitive
        for (int i = 0; i < board.length; i++) {
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c)
                return false;
        }

        return true;
    }

    private static void nQueen(int col, int[][] g, boolean[] rowFlag, boolean[] cornerFlag, boolean[] colFlag) {
        if (col >= g[0].length) {
            System.out.println(Arrays.deepToString(g));
            return;
        }

        for (int row = 0; row < g.length; row++) {
            if (canPlaceQueenAt(row, col, g, rowFlag, cornerFlag, colFlag)) {
                g[row][col] = 1;
                rowFlag[row] = true;
                cornerFlag[row + col] = true;
                colFlag[g.length - 1 + col - row] = true;
                //try placing in nextGreaterElement column
                nQueen(col + 1, g, rowFlag, cornerFlag, colFlag);
                cornerFlag[row + col] = false;
                colFlag[g.length - 1 + col - row] = false;
                rowFlag[row] = false;
                g[row][col] = 0;
            }
        }
    }

    private static boolean canPlaceQueenAt(int i, int j, int[][] g, boolean[] rowFlag, boolean[] cornerFlag, boolean[] colFlag) {
        //check for ith row if any cell is 1
       /* int col = j;
        while (col >= 0) {
            if (g[i][col--] == 1) return false;
        }*/
        if (colFlag[g.length - 1 + j - i]) return false;

        //first optimization
        //check for ith col if any cell is 1
        if (rowFlag[i]) return false;

        // if(cornerFlag[i+j]) return false;

        if (canAttackFromCorner(i, j, g)) return false;

        return true;
    }

    private static boolean canAttackFromCorner(int row, int col, int[][] g) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) continue;
                int deli = row + i;
                int delj = col + j;
                if (deli < 0 || delj < 0 || deli == g.length || delj == g[0].length) continue;
                if (g[deli][delj] == 1) return true;
            }
        }

        return false;
    }

    //time complexity : n!* n
    // n! to generate all permutation & n to iterate array elements
    private static void permutationUsingRecursion(int ind, int[] a, ArrayList<Integer> temp, boolean[] flag) {
        if (temp.size() == a.length) {
            System.out.println(temp);
            return;
        }
        for (int i = 0; i < a.length; i++) {
            if (!flag[i]) {
                temp.add(a[i]);
                flag[i] = true;
                permutationUsingRecursion(ind + 1, a, temp, flag);
                temp.remove(temp.size() - 1);
                flag[i] = false;
            }
        }
    }

    private static void permutationUsingSwap(int ind, int[] a) {
        if (ind >= a.length) {
            System.out.println(Arrays.toString(a));
            return;
        }
        for (int i = ind; i < a.length; i++) {
            swap(ind, i, a);
            permutationUsingSwap(ind + 1, a);
            swap(ind, i, a);
        }
    }

    private static void subSetSumII(int ind, ArrayList<Integer> temp, int[] a, List<List<Integer>> ans) {

        ans.add(new ArrayList<>(temp));
        for (int i = ind; i < a.length; i++) {
            //will act as base case
            if (i != ind && a[i - 1] == a[i]) continue;
            temp.add(a[i]);
            subSetSumII(i + 1, temp, a, ans);
            temp.remove(temp.size() - 1);
        }
    }

    private static void subSetSum(int i, ArrayList<Integer> temp, int[] a, List<List<Integer>> ans, int sum) {
        if (i == a.length) ans.add(new ArrayList<>(temp));
        if (i >= a.length) return;

        temp.add(a[i]);
        subSetSum(i + 1, temp, a, ans, sum + a[i]);
        temp.remove(temp.size() - 1);

        subSetSum(i + 1, temp, a, ans, sum);

    }

    private static void combinationSumII(int ind, int target, ArrayList<Integer> temp, int[] a, List<List<Integer>> ans) {

        if (target == 0) {
            ans.add(new ArrayList<>(temp));
            return;
        }

        if (ind >= a.length || target < a[ind]) {
            return;
        }

        for (int i = ind; i < a.length; i++) {
            if (target - a[i] >= 0 && (i == ind || a[i - 1] != a[i])) {
                temp.add(a[i]);
                combinationSumII(i + 1, target - a[i], temp, a, ans);
                temp.remove(temp.size() - 1);
            }
        }
    }

    private static void quickSort(int l, int r, int[] a) {
        if (l > r) return;
        int pIndex = partition(l, r, a);
        quickSort(l, pIndex - 1, a);
        quickSort(pIndex + 1, r, a);

    }

    private static int partition(int l, int r, int[] a) {
        int pivot = a[l];
        //{5, 2, 8, 1, 3}
        int ind = l;

        while (ind < r) {

            //find ind where element is greater than pivot
            while (a[ind] <= pivot && ind <= r) ind++;

            //find ind where element is less than pivot
            while (a[r] > pivot && r > l) r--;

            //if path not crossed then swap
            if (ind < r)
                swap(ind, r, a);

        }
        swap(l, r, a);
        return ind;
    }

    private static int partition1(int l, int r, int[] a) {
        int pivot = a[r];
        int i = l;
        for (int j = l; j < r; j++) {
            if (a[j] < pivot)
                swap(i++, j, a);
        }
        swap(r, l, a);

        return i;
    }

    static void mergeSort(int l, int r, int[] a) {
        if (l >= r) return;
        int mid = l + (r - l) / 2;
        mergeSort(l, mid, a);
        mergeSort(mid + 1, r, a);
        merge(l, mid, r, a);

    }

    private static void merge(int low, int mid, int high, int[] a) {
        int p1 = low;
        int p2 = mid + 1;
        int[] ans = new int[high + 1];
        int ind = 0;
        while (p1 <= mid && p2 <= high) {
            if (a[p1] <= a[p2]) {
                ans[ind++] = a[p1];
                p1++;
            } else {
                ans[ind++] = a[p2];
                p2++;
            }
        }

        while (p1 <= mid) {
            ans[ind++] = a[p1];
            p1++;
        }

        while (p2 <= high) {
            ans[ind++] = a[p2];
            p2++;
        }
        for (int i = low; i <= high; i++) {
            a[i] = ans[i - low];
        }
    }

    private static void swap(int p1, int p2, int[] a) {
        int temp = a[p2];
        a[p2] = a[p1];
        a[p1] = temp;
    }

    private static boolean combinationSumII(int i, ArrayList<Integer> temp, int[] a, int sum) {

        if (sum == 2) {
            System.out.println(temp);
            return true;
        }

        if (i >= a.length) {
            return false;
        }

        temp.add(a[i]);
        if (combinationSumII(i + 1, temp, a, sum + a[i])) return true;
        temp.remove(temp.size() - 1);

        if (combinationSumII(i + 1, temp, a, sum)) return true;

        return false;

    }
}