package com.mhz.enjoyretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mhz.enjoyretrofit.api.EnjoyWeatherApi;
import com.mhz.enjoyretrofit.retrofit.EnjoyRetrofit;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://restapi.amap.com").build();

        EnjoyRetrofit enjoyRetrofit = new EnjoyRetrofit.Builder().baseUrl("https://restapi.amap.com").build();
        EnjoyWeatherApi enjoyWeatherApi = enjoyRetrofit.create(EnjoyWeatherApi.class);
//        enjoyWeatherApi.getWeather()
    }
}
