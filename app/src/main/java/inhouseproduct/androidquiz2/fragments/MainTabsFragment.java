package inhouseproduct.androidquiz2.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import inhouseproduct.androidquiz2.R;
import inhouseproduct.androidquiz2.adapter.PagerAdapter;


/**
 * Created by Talha TBT on 11/22/2017.
 */

public class MainTabsFragment extends Fragment {



    Activity activity;
    ViewPager pager;
    public static boolean profileFirst;
    TabLayout tabLayout;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tabs,container, false);

        pager= (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout= (TabLayout) view.findViewById(R.id.tabLayout);

        activity=getActivity();

        Bundle bundle=getArguments();


        FragmentManager manager=getChildFragmentManager();

        //object of PagerAdapter passing fragment manager object as a parameter of constructor of PagerAdapter class.
        PagerAdapter adapter=new PagerAdapter(manager);

        //set Adapter to view pager
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);


        //set tablayout with viewpager
        tabLayout.setupWithViewPager(pager);

        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Setting tabs from adpater
        tabLayout.setTabsFromPagerAdapter(adapter);

        tabLayout.getTabAt(0).setText("Login");
        tabLayout.getTabAt(1).setText("SignUp");


        return view;



    }

    // initialize boolean to know tab is already loaded or load first time




}
