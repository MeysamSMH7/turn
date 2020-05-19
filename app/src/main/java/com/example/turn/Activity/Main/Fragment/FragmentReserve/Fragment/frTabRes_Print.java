package com.example.turn.Activity.Main.Fragment.FragmentReserve.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.turn.Activity.Main.Adapter.setDataToFragment;
import com.example.turn.Classes.EnglishNumber;
import com.example.turn.Classes.ShowMessage;
import com.example.turn.Classes.setConnectionVolley;
import com.example.turn.R;

import org.json.JSONObject;

public class frTabRes_Print extends Fragment {
//print

    private TextView txtPrint_date;
    private TextView txtPrint_time;
    private TextView txtPrint_hours;
    private TextView txtPrint_shit;
    private TextView txtPrint_doctor;
    private TextView  txtPrint_spcTitle;
    private TextView txtPrint_type;
    private TextView txtPrint_typeBime;
    private TextView txtPrint_firstLastName;
    private TextView  txtPrint_message;
    private TextView txtPrint_price;
    private TextView txtPrint_bakhsh;
    private TextView txtPrint_codNobat;
    private TextView txtPrint_numberNobat;
    private ImageView imgPrint_barcod;
    private TextView txtPrint_ghabzNumber;
    private TextView txtPrint_time2;
    private TextView txtPrint_hospitalName;
    private TextView txtPrint_hospitalAddress;
    private TextView txtPrint_hospitalTell;
    private Button btnPrint_dismis;
    private Button btnPrint_pay;
    private String rcp_id = "";
    private String hsp_id = "";
    //------------------------------------
    private AlertDialog alertDialogLoding;

    public static frTabRes_Print newInstance() {

        Bundle args = new Bundle();
        frTabRes_Print fragment = new frTabRes_Print();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_reserve_print, container, false);

        onAttachToParentFragment(getParentFragment());
        loading();
        print(view);

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
    LinearLayout layoutMain;

    public void getDataFromFragment(String message) {

        if (message.equals("false")) {
            txtNoData.setVisibility(View.VISIBLE);
            layoutMain.setVisibility(View.GONE);
        } else {

            txtNoData.setVisibility(View.GONE);
            layoutMain.setVisibility(View.VISIBLE);

            String vPazireshlink = message;
            JSONObject jsonObject = new JSONObject();

            alertDialogLoding.show();
            new setConnectionVolley(getContext(), vPazireshlink, jsonObject).connectStringRequest(new setConnectionVolley.OnResponse() {
                @Override
                public void OnResponse(String response) {
                    if (alertDialogLoding.isShowing()) alertDialogLoding.dismiss();
                    setDataPrint(response);
                }
            });
        }

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
                hsp_id = jsonHospitalInfo.getString("hsp_id");

                JSONObject jsonTSF = objectData.getJSONObject("turnSpecification");
                String date = jsonTSF.getString("date_string") + "";
                String timeRes = jsonTSF.getString("rcp_time_str") + "";
                String timeHozor = jsonTSF.getString("prg_time") + "";
                String shift = jsonTSF.getString("shift_title") + "";
                String doctorName = jsonTSF.getString("dr_name") + "";
                String typeRes = jsonTSF.getString("srv_title") + "";
                String   spcTitle = jsonTSF.getString("spc_title") + "";
                rcp_id = jsonTSF.getString("rcp_id") + "";

                JSONObject bimeData = jsonTSF.getJSONObject("pInsurance");
                String bimeTitle = bimeData.getString("ins_title");

                JSONObject jsonPatient = objectData.getJSONObject("patient");
                String nameFamily = jsonPatient.getString("first_name") + " " + jsonPatient.getString("last_name");

                JSONObject jsonPay = objectData.getJSONObject("pay");
                String price = jsonPay.getString("pat_pay_str") + "";

                String pakhshName = jsonTSF.getString("sec_title") + "";
                String codNobat = jsonTSF.getString("rcp_no") + "";
                String numberNobat = jsonTSF.getString("seq_no") + "";
                String shoamreGhbz = jsonTSF.getString("csh_rcp_no") + "";
                String shaateNobat = jsonTSF.getString("rcp_time_str") + "";

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
                txtPrint_hospitalAddress.setText(hospitalAddress );
                txtPrint_hospitalTell.setText(hospitalTel );
                txtPrint_date.setText(date );
                txtPrint_shit.setText( shift );
                txtPrint_doctor.setText(doctorName);
                txtPrint_spcTitle.setText(spcTitle );
                txtPrint_type.setText(typeRes );
                txtPrint_typeBime.setText( bimeTitle );

                txtPrint_firstLastName.setText(nameFamily);
                txtPrint_price.setText(price);
                Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/b_titr.ttf");
                txtPrint_message.setTypeface(font);
           /*

                txtPrint_bakhsh.setText("نام بخش: " + Html.fromHtml("<b>" + pakhshName + "</b>"));
                txtPrint_bakhsh.setVisibility(View.GONE);
                txtPrint_codNobat.setText("کد پیگیری: " + Html.fromHtml("<b>" + codNobat + "</b>"));
                txtPrint_codNobat.setVisibility(View.GONE);
                txtPrint_numberNobat.setText("شماره نوبت: " + Html.fromHtml("<b>" + numberNobat + "</b>"));
                txtPrint_numberNobat.setVisibility(View.GONE);
                txtPrint_ghabzNumber.setText("شماره ی قبض: " + Html.fromHtml("<b>" + shoamreGhbz + "</b>" + ""));
                txtPrint_ghabzNumber.setVisibility(View.GONE);
                txtPrint_hours.setText("ساعت حضور بیمار: " + Html.fromHtml("<b>" + timeHozor + "</b>"));
                txtPrint_hours.setVisibility(View.GONE);
                imgPrint_barcod.setVisibility(View.GONE);
                txtPrint_time2.setText(Html.fromHtml("<b>" + timeRes + "</b>"));
                txtPrint_time2.setVisibility(View.GONE);
                String img = jsonTSF.getString("print_img");
                Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/b_titr.ttf");
                txtPrint_numberNobat.setTypeface(font);
                txtPrint_time2.setTypeface(font);
                if (!img.equals("")) {
                    if (!img.equals("null")) {
                        try {
                            String encodedDataString = img;
                            encodedDataString = encodedDataString.replace("data:image/png;base64,", "");
                            byte[] imageAsBytes = Base64.decode(encodedDataString.getBytes(), 0);
                            imgPrint_barcod.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }*/
            } else if (message.equals("Error creating window handle.")) {
//--------- back to previous

            } else {
//--------- back to previous

                new ShowMessage(getContext()).ShowMessType2_NoBtn(message, true, 2);
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

    private void print(View view) {
        try {

            txtNoData = view.findViewById(R.id.txtNoData);
            layoutMain = view.findViewById(R.id.linearPrint);

            txtPrint_date = view.findViewById(R.id.txtPrint_date);
            /*txtPrint_time = view.findViewById(R.id.txtPrint_time);
            txtPrint_hours = view.findViewById(R.id.txtPrint_hours);*/
            txtPrint_shit = view.findViewById(R.id.txtPrint_shit);
            txtPrint_doctor = view.findViewById(R.id.txtPrint_doctor);
            txtPrint_spcTitle = view.findViewById(R.id.txtPrint_spcTitle);
            txtPrint_type = view.findViewById(R.id.txtPrint_type);
            txtPrint_typeBime = view.findViewById(R.id.txtPrint_typeBime);
            txtPrint_firstLastName = view.findViewById(R.id.txtPrint_firstLastName);
            txtPrint_message = view.findViewById(R.id.txtPrint_message);
            txtPrint_price = view.findViewById(R.id.txtPrint_price);
      /*      txtPrint_bakhsh = view.findViewById(R.id.txtPrint_bakhsh);
            txtPrint_codNobat = view.findViewById(R.id.txtPrint_codNobat);
            txtPrint_numberNobat = view.findViewById(R.id.txtPrint_numberNobat);
            imgPrint_barcod = view.findViewById(R.id.imgPrint_barcod);*/
            txtPrint_ghabzNumber = view.findViewById(R.id.txtPrint_ghabzNumber);
            txtPrint_time2 = view.findViewById(R.id.txtPrint_time2);
            txtPrint_hospitalName = view.findViewById(R.id.txtPrint_hospitalName);
            txtPrint_hospitalAddress = view.findViewById(R.id.txtPrint_hospitalAddress);
            txtPrint_hospitalTell = view.findViewById(R.id.txtPrint_hospitalTell);
            btnPrint_pay = view.findViewById(R.id.btnPrint_pay);
            btnPrint_dismis = view.findViewById(R.id.btnPrint_dismis);
        Button    btnPrint_dismis1 = view.findViewById(R.id.btnPrint_dismis1);
btnPrint_dismis1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        msetDataToFragment.setDataToFragment("","fourthToFirst");
        layoutMain.setVisibility(View.GONE);
        txtNoData.setVisibility(View.VISIBLE);
    }
});

            btnPrint_dismis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
//--------- back to previous


                        txtPrint_hospitalName.setText("");
                        txtPrint_hospitalAddress.setText("");
                        txtPrint_hospitalTell.setText("");

                        txtPrint_date.setText("");
                        txtPrint_time.setText("");
                        txtPrint_hours.setText("");
                        txtPrint_shit.setText("");
                        txtPrint_doctor.setText("");
                        txtPrint_type.setText("");
                        txtPrint_typeBime.setText("");

                        txtPrint_firstLastName.setText("");
                        txtPrint_price.setText("");
                        txtPrint_ghabzNumber.setText("");
                        txtPrint_time2.setText("");

                        txtPrint_bakhsh.setText("");
                        txtPrint_codNobat.setText("");
                        txtPrint_numberNobat.setText("");
                        msetDataToFragment.setDataToFragment("","fourthToFirst");

                        layoutMain.setVisibility(View.GONE);
                        txtNoData.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            btnPrint_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String link = "http://nobat.mazums.ac.ir/Pay/Apprdr/?r=" + rcp_id + "&h=" + hsp_id;
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                        //         setAllDataToDefault();
                        msetDataToFragment.setDataToFragment("","fourthToFirst");

                        layoutMain.setVisibility(View.GONE);
                        txtNoData.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}