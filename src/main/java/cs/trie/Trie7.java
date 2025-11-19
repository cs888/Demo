package cs.trie;

import java.util.*;

class NodeTrie7{
    NodeTrie7 []links=new NodeTrie7[2];

    public boolean containsKey(int bit) {
        return links[bit]!=null;
    }

    public NodeTrie7 get(int bit) {
        return links[bit];
    }

    public void put(int bit, NodeTrie7 nodeTrie6) {
        links[bit]=nodeTrie6;
    }
}

public class Trie7 {

    public static void main(String[] args) {
        int a[] = {4, 9, 2, 5, 0, 1};
        Arrays.sort(a);

        //xor value that can be taken , arr till max value allowed
        List<List<Integer>> queries = new ArrayList<>(List.of(
                new ArrayList<>(List.of(3, 0)),
                new ArrayList<>(List.of(3, 10)),
                new ArrayList<>(List.of(7, 5)),
                new ArrayList<>(List.of(7, 9))
        ));


        for (int i = 0; i <queries.size() ; i++) {
            queries.get(i).add(i);
        }

        Collections.sort(queries, Comparator.comparingInt(x->x.get(1)));

        int max[] = new int[queries.size()];
        Trie7 nodeTrie7 = new Trie7();
        for (int i = 0; i <queries.size() ; i++) {
            Integer maxArrElementVal = queries.get(i).get(1);
            int ind=0;
            while (ind < a.length && a[ind]<=maxArrElementVal) {
                nodeTrie7.insert(a[ind]);ind++;
            }
            max[queries.get(i).get(2)]= nodeTrie7.maxiMizeValue(queries.get(i).get(0));
        }

        System.out.println(Arrays.toString(max));
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
