package persistence;

import org.json.JSONObject;

// copied from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// represents something whose information can be written to a source file
public interface Writeable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
