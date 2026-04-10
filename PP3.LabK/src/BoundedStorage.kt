import java.util.ArrayDeque
import java.util.Queue
import java.util.concurrent.Semaphore

class BoundedStorage(capacity: Int) {
    private val access = Semaphore(1)
    private val fullSlots = Semaphore(capacity)
    private val emptySlots = Semaphore(0)
    
    private val queue: Queue<String> = ArrayDeque(capacity)

    fun put(item: String, producerId: Int) {
        fullSlots.acquire()
        access.acquire()
        
        try {
            queue.add(item)
            println("Producer $producerId added: $item. Total in storage: ${queue.size}")
        } finally {
            access.release()
            emptySlots.release()
        }
    }

    fun take(consumerId: Int): String {
        emptySlots.acquire()
        access.acquire()
        
        try {
            val item = queue.poll()
            println("Consumer $consumerId took: $item. Remaining: ${queue.size}")
            return item
        } finally {
            access.release()
            fullSlots.release()
        }
    }
}