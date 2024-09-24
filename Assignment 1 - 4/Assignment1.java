import java.util.Scanner;
import java.util.concurrent.*;

class Q {
    // an item
    int item;

    // semCon initialized with 0 permits
    // to ensure put() executes first
    static Semaphore semCon = new Semaphore(0);
    static Semaphore semProd = new Semaphore(1);

    // to get an item from buffer
    void get() {
        try {
            // Before consumer can consume an item, it must acquire a permit from semCon
            semCon.acquire();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }

        // consumer consuming an item
        System.out.println("Consumer consumed item : " + item);

        // After consumer consumes the item, it releases semProd to notify producer
        semProd.release();
    }

    // to put an item in buffer
    void put(int item) {
        try {
            // Before producer can produce an item, it must acquire a permit from semProd
            semProd.acquire();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        }

        // producer producing an item
        this.item = item;

        System.out.println("Producer produced item : " + item);

        // After producer produces the item, it releases semCon to notify consumer
        semCon.release();
    }
}

// Producer class
class Producer1 implements Runnable {
    Q q;

    Producer1(Q q) {
        this.q = q;
        new Thread(this, "Producer").start();
    }

    public void run() {
        for (int i = 0; i < 5; i++)
            // producer put items
            q.put(i);
    }
}

// Consumer class
class Consumer1 implements Runnable {
    Q q;

    Consumer1(Q q) {
        this.q = q;
        new Thread(this, "Consumer").start();
    }

    public void run() {
        for (int i = 0; i < 5; i++)
            // consumer get items
            q.get();
    }
}
class ProducerConsumer {
    private static BlockingQueue<Integer> Buffer = new LinkedBlockingDeque<>();
    private static Semaphore emptySlots = new Semaphore(10);
    private static Semaphore fullSlots = new Semaphore(0);
    private static volatile boolean terminate = false; // Termination signal

    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                while (!terminate) { // Check termination signal
                    int item = produceItem();
                    emptySlots.acquire();
                    Buffer.put(item);
                    fullSlots.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private int produceItem() {
            int num = (int) (Math.random() * 100);
            System.out.println("Produced: " + num);
            return num;
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                while (!terminate) { // Check termination signal
                    fullSlots.acquire();
                    int item = Buffer.take();
                    emptySlots.release();
                    consumeItem(item);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void consumeItem(int item) {
            System.out.println("Consumed: " + item);
        }
    }

    // Method to signal termination
    public void terminate() {
        terminate = true;
    }
}

public class Assignment1 {
    public static void main(String args[]) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        while(true) {
            System.out.println("Select Operation\n1-Semaphore\n2-Mutex\n3-Exit");
            choice = sc.nextInt();

            if (choice == 1) {
                Q q = new Q();
                new Consumer1(q);
                new Producer1(q);
            } else if (choice == 2) {
                ProducerConsumer pc = new ProducerConsumer();
                Thread producerThread = new Thread(pc.new Producer());
                Thread consumerThread = new Thread(pc.new Consumer());

                producerThread.start();
                consumerThread.start();

                // Let threads continue running until termination is signaled
                while (true) {
                    System.out.println("Press 'e' to exit");
                    if (sc.next().equalsIgnoreCase("e")) {
                        pc.terminate(); // Signal termination
                        break;
                    }
                }

                // Wait for threads to terminate
                try {
                    producerThread.join();
                    consumerThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Main thread interrupted");
                }
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break; // Exit the loop and terminate the program
            }
        }
    }
}

