package com.bigO.via;

public class ScheduleElement {

    private String name;
    private String time;
    private String data;

    public ScheduleElement(String name, String time, String data) {
        this.name = name;
        this.time = time;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
