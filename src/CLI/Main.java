package CLI;

import java.io.IOException;

import static CLI.TaskService.listTasks;
import static CLI.TaskService.removeTask;

public class Main {
    public static void main(String[] args) throws IOException {

        FileService.initializeFile();

        if (args.length == 0) {
            System.out.println("No command provided");
            return;
        }
        String command = args[0];
        switch (command) {
            case "add" -> TaskService.addTask(args);
            case "list" -> TaskService.listTasks();
            case "remove" -> TaskService.removeTask(args);
            case "update" -> TaskService.updateTask(args);

            case "mark" -> TaskService.markTask(args);
            case "mark-done" -> TaskService.markTask(new String[]{args[0], args[1], "done"});
            case "mark-in-progress" -> TaskService.markTask(new String[]{args[0], args[1], "in-progress"});

            case "list-done" -> TaskService.listByStatus("done");
            case "list-todo" -> TaskService.listByStatus("todo");
            case "list-in-progress" -> TaskService.listByStatus("in-progress");

            default -> System.out.println("Unknown command");
        }
    }
}