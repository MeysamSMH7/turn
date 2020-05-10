package com.example.turn.temp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.turn.Activity.Main.Adapter.onClickInterface;
import com.example.turn.R;

public class Activity_Test extends AppCompatActivity {

    GridView gridview;

    public static String[] osNameList = {
            "Android",
            "iOS",
            "Linux",
            "MacOS",
            "MS DOS",
            "Symbian",
            "Windows 10",
            "Windows XP",
    };
    public static int[] osImages = {
            R.drawable.ic_lungs,
            R.drawable.ic_lungs,
            R.drawable.ic_lungs,
            R.drawable.ic_lungs,
            R.drawable.ic_lungs,
            R.drawable.ic_lungs,
            R.drawable.ic_lungs,
            R.drawable.ic_lungs,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__test);


        gridview = (GridView) findViewById(R.id.customgrid);
        gridview.setAdapter(new CustomAdapter(this, osNameList, osImages));


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();




            }
        });

        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Activity_Test.this, "You Clicked " + osNameList[position] + "Long", Toast.LENGTH_SHORT).show();
                return true;

            }
        });

    }


}