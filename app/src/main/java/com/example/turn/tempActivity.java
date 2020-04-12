package com.example.turn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.net.URLEncoder;

public class tempActivity extends AppCompatActivity {

    final String TAG = "MyTag";
    StringRequest stringRequest; // Assume this exists.
    RequestQueue requestQueue;  // Assume this exists.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);


        stringRequest.setTag(TAG);
        requestQueue.add(stringRequest);

        String date = "\"<input type=\\\"hidden\\\" name=\\\"token\\\" value=\\\"qeva6h7NrD73DcZT7CNuu-N_yhYN5wO5IwZb3XILjes\\\"><input type=\\\"hidden\\\" name=\\\"language\\\" value=\\\"fa\\\">\"";


        WebView webview = findViewById(R.id.webView);
//        webview.loadData(date, "text/html; charset=UTF-8", null);
        webview.postUrl("https://sep2.shaparak.ir/_ipgw_//payment/",date.getBytes());

    }
}







//        Intent intent = new Intent("com.example.turn.tempActivity");
        /*
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        Bundle bundle = new Bundle();
        bundle.putString("msg_from_browser", "Launched from Browser");
        intent.putExtras(bundle);
        Toast.makeText(this, "دمت گرم", Toast.LENGTH_SHORT).show();
        Log.d("mobikul-->", intent.toUri(Intent.URI_INTENT_SCHEME));
//        Toast.makeText(this, intent.getStringExtra("msg_from_browser")+"", Toast.LENGTH_SHORT).show();

*/
