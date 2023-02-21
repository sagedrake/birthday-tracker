package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Calendar {
    private List<Birthday> birthdays;
    private Birthday userBirthday;

    // EFFECTS: create a new calendar with the user's birthday and an empty birthday list
    public Calendar(Birthday userBirthday) {
        birthdays = new ArrayList<>();
        this.userBirthday = userBirthday;
    }

    // REQUIRES: name is the name of a person whose birthday is in the calendar
    // MODIFIES: this
    // EFFECTS: deletes the birthday of the person with that name from the calendar
    public void deleteBirthday(String name) {
        for (Birthday b: birthdays) {
            if (b.getName().equals(name)) {
                birthdays.remove(b);
                break;
            }
        }
    }

    // REQUIRES: there is no birthday with that name already in the calendar
    //           month, day is a valid date in a calendar year
    // MODIFIES: this
    // EFFECTS: add birthday with this name and date to the calendar
    public void addBirthday(String name, int month, int day, int year,
                            ArrayList<String> interests, ArrayList<String> giftIdeas) {
        birthdays.add(new Birthday(name, month, day, year, interests, giftIdeas));
    }

    // EFFECTS: produce String which is a list of all the birthdays in the calendar
    //          each person's name and the date of the birthday are displayed
    //          birthdays are not listed in the order they are stored
    public String birthdaysToString() {
        String result = "";
        result += userBirthday.dateToString() + " - " + userBirthday.getName() + "\n";
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

    public Birthday getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Birthday userBirthday) {
        this.userBirthday = userBirthday;
    }

    public List<Birthday> getBirthdays() {
        return birthdays;
    }
}
