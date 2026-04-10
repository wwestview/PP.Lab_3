import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        int storageLimit = 5;
        int producersCount = 3;
        int consumersCount = 2;
        int itemsPerProducer = 4;
        int itemsPerConsumer = 6;

        BoundedStorage storage = new BoundedStorage(storageLimit);

        CountDownLatch latch = new CountDownLatch(producersCount + consumersCount);

        System.out.println("--- - Starting Operation - ---");

        for (int i = 1; i <= producersCount; i++) {
            WorkerInfo info = new WorkerInfo(i, itemsPerProducer);
            Thread thread = new Thread(new Producer(info, storage, latch));
            thread.start();
        }

        for (int i = 1; i <= consumersCount; i++) {
            WorkerInfo info = new WorkerInfo(i, itemsPerConsumer);
            Thread thread = new Thread(new Consumer(info, storage, latch));
            thread.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("--- - All operations completed successfully - ---");
    }
}