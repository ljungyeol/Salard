package com.ghosthawk.salard.Sell;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabWidget;
import android.widget.TextView;

import com.ghosthawk.salard.Buy.BuyHomeFollowFragment;
import com.ghosthawk.salard.Buy.BuyHomeMeFragment;
import com.ghosthawk.salard.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellHomeFragment extends Fragment {


    public SellHomeFragment() {
        // Required empty public constructor
    }

    FragmentTabHost tabHost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_home, container, false);
        tabHost = (FragmentTabHost)view.findViewById(R.id.tabhost);
        tabHost.setup(getContext(),getChildFragmentManager(),android.R.id.tabcontent);

        TabWidget tabWidget = (TabWidget)tabHost.findViewById(android.R.id.tabs);

        View tabHeader = inflater.inflate(R.layout.tab_header,tabWidget,false);
        TextView titleView = (TextView)tabHeader.findViewById(R.id.text_title);
        titleView.setText(R.string.home_tab_me_title);
        tabHost.addTab(tabHost.newTabSpec("Me").setIndicator(tabHeader),SellHomeMeFragment.class,null);

        tabHeader = inflater.inflate(R.layout.tab_header,tabWidget,false);
        titleView = (TextView)tabHeader.findViewById(R.id.text_title);
        titleView.setText(R.string.home_tab_sell_follower_title);
        tabHost.addTab(tabHost.newTabSpec("Followers").setIndicator(tabHeader),SellHomeFollowFragment.class,null);
        //tabHost.addTab(tabHost.newTabSpec("category").setIndicator(getString(R.string.CATEGORY)),TStoreCategoryFragment.class,null);
        //tabHost.addTab(tabHost.newTabSpec("search").setIndicator(getString(R.string.SEARCH)),TStoreSearchFragment.class,null);



        return view;}
}
