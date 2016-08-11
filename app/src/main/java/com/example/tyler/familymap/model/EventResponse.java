package com.example.tyler.familymap.model;

import java.util.ArrayList;

/**
 * Created by Tyler on 7/28/2016.
 */
public class EventResponse {

    //Used to hold all of the event objects as soon as they are loaded from the JSON

    private ArrayList<Event> events = new ArrayList<>();

    public  ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public void addToEvents(Event e)
    {
        events.add(e);
    }
}
