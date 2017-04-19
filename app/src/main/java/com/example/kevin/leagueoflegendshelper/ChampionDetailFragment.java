package com.example.kevin.leagueoflegendshelper;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 4/18/2017.
 */

public class ChampionDetailFragment extends Fragment {

    private int champIndex;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;

    private List<Map<String, ?>> champInfo = new ArrayList<Map<String, ?>>();

    public ChampionDetailFragment() {
        //constructor for a fragment must be empty
    }

    public static ChampionDetailFragment newInstance(int position) {
        Bundle bundle = new Bundle();

        bundle.putInt("position", position);

        ChampionDetailFragment frag = new ChampionDetailFragment();
        frag.setArguments(bundle);

        return frag;
    }

    public void readBundle(Bundle bundle) {
        if (bundle != null) {
            champIndex = bundle.getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readBundle(getArguments());

        View view;
        view = inflater.inflate(R.layout.champ_detail_fragment_layout, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.view_Pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_Layout);

        adapter = new ChampStatePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);

        RiotPortal.DownloadChampionInformation downloader;

        downloader = new RiotPortal.DownloadChampionInformation(adapter, champInfo);

        HashMap currChamp = (HashMap) ChampionList.getItem(champIndex);
        String currChampID = (String) currChamp.get("id");

        downloader.execute(currChampID);

        return view;
    }

    private class ChampStatePagerAdapter extends FragmentPagerAdapter {

        public ChampStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

            }
            return ItemDetailFragment.newInstance(1, "lul");
        }

        @Override
        public int getCount() {
            return champInfo.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ((HashMap) champInfo.get(position)).get("name").toString();
            //return "test";
        }
    }
}
