package src.service;

import src.model.Food;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {

    private File userFile;

    public AuditService() {
        this.userFile = new File("src/resources/audit.csv");
        if(!userFile.exists()) {
            try {
                userFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeAction(String action) {
        try {
            FileWriter wr = new FileWriter(userFile, true);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = LocalDateTime.now().format(formatter);
            wr.write(action);
            wr.write(",");
            wr.write(formatDateTime);
            wr.write("\n");
            wr.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
