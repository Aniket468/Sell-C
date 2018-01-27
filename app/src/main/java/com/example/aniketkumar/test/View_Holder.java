package com.example.aniketkumar.test;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Aniket Kumar on 24-Jan-18.
 */


public class View_Holder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView title;
    TextView description;
    TextView price,sr,id,rupee;
    ImageView imageView;
    View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        title = (TextView) itemView.findViewById(R.id.name);
        price=itemView.findViewById(R.id.price);
        id=itemView.findViewById(R.id.id);
        sr=itemView.findViewById(R.id.sr);
        sr.setVisibility(View.GONE);
        id.setVisibility(View.GONE);
        description = (TextView) itemView.findViewById(R.id.description);
        imageView = (ImageView) itemView.findViewById(R.id.image123);
    }
}