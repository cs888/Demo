package cs.rec;

import java.util.*;

public class Array {
    public static void main(String[] args) {
        int a[] = {};
        long a1 = 3;
        long b = 3;
        System.out.println(a1 == b);
    }

    //BS-19
    public static List<Integer> majorityElement(int[] a) {

        int count1 = 0, ele1 = 1;
        int count2 = 0, ele2 = 0;
        for (int i = 0; i < a.length; i++) {

            if (count1 == 0 && a[i] != ele2) {
                count1++;
                ele1 = a[i];
            } else if (count2 == 0 && a[i] != ele1) {
                count2++;
                ele2 = a[i];
            } else if (a[i] == ele1) {
                count1++;
            } else if (a[i] == ele2) {
                count2++;
            } else {
                count1--;
                count2--;
            }
        }
        count1 = 0;
        count2 = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == ele1) count1++;
            if (a[i] == ele2) count2++;
        }

        int l = (int) Math.floor(a.length / 3);
        List<Integer> ans = new ArrayList<>();
        if (count1 > l) ans.add(ele1);
        if (count2 > l) ans.add(ele2);
        return ans;

    }

    //BS-18
    public static List<List<Integer>> pascalTrianglePrint(int totalRow) {
        List<List<Integer>> ans = new ArrayList<>();
        for (int row = 1; row <= totalRow; row++) {
            ans.add(generateRow(row));
        }
        return ans;
    }

    public static List<Integer> generateRow(int NthRow) {
        int ans = 1;
        List<Integer> temp = new ArrayList<>();
        temp.add(1);
        for (int col = 1; col < NthRow; col++) {
            ans *= (NthRow - col);
            ans /= col;
            temp.add(ans);
        }
        return temp;
    }

    public static int pascalTriangleForSingleElement(int N, int r) {
        int ans = 1;
        //replicaation of n-1!^C,r-1!
        for (int i = 0; i < r; i++) {
            ans *= N - i;
            ans /= i + 1;
        }
        return ans;
    }

    //AP-17
    public static int findAllSubarraysWithGivenSum(int a[], int sum) {

        int prefixSum = 0, count = 0;
        // prefixSum,prefixSumCount
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int i = 0; i < a.length; i++) {
            prefixSum += a[i];
            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);

            count += map.getOrDefault(prefixSum - sum, 0);
        }
        return count;
    }

    // AP - 16
    public static int[] spiralMatrix(int[][] a) {
        int left = 0, right = a[0].length - 1, top = 0, bottom = a.length - 1, ind = 0;
        int[] ans = new int[a.length * a[0].length];
        while (left <= right && top <= bottom) {

            if (top <= bottom)
                for (int i = left; i <= right; i++) {
                    ans[ind++] = a[top][i];
                }

            top++;

            if (left <= right)
                for (int i = top; i <= bottom; i++) {
                    ans[ind++] = a[i][right];
                }

            right--;

            if (top <= bottom)
                for (int i = right; i >= left; i--) {
                    ans[ind++] = a[bottom][i];
                }

            bottom--;

            if (left <= right)
                for (int i = bottom; i >= top; i--) {
                    ans[ind++] = a[i][left];
                }

            left++;
        }
        return ans;

    }

    //AP-15
    public static void rotateMatrix(int[][] a) {

        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a[0].length; j++) {
                if (i == j)
                    continue;
                int temp = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = temp;
            }
        }

        for (int i = 0; i < a.length; i++) {
            int l = 0, r = a.length - 1;
            while (l < r) {
                int temp = a[i][l];
                a[i][l] = a[i][r];
                a[i][r] = temp;
                l++;
                r--;
            }
        }
    }

    //AP-14
    public static ArrayList<ArrayList<Integer>> zeroMatrix(ArrayList<ArrayList<Integer>> matrix, Integer n, Integer m) {

        int row[] = new int[n];
        int col[] = new int[m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix.get(i).get(j) == 0) {
                    row[i] = 1;
                    col[j] = 1;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (row[i] == 1 || col[j] == 1) {
                    matrix.get(i).set(j, 0);
                }
            }
        }
        return matrix;
    }

    //AP- 12
    public static List<Integer> superiorElements(int[] a) {
        List<Integer> ans = new ArrayList<>();
        int last = Integer.MIN_VALUE;
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] > last) {
                ans.add(a[i]);
                last = a[i];
            }
        }
        return ans;
    }

    // AP -11
    private static void nextPermutation(int[] a) {
        int n = a.length;
        int ind = -1;
        //find first smaller
        for (int i = n - 2; i >= 0; i--) {
            if (a[i] < a[i + 1]) {
                ind = i;
                break;
            }
        }
        //return sorted array if array already sorted
        if (ind == -1) {
            Arrays.sort(a);
            return;
        }

        //find smallest element greater than element at index {{ind}} & swap with ind
        // i.e  this element would be first greater element from array last
        for (int i = n - 1; i >= 0; i--) {
            if (a[ind] < a[i]) {
                swap(ind, i, a);
                break;
            }
        }

        //reverse element from index+1 to n-1
        int l = ind + 1, r = n - 1;
        while (l < r) {
            swap(l, r, a);
            l++;
            r--;
        }

    }

    //AP-10 part 1
    public static int[] alternateNumbers(int[] a) {
        int pos = 0;
        int neg = 1;
        int n = a.length;
        while (pos < n || neg < n) {
            if (a[pos] < 0) {
                swap(pos, neg, a);
            }
            pos++;
            if (a[neg] > 0) {
                swap(pos, neg, a);
                neg += 2;
            }
        }
        return a;
    }


    //AP -8
    public static long maxSubarraySum(int[] a, int n) {
        //start & end will give array total length or start index & end index
        int ans = 0, tempsum = 0, start = -1, end = -1;
        for (int i = 0; i < a.length; i++) {
            if (tempsum == 0) start = i;
            tempsum += a[i];
            if (tempsum > ans) {
                ans = tempsum;
                end = i;
            }
            if (tempsum < 0) tempsum = 0;
        }
        return ans;
    }

    //TODO:
    //  for only postive & negatives
    public static int getLongestSubarray(int[] a, int k) {

        int l = 0, r = 0, temp = 0, ans = 0;
        while (r < a.length) {
            if (temp + a[r] < k) temp -= a[l];
            else {
                temp += a[r];
                if (temp == k) ans = Math.max(ans, r - l + 1);
                r++;
            }
        }
        return ans;
    }

    //for positives , negatives , also zero from o to Integer.MaxValue
    //AP-4
    public static int getLongestSubarray1(int[] a, int k) {
        int N = a.length;
        long tempSum = 0;
        //sum,Index
        Map<Long, Integer> map = new HashMap<>();
        int ans = 0;
        for (int i = 0; i < N; i++) {
            tempSum += a[i];
            if (tempSum == k) {
                ans = Math.max(ans, i + 1);
            } else if (map.containsKey(tempSum - k)) {
                ans = Math.max(ans, i - map.get(tempSum - k));
            }
            map.putIfAbsent(tempSum, i);
        }
        return ans;
    }

    //AP-10
    public static int missingNumber(int[] a, int N) {
        int xor1 = 0;
        for (int i = 0; i < N - 1; i++) {
            xor1 ^= a[i] ^ (i + 1);
        }
        xor1 ^= N;

        return xor1;
    }

    //AP- 2
    //array Intersection
    public static ArrayList<Integer> findArrayIntersection(ArrayList<Integer> a1, int n, ArrayList<Integer> a2, int m) {
        ArrayList<Integer> ans = new ArrayList<Integer>();
        int i = 0;
        int j = 0;
        while (i < n && j < m) {
            if (a1.get(i) < a2.get(j)) {
                i++;
            } else if (a1.get(i) > a2.get(j)) {
                j++;
            } else {
                ans.add(a1.get(i));
                i++;
                j++;
            }

        }
        return ans;
    }


    public static List<Integer> sortedArray(int[] a, int[] b) {
        List<Integer> ans = new ArrayList<>();
        int i = 0, j = 0;
        int last = Integer.MAX_VALUE;
        while (i < a.length && j < b.length) {
            // if (last==a[i] && last==b[j]){i++;j++;}
            if (a[i] < b[j]) {
                if (last != a[i]) ans.add(a[i]);
                last = a[i];
                i++;
            } else {
                if (last != b[j]) ans.add(b[j]);
                last = b[j];
                j++;
            }
        }

        if (i < a.length) {
            while (i < a.length) {
                if (last != a[i]) {
                    ans.add(a[i]);
                    last = a[i];
                }
                i++;
            }
        }
        if (j < b.length) {
            while (j < b.length) {
                if (last != b[j]) {
                    ans.add(b[j]);
                    last = b[j];
                }
                j++;
            }
        }
        return ans;
    }

    public static int[] moveZeros(int n, int[] a) {
        int zero = 0;
        int itr = 0;
        while (itr < a.length) {
            if (a[itr] != 0) {
                swap(zero++, itr++, a);
            } else itr++;
        }
        return a;
    }

    public static void swap(int p1, int p2, int[] a) {
        int temp = a[p2];
        a[p2] = a[p1];
        a[p1] = temp;
    }
}
