package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalendarTest {
    private Calendar calendarTest;
    private ArrayList<String> emptyList;
    private Birthday snoopy;
    private Birthday birthday2;
    private Birthday birthday3;
    private Birthday birthday4;

    @BeforeEach
    public void runBefore() {
        calendarTest = new Calendar();
        emptyList = new ArrayList<>();
        snoopy = new Birthday("Snoopy", 1, 10, 1950, emptyList, emptyList);
        birthday2 = new Birthday("Julian", 8, 25, 2007, emptyList, emptyList);
        birthday3 = new Birthday("Jane", 2, 22, 1966, emptyList, emptyList);
        birthday4 = new Birthday("Filler Person", 2, 3, 3333, emptyList, emptyList);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, calendarTest.getBirthdays().size());
    }

    @Test
    public void testAddBirthday() {
        calendarTest.addBirthday(snoopy);
        assertEquals(1, calendarTest.getBirthdays().size());
        assertEquals(snoopy.getName(), calendarTest.getBirthdays().get(0).getName());

        calendarTest.addBirthday(birthday2);
        assertEquals(2, calendarTest.getBirthdays().size());
        assertEquals(birthday2.getMonth(), calendarTest.getBirthdays().get(1).getMonth());

        calendarTest.addBirthday(birthday3);
        assertEquals(3, calendarTest.getBirthdays().size());
        assertEquals(birthday3.getDayNum(), calendarTest.getBirthdays().get(2).getDayNum());
    }

    @Test
    public void deleteBirthday(){
        calendarTest.addBirthday(snoopy);
        calendarTest.addBirthday(birthday2);
        calendarTest.addBirthday(birthday3);
        assertEquals(3, calendarTest.getBirthdays().size());

        calendarTest.deleteBirthday(snoopy.getName());
        assertEquals(2, calendarTest.getBirthdays().size());
        assertEquals(birthday3.getName(), calendarTest.getBirthdays().get(1).getName());

        calendarTest.deleteBirthday(birthday3.getName());
        assertEquals(1, calendarTest.getBirthdays().size());
        assertEquals(birthday2.getName(), calendarTest.getBirthdays().get(0).getName());

        calendarTest.deleteBirthday(birthday2.getName());
        assertEquals(0, calendarTest.getBirthdays().size());
    }

    @Test
    public void testBirthdaysToString(){
        String expectedString = "No birthdays added yet.\n";
        assertEquals(expectedString, calendarTest.birthdaysToString());

        calendarTest.addBirthday(snoopy);
        calendarTest.addBirthday(birthday2);
        calendarTest.addBirthday(birthday3);
        calendarTest.addBirthday(birthday4);

        expectedString = "01/10 - Snoopy\n08/25 - Julian\n02/22 - Jane\n02/03 - Filler Person\n";

        assertEquals(expectedString, calendarTest.birthdaysToString());
    }

    @Test
    public void testRetrieveBirthday(){
        calendarTest.addBirthday(snoopy);
        calendarTest.addBirthday(birthday2);
        calendarTest.addBirthday(birthday3);

        Birthday snoopyRetrieved = calendarTest.retrieveBirthday(snoopy.getName());
        assertEquals(snoopy.getName(), snoopyRetrieved.getName());
        assertEquals(snoopy.getMonth(), snoopyRetrieved.getMonth());

        Birthday nullRetrieved = calendarTest.retrieveBirthday(birthday4.getName());
        assertEquals(null, nullRetrieved);
    }

}
