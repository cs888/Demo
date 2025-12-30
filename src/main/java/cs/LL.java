package cs;

import java.util.*;

public class LL {
    public static void main(String[] args) {
        LLNode n1 = new LLNode(1);
        n1.next = new LLNode(2);
        n1.next.next = new LLNode(3);
        n1.next.next.next = new LLNode(4);


        LLNode n2 = new LLNode(3);
        n2.next = new LLNode(5);
        LLNode llNode = reverseKGroup(n1, 3);
        System.out.println(llNode);

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
        BrowserNode root;

        public Browser(String homePage) {
            root = new BrowserNode(homePage);
        }

        public void visit(String str) {
            BrowserNode newBrowserNode = new BrowserNode(str);
            if (root == null)
                root = newBrowserNode;
            else {
                newBrowserNode.prev = root;
                root.next = newBrowserNode;
                root = newBrowserNode;
            }
        }

        public String back(int val) {

            while (val > 0 && root.prev != null) {
                root = root.prev;
                val--;
            }
            return root.page;
        }

        public String forward(int val) {

            while (val > 0 && root.next != null) {
                root = root.next;
                val--;
            }
            return root.page;
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


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {}

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    //LL-22
    public ListNode rotateRight(ListNode head, int k) {
        if(k==0 || head ==null) return head;
        int len=0;
        ListNode lentemp=head;
        while(lentemp!=null){
            len++;lentemp=lentemp.next;
        }

        int r=k%len;
        if(r==0) return head;
        int nodeFromStart=len-r;
        ListNode tempPtr=head;
        ListNode prev_Head=head;


        while(tempPtr.next!=null){
            nodeFromStart--;
            if(nodeFromStart==0)prev_Head=tempPtr;
            tempPtr=tempPtr.next;
        }

        ListNode ans=prev_Head.next;
        prev_Head.next=null;
        return ans;

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
        //point last node next to head
        cur.next = head;
        LLNode root = map.get(len - k);
        if (root != null) {
            head = root.next;
            root.next = null;
        }
        return head;
    }

    //LL-21
    public static LLNode reverseKGroup(LLNode root, int k) {

        LLNode temp = root ;
        LLNode dummyNode=new LLNode(-1) ,ans=dummyNode;
        while (temp != null) {
            LLNode kthNode=getKthNode(temp,k);
            if(kthNode ==null){
                ans.next=temp;
                break;
            }
            LLNode kthNodeNext= kthNode.next;

            kthNode.next=null;
            ans.next=reversee(temp);
            ans=temp;

            temp = kthNodeNext;
        }
        return dummyNode.next;
    }


    static LLNode getKthNode(LLNode node,int k){
        int i=1;
        LLNode temp=node;
        while(i<k && temp!=null){
            i++;
            temp=temp.next;
        }
        return temp;
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

    //LL-20
    public static LLNode removeDuplicateSoretedDLL(LLNode head) {

        LLNode temp = head;
        while (temp != null && temp.next != null) {
            LLNode nextNode = head.next;
            while (nextNode != null && temp.data == nextNode.data) {
                nextNode = nextNode.next;
            }
            temp.next = nextNode;
            if (nextNode != null) nextNode.prev = temp;
            temp = temp.next;
        }
        return head;
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
    public static LLNode firstNodeInCycle(LLNode head) {
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
            if (slow == fast) return findLen(slow);
        }
        return 0;
    }

    private static int findLen(LLNode meeting) {
        int len = 1;
        LLNode temp=meeting.next;
        while (temp !=meeting) {
            temp=temp.next;
            len++;
        }
        return len ;
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

        LLNode newHead = revSingleList(slow);
        slow = head;
        while (newHead != null) {
            if (slow.data != newHead.data)
                return false;
            slow = slow.next;
            newHead = newHead.next;
        }
        return true;
    }

    // first move fast ko Kth Step
    // then keep slow and fast moving together
    public static LLNode removeKthFromEndUsingSlowFast(LLNode head , int k) {

        LLNode fast=head;
        while ( k>0) {
            k--;
            if(fast==null) return head.next;
            fast=fast.next;
        }

        // for exactly case k== length of list
        if(fast==null) return head.next;

        LLNode slow=head;
        while (fast.next!=null) {
            fast=fast.next;
            slow=slow.next;
        }
        slow.next=slow.next.next;
        return head;

    }

    //video -9
    public static LLNode removeKthFromEnd(LLNode head , int k) {

        int len=0;
        LLNode temp=head;
        while (temp!=null) {
            len++;
            temp=temp.next;
        }
       int fromStart=len-k;

        temp=head;

        while (temp!=null) {
            fromStart--;
            if(fromStart==0) {
                temp.next=temp.next.next;
                break;
            }
            temp=temp.next;
        }
        return head;

    }



    // video - 7
    public static LLNode sort_0_1_2_List(LLNode head) {

        LLNode zeroHead=new LLNode(-1);
        LLNode oneHead=new LLNode(-1);
        LLNode twoHead=new LLNode(-1);

        LLNode zero=zeroHead,one=oneHead,two=twoHead;
        LLNode temp=head;
        while (temp!=null){
            if(temp.data==0){
                zero.next=temp;
                zero=zero.next;
            }

           else if(temp.data==1){
                one.next=temp;
                one=one.next;
            }
           else {
                two.next=temp;
                two=two.next;
            }

            temp=temp.next;
        }

        zero.next=oneHead.next!=null?oneHead.next:twoHead.next;
        one.next=twoHead.next;
        two.next=null;
        return zeroHead.next;

    }
    //LL- 6
    public static LLNode oddEvenList(LLNode head) {
        LLNode odd = head;
        LLNode even = head.next;
        LLNode even_head = even;

        while (even != null && even.next != null) {
            odd.next = odd.next.next;
            odd = odd.next;

            even.next = even.next.next;
            even = even.next;
        }
        odd.next = even_head;
        return head;

    }

    //LL - video-5
    public ListNode addTwoNumbers(ListNode head1, ListNode head2) {
        ListNode dummyNode=new ListNode(-1) , ans=dummyNode;
        int carry=0;
        while(head1!=null || head2!=null){
            int val=0;
            if(head1!=null){val+=head1.val;head1=head1.next;}
            if(head2!=null){val+=head2.val;head2=head2.next;}
            val+=carry;
            carry=val/10;
            val%=10;

            ans.next=new ListNode(val);
            ans=ans.next;
        }
        if(carry>0){
            ans.next=new ListNode(carry);
        }
        return dummyNode.next;
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

    static LLNode revDoubleList(LLNode head) {

        LLNode cur=head;
        LLNode newHead=null;
        while (cur!=null){

            LLNode temp=cur.next;
            cur.next=cur.prev;
            cur.prev=temp;

            newHead=cur;
            cur=temp;
        }
        return newHead;
    }

    static LLNode revSingleList(LLNode head) {
        if (head == null || head.next == null)
            return head;
        LLNode newhead = revSingleList(head.next);
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
         return "LLNode{" +
                 "data=" + data +
                 ", next=" + next +
                 ", prev=" + prev +
                 ", child=" + child +
                 ", random=" + random +
                 '}';
     }
 }

class LinkedListNode<Integer> {
    int data;
    LinkedListNode<Integer> next;

    public LinkedListNode(int data) {
        this.data = data;
    }
}