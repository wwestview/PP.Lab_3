using PoPLab3;
using System;
using System.Threading;

namespace PoPLab3
{
    class Program
    {
        static void Main()
        {
            int storageLimit = 5;
            int producersCount = 3;
            int consumersCount = 2;
            int itemsPerProducer = 4;
            int itemsPerConsumer = 6;

            var storage = new BoundedStorage(storageLimit);

            var countdown = new CountdownEvent(producersCount + consumersCount);

            Console.WriteLine("--- - Starting Operation - ---");

            for (int i = 1; i <= producersCount; i++)
            {
                var info = new WorkerInfo(i, itemsPerProducer);
                var producer = new Producer(info, storage, countdown);
                new Thread(producer.Run).Start();
            }

            for (int i = 1; i <= consumersCount; i++)
            {
                var info = new WorkerInfo(i, itemsPerConsumer);
                var consumer = new Consumer(info, storage, countdown);
                new Thread(consumer.Run).Start();
            }

            countdown.Wait();

            Console.WriteLine("--- - All operations completed successfully - ---");
        }
    }
}