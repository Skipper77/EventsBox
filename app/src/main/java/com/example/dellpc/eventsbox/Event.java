package com.example.dellpc.eventsbox;

import java.util.ArrayList;

/**
 * Created by DELL PC on 11-Apr-17.
 */

public class Event {
    private String title;
    private String societyBelongTo;
    private String venue;
    private String description;
    private String date;
    private ArrayList<Volunteer> volunteerList;
    private String imageUrl;
    public Event(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSocietyBelongTo() {
        return societyBelongTo;
    }

    public void setSocietyBelongTo(String societyBelongTo) {
        this.societyBelongTo = societyBelongTo;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Volunteer> getVolunteerList() {
        return volunteerList;
    }

    public void setVolunteerList(ArrayList<Volunteer> volunteerList) {
        this.volunteerList = volunteerList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
