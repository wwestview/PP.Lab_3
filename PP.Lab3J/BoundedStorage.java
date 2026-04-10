import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BoundedStorage {
    private final Semaphore access;
    private final Semaphore fullSlots;
    private final Semaphore emptySlots;
    private final Queue<String> queue;

    public BoundedStorage(int capacity) {
        this.access = new Semaphore(1);
        this.fullSlots = new Semaphore(capacity);
        this.emptySlots = new Semaphore(0);
        this.queue = new ArrayDeque<>(capacity);
    }

    
    public void put(String item, int producerId) throws InterruptedException {
        fullSlots.acquire(); 
        access.acquire();    

        try {
            queue.add(item);
            System.out.println("Producer " + producerId + " added: " + item + ". Total in storage: " + queue.size());
        } finally {
            access.release();
            emptySlots.release(); 
        }
    }

    public String take(int consumerId) throws InterruptedException {
        emptySlots.acquire(); 
        access.acquire();     

        try {
            String item = queue.poll();
            System.out.println("Consumer " + consumerId + " took: " + item + ". Remaining: " + queue.size());
            return item;
        } finally {
            access.release();
            fullSlots.release(); 
        }
    }
}