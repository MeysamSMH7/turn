package com.example.turn.Activity.Main.Fragment.FragmentEstelam;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turn.Activity.Main.Adapter.AdRecycPopUp;
import com.example.turn.Activity.Main.Fragment.FragmentEstelam.Adapter.AdRecycEstelam;
import com.example.turn.Activity.Main.Adapter.onClickInterface;
import com.example.turn.Activity.Main.Model.ModAlerts;
import com.example.turn.Activity.Main.Fragment.FragmentEstelam.Model.ModEstelam;
import com.example.turn.Classes.ShowMessage;
import com.example.turn.Classes.setConnectionVolley;
import com.example.turn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class frTab_estelam extends Fragment implements SearchView.OnQueryTextListener {


    private EditText edtEstelam_codMeli;
    private TextView txtEstelam_hospital;
    private Button btnEstelam_search;
    private RecyclerView rcycEstelam;
    private AlertDialog alertDialogHospital;

    private ArrayList dataHospital;
    private ArrayList dataHospitalID;
    private String hospiralId = "nothing";
    private ArrayList<ModEstelam> arrayListEstelam;
    private AlertDialog alertDialogFilter;
    private String hospitalId;

    public static frTab_estelam newInstance() {

        Bundle args = new Bundle();
        frTab_estelam fragment = new frTab_estelam();
        return fragment;
    }

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

    private AlertDialog alertDialogLoding;
    private NestedScrollView nestedMain;
    private LinearLayout linearPrint;

    private boolean estelamDone = false;
    private SharedPreferences preferences;
    private  int positionClick = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_tab_estelam, container, false);
// cods here
        loading();
        findViews(view);
        print(view);

        nestedMain.setVisibility(View.VISIBLE);
        linearPrint.setVisibility(View.GONE);

        return view;
    }

    private void findViews(View view) {
        preferences = getContext().getSharedPreferences("TuRn", 0);

        nestedMain = view.findViewById(R.id.nestedMain);
        edtEstelam_codMeli = view.findViewById(R.id.edtEstelam_codMeli);
        txtEstelam_hospital = view.findViewById(R.id.txtEstelam_hospital);
        btnEstelam_search = view.findViewById(R.id.btnEstelam_search);
        rcycEstelam = view.findViewById(R.id.rcycEstelam);

        dataHospital = new ArrayList();
        dataHospitalID = new ArrayList();
        arrayListEstelam = new ArrayList();

        txtEstelam_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogShow("darmonghah");
            }
        });

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
      /*  String response = "{\"status\":\"yes\",\"message\":\"\",\"data\":{ \"hospital\":[{\"id\":\"0\",\"title\":\"تمامی بیمارستان ها\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"4\",\"title\":\"بیمارستان شهید بهشتی\"},{\"id\":\"1\",\"title\":\"بیمارستان عمومی ماساچوست\"}],\"doctor\":[{\"id\":\"0\",\"title\":\"تمامی دکترها\"},{\"id\":\"4\",\"title\":\"علی علیزاده\"},{\"id\":\"1\",\"title\":\"حسین زارعی\"}],\"specialty\":[{\"id\":\"0\",\"title\":\"تمامی تخصص ها\"},{\"id\":\"6\",\"title\":\"اطفال\"},{\"id\":\"1\",\"title\":\"عفونی\"}],\"time\":[{\"id\":\"0\",\"title\":\"تمامی زمان ها\"},{\"id\":\"4\",\"title\":\"فردا\"},{\"id\":\"5\",\"title\":\"پسفردا\"},{\"id\":\"4\",\"title\":\"دیروز\"}],\"city\":[{\"id\":\"0\",\"title\":\"تمامی شهرها\"},{\"id\":\"1\",\"title\":\"اراک\"},{\"id\":\"2\",\"title\":\"امل\"},{\"id\":\"41\",\"title\":\"تهران\"}]}}";
        setDropDownsData(response);*/

        btnEstelam_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doSearch();


            }
        });
    }

    private void doSearch() {
        if (arrayListEstelam.size() != 0)
            arrayListEstelam.clear();
//                TODO: send cod meli and id hospital to link
        String cod = edtEstelam_codMeli.getText().toString();
        JSONObject obj = new JSONObject();
        try {
            obj.put("pp_id", cod);
            obj.put("hsp_id", hospiralId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String vEstelamUrl = "http://nobat.mazums.ac.ir/turnappApi/turn/turnlist?pPpId="
                + edtEstelam_codMeli.getText().toString() + "&pHspId=" + hospitalId;
        alertDialogLoding.show();
        new setConnectionVolley(getContext(), vEstelamUrl, obj).connectStringRequest(new setConnectionVolley.OnResponse() {
            @Override
            public void OnResponse(String response) {
                alertDialogLoding.dismiss();
                getData(response);
            }
        });

        estelamDone = true;
        checkForRefresh();

            /*    String res = "{\"status\":\"yes\",\"message\":\"\",\"data\":[{\"prg_turn_date_pp_rcp_pat_pay_pat_id\":\"1233\",\"prg_title\":\"دکتر مددی(داخلی)\",\"date_string\":\"1399/09/09\",\"status_title\":\"لغو شده\",\"status_detail\":\" وب\"},{\"prg_turn_date_pp_rcp_pat_pay_pat_id\":\"1233\",\"prg_title\":\"دکتر مددی(داخلی)\",\"date_string\":\"1399/09/09\",\"status_title\":\"لغو شده\",\"status_detail\":\" وب\"},{\"prg_turn_date_pp_rcp_pat_pay_pat_id\":\"1233\",\"prg_title\":\"دکتر مددی(داخلی)\",\"date_string\":\"1399/09/09\",\"status_title\":\"لغو شده\",\"status_detail\":\" وب\"},{\"prg_turn_date_pp_rcp_pat_pay_pat_id\":\"1233\",\"prg_title\":\"دکتر مددی(داخلی)\",\"date_string\":\"1399/09/09\",\"status_title\":\"لغو شده\",\"status_detail\":\" وب\"}]}";
                getData(res);*/
    }

    private void checkForRefresh() {

        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (estelamDone) {
                        boolean isLaghvDone = preferences.getBoolean("laghV", false);
                        if (isLaghvDone)
                            doSearch();
                    }
                    checkForRefresh();
                }
            }, 1000);
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

            } else
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.loading, null, false);

        builder.setView(layout);
        alertDialogLoding = builder.create();
        alertDialogLoding.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    private void getData(String res) {
        try {
            JSONObject object = new JSONObject(res);
            String status = object.getString("status");
            String message = object.getString("message");
            if (status.equals("yes")) {
                String data = object.getString("data");
                JSONObject objectData1 = new JSONObject(data);
                JSONArray arrayData = objectData1.getJSONArray("othTurnList");

                for (int i = 0; i < arrayData.length(); i++) {

                    JSONObject jsonObject = arrayData.getJSONObject(i);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("patient");
                    JSONObject jsonObject2 = jsonObject.getJSONObject("pay");
                    ModEstelam modEstelam = new ModEstelam();
                    //modEstelam.prg_turn_date_pp_rcp_pat_pay_pat_id = jsonObject.getString("prg_turn_date_pp_rcp_pat_pay_pat_id"); // split
                    String temp = jsonObject.getString("prg_title"); // split docotr and hospital title in chiee?bezar dakhele json bebinim
                    temp = temp.replace(")", "");
                    String[] tempAr = temp.split("\\(");
                    modEstelam.dr_name = tempAr[0];
                    modEstelam.spc_title = tempAr[1];
               /*     prg_title:"",date_string:"",status_title:"",status_detail:"",
                            prg_id:0,turn_date:"",pp_id:"",rcp_id:0,pat_id:0,pat_pay:0,hsp_id:0}*/
                    modEstelam.prg_title = jsonObject.getString("prg_title");
                    modEstelam.date_string = jsonObject.getString("date_string");
                    modEstelam.status_title = jsonObject.getString("status_title");
                    modEstelam.status_detail = jsonObject.getString("status_detail");
                    //ETELAATE MASALAN DOKME
                    modEstelam.prg_id = jsonObject.getString("prg_id");
                    modEstelam.turn_date = jsonObject.getString("turn_date");
                    modEstelam.pp_id = jsonObject.getString("pp_id");
                    modEstelam.rcp_id = jsonObject.getString("rcp_id");
                    modEstelam.pat_id = jsonObject1.getString("pat_id");
                    modEstelam.pat_pay = jsonObject2.getString("pat_pay");
                    modEstelam.hsp_id = jsonObject.getString("hsp_id");
                    arrayListEstelam.add(modEstelam);
                }

                AdRecycEstelam adRecycEstelam = new AdRecycEstelam(getContext(), arrayListEstelam, new onClickInterface() {
                    @Override
                    public void setClick(int position, boolean canUse, View view) {

// TODO: send "prg_turn_date_pp_rcp_pat_pay_pat_id" to show new reserve page

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("dr_id", "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (edtEstelam_codMeli.getText().toString().length() != 10) {
                            Toast.makeText(getContext(), "کد ملی نا معتبر است", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (hospitalId.equals(dataHospitalID.get(0))) {
                            Toast.makeText(getContext(), "لطفا یک بیمارستان انتخاب کنید", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ModEstelam modEstelam = arrayListEstelam.get(position);
positionClick =  position;
                        nestedMain.setVisibility(View.GONE);
                        linearPrint.setVisibility(View.VISIBLE);
                        String vPazireshlink = "http://nobat.mazums.ac.ir/TurnAppApi/turn/showturn?"
                                + "prg_id=" + modEstelam.prg_id
                                + "&hsp_id=" + modEstelam.hsp_id
                                + "&rcp_id=" + modEstelam.rcp_id
                                + "&turn_date=" + modEstelam.turn_date
                                + "&pp_id=" + edtEstelam_codMeli.getText().toString();
                        alertDialogLoding.show();
                        new setConnectionVolley(getContext(), vPazireshlink, jsonObject).connectStringRequest(new setConnectionVolley.OnResponse() {
                            @Override
                            public void OnResponse(String response) {
                                alertDialogLoding.dismiss();
                                setDataPrint(response);
                            }
                        });
                    }
                });
                rcycEstelam.setAdapter(adRecycEstelam);

            } else
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void print(View view) {

        linearPrint = view.findViewById(R.id.linearPrint);
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
        btnPrint_dismis = view.findViewById(R.id.btnPrint_dismis);


        btnPrint_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (positionClick != -1) {
                    String link = "http://nobat.mazums.ac.ir/Pay/Apprdr/?r=" + arrayListEstelam.get(positionClick).rcp_id + "&h=" + hospiralId;
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                }
            }
        });


        btnPrint_dismis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nestedMain.setVisibility(View.VISIBLE);
                linearPrint.setVisibility(View.GONE);
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
                String hospitalName = jsonHospitalInfo.getString("hsp_title") + "";
                String hospitalAddress = jsonHospitalInfo.getString("hsp_addr") + "";
                String hospitalTel = jsonHospitalInfo.getString("hsp_tel") + "";

                JSONObject jsonTSF = objectData.getJSONObject("turnSpecification");
                String date = jsonTSF.getString("date_string") + "";
                String timeRes = jsonTSF.getString("rcp_time_str") + "";
                String timeHozor = jsonTSF.getString("prg_time") + "";
                String shift = jsonTSF.getString("shift_title") + "";
                String doctorName = jsonTSF.getString("dr_name") + "";
                String typeRes = jsonTSF.getString("srv_title") + "";

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


            } else
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

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
                    hospitalId = id;  // the last hsp_id save in here
                    txtEstelam_hospital.setText(title + "");
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

    //  SearchView -------------------------------------------------------------
    private com.example.turn.Activity.Main.Adapter.AdRecycPopUp adRecycPopUp;
    private SearchView editsearchSearchView;
    private ArrayList<ModAlerts> arraylistSearchView = new ArrayList<ModAlerts>();

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