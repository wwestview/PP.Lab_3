import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

fun main() {
    print("Enter storage capacity: ")
    val storageSize = readln().toInt()

    print("Enter total number of items: ")
    val totalItems = readln().toInt()

    print("Enter number of Producers: ")
    val producersCount = readln().toInt()

    print("Enter number of Consumers: ")
    val consumersCount = readln().toInt()

    println("\n--- Starting simulation (Kotlin) ---")

    val storage = BoundedStorage(storageSize)
    val latch = CountDownLatch(producersCount + consumersCount)

    val baseProducerItems = totalItems / producersCount
    val remainderProducerItems = totalItems % producersCount

    for (i in 0 until producersCount) {
        val quota = baseProducerItems + if (i < remainderProducerItems) 1 else 0
        val info = WorkInfo(i + 1, quota)
        val producer = Producer(info, storage)

        thread {
            producer.run()
            latch.countDown()
        }
    }

    val baseConsumerItems = totalItems / consumersCount
    val remainderConsumerItems = totalItems % consumersCount

    for (i in 0 until consumersCount) {
        val quota = baseConsumerItems + if (i < remainderConsumerItems) 1 else 0
        val info = WorkInfo(i + 1, quota)
        val consumer = Consumer(info, storage)

        thread {
            consumer.run()
            latch.countDown()
        }
    }

    latch.await()

    println("--- All threads completed successfully. ---")
}1