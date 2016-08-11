package com.example.tyler.familymap.model;

import com.amazon.geo.mapsv2.AmazonMap;
import com.example.tyler.familymap.enums.LineColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tyler on 7/30/2016.
 */
public class ModelData {

    //Singleton

    private static ModelData instance;

    public EventIDMap eventIDMap = new EventIDMap();

    public PersonIDMap personIDMap = new PersonIDMap();

    public PersonIDtoEvents personIDtoEvents = new PersonIDtoEvents();

    public PersonResponse personResponse = new PersonResponse();

    public EventResponse eventResponse = new EventResponse();

    public ArrayList<Event> allEvents = new ArrayList<>();

    public List<String> eventTypes = new ArrayList<>();

    public HashMap<String, Boolean> eventTypesSwitchMap = new HashMap<>();

    public boolean mothersSideSwitchOn = true;
    public boolean fathersSideSwitchOn = true;
    public boolean maleEventsSwitchOn = true;
    public boolean femaleEventsSwitchOn = true;

    public AmazonMap map;

    public Person me;

    //Global enums for line colors
    public LineColor lifeStoryLineColor = LineColor.GREEN;

    public LineColor spouseLineColor = LineColor.MAGENTA;

    public LineColor familyTreeLineColor = LineColor.CYAN;

    //Global booleans for line on/off toggle
    public boolean lifeStoryLinesOn = true;
    public boolean spouseLinesOn = true;
    public boolean familyTreeLinesOn = true;

    public int mapType = AmazonMap.MAP_TYPE_NORMAL;

    public boolean loggedIn = false;

    public LoginCredentials loginCredentials = new LoginCredentials();

    protected ModelData()
    {

    }


    public static ModelData getInstance() {
        if(instance == null) {
            instance = new ModelData();
        }
        return instance;
    }

    public static void destroyAll()
    {
        instance = null;
    }


    /**
     * Fills the personIDtoEventsMap with values
     */
    public void fillPersonIDtoEventsMap()
    {
        ArrayList<Person> people = ModelData.getInstance().personResponse.getPeople();
        //iterate through all people
        for (Person p : people)
        {
            String personID = p.getPersonID();
            ArrayList<Event> eventsForPerson = new ArrayList<>();
            //iterate through all events
            for (Event e : ModelData.getInstance().eventResponse.getEvents())
            {
                //if the event corresponds to the person, put that event in a list of events
                if (personID.equals(e.getPersonID()))
                {
                    eventsForPerson.add(e);
                }
            }
            //Then add that personID as a key for the personIDtoEvent map and add the list of events as the value
            ModelData.getInstance().personIDtoEvents.getPersonIDtoEventsMap().put(personID, eventsForPerson);
        }
    }

}
