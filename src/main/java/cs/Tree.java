package cs;

import java.util.*;

public class Tree {
    static Map<Integer, Integer> map = new HashMap<>();
    static TreeNode temp;

    public static void main(String[] args) {
        TreeNode t = new TreeNode(10);
        t.left = new TreeNode(4);
        t.right = new TreeNode(15);
        t.left.left = new TreeNode(1);
        t.right.left = new TreeNode(14);
        t.right.right = new TreeNode(19);
        t.right.right.right = new TreeNode(20);
        preInPostTraversalIterative(t);

    }
    static class Pair {
        TreeNode node;
        int val;

        public Pair(TreeNode root, int val) {
            this.node = root;
            this.val = val;
        }

        public TreeNode getNode() {
            return node;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }
    }

    private static void preInPostTraversalIterative(TreeNode root) {
        List<Integer> preOrder = new ArrayList<>();
        List<Integer> inOrder = new ArrayList<>();
        List<Integer> postOrder = new ArrayList<>();
        Stack<Pair> stack = new Stack<>();
        stack.add(new Pair(root, 1));
        while (!stack.isEmpty()) {
            Pair pair = stack.peek();
            if (pair.getVal() == 1) {
                preOrder.add(pair.getNode().val);
                pair.setVal(2);
                if (pair.getNode().left != null)
                    stack.add(new Pair(pair.getNode().left, 1));
            } else if (pair.getVal() == 2) {
                inOrder.add(pair.getNode().val);
                pair.setVal(3);
                if (pair.getNode().right != null)
                    stack.add(new Pair(pair.getNode().right, 1));
            } else {
                postOrder.add(pair.getNode().val);
                stack.pop();
            }
        }

        System.out.println("preOrder:" + preOrder);
        System.out.println("inOrder:" + inOrder);
        System.out.println("postOrder:" + postOrder);

    }


    private static void postOrderIterativeUsing1Stack(TreeNode root) {
        Stack<TreeNode> st = new Stack<>();
        TreeNode cur = root;
        TreeNode temp;
        while (cur != null || !st.isEmpty()) {
            if (cur != null) {
                st.push(cur);
                cur = cur.left;
            } else {
                temp = st.peek().right;
                if (temp == null) {
                    temp = st.pop();
                    System.out.print(temp.val + " ");
                    while (!st.isEmpty() && temp == st.peek().right) {
                        temp = st.pop();
                        System.out.print(temp.val + " ");
                    }
                } else {
                    cur = temp;
                }

            }
        }

    }

    //1 4 10 14 15 19 20
    //preOrder -> NLR ->NRL(for stack to do)
    private static void preOrderIterative(TreeNode t) {
        Stack<TreeNode> stack = new Stack<>();
        stack.add(t);
        while (!stack.isEmpty()) {
            TreeNode element = stack.pop();
            System.out.print(element.val + " ");
            if (element.right != null) stack.add(element.right);
            if (element.left != null) stack.add(element.left);
        }
    }

    private static void inOrderIterative(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while (true) {
            if (node != null) {
                stack.add(node);
                node = node.left;
            } else {
                if (stack.isEmpty()) break;
                node = stack.pop();
                System.out.print(node.val + " ");
                node = node.right;
            }
        }
    }

    //LRN
    private static void postOrderIterative(TreeNode root) {
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.add(root);
        while (!stack1.isEmpty()) {
            TreeNode top = stack1.pop();
            stack2.add(top);
            if (top.left != null) stack1.add(top.left);
            if (top.right != null) stack1.add(top.right);
        }
        while (!stack2.isEmpty()) System.out.print(stack2.pop().val + " ");

    }

    private static void stackTraverSal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        while (!stack.isEmpty() || current != null) {

            while (current != null) {
                stack.add(current);
                current = current.left;
            }

            current = stack.pop();
            System.out.print(current.val + " ");
            current = current.right;
        }
    }

    private static TreeNode treeFromPostOrderInorder(int[] in, Integer inStart, Integer inEnd, int[] po, int poStart, int poEnd, Map<Integer, Integer> map) {
        if (inStart == inEnd) return new TreeNode(in[inEnd]);
        Integer index = map.get(inEnd);
        TreeNode root = new TreeNode(in[inEnd + 1]);
        if (index != null) {
            root.left = treeFromPostOrderInorder(in, inStart, inEnd, po, poStart, inEnd, map);
            root.right = treeFromPostOrderInorder(in, inEnd + 1, poEnd, po, index, poEnd - 1, map);
        }
        return root;
    }

    private static Integer findIndex(int[] in, int root_ele) {
        for (int i = 0; i < in.length; i++) {
            if (in[i] == root_ele) return i;
        }
        return null;
    }

    private static boolean isBST(int min, TreeNode TreeNode, int max) {
        if (TreeNode == null) return true;
        if (TreeNode.val > max || TreeNode.val < min) return false;
        return isBST(min, TreeNode.left, TreeNode.val) && isBST(TreeNode.val, TreeNode.right, max);
    }

    private static void inorder(TreeNode t) {
        if (t == null) return;
        inorder(t.left);
        System.out.print(t.val + " ");
        inorder(t.right);
    }


    private static void invert(TreeNode t) {
        if (t == null) return;
        temp = t.left;
        t.left = t.right;
        t.right = temp;
        invert(t.left);
        invert(t.right);
    }

    private static boolean levelOrderTraversal(TreeNode root) {

        Queue<TreeNode> q = new ArrayDeque<>();
        List<List<Integer>> res = new ArrayList<>();
        q.add(root);
        TreeNode temp;
        List<Integer> list;
        while (!q.isEmpty()) {
            list = new ArrayList<>();
            int len = q.size();
            for (int i = 0; i < len; i++) {
                temp = q.poll();
                if (temp.left != null) q.add(temp.left);
                if (temp.right != null) q.add(temp.right);
                list.add(temp.val);
            }
            res.add(list);
        }
        return false;
    }

    private static int depth(TreeNode t) {
        if (t == null) return 0;

        int l = 0, r = 0;
        if (t.left != null)
            l = 1 + depth(t.left);
        if (t.right != null)
            r = 1 + depth(t.right);

        return Math.max(l, r);

    }

    private static void print(TreeNode t) {
        if (t == null) return;
        print(t.left);
        print(t.right);
        System.out.println(t.val);

    }
}

class TreeNode {

    TreeNode left;
    int val;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
        left = right = null;
    }

    @Override
    public String toString() {
        return "Tree{" +
                "data=" + val +
                '}';
    }
}
