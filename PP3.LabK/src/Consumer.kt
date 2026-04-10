import java.util.concurrent.CountDownLatch

class Consumer(
    private val info: WorkerInfo,
    private val storage: BoundedStorage,
    private val latch: CountDownLatch
) : Runnable {

    override fun run() {
        try {
            for (i in 1..info.count) {
                storage.take(info.id)
                Thread.sleep(800)
            }
            println("<<< Consumer ${info.id} has finished their quota.")
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        } finally {
            latch.countDown()
        }
    }
}