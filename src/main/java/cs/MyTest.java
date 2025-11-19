package cs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyTest {
    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>();
        map.put("A", "Red");
        map.put("B", "Blue");
        map.put("C", "Red");
        map.put("D", "Green");
        map.put("E", "Blue");

        // Group keys by value
        Map<String, List<String>> grouped = map.entrySet()
                .stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,                        // group by value
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList()) // collect keys
                ));
        map.entrySet()
                .stream().collect(
                        Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())


                        ));

        System.out.println(grouped);
    }

//17164.15
}
