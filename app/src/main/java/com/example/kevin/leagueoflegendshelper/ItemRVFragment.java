package com.example.kevin.leagueoflegendshelper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //private List<Map<String, ?>> itemList;

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

        //itemList = new ArrayList<Map<String,?>>();

        setHasOptionsMenu(true);

        rV = (RecyclerView) view.findViewById(R.id.itemRV);
        rV.setHasFixedSize(true);

        //lM = new LinearLayoutManager(view.getContext());
        lM = new GridLayoutManager(view.getContext(), 4);


        itemAdapter = new ItemRVAdapter(this, ItemList.getList(), getContext());

        rV.setLayoutManager(lM);

        rV.setAdapter(itemAdapter);

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

        /*searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                                                     @Override
                                                     public boolean onMenuItemActionExpand(MenuItem item) {
                                                         Log.d("CLICK", "Search Action collapsed");
                                                         ItemList.restoreList();
                                                         itemAdapter.notifyDataSetChanged();
                                                         return true;
                                                     }

                                                     @Override
                                                     public boolean onMenuItemActionCollapse(MenuItem item) {
                                                         Log.d("CLICK", "Search Action expanded");
                                                         return true;
                                                     }
                                                 });*/


                MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        Log.d("CLICK", "Search Action collapsed");
                        ItemList.restoreList();
                        itemAdapter.notifyDataSetChanged();
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
        int position = ItemList.findMatching(query);
        //Log.d("test", "Returned position: " + position);

        switch (position) {
            case -1:
                break;
            default:
                itemAdapter.notifyDataSetChanged();
                break;
        }
        /*for (Map<String, ?> item : ItemList.getList()) {
            Log.d("test", item.get("name").toString());
        }*/
        //searchMenuItem.collapseActionView();

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        /*View v = this.getActivity().getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) this.get(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }*/

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    @Override
    public void itemImageClicked(View view, int position) {

        itemRVClicked.itemRVClicked(view, position);

    }
}
