public class WorkInfo {
    private final int id;
    private final int quota;

    public WorkInfo(int id, int quota) {
        this.id = id;
        this.quota = quota;
    }

    public int getId() { return id; }
    public int getQuota() { return quota; }
}