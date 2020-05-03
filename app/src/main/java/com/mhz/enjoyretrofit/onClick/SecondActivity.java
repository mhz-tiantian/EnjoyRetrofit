package com.mhz.enjoyretrofit.onClick;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mhz.enjoyretrofit.R;

public class SecondActivity extends AppCompatActivity {


    public static void launchActivity(Activity activity) {
        Intent intent = new Intent(activity, SecondActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        BindClass.init(this);
    }

    @OnClick({R.id.text_click1, R.id.text_click2})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.text_click1:
                Toast.makeText(this, "点击1", Toast.LENGTH_LONG).show();
                break;
            case R.id.text_click2:
                Toast.makeText(this, "点击2", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @OnLongClick({R.id.text_long_click1, R.id.text_long_click2})
    public boolean longClickView(View view) {
        switch (view.getId()) {
            case R.id.text_long_click1:
                Toast.makeText(this, "长按1", Toast.LENGTH_LONG).show();

                break;
            case R.id.text_long_click2:
                Toast.makeText(this, "长按2", Toast.LENGTH_LONG).show();
                break;

        }
        return false;
    }
}
