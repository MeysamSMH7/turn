package com.example.turn.Activity.Wellcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.turn.Activity.Main.Activity_Main_Turn;
import com.example.turn.R;

public class Activity_Welcome_Turn extends AppCompatActivity {

    ViewPager view_pager;
    LinearLayout dotsLayout;
    Button btnBack, btnNext;
    int[] layouts;
    TextView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_turn);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        view_pager =  findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.dotsLayout);
        btnNext =  findViewById(R.id.btnNext);
        btnBack =  findViewById(R.id.btnBack);

        layouts = new int[]{
                R.layout.wellcomeslider_ds_1,
                R.layout.wellcomeslider_ds_2,
                R.layout.wellcomeslider_ds_3
        };

        AddBottomDots(0);

        btnBack.setVisibility(View.GONE);

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        view_pager.setAdapter(myViewPagerAdapter);
        view_pager.addOnPageChangeListener(onPageChangeListener);

        btnBack.setOnClickListener(btnBackOnClickListener);
        btnNext.setOnClickListener(btnNextOnClickListener);

    }

    View.OnClickListener btnBackOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int currentPage = view_pager.getCurrentItem();
            if (currentPage < layouts.length) {
                view_pager.setCurrentItem(currentPage - 1);
            }
        }
    };

    View.OnClickListener btnNextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int currentPage = view_pager.getCurrentItem();

            if (currentPage == 3)
                btnNext.setText("ورود");
            else
                btnNext.setText("بعدی");

            if (currentPage < layouts.length - 1) {
                view_pager.setCurrentItem(currentPage + 1);
            } else {
                Intent intent = new Intent(Activity_Welcome_Turn.this, Activity_Main_Turn.class);
                startActivity(intent);
                finish();
            }
        }
    };

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            AddBottomDots(position);

            //First Page
            if (position == 0) {
                btnBack.setVisibility(View.GONE);
            } else {
                btnBack.setVisibility(View.VISIBLE);
            }
            if (position == 2)
                btnNext.setText("ورود");
            else
                btnNext.setText("بعدی");

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    void AddBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_light_active);
        int[] colorsInActive = getResources().getIntArray(R.array.array_dark_inactive);

        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInActive[i]);
            dotsLayout.addView(dots[i]);
        }

        dots[currentPage].setTextColor(colorsActive[currentPage]);
    }


    class MyViewPagerAdapter extends PagerAdapter {

        LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

    }
}