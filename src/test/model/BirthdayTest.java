package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BirthdayTest {
    private Birthday testBirthday;
    ArrayList<String> emptyList;

    @BeforeEach
    public void runBefore() {
        ArrayList<String> snoopyInterests = new ArrayList<>();
        snoopyInterests.add("Reading");
        snoopyInterests.add("Dancing");
        snoopyInterests.add("Sleeping");
        ArrayList<String> snoopyGiftIdeas = new ArrayList<>();
        snoopyGiftIdeas.add("War and Peace");
        snoopyGiftIdeas.add("Root beer");
        testBirthday = new Birthday("Snoopy", 8, 10, 1950, snoopyInterests, snoopyGiftIdeas);

        emptyList = new ArrayList<>();
    }

    @Test
    public void testConstructor() {
        assertEquals("Snoopy", testBirthday.getName());
        assertEquals(8, testBirthday.getMonth());
        assertEquals(10, testBirthday.getDayNum());
        assertEquals(1950, testBirthday.getYear());

        ArrayList<String> snoopyInterests = testBirthday.getInterests();
        ArrayList<String> snoopyGiftIdeas = testBirthday.getGiftIdeas();
        assertEquals(3, snoopyInterests.size());
        assertEquals("Reading", snoopyInterests.get(0));
        assertEquals(2, snoopyGiftIdeas.size());
        assertEquals("Root beer", snoopyGiftIdeas.get(1));
    }

    @Test
    public void testDateToString() {
        assertEquals("08/10", testBirthday.dateToString());
    }


    @Test
    public void testYearToString() {
        assertEquals("1950", testBirthday.yearToString());
    }

    @Test
    public void testYearToStringUnknownYear() {
        ArrayList<String> emptyList = new ArrayList<>();
        testBirthday = new Birthday("Name", 1, 1, 0, emptyList, emptyList);
        assertEquals("Unknown", testBirthday.yearToString());
    }

    @Test
    public void testGiftIdeasToString() {
        assertEquals("War and Peace, Root beer", testBirthday.giftIdeasToString());
    }

    @Test
    public void testGiftIdeasToStringNoGiftIdeas() {
        testBirthday = new Birthday("Name", 1, 1, 0, emptyList, emptyList);
        assertEquals("None", testBirthday.giftIdeasToString());
    }

    @Test
    public void testInterestsToString() {
        assertEquals("Reading, Dancing, Sleeping", testBirthday.interestsToString());
    }

    @Test
    public void testInterestsToStringNoInterests() {
        testBirthday = new Birthday("Name", 1, 1, 1, emptyList, emptyList);
        assertEquals("None", testBirthday.interestsToString());
    }

    @Test
    public void testSetName() {
        testBirthday.setName("Snoopy Brown");
        assertEquals("Snoopy Brown", testBirthday.getName());
    }

    @Test
    public void testDaysUntilTypicalBirthday() {
        LocalDate today = LocalDate.now();
        LocalDate typicalDate = today.plusDays(75);

        Birthday bdayTypical = new Birthday("name", typicalDate.getMonthValue(), typicalDate.getDayOfMonth(),
                1840, emptyList, emptyList);

        assertEquals(75, bdayTypical.daysUntil());
    }

    @Test
    public void testDaysUntilBirthdayWasYesterday() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        Birthday bdayYesterday = new Birthday("name", yesterday.getMonthValue(), yesterday.getDayOfMonth(),
                yesterday.getYear(), emptyList, emptyList);

        if (Year.now().isLeap() && today.getMonthValue() <= 2) { // if it is jan or feb of a leap year
            assertEquals(365, bdayYesterday.daysUntil());
        } else if (Year.isLeap(today.getYear() + 1)
                && today.getMonthValue() > 2) { // if it is past feb and next year is a leap year
            assertEquals(365, bdayYesterday.daysUntil());
        } else {
            assertEquals(364, bdayYesterday.daysUntil());
        }
    }

    @Test
    public void testDaysUntilBirthdayIsToday() {
        LocalDate today = LocalDate.now();
        Birthday bdayToday = new Birthday("name", today.getMonthValue(), today.getDayOfMonth(), 1999,
                emptyList, emptyList);
        assertEquals(0, bdayToday.daysUntil());
    }

    @Test
    public void testDaysUntilBirthdayIsTomorrow() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        Birthday bdayTomorrow = new Birthday("name", tomorrow.getMonthValue(), tomorrow.getDayOfMonth(), 2020,
                emptyList, emptyList);
        assertEquals(1, bdayTomorrow.daysUntil());
    }

    @Test
    public void testAgeAsStringUnknownBirthYear() {
        Birthday unknownBirthYear = new Birthday("name", 1, 1, 0, emptyList, emptyList);
        assertEquals("Unknown", unknownBirthYear.ageAsString());
    }

    @Test
    public void testAgeAsStringBirthdayIsToday() {
        LocalDate today = LocalDate.now();

        Birthday bdayToday = new Birthday("name", today.getMonthValue(), today.getDayOfMonth(), 1999,
                emptyList, emptyList);

        assertEquals(Integer.toString(today.getYear() - bdayToday.getYear()), bdayToday.ageAsString());
    }

    @Test
    public void testAgeAsStringBirthdayWasYesterday() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        Birthday bdayYesterday = new Birthday("name", yesterday.getMonthValue(), yesterday.getDayOfMonth(),
                yesterday.getYear(), emptyList, emptyList);

        // if today is not Jan 1st
        if (!(today.getMonthValue()==1 && today.getDayOfMonth()==1)) {
            assertEquals(Integer.toString(today.getYear() - bdayYesterday.getYear()), bdayYesterday.ageAsString());
        }
    }

    @Test
    public void testAgeAsStringBirthdayIsTomorrow() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        Birthday bdayTomorrow = new Birthday("name", tomorrow.getMonthValue(), tomorrow.getDayOfMonth(), 2020,
                emptyList, emptyList);

        // if today is not Dec 31st
        if (!(today.getMonthValue()==12 && today.getDayOfMonth()==31)) {
            assertEquals(Integer.toString(today.getYear() - bdayTomorrow.getYear() - 1), bdayTomorrow.ageAsString());
        }
    }

    @Test
    public void testToJson() {
        JSONObject jsonObject = testBirthday.toJson();
        assertEquals("Snoopy", jsonObject.getString("name"));
        assertEquals(8, jsonObject.getInt("month"));
        assertEquals(10, jsonObject.getInt("day"));
        assertEquals(1950, jsonObject.getInt("year"));
        JSONArray interests = jsonObject.getJSONArray("interests");
        assertEquals(3, interests.length());
        assertEquals("Reading", interests.get(0).toString());
        JSONArray giftIdeas = jsonObject.getJSONArray("gift ideas");
        assertEquals(2, giftIdeas.length());
        assertEquals("War and Peace", giftIdeas.get(0).toString());
    }
}