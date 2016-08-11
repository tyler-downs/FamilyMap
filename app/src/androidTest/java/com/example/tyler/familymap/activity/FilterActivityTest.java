package com.example.tyler.familymap.activity;

import android.graphics.PorterDuff;
import android.test.AndroidTestCase;

import com.example.tyler.familymap.model.Event;
import com.example.tyler.familymap.model.ModelData;
import com.example.tyler.familymap.model.Person;

/**
 * Created by Tyler on 8/7/2016.
 */
public class FilterActivityTest extends AndroidTestCase {


    public void testEventTypeFilters()
    {
        FilterActivity filterActivity = new FilterActivity();
        //make some fake people
        Person user = new Person();
        user.setFirstName("Tyler");
        user.setLastName("Downs");
        user.setGender("m");
        user.setPersonID("me");
        user.setFather("father");
        user.setMother("mother");

        ModelData.getInstance().me = user;

        Person mom = new Person();
        mom.setFirstName("Taylor");
        mom.setLastName("Swift");
        mom.setPersonID("mother");
        mom.setGender("f");
        mom.setFather("taylorsDad");
        mom.setMother("taylorsMom");

        Person dad = new Person();
        dad.setFirstName("John");
        dad.setLastName("Travolta");
        dad.setPersonID("father");
        dad.setGender("m");
        dad.setFather("johnsDad");
        dad.setMother("johnsMom");

        Person gpaF = new Person();
        gpaF.setFirstName("John");
        gpaF.setLastName("Mayer");
        gpaF.setPersonID("taylorsDad");
        gpaF.setGender("m");

        Person gmaF = new Person();
        gmaF.setFirstName("Katy");
        gmaF.setLastName("Perry");
        gmaF.setPersonID("taylorsMom");
        gmaF.setGender("f");

        Person gpaM = new Person();
        gpaM.setFirstName("Clint");
        gpaM.setLastName("Eastwood");
        gpaM.setGender("m");
        gpaM.setPersonID("johnsDad");

        Person gmaM = new Person();
        gpaM.setFirstName("Joni");
        gmaM.setLastName("Mitchell");
        gmaM.setGender("f");
        gmaM.setPersonID("johnsMom");

        //Make some fake events
        Event myBirth = new Event();
        myBirth.setDescription("birth");
        myBirth.setPersonID("me");
        myBirth.setYear("1993");
        myBirth.setEventID("myBirth");

        Event myDeath = new Event();
        myDeath.setDescription("death");
        myDeath.setPersonID("me");
        myDeath.setEventID("myDeath");
        myDeath.setYear("2070");

        Event myMarriage = new Event();
        myMarriage.setDescription("marriage");
        myMarriage.setYear("2016");
        myMarriage.setEventID("myMarriage");
        myMarriage.setPersonID("me");

        //add these to eventResponse, eventtypesSwitchesMap, and allEvents, and personResponse
        ModelData.getInstance().eventResponse.getEvents().add(myBirth);
        ModelData.getInstance().eventResponse.getEvents().add(myDeath);
        ModelData.getInstance().eventResponse.getEvents().add(myMarriage);
        ModelData.getInstance().allEvents.add(myBirth);
        ModelData.getInstance().allEvents.add(myDeath);
        ModelData.getInstance().allEvents.add(myMarriage);

        ModelData.getInstance().personResponse.addToPeople(user);
        ModelData.getInstance().personResponse.addToPeople(mom);
        ModelData.getInstance().personResponse.addToPeople(dad);
        ModelData.getInstance().personResponse.addToPeople(gpaF);
        ModelData.getInstance().personResponse.addToPeople(gmaF);
        ModelData.getInstance().personResponse.addToPeople(gpaM);
        ModelData.getInstance().personResponse.addToPeople(gmaM);

        ModelData.getInstance().eventTypesSwitchMap.put("birth", true);
        ModelData.getInstance().eventTypesSwitchMap.put("death", false);
        ModelData.getInstance().eventTypesSwitchMap.put("marriage", true);

        //run the function
        filterActivity.buildFilters();

        //Test
        assertEquals(2, ModelData.getInstance().eventResponse.getEvents().size());
    }

    public void testSideFilters()
    {
        FilterActivity filterActivity = new FilterActivity();
        //make some fake people
        Person user = new Person();
        user.setFirstName("Tyler");
        user.setLastName("Downs");
        user.setGender("m");
        user.setPersonID("me");
        user.setFather("father");
        user.setMother("mother");

        ModelData.getInstance().me = user;

        Person mom = new Person();
        mom.setFirstName("Taylor");
        mom.setLastName("Swift");
        mom.setPersonID("mother");
        mom.setGender("f");
        mom.setFather("taylorsDad");
        mom.setMother("taylorsMom");

        Person dad = new Person();
        dad.setFirstName("John");
        dad.setLastName("Travolta");
        dad.setPersonID("father");
        dad.setGender("m");
        dad.setFather("johnsDad");
        dad.setMother("johnsMom");

        Person gpaF = new Person();
        gpaF.setFirstName("John");
        gpaF.setLastName("Mayer");
        gpaF.setPersonID("taylorsDad");
        gpaF.setGender("m");

        Person gmaF = new Person();
        gmaF.setFirstName("Katy");
        gmaF.setLastName("Perry");
        gmaF.setPersonID("taylorsMom");
        gmaF.setGender("f");

        Person gpaM = new Person();
        gpaM.setFirstName("Clint");
        gpaM.setLastName("Eastwood");
        gpaM.setGender("m");
        gpaM.setPersonID("johnsDad");

        Person gmaM = new Person();
        gpaM.setFirstName("Joni");
        gmaM.setLastName("Mitchell");
        gmaM.setGender("f");
        gmaM.setPersonID("johnsMom");

        //Make some fake events
        Event myBirth = new Event();
        myBirth.setDescription("birth");
        myBirth.setPersonID("gmaF");
        myBirth.setYear("1993");
        myBirth.setEventID("myBirth");

        Event myDeath = new Event();
        myDeath.setDescription("death");
        myDeath.setPersonID("gmaF");
        myDeath.setEventID("myDeath");
        myDeath.setYear("2070");

        Event myMarriage = new Event();
        myMarriage.setDescription("marriage");
        myMarriage.setYear("2016");
        myMarriage.setEventID("myMarriage");
        myMarriage.setPersonID("me");

        //add these to eventResponse, eventtypesSwitchesMap, and allEvents, and personResponse
        ModelData.getInstance().eventResponse.getEvents().add(myBirth);
        ModelData.getInstance().eventResponse.getEvents().add(myDeath);
        ModelData.getInstance().eventResponse.getEvents().add(myMarriage);
        ModelData.getInstance().allEvents.add(myBirth);
        ModelData.getInstance().allEvents.add(myDeath);
        ModelData.getInstance().allEvents.add(myMarriage);

        ModelData.getInstance().personResponse.addToPeople(user);
        ModelData.getInstance().personResponse.addToPeople(mom);
        ModelData.getInstance().personResponse.addToPeople(dad);
        ModelData.getInstance().personResponse.addToPeople(gpaF);
        ModelData.getInstance().personResponse.addToPeople(gmaF);
        ModelData.getInstance().personResponse.addToPeople(gpaM);
        ModelData.getInstance().personResponse.addToPeople(gmaM);

        ModelData.getInstance().mothersSideSwitchOn = false;

        filterActivity.buildFilters();

        assertEquals(1, ModelData.getInstance().eventResponse.getEvents().size());

        ModelData.getInstance().mothersSideSwitchOn = true;
        ModelData.getInstance().fathersSideSwitchOn = false;

        filterActivity.buildFilters();

        assertEquals(2, ModelData.getInstance().eventResponse.getEvents().size());
    }

    public void testGenderFilters()
    {
        FilterActivity filterActivity = new FilterActivity();
        //make some fake people
        Person user = new Person();
        user.setFirstName("Tyler");
        user.setLastName("Downs");
        user.setGender("m");
        user.setPersonID("me");
        user.setFather("father");
        user.setMother("mother");

        ModelData.getInstance().me = user;

        Person mom = new Person();
        mom.setFirstName("Taylor");
        mom.setLastName("Swift");
        mom.setPersonID("mother");
        mom.setGender("f");
        mom.setFather("taylorsDad");
        mom.setMother("taylorsMom");

        Person dad = new Person();
        dad.setFirstName("John");
        dad.setLastName("Travolta");
        dad.setPersonID("father");
        dad.setGender("m");
        dad.setFather("johnsDad");
        dad.setMother("johnsMom");

        Person gpaF = new Person();
        gpaF.setFirstName("John");
        gpaF.setLastName("Mayer");
        gpaF.setPersonID("taylorsDad");
        gpaF.setGender("m");

        Person gmaF = new Person();
        gmaF.setFirstName("Katy");
        gmaF.setLastName("Perry");
        gmaF.setPersonID("taylorsMom");
        gmaF.setGender("f");

        Person gpaM = new Person();
        gpaM.setFirstName("Clint");
        gpaM.setLastName("Eastwood");
        gpaM.setGender("m");
        gpaM.setPersonID("johnsDad");

        Person gmaM = new Person();
        gpaM.setFirstName("Joni");
        gmaM.setLastName("Mitchell");
        gmaM.setGender("f");
        gmaM.setPersonID("johnsMom");

        //Make some fake events
        Event myBirth = new Event();
        myBirth.setDescription("birth");
        myBirth.setPersonID("gmaF");
        myBirth.setYear("1993");
        myBirth.setEventID("myBirth");

        Event myDeath = new Event();
        myDeath.setDescription("death");
        myDeath.setPersonID("gmaF");
        myDeath.setEventID("myDeath");
        myDeath.setYear("2070");

        Event myMarriage = new Event();
        myMarriage.setDescription("marriage");
        myMarriage.setYear("2016");
        myMarriage.setEventID("myMarriage");
        myMarriage.setPersonID("me");

        //add these to eventResponse, eventtypesSwitchesMap, and allEvents, and personResponse
        ModelData.getInstance().eventResponse.getEvents().add(myBirth);
        ModelData.getInstance().eventResponse.getEvents().add(myDeath);
        ModelData.getInstance().eventResponse.getEvents().add(myMarriage);
        ModelData.getInstance().allEvents.add(myBirth);
        ModelData.getInstance().allEvents.add(myDeath);
        ModelData.getInstance().allEvents.add(myMarriage);

        ModelData.getInstance().personResponse.addToPeople(user);
        ModelData.getInstance().personResponse.addToPeople(mom);
        ModelData.getInstance().personResponse.addToPeople(dad);
        ModelData.getInstance().personResponse.addToPeople(gpaF);
        ModelData.getInstance().personResponse.addToPeople(gmaF);
        ModelData.getInstance().personResponse.addToPeople(gpaM);
        ModelData.getInstance().personResponse.addToPeople(gmaM);

        ModelData.getInstance().maleEventsSwitchOn = false;

        filterActivity.buildFilters();

        assertEquals(2, ModelData.getInstance().eventResponse.getEvents().size());

        ModelData.getInstance().maleEventsSwitchOn = true;
        ModelData.getInstance().femaleEventsSwitchOn = false;

        assertEquals(1, ModelData.getInstance().eventResponse.getEvents().size());
    }

}
