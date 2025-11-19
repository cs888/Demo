package cs.trie;

class Node2 {

    Node2[] links = new Node2[26];
    int startw;
    int endW;

    public Node2() {
    }

    public boolean contains(char ch) {
        return links[ch-'a']!=null;
    }

    public void put(char ch, Node2 node2) {
        links[ch-'a']=node2;
    }

    public Node2 get(char ch) {
        return links[ch-'a'];
    }

    public void increaseWordEnd() {
        this.endW++;
    }

    public void increaseStartWordCount() {
        this.startw++;
    }

    public void decreaseStartWordCount() {
        this.startw--;
    }

    public void decreaseEndWordCount() {
        this.endW--;
    }
}

public class Trie2 {

    public static void main(String[] args) {
        Trie2 trie2=new Trie2();
        trie2.insert("samsung");
        trie2.insert("samsung");
        trie2.insert("vivo");
        trie2.erase("vivo");
        trie2.insert("sat");
        System.out.println(trie2.countWordStartWithTo("sa"));
    }
    Node2 root;
    public Trie2() {
        root=new Node2();
    }

    void insert(String str){
        Node2 temp = root;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(!temp.contains(ch)){
                temp.put(ch,new Node2());
            }
            temp = temp.get(ch);
            temp.increaseStartWordCount();
        }
        temp.increaseWordEnd();
    }

    int countWordEqualTo(String str){
        Node2 temp = root;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(!temp.contains(ch)){
                return 0;
            }
            temp = temp.get(ch);
        }
        return temp.endW ;
    }

    int countWordStartWithTo(String str){
        Node2 temp = root;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(!temp.contains(ch)){
                return 0;
            }
            temp = temp.get(ch);
        }
        return temp.startw ;
    }

    void erase(String str){
        Node2 temp = root;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if(!temp.contains(ch)){
                return ;
            }
            temp.decreaseStartWordCount();
            temp = temp.get(ch);
        }
        temp.decreaseEndWordCount();
    }

}
