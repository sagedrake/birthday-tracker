package persistence;

import model.Birthday;
import model.Calendar;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// based on JsonReader class from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads a calendar from JSON data stored in a source file
public class JsonReader {
    private String sourceFile;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.sourceFile = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Calendar read() throws IOException {
        String jsonData = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCalendar(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses calendar from JSON object and returns it
    private Calendar parseCalendar(JSONObject jsonObject) {
        Calendar c = new Calendar();
        addBirthdays(c, jsonObject);
        return c;
    }

    // MODIFIES: c
    // EFFECTS: parses birthdays from JSON object and adds them to calendar
    private void addBirthdays(Calendar c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("birthdays");
        for (Object json : jsonArray) {
            JSONObject nextBirthday = (JSONObject) json;
            addBirthday(c, nextBirthday);
        }
    }

    // MODIFIES: c
    // EFFECTS: parses a birthday from JSON object and adds it to the calendar
    private void addBirthday(Calendar c, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int day = jsonObject.getInt("day");
        int month = jsonObject.getInt("month");
        int year = jsonObject.getInt("year");

        JSONArray jsonArrayI = jsonObject.getJSONArray("interests");
        ArrayList<String> interests = new ArrayList<>();
        for (Object json : jsonArrayI) {
            interests.add(json.toString());
        }

        JSONArray jsonArrayG = jsonObject.getJSONArray("gift ideas");
        ArrayList<String> giftIdeas = new ArrayList<>();
        for (Object json : jsonArrayG) {
            giftIdeas.add(json.toString());
        }

        Birthday birthday = new Birthday(name, month, day, year, interests, giftIdeas);
        c.addBirthday(birthday);
    }
}
