package com.example.tyler.familymap.model;

import java.util.HashMap;

/**
 * Created by Tyler on 7/30/2016.
 */
public class PersonIDMap {

    private HashMap<String, Person> personIDmap = new HashMap<>();

    public HashMap<String, Person> getPersonIDmap() {
        return personIDmap;
    }

    public void setPersonIDmap(HashMap<String, Person> personIDmap) {
        this.personIDmap = personIDmap;
    }
}
