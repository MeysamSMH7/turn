package com.example.turn.Classes;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.turn.R;

import java.io.UnsupportedEncodingException;

public class onErrorListener {

    Context context;
    VolleyError error;

    public String onErrorListener(Context context, VolleyError error,String url) {
        this.context = context;
        this.error = error;
        String errorMessage = "";

        if (error instanceof NetworkError)
            errorMessage = context.getString(R.string.cantGetServer);
        else if (error instanceof ServerError)
            errorMessage = context.getString(R.string.cantGetServer);
        else if (error instanceof AuthFailureError)
            errorMessage = context.getString(R.string.cantGetServer);
        else if (error instanceof ParseError)
            errorMessage = context.getString(R.string.cantGetServer);
        else if (error instanceof TimeoutError)
            errorMessage = context.getString(R.string.cantGetServer);
        else
            errorMessage = context.getString(R.string.cantGetServer);

        NetworkResponse response = error.networkResponse;
        if (error instanceof ServerError && response != null) {
            try {
                String result = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                String a = result;

                ClipboardManager clipboard = (ClipboardManager)context. getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Source Text", url + "\n"+a);
                clipboard.setPrimaryClip(clip);

                Log.e("Error!!!", result + "");
//                Toast.makeText(context, result+"", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return errorMessage;
    }
}
