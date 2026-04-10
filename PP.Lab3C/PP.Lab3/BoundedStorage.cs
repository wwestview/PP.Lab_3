using System;
using System.Collections.Generic;
using System.Threading;

namespace PoPLab3
{
    public class BoundedStorage
    {
        private readonly Queue<int> _queue = new Queue<int>();
        private readonly SemaphoreSlim _access = new SemaphoreSlim(1, 1);
        private readonly SemaphoreSlim _fullSlots;
        private readonly SemaphoreSlim _emptySlots;

        public BoundedStorage(int capacity)
        {
            _fullSlots = new SemaphoreSlim(capacity, capacity);
            _emptySlots = new SemaphoreSlim(0, capacity);
        }

        public void Put(int item, int producerId)
        {
            _fullSlots.Wait();
            _access.Wait();
            try
            {
                _queue.Enqueue(item);
                Console.WriteLine($"Producer {producerId} added item ¹{item}. Storage: {_queue.Count}");
            }
            finally
            {
                _access.Release();
                _emptySlots.Release();
            }
        }

        public void Take(int consumerId)
        {
            _emptySlots.Wait();
            _access.Wait();
            try
            {
                int item = _queue.Dequeue();
                Console.WriteLine($"Consumer {consumerId} took item ¹{item}. Storage: {_queue.Count}");
            }
            finally
            {
                _access.Release();
                _fullSlots.Release();
            }
        }
    }
}