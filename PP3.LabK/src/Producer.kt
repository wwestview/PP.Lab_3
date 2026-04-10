import java.util.concurrent.CountDownLatch

class Producer(
    private val info: WorkerInfo,
    private val storage: BoundedStorage,
    private val latch: CountDownLatch
) : Runnable {

    override fun run() {
        try {
            for (i in 1..info.count) {
                val item = "Item $i from Producer #${info.id}"
                storage.put(item, info.id)
                Thread.sleep(500)
            }
            println(">>> Producer ${info.id} has finished their quota.")
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        } finally {
            latch.countDown()
        }
    }
}