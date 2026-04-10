using PoPLab3;
using System;
using System.Threading;

namespace PoPLab3
{
    public class Consumer
    {
        private readonly WorkerInfo _info;
        private readonly BoundedStorage _storage;
        private readonly CountdownEvent _countdown;

        public Consumer(WorkerInfo info, BoundedStorage storage, CountdownEvent countdown)
        {
            _info = info;
            _storage = storage;
            _countdown = countdown;
        }

        public void Run()
        {
            try
            {
                for (int i = 1; i <= _info.Count; i++)
                {
                    _storage.Take(_info.Id); 
                    Thread.Sleep(800); 
                }
                Console.WriteLine($"<<< Consumer {_info.Id} has finished their quota.");
            }
            finally
            {
                _countdown.Signal();
            }
        }
    }
}