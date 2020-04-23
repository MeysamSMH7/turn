package com.example.turn.Activity.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.turn.Activity.Main.Activity_Main_Turn;
import com.example.turn.Activity.Main.Model.ModAlerts;
import com.example.turn.Activity.Wellcome.Activity_Welcome_Turn;
import com.example.turn.Classes.CheckInternet;
import com.example.turn.Classes.ShowMessage;
import com.example.turn.Classes.setConnectionVolley;
import com.example.turn.R;
import com.example.turn.SearchAlertDialog;

import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_Splash_Turn extends AppCompatActivity {

    private Context context = this;
    private CheckInternet internet;
    private TextView txtAcS_tryAgain;
    private SharedPreferences preferences;
    private String version = "0";
    private Button btnAcS_download;
    private String urlDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_turn);

        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        findViews();
        clicks();
        checkConnection();

     //   startAActivity();
    }

    private void findViews() {

        internet = new CheckInternet(context);
        preferences = getSharedPreferences("TuRn", 0);
        RelativeLayout layoutMain = findViewById(R.id.layoutMain);
        btnAcS_download = findViewById(R.id.btnAcS_download);
        btnAcS_download.setVisibility(View.GONE);

        txtAcS_tryAgain = findViewById(R.id.txtAcS_tryAgain);

        ProgressBar progressBar = findViewById(R.id.progressAcS);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor(String.valueOf("#DF69A8")), PorterDuff.Mode.SRC_IN);

        TextView txtAcS_Version = findViewById(R.id.txtAcS_Version);
        txtAcS_Version.setText("نسخه " + version);

        btnAcS_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlDownload)));
            }
        });
    }

    private void defaults() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startAActivity();
            }
        }, 1000);

    }

    private void clicks() {
        txtAcS_tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnection();
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

    private void checkConnection() {
        if (internet.CheckNetworkConnection()) {
            JSONObject jsonObject = new JSONObject();
            String vUrl = "http://nobat.mazums.ac.ir/turnappapi/home/getversion";
            new setConnectionVolley(context, "url", jsonObject).connectStringRequest(new setConnectionVolley.OnResponse() {
                @Override
                public void OnResponse(String response) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(response);
                        String versionWeb = jsonObject1.getString("version");
                        urlDownload = jsonObject1.getString("url");
                        if (versionWeb.equals(version))
                            defaults();
                        else
                            btnAcS_download.setVisibility(View.VISIBLE);

                        btnAcS_download.setText("بروزرسانی به نسخه ی " + versionWeb);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            txtAcS_tryAgain.setVisibility(View.VISIBLE);
            new ShowMessage(context).ShowMessType2_NetError();
        }

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
