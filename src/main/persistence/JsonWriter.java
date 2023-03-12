package persistence;

import model.Calendar;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// based on JsonWriter class from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String fileName;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String fileName) {
        this.fileName = fileName;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(fileName);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(Calendar c) {
        JSONObject json = c.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
