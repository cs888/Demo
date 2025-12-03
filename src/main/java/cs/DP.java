package cs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class DP {
    public static void main(String[] args) {

        int[][] a = {
                {2, 3, 1, 2},
                {3, 4, 2, 2},
                {5, 6, 3, 5}
        };
        int a1[] = {1,2,3,4};
        int target = 7;
        boolean dp[][] = new boolean[a1.length + 1][target + 2];
        int dpp[] = new int[7];
        Arrays.fill(dpp, -1);
        System.out.println(fibBottomUP(6, dpp));
    }

    //DP - 56
    // min of diag,up,left +1 , skipping 0th row , 0th Col
    public int countNumberOfSquares(int[][] matrix) {
        int dp[][] = new int[matrix.length][matrix[0].length];
        int count = 0;

        for (int i = 0; i < matrix.length; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < matrix[0].length; j++) {
            dp[0][j] = 1;
        }
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0)
                    dp[i][j] = 0;
                else
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));

            }
        }
        //do sum
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                count += dp[i][j];
            }
        }
        return count;
    }

    //dp - 55
    private static int maxRectangleAreaWithAll_1(int[][] matrix) {
        int height[] = new int[matrix[0].length];
        int maxi = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) height[j] += 1;
                else height[j] = 0;
            }
            int max = maxHistogramArea(height);
            maxi = Math.max(maxi, max);
        }
        return maxi;
    }

    static int maxHistogramArea(int a[]) {
        //index
        Stack<Integer> st = new Stack<>();
        int maxA = 0;
        int n = a.length;
        for (int i = 0; i <= n; i++) {
            //if cur is smaller than stack top
            while (!st.empty() && (i == n || a[st.peek()] >= a[i])) {
                int height = a[st.pop()];
                int width;
                if (st.empty()) width = i;
                else width = i - (st.peek() + 1);
                maxA = Math.max(maxA, width * height);
            }
            st.push(i);
        }
        return maxA;
    }


    private static int pa(int[] a, int k) {
        int dp[] = new int[a.length + 1];
        int n = a.length;
        for (int i = n - 1; i >= 0; i--) {
            int maxi = Integer.MIN_VALUE;
            int maxVal = 0;
            for (int j = i; j < Math.min(i + k, a.length); j++) {
                maxVal = Math.max(maxVal, a[j]);
                int max = ((j - i + 1) * maxVal) + dp[j + 1];
                maxi = Math.max(maxi, max);
            }
            dp[i] = maxi;
        }
        return dp[0];
    }

    private static int pa(int i, int[] a, int k, int[] dp) {
        if (i >= a.length) return 0;
        if (dp[i] != 0) return dp[i];
        int maxi = Integer.MIN_VALUE;
        int maxVal = 0;
        for (int j = i; j < Math.min(i + k, a.length); j++) {
            maxVal = Math.max(maxVal, a[j]);
            int max = ((j - i + 1) * maxVal) + pa(j + 1, a, k, dp);
            maxi = Math.max(maxi, max);
        }
        return dp[i] = maxi;
    }

    //dp - 54
    // pass a[0] as max
    private static int pa(int i, int[] a, int k, int maxValTillNow) {
        if (i >= a.length) return 0;
        int maxi = Integer.MIN_VALUE;
        for (int j = i; j < Math.min(i + k, a.length); j++) {
            int max = (j - i + 1) * maxValTillNow
                      + pa(j + 1, a, k, Math.max(maxValTillNow, a[j]));
            maxi = Math.max(maxi, max);
        }
        return maxi;
    }

    private static int pallindromePartitioning(char[] s, int[] dp) {
        int n = s.length;
        for (int i = n - 1; i >= 0; i--) {
            int mini = Integer.MAX_VALUE;
            for (int ind = i; ind < s.length; ind++) {
                if (isPallindrome(i, ind, s)) {
                    int min = 1 + dp[ind + 1];
                    mini = Math.min(min, mini);
                }
            }
            //return mini-1 at last since partition is happening at last
            dp[i] = mini;

        }
        return dp[0];

    }

    private static int pallindromePartitioning(int i, char[] s, int[] dp) {
        if (i >= s.length) return 0;
        if (dp[i] != 0) return dp[i];
        int mini = Integer.MAX_VALUE;
        for (int ind = i; ind < s.length; ind++) {
            if (isPallindrome(i, ind, s)) {
                int min = 1 + pallindromePartitioning(ind + 1, s, dp);
                mini = Math.min(min, mini);
            }
        }
        //return mini-1 at last since partition is happening at last
        return dp[i] = mini;
    }

    //video - 53
    //mininum number of partitions required for string
    // from partion
    private static int pallindromePartitioning(int i, char[] s) {
        if (i >= s.length) return 0;
        int mini = Integer.MAX_VALUE;
        for (int ind = i; ind < s.length; ind++) {
            if (isPallindrome(i, ind, s)) {
                int min = 1 + pallindromePartitioning(ind + 1, s);
                mini = Math.min(min, mini);
            }
        }
        //return mini-1 at last since partition is happening at last
        return mini - 1;
    }

    private static boolean isPallindrome(int start, int end, char[] s) {

        while (start < end) {
            if (s[start] != s[end]) return false;
            else {
                start++;
                end--;
            }
        }
        return true;
    }

    //TODO::doubt in bottom up
    //DP-52
    //        int ans = evalTrue(0,a.length-1,1,s.toCharArray(),dp);
    private static int evalTrue(char[] a, int[][][] dp) {
        int n = a.length;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= n - 1; j++) {
                for (int requireTrue = 0; requireTrue <= 1; requireTrue++) {
                    if (i > j) continue;
                    if (i == j) {
                        if (requireTrue == 1 && a[i] == 'T') dp[i][j][requireTrue] = 1;
                        else if (a[i] == 'F') dp[i][j][requireTrue] = 1;
                    }
                    int ways = 0;
                    for (int ind = i + 1; ind <= j - 1; ind += 2) {
                        int lt = evalTrue(i, ind - 1, 1, a, dp);
                        int lf = evalTrue(i, ind - 1, 0, a, dp);
                        int rt = evalTrue(ind + 1, j, 1, a, dp);
                        int rf = evalTrue(ind + 1, j, 0, a, dp);

                        if (a[ind] == '&') {
                            if (requireTrue == 1) ways += lt * rt;
                            else ways += lt * rf + lf * rt + lf * rf;
                        } else if (a[ind] == '^') {
                            if (requireTrue == 1) ways += lt * rf + lf * rt;
                            else ways += lt * rt + lf * rf;
                        } else {
                            if (requireTrue == 1) ways += lt * rt + lt * rf + lf * rt;
                            else ways += lf * rf;
                        }
                    }
                    dp[i][j][requireTrue] = ways;
                }
            }
        }
        return dp[0][n - 1][1];
    }

    private static int evalTrue(int i, int j, int requireTrue, char[] a, int[][][] dp) {
        if (i > j) return 0;
        if (i == j) {
            if (requireTrue == 1) return a[i] == 'T' ? 1 : 0;
            else return a[i] == 'F' ? 1 : 0;
        }
        if (dp[i][j][requireTrue] != 0) return dp[i][j][requireTrue];

        int ways = 0;
        for (int ind = i + 1; ind <= j - 1; ind += 2) {
            int lt = evalTrue(i, ind - 1, 1, a, dp);
            int lf = evalTrue(i, ind - 1, 0, a, dp);
            int rt = evalTrue(ind + 1, j, 1, a, dp);
            int rf = evalTrue(ind + 1, j, 0, a, dp);

            if (a[ind] == '&') {
                if (requireTrue == 1) ways += lt * rt;
                else ways += lt * rf + lf * rt + lf * rf;
            } else if (a[ind] == '^') {
                if (requireTrue == 1) ways += lt * rf + lf * rt;
                else ways += lt * rt + lf * rf;
            } else {
                if (requireTrue == 1) ways += lt * rt + lt * rf + lf * rt;
                else ways += lf * rf;
            }
        }
        return dp[i][j][requireTrue] = ways;
    }

    //video -52
    // return (0,a.length-1,true,a)
    private static int evalTrue(int i, int j, boolean requireTrue, char[] a) {
        if (i > j) return 0;
        if (i == j) {
            if (requireTrue) return a[i] == 'T' ? 1 : 0;
            else return a[i] == 'F' ? 1 : 0;
        }

        int ways = 0;
        for (int ind = i + 1; ind <= j - 1; ind += 2) {
            int lt = evalTrue(i, ind - 1, true, a);
            int lf = evalTrue(i, ind - 1, false, a);
            int rt = evalTrue(ind + 1, j, true, a);
            int rf = evalTrue(ind + 1, j, false, a);

            if (a[ind] == '&') {
                if (requireTrue) ways += lt * rt;
                else ways += lt * rf + lf * rt + lf * rf;
            } else if (a[ind] == '^') {
                if (requireTrue) ways += lt * rf + lf * rt;
                else ways += lt * rt + lf * rf;
            } else {
                if (requireTrue) ways += lt * rt + lt * rf + lf * rt;
                else ways += lf * rf;
            }
        }
        return ways;
    }

    //append 1 to start & end
    private static int burstBaloon(int[] cost, int[][] dp) {
        int n = cost.length;
        for (int i = n - 1; i >= 1; i--) {
            for (int j = 1; j <= n - 2; j++) {
                if (i > j) continue;
                int maxi = Integer.MIN_VALUE;
                for (int ind = i; ind <= j; ind++) {
                    int max = cost[i - 1] * cost[ind] * cost[j + 1] + burstBaloon(i, ind - 1, cost, dp) + burstBaloon(ind + 1, j, cost, dp);
                    maxi = Math.max(maxi, max);
                }
                dp[1][n - 2] = maxi;
            }
        }
        return dp[1][n - 2];
    }

    private static int burstBaloon(int i, int j, int[] cost, int[][] dp) {
        if (i > j) return 0;
        if (dp[i][j] != 0) return dp[i][j];
        int maxi = Integer.MIN_VALUE;
        for (int ind = i; ind <= j; ind++) {
            int max = cost[i - 1] * cost[ind] * cost[j + 1] + burstBaloon(i, ind - 1, cost, dp) + burstBaloon(ind + 1, j, cost, dp);
            maxi = Math.max(maxi, max);
        }
        return dp[i][j] = maxi;
    }

    //video - 50
    //aapend 1 to start & end
    // start from bottom
    /*int [] cost=new int[a.length+2];
        System.arraycopy(a,0,cost,1,a.length);
    cost[0]=1;
    cost[cost.length-1]=1;
        return burstBaloon(1,a.length,cost);*/
    private static int burstBaloon(int i, int j, int[] cost) {
        if (i > j) return 0;
        int maxi = Integer.MIN_VALUE;
        for (int ind = i; ind <= j; ind++) {
            int max = cost[i - 1] * cost[ind] * cost[j + 1]
                    + burstBaloon(i, ind - 1, cost)
                    + burstBaloon(ind + 1, j, cost);
            maxi = Math.max(maxi, max);
        }
        return maxi;
    }

    // int ans = cutStick(1, a.length -2, a);
    private static int cutStick(int[] cuts, int[][] dp) {
        int n = cuts.length;
        for (int i = n - 1; i >= 1; i--) {
            for (int j = 1; j <= n - 2; j++) {
                if (i > j) continue;
                int mini = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++) {
                    int min = cuts[j + 1] - cuts[i - 1] + dp[i][k - 1] + dp[k + 1][j];
                    mini = Math.min(mini, min);
                }
                dp[i][j] = mini;
            }
        }
        return dp[1][n - 2];
    }

    private static int cutStick(int i, int j, int[] cuts, int[][] dp) {
        if (i > j) return 0;
        if (dp[i][j] != 0) return dp[i][j];
        int mini = Integer.MAX_VALUE;
        for (int k = i; k <= j; k++) {
            int min = cuts[j + 1] - cuts[i - 1] + cutStick(i, k - 1, cuts, dp) + cutStick(k + 1, j, cuts, dp);
            mini = Math.min(mini, min);
        }
        return dp[i][j] = mini;
    }

    // DP-50
    //sort cuts array
    //  Arrays.sort(cuts);
    //add[0]=0
    //add[cut.length-1]=length of stick

    //        int [] temp=new int[cuts.length+2];
    //        System.arraycopy(cuts,0,temp,1,cuts.length);
    //        temp[cuts.length+2-1]=n;
    //       return cutStick(1,cuts.length,temp);
    private static int cutStick(int i, int j, int[] cuts) {
        if (i > j) return 0;
        int mini = Integer.MAX_VALUE;
        for (int k = i; k <= j; k++) {
            //cuts[j + 1] - cuts[i - 1] represt length of stick
            int min = cuts[j + 1] - cuts[i - 1] +
                    cutStick(i, k - 1, cuts) +
                    cutStick(k + 1, j, cuts);
            mini = Math.min(mini, min);
        }
        return mini;
    }

    private static int mm(int[] a, int[][] dp) {

        int n = a.length;
        for (int i = n - 1; i >= 1; i--) {
            for (int j = i + 1; j <= n - 1; j++) {
                int mini = Integer.MAX_VALUE;
                for (int k = i; k <= j - 1; k++) {
                    //total at once for i,j of taking both next step at once
                    int min = a[i - 1] * a[j] * a[k];
                    //split into 2 & calculate each
                    min += dp[i][k] + dp[k + 1][j];
                    mini = Math.min(mini, min);
                }
                dp[i][j] = mini;
            }
        }
        System.out.println(Arrays.deepToString(dp));
        return dp[1][n - 1];
    }

    // mm(1,a.length-1,a,dp)
    private static int mm(int i, int j, int[] a, int[][] dp) {
        if (i == j) return 0;
        if (dp[i][j] != 0) return dp[i][j];
        int mini = Integer.MAX_VALUE;
        for (int k = i; k <= j - 1; k++) {
            //total at once for i,j of taking both next step at once
            int min = a[i - 1] * a[j] * a[k];
            //split into 2 & calculate each
            min += mm(i, k, a, dp) + mm(k + 1, j, a, dp);
            mini = Math.min(mini, min);
        }
        return dp[i][j] = mini;
    }

    //video - 48
    //i =1 j=n-1
    private static int mm(int i, int j, int[] a) {
        if (i == j) return 0;
        int mini = Integer.MAX_VALUE;
        //till <=j-1 because we are taking k+1 further
        for (int k = i; k <= j - 1; k++) {
            int min = a[i - 1] * a[j] * a[k]+ mm(i, k, a) + mm(k + 1, j, a);
            mini = Math.min(mini, min);
        }
        return mini;
    }

    //dp-47
    private static int noOfLIS(int[] a) {

        int dp[] = new int[a.length];
        int count[] = new int[a.length];

        Arrays.fill(dp, 1);
        Arrays.fill(count, 1);
        int maxi = 0;
        for (int i = 1; i < a.length; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (a[prev] < a[i] && dp[i] < 1 + dp[prev]) {
                    dp[i] = 1 + dp[prev];
                    //inherit from prev
                    // if get attached len remains of prev
                    count[i] = count[prev];
                }
                //update count array condition
                else if (a[prev] < a[i] && dp[i] == dp[prev]+1) {
                    count[i] = count[i] + count[prev];
                }
            }
            maxi = Math.max(maxi, dp[i]);
        }

        int totalSum = 0;
        //do sum of all index having maximum count
        for (int i = 0; i < a.length; i++) {
            if (dp[i] == maxi) totalSum += count[i];
        }
        System.out.println(Arrays.toString(dp));
        System.out.println(Arrays.toString(count));
        return totalSum;
    }

    //dp - 46
    private static int longestBiotonicSubsequence(int[] a) {

        int dp1[] = new int[a.length];
        Arrays.fill(dp1, 1);
        int maxVal = 1;

        //increasing array LIS
        for (int i = 1; i < a.length; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (a[prev] < a[i] && dp1[i] < 1 + dp1[prev]) {
                    dp1[i] = 1 + dp1[prev];
                }
            }
        }

        //decreasing array LIS
        int dp2[] = new int[a.length];
        Arrays.fill(dp2, 1);
        for (int i = a.length - 1; i >= 0; i--) {
            for (int prev = a.length - 1; prev > i; prev--) {
                if (a[prev] < a[i] && dp2[i] < 1 + dp2[prev]) {
                    dp2[i] = 1 + dp2[prev];
                }
            }
        }
        for (int i = 0; i < a.length; i++) {
            maxVal = Math.max(dp2[i] + dp1[i] - 1, maxVal);
        }

        return maxVal;

    }

    //TC -> N2*l+logn for sorting
    //dp-45
    private static int longestStringChain(String[] a) {
        int dp[] = new int[a.length];
        Arrays.fill(dp, 1);
        int maxVal = 1;
        for (int i = 1; i < a.length; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (compare(i, prev, a) && dp[i] < 1 + dp[prev]) {
                    dp[i] = 1 + dp[prev];
                    maxVal = Math.max(dp[i], maxVal);
                }
            }
        }
        System.out.println(Arrays.toString(dp));
        return maxVal;
    }

    private static boolean compare(int i, int prev, String[] a) {
        //a[i] length is greater
        if (a[i].length() - a[prev].length() != 1) return false;
        int start1 = 0;
        int start2 = 0;
        //if not matching , increase the larger length by 1
        while (start1 < a[i].length() && start2 < a[prev].length()) {
            if (a[start1] == a[start2]) {
                start1++;
                start2++;
            } else start1++;
        }
        return start1 == start2;
    }

    //dp-44
    private static int largestDivisibleSubset(int[] a) {
        Arrays.sort(a);
        int dp[] = new int[a.length];
        Arrays.fill(dp, 1);
        int maxVal = 1;
        for (int i = 0; i < a.length; i++) {
            for (int prev = 0; prev < i; prev++) {
                if ((a[i] % a[prev] == 0) && dp[prev] + 1 > dp[i]) {
                    dp[i] = 1 + dp[prev];
                    maxVal = Math.max(dp[i], maxVal);
                }
            }
        }
        System.out.println(Arrays.toString(dp));
        return maxVal;
    }

    //dp-43
    private static int lislength(int[] a) {
        List<Integer> ans = new ArrayList<>();
        ans.add(a[0]);
        for (int i = 1; i < a.length; i++) {
            if (a[i] > ans.get(ans.size() - 1)) ans.add(a[i]);
            else {
                int[] a1 = ans.stream().mapToInt(x -> x).toArray();
                int lIndex = getLeftIndex(a1, a[i]);
                ans.set(lIndex, a[i]);
            }
        }
        return ans.size();
    }

    private static int getLeftIndex(int[] a, int target) {
        int l = 0;
        int r = a.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (target > a[mid]) l = mid + 1;
            else r = mid;
        }
        return l;
    }

    //dp-42
    private static List<Integer> lislengthAndLISString(int[] a) {

        int dp[] = new int[a.length];
        int parent[] = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            parent[i] = i;
        }
        Arrays.fill(dp, 1);
        int maxVal = 0;
        int maxValueIndex = -1;
        for (int i = 0; i < a.length; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (a[prev] < a[i] && dp[prev] + 1 > dp[i]) {
                    dp[i] = 1 + dp[prev];
                    parent[i] = prev;

                    if (maxVal < dp[i]) {
                        maxVal = dp[i];
                        maxValueIndex = i;
                    }
                }
            }
        }
        //return maxVal;

        List<Integer> list = new ArrayList<>();
        list.add(a[maxValueIndex]);
        while (parent[maxValueIndex] != maxValueIndex) {
            maxValueIndex = parent[maxValueIndex];
            list.add(a[maxValueIndex]);
        }

        return list;
    }

    //had doubt in bootom up
    private static int lis(int[] a, int[][] dp) {
        int n = a.length;
        int[] prev = new int[a.length + 1];
        int[] cur = new int[a.length + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int prevIndex = i - 1; prevIndex >= -1; prevIndex--) {
                int take = 0;
                if (prevIndex == -1 || a[i] > a[prevIndex])
                    take = 1 + prev[i + 1];
                int not_take = prev[prevIndex + 1];
                cur[prevIndex + 1] = Math.max(take, not_take);
            }
            prev = cur;
        }
        return cur[-1 + 1];
    }

    private static int lis(int i, int prevIndex, int[] a, int[][] dp) {
        if (i >= a.length) return 0;
        int take = 0;
        if (dp[i][prevIndex + 1] != 0) return dp[i][prevIndex + 1];
        if (prevIndex == -1 || a[i] > a[prevIndex])
            take = 1 + lis(i + 1, i, a, dp);

        int not_take = lis(i + 1, prevIndex, a, dp);

        return dp[i][prevIndex + 1] = Math.max(take, not_take);
    }

    //video - 41
    private static int lis(int i, int prevIndex, int[] a) {
        if (i >= a.length) return 0;
        int take = 0;
        //take
        if (prevIndex == -1 || a[i] > a[prevIndex])
            take = 1 + lis(i + 1, i, a);
        //not take
        int not_take = 0 + lis(i + 1, prevIndex, a);
        return Math.max(take, not_take);
    }

    //dp-40
    private static int stock2(int[] a, int fee) {
        int n = a.length;
        int[] prev = new int[2];
        for (int i = n - 1; i >= 0; i--) {
            for (int buy = 0; buy <= 1; buy++) {
                if (buy == 1) prev[1] = Math.max(-a[i] + prev[0], prev[1]);
                else
                    prev[0] = Math.max(a[i] - fee + prev[1], prev[0]);
            }
        }
        return prev[1];
    }

    //DP-40 fee to paid on complete of txn
    private static int stock2(int i, int buy, int[] a, int fee) {
        if (i >= a.length) return 0;
        if (buy == 1) return Math.max(-a[i] + stock2(i + 1, 0, a, fee), 0 + stock2(i + 1, buy, a, fee));
        else
            return Math.max(a[i] - fee + stock2(i + 1, 1, a, fee), stock2(i + 1, buy, a, fee));
    }

    //dp-39
    private static int stock5(int[] a, int[][] dp) {
        int n = a.length;
        int[] prev2 = new int[2];
        int[] prev1 = new int[2];
        int[] cur = new int[2];
        for (int i = n - 1; i >= 0; i--) {
            cur[1] = Math.max(-a[i] + prev1[0], prev1[1]);
            cur[0] = Math.max(a[i] + prev2[1], prev1[0]);

            prev2 = prev1;
            prev1 = cur.clone();
        }
        return prev1[1];
    }

    private static int stock5(int i, int buy, int[] a, int[][] dp) {
        if (i >= a.length) return 0;
        if (dp[i][buy] != 0) return dp[i][buy];
        if (buy == 1) return dp[i][buy] = Math.max(-a[i] + stock5(i + 1, 0, a, dp), 0 + stock5(i + 1, buy, a, dp));
        else
            return dp[i][buy] = Math.max(a[i] + stock5(i + 2, 1, a, dp), stock5(i + 1, buy, a, dp));
    }

    //cooldown
    //DP - 35
    private static int stock5(int i, int buy, int[] a) {
        if (i >= a.length) return 0;
        if (buy == 1) return Math.max(-a[i] + stock5(i + 1, 0, a), 0 + stock5(i + 1, buy, a));
        else
            return Math.max(a[i] + stock5(i + 2, 1, a), stock5(i + 1, buy, a));
    }

    //dp -38 using transactionNumber
    private static int st(int[] a, int tt, int[][] dp) {
        int n = a.length;
        int[] prev = new int[tt + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int tn = tt - 1; tn >= 0; tn--) {
                //buy
                if (tn % 2 == 0) {
                    prev[tn] = Math.max(-a[i] + prev[tn + 1], prev[tn]);
                } else
                    prev[tn] = Math.max(a[i] + prev[tn + 1], prev[tn]);
            }
            ;

        }
        return prev[0];
    }

    private static int st(int i, int tn, int[] a, int totalTrans, int[][] dp) {
        if (i >= a.length || tn == totalTrans) return 0;
        if (dp[i][tn] != 0) return dp[i][tn];
        //buy
        if (tn % 2 == 0) {
            return dp[i][tn] = Math.max(-a[i] + st(i + 1, tn + 1, a, totalTrans, dp), st(i + 1, tn, a, totalTrans, dp));
        } else
            return dp[i][tn] = Math.max(a[i] + st(i + 1, tn + 1, a, totalTrans, dp), st(i + 1, tn, a, totalTrans, dp));

    }

    //using txn number
    private static int st(int i, int tn, int[] a, int totalTrans) {
        if (i >= a.length || tn == totalTrans) return 0;
        //buy
        if (tn % 2 == 0) {
            return Math.max(-a[i] + st(i + 1, tn + 1, a, totalTrans), st(i + 1, tn, a, totalTrans));
        } else
            return Math.max(a[i] + st(i + 1, tn + 1, a, totalTrans), st(i + 1, tn, a, totalTrans));

    }


    private static int stock4(int[] a, int k) {
        int n = a.length;
        int[][] prev = new int[2][k + 1];
        int[][] cur = new int[2][k + 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int buy = 0; buy <= 1; buy++) {
                for (int cap = 1; cap <= k; cap++) {

                    if (buy == 1) cur[buy][cap] = Math.max(-a[i] + prev[0][cap], prev[buy][cap]);
                    else
                        cur[buy][cap] = Math.max(a[i] + prev[1][cap - 1], prev[buy][cap]);
                }
            }
            prev = cur;
        }
        return prev[1][2];
    }

    //dp-37-38
    //at most k txn
    private static int stock4(int i, int buy, int cap, int[] a, int[][][] dp) {
        if (i >= a.length) return 0;
        if (cap == 0) return 0;
        if (dp[i][buy][cap] != 0) return dp[i][buy][cap];
        if (buy == 1)
            return dp[i][buy][cap] = Math.max(-a[i] + stock4(i + 1, 0, cap, a, dp), 0 + stock4(i + 1, buy, cap, a, dp));
        else
            return dp[i][buy][cap] = Math.max(a[i] + stock4(i + 1, 1, cap - 1, a, dp), stock4(i + 1, buy, cap, a, dp));
    }

    //dp - 37
    // at most k txn
    private static int stock4(int i, int buy, int cap, int[] a) {
        if (i >= a.length) return 0;
        if (cap == 0) return 0;
        if (buy == 1) return Math.max(-a[i] + stock4(i + 1, 0, cap, a), 0 + stock4(i + 1, buy, cap, a));
        else
            return Math.max(a[i] + stock4(i + 1, 1, cap - 1, a), stock4(i + 1, buy, cap, a));
    }

    //dp -36
    private static int stock2(int[] a) {
        int n = a.length;
        int[] prev = new int[2];
        for (int i = n - 1; i >= 0; i--) {
            for (int buy = 0; buy <= 1; buy++) {
                if (buy == 1) prev[1] = Math.max(-a[i] + prev[0], prev[1]);
                else
                    prev[0] = Math.max(a[i] + prev[1], prev[0]);
            }
        }
        return prev[1];
    }

    private static int stock2(int i, int buy, int[] a, int[][] dp) {
        if (i >= a.length) return 0;
        if (dp[i][buy] != 0) return dp[i][buy];
        if (buy == 1) return dp[i][buy] = Math.max(-a[i] + stock2(i + 1, 0, a, dp), 0 + stock2(i + 1, buy, a, dp));
        else
            return dp[i][buy] = Math.max(a[i] + stock2(i + 1, 1, a, dp), stock2(i + 1, buy, a, dp));
    }

    // video - 36
    //infinite txn at at time
    private static int stock2(int i, int buy, int[] a) {
        if (i >= a.length) return 0;

        if (buy == 1) return Math.max(-a[i] + stock2(i + 1, 0, a), 0 + stock2(i + 1, buy, a));
        else
            return Math.max(a[i] + stock2(i + 1, 1, a), stock2(i + 1, buy, a));
    }

    //dp-35
    private static int stockMax(int[] a) {
        int buy_min = a[0];
        int profit = 0;
        for (int i = 1; i < a.length; i++) {
            profit = Math.max(profit, a[i] - buy_min);
            buy_min = Math.min(buy_min, a[i]);
        }
        return profit;
    }

    //dp-34
    //TODO: has issue in bootom UP writing
    private static boolean wild(char[] t, char[] p, boolean[][] dp) {
        int m = t.length;
        int n = p.length;
        boolean[] prev = new boolean[n + 1];
        boolean[] cur = new boolean[n + 1];
        prev[0] = true;
        for (int i = 1; i <= m; i++) {
            boolean flag = true;
            for (int k = 1; k <= i; k++) {
                if (t[k - 1] != '*') {
                    flag = false;
                    break;
                }
            }
            cur[0] = flag;
            for (int j = 1; j <= n; j++) {
                if (t[i - 1] == p[j - 1] || p[j - 1] == '?')
                    cur[j] = prev[j - 1];
                else if (p[j - 1] == '*')
                    cur[j] = prev[j] || cur[j - 1];
            }
            prev = cur;
        }
        return prev[n];
    }

    private static boolean wild(int i, int j, char[] t, char[] p, Boolean[][] dp) {
        if (i == 0 && j == 0) return true;
        if (i == 0 && j > 0) return false;
        if (j == 0 && i > 0) {
            for (int k = 1; k <= i; k++) {
                if (t[k] != '*') return false;
            }
            return true;
        }
        if (dp[i][j] != null) return dp[i][j];

        if (t[i - 1] == p[j - 1] || p[j - 1] == '?')
            return dp[i][j] = wild(i - 1, j - 1, t, p, dp);
        else if (p[j - 1] == '*')
            return dp[i][j] = wild(i - 1, j, t, p, dp) || wild(i, j - 1, t, p, dp);
        else return dp[i][j] = false;

    }

    //dp - 34
    private static boolean wild(int i, int j, char[] t, char[] p) {
        if (i < 0 && j < 0) return true;
        if (i < 0 && j >= 0) return false;
        if (i >= 0 && j < 0) {
            for (int k = 0; k <= i; k++) {
                if (t[k] != '*') return false;
            }
            return true;
        }

        if (t[i] == p[j] || p[j] == '?')
            return wild(i - 1, j - 1, t, p);
        else if (p[j] == '*')
            return wild(i - 1, j, t, p) || wild(i, j - 1, t, p);
        return false;

    }

    //dp-33
    private static int edit(char[] s, char[] t, int[][] dp) {
        int m = s.length;
        int n = t.length;
        int[] prev = new int[n + 1];
        int[] cur = new int[n + 1];

        for (int j = 0; j <= n; j++) {
            prev[j] = j;
        }
        for (int i = 1; i <= m; i++) {
            cur[0] = i;
            for (int j = 1; j <= n; j++) {
                if (s[i - 1] == t[j - 1])
                    cur[j] = prev[j - 1];
                else
                    cur[j] = 1 + Math.min(prev[j - 1], Math.min(prev[j], cur[j - 1]));
            }
            prev = cur.clone();
        }
        return prev[n];
    }


    private static int edit(int i, int j, char[] s, char[] t, int[][] dp) {
        if (i == 0) return j;
        if (j == 0) return i;
        if (dp[i][j] != 0) return dp[i][j];
        if (s[i - 1] == t[j - 1])
            return dp[i][j] = edit(i - 1, j - 1, s, t, dp);
        else
            return dp[i][j] = 1 + Math.min(edit(i - 1, j - 1, s, t, dp), Math.min(edit(i - 1, j, s, t, dp), edit(i, j - 1, s, t, dp)));
    }

    //video -33
    private static int edit(int i, int j, char[] s, char[] t) {
        //length is j+1
        if (i < 0) return j + 1;
        //length is i+1
        if (j < 0) return i + 1;
        if (s[i] == t[j])
            return edit(i - 1, j - 1, s, t);
        else
            return 1 + Math.min(edit(i - 1, j - 1, s, t),
                    Math.min(edit(i - 1, j, s, t), edit(i, j - 1, s, t)));
    }

    //dp 32 distinct
    private static int dss(char[] s, char[] t, int[][] dp) {
        int m = s.length;
        int n = t.length;
        int prev[] = new int[n + 1];
        prev[0] = 1;

        for (int i1 = 1; i1 <= m; i1++) {
            for (int i2 = n; i2 >= 1; i2--) {
                if (s[i1 - 1] == t[i2 - 1])
                    prev[i2] = prev[i2 - 1] + prev[i2];
            }
        }
        return prev[n];
    }

    private static int dss(int i1, int i2, char[] s, char[] t, int[][] dp) {
        if (i2 == 0) return 1;
        if (i1 == 0) return 0;
        if (dp[i1][i2] != 0) return dp[i1][i2];
        if (s[i1 - 1] == t[i2 - 1])
            return dp[i1][i2] = dss(i1 - 1, i2 - 1, s, t, dp) + dss(i1 - 1, i2, s, t, dp);
        else
            return dp[i1][i2] = dss(i1 - 1, i2, s, t, dp);

    }

    //dp- 33
    private static int dss(int i1, int i2, char[] s, char[] t) {
        if (i2 < 0) return 1;
        if (i1 < 0) return 0;
        if (s[i1] == t[i2])
            // if mathcing i can take or not take
            return dss(i1 - 1, i2 - 1, s, t) + dss(i1 - 1, i2, s, t);
        else
            return dss(i1 - 1, i2, s, t);

    }

    //dp-32
    private static StringBuffer supersequencePrint(char[] s, char[] t, int[][] dp) {

        //build dp table
        lcs2(s, t, dp);
        int i = s.length - 1;
        int j = t.length - 1;

        StringBuffer ans = new StringBuffer();
        while (i > 0 && j > 0) {
            if (s[i] == t[j]) {
                ans.append(s[i]);
                i--;
                j--;
            } else if (dp[i][j - 1] > dp[i - 1][j]) {
                ans.append(t[j]);
                j--;
            } else {
                ans.append(s[i]);
                i--;
            }
        }

        //lefover
        while (i > 0) {
            ans.append(s[i--]);
        }

        // //lefover
        while (j > 0) {
            ans.append(t[j--]);
        }

        return ans.reverse();
    }

    //dp -31
    private static int supersequenceLength(String s, String t, int[][] dp) {
        //take common only once
        return s.length() + t.length() - lcs2(s.toCharArray(), t.toCharArray(), dp);
    }


    //dp 30
    private static int minInsertionConverAtoB(String s, String t, int[][] dp) {
        int lcs = lcs2(s.toCharArray(), t.toCharArray(), dp);
        //first delete all unwanted
        int deletion = s.length() - lcs;
        // insert all wanted
        int insertion = t.length() - lcs;

        return deletion + insertion;
    }

    //dp-29
    private static int minInsertionToMakePallindrome(String s, int[][] dp) {
        //length - longest pallindrome subsequence
        return s.length() - lps(s, dp);
    }


    //dp- 28
    private static int lps(String s, int[][] dp) {
        return lcs2(s.toCharArray(), new StringBuffer(s).reverse().toString().toCharArray(), dp);
    }

    //dp - 27
    private static int lcSubString(char[] s, char[] t, int[][] dp) {
        int n = s.length - 1;
        int m = t.length - 1;
        int ans = 0;
        for (int i1 = 1; i1 < n; i1++) {
            for (int i2 = 1; i2 < m; i2++) {
                if (s[i1 - 1] == t[i2 - 1])
                    dp[i1][i2] = 1 + dp[i1 - 1][i2 - 1];
                ans = Math.max(ans, dp[i1][i2]);
            }
        }
        return ans;
    }

    //dp -26
    private static void printLCS(char[] s, char[] t) {

        int[][] dp = new int[s.length + 1][t.length + 1];
        //build dp array
        lcs1(s, t, dp);
        StringBuffer ans = new StringBuffer();

        int i = s.length - 1, j = t.length - 1;
        while (i > 0 && j > 0) {
            if (s[i] == t[j]) {
                ans.append(s[i]);
                i = i - 1;
                j = j - 1;
            } else if (dp[i][j - 1] < dp[i - 1][j]) i = i - 1;
            else j = j - 1;
        }
        System.out.println(Arrays.deepToString(dp));

        System.out.println(ans.reverse());
    }

    private static int lcs2(char[] s, char[] t, int[][] dp) {
        int n = s.length;
        int m = t.length;

        for (int i1 = 1; i1 <= n; i1++) {
            for (int i2 = 1; i2 <= m; i2++) {
                if (s[i1 - 1] == t[i2 - 1])
                    dp[i1][i2] = 1 + dp[i1 - 1][i2 - 1];

                else dp[i1][i2] = Math.max(dp[i1 - 1][i2], dp[i1][i2 - 1]);
            }
        }
        return dp[n][m];
    }

    private static int lcs1(char[] s, char[] t, int[][] dp) {
        int n = s.length;
        int m = t.length;

        for (int i1 = 0; i1 < n; i1++) {
            for (int i2 = 0; i2 < m; i2++) {
                if (s[i1] == t[i2])
                    dp[i1 + 1][i2 + 1] = 1 + dp[i1][i2];
                else dp[i1 + 1][i2 + 1] = Math.max(dp[i1][i2 + 1], dp[i1 + 1][i2]);
            }
        }
        return dp[n][m];
    }

    private static int lcs(char[] s, char[] t) {
        int n = s.length;
        int m = t.length;
        int prev[] = new int[s.length];
        int cur[] = new int[s.length];
        for (int i1 = 0; i1 < n; i1++) {
            for (int i2 = 0; i2 < m; i2++) {
                if (i1 == 0) {
                    if (s[i1] == t[i2]) cur[i2] = 1;
                } else if (i2 == 0) {
                    if (t[i2] == s[i1]) cur[i2] = 1;
                } else if (s[i1] == t[i2])
                    cur[i2] = 1 + prev[i2 - 1];
                else cur[i2] = Math.max(prev[i2], cur[i2 - 1]);
            }
            prev = cur;
        }
        return prev[m - 1];
    }

    private static int lcs(int i1, int i2, char[] s, char[] t, int[][] dp) {
        if (i1 < 0 || i2 < 0) return 0;
        if (dp[i1][i2] != 0) return dp[i1][i2];
        if (s[i1] == t[i2])
            return dp[i1][i2] = 1 + lcs(i1 - 1, i2 - 1, s, t, dp);
        else return dp[i1][i2] = Math.max(lcs(i1 - 1, i2, s, t, dp), lcs(i1, i2 - 1, s, t, dp));

    }

    //dp-25
    private static int lcs(int i1, int i2, char[] s, char[] t) {
        if (i1 < 0 || i2 < 0) return 0;
        if (s[i1] == t[i2])
            return 1 + lcs(i1 - 1, i2 - 1, s, t);
        else return Math.max(lcs(i1 - 1, i2, s, t), lcs(i1, i2 - 1, s, t));

    }

    //dp-24
    private static int rod(int rodLength, int[] val, int[][] dp) {
        int n = val.length;
        int[] prev = new int[rodLength + 1];
        for (int i = 0; i < n; i++) {
            for (int N = 0; N <= rodLength; N++) {
                int take = 0;
                if (N - i - 1 >= 0)
                    take = val[i] + prev[N - i - 1];

                int not_take = 0;
                if (i > 0) not_take += prev[N];
                prev[N] = Math.max(take, not_take);
            }
        }
        return prev[rodLength];
    }

    private static int rod(int i, int N, int[] val, int[][] dp) {

        if (i < 0) return 0;
        if (dp[i][N] != 0) return dp[i][N];
        int take = 0;
        if (N - i - 1 >= 0)
            take = val[i] + rod(i, N - i - 1, val, dp);

        int not_take = rod(i - 1, N, val, dp);
        return dp[i][N] = Math.max(take, not_take);

    }

    //i can pick a index any number of times
    //dp on subsequence
    //dp - 24
    private static int rod(int i, int N, int[] val) {

        if (i < 0) return 0;
        int take = 0;
        if (N - i - 1 >= 0)
            take = val[i] + rod(i, N - i - 1, val);

        int not_take = rod(i - 1, N, val);
        return Math.max(take, not_take);

    }

    private static int bKnap(int Wt, int[] wt, int[] val) {
        int n = wt.length;
        int[] prev = new int[Wt + 1];
        int[] cur = new int[Wt + 1];
        //if i==0 do not work then write cases for i=0 manually
        for (int i = 0; i < n; i++) {
            for (int W = 0; W <= Wt; W++) {
                //if(i<0) return 0;
                int take = 0;
                if (W - wt[i] >= 0)
                    take = val[i] + cur[W - wt[i]];

                int not_take = 0;
                if (i > 0) not_take += prev[W];
                cur[W] = Math.max(take, not_take);
            }
        }
        return prev[Wt];
    }

    private static int bKnap(int i, int W, int[] wt, int[] val, int[][] dp) {
        if (i < 0) return 0;
        if (dp[i][W] != 0) return dp[i][W];
        int take = 0;
        if (W - wt[i] >= 0)
            take = val[i] + bKnap(i, W - wt[i], wt, val, dp);

        int not_take = bKnap(i - 1, W, wt, val, dp);
        return dp[i][W] = Math.max(take, not_take);
    }

    //DP -13
    private static int bKnap(int i, int W, int[] wt, int[] val) {
        if (i < 0) return 0;
        int take = 0;
        if (W - wt[i] >= 0)
            take = val[i] + bKnap(i, W - wt[i], wt, val);

        int not_take = bKnap(i - 1, W, wt, val);
        return Math.max(take, not_take);
    }

    //dp - 22
    private static int coin1(int[] a, int target, int[][] dp) {
        int n = a.length;
        int[] prev = new int[target + 1];
        int[] cur = new int[target + 1];
        for (int i = 0; i < n; i++) {
            for (int sum = 0; sum <= target; sum++) {
                if (i == 0) {
                    if (sum % a[i] == 0) cur[sum] = 1;
                    continue;
                }
                int take = 0;
                if (sum - a[i] >= 0)
                    take = cur[sum - a[i]];
                int not_take = prev[sum];

                cur[sum] = take + not_take;
            }
            prev = cur;
        }
        return prev[target];
    }

    private static int coin1(int i, int[] a, int sum, int[][] dp) {
        if (i == 0) {
            if (sum % a[i] == 0) return 1;
            return 0;
        }
        if (dp[i][sum] != 0) return dp[i][sum];
        int take = 0;
        if (sum - a[i] >= 0)
            take = coin1(i, a, sum - a[i], dp);
        int not_take = coin1(i - 1, a, sum, dp);

        return dp[i][sum] = take + not_take;
    }

    //infinite supply means be at same index
    private static int coin1(int i, int[] a, int sum) {
        if (i == 0) {
            if (sum % a[i] == 0) return 1;
            return 0;
        }
        int take = 0;
        if (sum - a[i] >= 0)
            take = coin1(i, a, sum - a[i]);
        int not_take = coin1(i - 1, a, sum);

        return take + not_take;
    }

    //dp- dp-21 solution , take solution of dp-18 which is same as this
    private static int ts(int i, int sum, int[] a) {
        if (i < 0) {
            if (sum == 0) return 1;
            return 0;
        }
        int plus = ts(i - 1, sum + a[i], a);
        int minus = ts(i - 1, sum - a[i], a);

        return plus + minus;
    }

    //dp-20
    private static int coin(int[] a, int sum) {
        int n = a.length;
        int prev[] = new int[sum + 1];
        int cur[] = new int[sum + 1];
        for (int i = 0; i < n; i++) {
            for (int amount = 0; amount <= sum; amount++) {
                if (i == 0) {
                    if (amount % a[i] == 0) cur[amount] = amount / a[0];
                    else cur[amount] = (int) 1e9;
                } else {
                    int take = Integer.MAX_VALUE;
                    if (amount - a[i] >= 0)
                        take = 1 + cur[amount - a[i]];

                    int not_take = 0 + prev[amount];
                    cur[amount] = Math.min(take, not_take);
                }
            }
            prev = cur;
        }

        return prev[sum];
    }

    private static int coin(int i, int[] a, int amount, int[][] dp) {
        if (i == 0) {
            if (amount % a[0] == 0) return amount / a[0];
            return (int) 1e9;
        }
        if (dp[i][amount] != 0) return dp[i][amount];
        int take = Integer.MAX_VALUE;
        if (amount - a[i] >= 0)
            take = 1 + coin(i, a, amount - a[i], dp);

        int not_take = 0 + coin(i - 1, a, amount, dp);
        return dp[i][amount] = Math.min(take, not_take);

    }

    //dp - 20
    //TODO: ISSUE
    private int coinz(int i, int[] coins, int sum) {
        if (sum == 0) return 0;
        if (i >= coins.length) {
            //if < 0 then return big no. so that it is ruled out
            return (int) 1e8;
        }
        int take = (int) 1e8;
        if (sum - coins[i] >= 0)
            take = 1 + coinz(i, coins, sum - coins[i]);
        int not_take = 0 + coinz(i + 1, coins, sum);
        return Math.min(take, not_take);
    }

    //dp-19
    private static int knap(int BagWeight, int[] wt, int[] val) {
        int n = wt.length;
        int prev[] = new int[BagWeight + 1];
        int cur[] = new int[BagWeight + 1];
        for (int i = 0; i < n; i++) {
            for (int W = 0; W <= BagWeight; W++) {
                //this was error facing which i fixed later
                if (i == 0) {
                    if (wt[0] <= BagWeight) cur[W] = val[0];
                } else {
                    int take = 0;
                    if (W - wt[i] >= 0 && i > 0)
                        take = val[i] + prev[W - wt[i]];
                    int not_take = 0;
                    if (i > 0) not_take += prev[W];
                    cur[W] = Math.max(take, not_take);
                }
            }
            prev = cur.clone();
        }
        return prev[BagWeight];
    }

    private static int knap(int i, int W, int[] wt, int[] val, int[][] dp) {
        if (i < 0) return 0;
        if (dp[i][W] != 0) return dp[i][W];
        int take = 0;
        if (W - wt[i] >= 0)
            take = val[i] + knap(i - 1, W - wt[i], wt, val, dp);
        int not_take = 0 + knap(i - 1, W, wt, val, dp);
        return dp[i][W] = Math.max(take, not_take);

    }

    //dp - 19
    private static int knap(int i, int W, int[] wt, int[] val) {
        if (i < 0) return 0;
        int take = 0;
        if (W - wt[i] >= 0)
            take = val[i] + knap(i - 1, W - wt[i], wt, val);
        int not_take = 0 + knap(i - 1, W, wt, val);

        return Math.max(take, not_take);

    }

    //dp - 18
    // s1-s2=D , s1>s2
    // totSum-s2-s2=D , so s2=(totsum-d)/2 which needs to find
    //count of subset whose sum is (totSum-D)/2 ...
    private static int sp(int[] a, int d) {
        int totSum = Arrays.stream(a).sum();
        int target = (totSum - d) / 2;
        int[][] dp = new int[a.length][target + 1];
        // if(target%2!=0 ||target<0) return 0;
        return subsetCounts(a.length - 1, a, target, dp);
    }


    //TODO: facing issue in toding this
    private static int subsetCountsBootomUp(int[] a, int givenTarget, int[][] dp) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int target = 0; target <= givenTarget; target++) {
                if (target == 0) dp[i][target] = 1;
                else {
                    int take = 0;
                    if (target - a[i] >= 0)
                        take += subsetCounts(i - 1, a, target - a[i], dp);
                    int not_take = subsetCounts(i - 1, a, target, dp);
                    dp[i][target] = take + not_take;
                }
            }
        }
        return dp[n - 1][givenTarget];
    }

    private static int subsetCounts(int i, int[] a, int target, int[][] dp) {
        if (target == 0) return 1;
        if (i < 0) return 0;
        if (dp[i][target] != 0) return dp[i][target];
        int take = 0;
        if (target - a[i] >= 0)
            take += subsetCounts(i - 1, a, target - a[i], dp);

        int not_take = subsetCounts(i - 1, a, target, dp);

        return dp[i][target] = take + not_take;

    }

    //dp - 17
    private static int subsetCounts(int i, int[] a, int target) {
        if (target == 0) return 1;
        if (i < 0) return 0;

        int take = 0;
        if (target - a[i] >= 0)
            take += subsetCounts(i - 1, a, target - a[i]);

        int not_take = subsetCounts(i - 1, a, target);

        return take + not_take;

    }

    //DP 17 impl - doubt
    private static int partitionTwoSubSetMinDifference(int[] a, int givenTarget) {
        int row = a.length;
        boolean prev[] = new boolean[givenTarget + 1];
        boolean cur[] = new boolean[givenTarget + 1];
        for (int i = 0; i < row; i++) {
            for (int target = 0; target <= givenTarget; target++) {
                if (target == 0) cur[target] = true;
                else {
                    boolean take = false;
                    if (target - a[i] >= 0 && i > 0) take = prev[target - a[i]];
                    boolean notTake = false;
                    if (i > 0) notTake = prev[target];
                    cur[target] = take || notTake;
                }
            }
            prev = cur.clone();
        }

        int ans = (int) 1e8;
        for (int s1 = 0; s1 <= givenTarget / 2; s1++) {
            if (prev[s1]) {
                ans = Math.min(ans, Math.abs((givenTarget - s1) - s1));
            }
        }

        return ans;
    }

    //wrong answer
    private static boolean taragetSubsetSum2(int[] a, int givenTarget) {
        int row = a.length;
        boolean prev[] = new boolean[givenTarget + 1];
        boolean cur[] = new boolean[givenTarget + 1];
        for (int i = 0; i < row; i++) {
            for (int target = 0; target <= givenTarget; target++) {
                if (target == 0) cur[target] = true;
                else {
                    boolean take = false;
                    if (target - a[i] >= 0 && i > 0) take = prev[target - a[i]];
                    boolean notTake = false;
                    if (i > 0) notTake = prev[target];
                    cur[target] = take || notTake;
                }
            }
            prev = cur.clone();
        }
        System.out.println(Arrays.toString(prev));
        return prev[givenTarget];
    }

    //TC - > n*target
    //video - 14
    private static boolean taragetSubsetSum(int i, int target, int[] a, Boolean[][] dp) {
        if (target == 0) return true;
        if (i < 0) return false;
        if (dp[i][target] != null) return dp[i][target];
        boolean take = false;
        if (target - a[i] >= 0) take = taragetSubsetSum(i - 1, target - a[i], a, dp);

        boolean notTake = taragetSubsetSum(i - 1, target, a, dp);
        return dp[i][target] = take || notTake;
    }

    private static boolean taragetSubsetSum(int i, int target, int[] a) {
        if (target == 0) return true;
        if (i < 0) return false;
        boolean take = false;
        if (target - a[i] >= 0) take = taragetSubsetSum(i - 1, target - a[i], a);

        boolean notTake = taragetSubsetSum(i - 1, target, a);
        return take || notTake;
    }

    //issue
    private static int cherryPickBottomUP(int[][] g, int[][][] dp) {

        int m = g.length;
        int n = g[0].length;
        int ans = 0;
        for (int i = m - 1; i >= 0; i--) {
            for (int j1 = n - 1; j1 >= 0; j1--) {
                for (int j2 = 0; j2 <= n - 1; j2++) {
                    for (int d1 = -1; d1 <= 1; d1++) {
                        for (int d2 = -1; d2 <= 1; d2++) {
                            int val = 0;
                            if (j1 == j2) val = g[i][j1];
                            else val = g[i][j1] + g[i][j2];
                            if (i + 1 < m && j1 + d1 >= 0 && j2 + d2 >= 0 && j1 + d1 < n && j2 + d2 < n)
                                val += dp[i + 1][j1 + d1][j2 + d2];
                            dp[i][j1][j2] = ans = Math.max(ans, val);
                        }
                    }
                }
            }
        }
        return ans;
    }

    private static int cherryPick(int i, int j1, int j2, int[][] g, int[][][] dp) {
        if (j1 < 0 || j2 < 0 || j1 >= g[0].length || j2 >= g[0].length || i >= g.length) return 0;
        if (dp[i][j1][j2] != 0) return dp[i][j1][j2];
        int ans = 0;
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                int val = 0;
                if (j1 == j2) val = g[i][j1];
                else val = g[i][j1] + g[i][j2];
                val += cherryPick(i + 1, j1 + d1, j2 + d2, g, dp);
                dp[i][j1][j2] = ans = Math.max(ans, val);
            }
        }
        return ans;
    }

    //doubt
    private static int cherryPick(int i, int j1, int j2, int[][] g) {
        if (j1 < 0 || j2 < 0 || j1 >= g[0].length || j2 >= g[0].length || i >= g.length) return 0;

        int ans = 0;
        for (int d1 = -1; d1 <= 1; d1++) {
            for (int d2 = -1; d2 <= 1; d2++) {
                int val;
                if (j1 == j2) val = g[i][j1];
                else val = g[i][j1] + g[i][j2];
                val += cherryPick(i + 1, j1 + d1, j2 + d2, g);
                ans = Math.max(ans, val);
            }
        }
        return ans;
    }


    //another way using manual loop
    //TODO::doubt not working
    private static int cherry2(int i, int j1, int j2, int[][] g) {
        if (j1 < 0 || j2 < 0 || j1 >= g[0].length || j2 >= g[0].length || i >= g.length) return 0;

        int max = Integer.MIN_VALUE;
        for (int bob = -1; bob <= 1; bob++) {
            for (int alice = -1; alice <= 1; alice++) {
                int val = g[i][j1] + g[i][j2];
                if (bob == alice) val = g[i][j1];
                int tmep=val+  cherry2(i + 1, j1+bob, j2+alice, g);
                max = Math.max(tmep, max);
            }
        }

        return max;
    }

    private static int cherry(int i, int j1, int j2, int[][] g) {
        if (j1 < 0 || j2 < 0 || j1 >= g[0].length || j2 >= g[0].length || i >= g.length) return 0;

        int alice = Math.max(Math.max(cherry(i + 1, j1 - 1, j2, g),
                                      cherry(i + 1, j1, j2, g)),
                                      cherry(i + 1, j1 + 1, j2, g));

        int bob =   Math.max(Math.max(
                        cherry(i + 1, j1, j2 - 1, g),
                        cherry(i + 1, j1, j2, g)),
                        cherry(i + 1, j1, j2 + 1, g));

        if (j1 == j2) return g[i][j1] + Math.max(alice, bob);
        else return g[i][j1] + g[i][j2] + Math.max(alice, bob);

    }

    private static int fallingPathSumBottomUp(int[][] g, int[][] dp) {
        int n = g.length;
        int m = g[0].length;
        int[] prev = new int[m];

        for (int i = 0; i < n; i++) {
            //instead of clone declare here cur array
            int[] cur = new int[m];
            for (int j = 0; j < m; j++) {
                if (i == 0) cur[j] = g[i][j];
                else {
                    int down = prev[j];
                    int left_diag = 0;
                    if (j > 0) left_diag += prev[j - 1];
                    int right_diag = 0;
                    if (j + 1 < m) right_diag += prev[j + 1];
                    cur[j] = g[i][j] + Math.max(down, Math.max(left_diag, right_diag));
                }
            }
            prev = cur;
        }
        int ans = 0;
        for (int j = 0; j < m; j++) {
            ans = Math.max(ans, prev[j]);
        }
        return ans;
    }

    private static int fallingPathSum(int i, int j, int[][] g, int[][] dp) {
        if (j < 0 || j >= g[0].length) return 0;
        if (i == 0) return g[i][j];
        if (dp[i][j] != -1) return dp[i][j];

        int down = fallingPathSum(i - 1, j, g, dp);
        int left_diag = fallingPathSum(i - 1, j - 1, g, dp);
        int right_diag = fallingPathSum(i - 1, j + 1, g, dp);
        return dp[i][j] = g[i][j] + Math.max(down, Math.max(left_diag, right_diag));

    }

    //dp 12
    //try for all element in first row
    //start from either n-1, or 0th row
    private static int fallingPathSum(int i, int j, int[][] g) {
        if (j < 0 || j >= g[0].length) return 0;
        if (i == 0) return g[i][j];

        int down = g[i][j] + fallingPathSum(i - 1, j, g);
        int left_diag = g[i][j] + fallingPathSum(i - 1, j - 1, g);
        int right_diag = g[i][j] + fallingPathSum(i - 1, j + 1, g);
        return Math.max(down, Math.max(left_diag, right_diag));

    }

    private static int triangleFixedStartingPointBottomUp(int[][] g) {
        int[] cur = new int[g.length];
        int[] next = new int[g.length];
        for (int i = g.length - 1; i >= 0; i--) {
            for (int j = g[i].length - 1; j >= 0; j--) {
                if (i == g.length - 1) cur[j] = g[i][j];
                else {
                    int down = g[i][j] + next[j];
                    int diag = g[i][j] + next[j + 1];
                    cur[j] = Math.min(down, diag);
                }
            }
            next = cur;
        }
        return next[0];
    }

    private static int triangleFixedStartingPointDP(int i, int j, int[][] g, int[][] dp) {
        if (i == g.length - 1) return g[g.length - 1][j];
        if (dp[i][j] != -1) return dp[i][j];
        int down = g[i][j] + triangleFixedStartingPointDP(i + 1, j, g, dp);

        int diag = g[i][j] + triangleFixedStartingPointDP(i + 1, j + 1, g, dp);

        return dp[i][j] = Math.min(down, diag);

    }

    //min path sum
    // dp - 11
    private static int triangleFixedStartingPoint(int i, int j, int[][] g) {
        if (i == g.length - 1) return g[g.length - 1][j];
        int downPath = g[i][j] + triangleFixedStartingPoint(i + 1, j, g);

        int diagPath = g[i][j] + triangleFixedStartingPoint(i + 1, j + 1, g);

        return Math.min(downPath, diagPath);

    }

    private static int minPathSumBottomUP(int[][] g) {
        int prev[] = new int[g.length];
        int cur[] = new int[g.length];
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[0].length; j++) {
                if (i == 0 && j == 0) cur[j] = g[i][j];
                else {
                    int up = g[i][j];
                    if (i > 0) up += prev[j];
                    else up += (int) 1e9;
                    int left = g[i][j];
                    if (j > 0) left += cur[j - 1];
                    else up += (int) 1e9;
                    cur[j] = Math.min(up, left);
                }
            }
            prev = cur;
        }
        return prev[g[0].length - 1];
    }

    private static int minPathSum(int i, int j, int[][] g, int[][] dp) {
        if (i == 0 && j == 0) return g[i][j];
        if (i < 0 || j < 0) return (int) 1e8;
        if (dp[i][j] != -1) return dp[i][j];
        int up = g[i][j] + minPathSum(i - 1, j, g, dp);
        int left = g[i][j] + minPathSum(i, j - 1, g, dp);
        return dp[i][j] = Math.min(up, left);
    }

    //DP -9
    private static int minPathSum(int i, int j, int[][] g) {
        if (i == 0 && j == 0) return g[i][j];
        //return very big number so that that calculated path sum is ruled out
        if (i < 0 || j < 0) return (int) 1e8;
        int up = g[i][j] + minPathSum(i - 1, j, g);
        int left = g[i][j] + minPathSum(i, j - 1, g);
        return Math.min(up, left);
    }

    private static int upathBottomUpOptimized(int[][] a) {
        int m = a.length;
        int n = a[0].length;
        int[] prev = new int[a.length];
        int[] cur = new int[a.length];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) cur[j] = 1;
                else {
                    int up = 0;if (i > 0) up += prev[j];
                    int left = 0;if (j > 0) left += cur[j - 1];
                    cur[j] = up + left;
                }
            }
            prev = cur;
        }
        return prev[n - 1];
    }

    private static int upathBottomUp(int i1, int j1, int[][] dp) {

        for (int i = 0; i <= i1; i++) {
            for (int j = 0; j <= j1; j++) {
                if (i == 0 && j == 0) dp[0][0] = 1;
                else {
                    int up = 0; if(i>0)up+=dp[i - 1][ j];
                    int left = 0; if(j>0)up+=dp[i] [j - 1];
                    dp[i][j] = up + left;
                }
            }
        }
        return dp[i1][j1];
    }

    private static int upathDp(int i, int j, int[][] a, int[][] dp) {
        if (i == 0 && j == 0) return 1;
        if (i < 0 || j < 0) return 0;
        if (dp[i][j] != -1) return dp[i][j];
        int up = upathDp(i - 1, j, a, dp);
        int left = upathDp(i, j - 1, a, dp);
        return dp[i][j] = up + left;

    }

    //number of paths
    //dp -9
    private static int upath(int i, int j, int[][] a) {
        if (i == 0 && j == 0) return 1;
        if (i < 0 || j < 0) return 0;
        int up = upath(i - 1, j, a);
        int left = upath(i, j - 1, a);
        return up + left;

    }

    //need to correct this , not working
    private static int ninjaBottomUpOptimized(int prev, int[][] a) {
        int pre[] = new int[a.length];
        int cur[] = new int[a.length];

        for (int i = 0; i < a.length; i++) {
            int ans = Integer.MIN_VALUE;
            for (int j = 0; j < 3; j++) {
                if (prev != j) {
                    int temp_ans = a[i][j];
                    if (i > 0) temp_ans += pre[j];
                    cur[prev + 1] = ans = Math.max(temp_ans, ans);
                }
            }
            pre = cur;

        }
        return pre[prev];

    }

    //TODO: giving error
    private static int ninjaBottomUp1(int i1, int prev1, int[][] a) {

        int []prevv=new int[4];
        int[] cur=new int[4];

        for (int i = 0; i <= i1; i++) {
            for (int prev = 0; prev <=prev1; prev++) {
                int ans = Integer.MIN_VALUE;
                for (int j = 0; j < 3; j++) {
                    if (prev != j) {
                        int temp_ans = a[i][j]; if (i > 0) temp_ans += prevv[j];
                        cur[prev] = ans = Math.max(temp_ans, ans);
                    }
                }
            }
            prevv=cur;
        }
        return cur[prev1];
    }

    private static int ninjaBottomUp(int i1, int prev1, int[][] a, int[][] dp) {

        for (int i = 0; i <= i1; i++) {
            for (int prev = 0; prev <=prev1; prev++) {
                int ans = Integer.MIN_VALUE;
                for (int j = 0; j < 3; j++) {
                    if (prev != j) {
                        int temp_ans = a[i][j]; if (i > 0) temp_ans += dp[i - 1][j];
                        dp[i][prev] = ans = Math.max(temp_ans, ans);
                    }
                }
            }
        }
        return dp[i1][prev1];
    }

    private static int ninja(int i, int prev, int[][] a, int[][] dp) {
        if (i < 0) return 0;
        if (dp[i][prev] != -1) return dp[i][prev];
        int ans = Integer.MIN_VALUE;
        for (int j = 0; j < 3; j++) {
            if (prev != j) {
                int temp_ans = a[i][j] + ninja(i - 1, j, a, dp);
                dp[i][prev] = ans = Math.max(temp_ans, ans);
            }
        }
        return dp[i][prev];
    }

    //DP -7
    private static int ninja(int i, int prev, int[][] a) {
        if (i < 0) return 0;
        int ans = Integer.MIN_VALUE;
        for (int task = 0; task < 3; task++) {
            if (prev != task) {
                int temp_ans = a[i][task] + ninja(i - 1, task, a);
                ans = Math.max(temp_ans, ans);
            }
        }
        return ans;
    }

    //DP - 6
    //note - change all type of method nonAdjBottomUp to long
    //otherwise , it will fail some case
    public static long houseRobber(int[] valueInHouse) {
        int n=valueInHouse.length;
        if(n==1) return valueInHouse[n-1];
        int []a1=Arrays.copyOfRange(valueInHouse, 0, n-1);
        int []a2=Arrays.copyOfRange(valueInHouse, 1, n);

        return Math.max(nonAdjBottomUp(a1), nonAdjBottomUp(a2));
    }

    private static int nonAdjBottomUp(int[] a) {
        int prev1 = 0; int prev2 = 0;
        for (int i = 0; i < a.length; i++) {
            int take = a[i];if (i > 1) take += prev2;
            int not_take = 0; if (i > 0) not_take += prev1;
            int cur = Math.max(take, not_take);
            prev2 = prev1;
            prev1 = cur;
        }
        return prev1;
    }

    private static int nonAdjBottomUp(int n, int[] a, int[] dp) {

        for (int i = 0; i <=n ; i++) {
            int take = a[i] ; if(i>1) take+= nonAdjBottomUp(i - 2, a, dp);
            int not_take = 0 ; if(i>1) not_take+= nonAdjBottomUp(i - 1, a, dp);
             dp[i] = Math.max(take, not_take);
        }
        return dp[n];
    }
    private static int nonAdj1(int i, int[] a, int[] dp) {
        if (i < 0) return 0;
        if (dp[i] != -1) return dp[i];
        int take = a[i] + nonAdj1(i - 2, a, dp);
        int not_take = 0 + nonAdj1(i - 1, a, dp);

        return dp[i] = Math.max(take, not_take);

    }

    //video - 5
    private static int nonAdj(int i, int[] a) {
        if (i < 0) return 0;
        int take = a[i] + nonAdj(i - 2, a);
        int not_take = 0 + nonAdj(i - 1, a);

        return Math.max(take, not_take);

    }

    //video - 4
    private static int frogKStep(int i, int[] a, int K) {
        if (i == 0) return 0;
        int min_energy = (int) 1e7;
        for (int jump = 1; jump <= K; jump++) {
            if (i - jump >= 0) {
                int kthEnergy = frog(i - jump, a) + Math.abs(a[i] - a[i - jump]);
                min_energy = Math.min(min_energy, kthEnergy);
            }
        }
        return min_energy;
    }

    private static int frogBottomUpOptimized(int[] a) {

        int prev = 0, prev2 = 0, cur;
        for (int i = 1; i < a.length; i++) {
            int left = prev + Math.abs(a[i] - a[i - 1]);
            int right = (int) 1e9;
            if (i > 1) right = prev2 + Math.abs(a[i] - a[i - 2]);
            cur = Math.min(left, right);
            prev2 = prev;
            prev = cur;
        }
        return prev;
    }

    private static int frogBottomUp(int n1, int[] a, int[] dp) {
        dp[0]= 0; //base case
        for (int n = 1; n <=n1 ; n++) {
            int left = dp[n - 1] + Math.abs(a[n] - a[n - 1]);
            int right = (int) 1e9;
            if (n > 1) right = dp[n - 2] + Math.abs(a[n] - a[n - 2]);
            dp[n] = Math.min(left, right);
        }

        return dp[n1];
    }
    private static int frog(int n, int[] a, int[] dp) {
        if (n == 0) return 0;
        if (dp[n] != -1) return dp[n];
        int left = frog(n - 1, a, dp) + Math.abs(a[n] - a[n - 1]);
        int right = (int) 1e9;
        if (n > 1) right = frog(n - 2, a, dp) + Math.abs(a[n] - a[n - 2]);
        return dp[n] = Math.min(left, right);
    }

    //dp-3 usnig dp
    //fn description - energy required to go from index i to 0
    private static int frog(int i, int[] a) {
        if (i == 0) return 0;
        int left = frog(i - 1, a) + Math.abs(a[i] - a[i - 1]);
        int right = (int) 1e9;
        if (i > 1) right = frog(i - 2, a) + Math.abs(a[i] - a[i - 2]);
        return Math.min(left, right);
    }

    //dp -2
    public int climbStairs(int n) {
        if (n == 0) return 1;
        if (n < 0) return 0;
        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    //video-1 dp
    //bottom up
    public static int fibBottomUP(int k, int dp[]) {

        dp[0] = 0;
        dp[1] = 1;
        for (int n = 2; n <= k; n++) {
            dp[n] = dp[n - 1] + dp[n - 2];
        }
        return dp[k];
    }

    //video-1 dp
    public static int fib(int n, int dp[]) {

        if (n <= 1) return n;
        if (dp[n] != -1) return dp[n];
        return dp[n] = fib(n - 1) + fib(n - 2);
    }

    //video-1 using recursion
    public static int fib(int n) {

        if (n <= 1) return n;
        return fib(n - 1) + fib(n - 2);

    }

}