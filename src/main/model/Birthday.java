package model;

import java.util.ArrayList;

public class Birthday {
    private int month;

    private int dayNum;
    private String name;

    private int year;

    private ArrayList<String> interests;
    private ArrayList<String> giftIdeas;

    // REQUIRES: month, day is a valid date in a calendar year
    // EFFECTS: create a Birthday with the given name and date
    public Birthday(String name, int month, int dayNum, int year, ArrayList<String> interests,
                    ArrayList<String> giftIdeas) {
        this.name = name;
        this.month = month;
        this.dayNum = dayNum;
        this.year = year;
        this.interests = interests;
        this.giftIdeas = giftIdeas;
    }

    // EFFECTS: return String representation of birthdate
    public String dateToString() {
        String birthdayString = "";

        if (month < 10) {
            birthdayString += "0" + month;
        } else {
            birthdayString += Integer.toString(month);
        }

        birthdayString += "/";

        if (dayNum < 10) {
            birthdayString += "0" + dayNum;
        } else {
            birthdayString += Integer.toString(dayNum);
        }

        return birthdayString;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public ArrayList<String> getGiftIdeas() {
        return giftIdeas;
    }

    // EFFECTS: Return string representation of interests
    public String interestsToString() {
        if (interests.isEmpty()) {
            return "None";
        }
        String result = interests.get(0);
        for (int i = 1; i < interests.size(); i++) {
            result += ", " + interests.get(i);
        }
        return result;
    }

    // EFFECTS: Return string representation of year, or "Unknown" is year is -1
    public String yearToString() {
        if (year == -1) {
            return "Unknown";
        }
        return Integer.toString(year);
    }

    // Effects: Return string representation of gift ideas
    public String giftIdeasToString() {
        if (giftIdeas.isEmpty()) {
            return "None";
        }
        String result = giftIdeas.get(0);
        for (int i = 1; i < giftIdeas.size(); i++) {
            result += ", " + giftIdeas.get(i);
        }
        return result;
    }
}
