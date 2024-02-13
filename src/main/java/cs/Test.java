package cs;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {

        int a[][] = {{4, 30, 3}, {1, 20, 2}, {2, 10, 1}};

        //John.*+hurt
        Pattern pattern = Pattern.compile("\\a*");
        Matcher matcher = pattern.matcher("hi");
        int match = 0;
        while (matcher.find()) {
            match++;
            System.out.println(matcher.group());
        }
        System.out.println("total:" + match);

        Arrays.sort(a, Comparator.comparingInt(n -> n[2]));

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(0, 1);

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            adj.add(i, new ArrayList<>());
        }

        adj.get(0).addAll(Arrays.asList(1, 3));
        adj.get(1).addAll(Arrays.asList(0, 2));
        adj.get(2).addAll(Arrays.asList(1, 3, 4));
        adj.get(3).addAll(Arrays.asList(0, 2, 4));
        adj.get(4).addAll(Arrays.asList(2, 3));

        Queue<Integer> stack = new PriorityQueue<>();
        stack.add(1);
        stack.add(2);
        stack.add(3);

        stack.remove(0);
        List<Integer> collect = stack.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
        int t[] = {1, 2, 4, 5};
        int[] temp = new int[t.length + 2];
        System.arraycopy(t, 0, temp, 1, t.length - 2);
        // System.out.println(Arrays.toString(temp));

    }

    private static List<String> stringSplit() {

        int start = 0;
        int end = 1;
        List<String> ans = new ArrayList<>();
        String s = "'041c1f43-746b-4b1a-9dea-af02216cdfea','00:0c:29:99:bc:4c',0,'VMware, Inc.','Hardware Manufacturer/VMware, Inc.',";
        while (end < s.length() && start < s.length()) {
            if (s.charAt(start) == '\'') {
                end = start + 1;
                while (s.charAt(end) != '\'') {
                    end++;
                }
                String substring = s.substring(start, end + 1);
                ans.add(substring);
                start = end + 2;
            } else if (Character.isDigit(s.charAt(start))) {
                end = start + 1;
                while (s.charAt(end) != ',') {
                    end++;
                }
                String substring = s.substring(start, end);
                ans.add(substring);
                start = end + 1;
            }
        }
        return ans;
    }


    static boolean[] twins(String[] a, String[] b) {
        boolean[] result = new boolean[a.length];

        for (int i = 0; i < a.length; i++) {
            String aVal = a[i].toLowerCase();
            String bVal = b[i].toLowerCase();
            String[] aValArray = aVal.split("");
            String[] bValArray = bVal.split("");

            for (String s : aValArray) {
                for (int index = 0; index < aValArray.length; index++) {
                    if (bValArray[index].equals(s)) { // checking whether the index match or not
                        if ((s.indexOf(s) % 2 == 0 && index % 2 == 0) || // checking even values
                                (s.indexOf(s) % 2 != 0 && index % 2 != 0)) {
                            result[i] = false;
                        } else if ((s.indexOf(s) % 2 == 0 && index % 2 != 0)
                                || (s.indexOf(s) % 2 != 0 && index % 2 == 0)) {
                            result[i] = true;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    private static void dfs(int startNode, List<List<Integer>> adj) {
        Stack<Integer> queue = new Stack<>();
        boolean[] vis = new boolean[adj.size()];
        queue.add(startNode);
        while (!queue.isEmpty()) {
            Integer temp = queue.pop();
            if (!vis[temp]) {
                vis[temp] = true;
                System.out.print(temp + " ");
                for (int neighbour : adj.get(temp)) {
                    if (!vis[neighbour]) {
                        queue.add(neighbour);
                    }
                }
            }
        }
    }
}