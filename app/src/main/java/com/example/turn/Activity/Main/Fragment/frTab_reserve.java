package com.example.turn.Activity.Main.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turn.Activity.Main.Adapter.AdRecycResTimes;
import com.example.turn.Activity.Main.Adapter.onClickInterface;
import com.example.turn.Activity.Main.Model.ModAlerts;
import com.example.turn.Activity.Main.Adapter.ListViewAdapter;
import com.example.turn.Activity.Main.Model.ModResTime;
import com.example.turn.Classes.ShowMessage;
import com.example.turn.Classes.setConnectionVolley;
import com.example.turn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class frTab_reserve extends Fragment implements SearchView.OnQueryTextListener {

    //  linearSelectFilters
    private AlertDialog alertDialog;
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
    private ArrayList dataTime;
    private ArrayList dataDoctor;

    private ArrayList dataCityID;
    private ArrayList dataTakhasosID;
    private ArrayList dataHospitalID;
    private ArrayList dataTimeID;
    private ArrayList dataDoctorID;

    private String cityId = "";
    private String takhasosId = "";
    private String hospiralId = "";
    private String timeId = "";
    private String doctorId = "";

    //------------ linearResTimes
    private LinearLayout linearResTimes;
    private LinearLayout linearResTimesBtn;
    private RecyclerView rcycRT;

    private ArrayList<ModResTime> arrayListResTimes;

    private Button btnRT_previous;

    //------------ linearPazireshPage
    private LinearLayout linearPazireshPage;
    private LinearLayout linearPazireshPageBtn;
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
    private EditText edtFrPP_phone;
    private LinearLayout linearFrPP_City;
    private TextView txtFrPP_city;
    private EditText edtFrPP_address;
    private LinearLayout linearFrPP_bime;
    private TextView txtFrPP_bime;

    private Button btnPP_previous;

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


        return view;
    }

    private void paziresh(View view) {

        linearPazireshPage = view.findViewById(R.id.linearPazireshPage);
        linearPazireshPageBtn = view.findViewById(R.id.linearPazireshPageBtn);
        txtPP_markazName = view.findViewById(R.id.txtPP_markazName);
        txtPP_doctorName = view.findViewById(R.id.txtPP_doctorName);
        txtPP_motakhasesName = view.findViewById(R.id.txtPP_motakhasesName);
        txtPP_datePP = view.findViewById(R.id.txtPP_datePP);
        radioGPPP_CodMeli = view.findViewById(R.id.radioGPPP_CodMeli);
        edtFrPP_Cod = view.findViewById(R.id.edtFrPP_Cod);
        btnPP_search = view.findViewById(R.id.btnPP_search);


        btnPP_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "اجرا شدن لودینگ", Toast.LENGTH_SHORT).show();
                linearPazireshPage2.setVisibility(View.VISIBLE);
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
        edtFrPP_phone = view.findViewById(R.id.edtFrPP_phone);
        linearFrPP_City = view.findViewById(R.id.linearFrPP_City);
        txtFrPP_city = view.findViewById(R.id.txtFrPP_city);
        edtFrPP_address = view.findViewById(R.id.edtFrPP_address);
        linearFrPP_bime = view.findViewById(R.id.linearFrPP_bime);
        txtFrPP_bime = view.findViewById(R.id.txtFrPP_bime);

        btnPP_previous = view.findViewById(R.id.btnPP_previous);
        btnPP_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearResTimes.setVisibility(View.VISIBLE);
                linearResTimesBtn.setVisibility(View.VISIBLE);
                linearPazireshPage.setVisibility(View.GONE);
                linearPazireshPageBtn.setVisibility(View.GONE);

            }
        });

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

                linearSelectFilters.setVisibility(View.VISIBLE);
                linearSelectFiltersBtn.setVisibility(View.VISIBLE);
                linearResTimes.setVisibility(View.GONE);
                linearResTimesBtn.setVisibility(View.GONE);

            }
        });


        String res = "{\"status\":\"yes\",\"message\":\"\",\"data\":[{\"dr_prg_hsp_mdc_spc_date_id\":\"1\",\"hsp_title\":\"بیمارستان شهید بهشتی\",\"shift_title\":\"صبح\",\"dr_name\":\"مهسا طاهری\",\"spc_title\":\"اطفال\",\"prg_date\":\"1399/01/06\",\"web_turn\":\"1\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"12\",\"hsp_title\":\"بیمارستان شهید حسینی\",\"shift_title\":\"عصر\",\"dr_name\":\"مهدی منصوری\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/02/07\",\"web_turn\":\"0\",\"status_type\":\"اتمام\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"13\",\"hsp_title\":\"بیمارستان شهید صالحی\",\"shift_title\":\"ش\",\"dr_name\":\"فرزاد اکبری\",\"spc_title\":\"عمومی\",\"prg_date\":\"1399/03/09\",\"web_turn\":\"5\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"14\",\"hsp_title\":\"بیمارستان شهید عابدزاده\",\"shift_title\":\"عصر\",\"dr_name\":\"علی ملکی\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/04/19\",\"web_turn\":\"3\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"15\",\"hsp_title\":\"بیمارستان  بهشتی\",\"shift_title\":\"صبح\",\"dr_name\":\"نجمه مقدم\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/09/05\",\"web_turn\":\"0\",\"status_type\":\"اتمام\"}]}";
        setRecycViewData(res);

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
                    public void setClick(int position, boolean canUse) {
                        if (canUse)
                            Toast.makeText(getContext(), position + "میتونی بری بعدی", Toast.LENGTH_LONG).show();
                         else
                            Toast.makeText(getContext(), "نمیتونی داییه", Toast.LENGTH_SHORT).show();

                    }
                };
                AdRecycResTimes adapterResTimes = new AdRecycResTimes(getContext(), arrayListResTimes, onclickInterface);
                rcycRT.setAdapter(adapterResTimes);

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
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.item_list_first_fr, null, false);
        Button btnFr = layout.findViewById(R.id.btnFr);
        btnFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        ArrayList arrayList = new ArrayList();
        ArrayList arrayListID = new ArrayList();

        if (tag.equals("city"))
            arrayList = new ArrayList(dataCity);
        else if (tag.equals("takhasos"))
            arrayList = new ArrayList(dataTakhasos);
        else if (tag.equals("darmonghah"))
            arrayList = new ArrayList(dataHospital);
        else if (tag.equals("time"))
            arrayList = new ArrayList(dataTime);
        else if (tag.equals("doctor"))
            arrayList = new ArrayList(dataDoctor);

        if (tag.equals("city"))
            arrayListID = new ArrayList(dataCityID);
        else if (tag.equals("takhasos"))
            arrayListID = new ArrayList(dataTakhasosID);
        else if (tag.equals("darmonghah"))
            arrayListID = new ArrayList(dataHospitalID);
        else if (tag.equals("time"))
            arrayListID = new ArrayList(dataTimeID);
        else if (tag.equals("doctor"))
            arrayListID = new ArrayList(dataDoctorID);

        if (arraylistSearchView.size() != 0)
            arraylistSearchView.clear();

        listSearchView = layout.findViewById(R.id.listviewFr);
        for (int i = 0; i < arrayList.size(); i++) {
            ModAlerts modAlerts = new ModAlerts(arrayList.get(i) + "", arrayListID.get(i) + "");
            arraylistSearchView.add(modAlerts);
        }
        adapterSearchView = new ListViewAdapter(getContext(), arraylistSearchView);
        listSearchView.setAdapter(adapterSearchView);

        editsearchSearchView = layout.findViewById(R.id.searchFr);
        editsearchSearchView.setOnQueryTextListener(this);


        listSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                TextView txttitle = ((LinearLayout) view).findViewById(R.id.txtTitle);
                TextView txtId = ((LinearLayout) view).findViewById(R.id.txtId);
                String title = txttitle.getText().toString();
                String id = txtId.getText().toString();

                if (tag.equals("city"))
                    txtFrRes_city.setText(title + "");
                else if (tag.equals("takhasos"))
                    txtFrRes_takhasos.setText(title + "");
                else if (tag.equals("darmonghah"))
                    txtFrRes_darmonghah.setText(title + "");
                else if (tag.equals("time"))
                    txtFrRes_time.setText(title + "");
                else if (tag.equals("doctor"))
                    txtFrRes_doctor.setText(title + "");

                if (tag.equals("city"))
                    cityId = id;
                else if (tag.equals("takhasos"))
                    takhasosId = id;
                else if (tag.equals("darmonghah"))
                    hospiralId = id;
                else if (tag.equals("time"))
                    timeId = id;
                else if (tag.equals("doctor"))
                    doctorId = id;

                alertDialog.dismiss();
            }
        });

        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
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
                linearSelectFilters.setVisibility(View.GONE);
                linearSelectFiltersBtn.setVisibility(View.GONE);
                linearResTimes.setVisibility(View.VISIBLE);
                linearResTimesBtn.setVisibility(View.VISIBLE);

            }
        });


        dataCity = new ArrayList();
        dataTakhasos = new ArrayList();
        dataHospital = new ArrayList();
        dataTime = new ArrayList();
        dataDoctor = new ArrayList();
        dataCityID = new ArrayList();
        dataTakhasosID = new ArrayList();
        dataHospitalID = new ArrayList();
        dataTimeID = new ArrayList();
        dataDoctorID = new ArrayList();


//------ Connection data for dropDowns

//        JSONObject object = new JSONObject();
//        try {
//            object.put("id", "-1");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        new setConnectionVolley(getContext(), "url", object
//        ).connectStringRequest(new setConnectionVolley.OnResponse() {
//            @Override
//            public void OnResponse(String response) {
//                setDropDownsData(response);
//            }
//        });

        String response = "{\"status\":\"yes\",\"message\":\"\",\"data\":{ \"hospital\":[{\"id\":\"0\",\"title\":\"تمامی بیمارستان ها\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"1\",\"title\":\"بیمارستان عمومی ماساچوست\"}],\"doctor\":[{\"id\":\"0\",\"title\":\"تمامی دکترها\"},{\"id\":\"4\",\"title\":\"علی علیزاده\"},{\"id\":\"1\",\"title\":\"حسین زارعی\"}],\"specialty\":[{\"id\":\"0\",\"title\":\"تمامی تخصص ها\"},{\"id\":\"6\",\"title\":\"اطفال\"},{\"id\":\"1\",\"title\":\"عفونی\"}],\"time\":[{\"id\":\"0\",\"title\":\"تمامی زمان ها\"},{\"id\":\"4\",\"title\":\"فردا\"},{\"id\":\"5\",\"title\":\"پسفردا\"},{\"id\":\"4\",\"title\":\"دیروز\"}],\"city\":[{\"id\":\"0\",\"title\":\"تمامی شهرها\"},{\"id\":\"1\",\"title\":\"اراک\"},{\"id\":\"2\",\"title\":\"امل\"},{\"id\":\"41\",\"title\":\"تهران\"}]}}";
        setDropDownsData(response);

    }

    private void setDropDownsData(String res) {
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

                for (int i = 0; i < arrayHospital.length(); i++) {
                    JSONObject object1 = arrayHospital.getJSONObject(i);
                    dataHospitalID.add(object1.getString("id"));
                    dataHospital.add(object1.getString("title"));
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
    private ListView listSearchView;
    private ListViewAdapter adapterSearchView;
    private SearchView editsearchSearchView;
    private ArrayList<ModAlerts> arraylistSearchView = new ArrayList<ModAlerts>();

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapterSearchView.filter(text);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private void defaultDataDropDown() {
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

}