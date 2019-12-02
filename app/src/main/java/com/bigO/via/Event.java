package com.bigO.via;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {

    private int eventImage;
    private String eventName;
    private String blurb;
    private Date startDate;
    private Date endDate;
    private boolean isPromoted;
    private boolean isAvailable;

    public Event(int eventImage, String anEventName, String aBlurb, Date aStartDate, Date anEndDate, boolean isPromoted, boolean isAvailable) {
        this.eventImage = eventImage;
        this.eventName = anEventName;
        this.blurb = aBlurb;
        this.startDate = aStartDate;
        this.endDate = anEndDate;
        this.isPromoted = isPromoted;
        this.isAvailable = isAvailable;
    }

    public String getEventName() {
        return eventName;
    }

    public String getBlurb() {
        return blurb;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getDatesAsString() {
        DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String startDateString = outputFormatter.format(startDate);
        String endDateString = outputFormatter.format(endDate);
        return startDateString + " - " + endDateString;
    }

    public int getEventImage() {
        return eventImage;
    }

    public boolean isPromoted() {
        return isPromoted;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
