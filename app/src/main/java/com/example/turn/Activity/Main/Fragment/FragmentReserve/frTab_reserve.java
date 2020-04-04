package com.example.turn.Activity.Main.Fragment.FragmentReserve;


import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.azimolabs.maskformatter.MaskFormatter;
import com.example.turn.Activity.Main.Adapter.AdRecycPopUp;
import com.example.turn.Activity.Main.Fragment.FragmentReserve.Adapter.AdRecycResTimes;
import com.example.turn.Activity.Main.Adapter.onClickInterface;
import com.example.turn.Activity.Main.Model.ModAlerts;
import com.example.turn.Activity.Main.Fragment.FragmentReserve.Model.ModResTime;
import com.example.turn.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class frTab_reserve extends Fragment implements SearchView.OnQueryTextListener {

    //  linearSelectFilters
    private AlertDialog alertDialogFilter;
    private LinearLayout linearSelectFilters;
    private LinearLayout linearSelectFiltersBtn;

    private TextView txtFrResTitle_city;
    private TextView txtFrRes_city;
    private TextView txtFrResTitle_takhasos;
    private TextView txtFrRes_takhasos;
    private TextView txtFrResTitle_darmonghah;
    private TextView txtFrRes_darmonghah;
    private TextView txtFrResTitle_time;
    private TextView txtFrRes_time;
    private TextView txtFrResTitle_doctor;
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
    private ArrayList dataHospital;
    private ArrayList dataHospital2;
    private ArrayList dataTime;
    private ArrayList dataDoctor;

    private ArrayList dataCityID;
    private ArrayList dataTakhasosID;
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
    private AlertDialog alertDialogResTimes;
    private LinearLayout linearResTimes;
    private LinearLayout linearResTimesBtn;
    private RecyclerView rcycRT;

    private ArrayList<ModResTime> arrayListResTimes;

    private ImageView btnRT_previous;
    private String dr_prg_hsp_mdc_spc_date_id = "";
    private int positionItemRecycleView = -1;

    //------------ linearPazireshPage
    private LinearLayout linearPazireshPage;
    private LinearLayout linearPazireshPageBtn;
    private TextView txtPP_markazName;
    private TextView txtPP_doctorName;
    private TextView txtPP_motakhasesName;
    private TextView txtPP_datePP;
    private TextView txtPP_shift;
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
    private EditText edtFrPP_address;
    private LinearLayout linearFrPP_bime;
    private TextView txtFrPP_bime;

    private Button btnPP_previous;

    private String bimeSamane;
    private String addressSamane;
    private String sexSamane;
    private String cityIdSamane;
    private String phoneNumSamane;
    private String fatherNameSamane;
    private String lastNameSamane;
    private String firstNameSamane;

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
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_tab_reserve, container, false);

        selectFilters(view);
        reservationTimes(view);
        paziresh(view);

        linearSelectFilters.setVisibility(View.VISIBLE);
        linearSelectFiltersBtn.setVisibility(View.VISIBLE);

        linearResTimes.setVisibility(View.GONE);
        linearResTimesBtn.setVisibility(View.GONE);

        linearPazireshPage.setVisibility(View.GONE);
        linearPazireshPageBtn.setVisibility(View.GONE);
        linearPazireshPage2.setVisibility(View.GONE);

    //    loading();

        return view;
    }

    private void loading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.loading, null, false);

        GifImageView gifImage = layout.findViewById(R.id.gifImage);
        gifImage.setImageResource(R.drawable.loading);

        builder.setView(layout);
        alertDialogLoding = builder.create();
        alertDialogLoding.show();
    }

    private void paziresh(View view) {
        final boolean meliOrErja = false; // default is meli and its false
        linearPazireshPage = view.findViewById(R.id.linearPazireshPage);
        linearPazireshPageBtn = view.findViewById(R.id.linearPazireshPageBtn);
        txtPP_markazName = view.findViewById(R.id.txtPP_markazName);
        txtPP_doctorName = view.findViewById(R.id.txtPP_doctorName);
        txtPP_motakhasesName = view.findViewById(R.id.txtPP_motakhasesName);
        txtPP_datePP = view.findViewById(R.id.txtPP_datePP);
        txtPP_shift = view.findViewById(R.id.txtPP_shift);
        radioGPPP_CodMeli = view.findViewById(R.id.radioGPPP_CodMeli);
        edtFrPP_Cod = view.findViewById(R.id.edtFrPP_Cod);
        btnPP_search = view.findViewById(R.id.btnPP_search);

        btnPP_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "اجرا شدن لودینگ", Toast.LENGTH_SHORT).show();
                linearPazireshPage2.setVisibility(View.VISIBLE);
//                TODO: Send id , meli and erja to link3 -----------------------------------------

//                String meliOrEjra = edtFrPP_Cod.getText().toString();
//                JSONObject object = new JSONObject();
//                try {
//                    object.put("mdc_id", dr_prg_hsp_mdc_spc_date_id);
//
//                    if (meliOrErja) {
//                        object.put("pp_id", meliOrEjra);
//                        object.put("srv_id", "");
//                    } else {
//                        object.put("pp_id", "");
//                        object.put("srv_id", meliOrEjra);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                new setConnectionVolley(getContext(), "link3", object
//                ).connectStringRequest(new setConnectionVolley.OnResponse() {
//                    @Override
//                    public void OnResponse(String response) {
//                        setDataFromSamane(response);
//                    }
//                });

                String res = "{\"status\":\"yes\",\"message\":\"\",\"data\":{\"ins_id\":\"بیمه ی تکمیلی\",\"home_adr\":\"اراک انتهای خیابان\",\"is_sex\":\"0\",\"city_id\":\"1\",\"home_mbl\":\"09386977100\",\"father_name\":\"علی\",\"last_name\":\"حسینی\",\"first_name\":\"سیدمیثم\"}}";
                setDataFromSamane(res);


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
                        edtFrPP_Cod.setHint("کد ارجا");
                        break;
                }
            }
        });

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
        edtFrPP_address = view.findViewById(R.id.edtFrPP_address);
        linearFrPP_bime = view.findViewById(R.id.linearFrPP_bime);
        txtFrPP_bime = view.findViewById(R.id.txtFrPP_bime);

        MaskFormatter maskFormatter = new MaskFormatter("999 999 9999", edtFrPP_phone);
        edtFrPP_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtFrPP_phone.setHint("99 999 99999");
        edtFrPP_phone.addTextChangedListener(maskFormatter);

        btnPP_previous = view.findViewById(R.id.btnPP_previous);
        btnPP_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                previousPage(linearPazireshPage, linearPazireshPageBtn, linearResTimes, linearResTimesBtn);

            }
        });

    }

    private void setDataFromSamane(String res) {
        try {
            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");
            if (status.equals("yes")) {
                linearPazireshPage2.setVisibility(View.VISIBLE);
                String data = object.getString("data");
                JSONObject objectData = new JSONObject(data);

                bimeSamane = objectData.getString("ins_id");
                addressSamane = objectData.getString("home_adr");
                sexSamane = objectData.getString("is_sex"); // zero = man | one = woman
                cityIdSamane = objectData.getString("city_id");
                phoneNumSamane = objectData.getString("home_mbl");
                fatherNameSamane = objectData.getString("father_name");
                lastNameSamane = objectData.getString("last_name");
                firstNameSamane = objectData.getString("first_name");

                edtFrPP_name.setText(firstNameSamane + "");
                edtFrPP_name.setEnabled(false);
                edtFrPP_family.setText(lastNameSamane + "");
                edtFrPP_family.setEnabled(false);
                edtFrPP_fatherName.setText(fatherNameSamane + "");
                edtFrPP_phone.setText(phoneNumSamane + "");
                txtFrPP_city.setText(cityIdSamane + " =cityID");
                edtFrPP_address.setText(addressSamane + "");
                txtFrPP_bime.setText(bimeSamane + "");

                if (sexSamane.equals("0"))
                    radioBtnPP_male.setChecked(true);
                else if (sexSamane.equals("1"))
                    radioBtnPP_female.setChecked(true);


            } else
//                new ShowMessage(getContext()).ShowMessage_SnackBar(linearSelectFilters, message + "");
                Toast.makeText(getContext(), message + "", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void reservationTimes(View view) {
        linearResTimes = view.findViewById(R.id.linearResTimes);
        linearResTimesBtn = view.findViewById(R.id.linearResTimesBtn);
        btnRT_previous = view.findViewById(R.id.btnRT_previous);
        rcycRT = view.findViewById(R.id.rcycRT);
        arrayListResTimes = new ArrayList();

        btnRT_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousPage(linearResTimes, linearResTimesBtn, linearSelectFilters, linearSelectFiltersBtn);
            }
        });


    }

    private void setRecycViewData(String res) {
        try {
            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");
            if (status.equals("yes")) {
                String data = object.getString("data");
                JSONArray arrayData = new JSONArray(data);

                for (int i = 0; i < arrayData.length(); i++) {
                    JSONObject objectTemp = arrayData.getJSONObject(i);
                    ModResTime time = new ModResTime();
                    time.id = objectTemp.getString("dr_prg_hsp_mdc_spc_date_id");
                    time.hsp_title = objectTemp.getString("hsp_title");
                    time.shift_title = objectTemp.getString("shift_title");
                    time.dr_name = objectTemp.getString("dr_name");
                    time.spc_title = objectTemp.getString("spc_title");
                    time.prg_date = objectTemp.getString("prg_date");
                    time.web_turn = objectTemp.getString("web_turn");
                    time.status_type = objectTemp.getString("status_type");
                    arrayListResTimes.add(time);
                }

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
                                    alertDialogResTimes.dismiss();
                                }
                            });
                            btnTavafogh_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    linearPazireshPage2.setVisibility(View.GONE);
                                    dr_prg_hsp_mdc_spc_date_id = arrayListResTimes.get(position).id;
                                    positionItemRecycleView = position;
                                    nextPage(linearResTimes, linearResTimesBtn, linearPazireshPage, linearPazireshPageBtn);
                                    alertDialogResTimes.dismiss();

// set data in pazireshPage
                                    ModResTime modResTime = arrayListResTimes.get(positionItemRecycleView);

                                    String hospitalName = modResTime.hsp_title;
                                    hospitalName = hospitalName.replace("بیمارستان", "");
                                    txtPP_markazName.setText("بیمارستان " + hospitalName);
                                    txtPP_shift.setText("شیفت " + modResTime.shift_title);
                                    txtPP_doctorName.setText("دکتر " + modResTime.dr_name);
                                    txtPP_motakhasesName.setText("تخصص " + modResTime.spc_title);
                                    txtPP_datePP.setText("تاریخ " + modResTime.prg_date);

                                }
                            });

                            builder.setView(layout);
                            alertDialogResTimes = builder.create();
                            alertDialogResTimes.show();
//----- end of alert
                        } else
                            Toast.makeText(getContext(), "این نوبت به پایین رسیده است.", Toast.LENGTH_SHORT).show();

                    }
                };
                AdRecycResTimes adapterResTimes = new AdRecycResTimes(getContext(), arrayListResTimes, onclickInterface);
                rcycRT.setAdapter(adapterResTimes);

                nextPage(linearSelectFilters, linearSelectFiltersBtn, linearResTimes, linearResTimesBtn);

            } else
//                new ShowMessage(getContext()).ShowMessage_SnackBar(linearSelectFilters, message + "");
                Toast.makeText(getContext(), message + "", Toast.LENGTH_SHORT).show();

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
        }

        if (arraylistSearchView.size() != 0)
            arraylistSearchView.clear();

        for (int i = 0; i < arrayList.size(); i++) {
            ModAlerts modAlerts = new ModAlerts(arrayList.get(i) + "", arrayListID.get(i) + "");
            arraylistSearchView.add(modAlerts);
        }

        RecyclerView recycFitler = layout.findViewById(R.id.recycFitler);
        AdRecycPopUp = new AdRecycPopUp(getContext(), arraylistSearchView, new onClickInterface() {
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

//        JSONObject object = new JSONObject();
//        try {
//            object.put("id", id);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        new setConnectionVolley(getContext(), "link1", object
//        ).connectStringRequest(new setConnectionVolley.OnResponse() {
//            @Override
//            public void OnResponse(String response) {
//                setDropDownsData(response,"update");
//            }
//        });
                    }
                    String result = "{\"status\":\"yes\",\"message\":\"\",\"data\":{\"doctor\":[{\"id\":\"0\",\"title\":\"تمامی پزشکان\"},{\"id\":\"42\",\"title\":\"  -بيمارستان\"},{\"id\":\"153\",\"title\":\" سليماني نژاد-مجيد\"},{\"id\":\"33\",\"title\":\" كلهر-ابراهيم \"},{\"id\":\"18\",\"title\":\" كياكجوري-سعيد\"},{\"id\":\"100\",\"title\":\" محمدزاده-ماريا\"},{\"id\":\"42\",\"title\":\" ملائي-عباسعلي\"},{\"id\":\"39\",\"title\":\".-ماما\"},{\"id\":\"8\",\"title\":\"آذري-پريا\"},{\"id\":\"181\",\"title\":\"آرام بنياد-نوشين\"},{\"id\":\"210\",\"title\":\"آزادبخش-مونا\"},{\"id\":\"286\",\"title\":\"آقائي-حسين\"},{\"id\":\"12\",\"title\":\"آقابيگي-عليرضا\"},{\"id\":\"197\",\"title\":\"آقاجان خواه-محمد رضا\"},{\"id\":\"70\",\"title\":\"آقاجانزاده-محمد ابراهيم \"},{\"id\":\"259\",\"title\":\"آملي-حسين\"},{\"id\":\"304\",\"title\":\"آهنگري-ليلا\"},{\"id\":\"197\",\"title\":\"آهنگري-مهدي\"},{\"id\":\"100\",\"title\":\"ابراهيم نتاج-علي\"},{\"id\":\"2\",\"title\":\"ابراهيمي - مازيار\"},{\"id\":\"77\",\"title\":\"ابراهيمي پاكزاد-سيدحمزه\"},{\"id\":\"70\",\"title\":\"ابراهيمي كياسري-محمد تقي\"},{\"id\":\"4\",\"title\":\"ابراهيمي-ابراهيم\"},{\"id\":\"153\",\"title\":\"ابراهيمي-سيده خديجه\"},{\"id\":\"77\",\"title\":\"ابطحي-فرح\"},{\"id\":\"310\",\"title\":\"ابطحي-فرح\"},{\"id\":\"432\",\"title\":\"ابوطالبي -مهنوش\"},{\"id\":\"8\",\"title\":\"احتشامي-سعيد\"},{\"id\":\"4\",\"title\":\"احتشامی-سعید\"},{\"id\":\"7\",\"title\":\"احمد نژاد-سكينه\"},{\"id\":\"303\",\"title\":\"احمدزاده عالمي-سارا\"},{\"id\":\"8\",\"title\":\"احمدنژاد-شاهين\"},{\"id\":\"4\",\"title\":\"احمدي پور- فريبرز\"},{\"id\":\"304\",\"title\":\"احمدي(ايمانه )-ايمانه\"},{\"id\":\"39\",\"title\":\"احمدي-ايمانه\"},{\"id\":\"343\",\"title\":\"احمدي-محمد\"},{\"id\":\"232\",\"title\":\"احمدي-مطهره\"},{\"id\":\"69\",\"title\":\"اخلاقي-ژابيز\"},{\"id\":\"39\",\"title\":\"اخي-عذرا \"},{\"id\":\"42\",\"title\":\"ارم-شيوا\"},{\"id\":\"40\",\"title\":\"اسپهبدي-فاطمه\"},{\"id\":\"69\",\"title\":\"اسپهبدي-فاطمه\"},{\"id\":\"434\",\"title\":\"اسپهبدي-فاطمه\"},{\"id\":\"70\",\"title\":\"اسحاقي -محمد\"},{\"id\":\"4\",\"title\":\"اسدالله پور-مختار \"},{\"id\":\"243\",\"title\":\"اسدي -الميرا\"},{\"id\":\"83\",\"title\":\"اسدي پور-نسرين\"},{\"id\":\"264\",\"title\":\"اسديان-ليلا\"},{\"id\":\"23\",\"title\":\"اسدي-فاطمه\"},{\"id\":\"255\",\"title\":\"اسدي-فريبا \"},{\"id\":\"249\",\"title\":\"اسعدي-بهرام\"},{\"id\":\"23\",\"title\":\"اسفنديار-علي اصغر\"},{\"id\":\"40\",\"title\":\"اسفنديار-مائده\"},{\"id\":\"237\",\"title\":\"اسكندريون-عباسعلي \"},{\"id\":\"7\",\"title\":\"اسمائيلي آزاد-مرجان\"},{\"id\":\"33\",\"title\":\"اسماعيلي-سميه\"},{\"id\":\"8\",\"title\":\"اصغري قاجاري-معصومه\"},{\"id\":\"109\",\"title\":\"اصغري قراخيلي -محمد\"},{\"id\":\"197\",\"title\":\"اصغري-پيمان\"},{\"id\":\"288\",\"title\":\"اصغري-حسن\"},{\"id\":\"32\",\"title\":\"اصغري-ذبيح الله \"},{\"id\":\"150\",\"title\":\"اصغري-محمد مهدي\"},{\"id\":\"83\",\"title\":\"اعتماد-رجا\"},{\"id\":\"286\",\"title\":\"اعزي پاشاكلايي-گلي\"},{\"id\":\"40\",\"title\":\"اعلائي عبدالرسول-\"},{\"id\":\"434\",\"title\":\"افرادي-قاسم\"},{\"id\":\"197\",\"title\":\"افضليان اشكذري-الهام\"},{\"id\":\"7\",\"title\":\"اقبالي-نصراله\"},{\"id\":\"12\",\"title\":\"اكبرپور-مليحه\"},{\"id\":\"28\",\"title\":\"اكبرنژاد-سوده\"},{\"id\":\"8\",\"title\":\"اكبري-حسنعلي \"},{\"id\":\"293\",\"title\":\"اكبري-فرامرز\"},{\"id\":\"44\",\"title\":\"الحسيني-سلاله\"},{\"id\":\"8\",\"title\":\"الهاميان-رضا \"},{\"id\":\"49\",\"title\":\"الوندي پور-مينا \"},{\"id\":\"259\",\"title\":\"امامي قراء-الهي\"},{\"id\":\"90\",\"title\":\"امامي-مهديه\"},{\"id\":\"232\",\"title\":\"امري ساروكلائي-رضا\"},{\"id\":\"181\",\"title\":\"اميدوار-ترگل\"},{\"id\":\"303\",\"title\":\"اميري فر-سپيده\"},{\"id\":\"69\",\"title\":\"اميني-رضا\"},{\"id\":\"363\",\"title\":\"انعامي-ابوالفضل\"},{\"id\":\"4\",\"title\":\"انگورج تقوي-مسلم\"},{\"id\":\"232\",\"title\":\"اورنگ-الهه\"},{\"id\":\"110\",\"title\":\"بابا عليزاده-علي اكبر \"},{\"id\":\"44\",\"title\":\"بابا محمودي-فرهنگ\"},{\"id\":\"138\",\"title\":\"بابائي نژاد-ميثم\"},{\"id\":\"286\",\"title\":\"بابائيان كوپايي-مهستي\"},{\"id\":\"28\",\"title\":\"بابازاده-جواد\"},{\"id\":\"50\",\"title\":\"بابامحمودي -فرهنگ\"},{\"id\":\"4\",\"title\":\"باباييان-مهستي\"},{\"id\":\"423\",\"title\":\"باطبي-زهره\"},{\"id\":\"28\",\"title\":\"باقر زاده صبا-علي \"},{\"id\":\"83\",\"title\":\"باقرزاده صبا-علي\"},{\"id\":\"250\",\"title\":\"باقري -سيد وفا \"},{\"id\":\"33\",\"title\":\"باقري-اكبر\"},{\"id\":\"70\",\"title\":\"باقريان-محمد حسن \"},{\"id\":\"8\",\"title\":\"باقريان-محمد علي\"},{\"id\":\"249\",\"title\":\"باقريان-محمدعلي\"},{\"id\":\"250\",\"title\":\"باقري-بنيامين\"},{\"id\":\"39\",\"title\":\"باقري-بهزاد\"},{\"id\":\"249\",\"title\":\"باقري-بهزاد\"},{\"id\":\"369\",\"title\":\"باقري-ليلا\"},{\"id\":\"18\",\"title\":\"باكري-فاطمه\"},{\"id\":\"12\",\"title\":\"بامداديان حسين-\"},{\"id\":\"77\",\"title\":\"بامداديان-حسين\"},{\"id\":\"44\",\"title\":\"باوندي-اقاي \"},{\"id\":\"50\",\"title\":\"ببببببب-بببب\"},{\"id\":\"70\",\"title\":\"بخشي نسب-حسين \"},{\"id\":\"432\",\"title\":\"براتي-محمدصالح\"},{\"id\":\"324\",\"title\":\"بردبار-مهدي\"},{\"id\":\"292\",\"title\":\"برزگر بادله-مهدي\"},{\"id\":\"293\",\"title\":\"برزگرنژاد-ايوب \"},{\"id\":\"50\",\"title\":\"بريماني-هوشنگ\"},{\"id\":\"409\",\"title\":\"بشيري ابراهيميان-سيد مرتضي \"},{\"id\":\"150\",\"title\":\"بصيرت-علي اكبر\"},{\"id\":\"90\",\"title\":\"بنافتي-سيده رفعت\"},{\"id\":\"292\",\"title\":\"بني مصطفوي-الهام سادات\"},{\"id\":\"250\",\"title\":\"بهرامي آهنگر-محمد\"},{\"id\":\"23\",\"title\":\"بهرامي-كورش\"},{\"id\":\"453\",\"title\":\"بهشتي-علي اكبر\"},{\"id\":\"8\",\"title\":\"بورچي-عليرضا\"},{\"id\":\"50\",\"title\":\"بيگدلي-محمدرضا\"},{\"id\":\"364\",\"title\":\"بيگلرتبار-منوچهر\"},{\"id\":\"338\",\"title\":\"بيمارستان امام رضا آمل-\"},{\"id\":\"423\",\"title\":\"پارياب-ابراهيم\"},{\"id\":\"52\",\"title\":\"پاشازانوسي-محمدرضا\"},{\"id\":\"12\",\"title\":\"پاك سرشت-سعيد\"},{\"id\":\"220\",\"title\":\"پاكدل -ليلا\"},{\"id\":\"28\",\"title\":\"پرندوش-مريم\"},{\"id\":\"12\",\"title\":\"پرويزي عمران- بهنام\"},{\"id\":\"255\",\"title\":\"پرويزي عمران-بهنام \"},{\"id\":\"255\",\"title\":\"پروين -بهاره \"},{\"id\":\"237\",\"title\":\"پزشكان-پدرام\"},{\"id\":\"293\",\"title\":\"پور احمديان-ايرج\"},{\"id\":\"33\",\"title\":\"پور اصغر-مهدي\"},{\"id\":\"70\",\"title\":\"پور موسي-رستم\"},{\"id\":\"303\",\"title\":\"پوراحمديان-ايرج\"},{\"id\":\"220\",\"title\":\"پورخاني-علي\"},{\"id\":\"100\",\"title\":\"پورطباطبائي-پرويز\"},{\"id\":\"250\",\"title\":\"پورطباطبائي-پرويز\"},{\"id\":\"49\",\"title\":\"پيام-مريم\"},{\"id\":\"138\",\"title\":\"پيران-راژان\"},{\"id\":\"42\",\"title\":\"پيروزيان-شهره\"},{\"id\":\"181\",\"title\":\"پيش سرائيان-ابراهيم\"},{\"id\":\"50\",\"title\":\"پيوندي -عبدالخالق\"},{\"id\":\"197\",\"title\":\"ترابي زاده-ژيلا\"},{\"id\":\"77\",\"title\":\"تقوايي- ترنگ \"},{\"id\":\"2\",\"title\":\"تقوايي -رحمت ا...\"},{\"id\":\"181\",\"title\":\"تقوايي-ترنگ\"},{\"id\":\"83\",\"title\":\"تقوايي-رحمت اله\"},{\"id\":\"363\",\"title\":\"تقوي 2 K-سيده زينب\"},{\"id\":\"255\",\"title\":\"تقوي پور-مونا\"},{\"id\":\"255\",\"title\":\"تقوي گرمستاني-سيد مقداد \"},{\"id\":\"109\",\"title\":\"تقوي-نعمت\"},{\"id\":\"39\",\"title\":\"تقي زاده-ابراهيم\"},{\"id\":\"90\",\"title\":\"تناسان-محمد\"},{\"id\":\"210\",\"title\":\"توپا ابراهيمي-رضا\"},{\"id\":\"50\",\"title\":\"توراني-مختار\"},{\"id\":\"110\",\"title\":\"توسلي اشرفي-احمد\"},{\"id\":\"44\",\"title\":\"توسلي -ماريا\"},{\"id\":\"100\",\"title\":\"توسن-عباس\"},{\"id\":\"44\",\"title\":\"توفيقي- دكترمريم\"},{\"id\":\"260\",\"title\":\"توفيقي -محمد \"},{\"id\":\"339\",\"title\":\"توفيقي-مرجان\"},{\"id\":\"220\",\"title\":\"توكلي -حسن \"},{\"id\":\"150\",\"title\":\"توكلي-سعداله\"},{\"id\":\"382\",\"title\":\"تيمورزاده بابلي-مژگان\"},{\"id\":\"292\",\"title\":\"ثقه مجتهدي-ديانوش\"},{\"id\":\"32\",\"title\":\"جابري-امير\"},{\"id\":\"4\",\"title\":\"جاري-طاهره\"},{\"id\":\"2\",\"title\":\"جان بابايي-قاسم \"},{\"id\":\"8\",\"title\":\"جان بابايي-قاسم\"},{\"id\":\"237\",\"title\":\"جبار پور-شيرين\"},{\"id\":\"18\",\"title\":\"جبارپور-شيرين\"},{\"id\":\"294\",\"title\":\"جعفري (جراح مغز)-محمد\"},{\"id\":\"2\",\"title\":\"جعفري راد-عبدالرضا\"},{\"id\":\"100\",\"title\":\"جعفريان جلودار-حسن\"},{\"id\":\"12\",\"title\":\"جعفريان-حسن\"},{\"id\":\"363\",\"title\":\"جعفري-محمودرضا\"},{\"id\":\"109\",\"title\":\"جعفري-هاجر\"},{\"id\":\"52\",\"title\":\"جعفرييان جلودار-علي\"},{\"id\":\"303\",\"title\":\"جغتايي-حسام الدين\"},{\"id\":\"69\",\"title\":\"جليل پور-اسماعيل \"},{\"id\":\"23\",\"title\":\"جمشيدي جامخانه اي-يحيي\"},{\"id\":\"50\",\"title\":\"جمشيدي-ايوب\"},{\"id\":\"364\",\"title\":\"جمشيدي-ميثم\"},{\"id\":\"250\",\"title\":\"جنگ جو-سولماز\"},{\"id\":\"455\",\"title\":\"جهان فكر-وحيد\"},{\"id\":\"2\",\"title\":\"جهانبخش-ذبيح الله \"},{\"id\":\"339\",\"title\":\"جهانبخش-ذبيح اله\"},{\"id\":\"44\",\"title\":\"جوان آملي-مجيد\"},{\"id\":\"44\",\"title\":\"جوكار-رحمت اله \"},{\"id\":\"288\",\"title\":\"چراغ مكاني-\"},{\"id\":\"138\",\"title\":\"چگيني-نسترن\"},{\"id\":\"28\",\"title\":\"حاتم-آرزو\"},{\"id\":\"4\",\"title\":\"حاجي آبادي-مريم\"},{\"id\":\"181\",\"title\":\"حاجي پور-روجا\"},{\"id\":\"33\",\"title\":\"حاجي زاده-محبوبه \"},{\"id\":\"12\",\"title\":\"حبيبي -محمدرضا\"},{\"id\":\"453\",\"title\":\"حبيبي -ولي اله\"},{\"id\":\"397\",\"title\":\"حبيبيان-نرگس \"},{\"id\":\"12\",\"title\":\"حجازي-ابراهيم\"},{\"id\":\"8\",\"title\":\"حجتي -مهدي \"},{\"id\":\"33\",\"title\":\"حجتي(مهدي)-مهدي\"},{\"id\":\"69\",\"title\":\"حدادی-کاوه\"},{\"id\":\"52\",\"title\":\"حسامي رستمي-جعفر\"},{\"id\":\"50\",\"title\":\"حسامي رستمي-محمد حسين \"},{\"id\":\"42\",\"title\":\"حسن زاده -زهرا\"},{\"id\":\"7\",\"title\":\"حسن زاده-هما\"},{\"id\":\"7\",\"title\":\"حسن نتاج-مجد\"},{\"id\":\"83\",\"title\":\"حسين اشرفي-سيد كاميار\"},{\"id\":\"32\",\"title\":\"حسين پور بنافتي-ايران\"},{\"id\":\"150\",\"title\":\"حسين پور-ميترا\"},{\"id\":\"109\",\"title\":\"حسين جان زاده-دكتر\"},{\"id\":\"33\",\"title\":\"حسيني -بهجت السادات \"},{\"id\":\"138\",\"title\":\"حسيني نژاد -سيدجواد\"},{\"id\":\"8\",\"title\":\"حسينيان-بي بي اعظم \"},{\"id\":\"281\",\"title\":\"حسينيان-محمدعلي \"},{\"id\":\"7\",\"title\":\"حسيني-وحيد\"},{\"id\":\"49\",\"title\":\"حسینی-وحید\"},{\"id\":\"210\",\"title\":\"حق بين-سوسن\"},{\"id\":\"153\",\"title\":\"حق پناه اسكي-مستعان\"},{\"id\":\"7\",\"title\":\"حق شناس -حسن\"},{\"id\":\"18\",\"title\":\"حق نظر-هدي\"},{\"id\":\"90\",\"title\":\"حقوقي فرد-كاميار\"},{\"id\":\"12\",\"title\":\"حقي- ناهيد\"},{\"id\":\"7\",\"title\":\"حكيمي-سيد حامد\"},{\"id\":\"18\",\"title\":\"حمزه رباطي-منصوره\"},{\"id\":\"452\",\"title\":\"حيدريان-سميرا\"},{\"id\":\"2\",\"title\":\"حيدري-محمد اسمائيل\"},{\"id\":\"243\",\"title\":\"خادميان-مجتبي\"},{\"id\":\"23\",\"title\":\"خان احمدي-عباس\"},{\"id\":\"138\",\"title\":\"خبازيان-مهديه\"},{\"id\":\"18\",\"title\":\"خدادادي -خداداد\"},{\"id\":\"138\",\"title\":\"خداد-تبسم\"},{\"id\":\"281\",\"title\":\"خرازي زاده-مريم\"},{\"id\":\"324\",\"title\":\"خرم-نصيبه\"},{\"id\":\"28\",\"title\":\"خسروداد -حسن \"},{\"id\":\"294\",\"title\":\"خسروي -نويد\"},{\"id\":\"159\",\"title\":\"خسروي نويد (متادن )-\"},{\"id\":\"150\",\"title\":\"خلج سياوش-\"},{\"id\":\"23\",\"title\":\"خلعتبري-سيمين\"},{\"id\":\"49\",\"title\":\"خليلي-زينب\"},{\"id\":\"32\",\"title\":\"خليلي-محمدجعفر\"},{\"id\":\"33\",\"title\":\"خميري ثاني-فاطمه\"},{\"id\":\"40\",\"title\":\"خواجه حسيني-دكترزهرا\"},{\"id\":\"28\",\"title\":\"خواجه نوري-\"},{\"id\":\"39\",\"title\":\"خواجوند -حسين \"},{\"id\":\"181\",\"title\":\"خواجوند -حسين \"},{\"id\":\"33\",\"title\":\"خوزان-محبوبه\"},{\"id\":\"255\",\"title\":\"خوش نظر شورابسري-علي\"},{\"id\":\"409\",\"title\":\"خوشكام -مائده \"},{\"id\":\"32\",\"title\":\"خيالي فرد-امل \"},{\"id\":\"255\",\"title\":\"دادگر-جاويد\"},{\"id\":\"52\",\"title\":\"دارائي-مونا\"},{\"id\":\"197\",\"title\":\"دارابي-نازنين\"},{\"id\":\"210\",\"title\":\"دانش پور-سيد مهدي\"},{\"id\":\"52\",\"title\":\"دانشگر-عباس\"},{\"id\":\"210\",\"title\":\"داودي-مهدي\"},{\"id\":\"153\",\"title\":\"دباغ زاده سوركي-عباس\"},{\"id\":\"281\",\"title\":\"درويش زاده-اكرم\"},{\"id\":\"42\",\"title\":\"درويشي-فرشته\"},{\"id\":\"4\",\"title\":\"دكتر--\"},{\"id\":\"83\",\"title\":\"دلدار-عبدالرضا\"},{\"id\":\"83\",\"title\":\"دليري كاديجاني-آذين\"},{\"id\":\"7\",\"title\":\"دهپوري-فاطمه\"},{\"id\":\"304\",\"title\":\"دهدار چلمردي-نبي اله \"},{\"id\":\"18\",\"title\":\"دهدار-نبي اله\"},{\"id\":\"33\",\"title\":\"دهشيري-محمدرضا \"},{\"id\":\"338\",\"title\":\"دهقان-سيدحامد\"},{\"id\":\"153\",\"title\":\"دهقانكار-سيده زهرا\"},{\"id\":\"197\",\"title\":\"دهقان-وحيد\"},{\"id\":\"243\",\"title\":\"دهمرده-سارا\"},{\"id\":\"343\",\"title\":\"دوانلو(راديولوژيست)-اميرحسين \"},{\"id\":\"8\",\"title\":\"ديانتي-ام السليمه\"},{\"id\":\"39\",\"title\":\"ديانتي-فاطيما\"},{\"id\":\"32\",\"title\":\"ذاكري دانا-مائده\"},{\"id\":\"150\",\"title\":\"ذبيحي-علي \"},{\"id\":\"243\",\"title\":\"ذكريايي -ذكريا\"},{\"id\":\"110\",\"title\":\"رئيس محمدي-ليلا\"},{\"id\":\"181\",\"title\":\"رادپور-علي رضا\"},{\"id\":\"52\",\"title\":\"رازي-حبيبه\"},{\"id\":\"33\",\"title\":\"راعي-ربابه\"},{\"id\":\"49\",\"title\":\"ربيعي-بهمن\"},{\"id\":\"243\",\"title\":\"ربيعي-ندا\"},{\"id\":\"4\",\"title\":\"رجائي-فاطمه\"},{\"id\":\"138\",\"title\":\"رجب زاده-پويا\"},{\"id\":\"159\",\"title\":\"رحماني بوئيني-نسرين\"},{\"id\":\"40\",\"title\":\"رحمانيان-عبدالرحمن \"},{\"id\":\"220\",\"title\":\"رحيمي النگي-توحيد\"},{\"id\":\"343\",\"title\":\"رحيمي -بهداد\"},{\"id\":\"33\",\"title\":\"رزيدنت -\"},{\"id\":\"220\",\"title\":\"رستگارتبار-حجت الله\"},{\"id\":\"33\",\"title\":\"رستمي  -مرواريد\"},{\"id\":\"423\",\"title\":\"رستميان-نرگس\"},{\"id\":\"243\",\"title\":\"رسولي لورمايي -احمد رضا\"},{\"id\":\"259\",\"title\":\"رسولي-مير قربان\"},{\"id\":\"83\",\"title\":\"رشيدي-الهه\"},{\"id\":\"42\",\"title\":\"رشيدي-حميد\"},{\"id\":\"39\",\"title\":\"رشيدي-فاطمه\"},{\"id\":\"153\",\"title\":\"رضائي-سامان \"},{\"id\":\"33\",\"title\":\"رضائي-محمدمهدي \"},{\"id\":\"286\",\"title\":\"رضايي شهري-ايرج\"},{\"id\":\"40\",\"title\":\"رضايي كلانتري-زهرا\"},{\"id\":\"325\",\"title\":\"رضايي مهر(زرديس )-زرديس \"},{\"id\":\"42\",\"title\":\"رضاييان-رمضانعلي\"},{\"id\":\"264\",\"title\":\"رضايي-حمزه\"},{\"id\":\"4\",\"title\":\"رضايي-علي\"},{\"id\":\"23\",\"title\":\"رضايي-فاطمه \"},{\"id\":\"181\",\"title\":\"رضايي-محمد رضا\"},{\"id\":\"23\",\"title\":\"ركان قزويني-آرمان\"},{\"id\":\"255\",\"title\":\"رمضان پورگريودهي-سوده\"},{\"id\":\"220\",\"title\":\"رمضانپور-مليكا\"},{\"id\":\"33\",\"title\":\"رمضاني -فاطمه\"},{\"id\":\"255\",\"title\":\"رمضاني قراخيلي-شقايق\"},{\"id\":\"181\",\"title\":\"رمضاني لهمالي-داريوش\"},{\"id\":\"324\",\"title\":\"رمضاني-محبوبه\"},{\"id\":\"2\",\"title\":\"روحاني نژاد-حسين\"},{\"id\":\"249\",\"title\":\"روحاني-مرتضي\"},{\"id\":\"28\",\"title\":\"روحاني-وحيد رضا\"},{\"id\":\"77\",\"title\":\"رياحي-حكيمه \"},{\"id\":\"423\",\"title\":\"زابلي-احسان\"},{\"id\":\"159\",\"title\":\"زارعي-علي\"},{\"id\":\"42\",\"title\":\"زاهدي-ناصر\"},{\"id\":\"50\",\"title\":\"زرواني-اشرف \"},{\"id\":\"369\",\"title\":\"زرين خامه-پروانه\"},{\"id\":\"264\",\"title\":\"زمانفر-دانيل \"},{\"id\":\"259\",\"title\":\"زمانيان-حسين\"},{\"id\":\"18\",\"title\":\"زماني-حسن\"},{\"id\":\"294\",\"title\":\"زماني-سيد حسين\"},{\"id\":\"28\",\"title\":\"زنجاني-رويا\"},{\"id\":\"264\",\"title\":\"زنده دل-سجاد\"},{\"id\":\"325\",\"title\":\"زواري-حسن\"},{\"id\":\"249\",\"title\":\"ساروي پور -سيدضياء الدين\"},{\"id\":\"250\",\"title\":\"سازگار-محمد\"},{\"id\":\"39\",\"title\":\"سالاريان-بيژن\"},{\"id\":\"40\",\"title\":\"سالاريان-عبدالعظيم\"},{\"id\":\"42\",\"title\":\"سالاريان-كيانوش\"},{\"id\":\"259\",\"title\":\"سام دليري-ارزو\"},{\"id\":\"8\",\"title\":\"سبحاني پيام-\"},{\"id\":\"49\",\"title\":\"سبحاني-مصطفي\"},{\"id\":\"138\",\"title\":\"ستاره-جواد\"},{\"id\":\"288\",\"title\":\"سجادي ساروي-مجيد\"},{\"id\":\"220\",\"title\":\"سفيدگرنيا اميري-مريم\"},{\"id\":\"264\",\"title\":\"سلطان تويه-محمد\"},{\"id\":\"260\",\"title\":\"سلطاني-امير\"},{\"id\":\"12\",\"title\":\"سليمان پور-عبدالله\"},{\"id\":\"138\",\"title\":\"سليمان نژاد-مجيد\"},{\"id\":\"159\",\"title\":\"سليمان نژاد-مصطفي\"},{\"id\":\"2\",\"title\":\"سليمان نژاد-مهدي\"},{\"id\":\"12\",\"title\":\"سليمي-شيما\"},{\"id\":\"220\",\"title\":\"سنگ شكن--\"},{\"id\":\"181\",\"title\":\"سنگسري-افشين\"},{\"id\":\"110\",\"title\":\"سهراب-مهرنوش\"},{\"id\":\"397\",\"title\":\"سوادكوهي -فرزاد\"},{\"id\":\"44\",\"title\":\"سوركي-محمد باقر\"},{\"id\":\"50\",\"title\":\"سيد اشرفي-مقداد\"},{\"id\":\"40\",\"title\":\"سيدي-رويا\"},{\"id\":\"2\",\"title\":\"سيف الله پور-زهرا\"},{\"id\":\"109\",\"title\":\"سيفي -آزاده \"},{\"id\":\"49\",\"title\":\"سيفي پاشا-مهدي\"},{\"id\":\"343\",\"title\":\"سينا-مريم\"},{\"id\":\"90\",\"title\":\"شارع پور-ماريا\"},{\"id\":\"197\",\"title\":\"شاه حسيني-رزا\"},{\"id\":\"23\",\"title\":\"شاهاني-محمد\"},{\"id\":\"250\",\"title\":\"شاهپوري-پويا\"},{\"id\":\"7\",\"title\":\"شاهسواراني-محمد\"},{\"id\":\"286\",\"title\":\"شايسته اذر-مسعود\"},{\"id\":\"310\",\"title\":\"شايسته-رضا\"},{\"id\":\"110\",\"title\":\"شجاعي-ليلا\"},{\"id\":\"232\",\"title\":\"شريعتي-سكينه \"},{\"id\":\"32\",\"title\":\"شريعتي-نوشين\"},{\"id\":\"69\",\"title\":\"شريف-حسينعلي\"},{\"id\":\"237\",\"title\":\"شريفي رضوي-آتنا\"},{\"id\":\"28\",\"title\":\"شريفي سوركي-ولي اله\"},{\"id\":\"150\",\"title\":\"شفاعت (روانپزشک اطفال)-عارفه \"},{\"id\":\"32\",\"title\":\"شفتي-ويدا\"},{\"id\":\"325\",\"title\":\"شفيعي (سجاد ) -سجاد \"},{\"id\":\"310\",\"title\":\"شفيعي-سجاد\"},{\"id\":\"52\",\"title\":\"شفيعي-مريم \"},{\"id\":\"28\",\"title\":\"شكرريز فومني-رامين\"},{\"id\":\"49\",\"title\":\"شكري-محمد\"},{\"id\":\"397\",\"title\":\"شمس اميري -روزبه\"},{\"id\":\"7\",\"title\":\"شمسايي-مرتضي\"},{\"id\":\"28\",\"title\":\"شهاب الديني-مهدي\"},{\"id\":\"310\",\"title\":\"شهبازنژاد -ليلا\"},{\"id\":\"100\",\"title\":\"شهدا-بيمارستان\"},{\"id\":\"23\",\"title\":\"شيخ رضايي-مجيد رضا\"},{\"id\":\"153\",\"title\":\"شيخ مونسي-فاطمه\"},{\"id\":\"441\",\"title\":\"شيدايي-سميه\"},{\"id\":\"232\",\"title\":\"شيرآشتياني-حميدرضا\"},{\"id\":\"50\",\"title\":\"شيرزاد-ماشاءالله\"},{\"id\":\"292\",\"title\":\"شيرود بخشي-خاطره\"},{\"id\":\"52\",\"title\":\"صابري فر-مرتضي\"},{\"id\":\"110\",\"title\":\"صادقي نيك-مولود\"},{\"id\":\"382\",\"title\":\"صادقي-تكتم\"},{\"id\":\"109\",\"title\":\"صادقي-سپيده\"},{\"id\":\"33\",\"title\":\"صادقي-فريبا\"},{\"id\":\"150\",\"title\":\"صادقي-كيميا\"},{\"id\":\"7\",\"title\":\"صادقي-نرگس\"},{\"id\":\"42\",\"title\":\"صارمي-اردشير\"},{\"id\":\"220\",\"title\":\"صالحي كهريزسنگي-فاطمه\"},{\"id\":\"288\",\"title\":\"صالحي-سپيده\"},{\"id\":\"249\",\"title\":\"صالحي-مهدي\"},{\"id\":\"50\",\"title\":\"صحفي-سيده مريم\"},{\"id\":\"4\",\"title\":\"صدقي -پروين\"},{\"id\":\"304\",\"title\":\"صدقيان-احمد\"},{\"id\":\"364\",\"title\":\"صفري-فرشاد\"},{\"id\":\"109\",\"title\":\"صمدي نسب-احمد \"},{\"id\":\"181\",\"title\":\"صياديان سپرغان-داود\"},{\"id\":\"197\",\"title\":\"صيرفي-آذر\"},{\"id\":\"159\",\"title\":\"ضرغامي-مهران\"},{\"id\":\"220\",\"title\":\"ضيا،بخش-حسين\"},{\"id\":\"52\",\"title\":\"طالبي لياسي-مريم\"},{\"id\":\"69\",\"title\":\"طالبي نسامي-معصومه\"},{\"id\":\"12\",\"title\":\"طاهري-محسن\"},{\"id\":\"109\",\"title\":\"طاهري-مريم\"},{\"id\":\"153\",\"title\":\"طباطبايي پور-مريم السادات\"},{\"id\":\"32\",\"title\":\"طراوتي-آيسان\"},{\"id\":\"32\",\"title\":\"طوير سياري-عبدالعلي \"},{\"id\":\"220\",\"title\":\"طيبي -شهنام \"},{\"id\":\"292\",\"title\":\"طيبي-خديجه\"},{\"id\":\"33\",\"title\":\"طيبي-شهنام \"},{\"id\":\"12\",\"title\":\"طيبي-عاطفه\"},{\"id\":\"441\",\"title\":\"طيبي-گويا \"},{\"id\":\"2\",\"title\":\"ظفرمند-رسول\"},{\"id\":\"90\",\"title\":\"عابد فيروزجاه-ام البنين\"},{\"id\":\"432\",\"title\":\"عابدي ارزفوني-سيده فاطمه\"},{\"id\":\"42\",\"title\":\"عابدي سما گوش-محمد\"},{\"id\":\"259\",\"title\":\"عابدي فيروزجايي-سكينه\"},{\"id\":\"453\",\"title\":\"عابدي نژاد-شيدا\"},{\"id\":\"42\",\"title\":\"عابدي ولوكلايي-سيده خديجه \"},{\"id\":\"4\",\"title\":\"عابديني-عليرضا\"},{\"id\":\"77\",\"title\":\"عادلاني-بهرام \"},{\"id\":\"2\",\"title\":\"عارفي-صبورا\"},{\"id\":\"40\",\"title\":\"عاشقي-ابراهيم\"},{\"id\":\"18\",\"title\":\"عاشورزاده-بهرنگ\"},{\"id\":\"150\",\"title\":\"عاشوري زاده -بهرنگ \"},{\"id\":\"90\",\"title\":\"عالمي-ثريا\"},{\"id\":\"52\",\"title\":\"عاليان -شهريار\"},{\"id\":\"232\",\"title\":\"عاليزاد-مليكه\"},{\"id\":\"7\",\"title\":\"عاليشاه-ناهيد\"},{\"id\":\"293\",\"title\":\"عامريان-محمد\"},{\"id\":\"50\",\"title\":\"عباس پور-شميلا\"},{\"id\":\"243\",\"title\":\"عباس زاده مرزبالي-نرجس\"},{\"id\":\"260\",\"title\":\"عباس زاده-مينا\"},{\"id\":\"39\",\"title\":\"عباسخاني دوانلو-علي\"},{\"id\":\"150\",\"title\":\"عباسيان-ستاره\"},{\"id\":\"325\",\"title\":\"عباسي-حامد\"},{\"id\":\"243\",\"title\":\"عباسي-عادله\"},{\"id\":\"77\",\"title\":\"عبدا...پور-نگار\"},{\"id\":\"32\",\"title\":\"عبدالهي- سيد محمد\"},{\"id\":\"260\",\"title\":\"عبدالهي-مرضيه\"},{\"id\":\"237\",\"title\":\"عبدي-ليلا\"},{\"id\":\"150\",\"title\":\"عرب تقوايي-ترنگ\"},{\"id\":\"363\",\"title\":\"عرب زاده -آيدين\"},{\"id\":\"210\",\"title\":\"عرفاني-سپيدخت\"},{\"id\":\"7\",\"title\":\"عزت پناه-يزدان\"},{\"id\":\"292\",\"title\":\"عزيزي-پريا\"},{\"id\":\"159\",\"title\":\"عزيزي-محمد رضا\"},{\"id\":\"220\",\"title\":\"عسگري فيروز جايي-رشيد\"},{\"id\":\"70\",\"title\":\"عسگري فيروزجائي رشيد-\"},{\"id\":\"286\",\"title\":\"عصائيان-حميد رضا\"},{\"id\":\"181\",\"title\":\"عصائيان-فروغ\"},{\"id\":\"39\",\"title\":\"عظيمي -سميرا\"},{\"id\":\"39\",\"title\":\"عظيمي -مسعود\"},{\"id\":\"197\",\"title\":\"علم-عليرضا\"},{\"id\":\"409\",\"title\":\"علوي-فاطمه \"},{\"id\":\"40\",\"title\":\"علي نژاد-مرضيه\"},{\"id\":\"40\",\"title\":\"عليپور-عبدالرضا\"},{\"id\":\"250\",\"title\":\"عليپور-مهناز\"},{\"id\":\"153\",\"title\":\"عليرضا داودي بدابي-\"},{\"id\":\"28\",\"title\":\"عليزاده فروتن-مصطفي\"},{\"id\":\"255\",\"title\":\"عليزاده-ميترا\"},{\"id\":\"338\",\"title\":\"عليمحمدي-سيد ميثم\"},{\"id\":\"70\",\"title\":\"عليمرادي -امير ضيا الدين \"},{\"id\":\"40\",\"title\":\"علیالی-مسعود\"},{\"id\":\"249\",\"title\":\"عمادي-احمد\"},{\"id\":\"2\",\"title\":\"عمادي-سيد جمشيد\"},{\"id\":\"100\",\"title\":\"عمادي-سيده نرگس\"},{\"id\":\"2\",\"title\":\"عمراني-الهام\"},{\"id\":\"109\",\"title\":\"عموزاد-محسن\"},{\"id\":\"23\",\"title\":\"عموزاده عمران-مريم\"},{\"id\":\"110\",\"title\":\"عمويي خانعباسي-مهرناز\"},{\"id\":\"441\",\"title\":\"عنايتي-معصومه\"},{\"id\":\"294\",\"title\":\"غريبي-ارد\"},{\"id\":\"434\",\"title\":\"غزائيان-منيره\"},{\"id\":\"42\",\"title\":\"غفاري همداني-سيد محمد مهدي\"},{\"id\":\"69\",\"title\":\"غفاري-رحمان\"},{\"id\":\"232\",\"title\":\"غفور -ايده \"},{\"id\":\"250\",\"title\":\"غلام قاسمي-اميد\"},{\"id\":\"44\",\"title\":\"غياثي-علينقي\"},{\"id\":\"339\",\"title\":\"غياثي-غلام\"},{\"id\":\"197\",\"title\":\"فاتحي-زهرا\"},{\"id\":\"255\",\"title\":\"فاخري-فاطمه\"},{\"id\":\"159\",\"title\":\"فاطمي-سيد كمال الدين\"},{\"id\":\"281\",\"title\":\"فاني-حبيب الله\"},{\"id\":\"28\",\"title\":\"فتاحيان-عليرضا\"},{\"id\":\"52\",\"title\":\"فتح سامي -شاهرخ \"},{\"id\":\"338\",\"title\":\"فتحي-حسين\"},{\"id\":\"441\",\"title\":\"فتحي-مهران\"},{\"id\":\"260\",\"title\":\"فدوي-بهروز\"},{\"id\":\"49\",\"title\":\"فراهاني-فرزانه\"},{\"id\":\"100\",\"title\":\"فرخ زاد-عاصفه\"},{\"id\":\"18\",\"title\":\"فرزاد فر-\"},{\"id\":\"255\",\"title\":\"فرزادفر-اسماعيل\"},{\"id\":\"42\",\"title\":\"فرزاميان -مهدي \"},{\"id\":\"409\",\"title\":\"فرسويان-حسين\"},{\"id\":\"237\",\"title\":\"فرمانبر-محمد علي\"},{\"id\":\"304\",\"title\":\"فرمانبر-محمدعلي \"},{\"id\":\"197\",\"title\":\"فرنیا(روانپزشک اطفال)-سمانه \"},{\"id\":\"23\",\"title\":\"فرهادي-رويا\"},{\"id\":\"232\",\"title\":\"فرهمند-حميدرضا\"},{\"id\":\"197\",\"title\":\"فرهنگي -فرنوش\"},{\"id\":\"49\",\"title\":\"فرهنگي-كريم\"},{\"id\":\"232\",\"title\":\"فشاركي زاده-\"},{\"id\":\"432\",\"title\":\"فضلعلي-بهمن\"},{\"id\":\"339\",\"title\":\"فغاني ماكراني-نفيسه\"},{\"id\":\"181\",\"title\":\"فقاني -مسلم\"},{\"id\":\"259\",\"title\":\"فقيهي نيا-محسن\"},{\"id\":\"100\",\"title\":\"فلاح پور-عليرضا\"},{\"id\":\"232\",\"title\":\"فلاح تلوكي -گلناز\"},{\"id\":\"52\",\"title\":\"فلاح-ابوالقاسم\"},{\"id\":\"12\",\"title\":\"فلاح-كريم\"},{\"id\":\"286\",\"title\":\"فيض زاده كريق-بهزاد\"},{\"id\":\"110\",\"title\":\"قائميان -علي\"},{\"id\":\"259\",\"title\":\"قائمي-حميد رضا\"},{\"id\":\"249\",\"title\":\"قائمي-فرزانه\"},{\"id\":\"49\",\"title\":\"قادي-محمدرضا\"},{\"id\":\"237\",\"title\":\"قاسمپوري -هيوا\"},{\"id\":\"452\",\"title\":\"قاسمي تيرتاشي-مرجان\"},{\"id\":\"32\",\"title\":\"قاسمي -حمزه \"},{\"id\":\"260\",\"title\":\"قاسمي خاني-ناهيد\"},{\"id\":\"23\",\"title\":\"قاسمي نژاد اباذر-\"},{\"id\":\"52\",\"title\":\"قاسميان-رويا\"},{\"id\":\"338\",\"title\":\"قاسميان-رويا\"},{\"id\":\"455\",\"title\":\"قاهري-سيدعباس\"},{\"id\":\"2\",\"title\":\"قديرنژاد-سيد ناصر\"},{\"id\":\"44\",\"title\":\"قديري -علي \"},{\"id\":\"69\",\"title\":\"قربان تبار عمراني-محسن\"},{\"id\":\"453\",\"title\":\"قربان زاده-بهروز\"},{\"id\":\"281\",\"title\":\"قربان زاده-سارا\"},{\"id\":\"39\",\"title\":\"قربانپور-عباس\"},{\"id\":\"69\",\"title\":\"قرباني پاشاكلائي-مريم\"},{\"id\":\"138\",\"title\":\"قرباني نژاد-الهام \"},{\"id\":\"70\",\"title\":\"قرباني-محمد\"},{\"id\":\"181\",\"title\":\"قرباني-نفيسه \"},{\"id\":\"138\",\"title\":\"قره داغي-بابك\"},{\"id\":\"281\",\"title\":\"قريشي -سيدمحمود\"},{\"id\":\"77\",\"title\":\"قريشي-سيد محمود\"},{\"id\":\"363\",\"title\":\"قريشي-محمود\"},{\"id\":\"49\",\"title\":\"قشلاقي -پرند \"},{\"id\":\"100\",\"title\":\"قشلاقي-پرند\"},{\"id\":\"2\",\"title\":\"قلعه سري-قربانعلي\"},{\"id\":\"237\",\"title\":\"قلي پور-الهام\"},{\"id\":\"237\",\"title\":\"قلي زاده سرستي-آزاده\"},{\"id\":\"49\",\"title\":\"قلي زاده-بهروز\"},{\"id\":\"288\",\"title\":\"قلي زاده-شهاب\"},{\"id\":\"243\",\"title\":\"قليزاده پاشا-عبدالحميد\"},{\"id\":\"260\",\"title\":\"قناعت-محبوبه\"},{\"id\":\"12\",\"title\":\"قنبر پور شياده-\"},{\"id\":\"52\",\"title\":\"قهرماني-فاضله \"},{\"id\":\"18\",\"title\":\"قوامي پور-محمدعلي\"},{\"id\":\"232\",\"title\":\"كائيدي مجد -شقايق\"},{\"id\":\"12\",\"title\":\"كاشي-زهرا\"},{\"id\":\"210\",\"title\":\"كاظمي ويسري-ارش\"},{\"id\":\"281\",\"title\":\"كاظمي-رحمت اله\"},{\"id\":\"39\",\"title\":\"كاظمي-عباس\"},{\"id\":\"50\",\"title\":\"كاكوئي-محمد زمان\"},{\"id\":\"237\",\"title\":\"كامراني-معصومه\"},{\"id\":\"293\",\"title\":\"كبيري نسب-مطهره \"},{\"id\":\"42\",\"title\":\"كثيري -عبدالمجيد\"},{\"id\":\"39\",\"title\":\"كربلائي زاده-ماريا\"},{\"id\":\"260\",\"title\":\"كرجاليان چايجاني-مريم\"},{\"id\":\"159\",\"title\":\"كرد جزي-محمدمهدي\"},{\"id\":\"44\",\"title\":\"كرمي -حسن\"},{\"id\":\"8\",\"title\":\"كرمي-حسن\"},{\"id\":\"294\",\"title\":\"كريمي (اطفال)-محمد\"},{\"id\":\"23\",\"title\":\"كريمي علي آبادي-پرستو\"},{\"id\":\"52\",\"title\":\"كريمي-كسري\"},{\"id\":\"210\",\"title\":\"كريمي-محمد\"},{\"id\":\"249\",\"title\":\"كسائي -فاطمه\"},{\"id\":\"42\",\"title\":\"كسائي-حامد \"},{\"id\":\"324\",\"title\":\"كشاورز-محسن\"},{\"id\":\"50\",\"title\":\"كشاورزيان-مژگان\"},{\"id\":\"2\",\"title\":\"كشيري-محمد\"},{\"id\":\"77\",\"title\":\"كلارستاقي-منيژه \"},{\"id\":\"28\",\"title\":\"كمالي-علي \"},{\"id\":\"232\",\"title\":\"كوثريان -مهرنوش \"},{\"id\":\"250\",\"title\":\"كوثريان-سيداحمد\"},{\"id\":\"264\",\"title\":\"كوچكي-نفيسه\"},{\"id\":\"259\",\"title\":\"كوزه گر- ندا\"},{\"id\":\"100\",\"title\":\"كياني-عزت\"},{\"id\":\"8\",\"title\":\"كياني-موسي\"},{\"id\":\"39\",\"title\":\"كيوان شاد-سميرا \"},{\"id\":\"343\",\"title\":\"گدازنده-\"},{\"id\":\"39\",\"title\":\"گدازنده-غلامعلي\"},{\"id\":\"260\",\"title\":\"گدازنده-فرناز\"},{\"id\":\"232\",\"title\":\"گرجي-رضا\"},{\"id\":\"18\",\"title\":\"گرشاسب زاده-جلال\"},{\"id\":\"90\",\"title\":\"گل عليزاده-عزت اله\"},{\"id\":\"249\",\"title\":\"گلبابايي-عليرضا\"},{\"id\":\"220\",\"title\":\"گلچين جو پشتي-مريم\"},{\"id\":\"69\",\"title\":\"گلشني -صمد\"},{\"id\":\"18\",\"title\":\"گلين مقدم-نجمه\"},{\"id\":\"243\",\"title\":\"گودرزيان-مائده\"},{\"id\":\"4\",\"title\":\"گودرزيان-نصرت اله\"},{\"id\":\"264\",\"title\":\"گوران-شكراله\"},{\"id\":\"181\",\"title\":\"لشتوآقايي-بيتا\"},{\"id\":\"28\",\"title\":\"لطيفي -محمدحسين \"},{\"id\":\"153\",\"title\":\"لطيفي-كيوان\"},{\"id\":\"260\",\"title\":\"مالايي كناري-زهرا\"},{\"id\":\"4\",\"title\":\"مالكي-حسين \"},{\"id\":\"109\",\"title\":\"متاجي-عباس\"},{\"id\":\"39\",\"title\":\"متولي الموتي-الهام\"},{\"id\":\"264\",\"title\":\"مجتهديان -ميلاد \"},{\"id\":\"18\",\"title\":\"مجرد نقيبي-جمشيد\"},{\"id\":\"293\",\"title\":\"مجيدي (راديولوژيست)-هادي\"},{\"id\":\"232\",\"title\":\"مجيديان-مهتا\"},{\"id\":\"49\",\"title\":\"مجيديان-مهسا\"},{\"id\":\"264\",\"title\":\"محسنيان-سليمه\"},{\"id\":\"249\",\"title\":\"محسني-محمد\"},{\"id\":\"77\",\"title\":\"محضري-نظام الدين\"},{\"id\":\"77\",\"title\":\"محمد جعفري-حميد\"},{\"id\":\"232\",\"title\":\"محمد كرمانشاهاني-عباس\"},{\"id\":\"243\",\"title\":\"محمد نژاد-فاطمه\"},{\"id\":\"250\",\"title\":\"محمدپور شجاعي-آرش\"},{\"id\":\"210\",\"title\":\"محمدي نرگس-\"},{\"id\":\"310\",\"title\":\"محمدي-اسمعيل\"},{\"id\":\"18\",\"title\":\"محمدي-سلطان\"},{\"id\":\"4\",\"title\":\"محمدي-عزت الله\"},{\"id\":\"8\",\"title\":\"محمدي-محمد\"},{\"id\":\"28\",\"title\":\"محمود جانلو-قدرت اله\"},{\"id\":\"83\",\"title\":\"محمودزاده كناري-محمد\"},{\"id\":\"197\",\"title\":\"محمودي -ماني \"},{\"id\":\"303\",\"title\":\"محمودي-پيام\"},{\"id\":\"249\",\"title\":\"مختاري-امينه\"},{\"id\":\"138\",\"title\":\"مختاري-فرزاد\"},{\"id\":\"90\",\"title\":\"مخلوق-عطيه\"},{\"id\":\"288\",\"title\":\"مدديان-شهناز\"},{\"id\":\"23\",\"title\":\"مددي-فيروزه \"},{\"id\":\"70\",\"title\":\"مدني-زهرا\"},{\"id\":\"250\",\"title\":\"مرادي نژاد-محمد رضا\"},{\"id\":\"255\",\"title\":\"مرادي-شهرام\"},{\"id\":\"364\",\"title\":\"مرادي-محمد زمان\"},{\"id\":\"382\",\"title\":\"مردانشاهي -زهرا\"},{\"id\":\"90\",\"title\":\"مسعود زاده-عباس \"},{\"id\":\"264\",\"title\":\"مسعودزاده-عباس\"},{\"id\":\"90\",\"title\":\"مسعودي-اسماعيل\"},{\"id\":\"159\",\"title\":\"مسعودي-محبوبه\"},{\"id\":\"260\",\"title\":\"مسعودي-هاله\"},{\"id\":\"423\",\"title\":\"مسگريان-مرجان\"},{\"id\":\"83\",\"title\":\"مسلمي زاده-نرگس\"},{\"id\":\"110\",\"title\":\"مصطفوي-محمد كاظم\"},{\"id\":\"220\",\"title\":\"مظفرپورنوري-علي\"},{\"id\":\"100\",\"title\":\"مظفري-محبوبه\"},{\"id\":\"77\",\"title\":\"معبودي -مهرداد\"},{\"id\":\"49\",\"title\":\"معبودي-سيد عبداله\"},{\"id\":\"12\",\"title\":\"معتمدي-احمد\"},{\"id\":\"39\",\"title\":\"معتمدي-فاطمه\"},{\"id\":\"210\",\"title\":\"معرفتي-فروزان\"},{\"id\":\"343\",\"title\":\"معروفي-اميركيوان\"},{\"id\":\"83\",\"title\":\"معماران دادگر جاويد-\"},{\"id\":\"109\",\"title\":\"معماريان-مهدي\"},{\"id\":\"69\",\"title\":\"معيني-اطلس\"},{\"id\":\"338\",\"title\":\"مفيديان-رضا\"},{\"id\":\"4\",\"title\":\"مقصود لو-علي رضا \"},{\"id\":\"77\",\"title\":\"مقصودلو-عليرضا\"},{\"id\":\"260\",\"title\":\"مكارمي-مائده\"},{\"id\":\"281\",\"title\":\"ملاصالحي-ركسانا\"},{\"id\":\"294\",\"title\":\"ملك پور-انوشيروان\"},{\"id\":\"159\",\"title\":\"ملك محمودي-شهره\"},{\"id\":\"237\",\"title\":\"ملك-شمسا\"},{\"id\":\"69\",\"title\":\"ملكي -علي \"},{\"id\":\"83\",\"title\":\"ملكي-فاطمه\"},{\"id\":\"153\",\"title\":\"منتظر -سيدحسن\"},{\"id\":\"83\",\"title\":\"منتظري--\"},{\"id\":\"138\",\"title\":\"منتظري جويباري-محمد حسين\"},{\"id\":\"259\",\"title\":\"منصورسمايي-غزال\"},{\"id\":\"70\",\"title\":\"منصور-مهدي \"},{\"id\":\"110\",\"title\":\"منصوريان-احسان\"},{\"id\":\"7\",\"title\":\"منصوري-حسين\"},{\"id\":\"243\",\"title\":\"منفردي-فاطمه\"},{\"id\":\"249\",\"title\":\"مه تيان-الهام\"},{\"id\":\"281\",\"title\":\"مهاجري-شيوا\"},{\"id\":\"52\",\"title\":\"مهدوي اميري-محمدرضا\"},{\"id\":\"44\",\"title\":\"مهدوي سرشت-شيوا\"},{\"id\":\"100\",\"title\":\"مهدوي عمران-بهرام\"},{\"id\":\"69\",\"title\":\"مهدوي نيا-سهيلا\"},{\"id\":\"90\",\"title\":\"مهدوي-علي\"},{\"id\":\"293\",\"title\":\"مهدوي-مجدالدين \"},{\"id\":\"23\",\"title\":\"مهدي زاده-زين العابدين\"},{\"id\":\"343\",\"title\":\"مهدي زاده-زين العابدين\"},{\"id\":\"153\",\"title\":\"مهري فيروزجائي-محمدعلي\"},{\"id\":\"210\",\"title\":\"موجرلو-محمد\"},{\"id\":\"339\",\"title\":\"مودبي-اميرحسين\"},{\"id\":\"210\",\"title\":\"موسوي -سيد عباس \"},{\"id\":\"369\",\"title\":\"موسوي -سيدجواد\"},{\"id\":\"40\",\"title\":\"موسوي(جراح اطفال)-سيد عبداله\"},{\"id\":\"39\",\"title\":\"موسويان-مستعان\"},{\"id\":\"181\",\"title\":\"موسوي-سيد محمد\"},{\"id\":\"286\",\"title\":\"موسوي-سيدعبداله\"},{\"id\":\"32\",\"title\":\"موسوي-سيدمحمود\"},{\"id\":\"288\",\"title\":\"موسي نژاد-نادعلي\"},{\"id\":\"7\",\"title\":\"موميوند-حسين \"},{\"id\":\"23\",\"title\":\"ميرجليلي-سيدعباس\"},{\"id\":\"7\",\"title\":\"ميرچي-غلامرضا\"},{\"id\":\"264\",\"title\":\"ميرحاجي-روجا\"},{\"id\":\"69\",\"title\":\"ميرزائي كوتنائي-ابراهيم\"},{\"id\":\"110\",\"title\":\"ميرسنجري -ميترا\"},{\"id\":\"237\",\"title\":\"ميرصفايي ريزي-فرزانه السادات\"},{\"id\":\"109\",\"title\":\"ميلاني-حسين\"},{\"id\":\"100\",\"title\":\"مینا-بهرام\"},{\"id\":\"220\",\"title\":\"نادري سوركي -محمد \"},{\"id\":\"83\",\"title\":\"نادري -عليرضا \"},{\"id\":\"4\",\"title\":\"نادري-ابراهيم\"},{\"id\":\"292\",\"title\":\"نادري-فائزه\"},{\"id\":\"110\",\"title\":\"ناصحي-محمد مهدي\"},{\"id\":\"23\",\"title\":\"ناصري-محمد\"},{\"id\":\"294\",\"title\":\"ناظريان-سيما\"},{\"id\":\"90\",\"title\":\"ناظري-مهشيد\"},{\"id\":\"7\",\"title\":\"ناظمي بيدگلي-محمد\"},{\"id\":\"28\",\"title\":\"ناييجي-ولي محمد\"},{\"id\":\"197\",\"title\":\"نبوي-فرهاد\"},{\"id\":\"249\",\"title\":\"نجاتي-فاطمه\"},{\"id\":\"455\",\"title\":\"نجفي تروجني-سيد آقاجان\"},{\"id\":\"77\",\"title\":\"نجفي تيرتاشي-هادي\"},{\"id\":\"7\",\"title\":\"نجفي سوركي-نرگس\"},{\"id\":\"70\",\"title\":\"نخشب-مريم\"},{\"id\":\"325\",\"title\":\"نژاد عليرضايي-آرزو\"},{\"id\":\"264\",\"title\":\"نژادي كلاريجاني-فاطمه \"},{\"id\":\"70\",\"title\":\"نصري-شيده\"},{\"id\":\"255\",\"title\":\"نصيري زيدي-ستاره\"},{\"id\":\"90\",\"title\":\"نظافتي-صديقه\"},{\"id\":\"455\",\"title\":\"نعمتي-بابك\"},{\"id\":\"181\",\"title\":\"نقش وار-فرشاد\"},{\"id\":\"44\",\"title\":\"نقي زاده -نوشين\"},{\"id\":\"110\",\"title\":\"نقي زاده-عليرضا\"},{\"id\":\"7\",\"title\":\"نقي زاده-نيلوفر\"},{\"id\":\"8\",\"title\":\"نقيب حسيني-ابراهيم\"},{\"id\":\"77\",\"title\":\"نمدانيان-غلامرضا\"},{\"id\":\"181\",\"title\":\"نوايي فر -محمد رضا \"},{\"id\":\"109\",\"title\":\"نوراني-سيده زهرا\"},{\"id\":\"237\",\"title\":\"نوراني-سيده مهسا\"},{\"id\":\"210\",\"title\":\"نوراني-نغمه\"},{\"id\":\"138\",\"title\":\"نوروز پور ديلمي-كيومرث\"},{\"id\":\"303\",\"title\":\"نوروزپورديلمي-كيومرث\"},{\"id\":\"434\",\"title\":\"نوروزي لاجيمي-كميل \"},{\"id\":\"18\",\"title\":\"نوروزي-حسن \"},{\"id\":\"52\",\"title\":\"نوري زاده-حبيب\"},{\"id\":\"69\",\"title\":\"نوري-نسرين\"},{\"id\":\"7\",\"title\":\"نيابتي-نيلوفر\"},{\"id\":\"281\",\"title\":\"نياستي-علي\"},{\"id\":\"343\",\"title\":\"نيك زاد جمناني-عليرضا\"},{\"id\":\"109\",\"title\":\"نيك-حسن\"},{\"id\":\"18\",\"title\":\"نيكخواهان-بابك\"},{\"id\":\"4\",\"title\":\"نيكخواه-مهدي\"},{\"id\":\"264\",\"title\":\"نيكخو-شهاب\"},{\"id\":\"250\",\"title\":\"هادي زاده مقدم-مهدي\"},{\"id\":\"12\",\"title\":\"هاشمي امير-سيدحسن \"},{\"id\":\"110\",\"title\":\"هاشمي پطرودي-سيد محمد جواد\"},{\"id\":\"109\",\"title\":\"هاشمي لمرودي-سيد سعيد\"},{\"id\":\"44\",\"title\":\"هاشمي(جراح)-محمد\"},{\"id\":\"249\",\"title\":\"هامون-سامان\"},{\"id\":\"150\",\"title\":\"هدايتي گودرزي-محمد تقي\"},{\"id\":\"364\",\"title\":\"هزارخاني-شرابه\"},{\"id\":\"138\",\"title\":\"هوشيار-بهاره\"},{\"id\":\"70\",\"title\":\"واقفي فر-صدف\"},{\"id\":\"109\",\"title\":\"وثوقي-هوشنگ\"},{\"id\":\"39\",\"title\":\"وحيد-سعيد\"},{\"id\":\"197\",\"title\":\"وحيدي راد-زمان\"},{\"id\":\"110\",\"title\":\"ورشوچي-فاطمه\"},{\"id\":\"52\",\"title\":\"وشتاني-سيدعباس\"},{\"id\":\"90\",\"title\":\"وشتاني-وحيد\"},{\"id\":\"288\",\"title\":\"وطني-مهسا\"},{\"id\":\"397\",\"title\":\"وفايي زاده-فرحناز\"},{\"id\":\"159\",\"title\":\"وفايي نژاد-مريم\"},{\"id\":\"310\",\"title\":\"ولي زادگان-محمد\"},{\"id\":\"77\",\"title\":\"ولي زاده-سيد محمد \"},{\"id\":\"42\",\"title\":\"يحيي زاده-محمد كاظم\"},{\"id\":\"100\",\"title\":\"يخكشي-مختار\"},{\"id\":\"32\",\"title\":\"يزدان زاد-مريم\"},{\"id\":\"210\",\"title\":\"يزداني مياركلائي-الهام\"},{\"id\":\"138\",\"title\":\"يزداني-ساناز\"},{\"id\":\"452\",\"title\":\"يزداني-عليرضا\"},{\"id\":\"90\",\"title\":\"يساري -فاطمه\"},{\"id\":\"325\",\"title\":\"يساري-فاطمه\"},{\"id\":\"250\",\"title\":\"يصادقي ولني-آسيه\"},{\"id\":\"210\",\"title\":\"يقيني-مصطفي\"},{\"id\":\"264\",\"title\":\"يوسف مهاري-بهناز\"},{\"id\":\"304\",\"title\":\"يوسف نژاد-اميد\"},{\"id\":\"70\",\"title\":\"يوسف نيا -علي اصغر \"},{\"id\":\"32\",\"title\":\"يوسفي عبدالملكي -الهام\"},{\"id\":\"220\",\"title\":\"يوسفي نورائي-حسنيه\"},{\"id\":\"69\",\"title\":\"يوسفي-اسماعيل\"},{\"id\":\"28\",\"title\":\"يوسفيان-محمد اسماعيل\"},{\"id\":\"441\",\"title\":\"يوسفي-رضا\"},{\"id\":\"8\",\"title\":\"يوسفي-سيده صديقه\"},{\"id\":\"12\",\"title\":\"يوسفي-عاطفه\"},{\"id\":\"259\",\"title\":\"ييلاقي اشرفي-سكينه\"},{\"id\":\"8\",\"title\":\"یونسی-مهدی\"}],\"hospital\":[{\"id\":\"0\",\"title\":\"تمامی درمانگاهها\"},{\"id\":\"4751791151\",\"title\":\"امام خميني فريدونكنار\"},{\"id\":\"4841634838\",\"title\":\"بيمارستان امام حسين نكا\"},{\"id\":\"4615747836\",\"title\":\"بيمارستان17 شهريور آمل\"},{\"id\":\"4674153768\",\"title\":\"بیمارستان آیت الله خامنه ای عباس آباد\"},{\"id\":\"4641964595\",\"title\":\"بیمارستان امام خميني نور\"},{\"id\":\"4851774491\",\"title\":\"بیمارستان امام خمینی بهشهر\"},{\"id\":\"4816633131\",\"title\":\"بیمارستان امام خمینی ساری\"},{\"id\":\"4614937597\",\"title\":\"بیمارستان امام رضا آمل\"},{\"id\":\"4691714143\",\"title\":\"بیمارستان امام سجاد رامسر\"},{\"id\":\"4613916978\",\"title\":\"بیمارستان امام علي آمل\"},{\"id\":\"4815838477\",\"title\":\"بیمارستان بوعلی سینا ساری\"},{\"id\":\"4861768711\",\"title\":\"بیمارستان ثامن گلوگاه\"},{\"id\":\"4816895475\",\"title\":\"بیمارستان رازی  قائمشهر\"},{\"id\":\"4817119164\",\"title\":\"بیمارستان زارع ساری\"},{\"id\":\"4851613185\",\"title\":\"بیمارستان شهدا بهشهر\"},{\"id\":\"4781779718\",\"title\":\"بیمارستان شهدا زيراب\"},{\"id\":\"4631917364\",\"title\":\"بیمارستان شهدا محمودآباد\"},{\"id\":\"4651881316\",\"title\":\"بیمارستان شهيد بهشتي نوشهر\"},{\"id\":\"4681989888\",\"title\":\"بیمارستان شهید رجائی تنکابن\"},{\"id\":\"4661619384\",\"title\":\"بیمارستان طالقانی چالوس\"},{\"id\":\"4771666414\",\"title\":\"بیمارستان عزيزي جويبار\"},{\"id\":\"4741637654\",\"title\":\"حضرت زينب بابلسر\"},{\"id\":\"4818813771\",\"title\":\"فاطمه زهرا (س) ساري\"},{\"id\":\"4666147111\",\"title\":\"قائم كلاردشت\"},{\"id\":\"4816895474\",\"title\":\"مجتمع  تخصصی و فوق تخصصی باغبان(طوبی)\"},{\"id\":\"4844143438\",\"title\":\"کلینیک تخصصی حاج مصطفی آشوری(طوبی 2)\"},{\"id\":\"4616797839\",\"title\":\"کلینیک تخصصی و فوق تخصصی ارین\"},{\"id\":\"4851916676\",\"title\":\"کلینیک داخلی بهشهر\"},{\"id\":\"4631917369\",\"title\":\"کلینیک طوبی محمودآباد\"},{\"id\":\"4819653441\",\"title\":\"کلینیک ویژه تخصصی و فوق تخصصی شهروند\"},{\"id\":\"4741858899\",\"title\":\"کلینیک ویژه تخصصی و فوق تخصصی شهیدصالحی\"},{\"id\":\"4841733367\",\"title\":\"کلینیک ویژه تخصصی و فوق تخصصی طوبی شهرستان نکا\"}],\"city\":[{\"id\":\"0\",\"title\":\"تمامی زمان ها\"},{\"id\":\"100\",\"title\":\"آمل\"},{\"id\":\"111\",\"title\":\"بابلسر\"},{\"id\":\"103\",\"title\":\"بهشهر\"},{\"id\":\"104\",\"title\":\"تنكابن\"},{\"id\":\"115\",\"title\":\"جويبار\"},{\"id\":\"114\",\"title\":\"چالوس\u200C\"},{\"id\":\"105\",\"title\":\"رامسر\"},{\"id\":\"106\",\"title\":\"ساري\u200C\"},{\"id\":\"3716\",\"title\":\"سواد كوه \"},{\"id\":\"120\",\"title\":\"فريدونكنار\"},{\"id\":\"108\",\"title\":\"قائم\u200Cشهر\"},{\"id\":\"3718\",\"title\":\"كلار دشت\"},{\"id\":\"2851\",\"title\":\"گلوگاه\"},{\"id\":\"112\",\"title\":\"محموداباد\"},{\"id\":\"113\",\"title\":\"نكا\"},{\"id\":\"109\",\"title\":\"نور\"},{\"id\":\"110\",\"title\":\"نوشهر\"}],\"specialty\":[{\"id\":\"0\",\"title\":\"تمامی تخصص ها\"},{\"id\":\"65\",\"title\":\"رزيدنت اورژانس\"},{\"id\":\"88\",\"title\":\"ارولوژي\"},{\"id\":\"90\",\"title\":\"چشم\"},{\"id\":\"92\",\"title\":\"بيهوشي\"},{\"id\":\"94\",\"title\":\"آزمايشگاه\"},{\"id\":\"95\",\"title\":\"عمومي\"},{\"id\":\"99\",\"title\":\"يبينايي سنجي\"},{\"id\":\"195\",\"title\":\"روانپزشک\"},{\"id\":\"206\",\"title\":\"جراح اطفال\"},{\"id\":\"211\",\"title\":\"دندانپزشك\"},{\"id\":\"272\",\"title\":\"زنان وزايمان\"},{\"id\":\"275\",\"title\":\"جراح  عمومي\"},{\"id\":\"276\",\"title\":\"جراح  مغزواعصاب\"},{\"id\":\"277\",\"title\":\"جراح  قفسه  صدري (تراكس\"},{\"id\":\"278\",\"title\":\"جراح  قلب\"},{\"id\":\"280\",\"title\":\"جراح  كليه ومجاري ادرار\"},{\"id\":\"281\",\"title\":\"جراح تر ميمي\"},{\"id\":\"282\",\"title\":\" گوش وحلق وبيني\"},{\"id\":\"284\",\"title\":\"ارتوپدي\"},{\"id\":\"285\",\"title\":\"روانپزشكي\"},{\"id\":\"287\",\"title\":\"گوارش\"},{\"id\":\"288\",\"title\":\"قلب و عروق\"},{\"id\":\"289\",\"title\":\"روماتو لوژي\"},{\"id\":\"291\",\"title\":\"عفوني\"},{\"id\":\"293\",\"title\":\"غدد\"},{\"id\":\"295\",\"title\":\"پوست\"},{\"id\":\"693\",\"title\":\"ترميمي و زيبائي\"},{\"id\":\"694\",\"title\":\"اعصاب و روان\"},{\"id\":\"698\",\"title\":\"ارتو دنسي\"},{\"id\":\"699\",\"title\":\"راديو لوژي دندانپز شكي\"},{\"id\":\"702\",\"title\":\"مواد دنداني\"},{\"id\":\"724\",\"title\":\"ريه\"},{\"id\":\"725\",\"title\":\"داخلي\"},{\"id\":\"735\",\"title\":\"داروسازي\"},{\"id\":\"737\",\"title\":\"راديوتراپي\"},{\"id\":\"740\",\"title\":\"كودكان\"},{\"id\":\"746\",\"title\":\"پاتولوژي كلينيكال و آناتوميكال\"},{\"id\":\"747\",\"title\":\"راديولوژي\"},{\"id\":\"768\",\"title\":\"اطفال\"},{\"id\":\"772\",\"title\":\"غدد اطفال\"},{\"id\":\"1147\",\"title\":\"فوق تخصص قلب اطفال\"},{\"id\":\"2273\",\"title\":\"پزشكي قانوني\"},{\"id\":\"3501\",\"title\":\"مغز واعصاب\"},{\"id\":\"3546\",\"title\":\"فوق تخصص هماتولوژي و آنكولوژي\"},{\"id\":\"10001\",\"title\":\"كارشناسي\"},{\"id\":\"10002\",\"title\":\"كارشناسي ارشد\"},{\"id\":\"10004\",\"title\":\"فوق تخصص نوزادان\"},{\"id\":\"10005\",\"title\":\"فوق تخصص کليه\"},{\"id\":\"10007\",\"title\":\"رزيدنت\"},{\"id\":\"10008\",\"title\":\"فک وصورت\"},{\"id\":\"10009\",\"title\":\"فوق تخصص قلب و عروق\"},{\"id\":\"10010\",\"title\":\"فلوشيپ قلب و عروق\"},{\"id\":\"10011\",\"title\":\"PHD بينايي سنجي\"},{\"id\":\"10012\",\"title\":\"فلوشيب جراحي پلاستيک چشم\"},{\"id\":\"10014\",\"title\":\"جراح مغز واعصاب\"},{\"id\":\"10015\",\"title\":\"فوق تخصص روانپزشکي\"},{\"id\":\"10016\",\"title\":\"فلوشيب Ms\"},{\"id\":\"10020\",\"title\":\"روماتو لوژي  کودکان\"},{\"id\":\"10022\",\"title\":\" فوق تخصص گوارش کودکان\"},{\"id\":\"10023\",\"title\":\"فوق تخصص کليه اطفال\"},{\"id\":\"10026\",\"title\":\"داخلي کودکان\"},{\"id\":\"10030\",\"title\":\"هماتولوژي و آنكولوژي کودکان\"}],\"time\":[{\"id\":\"0\",\"title\":\"تمامی زمان ها\"},{\"id\":\"1\",\"title\":\"هفت روز آینده\"},{\"id\":\"2\",\"title\":\"دو هفته آینده\"},{\"id\":\"3\",\"title\":\"یک ماه آینده\"}],\"turnList\":[]}}";
                    setDropDownsData(result, "update");
                } else if (tag.equals("takhasos")) {
                    txtFrRes_takhasos.setText(title + "");
                    takhasosId = id;
                } else if (tag.equals("darmonghah")) {
                    txtFrRes_darmonghah.setText(title + "");
                    hospiralId = id;
                } else if (tag.equals("time")) {
                    txtFrRes_time.setText(title + "");
                    timeId = id;
                } else if (tag.equals("doctor")) {
                    txtFrRes_doctor.setText(title + "");
                    doctorId = id;
                }

                alertDialogFilter.dismiss();
            }
        });
        recycFitler.setAdapter(AdRecycPopUp);

        editsearchSearchView = layout.findViewById(R.id.searchFr);
        editsearchSearchView.setOnQueryTextListener(this);

        builder.setView(layout);
        alertDialogFilter = builder.create();
        alertDialogFilter.show();
        alertDialogFilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    private void selectFilters(View view) {
        linearSelectFilters = view.findViewById(R.id.linearSelectFilters);
        linearSelectFiltersBtn = view.findViewById(R.id.linearSelectFiltersBtn);
        txtFrResTitle_city = view.findViewById(R.id.txtFrResTitle_city);
        txtFrRes_city = view.findViewById(R.id.txtFrRes_city);
        txtFrResTitle_takhasos = view.findViewById(R.id.txtFrResTitle_takhasos);
        txtFrRes_takhasos = view.findViewById(R.id.txtFrRes_takhasos);
        txtFrResTitle_darmonghah = view.findViewById(R.id.txtFrResTitle_darmonghah);
        txtFrRes_darmonghah = view.findViewById(R.id.txtFrRes_darmonghah);
        txtFrResTitle_time = view.findViewById(R.id.txtFrResTitle_time);
        txtFrRes_time = view.findViewById(R.id.txtFrRes_time);
        txtFrResTitle_doctor = view.findViewById(R.id.txtFrResTitle_doctor);
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
                if (arrayListResTimes.size() != 0)
                    arrayListResTimes.clear();

                // TODO: Sent data and get ResTimes data (RecycleView) | link2--------------------------------------------------------
                String res = "{\"status\":\"yes\",\"message\":\"\",\"data\":[{\"dr_prg_hsp_mdc_spc_date_id\":\"1\",\"hsp_title\":\"بیمارستان شهید بهشتی\",\"shift_title\":\"صبح\",\"dr_name\":\"مهسا طاهری\",\"spc_title\":\"اطفال\",\"prg_date\":\"1399/01/06\",\"web_turn\":\"1\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"12\",\"hsp_title\":\"بیمارستان شهید حسینی\",\"shift_title\":\"عصر\",\"dr_name\":\"مهدی منصوری\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/02/07\",\"web_turn\":\"0\",\"status_type\":\"اتمام\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"13\",\"hsp_title\":\"بیمارستان شهید صالحی\",\"shift_title\":\"شب\",\"dr_name\":\"فرزاد اکبری\",\"spc_title\":\"عمومی\",\"prg_date\":\"1399/03/09\",\"web_turn\":\"5\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"14\",\"hsp_title\":\"بیمارستان شهید عابدزاده\",\"shift_title\":\"عصر\",\"dr_name\":\"علی ملکی\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/04/19\",\"web_turn\":\"3\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"15\",\"hsp_title\":\"بیمارستان  بهشتی\",\"shift_title\":\"صبح\",\"dr_name\":\"نجمه مقدم\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/09/05\",\"web_turn\":\"0\",\"status_type\":\"اتمام\"}]}";
                setRecycViewData(res);


            }
        });


        dataCity = new ArrayList();
        dataTakhasos = new ArrayList();
        dataHospital = new ArrayList();
        dataHospital2 = new ArrayList();
        dataTime = new ArrayList();
        dataDoctor = new ArrayList();
        dataCityID = new ArrayList();
        dataTakhasosID = new ArrayList();
        dataHospitalID = new ArrayList();
        dataHospitalID2 = new ArrayList();
        dataTimeID = new ArrayList();
        dataDoctorID = new ArrayList();

//TODO------ Connection data for dropDowns link1

//        JSONObject object = new JSONObject();
//        try {
//            object.put("id", "-1");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        new setConnectionVolley(getContext(), "http://nobat.mazums.ac.ir/TurnApi/Search?id=-1", object
//        ).connectStringRequest(new setConnectionVolley.OnResponse() {
//            @Override
//            public void OnResponse(String response) {
//                setDropDownsData(response, "new");
//            }
//        });
        String response  = "{\"status\":\"yes\",\"message\":\"\",\"data\":{\"doctor\":[{\"id\":\"0\",\"title\":\"تمامی پزشکان\"},{\"id\":\"42\",\"title\":\"  -بيمارستان\"},{\"id\":\"153\",\"title\":\" سليماني نژاد-مجيد\"},{\"id\":\"33\",\"title\":\" كلهر-ابراهيم \"},{\"id\":\"18\",\"title\":\" كياكجوري-سعيد\"},{\"id\":\"100\",\"title\":\" محمدزاده-ماريا\"},{\"id\":\"42\",\"title\":\" ملائي-عباسعلي\"},{\"id\":\"39\",\"title\":\".-ماما\"},{\"id\":\"8\",\"title\":\"آذري-پريا\"},{\"id\":\"181\",\"title\":\"آرام بنياد-نوشين\"},{\"id\":\"210\",\"title\":\"آزادبخش-مونا\"},{\"id\":\"286\",\"title\":\"آقائي-حسين\"},{\"id\":\"12\",\"title\":\"آقابيگي-عليرضا\"},{\"id\":\"197\",\"title\":\"آقاجان خواه-محمد رضا\"},{\"id\":\"70\",\"title\":\"آقاجانزاده-محمد ابراهيم \"},{\"id\":\"259\",\"title\":\"آملي-حسين\"},{\"id\":\"304\",\"title\":\"آهنگري-ليلا\"},{\"id\":\"197\",\"title\":\"آهنگري-مهدي\"},{\"id\":\"100\",\"title\":\"ابراهيم نتاج-علي\"},{\"id\":\"2\",\"title\":\"ابراهيمي - مازيار\"},{\"id\":\"77\",\"title\":\"ابراهيمي پاكزاد-سيدحمزه\"},{\"id\":\"70\",\"title\":\"ابراهيمي كياسري-محمد تقي\"},{\"id\":\"4\",\"title\":\"ابراهيمي-ابراهيم\"},{\"id\":\"153\",\"title\":\"ابراهيمي-سيده خديجه\"},{\"id\":\"77\",\"title\":\"ابطحي-فرح\"},{\"id\":\"310\",\"title\":\"ابطحي-فرح\"},{\"id\":\"432\",\"title\":\"ابوطالبي -مهنوش\"},{\"id\":\"8\",\"title\":\"احتشامي-سعيد\"},{\"id\":\"4\",\"title\":\"احتشامی-سعید\"},{\"id\":\"7\",\"title\":\"احمد نژاد-سكينه\"},{\"id\":\"303\",\"title\":\"احمدزاده عالمي-سارا\"},{\"id\":\"8\",\"title\":\"احمدنژاد-شاهين\"},{\"id\":\"4\",\"title\":\"احمدي پور- فريبرز\"},{\"id\":\"304\",\"title\":\"احمدي(ايمانه )-ايمانه\"},{\"id\":\"39\",\"title\":\"احمدي-ايمانه\"},{\"id\":\"343\",\"title\":\"احمدي-محمد\"},{\"id\":\"232\",\"title\":\"احمدي-مطهره\"},{\"id\":\"69\",\"title\":\"اخلاقي-ژابيز\"},{\"id\":\"39\",\"title\":\"اخي-عذرا \"},{\"id\":\"42\",\"title\":\"ارم-شيوا\"},{\"id\":\"40\",\"title\":\"اسپهبدي-فاطمه\"},{\"id\":\"69\",\"title\":\"اسپهبدي-فاطمه\"},{\"id\":\"434\",\"title\":\"اسپهبدي-فاطمه\"},{\"id\":\"70\",\"title\":\"اسحاقي -محمد\"},{\"id\":\"4\",\"title\":\"اسدالله پور-مختار \"},{\"id\":\"243\",\"title\":\"اسدي -الميرا\"},{\"id\":\"83\",\"title\":\"اسدي پور-نسرين\"},{\"id\":\"264\",\"title\":\"اسديان-ليلا\"},{\"id\":\"23\",\"title\":\"اسدي-فاطمه\"},{\"id\":\"255\",\"title\":\"اسدي-فريبا \"},{\"id\":\"249\",\"title\":\"اسعدي-بهرام\"},{\"id\":\"23\",\"title\":\"اسفنديار-علي اصغر\"},{\"id\":\"40\",\"title\":\"اسفنديار-مائده\"},{\"id\":\"237\",\"title\":\"اسكندريون-عباسعلي \"},{\"id\":\"7\",\"title\":\"اسمائيلي آزاد-مرجان\"},{\"id\":\"33\",\"title\":\"اسماعيلي-سميه\"},{\"id\":\"8\",\"title\":\"اصغري قاجاري-معصومه\"},{\"id\":\"109\",\"title\":\"اصغري قراخيلي -محمد\"},{\"id\":\"197\",\"title\":\"اصغري-پيمان\"},{\"id\":\"288\",\"title\":\"اصغري-حسن\"},{\"id\":\"32\",\"title\":\"اصغري-ذبيح الله \"},{\"id\":\"150\",\"title\":\"اصغري-محمد مهدي\"},{\"id\":\"83\",\"title\":\"اعتماد-رجا\"},{\"id\":\"286\",\"title\":\"اعزي پاشاكلايي-گلي\"},{\"id\":\"40\",\"title\":\"اعلائي عبدالرسول-\"},{\"id\":\"434\",\"title\":\"افرادي-قاسم\"},{\"id\":\"197\",\"title\":\"افضليان اشكذري-الهام\"},{\"id\":\"7\",\"title\":\"اقبالي-نصراله\"},{\"id\":\"12\",\"title\":\"اكبرپور-مليحه\"},{\"id\":\"28\",\"title\":\"اكبرنژاد-سوده\"},{\"id\":\"8\",\"title\":\"اكبري-حسنعلي \"},{\"id\":\"293\",\"title\":\"اكبري-فرامرز\"},{\"id\":\"44\",\"title\":\"الحسيني-سلاله\"},{\"id\":\"8\",\"title\":\"الهاميان-رضا \"},{\"id\":\"49\",\"title\":\"الوندي پور-مينا \"},{\"id\":\"259\",\"title\":\"امامي قراء-الهي\"},{\"id\":\"90\",\"title\":\"امامي-مهديه\"},{\"id\":\"232\",\"title\":\"امري ساروكلائي-رضا\"},{\"id\":\"181\",\"title\":\"اميدوار-ترگل\"},{\"id\":\"303\",\"title\":\"اميري فر-سپيده\"},{\"id\":\"69\",\"title\":\"اميني-رضا\"},{\"id\":\"363\",\"title\":\"انعامي-ابوالفضل\"},{\"id\":\"4\",\"title\":\"انگورج تقوي-مسلم\"},{\"id\":\"232\",\"title\":\"اورنگ-الهه\"},{\"id\":\"110\",\"title\":\"بابا عليزاده-علي اكبر \"},{\"id\":\"44\",\"title\":\"بابا محمودي-فرهنگ\"},{\"id\":\"138\",\"title\":\"بابائي نژاد-ميثم\"},{\"id\":\"286\",\"title\":\"بابائيان كوپايي-مهستي\"},{\"id\":\"28\",\"title\":\"بابازاده-جواد\"},{\"id\":\"50\",\"title\":\"بابامحمودي -فرهنگ\"},{\"id\":\"4\",\"title\":\"باباييان-مهستي\"},{\"id\":\"423\",\"title\":\"باطبي-زهره\"},{\"id\":\"28\",\"title\":\"باقر زاده صبا-علي \"},{\"id\":\"83\",\"title\":\"باقرزاده صبا-علي\"},{\"id\":\"250\",\"title\":\"باقري -سيد وفا \"},{\"id\":\"33\",\"title\":\"باقري-اكبر\"},{\"id\":\"70\",\"title\":\"باقريان-محمد حسن \"},{\"id\":\"8\",\"title\":\"باقريان-محمد علي\"},{\"id\":\"249\",\"title\":\"باقريان-محمدعلي\"},{\"id\":\"250\",\"title\":\"باقري-بنيامين\"},{\"id\":\"39\",\"title\":\"باقري-بهزاد\"},{\"id\":\"249\",\"title\":\"باقري-بهزاد\"},{\"id\":\"369\",\"title\":\"باقري-ليلا\"},{\"id\":\"18\",\"title\":\"باكري-فاطمه\"},{\"id\":\"12\",\"title\":\"بامداديان حسين-\"},{\"id\":\"77\",\"title\":\"بامداديان-حسين\"},{\"id\":\"44\",\"title\":\"باوندي-اقاي \"},{\"id\":\"50\",\"title\":\"ببببببب-بببب\"},{\"id\":\"70\",\"title\":\"بخشي نسب-حسين \"},{\"id\":\"432\",\"title\":\"براتي-محمدصالح\"},{\"id\":\"324\",\"title\":\"بردبار-مهدي\"},{\"id\":\"292\",\"title\":\"برزگر بادله-مهدي\"},{\"id\":\"293\",\"title\":\"برزگرنژاد-ايوب \"},{\"id\":\"50\",\"title\":\"بريماني-هوشنگ\"},{\"id\":\"409\",\"title\":\"بشيري ابراهيميان-سيد مرتضي \"},{\"id\":\"150\",\"title\":\"بصيرت-علي اكبر\"},{\"id\":\"90\",\"title\":\"بنافتي-سيده رفعت\"},{\"id\":\"292\",\"title\":\"بني مصطفوي-الهام سادات\"},{\"id\":\"250\",\"title\":\"بهرامي آهنگر-محمد\"},{\"id\":\"23\",\"title\":\"بهرامي-كورش\"},{\"id\":\"453\",\"title\":\"بهشتي-علي اكبر\"},{\"id\":\"8\",\"title\":\"بورچي-عليرضا\"},{\"id\":\"50\",\"title\":\"بيگدلي-محمدرضا\"},{\"id\":\"364\",\"title\":\"بيگلرتبار-منوچهر\"},{\"id\":\"338\",\"title\":\"بيمارستان امام رضا آمل-\"},{\"id\":\"423\",\"title\":\"پارياب-ابراهيم\"},{\"id\":\"52\",\"title\":\"پاشازانوسي-محمدرضا\"},{\"id\":\"12\",\"title\":\"پاك سرشت-سعيد\"},{\"id\":\"220\",\"title\":\"پاكدل -ليلا\"},{\"id\":\"28\",\"title\":\"پرندوش-مريم\"},{\"id\":\"12\",\"title\":\"پرويزي عمران- بهنام\"},{\"id\":\"255\",\"title\":\"پرويزي عمران-بهنام \"},{\"id\":\"255\",\"title\":\"پروين -بهاره \"},{\"id\":\"237\",\"title\":\"پزشكان-پدرام\"},{\"id\":\"293\",\"title\":\"پور احمديان-ايرج\"},{\"id\":\"33\",\"title\":\"پور اصغر-مهدي\"},{\"id\":\"70\",\"title\":\"پور موسي-رستم\"},{\"id\":\"303\",\"title\":\"پوراحمديان-ايرج\"},{\"id\":\"220\",\"title\":\"پورخاني-علي\"},{\"id\":\"100\",\"title\":\"پورطباطبائي-پرويز\"},{\"id\":\"250\",\"title\":\"پورطباطبائي-پرويز\"},{\"id\":\"49\",\"title\":\"پيام-مريم\"},{\"id\":\"138\",\"title\":\"پيران-راژان\"},{\"id\":\"42\",\"title\":\"پيروزيان-شهره\"},{\"id\":\"181\",\"title\":\"پيش سرائيان-ابراهيم\"},{\"id\":\"50\",\"title\":\"پيوندي -عبدالخالق\"},{\"id\":\"197\",\"title\":\"ترابي زاده-ژيلا\"},{\"id\":\"77\",\"title\":\"تقوايي- ترنگ \"},{\"id\":\"2\",\"title\":\"تقوايي -رحمت ا...\"},{\"id\":\"181\",\"title\":\"تقوايي-ترنگ\"},{\"id\":\"83\",\"title\":\"تقوايي-رحمت اله\"},{\"id\":\"363\",\"title\":\"تقوي 2 K-سيده زينب\"},{\"id\":\"255\",\"title\":\"تقوي پور-مونا\"},{\"id\":\"255\",\"title\":\"تقوي گرمستاني-سيد مقداد \"},{\"id\":\"109\",\"title\":\"تقوي-نعمت\"},{\"id\":\"39\",\"title\":\"تقي زاده-ابراهيم\"},{\"id\":\"90\",\"title\":\"تناسان-محمد\"},{\"id\":\"210\",\"title\":\"توپا ابراهيمي-رضا\"},{\"id\":\"50\",\"title\":\"توراني-مختار\"},{\"id\":\"110\",\"title\":\"توسلي اشرفي-احمد\"},{\"id\":\"44\",\"title\":\"توسلي -ماريا\"},{\"id\":\"100\",\"title\":\"توسن-عباس\"},{\"id\":\"44\",\"title\":\"توفيقي- دكترمريم\"},{\"id\":\"260\",\"title\":\"توفيقي -محمد \"},{\"id\":\"339\",\"title\":\"توفيقي-مرجان\"},{\"id\":\"220\",\"title\":\"توكلي -حسن \"},{\"id\":\"150\",\"title\":\"توكلي-سعداله\"},{\"id\":\"382\",\"title\":\"تيمورزاده بابلي-مژگان\"},{\"id\":\"292\",\"title\":\"ثقه مجتهدي-ديانوش\"},{\"id\":\"32\",\"title\":\"جابري-امير\"},{\"id\":\"4\",\"title\":\"جاري-طاهره\"},{\"id\":\"2\",\"title\":\"جان بابايي-قاسم \"},{\"id\":\"8\",\"title\":\"جان بابايي-قاسم\"},{\"id\":\"237\",\"title\":\"جبار پور-شيرين\"},{\"id\":\"18\",\"title\":\"جبارپور-شيرين\"},{\"id\":\"294\",\"title\":\"جعفري (جراح مغز)-محمد\"},{\"id\":\"2\",\"title\":\"جعفري راد-عبدالرضا\"},{\"id\":\"100\",\"title\":\"جعفريان جلودار-حسن\"},{\"id\":\"12\",\"title\":\"جعفريان-حسن\"},{\"id\":\"363\",\"title\":\"جعفري-محمودرضا\"},{\"id\":\"109\",\"title\":\"جعفري-هاجر\"},{\"id\":\"52\",\"title\":\"جعفرييان جلودار-علي\"},{\"id\":\"303\",\"title\":\"جغتايي-حسام الدين\"},{\"id\":\"69\",\"title\":\"جليل پور-اسماعيل \"},{\"id\":\"23\",\"title\":\"جمشيدي جامخانه اي-يحيي\"},{\"id\":\"50\",\"title\":\"جمشيدي-ايوب\"},{\"id\":\"364\",\"title\":\"جمشيدي-ميثم\"},{\"id\":\"250\",\"title\":\"جنگ جو-سولماز\"},{\"id\":\"455\",\"title\":\"جهان فكر-وحيد\"},{\"id\":\"2\",\"title\":\"جهانبخش-ذبيح الله \"},{\"id\":\"339\",\"title\":\"جهانبخش-ذبيح اله\"},{\"id\":\"44\",\"title\":\"جوان آملي-مجيد\"},{\"id\":\"44\",\"title\":\"جوكار-رحمت اله \"},{\"id\":\"288\",\"title\":\"چراغ مكاني-\"},{\"id\":\"138\",\"title\":\"چگيني-نسترن\"},{\"id\":\"28\",\"title\":\"حاتم-آرزو\"},{\"id\":\"4\",\"title\":\"حاجي آبادي-مريم\"},{\"id\":\"181\",\"title\":\"حاجي پور-روجا\"},{\"id\":\"33\",\"title\":\"حاجي زاده-محبوبه \"},{\"id\":\"12\",\"title\":\"حبيبي -محمدرضا\"},{\"id\":\"453\",\"title\":\"حبيبي -ولي اله\"},{\"id\":\"397\",\"title\":\"حبيبيان-نرگس \"},{\"id\":\"12\",\"title\":\"حجازي-ابراهيم\"},{\"id\":\"8\",\"title\":\"حجتي -مهدي \"},{\"id\":\"33\",\"title\":\"حجتي(مهدي)-مهدي\"},{\"id\":\"69\",\"title\":\"حدادی-کاوه\"},{\"id\":\"52\",\"title\":\"حسامي رستمي-جعفر\"},{\"id\":\"50\",\"title\":\"حسامي رستمي-محمد حسين \"},{\"id\":\"42\",\"title\":\"حسن زاده -زهرا\"},{\"id\":\"7\",\"title\":\"حسن زاده-هما\"},{\"id\":\"7\",\"title\":\"حسن نتاج-مجد\"},{\"id\":\"83\",\"title\":\"حسين اشرفي-سيد كاميار\"},{\"id\":\"32\",\"title\":\"حسين پور بنافتي-ايران\"},{\"id\":\"150\",\"title\":\"حسين پور-ميترا\"},{\"id\":\"109\",\"title\":\"حسين جان زاده-دكتر\"},{\"id\":\"33\",\"title\":\"حسيني -بهجت السادات \"},{\"id\":\"138\",\"title\":\"حسيني نژاد -سيدجواد\"},{\"id\":\"8\",\"title\":\"حسينيان-بي بي اعظم \"},{\"id\":\"281\",\"title\":\"حسينيان-محمدعلي \"},{\"id\":\"7\",\"title\":\"حسيني-وحيد\"},{\"id\":\"49\",\"title\":\"حسینی-وحید\"},{\"id\":\"210\",\"title\":\"حق بين-سوسن\"},{\"id\":\"153\",\"title\":\"حق پناه اسكي-مستعان\"},{\"id\":\"7\",\"title\":\"حق شناس -حسن\"},{\"id\":\"18\",\"title\":\"حق نظر-هدي\"},{\"id\":\"90\",\"title\":\"حقوقي فرد-كاميار\"},{\"id\":\"12\",\"title\":\"حقي- ناهيد\"},{\"id\":\"7\",\"title\":\"حكيمي-سيد حامد\"},{\"id\":\"18\",\"title\":\"حمزه رباطي-منصوره\"},{\"id\":\"452\",\"title\":\"حيدريان-سميرا\"},{\"id\":\"2\",\"title\":\"حيدري-محمد اسمائيل\"},{\"id\":\"243\",\"title\":\"خادميان-مجتبي\"},{\"id\":\"23\",\"title\":\"خان احمدي-عباس\"},{\"id\":\"138\",\"title\":\"خبازيان-مهديه\"},{\"id\":\"18\",\"title\":\"خدادادي -خداداد\"},{\"id\":\"138\",\"title\":\"خداد-تبسم\"},{\"id\":\"281\",\"title\":\"خرازي زاده-مريم\"},{\"id\":\"324\",\"title\":\"خرم-نصيبه\"},{\"id\":\"28\",\"title\":\"خسروداد -حسن \"},{\"id\":\"294\",\"title\":\"خسروي -نويد\"},{\"id\":\"159\",\"title\":\"خسروي نويد (متادن )-\"},{\"id\":\"150\",\"title\":\"خلج سياوش-\"},{\"id\":\"23\",\"title\":\"خلعتبري-سيمين\"},{\"id\":\"49\",\"title\":\"خليلي-زينب\"},{\"id\":\"32\",\"title\":\"خليلي-محمدجعفر\"},{\"id\":\"33\",\"title\":\"خميري ثاني-فاطمه\"},{\"id\":\"40\",\"title\":\"خواجه حسيني-دكترزهرا\"},{\"id\":\"28\",\"title\":\"خواجه نوري-\"},{\"id\":\"39\",\"title\":\"خواجوند -حسين \"},{\"id\":\"181\",\"title\":\"خواجوند -حسين \"},{\"id\":\"33\",\"title\":\"خوزان-محبوبه\"},{\"id\":\"255\",\"title\":\"خوش نظر شورابسري-علي\"},{\"id\":\"409\",\"title\":\"خوشكام -مائده \"},{\"id\":\"32\",\"title\":\"خيالي فرد-امل \"},{\"id\":\"255\",\"title\":\"دادگر-جاويد\"},{\"id\":\"52\",\"title\":\"دارائي-مونا\"},{\"id\":\"197\",\"title\":\"دارابي-نازنين\"},{\"id\":\"210\",\"title\":\"دانش پور-سيد مهدي\"},{\"id\":\"52\",\"title\":\"دانشگر-عباس\"},{\"id\":\"210\",\"title\":\"داودي-مهدي\"},{\"id\":\"153\",\"title\":\"دباغ زاده سوركي-عباس\"},{\"id\":\"281\",\"title\":\"درويش زاده-اكرم\"},{\"id\":\"42\",\"title\":\"درويشي-فرشته\"},{\"id\":\"4\",\"title\":\"دكتر--\"},{\"id\":\"83\",\"title\":\"دلدار-عبدالرضا\"},{\"id\":\"83\",\"title\":\"دليري كاديجاني-آذين\"},{\"id\":\"7\",\"title\":\"دهپوري-فاطمه\"},{\"id\":\"304\",\"title\":\"دهدار چلمردي-نبي اله \"},{\"id\":\"18\",\"title\":\"دهدار-نبي اله\"},{\"id\":\"33\",\"title\":\"دهشيري-محمدرضا \"},{\"id\":\"338\",\"title\":\"دهقان-سيدحامد\"},{\"id\":\"153\",\"title\":\"دهقانكار-سيده زهرا\"},{\"id\":\"197\",\"title\":\"دهقان-وحيد\"},{\"id\":\"243\",\"title\":\"دهمرده-سارا\"},{\"id\":\"343\",\"title\":\"دوانلو(راديولوژيست)-اميرحسين \"},{\"id\":\"8\",\"title\":\"ديانتي-ام السليمه\"},{\"id\":\"39\",\"title\":\"ديانتي-فاطيما\"},{\"id\":\"32\",\"title\":\"ذاكري دانا-مائده\"},{\"id\":\"150\",\"title\":\"ذبيحي-علي \"},{\"id\":\"243\",\"title\":\"ذكريايي -ذكريا\"},{\"id\":\"110\",\"title\":\"رئيس محمدي-ليلا\"},{\"id\":\"181\",\"title\":\"رادپور-علي رضا\"},{\"id\":\"52\",\"title\":\"رازي-حبيبه\"},{\"id\":\"33\",\"title\":\"راعي-ربابه\"},{\"id\":\"49\",\"title\":\"ربيعي-بهمن\"},{\"id\":\"243\",\"title\":\"ربيعي-ندا\"},{\"id\":\"4\",\"title\":\"رجائي-فاطمه\"},{\"id\":\"138\",\"title\":\"رجب زاده-پويا\"},{\"id\":\"159\",\"title\":\"رحماني بوئيني-نسرين\"},{\"id\":\"40\",\"title\":\"رحمانيان-عبدالرحمن \"},{\"id\":\"220\",\"title\":\"رحيمي النگي-توحيد\"},{\"id\":\"343\",\"title\":\"رحيمي -بهداد\"},{\"id\":\"33\",\"title\":\"رزيدنت -\"},{\"id\":\"220\",\"title\":\"رستگارتبار-حجت الله\"},{\"id\":\"33\",\"title\":\"رستمي  -مرواريد\"},{\"id\":\"423\",\"title\":\"رستميان-نرگس\"},{\"id\":\"243\",\"title\":\"رسولي لورمايي -احمد رضا\"},{\"id\":\"259\",\"title\":\"رسولي-مير قربان\"},{\"id\":\"83\",\"title\":\"رشيدي-الهه\"},{\"id\":\"42\",\"title\":\"رشيدي-حميد\"},{\"id\":\"39\",\"title\":\"رشيدي-فاطمه\"},{\"id\":\"153\",\"title\":\"رضائي-سامان \"},{\"id\":\"33\",\"title\":\"رضائي-محمدمهدي \"},{\"id\":\"286\",\"title\":\"رضايي شهري-ايرج\"},{\"id\":\"40\",\"title\":\"رضايي كلانتري-زهرا\"},{\"id\":\"325\",\"title\":\"رضايي مهر(زرديس )-زرديس \"},{\"id\":\"42\",\"title\":\"رضاييان-رمضانعلي\"},{\"id\":\"264\",\"title\":\"رضايي-حمزه\"},{\"id\":\"4\",\"title\":\"رضايي-علي\"},{\"id\":\"23\",\"title\":\"رضايي-فاطمه \"},{\"id\":\"181\",\"title\":\"رضايي-محمد رضا\"},{\"id\":\"23\",\"title\":\"ركان قزويني-آرمان\"},{\"id\":\"255\",\"title\":\"رمضان پورگريودهي-سوده\"},{\"id\":\"220\",\"title\":\"رمضانپور-مليكا\"},{\"id\":\"33\",\"title\":\"رمضاني -فاطمه\"},{\"id\":\"255\",\"title\":\"رمضاني قراخيلي-شقايق\"},{\"id\":\"181\",\"title\":\"رمضاني لهمالي-داريوش\"},{\"id\":\"324\",\"title\":\"رمضاني-محبوبه\"},{\"id\":\"2\",\"title\":\"روحاني نژاد-حسين\"},{\"id\":\"249\",\"title\":\"روحاني-مرتضي\"},{\"id\":\"28\",\"title\":\"روحاني-وحيد رضا\"},{\"id\":\"77\",\"title\":\"رياحي-حكيمه \"},{\"id\":\"423\",\"title\":\"زابلي-احسان\"},{\"id\":\"159\",\"title\":\"زارعي-علي\"},{\"id\":\"42\",\"title\":\"زاهدي-ناصر\"},{\"id\":\"50\",\"title\":\"زرواني-اشرف \"},{\"id\":\"369\",\"title\":\"زرين خامه-پروانه\"},{\"id\":\"264\",\"title\":\"زمانفر-دانيل \"},{\"id\":\"259\",\"title\":\"زمانيان-حسين\"},{\"id\":\"18\",\"title\":\"زماني-حسن\"},{\"id\":\"294\",\"title\":\"زماني-سيد حسين\"},{\"id\":\"28\",\"title\":\"زنجاني-رويا\"},{\"id\":\"264\",\"title\":\"زنده دل-سجاد\"},{\"id\":\"325\",\"title\":\"زواري-حسن\"},{\"id\":\"249\",\"title\":\"ساروي پور -سيدضياء الدين\"},{\"id\":\"250\",\"title\":\"سازگار-محمد\"},{\"id\":\"39\",\"title\":\"سالاريان-بيژن\"},{\"id\":\"40\",\"title\":\"سالاريان-عبدالعظيم\"},{\"id\":\"42\",\"title\":\"سالاريان-كيانوش\"},{\"id\":\"259\",\"title\":\"سام دليري-ارزو\"},{\"id\":\"8\",\"title\":\"سبحاني پيام-\"},{\"id\":\"49\",\"title\":\"سبحاني-مصطفي\"},{\"id\":\"138\",\"title\":\"ستاره-جواد\"},{\"id\":\"288\",\"title\":\"سجادي ساروي-مجيد\"},{\"id\":\"220\",\"title\":\"سفيدگرنيا اميري-مريم\"},{\"id\":\"264\",\"title\":\"سلطان تويه-محمد\"},{\"id\":\"260\",\"title\":\"سلطاني-امير\"},{\"id\":\"12\",\"title\":\"سليمان پور-عبدالله\"},{\"id\":\"138\",\"title\":\"سليمان نژاد-مجيد\"},{\"id\":\"159\",\"title\":\"سليمان نژاد-مصطفي\"},{\"id\":\"2\",\"title\":\"سليمان نژاد-مهدي\"},{\"id\":\"12\",\"title\":\"سليمي-شيما\"},{\"id\":\"220\",\"title\":\"سنگ شكن--\"},{\"id\":\"181\",\"title\":\"سنگسري-افشين\"},{\"id\":\"110\",\"title\":\"سهراب-مهرنوش\"},{\"id\":\"397\",\"title\":\"سوادكوهي -فرزاد\"},{\"id\":\"44\",\"title\":\"سوركي-محمد باقر\"},{\"id\":\"50\",\"title\":\"سيد اشرفي-مقداد\"},{\"id\":\"40\",\"title\":\"سيدي-رويا\"},{\"id\":\"2\",\"title\":\"سيف الله پور-زهرا\"},{\"id\":\"109\",\"title\":\"سيفي -آزاده \"},{\"id\":\"49\",\"title\":\"سيفي پاشا-مهدي\"},{\"id\":\"343\",\"title\":\"سينا-مريم\"},{\"id\":\"90\",\"title\":\"شارع پور-ماريا\"},{\"id\":\"197\",\"title\":\"شاه حسيني-رزا\"},{\"id\":\"23\",\"title\":\"شاهاني-محمد\"},{\"id\":\"250\",\"title\":\"شاهپوري-پويا\"},{\"id\":\"7\",\"title\":\"شاهسواراني-محمد\"},{\"id\":\"286\",\"title\":\"شايسته اذر-مسعود\"},{\"id\":\"310\",\"title\":\"شايسته-رضا\"},{\"id\":\"110\",\"title\":\"شجاعي-ليلا\"},{\"id\":\"232\",\"title\":\"شريعتي-سكينه \"},{\"id\":\"32\",\"title\":\"شريعتي-نوشين\"},{\"id\":\"69\",\"title\":\"شريف-حسينعلي\"},{\"id\":\"237\",\"title\":\"شريفي رضوي-آتنا\"},{\"id\":\"28\",\"title\":\"شريفي سوركي-ولي اله\"},{\"id\":\"150\",\"title\":\"شفاعت (روانپزشک اطفال)-عارفه \"},{\"id\":\"32\",\"title\":\"شفتي-ويدا\"},{\"id\":\"325\",\"title\":\"شفيعي (سجاد ) -سجاد \"},{\"id\":\"310\",\"title\":\"شفيعي-سجاد\"},{\"id\":\"52\",\"title\":\"شفيعي-مريم \"},{\"id\":\"28\",\"title\":\"شكرريز فومني-رامين\"},{\"id\":\"49\",\"title\":\"شكري-محمد\"},{\"id\":\"397\",\"title\":\"شمس اميري -روزبه\"},{\"id\":\"7\",\"title\":\"شمسايي-مرتضي\"},{\"id\":\"28\",\"title\":\"شهاب الديني-مهدي\"},{\"id\":\"310\",\"title\":\"شهبازنژاد -ليلا\"},{\"id\":\"100\",\"title\":\"شهدا-بيمارستان\"},{\"id\":\"23\",\"title\":\"شيخ رضايي-مجيد رضا\"},{\"id\":\"153\",\"title\":\"شيخ مونسي-فاطمه\"},{\"id\":\"441\",\"title\":\"شيدايي-سميه\"},{\"id\":\"232\",\"title\":\"شيرآشتياني-حميدرضا\"},{\"id\":\"50\",\"title\":\"شيرزاد-ماشاءالله\"},{\"id\":\"292\",\"title\":\"شيرود بخشي-خاطره\"},{\"id\":\"52\",\"title\":\"صابري فر-مرتضي\"},{\"id\":\"110\",\"title\":\"صادقي نيك-مولود\"},{\"id\":\"382\",\"title\":\"صادقي-تكتم\"},{\"id\":\"109\",\"title\":\"صادقي-سپيده\"},{\"id\":\"33\",\"title\":\"صادقي-فريبا\"},{\"id\":\"150\",\"title\":\"صادقي-كيميا\"},{\"id\":\"7\",\"title\":\"صادقي-نرگس\"},{\"id\":\"42\",\"title\":\"صارمي-اردشير\"},{\"id\":\"220\",\"title\":\"صالحي كهريزسنگي-فاطمه\"},{\"id\":\"288\",\"title\":\"صالحي-سپيده\"},{\"id\":\"249\",\"title\":\"صالحي-مهدي\"},{\"id\":\"50\",\"title\":\"صحفي-سيده مريم\"},{\"id\":\"4\",\"title\":\"صدقي -پروين\"},{\"id\":\"304\",\"title\":\"صدقيان-احمد\"},{\"id\":\"364\",\"title\":\"صفري-فرشاد\"},{\"id\":\"109\",\"title\":\"صمدي نسب-احمد \"},{\"id\":\"181\",\"title\":\"صياديان سپرغان-داود\"},{\"id\":\"197\",\"title\":\"صيرفي-آذر\"},{\"id\":\"159\",\"title\":\"ضرغامي-مهران\"},{\"id\":\"220\",\"title\":\"ضيا،بخش-حسين\"},{\"id\":\"52\",\"title\":\"طالبي لياسي-مريم\"},{\"id\":\"69\",\"title\":\"طالبي نسامي-معصومه\"},{\"id\":\"12\",\"title\":\"طاهري-محسن\"},{\"id\":\"109\",\"title\":\"طاهري-مريم\"},{\"id\":\"153\",\"title\":\"طباطبايي پور-مريم السادات\"},{\"id\":\"32\",\"title\":\"طراوتي-آيسان\"},{\"id\":\"32\",\"title\":\"طوير سياري-عبدالعلي \"},{\"id\":\"220\",\"title\":\"طيبي -شهنام \"},{\"id\":\"292\",\"title\":\"طيبي-خديجه\"},{\"id\":\"33\",\"title\":\"طيبي-شهنام \"},{\"id\":\"12\",\"title\":\"طيبي-عاطفه\"},{\"id\":\"441\",\"title\":\"طيبي-گويا \"},{\"id\":\"2\",\"title\":\"ظفرمند-رسول\"},{\"id\":\"90\",\"title\":\"عابد فيروزجاه-ام البنين\"},{\"id\":\"432\",\"title\":\"عابدي ارزفوني-سيده فاطمه\"},{\"id\":\"42\",\"title\":\"عابدي سما گوش-محمد\"},{\"id\":\"259\",\"title\":\"عابدي فيروزجايي-سكينه\"},{\"id\":\"453\",\"title\":\"عابدي نژاد-شيدا\"},{\"id\":\"42\",\"title\":\"عابدي ولوكلايي-سيده خديجه \"},{\"id\":\"4\",\"title\":\"عابديني-عليرضا\"},{\"id\":\"77\",\"title\":\"عادلاني-بهرام \"},{\"id\":\"2\",\"title\":\"عارفي-صبورا\"},{\"id\":\"40\",\"title\":\"عاشقي-ابراهيم\"},{\"id\":\"18\",\"title\":\"عاشورزاده-بهرنگ\"},{\"id\":\"150\",\"title\":\"عاشوري زاده -بهرنگ \"},{\"id\":\"90\",\"title\":\"عالمي-ثريا\"},{\"id\":\"52\",\"title\":\"عاليان -شهريار\"},{\"id\":\"232\",\"title\":\"عاليزاد-مليكه\"},{\"id\":\"7\",\"title\":\"عاليشاه-ناهيد\"},{\"id\":\"293\",\"title\":\"عامريان-محمد\"},{\"id\":\"50\",\"title\":\"عباس پور-شميلا\"},{\"id\":\"243\",\"title\":\"عباس زاده مرزبالي-نرجس\"},{\"id\":\"260\",\"title\":\"عباس زاده-مينا\"},{\"id\":\"39\",\"title\":\"عباسخاني دوانلو-علي\"},{\"id\":\"150\",\"title\":\"عباسيان-ستاره\"},{\"id\":\"325\",\"title\":\"عباسي-حامد\"},{\"id\":\"243\",\"title\":\"عباسي-عادله\"},{\"id\":\"77\",\"title\":\"عبدا...پور-نگار\"},{\"id\":\"32\",\"title\":\"عبدالهي- سيد محمد\"},{\"id\":\"260\",\"title\":\"عبدالهي-مرضيه\"},{\"id\":\"237\",\"title\":\"عبدي-ليلا\"},{\"id\":\"150\",\"title\":\"عرب تقوايي-ترنگ\"},{\"id\":\"363\",\"title\":\"عرب زاده -آيدين\"},{\"id\":\"210\",\"title\":\"عرفاني-سپيدخت\"},{\"id\":\"7\",\"title\":\"عزت پناه-يزدان\"},{\"id\":\"292\",\"title\":\"عزيزي-پريا\"},{\"id\":\"159\",\"title\":\"عزيزي-محمد رضا\"},{\"id\":\"220\",\"title\":\"عسگري فيروز جايي-رشيد\"},{\"id\":\"70\",\"title\":\"عسگري فيروزجائي رشيد-\"},{\"id\":\"286\",\"title\":\"عصائيان-حميد رضا\"},{\"id\":\"181\",\"title\":\"عصائيان-فروغ\"},{\"id\":\"39\",\"title\":\"عظيمي -سميرا\"},{\"id\":\"39\",\"title\":\"عظيمي -مسعود\"},{\"id\":\"197\",\"title\":\"علم-عليرضا\"},{\"id\":\"409\",\"title\":\"علوي-فاطمه \"},{\"id\":\"40\",\"title\":\"علي نژاد-مرضيه\"},{\"id\":\"40\",\"title\":\"عليپور-عبدالرضا\"},{\"id\":\"250\",\"title\":\"عليپور-مهناز\"},{\"id\":\"153\",\"title\":\"عليرضا داودي بدابي-\"},{\"id\":\"28\",\"title\":\"عليزاده فروتن-مصطفي\"},{\"id\":\"255\",\"title\":\"عليزاده-ميترا\"},{\"id\":\"338\",\"title\":\"عليمحمدي-سيد ميثم\"},{\"id\":\"70\",\"title\":\"عليمرادي -امير ضيا الدين \"},{\"id\":\"40\",\"title\":\"علیالی-مسعود\"},{\"id\":\"249\",\"title\":\"عمادي-احمد\"},{\"id\":\"2\",\"title\":\"عمادي-سيد جمشيد\"},{\"id\":\"100\",\"title\":\"عمادي-سيده نرگس\"},{\"id\":\"2\",\"title\":\"عمراني-الهام\"},{\"id\":\"109\",\"title\":\"عموزاد-محسن\"},{\"id\":\"23\",\"title\":\"عموزاده عمران-مريم\"},{\"id\":\"110\",\"title\":\"عمويي خانعباسي-مهرناز\"},{\"id\":\"441\",\"title\":\"عنايتي-معصومه\"},{\"id\":\"294\",\"title\":\"غريبي-ارد\"},{\"id\":\"434\",\"title\":\"غزائيان-منيره\"},{\"id\":\"42\",\"title\":\"غفاري همداني-سيد محمد مهدي\"},{\"id\":\"69\",\"title\":\"غفاري-رحمان\"},{\"id\":\"232\",\"title\":\"غفور -ايده \"},{\"id\":\"250\",\"title\":\"غلام قاسمي-اميد\"},{\"id\":\"44\",\"title\":\"غياثي-علينقي\"},{\"id\":\"339\",\"title\":\"غياثي-غلام\"},{\"id\":\"197\",\"title\":\"فاتحي-زهرا\"},{\"id\":\"255\",\"title\":\"فاخري-فاطمه\"},{\"id\":\"159\",\"title\":\"فاطمي-سيد كمال الدين\"},{\"id\":\"281\",\"title\":\"فاني-حبيب الله\"},{\"id\":\"28\",\"title\":\"فتاحيان-عليرضا\"},{\"id\":\"52\",\"title\":\"فتح سامي -شاهرخ \"},{\"id\":\"338\",\"title\":\"فتحي-حسين\"},{\"id\":\"441\",\"title\":\"فتحي-مهران\"},{\"id\":\"260\",\"title\":\"فدوي-بهروز\"},{\"id\":\"49\",\"title\":\"فراهاني-فرزانه\"},{\"id\":\"100\",\"title\":\"فرخ زاد-عاصفه\"},{\"id\":\"18\",\"title\":\"فرزاد فر-\"},{\"id\":\"255\",\"title\":\"فرزادفر-اسماعيل\"},{\"id\":\"42\",\"title\":\"فرزاميان -مهدي \"},{\"id\":\"409\",\"title\":\"فرسويان-حسين\"},{\"id\":\"237\",\"title\":\"فرمانبر-محمد علي\"},{\"id\":\"304\",\"title\":\"فرمانبر-محمدعلي \"},{\"id\":\"197\",\"title\":\"فرنیا(روانپزشک اطفال)-سمانه \"},{\"id\":\"23\",\"title\":\"فرهادي-رويا\"},{\"id\":\"232\",\"title\":\"فرهمند-حميدرضا\"},{\"id\":\"197\",\"title\":\"فرهنگي -فرنوش\"},{\"id\":\"49\",\"title\":\"فرهنگي-كريم\"},{\"id\":\"232\",\"title\":\"فشاركي زاده-\"},{\"id\":\"432\",\"title\":\"فضلعلي-بهمن\"},{\"id\":\"339\",\"title\":\"فغاني ماكراني-نفيسه\"},{\"id\":\"181\",\"title\":\"فقاني -مسلم\"},{\"id\":\"259\",\"title\":\"فقيهي نيا-محسن\"},{\"id\":\"100\",\"title\":\"فلاح پور-عليرضا\"},{\"id\":\"232\",\"title\":\"فلاح تلوكي -گلناز\"},{\"id\":\"52\",\"title\":\"فلاح-ابوالقاسم\"},{\"id\":\"12\",\"title\":\"فلاح-كريم\"},{\"id\":\"286\",\"title\":\"فيض زاده كريق-بهزاد\"},{\"id\":\"110\",\"title\":\"قائميان -علي\"},{\"id\":\"259\",\"title\":\"قائمي-حميد رضا\"},{\"id\":\"249\",\"title\":\"قائمي-فرزانه\"},{\"id\":\"49\",\"title\":\"قادي-محمدرضا\"},{\"id\":\"237\",\"title\":\"قاسمپوري -هيوا\"},{\"id\":\"452\",\"title\":\"قاسمي تيرتاشي-مرجان\"},{\"id\":\"32\",\"title\":\"قاسمي -حمزه \"},{\"id\":\"260\",\"title\":\"قاسمي خاني-ناهيد\"},{\"id\":\"23\",\"title\":\"قاسمي نژاد اباذر-\"},{\"id\":\"52\",\"title\":\"قاسميان-رويا\"},{\"id\":\"338\",\"title\":\"قاسميان-رويا\"},{\"id\":\"455\",\"title\":\"قاهري-سيدعباس\"},{\"id\":\"2\",\"title\":\"قديرنژاد-سيد ناصر\"},{\"id\":\"44\",\"title\":\"قديري -علي \"},{\"id\":\"69\",\"title\":\"قربان تبار عمراني-محسن\"},{\"id\":\"453\",\"title\":\"قربان زاده-بهروز\"},{\"id\":\"281\",\"title\":\"قربان زاده-سارا\"},{\"id\":\"39\",\"title\":\"قربانپور-عباس\"},{\"id\":\"69\",\"title\":\"قرباني پاشاكلائي-مريم\"},{\"id\":\"138\",\"title\":\"قرباني نژاد-الهام \"},{\"id\":\"70\",\"title\":\"قرباني-محمد\"},{\"id\":\"181\",\"title\":\"قرباني-نفيسه \"},{\"id\":\"138\",\"title\":\"قره داغي-بابك\"},{\"id\":\"281\",\"title\":\"قريشي -سيدمحمود\"},{\"id\":\"77\",\"title\":\"قريشي-سيد محمود\"},{\"id\":\"363\",\"title\":\"قريشي-محمود\"},{\"id\":\"49\",\"title\":\"قشلاقي -پرند \"},{\"id\":\"100\",\"title\":\"قشلاقي-پرند\"},{\"id\":\"2\",\"title\":\"قلعه سري-قربانعلي\"},{\"id\":\"237\",\"title\":\"قلي پور-الهام\"},{\"id\":\"237\",\"title\":\"قلي زاده سرستي-آزاده\"},{\"id\":\"49\",\"title\":\"قلي زاده-بهروز\"},{\"id\":\"288\",\"title\":\"قلي زاده-شهاب\"},{\"id\":\"243\",\"title\":\"قليزاده پاشا-عبدالحميد\"},{\"id\":\"260\",\"title\":\"قناعت-محبوبه\"},{\"id\":\"12\",\"title\":\"قنبر پور شياده-\"},{\"id\":\"52\",\"title\":\"قهرماني-فاضله \"},{\"id\":\"18\",\"title\":\"قوامي پور-محمدعلي\"},{\"id\":\"232\",\"title\":\"كائيدي مجد -شقايق\"},{\"id\":\"12\",\"title\":\"كاشي-زهرا\"},{\"id\":\"210\",\"title\":\"كاظمي ويسري-ارش\"},{\"id\":\"281\",\"title\":\"كاظمي-رحمت اله\"},{\"id\":\"39\",\"title\":\"كاظمي-عباس\"},{\"id\":\"50\",\"title\":\"كاكوئي-محمد زمان\"},{\"id\":\"237\",\"title\":\"كامراني-معصومه\"},{\"id\":\"293\",\"title\":\"كبيري نسب-مطهره \"},{\"id\":\"42\",\"title\":\"كثيري -عبدالمجيد\"},{\"id\":\"39\",\"title\":\"كربلائي زاده-ماريا\"},{\"id\":\"260\",\"title\":\"كرجاليان چايجاني-مريم\"},{\"id\":\"159\",\"title\":\"كرد جزي-محمدمهدي\"},{\"id\":\"44\",\"title\":\"كرمي -حسن\"},{\"id\":\"8\",\"title\":\"كرمي-حسن\"},{\"id\":\"294\",\"title\":\"كريمي (اطفال)-محمد\"},{\"id\":\"23\",\"title\":\"كريمي علي آبادي-پرستو\"},{\"id\":\"52\",\"title\":\"كريمي-كسري\"},{\"id\":\"210\",\"title\":\"كريمي-محمد\"},{\"id\":\"249\",\"title\":\"كسائي -فاطمه\"},{\"id\":\"42\",\"title\":\"كسائي-حامد \"},{\"id\":\"324\",\"title\":\"كشاورز-محسن\"},{\"id\":\"50\",\"title\":\"كشاورزيان-مژگان\"},{\"id\":\"2\",\"title\":\"كشيري-محمد\"},{\"id\":\"77\",\"title\":\"كلارستاقي-منيژه \"},{\"id\":\"28\",\"title\":\"كمالي-علي \"},{\"id\":\"232\",\"title\":\"كوثريان -مهرنوش \"},{\"id\":\"250\",\"title\":\"كوثريان-سيداحمد\"},{\"id\":\"264\",\"title\":\"كوچكي-نفيسه\"},{\"id\":\"259\",\"title\":\"كوزه گر- ندا\"},{\"id\":\"100\",\"title\":\"كياني-عزت\"},{\"id\":\"8\",\"title\":\"كياني-موسي\"},{\"id\":\"39\",\"title\":\"كيوان شاد-سميرا \"},{\"id\":\"343\",\"title\":\"گدازنده-\"},{\"id\":\"39\",\"title\":\"گدازنده-غلامعلي\"},{\"id\":\"260\",\"title\":\"گدازنده-فرناز\"},{\"id\":\"232\",\"title\":\"گرجي-رضا\"},{\"id\":\"18\",\"title\":\"گرشاسب زاده-جلال\"},{\"id\":\"90\",\"title\":\"گل عليزاده-عزت اله\"},{\"id\":\"249\",\"title\":\"گلبابايي-عليرضا\"},{\"id\":\"220\",\"title\":\"گلچين جو پشتي-مريم\"},{\"id\":\"69\",\"title\":\"گلشني -صمد\"},{\"id\":\"18\",\"title\":\"گلين مقدم-نجمه\"},{\"id\":\"243\",\"title\":\"گودرزيان-مائده\"},{\"id\":\"4\",\"title\":\"گودرزيان-نصرت اله\"},{\"id\":\"264\",\"title\":\"گوران-شكراله\"},{\"id\":\"181\",\"title\":\"لشتوآقايي-بيتا\"},{\"id\":\"28\",\"title\":\"لطيفي -محمدحسين \"},{\"id\":\"153\",\"title\":\"لطيفي-كيوان\"},{\"id\":\"260\",\"title\":\"مالايي كناري-زهرا\"},{\"id\":\"4\",\"title\":\"مالكي-حسين \"},{\"id\":\"109\",\"title\":\"متاجي-عباس\"},{\"id\":\"39\",\"title\":\"متولي الموتي-الهام\"},{\"id\":\"264\",\"title\":\"مجتهديان -ميلاد \"},{\"id\":\"18\",\"title\":\"مجرد نقيبي-جمشيد\"},{\"id\":\"293\",\"title\":\"مجيدي (راديولوژيست)-هادي\"},{\"id\":\"232\",\"title\":\"مجيديان-مهتا\"},{\"id\":\"49\",\"title\":\"مجيديان-مهسا\"},{\"id\":\"264\",\"title\":\"محسنيان-سليمه\"},{\"id\":\"249\",\"title\":\"محسني-محمد\"},{\"id\":\"77\",\"title\":\"محضري-نظام الدين\"},{\"id\":\"77\",\"title\":\"محمد جعفري-حميد\"},{\"id\":\"232\",\"title\":\"محمد كرمانشاهاني-عباس\"},{\"id\":\"243\",\"title\":\"محمد نژاد-فاطمه\"},{\"id\":\"250\",\"title\":\"محمدپور شجاعي-آرش\"},{\"id\":\"210\",\"title\":\"محمدي نرگس-\"},{\"id\":\"310\",\"title\":\"محمدي-اسمعيل\"},{\"id\":\"18\",\"title\":\"محمدي-سلطان\"},{\"id\":\"4\",\"title\":\"محمدي-عزت الله\"},{\"id\":\"8\",\"title\":\"محمدي-محمد\"},{\"id\":\"28\",\"title\":\"محمود جانلو-قدرت اله\"},{\"id\":\"83\",\"title\":\"محمودزاده كناري-محمد\"},{\"id\":\"197\",\"title\":\"محمودي -ماني \"},{\"id\":\"303\",\"title\":\"محمودي-پيام\"},{\"id\":\"249\",\"title\":\"مختاري-امينه\"},{\"id\":\"138\",\"title\":\"مختاري-فرزاد\"},{\"id\":\"90\",\"title\":\"مخلوق-عطيه\"},{\"id\":\"288\",\"title\":\"مدديان-شهناز\"},{\"id\":\"23\",\"title\":\"مددي-فيروزه \"},{\"id\":\"70\",\"title\":\"مدني-زهرا\"},{\"id\":\"250\",\"title\":\"مرادي نژاد-محمد رضا\"},{\"id\":\"255\",\"title\":\"مرادي-شهرام\"},{\"id\":\"364\",\"title\":\"مرادي-محمد زمان\"},{\"id\":\"382\",\"title\":\"مردانشاهي -زهرا\"},{\"id\":\"90\",\"title\":\"مسعود زاده-عباس \"},{\"id\":\"264\",\"title\":\"مسعودزاده-عباس\"},{\"id\":\"90\",\"title\":\"مسعودي-اسماعيل\"},{\"id\":\"159\",\"title\":\"مسعودي-محبوبه\"},{\"id\":\"260\",\"title\":\"مسعودي-هاله\"},{\"id\":\"423\",\"title\":\"مسگريان-مرجان\"},{\"id\":\"83\",\"title\":\"مسلمي زاده-نرگس\"},{\"id\":\"110\",\"title\":\"مصطفوي-محمد كاظم\"},{\"id\":\"220\",\"title\":\"مظفرپورنوري-علي\"},{\"id\":\"100\",\"title\":\"مظفري-محبوبه\"},{\"id\":\"77\",\"title\":\"معبودي -مهرداد\"},{\"id\":\"49\",\"title\":\"معبودي-سيد عبداله\"},{\"id\":\"12\",\"title\":\"معتمدي-احمد\"},{\"id\":\"39\",\"title\":\"معتمدي-فاطمه\"},{\"id\":\"210\",\"title\":\"معرفتي-فروزان\"},{\"id\":\"343\",\"title\":\"معروفي-اميركيوان\"},{\"id\":\"83\",\"title\":\"معماران دادگر جاويد-\"},{\"id\":\"109\",\"title\":\"معماريان-مهدي\"},{\"id\":\"69\",\"title\":\"معيني-اطلس\"},{\"id\":\"338\",\"title\":\"مفيديان-رضا\"},{\"id\":\"4\",\"title\":\"مقصود لو-علي رضا \"},{\"id\":\"77\",\"title\":\"مقصودلو-عليرضا\"},{\"id\":\"260\",\"title\":\"مكارمي-مائده\"},{\"id\":\"281\",\"title\":\"ملاصالحي-ركسانا\"},{\"id\":\"294\",\"title\":\"ملك پور-انوشيروان\"},{\"id\":\"159\",\"title\":\"ملك محمودي-شهره\"},{\"id\":\"237\",\"title\":\"ملك-شمسا\"},{\"id\":\"69\",\"title\":\"ملكي -علي \"},{\"id\":\"83\",\"title\":\"ملكي-فاطمه\"},{\"id\":\"153\",\"title\":\"منتظر -سيدحسن\"},{\"id\":\"83\",\"title\":\"منتظري--\"},{\"id\":\"138\",\"title\":\"منتظري جويباري-محمد حسين\"},{\"id\":\"259\",\"title\":\"منصورسمايي-غزال\"},{\"id\":\"70\",\"title\":\"منصور-مهدي \"},{\"id\":\"110\",\"title\":\"منصوريان-احسان\"},{\"id\":\"7\",\"title\":\"منصوري-حسين\"},{\"id\":\"243\",\"title\":\"منفردي-فاطمه\"},{\"id\":\"249\",\"title\":\"مه تيان-الهام\"},{\"id\":\"281\",\"title\":\"مهاجري-شيوا\"},{\"id\":\"52\",\"title\":\"مهدوي اميري-محمدرضا\"},{\"id\":\"44\",\"title\":\"مهدوي سرشت-شيوا\"},{\"id\":\"100\",\"title\":\"مهدوي عمران-بهرام\"},{\"id\":\"69\",\"title\":\"مهدوي نيا-سهيلا\"},{\"id\":\"90\",\"title\":\"مهدوي-علي\"},{\"id\":\"293\",\"title\":\"مهدوي-مجدالدين \"},{\"id\":\"23\",\"title\":\"مهدي زاده-زين العابدين\"},{\"id\":\"343\",\"title\":\"مهدي زاده-زين العابدين\"},{\"id\":\"153\",\"title\":\"مهري فيروزجائي-محمدعلي\"},{\"id\":\"210\",\"title\":\"موجرلو-محمد\"},{\"id\":\"339\",\"title\":\"مودبي-اميرحسين\"},{\"id\":\"210\",\"title\":\"موسوي -سيد عباس \"},{\"id\":\"369\",\"title\":\"موسوي -سيدجواد\"},{\"id\":\"40\",\"title\":\"موسوي(جراح اطفال)-سيد عبداله\"},{\"id\":\"39\",\"title\":\"موسويان-مستعان\"},{\"id\":\"181\",\"title\":\"موسوي-سيد محمد\"},{\"id\":\"286\",\"title\":\"موسوي-سيدعبداله\"},{\"id\":\"32\",\"title\":\"موسوي-سيدمحمود\"},{\"id\":\"288\",\"title\":\"موسي نژاد-نادعلي\"},{\"id\":\"7\",\"title\":\"موميوند-حسين \"},{\"id\":\"23\",\"title\":\"ميرجليلي-سيدعباس\"},{\"id\":\"7\",\"title\":\"ميرچي-غلامرضا\"},{\"id\":\"264\",\"title\":\"ميرحاجي-روجا\"},{\"id\":\"69\",\"title\":\"ميرزائي كوتنائي-ابراهيم\"},{\"id\":\"110\",\"title\":\"ميرسنجري -ميترا\"},{\"id\":\"237\",\"title\":\"ميرصفايي ريزي-فرزانه السادات\"},{\"id\":\"109\",\"title\":\"ميلاني-حسين\"},{\"id\":\"100\",\"title\":\"مینا-بهرام\"},{\"id\":\"220\",\"title\":\"نادري سوركي -محمد \"},{\"id\":\"83\",\"title\":\"نادري -عليرضا \"},{\"id\":\"4\",\"title\":\"نادري-ابراهيم\"},{\"id\":\"292\",\"title\":\"نادري-فائزه\"},{\"id\":\"110\",\"title\":\"ناصحي-محمد مهدي\"},{\"id\":\"23\",\"title\":\"ناصري-محمد\"},{\"id\":\"294\",\"title\":\"ناظريان-سيما\"},{\"id\":\"90\",\"title\":\"ناظري-مهشيد\"},{\"id\":\"7\",\"title\":\"ناظمي بيدگلي-محمد\"},{\"id\":\"28\",\"title\":\"ناييجي-ولي محمد\"},{\"id\":\"197\",\"title\":\"نبوي-فرهاد\"},{\"id\":\"249\",\"title\":\"نجاتي-فاطمه\"},{\"id\":\"455\",\"title\":\"نجفي تروجني-سيد آقاجان\"},{\"id\":\"77\",\"title\":\"نجفي تيرتاشي-هادي\"},{\"id\":\"7\",\"title\":\"نجفي سوركي-نرگس\"},{\"id\":\"70\",\"title\":\"نخشب-مريم\"},{\"id\":\"325\",\"title\":\"نژاد عليرضايي-آرزو\"},{\"id\":\"264\",\"title\":\"نژادي كلاريجاني-فاطمه \"},{\"id\":\"70\",\"title\":\"نصري-شيده\"},{\"id\":\"255\",\"title\":\"نصيري زيدي-ستاره\"},{\"id\":\"90\",\"title\":\"نظافتي-صديقه\"},{\"id\":\"455\",\"title\":\"نعمتي-بابك\"},{\"id\":\"181\",\"title\":\"نقش وار-فرشاد\"},{\"id\":\"44\",\"title\":\"نقي زاده -نوشين\"},{\"id\":\"110\",\"title\":\"نقي زاده-عليرضا\"},{\"id\":\"7\",\"title\":\"نقي زاده-نيلوفر\"},{\"id\":\"8\",\"title\":\"نقيب حسيني-ابراهيم\"},{\"id\":\"77\",\"title\":\"نمدانيان-غلامرضا\"},{\"id\":\"181\",\"title\":\"نوايي فر -محمد رضا \"},{\"id\":\"109\",\"title\":\"نوراني-سيده زهرا\"},{\"id\":\"237\",\"title\":\"نوراني-سيده مهسا\"},{\"id\":\"210\",\"title\":\"نوراني-نغمه\"},{\"id\":\"138\",\"title\":\"نوروز پور ديلمي-كيومرث\"},{\"id\":\"303\",\"title\":\"نوروزپورديلمي-كيومرث\"},{\"id\":\"434\",\"title\":\"نوروزي لاجيمي-كميل \"},{\"id\":\"18\",\"title\":\"نوروزي-حسن \"},{\"id\":\"52\",\"title\":\"نوري زاده-حبيب\"},{\"id\":\"69\",\"title\":\"نوري-نسرين\"},{\"id\":\"7\",\"title\":\"نيابتي-نيلوفر\"},{\"id\":\"281\",\"title\":\"نياستي-علي\"},{\"id\":\"343\",\"title\":\"نيك زاد جمناني-عليرضا\"},{\"id\":\"109\",\"title\":\"نيك-حسن\"},{\"id\":\"18\",\"title\":\"نيكخواهان-بابك\"},{\"id\":\"4\",\"title\":\"نيكخواه-مهدي\"},{\"id\":\"264\",\"title\":\"نيكخو-شهاب\"},{\"id\":\"250\",\"title\":\"هادي زاده مقدم-مهدي\"},{\"id\":\"12\",\"title\":\"هاشمي امير-سيدحسن \"},{\"id\":\"110\",\"title\":\"هاشمي پطرودي-سيد محمد جواد\"},{\"id\":\"109\",\"title\":\"هاشمي لمرودي-سيد سعيد\"},{\"id\":\"44\",\"title\":\"هاشمي(جراح)-محمد\"},{\"id\":\"249\",\"title\":\"هامون-سامان\"},{\"id\":\"150\",\"title\":\"هدايتي گودرزي-محمد تقي\"},{\"id\":\"364\",\"title\":\"هزارخاني-شرابه\"},{\"id\":\"138\",\"title\":\"هوشيار-بهاره\"},{\"id\":\"70\",\"title\":\"واقفي فر-صدف\"},{\"id\":\"109\",\"title\":\"وثوقي-هوشنگ\"},{\"id\":\"39\",\"title\":\"وحيد-سعيد\"},{\"id\":\"197\",\"title\":\"وحيدي راد-زمان\"},{\"id\":\"110\",\"title\":\"ورشوچي-فاطمه\"},{\"id\":\"52\",\"title\":\"وشتاني-سيدعباس\"},{\"id\":\"90\",\"title\":\"وشتاني-وحيد\"},{\"id\":\"288\",\"title\":\"وطني-مهسا\"},{\"id\":\"397\",\"title\":\"وفايي زاده-فرحناز\"},{\"id\":\"159\",\"title\":\"وفايي نژاد-مريم\"},{\"id\":\"310\",\"title\":\"ولي زادگان-محمد\"},{\"id\":\"77\",\"title\":\"ولي زاده-سيد محمد \"},{\"id\":\"42\",\"title\":\"يحيي زاده-محمد كاظم\"},{\"id\":\"100\",\"title\":\"يخكشي-مختار\"},{\"id\":\"32\",\"title\":\"يزدان زاد-مريم\"},{\"id\":\"210\",\"title\":\"يزداني مياركلائي-الهام\"},{\"id\":\"138\",\"title\":\"يزداني-ساناز\"},{\"id\":\"452\",\"title\":\"يزداني-عليرضا\"},{\"id\":\"90\",\"title\":\"يساري -فاطمه\"},{\"id\":\"325\",\"title\":\"يساري-فاطمه\"},{\"id\":\"250\",\"title\":\"يصادقي ولني-آسيه\"},{\"id\":\"210\",\"title\":\"يقيني-مصطفي\"},{\"id\":\"264\",\"title\":\"يوسف مهاري-بهناز\"},{\"id\":\"304\",\"title\":\"يوسف نژاد-اميد\"},{\"id\":\"70\",\"title\":\"يوسف نيا -علي اصغر \"},{\"id\":\"32\",\"title\":\"يوسفي عبدالملكي -الهام\"},{\"id\":\"220\",\"title\":\"يوسفي نورائي-حسنيه\"},{\"id\":\"69\",\"title\":\"يوسفي-اسماعيل\"},{\"id\":\"28\",\"title\":\"يوسفيان-محمد اسماعيل\"},{\"id\":\"441\",\"title\":\"يوسفي-رضا\"},{\"id\":\"8\",\"title\":\"يوسفي-سيده صديقه\"},{\"id\":\"12\",\"title\":\"يوسفي-عاطفه\"},{\"id\":\"259\",\"title\":\"ييلاقي اشرفي-سكينه\"},{\"id\":\"8\",\"title\":\"یونسی-مهدی\"}],\"hospital\":[{\"id\":\"0\",\"title\":\"تمامی درمانگاهها\"},{\"id\":\"4751791151\",\"title\":\"امام خميني فريدونكنار\"},{\"id\":\"4841634838\",\"title\":\"بيمارستان امام حسين نكا\"},{\"id\":\"4615747836\",\"title\":\"بيمارستان17 شهريور آمل\"},{\"id\":\"4674153768\",\"title\":\"بیمارستان آیت الله خامنه ای عباس آباد\"},{\"id\":\"4641964595\",\"title\":\"بیمارستان امام خميني نور\"},{\"id\":\"4851774491\",\"title\":\"بیمارستان امام خمینی بهشهر\"},{\"id\":\"4816633131\",\"title\":\"بیمارستان امام خمینی ساری\"},{\"id\":\"4614937597\",\"title\":\"بیمارستان امام رضا آمل\"},{\"id\":\"4691714143\",\"title\":\"بیمارستان امام سجاد رامسر\"},{\"id\":\"4613916978\",\"title\":\"بیمارستان امام علي آمل\"},{\"id\":\"4815838477\",\"title\":\"بیمارستان بوعلی سینا ساری\"},{\"id\":\"4861768711\",\"title\":\"بیمارستان ثامن گلوگاه\"},{\"id\":\"4816895475\",\"title\":\"بیمارستان رازی  قائمشهر\"},{\"id\":\"4817119164\",\"title\":\"بیمارستان زارع ساری\"},{\"id\":\"4851613185\",\"title\":\"بیمارستان شهدا بهشهر\"},{\"id\":\"4781779718\",\"title\":\"بیمارستان شهدا زيراب\"},{\"id\":\"4631917364\",\"title\":\"بیمارستان شهدا محمودآباد\"},{\"id\":\"4651881316\",\"title\":\"بیمارستان شهيد بهشتي نوشهر\"},{\"id\":\"4681989888\",\"title\":\"بیمارستان شهید رجائی تنکابن\"},{\"id\":\"4661619384\",\"title\":\"بیمارستان طالقانی چالوس\"},{\"id\":\"4771666414\",\"title\":\"بیمارستان عزيزي جويبار\"},{\"id\":\"4741637654\",\"title\":\"حضرت زينب بابلسر\"},{\"id\":\"4818813771\",\"title\":\"فاطمه زهرا (س) ساري\"},{\"id\":\"4666147111\",\"title\":\"قائم كلاردشت\"},{\"id\":\"4816895474\",\"title\":\"مجتمع  تخصصی و فوق تخصصی باغبان(طوبی)\"},{\"id\":\"4844143438\",\"title\":\"کلینیک تخصصی حاج مصطفی آشوری(طوبی 2)\"},{\"id\":\"4616797839\",\"title\":\"کلینیک تخصصی و فوق تخصصی ارین\"},{\"id\":\"4851916676\",\"title\":\"کلینیک داخلی بهشهر\"},{\"id\":\"4631917369\",\"title\":\"کلینیک طوبی محمودآباد\"},{\"id\":\"4819653441\",\"title\":\"کلینیک ویژه تخصصی و فوق تخصصی شهروند\"},{\"id\":\"4741858899\",\"title\":\"کلینیک ویژه تخصصی و فوق تخصصی شهیدصالحی\"},{\"id\":\"4841733367\",\"title\":\"کلینیک ویژه تخصصی و فوق تخصصی طوبی شهرستان نکا\"}],\"city\":[{\"id\":\"0\",\"title\":\"تمامی زمان ها\"},{\"id\":\"100\",\"title\":\"آمل\"},{\"id\":\"111\",\"title\":\"بابلسر\"},{\"id\":\"103\",\"title\":\"بهشهر\"},{\"id\":\"104\",\"title\":\"تنكابن\"},{\"id\":\"115\",\"title\":\"جويبار\"},{\"id\":\"114\",\"title\":\"چالوس\u200C\"},{\"id\":\"105\",\"title\":\"رامسر\"},{\"id\":\"106\",\"title\":\"ساري\u200C\"},{\"id\":\"3716\",\"title\":\"سواد كوه \"},{\"id\":\"120\",\"title\":\"فريدونكنار\"},{\"id\":\"108\",\"title\":\"قائم\u200Cشهر\"},{\"id\":\"3718\",\"title\":\"كلار دشت\"},{\"id\":\"2851\",\"title\":\"گلوگاه\"},{\"id\":\"112\",\"title\":\"محموداباد\"},{\"id\":\"113\",\"title\":\"نكا\"},{\"id\":\"109\",\"title\":\"نور\"},{\"id\":\"110\",\"title\":\"نوشهر\"}],\"specialty\":[{\"id\":\"0\",\"title\":\"تمامی تخصص ها\"},{\"id\":\"65\",\"title\":\"رزيدنت اورژانس\"},{\"id\":\"88\",\"title\":\"ارولوژي\"},{\"id\":\"90\",\"title\":\"چشم\"},{\"id\":\"92\",\"title\":\"بيهوشي\"},{\"id\":\"94\",\"title\":\"آزمايشگاه\"},{\"id\":\"95\",\"title\":\"عمومي\"},{\"id\":\"99\",\"title\":\"يبينايي سنجي\"},{\"id\":\"195\",\"title\":\"روانپزشک\"},{\"id\":\"206\",\"title\":\"جراح اطفال\"},{\"id\":\"211\",\"title\":\"دندانپزشك\"},{\"id\":\"272\",\"title\":\"زنان وزايمان\"},{\"id\":\"275\",\"title\":\"جراح  عمومي\"},{\"id\":\"276\",\"title\":\"جراح  مغزواعصاب\"},{\"id\":\"277\",\"title\":\"جراح  قفسه  صدري (تراكس\"},{\"id\":\"278\",\"title\":\"جراح  قلب\"},{\"id\":\"280\",\"title\":\"جراح  كليه ومجاري ادرار\"},{\"id\":\"281\",\"title\":\"جراح تر ميمي\"},{\"id\":\"282\",\"title\":\" گوش وحلق وبيني\"},{\"id\":\"284\",\"title\":\"ارتوپدي\"},{\"id\":\"285\",\"title\":\"روانپزشكي\"},{\"id\":\"287\",\"title\":\"گوارش\"},{\"id\":\"288\",\"title\":\"قلب و عروق\"},{\"id\":\"289\",\"title\":\"روماتو لوژي\"},{\"id\":\"291\",\"title\":\"عفوني\"},{\"id\":\"293\",\"title\":\"غدد\"},{\"id\":\"295\",\"title\":\"پوست\"},{\"id\":\"693\",\"title\":\"ترميمي و زيبائي\"},{\"id\":\"694\",\"title\":\"اعصاب و روان\"},{\"id\":\"698\",\"title\":\"ارتو دنسي\"},{\"id\":\"699\",\"title\":\"راديو لوژي دندانپز شكي\"},{\"id\":\"702\",\"title\":\"مواد دنداني\"},{\"id\":\"724\",\"title\":\"ريه\"},{\"id\":\"725\",\"title\":\"داخلي\"},{\"id\":\"735\",\"title\":\"داروسازي\"},{\"id\":\"737\",\"title\":\"راديوتراپي\"},{\"id\":\"740\",\"title\":\"كودكان\"},{\"id\":\"746\",\"title\":\"پاتولوژي كلينيكال و آناتوميكال\"},{\"id\":\"747\",\"title\":\"راديولوژي\"},{\"id\":\"768\",\"title\":\"اطفال\"},{\"id\":\"772\",\"title\":\"غدد اطفال\"},{\"id\":\"1147\",\"title\":\"فوق تخصص قلب اطفال\"},{\"id\":\"2273\",\"title\":\"پزشكي قانوني\"},{\"id\":\"3501\",\"title\":\"مغز واعصاب\"},{\"id\":\"3546\",\"title\":\"فوق تخصص هماتولوژي و آنكولوژي\"},{\"id\":\"10001\",\"title\":\"كارشناسي\"},{\"id\":\"10002\",\"title\":\"كارشناسي ارشد\"},{\"id\":\"10004\",\"title\":\"فوق تخصص نوزادان\"},{\"id\":\"10005\",\"title\":\"فوق تخصص کليه\"},{\"id\":\"10007\",\"title\":\"رزيدنت\"},{\"id\":\"10008\",\"title\":\"فک وصورت\"},{\"id\":\"10009\",\"title\":\"فوق تخصص قلب و عروق\"},{\"id\":\"10010\",\"title\":\"فلوشيپ قلب و عروق\"},{\"id\":\"10011\",\"title\":\"PHD بينايي سنجي\"},{\"id\":\"10012\",\"title\":\"فلوشيب جراحي پلاستيک چشم\"},{\"id\":\"10014\",\"title\":\"جراح مغز واعصاب\"},{\"id\":\"10015\",\"title\":\"فوق تخصص روانپزشکي\"},{\"id\":\"10016\",\"title\":\"فلوشيب Ms\"},{\"id\":\"10020\",\"title\":\"روماتو لوژي  کودکان\"},{\"id\":\"10022\",\"title\":\" فوق تخصص گوارش کودکان\"},{\"id\":\"10023\",\"title\":\"فوق تخصص کليه اطفال\"},{\"id\":\"10026\",\"title\":\"داخلي کودکان\"},{\"id\":\"10030\",\"title\":\"هماتولوژي و آنكولوژي کودکان\"}],\"time\":[{\"id\":\"0\",\"title\":\"تمامی زمان ها\"},{\"id\":\"1\",\"title\":\"هفت روز آینده\"},{\"id\":\"2\",\"title\":\"دو هفته آینده\"},{\"id\":\"3\",\"title\":\"یک ماه آینده\"}],\"turnList\":[]}}";
        setDropDownsData(response, "new");

    }

    private void setDropDownsData(String res, String tag) {
        try {
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

                } else if (tag.equals("update")) {

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

                }

            } else
//                new ShowMessage(getContext()).ShowMessage_SnackBar(linearSelectFilters, message + "");
                Toast.makeText(getContext(), message + "", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    //  SearchView -------------------------------------------------------------
    private SearchView editsearchSearchView;
    private AdRecycPopUp AdRecycPopUp;
    private ArrayList<ModAlerts> arraylistSearchView = new ArrayList<ModAlerts>();

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        AdRecycPopUp.filter(text);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private void defaultDataDropDown() {
        dataHospital = new ArrayList(dataHospital2);
        dataHospitalID = new ArrayList(dataHospitalID2);
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

    }

}