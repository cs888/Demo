package cs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class LL {
    public static void main(String[] args) {
        Node n1 = new Node(1);
        n1.next = new Node(4);
        n1.next.next = new Node(7);
        n1.next.next.next = new Node(9);


        Node n2 = new Node(3);
        n2.next = new Node(5);

        Node merge = merge(n1, n2);

        Node temp = merge;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }

    }

    //LL-10
    public static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;

        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node newHead = rev(slow);
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
    public static Node oddEvenList(Node head) {
        Node odd = head;
        Node even = head.next;
        Node even_head = head.next;

        while (even != null && even.next != null) {
            odd.next = odd.next.next;
            odd = odd.next;

            even.next = even.next.next;
            even = even.next;
        }
        odd.next = even_head;
        return head;

    }

    private static Node merge(Node n1, Node n2) {
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

    static Node rev(Node head) {
        if (head == null || head.next == null)
            return head;
        Node newhead = rev(head.next);
        head.next.next = head;
        head.next = null;
        return newhead;
    }
}

class Node {
    int data;
    Node next;
    Node prev;

    Node() {
    }

    Node(int data) {
        this.data = data;
    }

    Node(int data, Node next, Node prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}