package com.example.kevin.leagueoflegendshelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 4/11/2017.
 */

public class ItemRVFragment extends Fragment {

    private RecyclerView rV;
    private List<Map<String, ?>> itemList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_rv_fragment_layout, container, false);

        rV = (RecyclerView) view.findViewById(R.id.itemRV);
        rV.setHasFixedSize(true);

        //RiotPortal.DownloadAllItems downloader = new RiotPortal.DownloadAllItems();
        //downloader.execute("h");



        return view;
    }

}
