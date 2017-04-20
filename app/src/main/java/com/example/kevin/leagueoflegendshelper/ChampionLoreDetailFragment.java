package com.example.kevin.leagueoflegendshelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * Created by Kevin on 4/18/2017.
 */

public class ChampionLoreDetailFragment extends Fragment {

    private HashMap loreHash;

    public ChampionLoreDetailFragment() {
        //fragment constructor should be empty
    }

    public static ChampionLoreDetailFragment newInstance(HashMap hash) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("hash", hash);

        ChampionLoreDetailFragment frag = new ChampionLoreDetailFragment();
        frag.setArguments(bundle);

        return frag;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            loreHash = (HashMap) bundle.getSerializable("hash");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readBundle(getArguments());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
