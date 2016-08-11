package com.example.tyler.familymap.model;

import android.test.AndroidTestCase;

import java.util.ArrayList;

/**
 * Created by Tyler on 8/1/2016.
 */
public class PersonIDtoEventsTest extends AndroidTestCase {


    /**
     * Tests the chronological sorting of events
     */
    public void testSortEventsChronologically()
    {
        ArrayList<Event> events = new ArrayList<>();
        Event a = new Event();
        Event b = new Event();
        Event c = new Event();
        Event d = new Event();
        Event e = new Event();
        a.setDescription("baptism");
        a.setYear("1935");
        b.setDescription("death");
        b.setYear("1990");
        c.setDescription("birth");
        c.setYear("1920");
        d.setDescription("tacos");
        d.setYear("1935");
        e.setDescription("marriage");
        e.setYear("1960");

        events.add(a);
        events.add(b);
        events.add(c);
        events.add(d);
        events.add(e);

        ArrayList<Event> sortedEvents = PersonIDtoEvents.sortEventsChronologically(events);

        assertEquals(5, sortedEvents.size());
    }
}
