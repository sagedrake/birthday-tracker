package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

// represents a person's birthday with their name and birthdate
// optionally includes their birth year, and their interests and gift ideas to help with choosing birthday gifts
public class Birthday {
    private LocalDate birthdate; // birthdate with year 0 represents the year being unknown
    private String name;

    private ArrayList<String> interests;
    private ArrayList<String> giftIdeas;

    // REQUIRES: month, day is a valid date in a calendar year
    // EFFECTS: create a Birthday with the given name and date
    public Birthday(String name, int month, int dayNum, int year, ArrayList<String> interests,
                    ArrayList<String> giftIdeas) {
        this.name = name;
        this.birthdate = LocalDate.of(year, month, dayNum);
        this.interests = interests;
        this.giftIdeas = giftIdeas;
    }

    public int getMonth() {
        return birthdate.getMonthValue();
    }

    public int getDayNum() {
        return birthdate.getDayOfMonth();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return birthdate.getYear();
    }

    public ArrayList<String> getInterests() {
        return interests;
    }

    public ArrayList<String> getGiftIdeas() {
        return giftIdeas;
    }

    // EFFECTS: return String representation of birthdate
    public String dateToString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd");
        return birthdate.format(dateTimeFormatter);
    }

    // EFFECTS: Return string representation of year, or "Unknown" is year is -1
    public String yearToString() {
        if (birthdate.getYear() == 0) {
            return "Unknown";
        }
        return Integer.toString(birthdate.getYear());
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

    // EFFECTS: calculate number of days until the next occurrence of the birthday
    public int daysUntil() {
        LocalDate today = LocalDate.now();

        // date of birthday with this year's year
        LocalDate birthdateCurrentYear = LocalDate.of(today.getYear(), getMonth(), getDayNum());

        int currentYearDaysUntil = (int)ChronoUnit.DAYS.between(LocalDate.now(), birthdateCurrentYear);
        // if birthday has not already passed this year (days between will be negative if it has already passed)
        if (currentYearDaysUntil >= 0) {
            return currentYearDaysUntil;
        }
        // date of birthday with next year's year
        LocalDate birthdateNextYear = LocalDate.of(today.getYear() + 1, getMonth(), getDayNum());
        int nextYearDaysUntil = (int)ChronoUnit.DAYS.between(LocalDate.now(), birthdateNextYear);

        return nextYearDaysUntil;
    }

    // EFFECTS: calculate and return person's age as a String, or "Unknown" if the person's birth year is unknown
    public String ageAsString() {
        int bdayDay = birthdate.getDayOfMonth();
        int bdayMonth = birthdate.getMonthValue();
        int bdayYear = birthdate.getYear();

        // year 0000 represents unknown year
        if (bdayYear == 0) {
            return "Unknown";
        }

        LocalDate today = LocalDate.now();
        int todayDay = today.getDayOfMonth();
        int todayMonth = today.getMonthValue();
        int todayYear = today.getYear();

        boolean bdayHasPassed = (todayMonth > bdayMonth) || ((todayMonth == bdayMonth) && (todayDay >= bdayDay));
        if (bdayHasPassed) {
            return Integer.toString(todayYear - bdayYear);
        }
        return Integer.toString(todayYear - bdayYear - 1);
    }
}
