package model;

import java.time.LocalDateTime;

public record Todo(String id, String todo, LocalDateTime dateCreated, Status status, LocalDateTime dateCompleted) {

}
