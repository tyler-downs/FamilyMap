package com.example.tyler.familymap.model;

import java.util.ArrayList;

/**
 * Created by Tyler on 7/28/2016.
 */
public class Person {

    //descendant is the Username
    private String descendant;
    private String personID;
    private String firstName;
    private String lastName;
    //This is m or f
    private String gender;
    //These are personIDs (father and mother are optional
    private String father;
    private String mother;
    private String spouse;
    private ArrayList<String> children = new ArrayList<>();
    private boolean isOnFathersSide;

    public ArrayList<String> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<String> children) {
        this.children = children;
    }

    public String getDescendant() {
        return descendant;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName);
        sb.append(" ");
        sb.append(lastName);
        return sb.toString();
    }


    public boolean isOnFathersSide() {
        return isOnFathersSide;
    }

    public void setOnFathersSide(boolean onFathersSide) {
        isOnFathersSide = onFathersSide;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }
}
