package com.example.tyler.familymap.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Tyler on 8/1/2016.
 */
public class PersonIDtoEvents {

    //A map mapping the person IDs of all persons to a list of all the events related to that person
    private HashMap<String, ArrayList<Event>> personIDtoEventsMap = new HashMap<>();

    public HashMap<String, ArrayList<Event>> getPersonIDtoEventsMap() {
        return personIDtoEventsMap;
    }

    public void setPersonIDtoEventsMap(HashMap<String, ArrayList<Event>> personIDtoEventsMap) {
        this.personIDtoEventsMap = personIDtoEventsMap;
    }

    public static ArrayList<Event> sortEventsChronologically(ArrayList<Event> inEvents)
    {
        ArrayList<Event> inputEvents = (ArrayList<Event>)inEvents.clone();
        ArrayList<Event> sortedEvents = new ArrayList<>();
        for (Event e : inputEvents)
        {
            if (e.getDescription().equals("birth"))
            {
                sortedEvents.add(e);
            }
        }
        inputEvents.removeAll(sortedEvents); //removes all the events that have been sorted so far
        //now the birth has been added. now add all other events chronologically except death
        ArrayList<Integer> years = new ArrayList<>();
        for (Event e : inputEvents)
        {
            if (!e.getDescription().equals("death") && e.getYear() != null)
            {
                years.add(Integer.valueOf(e.getYear()));
            }
        }
        //Sort the list of years
        Collections.sort(years);
        //Now go through the list of years and add the events to sortedEvents correspondingly
        for (Integer i : years)
        {
            for (Event e : inputEvents)
            {
                if (String.valueOf(i).equals(e.getYear()))
                {
                    sortedEvents.add(e);
                }
            }
            inputEvents.removeAll(sortedEvents);
        }
        //remove the events in sortedEvents from the inputEvents
        inputEvents.removeAll(sortedEvents);

        //sort the rest of the events besides death by name
        ArrayList<String> descriptions = new ArrayList<>();
        for (Event e : inputEvents)
        {
            if (!e.getDescription().equals("death"))
            {
                descriptions.add(e.getDescription());
            }
        }
        //Sort the list of descriptions
        Collections.sort(descriptions);
        //Now go through the list of years and add the events to sortedEvents correspondingly
        for (String s : descriptions)
        {
            for (Event e : inputEvents)
            {
                if (s.equals(e.getDescription()))
                {
                    sortedEvents.add(e);
                }
            }
        }
        //remove the sorted events from the inputEvents
        inputEvents.removeAll(sortedEvents);
        //Now add the rest of the events to sortedEvents besides death

        sortedEvents.addAll(inputEvents);
        return sortedEvents;
    }
}
