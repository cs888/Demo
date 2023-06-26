package cs;

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
            System.out.print(temp.val + " ");
            temp = temp.next;
        }

    }

    private static Node merge(Node n1, Node n2) {
        if (n1 == null) return n2;
        else if (n2 == null) return n1;
        if (n1.val < n2.val) {
            n1.next = merge(n1.next, n2);
            return n1;
        } else {
            n2.next = merge(n1, n2.next);
            return n2;
        }
    }


    private static Node rev(Node node) {
        if (node.next == null) return node;
        Node head = rev(node.next);
        node.next.next = node;
        node.next = null;
        return head;
    }


}

class Node {
    int val;
    Node next;

    Node() {
    }

    Node(int val) {
        this.val = val;
    }

    Node(int val, Node next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}