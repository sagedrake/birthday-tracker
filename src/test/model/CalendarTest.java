package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalendarTest {
    private Calendar calendarTest;
    private ArrayList<String> emptyList;

    @BeforeEach
    public void runBefore() {
        Birthday userBirthday = new Birthday("Sage", 11, 26, 2003, emptyList, emptyList);
        calendarTest = new Calendar(userBirthday);
        emptyList = new ArrayList<>();

    }

    @Test
    public void testConstructor() {
        Birthday userBirthdayTest = calendarTest.getUserBirthday();
        assertEquals("Sage", userBirthdayTest.getName());
        assertEquals(11, userBirthdayTest.getMonth());
        assertEquals(26, userBirthdayTest.getDayNum());

        assertEquals(0, calendarTest.getBirthdays().size());
    }

    @Test
    public void testAddBirthday() {
        calendarTest.addBirthday("Snoopy", 1, 10, 1950, emptyList, emptyList);
        assertEquals(1, calendarTest.getBirthdays().size());
        assertEquals("Snoopy", calendarTest.getBirthdays().get(0).getName());

        calendarTest.addBirthday("Julian", 8, 25, 2007, emptyList, emptyList);
        assertEquals(2, calendarTest.getBirthdays().size());
        assertEquals(8, calendarTest.getBirthdays().get(1).getMonth());

        calendarTest.addBirthday("Jane", 2, 22, 1966, emptyList, emptyList);
        assertEquals(3, calendarTest.getBirthdays().size());
        assertEquals(22, calendarTest.getBirthdays().get(2).getDayNum());
    }

    @Test
    public void deleteBirthday(){
        calendarTest.addBirthday("Snoopy", 1, 10, 1950, emptyList, emptyList);
        calendarTest.addBirthday("Julian", 8, 25, 2007, emptyList, emptyList);
        calendarTest.addBirthday("Jane", 2, 22, 1966, emptyList, emptyList);
        assertEquals(3, calendarTest.getBirthdays().size());

        calendarTest.deleteBirthday("Snoopy");
        assertEquals(2, calendarTest.getBirthdays().size());
        assertEquals("Jane", calendarTest.getBirthdays().get(1).getName());

        calendarTest.deleteBirthday("Jane");
        assertEquals(1, calendarTest.getBirthdays().size());
        assertEquals("Julian", calendarTest.getBirthdays().get(0).getName());

        calendarTest.deleteBirthday("Julian");
        assertEquals(0, calendarTest.getBirthdays().size());
    }

    @Test
    public void testBirthdaysToString(){
        calendarTest.addBirthday("Snoopy", 1, 10, 1950, emptyList, emptyList);
        calendarTest.addBirthday("Julian", 8, 25, 2007, emptyList, emptyList);
        calendarTest.addBirthday("Jane", 2, 22, 1966, emptyList, emptyList);
        calendarTest.addBirthday("Filler Person", 2, 3, 3333, emptyList, emptyList);

        String expectedString = "01/10 - Snoopy\n08/25 - Julian\n02/22 - Jane\n02/03 - Filler Person\n";

        assertEquals(expectedString, calendarTest.birthdaysToString());
    }

    @Test
    public void testRetrieveBirthday(){
        calendarTest.addBirthday("Snoopy", 1, 10, 1950, emptyList, emptyList);
        calendarTest.addBirthday("Julian", 8, 25, 2007, emptyList, emptyList);
        calendarTest.addBirthday("Filler Person", 2, 3, 3333, emptyList, emptyList);

        Birthday snoopyRetrieved = calendarTest.retrieveBirthday("Snoopy");
        assertEquals("Snoopy", snoopyRetrieved.getName());
        assertEquals(1, snoopyRetrieved.getMonth());

        Birthday fillerRetrieved = calendarTest.retrieveBirthday("Filler Person");
        assertEquals("Filler Person", fillerRetrieved.getName());
        assertEquals(3, fillerRetrieved.getDayNum());
    }

    @Test
    public void testSetUserBirthday(){
        calendarTest.setUserBirthday(new Birthday("user", 12, 23, 1467, emptyList, emptyList));

        Birthday userBirthdayTest = calendarTest.getUserBirthday();
        assertEquals("user", userBirthdayTest.getName());
        assertEquals(12, userBirthdayTest.getMonth());
        assertEquals(23, userBirthdayTest.getDayNum());
    }
}
