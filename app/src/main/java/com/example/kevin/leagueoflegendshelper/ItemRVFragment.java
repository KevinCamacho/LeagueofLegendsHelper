package com.example.kevin.leagueoflegendshelper;

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
    private List<Map<String, ?>> itemList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_rv_fragment_layout, container, false);

        itemList = new ArrayList<Map<String,?>>();

        rV = (RecyclerView) view.findViewById(R.id.itemRV);
        rV.setHasFixedSize(true);

        //lM = new LinearLayoutManager(view.getContext());
        lM = new GridLayoutManager(view.getContext(), 4);

        itemAdapter = new ItemRVAdapter(this, itemList);

        rV.setLayoutManager(lM);

        rV.setAdapter(itemAdapter);


        RiotPortal.DownloadAllItems downloader = new RiotPortal.DownloadAllItems( (ItemRVAdapter) itemAdapter, itemList);
        downloader.execute("h");



        return view;
    }

    @Override
    public void itemImageClicked(View view, int position) {
        Log.d("test", "Image with id " + itemList.get(position).get("id") + " was clicked");
    }
}
