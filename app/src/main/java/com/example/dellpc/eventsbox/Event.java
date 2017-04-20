package com.example.dellpc.eventsbox;

import android.net.Uri;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

/**
 * Created by DELL PC on 11-Apr-17.
 */

public class Event implements Serializable{
    private String eventId;
    private String title;
    private String societyBelongTo;
    private String venue;
    private String description;
    private String startDate;
    private String endDate;
    private String volunteerListId;
    private String imageUrl;
    private Uri imageUri;
    private boolean status;
    private boolean hasRegisterOption;

    private String societyBelongTo_status;
    private String startDate_status,endDate_status;

    public Event(){

    }


    public Event(String title, String societyBelongTo, String venue, String description, String startDate, String endDate, Uri imageUri, boolean status, boolean hasRegisterOption, String societyBelongTo_status, String startDate_status, String endDate_status) {
        //this.eventId = eventId;
        this.title = title;
        this.societyBelongTo = societyBelongTo;
        this.venue = venue;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        //this.volunteerList = volunteerList;


        this.imageUri = imageUri;
        this.status = status;
        this.hasRegisterOption = hasRegisterOption;
        this.societyBelongTo_status = societyBelongTo_status;
        this.startDate_status = startDate_status;
        this.endDate_status = endDate_status;
    }



    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSocietyBelongTo_status() {
        return societyBelongTo_status;
    }

    public void setSocietyBelongTo_status(String societyBelongTo_status) {
        this.societyBelongTo_status = societyBelongTo_status;
    }

    public String getStartDate_status() {
        return startDate_status;
    }

    public void setStartDate_status(String startDate_status) {
        this.startDate_status = startDate_status;
    }

    public String getEndDate_status() {
        return endDate_status;
    }

    public void setEndDate_status(String endDate_status) {
        this.endDate_status = endDate_status;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getHasRegisterOption() {
        return hasRegisterOption;
    }

    public void setHasRegisterOption(boolean hasRegisterOption) {
        this.hasRegisterOption = hasRegisterOption;
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

    public void setVolunteerListId(String volunteerListId) {
        this.volunteerListId = volunteerListId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVolunteerListId() {
        return volunteerListId;
    }

    @Exclude
    public Uri getImageUri() {
        return imageUri;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId='" + eventId + '\'' +
                ", title='" + title + '\'' +
                ", societyBelongTo='" + societyBelongTo + '\'' +
                ", venue='" + venue + '\'' +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", volunteerListId='" + volunteerListId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageUri=" + imageUri +
                ", status=" + status +
                ", hasRegisterOption=" + hasRegisterOption +
                ", societyBelongTo_status='" + societyBelongTo_status + '\'' +
                ", startDate_status='" + startDate_status + '\'' +
                ", endDate_status='" + endDate_status + '\'' +
                '}';
    }
}
