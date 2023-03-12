package persistence;

import model.Calendar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Heavily based on JsonReaderTest class from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest {
    JsonReader reader;
    Calendar c;

    @Test
    void testReaderNonExistentFile() {
        reader = new JsonReader("./data/nonexistentFile.json");
        try {
            c = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCalendar() {
        reader = new JsonReader("./data/testJsonReaderEmptyCalendar.json");
        try {
            c = reader.read();
            assertEquals(0, c.getBirthdays().size());
        } catch (IOException e) {
            fail("Unexpected " + e.getClass().getName());
        }
    }

    @Test
    void testReaderGeneralCalendar() {
        reader = new JsonReader("./data/testJsonReaderGenericCalendar.json");
        try {
            c = reader.read();
            assertEquals(2, c.getBirthdays().size());
            assertEquals("01/10 - Snoopy\n05/05 - Generic Person\n", c.birthdaysToString());
            assertEquals("War and Peace, Root beer", c.retrieveBirthday("Snoopy").giftIdeasToString());
            assertEquals("None", c.retrieveBirthday("Generic Person").interestsToString());
            assertEquals(1950, c.retrieveBirthday("Snoopy").getYear());
        } catch (IOException e) {
            fail("Unexpected IOException :(");
        }
    }
}
