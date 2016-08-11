package com.example.tyler.familymap.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.amazon.geo.mapsv2.AmazonMap;
import com.example.tyler.familymap.R;
import com.example.tyler.familymap.communication.FamilyMapProxy;
import com.example.tyler.familymap.communication.IProxyDelegate;
import com.example.tyler.familymap.communication.ProxyResult;
import com.example.tyler.familymap.enums.LineColor;
import com.example.tyler.familymap.model.Event;
import com.example.tyler.familymap.model.LoginCredentials;
import com.example.tyler.familymap.model.ModelData;
import com.example.tyler.familymap.model.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tyler on 8/4/2016.
 */
public class SettingsActivity extends AppCompatActivity implements IProxyDelegate{

    //Switches
    Switch mLifeStoryLinesSwtich;
    Switch mFamilyTreeLinesSwitch;
    Switch mSpouseLinesSwitch;

    //Spinners
    Spinner mLifeStoryLinesSpinner;
    Spinner mFamilyTreeLinesSpinner;
    Spinner mSpouseLinesSpinner;
    Spinner mMapTypeSpinner;

    //TextViews
    TextView mResyncTextView;
    TextView mLogoutTextView;

    FamilyMapProxy familyMapProxy;
    String authToken;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);

        //enable the up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wireLifeStoryLines();
        wireFamilyTreeLines();
        wireSpouseLines();
        wireMapTypeSpinner();
        wireResyncTextView();
        wireLogoutView();
    }

    /**
     * Wires the life story lines switch and spinner
     */
    private void wireLifeStoryLines(){
        mLifeStoryLinesSwtich = (Switch) findViewById(R.id.life_story_lines_switch);
        mLifeStoryLinesSwtich.setChecked(ModelData.getInstance().lifeStoryLinesOn);
        mLifeStoryLinesSwtich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(getApplicationContext(), "Switch on", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().lifeStoryLinesOn = true;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Switch off", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().lifeStoryLinesOn = false;
                }
            }
        });

        mLifeStoryLinesSpinner = (Spinner) findViewById(R.id.life_story_lines_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_colors_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mLifeStoryLinesSpinner.setAdapter(adapter);
        mLifeStoryLinesSpinner.setSelection(ModelData.getInstance().lifeStoryLineColor.ordinal());
        mLifeStoryLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = adapterView.getItemAtPosition(i).toString();
                switch (selection){
                    case "Green":
                        //change enum to green
                        ModelData.getInstance().lifeStoryLineColor = LineColor.GREEN;
                        break;
                    case "Cyan":
                        //change enum to cyan
                        ModelData.getInstance().lifeStoryLineColor = LineColor.CYAN;
                        break;
                    case "Magenta":
                        //change enum to magenta
                        ModelData.getInstance().lifeStoryLineColor = LineColor.MAGENTA;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * Wires the family tree lines switch and spinner
     */
    private void wireFamilyTreeLines(){
        mFamilyTreeLinesSwitch = (Switch) findViewById(R.id.family_tree_lines_switch);
        mFamilyTreeLinesSwitch.setChecked(ModelData.getInstance().familyTreeLinesOn);
        mFamilyTreeLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(getApplicationContext(), "Switch on", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().familyTreeLinesOn = true;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Switch off", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().familyTreeLinesOn = false;
                }
            }
        });

        mFamilyTreeLinesSpinner = (Spinner) findViewById(R.id.family_tree_lines_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_colors_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mFamilyTreeLinesSpinner.setAdapter(adapter);
        mFamilyTreeLinesSpinner.setSelection(ModelData.getInstance().familyTreeLineColor.ordinal());
        mFamilyTreeLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = adapterView.getItemAtPosition(i).toString();
                switch (selection){
                    case "Green":
                        //change enum to green
                        ModelData.getInstance().familyTreeLineColor = LineColor.GREEN;
                        break;
                    case "Cyan":
                        //change enum to cyan
                        ModelData.getInstance().familyTreeLineColor = LineColor.CYAN;
                        break;
                    case "Magenta":
                        //change enum to magenta
                        ModelData.getInstance().familyTreeLineColor = LineColor.MAGENTA;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * Wires the spouse lines switch and spinner
     */
    private void wireSpouseLines(){
        mSpouseLinesSwitch = (Switch) findViewById(R.id.spouse_lines_switch);
        mSpouseLinesSwitch.setChecked(ModelData.getInstance().spouseLinesOn);
        mSpouseLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(getApplicationContext(), "Switch on", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().spouseLinesOn = true;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Switch off", Toast.LENGTH_SHORT).show();
                    ModelData.getInstance().spouseLinesOn = false;
                }
            }
        });

        mSpouseLinesSpinner = (Spinner) findViewById(R.id.spouse_lines_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_colors_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpouseLinesSpinner.setAdapter(adapter);
        mSpouseLinesSpinner.setSelection(ModelData.getInstance().spouseLineColor.ordinal());
        mSpouseLinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = adapterView.getItemAtPosition(i).toString();
                switch (selection){
                    case "Green":
                        //change enum to green
                        ModelData.getInstance().spouseLineColor = LineColor.GREEN;
                        break;
                    case "Cyan":
                        //change enum to cyan
                        ModelData.getInstance().spouseLineColor = LineColor.CYAN;
                        break;
                    case "Magenta":
                        //change enum to magenta
                        ModelData.getInstance().spouseLineColor = LineColor.MAGENTA;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * Wires the map type spinner
     */
    private void wireMapTypeSpinner(){
        mMapTypeSpinner = (Spinner) findViewById(R.id.map_type_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_map_types_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mMapTypeSpinner.setAdapter(adapter);
        mMapTypeSpinner.setSelection(ModelData.getInstance().mapType-1);
        mMapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = adapterView.getItemAtPosition(i).toString();
                switch (selection){
                    case "Normal":
                        ModelData.getInstance().mapType = AmazonMap.MAP_TYPE_NORMAL;
                        break;
                    case "Satellite":
                        ModelData.getInstance().mapType = AmazonMap.MAP_TYPE_SATELLITE;
                        break;
                    case "Terrain":
                        ModelData.getInstance().mapType = AmazonMap.MAP_TYPE_TERRAIN;
                        break;
                    case "Hybrid":
                        ModelData.getInstance().mapType = AmazonMap.MAP_TYPE_HYBRID;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * Wires the clickable text view that resyncs the data
     */
    private void wireResyncTextView()
    {
        mResyncTextView = (TextView) findViewById(R.id.resync_name);
        mResyncTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //resync the data
                LoginCredentials loginCredentials = ModelData.getInstance().loginCredentials;
                authToken = loginCredentials.getAuthToken();
                ModelData.destroyAll();

                reSync(loginCredentials);

            }
        });
    }



    private void reSync(LoginCredentials loginCredentials)
    {
        familyMapProxy = new FamilyMapProxy(loginCredentials.getmServerHost() + ":" +
                loginCredentials.getmServerPort(), "80", getApplicationContext());

        familyMapProxy.login(loginCredentials.getmUsername(), loginCredentials.getmPassword(), this);
    }


    /**
     * Wires the logout text view clickable
     */
    private void wireLogoutView()
    {
        mLogoutTextView = (TextView) findViewById(R.id.logout_name);
        mLogoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //log out and return to the login fragment, delete all the data in memory and everything
                ModelData.destroyAll();
                //return to the main activity map fragment
                returnToLoginScreen();
            }
        });
    }

    private void returnToLoginScreen()
    {
        if(NavUtils.getParentActivityName(this) != null) {
            NavUtils.navigateUpFromSameTask(this);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // do something useful
                onBackPressed();
                return(true);
        }

        return(super.onOptionsItemSelected(item));
    }

    private String serverResponse;

    @Override
    public void onProxyResult(ProxyResult result) {
        if(result.hasError()) {
            Toast.makeText(getApplicationContext(), "The proxy had an error.", Toast.LENGTH_SHORT).show();
        }
        else {
            serverResponse = (String) result.getData();

            if (!ModelData.getInstance().loggedIn)//this would be null if the user has not logged in
            {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(serverResponse);
                    authToken = obj.getString("Authorization");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                familyMapProxy.getPeople(authToken, this);
                //familyMapProxy.getFirstLastName(loginReturns.get(0), personID, this);
                //Here, get the authorization code and person ID of yourself and save them
                //Then use those and call fmailyMapProxy.getPeople to get all the people
                ModelData.getInstance().loggedIn = true;
                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
            }
            else //the user has already logged in
            {
                if (ModelData.getInstance().personResponse.getPeople().isEmpty())
                {
                    //serverResponse contains the Person data
                    parsePeople(serverResponse); //this populates the PersonResponse.people list
                    ArrayList<Person> peopleInMemory = ModelData.getInstance().personResponse.getPeople();
                    //now call proxy.getEvents to make the proxy result yield the JSON of events
                    familyMapProxy.getEvents(authToken, this);
                    Toast.makeText(getApplicationContext(), "got People", Toast.LENGTH_SHORT).show();
                }
                else if (ModelData.getInstance().eventResponse.getEvents().isEmpty())
                {
                    //serverResponse contains the Event data
                    parseEvents(serverResponse);
                    ArrayList<Event> eventsInMemory = ModelData.getInstance().eventResponse.getEvents();
                    Toast.makeText(getApplicationContext(), "got Events", Toast.LENGTH_SHORT).show();
                    //Fill the personIDtoEvents map
                    fillPersonIDtoEventsMap();
                    //Give all the people children
                    givePeopleChildren();
                    //return to the main activity map fragment
                    if(NavUtils.getParentActivityName(this) != null) {
                        NavUtils.navigateUpFromSameTask(this);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Already logged in", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private void givePeopleChildren()
    {
        for (Person p : ModelData.getInstance().personResponse.getPeople())
        {
            for (Person child : ModelData.getInstance().personResponse.getPeople())
            {
                if (p.getGender().equals("m"))
                {
                    if (child.getFather() != null && child.getFather().equals(p.getPersonID())) //if p is child's father
                    {
                        p.getChildren().add(child.getPersonID());
                    }
                }
                else
                {
                    if (child.getMother() != null && child.getMother().equals(p.getPersonID())) //if p is child's mother
                    {
                        p.getChildren().add(child.getPersonID());
                    }
                }
            }
        }
    }

    /**
     * Fills the personIDtoEventsMap with values
     */
    private void fillPersonIDtoEventsMap()
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

    private void parseEvents(String json)
    {
        try {
            JSONObject rootObj = new JSONObject(json);
            JSONArray data = rootObj.getJSONArray("data");
            for (int i = 0; i < data.length(); i++)
            {
                JSONObject eventObj = data.getJSONObject(i);
                Event e = new Event();
                e.setEventID(eventObj.getString("eventID"));
                e.setPersonID(eventObj.getString("personID"));
                e.setLatitude(eventObj.getDouble("latitude"));
                e.setLongitude(eventObj.getDouble("longitude"));
                e.setCountry(eventObj.getString("country"));
                e.setCity(eventObj.getString("city"));
                e.setDescription(eventObj.getString("description"));
                e.setYear(eventObj.getString("year"));
                e.setDescendant(eventObj.getString("descendant"));
                //put e in eventResponse list
                ModelData.getInstance().eventResponse.addToEvents(e);
                ModelData.getInstance().eventIDMap.getEventIDmap().put(e.getEventID(), e);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Parses all the people in the person json from the server and puts them in personResponse object
     * @param json
     */
    private void parsePeople(String json)
    {
        try {
            JSONObject rootObj = new JSONObject(json);
            JSONArray data = rootObj.getJSONArray("data");
            for (int i = 0; i < data.length(); i++)
            {
                JSONObject personObj = data.getJSONObject(i);
                Person p = new Person();
                p.setDescendant(personObj.getString("descendant"));
                p.setPersonID(personObj.getString("personID"));
                p.setFirstName(personObj.getString("firstName"));
                p.setLastName(personObj.getString("lastName"));
                p.setGender(personObj.getString("gender"));
                if (personObj.has("spouse"))
                {
                    p.setSpouse(personObj.getString("spouse"));
                }
                if (personObj.has("father"))
                {
                    p.setFather(personObj.getString("father"));
                    p.setMother(personObj.getString("mother"));
                }
                //put p in the personResponse list
                ModelData.getInstance().personResponse.addToPeople(p);
                ModelData.getInstance().personIDMap.getPersonIDmap().put(p.getPersonID(), p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
