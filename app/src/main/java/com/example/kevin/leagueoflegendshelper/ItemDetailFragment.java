package com.example.kevin.leagueoflegendshelper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    private TextView buildsOutOfLabel;

    private ImageView itemImage;
    private ImageView fromImage1;
    private ImageView fromImage2;
    private ImageView fromImage3;

    private int itemIndex;

    private String transitionName;

    private int item1From = -1;
    private int item2From = -1;
    private int item3From = -1;

    private ItemDetailFragClick itemDetailFragClick;



    public ItemDetailFragment() {
        //constructor for a fragment must be empty
    }

    public static ItemDetailFragment newInstance(int id, String transitionName) {
        Bundle bundle = new Bundle();

        bundle.putInt("id", id);
        bundle.putString("transitionName", transitionName);

        ItemDetailFragment frag = new ItemDetailFragment();
        frag.setArguments(bundle);

        return frag;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            itemIndex = bundle.getInt("id");
            this.transitionName = bundle.getString("transitionName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readBundle(getArguments());

        View view = inflater.inflate(R.layout.item_detail_fragment_layout, container, false);

        totalGold = (TextView) view.findViewById(R.id.totalGold);
        combineGold = (TextView) view.findViewById(R.id.combineGold);
        description = (TextView) view.findViewById(R.id.description);
        buildsOutOfLabel = (TextView) view.findViewById(R.id.buildsOutOfLabel);

        itemImage = (ImageView) view.findViewById(R.id.itemImage);
        fromImage1 = (ImageView) view.findViewById(R.id.fromImage1);
        fromImage2 = (ImageView) view.findViewById(R.id.fromImage2);
        fromImage3 = (ImageView) view.findViewById(R.id.fromImage3);

        populateFields();

        return view;
    }

    private void populateFields() {
        HashMap currItem = (HashMap) ItemList.getItem(itemIndex);

        totalGold.setText(currItem.get("totalGold").toString());
        combineGold.setText(currItem.get("combineGold").toString());

        description.setText(currItem.get("description").toString());

        Picasso.with(getContext()).load(RiotPortal.getItemImageURL() + currItem.get("imageLink")).into(itemImage);

        //itemImage.setImageBitmap((Bitmap) currItem.get("image"));

        ArrayList<String> fromArray = (ArrayList<String>) currItem.get("from");

        itemImage.setTransitionName(transitionName);

        switch(fromArray.size()) {
            case 3:
                item3From = ItemList.getIndexOf(fromArray.get(2).toString());
                HashMap<String, ?> item3 = (HashMap) ItemList.getItem(item3From);
                Picasso.with(getContext()).load(RiotPortal.getItemImageURL() + item3.get("imageLink")).into(fromImage3);
                //fromImage3.setImageBitmap((Bitmap) item3.get("image"));
                fromImage3.setTransitionName(transitionName+item3.get("id") + "3");
                fromImage3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemDetailFragClick.itemDetailFromClicked(v, item3From);
                    }
                });
            case 2:
                item2From = ItemList.getIndexOf(fromArray.get(1).toString());
                HashMap<String, ?> item2 = (HashMap) ItemList.getItem(item2From);
                Picasso.with(getContext()).load(RiotPortal.getItemImageURL() + item2.get("imageLink")).into(fromImage2);
                //fromImage2.setImageBitmap((Bitmap) item2.get("image"));
                fromImage2.setTransitionName(transitionName+item2.get("id") + "2");
                fromImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemDetailFragClick.itemDetailFromClicked(v, item2From);
                    }
                });
            case 1:
                item1From = ItemList.getIndexOf(fromArray.get(0).toString());
                HashMap<String, ?> item1 = (HashMap) ItemList.getItem(item1From);
                Picasso.with(getContext()).load(RiotPortal.getItemImageURL() + item1.get("imageLink")).into(fromImage1);
                //fromImage1.setImageBitmap((Bitmap) item1.get("image"));
                fromImage1.setTransitionName(transitionName+item1.get("id") + "1");
                fromImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemDetailFragClick.itemDetailFromClicked(v, item1From);
                    }
                });
                buildsOutOfLabel.setVisibility(View.VISIBLE);
            default:
                break;
        }
        try {
        }
        catch(IndexOutOfBoundsException e) {
            Log.d("from", "There are not that many from items");
        }


    }

    @Override
    @Deprecated
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        itemDetailFragClick = (ItemDetailFragClick) activity;
    }

    public interface ItemDetailFragClick {
        public void itemDetailFromClicked(View v, int position);
    }
}
