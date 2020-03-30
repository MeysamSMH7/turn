package com.example.turn.Activity.Main.Fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.turn.Activity.Main.Model.ModAlerts;
import com.example.turn.Activity.Main.Adapter.ListViewAdapter;
import com.example.turn.R;

import java.util.ArrayList;

public class frTab_reserve extends Fragment implements
        SearchView.OnQueryTextListener {


//  linearSelectFilters
private LinearLayout linearSelectFilters;

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

    private AlertDialog alertDialog;

    public static frTab_reserve newInstance() {

        Bundle args = new Bundle();
        frTab_reserve fragment = new frTab_reserve();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_tab_reserve, container, false);
// cods here
        findViews(view);
        clicks();

        return view;
    }

    private void clicks() {

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


    }

//    SearchView
    private void alertDialogShow(String tag) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.item_list_first_fr, null, false);

        if (tag.equals("city"))
            animalNameListSearchView = new String[]{"اون شهر", "این شهر", "اراک", "چیز"};
        else if (tag.equals("takhasos"))
            animalNameListSearchView = new String[]{"سر", "سندرم دست بی قرار", "یه سندرم دیگه", "چیز"};
        else if (tag.equals("darmonghah"))
            animalNameListSearchView = new String[]{"درمانگاه سر کوچه", "درمانگاه سر خیابون", "درمانگاه بغل دستمون", "چیز"};
        else if (tag.equals("time"))
            animalNameListSearchView = new String[]{"دیروز :|", "هفته ی بعد", "ماه بعد", "چیز"};
        else if (tag.equals("doctor"))
            animalNameListSearchView = new String[]{"دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر مهسا طاهری", "دکتر سیدمیثم حسینی", "دکتر چیز زاده", "چیز"};

        if (arraylistSearchView.size() != 0)
            arraylistSearchView.clear();

        listSearchView = layout.findViewById(R.id.listviewFr);
        for (int i = 0; i < animalNameListSearchView.length; i++) {
            ModAlerts modAlerts = new ModAlerts(animalNameListSearchView[i]);
            arraylistSearchView.add(modAlerts);
        }
        adapterSearchView= new ListViewAdapter(getContext(), arraylistSearchView);
        listSearchView.setAdapter(adapterSearchView);

        editsearchSearchView = layout.findViewById(R.id.searchFr);
        editsearchSearchView.setOnQueryTextListener(this);

        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void findViews(View view) {
        linearSelectFilters = view.findViewById(R.id.linearSelectFilters);
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
//-------------------------




    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    //  SearchView
    private ListView listSearchView;
    private ListViewAdapter adapterSearchView;
    private SearchView editsearchSearchView;
    private String[] animalNameListSearchView;
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


}