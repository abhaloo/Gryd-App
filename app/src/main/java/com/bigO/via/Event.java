package com.bigO.via;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {

    private String eventName;
    private String blurb;
    private Date startDate;
    private Date endDate;

    public Event(String anEventName, String aBlurb, Date aStartDate, Date anEndDate) {
        this.eventName = anEventName;
        this.blurb = aBlurb;
        this.startDate = aStartDate;
        this.endDate = anEndDate;
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
}
