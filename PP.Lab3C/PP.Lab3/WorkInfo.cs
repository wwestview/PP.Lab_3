namespace PoPLab3
{
    public class WorkInfo
    {
        public int Id { get; }
        public int Quota { get; }

        public WorkInfo(int id, int quota)
        {
            Id = id;
            Quota = quota;
        }
    }
}