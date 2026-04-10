import java.util.concurrent.CountDownLatch;

public class Consumer implements Runnable {
    private final WorkerInfo info;
    private final BoundedStorage storage;
    private final CountDownLatch latch;

    public Consumer(WorkerInfo info, BoundedStorage storage, CountDownLatch latch) {
        this.info = info;
        this.storage = storage;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= info.count(); i++) {
                storage.take(info.id()); 
                Thread.sleep(800); 
            }
            System.out.println("<-- Consumer " + info.id() + " has finished their quota.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            latch.countDown(); 
        }
    }
}