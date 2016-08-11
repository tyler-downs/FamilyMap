package com.example.tyler.familymap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tyler.familymap.R;
import com.example.tyler.familymap.adapter.ExpandableListAdapter;
import com.example.tyler.familymap.model.Event;
import com.example.tyler.familymap.model.FamilyTree;
import com.example.tyler.familymap.model.ModelData;
import com.example.tyler.familymap.model.Person;
import com.example.tyler.familymap.model.PersonIDtoEvents;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tyler on 8/1/2016.
 */
public class PersonActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView listView;
    ArrayList<String> listHeaders = new ArrayList<>();
    HashMap<String, ArrayList<ArrayList<String>>> listChildData = new HashMap<>();
    TextView firstNameTextView;
    TextView lastNameTextView;
    TextView genderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_activity);

        listView = (ExpandableListView) findViewById(R.id.expandable_list_view);

        //Inflate the other things too
        firstNameTextView = (TextView) findViewById(R.id.first_name_person);
        lastNameTextView = (TextView) findViewById(R.id.last_name_person);
        genderTextView = (TextView) findViewById(R.id.gender_person);

        Person person = ModelData.getInstance().personIDMap.getPersonIDmap().get(getIntent().getStringExtra("personID"));
        firstNameTextView.setText(person.getFirstName());
        lastNameTextView.setText(person.getLastName());
        if (person.getGender().equals("m"))
        {
            genderTextView.setText("Male");
        }
        else genderTextView.setText("Female");

        //enable the up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Prepare the list data
        prepData();

        listAdapter = new ExpandableListAdapter(this, listHeaders, listChildData);

        listView.setAdapter(listAdapter);

        // Listview Group click listener
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listHeaders.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listHeaders.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listHeaders.get(groupPosition)
                                + " : "
                                + listChildData.get(
                                listHeaders.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                //if the child that is clicked is in the
                if (listHeaders.get(groupPosition).equals("Family"))
                {
                    //create and inflate another person activity for the person that was chosen
                    Intent i = new Intent(getApplicationContext(), PersonActivity.class);
                    i.putExtra("personID", listChildData.get("Family").get(childPosition).get(3));
                    startActivity(i);
                }
                else
                {
                    //create and initialize a map activity
                    Intent i = new Intent(getApplicationContext(), MapActivity.class);
                    i.putExtra("eventID", listChildData.get("Events").get(childPosition).get(2));
                    startActivity(i);
                }
                return false;
            }
        });
    }

    private void prepData()
    {
        String personID = getIntent().getStringExtra("personID");
        Person person = ModelData.getInstance().personIDMap.getPersonIDmap().get(personID);
        ArrayList<Event> eventsOfPerson = PersonIDtoEvents.sortEventsChronologically(
                ModelData.getInstance().personIDtoEvents.getPersonIDtoEventsMap().get(personID));
        //The events are now sorted chronologically
        //Now create a list of event strings
        ArrayList<String> eventsStrings = new ArrayList<>();
        for (Event e : eventsOfPerson)
        {
            eventsStrings.add(e.toString());
        }
        ArrayList<ArrayList<String>> eventdata = new ArrayList<>();
        for (int i = 0; i < eventsStrings.size(); i++)
        {
            ArrayList<String> singleEventData = new ArrayList<String>();
            singleEventData.add(eventsStrings.get(i));
            singleEventData.add(ModelData.getInstance().personIDMap.getPersonIDmap().get(personID).toString());
            singleEventData.add(eventsOfPerson.get(i).getEventID()); //index 2 of inner arrayList contains eventID
            eventdata.add(singleEventData);
        }
        //now create the list of family
        ArrayList<ArrayList<String>> familydata = new ArrayList<>();
        if (person.getFather() != null)
        {
            Person father = ModelData.getInstance().personIDMap.getPersonIDmap().get(person.getFather());
            //get the name and "father" and put into family data
            ArrayList<String> fatherStrings = new ArrayList<>();
            fatherStrings.add(father.toString());
            fatherStrings.add("Father");
            fatherStrings.add("");
            fatherStrings.add(3, father.getPersonID());
            familydata.add(fatherStrings);
        }
        if (person.getMother() != null)
        {
            Person mother = ModelData.getInstance().personIDMap.getPersonIDmap().get(person.getMother());
            //get the name and "mother" and put into family data
            ArrayList<String> motherStrings = new ArrayList<>();
            motherStrings.add(mother.toString());
            motherStrings.add("Mother");
            motherStrings.add("");
            motherStrings.add(3, mother.getPersonID());
            familydata.add(motherStrings);
        }
        if (person.getSpouse() != null)
        {
            Person spouse = ModelData.getInstance().personIDMap.getPersonIDmap().get(person.getSpouse());
            //get the name and "spouse" and put into family data
            ArrayList<String> spouseStrings = new ArrayList<>();
            spouseStrings.add(spouse.toString());
            spouseStrings.add("Spouse");
            spouseStrings.add(spouse.getGender());
            spouseStrings.add(3, spouse.getPersonID());
            familydata.add(spouseStrings);
        }
        if (!person.getChildren().isEmpty())
        {
            for (String s : person.getChildren())
            {
                //s is the ID of the child
                Person child = ModelData.getInstance().personIDMap.getPersonIDmap().get(s);
                ArrayList<String> childStrings = new ArrayList<>();
                childStrings.add(child.toString());
                if(child.getGender().equals("m"))
                {
                    childStrings.add("Son");
                }
                else childStrings.add("Daughter");
                childStrings.add("");
                childStrings.add(3, child.getPersonID());
                familydata.add(childStrings);
            }
        }

        listHeaders.add("Events");
        listHeaders.add("Family");
        listChildData.put("Events", eventdata);
        listChildData.put("Family", familydata);

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
