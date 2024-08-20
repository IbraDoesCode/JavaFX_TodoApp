package Controller;

import Model.Status;
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

    @FXML
    public TextField taskTextField;
    @FXML
    public Label taskCountLabel;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox vbox;

    private final FileService fileService = new FileService();

    private final TodoManager todoManager = new TodoManager(fileService);

    private final List<String[]> tasks = todoManager.getPendingTasks();

    @FXML
    public void initialize() {
        // Load tasks from CSV when initializing
        loadTasksFromCsv();

        updateTaskCount();
    }

    private void updateTaskCount() {
        vbox.getChildren().addListener((ListChangeListener<Node>) change -> updateTaskCount());

        int taskCount = vbox.getChildren().size();
        taskCountLabel.setText("You have " + taskCount + (taskCount == 1 ? " task!" : " tasks!"));
    }


    @FXML
    public void addTodo() {
        String task = taskTextField.getText().trim();
        if (!task.isEmpty()) {
            try {
                // Load the TodoItem FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TodoItem.fxml"));
                Node todoItem = loader.load();

                // Get the controller of the TodoItem and set the task text
                TodoItemController controller = loader.getController();
                controller.setTaskText(task);

                // Set the parent VBox and TodoManager
                controller.setParentVbox(vbox);
                controller.setTodoManager(todoManager);

                // Add the TodoItem to the VBox
                vbox.getChildren().add(todoItem);

                // Save the task to CSV
                saveTaskToCsv(task);

                // Clear the input field
                taskTextField.clear();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadTasksFromCsv() {

        for (String[] task : tasks) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TodoItem.fxml"));
                Node todoItem = loader.load();

                TodoItemController controller = loader.getController();
                controller.setTaskText(task[0]);
                controller.setParentVbox(vbox);
                controller.setTodoManager(todoManager);

                // add the  todoItem to the vbox
                vbox.getChildren().add(todoItem);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void saveTaskToCsv(String task) {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
        String[] data = {task, dateTime, String.valueOf(Status.PENDING), ""};
        todoManager.addTask(data);
    }

}
