import java.util.concurrent.CountDownLatch;

public class Producer implements Runnable {
    private final WorkerInfo info;
    private final BoundedStorage storage;
    private final CountDownLatch latch;

    public Producer(WorkerInfo info, BoundedStorage storage, CountDownLatch latch) {
        this.info = info;
        this.storage = storage;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= info.count(); i++) {
                String item = "Item " + i;
                storage.put(item, info.id()); 
                Thread.sleep(500); 
            }
            System.out.println(">>> Producer " + info.id() + " has finished their quota.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            latch.countDown(); 
        }
    }
}