package com.example.kevin.leagueoflegendshelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kevin on 4/24/2017.
 */

public class SummonerSearchedFragment extends Fragment implements RiotPortal.GetRecentMatches.test {

    private String summonerName;
    private String summonerID;

    private MatchList matchList;

    private RecyclerView rV;
    private RecyclerView.Adapter matchAdapter;
    private RecyclerView.LayoutManager lM;

    public SummonerSearchedFragment() {
        //constructor needs to be empty
    }

    public static SummonerSearchedFragment newInstance(String name, String id) {
        SummonerSearchedFragment frag = new SummonerSearchedFragment();

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("id", id);

        frag.setArguments(bundle);

        return frag;
    }

    private void readBundle(Bundle bundle) {
        summonerName = bundle.getString("name");
        summonerID = bundle.getString("id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.summoner_searched_fragment_layout, container, false);

        readBundle(getArguments());

        matchList = new MatchList();


        matchAdapter = new MatchListRVAdapter(matchList, getContext());

        RiotPortal.GetRecentMatches downloader = new RiotPortal.GetRecentMatches(matchList, matchAdapter, this);
        Log.d("test", "From SummonerSearchedFragment: SummonerID = " + summonerID);
        downloader.execute(summonerID);


        rV = (RecyclerView) view.findViewById(R.id.matchRV);
        //rV.setHasFixedSize(true);


        lM = new LinearLayoutManager(getContext());

        rV.setAdapter(matchAdapter);
        rV.setLayoutManager(lM);


        return view;
    }

    @Override
    public void onResume() {
        matchList.getList().clear();
        matchAdapter.notifyDataSetChanged();
        super.onResume();
    }



    @Override
    public void test() {
        Log.d("test", matchList.getSize() + "" );
    }
}
