package cs.trie;

import java.util.Arrays;

class NodeTrie6{
    NodeTrie6 []links=new NodeTrie6[2];

    public boolean containsKey(int bit) {
        return links[bit]!=null;
    }

    public NodeTrie6 get(int bit) {
        return links[bit];
    }

    public void put(int bit, NodeTrie6 nodeTrie6) {
        links[bit]=nodeTrie6;
    }
}

public class Trie6 {

    public static void main(String[] args) {
        int a[] = {6, 8};
        int b[] = {7, 8, 2};
        Trie6 nodeTrie6 = new Trie6();
        Arrays.stream(a).forEach(x -> nodeTrie6.insert(x));
        int max = 0;
        for (int i = 0; i < b.length; i++) {
            max = Integer.max(max, nodeTrie6.maxiMizeValue(b[i]));
        }
        System.out.println(max);
    }

    NodeTrie6 root =new NodeTrie6();

    void insert(int n) {
        NodeTrie6 temp = root;
        for (int i = 31; i >= 0; i--) {
            int bit = (n >> i) & 1;
            if (!temp.containsKey(bit)) {
                temp.put(bit, new NodeTrie6());
            }
            temp = temp.get(bit);
        }
    }

    int maxiMizeValue(int num) {
        NodeTrie6 temp = root;
        int val = 0;
        for (int i = 31; i >= 0; i--) {
            int bit = (num >> i) & 1;
            if (temp.containsKey(1 - bit)) {
                temp = temp.get(1 - bit);
                val |= (1 << i);
            } else {
                temp = temp.get(bit);
            }
        }
        return val;
    }

}
