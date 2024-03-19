package cs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class BinarySearch {

    public static void main(String[] args) {
        int a[] = {1, 2, 3};
        lowerBound(a, 2);
    }

    //BS-27
    public static int findMedian(int a[][], int m, int n) {
        int low = Integer.MAX_VALUE, high = Integer.MIN_VALUE;
        for (int i = 0; i < m; i++) {
            low = Math.min(low, a[i][0]);
            high = Math.max(high, a[i][n - 1]);
        }

        int reqCount = (n * m) / 2;
        while (low <= high) {
            int mid = low + high >> 1;
            int count = getCountOfItemLessthanEqualMid(mid, a);
            if (count <= reqCount) low = mid + 1;
            else high = mid - 1;
        }
        return low;
    }

    private static int getCountOfItemLessthanEqualMid(int mid, int[][] a) {
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            count += upperBound(a[i], mid);
        }
        return count;
    }

    //BS-26
    public static int[] findPeakGrid(int[][] g) {
        int low = 0, n = g.length, m = g[0].length, high = m - 1;
        while (low <= high) {
            int mid = low + high >> 1;
            int maxRowIndex = getMaxInCol(mid, g, n);
            int left = (mid - 1 >= 0) ? g[maxRowIndex][mid - 1] : -1;
            int right = (mid + 1) < m ? g[maxRowIndex][mid + 1] : -1;
            if (left < g[maxRowIndex][mid] && g[maxRowIndex][mid] > right)
                return new int[]{maxRowIndex, mid};
            else if (left > g[maxRowIndex][mid])
                high = mid - 1;
            else
                low = mid + 1;
        }

        return new int[]{-1, -1};
    }

    static int getMaxInCol(int mid, int[][] g, int totalRow) {
        int val = -1, ind = -1;
        for (int i = 0; i < totalRow; i++) {
            int temp = g[i][mid];
            if (temp > val) {
                val = temp;
                ind = i;
            }
        }
        return ind;
    }

    //BS-25 , OR can be solved same as BS-24
    public static boolean findInMatrix(int target, ArrayList<ArrayList<Integer>> a) {
        int n = a.size(), m = a.get(0).size();
        int row = 0, col = m - 1;
        while (row < n && col >= 0) {
            int val = a.get(row).get(col);
            if (val == target) return true;
            if (target > val) row++;
            else col--;
        }

        return false;
    }

    //BS-24
    static boolean searchMatrix(ArrayList<ArrayList<Integer>> a, int target) {

        int n = a.size(), m = a.get(0).size();
        int l = 0, r = n * m - 1;
        while (l <= r) {
            int mid = l + r >> 1;
            int row = mid / m;
            int col = mid % m;
            int val = a.get(row).get(col);
            if (val == target) return true;
            if (target > val) l = mid + 1;
            else r = mid - 1;
        }

        return false;
    }

    //BS-23
    public static int rowMaxOnes(ArrayList<ArrayList<Integer>> a, int n, int m) {

        int ithindex = 0, indexVal = 0;
        for (int index = 0; index < n; index++) {
            int ind = fOccurance(1, a.get(index));
            int nOOf1s = m - ind + 1;
            if (nOOf1s > indexVal) {
                indexVal = nOOf1s;
                ithindex = index;
            }
        }
        return ithindex;
    }

    static int fOccurance(int val, ArrayList<Integer> a) {
        int l = 0, r = a.size();
        while (l < r) {
            int mid = l + r >> 1;
            if (val > a.get(mid)) l = mid + 1;
            else r = mid - 1;
        }
        return r;
    }

    //BS-22
    public static int kthElement(ArrayList<Integer> a, ArrayList<Integer> b, int n1, int n2, int k) {

        if (n1 > n2)
            return kthElement(b, a, n2, n1, k);
        int symmetryNumberOfElementInLeft = k;
        int low = Math.max(0, k - n2), high = Math.min(n1, k);
        while (low <= high) {
            int mid1 = low + high >> 1;
            int mid2 = symmetryNumberOfElementInLeft - mid1;
            int l1 = Integer.MIN_VALUE;
            int l2 = Integer.MIN_VALUE;
            int r1 = Integer.MAX_VALUE;
            int r2 = Integer.MAX_VALUE;

            if (mid1 - 1 >= 0) l1 = a.get(mid1 - 1);
            if (mid2 - 1 >= 0) l2 = b.get(mid2 - 1);
            if (mid1 < n1) r1 = a.get(mid1);
            if (mid2 < n2) r2 = b.get(mid2);

            if (l1 <= r2 && l2 <= r1) {
                return Math.max(l1, l2);
            } else if (l1 > r2)
                high = mid1 - 1;

            else
                low = mid1 + 1;

        }
        return 0;
    }

    //BS-21
    public double findMedianSortedArrays(int[] a, int[] b) {
        int n1 = a.length;
        int n2 = b.length;
        int totLen = n1 + n2;
        if (n1 > n2)
            return findMedianSortedArrays(b, a);
        int left = (n1 + n2 + 1) / 2;
        int low = 0, high = n1;
        while (low <= high) {
            int mid1 = low + high >> 1;
            int mid2 = left - mid1;
            int l1 = Integer.MIN_VALUE;
            int l2 = Integer.MIN_VALUE;
            int r1 = Integer.MAX_VALUE;
            int r2 = Integer.MAX_VALUE;

            if (mid1 - 1 >= 0) l1 = a[mid1 - 1];
            if (mid2 - 1 >= 0) l2 = b[mid2 - 1];
            if (mid1 < n1) r1 = a[mid1];
            if (mid2 < n2) r2 = b[mid2];

            if (l1 <= r2 && l2 <= r1) {
                if (totLen % 2 != 0) return Math.max(l1, l2);
                else return (Math.max(l1, l2) + Math.min(r1, r2)) / 2.0;
            } else if (l1 > r2) high = mid1 - 1;

            else low = mid1 + 1;

        }
        return 0;
    }

    //BS-21
    public double findMedianSortedArraysBetter(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        int i = 0, j = 0, ans = 0;
        int m2 = len / 2, m1 = m2 - 1;
        double m1val = 0, m2val = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                if (ans == m1)
                    m1val = nums1[i];
                else if (ans == m2) {
                    m2val = nums1[i];
                }
                ans++;
                i++;
            } else {
                if (ans == m1)
                    m1val = nums2[j];
                else if (ans == m2) {
                    m2val = nums2[j];
                }
                ans++;
                j++;
            }
        }

        // nums1 exhausted
        while (j < nums2.length) {
            if (ans == m1)
                m1val = nums2[j];
            else if (ans == m2)
                m2val = nums2[j];
            j++;
            ans++;
        }

        // nums2 exahaused
        while (i < nums1.length) {
            if (ans == m1)
                m1val = nums1[i];
            else if (ans == m2)
                m2val = nums1[i];
            ans++;
            i++;
        }

        // odd
        if (len % 2 != 0) return m2val;

            // return even
        else return (m1val + m2val) / 2.0;
    }

    //BS-20
    public static double MinimiseMaxDistance(int[] arr, int k) {
        int n = arr.length; // size of the array
        double low = 0;
        //Find the maximum distance:
        double high = Arrays.stream(arr).max().getAsInt();

        //Apply Binary search:
        double diff = 1e-6;
        while (high - low > diff) {
            double mid = (low + high) / (2.0);
            int cnt = numberOfGasStationsRequired(mid, arr);
            if (cnt > k) low = mid;
            else high = mid;
        }
        return high;
    }

    public static int numberOfGasStationsRequired(double mid, int[] arr) {
        int n = arr.length; // size of the array
        int cnt = 0;
        for (int i = 1; i < n; i++) {
            int len = arr[i] - arr[i - 1];
            int numberInBetween = (int) (len / mid);
            if (len == (mid * numberInBetween)) {
                numberInBetween--;
            }
            cnt += numberInBetween;
        }
        return cnt;
    }

    //TODO issue in running
    public static double MinimiseMaxDistanceBetter(int[] a, int k) {
        int howMany[] = new int[a.length];
        int maxIndex = 0;
        double sectionLength = 0, maxVal = 0;
        Queue<int[]> queue = new PriorityQueue<>((a1, b) -> b[0] - a1[0]);
        for (int i = 0; i < a.length - 1; i++) {
            queue.add(new int[]{a[i + 1] - a[i], i});
        }

        for (int gasStation = 0; gasStation <= k; gasStation++) {
            int[] diff = queue.poll();
            howMany[diff[1]] += 1;
            sectionLength = diff[0] / (double) (howMany[diff[1]] + 1);
            queue.add(new int[]{(int) sectionLength, diff[1]});
        }

        double ans = Integer.MIN_VALUE;
        for (int i = 0; i < a.length - 1; i++) {
            int diff = a[i + 1] - a[i];
            sectionLength = diff / (howMany[i] + 1);
            ans = Math.max(ans, sectionLength);
        }
        return ans;
    }


    //max section length
    //TODO:iSSUE IN running
    public static double MinimiseMaxDistanceBrute(int[] a, int k) {
        int howMany[] = new int[a.length - 1];
        int maxIndex = 0;
        int sectionLength = 0, diff = 0, maxVal = 0;
        for (int gasStation = 0; gasStation <= k; gasStation++) {
            for (int i = 0; i < a.length - 1; i++) {
                diff = a[i + 1] - a[i];
                sectionLength = diff / (howMany[i] + 1);
                if (maxVal < sectionLength) {
                    maxVal = sectionLength;
                    maxIndex = i;
                }
            }
            howMany[maxIndex] += 1;
        }

        double ans = Integer.MIN_VALUE;
        for (int i = 0; i < a.length - 1; i++) {
            diff = a[i + 1] - a[i];
            sectionLength = diff / (howMany[i] + 1);
            ans = Math.max(ans, sectionLength);
        }
        return ans;
    }

    //BS- 19
    //or it is same as 18
    // or same as painter partion problem https://www.codingninjas.com/studio/problems/painter-s-partition-problem_1089557
    // or split array lartest sum https://www.codingninjas.com/studio/problems/largest-subarray-sum-minimized_7461751

    //BS-18
    //min is max or min(max) or l
    public static int findPages(ArrayList<Integer> arr, int n, int m) {
        if (arr.size() < m) return -1;
        int[] a = arr.stream().mapToInt(x -> x).toArray();
        int l = Arrays.stream(a).max().getAsInt();
        int r = Arrays.stream(a).sum();

        while (l <= r) {
            int mid = l + r >> 1;
            int count = getStudentCount(a, mid);
            if (count > m) l = mid + 1;
            else r = mid - 1;
        }
        return l;
    }

    private static int getStudentCount(int[] a, int maxPage) {
        int count = 1, tillLast = 0;
        for (int book = 0; book < a.length; book++) {
            if (a[book] + tillLast <= maxPage) {
                tillLast += a[book];
            } else {
                count++;
                tillLast = a[book];
            }
        }
        return count;
    }

    //BS-17
    //max is min or max(min) or r
    public static int aggressiveCows(int[] stalls, int k) {
        Arrays.sort(stalls);
        int maxDist = -1;
        for (int i = 0; i < stalls.length; i++) {
            maxDist = Math.max(maxDist, stalls[i]);
        }
        int l = 1, r = maxDist;
        while (l <= r) {
            int mid = l + r >> 1;
            if (canPlaceWithMid(mid, stalls, k)) l = mid + 1;
            else r = mid - 1;
        }
        return r;
    }

    private static boolean canPlaceWithMid(int dist, int[] stalls, int k) {
        int last = stalls[0];
        int count = 1;
        for (int i = 1; i < stalls.length; i++) {
            if (stalls[i] - last >= dist) {
                count++;
                last = stalls[i];
                if (count >= k) return true;
            }

        }
        return false;
    }

    //BS-16
    // ans is a[r]+more;
    //more is k minus missing or k-missing or k-a[i]-i-1
    // or a[r]+k-(a[r]-r-1)
    // or r+1+k or l+k
    private static int kth(int[] a, int k) {
        if (a[0] > k) return k;
        int n = a.length;
        int l = 0, r = n - 1;
        while (l < r) {
            int mid = l + r >> 1;
            int totalMissing = a[mid] - (mid + 1);
            if (totalMissing > k) r = mid - 1;
            else l = mid + 1;
        }
        //or l+k
        return r + 1 + k;
    }

    //BS-15
    public static int leastWeightCapacity(int[] weights, int d) {
        int l = 1, r = Arrays.stream(weights).sum();
        while (l <= r) {
            int mid = l + r >> 1;
            if (countDays(mid, weights) > d) r = mid - 1;
            else l = mid + 1;
        }
        return l;
    }

    private static int countDays(int cap, int[] weights) {
        int wtSum = 0, days = 0;
        for (int i = 0; i < weights.length; i++) {
            if (wtSum + weights[i] == cap) {
                days++;
                wtSum = 0;
            } else if (wtSum + weights[i] > cap) {
                days++;
                wtSum = weights[i];
            } else wtSum += weights[i];
        }
        return days;
    }

    //BS- 14
    public static int smallestDivisor(int arr[], int limit) {
        int l = 1, r = Arrays.stream(arr).max().getAsInt();
        while (l <= r) {
            int mid = l + r >> 1;
            if (countThreshold(mid, arr, limit) > limit) l = mid + 1;
            else r = mid - 1;
        }
        return l;
    }

    private static int countThreshold(double mid, int[] arr, int limit) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            count += Math.ceil(arr[i] / mid);
        }
        return count;
    }

    private static void swap(int pos, int neg, int[] a) {
        int temp = a[pos];
        a[pos] = a[neg];
        a[neg] = temp;
    }

    //BS-13
    public static int roseGarden(int[] arr, int roses, int bucket) {
        int l = Arrays.stream(arr).min().getAsInt(), r = Arrays.stream(arr).max().getAsInt();
        while (l <= r) {
            int mid = l + r >> 1;
            if (makePossible(mid, arr, roses, bucket)) r = mid - 1;
            else l = mid + 1;
        }
        return l;
    }

    private static boolean makePossible(int mid, int[] a, int roses, int bucket) {
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] >= mid) count++;
            else bucket -= count / roses;
        }
        return bucket <= 0 ? true : false;
    }

    //BS-12
    public static int minimumRateToEatBananas(int[] v, int h) {
        int l = 1, r = Arrays.stream(v).max().getAsInt();
        while (l <= r) {
            int mid = l + r >> 1;
            int hoursTake = noOfhoursTake(mid, v);
            if (hoursTake > h) l = mid + 1;
            else r = mid - 1;
        }

        return l;
    }

    private static int noOfhoursTake(double mid, int[] v) {
        int count = 0;
        for (int i = 0; i < v.length; i++) {
            count += Math.ceil(v[i] / mid);
        }
        return count;
    }

    //BS - 11
    //TODO:manual Pow
    public static int NthRoot(int n, int m) {
        int l = 1, r = m;
        while (l <= r) {
            int mid = l + r >> 1;
            int pow_val = (int) Math.pow(mid, n);
            if (pow_val == m) return mid;
            if (m < pow_val) r = mid - 1;
            else l = mid + 1;
        }
        return -1;
    }

    //BS-10
    public static int sqrtN(long N) {

        long lo = 1, hi = N;
        while (lo <= hi) {
            long mid = lo + hi >> 1;
            if (mid > (N / mid)) hi = mid - 1;
            else lo = mid + 1;
        }
        return (int) hi;
    }

    //BS-9
    //use concept of increasing slope
    // will work both for single & multiple peak
    public static int findPeakElement(ArrayList<Integer> a) {
        int len = a.size();
        if(len==1) return 0;
        if (a.get(0) > a.get(1)) return 0;
        if (a.get(len - 2) < a.get(len - 1)) return len - 1;
        int lo = 1, hi = len - 2;
        while (lo <= hi) {
            int mid = lo + hi >> 1;
            //if peak element
            if (a.get(mid - 1) < a.get(mid) && a.get(mid) > a.get(mid + 1)) return mid;
                //if it in increasing slope
                // a[mid-1]<a[mid] or/and a[mid]<a[mid+1] will also works
            else if (a.get(mid - 1) < a.get(mid)) {
                lo = mid + 1;
            } else hi = mid - 1;
        }
        return -1;
    }

    //BS-8
    // (even,odd) -> element is on right half , eliminate left half
    //(odd,even) ->element is on left half , eliminate right half
    public static int singleNonDuplicate(ArrayList<Integer> arr) {
        int[] a = arr.stream().mapToInt(x -> x).toArray();
        int len = a.length;
        if (len == 1) return a[0];
        int l = 1;
        int r = len - 2;
        if (a[0] != a[1]) return a[0];
        if (a[len - 1] != a[len - 2]) return a[len - 1];
        while (l <= r) {
            int mid = l + r >> 1;
            if (a[mid - 1] != a[mid] && a[mid] != a[mid + 1]) return a[mid];
                // i am on left side
            else if ((mid % 2 == 0 && a[mid] == a[mid + 1]) || (mid % 2 != 0 && a[mid] == a[mid - 1])) {
                l = mid + 1;
            } else r = mid - 1;
        }
        return -1;
    }

    //BS-7 Or,index of minimum Element in array
    public static int findKRotation(int[] a) {
        int lo = 0, hi = a.length - 1;
        int min = Integer.MAX_VALUE, minIndex = 0;
        while (lo <= hi) {
            int mid = lo + hi >> 1;
            // lef is sorted
            if (a[lo] <= a[mid]) {
                if (min > a[lo]) {
                    minIndex = lo;
                    min = a[lo];
                }
                lo = mid + 1;
            }
            // right is sorted
            else {
                if (min > a[mid]) {
                    minIndex = mid;
                    min = a[mid];
                }
                hi = mid - 1;
            }
        }
        return minIndex;
    }

    //BS-6
    public static int findMin(int[] a) {

        int l = 0, r = a.length - 1;
        int min = Integer.MAX_VALUE;
        while (l <= r) {
            int mid = l + r >> 1;
            //left is sorted
            if (a[l] <= a[mid]) {
                min = Math.min(min, a[l]);
                l = mid + 1;
            }
            //right is sorted
            else {
                min = Math.min(min, a[mid]);
                r = mid - 1;
            }
        }
        return min;
    }

    //BS-5
    public static boolean searchInARotatedSortedArrayII(int[] A, int key) {
        return search(A, key) == -1 ? false : true;
    }

    public static int search(int[] a, int target) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + hi >> 1;
            if (a[mid] == target)
                return mid;
            if (a[lo] == a[mid] && a[mid] == a[hi]) {
                lo++;
                hi--;
                continue;
            }
            // left is sorted
            else if (a[lo] <= a[mid]) {
                if (a[lo] <= target && target < a[mid])
                    hi = mid - 1;
                else
                    lo = mid + 1;
            }
            // right is sorted
            else {
                if (a[mid] < target && target <= a[hi])
                    lo = mid + 1;
                else
                    hi = mid - 1;
            }
        }
        return -1;
    }

    //BS-4
    public static int search(ArrayList<Integer> arr, int n, int k) {
        return search(arr, k);
    }

    public static int search(ArrayList<Integer> arr, int target) {
        int[] a = arr.stream().mapToInt(x -> x).toArray();
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + hi >> 1;
            if (a[mid] == target)
                return mid;
                // left is sorted
            else if (a[lo] <= a[mid]) {
                if (a[lo] <= target && target < a[mid])
                    hi = mid - 1;
                else
                    lo = mid + 1;
            }
            // right is sorted
            else {
                if (a[mid] < target && target <= a[hi])
                    lo = mid + 1;
                else
                    hi = mid - 1;
            }
        }
        return -1;
    }

    //BS-3-2
    public static int count(int arr[], int n, int k) {
        int first = first(arr, n, k);
        int last = last(arr, n, k);
        return last - first;
    }

    static int first(int[] arr, int n, int k) {
        int lo = 0, hi = n - 1;

        // same as lowerbound
        while (lo <= hi) {
            int mid = lo + hi >> 1;
            if (arr[mid] >= k)
                hi = mid - 1;
            else
                lo = mid + 1;
        }
        return lo;
    }

    // upper bound
    static int last(int[] arr, int n, int k) {
        int lo = 0, hi = n - 1;

        while (lo <= hi) {
            int mid = lo + hi >> 1;
            if (arr[mid] > k)
                hi = mid - 1;
            else
                lo = mid + 1;
        }
        return lo;
    }

    //BS-3
    public static int[] firstAndLastPosition(ArrayList<Integer> arr, int n, int k) {
        int first = first(arr, n, k);
        if (arr.get(first) != k || first == n)
            return new int[]{-1, -1};
        int last = last(arr, n, k);
        return new int[]{first, last - 1};
    }

    static int first(ArrayList<Integer> arr, int n, int k) {
        int lo = 0, hi = n - 1;

        // same as lowerbound
        while (lo <= hi) {
            int mid = lo + hi >> 1;
            if (arr.get(mid) >= k)
                hi = mid - 1;
            else
                lo = mid + 1;
        }
        return lo;
    }


    //upper bound
    static int last(ArrayList<Integer> arr, int n, int k) {
        int lo = 0, hi = n - 1;

        while (lo <= hi) {
            int mid = lo + hi >> 1;
            if (arr.get(mid) > k)
                hi = mid - 1;
            else
                lo = mid + 1;
        }
        return lo;
    }

    //BS-2
    // largest number such that a[i] <= target
    private static int floor(int[] a, int target) {

        int l = 0;
        int r = a.length - 1;
        int ans = -1;
        while (l <= r) {
            int mid = l + r >> 1;
            if (a[mid] <= target) {
                ans = a[mid];
                l = mid + 1;
            } else r = mid - 1;
        }
        return ans;
    }

    //same as lowerBound
    private static int ceil(int[] a, int target) {

        int l = 0;
        int r = a.length - 1;
        int ans = -1;
        while (l <= r) {
            int mid = l + r >> 1;
            if (a[mid] >= target) {
                ans = a[mid];
                r = mid - 1;
            } else l = mid + 1;
        }
        return ans;
    }

    // lower bound is defined as smallest index such that a[i]>target
    private static int upperBound(int[] a, int target) {

        int l = 0;
        int r = a.length - 1;
        int ans = a.length;
        while (l <= r) {
            int mid = l + r >> 1;
            if (a[mid] > target) {
                ans = mid;
                r = mid - 1;
            } else l = mid + 1;
        }
        return ans;
    }


    // lower bound is defined as smallest index or first index such that a[i]>=target
    //is same as ceil but ans=-1 as default
    private static int lowerBound(int[] a, int target) {

        int lo = 0;
        int hi = a.length - 1;
        //arr size by default
        int ans = a.length;
        while (lo <= hi) {
            int mid = lo + hi >> 1;
            if (a[mid] >= target) {
                ans = mid;
                hi = mid - 1;
            } else lo = mid + 1;
        }
        return ans;
    }
}
