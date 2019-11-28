package com.bigO.via;

import android.graphics.Bitmap;
import org.json.JSONObject;

public class PlaceData {

    private String name;
    private JSONObject placeData;
    private Bitmap icon;
    private EventDuration eventDuration;
    private boolean isEvent = true;


    public PlaceData(String name, JSONObject placeData,boolean isEvent ,Bitmap icon){
        this.name = name;
        this.placeData = placeData;
        this.icon = icon;
        this.isEvent = isEvent;
    }

    public String unwrapJson(JSONObject data){
        // TODO have a method to unwrap the JSON Object into String
        return "";
    }

    public String getName() {
        return name;
    }

    public JSONObject getPlaceData() {
        return placeData;
    }

    // rescale it and return
    public Bitmap getIcon() {
//        profileImage.setImageBitmap(Bitmap.createScaledBitmap(b, 120, 120, false));
        Bitmap newIcon = Bitmap.createScaledBitmap(this.icon, 120, 120, false);

        return newIcon;
    }


    public static boolean isEvent(String eventName) {
        boolean is_Event = true;
        String name = eventName.toLowerCase();
        String [] notEventArray = {"exit","toilet","desk","mcdonalds","wendy's","kfc","test"};

        for(String blacklist: notEventArray){
            if(name.contains(blacklist)){
                is_Event = false;
            }
        }

        return is_Event;
    }

}
