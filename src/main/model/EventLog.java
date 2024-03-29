package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a log of alarm system events.
// We use the Singleton Design Pattern to ensure that there is only
//      one EventLog in the system and that the system has global access
//      to the single instance of the EventLog.
public class EventLog implements Iterable<Event> {

    // the only EventLog in the system (Singleton Design Pattern)
    private static EventLog theLog;
    private Collection<Event> events;


    // EFFECTS: Creates a new event log with no events. Prevents external construction.
    private EventLog() {
        events = new ArrayList<Event>();
    }

    // MODIFIES: this
    // EFFECTS: Gets instance of EventLog - creates it if it doesn't already exist. (Singleton Design Pattern)
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: Adds an event to the event log.
    public void logEvent(Event e) {
        events.add(e);
    }


    // MODIFIES: this
    // EFFECTS: Clears the event log and logs the event.
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: return iterator for events
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
