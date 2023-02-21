package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BirthdayTest {
    private Birthday testBirthday;

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

        testBirthday.setMonth(11);
        assertEquals("11/10", testBirthday.dateToString());

        testBirthday.setMonth(1);
        testBirthday.setDayNum(2);
        assertEquals("01/02", testBirthday.dateToString());
    }

    @Test
    public void testInterestsToString() {
        assertEquals("Reading, Dancing, Sleeping", testBirthday.interestsToString());
        ArrayList<String> emptyList = new ArrayList<>();
        testBirthday = new Birthday("Name", 1, 1, 1, emptyList, emptyList);
        assertEquals("None", testBirthday.interestsToString());
    }

    @Test
    public void testYearToString() {
        assertEquals("1950", testBirthday.yearToString());
        ArrayList<String> emptyList = new ArrayList<>();
        testBirthday = new Birthday("Name", 1, 1, -1, emptyList, emptyList);
        assertEquals("Unknown", testBirthday.yearToString());
    }

    @Test
    public void testGiftIdeasToString() {
        assertEquals("War and Peace, Root beer", testBirthday.giftIdeasToString());
        ArrayList<String> emptyList = new ArrayList<>();
        testBirthday = new Birthday("Name", 1, 1, 1, emptyList, emptyList);
        assertEquals("None", testBirthday.giftIdeasToString());
    }

    @Test
    public void testSetName() {
        testBirthday.setName("Snoopy Brown");
        assertEquals("Snoopy Brown", testBirthday.getName());
    }

}