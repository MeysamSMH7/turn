package com.example.turn.Activity.Main.Fragment.FragmentReserve.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.turn.Activity.Main.Adapter.onClickInterface;
import com.example.turn.Activity.Main.Adapter.setDataToFragment;
import com.example.turn.Activity.Main.Fragment.FragmentReserve.Adapter.AdRecycResTimes;
import com.example.turn.Activity.Main.Fragment.FragmentReserve.Model.ModResTime;
import com.example.turn.Classes.EnglishNumber;
import com.example.turn.Classes.ShowMessage;
import com.example.turn.Classes.onErrorListener;
import com.example.turn.Classes.setConnectionVolley;
import com.example.turn.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class frTabRes_ReserveTime extends Fragment {


    public static frTabRes_ReserveTime newInstance() {

        Bundle args = new Bundle();
        frTabRes_ReserveTime fragment = new frTabRes_ReserveTime();
        return fragment;
    }

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
    private int positionItemRecycleViewFilter = -1;

    private Boolean isScrollingFP = false;
    private int currentItemFP = 0, totalItemsFP = 0, scrollOutItemsFP = 0;
    private LinearLayoutManager rowManager;
    private Boolean boolCheckNetFP = false;
    private int pageNumber = 0;
    private boolean morePost = true;

    private AlertDialog alertDialogLoding;


    private String cityId = "";
    private String takhasosId = "";
    private String hospiralId = "";
    private String timeId = "";
    private String doctorId = "";
    private String lat_value_single = "";
    private String lng_value_single = "";


    private String doctorSt = "";
    private String exp = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fr_reserve_time, container, false);

        reservationTimes(view);
        loading();
        onAttachToParentFragment(getParentFragment());

        return view;
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

    TextView txtNoData;
    RelativeLayout layoutMain;

    public void getDataFromFragment(String message) {

        if (message.equals("false")) {
            txtNoData.setVisibility(View.VISIBLE);
            layoutMain.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            layoutMain.setVisibility(View.VISIBLE);

            try {
                JSONObject jsonObject = new JSONObject(message);
                cityId = jsonObject.getString("cityId");
                takhasosId = jsonObject.getString("takhasosId");
                hospiralId = jsonObject.getString("hospiralId");
                timeId = jsonObject.getString("timeId");
                cityId = jsonObject.getString("cityId");
                doctorId = jsonObject.getString("doctorId");
                doctorSt = jsonObject.getString("doctorSt");
                lat_value_single = jsonObject.getString("lat_value_single");
                lng_value_single = jsonObject.getString("lng_value_single");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (!alertDialogLoding.isShowing())
                alertDialogLoding.show();
            arrayListResTimes.clear();
            adapterResTimes.notifyDataSetChanged();
            pageNumber = 0;
            morePost = true;
            refreshRT.setRefreshing(false);

            doSearch(0);
        }
    }

    private void reservationTimes(final View view) {
        try {

            linearResTimes = view.findViewById(R.id.linearResTimes);
            linearResTimesBtn = view.findViewById(R.id.linearResTimesBtn);
            btnRT_previous = view.findViewById(R.id.btnRT_previous);
            rcycRT = view.findViewById(R.id.rcycRT);
            btnRT_filter = view.findViewById(R.id.btnRT_filter);
            refreshRT = view.findViewById(R.id.refreshRT);
            txtNoData = view.findViewById(R.id.txtNoData);
            layoutMain = view.findViewById(R.id.layoutMain);

            arrayListResTimes = new ArrayList();

            refreshRT.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    try {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                arrayListResTimes.clear();
                                adapterResTimes.notifyDataSetChanged();
                                pageNumber = 0;
                                morePost = true;
                                refreshRT.setRefreshing(false);
                                doSearch(pageNumber);
                            }
                        }, 2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                            doSearch(pageNumber);
                        }
                    }
                }
            });

            onClickInterface onclickInterface = new onClickInterface() {
                @Override
                public void setClick(final int position, boolean canUse, View view) {
                    if (canUse) {
//// alert tavafoghname
                        JSONObject object = new JSONObject();
                        dr_prg_hsp_mdc_spc_date_id = arrayListResTimes.get(position).id;
                        String[] temp = dr_prg_hsp_mdc_spc_date_id.split("_");
                        String vUrlremote = "http://nobat.mazums.ac.ir/TurnAppApi/search/IsRemoteVisit?hsp_id=" + temp[2] + "&prg_id=" + temp[1];
                        new setConnectionVolley(getContext(), vUrlremote, object
                        ).connectStringRequest(new setConnectionVolley.OnResponse() {
                            @Override
                            public void OnResponse(String response) {
                                try {
//b man begoo man anjam bedam to azyat mishi

                                    //  هد yetike اکیه
                                    JSONObject jsonObject1 = new JSONObject(response);

if (jsonObject1.getString("status").equals("yes")) {
    String is_remote = jsonObject1.getString("is_remote");
    final String RemoteVisitMessage = jsonObject1.getString("RemoteVisitMessage");
    String RemoteAgreementation = jsonObject1.getString("RemoteAgreementation");
    exp = jsonObject1.getString("exp");

    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.tavafoghname, null, false);
    Button btnTavafogh_no = layout.findViewById(R.id.btnTavafogh_no);
    final Button btnTavafogh_ok = layout.findViewById(R.id.btnTavafogh_ok);
    final TextView txtTavafogh = layout.findViewById(R.id.txtTavafogh);
    btnTavafogh_no.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            alertDialogTavafoghName.dismiss();
        }
    });
    btnTavafogh_ok.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {


                alertDialogTavafoghName.dismiss();
                dr_prg_hsp_mdc_spc_date_id = arrayListResTimes.get(position).id;
                if (exp.equals("")) {
                    msetDataToFragment.setDataToFragment(dr_prg_hsp_mdc_spc_date_id, "secondToThird");
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setTitle("");
                    builder1.setMessage(exp);
                    builder1.setPositiveButton(" تایید", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            msetDataToFragment.setDataToFragment(dr_prg_hsp_mdc_spc_date_id, "secondToThird");
                        }
                    });
                    builder1.setNegativeButton(" انصراف", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder1.create();
                    builder1.show();
                }


                //2_242_4781779718_45367_291_13990120
                //dr_prg_hsp_mdc_spc_date

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });


    builder.setView(layout);
    alertDialogTavafoghName = builder.create();
    alertDialogTavafoghName.show();

    if (is_remote.equals("1")) {
        alertDialogTavafoghName.dismiss();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setTitle("");
        builder1.setMessage(RemoteVisitMessage);
        builder1.setPositiveButton("پذیرفتن", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                txtTavafogh.setText(RemoteVisitMessage + "");
                alertDialogTavafoghName.show();
            }
        });
        builder1.setNegativeButton("عدم پذیرش", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                txtTavafogh.setText(getString(R.string.tavafogName) + "");
                alertDialogTavafoghName.show();
            }
        });
        builder1.create();
        builder1.show();

    } else
        txtTavafogh.setText(getString(R.string.tavafogName) + "");

}
else    {
    String txt = jsonObject1.getString("message");
    new ShowMessage(getContext()).ShowMessType2_NoBtn( txt+ "", true, 2);
}
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

//----- end of alert
                    } else
                        Toast.makeText(getContext(), "این نوبت به پایین رسیده است.", Toast.LENGTH_SHORT).show();
                }
            };
            adapterResTimes = new AdRecycResTimes(getContext(), arrayListResTimes, onclickInterface);
            rcycRT.setAdapter(adapterResTimes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doSearch(int pageNumber) {
        try {
            if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();
            if (morePost) {
                // here
                // TODO: Sent new id to link2 and change HOSPITAL NAMES --------------------------------------------------------
                //city_id:0,hsp_id:0,spc_id:0,first_name:'',last_name:'', date_period:0,page_number:0,item_per_page:10}
                // if (doctorId  0){
                //     String[] temp = txtFrRes_doctor.getText().toString()).split(" ");
                //   }
                String nameFamily = EnglishNumber.convert(doctorSt + "");
                nameFamily = nameFamily.replace(" ", "%20");
                String[] temp = nameFamily.split("-");
                JSONObject object = new JSONObject();

                alertDialogLoding.show();

                String vSearchUrl = "";
// باید توی دوتا url
                // بدوتا عدد رو بذاری
                if (doctorSt.equals(""))
                    vSearchUrl = "http://nobat.mazums.ac.ir/turnappApi/search/TurnList?hsp_id=" +
                            hospiralId + "&city_id=" + cityId + "&spc_id=" + takhasosId + "&date_period="
                            + timeId + "&page_number=" + pageNumber + "&first_name=" +"" + "&doctor_id="
                            + doctorId + "&item_per_page=" + "10" + "&last_name="  + "&lat_value_single=" +
                            lat_value_single + "&lng_value_single=" +lng_value_single;
                else
                    vSearchUrl = "http://nobat.mazums.ac.ir/turnappApi/search/TurnList?hsp_id=" +
                            hospiralId + "&city_id=" + cityId + "&spc_id=" + takhasosId + "&date_period="
                            + timeId + "&page_number=" + pageNumber + "&first_name=" + temp[1] + "&doctor_id="
                            + doctorId + "&item_per_page=" + "10" + "&last_name=" + temp[0]
                            + "&lat_value_single=" + lat_value_single + "&lng_value_single" +lng_value_single;

               /*
                try {
                    if (temp.length == 1)

                    else

                } catch (Exception e) {
                    vSearchUrl = "http://nobat.mazums.ac.ir/turnappApi/search/TurnList?hsp_id=" +
                            hospiralId + "&city_id=" + cityId + "&spc_id=" + takhasosId + "&date_period="
                            + timeId + "&page_number=" + pageNumber + "&last_name=" + EnglishNumber.convert(doctorSt)
                            + doctorId + "&item_per_page=" + "10";
                    e.printStackTrace();
                }
*/
                new setConnectionVolley(getContext(), vSearchUrl, object
                ).connectStringRequest(new setConnectionVolley.OnResponse() {
                    @Override
                    public void OnResponse(String response) {
                        if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();
                        setRecycViewData(response);

                    }
                });

                // TODO: Sent data and get ResTimes data (RecycleView) | link2--------------------------------------------------------
                // String res = "{\"status\":\"yes\",\"message\":\"\",\"data\":[{\"dr_prg_hsp_mdc_spc_date_id\":\"1\",\"hsp_title\":\"بیمارستان شهید بهشتی\",\"shift_title\":\"صبح\",\"dr_name\":\"مهسا طاهری\",\"spc_title\":\"اطفال\",\"prg_date\":\"1399/01/06\",\"web_turn\":\"1\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"12\",\"hsp_title\":\"بیمارستان شهید حسینی\",\"shift_title\":\"عصر\",\"dr_name\":\"مهدی منصوری\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/02/07\",\"web_turn\":\"0\",\"status_type\":\"اتمام\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"13\",\"hsp_title\":\"بیمارستان شهید صالحی\",\"shift_title\":\"شب\",\"dr_name\":\"فرزاد اکبری\",\"spc_title\":\"عمومی\",\"prg_date\":\"1399/03/09\",\"web_turn\":\"5\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"14\",\"hsp_title\":\"بیمارستان شهید عابدزاده\",\"shift_title\":\"عصر\",\"dr_name\":\"علی ملکی\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/04/19\",\"web_turn\":\"3\",\"status_type\":\"دریافت نوبت\"},{\"dr_prg_hsp_mdc_spc_date_id\":\"15\",\"hsp_title\":\"بیمارستان  بهشتی\",\"shift_title\":\"صبح\",\"dr_name\":\"نجمه مقدم\",\"spc_title\":\"داخلی\",\"prg_date\":\"1399/09/05\",\"web_turn\":\"0\",\"status_type\":\"اتمام\"}]}";
                // setRecycViewData(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                if (arrayData.length() == 0 && pageNumber == 0) {
                    //    previousPage(linearResTimes, linearResTimesBtn, linearSelectFilters, linearSelectFiltersBtn);
                    new ShowMessage(getContext()).ShowMessType2_NoBtn("اطلاعاتی وجود ندارد", true, 2);
                    morePost = true;
                } else if (arrayData.length() < 10 && pageNumber == 0) {
                    morePost = false;
                } else if (arrayData.length() == 0 && pageNumber != 0) {
                    Toast.makeText(getContext(), "اطلاعات بیشتری وجود ندارد", Toast.LENGTH_SHORT).show();
                    morePost = false;
                }

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
                    time.web_turn = web_turn;
                    arrayListResTimes.add(time);
                }
                adapterResTimes.notifyDataSetChanged();

            } else {
                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);
                //previousPage(linearResTimes, linearResTimesBtn, linearSelectFilters, linearSelectFiltersBtn);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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