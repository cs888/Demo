package cs.trie;

 class Trie4Node{
     Trie4Node[] links = new Trie4Node[26];

     public boolean containKey(char ch) {
         return links[ch-'a']!=null;
     }

     public void put(char ch, Trie4Node trie4Node) {
         links[ch-'a']=trie4Node;
     }

     public Trie4Node get(char ch) {
        return links[ch-'a'];
     }
 }

public class Trie4 {
    public static void main(String[] args) {
        Trie4 trie4 = new Trie4();
        String st="abab";
        int ans[]={0};
        System.out.println(trie4.insertAndCount(st));
    }
     Trie4Node root=new Trie4Node();

    int insertAndCount(String str) {
        int count=0;
        for (int i = 0; i < str.length(); i++) {
            Trie4Node temp = root;
            for (int j = i; j < str.length() ; j++) {
                char ch = str.charAt(j);
                if (!temp.containKey(ch)) {
                    count++;
                    temp.put(ch, new Trie4Node());
                }
                temp=temp.get(ch);
            }
        }
        return count+1;
     }
}
