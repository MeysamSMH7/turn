package com.example.turn.Activity.Main.Fragment.FragmentReserve.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.azimolabs.maskformatter.MaskFormatter;
import com.example.turn.Activity.Main.Adapter.AdRecycPopUp;
import com.example.turn.Activity.Main.Adapter.onClickInterface;
import com.example.turn.Activity.Main.Adapter.setDataToFragment;
import com.example.turn.Activity.Main.Model.ModAlerts;
import com.example.turn.Classes.EnglishNumber;
import com.example.turn.Classes.ShowMessage;
import com.example.turn.Classes.setConnectionVolley;
import com.example.turn.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class frTabRes_RazireshPage extends Fragment implements SearchView.OnQueryTextListener {

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
//متغییر سراسری که همه جا بهش دسترسی داریم
   /* public String getHspId(){
        return hsp_pp;
    }*/

    private String bimeTitleSamane = "0";
    private String bimeIdSamane = "0";
    private String addressSamane = "0";
    private String sexSamane = "0";
    private String cityIdSamane = "0";
    private String ostanIdSamane = "0";
    private String orgIdSamane = "0";
    private String phoneNumSamane = "0";
    private String fatherNameSamane = "0";
    private String lastNameSamane = "0";
    private String firstNameSamane = "0";
    private String cityTitleSamane = "0";
    private String pp_id = "0";

    private String srv_id = "0";
    private String rcp_id = "0";
    private String codMeliOrErja = "کد ملی";
    private String ins_box_code = "0";
    private String ins_box_val = "0";
    private String dr_prg_hsp_mdc_spc_date_id = "";
    private TextView txtNoData;
    private RelativeLayout layoutMain;

    private AlertDialog alertDialogLoding;

    private AlertDialog alertDialogFilter;



    /**/
    //
    public static frTabRes_RazireshPage newInstance() {

        Bundle args = new Bundle();
        frTabRes_RazireshPage fragment = new frTabRes_RazireshPage();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_reserve_pazireshpage, container, false);

        onAttachToParentFragment(getParentFragment());
        loading();
        paziresh(view);


        return view;

    }


    public void getDataFromFragment(String message) {

        if (message.equals("false")) {
            txtNoData.setVisibility(View.VISIBLE);
            layoutMain.setVisibility(View.GONE);
        } else {

            txtNoData.setVisibility(View.GONE);
            layoutMain.setVisibility(View.VISIBLE);
            dr_prg_hsp_mdc_spc_date_id = message;
            fGetTurnINfo();
        }

    }

    private void fGetTurnINfo() {
        String[] temp = dr_prg_hsp_mdc_spc_date_id.split("_");
        JSONObject jsonObject = new JSONObject();
        //    hospiralId = temp[2];
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
        String link = "http://nobat.mazums.ac.ir/TurnAppApi/turn/TurnSpecification?dr_id=" + temp[0] + "" + "&prg_id=" + temp[1] +
                "" + "&hsp_id=" + temp[2] + "" + "&mdc_id=" + temp[3] + "" + "&spc_id=" + temp[4] + "&turn_date=" + temp[5] + "";
        new setConnectionVolley(getContext(), link, jsonObject).connectStringRequest(new setConnectionVolley.OnResponse() {
            @Override
            public void OnResponse(String response) {
                if (alertDialogLoding.isShowing())
                    alertDialogLoding.dismiss();
                setDataInPazireshPage(response);
//                      setDa-taToFragment.setDataToFragment(response, "firstToSecond");

            }
        });
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

    private void paziresh(View view) {
        try {

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
            // btnPP_paziresh.setVisibility(View.INVISIBLE);
            txtNoData = view.findViewById(R.id.txtNoData);
            layoutMain = view.findViewById(R.id.layoutMain);

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
                public void onClick(final View view) {
//                TODO: Send id , meli and erja to link3 -----------------------------------------
                    try {
                        if (arrayListBime.size() != 0) {
                            arrayListBime.clear();
                            arrayListBimeID.clear();
                        }
                        String meliOrEjra = EnglishNumber.convert(edtFrPP_Cod.getText().toString());
                        if (codMeliOrErja.equals("کد ملی")) {
                            if (meliOrEjra.length() != 10) {
                                Toast.makeText(getContext(), "کد ملی معتبر نیست", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }


                        if (codMeliOrErja.equals("کد ارجاع")) {
                            if (meliOrEjra.equals("")) {
                                Toast.makeText(getContext(), "شماره ارجاع نا معتبر است", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        JSONObject object = new JSONObject();
                        try {
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
                /*        aa
                        linearPazireshPage2.setVisibility(View.VISIBLE);
                        btnPP_paziresh.setVisibility(View.VISIBLE);*/

                        String vUrl = "";
                        if (codMeliOrErja.equals("کد ملی")) {
                            vUrl = "http://nobat.mazums.ac.ir/TurnAppApi/turn/SearchByPpId?hsp_id=" + hsp_pp + "&pp_id=" + meliOrEjra;
                        } else {
                            String[] temp = dr_prg_hsp_mdc_spc_date_id.split("_");
                            vUrl = "http://nobat.mazums.ac.ir/TurnAppApi/turn/SearchByRefId?hsp_id=" + hsp_pp + "&family_code=" + meliOrEjra + "&spc_id=" + temp[4];
                        }

                        alertDialogLoding.show();
                        new setConnectionVolley(getContext(), vUrl, object
                        ).connectStringRequest(new setConnectionVolley.OnResponse() {
                            @Override
                            public void OnResponse(String response) {
                                if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();
                                setDataFromSamane(response, view);
                            }
                        });
//                String res = "{\"status\":\"yes\",\"message\":\"\",\"data\":{\"ins_id\":\"بیمه ی تکمیلی\",\"home_adr\":\"اراک انتهای خیابان\",\"is_sex\":\"0\",\"city_id\":\"1\",\"home_mbl\":\"09386977100\",\"father_name\":\"علی\",\"last_name\":\"حسینی\",\"first_name\":\"سیدمیثم\"}}";
//                setDataFromSamane(res);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            btnPP_paziresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {


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

                        String phoneNum = EnglishNumber.convert(edtFrPP_phone.getText().toString()).replace(" ", "");

                        if (phoneNum.length() != 11) {
                            Toast.makeText(getContext(), "شماره ی موبایل نا معتبر است", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String[] phNumArr = phoneNum.split("0", 2);
                        phoneNum = phNumArr[1] + "";

                        if (txtFrPP_city.equals("0")) {
                            Toast.makeText(getContext(), "لطفا یکی از شهر ها را انتخاب کنید", Toast.LENGTH_SHORT).show();
                            return;
                        }

                  /*      if (edtFrPP_address.getText().toString()).equals("")) {
                            Toast.makeText(getContext(), "آدرس نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
                            return;
                        }*/


                        if (EnglishNumber.convert(edtFrPP_family.getText().toString()).equals("")) {
                            Toast.makeText(getContext(), "نام خانوادگی نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (EnglishNumber.convert(edtFrPP_family.getText().toString()).equals("-")) {
                            Toast.makeText(getContext(), "نام خانوادگی نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String vFamily = EnglishNumber.convert(edtFrPP_family.getText().toString()).replace(" ", "");
                        if (vFamily.length() < 3) {
                            Toast.makeText(getContext(), "نام خانوادگی نمیتواند کمتر از سه حرف باشد", Toast.LENGTH_SHORT).show();

                            return;
                        }
                        String vFamilys = safeChar(vFamily) + "";
                        if (vFamilys.length() > 0) {
                            Toast.makeText(getContext(), "نام خانوادگی نمیتواند شامل کارکتر غیر از حروف فارسی باشد.", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        if (EnglishNumber.convert(edtFrPP_name.getText().toString()).equals("")) {
                            Toast.makeText(getContext(), "نام نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        String vName = EnglishNumber.convert(edtFrPP_name.getText().toString()).replace(" ", "");
                        if (vName.length() < 3) {
                            Toast.makeText(getContext(), "نام  نمیتواند کمتر از سه حرف باشد", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String sName = safeChar(vName) + "";
                        if (sName.length() > 0) {
                            Toast.makeText(getContext(), "نام  نمیتواند شامل کارکتر غیر از حروف فارسی باشد.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String vfatherName = EnglishNumber.convert(edtFrPP_fatherName.getText().toString()) + "";
                        if (vfatherName.equals("")) {
                            Toast.makeText(getContext(), "نام پدر نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String sfatherName = safeChar(vfatherName) + "";
                        if (sfatherName.length() > 0) {
                            Toast.makeText(getContext(), "نام پدر نمیتواند شامل کارکتر غیر از حروف فارسی باشد.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                     /*   if (! EnglishNumber.convert(edtFrPP_fatherName.getText().toString()).equals("") || phoneNum.length() == 11 || ! txtFrPP_city.equals("0") ||
                       ! EnglishNumber.convert(edtFrPP_family.getText().toString()).equals("") ) {
                            btnPP_paziresh.setVisibility(View.VISIBLE);
                        }
*/


//----------- when print is ok this 3 lines will be there (after get data rom server!!!!!)
                  /*      linearLinears.setVisibility(View.GONE);
                        linearBtns.setVisibility(View.GONE);*/
                        //linearPrint.setVisibility(View.VISIBLE);

                /*{turn_date:'',prg_id:0,spc_id:0,hsp_id:0,city_id:0,srv_id:0,pp_id:'0',familiy_code:'',
                        patient:{mome_mbl:'',home_adr:'',first_name:'',last_name:'',is_sex:0,father_name:'',ins_id:0}*/


                        String vPazireshlink = "";

                        if (codMeliOrErja.equals("کد ملی")) {
                            vPazireshlink = "http://nobat.mazums.ac.ir/TurnAppApi/turn/MakeTurn?"
                                    + "prg_id=" + temp[1]
                                    + "&hsp_id=" + temp[2]
                                    + "&srv_id=" + srv_id
                                    + "&spc_id=" + temp[4]
                                    + "&dr_id=" + temp[0]
                                    + "&turn_date=" + temp[5]
                                    + "&pp_id=" + EnglishNumber.convert(edtFrPP_Cod.getText().toString()).replace(" ", "")
                                    + "&home_mbl=" + phoneNum
                                    + "&home_adr=" + EnglishNumber.convert(edtFrPP_address.getText().toString()).replace(" ", "%20")
                                    + "&last_name=" + EnglishNumber.convert(edtFrPP_family.getText().toString()).replace(" ", "%20")
                                    + "&first_name=" + EnglishNumber.convert(edtFrPP_name.getText().toString()).replace(" ", "%20")
                                    + "&is_sex=" + sexSamane
                                    + "&father_name=" + EnglishNumber.convert(edtFrPP_fatherName.getText().toString()).replace(" ", "").replace(" ", "%20")
                                    + "&ins_id=" + bimeIdSamane
                                    + "&ost_id=" + ostanIdSamane
                                    + "&org_id=" + orgIdSamane;
                        } else {
                            vPazireshlink = "http://nobat.mazums.ac.ir/TurnAppApi/turn/MakeTurn?"
                                    + "prg_id=" + temp[1]
                                    + "&dr_id=" + temp[0]
                                    + "&hsp_id=" + temp[2]
                                    + "&srv_id=" + srv_id
                                    + "&spc_id=" + temp[4]
                                    + "&turn_date=" + temp[5]
                                    + "&pp_id=" + pp_id
                                    + "&family_code" + EnglishNumber.convert(edtFrPP_Cod.getText().toString()).replace(" ", "")
                                    + "&home_mbl=" + phoneNum
                                    + "&home_adr=" + EnglishNumber.convert(edtFrPP_address.getText().toString()).replace(" ", "%20")
                                    + "&last_name=" + EnglishNumber.convert(edtFrPP_family.getText().toString()).replace(" ", "%20")
                                    + "&first_name=" + EnglishNumber.convert(edtFrPP_name.getText().toString()).replace(" ", "%20")
                                    + "&is_sex=" + sexSamane
                                    + "&father_name=" + EnglishNumber.convert(edtFrPP_fatherName.getText().toString()).replace(" ", "").replace(" ", "%20")
                                    + "&ins_id=" + bimeIdSamane
                                    + "&ost_id=" + ostanIdSamane
                                    + "&org_id=" + orgIdSamane;
                        }
                        //  alertDialogLoding.show();

                        msetDataToFragment.setDataToFragment(vPazireshlink, "thirdToFourth");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            });


            radioGPPP_CodMeli.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case R.id.radioBtnPP_CodMeli:
                            codMeliOrErja = "کد ملی";
                            edtFrPP_Cod.setHint("کد ملی");
                            break;
                        case R.id.radioBtnPP_NumErja:
                            codMeliOrErja = "کد ارجاع";
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

            cardViewFrPP_org.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogShow("PPOrg");
                }
            });

            MaskFormatter maskFormatter = new MaskFormatter("09999999999", edtFrPP_phone);
            edtFrPP_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtFrPP_phone.setHint("09113456789");
            edtFrPP_phone.addTextChangedListener(maskFormatter);

         /*   btnPP_previous = view.findViewById(R.id.btnPP_previous);
            btnPP_previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        previousPage(linearPazireshPage, linearPazireshPageBtn, linearResTimes, linearResTimesBtn);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // استعلام هویت  estelame hoviyat
    private void setDataFromSamane(String res, View view) {

        try {
            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");

            if (arrayListOstan.size() != 0)
                arrayListOstan.clear();
            if (arrayListCity.size() != 0)
                arrayListCity.clear();
            if (arrayListBime.size() != 0)
                arrayListBime.clear();
            if (arrayListOstanID.size() != 0)
                arrayListOstanID.clear();
            if (arrayListCityID.size() != 0)
                arrayListCityID.clear();
            if (arrayListBimeID.size() != 0)
                arrayListBimeID.clear();
            if (arrayListOrg.size() != 0)
                arrayListOrg.clear();
            if (arrayListOrgID.size() != 0)
                arrayListOrgID.clear();


            //dar halate code erja
            if (message.contains("عدم تطابق تخصص"))
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);
            //   if (status.equals("yes")) {
            linearPazireshPage2.setVisibility(View.VISIBLE);
            String data = object.getString("data");
            JSONObject objectData = new JSONObject(data);
            //get  patient
            JSONObject jsonPatient = objectData.getJSONObject("patient");
            firstNameSamane = jsonPatient.getString("first_name") + "";
            lastNameSamane = jsonPatient.getString("last_name") + "";
            if (firstNameSamane.equals("null") || lastNameSamane.equals("null")) {

                Snackbar.make(view, "اتصال به سرویس استحقاق درمان امکان پذیر نیست،لطفا فرم زیر را تکمیل کنید", Snackbar.LENGTH_LONG)
                        .setAction("بستن", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }


            if (!firstNameSamane.equals("null") || !lastNameSamane.equals("null")) {


                cardViewFrPP_org.setVisibility(View.GONE);
                fatherNameSamane = jsonPatient.getString("father_name") + "";
                if (fatherNameSamane.equals("null")) fatherNameSamane = "";
//                cityIdSamane = jsonPatient.getString("city_id");
//                cityTitleSamane = jsonPatient.getString("city_title");
                sexSamane = jsonPatient.getString("is_sex"); // zero = man | one = woman
                addressSamane = jsonPatient.getString("home_adr");
                phoneNumSamane = jsonPatient.getString("home_mbl");
                bimeIdSamane = jsonPatient.getString("ins_id");
                bimeTitleSamane = jsonPatient.getString("ins_title");

                if (jsonPatient.has("pp_id"))
                    pp_id = jsonPatient.getString("pp_id");

//---------------------------
/*
                if (codMeliOrErja.equals("کد ارجاع")) {
                    ins_box_code = jsonPatient.getString("ins_box_code");
                    ins_box_val = jsonPatient.getString("ins_box_val");

                    if (!ins_box_code.equals("null") || !ins_box_code.equals("")){

                        txtFrPP_bime.setText(ins_box_val);
                        txtFrPP_bime.setEnabled(false);

                    }

                }*/

                if (firstNameSamane.equals("null"))
                    firstNameSamane = "";

                if (lastNameSamane.equals("null"))
                    lastNameSamane = "";

                if (fatherNameSamane.equals("null"))
                    fatherNameSamane = "";

                if (cityTitleSamane.equals("null"))
                    cityTitleSamane = "";

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
                if (!cityTitleSamane.equals(""))
                    txtFrPP_city.setText(cityTitleSamane + "");
                edtFrPP_address.setText(addressSamane + "");
                if (!bimeTitleSamane.equals(""))
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
                if (arrayListOrg.size() != 0) {
                    arrayListOrg.clear();
                    arrayListOrgID.clear();
                }
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

            cityIdSamane = arrayListCityID.get(0) + "";
            txtFrPP_city.setText(arrayListCity.get(0) + "");

            // get bime
            JSONArray arrayBime = objectData.getJSONArray("inslist");
            for (int i = 0; i < arrayBime.length(); i++) {
                JSONObject temp = arrayBime.getJSONObject(i);
                arrayListBime.add(temp.getString("ins_title"));
                arrayListBimeID.add(temp.getString("ins_id"));
            }


            if (arrayListBime.size() != 0) {
                txtFrPP_bime.setText(arrayListBime.get(0) + "");
                bimeIdSamane = arrayListBimeID.get(0) + "";
            }

            // get ostan
            JSONArray arrayOstan = objectData.getJSONArray("ostans");
            for (int i = 0; i < arrayOstan.length(); i++) {
                JSONObject temp = arrayOstan.getJSONObject(i);
                arrayListOstan.add(temp.getString("title"));
                arrayListOstanID.add(temp.getString("id"));
            }

            txtFrPP_ostan.setText(arrayListOstan.get(0) + "");
            ostanIdSamane = arrayListOstanID.get(0) + "";

            //} else
//                new ShowMessage(getContext()).ShowMessage_SnackBar(linearSelectFilters, message + "");
            //   new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    SearchView
    private void alertDialogShow(final String tag) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.item_listview_chooser, null, false);

//voice type start
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

//voice type end


            Button btnFr = layout.findViewById(R.id.btnFr);
            btnFr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogFilter.dismiss();
                }
            });

            ArrayList arrayList = new ArrayList();
            ArrayList arrayListID = new ArrayList();

            if (tag.equals("PPCity")) {
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
                    try {

                        TextView txttitle = ((LinearLayout) view).findViewById(R.id.txtTitle);
                        TextView txtId = ((LinearLayout) view).findViewById(R.id.txtId);
                        String title = txttitle.getText().toString();
                        String id = txtId.getText().toString();
                        if (tag.equals("PPCity")) {


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
                           /* if (arrayListOrg.size() != 0) {
                                arrayListOrg.clear();
                                arrayListOrgID.clear();
                            }*/
                            txtFrPP_org.setText(title + "");
                            orgIdSamane = id;
                            // clicked on Org dropDown
                            JSONObject jsonObject = new JSONObject();
                            String linkpp = "http://nobat.mazums.ac.ir/TurnAppApi/turn/InsList?id=" + orgIdSamane + "&hspId=" + hsp_pp;
                            new setConnectionVolley(getContext(), linkpp, jsonObject).connectStringRequest(new setConnectionVolley.OnResponse() {
                                @Override
                                public void OnResponse(String response) {
                                    // update city in pp
                                    alertDialogLoding.show();
                                    updteBimePP(response);
                                    if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();
                                }
                            });
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
       /* text = text.replace("ا","آ");*/
        adRecycPopUp.filter(text);
    /*    text = text.replace("آ","ا");
        adRecycPopUp.filter(text);*/
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public static String safeChar(String input) {
        char[] allowed = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_".toCharArray();
        char[] charArray = input.toString().toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : charArray) {
            for (char a : allowed) {
                if (c == a) result.append(a);
            }
        }
        return result.toString();
    }

    private void updateCityPP(String response) {
        try {
            if (arrayListCity.size() != 0) {
                arrayListCity.clear();
                arrayListCityID.clear();
            }
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

                if (arrayListCity.size() != 0) {
                    txtFrPP_city.setText(arrayListCity.get(0) + "");
                    cityIdSamane = arrayListCityID.get(0) + "";
                } else {
                    txtFrPP_city.setText("");
                    cityIdSamane = "0";
                }

            } else
//                new ShowMessage(getContext()).ShowMessage_SnackBar(linearSelectFilters, message + "");
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updteBimePP(String response) {
        try {
            if (arrayListBime.size() != 0) {
                arrayListBime.clear();
                arrayListBimeID.clear();
            }
            JSONObject object = new JSONObject(response);
            String status = object.getString("status");
            String message = object.getString("message");
            if (status.equals("yes")) {
                String data = object.getString("data"); // data! nvase chi whatsapp
                JSONObject arrayData1 = new JSONObject(data);

                JSONArray arrayData = arrayData1.getJSONArray("inslist");  // chish moishkel dare
                for (int i = 0; i < arrayData.length(); i++) {
                    JSONObject objectTemp = arrayData.getJSONObject(i);
                    arrayListBimeID.add(objectTemp.getString("ins_id"));
                    arrayListBime.add(objectTemp.getString("ins_title"));
                }

                if (arrayListBime.size() != 0) {
                    txtFrPP_bime.setText(arrayListBime.get(0) + "");
                    bimeIdSamane = arrayListBimeID.get(0) + "";
                } else {
                    txtFrPP_bime.setText("");
                    bimeIdSamane = "0";
                }
            } else
//                new ShowMessage(getContext()).ShowMessage_SnackBar(linearSelectFilters, message + "");
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);

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

                String[] temp = dr_prg_hsp_mdc_spc_date_id.split("_");

                hsp_pp = temp[2] + "";
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

                hsp_title = hsp_title.replace("بیمارستان: ", "");
                txtPP_markazName.setText("" + hsp_title);
                txtPP_doctorName.setText("دکتر: " + dr_name);
                txtPP_motakhasesName.setText(spc_level_title + ": " + spc_title);
                txtPP_datePP.setText("تاریخ: " + turn_date);
                //  txtPP_datePP.setText("تاریخ:" + turn_date);
            /*    if (!dr_image.equals("null"))
                    if (!dr_image.equals("")) {
                        try {
                            String encodedDataString = dr_image;
                            encodedDataString = encodedDataString.replace("data:image/png;base64,", "");
                            byte[] imageAsBytes = Base64.decode(encodedDataString.getBytes(), 0);
                            imgPP_drPic.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }*/

                if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();

            } else {
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);
                //    previousPage(linearPazireshPage, linearPazireshPageBtn, linearResTimes, linearResTimesBtn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //voice type start
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
        }
    }
    //voice type end
}
