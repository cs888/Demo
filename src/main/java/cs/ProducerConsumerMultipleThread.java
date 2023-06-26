package cs;

public class ProducerConsumerMultipleThread {
    public static void main(String[] args) {

        Object monitor = new Object();
        Runnable r1 = new Sequence(0, monitor);
        Runnable r2 = new Sequence(1, monitor);
        Runnable r3 = new Sequence(2, monitor);

        Thread t1 = new Thread(r1, "thread-1");
        Thread t2 = new Thread(r2, "thread-2");
        Thread t3 = new Thread(r3, "thread-3");
        t1.start();
        t2.start();
        t3.start();
    }
}


class Sequence implements Runnable {
    static int counter = 1;
    int threadnumber;
    Object monitor;

    public Sequence(int threadNumber, Object monitor) {
        this.threadnumber = threadNumber;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (counter < 13) {
            synchronized (monitor) {
                while (counter % 3 != threadnumber) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " " + counter);
                monitor.notifyAll();
                //nextGreaterElement thread
                counter++;
            }
        }
    }
}
