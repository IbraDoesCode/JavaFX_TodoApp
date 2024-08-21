package Controller;

import Model.Status;
import Model.Todo;
import Service.TodoManager;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import Service.FileService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TodoController {

    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    @FXML
    public TextField textField;
    @FXML
    public Label taskCountLabel;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox vbox;

    private final FileService fileService = new FileService();

    private final TodoManager todoManager = new TodoManager(fileService);

    private final List<Todo> todoList = todoManager.getPendingTodos();

    @FXML
    public void initialize() {
        // Load tasks from CSV when initializing
        loadTodoFromCsv();

        updateTaskCount();
    }

    private void updateTaskCount() {
        vbox.getChildren().addListener((ListChangeListener<Node>) change -> updateTaskCount());

        int taskCount = vbox.getChildren().size();
        taskCountLabel.setText("You have " + taskCount + (taskCount == 1 ? " task!" : " tasks!"));
    }


    @FXML
    public void addTodo() {
        String todo = textField.getText().trim();
        if (!todo.isEmpty()) {
            try {
                // Load the TodoItem FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TodoItem.fxml"));
                Node todoItem = loader.load();

                // Get the controller of the TodoItem and set the todo text
                TodoItemController controller = loader.getController();
                controller.setCheckBoxText(todo);

                // Set the parent VBox and TodoManager
                controller.setParentVbox(vbox);
                controller.setTodoManager(todoManager);

                // Add the TodoItem to the VBox
                vbox.getChildren().add(todoItem);

                // Save the todo to CSV
                saveTodo(todo);

                // Clear the input field
                textField.clear();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadTodoFromCsv() {

        for (Todo todo : todoList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TodoItem.fxml"));
                Node todoItem = loader.load();

                TodoItemController controller = loader.getController();
                controller.setCheckBoxText(todo.getTodo());
                controller.setParentVbox(vbox);
                controller.setTodoManager(todoManager);

                // add the  todoItem to the vbox
                vbox.getChildren().add(todoItem);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveTodo(String todo) {
        String id = todoManager.generateId();
        LocalDateTime dateTime = LocalDateTime.now();
        Status status = Status.PENDING;
        todoManager.addTodo(new Todo(id, todo, dateTime, status, null));
    }

}
