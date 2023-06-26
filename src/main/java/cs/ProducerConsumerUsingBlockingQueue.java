package cs;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumerUsingBlockingQueue {
    public static void main(String[] args) {
        BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>();
        ProducerUsingBlockingQ producerUsingBlockingQ = new ProducerUsingBlockingQ(sharedQueue);
        ConsumerUingBlockingQ consumerUingBlockingQ = new ConsumerUingBlockingQ(sharedQueue);

        producerUsingBlockingQ.start();
        consumerUingBlockingQ.start();

    }
}

class ProducerUsingBlockingQ extends Thread {

    BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>();

    public ProducerUsingBlockingQ(BlockingQueue<Integer> sharedQueue) {
        super("PRODUCER");
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {

        for (int i = 0; i < 11; i++) {
            System.out.println("Producing:" + i);
            try {
                sharedQueue.put(i);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class ConsumerUingBlockingQ extends Thread {

    BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>();

    public ConsumerUingBlockingQ(BlockingQueue<Integer> sharedQueue) {
        super("CONSUMER");
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {

        while (true) {
            try {
                System.out.println("Consuming:" + sharedQueue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
