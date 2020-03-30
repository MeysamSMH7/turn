package com.example.turn.Activity.Main;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.turn.Activity.Main.Adapter.ViewPagerAdapter;
import com.example.turn.Activity.Main.Fragment.frTab_estelam;
import com.example.turn.Activity.Main.Fragment.frTab_laghv;
import com.example.turn.Activity.Main.Fragment.frTab_reserve;
import com.example.turn.R;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Activity_Main_Turn extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Context context = this;
    private Toolbar toolbar;

    private TabLayout tl_tabLayout;
    private ViewPager vp_viewPager;
    private List<Fragment> fragments;
    private String[] titles;
    private int[] tabIcons = {
            R.drawable.ic_num_1,
            R.drawable.ic_num_2,
            R.drawable.ic_num_1
    };
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_trun);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        findViews();
        changeTabsFont(tl_tabLayout);
        initViewPager();


        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("FirstTime?", false);
        editor.apply();

    }


    private void findViews() {
        preferences = getSharedPreferences("TuRn", 0);
        toolbar = findViewById(R.id.toolbar);
        tl_tabLayout = (TabLayout) findViewById(R.id.tl_tabLayout);
        vp_viewPager = (ViewPager) findViewById(R.id.vp_viewPager);
    }

    protected void changeTabsFont(TabLayout tabLayout) {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof AppCompatTextView) {
                    Typeface type = Typeface.createFromAsset(this.getAssets(), "fonts/vazir.ttf");
                    TextView viewChild = (TextView) tabViewChild;
                    viewChild.setTypeface(type);
                    viewChild.setAllCaps(false);
                }
            }
        }
    }

    private void initViewPager() {

        fragments = new ArrayList<>();
        fragments.add(frTab_estelam.newInstance());
        fragments.add(frTab_laghv.newInstance());
        fragments.add(frTab_reserve.newInstance());

        titles = new String[]{"لغو", "استعلام", "رزرو"};

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vp_viewPager.setAdapter(adapter);
        tl_tabLayout.setupWithViewPager(vp_viewPager);
        int limit = (adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);
        vp_viewPager.setOffscreenPageLimit(limit);
        vp_viewPager.setCurrentItem(2);

        tl_tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tl_tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tl_tabLayout.getTabAt(2).setIcon(tabIcons[2]);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

