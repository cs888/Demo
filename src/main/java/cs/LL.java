package cs;

import java.util.*;

public class LL {
    public static void main(String[] args) {
        LLNode n1 = new LLNode(1);
        n1.next = new LLNode(4);
        n1.next.next = new LLNode(7);
        n1.next.next.next = new LLNode(9);


        LLNode n2 = new LLNode(3);
        n2.next = new LLNode(5);

    }

    //LL-28
    class BrowserNode {
        String page;
        BrowserNode prev;
        BrowserNode next;

        BrowserNode(String page) {
            this.page = page;
        }
    }

    public class Browser {
        BrowserNode head;

        public Browser(String homePage) {
            head = new BrowserNode(homePage);
        }

        public void visit(String str) {
            BrowserNode newBrowserNode = new BrowserNode(str);
            if (head == null)
                head = newBrowserNode;
            else {
                newBrowserNode.prev = head;
                head.next = newBrowserNode;
                head = newBrowserNode;
            }
        }

        public String back(int val) {

            while (val > 0 && head.prev != null) {
                head = head.prev;
                val--;
            }
            return head.page;
        }

        public String forward(int val) {

            while (val > 0 && head.next != null) {
                head = head.next;
                val--;
            }
            return head.page;
        }
    }

    //LL-27 another Way Optimized
    public static LLNode cloneLLOptimized(LLNode head) {
        LLNode dummyLLNode = new LLNode(-1);
        LLNode ans = dummyLLNode;
        LLNode cur = head;
        // create node in b/w
        while (cur != null) {
            LLNode newLLNode = new LLNode(cur.data);
            newLLNode.next = cur.next;
            cur.next = newLLNode;
            cur = cur.next.next;
        }

        // assign random pointer
        cur = head;
        while (cur != null && cur.next != null) {
            // newNode
            if (cur.random != null) {
                cur.next.random = cur.random.next;
            }
            cur = cur.next.next;
        }

        // re-assign old & newNode pointer poiner
        cur = head;
        while (cur != null) {
            ans.next = cur.next;
            // or ans = ans.next will also work
            ans = cur.next;
            cur.next = cur.next.next;
            cur = cur.next;
        }

        return dummyLLNode.next;
    }

    //LL-27
    public static LLNode cloneLL(LLNode head) {
        Map<LLNode, LLNode> map = new HashMap<>();
        return cloneLL(head, map);
    }

    static LLNode cloneLL(LLNode head, Map<LLNode, LLNode> map) {
        if (head == null)
            return head;
        LLNode newLLNode = new LLNode(head.data);
        map.put(head, newLLNode);
        newLLNode.next = cloneLL(head.next, map);
        newLLNode.random = map.get(head.random);
        return newLLNode;
    }

    //LL-26
    public static LLNode sortLL(LLNode head) {
        return sort(head);
    }

    static LLNode sort(LLNode head) {
        if (head == null || head.next == null)
            return head;
        LLNode prev = head;
        LLNode slow = head;
        LLNode fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // if (prev != null)
        prev.next = null;
        LLNode l1 = sort(head);
        LLNode l2 = sort(slow);
        return merge(l1, l2);

    }

    //LL-25
    public static LinkedListNode<Integer> mergeKLists(LinkedListNode<Integer>[] listArray) {

        Queue<LinkedListNode<Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(x -> x.data));
        LinkedListNode<Integer> dummyNode = new LinkedListNode<>(-1);
        LinkedListNode<Integer> ans = dummyNode;

        for (int i = 0; i < listArray.length; i++) {
            queue.add(listArray[i]);
        }
        while (!queue.isEmpty()) {
            LinkedListNode<Integer> temp = queue.poll();
            ans.next = temp;
            ans = temp;
            if (temp.next != null)
                queue.add(temp.next);
        }

        return dummyNode.next;
    }

    //LL-24
    public static LLNode flattenLinkedList(LLNode head) {
        return flatten(head);
    }

    public static LLNode flatten(LLNode head) {
        if (head == null || head.next == null)
            return head;
        LLNode last = flatten(head.next);
        head.next = null;
        return mergeVertical(head, last);
    }

    static LLNode mergeVertical(LLNode l1, LLNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;

        if (l1.data < l2.data) {
            l1.child = mergeVertical(l1.child, l2);
            return l1;
        } else {
            l2.child = mergeVertical(l1, l2.child);
            return l2;
        }
    }

    //LL-23
    public static LLNode sortTwoLists(LLNode l1, LLNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        if (l1.data < l2.data) {
            l1.next = sortTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = sortTwoLists(l1, l2.next);
            return l2;
        }
    }

    //LL-22
    public static LLNode rotate(LLNode head, int k) {

        LLNode cur = head;
        int len = 1;
        Map<Integer, LLNode> map = new HashMap<>();
        while (cur.next != null) {
            map.put(len, cur);
            len++;
            cur = cur.next;
        }
        if (k % len == 0)
            return head;
        k = k % len;
        cur.next = head;
        LLNode root = map.get(len - k);
        if (root != null) {
            head = root.next;
            root.next = null;
        }
        return head;
    }

    //LL-21
    public static LLNode kReverse(LLNode head, int k) {
        int count = 0;
        LLNode cur = head, curHead = head, prev_Tail = head;
        while (cur != null) {
            if (count == 0) {
                curHead = cur;
                count++;
                cur = cur.next;
                // reached at current k+1
            } else if (count == k - 1) {
                // 1 2 3
                LLNode curNextTemp = cur.next;
                cur.next = null;
                // reverse
                LLNode reversed = reversee(curHead);

                if (curHead == head) {
                    LLNode tempHead = head;
                    head = reversed;
                    tempHead.next = curNextTemp;
                } else {
                    prev_Tail.next = reversed;
                    curHead.next = curNextTemp;
                }
                count = 0;
                prev_Tail = curHead;
                cur = curNextTemp;
            } else {
                count++;
                cur = cur.next;
            }
        }
        return head;
    }

    static LLNode reversee(LLNode head) {
        if (head == null || head.next == null)
            return head;
        LLNode temp = reversee(head.next);
        head.next.next = head;
        head.next = null;
        return temp;
    }

    static LLNode reverse(LLNode head) {
        if (head == null || head.next == null)
            return head;
        LLNode temp = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return temp;
    }

    //LL-19
    public static List<List<Integer>> findPair(LLNode head, int k) {
        LLNode start = head;
        LLNode end = head;
        while (end.next != null) end = end.next;
        List<List<Integer>> ans = new ArrayList<>();
        while (start.data < end.data) {
            int cursum = start.data + end.data;
            if (cursum == k) {
                ans.add(List.of(start.data, end.data));
                start = start.next;
            } else if (cursum > k)
                end = end.prev;
            else
                start = start.next;
        }
        return ans;
    }

    //LL-18
    public static LLNode deleteAllOccurrences(LLNode head, int k) {

        LLNode prev = null;
        LLNode temp = head;
        while (temp != null) {
            if (temp.data == k) {
                if (temp == head)
                    head = head.next;
                else {
                    prev.next = temp.next;
                    if (temp.next != null)
                        temp.next.prev = temp.prev;
                }
            }
            prev = temp;
            temp = temp.next;
        }
        return head;
    }

    //LL-17
    public static LLNode firstNode(LLNode head) {
        LLNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }

    //LL-16
    //can be done by skip on iteartion of fast i.e doing fast=fast.next.next before
    public static LLNode deleteMiddle(LLNode head) {
        LLNode slow = head, fast = head, prev = null;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        if (prev == null) return new LLNode(-1);
        prev.next = slow.next;
        return head;
    }

    //LL-15
    public static int lengthOfLoop(LLNode head) {
        LLNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return findLen(slow, fast);
        }
        return 0;
    }

    private static int findLen(LLNode slow, LLNode fast) {
        int len = 0;
        while (slow.next != fast) {
            slow = slow.next;
            len++;
        }
        return len + 1;
    }

    //LL-14
    public static boolean detectCycle(LLNode head) {
        LLNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    //LL-13
    public static LLNode findMiddle(LLNode head) {
        LLNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //ll-12
    public static int findIntersection(LLNode firstHead, LLNode secondHead) {
        LLNode p1 = firstHead, p2 = secondHead;
        while (p1 != p2) {
            p1 = p1 == null ? secondHead : p1.next;
            p2 = p2 == null ? firstHead : p2.next;
        }
        return p1.data;
    }

    //LL-11
    public static LLNode addOne(LLNode head) {
        int a[] = {1};
        addOne(head, a);
        //System.out.println("a[0]:" + a[0]);
        if (a[0] > 0) {
            LLNode temp = new LLNode(a[0]);
            temp.next = head;
            return temp;
        }
        return head;
    }

    public static LLNode addOne(LLNode head, int a[]) {
        if (head == null) return head;
        addOne(head.next, a);
        a[0] += head.data;
        head.data = a[0] % 10;
        a[0] /= 10;
        return head;
    }

    //LL-10
    public static boolean isPalindrome(LLNode head) {
        if (head == null || head.next == null) return true;

        LLNode slow = head;
        LLNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        LLNode newHead = rev(slow);
        slow = head;
        while (newHead != null) {
            if (slow.data != newHead.data)
                return false;
            slow = slow.next;
            newHead = newHead.next;
        }
        return true;
    }

    //LL- 6
    public static LLNode oddEvenList(LLNode head) {
        LLNode odd = head;
        LLNode even = head.next;
        LLNode even_head = head.next;

        while (even != null && even.next != null) {
            odd.next = odd.next.next;
            odd = odd.next;

            even.next = even.next.next;
            even = even.next;
        }
        odd.next = even_head;
        return head;

    }

    private static LLNode merge(LLNode n1, LLNode n2) {
        if (n1 == null) return n2;
        else if (n2 == null) return n1;
        if (n1.data < n2.data) {
            n1.next = merge(n1.next, n2);
            return n1;
        } else {
            n2.next = merge(n1, n2.next);
            return n2;
        }
    }

    static LLNode rev(LLNode head) {
        if (head == null || head.next == null)
            return head;
        LLNode newhead = rev(head.next);
        head.next.next = head;
        head.next = null;
        return newhead;
    }
}

 class LLNode {
    int data;
    LLNode next;
    LLNode prev;
    LLNode child;
    LLNode random;

    LLNode() {
    }

    LLNode(int data) {
        this.data = data;
    }

    LLNode(int data, LLNode next, LLNode prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    LLNode(int data, LLNode next, LLNode prev, LLNode child) {
        this.data = data;
        this.next = next;
        this.prev = prev;
        this.child = child;
    }

    LLNode(int data, LLNode next, LLNode prev, LLNode child, LLNode random) {
        this.data = data;
        this.next = next;
        this.prev = prev;
        this.child = child;
        this.random = random;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class LinkedListNode<Integer> {
    int data;
    LinkedListNode<Integer> next;

    public LinkedListNode(int data) {
        this.data = data;
    }
}