package cs;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        String str = "abcdb";
        char[] chars = str.toCharArray();
        //char,count
        Map<Character, Integer> map = new HashMap<>();
        map.put('c',1);

       for ( Map.Entry entry : map.entrySet()) {
           System.out.println(entry.getValue());

       }
    }
}
