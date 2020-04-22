package com.example.turn.Activity.Main.Fragment.FragmentLaghv;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turn.Activity.Main.Adapter.AdRecycPopUp;
import com.example.turn.Activity.Main.Adapter.onClickInterface;
import com.example.turn.Activity.Main.Model.ModAlerts;
import com.example.turn.Classes.ShowMessage;
import com.example.turn.Classes.setConnectionVolley;
import com.example.turn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class frTab_laghv extends Fragment implements SearchView.OnQueryTextListener {

    private EditText edtLaghv_codMeli;
    private EditText edtLaghv_cod;
    private TextView txtLaghv_chooseHospital;
    private Button btnLaghv_search;
    private TextView txtLaghv_drName;
    private TextView txtLaghv_takhasos;
    private TextView txtLaghv_hospital;
    private TextView txtLaghv_nameNobat;
    private TextView txtLaghv_date;
    private Button btnLaghv_laghv;
    private CardView cardViewLaghv;

    private AlertDialog alertDialogFilter;
    private String hospitalId;
    private String prg_id;
    private String turn_date;
    private String turn_time;
    private String q_type;

    private ArrayList dataHospital = new ArrayList();
    private ArrayList dataHospitalID = new ArrayList();

    private AlertDialog alertDialogLoding;
    private boolean laghvDone = false;

    public static frTab_laghv newInstance() {

        Bundle args = new Bundle();
        frTab_laghv fragment = new frTab_laghv();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_tab_laghv, container, false);
// cods here
        loading();
        findViews(view);

        return view;
    }

    private void findViews(View view) {
        try {
            edtLaghv_codMeli = view.findViewById(R.id.edtLaghv_codMeli);
            edtLaghv_cod = view.findViewById(R.id.edtLaghv_cod);
            txtLaghv_chooseHospital = view.findViewById(R.id.txtLaghv_chooseHospital);
            btnLaghv_search = view.findViewById(R.id.btnLaghv_search);
            txtLaghv_drName = view.findViewById(R.id.txtLaghv_drName);
            txtLaghv_takhasos = view.findViewById(R.id.txtLaghv_takhasos);
            txtLaghv_hospital = view.findViewById(R.id.txtLaghv_hospital);
            txtLaghv_nameNobat = view.findViewById(R.id.txtLaghv_nameNobat);
            txtLaghv_date = view.findViewById(R.id.txtLaghv_date);
            btnLaghv_laghv = view.findViewById(R.id.btnLaghv_laghv);
            cardViewLaghv = view.findViewById(R.id.cardViewLaghv);
            cardViewLaghv.setVisibility(View.GONE);

            txtLaghv_chooseHospital.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogShow("darmonghah");
                }
            });

            btnLaghv_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String codMeli = edtLaghv_codMeli.getText().toString();
                        String cod = edtLaghv_cod.getText().toString();
                        // TODO:
                        JSONObject object = new JSONObject();
                        try {
                            object.put("pp_id", codMeli);
                            object.put("rcp_no", cod);
                            object.put("hsp_id", hospitalId);
                            object.put("id", "0");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (codMeli.length() != 10) {
                            Toast.makeText(getContext(), "کد ملی نا معتبر است", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (cod.equals("")) {
                            Toast.makeText(getContext(), "کد پیگیری نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (hospitalId.equals(dataHospitalID.get(0))) {
                            Toast.makeText(getContext(), "لطفا یک بیمارستان انتخاب کنید", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String vLghvUrl = "http://nobat.mazums.ac.ir/TurnAppApi/turn/cancelTurnList?pp_id=" +
                                edtLaghv_codMeli.getText().toString() + "&rcp_no=" + edtLaghv_cod.getText().toString() + "&hsp_id=" + hospitalId;
                        alertDialogLoding.show();
                        new setConnectionVolley(getContext(), vLghvUrl, object).connectStringRequest(new setConnectionVolley.OnResponse() {
                            @Override
                            public void OnResponse(String response) {
                                alertDialogLoding.dismiss();
                                getDataForLagv(response);
                            }
                        });

              /*  String data = "{\"status\":\"yes\",\"message\":\"\",\"data\":{\"id\":\"12334\",\"dr_name\":\"علی کریمی\",\"date_string\":\"1399/03/03\",\"prg_title\":\"کریمی(داخلی)\",\"hsp_title\":\"بیمارستان شهید بهشتی\"}}";
                getData(data);*/

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            btnLaghv_laghv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (!laghvDone) {
                            // TODO:
                            JSONObject object = new JSONObject();
                            try {
                                object.put("pp_id", "0");
                                object.put("rcp_no", "0");
                                object.put("hsp_id", "0");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (edtLaghv_codMeli.getText().toString().length() != 10) {
                                Toast.makeText(getContext(), "کد ملی نا معتبر است", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (edtLaghv_codMeli.getText().toString().equals("")) {
                                Toast.makeText(getContext(), "کد پیگیری نمیتواند خالی باشد", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (hospitalId.equals(dataHospitalID.get(0))) {
                                Toast.makeText(getContext(), "لطفا یک بیمارستان انتخاب کنید", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String vLaghvUrl = "http://nobat.mazums.ac.ir/turnappApi/turn/cancelTurn?pp_id="
                                    + edtLaghv_codMeli.getText().toString() +
                                    "&prg_id=" + prg_id +
                                    "&turn_date=" + turn_date +
                                    "&turn_time=" + turn_time +
                                    "&hsp_id=" + hospitalId +
                                    "&q_type=" + q_type +
                                    "&rcp_id=" + edtLaghv_cod.getText().toString();
                            alertDialogLoding.show();

                            new setConnectionVolley(getContext(), vLaghvUrl, object).connectStringRequest(new setConnectionVolley.OnResponse() {
                                @Override
                                public void OnResponse(String response) {
                                    if (alertDialogLoding.isShowing())
                                        alertDialogLoding.dismiss();
                                    new ShowMessage(getContext()).ShowMessType2_NoBtn("نوبت شما با موفقیت لغو شد", true, 2);

                                    try {
                                        laghvDone = true;
                                        btnLaghv_laghv.setText("لغو شده");

                                        SharedPreferences preferences = getContext().getSharedPreferences("TuRn", 0);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putBoolean("laghV", true);
                                        editor.apply();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "این نوبت قبلا لغو شده است.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

//TODO------ Connection data for dropDowns link1

            JSONObject object = new JSONObject();
            try {
                //     object.put("hospital", "-1");
                //  object.put("city", "-1");
                // object.put("specialty", "-1");

            } catch (Exception e) {
                e.printStackTrace();
            }

            alertDialogLoding.show();
            new setConnectionVolley(getContext(), "http://nobat.mazums.ac.ir/turnappApi/turn/getHosptals", object
            ).connectStringRequest(new setConnectionVolley.OnResponse() {
                @Override
                public void OnResponse(String response) {
                    alertDialogLoding.dismiss();
                    setDropDownsData(response);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
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

                for (int i = 0; i < arrayHospital.length(); i++) {
                    JSONObject object1 = arrayHospital.getJSONObject(i);
                    dataHospitalID.add(object1.getString("id"));
                    dataHospital.add(object1.getString("title"));
                }
                txtLaghv_chooseHospital.setText(dataHospital.get(0) + "");
                hospitalId = dataHospitalID.get(0) + "";

            } else
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getDataForLagv(String res) {
        try {
            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");
            if (message.equals("")) {
                if (status.equals("yes")) {
                    String data = object.getString("data");
                    JSONObject objectData1 = new JSONObject(data);
                    //  JSONObject objectData = objectData1.getJSONObject("othTurnList");
                    JSONArray jsonArray = objectData1.getJSONArray("othTurnList");
                    JSONObject objectData = jsonArray.getJSONObject(0);
                    JSONObject objecthspTitle = objectData.getJSONObject("cityhsp");

           /*     data:{
                    othturnList:[{
                        sec_title:"",date_string:"",prg_title:"",dr_name:"",//نمایش مشخصات نوبت
                                prg_id:0,turn_date:"",turn_time:"",hsp_id:0,q_type:0} //با زدن روی دکه لغو باید این اطلاعات رو بفرستی تا من بتونم لغو کنم
                }]
            }*/


                    String drName = objectData.getString("dr_name");
                    String hospitalName = objecthspTitle.getString("hsp_title");
                    String date = objectData.getString("date_string");
                    String nobatName = objectData.getString("prg_title");
                    prg_id = objectData.getString("prg_id");
                    turn_date = objectData.getString("turn_date");
                    turn_time = objectData.getString("turn_time");
                    q_type = objectData.getString("q_type");

                    if (drName.equals("null"))
                        drName = "";
                    if (hospitalName.equals("null"))
                        hospitalName = "";
                    if (date.equals("null"))
                        date = "";
                    if (nobatName.equals("null"))
                        nobatName = "";
                    if (prg_id.equals("null"))
                        prg_id = "";
                    if (turn_date.equals("null"))
                        turn_date = "";
                    if (turn_time.equals("null"))
                        turn_time = "";
                    if (q_type.equals("null"))
                        q_type = "";

                    String temp = nobatName;
                    temp = temp.replace(")", "");
                    String[] tempAr = temp.split("\\(");
                    String takhasos = "";
                    if (tempAr.length != 1)
                        takhasos = tempAr[1];

                    cardViewLaghv.setVisibility(View.VISIBLE);

                    hospitalName = hospitalName.replace("بیمارستان", "");
                    txtLaghv_hospital.setText("بیمارستان " + hospitalName);
                    txtLaghv_drName.setText("دکتر " + drName);
                    txtLaghv_takhasos.setText("تخصص: " + takhasos);
                    txtLaghv_date.setText("تاریخ: " + date);
                    txtLaghv_nameNobat.setText("نام نوبت: " + nobatName);
                    laghvDone = false;

                } else
                    new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);
            } else
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void alertDialogShow(final String tag) {
        try {
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
            if (tag.equals("darmonghah")) {
                arrayList = new ArrayList(dataHospital);
                arrayListID = new ArrayList(dataHospitalID);
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
                    if (tag.equals("darmonghah")) {
                        txtLaghv_chooseHospital.setText(title + "");
                        hospitalId = id;  // the last hsp_id save in here
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
}