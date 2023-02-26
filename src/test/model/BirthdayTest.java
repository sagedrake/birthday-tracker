package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
    public void testInterestsToString() {
        assertEquals("Reading, Dancing, Sleeping", testBirthday.interestsToString());
        testBirthday = new Birthday("Name", 1, 1, 1, emptyList, emptyList);
        assertEquals("None", testBirthday.interestsToString());
    }

    @Test
    public void testYearToString() {
        assertEquals("1950", testBirthday.yearToString());
        ArrayList<String> emptyList = new ArrayList<>();
        testBirthday = new Birthday("Name", 1, 1, 0, emptyList, emptyList);
        assertEquals("Unknown", testBirthday.yearToString());
    }

    @Test
    public void testGiftIdeasToString() {
        assertEquals("War and Peace, Root beer", testBirthday.giftIdeasToString());
        ArrayList<String> emptyList = new ArrayList<>();
        testBirthday = new Birthday("Name", 1, 1, 0, emptyList, emptyList);
        assertEquals("None", testBirthday.giftIdeasToString());
    }

    @Test
    public void testSetName() {
        testBirthday.setName("Snoopy Brown");
        assertEquals("Snoopy Brown", testBirthday.getName());
    }

    @Test
    public void testDaysUntil() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate yesterday = today.minusDays(1);
        LocalDate typicalDate = today.plusDays(75);

        Birthday bdayTomorrow = new Birthday("name1", tomorrow.getMonthValue(), tomorrow.getDayOfMonth(), 2020,
                emptyList, emptyList);
        Birthday bdayYesterday = new Birthday("name2", yesterday.getMonthValue(), yesterday.getDayOfMonth(),
                yesterday.getYear(), emptyList, emptyList);
        Birthday bdayToday = new Birthday("name3", today.getMonthValue(), today.getDayOfMonth(), 1999,
                emptyList, emptyList);
        Birthday bdayTypical = new Birthday("name4", typicalDate.getMonthValue(), typicalDate.getDayOfMonth(),
                1840, emptyList, emptyList);


        assertEquals(1, bdayTomorrow.daysUntil());
        assertEquals(0, bdayToday.daysUntil());
        if (Year.now().isLeap()) {
            assertEquals(365, bdayYesterday.daysUntil());
        } else {
            assertEquals(364, bdayYesterday.daysUntil());
        }
        assertEquals(75, bdayTypical.daysUntil());
    }

    @Test
    public void testAgeAsStringUnknownBirthYear() {
        Birthday unknownBirthYear = new Birthday("name", 1, 1, 0, emptyList, emptyList);
        assertEquals("Unknown", unknownBirthYear.ageAsString());
    }

    @Test
    public void testAgeAsString() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate yesterday = today.minusDays(1);

        Birthday bdayTomorrow = new Birthday("name1", tomorrow.getMonthValue(), tomorrow.getDayOfMonth(), 2020,
                emptyList, emptyList);
        Birthday bdayYesterday = new Birthday("name2", yesterday.getMonthValue(), yesterday.getDayOfMonth(),
                yesterday.getYear(), emptyList, emptyList);
        Birthday bdayToday = new Birthday("name3", today.getMonthValue(), today.getDayOfMonth(), 1999,
                emptyList, emptyList);

        assertEquals(Integer.toString(today.getYear() - bdayTomorrow.getYear() - 1), bdayTomorrow.ageAsString());
        assertEquals(Integer.toString(today.getYear() - bdayToday.getYear()), bdayToday.ageAsString());
        assertEquals(Integer.toString(today.getYear() - bdayYesterday.getYear()), bdayYesterday.ageAsString());
    }

}