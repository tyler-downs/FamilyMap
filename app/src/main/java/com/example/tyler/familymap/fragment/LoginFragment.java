package com.example.tyler.familymap.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tyler.familymap.activity.MainActivity;
import com.example.tyler.familymap.R;
import com.example.tyler.familymap.communication.FamilyMapProxy;
import com.example.tyler.familymap.communication.IProxyDelegate;
import com.example.tyler.familymap.communication.ProxyResult;
import com.example.tyler.familymap.model.Event;
import com.example.tyler.familymap.model.EventIDMap;
import com.example.tyler.familymap.model.EventResponse;
import com.example.tyler.familymap.model.LoginCredentials;
import com.example.tyler.familymap.model.ModelData;
import com.example.tyler.familymap.model.Person;
import com.example.tyler.familymap.model.PersonIDMap;
import com.example.tyler.familymap.model.PersonIDtoEvents;
import com.example.tyler.familymap.model.PersonResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tyler on 7/26/2016.
 */
public class LoginFragment extends Fragment implements IProxyDelegate{ //hosted by activity MainActivity

    private LoginCredentials mLoginCredentials;

    private EditText mUsernameField;
    private EditText mPasswordField;
    private EditText mServerHostField;
    private EditText mServerPortField;
    private Button mSignInButton;

    public FamilyMapProxy familyMapProxy;

    private String serverResponse;
    private String myPersonID;
    private String authToken;

    private MainActivity mainActivityRef;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mLoginCredentials = new LoginCredentials();
        //do not inflate the fragment's view in this function
    }

    /**
     * Inflate the layout for the fragment's view and return the inflated View to the hosting activity
     * Also "wire up" the editText fields to respond to user input
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the inflated view from login_fragment.xml
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Inflate the view
        View v = inflater.inflate(R.layout.login_fragment, container, false);

        //wire up the edit text fields to respond to user input
        //Just using a listener for the button and calling a different function when the button is pressed that will do all the server tasks
        mSignInButton = (Button) v.findViewById(R.id.sign_in_button);
        mUsernameField = (EditText) v.findViewById(R.id.username);
        mPasswordField = (EditText) v.findViewById(R.id.password);
        mServerHostField = (EditText) v.findViewById(R.id.server_host);
        mServerPortField = (EditText) v.findViewById(R.id.server_port);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do whatever happens when you click the button
                /*
                1. save all the currently typed information from the editText fields into the model object
                2. create the async task that will try to log you in, etc
                 */
                mLoginCredentials.setmUsername(mUsernameField.getText().toString());
                mLoginCredentials.setmPassword(mPasswordField.getText().toString());
                mLoginCredentials.setmServerHost(mServerHostField.getText().toString());
                mLoginCredentials.setmServerPort(mServerPortField.getText().toString());

                familyMapProxy = new FamilyMapProxy(mLoginCredentials.getmServerHost() + ":" +
                        mLoginCredentials.getmServerPort(), "80", getContext());

                ModelData.getInstance().loginCredentials = mLoginCredentials;
                login(mLoginCredentials.getmUsername(), mLoginCredentials.getmPassword());

            }
        });


        return v;
    }

    /**
     * Wrapper function for familyMapProxy login stuff
     * @param username
     * @param password
     */
    private void login(String username, String password)
    {
        familyMapProxy.login(username, password, this);
    }



    @Override
    public void onProxyResult(ProxyResult result) {
        if(result.hasError()) {
            Toast.makeText(getActivity(), "The proxy had an error.", Toast.LENGTH_SHORT).show();
        }
        else {
            serverResponse = (String) result.getData();

            if (myPersonID == null)//this would be null if the user has not logged in
            {
                familyMapProxy.getPeople(authToken, this);
                Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_SHORT).show();
            }
            else //the user has already logged in
            {
                if (ModelData.getInstance().personResponse.getPeople().isEmpty())
                {
                    //serverResponse contains the Person data
                    parsePeople(serverResponse); //this populates the PersonResponse.people list
                    //now call proxy.getEvents to make the proxy result yield the JSON of events
                    familyMapProxy.getEvents(authToken, this);
                    Toast.makeText(getActivity(), "got People", Toast.LENGTH_SHORT).show();
                }
                else if (ModelData.getInstance().eventResponse.getEvents().isEmpty())
                {
                    //serverResponse contains the Event data
                    parseEvents(serverResponse);
                    Toast.makeText(getActivity(), "got Events", Toast.LENGTH_SHORT).show();
                    //Fill the personIDtoEvents map
                    ModelData.getInstance().fillPersonIDtoEventsMap();
                    //Give all the people children
                    givePeopleChildren();
                    //get the reference to the user's person object into the singleton
                    getReferenceToMe();
                    //Set the father/mother side bool in each person object using this function
                    setFatherOrMotherSide();
                    //Now all data is in the Person and Event Response classes
                    //Now switch the fragment
                    mainActivityRef = (MainActivity) getActivity();
                    mainActivityRef.switchFragment();
                }
                else
                {
                    Toast.makeText(getActivity(), "Already logged in", Toast.LENGTH_SHORT).show();
                }
            }

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

    /**
     * Parses the events JSON from the server and puts the events into model objects
     * @param json the JSON of events from the server
     */
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
                ModelData.getInstance().allEvents.add(e);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    /**
     * Parses the JSON retrieved from a successful login
     * @param json
     * @return an array list of the Authorization token and the personID, in that order
     */
    private ArrayList<String> parseLogin(String json)
    {
        ArrayList<String> list = new ArrayList<>();
        //parse the JSON
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(serverResponse);
            authToken = (String) jsonObject.get("Authorization");
            ModelData.getInstance().loginCredentials.setAuthToken(authToken);
            myPersonID = (String) jsonObject.get("personId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        list.add(authToken);
        list.add(myPersonID);
        return list;
    }


    /**
     * Iterates through the list of all people and gives each person a reference to their own children
     */
    protected void givePeopleChildren()
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
     * Uses the myPersonID obtained from the login to get a reference to the user's own person object
     * and saves it into the ModelData singleton
     */
    private void getReferenceToMe()
    {
        Person me;
        for (String s : ModelData.getInstance().personIDMap.getPersonIDmap().keySet())
        {
            if (s.equals(myPersonID))
            {
                me = ModelData.getInstance().personIDMap.getPersonIDmap().get(s);
                ModelData.getInstance().me = me;
                break;
            }
        }
    }

    /**
     * The function that houses and facilitates the calling of the recursive functions that will
     * set, for each person, whether the person is on the father's or mother's side of the user
     */
    protected void setFatherOrMotherSide()
    {
        Person me = ModelData.getInstance().me;
        Person mother = ModelData.getInstance().personIDMap.getPersonIDmap().get(me.getMother());
        Person father = ModelData.getInstance().personIDMap.getPersonIDmap().get(me.getFather());
        mother.setOnFathersSide(false);
        setMotherSide(mother);
        father.setOnFathersSide(true);
        setFatherSide(father);
    }

    /**
     * The user's father is passed in. This recursively goes through all people in the model and sets
     * onFathersSide to true
     * @param person
     */
    private void setFatherSide(Person person)
    {
        if (person.getMother() != null)
        {
            Person mother = ModelData.getInstance().personIDMap.getPersonIDmap().get(person.getMother());
            mother.setOnFathersSide(true);
            setFatherSide(mother);
        }
        if (person.getFather() != null)
        {
            Person father = ModelData.getInstance().personIDMap.getPersonIDmap().get(person.getFather());
            father.setOnFathersSide(true);
            setFatherSide(father);
        }
    }

    /**
     * The user's mother is passed in. This recursively goes through all people in the model and sets
     * onFathersSide to false
     * @param person
     */
    private void setMotherSide(Person person)
    {
        if (person.getMother() != null)
        {
            Person mother = ModelData.getInstance().personIDMap.getPersonIDmap().get(person.getMother());
            mother.setOnFathersSide(false);
            setMotherSide(mother);
        }
        if (person.getFather() != null)
        {
            Person father = ModelData.getInstance().personIDMap.getPersonIDmap().get(person.getFather());
            father.setOnFathersSide(false);
            setMotherSide(father);
        }
    }
}
