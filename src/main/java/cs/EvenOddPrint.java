package cs;

public class EvenOddPrint {
    public static void main(String[] args) {

        PrintingClass p = new PrintingClass();
        Thread t1 = new Thread(
                () -> {
                    try {
                        p.evenprint();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

        Thread t2 = new Thread(
                () -> {
                    try {
                        p.oddprint();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

        t1.start();
        t2.start();

    }


}

class PrintingClass {
    int counter = 1;

    synchronized void evenprint() throws InterruptedException {
        while (counter < 15) {
            while (counter % 2 != 0) {
                wait();
            }
            System.out.println("even:" + counter++);
            notify();
        }
    }

    synchronized void oddprint() throws InterruptedException {
        while (counter < 15) {
            while (counter % 2 == 0) {
                wait();
            }
            System.out.println("Odd:" + counter++);
            notify();
        }
    }
}