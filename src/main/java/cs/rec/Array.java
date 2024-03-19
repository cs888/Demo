package cs.rec;

import java.util.*;

public class Array {
    public static void main(String[] args) {
        int a[] = {};
        long a1 = 3;
        long b = 3;
        System.out.println(a1 == b);
    }

    //AP-28
    public static int subarrayWithMaxProduct(int[] a) {
        int ans = Integer.MIN_VALUE, suffix = 1, prefix = 1;
        int n = a.length;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 0) prefix = 1;
            if (a[n - i - 1] == 0) suffix = 1;
            prefix *= a[i];
            suffix *= a[n - i - 1];
            ans = Math.max(ans, Math.max(prefix, suffix));
        }
        return ans;
    }

    //AP-27
    public static int team(int[] skill, int n) {
        return mergeSortCountInversionsPair(0, n - 1, skill);
    }

    static int mergeSortCountInversionsPair(int low, int high, int[] arr) {
        if (low >= high)
            return 0;
        int count = 0;
        int mid = low + high >> 1;
        count += mergeSortCountInversionsPair(low, mid, arr);
        count += mergeSortCountInversionsPair(mid + 1, high, arr);
        count += mergeAndCountInversionsPair(low, mid, high, arr);
        return count;
    }

    static int mergeAndCountInversionsPair(int low, int mid, int high, int[] arr) {
        int[] temp = new int[high - low + 1];
        int ind = 0, left = low, right = mid + 1, count = 0;
        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) {
                temp[ind++] = arr[left++];
            } else {
                temp[ind++] = arr[right++];
                // get count
                count = getPairCount(low, mid, high, arr);
            }
        }
        while (left <= mid)
            temp[ind++] = arr[left++];

        while (right <= high)
            temp[ind++] = arr[right++];

        for (int i = low, j = 0; i <= high; i++) {
            arr[i] = temp[j++];
        }
        return count;
    }

    static int getPairCount(int low, int mid, int high, int[] arr) {
        int count = 0;
        int right = mid + 1;
        for (int i = low; i <= mid; i++) {
            while (right <= high && arr[i] > 2 * arr[right]) {
                right++;
            }
            count += right - mid - 1;
        }
        return count;
    }

    //AP-26
    public static int numberOfInversions(int[] a, int n) {
        return mergeSortCountInversions(0, n - 1, a);
    }

    static int mergeSortCountInversions(int l, int r, int[] arr) {
        if (l >= r)
            return 0;
        int count = 0;
        int mid = l + r >> 1;
        count += mergeSortCountInversions(l, mid, arr);
        count += mergeSortCountInversions(mid + 1, r, arr);
        count += mergeAndCountInversions(l, mid, r, arr);
        return count;
    }

    static int mergeAndCountInversions(int l, int mid, int r, int[] arr) {
        int[] temp = new int[r - l + 1];
        int ind = 0, lStart = l, rStart = mid + 1, count = 0;
        while (lStart <= mid && rStart <= r) {
            if (arr[lStart] <= arr[rStart]) {
                temp[ind++] = arr[lStart++];
            } else {
                count += mid - lStart + 1;
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
        return count;
    }


    //mergeSort Algorithm
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

    //AP-25
    public static int[] findMissingRepeatingNumbers(int[] a) {
        int n = a.length, xr = 0;
        for (int i = 0; i < n; i++) {
            xr ^= a[i];
            xr ^= i + 1;
        }
        //get first nonZero bit of xr
        int bitNo = xr & ~(xr - 1);
        int xrZeroGroup = 0, xrOneGroup = 0;
        for (int i = 0; i < n; i++) {
            if ((a[i] & bitNo) != 0) xrOneGroup ^= a[i];
            else xrZeroGroup ^= a[i];
        }

        for (int i = 1; i <= n; i++) {
            if ((i & bitNo) != 0) xrOneGroup ^= i;
            else xrZeroGroup ^= i;
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] == xrOneGroup) count++;
        }
        if (count == 2) return new int[]{xrOneGroup, xrZeroGroup};
        return new int[]{xrZeroGroup, xrOneGroup};

    }

    public static int[] findMissingRepeatingNumbers2(int[] a) {
        long n = a.length;
        long s = Arrays.stream(a).sum();
        long sn = (n * (n + 1)) / 2;
        long x_minus_y = s - sn;
        long s2 = Arrays.stream(a).map(x -> x * x).sum();
        long s2n = (n * (n + 1) * (2 * n + 1)) / 6;
        long xsquare_mius_ysquare = s2 - s2n;
        long x_plus_y = xsquare_mius_ysquare / x_minus_y;
        long x = (int) ((x_plus_y + x_minus_y) / 2);
        long y = (int) x_plus_y - x;
        return new int[]{(int) x, (int) y};
    }

    //AP-24
    // another way
    public static void mergeTwoSortedArraysWithoutExtraSpace1(long[] a, long[] b) {

        int i = a.length - 1, j = 0;
        while (i >= 0 && j < b.length) {
            if (a[i] > b[j]) {
                swap(a, b, i, j);
                j++;
            }
            i--;
        }
        Arrays.sort(a);
        Arrays.sort(b);
    }

    //AP-24
    public static void mergeTwoSortedArraysWithoutExtraSpace(long[] a, long[] b) {
        int n = a.length;
        int m = b.length;
        int len = n + m;
        int gap = (int) Math.ceil(len / 2);
        while (gap > 0) {
            int i = 0, j = i + gap;
            while (j < n + m) {
                if (j < n) {
                    // both lies in first array
                    if (a[i] > a[j]) {
                        swap(a, a, i, j);
                    }
                } else if (i >= n) {
                    // both lies in second array
                    if (b[i - n] > b[j - n]) {
                        swap(b, b, i - n, j - n);
                    }
                } else {
                    if (a[i] > b[j - n]) {
                        swap(a, b, i, j - n);
                    }
                }
                i++;
                j++;
            }
            if (gap == 1) break;
            gap = gap / 2 + gap % 2;
        }
    }

    private static void swap(long[] a, long[] b, int i, int j) {
        long temp = a[i];
        a[i] = b[j];
        b[j] = temp;
    }

    //AP-23
    //another way also there usig prev
    public static List<List<Integer>> mergeOverlappingIntervals(int[][] a) {
        Arrays.sort(a, Comparator.comparingInt(x -> x[0]));
        List<List<Integer>> ans = new ArrayList<>();

        for (int i = 0; i < a.length; i++) {
            if (ans.isEmpty() || ans.get(ans.size() - 1).get(1) < a[i][0]) {
                ans.add(new ArrayList<>(List.of(a[i][0], a[i][1])));
            } else {
                ans.get(ans.size() - 1).set(1, Math.max(ans.get(ans.size() - 1).get(1), a[i][1]));
            }
        }
        return ans;
    }

    //AP -22
    public static int subarraysWithSumK(int[] arr, int k) {
        //previousSum,Count
        Map<Integer, Integer> map = new HashMap<>();
        int zorTillNow = 0, count = 0;
        map.put(0, 1); // Setting for 0 in the map.
        for (int i = 0; i < arr.length; i++) {
            // add current element to prefix Sum:
            zorTillNow ^= arr[i];

            // Add the number of previous subarrays
            count += map.getOrDefault(zorTillNow ^ k, 0);

            // Update the count of prefix sum in the map.
            map.put(zorTillNow, map.getOrDefault(zorTillNow, 0) + 1);
        }
        return count;
    }

    //AP-21
    static public List<List<Integer>> fourSum(int[] a, int target) {
        Arrays.sort(a);
        int n = a.length;
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i > 0 && a[i] == a[i - 1])
                continue;
            for (int j = i + 1; j < n; j++) {
                if (j > i + 1 && a[j - 1] == a[j])
                    continue;
                int k = j + 1, l = n - 1;
                while (k < l) {
                    int sum = a[i] + a[j] + a[k] + a[l];
                    if (sum < target)
                        k++;
                    else if (sum > target)
                        l--;
                    else {
                        ans.add(List.of(a[i], a[j], a[k], a[l]));
                        k++;
                        l--;
                        while (k < l && a[k] == a[k - 1])
                            k++;
                        while (k < l && a[l] == a[l + 1])
                            l--;
                    }
                }
            }
        }
        return ans;
    }

    //AP-20
    public List<List<Integer>> threeSum(int n, int[] a) {
        Arrays.sort(a);
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i > 0 && a[i] == a[i - 1])
                continue;
            int j = i + 1, k = n - 1;
            while (j < k) {
                int sum = a[i] + a[j] + a[k];
                if (sum < 0)
                    j++;
                else if (sum > 0)
                    k--;
                else {
                    ans.add(List.of(a[i], a[j], a[k]));
                    j++;
                    k--;
                    while (j < k && a[j] == a[j - 1])
                        j++;
                    while (j < k && a[k] == a[k + 1])
                        k--;
                }
            }
        }
        return ans;
    }


    //AP-19
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

    //AP-19
    public static List<List<Integer>> triplet(int n, int[] a) {
        Arrays.sort(a);
        int start = 0, mid = start + 1, end = n - 1;
        List<List<Integer>> ans = new ArrayList<>();
        while (start < n - 2) {
            while (mid < end) {
                if (start != mid && a[mid - 1] == a[mid]) continue;
                else if (a[start] + a[mid] + a[end] == 0) {
                    ans.add(List.of(a[start], a[mid], a[end]));
                    mid++;
                } else if (a[mid] + a[end] < 0) mid++;
                else end--;
            }
            start++;
        }
        return ans;
    }

    //AP-18
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

    //  for only postive & zeros in array
    public static int longestSubarrayWithSumK(int[] a, long k) {

        long tempSum = 0;
        int len = 0, r = 0, l = 0;
        while (r < a.length) {
            if (tempSum > k) {
                tempSum -= a[l++];
                continue;
            } else if (tempSum == k) {
                // had taken till last sum so it will r-l not r-l+1
                len = Math.max(len, r - l);
            }
            tempSum += a[r++];
        }
        // for last Element
        if (tempSum == k)
            len = Math.max(len, r - l);
        return len;
    }

    //for positives , negatives , also zero from o to Integer.MaxValue
    //AP-4
    public static int getLongestSubarray1(int[] a, int k) {
        int N = a.length;
        long tempSum = 0;
        //sum,tillIndex
        Map<Long, Integer> map = new HashMap<>();
        int ans = 0;
        for (int i = 0; i < N; i++) {
            tempSum += a[i];
            if (tempSum == k) {
                ans = Math.max(ans, i + 1);
            } else if (map.containsKey(tempSum - k)) {
                ans = Math.max(ans, i - map.get(tempSum - k));
            }
            //for 0 case considerations , map.putIfAbsent for take longer len
            map.putIfAbsent(tempSum, i);
        }
        return ans;
    }

    //AP-3
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
        int i = 0, j = 0;
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

    //AP-2
    //union of two sorted Arrays
    public static List<Integer> sortedArray(int[] a, int[] b) {
        List<Integer> ans = new ArrayList<>();
        int i = 0, j = 0;
        int last = Integer.MAX_VALUE;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                if (last != a[i]) {
                    ans.add(a[i]);
                    last = a[i];
                }
                i++;
            } else {
                if (last != b[j]) {
                    ans.add(b[j]);
                    last = b[j];
                }
                j++;
            }
        }

        while (i < a.length) {
            if (last != a[i]) {
                ans.add(a[i]);
                last = a[i];
            }
            i++;
        }

        while (j < b.length) {
            if (last != b[j]) {
                ans.add(b[j]);
                last = b[j];
            }
            j++;
        }
        return ans;
    }

    public static int[] moveZeros(int n, int[] a) {
        int zeroIndex = 0;
        int itr = 0;
        while (itr < a.length) {
            if (a[itr] != 0) {
                swap(zeroIndex++, itr++, a);
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
