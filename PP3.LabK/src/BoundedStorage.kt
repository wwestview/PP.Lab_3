import java.util.LinkedList
import java.util.Queue
import java.util.concurrent.Semaphore

class BoundedStorage(capacity: Int) {
    private val queue: Queue<Int> = LinkedList()
    private val access = Semaphore(1)
    private val fullSlots = Semaphore(capacity)
    private val emptySlots = Semaphore(0)

    fun put(item: Int, producerId: Int) {
        try {
            fullSlots.acquire()
            access.acquire()
            queue.add(item)
            println("Producer $producerId added item $item. Storage: ${queue.size}")
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        } finally {
            access.release()
            emptySlots.release()
        }
    }

    fun take(consumerId: Int) {
        try {
            emptySlots.acquire()
            access.acquire()
            val item = queue.poll()
            println("Consumer $consumerId took item $item. Storage: ${queue.size}")
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        } finally {
            access.release()
            fullSlots.release()
        }
    }
}