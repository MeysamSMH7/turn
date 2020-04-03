package com.example.turn.Activity.Main.Fragment.FragmentLaghv;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.turn.Classes.setConnectionVolley;
import com.example.turn.R;

import org.json.JSONException;
import org.json.JSONObject;

public class frTab_laghv extends Fragment {

    EditText edtLaghv_codMeli;
    EditText edtLaghv_cod;
    TextView txtLaghv_chooseHospital;
    Button btnLaghv_search;
    TextView txtLaghv_drName;
    TextView txtLaghv_takhasos;
    TextView txtLaghv_hospital;
    TextView txtLaghv_nameNobat;
    TextView txtLaghv_date;
    Button btnLaghv_laghv;
    CardView cardViewLaghv;

    String hospitalId;

    public static frTab_laghv newInstance() {

        Bundle args = new Bundle();
        frTab_laghv fragment = new frTab_laghv();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_tab_laghv, container, false);
// cods here
        findViews(view);


        return view;
    }

    private void findViews(View view) {

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

        btnLaghv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codMeli = edtLaghv_codMeli.getText().toString();
                String cod = edtLaghv_cod.getText().toString();
                // TODO:
//                JSONObject object = new JSONObject();
//                try {
//                    object.put("pp_id", codMeli);
//                    object.put("rcp_no", cod);
//                    object.put("hsp_id", hospitalId);
//                    object.put("id", "0");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                new setConnectionVolley(getContext(),"link",object).connectStringRequest(new setConnectionVolley.OnResponse() {
//                    @Override
//                    public void OnResponse(String response) {
//                        getData(response);
//                    }
//                });

            }
        });

    }

    private void getData(String res) {
        try {
            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");
            if (status.equals("yes")) {
                String data = object.getString("data");
                JSONObject objectData = new JSONObject(data);

                String id = objectData.getString("id");
                String drName = objectData.getString("dr_name");
                String hospitalName = objectData.getString("sec_title");
                String date = objectData.getString("date_string");
                String nobatName = objectData.getString("prg_title");

                nobatName.replace(")", "");
                String[] tempAr = nobatName.split("\\(");
                String takhasos = tempAr[1];

                cardViewLaghv.setVisibility(View.VISIBLE);

                hospitalName = hospitalName.replace("بیمارستان", "");
                txtLaghv_hospital.setText("بیمارستان " + hospitalName);
                txtLaghv_drName.setText("دکتر " + drName);
                txtLaghv_takhasos.setText("تخصص: " + takhasos);
                txtLaghv_date.setText("تاریخ: " + date);
                txtLaghv_nameNobat.setText("نام نوبت: " + nobatName);

                btnLaghv_laghv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // TODO:
//                JSONObject object = new JSONObject();
//                try {
//                    object.put("id", id);
//                    object.put("pp_id", "0");
////                  object.put("rcp_no", "0");
////                  object.put("hsp_id", "0");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                new setConnectionVolley(getContext(),"link",object).connectStringRequest(new setConnectionVolley.OnResponse() {
//                    @Override
//                    public void OnResponse(String response) {
//                        getData(response);
//                    }
//                });

                    }
                });

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


}