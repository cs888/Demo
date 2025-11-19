package cs.trie;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

class Node {
    Node[] links = new Node[25];
    boolean flag;

    public Node() {
        flag = false;
    }

    boolean containsKey(char ch) {
        return links[ch - 'a'] != null;
    }

    void put(char ch, Node node) {
        links[ch - 'a'] = node;
    }

    Node get(char ch) {
        return links[ch - 'a'];
    }

    public void setEnd() {
        flag = true;
    }

    public boolean getEnd() {
        return flag;
    }
}

public class Trie {
    public static void main(String[] args) {
        Trie trie=new Trie();
        String []s={"n","ninja","ninj","ni","nin","ninga"};

        Arrays.stream(s).forEach(x-> trie.insert(x));
        AtomicInteger ansLen= new AtomicInteger();  AtomicReference<String> ansStr = new AtomicReference<>("");
        Arrays.stream(s).forEach(x ->
        {
            if (!trie.checkIfAllPrefixExists(x)) {
                if (x.length() > ansLen.get()) {
                    ansLen.set(x.length());
                    ansStr.set(x);
                }
                else if (x.length() == ansLen.get()) {
                    if(x.compareTo(ansStr.get())>0)
                        ansStr.set(x);
                }
            }
        });

        if(ansStr.get().equals("")) System.out.println("NONE");
        else System.out.println(ansStr.get());

    }

    private Node root;

    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!node.containsKey(ch)) {
                node.put(ch, new Node());
            }
            node = node.get(ch);
        }
        node.setEnd();
    }

    public boolean search(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!node.containsKey(ch)) return false;
            node = node.get(ch);
        }

        return node.getEnd();
    }

    public boolean contains(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!node.containsKey(ch)) return false;
            node = node.get(ch);
        }
        return true;
    }

    private boolean checkIfAllPrefixExists(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!node.containsKey(ch) || !node.flag) return false;
            node = node.get(ch);
        }
        return true;
    }

}
