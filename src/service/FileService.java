package service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    // External file path on the user's system
    private final String filepath;

    public FileService() {
        // Determine the external file path within the user's home directory.
        filepath = System.getProperty("user.home") + "/tasks.csv";

        // Copy resource from within the JAR to the external file path
        copyResourceToExternalFile();
    }

    private void copyResourceToExternalFile() {

        // Path to the csv file within the JAR
        String resourcePath = "/resources/database/todoData.csv";
        // Retrieve resource within the JAR
        try (InputStream resourceStream = getClass().getResourceAsStream(resourcePath)) {
            if (resourceStream == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }

            // Get the path of the external file
            Path externalFile = Path.of(filepath);

            // If external file does not exist
            if (!Files.exists(externalFile)) {
                // Copy the csv from the JAR to the external file path
                Files.copy(resourceStream, externalFile);
            }

        } catch (IOException e) {
            System.err.println("Error copying resource to external file: " + e.getMessage());
        }
    }

    /**
     * @return List of String Array representing rows and columns
     */
    public List<String[]> readCsv() {
        List<String[]> data = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filepath))) {
            // skip headers row 1.
            reader.skip(1);
            // read the rest of the rows
            data = reader.readAll();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to locate task.csv " + e);
        } catch (IOException | CsvException e) {
            System.err.println(e.getMessage());
        }
        return data;
    }

    public void appendData(String[] record) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filepath, true))) {
            // appending the record
            writer.writeNext(record);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void writeData(List<String[]> data) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filepath))) {
            // write the header
            writer.writeNext(new String[] {"id","task", "dateCreated", "status", "dateCompleted"});

            // write the data
            writer.writeAll(data);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
