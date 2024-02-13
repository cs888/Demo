package pattern;

public class DecoratorPattern {
    public static void main(String[] args) {
        Car car=new LuxuryCar(new SportsCar(new BasicCar()));
        car.assemble();

    }
}

interface Car{
    void assemble();
}
class BasicCar implements Car{


    @Override
    public void assemble() {
        System.out.println("Basic Car");
    }
}
class CarDecorator implements Car{
     protected Car car;
     CarDecorator(Car car){
         this.car=car;
     }

    @Override
    public void assemble() {
        car.assemble();
    }
}

class SportsCar extends CarDecorator {

    SportsCar(Car car) {
        super(car);
    }
    @Override
    public void assemble() {
        super.assemble();
        System.out.println("Adding SportsCar features");
    }
}

class LuxuryCar extends CarDecorator {

    LuxuryCar(Car car) {
        super(car);
    }
    @Override
    public void assemble() {
        super.assemble();
        System.out.println("Adding LuxuryCar features");
    }
}