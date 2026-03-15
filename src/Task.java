public class Task {
    private int id;
    private String description;
    private String status;
    private String startDate;
    private String updatedDate;
    private String finishedDate;

    public Task(int id, String description, String status, String startDate, String updatedDate, String finishedDate) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.updatedDate = updatedDate;
        this.finishedDate = finishedDate;
    }
}

