package com.example.kevin.leagueoflegendshelper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by Kevin on 4/25/2017.
 */

public class MatchListRVAdapter extends RecyclerView.Adapter<MatchListRVAdapter.MatchViewHolder> {

    private MatchList matchList;
    private Context context;

    public MatchListRVAdapter(MatchList mL, Context theContext) {
        this.matchList = mL;
        this.context = theContext;
    }

    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_match_layout, parent, false);


        MatchViewHolder vH = new MatchViewHolder(v);

        return vH;
    }

    @Override
    public void onBindViewHolder(MatchViewHolder holder, int position) {

        HashMap match = (HashMap) matchList.getItem(position);
        Picasso.with(context).load(RiotPortal.getItemImageURL() + match.get("item1ID") + ".png").into(holder.item1);
        Picasso.with(context).load(RiotPortal.getItemImageURL() + match.get("item2ID") + ".png").into(holder.item2);
        Picasso.with(context).load(RiotPortal.getItemImageURL() + match.get("item3ID") + ".png").into(holder.item3);
        Picasso.with(context).load(RiotPortal.getItemImageURL() + match.get("item4ID") + ".png").into(holder.item4);
        Picasso.with(context).load(RiotPortal.getItemImageURL() + match.get("item5ID") + ".png").into(holder.item5);
        Picasso.with(context).load(RiotPortal.getItemImageURL() + match.get("item6ID") + ".png").into(holder.item6);
        Picasso.with(context).load(RiotPortal.getItemImageURL() + match.get("trinketID") + ".png").into(holder.trinket);
        Picasso.with(context).load(RiotPortal.getChampImageURL() + match.get("imageLink")).into(holder.playedChamp);

        switch((String) match.get("win")) {
            case "true":
                holder.winOrLose.setText("VICTORY");
                holder.cardView.setCardBackgroundColor(Color.parseColor("#A0DEB3"));
                break;
            case "false":
                holder.winOrLose.setText("DEFEAT");
                holder.cardView.setCardBackgroundColor(Color.parseColor("#E4A0A0"));
                break;
            default:
                holder.winOrLose.setText("VICTORY");
                break;
        }

        String kda = match.get("kills") + "/" + match.get("deaths") + "/" + match.get("assists");
        holder.kda.setText(kda);

    }

    @Override
    public int getItemCount() {
        return matchList.getSize();
    }

    public class MatchViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        private TextView winOrLose;
        private TextView kda;
        private ImageView playedChamp;
        private ImageView item1;
        private ImageView item2;
        private ImageView item3;
        private ImageView item4;
        private ImageView item5;
        private ImageView item6;
        private ImageView trinket;

        public MatchViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.matchRVCard);

            winOrLose = (TextView) itemView.findViewById(R.id.winOrLose);
            kda = (TextView) itemView.findViewById(R.id.kda);
            playedChamp = (ImageView) itemView.findViewById(R.id.playedChampPic);
            item1 = (ImageView) itemView.findViewById(R.id.item1);
            item2 = (ImageView) itemView.findViewById(R.id.item2);
            item3 = (ImageView) itemView.findViewById(R.id.item3);
            item4 = (ImageView) itemView.findViewById(R.id.item4);
            item5 = (ImageView) itemView.findViewById(R.id.item5);
            item6 = (ImageView) itemView.findViewById(R.id.item6);

            trinket = (ImageView) itemView.findViewById(R.id.trinket);

        }
    }
}
