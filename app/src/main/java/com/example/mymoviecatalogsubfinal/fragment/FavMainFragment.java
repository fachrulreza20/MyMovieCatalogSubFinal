package com.example.mymoviecatalogsubfinal.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mymoviecatalogsubfinal.R;
import com.example.mymoviecatalogsubfinal.adapter.MyPagerAdapter;


public class FavMainFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabs;

    public FavMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_fav_main, container, false);

        viewPager = rootview.findViewById(R.id.viewpager_main);
        tabs = rootview.findViewById(R.id.tabs_main);

        MyPagerAdapter fragmentAdapter = new MyPagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(fragmentAdapter);// = fragmentAdapter
        tabs.setupWithViewPager(viewPager);

        return rootview;
    }

}
