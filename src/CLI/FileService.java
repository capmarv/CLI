package CLI;

import java.io.File;
import java.io.IOException;

public class FileService {
    static void initializeFile() throws IOException {

        File file = new File("tasks.json");

        if(!file.exists()) {
            file.createNewFile();
            System.out.println("Successfully new file created");
        }
    }
}
