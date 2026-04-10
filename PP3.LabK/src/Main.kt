import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

fun main() {
    val storageLimit = 5
    val producersCount = 3
    val consumersCount = 2
    val itemsPerProducer = 4
    val itemsPerConsumer = 6

    val storage = BoundedStorage(storageLimit)
    
    val latch = CountDownLatch(producersCount + consumersCount)

    println("--- - Starting Operation - ---")

    for (i in 1..producersCount) {
        val info = WorkerInfo(id = i, count = itemsPerProducer)
        thread { Producer(info, storage, latch).run() }
    }

    for (i in 1..consumersCount) {
        val info = WorkerInfo(id = i, count = itemsPerConsumer)
        thread { Consumer(info, storage, latch).run() }
    }

    try {
        latch.await()
    } catch (e: InterruptedException) {
        Thread.currentThread().interrupt()
    }

    println("--- - All operations completed successfully - ---")
}