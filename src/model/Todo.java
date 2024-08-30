package model;

import java.time.LocalDateTime;

public class Todo {

    private final String id;
    private final LocalDateTime dateCreated;
    private final Status status;
    private final LocalDateTime dateCompleted;
    private final String todo;

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

}
