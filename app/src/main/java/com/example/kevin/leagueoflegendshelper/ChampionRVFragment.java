package com.example.kevin.leagueoflegendshelper;

import android.app.Activity;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by Kevin on 4/18/2017.
 */

public class ChampionRVFragment extends Fragment implements ChampionRVAdapter.ChampClickListener,
                                                    SearchView.OnQueryTextListener {

    private RecyclerView rV;
    private RecyclerView.Adapter champAdapter;
    private RecyclerView.LayoutManager lM;

    private MenuItem searchMenuItem;
    private SearchView searchView;

    String lastQuery = "";

    private ChampRVCardClickedListener champRVCardClickedListener;

    public ChampionRVFragment() {
        //constructor for a fragment must be empty
    }

    public interface ChampRVCardClickedListener {
        public void champRVClicked(View v, int position);
    }

    @Override
    @Deprecated
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        champRVCardClickedListener = (ChampRVCardClickedListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.champ_rv_fragment_layout, container, false);

        setHasOptionsMenu(true);

        rV = (RecyclerView) view.findViewById(R.id.champRV);
        rV.setHasFixedSize(true);

        lM = new GridLayoutManager(view.getContext(), 4);

        champAdapter = new ChampionRVAdapter(this, ChampionList.getList(), getContext());

        rV.setLayoutManager(lM);

        AlphaInAnimationAdapter rvAnimator = new AlphaInAnimationAdapter(champAdapter);
        rvAnimator.setFirstOnly(false);

        rV.setAdapter(rvAnimator);

        if (ChampionList.getSize() == 0) {
            RiotPortal.DownloadAllChampions downloader = new RiotPortal.DownloadAllChampions((ChampionRVAdapter) champAdapter, ChampionList.getList());
            downloader.execute("h");
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_button_menu, menu);

        searchMenuItem = menu.findItem(R.id.action_Search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setOnQueryTextListener(this);

        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                lastQuery = "";
                Log.d("CLICK", "Search Action collapsed");
                ChampionList.restoreList();
                champAdapter.notifyDataSetChanged();
                //Log.d("test", "Size of ChampList after collapse " + ChampionList.getSize());
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.d("CLICK", "Search Action expanded");
                return true;
            }
        };


        MenuItemCompat.setOnActionExpandListener(searchMenuItem, expandListener);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {


        if (!lastQuery.equals(query)) {
            lastQuery = query;
            int position = ChampionList.findMatching(query);
            //Log.d("test", "Returned position: " + position);

            switch (position) {
                case -1:
                    break;
                default:
                    champAdapter.notifyDataSetChanged();
                    break;
            }

            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void champImageClicked(View view, int position) {
        searchMenuItem.collapseActionView();
        champRVCardClickedListener.champRVClicked(view, position);
    }
}
