package com.example.tyler.familymap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tyler.familymap.R;
import com.example.tyler.familymap.adapter.EventListAdapter;
import com.example.tyler.familymap.adapter.PersonListAdapter;
import com.example.tyler.familymap.model.Event;
import com.example.tyler.familymap.model.ModelData;
import com.example.tyler.familymap.model.Person;

import java.util.ArrayList;

/**
 * Created by Tyler on 8/3/2016.
 */
public class SearchActivity extends AppCompatActivity {

    EditText mSearchEditText;
    ListView mPersonListView;
    ListView mEventListView;

    ArrayList<Person> personSearchResults = new ArrayList<>();
    ArrayList<Event> eventSearchResults = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_activity);

        //enable the up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Wire the edit text
        mSearchEditText = (EditText) findViewById(R.id.search_edit_text);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //generate the search depending on what is in the text field
                getPersonSearchResults();
                mPersonListView.setAdapter(new PersonListAdapter(getApplicationContext(), R.layout.search_person_result, personSearchResults));
                mPersonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //i=position
                        //Create a person activity with the Person at that point in the listView
                        Person personClicked = personSearchResults.get(i);
                        Intent personActIntent = new Intent(getApplicationContext(), PersonActivity.class);
                        personActIntent.putExtra("personID", personClicked.getPersonID());
                        startActivity(personActIntent);
                    }
                });

                //now do this^^ but for the events
                getEventSearchResults();
                mEventListView.setAdapter(new EventListAdapter(getApplicationContext(), R.layout.search_person_result, eventSearchResults));
                mEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //i=position
                        //Create a person activity with the Person at that point in the listView
                        Event eventClicked = eventSearchResults.get(i);
                        Intent mapActIntent = new Intent(getApplicationContext(), MapActivity.class);
                        mapActIntent.putExtra("eventID", eventClicked.getEventID());
                        startActivity(mapActIntent);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Configure the person list view
        mPersonListView = (ListView) findViewById(R.id.person_list_view_search);

        //Configure the events list view
        mEventListView = (ListView) findViewById(R.id.event_list_view_search);

    }

    /**
     * Gets the search results from the string currently in the EditText
     */
    private void getPersonSearchResults(){
        String searchString = mSearchEditText.getText().toString();
        if (!personSearchResults.isEmpty())
        {
            personSearchResults.clear();
        }
        if(searchString == null || searchString.equals(""))
        {
            return;
        }
        //now find the people that have searchString as a substring in their first OR last name and add them to the searchresults
        ArrayList<Person> allPeople = ModelData.getInstance().personResponse.getPeople();
        for (Person p : allPeople)
        {
            if (p.toString().toLowerCase().contains(searchString.toLowerCase()))
            {
                personSearchResults.add(p);
            }
        }
    }

    /**
     * Gets the search results from the string currently in the editText
     */
    private void getEventSearchResults()
    {
        String searchString = mSearchEditText.getText().toString();
        if (!eventSearchResults.isEmpty())
        {
            eventSearchResults.clear();
        }
        if(searchString == null || searchString.equals(""))
        {
            return;
        }
        ArrayList<Event> allEvents = ModelData.getInstance().eventResponse.getEvents();
        for (Event e : allEvents)
        {
            //type, city, country, date, and associated person's name
            Person personOfEvent = ModelData.getInstance().personIDMap.getPersonIDmap().get(e.getPersonID());
            if (e.getDescription().toLowerCase().contains(searchString.toLowerCase()) ||
                    e.getCity().toLowerCase().contains(searchString.toLowerCase()) ||
                    e.getCountry().toLowerCase().contains(searchString.toLowerCase()) ||
                    e.getYear().toLowerCase().contains(searchString.toLowerCase()) ||
                    personOfEvent.toString().toLowerCase().contains(searchString.toLowerCase()))
            {
                eventSearchResults.add(e);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.go_to_top_only_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return(true);
            case R.id.menu_item_go_to_top:
                //go to the top, destroying all activities on the way to the mainActivity
                Toast.makeText(getApplicationContext(), "go to top button pressed", Toast.LENGTH_SHORT).show();
                if(NavUtils.getParentActivityName(this) != null) {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

}
