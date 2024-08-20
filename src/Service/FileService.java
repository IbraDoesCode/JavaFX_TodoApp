package Service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    private final String filePath = "src/Resources/Database/tasks.csv";

    /**
     * @return List of String Array representing rows and columns
     */
    public List<String[]> readCsv() {
        List<String[]> data = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
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
        // passing true as the second parameter for append
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, true))) {
            // appending the record
            writer.writeNext(record);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void writeData(List<String[]> data) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // write the header
            writer.writeNext(new String[] {"task", "dateCreated", "status", "dateCompleted"});

            // write the data
            writer.writeAll(data);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
