package com.example.tyler.familymap.model;

import com.amazon.geo.mapsv2.model.Marker;

/**
 * Created by Tyler on 7/28/2016.
 */
public class Event {

    private String eventID;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    //This is birth, death, etc
    private String description;
    private String year;
    private String descendant; //this is Username

    private Marker markerRef; //a reference to the marker on the map for this event

    public Marker getMarkerRef() {
        return markerRef;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(description);
        sb.append(": ");
        sb.append(city);
        sb.append(", ");
        sb.append(country);
        sb.append(" (");
        sb.append(year);
        sb.append(")");
        return sb.toString();
    }


    public void setMarkerRef(Marker markerRef) {
        this.markerRef = markerRef;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }
}
