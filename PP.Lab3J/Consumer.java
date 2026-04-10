public class Consumer implements Runnable {
    private final WorkInfo info;
    private final BoundedStorage storage;

    public Consumer(WorkInfo info, BoundedStorage storage) {
        this.info = info;
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < info.getQuota(); i++) {
            try { Thread.sleep(800); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            storage.take(info.getId());
        }
    }
}