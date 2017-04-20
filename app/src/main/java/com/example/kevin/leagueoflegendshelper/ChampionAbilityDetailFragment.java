package com.example.kevin.leagueoflegendshelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class ChampionAbilityDetailFragment extends Fragment {

    private HashMap abilityHash;

    boolean isPassive;

    private ImageView abilityImage;
    private TextView abilityName;
    private TextView abilityDescription;

    public ChampionAbilityDetailFragment() {
        //fragment constructor should be empty
    }

    public static ChampionAbilityDetailFragment newInstance(HashMap hash, boolean passive) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("hash", hash);
        bundle.putBoolean("isPassive", passive);

        ChampionAbilityDetailFragment frag = new ChampionAbilityDetailFragment();
        frag.setArguments(bundle);

        return frag;
    }

    private void readBundle(Bundle bundle) {
        abilityHash = (HashMap) bundle.getSerializable("hash");
        isPassive = bundle.getBoolean("isPassive");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readBundle(getArguments());

        View view = inflater.inflate(R.layout.champ_ability_detail_fragment_layout, container, false);

        abilityImage = (ImageView) view.findViewById(R.id.abilityImage);
        abilityName = (TextView) view.findViewById(R.id.abilityName);
        abilityDescription = (TextView) view.findViewById(R.id.abilityDescription);

        populateFields();

        return view;
    }

    private void populateFields() {
        abilityName.setText(abilityHash.get("name").toString());
        abilityDescription.setText(abilityHash.get("description").toString());

        if (isPassive) {
            Picasso.with(getContext()).load(RiotPortal.getAbilityImageURL() + "/passive/" + abilityHash.get("imageLink")).into(abilityImage);
        }
        else {
            Picasso.with(getContext()).load(RiotPortal.getAbilityImageURL() + "/spell/" + abilityHash.get("imageLink")).into(abilityImage);
        }
    }
}
