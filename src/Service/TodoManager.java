package Service;

import Model.Status;
import Model.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TodoManager {

    private final FileService fileService;
    private final List<String[]> todoList;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    public TodoManager(FileService fileService) {
        this.fileService = fileService;
        this.todoList = fileService.readCsv();
    }

    public String generateId() {
        int todoNum = todoList.isEmpty() ? 1 : Integer.parseInt(todoList.getLast()[0].split("-")[1]) + 1;
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM:dd:yy")) + "-" + todoNum;
    }

    public List<Todo> getPendingTodos() {
        List<Todo> pendingTodos = new ArrayList<>();
        for (String[] row : todoList) {
            if (row[3].equals(String.valueOf(Status.PENDING))) {
                pendingTodos.add(
                        new Todo(row[0],
                                row[1],
                                LocalDateTime.parse(row[2], DATE_FORMAT),
                                Status.valueOf(row[3]),
                                null
                        ));
            }
        }
        return pendingTodos;
    }

    public String[] convertTodoToArray(Todo todo) {
        return new String[]{
                todo.getId(),
                todo.getTodo(),
                todo.getDateCreated().format(DATE_FORMAT),
                String.valueOf(todo.getStatus()),
                String.valueOf(todo.getDateCompleted())
        };
    }

    public void addTodo(Todo todo) {
        // add the new task to the in-memory data list
        todoList.add(convertTodoToArray(todo));

        // append the new task to the CSV
        fileService.appendData(convertTodoToArray(todo));
    }

    public void deleteTodo(String todo) {
        todoList.removeIf(row -> row[1].equals(todo));

        // write the updated data list back to the CSV
        fileService.writeData(todoList);
    }

    public void updateTodoStatus(String todo) {
        for (String[] row : todoList) {
            if (row[1].equals(todo)) {
                // update status
                row[3] = String.valueOf(Status.COMPLETE);
                // update dateCompleted
                row[4] = LocalDateTime.now().format(DATE_FORMAT);
            }
            // Save the changes in the CSV
            fileService.writeData(todoList);
        }
    }

}
