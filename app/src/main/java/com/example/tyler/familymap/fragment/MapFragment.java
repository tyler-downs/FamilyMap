package com.example.tyler.familymap.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.CameraUpdate;
import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.model.BitmapDescriptor;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.amazon.geo.mapsv2.model.Polyline;
import com.amazon.geo.mapsv2.model.PolylineOptions;
import com.example.tyler.familymap.R;
import com.example.tyler.familymap.activity.FilterActivity;
import com.example.tyler.familymap.activity.PersonActivity;
import com.example.tyler.familymap.activity.SearchActivity;
import com.example.tyler.familymap.activity.SettingsActivity;
import com.example.tyler.familymap.enums.LineColor;
import com.example.tyler.familymap.model.Event;
import com.example.tyler.familymap.model.EventIDMap;
import com.example.tyler.familymap.model.EventResponse;
import com.example.tyler.familymap.model.FamilyTree;
import com.example.tyler.familymap.model.ModelData;
import com.example.tyler.familymap.model.Person;
import com.example.tyler.familymap.model.PersonIDMap;
import com.example.tyler.familymap.model.PersonIDtoEvents;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Tyler on 7/27/2016.
 */
public class MapFragment extends Fragment {

    TextView mNameTextView;
    TextView mEventTextView;
    ImageView mImageView;
    ArrayList<Polyline> polylines = new ArrayList<>();
    String mStartEventID;
    ArrayList<Event> myEvents;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Do not inflate the view in this function
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View v = inflater.inflate(R.layout.map_fragment, container, false);
        setHasOptionsMenu(true);

        ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(AmazonMap amazonMap) {
                ModelData.getInstance().map = amazonMap;
                ModelData.getInstance().map.setMapType(ModelData.getInstance().mapType);
                //Here put a default message and icon into the text views of the map fragment
                mNameTextView = (TextView) v.findViewById(R.id.person_name);
                mEventTextView = (TextView) v.findViewById(R.id.event_details);
                mImageView = (ImageView) v.findViewById(R.id.icon_image);
                mNameTextView.setText("Select a pin");
                mEventTextView.setText("The event info will be displayed here");
                Drawable defaultIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_android)
                        .sizeDp(40);
                mImageView.setImageDrawable(defaultIcon);
                addMarkers(ModelData.getInstance().map);
                if (mStartEventID != null)
                {
                    setStartEventCamera();
                }
                ModelData.getInstance().map.setOnMarkerClickListener(new AmazonMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        //do what you do when you click on a marker
                        //Display the person's name and the event data in the Text View
                        Event eventInQuestion = ModelData.getInstance().eventIDMap.getEventIDmap().get(marker.getTitle());
                        final String personID = eventInQuestion.getPersonID();
                        Person personOfEvent = ModelData.getInstance().personIDMap.getPersonIDmap().get(personID);
                        String personName = personOfEvent.toString();
                        mNameTextView.setText(personName);
                        //Display the event data
                        String eventText = eventInQuestion.toString();
                        mEventTextView.setText(eventText);
                        //Draw the icon
                        drawGenderIcon(personOfEvent);
                        mImageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getActivity(), PersonActivity.class);
                                i.putExtra("personID", personID);
                                startActivity(i);
                            }
                        });
                        //Clear the existing polylines, if any
                        clearPolylines();
                        //Draw the spouse lines
                        drawSpouseLines(ModelData.getInstance().map, eventInQuestion, personOfEvent, ModelData.getInstance().spouseLinesOn);
                        //Draw the family tree lines
                        drawFamilyTreeLines(ModelData.getInstance().map, eventInQuestion, personOfEvent, ModelData.getInstance().familyTreeLinesOn, 10);
                        //Draw the life story lines
                        drawLifeStoryLines(ModelData.getInstance().map, eventInQuestion, personOfEvent, ModelData.getInstance().lifeStoryLinesOn);
                        return true;
                    }
                });
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        if (mStartEventID == null)
        {
            inflater.inflate(R.menu.map_fragment_action_bar_menu, menu);
            MenuItem search = menu.getItem(0);
            MenuItem filter = menu.getItem(1);
            MenuItem settings = menu.getItem(2);
            //Set the search icon
            Drawable searchIcon = new IconDrawable(getContext(), Iconify.IconValue.fa_search).sizeDp(40).color(Color.WHITE);
            search.setIcon(searchIcon);
            Drawable filterIcon = new IconDrawable(getContext(), Iconify.IconValue.fa_filter).sizeDp(40).color(Color.WHITE);
            filter.setIcon(filterIcon);
            Drawable settingsIcon = new IconDrawable(getContext(), Iconify.IconValue.fa_cog).sizeDp(40).color(Color.WHITE);
            settings.setIcon(settingsIcon);
        }
        else
        {
            inflater.inflate(R.menu.go_to_top_only_menu, menu);
        }
    }

    private void addMarkers(AmazonMap map)
    {
        map.clear();
        //go through the Events list and add a marker for every event in the list
        for (Event e : ModelData.getInstance().eventResponse.getEvents())
        {
            MarkerOptions options = new MarkerOptions();
            options.position(new LatLng(e.getLatitude(), e.getLongitude()))
                    .title(e.getEventID());
            switch (e.getDescription()){
                case "birth":
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    break;
                case "christening":
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                    break;
                case "census":
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    break;
                case "marriage":
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                    break;
                case "baptism":
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    break;
                case "death":
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    break;
                default:
                    //default case
                    break;

            }

            Marker m = map.addMarker(options);
            e.setMarkerRef(m);
        }
    }

    private void drawGenderIcon(Person person)
    {
        switch (person.getGender()){
            case "m":
                Drawable maleIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_male).
                        colorRes(R.color.male_icon).sizeDp(40);
                mImageView.setImageDrawable(maleIcon);
                break;
            case "f":
                Drawable femaleIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_female).
                        colorRes(R.color.female_icon).sizeDp(40);
                mImageView.setImageDrawable(femaleIcon);
                break;
            default:
                break;
        }
    }

    /**
     * Draws the spouse lines on the map when the person is clicked
     * @param map the map
     * @param event the event that was clicked
     * @param person the person related to that event
     * @param isVisible boolean for if the lines should be visible
     */
    private void drawSpouseLines(AmazonMap map, Event event, Person person, boolean isVisible)
    {
        //Different color for each kind of line
        if (person.getSpouse() != null)
        {
            //if birth event is recorded for the spouse, draw a line from this event to that
            String spouseID = person.getSpouse();
            ArrayList<Event> spouseEvents = ModelData.getInstance().personIDtoEvents.getPersonIDtoEventsMap().get(spouseID);
            //Check the spouseEvents for a birth event
            Event lowestYearEvent = getLowestYearEvent(spouseEvents);
            if (spouseEvents == null || spouseEvents.isEmpty() || lowestYearEvent == null)
            {
                return;
            }
            //now draw the line from the event in question to the lowest year event
            PolylineOptions opt = new PolylineOptions()
                                .add(event.getMarkerRef().getPosition(), lowestYearEvent.getMarkerRef().getPosition())
                                .color(lineColorEnumToInt(ModelData.getInstance().spouseLineColor))
                                .visible(isVisible);

            Polyline p = map.addPolyline(opt);
            polylines.add(p);
        }

    }

    private void drawFamilyTreeLines(AmazonMap map, Event event, Person person, boolean isVisible, float width)
    {
        if (person.getFather() != null && !ModelData.getInstance().personIDtoEvents.getPersonIDtoEventsMap().get(person.getFather()).isEmpty())
        {
            //get the lowest year event from the father's events, draw the line, and recursively call, then return
            Event lowestEvent = getLowestYearEvent(ModelData.getInstance().personIDtoEvents.getPersonIDtoEventsMap().get(person.getFather()));
            if (lowestEvent == null)
            {
                return;
            }
            PolylineOptions opt = new PolylineOptions()
                    .add(event.getMarkerRef().getPosition(), lowestEvent.getMarkerRef().getPosition())
                    .color(lineColorEnumToInt(ModelData.getInstance().familyTreeLineColor))
                    .width(width)
                    .visible(isVisible);

            Polyline p = map.addPolyline(opt);
            polylines.add(p);
            drawFamilyTreeLines(map, lowestEvent, ModelData.getInstance().personIDMap.getPersonIDmap().get(person.getFather()),
                    isVisible, width-1.5f);
        }
        if (person.getMother() != null &&!ModelData.getInstance().personIDtoEvents.getPersonIDtoEventsMap().get(person.getMother()).isEmpty())
        {
            //get the lowest year event from the mother's events, draw the line, and recursively call, then return
            Event lowestEvent = getLowestYearEvent(ModelData.getInstance().personIDtoEvents.getPersonIDtoEventsMap().get(person.getMother()));
            if (lowestEvent == null)
            {
                return;
            }
            PolylineOptions opt = new PolylineOptions()
                    .add(event.getMarkerRef().getPosition(), lowestEvent.getMarkerRef().getPosition())
                    .color(lineColorEnumToInt(ModelData.getInstance().familyTreeLineColor))
                    .width(width)
                    .visible(isVisible);

            Polyline p = map.addPolyline(opt);
            polylines.add(p);

            drawFamilyTreeLines(map, lowestEvent, ModelData.getInstance().personIDMap.getPersonIDmap().get(person.getMother()),
                    isVisible, width-1.5f);
        }
    }


    /**
     * Takes a list of events and returns the one with the lowest year
     * @param events
     * @return
     */
    private Event getLowestYearEvent(ArrayList<Event> events)
    {
        Event lowestYearEvent = new Event();
        int minYear = 3000; //not much has changed except they live underwater
        for (Event e : events)
        {
            if (Integer.valueOf(e.getYear()) < minYear)
            {
                minYear = Integer.valueOf(e.getYear());
            }
        }
        //now minYear contains the minimum year of all the events
        for (Event e : events)
        {
            if (Integer.valueOf(e.getYear()) == minYear)
            {
                lowestYearEvent = e;
            }
        }
        return lowestYearEvent;
    }

    private void drawLifeStoryLines(AmazonMap map, Event event, Person person, boolean isVisible)
    {
        ArrayList<Event> eventsOfPerson = ModelData.getInstance().personIDtoEvents.getPersonIDtoEventsMap().get(person.getPersonID());
        ArrayList<Event> sortedEvents = PersonIDtoEvents.sortEventsChronologically(eventsOfPerson);
        if (sortedEvents == null || sortedEvents.isEmpty())
        {
            return;
        }
        //now draw lines between each of the events
        for (int i = 0; i < sortedEvents.size()-1; i++)
        {

        }
        PolylineOptions opt = new PolylineOptions();
        for (Event e : sortedEvents)
        {
            opt.add(e.getMarkerRef().getPosition());
        }
        opt.color(lineColorEnumToInt(ModelData.getInstance().lifeStoryLineColor));
        opt.visible(isVisible);
        Polyline p = map.addPolyline(opt);
        polylines.add(p);
    }

    private void clearPolylines()
    {
        if (!polylines.isEmpty())
        {
            for (Polyline p : polylines)
            {
                p.remove();
            }
            polylines.clear();
        }
    }

    private void setStartEventCamera()
    {
        Event startEvent = ModelData.getInstance().eventIDMap.getEventIDmap().get(mStartEventID);
        LatLng startEventLocation = new LatLng(startEvent.getLatitude(), startEvent.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(startEventLocation, 5);
        ModelData.getInstance().map.animateCamera(cameraUpdate);

        //do what you do when you click on a marker
        //Display the person's name and the event data in the Text View
        Event eventInQuestion = ModelData.getInstance().eventIDMap.getEventIDmap().get(startEvent.getMarkerRef().getTitle());
        final String personID = eventInQuestion.getPersonID();
        Person personOfEvent = ModelData.getInstance().personIDMap.getPersonIDmap().get(personID);
        String personName = personOfEvent.getFirstName() + " " + personOfEvent.getLastName();
        mNameTextView.setText(personName);
        //Display the event data
        String eventText = eventInQuestion.toString();
        mEventTextView.setText(eventText);
        //Draw the icon
        drawGenderIcon(personOfEvent);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PersonActivity.class);
                i.putExtra("personID", personID);
                startActivity(i);
            }
        });
        //Clear the existing polylines, if any
        clearPolylines();
        //Draw the spouse lines
        drawSpouseLines(ModelData.getInstance().map, eventInQuestion, personOfEvent, true);
        //Draw the family tree lines
        drawFamilyTreeLines(ModelData.getInstance().map, eventInQuestion, personOfEvent, true, 10);
        //Draw the life story lines
        drawLifeStoryLines(ModelData.getInstance().map, eventInQuestion, personOfEvent, true);
    }

    public String getmStartEventID() {
        return mStartEventID;
    }

    public void setmStartEventID(String mStartEventID) {
        this.mStartEventID = mStartEventID;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_search:
                //create the search activity
                Intent searchIntent = new Intent(getContext(), SearchActivity.class);
                startActivity(searchIntent);
                return true;
            case R.id.menu_item_filter:
                //create the filter activity
                Intent filterIntent = new Intent(getContext(), FilterActivity.class);
                startActivity(filterIntent);
                return true;
            case R.id.menu_item_settings:
                //create the settings activity
                Intent settingsIntent = new Intent(getContext(), SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int lineColorEnumToInt(LineColor lineColor)
    {
        switch (lineColor){
            case GREEN:
                return Color.GREEN;
            case CYAN:
                return Color.CYAN;
            case MAGENTA:
                return Color.MAGENTA;
            default:
                return 0;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (ModelData.getInstance().map != null)
        {
            ModelData.getInstance().map.setMapType(ModelData.getInstance().mapType);
            addMarkers(ModelData.getInstance().map);
        }
    }
}
