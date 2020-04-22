package com.example.turn.Classes;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class setConnectionVolley {

    private Context context;
    private String url;
    private JSONObject jsonObject;

    public setConnectionVolley(Context context, String url, JSONObject jsonObject) {
        this.context = context;
        this.url = url;
        this.jsonObject = jsonObject;
    }

    public void connectStringRequest(final OnResponse OnResponse) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                OnResponse.OnResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String txt = new onErrorListener().onErrorListener(context, error);
                new ShowMessage(context).ShowMessType2_NoBtn( txt+ "", true, 2);
                OnResponse.OnResponse("null");

                ClipboardManager clipboard = (ClipboardManager)context. getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Source Text", url + "\n"+txt);
                clipboard.setPrimaryClip(clip);

            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("clientPost", jsonObject.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        CheckInternet internet = new CheckInternet(context);
        if (internet.CheckNetworkConnection()) {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } else {
            OnResponse.OnResponse("net");
            new ShowMessage(context).ShowMessType2_NetError();
        }
    }

    public interface OnResponse {
        void OnResponse(String response);
    }

}
