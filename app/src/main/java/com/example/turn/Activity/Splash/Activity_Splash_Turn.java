package com.example.turn.Activity.Splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Base64;
import android.util.Log;
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
import android.widget.Toast;

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

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

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
       // checkConnection();
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            , Manifest.permission.WRITE_SETTINGS
                            , Manifest.permission.WRITE_SECURE_SETTINGS
                    }
                    , RequestPermissionCode);
        } else
            getLocation();
     //   startAActivity();
    }


    private static final int RequestPermissionCode = 1;
    Intent intent1;
    Location location;
    LocationManager locationManager;
    boolean GpsStatus = false;
    Criteria criteria;
    String Holder;

    private void setLoctionSetting() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        Holder = locationManager.getBestProvider(criteria, false);
        CheckGpsStatus();
    }

    private void CheckGpsStatus() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    private void getLocation() {

        // میخواییم اینجا الرت دیالوگ رو باز کنیم
// یک شئی از کتابخونه ی پرتی دیالگو میسازیم
        final PrettyDialog prettyDialog = new PrettyDialog(context);
        prettyDialog.setCancelable(false);
        // ایکون تعریف میکنیم براش
        prettyDialog.setIcon(
                R.drawable.icon_warning,
                null,
                new PrettyDialogCallback() {
                    @Override
                    public void onClick() {

                    }
                });
// این دکمه هم برای باز کردن تنظیماته
        prettyDialog.addButton(
                "روشن کردن",
                R.color.pdlg_color_white,
                R.color.colorPrimaryDark,
                new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
//                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 223);
                        prettyDialog.dismiss();
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 222);
                    }
                }
        );
// یک دکمه ی بستن میذاریم
        prettyDialog.addButton(
                "بستن",
                R.color.pdlg_color_white,
                R.color.colorPrimaryDark,
                new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        prettyDialog.dismiss();
                        checkConnection();
                    }
                }
        );

// متن بادی
        prettyDialog.setMessage("لوکشین شما خاموش است لطفا روشنش کنید");


        //  اینجا باید یه ایف بذاریم اگر لوکشینش  خاموش بود الرت دیالوگ رو رون کنه
        // اگر روشن بود لوکیشنش رو بگیره
// نمیدونم ایا این وضعیت رو میگیر یا نه
        // برنامه رو ران بگیر با لوکشین خاموش ببینم اینجا می افته یا نه
        // چشم ولی الان سرور قطعه خخخخ
        CheckGpsStatus();
        if (GpsStatus)
            getLocation2();
        else
            prettyDialog.show();

    }

    private void getLocation2() {
        setLoctionSetting();
        CheckGpsStatus();

        if (GpsStatus == true) {
            if (Holder != null) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        &&
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                location = locationManager.getLastKnownLocation(Holder);
                locationManager.requestLocationUpdates(Holder, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.d("TAGTAGATA", "Longitude:" + location.getLongitude() + " " + "Latitude:" + location.getLatitude());
                        //   Toast.makeText(context, "ok", Toast.LENGTH_SHORT).show();
                      //  lng_value_single = location.getLongitude() + "";
                      //  lat_value_single = location.getLatitude() + "";
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });

                checkConnection();
            }
        } else {

            Toast.makeText(context, "لطفا ابتدا دسترسی به مکان را روشن کنید ", Toast.LENGTH_LONG).show();
            //
            if (Build.VERSION.SDK_INT >= 23) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                                , Manifest.permission.WRITE_SETTINGS
                                , Manifest.permission.WRITE_SECURE_SETTINGS
                        }
                        , RequestPermissionCode);
            } else
                getLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        if (RC == RequestPermissionCode) {
            if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(context, "Permission Granted, Now your application can access GPS.", Toast.LENGTH_LONG).show();
                getLocation();
            } else {

                //   Toast.makeText(context, "Permission Canceled, Now your application cannot access GPS.", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

     if (requestCode == 222) {
            CheckGpsStatus();
            if (GpsStatus)
                getLocation2();
        }
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
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+urlDownload)));
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
            new setConnectionVolley(context, vUrl, jsonObject).connectStringRequest(new setConnectionVolley.OnResponse() {
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
