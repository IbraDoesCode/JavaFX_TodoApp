package Controller;


import Service.TodoManager;
import Util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;


public class TodoItemController {

    @FXML
    private CheckBox taskCheckBox;
    private VBox parentVbox;
    private TodoManager todoManager;

    @FXML
    public void markTaskAsComplete() {

        boolean confirmed = AlertUtil.confirmAction("Confirm Task Completion",
                "Are you sure you want to mark this task as complete? This action cannot be undone.");


        if (taskCheckBox.isSelected()) {
            if (confirmed) {
                parentVbox.getChildren().remove(taskCheckBox.getParent());
                todoManager.updateTaskStatus(taskCheckBox.getText());
            } else {
                taskCheckBox.selectedProperty().setValue(false);
            }
        }
    }

    @FXML
    public void deleteTask() {

        boolean confirmed = AlertUtil.confirmAction("Confirm Task Deletion",
                "Are you sure you want to delete this task. This action cannot be undone.");

        if (confirmed) {
            parentVbox.getChildren().remove(taskCheckBox.getParent());
            todoManager.deleteTask(taskCheckBox.getText());
        }
    }

    public void setTaskText(String text) {
        taskCheckBox.setText(text);
    }

    public void setParentVbox(VBox parentVbox) {
        this.parentVbox = parentVbox;
    }

    public void setTodoManager(TodoManager todoManager) {
        this.todoManager = todoManager;
    }

}
