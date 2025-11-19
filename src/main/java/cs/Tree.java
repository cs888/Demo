package cs;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tree {
    public static void main(String[] args) {
        TreeNode node = new TreeNode(2);
        node.left = new TreeNode(35);
        node.right = new TreeNode(10);
        node.left.left = new TreeNode(2);
        node.left.right = new TreeNode(3);
        node.right.left = new TreeNode(5);
        node.right.right = new TreeNode(2);
        List<Integer> ans = new ArrayList<>();
        int a[] = {8, 5, 1, 7, 10, 12};
        levelOrder(node);
        System.out.println();
        levelOrder(node);

        int p[] = {8, 5, 1, 7, 10, 12};
        bstFromPreOrder(p, Integer.MAX_VALUE, new int[]{0});


    }

    private static int largestBSTTree(TreeNode node) {
        return largestBSTTreeHelper(node).size;
    }

    static class NodeValue {
        int min;
        int max;
        int size;

        public NodeValue(int size, int min, int max) {
            this.size = size;
            this.min = min;
            this.max = max;

        }

    }

    //TC 0(N)
    private static NodeValue largestBSTTreeHelper(TreeNode root) {
        if (root == null) return new NodeValue(0, Integer.MAX_VALUE, Integer.MIN_VALUE);

        NodeValue left = largestBSTTreeHelper(root.left);
        NodeValue right = largestBSTTreeHelper(root.right);

        // is BST
        if (left.max < root.data && root.data < right.min)
            return new NodeValue(1 + left.size + right.size, Math.max(root.data,right.max), Math.min(root.data, left.min));

        //not BST
        return new NodeValue(Math.max(left.size, right.size), Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    static TreeNode prev = null;

    static TreeNode first = null;
    static TreeNode mid = null;
    static TreeNode last = null;

    //video - 53
    private static void recoverBST(TreeNode node) {

        inOrderRecover(node);
        if (last != null) {
            swap(first, last);
        } else swap(first, mid);

    }

    private static void swap(TreeNode prev, TreeNode last) {
        int t = prev.data;
        prev.data = last.data;
        last.data = t;
    }


    // if last ==null , swap first & mid
    //else swap first,last
    //video - 52
    private static void inOrderRecover(TreeNode node) {
        if (node == null) return;
        inOrderRecover(node.left);
        //violation
        if (prev != null && prev.data > node.data) {
            //if first violation
            if (first == null) {
                first = prev;
                mid = node;
            } else last = node;
        }
        prev = node;
        inOrderRecover(node.right);
    }

    //video - 52
    private static boolean sum2BST(TreeNode node, int target) {
        class BSTIteratorIncrDecr {
            Stack<TreeNode> stack = new Stack<>();
            boolean increasing;

            public BSTIteratorIncrDecr(TreeNode root, boolean increasing) {
                this.increasing = increasing;
                addAllToStack(root);
            }

            private void addAllToStack(TreeNode root) {
                TreeNode temp = root;
                while (temp != null) {
                    stack.add(temp);
                    if (increasing) {
                        temp = temp.left;
                    } else {
                        temp = temp.right;
                    }
                }
            }

            public boolean hasNext() {
                return !stack.isEmpty();
            }

            public int next() {
                TreeNode poppedItem = stack.pop();
                if (increasing && poppedItem.left != null) {
                    addAllToStack(poppedItem.left);
                } else if (!increasing && poppedItem.right != null) {
                    addAllToStack(poppedItem.right);
                }
                return poppedItem.data;
            }
        }
        BSTIteratorIncrDecr l = new BSTIteratorIncrDecr(node, true);
        BSTIteratorIncrDecr r = new BSTIteratorIncrDecr(node, false);

        int lnext = l.next();
        int rnext = r.next();
        while (lnext < rnext) {

            int sum = lnext + rnext;
            if (sum == target) return true;
            else if (sum < target) {
                lnext = l.next();
            } else rnext = r.next();
        }
        return false;
    }

    //Iterator class for IMPL
    class BSTIteratorNextAndHasNext {
        Stack<TreeNode> stack = new Stack<>();

        public BSTIteratorNextAndHasNext(TreeNode root) {
            addAllToStack(root);
        }

        private void addAllToStack(TreeNode root) {
            TreeNode temp = root;
            while (temp != null) {
                stack.add(temp);
                temp = temp.left;
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public int next() {
            TreeNode pop = stack.pop();
            if (pop.right != null) {
                addAllToStack(pop.right);
            }
            return pop.data;
        }
    }

    //video - 49
    private static TreeNode successorBST(TreeNode root, int target) {
        TreeNode sucessor = null;
        TreeNode cur = root;
        while (cur != null) {
            if (cur.data <= target) {
                cur = cur.right;
            } else {
                sucessor = cur;
                cur = cur.left;
            }
        }
        return sucessor;
    }

    //TODO: revise
    //video - 48
    private static TreeNode bstFromPreOrder(int[] input, int bound, int[] i) {
        if (i[0] == input.length || input[i[0]] > bound) return null;
        TreeNode node = new TreeNode(input[i[0]++]);
        node.left = bstFromPreOrder(input, node.data, i);
        node.right = bstFromPreOrder(input, bound, i);
        return node;
    }

    //video - 47
    // TC -> (H)
    //point of split or point of split which is LCA
    private static TreeNode lcaBST(TreeNode node, int p, int q) {
        if (node == null) return null;
        if (p > node.data && q > node.data)
            return lcaBST(node.right, p, q);
        if (p < node.data && q < node.data)
            return lcaBST(node.left, p, q);

        //point of split
        return node;
    }


    //video - 46
    private static boolean validBST(int minValue, TreeNode root, int maxValue) {
        if (root == null) return true;
        if (minValue >= root.data || root.data >= maxValue) return false;
        return validBST(minValue, root.left, root.data) && validBST(root.data, root.right, maxValue);
    }

    //kth smallest
    // kth largest will totalSize(N-k) smallest
    //video - 45
    private static void kthSmallest(TreeNode node, int[] count, int k) {
        if (node == null) return;
        kthSmallest(node.left, count, k);
        count[0]++;
        if (count[0] == k) {
            count[1] = node.data;
            return;
        }
        kthSmallest(node.right, count, k);
    }

    //video - 44
    private static TreeNode deleteNode(TreeNode node, int key) {
        if (node == null) return node;
        //for root Node
        if (node.data == key) return getReplacingNode(node);
        TreeNode cur = node;
        //for other than root node
        while (cur != null) {
            if (key > node.data) {
                if (cur.right != null && cur.right.data == key) {
                    cur.right = getReplacingNode(cur.right);
                } else {
                    cur = cur.right;
                }

            } else {
                if (cur.left != null && cur.left.data == key) {
                    cur.left = getReplacingNode(cur.left);
                } else {
                    cur = cur.left;
                }
            }
        }
        return node;
    }

    private static TreeNode getReplacingNode(TreeNode node) {

        if (node.left == null) return node.right;
        else if (node.right == null) return node.left;
        else {
            TreeNode temp = node.left;
            while (temp.right != null) {
                temp = temp.right;
            }
            temp.right = node.right;
            //attached right of node to down right
            return node.left;
        }

    }

    //video- 43
    private static TreeNode insertBST(TreeNode root, int item) {
        TreeNode cur = root;
        while (cur != null) {
            if (item >= cur.data) {
                if (cur.right != null) cur = cur.right;
                else {
                    cur.right = new TreeNode(item);
                    break;
                }
            } else {
                if (cur.left != null) cur = cur.left;
                else {
                    cur.left = new TreeNode(item);
                    break;
                }
            }
        }
        return root;
    }

    //video - 42
    //or Predecessor
    private static TreeNode floorBST(TreeNode root, int key) {
        if (root == null) return null;
        TreeNode ceil = null;
        while (root != null) {
            if (root.data == key)
                return root;
            if (root.data < key) {
                root = root.right;
                ceil = root;
            } else
                root = root.left;
        }
        return ceil;
    }

    //or Successor
    private static TreeNode ceilBST(TreeNode root, int key) {
        if (root == null) return null;
        TreeNode ceil = null;
        while (root != null) {
            if (root.data == key)
                return root;
            if (root.data < key) root = root.right;
            else {
                root = root.left;
                ceil = root;
            }
        }
        return ceil;
    }

    private static TreeNode bstSearch(TreeNode node, int target) {
        if (node == null) return node;
        if (node.data == target) return node;
        else if (node.data < target) return bstSearch(node.right, target);
        else return bstSearch(node.left, target);
    }

    //1 2 13 4 5
    //video - 39
    //O(n) , space -> O(1)
    private static TreeNode flattenBinaryTreeMorris(TreeNode node) {
        TreeNode cur = node;
        while (cur != null) {
            if (cur.left != null) {
                TreeNode tempPredCessor = cur.left;
                while (tempPredCessor.right != null) {
                    tempPredCessor = tempPredCessor.right;
                }
                tempPredCessor.right = cur.right;
                cur.right = cur.left;
                cur.left = null;
            }
            cur = cur.right;
        }
        return node;
    }

    //O(n)
    //flatten step - 2
    private static void flattenBinaryTree2(TreeNode root) {

        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            if (cur != null && cur.right != null) stack.add(cur.right);
            if (cur != null && cur.left != null) stack.add(cur.left);
            if (!stack.isEmpty()) cur.right = stack.peek();
            cur.left = null;
        }
    }

    //1 2 13 4 5
    //do RLN traversal , logic
    // node.left=null;
    // node.right=prev;
    //prev=node;
    private static TreeNode flattenBinaryTree(TreeNode node, TreeNode[] prev) {
        if (node == null) return node;
        flattenBinaryTree(node.right, prev);
        flattenBinaryTree(node.left, prev);

        node.right = prev[0];
        node.left = null;
        prev[0] = node;
        return node;
    }

    //use to inOrder traversal without extra space
    //inorder to preOrder traversal in single line change
    //vide0 - 37
    private static List<Integer> morrisTraverSal(TreeNode root) {
        List<Integer> inOrder = new ArrayList<>();
        TreeNode cur = root;
        while (cur != null) {
            //case - 1
            if (cur.left == null) {
                inOrder.add(cur.data);
                cur = cur.right;
            } else {//case - 2
                TreeNode tempPredCessor = cur.left;
                while (tempPredCessor.right != null || tempPredCessor.right != cur) {
                    tempPredCessor = tempPredCessor.right;
                }
                //add link
                if (tempPredCessor.right == null) {
                    tempPredCessor.right = cur;
                    cur = cur.left;
                } else {
                    //cut link
                    tempPredCessor.right = null;
                    inOrder.add(cur.data);
                    cur = cur.right;
                }
            }
        }
        return inOrder;
    }

    //video - 36
    //do level order for serialize , it is one of many ways
    public static String serialize(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        StringBuffer ans = new StringBuffer();
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode temp = queue.poll();
                if (temp == null) ans.append("#,");
                else {
                    ans.append(temp.data + ",");
                    queue.add(temp.left);
                    queue.add(temp.right);
                }
            }
        }
        //remove last comma
        ans.replace(ans.length() - 1, ans.length(), "");

        return ans.toString();
    }

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        String[] splitted = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        int i = 0;
        queue.add(new TreeNode(Integer.valueOf(splitted[i++])));
        TreeNode root = null;
        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            if (root == null) root = temp;
            //for L
            if (splitted[i].equals("#")) {
                temp.left = null;
                i++;
            } else {
                TreeNode l = new TreeNode(Integer.valueOf(splitted[i]));
                queue.add(l);
                temp.left = l;
                i++;
            }
            //for R
            if (splitted[i].equals("#")) {
                temp.right = null;
                i++;
            } else {
                TreeNode r = new TreeNode(Integer.valueOf(splitted[i]));
                queue.add(r);
                temp.right = r;
                i++;
            }
        }
        return root;
    }

    //video - 35
    //TC - O(n)
    private static TreeNode treeFromInorderPostOrder(int is, int ie, int[] inOrder, int ps, int pe, int[] poOrder, Map<Integer, Integer> map) {
        if (is > ie || ps > pe) return null;
        //get last element of poOrder
        int element = poOrder[pe];
        TreeNode root = new TreeNode(element);
        int index = map.get(element);
        //number of element on left side of inorder
        int numberCount = index - is;
        //divide at ps+numsLeft-1
        root.left = treeFromInorderPostOrder(is, index - 1, inOrder, ps, ps + numberCount - 1, poOrder, map);
        root.right = treeFromInorderPostOrder(index + 1, ie, inOrder, ps + numberCount, pe - 1, poOrder, map);

        return root;
    }

    //video - 34
    //TC -> O(n)
    private static TreeNode treeFromInOrderAndPreOrder(int is, int ie, int[] inOrder, int preStart, int preEnd, int[] preOrder, Map<Integer, Integer> map) {
        if (is > ie || preStart > preEnd) return null;
        //get first element of preOrder
        int element = preOrder[preStart];
        TreeNode root = new TreeNode(element);
        int index = map.get(element);
        int numbersCount = index - is;
        //divide at preStart+numbsCountLeft
        root.left = treeFromInOrderAndPreOrder(is, index - 1, inOrder, preStart + 1, preStart + numbersCount, preOrder, map);
        root.right = treeFromInOrderAndPreOrder(index + 1, ie, inOrder, preStart + numbersCount + 1, preEnd, preOrder, map);
        return root;
    }


    //video - 32
    //comp : logn^2 - logn for finding height & log(n) for traversing
    private static int countTotalNodes(TreeNode node) {
        if (node == null) return 0;
        int lh = maxDepthLeft(node.left);
        int rh = maxDepthRight(node.right);
        // lh+1 if it is edge based
        if (lh == rh) return (2 << lh) - 1;
        else return 1 + countTotalNodes(node.left) + countTotalNodes(node.right);
    }

    private static int maxDepthRight(TreeNode node) {
        int count = 0;
        TreeNode temp = node;
        while (temp != null) {
            count++;
            temp = temp.right;
        }
        return count;
    }

    private static int maxDepthLeft(TreeNode node) {
        int count = 0;
        TreeNode temp = node;
        while (temp != null) {
            count++;
            temp = temp.left;
        }
        return count;
    }

    //video - 31
    private static void burnTree(TreeNode node, int target) {
        Map<Integer, TreeNode> map = new HashMap<>();
        //fill map with child->parent & mark target
        TreeNode targtNode = getTargetNode(node, target);
        parentFill(node, map);

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(targtNode);
        int burnTime = 0;
        Map<Integer, Boolean> visited = new HashMap<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode temp = queue.poll();
                if (temp.left != null && visited.get(temp.left.data) != Boolean.TRUE) {
                    queue.add(temp.left);
                    visited.put(temp.left.data, Boolean.TRUE);
                }
                if (temp.right != null && visited.get(temp.right.data) != Boolean.TRUE) {
                    queue.add(temp.right);
                    visited.put(temp.right.data, Boolean.TRUE);
                }
                if (map.get(temp.data) != null && visited.get(temp.data) != Boolean.TRUE) {
                    queue.add(map.get(temp.data));
                    visited.put(map.get(temp.data).data, Boolean.TRUE);
                }
                visited.put(temp.data, Boolean.TRUE);
            }
            burnTime++;
        }
        System.out.println(burnTime);
    }


    //video - 30
    private static Queue<TreeNode> distanceAtNodes(TreeNode node, int target, int k) {
        Map<Integer, TreeNode> parentMap = new HashMap<>();
        //fill map with child->parent & mark target
        TreeNode targtNode = getTargetNode(node, target);
        parentFill(node, parentMap);

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(targtNode);
        int dist = 0;
        Map<Integer, Boolean> visited = new HashMap<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode temp = queue.poll();

                if (temp.left != null && visited.get(temp.left.data) == Boolean.FALSE) {
                    queue.add(temp.left);
                    visited.put(temp.left.data, Boolean.TRUE);
                }
                if (temp.right != null && visited.get(temp.right.data) == Boolean.FALSE) {
                    queue.add(temp.right);
                    visited.put(temp.right.data, Boolean.TRUE);
                }
                if (parentMap.get(temp.data) != null && visited.get(temp.data) == Boolean.FALSE) {
                    queue.add(parentMap.get(temp.data));
                    visited.put(parentMap.get(temp.data).data, Boolean.TRUE);
                }
                visited.put(temp.data, Boolean.TRUE);
            }
            dist++;
            if (dist == k) break;
        }
        while (!queue.isEmpty()) {
            System.out.println(queue.poll().data + " ");
        }
        return queue;
    }

    private static TreeNode getTargetNode(TreeNode node, int target) {
        if (node == null) return node;
        if (node.data == target) return node;
        TreeNode l = getTargetNode(node.left, target);
        if (l.data == target) return l;

        TreeNode r = getTargetNode(node.right, target);
        if (r.data == target) return r;
        return null;
    }

    private static void parentFill(TreeNode node, Map<Integer, TreeNode> map) {
        if (node == null) return;

        if (node.left != null) {
            map.put(node.left.data, node);
            parentFill(node.left, map);
        }
        if (node.right != null) {
            map.put(node.right.data, node);
            parentFill(node.right, map);
        }
    }

    //video - 29
    // while going down make sure that parent value is greater than sum of left & right node value
    //while coming up sum left & right
    private static void childSumProperty(TreeNode node) {
        if (node == null) return;

        int childSum = 0;
        if (node.left != null) childSum += node.left.data;
        if (node.right != null) childSum += node.right.data;

        //if sum down is lesser than copy the value
        if (node.data >= childSum) {
            if (node.left != null) node.left.data = node.data;
            if (node.right != null) node.right.data = node.data;
        }
        else {
            node.data =  childSum;
        }
        childSumProperty(node.left);
        childSumProperty(node.right);

        int sum_up = 0;
        if (node.left != null) sum_up += node.left.data;
        if (node.right != null) sum_up += node.right.data;
        if(node.left!=null || node.right!=null)node.data = sum_up;

    }

    //width - number of nodes at any level b/w 2 nodes
    //https://www.youtube.com/watch?v=ZbybYvcVLks&list=PLgUwDviBIf0q8Hkd7bK2Bpryj2xVJk8Vk&index=29
    private static int maxWidth(TreeNode node) {
        class Pair {
            TreeNode node;
            int n;

            public Pair(TreeNode node, int n) {
                this.node = node;
                this.n = n;
            }
        }
        Queue<Pair> queue = new ArrayDeque<>();
        queue.add(new Pair(node, 0));
        int ans = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            int left = 0, right = 0;
            for (int i = 0; i < size; i++) {
                Pair temp = queue.poll();
                if (i == 0) left = temp.n;
                right = temp.n;
                if (temp.node.left != null) {
                    queue.add(new Pair(temp.node.left, 2 * (temp.n) + 1 - left));
                }
                if (temp.node.right != null) {
                    queue.add(new Pair(temp.node.right, 2 * (temp.n) + 2 - left));
                }
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    //video - 27
    // TC -> O(n)
    private static TreeNode lca(TreeNode node, int p, int q) {
        if (node == null) return null;
        if (node.data == p || node.data == q) return node;
        TreeNode l = lca(node.left, p, q);
        TreeNode r = lca(node.right, p, q);
        //or if (l == null ) return r;
        if (l == null) return r;

        //or if (r == null) return l;
        if (r == null) return l;
            //both is not null , so return node
        else return node;

    }

    //video -26
    // TODO
    private static boolean printRootToNodePath(TreeNode node, int n, List<Integer> ans) {
        if (node == null) return false;

        ans.add(node.data);
        if (node.data == n) {
            System.out.println(ans);
            return true;
        }

        if(printRootToNodePath(node.left, n, ans)) return true;
        if(printRootToNodePath(node.right, n, ans)) return true;
        ans.remove(ans.size() - 1);

        return false;
    }

    //video - 26
    private static boolean isSymmetrical(TreeNode left, TreeNode right) {
        // if(left==null && right!=null) return false
        // if(left!=null && right==null) return false
        //if(left==null && right==null) return true
        //if(left!null && right!null & left==right) return true
        if (left == null || right == null) return left == right;
        if (left.data != right.data) return false;
        return isSymmetrical(left.left, right.right)
                && isSymmetrical(left.right, right.left);

    }

    //video - 25
    private static void leftView(TreeNode node, int level, List<Integer> ans) {
        if (node == null) return;
        if (level == ans.size()) ans.add(node.data);
        leftView(node.left, level + 1, ans);
        leftView(node.right, level + 1, ans);
    }

    //video - 25
    private static void rightView(TreeNode node, int level, List<Integer> ans) {
        if (node == null) return;
        if (level == ans.size()) ans.add(node.data);
        rightView(node.right, level + 1, ans);
        rightView(node.left, level + 1, ans);
    }

    //map.values().stream().collect( .toList())
    // recursive order will not work , use levelOrder Travesal
    // for recursive need to maintain height as well
    private static void bottomView(TreeNode node, int val, TreeMap<Integer, Integer> map) {
        if (node == null) return;
        map.put(val, node.data);
        bottomView(node.left, val - 1, map);
        bottomView(node.right, val + 1, map);

    }

    //video - 23
    //map.values().stream().collect(Collectors.toList())
    //first element in verticalTraverSal gives top View
    //TC - O(n)
    //use while loop or level order traversal since recursive will give change order
    private static void topView(TreeNode node, int val, TreeMap<Integer, Integer> map) {
        if (node == null) return;
        map.putIfAbsent(val, node.data);
        topView(node.left, val - 1, map);
        topView(node.right, val + 1, map);
    }

    //video - 21
    private static void verticalTraversal(TreeNode node, int val, TreeMap<Integer, List<Integer>> map) {
        if (node == null) return;
        verticalTraversal(node.left, val - 1, map);
        //in short   map.computeIfAbsent(val,k-> new ArrayList<>()).add(node.data);
        List<Integer> list = map.getOrDefault(val, new ArrayList<>());
        list.add(node.data);
        map.put(val, list);
        verticalTraversal(node.right, val + 1, map);

    }

    //boundryTraversal
    //video - 20
    private static void boundryTraversal(TreeNode node) {
        List<Integer> left = new ArrayList<>();
        addLeftBoundary(node, left);
        List<Integer> leafList = new ArrayList<>();
        addLeaves(node, leafList);
        ArrayList<Integer> right = new ArrayList<>();
        addRightBoundary(node.right, right);

        List<Integer> collect = Stream.of(left, leafList, right).flatMap(x -> x.stream()).collect(Collectors.toList());
        System.out.println(collect);

    }

    //do inOrder
    private static void addLeaves(TreeNode node, List<Integer> res) {
        if (isLeaf(node)) {
            res.add(node.data);
            return;
        }
        if (node.left != null) addLeaves(node.left, res);
        if (node.right != null) addLeaves(node.right, res);
    }

    private static boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }
    private static void addLeftBoundary(TreeNode node, List<Integer> res) {
        while (node != null) {
            if (!isLeaf(node)) res.add(node.data);
            if (node.left != null)
                node = node.left;
            else
                node = node.right;
        }
    }

    private static void addRightBoundary(TreeNode node, List<Integer> res) {
        List<Integer> temp = new ArrayList<>();
        while (node != null) {
            if (!isLeaf(node)) temp.add(node.data);
            if (node.right != null)
                node = node.right;
            else
                node = node.left;
        }
        // add in reverse order
        for (int i = temp.size() - 1; i >= 0; i--) {
            res.add(temp.get(i));
        }
    }

    //video - 19
    private static void zigzagTraversalUsingQueueReverse(TreeNode node) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(node);
        List<List<Integer>> ans = new ArrayList<>();
        boolean flag = true;
        while (!queue.isEmpty()) {
            //this was the issue
            int size = queue.size();
            List<Integer> tempList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode temp = queue.poll();
                tempList.add(temp.data);
                if (temp.left != null) queue.add(temp.left);
                if (temp.right != null) queue.add(temp.right);
            }
            if (flag) Collections.reverse(tempList);
            ans.add(tempList);
            flag = !flag;
        }
        System.out.println(ans);
    }

    //video - 19
    private static void zigzagTraversal(TreeNode node) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.add(node);
        List<Integer> ans = new ArrayList<>();
        boolean flag = true;
        while (!queue.isEmpty()) {
            //this was the issue
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                if (flag) {
                    TreeNode temp = queue.pollFirst();
                    ans.add(temp.data);
                    if (temp.left != null) queue.addLast(temp.left);
                    if (temp.right != null) queue.addLast(temp.right);
                } else {
                    TreeNode temp = queue.pollLast();
                    ans.add(temp.data);
                    if (temp.right != null) queue.addFirst(temp.right);
                    if (temp.left != null) queue.addFirst(temp.left);
                }
            }
            flag = !flag;
        }
        System.out.println(ans);
    }

    //video - 18
    private static boolean isSameTree1(TreeNode p, TreeNode q) {
        if (p == null || q == null) return p == q;
        if (p.data != q.data) return false;
        return isSameTree1(p.left, q.left) && isSameTree1(p.right, q.right);
    }

    //video - 17
    //ignore negative path sum , means if path sum is less than 0 then return 0
    private static int maxPathSum(TreeNode node, int[] res) {
        if (node == null) return 0;
        int lh = maxPathSum(node.left, res);
        if (lh < 0) lh = 0;
        int rh = maxPathSum(node.right, res);
        if (rh < 0) rh = 0;
        res[0] = Math.max(res[0], node.data + lh + rh);
        //note next line
        return node.data + Math.max(lh, rh);

    }

    //TC -> O(n)
    //diameter means longest path b/w any 2 node , may not be from root
    //video - 16
    private static int treeDiameter(TreeNode node, int[] res) {
        if (node == null) return 0;
        int lh = treeDiameter(node.left, res);
        int rh = treeDiameter(node.right, res);
        res[0] = Math.max(res[0], lh + rh);
        return 1 + Math.max(lh, rh);

    }

    //return -1 if not balancedBinaryTree
    // else return height of the tree
    // Balanced_Binary_Tree means height difference should be less than 2
    private static int balancedBinaryTree(TreeNode node) {
        if (node == null) return 0;

        int lh = balancedBinaryTree(node.left);
        //step = 1
        if (lh == -1) return -1;
        int rh = balancedBinaryTree(node.right);

        //step=2
        if (rh == -1) return -1;
        if (Math.abs(rh - lh) > 1) return -1;
        return 1 + Math.max(lh, rh);
    }

    //TC - > O(n)
    //video - 14
    // max tree height or depth or number of nodes
    private static int maxDepth(TreeNode node) {
        if (node == null) return 0;
        int lh = maxDepth(node.left);
        int rh = maxDepth(node.right);
        return 1 + Math.max(lh, rh);
    }

    //TC-> O(3n)
    private static void aLL3Iterative(TreeNode node) {
        class NodeVal {
            TreeNode node;
            int n;

            public NodeVal(TreeNode node, int n) {
                this.node = node;
                this.n = n;
            }
        }

        Stack<NodeVal> stack = new Stack<>();
        //0->node , 1->no
        stack.add(new NodeVal(node, 1));
        List<TreeNode> pre = new ArrayList<>();
        List<TreeNode> in = new ArrayList<>();
        List<TreeNode> post = new ArrayList<>();
        while (!stack.isEmpty()) {
            NodeVal peekElement = stack.peek();
            if (peekElement.n == 1) {
                pre.add(peekElement.node);
                peekElement.n++;
                if (peekElement.node.left != null) stack.add(new NodeVal(peekElement.node.left, 1));
            } else if (peekElement.n == 2) {
                in.add(peekElement.node);
                peekElement.n++;
                if (peekElement.node.right != null) stack.add(new NodeVal(peekElement.node.right, 1));
            } else {
                post.add(stack.pop().node);
            }
        }
        System.out.println(pre);
        System.out.println(in);
        System.out.println(post);
    }

    //TODO:DOUBT
    //TC-> O(2n)
    private static void postOrderIterative1Stack(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> ans = new ArrayList<>();
        TreeNode cur = node;
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.add(cur);
                cur = cur.left;
            } else {
                TreeNode temp = stack.peek().right;
                if (temp == null) {
                    temp = stack.pop();
                    ans.add(temp.data);
                    while (!stack.isEmpty() && stack.peek().right == temp) {
                        //pop
                        temp = stack.pop();
                        ans.add(temp.data);
                    }
                } else cur = temp;
            }
        }
    }

    //TC-> O(n)
    private static List<Integer> postOrderIterative2Stack(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        stack.add(node);
        //since adding at 0 index no need to reverse
        //Otherwise, ans is reverse of list
        List<Integer> ans = new LinkedList<>();
        while (!stack.isEmpty()) {
            TreeNode temp = stack.pop();
            if (temp.left != null) stack.add(temp.left);
            if (temp.right != null) stack.add(temp.right);
            ans.add(0, temp.data);
        }
        return ans;
    }

    //TC-> O(n)
    //video - 10
    private static void inOrderIterative(TreeNode root) {
        //L-Root-R
        //use stack for right iteration
        Stack<TreeNode> stack = new Stack<>();
        TreeNode temp = root;
        while (true) {
            if (temp != null) {
                stack.add(temp);
                temp = temp.left;
            } else {
                if (stack.isEmpty()) break;
                temp = stack.pop();
                System.out.print(temp.data + " ");
                temp = temp.right;
            }
        }
    }

    //TC-> O(n)
    //video -9
    private static List<Integer> preOrderIterative(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        List<Integer> ans = new LinkedList<>();
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            ans.add(node.data);
            if (node.right != null) stack.add(node.right);
            if (node.left != null) stack.add(node.left);
        }
        return ans;
    }

    //LRN
    private static boolean isBST(int min, TreeNode TreeNode, int max) {
        if (TreeNode == null) return true;
        if (min > TreeNode.data || TreeNode.data > max) return false;
        return isBST(min, TreeNode.left, TreeNode.data) && isBST(TreeNode.data, TreeNode.right, max);
    }


    private static void invertTree(TreeNode t) {
        if (t == null) return;
        TreeNode temp = t.left;
        t.left = t.right;
        t.right = temp;
        invertTree(t.left);
        invertTree(t.right);
    }

    //video-8
    private static void levelOrder(TreeNode node) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode temp = queue.poll();
                System.out.print(temp.data + " ");
                if (temp.left != null) queue.add(temp.left);
                if (temp.right != null) queue.add(temp.right);
            }
            System.out.println();
        }
    }

    //postorder
    private static void postOrder(TreeNode t) {
        if (t == null) return;
        postOrder(t.left);
        postOrder(t.right);
        System.out.println(t.data);

    }

    //inOrder
    private static void inOrder(TreeNode t) {
        if (t == null) return;
        inOrder(t.left);
        System.out.print(t.data + " ");
        inOrder(t.right);
    }

    // 1,2,4,5,6,3,7,8,9,10
    private static void preOrderTraversal(TreeNode root) {
        if(root ==null) return;
        System.out.print(root.data+" ");
        preOrderTraversal(root.left);
        preOrderTraversal(root.right);
    }
}

class TreeNode {

    TreeNode left;
    int data;
    TreeNode right;

    public TreeNode(int data) {
        this.data = data;
        left = right = null;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "left=" + left +
                ", val=" + data +
                ", right=" + right +
                '}';
    }
}
