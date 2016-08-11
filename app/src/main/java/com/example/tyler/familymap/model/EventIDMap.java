package com.example.tyler.familymap.model;

import java.util.HashMap;

/**
 * Created by Tyler on 7/30/2016.
 */
public class EventIDMap {

    private HashMap<String, Event> eventIDmap = new HashMap<>();

    public HashMap<String, Event> getEventIDmap() {
        return eventIDmap;
    }

    public void setEventIDmap(HashMap<String, Event> eventIDmap) {
        this.eventIDmap = eventIDmap;
    }
}
