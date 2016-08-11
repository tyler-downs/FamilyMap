package com.example.tyler.familymap.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyler.familymap.R;
import com.example.tyler.familymap.adapter.FilterEventListAdapter;
import com.example.tyler.familymap.model.Event;
import com.example.tyler.familymap.model.EventResponse;
import com.example.tyler.familymap.model.ModelData;
import com.example.tyler.familymap.model.Person;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Tyler on 8/5/2016.
 */
public class FilterActivity extends AppCompatActivity {

    private ArrayList<String> eventDescriptions = new ArrayList<>(); //normalize to lower case

    private ListView eventFilterListView;

    //Father's Side elements
    private Switch fathersSideSwitch;
    private TextView fathersSideTopText;
    private TextView fathersSideBottomText;

    //Mother's Side elements
    private Switch mothersSideSwitch;
    private TextView mothersSideTopText;
    private TextView mothersSideBottomText;

    //Male events elements
    private Switch maleEventsSwitch;
    private TextView maleEventsTopText;
    private TextView maleEventsBottomText;

    //Female events elements
    private Switch femaleEventsSwitch;
    private TextView femaleEventsTopText;
    private TextView femaleEventsBottomText;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.filter_activity);

        //enable the up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventFilterListView = (ListView) findViewById(R.id.filter_list_view);
        getEventDescriptions();
        eventFilterListView.setAdapter(new FilterEventListAdapter(this, R.layout.filter_list_item, eventDescriptions));
        wireFathersSide();
        wireMothersSide();
        wireMaleEvents();
        wireFemaleEvents();
    }



    private void getEventDescriptions()
    {
        //get all the event descriptions in memory, normailized to lower case, and put them in eventDescriptions
        ArrayList<Event> allEvents = ModelData.getInstance().allEvents;
        for (Event e : allEvents)
        {
            String description = e.getDescription().toLowerCase();
            if (!eventDescriptions.contains(description))
            {
                eventDescriptions.add(description);
            }
        }
    }

    private void wireFathersSide()
    {
        //wire the text
        fathersSideTopText = (TextView) findViewById(R.id.fathers_side_top_text);
        fathersSideTopText.setText("Father's Events");
        fathersSideBottomText = (TextView) findViewById(R.id.fathers_side_bottom_text);
        fathersSideBottomText.setText("Filter by father's side of family");
        //wire the switch, set on checked changed listener
        fathersSideSwitch = (Switch) findViewById(R.id.fathers_side_switch);
        fathersSideSwitch.setChecked(ModelData.getInstance().fathersSideSwitchOn);
        fathersSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    Toast.makeText(getApplicationContext(), "Switch on", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().fathersSideSwitchOn = true;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Switch off", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().fathersSideSwitchOn = false;
                }
            }
        });
    }

    private void wireMothersSide()
    {
        //wire the text
        mothersSideTopText = (TextView) findViewById(R.id.mothers_side_top_text);
        mothersSideTopText.setText("Mother's Events");
        mothersSideBottomText = (TextView) findViewById(R.id.mothers_side_bottom_text);
        mothersSideBottomText.setText("Filter by mother's side of family");
        //wire the switch, set on checked changed listener
        mothersSideSwitch = (Switch) findViewById(R.id.mothers_side_switch);
        mothersSideSwitch.setChecked(ModelData.getInstance().mothersSideSwitchOn);
        mothersSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    Toast.makeText(getApplicationContext(), "Switch on", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().mothersSideSwitchOn = true;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Switch off", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().mothersSideSwitchOn = false;
                }
            }
        });
    }

    private void wireMaleEvents()
    {
        //wire the text
        maleEventsTopText = (TextView) findViewById(R.id.male_events_top_text);
        maleEventsTopText.setText("Male Events");
        maleEventsBottomText = (TextView) findViewById(R.id.male_events_bottom_text);
        maleEventsBottomText.setText("Filter events based on gender");
        //wire the switch, set on checked changed listener
        maleEventsSwitch = (Switch) findViewById(R.id.male_events_switch);
        maleEventsSwitch.setChecked(ModelData.getInstance().maleEventsSwitchOn);
        maleEventsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    Toast.makeText(getApplicationContext(), "Switch on", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().maleEventsSwitchOn = true;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Switch off", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().maleEventsSwitchOn = false;
                }
            }
        });
    }

    private void wireFemaleEvents()
    {
        //wire the text
        femaleEventsTopText = (TextView) findViewById(R.id.female_events_top_text);
        femaleEventsTopText.setText("Female Events");
        femaleEventsBottomText = (TextView) findViewById(R.id.female_events_bottom_text);
        femaleEventsBottomText.setText("Filter events based on gender");
        //wire the switch, set on checked changed listener
        femaleEventsSwitch = (Switch) findViewById(R.id.female_events_switch);
        femaleEventsSwitch.setChecked(ModelData.getInstance().femaleEventsSwitchOn);
        femaleEventsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    Toast.makeText(getApplicationContext(), "Switch on", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().femaleEventsSwitchOn = true;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Switch off", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().femaleEventsSwitchOn = false;
                }
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                buildFilters();
                onBackPressed();
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }



    protected void buildFilters()
    {
        //1st level filtering: event types
        ModelData.getInstance().eventResponse.setEvents((ArrayList<Event>)ModelData.getInstance().allEvents.clone());
        ModelData.getInstance().fillPersonIDtoEventsMap();
        Iterator<Event> eventIterator = ModelData.getInstance().allEvents.iterator();
        while (eventIterator.hasNext())
        {
            Event e = eventIterator.next();
            if (ModelData.getInstance().eventTypesSwitchMap.get(e.getDescription().toLowerCase()) == false)
            {
                ModelData.getInstance().eventResponse.getEvents().remove(e);
            }
        }

        //2nd level filtering: Father and mothers side
        for (Person p : ModelData.getInstance().personResponse.getPeople())
        {
            if (!ModelData.getInstance().fathersSideSwitchOn && p.isOnFathersSide())
            {
                //delete all events associated with this person
                ArrayList<Event> eventsOfPerson = ModelData.getInstance().personIDtoEvents.getPersonIDtoEventsMap().get(p.getPersonID());
                ModelData.getInstance().eventResponse.getEvents().removeAll(eventsOfPerson);
            }
            if (!ModelData.getInstance().mothersSideSwitchOn && !p.isOnFathersSide())
            {
                //delete all events associated with this person
                ArrayList<Event> eventsOfPerson = ModelData.getInstance().personIDtoEvents.getPersonIDtoEventsMap().get(p.getPersonID());
                ModelData.getInstance().eventResponse.getEvents().removeAll(eventsOfPerson);
            }
            //3rd level filtering: male and female
            if(!ModelData.getInstance().maleEventsSwitchOn && p.getGender().equals("m"))
            {
                //delete all events associated with this person
                ArrayList<Event> eventsOfPerson = ModelData.getInstance().personIDtoEvents.getPersonIDtoEventsMap().get(p.getPersonID());
                ModelData.getInstance().eventResponse.getEvents().removeAll(eventsOfPerson);
            }
            if(!ModelData.getInstance().femaleEventsSwitchOn && p.getGender().equals("f"))
            {
                //delete all events associated with this person
                ArrayList<Event> eventsOfPerson = ModelData.getInstance().personIDtoEvents.getPersonIDtoEventsMap().get(p.getPersonID());
                ModelData.getInstance().eventResponse.getEvents().removeAll(eventsOfPerson);
            }
        }

    }
}


