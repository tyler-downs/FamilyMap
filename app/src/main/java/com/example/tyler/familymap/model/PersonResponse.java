package com.example.tyler.familymap.model;

import java.util.ArrayList;

/**
 * Created by Tyler on 7/28/2016.
 */
public class PersonResponse {

    //Used to hold the person objects as soon as they are loaded from the server

    public  ArrayList<Person> people = new ArrayList<>();

    public  ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    public void addToPeople(Person p)
    {
        people.add(p);
    }
}
