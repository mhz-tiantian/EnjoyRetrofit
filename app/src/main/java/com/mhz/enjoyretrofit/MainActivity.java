package com.mhz.enjoyretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mhz.enjoyretrofit.api.EnjoyWeatherApi;
import com.mhz.enjoyretrofit.onClick.BindClass;
import com.mhz.enjoyretrofit.onClick.OnClick;
import com.mhz.enjoyretrofit.onClick.SecondActivity;
import com.mhz.enjoyretrofit.retrofit.EnjoyRetrofit;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EnjoyWeatherApi enjoyWeatherApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BindClass.init(this);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://restapi.amap.com").build();

        EnjoyRetrofit enjoyRetrofit = new EnjoyRetrofit.Builder().baseUrl("https://restapi.amap.com").build();
         enjoyWeatherApi = enjoyRetrofit.create(EnjoyWeatherApi.class);
//        enjoyWeatherApi.getWeather()

        findViewById(R.id.text_get).setOnClickListener(v -> enjoyGet());
        findViewById(R.id.text_post).setOnClickListener(v -> enjoyPost());



    }

    @OnClick({R.id.text_goto_second})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.text_goto_second:
                SecondActivity.launchActivity(this);
                break;
        }

    }

    private void enjoyGet(){
        Call call = enjoyWeatherApi.getWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "onResponse enjoy get: " + response.body().string());
                response.close();

            }
        });

    }

    private void  enjoyPost(){
        Call call = enjoyWeatherApi.postWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b");

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "onResponse enjoy post: " + response.body().string());
                response.close();

            }
        });
    }
}
