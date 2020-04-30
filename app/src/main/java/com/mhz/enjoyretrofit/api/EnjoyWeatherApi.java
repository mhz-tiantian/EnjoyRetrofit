package com.mhz.enjoyretrofit.api;


import com.mhz.enjoyretrofit.retrofit.annotation.Field;
import com.mhz.enjoyretrofit.retrofit.annotation.GET;
import com.mhz.enjoyretrofit.retrofit.annotation.POST;
import com.mhz.enjoyretrofit.retrofit.annotation.Query;

import okhttp3.Call;

public interface EnjoyWeatherApi {

    @POST("/v3/weather/weatherInfo")
    Call postWeather(@Field("city") String city, @Field("key") String key);


    @GET("/v3/weather/weatherInfo")
    Call getWeather(@Query("city") String city, @Query("key") String key);
}
