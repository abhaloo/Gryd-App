package com.bigO.via;

import android.graphics.Bitmap;
import org.json.JSONObject;

public class PlaceData {

    private String name;
    private JSONObject placeData;
    private Bitmap icon;

    public PlaceData(String name, JSONObject placeData, Bitmap icon){
        this.name = name;
        this.placeData = placeData;
        this.icon = icon;
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


}
