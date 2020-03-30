package com.example.turn.Classes;

import android.app.Application;

public class BaseActivity_Turn extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(),
                "SERIF", "fonts/vazir.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
//                "SERIF", "fonts/vazir.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }
}
