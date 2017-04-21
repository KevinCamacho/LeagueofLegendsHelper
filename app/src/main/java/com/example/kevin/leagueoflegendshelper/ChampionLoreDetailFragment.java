package com.example.kevin.leagueoflegendshelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by Kevin on 4/18/2017.
 */

public class ChampionLoreDetailFragment extends Fragment {

    private HashMap loreHash;

    private ImageView champImage;
    private TextView champName;
    private TextView champTitle;
    private TextView champLore;


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

        View view = inflater.inflate(R.layout.champ_lore_detail_fragment_layout, container, false);

        champImage = (ImageView) view.findViewById(R.id.champImage);
        champName = (TextView) view.findViewById(R.id.champName);
        champTitle = (TextView) view.findViewById(R.id.champTitle);
        champLore = (TextView) view.findViewById(R.id.champLore);


        populateFields();

        return view;
    }

    private void populateFields() {
        champName.setText(loreHash.get("name").toString());
        champTitle.setText(loreHash.get("title").toString());
        champLore.setText(loreHash.get("lore").toString());

        Picasso.with(getContext()).load(RiotPortal.getChampImageURL() + loreHash.get("imageLink")).into(champImage);
    }
}
