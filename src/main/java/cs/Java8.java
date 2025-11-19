package cs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Java8 {
    public static void main(String[] args) {

        Map<String, String> inputMap = Map.of(
                "A", "Apple",
                "B", "Banana",
                "C", "Apple",
                "D", "Durian",
                "E", "Banana"
        );

        // Group keys by their values

        inputMap.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey,Collectors.toList())));


        Map<String, List<String>> groupedByValue = inputMap.entrySet()
                .stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,              // Grouping by the value
                        Collectors.mapping(
                                Map.Entry::getKey,           // Mapping keys into the list
                                Collectors.toList()
                        )
                ));


        List<Emp3> list = Arrays.asList(new Emp3("amp", 100.0, 1),
                new Emp3("kct", 200.0, 2),
                new Emp3("bmp2", 300.7, 3),
                new Emp3("mpl", 400.4, 4));

        list.stream().map(Emp3::getSalary).sorted().forEach(System.out::println);

    }
}

class Emp3 {
    String name;
    Double salary;
    int dept;

    @Override
    public String toString() {
        return "emp{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", dept=" + dept +
                '}';
    }

    public Double getSalary() {
        return salary;
    }

    public int getDept() {
        return dept;
    }

    public String getName() {
        return name;
    }

    public Emp3(String name, Double salary, int dept) {
        this.name = name;
        this.salary = salary;
        this.dept = dept;
    }
}