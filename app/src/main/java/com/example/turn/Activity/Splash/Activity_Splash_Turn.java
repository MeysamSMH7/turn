package com.example.turn.Activity.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.turn.Activity.Main.Activity_Main_Turn;
import com.example.turn.Activity.Wellcome.Activity_Welcome_Turn;
import com.example.turn.Classes.CheckInternet;
import com.example.turn.Classes.ShowMessage;
import com.example.turn.R;

public class Activity_Splash_Turn extends AppCompatActivity {

    private Context context = this;
    private CheckInternet internet;
    private TextView txtAcS_tryAgain;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_turn);

        findViews();
        defaults();
        clicks();

    }

    private void findViews() {

        internet = new CheckInternet(context);
        preferences = getSharedPreferences("TuRn", 0);
        RelativeLayout layoutMain = findViewById(R.id.layoutMain);
      //  ImageView imgAcS_Logo = findViewById(R.id.imgAcS_Logo);
      //  TextView txtAcs_DDL = findViewById(R.id.txtAcs_DDL);
       // AnimationStartActivity(layoutMain, imgAcS_Logo, txtAcs_DDL);

        txtAcS_tryAgain = findViewById(R.id.txtAcS_tryAgain);

        ProgressBar progressBar = findViewById(R.id.progressAcS);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor(String.valueOf("#DF69A8")), PorterDuff.Mode.SRC_IN);

        TextView txtAcS_Version = findViewById(R.id.txtAcS_Version);
        txtAcS_Version.setText("Reservation V.1.0.0");

    }

    private void defaults() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startAActivity();
            }
        }, 4000);

    }

    private void clicks() {
        txtAcS_tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAActivity();
            }
        });

    }

    private void AnimationStartActivity(RelativeLayout layout, ImageView img, TextView txt) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        layout.clearAnimation();
        layout.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        anim.reset();
        img.clearAnimation();
        img.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        anim.reset();
        txt.clearAnimation();
        txt.startAnimation(anim);

    }

    private void startAActivity() {

        if (internet.CheckNetworkConnection()) {
            Intent intent = null;
            if (preferences.getBoolean("FirstTime?", true))
                intent = new Intent(context, Activity_Welcome_Turn.class);
            else
                intent = new Intent(context, Activity_Main_Turn.class);

            if (intent != null) {
                startActivity(intent);
                finish();
            }
        } else {
            txtAcS_tryAgain.setVisibility(View.VISIBLE);
            new ShowMessage(context).ShowMessType2_NetError();
        }
    }


}
