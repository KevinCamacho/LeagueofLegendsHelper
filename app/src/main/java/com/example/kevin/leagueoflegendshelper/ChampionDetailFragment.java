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

/**
 * Created by Kevin on 4/18/2017.
 */

public class ChampionDetailFragment extends Fragment {

    private int position;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ChampionDetailFragment() {
        //constructor for a fragment must be empty
    }

    public ChampionDetailFragment newInstance(int position) {
        Bundle bundle = new Bundle();

        bundle.putInt("position", position);

        ChampionDetailFragment frag = new ChampionDetailFragment();
        frag.setArguments(bundle);

        return frag;
    }

    public void readBundle(Bundle bundle) {
        if (bundle != null) {
            position = bundle.getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readBundle(getArguments());

        View view;
        view = inflater.inflate(R.layout.champ_detail_fragment_layout, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.view_Pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_Layout);

        viewPager.setAdapter(new ChampStatePagerAdapter(getFragmentManager()));
        viewPager.setCurrentItem(0);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }

    private class ChampStatePagerAdapter extends FragmentPagerAdapter {

        public ChampStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }
}
