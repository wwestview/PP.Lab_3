using PoPLab3;
using System;
using System.Threading;

namespace PoPLab3
{
    public class Producer
    {
        private readonly WorkerInfo _info;
        private readonly BoundedStorage _storage;
        private readonly CountdownEvent _countdown;

        public Producer(WorkerInfo info, BoundedStorage storage, CountdownEvent countdown)
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
                    string item = $"Item {i} from Producer #{_info.Id}";
                    _storage.Put(item, _info.Id); 
                    Thread.Sleep(500); 
                }
                Console.WriteLine($">>> Producer {_info.Id} has finished their quota.");
            }
            finally
            {
                _countdown.Signal();
            }
        }
    }
}