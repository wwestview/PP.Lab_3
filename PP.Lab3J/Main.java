import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter storage capacity: ");
        int storageSize = scanner.nextInt();

        System.out.print("Enter total number of items: ");
        int totalItems = scanner.nextInt();

        System.out.print("Enter number of Producers: ");
        int producersCount = scanner.nextInt();

        System.out.print("Enter number of Consumers: ");
        int consumersCount = scanner.nextInt();

        System.out.println("\n--- Starting simulation (Java) ---");

        BoundedStorage storage = new BoundedStorage(storageSize);
        CountDownLatch latch = new CountDownLatch(producersCount + consumersCount);

        int baseProducerItems = totalItems / producersCount;
        int remainderProducerItems = totalItems % producersCount;

        for (int i = 0; i < producersCount; i++) {
            int quota = baseProducerItems + (i < remainderProducerItems ? 1 : 0);
            WorkInfo info = new WorkInfo(i + 1, quota);
            Producer producer = new Producer(info, storage);

            new Thread(() -> {
                producer.run();
                latch.countDown();
            }).start();
        }

        int baseConsumerItems = totalItems / consumersCount;
        int remainderConsumerItems = totalItems % consumersCount;

        for (int i = 0; i < consumersCount; i++) {
            int quota = baseConsumerItems + (i < remainderConsumerItems ? 1 : 0);
            WorkInfo info = new WorkInfo(i + 1, quota);
            Consumer consumer = new Consumer(info, storage);

            new Thread(() -> {
                consumer.run();
                latch.countDown();
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("--- All threads completed successfully. ---");
    }
}