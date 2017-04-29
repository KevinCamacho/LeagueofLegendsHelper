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
 * Created by Kevin on 4/18/2017.
 */

public class ChampionRVAdapter extends RecyclerView.Adapter<ChampionRVAdapter.ChampViewHolder> {
    private List<Map<String, ?>> champList;
    private ChampClickListener champClickListener;

    private Context context;

    public ChampionRVAdapter(ChampClickListener listener, List<Map<String, ?>> list, Context theContext) {
        setListener(listener);
        champList = list;
        context = theContext;
    }

    @Override
    public ChampViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_champ_layout, parent, false);

        ChampViewHolder champVH = new ChampViewHolder(v);

        return champVH;
    }

    @Override
    public void onBindViewHolder(ChampViewHolder holder, int position) {
        HashMap champ = (HashMap) champList.get(position);

        Picasso.with(context).load(RiotPortal.getChampImageURL() +champ.get("imageLink")).into(holder.champImage);

        holder.champImage.setTransitionName("champTrans" + position);
    }

    @Override
    public int getItemCount() {
        return champList.size();
    }

    public interface ChampClickListener {
        public void champImageClicked(View view, int position);
    }

    public void setListener(ChampClickListener l) {
        this.champClickListener = l;
    }

    public class ChampViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView champImage;

        public ChampViewHolder(View itemView) {
            super(itemView);

            champImage = (ImageView) itemView.findViewById(R.id.rv_champ_Pic);

            champImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Log.d("test", "from adapter" + getAdapterPosition()+"");
            champClickListener.champImageClicked(v, getAdapterPosition());
        }
    }
}
