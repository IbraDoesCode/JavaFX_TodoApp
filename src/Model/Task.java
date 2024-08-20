package Model;

import java.time.LocalDate;

public class Task {

    private String taskId;
    private String task;
    private LocalDate dateCreated;
    private Status status;
    private LocalDate dateCompleted;

    public Task(String taskId, String task) {
        this.taskId = taskId;
        this.task = task;
        this.dateCreated = LocalDate.now();
        this.status = Status.PENDING;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", task='" + task + '\'' +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                ", dateCompleted=" + dateCompleted +
                '}';
    }

}
