package com.example.kevin.leagueoflegendshelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Map;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * Created by Kevin on 4/24/2017.
 */

public class SummonerSearchedFragment extends Fragment implements RiotPortal.GetRecentMatches.test {

    private String summonerName;
    private String summonerID;
    private String summonerProfileIconID;
    private String summonerLevel;

    private ImageView layoutProfileIcon;
    private TextView layoutSummonerName;
    private ImageView layoutRankIcon;
    private TextView layoutRankLabel;

    private Intent intentShare;

    private String shareMessage = "Oops! Something went wrong!";

    private MenuItem shareItem;
    private ShareActionProvider shareProvider;

    private MatchList matchList;

    private RecyclerView rV;
    private RecyclerView.Adapter matchAdapter;
    private RecyclerView.LayoutManager lM;

    public SummonerSearchedFragment() {
        //constructor needs to be empty
    }

    public static SummonerSearchedFragment newInstance(String name, String id, String profileIconid, String level) {
        SummonerSearchedFragment frag = new SummonerSearchedFragment();

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("accountID", id);
        bundle.putString("profileIconID", profileIconid);
        bundle.putString("summonerLevel", level);

        frag.setArguments(bundle);

        return frag;
    }

    private void readBundle(Bundle bundle) {
        summonerName = bundle.getString("name");
        summonerID = bundle.getString("accountID");
        summonerProfileIconID = bundle.getString("profileIconID");
        summonerLevel = bundle.getString("summonerLevel");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.summoner_searched_fragment_layout, container, false);

        readBundle(getArguments());

        setHasOptionsMenu(true);

        layoutProfileIcon = (ImageView) view.findViewById(R.id.profileIcon);
        layoutSummonerName = (TextView) view.findViewById(R.id.summonerName);
        layoutRankIcon = (ImageView) view.findViewById(R.id.rankIcon);
        layoutRankLabel = (TextView) view.findViewById(R.id.rankLabel);

        layoutSummonerName.setText(summonerName);

        Picasso.with(getContext()).load(RiotPortal.getProfileIconURL(summonerProfileIconID)).into(layoutProfileIcon);


        RiotPortal.GetLeaguesInfo dl = new RiotPortal.GetLeaguesInfo(layoutRankLabel, layoutRankIcon);
        dl.execute(summonerID);

        if (matchList == null) {
            matchList = new MatchList();
            matchAdapter = new MatchListRVAdapter(matchList, getContext());
            RiotPortal.GetRecentMatches downloader = new RiotPortal.GetRecentMatches(matchList, matchAdapter, this);
            //Log.d("test", "From SummonerSearchedFragment: SummonerID = " + summonerID);
            downloader.execute(summonerID);
        }





        rV = (RecyclerView) view.findViewById(R.id.matchRV);


        lM = new LinearLayoutManager(getContext());


        rV.setAdapter(matchAdapter);
        rV.setLayoutManager(lM);

        rV.setItemAnimator(new SlideInLeftAnimator());



        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.share_button_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("test", item.getItemId() + "");
        switch (item.getItemId()) {
            case R.id.action_Share:


                //Log.d("test", "Wins: " + numWins + "\nDefeats: " + numLoss + "\nKills: " + totalKill + "\nDeaths: " + totalDeath + "\nAssists: " + totalAssist + "\nKDA: " + kda + "\n");

                actionShare(shareMessage);

                Log.d("test", shareMessage);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void doneDownloading() {
        //Log.d("test", matchList.getSize() + "");

        int numWins = 0;
        int numLoss = 0;
        double totalKill = 0;
        double totalDeath = 0;
        double totalAssist = 0;

        for (Map<String, ?> match : matchList.getList()) {
            boolean win = Boolean.parseBoolean(match.get("win").toString());

            if (win) {
                numWins++;
            }
            else if (!win){
                numLoss++;
            }

            totalKill += Integer.parseInt(match.get("kills").toString());
            totalDeath += Integer.parseInt(match.get("deaths").toString());
            totalAssist += Integer.parseInt(match.get("assists").toString());

        }

        double kda =  (totalKill + totalAssist) / totalDeath;
        new DecimalFormat("#.##").format(kda);
        shareMessage = "In the past " + (numWins+numLoss) + " games, " + summonerName + " has won " + numWins + ", with a total of " + (int) totalKill + " kills, " + (int) totalDeath + " deaths, and " + (int) totalAssist + " assists, for a " + new DecimalFormat("#.##").format(kda) + "KDA.";



    }

    private void actionShare(String shareMessage) {
        intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(intentShare);
    }
}
