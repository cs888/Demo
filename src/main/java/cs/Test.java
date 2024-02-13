package cs;

import java.util.*;

public class Test {
    public static void main(String[] args) {


        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            adj.add(i, new ArrayList<>());
        }

        adj.get(0).addAll(Arrays.asList(1, 3));
        adj.get(1).addAll(Arrays.asList(0, 2));
        adj.get(2).addAll(Arrays.asList(1, 0, 3));
        adj.get(3).addAll(Arrays.asList(0, 2, 4));
        adj.get(4).addAll(Arrays.asList(2, 3));

    }

    public static String largestNumber(int[] nums) {
        String[] strings = Arrays.stream(nums).boxed().map(x -> x.toString()).toArray(String[]::new);

        Arrays.sort(strings, (o1, o2) -> (o2 + o1).compareTo(o1 + o2));
        System.out.println(Arrays.deepToString(strings));

        StringBuffer sf = new StringBuffer();
        for (String s : strings) {
            if (sf.length() == 1 && (sf.charAt(0) == '0') && (s.charAt(0) == '0')) continue;
            sf.append(s);
        }
        return sf.toString();
    }

}
class Employee {
    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    String name;

    public Employee(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                '}';
    }
}

class NodeVal {
    TreeNode node;
    int n;

    public NodeVal(TreeNode node, int n) {
        this.node = node;
        this.n = n;
    }
}