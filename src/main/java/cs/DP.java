package cs;

import java.util.*;

public class DP {
    public static void main(String[] args) {
        String s1 = "brute";
        String t1 = "groot";
        List<Integer> temp = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();

        int orig_cuts[] = {7, 1, 8};
        // Arrays.sort(orig_cuts);
        int[] cuts = new int[orig_cuts.length + 2];
        System.arraycopy(orig_cuts, 0, cuts, 1, orig_cuts.length);
        //assign last element to stick lenth
        cuts[0] = 1;
        cuts[cuts.length - 1] = 1;
        int[][] dp = new int[cuts.length][cuts.length];
        char[] a = "F|T^F".toCharArray();
        String s = "APPLE";
        int hi[] = {2, 1, 5, 6, 2, 3, 1};
        int i = histogramAreaMaxOptimized(hi);
        System.out.println(i);
    }

    private static int histogramAreaMaxOptimized(int[] a) {
        int maxArea = Integer.MIN_VALUE;
        //stack of index
        Stack<Integer> stack = new Stack<>();
        int ls = 0, rs = 0;
        for (int i = 0; i < a.length; i++) {
            if (!stack.isEmpty() && a[stack.peek()] > a[i]) {
                while (!stack.isEmpty() && a[stack.peek()] > a[i]) {
                    Integer currentIndexHeight = a[stack.pop()];
                    rs = i;
                    ls = stack.isEmpty() ? 0 : stack.peek();
                    int breadth = rs - ls - 1;
                    maxArea = Math.max(maxArea, (breadth == 0 ? 1 : breadth) * currentIndexHeight);
                }
            }
            stack.add(i);
        }
        return maxArea;
    }

    private static int histogramAreaMax(int[] a) {
        int maxArea = Integer.MIN_VALUE;
        //stack of index
        Stack<Integer> stack = new Stack<>();
        int[] lb = new int[a.length];
        for (int i = 0; i < a.length; i++) {

            while (!stack.isEmpty() && a[stack.peek()] >= a[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                lb[i] = stack.peek() + 1;
            }
            stack.add(i);
        }
        int[] rb = new int[a.length];
        //  rb[a.length-1]=a.length-1;
        for (int i = a.length - 1; i >= 0; i--) {

            while (!stack.isEmpty() && a[stack.peek()] >= a[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                rb[i] = stack.peek() - 1;
            }
            if (stack.isEmpty()) {
                rb[i] = a.length - 1;
            }
            stack.add(i);
        }

        //calculateAreaa
        for (int i = 0; i < a.length; i++) {
            maxArea = Math.max(maxArea, (rb[i] - lb[i] + 1) * a[i]);
        }

        System.out.println(maxArea);
        return maxArea;
    }

    private static int leftIndex(int start, int[] hi) {
        int temp = start - 1;
        while (temp >= 0) {
            if (hi[temp] < hi[start])
                return temp + 1;
            temp--;
        }
        return 0;
    }

    private static int rightIndex(int start, int[] hi) {

        int temp = start + 1;
        while (temp < hi.length) {
            if (hi[temp] < hi[start])
                return temp - 1;
            temp++;
        }
        return hi.length - 1;
    }

    private static int partitionArrayMaxSum(int i, int[] chars, int k) {
        if (i == chars.length) return 0;
        int maxSum = Integer.MIN_VALUE;
        int len = 0;
        int maxi = Integer.MIN_VALUE;
        for (int j = i; j < Math.min(j + k, chars.length); j++) {
            len++;
            maxi = Math.max(maxi, chars[j]);
            int jCut = len * maxi + partitionArrayMaxSum(j + 1, chars, k);
            maxSum = Math.max(maxSum, jCut);
        }
        return maxSum;
    }

    private static int pallindromePartitionMinCut(int i, char[] chars) {
        if (i == chars.length) return 0;
        int minCut = Integer.MAX_VALUE;
        for (int j = i; j < chars.length; j++) {
            if (isPallindrome(i, j, chars)) {
                int jCut = 1 + pallindromePartitionMinCut(j + 1, chars);
                minCut = Math.min(minCut, jCut);
            }
        }
        //-1 to ignore last cutp
        return minCut - 1;
    }

    private static int evaluateToTrue(int i, int j, char[] a, int requireTrue) {
        if (i > j) return 0;
        if (i == j) {
            if (requireTrue == 1) return a[i] == 'T' ? 1 : 0;
                //false
            else return a[i] == 'F' ? 1 : 0;
        }

        int count = 0;
        for (int ind = i + 1; ind <= j - 1; ind += 2) {

            int lt = evaluateToTrue(i, ind - 1, a, 1);
            int lf = evaluateToTrue(i, ind - 1, a, 0);
            int rt = evaluateToTrue(ind + 1, j, a, 1);
            int rf = evaluateToTrue(ind + 1, j, a, 0);
            if (a[ind] == '&') {
                if (requireTrue == 1) count += (lt & rt);
                else count += (lt * rf) + (rt * lf) + (lf * rf);
            } else if (a[ind] == '|') {
                if (requireTrue == 1) count += (lt * rf) + (rt * lf) + (lt * rt);
                else count += (lf * rf);
            } else if (a[ind] == '^') {
                if (requireTrue == 1) count += (lt * rf) + (rt * lf);
                else count += (lt * rt) + (lf * rf);
            }
        }

        return count;
    }

    private static int burstBallonBottomUP(int ii, int n, int[] cuts) {
        int[][] dp = new int[cuts.length][cuts.length];
        for (int i = n - 2; i >= 1; i--) {
            for (int j = 1; j <= n - 2; j++) {
                if (i > j) continue;
                int cost = Integer.MIN_VALUE;
                for (int ind = i; ind <= j; ind++) {
                    int iCost = cuts[i - 1] * cuts[ind] * cuts[j + 1] + dp[i][ind - 1] + dp[ind + 1][j];
                    cost = Math.max(cost, iCost);
                }
                dp[i][j] = cost;
            }
        }
        return dp[1][n - 2];
    }

    private static int burstBallon(int i, int j, int[] cuts) {
        if (i > j) return 0;
        int cost = Integer.MIN_VALUE;
        for (int ind = i; ind <= j; ind++) {
            int iCost = cuts[i - 1] * cuts[ind] * cuts[j + 1] + burstBallon(i, ind - 1, cuts) + burstBallon(ind + 1, j, cuts);
            cost = Math.max(cost, iCost);
        }
        return cost;
    }

    private static int minCostToCutStickBottomUP(int ii, int n, int[] cuts) {
        int[][] dp = new int[cuts.length][cuts.length];
        for (int i = n - 2; i >= 1; i--) {
            for (int j = 1; j <= n - 2; j++) {
                if (i > j) continue;
                int cost = Integer.MAX_VALUE;
                for (int ind = i; ind <= j; ind++) {
                    int iCost = cuts[j + 1] - cuts[i - 1] + dp[i][ind - 1] + dp[ind + 1][j];
                    cost = Math.min(cost, iCost);
                }
                dp[i][j] = cost;
            }
        }
        return dp[1][n - 2];
    }

    private static int minCostToCutStick(int i, int j, int[] cuts) {
        if (i > j) return 0;
        int cost = Integer.MAX_VALUE;
        for (int ind = i; ind <= j; ind++) {
            int iCost = cuts[j + 1] - cuts[i - 1] + minCostToCutStick(i, ind - 1, cuts) + minCostToCutStick(ind + 1, j, cuts);
            cost = Math.min(cost, iCost);
        }
        return cost;
    }

    private static int mmBottomUP(int[] a) {
        int n = a.length;
        int[][] dp = new int[a.length][a.length];
        int minSteps = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 1; i--) {
            for (int j = i + 1; j <= n - 1; j++) {

                int kthStep;
                for (int k = i; k < j; k++) {
                    int mulValue = a[i - 1] * a[k] * a[j];
                    kthStep = mulValue + dp[i][k] + dp[k + 1][j];
                    //update ans
                    if (kthStep < minSteps) minSteps = kthStep;
                }
                dp[i][j] = minSteps;
            }
        }
        return dp[1][n - 1];
    }

    private static int mm(int i, int j, int[] a) {
        if (i == j) return 0;
        int minSteps = Integer.MAX_VALUE;
        int kthStep;
        for (int k = i; k < j; k++) {
            int mulValue = a[i - 1] * a[k] * a[j];
            kthStep = mulValue + mm(i, k, a) + mm(k + 1, j, a);
            //update ans
            if (kthStep < minSteps) minSteps = kthStep;
        }

        return minSteps;
    }

    //not trival solutions
    private static int countNumberOfLIS(int[] a) {
        int dp[] = new int[a.length + 1];
        Arrays.fill(dp, 1);

        int ct[] = new int[a.length + 1];
        Arrays.fill(ct, 1);
        int maxct = 0;
        for (int i = 1; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                if (a[i] > a[j] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    ct[i] = ct[j];
                } else if (a[i] > a[j] && dp[j] + 1 == dp[i])
                    ct[i] += ct[j];

                maxct = Math.max(maxct, ct[i]);
            }
        }
        return maxct;

    }


    private static List<Integer> printListAnotherWay(int[] a, List<Integer> ans) {
        int dp[] = new int[a.length + 1];
        Arrays.fill(dp, 1);
        int backTrack[] = new int[dp.length];

        for (int i = 0; i < backTrack.length; i++) {
            backTrack[i] = i;
        }

        int maxValue = 0;
        int maxInd = 0;
        //{5, 4, 11, 1, 16, 8}
        for (int i = 1; i < a.length; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (a[prev] < a[i] && dp[prev] + 1 > dp[i]) {
                    dp[i] = dp[prev] + 1;
                    backTrack[i] = prev;
                    //update index & maxValue
                    if (maxValue < dp[i]) {
                        maxInd = i;
                        maxValue = dp[i];
                    }
                }
            }
        }
        //  System.out.println(maxValue+" ,"+maxInd);
        //    System.out.println(Arrays.toString(dp));
        //   System.out.println(Arrays.toString(backTrack));

        while (maxValue > 0) {
            ans.add(a[maxInd]);
            maxInd = backTrack[maxInd];
            maxValue--;
        }
        Collections.reverse(ans);
        return ans;
    }


    private static int longestBiotonic(int[] a) {
        int a1[] = lisFrom0(a);
        int a2[] = lisFromn(a);

        int max = 0;
        for (int i = 0; i < a.length; i++) {
            a1[i] = a1[i] + a2[i] - 1;
            max = Math.max(max, a1[i]);
        }

        return max;
    }

    private static int[] lisFromn(int[] a) {

        int dp[] = new int[a.length + 1];
        Arrays.fill(dp, 1);

        for (int i = a.length - 1; i >= 0; i--) {
            for (int prev = a.length - 1; prev > i; prev--) {
                if (a[i] > a[prev] && dp[prev] + 1 > dp[i]) {
                    dp[i] = dp[prev] + 1;
                }
            }
        }
        return dp;
    }

    private static int[] lisFrom0(int[] a) {

        int dp[] = new int[a.length + 1];
        Arrays.fill(dp, 1);

        for (int i = 1; i < a.length; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (a[i] > a[prev] && dp[prev] + 1 > dp[i]) {
                    dp[i] = dp[prev] + 1;
                }
            }
        }
        return dp;
    }

    private static int longestStringChain(String[] s) {
        Arrays.sort(s);
        int dp[] = new int[s.length + 1];
        Arrays.fill(dp, 1);

        int maxValue = 0;
        for (int i = 1; i < s.length; i++) {
            for (int j = 0; j < i; j++) {
                if (isDiffOf1(s[i].toCharArray(), s[j].toCharArray()) && (dp[j] + 1 > dp[i])) {
                    dp[i] = dp[j] + 1;
                    maxValue = Math.max(maxValue, dp[i]);
                }
            }
        }

        return maxValue;
    }

    private static boolean isDiffOf1(char[] s, char[] t) {
        if (s.length != t.length + 1) return false;
        int iPointer = 0;
        int jPointer = 0;
        while (iPointer < s.length) {
            if (jPointer < t.length && s[iPointer] == t[jPointer]) {
                iPointer++;
                jPointer++;
            } else iPointer++;
        }
        if (iPointer == s.length && jPointer == t.length) return true;
        return false;
    }

    private static List<Integer> printListForDivisionSubset(int[] a, List<Integer> ans) {
        Arrays.sort(a);
        int dp[] = new int[a.length + 1];
        Arrays.fill(dp, 1);
        int backTrack[] = new int[dp.length];

        for (int i = 0; i < backTrack.length; i++) {
            backTrack[i] = i;
        }

        int maxValue = 0;
        int maxInd = 0;
        //{5, 4, 11, 1, 16, 8}
        for (int i = 1; i < a.length; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (a[i] % a[prev] == 0 && dp[prev] + 1 > dp[i]) {
                    dp[i] = dp[prev] + 1;
                    backTrack[i] = prev;
                    //update index & maxValue
                    if (maxValue < dp[i]) {
                        maxInd = i;
                        maxValue = dp[i];
                    }
                }
            }
        }
        //  System.out.println(maxValue+" ,"+maxInd);
        //    System.out.println(Arrays.toString(dp));
        //   System.out.println(Arrays.toString(backTrack));

        while (maxValue > 0) {
            ans.add(a[maxInd]);
            maxInd = backTrack[maxInd];
            maxValue--;
        }
        Collections.reverse(ans);
        System.out.println(ans);
        return ans;
    }

    private static int printLIS(int i, int prev, int[] a, List<Integer> temp, List<List<Integer>> ans) {
        if (i >= a.length) {
            if (ans.size() == 0 || temp.size() > ans.get(0).size()) {
                if (ans.size() > 0) ans.remove(0);
                ans.add(0, new ArrayList<>(temp));
            }
            return 0;
        }
        int take = 0;
        if (prev == -1 || a[i] % prev == 0 || prev % a[i] == 0) {
            temp.add(a[i]);
            take = 1 + printLIS(i + 1, a[i], a, temp, ans);
            temp.remove(temp.size() - 1);
        }

        int nottake = 0 + printLIS(i + 1, prev, a, temp, ans);

        return Math.max(take, nottake);

    }

    private static int stockBuyWithNumberOfTxnLimit(int i, int[] price, boolean canBuy, int numberOfTxn) {
        if (i >= price.length) return 0;
        if (numberOfTxn <= 0) return 0;

        if (canBuy) {
            //buy or not buy
            return Math.max(-price[i] + stockBuyWithNumberOfTxnLimit(i + 1, price, false, numberOfTxn),
                    0 + stockBuyWithNumberOfTxnLimit(i + 1, price, canBuy, numberOfTxn));
        } else {
            //sell or not sell
            return Math.max(price[i] + stockBuyWithNumberOfTxnLimit(i + 1, price, true, numberOfTxn - 1),
                    0 + stockBuyWithNumberOfTxnLimit(i + 1, price, canBuy, numberOfTxn));
        }

    }

    private static void printSuperSequence(char[] s, char[] t, int[][] dp) {
        int i = dp.length - 1;
        int j = dp[0].length - 1;
        StringBuffer ans = new StringBuffer();
        while (i >= 1 && j >= 1) {
            if (s[i] == t[j]) {
                ans.append(s[i]);
                i--;
                j--;
            } else {
                if (dp[i - 1][j] >= dp[i][j]) {
                    ans.append(s[i]);
                    i--;
                } else {
                    ans.append(t[j]);
                    j--;
                }
            }
        }
        while (i >= 0) {
            ans.append(s[i]);
            i--;
        }

        while (j >= 0) {
            ans.append(t[j]);
            j--;
        }

        System.out.println(ans.reverse());
    }

    private static void longestSubString(char[] s, char[] t, int[][] dp) {
        int len = 0;
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < t.length; j++) {
                if (s[i] == t[j]) dp[i + 1][j + 1] = 1 + dp[i][j];
                len = Math.max(len, dp[i][j]);
            }
        }
        System.out.println(len);

    }

    private static void printLcsString(char[] s, char[] t, int[][] dp) {
        int i = dp.length - 1;
        int j = dp[0].length - 1;
        StringBuffer ans = new StringBuffer();
        while (i >= 0 && j >= 0) {
            if (s[i] == t[j]) {
                ans.append(s[i]);
                i--;
                j--;
            } else {
                if (dp[i - 1][j] >= dp[i][j]) {
                    i--;
                } else j--;
            }
        }
        System.out.println(ans.reverse());
    }

    private static int lcsOf(int i, int j, char[] s, char[] t, int[][] dp) {
        if (i < 0 || j < 0) return 0;

        if (dp[i][i] != 0) return dp[i][j];

        if (s[i] == t[j])
            return dp[i][j] = 1 + lcsOf(i - 1, j - 1, s, t, dp);

        else
            return dp[i][j] = Math.max(lcsOf(i - 1, j, s, t, dp), lcsOf(i, j - 1, s, t, dp));

    }

    private static int rodCutting(int i, int[] a, int len) {
        if (i < 0) return 0;

        int nottake = rodCutting(i - 1, a, len);
        int take = Integer.MIN_VALUE;
        if (len - i - 1 >= 0)
            take = a[i] + rodCutting(i, a, len - i - 1);

        return Math.max(take, nottake);

    }

    private static int coins(int i, int[] coins, int sum) {
        if (sum == 0) return 1;

        if (i < 0) return 0;

        int take = 0;
        if (sum - coins[i] >= 0)
            take = coins(i, coins, sum - coins[i]);

        int nottake = 0 + coins(i - 1, coins, sum);

        return take + nottake;
    }

    private static int bagKnapSack(int[] wt, int[] val, int maxBagWeight) {

        int prev[] = new int[maxBagWeight + 1];
        int cur[] = new int[maxBagWeight + 1];
        for (int i = 0; i < val.length; i++) {
            for (int W = 1; W <= maxBagWeight; W++) {
                int take = Integer.MIN_VALUE;
                if (W - wt[i] >= 0) {
                    take = val[i];
                    if (i - 1 >= 0) take += prev[W - wt[i]];
                }

                int nottake = 0;
                if (i - 1 >= 0)
                    nottake = prev[W];

                cur[W] = Math.max(take, nottake);
            }
            prev = cur;
        }
        return prev[maxBagWeight];

    }

    private static int bagKnapSackRec(int i, int[] wt, int[] val, int W) {
        if (i < 0) return 0;
        int take = Integer.MIN_VALUE;
        if (W - wt[i] >= 0)
            take = val[i] + bagKnapSackRec(i, wt, val, W - wt[i]);

        int nottake = bagKnapSackRec(i - 1, wt, val, W);

        return Math.max(take, nottake);
    }

    private static int noOfWaystoFormTarget(int i, int target, int[] a) {
        if (target == 0) return 1;
        if (i < 0) return 0;

        int take = 0;
        if (target - a[i] >= 0)
            take = noOfWaystoFormTarget(i - 1, target - a[i], a);
        //not need to remove since parametrized call & it will be auto deleted
        int nottake = noOfWaystoFormTarget(i - 1, target, a);

        return take + nottake;

    }

    private static boolean fTargetTD(int ind, int targetRequired, int[] a, boolean[][] dp) {

        for (int i1 = 0; i1 < a.length; i1++) {
            dp[i1][0] = true;
        }
        boolean[] prev = new boolean[a.length + 1];
        boolean[] cur = new boolean[a.length + 1];

        for (int i = 1; i < a.length; i++) {
            for (int target = 0; target <= targetRequired; target++) {
                boolean take = false;
                if (target - a[i] >= 0)
                    take = prev[target - a[i]];

                boolean not_take = prev[target];
                cur[target] = take || not_take;
            }
            prev = cur;
        }
        return dp[0][0];
    }

    private static boolean targetSum(int i, int target, int[] a) {
        if (target == 0) return true;
        if (i >= a.length) return false;
        boolean take = false;
        if (target - a[i] >= 0)
            take = targetSum(i - 1, target - a[i], a);

        boolean not_take = targetSum(i - 1, target - a[i], a);

        return take || not_take;

    }

    private static int anyStartingPoint(int row, int col, int n, int[][] a, int[][] dp) {

        if (col < 0 || col >= a[0].length) return (int) -1e8;
        if (row == n) return a[n][col];
        if (dp[row][col] != -1) return dp[row][col];
        int left = a[row][col] + anyStartingPoint(row + 1, col - 1, n, a, dp);
        int down = a[row][col] + anyStartingPoint(row + 1, col, n, a, dp);
        int right = a[row][col] + anyStartingPoint(row + 1, col + 1, n, a, dp);

        return dp[row][col] = Math.max(Math.max(left, down), right);
    }

    private static int ninjaTrainingBottomUp(int[][] a, int days, int[][] dp) {

        int[] prev = new int[a[0].length + 1];
        int[] cur = new int[a[0].length + 1];
        for (int day = 0; day < days; day++) {
            for (int prevTask = 0; prevTask < 4; prevTask++) {
                int maxi = Integer.MIN_VALUE;
                for (int task = 0; task < 3; task++) {
                    int steps = Integer.MIN_VALUE;
                    if (task != prevTask) {
                        steps = a[day][task];
                        if (day - 1 >= 0) steps += dp[day - 1][task];
                    }
                    maxi = Math.max(maxi, steps);
                }
                dp[day][prevTask] = maxi;
            }
        }
        return dp[days - 1][2];
    }


    private static int ninjaTraining(int day, int prevDayTask, int[][] a, int[][] dp) {

        if (day < 0) return 0;
        if (dp[day][prevDayTask] != -1) return dp[day][prevDayTask];
        int maxi = Integer.MIN_VALUE;
        for (int task = 0; task < 3; task++) {
            int steps = Integer.MIN_VALUE;
            if (task != prevDayTask) {
                steps = a[day][task] + ninjaTraining(day - 1, task, a, dp);
            }
            maxi = Math.max(maxi, steps);
        }
        return dp[day][prevDayTask] = maxi;
    }

    //TODO: need to fix this as result is not expected
    private static int frogJumpBottomUP1(int[] cost, int n, int[] dp, int K) {
        dp[n - 1] = 0;

        int minCost = Integer.MAX_VALUE;
        for (int k = 1; k <= K; k++) {
            int minStep = (int) 1e8;
            for (int j = n - 2; j >= 0; j--) {
                if (j + k < n) {
                    minStep = Math.abs(cost[j + k] - cost[j]) + dp[j + k];
                }
                minCost = Math.min(minCost, minStep);
            }
            dp[k] = minCost;
        }
        return minCost;
    }


    private static int frogJumpBottomUP(int[] cost, int n, int[] dp) {
        dp[n - 1] = 0;
        int current = 0, prev1 = 0, prev2 = 0;

        for (int i = n - 2; i >= 0; i--) {
            int left = (int) 1e8;

            if (i + 1 < n) {
                left = Math.abs(cost[i + 1] - cost[i]) + prev1;
            }

            int right = (int) 1e8;

            if (i + 2 < n) {
                right = Math.abs(cost[i + 2] - cost[i]) + prev2;
            }

            current = Math.min(left, right);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;

    }

    private static int frogJump(int i, int n, int[] cost, int[] dp, int K) {
        if (i == n - 1) return 0;

        if (dp[i] != -1) return dp[i];

        int left = (int) 1e8;
        int ans = Integer.MAX_VALUE;
        for (int k = 1; k <= K; k++) {
            if (i + k < n) {
                left = Math.abs(cost[i + k] - cost[i]) +
                        frogJump(i + k, n, cost, dp, k);
            }
            ans = Math.min(left, ans);
        }

        return ans;
    }

    public static int rainWaterTrap(int[] h) {
        int res = 0;
        int l = 0;
        int r = h.length - 1;
        int lmax = 0;
        int rmax = 0;
        while (r > l) {
            lmax = Math.max(lmax, h[l]);
            rmax = Math.max(lmax, h[r]);
            if (lmax < rmax) {
                res += lmax - h[l];
                l++;
            } else {
                res += rmax - h[r];
                r--;
            }
        }
        return res;
    }

    private static void permutationsOfArray(int i, int[] a) {
        if (i == a.length - 1) System.out.println(Arrays.toString(a));
        if (i == a.length) return;
        for (int j = i; j < a.length; j++) {
            replace(i, j, a);
            permutationsOfArray(i + 1, a);
            replace(j, i, a);
        }
    }

    static void replace(int i, int j, int a[]) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static int egg_floor_problem(int e, int f) {

        if (e < 1) return 0;
        if (f < 2) return f;

        int min_val = Integer.MAX_VALUE;
        for (int k = 1; k <= f; k++) {
            int broken = egg_floor_problem(e - 1, k - 1);
            int not_broken = egg_floor_problem(e, f - k);
            min_val = Math.min(min_val, 1 + Math.max(broken, not_broken));
        }
        return min_val;
    }

    private static int shortestPathGrid(int i, int j, int[][] a) {
        if (j == 0 && j == 0) return a[i][j];
        if (j < 0 || i < 0) return Integer.MAX_VALUE / 2;
        if (a[i][j] < 0) return Integer.MAX_VALUE / 2;
        int left = a[i][j] + shortestPathGrid(i, j - 1, a);
        int up = a[i][j] + shortestPathGrid(i - 1, j, a);
        return Math.min(left, up);
    }

    private static int pp(int i, char[] c, int[] d) {
        if (i == c.length) return 0;
        if (d[i] != -1) return d[i];
        int mincost = Integer.MAX_VALUE;
        for (int j = i; j < c.length; j++) {
            if (isPallindrome(i, j, c)) {
                int cost = 1 + pp(j + 1, c, d);
                mincost = Math.min(cost, mincost);
            }
        }
        return d[i] = mincost;
    }

    private static boolean isPallindrome(int i, int j, char[] c) {
        while (i < j) {
            if (c[i] != c[j]) return false;
            i++;
            j--;
        }
        return true;
    }

    private static int rod_selling_price(int i, int n, int[] a) {
        if (i < 0) return 0;
        if (n < 1) return 0;
        int not_take = 0 + rod_selling_price(i - 1, n, a);
        int take = 0;
        int rod_len = i + 1;
        if (n - rod_len >= 0)
            take = a[i] + rod_selling_price(i, n - rod_len, a);
        return Math.max(take, not_take);

    }

    private static boolean target_make_possible(int i, int target, int[] a) {
        if (target == 0) return true;
        if (i < 0) return false;
        boolean take = false;
        if (target - a[i] >= 0)
            take = target_make_possible(i, target - a[i], a);
        boolean not_take = target_make_possible(i - 1, target, a);
        return take || not_take;
    }

    private static int lis(int i, int prev_ind, int[] a, int[][] dp) {
        if (i == a.length) return 0;
        int take = 0;
        if (prev_ind == -1 || a[i] > a[prev_ind])
            take = 1 + lis(i + 1, i, a, dp);
        int not_take = 0 + lis(i + 1, prev_ind, a, dp);
        return Math.max(take, not_take);

    }

    private static boolean wildCard(int i, int j, char[] s, char[] t, Boolean[][] dp) {
        if (i == 0 && j > 0) return false;
        if (j == 0 & j == 0) return true;
        if (j == 0 && i > 0) {
            for (int k = 0; k <= i; k++) {
                if (s[k] != '*') return false;
            }
            return true;
        }
        if (dp[i][j] != null) return dp[i][j];
        if (s[i - 1] == t[j - 1] || s[i - 1] == '?')
            return dp[i][j] = wildCard(i - 1, j - 1, s, t, dp);
        if (s[i - 1] == '*')
            return dp[i][j] = wildCard(i - 1, j, s, t, dp) || wildCard(i, j - 1, s, t, dp);
        return false;
    }

    private static int editDistance1(int n, int m, char[] s, char[] t) {

        int prev[] = new int[m + 1];
        int cur[] = new int[m + 1];
        for (int j = 0; j <= m; j++) {
            prev[j] = j;
        }
        System.out.println(Arrays.toString(prev));
        for (int i = 1; i <= n; i++) {
            cur[0] = i;
            for (int j = 1; j <= m; j++) {
                if (s[i - 1] == t[j - 1])
                    cur[j] = prev[j - 1];
                else
                    cur[j] = Math.min(1 + prev[j - 1],
                            Math.min(1 + cur[j - 1], 1 + prev[j]));
            }

            prev = cur;
            cur = new int[m + 1];
            System.out.println(Arrays.toString(prev));

        }

        return prev[m];
    }

    private static int editDistance(int i, int j, char[] s, char[] t, int[][] dp) {
        if (i == 0) return j;
        if (j == 0) return i;
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        if (s[i - 1] == t[j - 1]) return dp[i][j] = 0 + editDistance(i - 1, j - 1, s, t, dp);
        else
            return dp[i][j] = Math.min(1 + editDistance(i - 1, j - 1, s, t, dp),
                    Math.min(1 + editDistance(i, j - 1, s, t, dp), 1 + editDistance(i - 1, j, s, t, dp)));
    }

    private static int distinctSupSeq(int i, int j, char[] s, char[] t, int[][] dp) {

        if (j == 0) return 1;
        if (i == 0) return 0;
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        if (s[i - 1] == t[j - 1]) {
            return dp[i][j] = distinctSupSeq(i - 1, j - 1, s, t, dp) + distinctSupSeq(i - 1, j, s, t, dp);
        } else return dp[i][j] = distinctSupSeq(i - 1, j, s, t, dp);
    }

    private static StringBuffer superString(int n, int m, char[] s, char[] t, int[][] dp) {
        int prev[] = new int[n + 1];
        int cur[] = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s[i - 1] == t[j - 1]) dp[i][j] = 1 + dp[i - 1][j - 1];
                else dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
            }
        }

        //   System.out.printf(Arrays.deepToString(dp));
        int i = n;
        int j = m;
        StringBuffer bf = new StringBuffer();
        while (i > 0 && j > 0) {
            if (s[i - 1] == t[j - 1]) {
                bf.append(s[i - 1]);
                i--;
                j--;

            } else if (dp[i][j - 1] > dp[i - 1][j]) {
                bf.append(t[j - 1]);
                j--;
            } else {
                bf.append(s[i - 1]);
                i--;
            }
        }
        if (i > 0) bf.append(s[i - 1]);
        if (j > 0) bf.append(t[j - 1]);

        return bf.reverse();
    }

    private static int lcsubtring(int n, int m, char[] s, char[] t, int[][] a) {
        int max = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s[i - 1] == t[j - 1]) a[i][j] = 1 + a[i - 1][j - 1];
                else a[i][j] = Math.max(a[i - 1][j], a[i][j - 1]);
            }
        }
        return max;
    }

    private static StringBuffer printLcs(int n, int m, char[] s, char[] t, int[][] a) {
        int prev[] = new int[n + 1];
        int cur[] = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s[i - 1] == t[j - 1]) a[i][j] = 1 + a[i - 1][j - 1];
                else a[i][j] = Math.max(a[i][j - 1], a[i - 1][j]);
            }
        }

        System.out.println(a[n][m]);
        // System.out.printf(Arrays.deepToString(dp));
        int i = n;
        int j = m;
        StringBuffer bf = new StringBuffer();
        while (i > 0 && j > 0) {
            if (s[i - 1] == t[j - 1]) {
                i--;
                m--;
                bf.append(s[i]);
            } else if (a[i][j - 1] > a[i - 1][j]) {
                j--;
            } else {
                i--;
            }
        }
        return bf.reverse();
    }

    private static int lcsBottomUP(int n, int m, char[] s, char[] t) {
        int prev[] = new int[n + 1];
        int cur[] = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s[i - 1] == t[j - 1]) cur[j] = 1 + prev[j - 1];
                else cur[j] = Math.max(cur[j - 1], prev[j]);
            }
            prev = cur;
        }

        // System.out.printf(Arrays.deepToString(dp));
        return prev[n];
    }

    private static int lcs(int i, int j, char[] s, char[] t, int[][] dp) {
        if (i < 0 || j < 0) {
            return 0;
        }

        if (s[i] == t[j]) {
            return dp[i][j] = 1 + lcs(i - 1, j - 1, s, t, dp);
        } else {
            return dp[i][j] = Math.max(
                    lcs(i, j - 1, s, t, dp),
                    lcs(i - 1, j, s, t, dp)
            );
        }
    }
}