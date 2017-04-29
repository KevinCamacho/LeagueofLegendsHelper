package com.example.kevin.leagueoflegendshelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 4/11/2017.
 */

public class ItemRVAdapter extends RecyclerView.Adapter<ItemRVAdapter.ItemViewHolder> {
    private List<Map<String, ?>> itemList;
    private ItemClickListener itemClickListener;

    Context context;


    public ItemRVAdapter(ItemClickListener listener, List<Map<String, ?>> list, Context theContext) {
        setItemClickListener(listener);
        itemList = list;
        context = theContext;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_layout, parent, false);

        ItemViewHolder itemVH = new ItemViewHolder(v);
        return itemVH;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        HashMap item = (HashMap) itemList.get(position);


        Picasso.with(context).load(RiotPortal.getItemImageURL() + item.get("imageLink")).into(holder.itemImage);

        holder.itemImage.setTransitionName("itemTrans" + position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface ItemClickListener {
        public void itemImageClicked(View view, int position);
    }

    public void setItemClickListener (ItemClickListener mItemClickListener) {
        this.itemClickListener = mItemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView itemImage;

        public ItemViewHolder(View itemView) {
            super(itemView);

            itemImage = (ImageView) itemView.findViewById(R.id.rv_item_Pic);

            itemImage.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            itemClickListener.itemImageClicked(v, getAdapterPosition());
        }
    }
}
