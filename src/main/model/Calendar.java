package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.List;

// represents a person's calendar with a list of birthdays of people they know
public class Calendar implements Writeable {
    private List<Birthday> birthdays;

    // EFFECTS: create a new calendar with an empty birthday list
    public Calendar() {
        birthdays = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("New calendar created"));
    }

    public List<Birthday> getBirthdays() {
        return birthdays;
    }

    // REQUIRES: name is the name of a person whose birthday is in the calendar
    // MODIFIES: this
    // EFFECTS: deletes the birthday of the person with that name from the calendar
    public void deleteBirthday(String name) {
        boolean removed = false;
        int i = 0;

        while (!removed) {
            if (birthdays.get(i).getName().equals(name)) {
                removed = birthdays.remove(birthdays.get(i));
            }
            i++;
        }
        EventLog.getInstance().logEvent(new Event("Birthday with name \"" + name + "\" deleted from calendar."));
    }

    // REQUIRES: there is no birthday with the same name already in the calendar
    // MODIFIES: this
    // EFFECTS: add this birthday to the calendar
    public void addBirthday(Birthday b) {
        birthdays.add(b);
        EventLog.getInstance().logEvent(new Event("Birthday with name \"" + b.getName()
                + "\" added to calendar."));
    }

    // EFFECTS: produce String which is a list of all the birthdays in the calendar
    //          each person's name and the date of the birthday are displayed
    //          birthdays are listed in the order they were added
    public String birthdaysToString() {

        if (birthdays.isEmpty()) {
            return "No birthdays added yet.\n";
        }

        String result = "";

        for (Birthday b: birthdays) {
            result += b.dateToString() + " - " + b.getName() + "\n";
        }
        return result;
    }

    // REQUIRES: name is the name of a person whose birthday is in the calendar
    // EFFECTS: return the Birthday object in the calendar with that name
    public Birthday retrieveBirthday(String name) {
        for (Birthday b: birthdays) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
        return null;
    }

    // EFFECTS: Returns JSONObject that represents this calendar
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("birthdays", birthdaysToJson());
        return json;
    }

    // Returns a JSONArray containing a JSONObject for each birthday in this calendar
    private JSONArray birthdaysToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Birthday b : birthdays) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }
}
