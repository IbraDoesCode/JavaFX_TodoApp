package Service;

import Model.Status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TodoManager {

    private final FileService fileService;

    private final List<String[]> data;

    public TodoManager(FileService fileService) {
        this.fileService = fileService;
        this.data = fileService.readCsv();
    }

    public List<String[]> getPendingTasks() {
        List<String[]> pendingTasks = new ArrayList<>();
        for (String[] row : data) {
            if (row[2].equals(String.valueOf(Status.PENDING))) {
                pendingTasks.add(row);
            }
        }
        return pendingTasks;
    }

    public void addTask(String[] taskData) {
        // add the new task to the in-memory data list
        data.add(taskData);

        // append the new task to the CSV
        fileService.appendData(taskData);
    }

    public void deleteTask(String task) {
        // remove the task from in-memory data list
        data.removeIf(row -> row[0].equals(task));

        // write the updated data list back to the CSV
        fileService.writeData(data);
    }

    public void updateTaskStatus(String task) {
        for (String[] row : data) {
            if (row[0].equals(task)) {
                // update status
                row[2] = String.valueOf(Status.COMPLETE);
                // update dateCompleted
                row[3] = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
            }
            // Save the changes in the CSV
            fileService.writeData(data);
        }
    }

}
