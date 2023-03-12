package persistence;

import org.json.JSONObject;

// copied from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writeable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
