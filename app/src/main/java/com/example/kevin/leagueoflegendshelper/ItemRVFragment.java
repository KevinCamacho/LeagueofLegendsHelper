package com.example.kevin.leagueoflegendshelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 4/11/2017.
 */

public class ItemRVFragment extends Fragment implements ItemRVAdapter.ItemClickListener {

    private RecyclerView rV;
    private RecyclerView.Adapter itemAdapter;
    private RecyclerView.LayoutManager lM;
    //private List<Map<String, ?>> itemList;

    private ItemRVCardClickListener clickListener;

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
        clickListener = (ItemRVCardClickListener) activity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_rv_fragment_layout, container, false);

        //itemList = new ArrayList<Map<String,?>>();

        rV = (RecyclerView) view.findViewById(R.id.itemRV);
        rV.setHasFixedSize(true);

        //lM = new LinearLayoutManager(view.getContext());
        lM = new GridLayoutManager(view.getContext(), 4);


        itemAdapter = new ItemRVAdapter(this, ItemList.getList());

        rV.setLayoutManager(lM);

        rV.setAdapter(itemAdapter);

        if (ItemList.getSize() == 0) {
            RiotPortal.DownloadAllItems downloader = new RiotPortal.DownloadAllItems((ItemRVAdapter) itemAdapter, ItemList.getList());
            downloader.execute("h");
        }



        return view;
    }

    @Override
    public void itemImageClicked(View view, int position) {

        clickListener.itemRVClicked(view, position);

    }
}
