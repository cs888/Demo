package pattern;

public class FacadePattern {
    public static void main(String[] args) {
        Facade facade = new Facade();
        //do multiple operaation at once
        facade.operation();
    }
}

// Complex subsystem classes
class SubsystemClass1 {
    public void operation1() {
        System.out.println("SubsystemClass1 operation1");
    }
}

class SubsystemClass2 {
    public void operation2() {
        System.out.println("SubsystemClass2 operation2");
    }
}

class SubsystemClass3 {
    public void operation3() {
        System.out.println("SubsystemClass3 operation3");
    }
}

// Facade
class Facade {
    private SubsystemClass1 subsystem1;
    private SubsystemClass2 subsystem2;
    private SubsystemClass3 subsystem3;

    public Facade() {
        subsystem1 = new SubsystemClass1();
        subsystem2 = new SubsystemClass2();
        subsystem3 = new SubsystemClass3();
    }

    public void operation() {
        subsystem1.operation1();
        subsystem2.operation2();
        subsystem3.operation3();
    }
}
