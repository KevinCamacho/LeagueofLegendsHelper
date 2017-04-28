package com.example.kevin.leagueoflegendshelper;

import android.app.Activity;
import android.os.Bundle;
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

/**
 * Created by Kevin on 4/11/2017.
 */

public class ItemRVFragment extends Fragment implements ItemRVAdapter.ItemClickListener,
                                                SearchView.OnQueryTextListener {

    private RecyclerView rV;
    private RecyclerView.Adapter itemAdapter;
    private RecyclerView.LayoutManager lM;

    private MenuItem searchMenuItem;
    private SearchView searchView;

    private String lastQuery = "";

    private ItemRVCardClickListener itemRVClicked;

    public ItemRVFragment() {
        //constructor for a fragment must be empty
    }

    public interface ItemRVCardClickListener {
        public void itemRVClicked(View v, int position);
    }

    @Override
    @Deprecated
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        itemRVClicked = (ItemRVCardClickListener) activity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_rv_fragment_layout, container, false);

        setHasOptionsMenu(true);

        rV = (RecyclerView) view.findViewById(R.id.itemRV);
        rV.setHasFixedSize(true);

        lM = new GridLayoutManager(view.getContext(), 4);


        itemAdapter = new ItemRVAdapter(this, ItemList.getList(), getContext());

        rV.setLayoutManager(lM);

        AlphaInAnimationAdapter rvAnimator = new AlphaInAnimationAdapter(itemAdapter);
        rvAnimator.setFirstOnly(false);

        rV.setAdapter(rvAnimator);

        if (ItemList.getSize() == 0) {
            RiotPortal.DownloadAllItems downloader = new RiotPortal.DownloadAllItems((ItemRVAdapter) itemAdapter, ItemList.getList());
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
                        ItemList.restoreList();
                        itemAdapter.notifyDataSetChanged();
                        //Log.d("test", "Size of ItemList after collapse " + ItemList.getSize());
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
        //Log.d("test", "Returned position: " + position);

        if (!lastQuery.equals(query)) {
            lastQuery = query;
            int position = ItemList.findMatching(query);
            switch (position) {
                case -1:
                    break;
                default:
                    itemAdapter.notifyDataSetChanged();
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
    public void itemImageClicked(View view, int position) {

        String id = ItemList.getItem(position).get("id").toString();

        ItemList.restoreList();
        itemAdapter.notifyDataSetChanged();

        int index = ItemList.getIndexOf(id);
        try {
            rV.scrollToPosition(index+5);
        }
        catch(Exception e) {
            rV.scrollToPosition(index);
        }

        searchMenuItem.collapseActionView();

        itemRVClicked.itemRVClicked(view, index);

    }
}
