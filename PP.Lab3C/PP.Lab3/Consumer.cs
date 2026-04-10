using PoPLab3;

namespace PoPLab3
{
    public class Consumer
    {
        private readonly WorkInfo _info;
        private readonly BoundedStorage _storage;

        public Consumer(WorkInfo info, BoundedStorage storage)
        {
            _info = info;
            _storage = storage;
        }

        public void Run()
        {
            for (int i = 0; i < _info.Quota; i++)
            {
                Thread.Sleep(500);
                _storage.Take(_info.Id);
            }
        }
    }
}