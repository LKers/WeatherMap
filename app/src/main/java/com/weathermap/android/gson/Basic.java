package com.weathermap.android.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lenovo on 2018/5/13.
 */

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{

        @SerializedName("loc")
        public String updateTime;

    }

}
