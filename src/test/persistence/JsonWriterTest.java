package persistence;

import model.Birthday;
import model.Calendar;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonWriterTest {
    private JsonWriter writer;
    private JsonReader reader;
    private Calendar c;

    @Test
    void testWriterInvalidFileName() {
        writer = new JsonWriter("./data/*illegalFileName.json");
        try {
            writer.open();
            fail("IOException expected!");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCalendar() {
        c = new Calendar();
        writer = new JsonWriter("./data/testJsonWriterEmptyCalendar.json");

        try {
            writer.open();
            writer.write(c);
            writer.close();

            reader = new JsonReader("./data/testJsonWriterEmptyCalendar.json");
            Calendar cAsReadByReader = reader.read();
            assertTrue(cAsReadByReader.getBirthdays().isEmpty());
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }

    @Test
    void testWriterGeneericCalendar() {
        c = new Calendar();
        ArrayList<String> interests = new ArrayList<>(Arrays.asList("Reading", "Dancing", "Sleeping"));
        ArrayList<String> giftIdeas = new ArrayList<>(Arrays.asList("War and Peace", "Root beer"));
        ArrayList<String> emptyList = new ArrayList<>();
        c.addBirthday(new Birthday("Snoopy", 1, 10, 1950, interests, giftIdeas));
        c.addBirthday(new Birthday("Generic Person", 5, 5, 0, emptyList, emptyList));
        writer = new JsonWriter("./data/testJsonWriterGenericCalendar.json");

        try {
            writer.open();
            writer.write(c);
            writer.close();

            reader = new JsonReader("./data/testJsonWriterGenericCalendar.json");
            Calendar cAsReadByReader = reader.read();
            assertEquals("01/10 - Snoopy\n05/05 - Generic Person\n", c.birthdaysToString());
            assertEquals("War and Peace, Root beer", c.retrieveBirthday("Snoopy").giftIdeasToString());
            assertEquals("None", c.retrieveBirthday("Generic Person").interestsToString());
            assertEquals(1950, c.retrieveBirthday("Snoopy").getYear());
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }
}
