package com.example.kevin.leagueoflegendshelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kevin on 4/18/2017.
 */

public class ChampionRVFragment extends Fragment implements ChampionRVAdapter.ChampClickListener {

    private RecyclerView rV;
    private RecyclerView.Adapter champAdapter;
    private RecyclerView.LayoutManager lM;

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

        rV = (RecyclerView) view.findViewById(R.id.champRV);
        rV.setHasFixedSize(true);

        lM = new GridLayoutManager(view.getContext(), 4);

        champAdapter = new ChampionRVAdapter(this, ChampionList.getList(), getContext());

        rV.setLayoutManager(lM);

        rV.setAdapter(champAdapter);

        if (ChampionList.getSize() == 0) {
            RiotPortal.DownloadAllChampions downloader = new RiotPortal.DownloadAllChampions((ChampionRVAdapter) champAdapter, ChampionList.getList());
            downloader.execute("h");
        }

        return view;
    }

    @Override
    public void champImageClicked(View view, int position) {
        champRVCardClickedListener.champRVClicked(view, position);
    }
}
