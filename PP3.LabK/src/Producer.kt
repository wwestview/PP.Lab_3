class Producer(private val info: WorkInfo, private val storage: BoundedStorage) : Runnable {
    override fun run() {
        for (i in 0 until info.quota) {
            Thread.sleep(500)
            storage.put(i, info.id)
        }
    }
}