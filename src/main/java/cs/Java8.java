package cs;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Java8 {
    public static void main(String[] args) {

        List<Emp> emps = Arrays.asList(new Emp("amp*", 100, 1),
                new Emp("amp*", 200, 2),
                new Emp("bmp2", 300, 3),
                new Emp("cmp4", 400, 4));

        UnaryOperator<Integer> unaryOperator = a -> a * 2;

        Map<Boolean, List<Emp>> collect = emps.stream().collect(Collectors.partitioningBy(e -> e.getSalary() > 30));


    }
}

class Emp {
    String name;
    int salary;
    int dept;

    @Override
    public String toString() {
        return "emp{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", dept=" + dept +
                '}';
    }

    public int getSalary() {
        return salary;
    }

    public int getDept() {
        return dept;
    }

    public String getName() {
        return name;
    }

    public Emp(String name, int salary, int dept) {
        this.name = name;
        this.salary = salary;
        this.dept = dept;
    }
}