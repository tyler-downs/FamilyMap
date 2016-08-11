package com.example.tyler.familymap.fragment;

import android.test.AndroidTestCase;
import android.text.GetChars;

import com.example.tyler.familymap.communication.FamilyMapProxy;
import com.example.tyler.familymap.communication.IProxyDelegate;
import com.example.tyler.familymap.communication.ProxyResult;
import com.example.tyler.familymap.model.ModelData;
import com.example.tyler.familymap.model.Person;

import java.util.ArrayList;

/**
 * Created by Tyler on 8/5/2016.
 */
public class LoginFragmentTest extends AndroidTestCase{

    public void testLoginAndSync()
    {
        /*
        write a JUnit test that logs into the service, downloads the user’s person and event data from
        the service, and verifies that the client’s model was correctly initialized with the returned data.
         */
        //Contrive credentials and login
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.familyMapProxy = new FamilyMapProxy("192.168.1.7:8080", "80", getContext());
        loginFragment.familyMapProxy.login("a", "a", loginFragment);

        //See if the
        int numberOfPeople = ModelData.getInstance().personResponse.getPeople().size();
        assertTrue(numberOfPeople > 0);

        //Makes sure the number of people in memory is the same across all containers
        assertEquals(numberOfPeople, ModelData.getInstance().personIDMap.getPersonIDmap().size());
        assertEquals(numberOfPeople, ModelData.getInstance().personIDtoEvents.getPersonIDtoEventsMap().size());

    }

    public void testOnFatherOrMothersSideSetter()
    {
        //make a few people of fake data and add them to the singleton
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

        //Add these people to the singleton
        ModelData.getInstance().personIDMap.getPersonIDmap().put(user.getPersonID(), user);
        ModelData.getInstance().personIDMap.getPersonIDmap().put(mom.getPersonID(), mom);
        ModelData.getInstance().personIDMap.getPersonIDmap().put(dad.getPersonID(), dad);
        ModelData.getInstance().personIDMap.getPersonIDmap().put(gmaF.getPersonID(), gmaF);
        ModelData.getInstance().personIDMap.getPersonIDmap().put(gpaF.getPersonID(), gpaF);
        ModelData.getInstance().personIDMap.getPersonIDmap().put(gmaM.getPersonID(), gmaM);
        ModelData.getInstance().personIDMap.getPersonIDmap().put(gpaM.getPersonID(), gpaM);


        //run the functions
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setFatherOrMotherSide();

        //compare and see whether it assigned them right

        assertTrue(dad.isOnFathersSide());
        assertTrue(!mom.isOnFathersSide());
        assertTrue(!gmaF.isOnFathersSide());
        assertTrue(gmaM.isOnFathersSide());
        assertTrue(!gpaF.isOnFathersSide());
        assertTrue(gpaM.isOnFathersSide());
    }

    public void testGivePeopleChildren()
    {
        Person dad = new Person();
        dad.setFirstName("John");
        dad.setLastName("Travolta");
        dad.setPersonID("father");
        dad.setGender("m");
        dad.setFather("johnsDad");
        dad.setMother("johnsMom");

        Person user = new Person();
        user.setFirstName("Tyler");
        user.setLastName("Downs");
        user.setGender("m");
        user.setPersonID("me");
        user.setFather("father");
        user.setMother("mother");

        Person sammy = new Person();
        sammy.setFirstName("Samantha");
        sammy.setLastName("Bagley");
        sammy.setGender("f");
        sammy.setPersonID("sammy");
        sammy.setFather("father");
        sammy.setMother("mother");

        Person mom = new Person();
        mom.setFirstName("Taylor");
        mom.setLastName("Swift");
        mom.setPersonID("mother");
        mom.setGender("f");
        mom.setFather("taylorsDad");
        mom.setMother("taylorsMom");

        //Add these people to personResponse
        ModelData.getInstance().personResponse.addToPeople(user);
        ModelData.getInstance().personResponse.addToPeople(sammy);
        ModelData.getInstance().personResponse.addToPeople(mom);
        ModelData.getInstance().personResponse.addToPeople(dad);

        LoginFragment loginFragment = new LoginFragment();
        loginFragment.givePeopleChildren();
        ArrayList<String> children = new ArrayList<>();
        children.add("me");
        children.add("sammy");


        //Tests
        assertEquals(children, mom.getChildren());
        assertEquals(children, dad.getChildren());
    }


}
