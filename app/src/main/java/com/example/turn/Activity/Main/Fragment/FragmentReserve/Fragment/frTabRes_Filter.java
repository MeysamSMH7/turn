package com.example.turn.Activity.Main.Fragment.FragmentReserve.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turn.Activity.Main.Adapter.AdRecycPopUp;
import com.example.turn.Activity.Main.Adapter.onClickInterface;
import com.example.turn.Activity.Main.Adapter.setDataToFragment;
import com.example.turn.Activity.Main.Model.ModAlerts;
import com.example.turn.Classes.ShowMessage;
import com.example.turn.Classes.setConnectionVolley;
import com.example.turn.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

import static android.app.Activity.RESULT_OK;

public class frTabRes_Filter extends Fragment implements SearchView.OnQueryTextListener {
    //  linearSelectFilters
    private AlertDialog alertDialogFilter;

    private TextView txtFrRes_city;
    private TextView txtFrRes_takhasos;
    private TextView txtFrRes_darmonghah;
    private TextView txtFrRes_time;
    private TextView txtFrRes_doctor;

    private LinearLayout linearFrRes_city;
    private LinearLayout linearFrRes_takhasos;
    private LinearLayout linearFrRes_darmonghah;
    private LinearLayout linearFrRes_time;
    private LinearLayout linearFrRes_doctor;

    private Button btnFrRes_filter;
    private Button btnFrRes_next;

    private ArrayList dataCity;
    private ArrayList dataTakhasos;
    private ArrayList dataTakhasos2;
    private ArrayList dataHospital;
    private ArrayList dataHospital2;
    private ArrayList dataTime;
    private ArrayList dataDoctor;
    private ArrayList dataDoctor2;

    private ArrayList dataCityID;
    private ArrayList dataTakhasosID;
    private ArrayList dataTakhasosID2;
    private ArrayList dataHospitalID;
    private ArrayList dataHospitalID2;
    private ArrayList dataTimeID;
    private ArrayList dataDoctorID;
    private ArrayList dataDoctorID2;

    private String cityId = "";
    private String takhasosId = "";
    private String hospiralId = "";
    private String timeId = "";
    private String doctorId = "";

    private String citySt = "";
    private String takhasosSt = "";
    private String hospiralSt = "";
    private String timeSt = "";
    private String doctorSt = "";

    private AlertDialog alertDialogLoding;

    private GoogleApiClient mGoogleApiClient = null;
    private Location mLastLocation;


    public static frTabRes_Filter newInstance() {

        Bundle args = new Bundle();
        frTabRes_Filter fragment = new frTabRes_Filter();
        return fragment;
    }


    public void getDataFromFragment(String message) {
        Toast.makeText(getContext(), message + "frTabRes_Filter", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fr_reserve_filter, container, false);

        loading();
        selectFilters(view);
        onAttachToParentFragment(getParentFragment());

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            , Manifest.permission.WRITE_SETTINGS
                            , Manifest.permission.WRITE_SECURE_SETTINGS
                    }
                    , RequestPermissionCode);
        } else
            getLocation();

        txtFrRes_city.setInputType(0);
        return view;
    }

    private static final int RequestPermissionCode = 1;
    private Intent intent1;
    private Location location;
    private LocationManager locationManager;
    private boolean GpsStatus = false;
    private Criteria criteria;
    private String Holder;

    private void setLoctionSetting() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        Holder = locationManager.getBestProvider(criteria, false);
        CheckGpsStatus();
    }

    private void CheckGpsStatus() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    private void getLocation() {

        // میخواییم اینجا الرت دیالوگ رو باز کنیم
// یک شئی از کتابخونه ی پرتی دیالگو میسازیم
        final PrettyDialog prettyDialog = new PrettyDialog(getContext());
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
//                        getActivity().startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 223);
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
                        getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        &&
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                location = locationManager.getLastKnownLocation(Holder);
                locationManager.requestLocationUpdates(Holder, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.d("TAGTAGATA", "Longitude:" + location.getLongitude() + " " + "Latitude:" + location.getLatitude());
                        //   Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                        lng_value_single = location.getLongitude() + "";
                        lat_value_single = location.getLatitude() + "";
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
            }
        } else {

            Toast.makeText(getContext(), "لطفا ابتدا دسترسی به مکان را روشن کنید ", Toast.LENGTH_LONG).show();
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
                // Toast.makeText(getContext(), "Permission Granted, Now your application can access GPS.", Toast.LENGTH_LONG).show();
                getLocation();
            } else {

                //   Toast.makeText(getContext(), "Permission Canceled, Now your application cannot access GPS.", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void selectFilters(View view) {
        try {

//      this layout is for linearResTimes
            LinearLayout layoutBtnsRT = view.findViewById(R.id.layoutBtnsRT);
            layoutBtnsRT.setVisibility(View.GONE);

            txtFrRes_city = view.findViewById(R.id.txtFrRes_city);
            txtFrRes_takhasos = view.findViewById(R.id.txtFrRes_takhasos);
            txtFrRes_darmonghah = view.findViewById(R.id.txtFrRes_darmonghah);
            txtFrRes_time = view.findViewById(R.id.txtFrRes_time);
            txtFrRes_doctor = view.findViewById(R.id.txtFrRes_doctor);

            linearFrRes_city = view.findViewById(R.id.linearFrRes_city);
            linearFrRes_takhasos = view.findViewById(R.id.linearFrRes_takhasos);
            linearFrRes_darmonghah = view.findViewById(R.id.linearFrRes_darmonghah);
            linearFrRes_time = view.findViewById(R.id.linearFrRes_time);
            linearFrRes_doctor = view.findViewById(R.id.linearFrRes_doctor);

            btnFrRes_filter = view.findViewById(R.id.btnFrRes_filter);
            btnFrRes_next = view.findViewById(R.id.btnFrRes_next);

//  Clicks
            linearFrRes_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogSearch("city");
                }
            });
            linearFrRes_takhasos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogSearch("takhasos");
                }
            });
            linearFrRes_darmonghah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogSearch("darmonghah");
                }
            });
            linearFrRes_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogSearch("time");
                }
            });

            linearFrRes_doctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogSearch("doctor");
                }
            });

            btnFrRes_filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: clear all
                    defaultDataDropDown();
                }
            });

            btnFrRes_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 /*   Snackbar.make(view, "This is main activity", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();*/


                    try {
//                Toast.makeText(getContext(), "اجرا شدن لودینگ 2 ثانیه", Toast.LENGTH_SHORT).show();
                        alertDialogLoding.show();
                        //nextPage(linearSelectFilters, linearSelectFiltersBtn, linearResTimes, linearResTimesBtn);
                        //   arrayListResTimes.clear();
                        //  adapterResTimes.notifyDataSetChanged();
                        if (Build.VERSION.SDK_INT >= 23) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                                            , Manifest.permission.WRITE_SETTINGS
                                            , Manifest.permission.WRITE_SECURE_SETTINGS
                                    }
                                    , RequestPermissionCode);
                        } else
                            getLocation();

                    /*    pageNumber = 0;
                        morePost = true;*/
                        doSearch(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            dataCity = new ArrayList();
            dataTakhasos = new ArrayList();
            dataTakhasos2 = new ArrayList();
            dataHospital = new ArrayList();
            dataHospital2 = new ArrayList();
            dataTime = new ArrayList();
            dataDoctor = new ArrayList();
            dataDoctor2 = new ArrayList();
            dataCityID = new ArrayList();
            dataTakhasosID = new ArrayList();
            dataTakhasosID2 = new ArrayList();
            dataHospitalID = new ArrayList();
            dataHospitalID2 = new ArrayList();
            dataTimeID = new ArrayList();
            dataDoctorID = new ArrayList();
            dataDoctorID2 = new ArrayList();
//TODO------ Connection data for dropDowns link1

            JSONObject object = new JSONObject();
            try {
                //     object.put("hospital", "-1");
                //  object.put("city", "-1");
                // object.put("specialty", "-1");
                object.put("hsp_id", "0");
                object.put("spc_id", "0");
                object.put("dr_id", "0");
                object.put("date_period", "0");
                object.put("city_id", "0");
            } catch (Exception e) {
                e.printStackTrace();
            }

            alertDialogLoding.show();

            new setConnectionVolley(getContext(), "http://nobat.mazums.ac.ir/turnappApi/Search?spc_id=0&dr_id=0&city_id=0&hsp_id=0&date_period=0", object
            ).connectStringRequest(new setConnectionVolley.OnResponse() {
                @Override
                public void OnResponse(String response) {
                    setDropDownsData(response, "new");
                }
            });

            //  String response = "{\"status\":\"yes\",\"message\":\"\",\"data\":{ \"hospital\":[{\"id\":\"0\",\"title\":\"تمامی بیمارستان ها\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"1\",\"title\":\"بیمارستان عمومی ماساچوست\"}],\"doctor\":[{\"id\":\"0\",\"title\":\"تمامی دکترها\"},{\"id\":\"4\",\"title\":\"علی علیزاده\"},{\"id\":\"1\",\"title\":\"حسین زارعی\"}],\"specialty\":[{\"id\":\"0\",\"title\":\"تمامی تخصص ها\"},{\"id\":\"6\",\"title\":\"اطفال\"},{\"id\":\"1\",\"title\":\"عفونی\"}],\"time\":[{\"id\":\"0\",\"title\":\"تمامی زمان ها\"},{\"id\":\"4\",\"title\":\"فردا\"},{\"id\":\"5\",\"title\":\"هفته ی جاری\"},{\"id\":\"4\",\"title\":\"ماه جاری\"}],\"city\":[{\"id\":\"0\",\"title\":\"تمامی شهرها\"},{\"id\":\"1\",\"title\":\"اراک\"},{\"id\":\"2\",\"title\":\"امل\"},{\"id\":\"41\",\"title\":\"تهران\"}]}}";
            //setDropDownsData(response, "new");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void defaultDataDropDown() {
        try {

            dataHospital = new ArrayList(dataHospital2);
            dataHospitalID = new ArrayList(dataHospitalID2);
            dataTakhasos = new ArrayList(dataTakhasos2);
            dataTakhasosID = new ArrayList(dataTakhasosID2);
            dataDoctor = new ArrayList(dataDoctor2);
            dataDoctorID = new ArrayList(dataDoctorID2);
            txtFrRes_city.setText(dataCity.get(0) + "");
            txtFrRes_takhasos.setText(dataTakhasos.get(0) + "");
            txtFrRes_darmonghah.setText(dataHospital.get(0) + "");
            txtFrRes_time.setText(dataTime.get(0) + "");
            txtFrRes_doctor.setText(dataDoctor.get(0) + "");
            cityId = dataCityID.get(0) + "";
            takhasosId = dataTakhasosID.get(0) + "";
            hospiralId = dataHospitalID.get(0) + "";
            timeId = dataTimeID.get(0) + "";
            doctorId = dataDoctorID.get(0) + "";

            doctorSt = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loading() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(false);
            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.loading, null, false);

            builder.setView(layout);
            alertDialogLoding = builder.create();
            alertDialogLoding.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDropDownsData(String res, String tag) {
        try {
            if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();

            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");

            if (status.equals("yes")) {
                String data = object.getString("data");
                JSONObject objData = new JSONObject(data);
                JSONArray arrayHospital = objData.getJSONArray("hospital");
                JSONArray arrayDoctor = objData.getJSONArray("doctor");
                JSONArray arraySpecialty = objData.getJSONArray("specialty");
                JSONArray arrayTime = objData.getJSONArray("time");
                JSONArray arrayCity = objData.getJSONArray("city");

                if (tag.equals("new")) {

                    for (int i = 0; i < arrayHospital.length(); i++) {
                        JSONObject object1 = arrayHospital.getJSONObject(i);
                        dataHospitalID.add(object1.getString("id"));
                        dataHospital.add(object1.getString("title"));
                        dataHospitalID2.add(object1.getString("id"));
                        dataHospital2.add(object1.getString("title"));
                    }
                    for (int i = 0; i < arrayDoctor.length(); i++) {
                        JSONObject object1 = arrayDoctor.getJSONObject(i);
                        dataDoctorID.add(object1.getString("id"));
                        dataDoctor.add(object1.getString("title"));
                        dataDoctorID2.add(object1.getString("id"));
                        dataDoctor2.add(object1.getString("title"));
                    }
                    for (int i = 0; i < arraySpecialty.length(); i++) {
                        JSONObject object1 = arraySpecialty.getJSONObject(i);
                        dataTakhasosID.add(object1.getString("id"));
                        dataTakhasos.add(object1.getString("title"));
                        dataTakhasosID2.add(object1.getString("id"));
                        dataTakhasos2.add(object1.getString("title"));
                    }
                    for (int i = 0; i < arrayTime.length(); i++) {
                        JSONObject object1 = arrayTime.getJSONObject(i);
                        dataTimeID.add(object1.getString("id"));
                        dataTime.add(object1.getString("title"));
                    }
                    for (int i = 0; i < arrayCity.length(); i++) {
                        JSONObject object1 = arrayCity.getJSONObject(i);
                        dataCityID.add(object1.getString("id"));
                        dataCity.add(object1.getString("title"));
                    }
                    defaultDataDropDown();

                } else if (tag.equals("updateHospital")) {

                    if (dataHospital.size() != 0)
                        dataHospital.clear();
                    if (dataHospitalID.size() != 0)
                        dataHospitalID.clear();
                    for (int i = 0; i < arrayHospital.length(); i++) {
                        JSONObject object1 = arrayHospital.getJSONObject(i);
                        dataHospitalID.add(object1.getString("id"));
                        dataHospital.add(object1.getString("title"));

                        txtFrRes_darmonghah.setText(dataHospital.get(0) + "");
                        hospiralId = dataHospitalID.get(0) + "";

                    }

                } else if (tag.equals("updateTakasos")) {

                    if (dataTakhasos.size() != 0) {
                        dataTakhasos.clear();
                        dataTakhasosID.clear();
                    }
                    for (int i = 0; i < arrayHospital.length(); i++) {
                        JSONObject object1 = arrayHospital.getJSONObject(i);
                        dataTakhasosID.add(object1.getString("id"));
                        dataTakhasos.add(object1.getString("title"));

                        txtFrRes_takhasos.setText(dataTakhasos.get(0) + "");
                        takhasosId = dataTakhasosID.get(0) + "";

                    }

                } else if (tag.equals("updateDoctor")) {

                    if (dataDoctor.size() != 0) {
                        dataDoctor.clear();
                        dataDoctorID.clear();
                    }
                    for (int i = 0; i < arrayDoctor.length(); i++) {
                        JSONObject object1 = arrayDoctor.getJSONObject(i);
                        dataDoctorID.add(object1.getString("id"));
                        dataDoctor.add(object1.getString("title"));

                        txtFrRes_doctor.setText(dataDoctor.get(0) + "");
                        doctorId = dataDoctorID.get(0) + "";

                    }

                }
            } else
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String lat_value_single = "0";
    private String lng_value_single = "0";

    private void doSearch(int pageNumber) {
        if (alertDialogLoding.isShowing())
            alertDialogLoding.dismiss();

        CheckGpsStatus();
        if (GpsStatus)
            getLocation2();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cityId", cityId);
            jsonObject.put("takhasosId", takhasosId);
            jsonObject.put("hospiralId", hospiralId);
            jsonObject.put("timeId", timeId);
            jsonObject.put("doctorId", doctorId);
            jsonObject.put("doctorSt", doctorSt);
            jsonObject.put("lat_value_single", lat_value_single);
            jsonObject.put("lng_value_single", lng_value_single);

        } catch (Exception e) {
            e.printStackTrace();
        }
        msetDataToFragment.setDataToFragment(jsonObject.toString(), "firstToSecond");
    }

    //    SearchView
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String resSpeak = result.get(0);
               /* resSpeak  = resSpeak.replace( "ی","ي");
                resSpeak  = resSpeak.replace( "ک","ك");*/
                editsearchSearchView.setQuery(resSpeak, false);
            }
        } else if (requestCode == 222) {
            CheckGpsStatus();
            if (GpsStatus)
                getLocation2();
        }
    }

    private void alertDialogSearch(final String tag) {

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.item_listview_chooser, null, false);
            ImageButton btnSpeak = layout.findViewById(R.id.btnSpeak);
            btnSpeak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");

                    startActivityForResult(intent, 123);
                }
            });


            Button btnFr = layout.findViewById(R.id.btnFr);
            btnFr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogFilter.dismiss();
                }
            });

            ArrayList arrayList = new ArrayList();
            ArrayList arrayListID = new ArrayList();


            if (tag.equals("city")) {
                arrayList = new ArrayList(dataCity);
                arrayListID = new ArrayList(dataCityID);

            } else if (tag.equals("takhasos")) {
                arrayList = new ArrayList(dataTakhasos);
                arrayListID = new ArrayList(dataTakhasosID);
            } else if (tag.equals("darmonghah")) {
                arrayList = new ArrayList(dataHospital);
                arrayListID = new ArrayList(dataHospitalID);
            } else if (tag.equals("time")) {
                arrayList = new ArrayList(dataTime);
                arrayListID = new ArrayList(dataTimeID);
            } else if (tag.equals("doctor")) {
                arrayList = new ArrayList(dataDoctor);
                arrayListID = new ArrayList(dataDoctorID);
            }


            if (arraylistSearchView.size() != 0)
                arraylistSearchView.clear();

            for (int i = 0; i < arrayList.size(); i++) {
                ModAlerts modAlerts = new ModAlerts(arrayList.get(i) + "", arrayListID.get(i) + "");
                arraylistSearchView.add(modAlerts);
            }

            RecyclerView recycFitler = layout.findViewById(R.id.recycFitler);
            adRecycPopUp = new AdRecycPopUp(getContext(), arraylistSearchView, new onClickInterface() {
                @Override
                public void setClick(int position, boolean canUse, View view) {
                    try {
                        TextView txttitle = ((LinearLayout) view).findViewById(R.id.txtTitle);
                        TextView txtId = ((LinearLayout) view).findViewById(R.id.txtId);
                        String title = txttitle.getText().toString();
                        String id = txtId.getText().toString();
                        if (tag.equals("city")) {
                            txtFrRes_city.setText(title + "");
                            cityId = id;
                            citySt = title;
                            if (id.equals("0")) {
                                if (dataHospital.size() != 0) {
                                    dataHospital.clear();
                                    dataHospitalID.clear();
                                }

                                dataHospital = new ArrayList(dataHospital2);
                                dataHospitalID = new ArrayList(dataHospitalID2);

                            } else {
                                // TODO: Sent new id to link1 and change HOSPITAL NAMES --------------------------------------------------------

                                JSONObject object = new JSONObject();
                                try {
                                    //    object.put("id", id);
                                    object.put("hsp_id", "");
                                    object.put("spc_id", "0");
                                    object.put("dr_id", "0");
                                    object.put("prg_date", "0");
                                    object.put("city_id", cityId + "");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                alertDialogLoding.show();

                                new setConnectionVolley(getContext(), "http://nobat.mazums.ac.ir/TurnAppApi/Search?city_id=" + cityId, object
                                ).connectStringRequest(new setConnectionVolley.OnResponse() {
                                    @Override
                                    public void OnResponse(String response) {
                                        setDropDownsData(response, "updateHospital");
                                        dataDoctor = new ArrayList(dataDoctor2);
                                        dataDoctorID = new ArrayList(dataDoctorID2);
                                        txtFrRes_doctor.setText(dataDoctor.get(0) + "");
                                        doctorId = dataDoctorID.get(0) + "";
                                    }
                                });
                            }
                            //  String result = "{\"status\":\"yes\",\"message\":\"\",\"data\":{ \"hospital\":[{\"id\":\"0\",\"title\":\"تمامی بیمارستان ها\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید حسینی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید حسینی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید حسینی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید حسینی\"},{\"id\":\"1\",\"title\":\"بیمارستان عمومی ماساچوست\"}],\"doctor\":[],\"specialty\":[],\"time\":[],\"city\":[]}}";
                            //  setDropDownsData(result, "update");
                        } else if (tag.equals("takhasos")) {
                            txtFrRes_takhasos.setText(title + "");
                            takhasosId = id;
                            takhasosSt = title;
                            if (id.equals("0")) {
                                if (dataTakhasos.size() != 0) {
                                    dataTakhasos.clear();
                                    dataTakhasosID.clear();
                                }
                                dataTakhasos = new ArrayList(dataTakhasos2);
                                dataTakhasosID = new ArrayList(dataTakhasosID2);
                                dataDoctorID = new ArrayList(dataDoctorID2);
                                dataDoctor = new ArrayList(dataDoctor2);

                            } else {
                                // TODO: Sent new id to link1 and change TAKASOS  --------------------------------------------------------

                                JSONObject object = new JSONObject();
                                try {
                                    //    object.put("id", id);
                                    object.put("hsp_id", hospiralId + "");
                                    object.put("spc_id", takhasosId + "");
                                    object.put("dr_id", "0");
                                    object.put("prg_date", "0");
                                    object.put("city_id", cityId);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                alertDialogLoding.show();
                                String vUrlTakhasos = "http://nobat.mazums.ac.ir/TurnAppApi/Search?hsp_id=" + hospiralId + "&spc_id=" + takhasosId;
                                new setConnectionVolley(getContext(), vUrlTakhasos, object  // inja chi bayad gozasht?  takhasos va hospital
                                ).connectStringRequest(new setConnectionVolley.OnResponse() {
                                    @Override
                                    public void OnResponse(String response) {
                                        setDropDownsData(response, "updateDoctor");
                                    }
                                });
                            }

                        } else if (tag.equals("darmonghah")) {
                            txtFrRes_darmonghah.setText(title + "");
                            hospiralId = id;  // the last hsp_id save in here
                            hospiralSt = title;
                            if (id.equals("0")) {
                                if (dataHospitalID2.size() != 0) {
                                    dataHospital2.clear();
                                    dataHospitalID2.clear();
                                }
                                dataHospital = new ArrayList(dataHospital2);
                                dataHospitalID = new ArrayList(dataHospitalID2);

                                dataDoctorID = new ArrayList(dataDoctorID2);
                                dataDoctor = new ArrayList(dataDoctor2);
                            } else {
                                // TODO: Sent new id to link1 and change TAKASOS  --------------------------------------------------------

                                JSONObject object = new JSONObject();
                                try {
                                    //    object.put("id", id);
                                    object.put("hsp_id", hospiralId + "");
                                    object.put("spc_id", takhasosId + "");
                                    object.put("dr_id", "0");
                                    object.put("prg_date", "0");
                                    object.put("city_id", "0"); // ؟
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                alertDialogLoding.show();

                                new setConnectionVolley(getContext(), "http://nobat.mazums.ac.ir/TurnAppApi/Search?hsp_id=" + hospiralId + "&spc_id=" + takhasosId, object  // inja chi bayad gozasht?  takhasos va hospital
                                ).connectStringRequest(new setConnectionVolley.OnResponse() {
                                    @Override
                                    public void OnResponse(String response) {
                                        setDropDownsData(response, "updateDoctor");
                                    }
                                });
                            }
                        } else if (tag.equals("time")) {
                            txtFrRes_time.setText(title + "");
                            timeId = id;
                            timeSt = title;
                        } else if (tag.equals("doctor")) {
                            txtFrRes_doctor.setText(title + "");
                            doctorId = id;
                            doctorSt = title;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    alertDialogFilter.dismiss();
                }
            });
            recycFitler.setAdapter(adRecycPopUp);

            editsearchSearchView = layout.findViewById(R.id.searchFr);
            editsearchSearchView.setOnQueryTextListener(this);

            builder.setView(layout);
            alertDialogFilter = builder.create();
            alertDialogFilter.show();
            alertDialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            btnFrRes_next.setTextColor(getResources().getColor(R.color.colorRed));
            btnFrRes_next.setBackground(getResources().getDrawable(R.drawable.button_background_red));

            //   new HideKeyboardFrom(getContext(), layout);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  SearchView -------------------------------------------------------------
    private SearchView editsearchSearchView;
    private AdRecycPopUp adRecycPopUp;
    private ArrayList<ModAlerts> arraylistSearchView = new ArrayList<ModAlerts>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adRecycPopUp.filter(text);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        // your search methods
        editsearchSearchView.clearFocus();
        return true;
    }

    private setDataToFragment msetDataToFragment;

    private void onAttachToParentFragment(Fragment fragment) {
        try {
            msetDataToFragment = (setDataToFragment) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    fragment.toString() + " must implement OnPlayerSelectionSetListener");
        }
    }
}