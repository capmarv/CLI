import java.io.*;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public Main() throws IOException {
    }

    public static void main(String[] args) throws IOException {

        initializeFile();

        if (args.length == 0) {
            System.out.println("No command provided");
            return;
        }
        String command = args[0];

        if (command.equals("add")) {
            addTask(args);
        } else if (command.equals("list")) {
            listTasks();
        } else if (command.equals("remove")) {
            removeTask(args);
        } else {
            System.out.println("Unknown command");
        }

    }

    private static void removeTask(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("task id required");
            return;
        }
        System.out.println("removing task : " + args[1]);
        int num = Integer.parseInt(args[1]);
        BufferedReader reader = new BufferedReader(new FileReader("tasks.json"));
        String line;
        StringBuilder sb = new StringBuilder();
        int currLine = 1;
        while ((line = reader.readLine()) != null) {
            if (currLine != num) {
                sb.append(line).append("\n");
            }
            currLine++;
        }
        reader.close();

        FileWriter writer = new FileWriter("tasks.json");
        writer.write(sb.toString());
        writer.close();

        System.out.println("task removed");
    }

    private static void listTasks() throws IOException {
        System.out.println("available tasks : ");
        BufferedReader reader = new BufferedReader(new FileReader("tasks.json"));
        String line;
        int count = 1;

        while((line = reader.readLine()) != null) {
            if(line.trim().isEmpty()) continue;
            String task = line.substring(line.indexOf(":\"") + 2, line.lastIndexOf("\""));
            System.out.println(count + ". " + task);
            count++;
        }
        reader.close();
    }

    private static void addTask(String[] args) throws IOException {
        if(args.length < 2){
            System.out.println("description required");
            return;
        } System.out.println("adding task : " + args[1]);

        FileWriter writer = new FileWriter("tasks.json", true);
        writer.write("\n[ ] " + args[1]);
            writer.close();

            System.out.println("task added");
    }
    private static void initializeFile() throws IOException {
        File file = new File("tasks.json");

        if(!file.exists()) {
            file.createNewFile();
            System.out.println("Successfully new file created");
        }
    }
}


