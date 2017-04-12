package com.example.kevin.leagueoflegendshelper;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 4/12/2017.
 */

public class ItemDetailFragment extends Fragment {
    private List<Map<String, ?>> allItems;

    private TextView totalGold;
    private TextView combineGold;
    private TextView description;

    private ImageView itemImage;

    private int itemID;

    public ItemDetailFragment() {
        //constructor for a fragment must be empty
    }

    public static ItemDetailFragment newInstance(int id) {
        Bundle bundle = new Bundle();

        bundle.putInt("id", id);

        ItemDetailFragment frag = new ItemDetailFragment();
        frag.setArguments(bundle);

        return frag;
    }

    private void readBundle(Bundle bundle) {
        itemID = bundle.getInt("id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readBundle(getArguments());

        View view = inflater.inflate(R.layout.item_detail_fragment_layout, container, false);

        totalGold = (TextView) view.findViewById(R.id.totalGold);
        combineGold = (TextView) view.findViewById(R.id.combineGold);
        description = (TextView) view.findViewById(R.id.description);

        itemImage = (ImageView) view.findViewById(R.id.itemImage);

        populateFields();

        return view;
    }

    private void populateFields() {
        HashMap currItem = (HashMap) ItemList.getItem(itemID);

        totalGold.setText(currItem.get("totalGold").toString());
        combineGold.setText(currItem.get("combineGold").toString());

        description.setText(currItem.get("description").toString());

        itemImage.setImageBitmap((Bitmap) currItem.get("image"));
    }
}
