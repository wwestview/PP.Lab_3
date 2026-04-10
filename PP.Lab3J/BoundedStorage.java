import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BoundedStorage {
    private final Queue<Integer> queue = new LinkedList<>();
    private final Semaphore access = new Semaphore(1);
    private final Semaphore fullSlots;
    private final Semaphore emptySlots = new Semaphore(0);

    public BoundedStorage(int capacity) {
        this.fullSlots = new Semaphore(capacity);
    }

    public void put(int item, int producerId) {
        try {
            fullSlots.acquire();
            access.acquire();
            queue.add(item);
            System.out.println("Producer " + producerId + " added item " + item + ". Storage: " + queue.size());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            access.release();
            emptySlots.release();
        }
    }

    public void take(int consumerId) {
        try {
            emptySlots.acquire();
            access.acquire();
            int item = queue.poll();
            System.out.println("Consumer " + consumerId + " took item " + item + ". Storage: " + queue.size());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            access.release();
            fullSlots.release();
        }
    }
}