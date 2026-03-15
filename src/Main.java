import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        initializeFile();

        if (args.length == 0) {
            System.out.println("No command provided");
            return;
        }
        String command = args[0];
        switch (command) {
            case "add" -> addTask(args);
            case "list" -> listTasks();
            case "remove" -> removeTask(args);
            case "update" -> updateTask(args);

            case "mark" -> markTask(args);
            case "mark-done" -> markTask(new String[]{args[0], args[1], "done"});
            case "mark-in-progress" -> markTask(new String[]{args[0], args[1], "in-progress"});

            case "list-done" -> listByStatus("done");
            case "list-todo" -> listByStatus("todo");
            case "list-in-progress" -> listByStatus("in-progress");

            default -> System.out.println("Unknown command");
        }
    }

    private static void updateTask(String[] args) throws IOException {

        if (args.length < 3) {
            System.out.println("new description required");
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

            String key = "\"id\":";
            int start = line.indexOf(key) + key.length();
            int end = line.indexOf(",", start);
            String idStr = line.substring(start, end);

            int id = Integer.parseInt(idStr);

            if (id == num) {
                foundNum = true;
                String statusKey = "\"status\":\"";
                int sStart = line.indexOf(statusKey) + statusKey.length();
                int sEnd = line.indexOf("\"", sStart);
                String status = line.substring(sStart, sEnd);

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


    private static int getId() throws IOException {
        int maxId = 0;
        BufferedReader reader = new BufferedReader(new FileReader("tasks.json"));
        String line;
        while((line = reader.readLine()) != null) {
            if(line.trim().isEmpty()) continue;
            if(!line.contains("\"id\"")) continue;
            String tId = line.substring(line.indexOf(":") + 1, line.indexOf(","));
            int id = Integer.parseInt(tId);
            if(id > maxId) {
                maxId = id;
            }
        }
        reader.close();
        return maxId;
    }

    private static void removeTask(String[] args) throws IOException {

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

            String key = "\"id\":";
            int start = line.indexOf(key) + key.length();
            int end = line.indexOf(",", start);
            String idStr = line.substring(start, end);

            int id = Integer.parseInt(idStr);

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

    private static void listTasks() throws IOException {
        System.out.println("available tasks : ");

        BufferedReader reader = new BufferedReader(new FileReader("tasks.json"));
        String line;
        int count = 1;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            String key = "\"description\":\"";
            int start = line.indexOf(key) + key.length();
            int end = line.indexOf("\"", start);
            String description = line.substring(start, end);

            String key2 = "\"status\":\"";
            int start2 = line.indexOf(key2) + key2.length();
            int end2 = line.indexOf("\"", start2);
            String status = line.substring(start2, end2);

            String idKey = "\"id\":";
            int idStart = line.indexOf(idKey) + idKey.length();
            int idEnd = line.indexOf(",", idStart);
            String id = line.substring(idStart, idEnd);

            System.out.println(count + ". " + "(" + id + ")" + " [" + status + "] " + description);

            count++;
        }

        reader.close();
    }

    private static void markTask(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("task id required");
            return;
        }
        int num = Integer.parseInt(args[1]);
        String newStatus = args[2];

        BufferedReader reader = new BufferedReader(new FileReader("tasks.json"));
        String line;

        StringBuilder sb = new StringBuilder();
        boolean foundNum = false;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            String key = "\"id\":";
            int start = line.indexOf(key) + key.length();
            int end = line.indexOf(",", start);
            String idStr = line.substring(start, end);

            int id = Integer.parseInt(idStr);

            if (id == num) {
                foundNum = true;
                String descKey = "\"description\":\"";
                int dStart = line.indexOf(descKey) + descKey.length();
                int dEnd = line.indexOf("\"", dStart);
                String description = line.substring(dStart, dEnd);

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


    private static void addTask(String[] args) throws IOException {
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

    private static void initializeFile() throws IOException {
        File file = new File("tasks.json");

        if(!file.exists()) {
            file.createNewFile();
            System.out.println("Successfully new file created");
        }
    }

    private static void listByStatus(String filterStatus) throws IOException {

        System.out.println("tasks with status: " + filterStatus);

        BufferedReader reader = new BufferedReader(new FileReader("tasks.json"));
        String line;
        int count = 1;

        while ((line = reader.readLine()) != null) {

            if (line.trim().isEmpty()) continue;

            String descKey = "\"description\":\"";
            int start = line.indexOf(descKey) + descKey.length();
            int end = line.indexOf("\"", start);
            String description = line.substring(start, end);

            String statusKey = "\"status\":\"";
            int start2 = line.indexOf(statusKey) + statusKey.length();
            int end2 = line.indexOf("\"", start2);
            String status = line.substring(start2, end2);

            String idKey = "\"id\":";
            int idStart = line.indexOf(idKey) + idKey.length();
            int idEnd = line.indexOf(",", idStart);
            String id = line.substring(idStart, idEnd);

            if (status.equals(filterStatus)) {
                System.out.println(count + ". (" + id + ") [" + status + "] " + description);
                count++;
            }
        }

        reader.close();
    }
}


