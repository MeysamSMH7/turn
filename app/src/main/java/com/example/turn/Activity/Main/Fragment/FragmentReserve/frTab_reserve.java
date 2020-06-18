package com.example.turn.Activity.Main.Fragment.FragmentReserve;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.turn.Activity.Main.Adapter.ViewPagerAdapterMain;
import com.example.turn.Activity.Main.Adapter.setDataToFragment;
import com.example.turn.Activity.Main.Fragment.FragmentReserve.Fragment.frTabRes_Filter;
import com.example.turn.Activity.Main.Fragment.FragmentReserve.Fragment.frTabRes_Print;
import com.example.turn.Activity.Main.Fragment.FragmentReserve.Fragment.frTabRes_RazireshPage;
import com.example.turn.Activity.Main.Fragment.FragmentReserve.Fragment.frTabRes_ReserveTime;
import com.example.turn.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class frTab_reserve extends Fragment implements setDataToFragment {

    public static frTab_reserve newInstance() {

        Bundle args = new Bundle();
        frTab_reserve fragment = new frTab_reserve();
        return fragment;
    }

    private TabLayout tl_tabLayout;
    private ViewPager vp_viewPager;
    private List<Fragment> fragments;
    private String[] titles;
    private int[] tabIcons = {
            R.drawable.ic_microphone_2,
            R.drawable.ic_microphone_2,
            R.drawable.ic_microphone_2,
            R.drawable.ic_microphone_2
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_tab_reserve, container, false);

        findViews(view);
        changeTabsFont(tl_tabLayout);
        initViewPager();

        titles = new String[]{"جستجو >>", "نوبت >>", "پذیرش >>", "چاپ"};
        customTab(titles);



        return view;
    }

    private void findViews(View view) {

        tl_tabLayout = (TabLayout) view.findViewById(R.id.tl_tabLayoutFR);
        vp_viewPager = (ViewPager) view.findViewById(R.id.vp_viewPagerFR);

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
                    Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "fonts/vazir.ttf");
                    TextView viewChild = (TextView) tabViewChild;
                    viewChild.setTypeface(type);
                    viewChild.setAllCaps(false);
                }
            }
        }
    }

    private void initViewPager() {

        fragments = new ArrayList<>();
        fragments.add(frTabRes_Filter.newInstance());
        fragments.add(frTabRes_ReserveTime.newInstance());
        fragments.add(frTabRes_RazireshPage.newInstance());
        fragments.add(frTabRes_Print.newInstance());

        titles = new String[]{"جستجو", "نوبت ها", "گرفتن نوبت", "چاپ"};

        ViewPagerAdapterMain adapter = new ViewPagerAdapterMain(getChildFragmentManager(), fragments, titles);
        vp_viewPager.setAdapter(adapter);
        tl_tabLayout.setupWithViewPager(vp_viewPager);
        int limit = (adapter.getCount() > 1 ? adapter.getCount() - 1 : 1);
        vp_viewPager.setOffscreenPageLimit(limit);
        vp_viewPager.setCurrentItem(0);

        tl_tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tl_tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tl_tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tl_tabLayout.getTabAt(3).setIcon(tabIcons[3]);


    }

    private void customTab(String titles[]) {
        titles = new String[]{"جستجو >>", "نوبت >>", "پذیرش >>", "چاپ"};
        for (int i = 0; i < tl_tabLayout.getTabCount(); i++) {
            LinearLayout tab = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_tablayout, null);
            TextView tab_label = (TextView) tab.findViewById(R.id.nav_label);
            ImageView tab_icon = (ImageView) tab.findViewById(R.id.nav_icon);
            tab_icon.setVisibility(View.GONE);
            tab_label.setText(titles[i]);

            tab_label.setTextColor(getResources().getColor(R.color.dim_gray));
            tab_icon.setImageResource(tabIcons[i]);


            // finally publish this custom view to navigation tab
            tl_tabLayout.getTabAt(i).setCustomView(tab);
        }
    }

    @Override
    public void setDataToFragment(String str, String tag) {
        if (tag.equals("firstToSecond")) {
            String tag2 = "android:switcher:" + R.id.vp_viewPagerFR + ":" + 1;
            frTabRes_ReserveTime fragmentByTag = (frTabRes_ReserveTime) getChildFragmentManager().findFragmentByTag(tag2);
            fragmentByTag.getDataFromFragment(str);
            vp_viewPager.setCurrentItem(1);

            tag2 = "android:switcher:" + R.id.vp_viewPagerFR + ":" + 2;
            frTabRes_RazireshPage fragmentByTag2 = (frTabRes_RazireshPage) getChildFragmentManager().findFragmentByTag(tag2);
            fragmentByTag2.getDataFromFragment("false");

            tag2 = "android:switcher:" + R.id.vp_viewPagerFR + ":" + 3;
            frTabRes_Print fragmentByTag3 = (frTabRes_Print) getChildFragmentManager().findFragmentByTag(tag2);
            fragmentByTag3.getDataFromFragment("false");


//            titles = new String[]{"جستجو1", "نوبت1 ها", "اخذ\u200Cنو1بت", "چا1پ"};
//            customTab(titles);



        } else if (tag.equals("secondToThird")) {
            String tag2 = "android:switcher:" + R.id.vp_viewPagerFR + ":" + 2;
            frTabRes_RazireshPage fragmentByTag = (frTabRes_RazireshPage) getChildFragmentManager().findFragmentByTag(tag2);
            fragmentByTag.getDataFromFragment(str);
            vp_viewPager.setCurrentItem(2);

            tag2 = "android:switcher:" + R.id.vp_viewPagerFR + ":" + 3;
            frTabRes_Print fragmentByTag3 = (frTabRes_Print) getChildFragmentManager().findFragmentByTag(tag2);
            fragmentByTag3.getDataFromFragment("false");

        } else if (tag.equals("thirdToFourth")) {

            String tag2 = "android:switcher:" + R.id.vp_viewPagerFR + ":" + 3;
            frTabRes_Print fragmentByTag = (frTabRes_Print) getChildFragmentManager().findFragmentByTag(tag2);
            fragmentByTag.getDataFromFragment(str);
            vp_viewPager.setCurrentItem(3);

        }else if (tag.equals("fourthToFirst")){

            String tag2 = "android:switcher:" + R.id.vp_viewPagerFR + ":" + 1;
            frTabRes_ReserveTime fragmentByTag = (frTabRes_ReserveTime) getChildFragmentManager().findFragmentByTag(tag2);
            fragmentByTag.getDataFromFragment("false");
            vp_viewPager.setCurrentItem(0);

            tag2 = "android:switcher:" + R.id.vp_viewPagerFR + ":" + 2;
            frTabRes_RazireshPage fragmentByTag2 = (frTabRes_RazireshPage) getChildFragmentManager().findFragmentByTag(tag2);
            fragmentByTag2.getDataFromFragment("false");

        }

    }

}