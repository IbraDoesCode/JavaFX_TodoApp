package model;

import java.time.LocalDateTime;

public class Todo {

    private String id;
    private LocalDateTime dateCreated;
    private Status status;
    private LocalDateTime dateCompleted;
    private String todo;

    public Todo(String id, String todo, LocalDateTime dateCreated, Status status, LocalDateTime dateCompleted) {
        this.id = id;
        this.todo = todo;
        this.dateCreated = dateCreated;
        this.status = status;
        this.dateCompleted = dateCompleted;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public String getId() {
        return id;
    }

    public String getTodo() {
        return todo;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getDateCompleted() {
        return dateCompleted;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id='" + id + '\'' +
                ", todo='" + todo + '\'' +
                ", dateCreated=" + dateCreated +
                ", status=" + status +
                ", dateCompleted=" + dateCompleted +
                '}';
    }
}
