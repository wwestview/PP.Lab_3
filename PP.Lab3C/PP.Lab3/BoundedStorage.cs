using System;
using System.Collections.Generic;
using System.Threading;

namespace PoPLab3
{
    public class BoundedStorage
    {
        private readonly SemaphoreSlim _access;
        private readonly SemaphoreSlim _fullSlots;
        private readonly SemaphoreSlim _emptySlots;
        private readonly Queue<string> _queue;

        public BoundedStorage(int capacity)
        {
            _access = new SemaphoreSlim(1, 1);
            _fullSlots = new SemaphoreSlim(capacity, capacity);
            _emptySlots = new SemaphoreSlim(0, capacity);
            _queue = new Queue<string>(capacity);
        }

        public void Put(string item, int producerId)
        {
            _fullSlots.Wait(); 
            _access.Wait();    

            try
            {
                _queue.Enqueue(item);
                Console.WriteLine($"Producer {producerId} added: {item}. Total in storage: {_queue.Count}");
            }
            finally
            {
                _access.Release();
                _emptySlots.Release(); 
            }
        }

        public string Take(int consumerId)
        {
            _emptySlots.Wait(); 
            _access.Wait();     

            try
            {
                string item = _queue.Dequeue();
                Console.WriteLine($"Consumer {consumerId} took: {item}. Remaining: {_queue.Count}");
                return item;
            }
            finally
            {
                _access.Release();
                _fullSlots.Release(); 
            }
        }
    }
}