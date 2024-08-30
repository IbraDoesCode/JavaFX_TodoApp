package service;

import model.Status;
import model.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TodoManager {

    private final FileService fileService;
    private final List<String[]> todoData;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    public TodoManager(FileService fileService) {
        this.fileService = fileService;
        this.todoData = fileService.readCsv();
    }

    public String generateId() {
        int todoNum = todoData.isEmpty() ? 1 : Integer.parseInt(todoData.getLast()[0].split("-")[1]) + 1;
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM:dd:yy")) + "-" + todoNum;
    }

    public List<Todo> getPendingTodos() {
        List<Todo> pendingTasks = new ArrayList<>();

        for (String[] row : todoData) {
            if (row[3].equals("PENDING")) {
                pendingTasks.add(new Todo(row[0],
                        row[1],
                        LocalDateTime.parse(row[2], DATE_FORMAT),
                        Status.valueOf(row[3]),
                        null));
            }
        }

        return pendingTasks;
    }

    public String[] convertTodoToArray(Todo todo) {
        return new String[]{
                todo.id(),
                todo.todo(),
                todo.dateCreated().format(DATE_FORMAT),
                String.valueOf(todo.status()),
                String.valueOf(todo.dateCompleted())
        };
    }

    public void addTodo(Todo todo) {
        // add the new task to the in-memory data list
        todoData.add(convertTodoToArray(todo));

        // append the new task to the CSV
        fileService.appendData(convertTodoToArray(todo));
    }

    public void deleteTodo(String todo) {
        todoData.removeIf(row -> row[1].equals(todo));

        // write the updated data list back to the CSV
        fileService.writeData(todoData);
    }

    public void updateTodoStatus(String todo) {
        for (String[] row : todoData) {
            if (row[1].equals(todo)) {
                // update status
                row[3] = String.valueOf(Status.COMPLETE);
                // update dateCompleted
                row[4] = LocalDateTime.now().format(DATE_FORMAT);
            }
        }
        // Save the changes in the CSV
        fileService.writeData(todoData);
    }

}
