using PoPLab3;
using System;
using System.Threading;

namespace PoPLab3
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.Write("Enter storage capacity: ");
            int storageSize = int.Parse(Console.ReadLine());

            Console.Write("Enter total number of items: ");
            int totalItems = int.Parse(Console.ReadLine());

            Console.Write("Enter number of Producers: ");
            int producersCount = int.Parse(Console.ReadLine());

            Console.Write("Enter number of Consumers: ");
            int consumersCount = int.Parse(Console.ReadLine());

            Console.WriteLine("\n--- Starting simulation ---");

            BoundedStorage storage = new BoundedStorage(storageSize);
            CountdownEvent countdown = new CountdownEvent(producersCount + consumersCount);

            int baseProducerItems = totalItems / producersCount;
            int remainderProducerItems = totalItems % producersCount;

            for (int i = 0; i < producersCount; i++)
            {
                int quota = baseProducerItems + (i < remainderProducerItems ? 1 : 0);
                WorkInfo info = new WorkInfo(i + 1, quota);
                Producer producer = new Producer(info, storage);

                new Thread(() =>
                {
                    producer.Run();
                    countdown.Signal();
                }).Start();
            }

            int baseConsumerItems = totalItems / consumersCount;
            int remainderConsumerItems = totalItems % consumersCount;

            for (int i = 0; i < consumersCount; i++)
            {
                int quota = baseConsumerItems + (i < remainderConsumerItems ? 1 : 0);
                WorkInfo info = new WorkInfo(i + 1, quota);
                Consumer consumer = new Consumer(info, storage);

                new Thread(() =>
                {
                    consumer.Run();
                    countdown.Signal();
                }).Start();
            }

            countdown.Wait();

            Console.WriteLine("--- All threads completed successfully. ---");
            Console.ReadKey();
        }
    }
}