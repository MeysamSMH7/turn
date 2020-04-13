package com.example.turn.Activity.Main.Fragment.FragmentReserve;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.azimolabs.maskformatter.MaskFormatter;
import com.example.turn.Activity.Main.Adapter.AdRecycPopUp;
import com.example.turn.Activity.Main.Adapter.onClickInterface;
import com.example.turn.Activity.Main.Fragment.FragmentReserve.Adapter.AdRecycResTimes;
import com.example.turn.Activity.Main.Fragment.FragmentReserve.Model.ModResTime;
import com.example.turn.Activity.Main.Model.ModAlerts;
import com.example.turn.Classes.ShowMessage;
import com.example.turn.Classes.setConnectionVolley;
import com.example.turn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class frTab_reserve extends Fragment implements SearchView.OnQueryTextListener {

    private LinearLayout linearLinears;
    private LinearLayout linearBtns;
    private LinearLayout linearPrint;

    //  linearSelectFilters
    private AlertDialog alertDialogFilter;
    private LinearLayout linearSelectFilters;
    private LinearLayout linearSelectFiltersBtn;

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

    private ArrayList dataCityID;
    private ArrayList dataTakhasosID;
    private ArrayList dataTakhasosID2;
    private ArrayList dataHospitalID;
    private ArrayList dataHospitalID2;
    private ArrayList dataTimeID;
    private ArrayList dataDoctorID;

    private String cityId = "";
    private String takhasosId = "";
    private String hospiralId = "";
    private String timeId = "";
    private String doctorId = "";

    //------------ linearResTimes
    private AlertDialog alertDialogTavafoghName;
    private AlertDialog alertFilterRT;
    private LinearLayout linearResTimes;
    private LinearLayout linearResTimesBtn;
    private RecyclerView rcycRT;
    private Button btnRT_filter;
    private SwipeRefreshLayout refreshRT;

    private AdRecycResTimes adapterResTimes;
    private ArrayList<ModResTime> arrayListResTimes;

    private ImageView btnRT_previous;
    private String dr_prg_hsp_mdc_spc_date_id = "";
    private int positionItemRecycleView = -1;

    private Boolean isScrollingFP = false;
    private int currentItemFP = 0, totalItemsFP = 0, scrollOutItemsFP = 0;
    private LinearLayoutManager rowManager;
    private Boolean boolCheckNetFP = false;
    private int pageNumber = -1;

    //------------ linearPazireshPage
    private LinearLayout linearPazireshPage;
    private LinearLayout linearPazireshPageBtn;
    private LinearLayout linearPP_pazireshData;
    private TextView txtPP_markazName;
    private TextView txtPP_doctorName;
    private TextView txtPP_motakhasesName;
    private TextView txtPP_datePP;
    private RadioGroup radioGPPP_CodMeli;
    private EditText edtFrPP_Cod;
    private Button btnPP_search;

    private LinearLayout linearPazireshPage2;
    private EditText edtFrPP_name;
    private EditText edtFrPP_family;
    private EditText edtFrPP_fatherName;
    private RadioGroup radioGPPP_sex;
    private RadioButton radioBtnPP_female;
    private RadioButton radioBtnPP_male;
    private EditText edtFrPP_phone;
    private LinearLayout linearFrPP_City;
    private TextView txtFrPP_city;
    private LinearLayout linearFrPP_ostan;
    private TextView txtFrPP_ostan;
    private EditText edtFrPP_address;
    private LinearLayout linearFrPP_bime;
    private TextView txtFrPP_bime;
    private CardView cardViewFrPP_org;
    private LinearLayout linearFrPP_org;
    private TextView txtFrPP_org;
    private ImageView imgPP_drPic;
    private Button btnPP_paziresh;

    private Button btnPP_previous;
    private String hsp_pp;

    private ArrayList arrayListOstan;
    private ArrayList arrayListCity;
    private ArrayList arrayListBime;
    private ArrayList arrayListOstanID;
    private ArrayList arrayListCityID;
    private ArrayList arrayListBimeID;
    private ArrayList arrayListOrg;
    private ArrayList arrayListOrgID;

    private String bimeTitleSamane;
    private String bimeIdSamane;
    private String addressSamane;
    private String sexSamane;
    private String cityIdSamane;
    private String ostanIdSamane;
    private String orgIdSamane;
    private String phoneNumSamane;
    private String fatherNameSamane;
    private String lastNameSamane;
    private String firstNameSamane;
    private String srv_id;
    private String rcp_id;
    //

    private TextView txtPrint_date;
    private TextView txtPrint_time;
    private TextView txtPrint_hours;
    private TextView txtPrint_shit;
    private TextView txtPrint_doctor;
    private TextView txtPrint_type;
    private TextView txtPrint_typeBime;
    private TextView txtPrint_firstLastName;
    private TextView txtPrint_price;
    private TextView txtPrint_bakhsh;
    private TextView txtPrint_codNobat;
    private TextView txtPrint_numberNobat;
    private ImageView imgPrint_batcod;
    private TextView txtPrint_ghabzNumber;
    private TextView txtPrint_time2;
    private TextView txtPrint_hospitalName;
    private TextView txtPrint_hospitalAddress;
    private TextView txtPrint_hospitalTell;
    private Button btnPrint_dismis;
    private Button btnPrint_pay;

    //------------------------------------
    private AlertDialog alertDialogLoding;


    public static frTab_reserve newInstance() {

        Bundle args = new Bundle();
        frTab_reserve fragment = new frTab_reserve();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_tab_reserve, container, false);


        linearLinears = view.findViewById(R.id.linearLinears);
        linearBtns = view.findViewById(R.id.linearBtns);
        linearPrint = view.findViewById(R.id.linearPrint);

        loading();

        selectFilters(view);
        reservationTimes(view);
        paziresh(view);
        print(view);

        linearSelectFilters.setVisibility(View.VISIBLE);
        linearSelectFiltersBtn.setVisibility(View.VISIBLE);

        linearResTimes.setVisibility(View.GONE);
        linearResTimesBtn.setVisibility(View.GONE);

        linearPazireshPage.setVisibility(View.GONE);
        linearPazireshPageBtn.setVisibility(View.GONE);
        linearPazireshPage2.setVisibility(View.GONE);

//----------------------------------------------------------------------------
        linearLinears.setVisibility(View.VISIBLE);
        linearBtns.setVisibility(View.VISIBLE);
        linearPrint.setVisibility(View.GONE);


        String txt = "null";
        String txt2 = txt.equals("null") ? "" : txt;

        return view;
    }

    private void print(View view) {

        txtPrint_date = view.findViewById(R.id.txtPrint_date);
        txtPrint_time = view.findViewById(R.id.txtPrint_time);
        txtPrint_hours = view.findViewById(R.id.txtPrint_hours);
        txtPrint_shit = view.findViewById(R.id.txtPrint_shit);
        txtPrint_doctor = view.findViewById(R.id.txtPrint_doctor);
        txtPrint_type = view.findViewById(R.id.txtPrint_type);
        txtPrint_typeBime = view.findViewById(R.id.txtPrint_typeBime);
        txtPrint_firstLastName = view.findViewById(R.id.txtPrint_firstLastName);
        txtPrint_price = view.findViewById(R.id.txtPrint_price);
        txtPrint_bakhsh = view.findViewById(R.id.txtPrint_bakhsh);
        txtPrint_codNobat = view.findViewById(R.id.txtPrint_codNobat);
        txtPrint_numberNobat = view.findViewById(R.id.txtPrint_numberNobat);
        imgPrint_batcod = view.findViewById(R.id.imgPrint_batcod);
        txtPrint_ghabzNumber = view.findViewById(R.id.txtPrint_ghabzNumber);
        txtPrint_time2 = view.findViewById(R.id.txtPrint_time2);
        txtPrint_hospitalName = view.findViewById(R.id.txtPrint_hospitalName);
        txtPrint_hospitalAddress = view.findViewById(R.id.txtPrint_hospitalAddress);
        txtPrint_hospitalTell = view.findViewById(R.id.txtPrint_hospitalTell);
        btnPrint_pay = view.findViewById(R.id.btnPrint_pay);
        btnPrint_pay.setVisibility(View.VISIBLE);
        btnPrint_dismis = view.findViewById(R.id.btnPrint_dismis);
        btnPrint_dismis.setVisibility(View.VISIBLE);

        btnPrint_dismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//--------- back to previous
                linearSelectFilters.setVisibility(View.GONE);
                linearSelectFiltersBtn.setVisibility(View.GONE);
                linearResTimes.setVisibility(View.GONE);
                linearResTimesBtn.setVisibility(View.GONE);
                linearPazireshPage.setVisibility(View.VISIBLE);
                linearPazireshPageBtn.setVisibility(View.VISIBLE);
                linearPazireshPage2.setVisibility(View.VISIBLE);
                linearLinears.setVisibility(View.VISIBLE);
                linearBtns.setVisibility(View.VISIBLE);
                linearPrint.setVisibility(View.GONE);

            }
        });

        btnPrint_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = "http://nobat.mazums.ac.ir/Pay/Apprdr/?r=" + rcp_id + "&h=" + hospiralId;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                setAllDataToDefault();
//--------- back to main page
                linearSelectFilters.setVisibility(View.VISIBLE);
                linearSelectFiltersBtn.setVisibility(View.VISIBLE);
                linearResTimes.setVisibility(View.GONE);
                linearResTimesBtn.setVisibility(View.GONE);
                linearPazireshPage.setVisibility(View.GONE);
                linearPazireshPageBtn.setVisibility(View.GONE);
                linearPazireshPage2.setVisibility(View.GONE);
                linearLinears.setVisibility(View.VISIBLE);
                linearBtns.setVisibility(View.VISIBLE);
                linearPrint.setVisibility(View.GONE);

            }
        });


    }

    private void loading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.loading, null, false);

        builder.setView(layout);
        alertDialogLoding = builder.create();
        alertDialogLoding.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    private void paziresh(View view) {
        final boolean meliOrErja = false; // default is meli and its false
        linearPazireshPage = view.findViewById(R.id.linearPazireshPage);
        linearPazireshPageBtn = view.findViewById(R.id.linearPazireshPageBtn);
        linearPP_pazireshData = view.findViewById(R.id.linearPP_pazireshData);
        txtPP_markazName = view.findViewById(R.id.txtPP_markazName);
        txtPP_doctorName = view.findViewById(R.id.txtPP_doctorName);
        txtPP_motakhasesName = view.findViewById(R.id.txtPP_motakhasesName);
        txtPP_datePP = view.findViewById(R.id.txtPP_datePP);
        radioGPPP_CodMeli = view.findViewById(R.id.radioGPPP_CodMeli);
        edtFrPP_Cod = view.findViewById(R.id.edtFrPP_Cod);
        btnPP_search = view.findViewById(R.id.btnPP_search);
        btnPP_paziresh = view.findViewById(R.id.btnPP_paziresh);
        btnPP_paziresh.setVisibility(View.INVISIBLE);

        arrayListOstan = new ArrayList();
        arrayListCity = new ArrayList();
        arrayListBime = new ArrayList();
        arrayListOstanID = new ArrayList();
        arrayListCityID = new ArrayList();
        arrayListBimeID = new ArrayList();
        arrayListOrg = new ArrayList();
        arrayListOrgID = new ArrayList();

        btnPP_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), "اجرا شدن لودینگ", Toast.LENGTH_SHORT).show();
                linearPazireshPage2.setVisibility(View.VISIBLE);
                btnPP_paziresh.setVisibility(View.VISIBLE);
//                TODO: Send id , meli and erja to link3 -----------------------------------------

                String meliOrEjra = edtFrPP_Cod.getText().toString();
                if (meliOrEjra.equals("")) {
                    Toast.makeText(getContext(), "کد ملی نا معتبر است", Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONObject object = new JSONObject();
                // in khat 214 chie   صاشفwhatspp
                try {
//                    object.put("mdc_id", dr_prg_hsp_mdc_spc_date_id);
//meliOrErja az  radio btn miad
                    if (meliOrErja) {
                        object.put("pp_id", meliOrEjra);
                        object.put("srv_id", "");
                    } else {
                        object.put("pp_id", "");
                        object.put("srv_id", meliOrEjra);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                String vUrl = "http://nobat.mazums.ac.ir/TurnAppApi/turn/SearchByPpId?hsp_id=" + hsp_pp + "&pp_id=" + meliOrEjra;
                alertDialogLoding.show();
                new setConnectionVolley(getContext(), vUrl, object
                ).connectStringRequest(new setConnectionVolley.OnResponse() {
                    @Override
                    public void OnResponse(String response) {
                        if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();
                        setDataFromSamane(response);
                    }
                });

//                String res = "{\"status\":\"yes\",\"message\":\"\",\"data\":{\"ins_id\":\"بیمه ی تکمیلی\",\"home_adr\":\"اراک انتهای خیابان\",\"is_sex\":\"0\",\"city_id\":\"1\",\"home_mbl\":\"09386977100\",\"father_name\":\"علی\",\"last_name\":\"حسینی\",\"first_name\":\"سیدمیثم\"}}";
//                setDataFromSamane(res);


            }
        });

        btnPP_paziresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//----------- when print is ok this 3 lines will be there (after get data rom server!!!!!)
                linearLinears.setVisibility(View.GONE);
                linearBtns.setVisibility(View.GONE);
                linearPrint.setVisibility(View.VISIBLE);

                //2_242_4781779718_45367_291_13990120
                //dr_prg_hsp_mdc_spc_date
                String[] temp = dr_prg_hsp_mdc_spc_date_id.split("_");
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("dr_id", temp[0] + "");
                    jsonObject.put("prg_id", temp[1] + "");
                    jsonObject.put("hsp_id", temp[2] + "");
                    jsonObject.put("mdc_id", temp[3] + "");
                    jsonObject.put("spc_id", temp[4] + "");
                    jsonObject.put("prg_date", temp[5] + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
// TODO: link check
                // set data in pazireshPage

                String phoneNum = edtFrPP_phone.getText().toString().replace(" ", "");

                if (phoneNum.equals("")) {
                    Toast.makeText(getContext(), "شماره ی موبایل نا معتبر است", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (edtFrPP_Cod.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "کد ملی نا معتبر است", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtFrPP_address.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "آدرس نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtFrPP_family.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "نام خانوادگی نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();

                    return;
                }

                if (edtFrPP_name.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "نام نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtFrPP_fatherName.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "نام پدر نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
                    return;
                }


                /*{turn_date:'',prg_id:0,spc_id:0,hsp_id:0,city_id:0,srv_id:0,pp_id:'0',familiy_code:'',
                        patient:{mome_mbl:'',home_adr:'',first_name:'',last_name:'',is_sex:0,father_name:'',ins_id:0}*/

                String vPazireshlink = "http://nobat.mazums.ac.ir/TurnAppApi/turn/MakeTurn?"
                        + "prg_id=" + temp[1]
                        + "&hsp_id=" + temp[2]
                        + "&srv_id=" + srv_id
                        + "&spc_id=" + temp[4]
                        + "&turn_date=" + temp[5]
                        + "&pp_id=" + edtFrPP_Cod.getText().toString()
                        + "&home_mbl=" + phoneNum
                        + "&home_adr=" + edtFrPP_address.getText().toString()
                        + "&last_name=" + edtFrPP_family.getText().toString()
                        + "&first_name=" + edtFrPP_name.getText().toString()
                        + "&is_sex=" + sexSamane
                        + "&father_name=" + edtFrPP_fatherName.getText().toString()
                        + "&ins_id=" + bimeIdSamane
                        + "&ost_id=" + ostanIdSamane
                        + "&org_id=" + orgIdSamane;
                alertDialogLoding.show();
                new setConnectionVolley(getContext(), vPazireshlink, jsonObject).connectStringRequest(new setConnectionVolley.OnResponse() {
                    @Override
                    public void OnResponse(String response) {
                        if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();
                        setDataPrint(response);
                    }
                });

            }


        });


        radioGPPP_CodMeli.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioBtnPP_CodMeli:
                        edtFrPP_Cod.setHint("کد ملی");
                        break;
                    case R.id.radioBtnPP_NumErja:
                        edtFrPP_Cod.setHint("کد ارجاع");
                        break;
                }
            }
        });
//-------------------------------------------------------------------------
        linearPazireshPage2 = view.findViewById(R.id.linearPazireshPage2);
        edtFrPP_name = view.findViewById(R.id.edtFrPP_name);
        edtFrPP_family = view.findViewById(R.id.edtFrPP_family);
        edtFrPP_fatherName = view.findViewById(R.id.edtFrPP_fatherName);
        radioGPPP_sex = view.findViewById(R.id.radioGPPP_sex); // radioBtnPP_female - radioBtnPP_male
        radioBtnPP_female = view.findViewById(R.id.radioBtnPP_female);
        radioBtnPP_male = view.findViewById(R.id.radioBtnPP_male);
        edtFrPP_phone = view.findViewById(R.id.edtFrPP_phone);
        linearFrPP_City = view.findViewById(R.id.linearFrPP_City);
        txtFrPP_city = view.findViewById(R.id.txtFrPP_city);
        linearFrPP_ostan = view.findViewById(R.id.linearFrPP_ostan);
        txtFrPP_ostan = view.findViewById(R.id.txtFrPP_ostan);
        edtFrPP_address = view.findViewById(R.id.edtFrPP_address);
        linearFrPP_bime = view.findViewById(R.id.linearFrPP_bime);
        txtFrPP_bime = view.findViewById(R.id.txtFrPP_bime);
        cardViewFrPP_org = view.findViewById(R.id.cardViewFrPP_org);
        linearFrPP_org = view.findViewById(R.id.linearFrPP_org);
        txtFrPP_org = view.findViewById(R.id.txtFrPP_org);

        imgPP_drPic = view.findViewById(R.id.imgPP_drPic);

        radioGPPP_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioBtnPP_male:
                        sexSamane = "0";
                        break;
                    case R.id.radioBtnPP_female:
                        sexSamane = "1";
                        break;
                }
            }
        });

        txtFrPP_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("PPCity");
            }
        });

        txtFrPP_ostan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("PPOstan");
            }
        });

        txtFrPP_bime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("PPBime");
            }
        });

        txtFrPP_org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("PPOrg");
            }
        });

        MaskFormatter maskFormatter = new MaskFormatter("999 999 9999", edtFrPP_phone);
        edtFrPP_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtFrPP_phone.setHint("911 345 6789");
        edtFrPP_phone.addTextChangedListener(maskFormatter);

        btnPP_previous = view.findViewById(R.id.btnPP_previous);
        btnPP_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                previousPage(linearPazireshPage, linearPazireshPageBtn, linearResTimes, linearResTimesBtn);

            }
        });


        linearFrPP_City.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open popup choose city
            }
        });


        linearFrPP_ostan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open popup choose ostan
            }
        });


        linearFrPP_bime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open popup choose bime
            }
        });


    }

    private void setDataPrint(String res) {
        try {
            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");
            if (status.equals("yes")) {
                String data = object.getString("data");
                JSONObject objectData = new JSONObject(data);
                //get  patient

                JSONObject jsonHospitalInfo = objectData.getJSONObject("hspInfo");
                String hospitalName = jsonHospitalInfo.getString("hsp_title");
                String hospitalAddress = jsonHospitalInfo.getString("hsp_addr");
                String hospitalTel = jsonHospitalInfo.getString("hsp_tel");

                JSONObject jsonTSF = objectData.getJSONObject("turnSpecification");
                String date = jsonTSF.getString("date_string") + "";
                String timeRes = jsonTSF.getString("rcp_time_str") + "";
                String timeHozor = jsonTSF.getString("prg_time") + "";
                String shift = jsonTSF.getString("shift_title") + "";
                String doctorName = jsonTSF.getString("dr_name") + "";
                String typeRes = jsonTSF.getString("srv_title") + "";
                rcp_id = jsonTSF.getString("rcp_id") + "";

                JSONObject bimeData = jsonTSF.getJSONObject("pInsurance");
                String bimeTitle = bimeData.getString("ins_title");

                JSONObject jsonPatient = objectData.getJSONObject("patient");
                String nameFamily = jsonPatient.getString("first_name") + " " + jsonPatient.getString("last_name");

                JSONObject jsonPay = objectData.getJSONObject("pay");
                String price = jsonPay.getString("pat_pay_str") + "";

                String pakhshName = jsonTSF.getString("sec_title") + "";
                String codNobat = jsonTSF.getString("rcp_no") + "";
                String numberNobat = jsonTSF.getString("csh_rcp_no") + "";


                if (hospitalName.equals("null"))
                    hospitalName = "";

                if (hospitalAddress.equals("null"))
                    hospitalAddress = "";

                if (hospitalTel.equals("null"))
                    hospitalTel = "";

                if (date.equals("null"))
                    date = "";

                if (timeRes.equals("null"))
                    timeRes = "";

                if (timeHozor.equals("null"))
                    timeHozor = "";

                if (shift.equals("null"))
                    shift = "";

                if (doctorName.equals("null"))
                    doctorName = "";

                if (typeRes.equals("null"))
                    typeRes = "";

                if (bimeTitle.equals("null"))
                    bimeTitle = "";

                if (nameFamily.equals("null"))
                    nameFamily = "";

                if (price.equals("null"))
                    price = "";

                if (pakhshName.equals("null"))
                    pakhshName = "";

                if (codNobat.equals("null"))
                    codNobat = "";

                if (numberNobat.equals("null"))
                    numberNobat = "";

                //---------------------------------
                txtPrint_hospitalName.setText(hospitalName);
                txtPrint_hospitalAddress.setText("آدرس: " + hospitalAddress);
                txtPrint_hospitalTell.setText("تلفن مرکز: " + hospitalTel);

                txtPrint_date.setText("تاریخ اخذ نوبت: " + date);
                txtPrint_time.setText("ساعت اخذ نوبت: " + timeRes);
                txtPrint_hours.setText("ساخت حضور بیمار: " + timeHozor);
                txtPrint_shit.setText("شیفت: " + shift);
                txtPrint_doctor.setText("پزشک: " + doctorName);
                txtPrint_type.setText("نوع خدمت: " + typeRes);
                txtPrint_typeBime.setText("نوبع بیمه: " + bimeTitle);

                txtPrint_firstLastName.setText("نام و نام خانوادگی بیمار: " + nameFamily);
                txtPrint_price.setText("مبلع قابل پرداخت: " + price);

                txtPrint_bakhsh.setText("نام بخش: " + pakhshName);
                txtPrint_codNobat.setText("کد پیگیری: " + codNobat);
                txtPrint_numberNobat.setText("شماره نوبت: " + numberNobat);


            } else if (message.equals("Error creating window handle.")) {
//--------- back to previous
                linearSelectFilters.setVisibility(View.GONE);
                linearSelectFiltersBtn.setVisibility(View.GONE);
                linearResTimes.setVisibility(View.GONE);
                linearResTimesBtn.setVisibility(View.GONE);
                linearPazireshPage.setVisibility(View.VISIBLE);
                linearPazireshPageBtn.setVisibility(View.VISIBLE);
                linearPazireshPage2.setVisibility(View.VISIBLE);
                linearLinears.setVisibility(View.VISIBLE);
                linearBtns.setVisibility(View.VISIBLE);
                linearPrint.setVisibility(View.GONE);
            } else {
//--------- back to previous
                linearSelectFilters.setVisibility(View.GONE);
                linearSelectFiltersBtn.setVisibility(View.GONE);
                linearResTimes.setVisibility(View.GONE);
                linearResTimesBtn.setVisibility(View.GONE);
                linearPazireshPage.setVisibility(View.VISIBLE);
                linearPazireshPageBtn.setVisibility(View.VISIBLE);
                linearPazireshPage2.setVisibility(View.VISIBLE);
                linearLinears.setVisibility(View.VISIBLE);
                linearBtns.setVisibility(View.VISIBLE);
                linearPrint.setVisibility(View.GONE);
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // استعلام هویت  estelame hoviyat
    private void setDataFromSamane(String res) {

        try {
            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");
            //   if (status.equals("yes")) {
            linearPazireshPage2.setVisibility(View.VISIBLE);
            String data = object.getString("data");
            JSONObject objectData = new JSONObject(data);
            //get  patient


            JSONObject jsonPatient = objectData.getJSONObject("patient");
            firstNameSamane = jsonPatient.getString("first_name") + "";
            lastNameSamane = jsonPatient.getString("last_name") + "";
            if (!firstNameSamane.equals("null") || !lastNameSamane.equals("null")) {
                cardViewFrPP_org.setVisibility(View.GONE);
                fatherNameSamane = jsonPatient.getString("father_name") + "";
                if (fatherNameSamane.equals("null")) fatherNameSamane = "";
                cityIdSamane = jsonPatient.getString("city_id");
                //               cityTitleSamane = jsonPatient.getString("city_title");
                sexSamane = jsonPatient.getString("is_sex"); // zero = man | one = woman
                addressSamane = jsonPatient.getString("home_adr");
                phoneNumSamane = jsonPatient.getString("home_mbl");
                bimeIdSamane = jsonPatient.getString("ins_id");
                bimeTitleSamane = jsonPatient.getString("ins_title");
//---------------------------

                if (firstNameSamane.equals("null"))
                    firstNameSamane = "";

                if (lastNameSamane.equals("null"))
                    lastNameSamane = "";

                if (fatherNameSamane.equals("null"))
                    fatherNameSamane = "";

//                if (cityTitleSamane.equals("null"))
//                    cityTitleSamane = "";

                if (sexSamane.equals("null"))
                    sexSamane = "-1";

                if (addressSamane.equals("null"))
                    addressSamane = "";

                if (phoneNumSamane.equals("null"))
                    phoneNumSamane = "";

                if (bimeIdSamane.equals("null"))
                    bimeIdSamane = "";

                if (bimeTitleSamane.equals("null"))
                    bimeTitleSamane = "";

                edtFrPP_name.setText(firstNameSamane + "");
                edtFrPP_name.setEnabled(false);
                edtFrPP_family.setText(lastNameSamane + "");
                edtFrPP_family.setEnabled(false);
                edtFrPP_fatherName.setText(fatherNameSamane + "");
                edtFrPP_phone.setText(phoneNumSamane + "");
                txtFrPP_city.setText(cityIdSamane + "");
                edtFrPP_address.setText(addressSamane + "");
                txtFrPP_bime.setText(bimeTitleSamane + "");

                if (sexSamane.equals("0"))
                    radioBtnPP_male.setChecked(true);
                else if (sexSamane.equals("1"))
                    radioBtnPP_female.setChecked(true);
                else {
                    radioBtnPP_male.setChecked(false);
                    radioBtnPP_female.setChecked(false);
                }
            } else {
                cardViewFrPP_org.setVisibility(View.VISIBLE);
                JSONArray arrayOrg = objectData.getJSONArray("orgs");
                for (int i = 0; i < arrayOrg.length(); i++) {
                    JSONObject temp = arrayOrg.getJSONObject(i);
                    arrayListOrg.add(temp.getString("title"));
                    arrayListOrgID.add(temp.getString("id"));
                }
                edtFrPP_name.setEnabled(true);
                edtFrPP_family.setEnabled(true);

            }

            // get city
            JSONArray arrayCity = objectData.getJSONArray("cities");
            for (int i = 0; i < arrayCity.length(); i++) {
                JSONObject temp = arrayCity.getJSONObject(i);
                arrayListCity.add(temp.getString("title"));
                arrayListCityID.add(temp.getString("id"));
            }

            // get bime
            JSONArray arrayBime = objectData.getJSONArray("inslist");
            for (int i = 0; i < arrayBime.length(); i++) {
                JSONObject temp = arrayBime.getJSONObject(i);
                arrayListBime.add(temp.getString("ins_title"));
                arrayListBimeID.add(temp.getString("ins_id"));
            }

            // get ostan
            JSONArray arrayOstan = objectData.getJSONArray("ostans");
            for (int i = 0; i < arrayOstan.length(); i++) {
                JSONObject temp = arrayOstan.getJSONObject(i);
                arrayListOstan.add(temp.getString("title"));
                arrayListOstanID.add(temp.getString("id"));
            }

            //} else
//                new ShowMessage(getContext()).ShowMessage_SnackBar(linearSelectFilters, message + "");
            //   new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reservationTimes(final View view) {
        linearResTimes = view.findViewById(R.id.linearResTimes);
        linearResTimesBtn = view.findViewById(R.id.linearResTimesBtn);
        btnRT_previous = view.findViewById(R.id.btnRT_previous);
        rcycRT = view.findViewById(R.id.rcycRT);
        btnRT_filter = view.findViewById(R.id.btnRT_filter);
        refreshRT = view.findViewById(R.id.refreshRT);

        arrayListResTimes = new ArrayList();

        btnRT_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousPage(linearResTimes, linearResTimesBtn, linearSelectFilters, linearSelectFiltersBtn);

                if (arrayListResTimes.size() != 0)
                    arrayListResTimes.clear();

                txtFrRes_city = view.findViewById(R.id.txtFrRes_city);
                txtFrRes_takhasos = view.findViewById(R.id.txtFrRes_takhasos);
                txtFrRes_darmonghah = view.findViewById(R.id.txtFrRes_darmonghah);
                txtFrRes_time = view.findViewById(R.id.txtFrRes_time);
                txtFrRes_doctor = view.findViewById(R.id.txtFrRes_doctor);

            }
        });

        refreshRT.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });


        btnRT_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(false);
                LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_select_filters, null, false);
                LinearLayout layoutBtnsRT = layout.findViewById(R.id.layoutBtnsRT);
                layoutBtnsRT.setVisibility(View.VISIBLE);

                Button btnRT_dismis = layout.findViewById(R.id.btnRT_dismis);
                Button btnRT_reset = layout.findViewById(R.id.btnRT_reset);
                Button btnRT_search = layout.findViewById(R.id.btnRT_search);

                btnRT_dismis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertFilterRT.dismiss();
                    }
                });

                btnRT_reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        defaultDataDropDown();
                    }
                });

                btnRT_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertFilterRT.dismiss();
                        refreshData();
                    }
                });

                LinearLayout linearFrRes_city = layout.findViewById(R.id.linearFrRes_city);
                LinearLayout linearFrRes_takhasos = layout.findViewById(R.id.linearFrRes_takhasos);
                LinearLayout linearFrRes_darmonghah = layout.findViewById(R.id.linearFrRes_darmonghah);
                LinearLayout linearFrRes_time = layout.findViewById(R.id.linearFrRes_time);
                LinearLayout linearFrRes_doctor = layout.findViewById(R.id.linearFrRes_doctor);

                txtFrRes_city = layout.findViewById(R.id.txtFrRes_city);
                txtFrRes_takhasos = layout.findViewById(R.id.txtFrRes_takhasos);
                txtFrRes_darmonghah = layout.findViewById(R.id.txtFrRes_darmonghah);
                txtFrRes_time = layout.findViewById(R.id.txtFrRes_time);
                txtFrRes_doctor = layout.findViewById(R.id.txtFrRes_doctor);

                //  Clicks
                linearFrRes_city.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialogShow("city");
                    }
                });
                linearFrRes_takhasos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialogShow("takhasos");
                    }
                });
                linearFrRes_darmonghah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialogShow("darmonghah");
                    }
                });
                linearFrRes_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialogShow("time");
                    }
                });

                linearFrRes_doctor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialogShow("doctor");
                    }
                });

                defaultDataDropDown();
                builder.setView(layout);
                alertFilterRT = builder.create();
                alertFilterRT.show();
                alertFilterRT.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }
        });


        rowManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcycRT.setLayoutManager(rowManager);

// after scroll to end and get new data (next page in web)
        rcycRT.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrollingFP = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItemFP = rowManager.getChildCount();
                totalItemsFP = rowManager.getItemCount();
                scrollOutItemsFP = rowManager.findFirstVisibleItemPosition();

                if (dx < 10) {
                    if (isScrollingFP && (currentItemFP + scrollOutItemsFP == totalItemsFP)) {
                        isScrollingFP = false;
                        if (!boolCheckNetFP) {
                            pageNumber++;
                        }
                        doSearch();
                    }
                }
            }
        });

        onClickInterface onclickInterface = new onClickInterface() {
            @Override
            public void setClick(final int position, boolean canUse, View view) {
                if (canUse) {
//// alert tavafoghname
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.tavafoghname, null, false);
                    Button btnTavafogh_no = layout.findViewById(R.id.btnTavafogh_no);
                    final Button btnTavafogh_ok = layout.findViewById(R.id.btnTavafogh_ok);
                    btnTavafogh_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialogTavafoghName.dismiss();
                        }
                    });
                    btnTavafogh_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            linearPazireshPage2.setVisibility(View.GONE);
                            btnPP_paziresh.setVisibility(View.INVISIBLE);
                            alertDialogLoding.show();
                            positionItemRecycleView = position;
                            nextPage(linearResTimes, linearResTimesBtn, linearPazireshPage, linearPazireshPageBtn);
                            alertDialogTavafoghName.dismiss();
                            dr_prg_hsp_mdc_spc_date_id = arrayListResTimes.get(position).id;
                            //2_242_4781779718_45367_291_13990120
                            //dr_prg_hsp_mdc_spc_date
                            String[] temp = dr_prg_hsp_mdc_spc_date_id.split("_");
                            JSONObject jsonObject = new JSONObject();
                            hospiralId = temp[2];

                            try {
                                jsonObject.put("dr_id", temp[0] + "");
                                jsonObject.put("prg_id", temp[1] + "");
                                jsonObject.put("hsp_id", temp[2] + "");
                                jsonObject.put("mdc_id", temp[3] + "");
                                jsonObject.put("spc_id", temp[4] + "");
                                jsonObject.put("prg_date", temp[5] + "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
// TODO: link check
                            // set data in pazireshPage
                            alertDialogLoding.show();
                            String link = "http://nobat.mazums.ac.ir/TurnAppApi/turn/TurnSpecification?dr_id=" + temp[0] + "" + "&prg_id=" + temp[1] + "" + "&hsp_id=" + temp[2] + "" + "&mdc_id=" + temp[3] + "" + "&spc_id=" + temp[4] + "&turn_date=" + temp[5] + "";
                            new setConnectionVolley(getContext(), link, jsonObject).connectStringRequest(new setConnectionVolley.OnResponse() {
                                @Override
                                public void OnResponse(String response) {
                                    if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();
                                    setDataInPazireshPage(response);
                                }
                            });

                        }
                    });

                    builder.setView(layout);
                    alertDialogTavafoghName = builder.create();
                    alertDialogTavafoghName.show();
//----- end of alert
                } else
                    Toast.makeText(getContext(), "این نوبت به پایین رسیده است.", Toast.LENGTH_SHORT).show();
            }
        };
        adapterResTimes = new AdRecycResTimes(getContext(), arrayListResTimes, onclickInterface);
        rcycRT.setAdapter(adapterResTimes);

    }

    private void setRecycViewData(String res) {
        try {
            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");
            if (status.equals("yes")) {
                String data = object.getString("data"); // data! nvase chi whatsapp
                JSONObject arrayData1 = new JSONObject(data);

                JSONArray arrayData = arrayData1.getJSONArray("turnList");  // chish moishkel dare

                if (arrayData.length() != 0 && pageNumber == 1) {
                    previousPage(linearResTimes, linearResTimesBtn, linearSelectFilters, linearSelectFiltersBtn);
                    Toast.makeText(getContext(), "اطلاعاتی وجود ندارد", Toast.LENGTH_SHORT).show();
                }

                if (arrayData.length() != 10)
                    Toast.makeText(getContext(), "اطلاعات بیشتری وجود ندارد", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < arrayData.length(); i++) {
                    JSONObject objectTemp = arrayData.getJSONObject(i);
                    ModResTime time = new ModResTime();
                    //in
                    String id = objectTemp.getString("id");
                    String hsp_title = objectTemp.getString("hsp_title");
                    String shift_title = objectTemp.getString("shift_title");
                    String dr_name = objectTemp.getString("dr_name");
                    String spc_title = objectTemp.getString("spc_title");
                    String prg_date = objectTemp.getString("prg_date");
                    String web_turn = objectTemp.getString("web_turn");
                    String status_type = objectTemp.getString("status_type");

                    if (id.equals("null"))
                        id = "";
                    if (hsp_title.equals("null"))
                        hsp_title = "";
                    if (shift_title.equals("null"))
                        shift_title = "";
                    if (dr_name.equals("null"))
                        dr_name = "";
                    if (spc_title.equals("null"))
                        spc_title = "";
                    if (prg_date.equals("null"))
                        prg_date = "";
                    if (web_turn.equals("null"))
                        web_turn = "";
                    if (status_type.equals("null"))
                        status_type = "";

                    time.id = id;
                    time.hsp_title = hsp_title;
                    time.shift_title = shift_title;
                    time.dr_name = dr_name;
                    time.spc_title = spc_title;
                    time.prg_date = prg_date;
                    time.web_turn = web_turn;
                    time.status_type = status_type;
                    arrayListResTimes.add(time);
                }
                adapterResTimes.notifyDataSetChanged();

            } else {
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);
                previousPage(linearResTimes, linearResTimesBtn, linearSelectFilters, linearSelectFiltersBtn);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDataInPazireshPage(String res) {

        try {
            if (alertDialogLoding.isShowing())
                alertDialogLoding.dismiss();
            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");

            if (status.equals("yes")) {
                String data = object.getString("data");
                JSONObject objData = new JSONObject(data);

                //get  f
                //      sec_id:0,srv_id:0, spc_level_title:"",dr_name:"", dr_image:"", spc_title:"",turn_date:'',ref_count:0},
                JSONObject jsonTSF = objData.getJSONObject("turnSpecification");
                String dr_name = jsonTSF.getString("dr_name");
                String dr_image = jsonTSF.getString("dr_image");
                String sec_id = jsonTSF.getString("sec_id");
                String spc_title = jsonTSF.getString("spc_title");
                String spc_level_title = jsonTSF.getString("spc_level_title");
                String ref_count = jsonTSF.getString("ref_count");
                String turn_date = jsonTSF.getString("date_string");
                // get hspInfo
                JSONObject jsonhspInfo = objData.getJSONObject("hspInfo");
                String hsp_title = jsonhspInfo.getString("hsp_title");

                hsp_pp = jsonhspInfo.getString("hsp_id");
                hsp_pp = hospiralId;
                srv_id = jsonTSF.getString("srv_id");

                if (dr_name.equals("null"))
                    dr_name = "";
                if (srv_id.equals("null"))
                    srv_id = "";
                if (spc_title.equals("null"))
                    spc_title = "";
                if (spc_level_title.equals("null"))
                    spc_level_title = "";
                if (turn_date.equals("null"))
                    turn_date = "";
                if (hsp_pp.equals("null"))
                    hsp_pp = "";
                if (hsp_title.equals("null"))
                    hsp_title = "";

                linearPP_pazireshData.setVisibility(View.VISIBLE);

                hsp_title = hsp_title.replace("بیمارستان", "");
                txtPP_markazName.setText("" + hsp_title);
                txtPP_doctorName.setText("دکتر " + dr_name);
                txtPP_motakhasesName.setText(spc_level_title + ": " + spc_title);
                txtPP_datePP.setText("تاریخ " + turn_date);
/*
                try {
                    byte[] decodeString = Base64.decode(dr_image, Base64.NO_WRAP);
                    InputStream inputStream = new ByteArrayInputStream(decodeString);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgPP_drPic.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
*/
                if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();

            } else {
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);
                previousPage(linearPazireshPage, linearPazireshPageBtn, linearResTimes, linearResTimesBtn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //    SearchView
    private void alertDialogShow(final String tag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.item_listview_chooser, null, false);

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
        } else if (tag.equals("PPCity")) {
            arrayList = new ArrayList(arrayListCity);
            arrayListID = new ArrayList(arrayListCityID);
        } else if (tag.equals("PPBime")) {
            arrayList = new ArrayList(arrayListBime);
            arrayListID = new ArrayList(arrayListBimeID);
        } else if (tag.equals("PPOstan")) {
            arrayList = new ArrayList(arrayListOstan);
            arrayListID = new ArrayList(arrayListOstanID);
        } else if (tag.equals("PPOrg")) {
            arrayList = new ArrayList(arrayListOrg);
            arrayListID = new ArrayList(arrayListOrgID);
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

                TextView txttitle = ((LinearLayout) view).findViewById(R.id.txtTitle);
                TextView txtId = ((LinearLayout) view).findViewById(R.id.txtId);
                String title = txttitle.getText().toString();
                String id = txtId.getText().toString();

                if (tag.equals("city")) {
                    txtFrRes_city.setText(title + "");
                    cityId = id;

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
                            }
                        });
                    }
                    //  String result = "{\"status\":\"yes\",\"message\":\"\",\"data\":{ \"hospital\":[{\"id\":\"0\",\"title\":\"تمامی بیمارستان ها\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید حسینی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید حسینی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید حسینی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید حسینی\"},{\"id\":\"1\",\"title\":\"بیمارستان عمومی ماساچوست\"}],\"doctor\":[],\"specialty\":[],\"time\":[],\"city\":[]}}";
                    //  setDropDownsData(result, "update");
                } else if (tag.equals("takhasos")) {
                    txtFrRes_takhasos.setText(title + "");
                    takhasosId = id;
                    if (id.equals("0")) {
                        if (dataTakhasos2.size() != 0) {
                            dataTakhasos2.clear();
                            dataTakhasosID2.clear();
                        }
                        dataTakhasos = new ArrayList(dataTakhasos2);
                        dataTakhasosID2 = new ArrayList(dataTakhasosID2);
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

                        new setConnectionVolley(getContext(), "http://nobat.mazums.ac.ir/TurnAppApi/Search?hsp_id=" + hospiralId + "&spc_id=" + takhasosId, object  // inja chi bayad gozasht?  takhasos va hospital
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
                    if (id.equals("0")) {
                        if (dataHospitalID2.size() != 0) {
                            dataHospital2.clear();
                            dataHospitalID2.clear();
                        }
                        dataHospital = new ArrayList(dataHospital2);
                        dataHospitalID = new ArrayList(dataTakhasosID2);
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
                } else if (tag.equals("doctor")) {
                    txtFrRes_doctor.setText(title + "");
                    doctorId = id;
                } else if (tag.equals("PPCity")) {
                    txtFrPP_city.setText(title + "");
                    cityIdSamane = id;
                } else if (tag.equals("PPOstan")) {
                    txtFrPP_ostan.setText(title + "");
                    ostanIdSamane = id;
//     TODO: set data to city in pp
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id", ostanIdSamane);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String linkpp = "http://nobat.mazums.ac.ir/TurnAppApi/turn/getCities?id=" + ostanIdSamane;
                    new setConnectionVolley(getContext(), linkpp, jsonObject).connectStringRequest(new setConnectionVolley.OnResponse() {
                        @Override
                        public void OnResponse(String response) {
                            // update city in pp
                            updateCityPP(response);
                        }
                    });

                } else if (tag.equals("PPBime")) {
                    txtFrPP_bime.setText(title + "");
                    bimeIdSamane = id;
                } else if (tag.equals("PPOrg")) {
                    txtFrPP_org.setText(title + "");
                    orgIdSamane = id;
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

    }

    private void updateCityPP(String response) {
        try {
            JSONObject object = new JSONObject(response);
            String status = object.getString("status");
            String message = object.getString("message");
            if (status.equals("yes")) {
                String data = object.getString("data"); // data! nvase chi whatsapp
                JSONObject arrayData1 = new JSONObject(data);

                JSONArray arrayData = arrayData1.getJSONArray("cities");  // chish moishkel dare
                for (int i = 0; i < arrayData.length(); i++) {
                    JSONObject objectTemp = arrayData.getJSONObject(i);
                    arrayListCity.add(objectTemp.getString("title"));
                    arrayListCityID.add(objectTemp.getString("id"));
                }
            } else
//                new ShowMessage(getContext()).ShowMessage_SnackBar(linearSelectFilters, message + "");
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void selectFilters(View view) {

//      this layout is for linearResTimes
        LinearLayout layoutBtnsRT = view.findViewById(R.id.layoutBtnsRT);
        layoutBtnsRT.setVisibility(View.GONE);


        linearSelectFilters = view.findViewById(R.id.linearSelectFilters);
        linearSelectFiltersBtn = view.findViewById(R.id.linearSelectFiltersBtn);
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
                alertDialogShow("city");
            }
        });
        linearFrRes_takhasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("takhasos");
            }
        });
        linearFrRes_darmonghah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("darmonghah");
            }
        });
        linearFrRes_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("time");
            }
        });

        linearFrRes_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("doctor");
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
//                Toast.makeText(getContext(), "اجرا شدن لودینگ 2 ثانیه", Toast.LENGTH_SHORT).show();
                alertDialogLoding.show();
                nextPage(linearSelectFilters, linearSelectFiltersBtn, linearResTimes, linearResTimesBtn);
                refreshData();
            }
        });

        dataCity = new ArrayList();
        dataTakhasos = new ArrayList();
        dataTakhasos2 = new ArrayList();
        dataHospital = new ArrayList();
        dataHospital2 = new ArrayList();
        dataTime = new ArrayList();
        dataDoctor = new ArrayList();
        dataCityID = new ArrayList();
        dataTakhasosID = new ArrayList();
        dataTakhasosID2 = new ArrayList();
        dataHospitalID = new ArrayList();
        dataHospitalID2 = new ArrayList();
        dataTimeID = new ArrayList();
        dataDoctorID = new ArrayList();

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

    }

    private void refreshData() {

        refreshRT.setRefreshing(true);

        refreshRT.setColorSchemeResources(
                R.color.colorPurple6,
                R.color.colorPink,
                R.color.colorLogo,
                R.color.colorIndigo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doSearch();
                refreshRT.setRefreshing(false);
            }
        }, 2000);

    }

    private void doSearch() {

        if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();

        pageNumber++;

        // here
        // TODO: Sent new id to link2 and change HOSPITAL NAMES --------------------------------------------------------
        //city_id:0,hsp_id:0,spc_id:0,first_name:'',last_name:'', date_period:0,page_number:0,item_per_page:10}
        // if (doctorId  0){
        //     String[] temp = txtFrRes_doctor.getText().toString().split(" ");
        //   }

        JSONObject object = new JSONObject();
        try {
            //    object.put("id", id);
            object.put("hsp_id", hospiralId + "");
            object.put("city_id", cityId + "");
            object.put("spc_id", takhasosId + "");
            object.put("first_name", txtFrRes_doctor.getText().toString() + "");
            object.put("last_name", "0");
            object.put("date_period", timeId + "");
            object.put("page_number", pageNumber);
            object.put("item_per_page", "10");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // + "&first_name=" +txtFrRes_doctor.getText().toString()+""
        alertDialogLoding.show();

        new setConnectionVolley(getContext(), "http://nobat.mazums.ac.ir/turnappApi/search/TurnList?hsp_id=" +
                hospiralId + "&city_id=" + cityId + "&spc_id=" + takhasosId + "&date_period="
                + timeId + "&page_number=" + pageNumber + "&item_per_page=" + "10", object
        ).connectStringRequest(new setConnectionVolley.OnResponse() {
            @Override
            public void OnResponse(String response) {
                if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();
                setRecycViewData(response);
            }
        });

        // TODO: Sent data and get ResTimes data (RecycleView) | link2--------------------------------------------------------
        //inja chi bayad respons bedim b setRecycViewData
        // String res = "{\"status\":\"yes\",\"message\":\"\",\"data\":[{\"dr_prg_hsp_mdc_spc_date_id\":\"1\",\"hsp_title\":\"بیمارستان شهید بهشتی\",\"shift_title\":\"صبح\",\"dr_name\":\"مهسا طاهری\",\"spc_title\":\"اطفال\",\"prg_date\":\"1399/01/06\",\"web_turn\":\"1\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"12\",\"hsp_title\":\"بیمارستان شهید حسینی\",\"shift_title\":\"عصر\",\"dr_name\":\"مهدی منصوری\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/02/07\",\"web_turn\":\"0\",\"status_type\":\"اتمام\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"13\",\"hsp_title\":\"بیمارستان شهید صالحی\",\"shift_title\":\"شب\",\"dr_name\":\"فرزاد اکبری\",\"spc_title\":\"عمومی\",\"prg_date\":\"1399/03/09\",\"web_turn\":\"5\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"14\",\"hsp_title\":\"بیمارستان شهید عابدزاده\",\"shift_title\":\"عصر\",\"dr_name\":\"علی ملکی\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/04/19\",\"web_turn\":\"3\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"15\",\"hsp_title\":\"بیمارستان  بهشتی\",\"shift_title\":\"صبح\",\"dr_name\":\"نجمه مقدم\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/09/05\",\"web_turn\":\"0\",\"status_type\":\"اتمام\"}]}";
        // setRecycViewData(res);
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
        return false;
    }

    private void defaultDataDropDown() {
        dataHospital = new ArrayList(dataHospital2);
        dataHospitalID = new ArrayList(dataHospitalID2);
        dataTakhasos2 = new ArrayList(dataTakhasos2);
        dataTakhasosID2 = new ArrayList(dataTakhasosID2);


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
    }

    private void nextPage(final LinearLayout first, final LinearLayout firstBtn,
                          final LinearLayout second, final LinearLayout secondBtn) {
// anim next - center to right (forFirstLinear) | left to center (forSecondLinear)
        Animation animCenterToRight = AnimationUtils.loadAnimation(getContext(), R.anim.center_to_right);
        final Animation animLeftToCenter = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_center);

        first.startAnimation(animCenterToRight);
        firstBtn.startAnimation(animCenterToRight);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                second.setVisibility(View.VISIBLE);
                secondBtn.setVisibility(View.VISIBLE);
                second.startAnimation(animLeftToCenter);
                secondBtn.startAnimation(animLeftToCenter);
            }
        }, 150);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                first.setVisibility(View.GONE);
                firstBtn.setVisibility(View.GONE);

            }
        }, 300);
    }

    private void previousPage(final LinearLayout first, final LinearLayout firstBtn,
                              final LinearLayout second, final LinearLayout secondBtn) {

// anim previous - center to left (First) | right to center (Second)
        final Animation animRightToCenter = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_center);
        Animation animCenterToLeft = AnimationUtils.loadAnimation(getContext(), R.anim.center_to_left);

        first.startAnimation(animCenterToLeft);
        firstBtn.startAnimation(animCenterToLeft);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                second.setVisibility(View.VISIBLE);
                secondBtn.setVisibility(View.VISIBLE);
                second.startAnimation(animRightToCenter);
                secondBtn.startAnimation(animRightToCenter);
            }
        }, 150);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                first.setVisibility(View.GONE);
                firstBtn.setVisibility(View.GONE);
            }
        }, 300);

        if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();

    }

    private void setAllDataToDefault() {

        dr_prg_hsp_mdc_spc_date_id = "";
        positionItemRecycleView = -1;

        isScrollingFP = false;
        currentItemFP = 0;
        totalItemsFP = 0;
        scrollOutItemsFP = 0;
        boolCheckNetFP = false;
        pageNumber = -1;

        cityId = "";
        takhasosId = "";
        hospiralId = "";
        timeId = "";
        doctorId = "";

        defaultDataDropDown();
    }

}