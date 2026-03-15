package CLI;

import java.io.*;

public class TaskService {

    public static int getId() throws IOException {
        int maxId = 0;
        BufferedReader reader = new BufferedReader(new FileReader("tasks.json"));
        String line;
        while((line = reader.readLine()) != null) {
            if(line.trim().isEmpty()) continue;
            if(!line.contains("\"id\"")) continue;
            int id = Integer.parseInt(getField(line, "id"));

            if(id > maxId) {
                maxId = id;
            }
        }
        reader.close();
        return maxId;
    }

    public static void addTask(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("task description required");
            return;
        }
        int newId = getId() + 1;
        BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.json", true));
        writer.write("\n{\"id\":" + newId + ",\"description\":\"" + args[1] + "\",\"status\":\"todo\"}");
        writer.close();

        System.out.println("task added ");
    }

    public static void updateTask(String[] args) throws IOException {

        if (args.length < 3) {
            System.out.println("description required");
            return;
        }

        int num = Integer.parseInt(args[1]);
        String newDescription = args[2];

        BufferedReader reader = new BufferedReader(new FileReader("tasks.json"));
        String line;

        StringBuilder sb = new StringBuilder();

        boolean foundNum = false;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            int id = Integer.parseInt(getField(line, "id"));

            if (id == num) {
                foundNum = true;
                String status = getField(line, "status");
                String newLine =
                        "{\"id\":" + id +
                                ",\"description\":\"" + newDescription +
                                "\",\"status\":\"" + status + "\"}";

                sb.append(newLine).append("\n");

            } else {
                sb.append(line).append("\n");
            }
        }
        reader.close();

        if(!foundNum) {
            System.out.println("No tasks found with the given id");
        } else {
            System.out.println("task updated");
        }

        FileWriter writer = new FileWriter("tasks.json");
        writer.write(sb.toString());
        writer.close();
    }

    public static void removeTask(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("task id required");
            return;
        }

        int num = Integer.parseInt(args[1]);

        BufferedReader reader = new BufferedReader(new FileReader("tasks.json"));
        String line;

        StringBuilder sb = new StringBuilder();

        boolean foundNum = false;

        while ((line = reader.readLine()) != null) {

            if(line.trim().isEmpty()) continue;

            int id = Integer.parseInt(getField(line, "id"));

            if (id != num) {
                sb.append(line).append("\n");
            } else {
                foundNum = true;
            }
        }

        reader.close();

        if(!foundNum) {
            System.out.println("No tasks found");
        } else {
            System.out.println("task removed");
        }

        FileWriter writer = new FileWriter("tasks.json");
        writer.write(sb.toString());
        writer.close();
    }

    public static void listTasks() throws IOException {
        System.out.println("available tasks : ");

        BufferedReader reader = new BufferedReader(new FileReader("tasks.json"));
        String line;
        int count = 1;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            int id = Integer.parseInt(getField(line, "id"));
            String description = getField(line, "description");
            String status = getField(line, "status");

            System.out.println(count + ". " + "(" + id + ")" + " [" + status + "] " + description);

            count++;
        }

        reader.close();
    }

    public static void markTask(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("task id required");
            return;
        }

        int num = Integer.parseInt(args[1]);
        String newStatus = args[2];

        if (!newStatus.equals("todo") &&
                !newStatus.equals("done") &&
                !newStatus.equals("in-progress")) {

            System.out.println("Invalid status");
            return;
        }

        BufferedReader reader = new BufferedReader(new FileReader("tasks.json"));
        String line;

        StringBuilder sb = new StringBuilder();
        boolean foundNum = false;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            int id = Integer.parseInt(getField(line, "id"));

            if (id == num) {
                foundNum = true;

                String description = getField(line, "description");

                String newLine =
                        "{\"id\":" + id +
                                ",\"description\":\"" + description +
                                "\",\"status\":\"" + newStatus + "\"}";

                sb.append(newLine).append("\n");

            } else {
                sb.append(line).append("\n");
            }
        }
        reader.close();

        if(!foundNum) {
            System.out.println("No task found with the given id");
            return;
        } else {
            System.out.println("status updated");
        }

        FileWriter writer = new FileWriter("tasks.json");
        writer.write(sb.toString());
        writer.close();
    }

    public static void listByStatus(String filterStatus) throws IOException {

        System.out.println("tasks with status: " + filterStatus);

        BufferedReader reader = new BufferedReader(new FileReader("tasks.json"));
        String line;
        int count = 1;
        boolean foundStatus = false;
        while ((line = reader.readLine()) != null) {

            if (line.trim().isEmpty()) continue;

            int id = Integer.parseInt(getField(line, "id"));
            String description = getField(line, "description");
            String status = getField(line, "status");

            if (status.equals(filterStatus)) {
                foundStatus = true;
                System.out.println(count + ". (" + id + ") [" + status + "] " + description);
                count++;
            }
        }
        if(!foundStatus) {
            System.out.println("status not found");
        }
        reader.close();
    }

    private static String getField(String line, String key) {

        String pattern = "\"" + key + "\":";

        int start = line.indexOf(pattern) + pattern.length();

        if(key.equals("id")) {
            int end = line.indexOf(",", start);
            return line.substring(start, end);
        } else {
            start += 1; // skip opening quote
            int end = line.indexOf("\"", start);
            return line.substring(start, end);
        }
    }
}
