package cs;

import java.util.*;

public class ArrayDemo {
    public static void main(String[] args) {
        String s = "1 2 3 4";

        System.out.println();

    }

    //ToDo
    public static void mergeTwoSortedArraysWithoutExtraSpace(long[] a1, long[] a2) {

        int n = a1.length + a2.length;
        int gap = (int) Math.ceil(n / 2);
        int start = 0;
        boolean flag = false;
        while (gap > 1 || flag) {
            while (gap < n) {
                if (start < a1.length && gap < a1.length && a1[start] > a1[gap]) {
                    swap(start, gap, a1);
                } else if (start >= a1.length && gap < a2.length && a2[start] > a2[gap]) {
                    swap(start, gap, a2);
                } else if (start < a1.length && gap < a2.length && a1[start] > a2[gap]) {
                    long temp = a2[gap];
                    a2[gap] = a1[start];
                    a1[start] = temp;
                }
                start++;
                gap++;
            }
            gap = (int) Math.ceil(gap / 2);
            if (gap == 1 && flag == false) flag = true;
            else flag = false;
        }
    }

    //TODO: ISSUE WITH CODE
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j < n; j++) {
                if (j != i + 1 && nums[j] == nums[j - 1]) continue;
                int k = j + 1, l = n - 1;
                while (k < n && l < n && k < l) {
                    int ij = target - (nums[i] + nums[j]);
                    int sum = nums[k] + nums[l];
                    if (ij == sum) {
                        ans.add(List.of(nums[i], nums[j], nums[k], nums[l]));
                        k++;
                    } else if (sum > target) {
                        l--;
                    } else k++;
                }
            }
        }
        return ans;
    }

    private static void reArrange(int[] a) {
        int ans[] = new int[a.length];
        int even_index = 0, odd_index = 1;
        int ind = 0;
        while (ind < a.length) {
            if (a[ind] > 0) {
                ans[even_index] = a[ind++];
                even_index += 2;
            } else {
                ans[odd_index] = a[ind++];
                odd_index += 2;
            }
        }
        System.out.println(Arrays.toString(ans));
    }

    public static int longestSubarrayWithSumK(int[] a, long sum) {
        long tempSum = 0;
        int len = 0;
        int l = 0, r = 0;
        while (l < a.length && r < a.length) {
            if (tempSum + a[r] < sum) {
                tempSum += a[r];
                r++;
            } else if (tempSum + a[r] == sum) {
                len = Math.max(len, r - l + 1);
                tempSum += a[r];
                r++;
            }
            //tempsum>sum
            else {
                tempSum -= a[l];
                l++;
            }
        }
        return len;
    }


    //1 2 3 1 1 1 1
    //AP-4
    // can we get requiredSum from earlier visted sum by doing currentSum-requiredSum is in map
    public static int longestSubarrayWithSumK1(int[] a, long requiredSum) {

        //curSum,index
        Map<Long, Integer> map = new HashMap<>();
        long curSum = 0;
        int len = 0;
        for (int i = 0; i < a.length; i++) {
            curSum += a[i];
            if (curSum == requiredSum) {
                len = Math.max(len, i + 1);
            } else if (curSum > requiredSum && map.containsKey(curSum - requiredSum)) {
                len = Math.max(len, i - map.get(curSum - requiredSum));
            }
            map.putIfAbsent(curSum, i);
        }
        return len;
    }

    //BS-8
    public static int singleNonDuplicate(ArrayList<Integer> arr) {
        int a[] = arr.stream().mapToInt(x -> x).toArray();
        int n = a.length;
        if (n == 1) return a[0];
        if (a[0] != a[1]) return a[0];
        if (a[n - 1] != a[n - 2]) return a[n - 1];

        int l = 0;
        int r = n - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (a[mid] != a[mid - 1] && a[mid] != a[mid + 1]) return a[mid];
            if ((mid % 2 != 0 && a[mid] == a[mid - 1]) || (mid % 2 == 0 && a[mid] == a[mid + 1])) {
                l = mid + 1;
            } else r = mid - 1;
        }
        return -1;
    }

    public static int findMin(int[] arr) {
        int l = 0;
        int r = arr.length - 1;
        int ans = Integer.MAX_VALUE;
        while (l <= r) {
            int mid = (l + r) / 2;
            //this half is sorted
            if (arr[l] <= arr[mid]) {
                ans = Math.min(ans, arr[l]);
                l = mid + 1;
            }
            //or , this half is sorted
            else {
                ans = Math.min(ans, arr[mid]);
                r = mid - 1;
            }
        }
        return ans;
    }

    private static int getFirstOccuranceLower1(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        int ans = nums.length;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (target <= nums[mid]) {
                ans = mid;
                r = mid - 1;
            } else
                l = mid + 1;
        }
        return ans;
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

    public static void swap(int p1, int p2, int[] a) {
        int temp = a[p2];
        a[p2] = a[p1];
        a[p1] = temp;
    }

    public static void swap(int p1, int p2, long[] a) {
        long temp = a[p2];
        a[p2] = a[p1];
        a[p1] = temp;
    }

}