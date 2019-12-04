package com.bigO.via;

public class EventDuration {

    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    private boolean isTimed;

    public EventDuration(){
        this.startHour = 0;
        this.startMinute = 0;
        this.endHour = 0;
        this.endMinute = 0;

        this.isTimed = false;
    }

    public EventDuration(int startHour, int startMinute, int endHour, int endMinute) {
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;

        this.isTimed = true;
    }

    public int getStartTime(){
        return startHour*100 + startMinute;
    }

    public int getEndTime(){
        return endHour*100 + endMinute;
    }

    public boolean isTimed() {
        return isTimed;
    }

    public String getDurationAsString(){

        if (isTimed){
            String startHourString = String.format ("%02d", startHour);
            String startMinuteString = String.format ("%02d", startMinute);
            String endHourString = String.format ("%02d", endHour);
            String endMinuteString = String.format ("%02d", endMinute);

            return startHourString + ":" + startMinuteString + " - " + endHourString + ":" + endMinuteString;
        }
        else {
            return "-";
        }
    }

}
