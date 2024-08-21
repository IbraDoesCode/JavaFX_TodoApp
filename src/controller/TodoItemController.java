package controller;


import service.TodoManager;
import util.AlertUtil;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;


public class TodoItemController {

    @FXML
    private CheckBox todoCheckBox;
    private VBox parentVbox;
    private TodoManager todoManager;

    public void setParentVbox(VBox parentVbox) {
        this.parentVbox = parentVbox;
    }

    public void setTodoManager(TodoManager todoManager) {
        this.todoManager = todoManager;
    }

    public void setCheckBoxText(String todo) {
        todoCheckBox.setText(todo);
    }

    @FXML
    public void markAsComplete() {

        boolean confirmed = AlertUtil.confirmAction("Confirm Task Completion",
                "Are you sure you want to mark this task as complete? This action cannot be undone.");


        if (todoCheckBox.isSelected()) {
            if (confirmed) {
                parentVbox.getChildren().remove(todoCheckBox.getParent());
                todoManager.updateTodoStatus(todoCheckBox.getText());
            } else {
                todoCheckBox.selectedProperty().setValue(false);
            }
        }
    }

    @FXML
    public void removeTodo() {

        boolean confirmed = AlertUtil.confirmAction("Confirm Task Deletion",
                "Are you sure you want to delete this task. This action cannot be undone.");

        if (confirmed) {
            parentVbox.getChildren().remove(todoCheckBox.getParent());
            todoManager.deleteTodo(todoCheckBox.getText());
        }
    }

}
